package com.tianhan.cloud.gateway.ribbon;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.ribbon.ExtendBalancer;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author NieAnTai
 * @Date 2021/4/15 9:29 上午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description Ribbon 服务隔离策略,参考NacosRule，添加version过滤代码
 **/
@Slf4j
public class ServerIsolationRule extends AbstractLoadBalancerRule {
    @Value("${spring.cloud.nacos.discovery.metadata.isolation.version:dev}")
    private String isolation;
    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;
    @Autowired
    private NacosServiceManager nacosServiceManager;

    @Override
    public Server choose(Object key) {
        try {
            String group = this.nacosDiscoveryProperties.getGroup();
            DynamicServerListLoadBalancer loadBalancer = (DynamicServerListLoadBalancer) getLoadBalancer();
            // 获取服务名
            String name = loadBalancer.getName();

            NamingService namingService = nacosServiceManager
                    .getNamingService(nacosDiscoveryProperties.getNacosProperties());
            // 获取服务列表
            List<Instance> instances = namingService.selectInstances(name, group, true);
            List<Instance> sameIsolationInstances = instances.stream()
                    .filter(instance -> {
                        // 判断服务隔离标志
                        String var = instance.getMetadata().getOrDefault("isolation.version", "dev");
                        return isolation.equals(var);
                    })
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(sameIsolationInstances)) {
                // 找不到对于隔离服务，返回null
                log.warn("服务名: {} 隔离标志：{} 找不到服务服务列表", name, isolation);
                return null;
            }

            Instance instance = ExtendBalancer.getHostByRandomWeight2(sameIsolationInstances);

            return new NacosServer(instance);
        } catch (Exception e) {
            log.warn("SeverIsolationRule error", e);
            return null;
        }
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
    }
}

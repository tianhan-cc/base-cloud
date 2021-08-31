package com.tianhan.cloud.gateway.route;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.Executor;

/**
 * @Author NieAnTai
 * @Date 2021/3/15 4:21 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description 动态路由配置
 **/
@Slf4j
@Component
public class NacosDynamicRoute implements RouteDefinitionRepository {
    @Resource
    private NacosConfigManager nacosConfigManager;
    @Resource
    private ApplicationEventPublisher publisher;
    @Value("${nacos.config.dynamic-route.date-id}")
    private String dynamicRouteDateId;
    @Value("${spring.cloud.nacos.config.namespace}")
    private String configNamespace;

    private Flux<RouteDefinition> route;

    @PostConstruct
    public void init() {
        log.info("================初始化动态路由================");
        refreshRoute();
        createListener();
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return route;
    }

    public void refreshRoute() {
        try {
            String routeConfigInfo = nacosConfigManager.getConfigService()
                    .getConfig(dynamicRouteDateId, nacosConfigManager.getNacosConfigProperties().getGroup(), 1000);
//            route = Flux.fromIterable(JSONObject.parseArray(routeConfigInfo, RouteDefinition.class));
            route = Flux.fromIterable(JSONObject.parseArray("[]", RouteDefinition.class));
        } catch (NacosException ignore) {
            log.error("动态路由获取失败");
            route = Flux.empty();
        }
    }

    public void createListener() {
        try {
            nacosConfigManager.getConfigService()
                    .addListener(dynamicRouteDateId, nacosConfigManager.getNacosConfigProperties().getGroup(), new Listener() {
                        @Override
                        public Executor getExecutor() {
                            return null;
                        }

                        @Override
                        public void receiveConfigInfo(String configInfo) {
                            log.info("==========监听配置文件变化,刷新路由==========");
                            refreshRoute();
                            publisher.publishEvent(new RefreshRoutesEvent(this));
                        }
                    });
        } catch (NacosException e) {
            e.printStackTrace();
            log.error("==========动态路由Nacos配置文件监听失败!===========");
        }
    }


    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return null;
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }
}

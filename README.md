# Base-cloud

## Spring Cloud 基础包

```
服务器注册与发现: Nacos
网关: Spring Gateway
服务调度: Dubbo
流量监控: Alibaba Sentinel
分布式事务: Seata
队列: RabbitMq
熔断: Hystrix
负载: Ribbon
认证: Spring Security
```

## gateway-dynamic-route 路由配置

```
- id: usercenter
  uri: lb://usercenter
  predicates:
  - Path=/usercenter/**
  filters:
  - StripPrefix=1
```
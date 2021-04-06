#  Skywalking K8S 搭建

**准备工作**

```shell
git clone https://github.com/apache/skywalking-kubernetes

cd skywalking-kubernetes/chart

helm dep up skywalking
```

**helm 3 skywalking**

> 这里采用H2存储 高可用请使用 elasticsearch 

```shell
helm -n {your-namespace} install skywalking skywalking --set oap.storageType='h2' --set ui.image.tag=8.3.0 --set oap.image.tag=8.3.0-es7 --set oap.replicas=2 --set oap.env.JAVA_OPTS='-Dmode=' --set elasticsearch.enabled=false --set oap.envoy.als.enabled=true
```

**服务端口**

skywalking-oap  12800/TCP  11800/TCP

skywalking-ui	  80/TCP

**Java探针 agent.config 配置**

```
collector.backend_service=skywalking-oap.{your-namespace}.svc.cluster.local:11800
```

**Agent 可选插件**

```
agent/optional-plugins
# 复制需要的 optional-plugin 到 plugins 目录下
# 如使用 apm-spring-annotation-plugin 可在Spring项目中 进行链路追踪时看到更多细节 （影响性能）
apm-spring-annotation-plugin-8.3.0.jar
```

**Java 项目 DockerFile 启动时添加参数** 

```
ENTRYPOINT java -jar -javaagent:skywalking/skywalking-agent.jar -Dskywalking.agent.service_name={your-app-name} springboot-app.jar --spring.profiles.active=prod
```
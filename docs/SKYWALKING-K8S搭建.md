#  Skywalking K8S 搭建

**准备工作**

```shell
export SKYWALKING_RELEASE_NAME=skywalking
export SKYWALKING_RELEASE_NAMESPACE=infra
export REPO=chart
git clone https://github.com/apache/skywalking-kubernetes
cd skywalking-kubernetes
helm repo add elastic https://helm.elastic.co
helm dep up ${REPO}/skywalking
```

**helm**

```shell
helm install "${SKYWALKING_RELEASE_NAME}" ${REPO}/skywalking -n "${SKYWALKING_RELEASE_NAMESPACE}" \
  --set oap.image.tag=8.8.1 \
  --set oap.storageType=elasticsearch \
  --set ui.image.tag=8.8.1 \
  --set elasticsearch.imageTag=6.8.6
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

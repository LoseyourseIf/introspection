#  Nacos-K8S-高可用搭建

### K8S YAML Nacos-Mysql

>  官方Nacos镜像不支持Mysql主从
>
>  可自建Mysql主从或使用高可用Mysql
>
>  注意PVC的配置

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: nacos-application-config
  namespace: infra
  labels:
    app: nacos-application-config
data:
  custom.properties: >
    # spring
    server.servlet.contextPath=${SERVER_SERVLET_CONTEXTPATH:/nacos}
    server.contextPath=/nacos
    server.port=${NACOS_APPLICATION_PORT:8848}
    spring.datasource.platform=${SPRING_DATASOURCE_PLATFORM:""}
    nacos.cmdb.dumpTaskInterval=3600
    nacos.cmdb.eventTaskInterval=10
    nacos.cmdb.labelTaskInterval=300
    nacos.cmdb.loadDataAtStart=false
    db.num=${MYSQL_DATABASE_NUM:1}
    db.url.0=jdbc:mysql://${MYSQL_SERVICE_HOST}:${MYSQL_SERVICE_PORT:3306}/${MYSQL_SERVICE_DB_NAME}?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
    db.url.1=jdbc:mysql://${MYSQL_SERVICE_HOST}:${MYSQL_SERVICE_PORT:3306}/${MYSQL_SERVICE_DB_NAME}?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
    db.user=${MYSQL_SERVICE_USER}
    db.password=${MYSQL_SERVICE_PASSWORD}
    ### The auth system to use, currently only 'nacos' is supported:
    nacos.core.auth.system.type=${NACOS_AUTH_SYSTEM_TYPE:nacos}
    ### The token expiration in seconds:
    nacos.core.auth.default.token.expire.seconds=${NACOS_AUTH_TOKEN_EXPIRE_SECONDS:18000}
    ### The default token:
    nacos.core.auth.default.token.secret.key=${NACOS_AUTH_TOKEN:SecretKey012345678901234567890123456789012345678901234567890123456789}
    ### Turn on/off caching of auth information. By turning on this switch, the
    update of auth information would have a 15 seconds delay.
    nacos.core.auth.caching.enabled=${NACOS_AUTH_CACHE_ENABLE:false}
    server.tomcat.accesslog.enabled=${TOMCAT_ACCESSLOG_ENABLED:false}
    server.tomcat.accesslog.pattern=%h %l %u %t "%r" %s %b %D
    # default current work dir
    server.tomcat.basedir=
    ## spring security config
    ### turn off security
    nacos.security.ignore.urls=/,/error,/**/*.css,/**/*.js,/**/*.html,/**/*.map,/**/*.svg,/**/*.png,/**/*.ico,/console-fe/public/**,/v1/auth/**,/v1/console/health/**,/actuator/**,/v1/console/server/**
    # metrics for elastic search
    management.metrics.export.elastic.enabled=false
    management.metrics.export.influx.enabled=false
    nacos.naming.distro.taskDispatchThreadCount=10
    nacos.naming.distro.taskDispatchPeriod=200
    nacos.naming.distro.batchSyncKeyCount=1000
    nacos.naming.distro.initDataRatio=0.9
    nacos.naming.distro.syncRetryDelay=5000
    nacos.naming.data.warmup=true
    ### turn on mcp server
    nacos.istio.mcp.server.enabled=false
---
kind: ConfigMap
apiVersion: v1
metadata:
  name: nacos-cm
  namespace: infra
data:
  mysql.master.db.name: nacos
  mysql.master.host: radondb-mysql-leader.infra.svc.cluster.local
  mysql.master.port: '3306'
  mysql.master.user: nacos
  mysql.master.password: nacos
---
kind: Service
apiVersion: v1
metadata:
  name: nacos-headless
  namespace: infra
  labels:
    app: nacos
  annotations:
    service.alpha.kubernetes.io/tolerate-unready-endpoints: 'true'
spec:
  ports:
    - name: server
      protocol: TCP
      port: 8848
      targetPort: 8848
    - name: rpc
      protocol: TCP
      port: 7848
      targetPort: 7848
    - name: mcp
      protocol: TCP
      port: 18848
      targetPort: 18848
  selector:
    app: nacos
  clusterIP: None
  type: ClusterIP
  sessionAffinity: None
---
kind: StatefulSet
apiVersion: apps/v1
metadata:
  name: nacos
  namespace: infra
spec:
  replicas: 3
  selector:
    matchLabels:
      app: nacos
  template:
    metadata:
      labels:
        app: nacos
      annotations:
        logging.kubesphere.io/logsidecar-config: '{}'
        pod.alpha.kubernetes.io/initialized: 'true'
    spec:
      volumes:
        - name: nacos-custom-properties
          configMap:
            name: nacos-application-config
            items:
              - key: custom.properties
                path: custom.properties
            defaultMode: 420
      initContainers:
        - name: peer-finder-plugin-install
          image: 'nacos/nacos-peer-finder-plugin:1.0'
          resources: {}
          terminationMessagePath: /dev/termination-log
          terminationMessagePolicy: File
          imagePullPolicy: IfNotPresent
      containers:
        - name: nacos
          image: 'nacos/nacos-server:1.3.0'
          ports:
            - name: mcp-18848
              containerPort: 18848
              protocol: TCP
            - name: server-8848
              containerPort: 8848
              protocol: TCP
            - name: server-7848
              containerPort: 7848
              protocol: TCP
          env:
            - name: NACOS_SERVERS
              value: >-
                nacos-0.nacos-headless.infra.svc.cluster.local 
                nacos-1.nacos-headless.infra.svc.cluster.local 
                nacos-2.nacos-headless.infra.svc.cluster.local
            - name: PREFER_HOST_MODE
              value: hostname
            - name: MYSQL_SERVICE_HOST
              valueFrom:
                configMapKeyRef:
                  name: nacos-cm
                  key: mysql.master.host
            - name: MYSQL_SERVICE_USER
              valueFrom:
                configMapKeyRef:
                  name: nacos-cm
                  key: mysql.master.user
            - name: MYSQL_SERVICE_PASSWORD
              valueFrom:
                configMapKeyRef:
                  name: nacos-cm
                  key: mysql.master.password
            - name: MYSQL_SERVICE_DB_NAME
              valueFrom:
                configMapKeyRef:
                  name: nacos-cm
                  key: mysql.master.db.name
            - name: MYSQL_DATABASE_NUM
              value: '1'
            - name: NACOS_REPLICAS
              value: '3'
          resources:
            limits:
              cpu: '1'
              memory: 2000Mi
            requests:
              cpu: 500m
              memory: 500Mi
          volumeMounts:
            - name: nacos-data-template
              mountPath: /home/nacos/data
            - name: nacos-custom-properties
              mountPath: /home/nacos/init.d/custom.properties
              subPath: custom.properties
          terminationMessagePath: /dev/termination-log
          terminationMessagePolicy: File
          imagePullPolicy: IfNotPresent
      restartPolicy: Always
      terminationGracePeriodSeconds: 30
      dnsPolicy: ClusterFirst
      securityContext: {}
      affinity:
        podAntiAffinity:
          requiredDuringSchedulingIgnoredDuringExecution:
            - labelSelector:
                matchExpressions:
                  - key: app
                    operator: In
                    values:
                      - nacos
              topologyKey: kubernetes.io/hostname
      schedulerName: default-scheduler
  volumeClaimTemplates:
    - kind: PersistentVolumeClaim
      apiVersion: v1
      metadata:
        name: nacos-data-template
      spec:
        accessModes:
          - ReadWriteOnce
        resources:
          requests:
            storage: 10Gi
        storageClassName: rook-ceph-block
        volumeMode: Filesystem
      status:
        phase: Pending
  serviceName: nacos-headless
  podManagementPolicy: OrderedReady
  updateStrategy:
    type: RollingUpdate
    rollingUpdate:
      partition: 0
  revisionHistoryLimit: 10
```

### Nacos 开启 MCP 与 Istio 交互

**Istio->Pilot->MCP**

```
# Nacos SVC 配置 MCP 端口
MCP Port 18848
# 使用nacos-server:1.3.0版本
image  nacos/nacos-server:1.3.0
```

**修改配置文件或挂载配置文件**

```
# Nacos 镜像中的spring配置文件
vi nacos/conf/application.properties
# 添加 nacos.istio.mcp.server.enabled=true 打开MCP服务 注意观察启动时会有 MCP 相关日志
nacos.istio.mcp.server.enabled=true
```

**修改 Istio Pilot 配置**

`kubectl edit cm istio -n istio-system`

**在ConfigSources下新增Nacos svc mcp**

```yaml
configSources:
- address: x.x.x.x:18848
```

**重启 Istio Pilot**
#  Nacos-K8S-高可用搭建

### K8S YAML Nacos-Mysql

>  官方Nacos镜像不支持Mysql主从
>
>  可自建Mysql主从或使用高可用Mysql
>
>  注意PVC的配置

```yaml
kind: Service
apiVersion: v1
metadata:
  name: k8s-nacos-mysql-master
  namespace: {your-namespace}
  labels:
    app: k8s-nacos-mysql-master
    version: v1
  annotations:
    kubesphere.io/creator: lxy
    kubesphere.io/serviceType: statefulservice
spec:
  ports:
    - name: tcp-33060
      protocol: TCP
      port: 33060
      targetPort: 33060
    - name: tcp-3306
      protocol: TCP
      port: 3306
      targetPort: 3306
  selector:
    app: k8s-nacos-mysql-master
  type: NodePort
  sessionAffinity: None
  externalTrafficPolicy: Cluster
---
kind: StatefulSet
apiVersion: apps/v1
metadata:
  name: k8s-nacos-mysql-master-v1
  namespace: {your-namespace}
  labels:
    app: k8s-nacos-mysql-master
    version: v1
  annotations:
    kubesphere.io/creator: lxy
spec:
  replicas: 1
  selector:
    matchLabels:
      app: k8s-nacos-mysql-master
      version: v1
  template:
    metadata:
      creationTimestamp: null
      labels:
        app: k8s-nacos-mysql-master
        version: v1
      annotations:
        kubesphere.io/containerSecrets: ''
        logging.kubesphere.io/logsidecar-config: '{}'
    spec:
      volumes:
        - name: host-time
          hostPath:
            path: /etc/localtime
            type: ''
        - name: volume-9555dc
          persistentVolumeClaim:
            claimName: nacos-mysql-data
        - name: volume-qq2hp5
          configMap:
            name: mysql-cnf
            defaultMode: 420
      containers:
        - name: nacos-mysql-container-d8dxhv
          image: nacos/nacos-mysql-master
          ports:
            - name: tcp-33060
              containerPort: 33060
              protocol: TCP
            - name: tcp-3306
              containerPort: 3306
              protocol: TCP
          env:
            - name: MYSQL_DATABASE
              value: nacos_k8s
            - name: MYSQL_USER
              value: nacos
            - name: MYSQL_PASSWORD
              value: nacos
            - name: MYSQL_REPLICATION_USER
              value: nacos_ru
            - name: MYSQL_REPLICATION_PASSWORD
              value: nacos_ru
            - name: MYSQL_ROOT_PASSWORD
              value: Aa123456
          resources:
            limits:
              cpu: '2'
              memory: 4000Mi
            requests:
              cpu: '1'
              memory: 1000Mi
          volumeMounts:
            - name: volume-9555dc
              mountPath: /var/lib/mysql
              subPath: mysql
            - name: volume-qq2hp5
              readOnly: true
              mountPath: /etc/mysql/conf.d/my.cnf
              subPath: my.cnf
          terminationMessagePath: /dev/termination-log
          terminationMessagePolicy: File
          imagePullPolicy: Always
      restartPolicy: Always
      terminationGracePeriodSeconds: 30
      dnsPolicy: ClusterFirst
      serviceAccountName: default
      serviceAccount: default
      securityContext: {}
      affinity: {}
      schedulerName: default-scheduler
  serviceName: k8s-nacos-mysql-master
  podManagementPolicy: OrderedReady
  updateStrategy:
    type: RollingUpdate
    rollingUpdate:
      partition: 0
  revisionHistoryLimit: 10
```

###  K8S YAML Nacos-Server

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: nacos-application-config
  namespace: {your-namespace}
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
    nacos.istio.mcp.server.enabled=true
---
kind: ConfigMap
apiVersion: v1
metadata:
  name: nacos-cm
  namespace: {your-namespace}
  annotations:
    kubesphere.io/creator: lxy
data:
  mysql.master.db.name: nacos_k8s
  mysql.master.host: k8s-nacos-mysql-master.{your-namespace}.svc.cluster.local
  mysql.master.password: nacos
  mysql.master.port: '3306'
  mysql.master.user: nacos
  mysql.root.pwd: root
---
kind: Service
apiVersion: v1
metadata:
  name: nacos-headless
  namespace: {your-namespace}
  labels:
    app: nacos
  annotations:
    kubesphere.io/creator: lxy
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
  namespace: {your-namespace}
spec:
  replicas: 3
  selector:
    matchLabels:
      app: nacos
  template:
    metadata:
      creationTimestamp: null
      labels:
        app: nacos
      annotations:
        logging.kubesphere.io/logsidecar-config: '{}'
        pod.alpha.kubernetes.io/initialized: 'true'
    spec:
      volumes:
        - name: volume-custom-properties
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
          imagePullPolicy: Always
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
                nacos-0.nacos-headless.micro-service.svc.cluster.local 
                nacos-1.nacos-headless.micro-service.svc.cluster.local 
                nacos-2.nacos-headless.micro-service.svc.cluster.local
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
            - name: volume-custom-properties
              mountPath: /home/nacos/init.d/custom.properties
              subPath: custom.properties
          terminationMessagePath: /dev/termination-log
          terminationMessagePolicy: File
          imagePullPolicy: Always
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
    - metadata:
        name: nacos-data-template
        namespace: micro-service
        creationTimestamp: null
      spec:
        accessModes:
          - ReadWriteOnce
        resources:
          requests:
            storage: 10Gi
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
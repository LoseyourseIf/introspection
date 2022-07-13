# SEATA-SERVER K8S 高可用搭建

###  **K8S Yaml**

```yaml
apiVersion: v1
kind: Service
metadata:
 name: seata-ha-server
 namespace: {your_namespace}
 labels:
  app.kubernetes.io/name: seata-ha-server
spec:
 type: ClusterIP
 ports:
  - port: 8091
   protocol: TCP
   name: http
 selector:
  app.kubernetes.io/name: seata-ha-server
---
kind: ConfigMap
apiVersion: v1
metadata:
  name: seata-ha-server-config
  namespace: {your_namespace}
data:
  registry.conf: |
    registry {
        type = "nacos"
        nacos {
          application = "seata-server"
          serverAddr = "nacos-server-addr:port"
          namespace = "seata-ha"
        }
    }
    config {
      type = "nacos"
      nacos {
        serverAddr = "nacos-server-addr:port"
        group = "SEATA_GROUP"
        namespace = "seata-ha"
      }
    }
---
apiVersion: apps/v1
kind: Deployment
metadata:
 name: seata-ha-server
 namespace: {your_namespace}
 labels:
  app.kubernetes.io/name: seata-ha-server
spec:
 replicas: 3
 selector:
  matchLabels:
   app.kubernetes.io/name: seata-ha-server
 template:
  metadata:
   labels:
    app.kubernetes.io/name: seata-ha-server
  spec:
   containers:
    - name: seata-ha-server
     image: docker.io/seataio/seata-server:latest
     imagePullPolicy: IfNotPresent
     env:
      - name: SEATA_CONFIG_NAME
       value: file:/root/seata-config/registry
     ports:
      - name: http
       containerPort: 8091
       protocol: TCP
     volumeMounts:
      - name: seata-config
       mountPath: /root/seata-config
   volumes:
    - name: seata-config
     configMap:
      name: seata-ha-server-config
```

### **Mysql Seata 建表**

```mysql
create database seata DEFAULT CHARSET = utf8;
use seata;
create table if not exists branch_table
(
  branch_id bigint not null primary key,
  xid varchar(128) not null,
  transaction_id bigint null,
  resource_group_id varchar(32) null,
  resource_id varchar(256) null,
  branch_type varchar(8) null,
  status tinyint null,
  client_id varchar(64) null,
  application_data varchar(2000) null,
  gmt_create datetime(6) null,
  gmt_modified datetime(6) null
)charset=utf8;

create index idx_xid
  on branch_table (xid);

create table if not exists global_table
(
  xid varchar(128) not null primary key,
  transaction_id bigint null,
  status tinyint not null,
  application_id varchar(32) null,
  transaction_service_group varchar(32) null,
  transaction_name varchar(128) null,
  timeout int null,
  begin_time bigint null,
  application_data varchar(2000) null,
  gmt_create datetime null,
  gmt_modified datetime null
)charset=utf8;

create index idx_gmt_modified_status
  on global_table (gmt_modified, status);
  
create index idx_transaction_id
  on global_table (transaction_id);
  
create table if not exists lock_table
(
  row_key varchar(128) not null primary key,
  xid varchar(96) null,
  transaction_id bigint null,
  branch_id bigint not null,
  resource_id varchar(256) null,
  table_name varchar(32) null,
  pk varchar(36) null,
  gmt_create datetime null,
  gmt_modified datetime null
)charset=utf8;

create index idx_branch_id
  on lock_table (branch_id);
  
-- 用户
create user seata;
grant ALL PRIVILEGES on seata.* to seata with grant option;
SET PASSWORD FOR seata = 'seata';
FLUSH PRIVILEGES;
```

### **业务库建表SQL undo_log**

```mysql
create table biz_db.undo_log
(
  branch_id bigint not null comment 'branch transaction id',
  xid varchar(100) not null comment 'global transaction id',
  context varchar(128) not null comment 'undo_log context,such as serialization',
  rollback_info longblob not null comment 'rollback info',
  log_status int not null comment '0:normal status,1:defense status',
  log_created datetime(6) not null comment 'create datetime',
  log_modified datetime(6) not null comment 'modify datetime',
  constraint ux_undo_log unique (xid, branch_id)
)
comment 'AT transaction mode undo table' charset=utf8;
```

###  **Nacos 配置 修改数据库密码不加密** 

进入SEATA script 目录

```
cd seata/script/config-center
```

**去掉 store.publicKey=failure durid 数据库密码加密** 

修改 store.mode=db store.db下的配置项

```properties
nacosAddr=172.16.19.12:32472
group=SEATA_GROUP
transport.type=TCP
transport.server=NIO
transport.heartbeat=true
transport.enableClientBatchSendRequest=false
transport.threadFactory.bossThreadPrefix=NettyBoss
transport.threadFactory.workerThreadPrefix=NettyServerNIOWorker
transport.threadFactory.serverExecutorThreadPrefix=NettyServerBizHandler
transport.threadFactory.shareBossWorker=false
transport.threadFactory.clientSelectorThreadPrefix=NettyClientSelector
transport.threadFactory.clientSelectorThreadSize=1
transport.threadFactory.clientWorkerThreadPrefix=NettyClientWorkerThread
transport.threadFactory.bossThreadSize=1
transport.threadFactory.workerThreadSize=default
transport.shutdown.wait=3
service.vgroupMapping.yx_seata_tc_group=default
service.default.grouplist=127.0.0.1:8091
service.enableDegrade=false
service.disableGlobalTransaction=false
client.rm.asyncCommitBufferLimit=10000
client.rm.lock.retryInterval=10
client.rm.lock.retryTimes=30
client.rm.lock.retryPolicyBranchRollbackOnConflict=true
client.rm.reportRetryCount=5
client.rm.tableMetaCheckEnable=false
client.rm.tableMetaCheckerInterval=60000
client.rm.sqlParserType=druid
client.rm.reportSuccessEnable=false
client.rm.sagaBranchRegisterEnable=false
client.tm.commitRetryCount=5
client.tm.rollbackRetryCount=5
client.tm.defaultGlobalTransactionTimeout=60000
client.tm.degradeCheck=false
client.tm.degradeCheckAllowTimes=10
client.tm.degradeCheckPeriod=2000
store.mode=db
store.publicKey=failure
store.file.dir=file_store/data
store.file.maxBranchSessionSize=16384
store.file.maxGlobalSessionSize=512
store.file.fileWriteBufferCacheSize=16384
store.file.flushDiskMode=async
store.file.sessionReloadReadSize=100
store.db.datasource=druid
store.db.dbType=mysql
store.db.driverClassName=com.mysql.jdbc.Driver
store.db.url=jdbc:mysql://172.16.19.12:32757/seata?useUnicode=true&rewriteBatchedStatements=true
store.db.user=seata
store.db.password=seata
store.db.minConn=5
store.db.maxConn=30
store.db.globalTable=global_table
store.db.branchTable=branch_table
store.db.queryLimit=100
store.db.lockTable=lock_table
store.db.maxWait=5000
store.redis.mode=single
store.redis.single.host=127.0.0.1
store.redis.single.port=6379
store.redis.maxConn=10
store.redis.minConn=1
store.redis.maxTotal=100
store.redis.database=0
store.redis.password=failure
store.redis.queryLimit=100
server.recovery.committingRetryPeriod=1000
server.recovery.asynCommittingRetryPeriod=1000
server.recovery.rollbackingRetryPeriod=1000
server.recovery.timeoutRetryPeriod=1000
server.maxCommitRetryTimeout=-1
server.maxRollbackRetryTimeout=-1
server.rollbackRetryTimeoutUnlockEnable=false
client.undo.dataValidation=true
client.undo.logSerialization=jackson
client.undo.onlyCareUpdateColumns=true
server.undo.logSaveDays=7
server.undo.logDeletePeriod=86400000
client.undo.logTable=undo_log
client.undo.compress.enable=true
client.undo.compress.type=zip
client.undo.compress.threshold=64k
log.exceptionRate=100
transport.serialization=seata
transport.compressor=none
metrics.enabled=false
metrics.registryType=compact
metrics.exporterList=prometheus
metrics.exporterPrometheusPort=9898
```

###  使用命令行注册配置

```
sh nacos/nacos-config.sh -h 127.0.0.1 -p 8848 -g SEATA_GROUP -t seata-ha -u nacos -w
 nacos

sh nacos/nacos-config.sh -h 127.0.0.1 -p 8848 -g SEATA_GROUP -t seata-ha -u nacos -w nacos
```

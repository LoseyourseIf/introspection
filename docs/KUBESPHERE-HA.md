# Kubesphere-HA

## 环境准备

### 虚拟机准备

| IP            | HOST     | 宿主机        | 配置                |
| ------------- | -------- | ------------- | ------------------- |
| 172.16.31.200 | lb       | 172.16.31.100 | 4C8G 50G            |
| 172.16.31.210 | master-0 | 172.16.31.100 | 4C8G 50G 100G 100G  |
| 172.16.31.211 | master-1 | 172.16.31.101 | 8C16G 50G 100G 200G |
| 172.16.31.212 | master-2 | 172.16.31.102 | 8C16G 50G 100G 100G |
| 172.16.31.220 | node-0   | 172.16.31.100 | 4C8G 50G 100G 100G  |
| 172.16.31.221 | node-1   | 172.16.31.101 | 4C8G 50G 100G 200G  |
| 172.16.31.222 | node-2   | 172.16.31.102 | 4C8G 50G 100G 100G  |
| 172.16.31.223 | node-3   | 172.16.31.103 | 8C16G 50G 100G 200G |
| 172.16.31.224 | node-4   | 172.16.31.103 | 4C8G 50G 100G 200G  |

### 固定IP

```shell
vi /etc/sysconfig/network-scripts/ifcfg-ens32

TYPE="Ethernet"
PROXY_METHOD="none"
BROWSER_ONLY="no"
BOOTPROTO="static"
DEFROUTE="yes"
IPV4_FAILURE_FATAL="no"
IPV6INIT="yes"
IPV6_AUTOCONF="yes"
IPV6_DEFROUTE="yes"
IPV6_FAILURE_FATAL="no"
IPV6_ADDR_GEN_MODE="stable-privacy"
NAME="ens32"
UUID="0251e36b-320b-42c9-9883-88f661b76d55"
DEVICE="ens32"
ONBOOT="yes"
IPADDR=172.16.31.210
NETMASK=255.255.255.0
GATEWAY=172.16.31.254
DNS1=114.114.114.114
DNS2=8.8.8.8
```

### 修改HOST

```shell
vi /etc/hosts

172.16.31.200 lb lb.local
172.16.31.210 master-0
172.16.31.211 master-1
172.16.31.212 master-2
172.16.31.220 node-0
172.16.31.221 node-1
172.16.31.222 node-2
172.16.31.223 node-3
172.16.31.224 node-4
```

### 免密登陆

在master-0上执行方便后续操作

```shell
ssh-keygen -f /root/.ssh/id_rsa -N ''

ssh-copy-id lb
ssh-copy-id master-0
ssh-copy-id node-0
...
```

### 初始化脚本

```shell
cd /opt/
touch init.sh
chmod +x init.sh
```

**init.sh**

```sh
#!/bin/bash
#init.sh
#时区
mv /etc/localtime /etc/localtime.bak

ln -s /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

#SELINUX
sed -i "s/SELINUX=enforcing/SELINUX=disabled/" /etc/selinux/config

setenforce 0

#firewalld
systemctl stop firewalld.service

systemctl disable firewalld.service

systemctl status firewalld.service

#关闭swap分区
swapoff -a
echo "vm.swappiness=0" >> /etc/sysctl.conf
cat /etc/sysctl.conf

sed -i 's$/dev/mapper/centos-swap$#/dev/mapper/centos-swap$g' /etc/fstab
free -m

#wget
yum install wget -y

#yum.repos
cd /etc/yum.repos.d/

mv CentOS-Base.repo CentOS-Base.repo_bak

wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo

yum clean all

yum makecache

yum install -y yum-utils

yum provides '*/applydeltarpm' 

yum install -y  deltarpm 

yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo

yum makecache fast

yum install curl -y

yum install openssl -y

yum install socat -y

yum install conntrack -y

yum install ebtables -y

yum install ipset -y

yum install -y containerd.io-1.4.12-3.1.el7.x86_64

yum install -y docker-ce-cli-20.10.1-3.el7

yum install -y docker-ce-20.10.1-3.el7

rpm -qa | grep containerd

systemctl start docker

systemctl enable docker

docker info |grep Cgroup
```

### 挂载盘脚本

```shell
cd /opt/
touch mount.sh
chmod +x mount.sh
```

**mount.sh**

```sh
#设备名模式匹配串
DEVICEPATTERN="^[a-z]d[a-z]$"
#读取需要挂载的设备名
function read_dirname ()
{
  devname=""
  while ! [[ "$devname" =~ $DEVICEPATTERN ]]
  do
    read -p "请输入需要格式化和挂载的磁盘,(如:vdc):" devname
  done
}
#读取挂载点
function read_mountdir ()
{
  mountdir=""
  while [ ! -d "$mountdir" ]
    do
    read -p "请输入磁盘挂载点,(如:/opt):" mountdir
    if [ ! -d "$mountdir" ]
    then
      echo "挂载点目录不存在,请重新输入"
    fi
  done
}

#main
#显示当前设备列表
echo "当前块设备列表:"
lsblk | awk '{printf "%s\t\t%s\n",$1,$4}'
read_dirname
read_mountdir
echo "n
p
1


wq
" | fdisk /dev/${devname} && mkfs -t ext4 /dev/${devname}1

uuid=` blkid /dev/${devname}1  | awk '{print $2}' | awk -F= '($1="UUID") {print $2}' | sed 's/\"//g'`
if  [ -n "$uuid" ]
then
echo -e "UUID=${uuid}\t${mountdir}\text4\tdefaults\t0\t2" >> /etc/fstab
mount -a
df -h
fi
```

### 拷贝脚本

```shell
cd /opt
scp ./*.sh master-1:/opt/
scp ./*.sh node-1:/opt/
...
```

### docker

**vi /etc/docker/daemon.json**

```shell
{
	"registry-mirrors": ["https://4nla2ik6.mirror.aliyuncs.com"],
	"insecure-registries": ["http://172.16.31.210:30002"],
	"exec-opts": ["native.cgroupdriver=systemd"],
	"bip": "101.101.233.1/24"
}
```

**重启docker**

```shell
systemctl daemon-reload
systemctl restart docker
systemctl enable docker
systemctl status docker
docker info |grep Cgroup
```

### load-balance

**在lb上安装nginx**

```shell
rpm -Uvh http://nginx.org/packages/centos/7/noarch/RPMS/nginx-release-centos-7-0.el7.ngx.noarch.rpm

yum install -y nginx

systemctl start nginx.service

systemctl enable nginx.service
```

**nginx.conf**

```nginx
user  nginx;
worker_processes  auto;

error_log  /var/log/nginx/error.log notice;
pid        /var/run/nginx.pid;

events {
      multi_accept on;
      use epoll;
      worker_connections 1024;
}

stream {
    upstream backend {
        least_conn;
        hash $remote_addr consistent;
        server 172.16.31.210:6443        max_fails=3 fail_timeout=30s;
        server 172.16.31.211:6443        max_fails=3 fail_timeout=30s;
        server 172.16.31.212:6443        max_fails=3 fail_timeout=30s;
    }

    server {
        listen 0.0.0.0:6443;
        proxy_connect_timeout 1s;
        proxy_pass backend;
    }
}

http {
    include       /etc/nginx/mime.types;
    default_type  application/octet-stream;
    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';
    access_log  /var/log/nginx/access.log  main;
    sendfile        on;
    #tcp_nopush     on;
    keepalive_timeout  65;
    #gzip  on;
    include /etc/nginx/conf.d/*.conf;
}
```

### 时间同步

```shell
yum install ntp ntpdate ntp-doc -y
```

```shell
#主机
vi /etc/chrony.conf

# Allow NTP client access from local network.
allow 172.16.31.0/24
# Serve time even if not synchronized to a time source.
local stratum 10

systemctl restart chronyd
chronyc sources -v  
```

```shell
#设置从机
vi /etc/chrony.conf

# Use public servers from the pool.ntp.org project.
# Please consider joining the pool (http://www.pool.ntp.org/join.html).
server 172.16.31.210 iburst
server 172.16.31.211 iburst
server 127.16.31.212 iburst

systemctl restart chronyd
chronyc sources -v  
```

## KubeKey

> K8S 版本对应 Docker 版本需在 https://github.com/kubernetes/kubernetes/blob/master/CHANGELOG/CHANGELOG-1.21.md#changelog-since-v1214 中查找
>
> latest validated version of Docker
>
> KubeSphere官方文档推荐 kubernetes v1.21.5 
>
> 对应 K8S ChangeLog 中提到 latest validated version of Docker 是 Docker version 20.10
>
> 环境准备中脚本安装 docker 版本为 **docker-ce.x86_64**                      3:20.10.1-3.el7                       @docker-ce-stable

#### **下载kk**

```shell
curl -sfL https://get-kk.kubesphere.io | VERSION=v1.2.1 sh -

chmod +x kk
```

#### **配置文件**

```shell
./kk create config --with-kubernetes v1.21.5 --with-kubesphere v3.2.1
mv config-sample.yaml kubesphere-ha.yaml
```

**kubesphere-ha.yaml**

```yaml
apiVersion: kubekey.kubesphere.io/v1alpha1
kind: Cluster
metadata:
  name: cluster
spec:
  hosts:
  - {name: master-0, address: 172.16.31.210, internalAddress: 172.16.31.210, user: root, password: asasas}
  - {name: master-1, address: 172.16.31.211, internalAddress: 172.16.31.211, user: root, password: asasas}
  - {name: master-2, address: 172.16.31.212, internalAddress: 172.16.31.212, user: root, password: asasas}
  - {name: node-0, address: 172.16.31.220, internalAddress: 172.16.31.220, user: root, password: asasas}
  - {name: node-1, address: 172.16.31.221, internalAddress: 172.16.31.221, user: root, password: asasas}
  - {name: node-2, address: 172.16.31.222, internalAddress: 172.16.31.222, user: root, password: asasas}
  - {name: node-3, address: 172.16.31.223, internalAddress: 172.16.31.223, user: root, password: asasas}
  - {name: node-4, address: 172.16.31.224, internalAddress: 172.16.31.224, user: root, password: asasas}
  roleGroups:
    etcd:
    - master-0
    - master-1
    - master-2
    master: 
    - master-0
    - master-1
    - master-2
    worker:
    - node-0
    - node-1
    - node-2
    - node-3
    - node-4
  controlPlaneEndpoint:
    ##Internal loadbalancer for apiservers 
    domain: lb.kubesphere.local
    address: "172.16.31.200"
    port: 6443
  kubernetes:
    version: v1.21.5
    clusterName: cluster.local
  network:
    plugin: calico
    kubePodsCIDR: 10.233.64.0/18
    kubeServiceCIDR: 10.233.0.0/18
  registry:
    registryMirrors: ["https://4nla2ik6.mirror.aliyuncs.com"]
    insecureRegistries: ["http://172.16.31.210:30002"]
  addons: []



---
apiVersion: installer.kubesphere.io/v1alpha1
kind: ClusterConfiguration
metadata:
  name: ks-installer
  namespace: kubesphere-system
  labels:
    version: v3.2.1
spec:
  persistence:
    storageClass: ""
  authentication:
    jwtSecret: ""
  local_registry: ""
  # dev_tag: ""
  etcd:
    monitoring: true
    endpointIps: localhost
    port: 2379
    tlsEnable: true
  common:
    core:
      console:
        enableMultiLogin: true
        port: 30880
        type: NodePort
    redis:
      enabled: true
      volumeSize: 4Gi
    openldap:
      enabled: false
      volumeSize: 2Gi
    minio:
      volumeSize: 20Gi
    monitoring:
      # type: external
      endpoint: http://prometheus-operated.kubesphere-monitoring-system.svc:9090
      GPUMonitoring:
        enabled: false
    gpu:
      kinds:         
      - resourceName: "nvidia.com/gpu"
        resourceType: "GPU"
        default: true
    es:
      master:
         volumeSize: 8Gi
         replicas: 3
         resources: {}
      data:
         volumeSize: 20Gi
         replicas: 1
         resources: {}
      logMaxAge: 7
      elkPrefix: logstash
      basicAuth:
        enabled: false
        username: ""
        password: ""
      externalElasticsearchHost: ""
      externalElasticsearchPort: ""
  alerting:
    enabled: true
    # thanosruler:
    #   replicas: 1
    #   resources: {}
  auditing:
    enabled: true
    # operator:
    #   resources: {}
    # webhook:
    #   resources: {}
  devops:
    enabled: true
    jenkinsMemoryLim: 4Gi
    jenkinsMemoryReq: 4096Mi
    jenkinsVolumeSize: 20Gi
    jenkinsJavaOpts_Xms: 1024m
    jenkinsJavaOpts_Xmx: 1024m
    jenkinsJavaOpts_MaxRAM: 4g
  events:
    enabled: true
    # operator:
    #   resources: {}
    # exporter:
    #   resources: {}
    # ruler:
    #   enabled: true
    #   replicas: 2
    #   resources: {}
  logging:
    enabled: true
    containerruntime: docker
    logsidecar:
      enabled: true
      replicas: 2
      # resources: {}
  metrics_server:
    enabled: true
  monitoring:
    storageClass: ""
    prometheus:
      replicas: 2
      volumeSize: 20Gi
      resources: {}
      operator:
        resources: {}
      adapter:
        resources: {}
  multicluster:
    clusterRole: none 
  network:
    networkpolicy:
      enabled: false
    ippool:
      type: none
    topology:
      type: none
  openpitrix:
    store:
      enabled: true
  servicemesh:
    enabled: true
```

#### 执行安装

```shell
export KKZONE=cn
./kk create cluster --with-kubernetes v1.21.5 --with-kubesphere v3.2.1 -f kubesphere-ha.yaml
```

#### 安装日志

```shell
kubectl logs -n kubesphere-system $(kubectl get pod -n kubesphere-system -l app=ks-install -o jsonpath='{.items[0].metadata.name}') -f
```

### 盘挂载

```shell
lsblk
df -h
fdisk -l
```

**使用挂载脚本将**

```shell
./mount.sh
sdb
/var/lib/docker
```



## Kubectl

### 自动补全

```shell
yum install -y bash-completion
echo 'source <(kubectl completion bash)' >>~/.bashrc
kubectl completion bash >/etc/bash_completion.d/kubectl
source ~/.bashrc
# exit 后重新登陆生效
```

## ROOK-CEPH

#### **克隆 Rook 仓库到本地**

```shell
cd /opt/
git clone -b release-1.4 https://github.com/rook/rook.git
```

#### **切换目录**

```shell
cd /opt/rook/cluster/examples/kubernetes/ceph
```

#### **部署 Rook，创建 CRD 资源**

>```text
># 说明：
># 1.comm.yaml里面主要是权限控制以及CRD资源定义
># 2.operator.yaml是rook-ceph-operator的deloyment
>```

```shell
kubectl create -f common.yaml -f operator.yaml
```

#### **创建 Ceph 集群**

修改 cluster.yaml 

```yaml
# vi cluster.yaml
	dashboard:
    enabled: true
    ssl: false
```

dashboard ssl 为 false 使用http访问 dashboard

```shell
kubectl create -f cluster.yaml
```

#### **检查 pod 状态**

```shell
kubectl get pod -n rook-ceph -o wide
```

#### **配置 Ceph 集群 dashboard**

```shell
kubectl apply -f dashboard-external-http.yaml
```

#### **创建 StorageClass**

> Ceph 可以同时提供对象存储 RADOSGW、块存储 RBD、文件系统存储 Ceph FS。 RBD 即 RADOS Block Device 的简称，RBD 块存储是最稳定且最常用的存储类型。RBD 块设备类似磁盘可以被挂载。 RBD 块设备具有快照、多副本、克隆和一致性等特性，数据以条带化的方式存储在 Ceph 集群的多个 OSD 中。

```shell
cd /opt/rook/cluster/examples/kubernetes/ceph/csi/rbd
kubectl  apply -f storageclass.yaml
```

#### **部署 rook 工具箱**

```shell
kubectl apply -f toolbox.yaml
```

#### **进入工具箱查看状态**

```shell
kubectl -n rook-ceph exec -it $(kubectl -n rook-ceph get pod -l "app=rook-ceph-tools" -o jsonpath='{.items[0].metadata.name}') -- bash
```

#### **常用命令**

```shell
ceph status
ceph osd status
ceph osd tree
ceph df
rados df
```

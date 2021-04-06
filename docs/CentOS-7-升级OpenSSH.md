# CentOS 7  升级 openSSH

>  漏扫出现
>
>  OpenSSH 用户枚举漏洞(CVE-2018-15919)
>
>  OpenSSH 用户枚举漏洞(CVE-2018-15473)
>
>  需升级 OpenSSH



#### 1、下载相关依赖包

```shell
yum install -y gcc openssl-devel pam-devel rpm-build
```

#### 2、下载安装包

```shell
wget http://ftp.openbsd.org/pub/OpenBSD/OpenSSH/portable/openssh-7.8p1.tar.gz
验证MD5
```

#### 3、卸载原Openssh

```shell
[root@Log ~]# rpm -qa |grep  openssh
openssh-clients-7.4p1-16.el7.x86_64
openssh-7.4p1-16.el7.x86_64
openssh-server-7.4p1-16.el7.x86_64
[root@Log ~]# for i in $(rpm -qa |grep openssh);do rpm -e $i --nodeps;done
[root@Log ~]#
```

#### 4、解压openssh安装包

```shell
[root@Log ~]# tar -zxvf openssh-7.8p1.tar.gz
[root@Log ~]# cd openssh-7.8p1
```

**注意安装升级openssh时需要关闭SELinux，否则退出后无法登陆**

```shell
临时关闭：setenforce 0
```

#### 5、编译安装

```shell
[root@Log ~]# ./configure --prefix=/usr --sysconfdir=/etc/ssh --with-md5-passwords--with-pam --with-tcp-wrappers  --with-ssl-dir=/usr/local/ssl --without-hardening
[root@Log ~]# rm -rf /etc/ssh
[root@Log ~]# make && make install
```

#### 6、安装完成，执行配置

```shell
[root@Log openssh-7.8p1]# cp contrib/redhat/sshd.init /etc/init.d/sshd
[root@Log openssh-7.8p1]# chkconfig --add sshd
[root@Log openssh-7.8p1]# chkconfig sshd on
[root@Log openssh-7.8p1]# chkconfig --list|grep sshd
[root@Log openssh-7.8p1]# sed -i "32a PermitRootLogin yes" /etc/ssh/sshd_config
[root@Log openssh-7.8p1]# systemctl restart sshd
```

#### 7、查看版本

```shell
[root@Log openssh-7.8p1]# systemctl status sshd
● sshd.service - SYSV: OpenSSH server daemon
   Loaded: loaded (/etc/rc.d/init.d/sshd; bad; vendor preset: enabled)
   Active: active (running) since Wed 2018-09-12 16:29:54 CST; 8s ago
     Docs: man:systemd-sysv-generator(8)
  Process: 1780 ExecStart=/etc/rc.d/init.d/sshd start (code=exited, status=0/SUCCESS)
 Main PID: 1786 (sshd)
   CGroup: /system.slice/sshd.service
           └─1786 /usr/sbin/sshd

Sep 12 16:29:54 Log-elasticsearch-nginx systemd[1]: Starting SYSV: OpenSSH server daemon...
Sep 12 16:29:54 Log-elasticsearch-nginx sshd[1786]: Server listening on 0.0.0.0 port 22.
Sep 12 16:29:54 Log-elasticsearch-nginx sshd[1786]: Server listening on :: port 22.
Sep 12 16:29:54 Log-elasticsearch-nginx sshd[1780]: Starting sshd:[  OK  ]
Sep 12 16:29:54 Log-elasticsearch-nginx systemd[1]: Started SYSV: OpenSSH server daemon.
[root@Log openssh-7.8p1]# ssh -V
OpenSSH_7.8p1, OpenSSL 1.0.2k-fips  26 Jan 2017
[root@Log openssh-7.8p1]# 
```

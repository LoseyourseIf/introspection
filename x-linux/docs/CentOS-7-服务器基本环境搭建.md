# CentOS 7 服务器基本环境搭建

## JDK

- 前往 Oracle 官方网下载 JDK 8

- http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

- 使用 scp 或 ftp 将 安装包上传服务器

  ```shell
  scp -P 22 /{userDir}/jdk-8u161-linux-x64.tar.gz root@{serverIp}:/opt
  ```


- SSH 登录服务器

  ```shell
  cd /usr
  mkdir java
  cp /opt/jdk-8u161-linux-x64.tar.gz /usr/java/
  tar -zxvf jdk-8u161-linux-x64.tar.gz
  #创建短链接
  ln -s /usr/java/jdk1.8.0_161/ /usr/jdk
  ```

- 配置环境变量

  ```shell
  vi /etc/profile

  #添加以下内容
  JAVA_HOME=/usr/jdk
  CLASSPATH=$JAVA_HOME/lib/
  PATH=$PATH:$JAVA_HOME/bin
  export PATH JAVA_HOME CLASSPATH

  #立即重启服务器
  sudo shutdown -r now

  #重新登录服务器
  java -version

  #看到如下内容配置成功
  java version "1.8.0_161"
  Java(TM) SE Runtime Environment (build 1.8.0_161-b12)
  Java HotSpot(TM) 64-Bit Server VM (build 25.161-b12, mixed mode)
  ```

## NGINX

- yum 安装

```shell
#YUM直接安装
yum install nginx

#若出现以下提示 需添加 repo 源
没有可用软件包 nginx。
#添加 nginx 源
rpm -ivh http://nginx.org/packages/centos/7/noarch/RPMS/nginx-release-centos-7-0.el7.ngx.noarch.rpm
#再执行安装
yum install nginx
```

- 以下是Nginx的默认路径：

  (1) Nginx配置路径：`/etc/nginx/`

  (2) PID目录：`/var/run/nginx.pid`

  (3) 错误日志：`/var/log/nginx/error.log`

  (4) 访问日志：`/var/log/nginx/access.log`

  (5) 默认站点目录：`/usr/share/nginx/html`

## MYSQL

- 下载mysql源安装包

  ```shell
  wget http://dev.mysql.com/get/mysql57-community-release-el7-8.noarch.rpm
  ```



- 安装mysql源

  ```shell
  yum localinstall mysql57-community-release-el7-8.noarch.rpm
  ```

  ​

- 检查mysql源是否安装成功`yum repolist enabled | grep "mysql.*-community.*"`

  ```shell
  #如下则安装源成功
  !mysql-connectors-community/x86_64 MySQL Connectors Community                 51
  !mysql-tools-community/x86_64      MySQL Tools Community                      63
  !mysql57-community/x86_64          MySQL 5.7 Community Server                267
  ```

- 可以修改`vim /etc/yum.repos.d/mysql-community.repo`源，改变默认安装的mysql版本。比如要安装5.6版本，将5.7源的enabled=1改成enabled=0。然后再将5.6源的enabled=0改成enabled=1即可

  ​

- 安装mysql`yum install mysql-community-server`

  ​

- 启动`systemctl start mysqld`

- 查看启动状态`systemctl status mysqld`显示如下启动成功

  ```shell
  ● mysqld.service - MySQL Server
     Loaded: loaded (/usr/lib/systemd/system/mysqld.service; enabled; vendor preset: disabled)
     Active: active (running) since 五 2018-04-20 09:27:29 CST; 35min ago
       Docs: man:mysqld(8)
             http://dev.mysql.com/doc/refman/en/using-systemd.html
   Main PID: 1057 (mysqld)
     CGroup: /system.slice/mysqld.service
             └─1057 /usr/sbin/mysqld --daemonize --pid-file=/var/run/mysqld/mysqld.pid

  4月 20 09:27:28 izwz958mrrj1x9plas7b11z systemd[1]: Starting MySQL Server...
  4月 20 09:27:29 izwz958mrrj1x9plas7b11z systemd[1]: Started MySQL Server.
  ```


- 开机启动

  ```shell
  systemctl enable mysqld
  systemctl daemon-reload
  ```


- 修改root本地登录密码

  - mysql安装完成之后，在/var/log/mysqld.log文件中给root生成了一个默认密码。通过下面的方式找到root默认密码，然后登录mysql进行修改

  ```shell
  grep 'temporary password' /var/log/mysqld.log

  2018-04-19T12:17:44.562577Z 1 [Note] A temporary password is generated for root@localhost: fI;#Gle/N7Ey

  mysql -uroot -p

  ALTER USER 'root'@'localhost' IDENTIFIED BY '{newPassword}'; 
  #或者
  set password for 'root'@'localhost'=password('{newPassword}'); 
  ```

  - 注意：mysql5.7默认安装了密码安全检查插件（validate_password），默认密码检查策略要求密码必须包含：大小写字母、数字和特殊符号，并且长度不能少于8位。


- 添加远程登录用户

  ```shell
  GRANT ALL PRIVILEGES ON *.* TO '{userName}'@'%' IDENTIFIED BY '{userPassword}' WITH GRANT OPTION;
  FLUSH PRIVILEGES;
  ```

- 配置默认编码 utf-8

  ```shell
  vi /etc/my.cnf
  #在[mysqld]下添加编码配置，如下所示:

  [mysqld]
  character_set_server=utf8
  init_connect='SET NAMES utf8'
  ```

- 重新启动mysql服务 `service mysqld restart`
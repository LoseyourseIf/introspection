## CentOS-7-安装 Node.js

1、下载源码，你需要在<https://nodejs.org/en/download/>下载Nodejs版本，以v8.9.1为例:

```shell
cd /usr/local/src/
wget http://nodejs.org/dist/v0.10.24/node-v8.9.1.tar.gz
```

2、解压源码

```shell
tar zxvf node-v8.9.1.tar.gz
```

3、 编译安装

```shell
cd node-v8.9.1
./configure --prefix=/usr/local/node/8.9.1
make
make install
```

4、 配置NODE_HOME，进入profile编辑环境变量

```shell
vim /etc/profile
```

设置nodejs环境变量，在 **\*export PATH USER LOGNAME MAIL HOSTNAME HISTSIZE HISTCONTROL*** 一行的上面添加如下内容:

```shell
#set for nodejs
export NODE_HOME=/usr/local/node/8.9.1
export PATH=$NODE_HOME/bin:$PATH
```

:wq保存并退出，编译/etc/profile 使配置生效

```shell
source /etc/profile
```

验证是否安装配置成功

```shell
node -v
```

输出 v8.9.1 表示配置成功

npm模块安装路径

```shell
/usr/local/node/8.9.1/lib/node_modules/
```

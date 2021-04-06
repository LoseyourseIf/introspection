## CentOS-7-禅道安装记录

```shell
cd /opt
wget http://dl.cnezsoft.com/zentao/10.0/ZenTaoPMS.10.0.stable.zbox_64.tar.gz
sudo tar -zxvf  ZenTaoPMS.9.8.3.zbox_64.tar.gz 
sudo /opt/zbox/zbox start
```

```
Start Apache success
Start Mysql success
```

Apache 和 Mysql 启动成功后 访问80端口出现则禅道页面安装成功
修改密码跟随教程了解使用

## 迁移

复制下面两个文件到新部署路径
```shell
/opt/zbox/app/zentao/www/data/upload/1
/opt/zbox/data/mysql/zentao
```

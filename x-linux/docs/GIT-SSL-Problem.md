#### 执行GIT命令 SSL certificate problem 解决办法

```shell
#windows
set GIT_SSL_NO_VERIFY=true git push 
#linux
env GIT_SSL_NO_VERIFY=true git push 
```

​	这里push可以根据需要换成其他的git命令。


​	也可以把临时环境变量变为永久

```shell
git config --global http.sslVerify false
```



# MySql 5.7 ONLY_FULL_GROUP_BY 报错

- 进入Mysql客户端执行命令

```shell
mysql -u root -p
select @@GLOBAL.sql_mode;
```

```
+--------------------------------------------------------------------------------------------------------------------------------------+
| @@GLOBAL.sql_mode                                                                                                                    |
+--------------------------------------------------------------------------------------------------------------------------------------+
|ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION 
+--------------------------------------------------------------------------------------------------------------------------------------+
```

- 看到上面的 ONLY_FULL_GROUP_BY 就是罪魁祸首
  - ​ONLY_FULL_GROUP_BY的意思是：对于GROUP BY聚合操作，如果在SELECT中的列，没有在GROUP BY中出现，那么这个SQL是不合法的，因为列不在GROUP BY从句中，也就是说查出来的列必须在GROUP BY后面出现否则就会报错，或者这个字段出现在聚合函数里面。
- 如果去改sql那么就很麻烦，推荐直接关掉这个配置项

```shell
vi /etc/my/cnf
[mysqld]
sql_mode=STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION
```

- 重启 mysql


注意 使用 set @@global.sql_mode = ......;也能解决问题、但是数据库重启后设置还原

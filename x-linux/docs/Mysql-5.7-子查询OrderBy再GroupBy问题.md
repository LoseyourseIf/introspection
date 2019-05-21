# Mysql-5.7-子查询OrderBy再GroupBy问题

>  在Mysql 5.7 中，由于MYSQL优化器的原因，在子查询中使用 order by 会被优化器优化导致排序失效
>
> 如：`select * from (select * from a order by b asc) as t group by a` 这样的语句
>
> 在mysql5.7中，如果不加limit，系统会把order by优化掉。
> 在mysql5.7手册的8.2.2.1中有解释：
> 子查询的优化是使用半连接的策略完成的(The optimizer uses semi-join strategies to improve subquery execution)
> 使用半连接进行优化，子查询语句必须满足一些标准(In MySQL, a subquery must satisfy these criteria to be handled as a semi-join)。
>
> 其中一个标准是:必须不是一个包含了limit和order by的语句(It must not have ORDER BY with LIMIT.)

#### 方法一

子查询中 order by 加上 limit

`select * from (select * from a order by b asc limit 0,20) as t group by a`

#### 方法二

使用变量

```sql
SELECT *
FROM (
       SELECT
         *,
         @rn := IF(@prev = table_column, @rn + 1, 1) AS rn,
         @prev := table_column
       FROM your_table
         join (select
                 @prev := NULL,
                 @rn := 0) AS vars
       order by order_column asc) a
where rn <= 1
GROUP BY table_column
```


--oracle 分组排序 OVER PARTITION BY
select * from (
  select
    ROW_NUMBER()
    OVER (
      PARTITION BY ta.taskid
      ORDER BY acapplid asc ) rn,
    ta.TASKID,
    ta.ACAPPLID
  from taskacappl ta
  where ta.taskid in (
    select t.taskid
    from taskreg t
      join zzyzzy z on trim(z.taref) = trim(t.taref)
  )
) where rn <2
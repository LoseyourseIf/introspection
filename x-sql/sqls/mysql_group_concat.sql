SELECT 
  a.type, 
  a.name, 
  group_concat(concat(a.sort, '.', a.field) ORDER BY sort DESC) AS field 
FROM dictionary a 
WHERE instr(type, 'dic-') > 0 
GROUP BY `type`
 ORDER BY a.type ASC, a.sort DESC;

-- {"returnValue":[{"field":["0.不重要","1.重要","2.一般重要","3.非常重要","4.极度重要"],"name":"重要度","type":"dic-importance"},{"field":["0.完成","1.其他","2.可忽略","3.长推迟","4.暂推迟","5.研究中","6.一般关注","7.重点关注","8.预备做","9.优先做"],"name":"优先级","type":"dic-priority"}],"success":true}
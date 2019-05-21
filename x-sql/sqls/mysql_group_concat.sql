-- group_concat
SELECT 
  a.type, 
  a.name, 
  group_concat(concat(a.sort, '.', a.field) ORDER BY sort DESC) AS field 
FROM dictionary a 
WHERE instr(type, 'dic-') > 0 
GROUP BY `type`
 ORDER BY a.type ASC, a.sort DESC;
-- instr
package xingyu.lu.lab.unified.utils.map;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xingyu.lu
 * @create 2021-04-28 11:22
 **/
public class MPUtil {

    public static Map<String, Object> pojo2Map(Object obj) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;
    }
}

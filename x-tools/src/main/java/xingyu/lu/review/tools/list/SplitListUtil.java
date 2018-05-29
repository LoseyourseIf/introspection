package xingyu.lu.review.tools.list;

import java.util.ArrayList;
import java.util.List;

/**
 * 按数量对List进行拆分 返回
 * @author xingyu.lu
 * @create 2018-03-28 17:27
 **/
public class SplitListUtil {
    public static <T> List<List<T>> groupListByQuantity(List<T> list, int quantity) {
        List<List<T>> dividedList = new ArrayList<>();
        if (list == null || list.size() == 0) {
            dividedList.add(list);
            return dividedList;
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Wrong Quantity");
        }

        int count = 0;
        while (count < list.size()) {
            dividedList.add(
                    list.subList(count, (count + quantity) > list.size() ?
                            list.size() :
                            count + quantity));
            count += quantity;
        }

        return dividedList;
    }
}

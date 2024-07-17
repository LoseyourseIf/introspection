package xingyu.lu.lab.leet;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @Description: SumProblem
 * @Version: 1.0
 * @Author: XingYu.Lu
 * @CreateTime: 2024-07-16  21:55
 */
public class SumProblem {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        int target = 10;
        System.out.println(Arrays.toString(twoSum(nums, target)));
    }

    /**
     * @description:
     * 将数据存入 HashMap
     * key 为补数 value 为下标
     * hash.containsKey(nums[i]) 直接找到补数下标
     **/
    public static int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        HashMap<Integer, Integer> hash = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            System.out.println("------------------------");
            if (hash.containsKey(nums[i])) {
                result[0] = i;
                result[1] = hash.get(nums[i]);
                break;
            }
            hash.put(target - nums[i], i);
        }
        return result;
    }
}

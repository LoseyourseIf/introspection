package xingyu.lu.lab.leet;

/**
 * @Description: 爬楼梯
 * @Version: 1.0
 * @Author: XingYu.Lu
 * @CreateTime: 2024-07-16  23:43
 */
public class ClimbStairs {

    public static int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    public static void main(String[] args) {
        System.out.println(climbStairs(2));
        System.out.println(climbStairs(3));
        System.out.println(climbStairs(4));
        System.out.println(climbStairs(5));
    }
}

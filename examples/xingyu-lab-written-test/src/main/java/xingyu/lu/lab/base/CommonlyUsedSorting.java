package xingyu.lu.lab.base;

/**
 * 常用排序算法
 *
 * @author xingyu.lu
 * @create 2018-03-07 16:12
 **/
public class CommonlyUsedSorting {

    private int[] arr = {4, 5, 1, 2, 8, 6, 7, 3, 10, 9};

    public static void main(String[] args) {
        CommonlyUsedSorting sorting = new CommonlyUsedSorting();

//        sorting.bubblingSorting(sorting.arr);
//        sorting.selectionSorting(sorting.arr);
//        sorting.insertSorting(sorting.arr);
        sorting.shellSorting(sorting.arr);
        for (int i : sorting.arr) {
            System.out.print(i + ",");
        }
    }

    /**
     * @Describe 冒泡排序
     * @Auther xingyu.lu
     */
    private void bubblingSorting(int[] arr) {
        int i, j;
        for (i = 0; i < arr.length - 1; i++) {
            for (j = 0; j < arr.length - i - 1; j++) {
                int temp;
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    /**
     * @Describe 快速排序
     * @Auther xingyu.lu
     */
    private void quickSorting(int[] arr, int low, int high) {
        int i, j, temp, t;
        if (low > high) {
            return;
        }
        i = low;
        j = high;
        //temp 基准位
        temp = arr[low];

        while (i < j) {
            //先看右边，依次往左递减
            while (temp <= arr[j] && i < j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp >= arr[i] && i < j) {
                i++;
            }
            if (i < j) {
                t = arr[j];
                arr[j] = arr[i];
                arr[i] = t;
            }
        }
        //最后将基准为与i和j相等位置的数字交换
        arr[low] = arr[i];
        arr[i] = temp;
        //递归调用左半数组
        this.quickSorting(arr, low, j - 1);
        //递归调用右半数组
        this.quickSorting(arr, j + 1, high);
    }

    /**
     * @Describe 选择排序
     * @Auther xingyu.lu
     */
    private void selectionSorting(int[] arr) {
        int i, j, index;
        for (i = 0; i < arr.length - 1; i++) {
            index = i;
            for (j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[index]) { //min
                    index = j;
                }
            }
            if (i != index) {
                int temp = arr[i];
                arr[i] = arr[index];
                arr[index] = temp;
            }
        }

    }

    /**
     * @Describe 插入排序
     * @Auther xingyu.lu
     */
    private void insertSorting(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i - 1;
            while (j >= 0 && temp < arr[j]) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }
    }

    /**
     * @Describe 希尔排序
     * @Auther xingyu.lu
     */
    private void shellSorting(int[] arr) {
        int gap = arr.length / 2;
        while (gap > 0) {
            for (int i = gap; i < arr.length; i++) {
                int temp = arr[i];
                int j = i - gap;
                while (j >= 0 && temp < arr[j]) {
                    arr[j + gap] = arr[j];
                    j = j - gap;
                }
                arr[j + gap] = temp;
                j = j - gap;
            }
            gap = gap / 2;
        }
    }

    /**
     * @Describe 堆排序
     * 堆 一棵顺序存储的完全二叉树，
     * 其非ROOT节点的值均不大于（或不小于）
     * 其 leftChild 或 rightChild 节点的值
     * 其中 每个节点的值小于或等于其 leftChild、rightChild 的值 这样的堆称为小根堆
     * 每个节点的值大于或等于其 leftChild、rightChild 的值 这样的堆称为大根堆
     * @Auther xingyu.lu
     */
    private void heapSorting(int[] a) {
        int i;
        for (i = a.length / 2 - 1; i >= 0; i--) {// 构建一个大顶堆
            adjustHeap(a, i, a.length - 1);
        }
        for (i = a.length - 1; i >= 0; i--) {// 将堆顶记录和当前未经排序子序列的最后一个记录交换
            int temp = a[0];
            a[0] = a[i];
            a[i] = temp;
            adjustHeap(a, 0, i - 1);// 将a中前i-1个记录重新调整为大顶堆
        }
    }

    /**
     * 构建大顶堆
     */
    private void adjustHeap(int[] a, int i, int len) {
        int temp, j;
        temp = a[i];
        for (j = 2 * i; j < len; j *= 2) {// 沿关键字较大的孩子结点向下筛选
            if (j < len && a[j] < a[j + 1])
                ++j; // j为关键字中较大记录的下标
            if (temp >= a[j])
                break;
            a[i] = a[j];
            i = j;
        }
        a[i] = temp;
    }

}

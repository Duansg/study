package org.example.algorithm.quickSort;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;

/**
 * QuickSort
 *
 * @author Duansg
 * @version 1.0
 * @date 2021/2/19 下午11:19
 */
public class QuickSort {
    /*
    核心思想：
        基础的快速排序算法核心就是一句话：找到基准值的位置。
    拆解步骤：
        1).选择一个值作为基准值[注：最简单的选择方法，一定是选择待排序区间的头部元素作为基准值]
        2).找到基准值的位置，并将小于基准值的元素放在基准值的前面，大于基准值的元素放在基准值的后面
        3).对基准值的左右两侧递归地进行这个过程
    示例：
        例如有数组[8，3，10，2，7，6，9，12]
    步骤1.
        选择基准值为下标0的第一位元素8；
    步骤2(重点).
        将小于基准值的元素放在基准值的前面，将大于基准值的元素放在基准值的后面。
        扩展延伸：
            可以这么理解，我们在选择完基准值以后，就意味着我们记录了这个基准值，而基准值所在的位置为空。
            然后从数组的末尾扫描，如果小于基准值就将这个数放到这个空位上，这样后面就空出一位了。
            然后，我们再将前面大于基准值的元素放到后面这个空位上。
            就这样交替进行，直到空位前面的值都小于基准值，空位后面的值都大于基准值为止。
     */
    public static void main(String[] args) {
        int[] n = {8,3,10,2,7,6,9,12};
        quickSort(n,0,n.length-1);
        System.out.println(JSON.toJSONString(n));
    }

    private static void quickSort(int[] arr, int l, int r) {
        if (l >= r) return ;
        // x  : 记录从前向后扫描的位置
        // y  : 记录从后向前扫描的位置
        // z  : 基准值，选择待排序区间的第一个元素
        int x = l, y = r, z = arr[l];
        //保证头指针不能大于尾指针,如果相等就说明头尾指针重合了，因为是同时扫描的，所以头指针<尾指针
        while (x < y){
            //先从后开始扫描,如果大于就步进1位,直到找到尾部小于基准位的，因为是内部扫描的，也要保证头指针<尾指针
            while (x < y && arr[y] >= z){
                --y;
            }
            if (x < y) {//头指针<尾指针
                arr[x] = arr[y];
                x++;//这个位置是空位
            }
            while (x < y && arr[x] <= z) {
                ++x;
            }
            if (x < y){
                arr[y] = arr[x];
                y--;
            }
        }
        // 将基准值 z 放入其正确位置数组的 x 位
        // 其实，此时 x==y，所以写成 arr[y] = z 也行
        // 再分别对左右区间，进行快速排序
        arr[x] = z;
        quickSort(arr, l, x - 1);
        quickSort(arr, x + 1, r);
    }
}



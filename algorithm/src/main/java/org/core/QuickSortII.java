package org.core;

import java.util.Arrays;

/**
 * 三路划分排序
 * Created by chriszhang on 2017/7/12.
 */
public class QuickSortII {


    /**
     * 三路划分排序，关键在于左边全小于flag，右边全大于flag，中间全等于flag
     * l始终表示小于flag区域的下一个位置，向右移动
     * h始终表示大于flag区域的下一个位置，向左移动
     * i使用表示等于flag区域的下一个位置，向右移动
     * @param a
     * @param si
     * @param ei
     */
    private void sort(int[] a, int si, int ei) {
        if (si >= ei) {
            return;
        }
        int l = si, i = si, h = ei - 1;
        int flag = a[h];
        while (i <= h) {
            if (a[i] < flag) {
                swap(a, l++, i++);
            } else if (a[i] > flag) {
                swap(a, i, h--);
            } else {
                i++;
            }
        }
        sort(a, si, l);
        sort(a, h + 1, ei);
    }

    private void swap(int[] a, int x, int y) {
        int tmp = a[x];
        a[x] = a[y];
        a[y] = tmp;
    }

    public static void main(String[] args){
        QuickSortII quickSortII = new QuickSortII();
        int[] a = {-3,-9,1,5,1,7,-1,5,8,3,4,1,5,0};
        quickSortII.sort(a,0,a.length);
        System.out.println(Arrays.toString(a));
    }
}

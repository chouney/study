package org.core;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 最大堆
 * Created by manatea on 2016/11/30.
 */
public class MaxHeapify {
    /**
     * 维护最大堆
     * @param a
     * @param ind
     */
    private void maxHeapfy(int[] a, int ind, int len) {
        int mi = ind;
        int lind = ind * 2;
        int rind = ind * 2 + 1;
        if (lind < len) {
            mi = a[lind] > a[mi] ? lind : mi;
        }
        if (rind < len) {
            mi = a[rind] > a[mi] ? rind : mi;
        }
        if (mi != ind) {
            swap(a, mi, ind);
            maxHeapfy(a, mi,len);
        }
    }

    /**
     * 建立最大堆
     * @param a
     */
    private void buildHeap(int[] a,int len){
        for(int i = (len-1)/2;i>=0;i--){
            maxHeapfy(a,i,len);
        }
    }

    /**
     * 堆排序
     * @param a
     */
    private void heapSort(int[] a){
        int[] tmp = Arrays.copyOf(a,a.length);
        buildHeap(tmp,tmp.length);
        for(int i = tmp.length-1,j=0;i>=0;i--,j++){
            swap(tmp,0,i);
            a[j]=tmp[i];
            maxHeapfy(tmp,0,i);
        }
    }

    /**
     * 交换函数
     * @param a
     * @param x
     * @param y
     */
    private void swap(int[] a, int x, int y) {
        int tmp = a[x];
        a[x] = a[y];
        a[y] = tmp;
    }

    public static void main(String[] args) {
        MaxHeapify maxHeapify = new MaxHeapify();
        int a[] = {-1, 3, 7, 1, 2, 9, 6, 3, 8};
        maxHeapify.heapSort(a);
        System.out.println(Arrays.toString(a));
    }
}

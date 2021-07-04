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
        int a1[] = {-1, 3, 7, 1, 2, 9, 6, 3, 8};
        maxHeapify.heapSort(a);
        maxHeapify.heapSort1(a1);
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(a1));
    }


    public void heapVeri(int a[],int ind,int len){
        if(ind > (len-1) >> 1){
            return;
        }
        int lt = (ind<<1)+1;
        int rt = (ind<<1)+2;
        int rsInd = ind;
        if(lt < len){
            rsInd = a[lt] > a[rsInd] ? lt : rsInd;
        }
        if(rt < len){
            rsInd = a[rt] > a[rsInd] ? rt : rsInd;
        }
        if(rsInd!=ind) {
            swap(a, rsInd, ind);
            heapVeri(a, rsInd, len);
        }
    }

    public void buildHeap1(int a[],int len){
        int lastMid = (len-2)>>1;
        for(int i = lastMid ; i>=0;i--){
            heapVeri(a,i,len);
        }
    }

    public void heapSort1(int a[]){
        int[] temp = Arrays.copyOf(a,a.length);
        buildHeap1(temp,temp.length);
        for(int j=temp.length-1,i = 0;j>=0;j--,i++){
            swap(temp,0,j);
            a[i] = temp[j];
            maxHeapfy(temp,0,j);
        }
    }
}

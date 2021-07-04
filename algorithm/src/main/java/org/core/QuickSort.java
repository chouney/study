package org.core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 快速排序
 * Created by manatea on 2016/11/30.
 */
public class QuickSort {
    private int partition(int[] a, int si, int ei) {
        int i = si - 1, flag = a[ei - 1];
        for (int j = si; j < ei; j++) {
            if (flag > a[j]) {
                i++;
                swap(a, i, j);
            }
        }
        swap(a, i + 1, ei - 1);
        return i + 1;
    }

    private void quickSort(int[] a, int si, int ei) {
        if (si >= ei) {
            return;
        }
        int ind = partition(a, si, ei);
        quickSort(a, si, ind);
        quickSort(a, ind + 1, ei);
    }

    private void swap(int[] a, int x, int y) {
        int tmp = a[x];
        a[x] = a[y];
        a[y] = tmp;
    }
    public void solve (int[] a) {
        quickSort(a,0,a.length);
    }
    public static void main(String[] args) {
        QuickSort quickSort = new QuickSort();
        SubQuickSort subQuickSort = quickSort.new SubQuickSort();
        int[] a = {3,1,2,5,4};
        subQuickSort.solve(a);
        System.out.println(Arrays.toString(a));
    }

    public class SubQuickSort extends QuickSort {
        private int partition(int[] a, int si, int ei) {
            if(si >= ei){
                return si;
            }
            //3 1 4 5 2
            //1 3 4 5 2
            //
            int i = si -1;
            int num = a[ei-1];
            for(int j = si;j<ei;j++){
                if(num > a[j]){
                    i++;
                    super.swap(a,i,j);
                }
            }
            super.swap(a,i+1,ei-1);
            return i+1;
        }

        private void quickSort(int[] a, int si, int ei) {
            if(si >= ei){
                return;
            }
            int ind = partition(a,si,ei);
            this.quickSort(a,si,ind);
            this.quickSort(a,ind+1,ei);
        }
        public void solve (int[] a) {
            this.quickSort(a,0,a.length);
        }
    }
}

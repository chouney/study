package algorithm;

import java.util.Arrays;

/**
 * Created by manatea on 2016/11/30.
 */
public class QuickSort {
  /*  public static int partition(int[] a,int s,int e){
        int l = e-1,j=s-1;
        for(int i=s;i<l;i++){
            if(a[i]<a[l]){
                j++;
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }
        int temp = a[j+1];
        a[j+1] = a[l];
        a[l] = temp;
        return j+1;
    }
    public static void quickSort(int[] a,int s,int e){
        if(s<e){
            int m = partition(a,s,e);
            quickSort(a,s,m);
            quickSort(a,m+1,e);
        }
    }*/

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

    public static void main(String[] args) {
        QuickSort quickSort = new QuickSort();
        int a[] = {-1, 3, 7, 1, 2, 9, 6, 3, 8};
        quickSort.quickSort(a, 0, a.length);
        System.out.println(Arrays.toString(a));
    }
}

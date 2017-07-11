package algorithm;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Created by manatea on 2016/11/30.
 */
public class MaxHeapify{
    public static void maxHeapify(int[] a,int i,int size){
        if(i*2+1 <= size){
            if(a[i*2+1] > a[i*2] && a[i*2+1] > a[i]){
                a[i] = a[i] + a[i*2+1];
                a[i*2+1] = a[i] - a[i*2+1];
                a[i] = a[i] - a[i*2+1];
                maxHeapify(a,i*2+1,size);
            }else if( a[i*2] > a[i*2+1] && a[i*2] > a[i]){
                a[i] = a[i] + a[i*2];
                a[i*2] = a[i] - a[i*2];
                a[i] = a[i] - a[i*2];
                maxHeapify(a,i*2,size);
            }
        }else if(i*2 <= size){
            if( a[i*2] > a[i]){
                a[i] = a[i] + a[i*2];
                a[i*2] = a[i] - a[i*2];
                a[i] = a[i] - a[i*2];
                maxHeapify(a,i*2,size);
            }
        }
    }
    public static void buildMapHeap(int[] a){
        for(int i = (a.length-1)/2; i >=1; i--){
            maxHeapify(a,i,a.length-1);
        }
    }
    public static void heapSort(int[] a){
        int size = a.length-1;
        int[] b = new int[a.length];
        for(int i = 0;size>0;){
            int temp = a[1];
            a[1] = a[size];
            a[size] = temp;
            b[i++] = a[size--];
            maxHeapify(a,1,size);
        }
        System.out.println(Arrays.toString(b));
    }
    public static void main(String[] args){
        int a[] = {-1,3,7,1,2,9,6,3,8};
        buildMapHeap(a);
        heapSort(a);
    }
}

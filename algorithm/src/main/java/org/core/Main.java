package org.core;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {



        System.out.println(Arrays.toString(findTopK(new int[]{9,5,3,7,5,2,1},3)));
    }

    private static int[] findTopK(int[] arr,int topK){
        int[] heap = buildHeap(arr,topK);
        for(int i = topK;i<arr.length;i++){
            int temp = heap[0];
            heap[0] = arr[i];
            fixHeap(heap);
            if(heap[0] < temp){
                heap[0] = temp;
            }
        }
        return heap;
    }



    private static int[] buildHeap(int[] arr,int topK){
        int[] heap = new int[topK];
        for(int i = 0;i<topK;i++){
            heap[i] = arr[i];
        }
        fixHeap(heap);
        return heap;
    }


    private static void verifyHeap(int[] heap,int i){
        if((i+1)*2-1>=heap.length&& (i+1)*2>=heap.length){
            return;
        }
        int leftInd = (i+1)*2-1;
        int rightInd = (i+1)*2;
        int minInd = i;
        if(leftInd<heap.length && heap[leftInd] < heap[minInd]){
            minInd = leftInd;
        }
        if(rightInd<heap.length && heap[rightInd] < heap[minInd]){
            minInd = rightInd;
        }
        if(minInd != i) {
            swap(heap,minInd,i);
            verifyHeap(heap,minInd);
        }
    }

    private static void fixHeap(int[] heap){
        int mid = (heap.length >> 1) -1;
        for(int i = mid;i>=0;i--){
            verifyHeap(heap,i);
        }
    }

    private static void swap(int[] arr,int a,int b){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }


}

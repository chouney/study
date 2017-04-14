package algorithm;

import java.util.Arrays;

/**
 * Created by manatea on 2016/11/30.
 */
public class RandomizedSelect {
    public static int randomizedSelect(int[] a,int s,int e,int i){
        if(s<e){
            int m = partition(a,s,e);
            if(m == i){
                return a[m];
            }
            if(i < m){
                return randomizedSelect(a,s,m,i);
            }
            if(i > m){
                return randomizedSelect(a,m+1,e,i);
            }
        }
        return -1;
    }
    public static int partition(int[] a,int s,int e){
        int j=s-1,k=e-1;
        for(int i=s;i<k;i++){
            if(a[i]<a[k]){
                j++;
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }
        int temp = a[k];
        a[j+1] = a[k];
        a[k] = temp;
        return j+1;
    }

    public int binarySearch(int[] a,int k){
        if(a.length==0){
            return -1;
        }
        int lo = 0,hi = a.length-1;
        while(lo<=hi){
            int mid = lo+(hi - lo)/2;
            if(a[mid] == k) return mid;
            if(a[mid] < k){
                lo = mid+1;
            }else{
                hi = mid-1;
            }
        }
        return -1;
    }
    public static void main(String[] args){
        int a[] = {-1,3,7,1,2,9,6,3,8};
        int b[] = {1,2,5,6};
        RandomizedSelect r = new RandomizedSelect();
        System.out.println(r.binarySearch(b,5));
    }
}

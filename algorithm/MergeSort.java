import java.util.Arrays;

/**
 * 归并排序
 * Created by manatea on 2016/11/30.
 */
public class MergeSort {
    public static void mergeSort(int[] n , int s,int e){
        if(s>=e) {
            return;
        }
        int mid = (s+e)>>1;
        mergeSort(n,s,mid);
        mergeSort(n,mid+1,e);
        merge(n,s,mid,mid+1,e);
    }
    public static void merge(int[] n ,int s1,int e1,int s2,int e2){
        int len = e2-s1+1;
        int[] t = new int[len];
        int i1=s1,i2=s2,i=0;
        for(;i1<=e1&&i2<=e2;){
            t[i++]=n[i1]<n[i2]?n[i1++]:n[i2++];
        }
        if(i1==e1+1){
            for(;i2<=e2;i2++){
                t[i++] = n[i2];
            }
        }else if(i2==e2+1){
            for(;i1<=e1;i1++){
                t[i++] = n[i1];
            }
        }
        for(int j =0,k=s1;j<t.length;j++,k++){
            n[k]=t[j];
        }
        return ;
    }
   public static void main(String[] args){
       int a[] = {3,7,1,2,9,6,3,8};
       mergeSort(a,0,a.length-1);
       System.out.println(Arrays.toString(a));
   }
}

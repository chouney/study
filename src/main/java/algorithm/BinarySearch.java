package algorithm;

/**
 * Created by chriszhang on 2017/7/20.
 */
public class BinarySearch {
    public int binarySearch(int[] arr,int num){
        int l = 0,h = arr.length,m;
        while(l<h){
            m = l + ((h-l)>>1);
            if(num == arr[m]){
                return m;
            }
            if(num < arr[m]){
                h = m;
            }else{
                l = m+1;
            }
        }
        return -1;
    }
    public static void main(String[] args){
        BinarySearch b = new BinarySearch();
        System.out.println(b.binarySearch(new int[]{0,1,2,3,4,5,6,7,8,9},-1));
        System.out.println(b.binarySearch(new int[]{0,1,2,3,4,5,6,7,8,9},0));
        System.out.println(b.binarySearch(new int[]{0,1,2,3,4,5,6,7,8,9},1));
        System.out.println(b.binarySearch(new int[]{0,1,2,3,4,5,6,7,8,9},2));
        System.out.println(b.binarySearch(new int[]{0,1,2,3,4,5,6,7,8,9},3));
        System.out.println(b.binarySearch(new int[]{0,1,2,3,4,5,6,7,8,9},4));
        System.out.println(b.binarySearch(new int[]{0,1,2,3,4,5,6,7,8,9},5));
        System.out.println(b.binarySearch(new int[]{0,1,2,3,4,5,6,7,8,9},6));
        System.out.println(b.binarySearch(new int[]{0,1,2,3,4,5,6,7,8,9},7));
        System.out.println(b.binarySearch(new int[]{0,1,2,3,4,5,6,7,8,9},8));
        System.out.println(b.binarySearch(new int[]{0,1,2,3,4,5,6,7,8,9},9));
        System.out.println(b.binarySearch(new int[]{0,1,2,3,4,5,6,7,8,9},10));

    }
}

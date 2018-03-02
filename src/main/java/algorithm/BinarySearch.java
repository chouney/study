/**
 * 二分查找算法
 * Created by chriszhang on 2017/7/20.
 */
public class BinarySearch {
    /**
     * 最基础的二分查找
     * @param arr
     * @param num
     * @return
     */
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

    /**
     * 时间复杂度为logn的前提下,返回t在数组中第一次出现的位置
     * @param arr
     * @param num
     * @return
     */
    public int findFirst(int[] arr,int num){
        int s = 0, e=arr.length,m;
        while(s<e){
            m = s+((e-s)>>1);
            if(arr[m] == num){
                return firstHelper(arr,m,arr[m]);
            }
            if(num < arr[m])
                e = m;
            else
                s = m+1;
        }
        return -1;
    }

    public int firstHelper(int[] arr,int index, int num){
        if(index == 0 || index >0 && arr[index-1]!=num)
            return index;
        int s = 0,e=index,m;
        while(s<e){
            m = s+((e-s)>>1);
            if(arr[m] == num){
                if(m == 0 || m>0 && arr[m-1]!=num)
                    return m+1;
                e = m;
            }else
                s = m+1;
        }
        return -1;
    }

    public static void main(String[] args){
        BinarySearch b = new BinarySearch();
//        System.out.println(b.binarySearch(new int[]{0,1,2,3,4,5,6,7,8,9},-1));
//        System.out.println(b.binarySearch(new int[]{0,1,2,3,4,5,6,7,8,9},0));
//        System.out.println(b.binarySearch(new int[]{0,1,2,3,4,5,6,7,8,9},1));
//        System.out.println(b.binarySearch(new int[]{0,1,2,3,4,5,6,7,8,9},2));
//        System.out.println(b.binarySearch(new int[]{0,1,2,3,4,5,6,7,8,9},3));
//        System.out.println(b.binarySearch(new int[]{0,1,2,3,4,5,6,7,8,9},4));
//        System.out.println(b.binarySearch(new int[]{0,1,2,3,4,5,6,7,8,9},5));
//        System.out.println(b.binarySearch(new int[]{0,1,2,3,4,5,6,7,8,9},6));
//        System.out.println(b.binarySearch(new int[]{0,1,2,3,4,5,6,7,8,9},7));
//        System.out.println(b.binarySearch(new int[]{0,1,2,3,4,5,6,7,8,9},8));
//        System.out.println(b.binarySearch(new int[]{0,1,2,3,4,5,6,7,8,9},9));
//        System.out.println(b.binarySearch(new int[]{0,1,2,3,4,5,6,7,8,9},10));
        System.out.println(b.findFirst(new int[]{1,1,1,1,1,1,1,1,1,1,3},3));

    }
}

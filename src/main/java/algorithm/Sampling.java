package algorithm;


import java.util.Arrays;
import java.util.Random;

/**
 * 抽样算法
 */
public class Sampling {


    /**
     * 洗牌算法
     * @param n
     */
    public static void shuffle(int[] n){
        if(n.length <=0){
            return ;
        }
        Random random = new Random();
        for(int i = n.length-1;i>=0;i--){
            swap(n,i,random.nextInt(i+1));
        }
    }

    /**
     * 蓄水池抽样算法,从数组n中等概率取出k个数
     *
     * @param n
     * @param k
     */
    public static int[] reserviorSampling(int[] n, int k) {
        if (k < 0 || n.length < k) {
            return new int[0];
        }
        Random random = new Random();
        for (int i = k; i < n.length; i++) {
            int tmp ;
            if ((tmp = random.nextInt(i + 1)) < k) {
                swap(n,tmp,i);
            }
        }
        return Arrays.copyOf(n,k);
    }

    private static void swap(int[] arr, int aindex, int bindex) {
        if(arr.length < aindex || arr.length < bindex || aindex<0 || bindex<0) {
            return ;
        }
        int tmp = arr[aindex];
        arr[aindex] = arr[bindex];
        arr[bindex] = tmp;
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5,6,7,8,9,0};
        shuffle(a);
        System.out.println(Arrays.toString(a));
    }

}
// Copyright (C) 2018 Meituan
// All rights reserved
import java.util.Arrays;

/**
 * 跨内存区翻转 abcdefg->aefdbcg
 * @author manatea
 * @version 1.0
 * @created 2018/2/5 PM5:00
 **/
public class ReverseString {
    private void reverse(char[] chs,int s,int e){
        if(s<0 || e>chs.length || s>e){
            throw new IllegalArgumentException();
        }
        for(int i = s,j=e ; i<=j ;i++,j--){
            exchange(chs,i,j);
        }
    }

    private void exchange(char[] chs,int i , int j) {
        if (i < 0 || j > chs.length || j < 0 || i > chs.length) {
            throw new IllegalArgumentException();
        }
        char tmp = chs[i];
        chs[i] = chs[j];
        chs[j] = tmp;
    }

    private void reverseFromNonNeightbor(char[] chs,int s1,int e1,int s2,int e2){
        checkArgument(chs, s1, e1, s2, e2);
        System.out.println(Arrays.toString(chs));
        reverse(chs,s1,e2-1);
        System.out.println(Arrays.toString(chs));
        reverse(chs,s1,e1-1);

        System.out.println(Arrays.toString(chs));
        reverse(chs,s2,e2-1);
        System.out.println(Arrays.toString(chs));
    }

    private void checkArgument(char[] chs,int s1,int e1,int s2,int e2){
        if(chs == null || chs.length == 0){
            throw new IllegalArgumentException();
        }
        if(s1 < 0 || e2 > chs.length || s1 > e1 ||
                s2 < 0 || s2 > chs.length || s2 > e2 ||
                e1>=s2){
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args){
        ReverseString rever = new ReverseString();
        char[] chs ="abcdefg".toCharArray();
        rever.reverseFromNonNeightbor(chs,1,3,4,6);
    }
}
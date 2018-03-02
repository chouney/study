package algorithm;

import java.util.Arrays;

/**
 * 字符串匹配算法Sunday
 * Created by manatea on 2016/11/30.
 */
public class Sunday {
    public static int search(char[] pattern,char ch){
        //1.这里是逆向查询
        for(int i = pattern.length-1 ;i>=0;i--){
            if(pattern[i]==ch){
                return i;
            }
        }
        return -1;
    }
    public static boolean compare(char[] str,char[] pattern,int index){
        for(int i = 0 ;i<pattern.length;i++){
            if(str[index++]!=pattern[i]){
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args){
        char[] str = "mississippi".toCharArray();
        char[] pattern = "sippia".toCharArray();
        int i=0;
        //2.1判断pattern是否大于str
        if(pattern.length > str.length){
            System.out.println(-1);
        }
        //2.2判断pattern是否为空
        if(pattern.length==0){
            System.out.println(0);
        }
        while(i<str.length){
            //3.判断比较过程中是否可能出现越界i+pattern.length>str.length
            if(i+pattern.length <= str.length && compare(str,pattern,i)){
                System.out.println(i);
                return;
            }else{
                //4.判断右移后是否越界
                i+=pattern.length;
                if(i>=str.length){
                    break;
                }
               int index;
                if((index = search(pattern,str[i]))!=-1){
                   i-=index;
                }
            }
        }
        System.out.println(-1);
    }
}

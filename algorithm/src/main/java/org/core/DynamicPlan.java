package org.core;// Copyright (C) 2018 Meituan
// All rights reserved
/**
 * 动态规划问题集
 * @author manatea
 * @version 1.0
 * @created 2018/2/28 PM2:21
 **/
public class DynamicPlan {

    /**
     * 求出数组中的子数组连续最大和
     * @param arr
     */
    public static void maxSumInSubArray(int[] arr){
        int dp = 0;
        int max = 0;
        for(int i = 0;i<arr.length;i++){
            dp = Math.max(0,dp+arr[i]);
            max = Math.max(dp,max);
        }
        System.out.println(max);
    }

    public static void main(String[] args){
        maxSumInSubArray(new int[]{4,6,1,-6,-7,-9,3,15,-7,-1,-2,-4,-5,2});
    }

}
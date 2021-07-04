package org.core;

import java.util.LinkedList;

public class Solution {

    private volatile Solution solution;


    public  Solution getInstance() {
        if (solution == null) {
            synchronized (Solution.class) {
                if(solution == null){
                    solution = new Solution();
                }
            }
        }
        return solution;
    }


    public double myPow(double x, int n) {
        double res = 1;
        if (n == 0) return res;
        int curN = n > 0 ? n : -n;
        while (curN > 0) {
            double ract = x;
            int ni;
            for (ni = 1; ni << 1 <= curN; ) {
                ni = ni << 1;
                ract *= ract;
            }
            curN -= ni;
            res *= ract;
        }
        return n > 0 ? res : (1.0D / res);
    }

//    public static void main(String[] args) {
//        int[] arr = new int[]{2,1,4,5,3};
//        System.out.println(findMaxK(arr,3));
//    }



}
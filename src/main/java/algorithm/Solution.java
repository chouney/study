package algorithm;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Solution {
    public String largestNumber(int[] nums) {
        Integer[] n = new Integer[nums.length];
        for(int i = 0;i<nums.length;i++){
            n[i] = nums[i];
        }
        Arrays.sort(n,new Comparator<Integer>(){
            public int compare(Integer o1,Integer o2){
                int count1=0,count2=0,to1=o1,to2=o2;
                while(to1!=0){
                    to1/=10;
                    count1++;
                }
                while(to2!=0){
                    to2/=10;
                    count2++;
                }
                count1 = count1 ==0 ? 1 : count1;
                count2 = count2 ==0 ? 1 : count2;
                double op1 = o1*Math.pow(10,count2)+o2;
                double op2 = o2*Math.pow(10,count1)+o1;
                return op1 == op2 ? 0 : (op1 > op2 ? -1 : 1) ;
            }
        });
        StringBuilder s = new StringBuilder();
        if(n.length>0 && n[0] == 0){
            s.append(0);
            return s.toString();
        }
        for(int i = 0;i<n.length;i++){
            s.append(n[i]);
        }
        return s.toString();
    }
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        boolean[] used = new boolean[nums.length];
        helper(result,new ArrayList<Integer>(),nums,used);
        return result;
    }
    private void helper(List<List<Integer>> list,List<Integer> tmp,int[] nums,boolean[] used){
        if(tmp.size() == nums.length){
            list.add(new ArrayList<>(tmp));
        }else{
            for(int i = 0;i<nums.length;i++){
                if(used[i] || (i > 0 && nums[i] == nums[i-1] && !used[i-1])) continue;
                used[i] = true;
                tmp.add(nums[i]);
                helper(list,tmp,nums,used);
                tmp.remove(tmp.size()-1);
                used[i] = false;
            }
        }
    }
    public static void main(String[] args) {
        Solution s = new Solution();
        new StringBuilder().length();
        System.out.println(s.permuteUnique(new int[]{1,1,2}));
    }
}
package algorithm;

import java.util.*;

public class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        LinkedList<TreeNode> deque = new LinkedList<TreeNode>();
        List ans = new ArrayList();
        boolean flag = false;
        if(root == null){
            return ans;
        }
        deque.push(root);
        while(!deque.isEmpty()){
            List arr = new ArrayList();
            int size = deque.size();
            flag =!flag;
            while(!tmp.isEmpty()){
                if(flag){
                    TreeNode node = deque.removeLast();
                    arr.add(node.val);
                    if(node.left !=null){
                        deque.addFirst(node.left);
                    }
                    if(node.right !=null){
                        deque.addFirst(node.right);
                    }
                }else{
                    TreeNode node = deque.removeFirst();
                    arr.add(node.val);
                    if(node.right !=null){
                        deque.addLast(node.right);
                    }
                    if(node.left !=null){
                        deque.addLast(node.left);
                    }
                }
            }
            ans.add(arr);
        }
        return ans;
    }
}
package org.core;

/**
 * 红黑树节点
 * Created by manatea on 2016/10/19.
 */
public class TreeNode  {
    public TreeNode parent;
    public TreeNode lchild;
    public TreeNode rchild;
    public int val;
    public Enum color;

    public class S{
        int a;
    }
    public enum  COLOR {RED,BLACK}

    public static TreeNode root;

    public final static TreeNode nil = new TreeNode(COLOR.BLACK);

    public TreeNode() {
        super();
        this.lchild = TreeNode.nil;
        this.rchild = TreeNode.nil;
        this.parent = TreeNode.nil;
//        a();
//        System.out.println(super.parent);
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(Enum color) {
        this.color = color;
    }
}

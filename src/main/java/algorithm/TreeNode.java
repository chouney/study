package algorithm;

/**
 * Created by manatea on 2016/10/19.
 */
public class TreeNode extends Node {
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

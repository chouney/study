package algorithm;

/**
 * Created by manatea on 2016/10/26.
 */
public class RedBlackTree {
    public static void leftRotate(TreeNode x){
        if(x.rchild!= TreeNode.nil){
            TreeNode y = x.lchild;
            y.parent = x.parent;
            if(y.lchild!= TreeNode.nil){
                y.lchild.parent = x;
            }
            x.rchild = y.lchild;
            if(x.parent== TreeNode.nil){
                TreeNode.root = y;
            }else if(x.parent.lchild==x){
                x.parent.lchild=y;
            }else{
                x.parent.rchild=y;
            }
            y.lchild =x;
            x.parent=y;
        }
    }
    public static void rightRotate(TreeNode x){
        if(x.lchild!= TreeNode.nil){
            TreeNode y = x.lchild;
            y.parent = x.parent;
            if(y.rchild!= TreeNode.nil){
                y.rchild.parent = x;
            }
            x.lchild = y.rchild;
            if(x.parent== TreeNode.nil){
                TreeNode.root = y;
            }else if(x.parent.lchild==x){
                x.parent.lchild =y;
            }else{
                x.parent.rchild=y;
            }
            y.rchild=x;
            x.parent=y;
        }
    }
    public static void insertTreeNode(int val){
        TreeNode idx = TreeNode.root;
        TreeNode y= TreeNode.nil;
        while(idx != TreeNode.nil){
            y = idx;
            if(val>idx.val){
                idx = idx.rchild;
            }else{
                idx = idx.lchild;
            }
        }
        TreeNode x = new TreeNode();
        x.parent = y;
        if(y== TreeNode.nil){
            TreeNode.root = x;
        }else if(val>y.val){
            y.rchild = x;
        }else{
            y.lchild=x;
        }
        x.lchild = TreeNode.nil;
        x.rchild = TreeNode.nil;
        x.color = TreeNode.COLOR.RED;
        insertFixUp(x);
    }
    public static void insertFixUp(TreeNode x){
        while(x.parent.color== TreeNode.COLOR.RED){
            if(x.parent.parent.lchild == x.parent){
                TreeNode y = x.parent.parent.rchild;
                if(y.color == TreeNode.COLOR.RED){
                    y.parent.color = TreeNode.COLOR.RED;
                    x.parent.color = TreeNode.COLOR.BLACK;
                    y.color = TreeNode.COLOR.BLACK;
                    x = y.parent;
                }else{
                    if(x == x.parent.rchild){
                        x = x.parent;
                        leftRotate(x);
                    }
                    x.parent.parent.color= TreeNode.COLOR.RED;
                    x.parent.color = TreeNode.COLOR.BLACK;
                    rightRotate(x.parent.parent);
                }
            }else {
                TreeNode y = x.parent.parent.lchild;
                if (y.color == TreeNode.COLOR.RED) {
                    y.parent.color = TreeNode.COLOR.RED;
                    y.color = TreeNode.COLOR.BLACK;
                    x.parent.color = TreeNode.COLOR.BLACK;
                    x = y.parent;
                } else {
                    if (x == x.parent.lchild) {
                        x = x.parent;
                        rightRotate(x);
                    }
                    x.parent.parent.color = TreeNode.COLOR.RED;
                    x.parent.color = TreeNode.COLOR.BLACK;
                    leftRotate(x.parent.parent);
                }
            }
        }
        TreeNode.root.color = TreeNode.COLOR.BLACK;
    }
}

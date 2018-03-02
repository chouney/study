package algorithm;

/**
 * 红黑树
 * Created by manatea on 2016/10/26.
 */
public class RedBlackTree {
    public static void leftRotate(TreeNode x){
        if(x.rchild!= TreeNode.nil){
            TreeNode y = x.lchild;
            //step 1 变更y的左节点的2个指针
            x.rchild = y.lchild;
            if(y.lchild!= TreeNode.nil){
                y.lchild.parent = x;
            }
            //step 2 变更x的父节点的2个指针
            y.parent = x.parent;
            if(x.parent== TreeNode.nil){
                TreeNode.root = y;
            }else if(x.parent.lchild==x){
                x.parent.lchild=y;
            }else{
                x.parent.rchild=y;
            }
            //step 3 变更y节点的2个指针
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

    public static void tmpInsert(int val){
        TreeNode idx = TreeNode.root;
        TreeNode y = TreeNode.nil;
        while(idx!= TreeNode.nil){
            y = idx;
            if(val < idx.val){
                idx = idx.lchild;
            }else{
                idx = idx.rchild;
            }
        }
        TreeNode x = new TreeNode(TreeNode.COLOR.RED);
        if(y == TreeNode.nil){
            x = TreeNode.root;
        }else if(x == y.lchild){
            y.lchild = x;
        }else{
            y.rchild = x;
        }
        x.lchild = TreeNode.nil;
        x.rchild = TreeNode.nil;
        x.parent = y;
        insertFixUp(x);
    }

    /**
     * 插入的逻辑还是很简单的
     * 遵循
     * @param val
     */
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

    /**
     * 红黑树五大属性：
     * 1.树颜色非黑即白
     * 2.根节点为黑色
     * 3.叶子节点为黑色
     * 4.红色结点的左右结点必须为黑色
     * 5.从根节点到任一叶子节点的简单路径的黑结点树相等
     *
     * 调整枚举总共有5中情况
     * @param x
     */
    public static void insertFixUp(TreeNode x){
        //情况1.结点为根节点则直接置黑色
        if(x == TreeNode.root){
            x.color = TreeNode.COLOR.BLACK;
            return;
        }
        //情况2.如果父节点为黑,则无需操作，否则破坏规则4
        while(x.parent.color == TreeNode.COLOR.RED){
            //查看该节点的父节点和叔叔结点
            //首先确认该父节点是祖父节点的左孩子还是右孩子,处理方式一样
            if(x.parent.parent.lchild == x.parent){
                TreeNode y = x.parent.parent.rchild;
                //判断叔叔结点颜色决定下一步怎么走
                if(TreeNode.COLOR.RED == y.color){
                    //2.1叔叔结点的颜色为红色，那么就是父亲节点和叔叔结点都是红色
                    //方法：处理方法很简单，将父节点和叔叔结点置黑，祖父节点置红，最后跳到祖父节点完成一次迭代
                    x.parent.color = y.color = TreeNode.COLOR.BLACK;
                    x.parent.parent.color = TreeNode.COLOR.RED;
                    x = x.parent.parent;
                }else{
                    //2.2.叔叔结点的颜色为黑色，那么则复杂一些，
                    if(x == x.parent.lchild){
                        //2.2.1 当前结点为父节点的左孩子
                        //父节点变黑色，祖父节点变红色
                        //以祖父节点为支点，做一次右旋
                        x.parent.color = TreeNode.COLOR.BLACK;
                        x.parent.parent.color = TreeNode.COLOR.RED;
                        rightRotate(x.parent.parent);
                    }else{
                        //2.2.2 当前结点为父节点的右孩子
                        //变更当前结点为父节点，做一次左旋
                        x = x.parent;
                        leftRotate(x);
                    }
                }
            }else{
                TreeNode y = x.parent.parent.lchild;
                if (y.color == TreeNode.COLOR.RED) {
                    y.parent.color = TreeNode.COLOR.RED;
                    y.color = TreeNode.COLOR.BLACK;
                    x.parent.color = TreeNode.COLOR.BLACK;
                    x = y.parent;
                } else {
                    if (x == x.parent.lchild) {
                        x.parent.color = TreeNode.COLOR.BLACK;
                        x.parent.parent.color = TreeNode.COLOR.RED;
                        rightRotate(x);
                    }
                    else{
                        x = x.parent;
                        leftRotate(x);
                    }
                }
            }
        }
    }

    /**
     * 红黑树删除
     * 与平衡二叉树相似
     *
     */
    public static void detele(TreeNode x){
        if(x.color == TreeNode.COLOR.RED){
            if(x.lchild == TreeNode.nil && x.rchild == TreeNode.nil){
                x.parent = null;

            }
        }else{

        }
    }

    public static void deteleFixUp(TreeNode x){

    }

    //替换方法，前提为旧节点必须为叶子节点
    private static void transplant(TreeNode oldN,TreeNode newN){
        //1.父节点替换
        if(newN == newN.parent.lchild){
            newN.parent.lchild = TreeNode.nil;
        }else{
            newN.parent.rchild = TreeNode.nil;
        }
        newN.parent = oldN.parent;
        if(oldN.parent == TreeNode.nil){
            TreeNode.root = newN;
        }else if (oldN == oldN.parent.lchild){
            oldN.parent.lchild = newN;
        }else {
            oldN.parent.rchild = newN;
        }
        //子节点替换
        if(oldN.lchild!=TreeNode.nil){
            oldN.lchild.parent = newN;
        }
        if(oldN.rchild!=TreeNode.nil){
            oldN.rchild.parent = newN;
        }
        newN.lchild = oldN.lchild;
        newN.rchild = oldN.rchild;
        //废弃oldN
        oldN.lchild = null;
        oldN.rchild = null;
        oldN.parent = null;
    }

    private static TreeNode findNextNode(TreeNode x) {
        if(x == TreeNode.nil){
            return x;
        }
        TreeNode y = x.rchild;
        while(y != TreeNode.nil && y.lchild!=TreeNode.nil){
            y = y.lchild;
        }
        return y;
    }

    public static void main(String[] args){
        TreeNode n1 = new TreeNode();
        n1.val=1;
        TreeNode n2 = new TreeNode();
        n2.val=2;
        TreeNode n3 = new TreeNode();
        n3.val=3;
        TreeNode n4 = new TreeNode();
        TreeNode.root = n1;
        n1.rchild = n3;
        n1.lchild = n2;
        transplant(n1,n2);
        System.out.println(n2);

    }
}

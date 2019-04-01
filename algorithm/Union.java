import java.util.Arrays;

/**
 * 并查集算法
 * Created by manatea on 2017/3/29.
 */
public class Union {
    int size = 100;
    int[] parent ;

    public Union() {
        parent = new int[size];
        for(int i = 0;i<size;i++){
            parent[i] = i;
        }
    }
    public int findParent(int[] p ,int x){
        while(p[x]!=x)
            x = p[x];
        return x;
    }
    public void setUnion(int[] p,int a,int b){
        int pa = findParent(p,a);
        int pb = findParent(p,b);
        if(pa == pb){
            return ;
        }
        if(pa<pb){
            p[pb] = pa;
        }
        else{
            p[pa] = pb;
        }
    }
    public static void main(String[] args){
        Union u = new Union();
        u.setUnion(u.parent,1,2);
        u.setUnion(u.parent,1,3);
        u.setUnion(u.parent,4,5);
        u.setUnion(u.parent,4,6);
        u.setUnion(u.parent,7,2);
        u.setUnion(u.parent,7,5);
        System.out.println(Arrays.toString(u.parent));
    }
}

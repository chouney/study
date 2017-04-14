import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;

/**
 * Created by manatea on 2017/3/6.
 */
public class BlockCheckLocking {
    public static void main(String[] args){
        ThreadMXBean th = ManagementFactory.getThreadMXBean();
        int[] a= new int[3];
        ThreadLocal local = new ThreadLocal();
//        Thread
//        local.set(a);
//        local.get();
//        local.Thread


    }
}

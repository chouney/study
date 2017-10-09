package concurrent;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Created by manatea on 2017/4/6.
 */
public class CountTask extends RecursiveTask<Integer>{
    private static final int THREAD_SSHORLD = 1000;
    private int start;
    private int end ;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;

        boolean canCompute = (end - start <= THREAD_SSHORLD);
        if(canCompute) {
            for(int i= start;i<=end;i++){
                sum+=i;
            }
        }else{
            int mid = start + ((end - start) >>1);
            CountTask left = new CountTask(start,mid);
            CountTask right = new CountTask(mid+1,end);
            left.fork();
            right.fork();
            int lRes = left.join();
            int rRes = right.join();
            sum = lRes + rRes;
        }
        return sum;
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        ForkJoinPool forkJoinPool = new ForkJoinPool();
//        CountTask countTask = new CountTask(0,10000);
//        Future<Integer> result = forkJoinPool.submit(countTask);
//        System.out.println(result.get());
        String[] params = {"qwe","123"};
        StringBuilder concat = new StringBuilder();
        if(Objects.nonNull(params)){
            Arrays.stream(params).forEach((String param) -> concat.append(param).append("=").
                    append(Arrays.stream(new String[]{}).findFirst().orElse("null")).append("&"));
        }
        System.out.println(concat.toString());
    }
}

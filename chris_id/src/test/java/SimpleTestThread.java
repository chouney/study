import org.v2.SecondSequenceGenerator;
import org.v2.SequenceConfig;
import org.v2.SequenceGenerator;
import org.v2.constant.IDGenerateTypeEnum;

/**
 * 单机测试类,10台机器每台1000qps的访问量
 * Created by ChrisZhang on 2020/2/13.
 */
public class SimpleTestThread extends Thread {
    private int machId;

    private SequenceGenerator sequenceGenerator ;

    public SimpleTestThread(int machId,SequenceGenerator sequenceGenerator) {
        super("testThread——"+machId);
        this.machId = machId;
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void run() {
        System.out.println("testTread——"+machId +"          start");

        while (true) {
            try {
                Thread.currentThread().sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long generateId = sequenceGenerator.generateID();
            System.out.println("testTread——"+machId +"          SeqId: "+generateId +
                    "          timestamp:"+sequenceGenerator.getTimeStamp(generateId) + "                seqId:"+sequenceGenerator.getSeqId(generateId));
        }
    }

    public static void main(String[] args){
        SequenceConfig sequenceConfig = new SequenceConfig(IDGenerateTypeEnum.LOCAL,1);
        SequenceGenerator sequenceGenerator = new SecondSequenceGenerator(sequenceConfig);
        for(int i = 0;i<10;i++){
            sequenceGenerator.setMachineID(i);
            new SimpleTestThread(i,sequenceGenerator).start();
        }
    }
}


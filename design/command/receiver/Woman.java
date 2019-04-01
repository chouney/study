package design.command.receiver;

/**
 * Created by chriszhang on 2018/1/2.
 */
public class Woman implements People{
    private int step = 0;

    @Override
    public void forward() {
        step++;
        System.out.println("she is moving forward "+step);
    }

    @Override
    public void backward() {
        if(step<=0) throw new RuntimeException();
        step--;
        System.out.println("she is jumping backward "+step);

    }
}

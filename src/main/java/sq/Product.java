package sq;

/**
 * Created by manatea on 2017/3/14.
 */
public class Product {
    private static int count = 0;
    private final int id = count++;

    public void consumeProduct(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public int getId(){
        return id;
    }
}

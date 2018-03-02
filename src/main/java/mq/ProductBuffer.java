/**
 *
 * 商品缓冲池实例
 * Created by manatea on 2017/3/14.
 */
public class ProductBuffer {
    private Product[] buffer;
    public int curIndex;

    public ProductBuffer(int capacity) {
        this.buffer = new Product[capacity];
        this.curIndex = 0;
    }

    public boolean isEmpty(){
        return curIndex==0;
    }

    public boolean isFull(){
        return curIndex==buffer.length;
    }

    public void offer(Product product){
        synchronized (this) {
            if (curIndex >= buffer.length) {
                throw new RuntimeException();
            }
            buffer[curIndex++] = product;
        }
    }
    public Product poll(){
        synchronized (this) {
            if (curIndex > 0) {
                return buffer[--curIndex];
            }
            throw new RuntimeException();
        }
    }
}

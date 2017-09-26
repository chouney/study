package module;

/**
 * Created by chriszhang on 2017/9/23.
 */
public class Singleon {
    //两种单例模式
    public static class Instance{
        private static Singleon singleon = new Singleon();
    }
    public static Singleon getInstance(){
        return Instance.singleon;
    }

    //两种单例模式
    public static volatile Singleon sinle;
    public static Singleon getInstance2(){
        if(sinle == null){
            synchronized (Singleon.class){
                if(sinle == null){
                    sinle = new Singleon();
                }
            }
        }
        return sinle;
    }
}

package design.factory.simple;

/**
 * 披萨实体
 * Created by chriszhang on 2017/12/28.
 */
public class Pizza {
    protected String name;

    public Pizza(String name) {
        this.name = name;
    }

    public void create(){
        System.out.println("create "+name+"Pizza");
    }
    public void cut(){
        System.out.println("cut "+name+"Pizza");

    }
    public void prepare(){
        System.out.println("prepare "+name+"Pizza");

    }
    public void boil(){
        System.out.println("boil "+name+"Pizza");
    }
}

import java.io.*;

/**
 * Created by manatea on 2016/8/23.
 */
public class Person implements Serializable {

    private String name;
    private transient String password;
    private int age;

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeUTF(name);
        stream.writeUTF(password);
        stream.writeInt(age);
//        stream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        this.name = stream.readUTF();
        this.password = stream.readUTF();
        this.age = stream.readInt();
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

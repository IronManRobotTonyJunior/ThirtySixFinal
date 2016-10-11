package obserable;

import java.util.Observable;

public class Boss extends Observable {

    String name;
    int age;
    public void sendOrder(String str) {
        setChanged();
        notifyObservers(str);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

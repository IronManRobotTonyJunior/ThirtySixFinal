package obserable;


import java.util.Observable;
import java.util.Observer;

public class BrotherMountain implements Observer {


    @Override
    public void update(Observable o, Object arg) {
        String str = (String) arg;
        Boss boss = (Boss) o;
    }

}

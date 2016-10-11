package com.example;

import java.util.ArrayList;
import java.util.List;

public class Boss implements Subject {


    private List<Observer> mObserver;
    private String sth;

    public Boss() {
        mObserver = new ArrayList<>();
    }

    @Override
    public void register(Observer observer) {
        mObserver.add(observer);
    }

    @Override
    public void unregister(Observer observer) {
        int index = mObserver.indexOf(observer);
        if (index >= 0 ){
            mObserver.remove(index);
        }
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : mObserver) {
            observer.update(sth);
        }

    }
    public void sendOrder(String sth){
        this.sth = sth;
        notifyObservers();
    }
}

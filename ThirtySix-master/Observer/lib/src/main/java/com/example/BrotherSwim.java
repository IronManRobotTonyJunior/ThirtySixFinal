package com.example;

public class BrotherSwim implements Observer {

    private Subject mSubject;

    public BrotherSwim(Subject mSubject) {
        this.mSubject = mSubject;
        mSubject.register(this);
    }

    @Override
    public void update(String sth) {
        System.out.println("我要娶了她" + sth);
    }
}

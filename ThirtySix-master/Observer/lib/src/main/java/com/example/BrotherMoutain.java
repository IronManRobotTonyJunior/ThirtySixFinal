package com.example;

public class BrotherMoutain implements Observer {
    private Subject mSubject;

    public BrotherMoutain(Subject mSubject) {
        this.mSubject = mSubject;
        mSubject.register(this);
    }

    @Override
    public void update(String sth) {
        System.out.println("杀了这个人" + sth);
    }
}

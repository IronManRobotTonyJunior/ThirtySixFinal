package com.example;

public class ObserverDemo {
    public static void main(String args[]){

        Boss boss = new Boss();
        BrotherMoutain moutain = new BrotherMoutain(boss);
        BrotherSwim swim = new BrotherSwim(boss);
        boss.sendOrder("钟琳龟");
    }
}

package com.policysphere.policy;

public class ThreadDemo {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            System.out.println("Task 1: " +
                    Thread.currentThread().getName());
        });

        Thread t2 = new Thread(() -> {
            System.out.println("Task 2: " +
                    Thread.currentThread().getName());
        });

        t1.start();
        t2.start();

        System.out.println("Main thread completed");
    }
}

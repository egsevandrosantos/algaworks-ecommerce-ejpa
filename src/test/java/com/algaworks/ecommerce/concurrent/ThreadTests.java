package com.algaworks.ecommerce.concurrent;

import org.junit.jupiter.api.Test;

public class ThreadTests {
    private static void log(Object obj, Object... args) {
        System.out.printf("[LOG " + System.currentTimeMillis() + "] " + obj + "%n", args);
    }

    private static void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException ignored) {}
    }

    @Test
    public void runThreads() {
        Runnable runnable1 = () -> {
            log("Runnable 01 waiting 3 seconds");
            wait(3);
            log("Runnable 01 concluded");
        };

        Runnable runnable2 = () -> {
            log("Runnable 02 waiting 1 second");
            wait(1);
            log("Runnable 02 concluded");
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log("Finish method");
    }
}

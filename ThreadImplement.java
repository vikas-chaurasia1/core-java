package com.example.demo.design;

import java.util.concurrent.*;

class TestCallable implements Callable<String>{

    @Override
    public String call(){
        return "value return from call method";
    }
}

public class ThreadImplement {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService ex= Executors.newFixedThreadPool(2);
        System.out.println("Implementing callable thread");
        TestCallable testCallObj=new TestCallable();
        Future<String> future=ex.submit(testCallObj);
        System.out.println(future.get());
        ex.shutdown();
    }
}

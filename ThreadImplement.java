package com.example.demo.design;

import java.util.concurrent.*;

class TestCallable implements Callable<String>{

    @Override
    public String call() throws InterruptedException {
        Thread.sleep(9000);
        return "value return from call method";
    }
}

class TestCompletableFuture{
    public int testCompltFut(){
        return 10;
    }
}

public class ThreadImplement {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService ex= Executors.newFixedThreadPool(2);
        System.out.println("Implementing callable thread");
        TestCallable testCallObj=new TestCallable();
        Future<String> future=ex.submit(testCallObj);
        System.out.println(future.get());


        CompletableFuture<Integer> completableFuture=CompletableFuture.supplyAsync(()->{
            System.out.println("Completable future");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 10;
        },ex).thenApply(x->x*27);

        completableFuture.thenAccept(x-> System.out.println("vlaue of x->"+x));
        System.out.println("checking completable future blocking or not");


        TestCompletableFuture obj2=new TestCompletableFuture();
        CompletableFuture<Integer> completableFuture1=CompletableFuture.supplyAsync(()->{
            System.out.println("testing with executor");
            return obj2.testCompltFut();
        },ex).thenApply(x->x*10);
        completableFuture1.thenAccept(y-> System.out.println("final result using ex service "+y));

        completableFuture.join();
        ex.shutdown();
    }
}

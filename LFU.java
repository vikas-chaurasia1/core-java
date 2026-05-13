package com.example.demo.design;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;

class LFUImpl{
    public static class Node{
        private int key;
        private String val;
        private int fre;

        public Node(int key,String val,int fre){
            this.key=key;
            this.val=val;
            this.fre=fre;
        }
    }
    private int minFre;
    private int capacity;
    private Map<Integer,Node> keyValMap;
    private Map<Integer, LinkedHashSet<Integer>> freMap;

    public LFUImpl(int capacity){
        this.capacity=capacity;
        this.keyValMap=new HashMap<>();
        this.freMap=new HashMap<>();
    }

    public void put(int key,String  val){
        if(keyValMap.containsKey(key)){
            Node node=keyValMap.get(key);
            node.val=val;
            updateFre(node);
            return;
        }

        if(keyValMap.size()>=capacity){
            LinkedHashSet<Integer> set=freMap.get(minFre);
            Iterator<Integer> itr=set.iterator();
            int remKey=itr.next();
            set.remove(remKey);
            if(set.isEmpty()){
                freMap.remove(minFre);
            }
            keyValMap.remove(remKey);
        }

        Node newMode=new Node(key,val,1);
        keyValMap.put(key,newMode);

        if(freMap.get(1)!=null){
            LinkedHashSet<Integer> set1=freMap.get(1);
            set1.add(key);
        }else{
            LinkedHashSet<Integer> newSet= new LinkedHashSet<>();
            newSet.add(key);
            freMap.put(1,newSet);
        }

        minFre=1;
    }

    public String get(int key){
        if(keyValMap.containsKey(key)){
            Node node=keyValMap.get(key);
            updateFre(node);
            return node.val;
        }
        return "Not Found";
    }

    private void updateFre(Node node){
        int prevFre=node.fre;
        node.fre=node.fre+1;
        LinkedHashSet<Integer> set=freMap.get(prevFre);
        set.remove(node.key);
        if(set.isEmpty()){
            freMap.remove(prevFre);
            if(prevFre==minFre){
                minFre=minFre+1;
            }
        }
        if(freMap.get(prevFre+1)!=null){
            LinkedHashSet<Integer> set1=freMap.get(prevFre+1);
            set1.add(node.key);
        }else{
            LinkedHashSet<Integer> newSet= new LinkedHashSet<>();
            newSet.add(node.key);
            freMap.put(prevFre+1,newSet);
        }
    }
}

public class LFU {
    public static void main(){
        System.out.println("Lfu");
        LFUImpl obj=new LFUImpl(2);
        obj.put(1,"vikas");
        obj.put(4,"chaurasia");
        System.out.println(obj.get(1));
        obj.put(5,"Kumar");
        System.out.println("Value "+obj.get(1)+" "+obj.get(4)+" "+obj.get(5));
    }
}

class Lru{
    public static class Node{
        private int key;
        private String  val;
        private Node next;
        private Node prev;

        Node(int key,String val){
            this.key=key;
            this.val=val;
        }
    }

    private int capacity;
    private HashMap<Integer,Node> m1;
    private Node head;
    private Node tail;



    public Lru(int capacity){
        this.capacity=capacity;
        this.m1=new HashMap<>();
        head=new Node(0,"d");
        tail=new Node(0,"dcd");
        head.next=tail;
        tail.prev=head;
    }

    public String get(int key){
        if(!m1.containsKey(key)){
            return null;
        }
        Node node=m1.get(key);
        String val=node.val;
        remove(node);
        update(node);
        return val;

    }

    public void put(int key,String value){
        if(m1.containsKey(key)){
            remove(m1.get(key));
            m1.remove(key);
        }

        if(m1.size()>=capacity && !m1.containsKey(key)){
            int k=tail.prev.key;
            remove(tail.prev);
            m1.remove(k);
        }
        Node node =new Node(key,value);
        m1.put(key,node);
        update(node);
    }

    public void update(Node node){
        Node tmp=head.next;
        head.next=node;
        node.prev=head;
        node.next=tmp;
        tmp.prev=node;
    }

    public void remove(Node node){
        node.prev.next=node.next;
        node.next.prev=node.prev;
    }
}

public class LruDesign {
    public static void main(String[] args){
        //System.out.println("vikas chaurasia");
        Lru obj1=new Lru(2);
        obj1.put(1,"vikas");
        obj1.put(2,"Chaurasia");
        System.out.println(obj1.get(1));
        obj1.put(3,"kumar");
        System.out.println(obj1.get(1) +" "+ obj1.get(2)+" "+ obj1.get(3));
    }
}

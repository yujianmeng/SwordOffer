import java.util.Scanner;

/**
 * Created by yujian on 2018/3/7.
 * 递归创建链表
 */
public class Test {
    private static Scanner scanner;
    private static int length = 0;
    public static void createList(Node node){
        int value = scanner.nextInt();
        if(value == -1){
            return;
        }
        else{
            Node next = new Node(value);
            node.next = next;
            length++;
            createList(next);
        }
    }

    public static void main(String[] args){
        scanner = new Scanner(System.in);
        Node head = new Node(Integer.MAX_VALUE);
        createList(head);
        Node p = head.next;
        for (int i=0; i<length;i++){
            System.out.println(p.value);
            p = p.next;
        }
    }
}

class Node{
    int value;
    Node next;
    public Node(int value){
        this.value = value;
        next = null;
    }
}

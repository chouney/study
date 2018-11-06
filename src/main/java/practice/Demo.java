package practice;

/**
 * Created by chriszhang on 2017/10/21.
 */
public class Demo {
    public static void main(String[] args){
        //括号匹配
//        System.out.println(Stack.bracketMatch("({)}}{()".toCharArray()));
//        System.out.println(Stack.bracketMatch(")(".toCharArray()));
//        System.out.println(Stack.bracketMatch("(){}()[]".toCharArray()));
//        System.out.println(Stack.bracketMatch("({[]})".toCharArray()));
//        System.out.println(Stack.bracketMatch("(({{}}[]))".toCharArray()));

        //环形队列
        CircularQueue<Integer> queue = new CircularQueue<>();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        queue.offer(5);
        queue.offer(6);
        queue.offer(7);
        queue.offer(8);
        queue.offer(9);
        System.out.println(queue.poll().toString());// 1
        System.out.println(queue.poll().toString());//2
        System.out.println(queue.poll().toString());//3
        System.out.println(queue.poll().toString());//4
        queue.offer(10);
        queue.offer(11);
        queue.offer(12);
        queue.offer(13);
        queue.offer(14);
        System.out.println(queue.size()); // 4
        System.out.println(queue.peek().toString()); // 1
        System.out.println(queue.poll().toString());// 1
        System.out.println(queue.poll().toString());//2
        System.out.println(queue.poll().toString());//3
        System.out.println(queue.poll().toString());//4
        queue.offer(15);
        System.out.println(queue.poll().toString());//5
       /* Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        System.out.println(stack.size());//4
        System.out.println(stack.peek().toString());//4
        System.out.println(stack.pop().toString());//4
        System.out.println(stack.pop().toString());//3
        System.out.println(stack.pop().toString());//2
        System.out.println(stack.pop().toString());//1*/
    }
}

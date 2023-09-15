package test;


import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(2147483646);
        minStack.push(2147483646);
        minStack.push(2147483647);
        System.out.println(minStack.top());
        minStack.pop();
        System.out.println(minStack.min());
        minStack.pop();
        System.out.println(minStack.min());
        minStack.pop();
        minStack.push(2147483647);
        System.out.println(minStack.min());
    }

}
class MinStack {
    Deque<Long> stack1;
    Deque<Long> stack2;

    /** initialize your data structure here. */
    public MinStack() {
        stack1 = new LinkedList<>();
        stack2 = new LinkedList<>();
        stack2.push(Long.MAX_VALUE);
    }

    public void push(int x) {
        stack1.push(Long.valueOf(x));
        if(x <= stack2.peek()){
            stack2.push(Long.valueOf(x));
        }
    }

    public void pop() {
        Long temp = stack1.pop();
        Long peek = stack2.peek();
        if( temp.equals(peek) ){
            stack2.pop();
        }
    }

    public int top() {
        long temp = stack1.peek();
        return (int)temp;
    }

    public int min() {
        long temp = stack2.peek();
        return temp == Long.MAX_VALUE ? 0 :(int) temp;
    }

}


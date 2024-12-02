package com.example.finalproject;

class Node<T> {
    T data;
    Node<T> next;

    public Node(T data) {
        this.data = data;
        this.next = null;
    }
}
public class Queue<T> {
    private Node<T> front;
    private Node<T> rear;
    private int size;

    public Queue() {
        front = rear = null;
        size = 0;
    }

    public void enqueue(T data) {
        Node<T> newNode = new Node<>(data);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        T data = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return data;
    }

    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return front.data;
    }

    public T peekLast() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return rear.data;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }

    public void printQueue() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
        } else {
            Node<T> current = front;
            while (current != null) {
                System.out.println(current.data + " ");
                current = current.next;
            }
            System.out.println();
        }
    }
}

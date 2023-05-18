package com.solvd.Hospital.Classes;

import java.util.ArrayList;

public class CustomLinkedList<E> {
    private Node<E> head;

    public CustomLinkedList() {
        this.head = null;
    }

    public void add(E data) {
        if (head == null) {
            head = new Node<>(data);
        } else {
            Node<E> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = new Node<>(data);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        Node<E> current = head;
        while (current != null) {
            result.append(current.data);
            if (current.next != null) {
                result.append(", ");
            }
            current = current.next;
        }
        result.append("]");
        return result.toString();
    }

    public ArrayList<E> toArrayList() {
        ArrayList<E> list = new ArrayList<>();
        Node<E> currentNode = head;
        while (currentNode != null) {
            list.add(currentNode.data);
            currentNode = currentNode.next;
        }
        return list;
    }
    private static class Node<E> {
        E data;
        Node<E> next;

        public Node(E data) {
            this.data = data;
            this.next = null;
        }
    }
}
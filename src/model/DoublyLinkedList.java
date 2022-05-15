package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoublyLinkedList<E> {
    private Map<Integer, Node<E>> nodes = new HashMap<>();
    private Node<E> head;
    private Node<E> tail;
    private int size = 0;

    public Node<E> getHead() {
        return head;
    }

    public Map<Integer, Node<E>> getNodes() {
        return nodes;
    }

    public void removeNodes() {
        nodes.clear();
        head = null;
        tail = null;
    }


    public void linkLast(AbstractTask task) {
        final Node<E> oldTail = tail;
        final Node<E> newNode = new Node(oldTail, task, null);
        tail = newNode;
        if (oldTail == null)
            head = newNode;
        else
            oldTail.setNext(newNode);
        ++size;
        nodes.put(task.getId(), newNode);
    }

    public List<E> getTasks() {
        List<E> history = new ArrayList<>();
        if (head != null) {
            history.add(head.getData());
            Node<E> nextNode = head.getNext();
            while (!(nextNode == null)) {
                history.add(nextNode.getData());
                nextNode = nextNode.getNext();
            }
            return history;
        } else {
            return history;
        }
    }

    public void removeNode(Node<E> node, int id) {
        if ((nodes.size() == 1)) {
            head = null;
            tail = null;
            size = 0;
            nodes.clear();
        } else {
            if (node == head) {
                node.getNext().setPrev(null);
                head = node.getNext();
            } else if (node == tail) {
                node.getPrev().setNext(null);
                tail = node.getPrev();
            } else {
                node.getPrev().setNext(node.getNext());
                node.getNext().setPrev(node.getPrev());
            }
            node.setPrev(null);
            node.setNext(null);
            nodes.remove(id);
        }
    }
}
package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoublyLinkedList<E> {
    public Map<Integer, Node<E>> nodes = new HashMap<>();
    public Node<E> head;
    public Node<E> tail;
    private int size = 0;

    public void linkLast(AbstractTask task) {
        final Node<E> oldTail = tail;
        final Node<E> newNode = new Node(oldTail, task, null);
        tail = newNode;
        if (oldTail == null)
            head = newNode;
        else
            oldTail.next = newNode;
        size++;
        nodes.put(task.getId(), newNode);

    }

    public List<E> getTasks() {
        List<E> tasksHistory = new ArrayList<>();
        tasksHistory.add(head.data);
        Node<E> nextNode = head.next;
        while (!(nextNode == null)) {
            tasksHistory.add(nextNode.data);
            nextNode = nextNode.next;
        }
        return tasksHistory;
    }

    public void removeNode(Node<E> node) {
        if (node == head) {
            node.next.prev = null;
            head = node.next;
        } else if (node == tail) {
            node.prev.next = null;
            tail = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        node.prev = null;
        node.next = null;
    }
}
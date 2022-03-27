package service;

import model.AbstractTask;
import service.HistoryManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int HISTORY_SIZE = 10;

    private DoublyLinkedList<AbstractTask> list = new DoublyLinkedList<>();

    @Override
    public List<AbstractTask> getHistory() {
        return list.getTasks();
    }

    @Override
    public void add(AbstractTask task) {
        if (list.nodes.size() == HISTORY_SIZE) {
            list.removeNode(list.head);
            list.nodes.remove(list.head.data.getId());
        }
        if (list.nodes.containsKey(task.getId())) {
            list.removeNode(list.nodes.get(task.getId()));
            list.nodes.remove(task.getId());
        }
        list.linkLast(task);
    }

    @Override
    public void remove(int id) {
        if (list.nodes.containsKey(id)) {
            list.removeNode(list.nodes.get(id));
            list.nodes.remove(id);
        }
    }

    public class DoublyLinkedList<E> {
        private Map<Integer, Node<E>> nodes = new HashMap<>();
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

        private void removeNode(Node<E> node) {
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
}


package service;

import model.AbstractTask;
import model.DoublyLinkedList;

import java.util.List;

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
}


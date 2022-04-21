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

    public static String toString(HistoryManager manager) {
        String text = "";
        for (AbstractTask task : manager.getHistory()) {
            if (!text.isBlank())
                text = text + "," + task.getId();
            else
                text = text + task.getId();
        }
        return text;
    }

    @Override
    public void add(AbstractTask task) {
        if (list.getNodes().size() == HISTORY_SIZE) {
            list.removeNode(list.getHead());
        }
        if (list.getNodes().containsKey(task.getId())) {
            list.removeNode(list.getNodes().get(task.getId()));
        }
        list.linkLast(task);
    }

    @Override
    public void remove(int id) {
        if (list.getNodes().containsKey(id)) {
            list.removeNode(list.getNodes().get(id));
        }
    }
}


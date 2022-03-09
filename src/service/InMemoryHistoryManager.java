package service;

import model.AbstractTask;
import service.HistoryManager;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int HISTORY_SIZE = 10;

    private List<AbstractTask> tasksHistory = new ArrayList<>();

    @Override
    public void add(AbstractTask task) {
        tasksHistory.add(task);
    }

    @Override
    public List<AbstractTask> getHistory() {
        return tasksHistory;
    }
}
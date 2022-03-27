package service;

import model.AbstractTask;

import java.util.List;

public interface HistoryManager {
    List<AbstractTask> getHistory();

    void add(AbstractTask task);

    void remove(int id);
}

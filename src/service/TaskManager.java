package service;

import model.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TaskManager {
    void createEpic(Epic epic);

    void createSubtask(Subtask subtask);

    void createTask(Task task);

    void removeTask(int taskId);

    void removeEpic(int epicId);

    void removeSubtask(int subtaskId);

    void removeAll();

    Map<Integer, Epic> getEpics();

    Map<Integer, Subtask> getSubtasks();

    Map<Integer, Task> getTasks();

    Task getTask(int id);

    Epic getEpic(int id);

    Subtask getSubtask(int id);

    List<AbstractTask> getSubtasksOfEpic(int epicId);

    Set<AbstractTask> getPrioritizedTasks();

    void updateStatusTask(int taskNumber, Status status);

    void updateStatusSubtask(int subtaskId, Status status);

    List<AbstractTask> getNewHistory();
}

import java.util.List;

public interface TaskManager {
    void createEpic(Epic epic);

    void createSubtask(Subtask subtask);

    void createTask(Task task);

    void removeTask(int taskId);

    void removeEpic(int epicId);

    void removeSubtask(int subtaskId);

    void removeAll();

    Task getTask(int id);

    Epic getEpic(int id);

    Subtask getSubtask(int id);

    List<StandardTask> getHistory();

    void printAllTasksAndEpicsAndSubtasks();

    void updateStatusTask(int taskNumber, Status status);

    void updateStatusSubtask(int subtaskId, Status status);

}

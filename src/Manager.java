import java.util.HashMap;

public class Manager {
    private int idCounter = 0;
    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, Subtask> subtasks = new HashMap<>();

    void createEpic(Epic epic) {
        idCounter += 1;
        epic.number = idCounter;
        epics.put(epic.number, epic);
    }

    void createSubtask(Subtask subtask, int epicNumber) {
        idCounter += 1;
        subtask.number = idCounter;
        subtasks.put(subtask.number, subtask);
        subtask.epicId = epicNumber;
    }

    void createTask(Task task) {
        idCounter += 1;
        task.number = idCounter;
        tasks.put(task.number, task);
    }

    void removeTask(int taskId) {
        tasks.remove(taskId);
    }

    void removeEpic(int epicId) {
        epics.remove(epicId);
    }

    void removeSubtask(int subtaskId) {
        subtasks.remove(subtaskId);
    }

    void removeAll() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }

    void getTask(int taskId) {
        tasks.get(taskId);
    }

    void getEpic(int epicId) {
        epics.get(epicId);
    }

    void getSubtask(int subtaskId) {
        subtasks.get(subtaskId);
    }

    void getAllTask() {
        for (Integer taskId : tasks.keySet()) {
            System.out.println(taskId + " " + tasks.get(taskId) + " " + tasks.get(taskId).status);
        }
    }

    void getAllEpic() {
        for (Integer epicId : epics.keySet()) {
            System.out.println(epicId + " " + epics.get(epicId).getName() + " " + epics.get(epicId).status);
            System.out.println(getSubtasksOfEpic(epicId));
        }
    }

    String getSubtasksOfEpic(int epicId) {
        String listSubtasksOfEpic = "";
        for (Integer idSubtask : subtasks.keySet()) {
            if (subtasks.get(idSubtask).epicId == epicId) {
                listSubtasksOfEpic += idSubtask + " " + subtasks.get(idSubtask).getName() + " " + subtasks.get(idSubtask).status + ". ";
            }
        }
        return listSubtasksOfEpic;
    }

    void getAll() {
        getAllTask();
        getAllEpic();
    }

    void updateStatusTask(int taskNumber, String status) {
        tasks.get(taskNumber).status = status;
    }

    void updateStatusSubtask(int subtaskId, String status) {
        subtasks.get(subtaskId).status = status;
        int epicSubtaskCounter = 0;
        int epicSubtaskDone = 0;

        for (Subtask subtask : subtasks.values()) {
            if (subtask.epicId == subtasks.get(subtaskId).epicId) {
                epicSubtaskCounter += 1;
                if (subtask.status.equals("DONE")) {
                    epicSubtaskDone += 1;
                }
            }
        }
        if (epicSubtaskDone == epicSubtaskCounter) {
            epics.get(subtasks.get(subtaskId).epicId).status = "DONE";
        } else if ((epicSubtaskDone < epicSubtaskCounter) && (epicSubtaskDone > 0)) {
            epics.get(subtasks.get(subtaskId).epicId).status = "IN_PROGRESS";
        }
    }
}




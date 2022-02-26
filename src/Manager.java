import java.util.HashMap;

public class Manager {
    private int idCounter = 0;
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public void createEpic(Epic epic) {
        idCounter += 1;
        epic.id = idCounter;
        epics.put(epic.id, epic);
    }

    public void createSubtask(Subtask subtask, int epicNumber) {
        idCounter += 1;
        subtask.id = idCounter;
        subtasks.put(subtask.id, subtask);
        subtask.epicId = epicNumber;
    }

    public void createTask(Task task) {
        idCounter += 1;
        task.id = idCounter;
        tasks.put(task.id, task);
    }

    public void removeTask(int taskId) {
        tasks.remove(taskId);
    }

    public void removeEpic(int epicId) {
        epics.remove(epicId);
    }

    public void removeSubtask(int subtaskId) {
        subtasks.remove(subtaskId);
    }

    public void removeAll() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    private void printAllTask() {
        for (Integer taskId : tasks.keySet()) {
            System.out.println(taskId + " " + tasks.get(taskId) + " " + tasks.get(taskId).status);
        }
    }

    private void printAllEpic() {
        for (Integer epicId : epics.keySet()) {
            System.out.println(epicId + " " + epics.get(epicId).getName() + " " + epics.get(epicId).status);
            System.out.println(getSubtasksOfEpic(epicId));
        }
    }

    public String getSubtasksOfEpic(int epicId) {
        String listSubtasksOfEpic = "";
        for (Integer idSubtask : subtasks.keySet()) {
            if (subtasks.get(idSubtask).epicId == epicId) {
                listSubtasksOfEpic += idSubtask + " " + subtasks.get(idSubtask).getName() + " " + subtasks.get(idSubtask).status + ". ";
            }
        }
        return listSubtasksOfEpic;
    }

    public void getAll() {
        printAllTask();
        printAllEpic();
    }

    public void updateStatusTask(int taskNumber, Task.Status status) {
        tasks.get(taskNumber).status = status;
    }

    public void updateStatusSubtask(int subtaskId, Task.Status status) {
        subtasks.get(subtaskId).status = status;
        int epicSubtaskCounter = 0;
        int epicSubtaskDone = 0;

        for (Subtask subtask : subtasks.values()) {
            if (subtask.epicId == subtasks.get(subtaskId).epicId) {
                epicSubtaskCounter += 1;
                if (subtask.status.equals(Task.Status.DONE)) {
                    epicSubtaskDone += 1;
                }
            }
        }
        if (epicSubtaskDone == epicSubtaskCounter) {
            epics.get(subtasks.get(subtaskId).epicId).status = Task.Status.DONE;
        } else if ((epicSubtaskDone < epicSubtaskCounter) && (epicSubtaskDone > 0)) {
            epics.get(subtasks.get(subtaskId).epicId).status = Task.Status.IN_PROGRESS;
        }
    }
}




import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Manager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public void createEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void createSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }

    public void createTask(Task task) {
        tasks.put(task.getId(), task);
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
            System.out.println(taskId + " " + tasks.get(taskId) + " " + tasks.get(taskId).getStatus());
        }
    }

    private void printAllEpic() {
        for (Integer epicId : epics.keySet()) {
            System.out.println(epicId + " " + epics.get(epicId).getName() + " " + epics.get(epicId).getStatus());
            for (Task subtask : getSubtasksOfEpic(epicId)) {
                System.out.println(subtask.getId() + " " + subtask.getName() + " " + subtask.getStatus());
            }
        }
    }

    public List<Task> getSubtasksOfEpic(int epicId) {
        List<Task> subtasksOfEpic = new ArrayList<>();
        for (Integer idSubtask : subtasks.keySet()) {
            if (subtasks.get(idSubtask).getEpicId() == epicId) {
                subtasksOfEpic.add(subtasks.get(idSubtask));
            }
        }
        return subtasksOfEpic;
    }

    public void getAll() {
        printAllTask();
        printAllEpic();
    }

    public void updateStatusTask(int taskNumber, Status status) {
        tasks.get(taskNumber).setStatus(status);
    }

    public void updateStatusSubtask(int subtaskId, Status status) {
        subtasks.get(subtaskId).setStatus(status);
        int epicSubtaskCounter = 0;
        int epicSubtaskDone = 0;

        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == subtasks.get(subtaskId).getEpicId()) {
                epicSubtaskCounter += 1;
                if (subtask.getStatus().equals(Status.DONE)) {
                    epicSubtaskDone += 1;
                }
            }
        }
        if (epicSubtaskDone == epicSubtaskCounter) {
            epics.get(subtasks.get(subtaskId).getEpicId()).setStatus(Status.DONE);
        } else if ((epicSubtaskDone < epicSubtaskCounter) && (epicSubtaskDone > 0)) {
            epics.get(subtasks.get(subtaskId).getEpicId()).setStatus(Status.IN_PROGRESS);
        }
    }
}




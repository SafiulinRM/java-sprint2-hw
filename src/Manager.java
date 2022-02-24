import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    int idCounter = 0;
    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private int idCounterEpic;

    void createEpic(Epic epic) {
        idCounter += 1;
        epic.number = idCounter;
        epics.put(epic.number, epic);
    }

    void createSubtask(Subtask subtask, int epicNumber) {
        idCounter += 1;
        subtask.number = idCounter;
        subtasks.put(subtask.number, subtask);
        subtask.idEpic = epicNumber;
    }

    void createTask(Task task) {
        idCounter += 1;
        task.number = idCounter;
        tasks.put(task.number, task);
    }

    void removeTask(int numberIdTask) {
        tasks.remove(numberIdTask);
    }

    void removeEpic(int numberIdEpic) {
        epics.remove(numberIdEpic);
    }

    void removeSubtask(int numberIdSubtask) {
        subtasks.remove(numberIdSubtask);
    }

    void removeAll() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }

    void getTask(int numberIdTask) {
        tasks.get(numberIdTask);
    }

    void getEpic(int numberIdEpic) {
        epics.get(numberIdEpic);
    }

    void getSubtask(int numberIdSubtask) {
        subtasks.get(numberIdSubtask);
    }

    void getAllTask() {
        for (Integer idTask : tasks.keySet()) {
            System.out.println(idTask + " " + tasks.get(idTask) + " " + tasks.get(idTask).status);
        }
    }

    void getAllEpic() {
        for (Integer idEpic : epics.keySet()) {
            System.out.println(idEpic + " " + epics.get(idEpic).epicName + " " + epics.get(idEpic).status);
            getSubtasksOfEpic(idEpic);
        }
    }

    void getSubtasksOfEpic(int idEpic) {
        for (Integer idSubtask : subtasks.keySet()) {
            if (subtasks.get(idSubtask).idEpic == idEpic) {
                System.out.println(idSubtask + " " + subtasks.get(idSubtask).subtaskName + " " + subtasks.get(idSubtask).status);
            }
        }
    }

    void q() {
        System.out.println(subtasks.get(2).idEpic);
    }

    void getAll() {
        getAllTask();
        getAllEpic();
    }

    void updateStatusTask(int taskNumber, String status) {
        tasks.get(taskNumber).status = status;
    }

    void updateStatusSubtask(int subtaskNumber, String status) {
        subtasks.get(subtaskNumber).status = status;
        int sizeSubtaskEpic = 0;
        int sizeSubtaskDONE = 0;

        for (Subtask subtask1 : subtasks.values()) {
            if (subtask1.idEpic == subtasks.get(subtaskNumber).idEpic) {
                sizeSubtaskEpic += 1;
                if (subtask1.status.equals("DONE")) {
                    sizeSubtaskDONE += 1;
                }
            }
        }
        if (sizeSubtaskDONE == sizeSubtaskEpic) {
            epics.get(subtasks.get(subtaskNumber).idEpic).status = "DONE";
        } else if ((sizeSubtaskDONE < sizeSubtaskEpic) && (sizeSubtaskDONE > 0)) {
            epics.get(subtasks.get(subtaskNumber).idEpic).status = "IN_PROGRESS";
        }
    }
}




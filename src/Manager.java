import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    int numberID = 0;
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    ArrayList<Subtask> subtasks;
    HashMap<Epic, ArrayList<Subtask>> subtasksAndEpics = new HashMap<>();
    private int num;

    void createSubtaskAndEpics(Epic epic, Subtask subtask1, Subtask subtask2) {
        numberID += 1;
        epic.number = numberID;
        epics.put(epic.number, epic);
        subtasks = new ArrayList<>();
        numberID += 1;
        subtask1.number = numberID;
        if (subtask2 != null) {
            numberID += 1;
            subtask2.number = numberID;
        }
        subtasks.add(subtask1);
        subtasks.add(subtask2);
        subtasksAndEpics.put(epic, subtasks);
    }

    void createTask(int number, Task task) {
        numberID += 1;
        task.number = numberID;
        tasks.put(number, task);
    }

    void removeTask(int number) {
        tasks.remove(number);
    }

    void removeEpic(int num) {
        this.num = num;
        subtasksAndEpics.remove(epics.get(num));
        epics.remove(num);
    }

    void removeSubtask(int number) {
        for (ArrayList<Subtask> subtasks : subtasksAndEpics.values()) {
            for (Subtask subtask : subtasks) {
                if (subtask.number == number) {
                    subtasks.remove(subtask);
                }
            }
        }

    }

    void removeAll() {
        tasks.clear();
        epics.clear();
        subtasksAndEpics.clear();
    }

    void getTaskOrEpicOrSubtask(int number) {
        if (tasks.containsKey(number)) {
            System.out.println(tasks.get(number).taskName);
        } else if (epics.containsKey(number)) {
            System.out.println(epics.get(number).epicName);
        } else {
            for (ArrayList<Subtask> subtasks : subtasksAndEpics.values()) {
                for (Subtask subtask : subtasks) {
                    if (subtask.number == number) {
                        System.out.println(subtask.subtaskName);
                    }
                }
            }
        }
    }

    void getAllSubtaskInEpic(Epic epic) {
        for (Subtask subtask : subtasksAndEpics.get(epic)) {
            if (subtask != null) {
                System.out.println(subtask.number + " " + subtask.subtaskName + " (Подзадача) " + subtask.status);
            }
        }
    }

    void getAll() {
        for (Integer task : tasks.keySet()) {
            System.out.println(tasks.get(task).number + " " + tasks.get(task).taskName + " (Простая задача) " + tasks.get(task).status);
        }
        for (Epic epic : subtasksAndEpics.keySet()) {
            System.out.println(epic.number + " " + epic.epicName + " (Большая задача) " + epic.status);
            for (Subtask subtask : subtasksAndEpics.get(epic)) {
                if (subtask != null) {
                    System.out.println(subtask.number + " " + subtask.subtaskName + " (Подзадача) " + subtask.status);

                }
            }
        }
    }

    void updateStatusTask(Task task, String status) {
        task.status = status;
    }

    void updateStatusSubtask(Subtask subtask, String status) {
        subtask.status = status;
        for (Epic epic : subtasksAndEpics.keySet()) {
            int size = subtasksAndEpics.get(epic).size();
            for (Subtask sub : subtasksAndEpics.get(epic)) {
                if (sub == null) {
                    size -= 1;
                }
                if ((sub != null) && (sub.status.equals("DONE"))) {
                    epic.status = "IN_PROGRESS";
                    size -= 1;
                }
                if (size == 0) {
                    epic.status = "DONE";
                }
            }
        }
    }
}




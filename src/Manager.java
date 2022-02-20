import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    ArrayList<Subtask> subtasks;
    HashMap<Epic, ArrayList<Subtask>> subtasksAndEpics = new HashMap<>();

    void createEpic(int number, Epic epic) {
        epics.put(number, epic);
    }

    void createSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    void createSubtaskAndEpics(Epic epic, ArrayList<Subtask> subtasks) {
        subtasksAndEpics.put(epic, subtasks);
    }
}

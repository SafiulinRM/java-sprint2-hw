import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private List<StandardTask> tasksHistory = new ArrayList<>();

    @Override
    public void createEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }

    @Override
    public void createTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void removeTask(int taskId) {
        tasks.remove(taskId);
    }

    @Override
    public void removeEpic(int epicId) {
        epics.remove(epicId);
    }

    @Override
    public void removeSubtask(int subtaskId) {
        subtasks.remove(subtaskId);
    }

    @Override
    public void removeAll() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }

    @Override
    public Task getTask(int id) {
        if (tasksHistory.size() < 10) {
        tasksHistory.add(tasks.get(id));
        }
        else if (tasksHistory.size() == 10) {
            tasksHistory.remove(0);
            tasksHistory.add(tasks.get(id));
        }
        return tasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        if (tasksHistory.size() < 10) {
            tasksHistory.add(epics.get(id));
        }
        else if (tasksHistory.size() == 10) {
            tasksHistory.remove(0);
            tasksHistory.add(epics.get(id));
        }
        return epics.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        if (tasksHistory.size() < 10) {
            tasksHistory.add(subtasks.get(id));
        }
        else if (tasksHistory.size() == 10) {
            tasksHistory.remove(0);
            tasksHistory.add(subtasks.get(id));
        }
        return subtasks.get(id);
    }

    @Override
    public List<StandardTask> getHistory() {
        return tasksHistory;
        }


    public void printHistory() {
        System.out.println("Просмотр истории задач!");
        for (int i = tasksHistory.size() - 1; i > -1; i--) {
            System.out.println(tasksHistory.get(i).getName());
        }
    }

    private void printAllTask() {
        for (Integer taskId : tasks.keySet()) {
            System.out.println(taskId + " " + tasks.get(taskId) + " " + tasks.get(taskId).getStatus());
        }
    }

    private void printAllEpic() {
        for (Integer epicId : epics.keySet()) {
            System.out.println(epicId + " " + epics.get(epicId).getName() + " " + epics.get(epicId).getStatus());
            for (StandardTask subtask : getSubtasksOfEpic(epicId)) {
                System.out.println(subtask.getId() + " " + subtask.getName() + " " + subtask.getStatus());
            }
        }
    }

    private List<StandardTask> getSubtasksOfEpic(int epicId) {
        List<StandardTask> subtasksOfEpic = new ArrayList<>();
        for (Integer idSubtask : subtasks.keySet()) {
            if (subtasks.get(idSubtask).getEpicId() == epicId) {
                subtasksOfEpic.add(subtasks.get(idSubtask));
            }
        }
        return subtasksOfEpic;
    }

    @Override
    public void printAllTasksAndEpicsAndSubtasks() {
        printAllTask();
        printAllEpic();
    }

    @Override
    public void updateStatusTask(int taskNumber, Status status) {
        tasks.get(taskNumber).setStatus(status);
    }

    @Override
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




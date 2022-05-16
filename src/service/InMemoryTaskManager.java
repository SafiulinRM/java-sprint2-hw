package service;

import model.AbstractTask;
import model.Epic;
import model.Subtask;
import model.Task;
import model.Status;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    protected static HashMap<Integer, Task> tasks = new HashMap<>();
    protected static HashMap<Integer, Epic> epics = new HashMap<>();
    protected static HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected static HistoryManager historyManager = Managers.getDefaultHistory();
    protected Set<AbstractTask> prioritizedTasks = new TreeSet<>();

    @Override
    public Set<AbstractTask> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    @Override
    public Map<Integer, Epic> getEpics() {
        return epics;
    }

    @Override
    public Map<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public void createEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        boolean intersection = true;
        for (AbstractTask task : getPrioritizedTasks()) {
            if (task.getStartTime().equals(subtask.getStartTime())) {
                intersection = false;
                break;
            }
        }
        if (intersection) {
            subtasks.put(subtask.getId(), subtask);
            epics.get(subtask.getEpicId()).setDuration(subtask.getDuration());
            epics.get(subtask.getEpicId()).setEndTime(subtask.getEndTime());
            if (epics.get(subtask.getEpicId()).getStartTime() == null) {
                epics.get(subtask.getEpicId()).setStartTime(subtask.getStartTime());
            }
            prioritizedTasks.add(subtask);
            updateStatusSubtask(subtask.getId(), subtask.getStatus());
        } else {
            System.out.println("Пересечение времени");
        }
    }

    @Override
    public void createTask(Task task) {
        boolean intersection = true;
        for (AbstractTask t : getPrioritizedTasks()) {
            if (t.getStartTime().equals(task.getStartTime())) {
                intersection = false;
                break;
            }
        }
        if (intersection) {
            tasks.put(task.getId(), task);
            prioritizedTasks.add(task);
        } else {
            System.out.println("Пересечение времени");
        }
    }

    @Override
    public void removeTask(int taskId) {
        tasks.remove(taskId);
        historyManager.remove(taskId);
    }

    @Override
    public void removeEpic(int epicId) {
        epics.remove(epicId);
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == epicId) {
                historyManager.remove(subtask.getId());
            }
        }
        historyManager.remove(epicId);
    }

    @Override
    public void removeSubtask(int subtaskId) {
        if (subtasks.containsKey(subtaskId)) {
            subtasks.remove(subtaskId);
            historyManager.remove(subtaskId);
        }
    }

    @Override
    public void removeAll() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
        historyManager.removeAll();
    }

    @Override
    public Task getTask(int id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public List<AbstractTask> getSubtasksOfEpic(int epicId) {
        List<AbstractTask> subtasksOfEpic = new ArrayList<>();
        for (Integer idSubtask : subtasks.keySet()) {
            if (subtasks.get(idSubtask).getEpicId() == epicId) {
                subtasksOfEpic.add(subtasks.get(idSubtask));
            }
        }
        return subtasksOfEpic;
    }

    @Override
    public void updateStatusTask(int taskNumber, Status status) {
        prioritizedTasks.remove(tasks.get(taskNumber));
        tasks.get(taskNumber).setStatus(status);
        prioritizedTasks.add(tasks.get(taskNumber));
    }

    @Override
    public void updateStatusSubtask(int subtaskId, Status status) {
        prioritizedTasks.remove(subtasks.get(subtaskId));
        subtasks.get(subtaskId).setStatus(status);
        prioritizedTasks.add(subtasks.get(subtaskId));
        int epicSubtaskCounter = 0;
        int epicSubtaskDone = 0;
        boolean epicSubtaskInProgress = false;

        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == subtasks.get(subtaskId).getEpicId()) {
                epicSubtaskCounter += 1;
                if (subtask.getStatus().equals(Status.DONE)) {
                    epicSubtaskDone += 1;
                }
                if (subtask.getStatus().equals(Status.IN_PROGRESS)) {
                    epicSubtaskInProgress = true;
                }
            }
        }
        if (epicSubtaskDone == epicSubtaskCounter) {
            epics.get(subtasks.get(subtaskId).getEpicId()).setStatus(Status.DONE);
        } else if (((epicSubtaskDone < epicSubtaskCounter) && (epicSubtaskDone > 0)) || epicSubtaskInProgress) {
            epics.get(subtasks.get(subtaskId).getEpicId()).setStatus(Status.IN_PROGRESS);
        }
    }

    @Override
    public List<AbstractTask> getNewHistory() {
        return historyManager.getHistory();
    }
}




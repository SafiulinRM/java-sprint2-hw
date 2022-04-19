package service;

import model.AbstractTask;
import model.Epic;
import model.Subtask;
import model.Task;
import model.Status;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class InMemoryTaskManager implements TaskManager {
    protected static HashMap<Integer, Task> tasks = new HashMap<>();
    protected static HashMap<Integer, Epic> epics = new HashMap<>();
    protected static HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected static HistoryManager historyManager = Managers.getDefaultHistory();

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

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

    private List<AbstractTask> getSubtasksOfEpic(int epicId) {
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

    public List<AbstractTask> getNewHistory() {
        return historyManager.getHistory();
    }

}




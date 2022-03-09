package service;

import java.util.HashMap;
import java.util.List;

import model.Epic;
import model.Subtask;
import model.Task;
import model.AbstractTask;

public class Printer {
    public void printTasks(HashMap<Integer, Task> tasks) {
        for (Integer taskId : tasks.keySet()) {
            System.out.println(taskId + " " + tasks.get(taskId) + " " + tasks.get(taskId).getStatus());
        }
    }

    public void printEpics(HashMap<Integer, Epic> epics, HashMap<Integer, Subtask> subtasks) {
        for (Integer epicId : epics.keySet()) {
            System.out.println(epicId + " " + epics.get(epicId).getName() + " " + epics.get(epicId).getStatus());
            for (Subtask subtask : subtasks.values()) {
                if (epicId == subtask.getEpicId()) {
                    System.out.println(subtask.getId() + " " + subtask.getName() + " " + subtask.getStatus());
                }
            }
        }
    }

    public void printTasksAndEpicsAndSubtasks(HashMap<Integer, Task> tasks, HashMap<Integer, Epic> epics, HashMap<Integer, Subtask> subtasks) {
        printTasks(tasks);
        printEpics(epics, subtasks);
    }

    public void printHistory(List<AbstractTask> tasksHistory) {
        System.out.println("Просмотр истории задач!");
        for (int i = tasksHistory.size() - 1; i > -1; i--) {
            System.out.println(tasksHistory.get(i).getName());
        }
    }
}

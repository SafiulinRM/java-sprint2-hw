package test.service;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.IdGenerator;
import service.TaskManager;

import java.io.File;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {
    public static final String LIST_WRONG = "Неправильное количество задач в списке";
    public static final String HISTORY_WRONG = "Неправильный список истории";
    public static final String STATUS_WRONG = "Неправильный статус";
    public static final String TASK_NOT_REMOVE = "Задача не удалилась";
    public static final String TASK_NOT_CREATE = "Задача не создалась";
    public IdGenerator generator = new IdGenerator();
    public File file;
    public T manager;


    protected TaskManagerTest(T manager) {
        this.manager = manager;
    }

    @BeforeEach
    public void beforeEach() {
        generator.setCounterId(0);
        manager.removeAll();
    }

    @Test
    void createEpic() {
        Epic epic1 = new Epic(generator.generate(), "epic1", Status.NEW);
        manager.createEpic(epic1);
        assertEquals(epic1, manager.getEpic(1), TASK_NOT_CREATE);

    }

    @Test
    void createSubtask() {
        Epic epic1 = new Epic(generator.generate(), "epic1", Status.NEW);
        manager.createEpic(epic1);
        assertEquals(epic1, manager.getEpic(1), TASK_NOT_CREATE);
        Subtask subtask2 = new Subtask(generator.generate(), "subtask2", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 0, 0), epic1.getId());
        manager.createSubtask(subtask2);
        assertEquals(subtask2, manager.getSubtask(2), TASK_NOT_CREATE);
    }

    @Test
    void createTask() {
        Task task4 = new Task(generator.generate(), "task4", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 0, 0));
        manager.createTask(task4);
    }

    @Test
    void removeTask() {
        Task task4 = new Task(generator.generate(), "task4", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 0, 0));
        manager.createTask(task4);
        assertEquals(task4, manager.getTask(1), TASK_NOT_CREATE);
        manager.removeTask(1);
        assertThrows(NullPointerException.class, () -> manager.getTask(1), TASK_NOT_REMOVE);
    }

    @Test
    void removeEpic() {
        Epic epic1 = new Epic(generator.generate(), "epic1", Status.NEW);
        manager.createEpic(epic1);
        assertEquals(epic1, manager.getEpic(1), TASK_NOT_CREATE);
        manager.removeEpic(1);
        assertThrows(NullPointerException.class, () -> manager.getEpic(1), TASK_NOT_REMOVE);
    }

    @Test
    void removeSubtask() {
        Epic epic1 = new Epic(generator.generate(), "epic1", Status.NEW);
        manager.createEpic(epic1);
        Subtask subtask2 = new Subtask(generator.generate(), "subtask2", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 0, 0), epic1.getId());
        manager.createSubtask(subtask2);
        assertEquals(subtask2, manager.getSubtask(2), TASK_NOT_CREATE);
        manager.removeSubtask(2);
        assertThrows(NullPointerException.class, () -> manager.getSubtask(2), TASK_NOT_REMOVE);
    }

    @Test
    void removeAll() {
        Epic epic1 = new Epic(generator.generate(), "epic1", Status.NEW);
        manager.createEpic(epic1);
        Subtask subtask2 = new Subtask(generator.generate(), "subtask2", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 0, 0), epic1.getId());
        manager.createSubtask(subtask2);
        assertEquals(subtask2, manager.getSubtask(2), TASK_NOT_CREATE);
        Task task4 = new Task(generator.generate(), "task4", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 1, 1));
        manager.createTask(task4);
        assertEquals(task4, manager.getTask(3), TASK_NOT_CREATE);
        manager.removeAll();
        assertThrows(NullPointerException.class, () -> manager.getEpic(1), TASK_NOT_REMOVE);
        assertThrows(NullPointerException.class, () -> manager.getSubtask(2), TASK_NOT_REMOVE);
        assertThrows(NullPointerException.class, () -> manager.getTask(3), TASK_NOT_REMOVE);
    }

    @Test
    void getEpics() {
        assertEquals(0, manager.getEpics().size(), LIST_WRONG);
        Epic epic1 = new Epic(generator.generate(), "epic1", Status.NEW);
        manager.createEpic(epic1);
        assertEquals(1, manager.getEpics().size(), LIST_WRONG);
        assertEquals(epic1, manager.getEpic(1), TASK_NOT_CREATE);
    }

    @Test
    void getSubtasks() {
        assertEquals(0, manager.getSubtasks().size(), TASK_NOT_CREATE);
        Epic epic1 = new Epic(generator.generate(), "epic1", Status.NEW);
        manager.createEpic(epic1);
        Subtask subtask2 = new Subtask(generator.generate(), "subtask2", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 0, 0), epic1.getId());
        manager.createSubtask(subtask2);
        assertEquals(1, manager.getSubtasks().size(), TASK_NOT_CREATE);
        assertEquals(subtask2, manager.getSubtask(2), TASK_NOT_CREATE);
    }

    @Test
    void getTasks() {
        assertEquals(0, manager.getTasks().size(), TASK_NOT_CREATE);
        Task task4 = new Task(generator.generate(), "task4", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 0, 0));
        manager.createTask(task4);
        assertEquals(1, manager.getTasks().size(), TASK_NOT_CREATE);
        assertEquals(task4, manager.getTask(1), TASK_NOT_CREATE);
    }

    @Test
    void getTask() {
        Task task4 = new Task(generator.generate(), "task4", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 0, 0));
        manager.createTask(task4);
        assertEquals(task4, manager.getTask(1), TASK_NOT_CREATE);
    }

    @Test
    void getEpic() {
        Epic epic1 = new Epic(generator.generate(), "epic1", Status.NEW);
        manager.createEpic(epic1);
        assertEquals(epic1, manager.getEpic(1), TASK_NOT_CREATE);
    }

    @Test
    void getSubtask() {
        Epic epic1 = new Epic(generator.generate(), "epic1", Status.NEW);
        manager.createEpic(epic1);
        Subtask subtask2 = new Subtask(generator.generate(), "subtask2", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 0, 0), epic1.getId());
        manager.createSubtask(subtask2);
        assertEquals(subtask2, manager.getSubtask(2), TASK_NOT_CREATE);
    }

    @Test
    void getSubtasksOfEpic() {
        Epic epic1 = new Epic(generator.generate(), "epic1", Status.NEW);
        manager.createEpic(epic1);
        assertEquals(0, manager.getSubtasksOfEpic(1).size(), LIST_WRONG);
        Subtask subtask2 = new Subtask(generator.generate(), "subtask2", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 0, 0), epic1.getId());
        manager.createSubtask(subtask2);
        assertEquals(1, manager.getSubtasksOfEpic(1).size(), LIST_WRONG);
        Subtask subtask3 = new Subtask(generator.generate(), "subtask3", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 1, 1), epic1.getId());
        manager.createSubtask(subtask3);
        assertEquals(2, manager.getSubtasksOfEpic(1).size(), LIST_WRONG);
    }

    @Test
    void getPrioritizedTasks() {
        Epic epic1 = new Epic(generator.generate(), "epic1", Status.NEW);
        manager.createEpic(epic1);
        assertEquals(0, manager.getPrioritizedTasks().size(), LIST_WRONG);
        Subtask subtask2 = new Subtask(generator.generate(), "subtask2", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 0, 0), epic1.getId());
        manager.createSubtask(subtask2);
        assertEquals(1, manager.getPrioritizedTasks().size(), LIST_WRONG);
        Task task4 = new Task(generator.generate(), "task4", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 1, 1));
        manager.createTask(task4);
        assertEquals(2, manager.getPrioritizedTasks().size(), LIST_WRONG);
    }

    @Test
    void updateStatusTask() {
        Task task4 = new Task(generator.generate(), "task4", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 0, 0));
        manager.createTask(task4);
        manager.updateStatusTask(1, Status.DONE);
        assertEquals(Status.DONE, manager.getTask(1).getStatus(), STATUS_WRONG);
    }

    @Test
    void updateStatusSubtask() {
        Epic epic1 = new Epic(generator.generate(), "epic1", Status.NEW);
        manager.createEpic(epic1);
        Subtask subtask2 = new Subtask(generator.generate(), "subtask2", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 0, 0), epic1.getId());
        manager.createSubtask(subtask2);
        manager.updateStatusSubtask(2, Status.IN_PROGRESS);
        assertEquals(Status.IN_PROGRESS, manager.getSubtask(2).getStatus(), STATUS_WRONG);
        assertEquals(Status.IN_PROGRESS, manager.getEpic(1).getStatus(), STATUS_WRONG);
    }

    @Test
    void getNewHistory() {
        assertEquals(0, manager.getNewHistory().size(), HISTORY_WRONG);
        Epic epic1 = new Epic(generator.generate(), "epic1", Status.NEW);
        manager.createEpic(epic1);
        manager.getEpic(1);
        assertEquals(1, manager.getNewHistory().size(), HISTORY_WRONG);
        Subtask subtask2 = new Subtask(generator.generate(), "subtask2", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 0, 0), epic1.getId());
        manager.createSubtask(subtask2);
        assertEquals(subtask2, manager.getSubtask(2), TASK_NOT_CREATE);
        assertEquals(2, manager.getNewHistory().size(), HISTORY_WRONG);
        Subtask subtask3 = new Subtask(generator.generate(), "subtask3", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 1, 1), epic1.getId());
        manager.createSubtask(subtask3);
        assertEquals(subtask3, manager.getSubtask(3), TASK_NOT_CREATE);
        assertEquals(3, manager.getNewHistory().size(), HISTORY_WRONG);
        manager.removeSubtask(2);
        assertThrows(NullPointerException.class, () -> manager.getSubtask(2), TASK_NOT_REMOVE);
        manager.removeSubtask(3);
        assertThrows(NullPointerException.class, () -> manager.getSubtask(3), TASK_NOT_REMOVE);
        assertEquals(1, manager.getNewHistory().size(), HISTORY_WRONG);
    }
}
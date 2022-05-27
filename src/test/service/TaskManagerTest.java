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
    public static final String NAME = "task";
    public static final long DURATION_MS = 100;
    public static final LocalDateTime START_TIME_1 = LocalDateTime.of(2022, 6, 1, 0, 0);
    public static final LocalDateTime START_TIME_2 = LocalDateTime.of(2022, 6, 1, 1, 1);
    public IdGenerator generator = new IdGenerator();
    public File file = new File("save.txt");
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
        Epic epic = new Epic(generator.generate(), NAME, Status.NEW);
        manager.createEpic(epic);
        assertEquals(epic, manager.getEpic(1), TASK_NOT_CREATE);
    }

    @Test
    void createSubtask() {
        Epic epic = new Epic(generator.generate(), NAME, Status.NEW);
        manager.createEpic(epic);
        assertEquals(epic, manager.getEpic(1), TASK_NOT_CREATE);
        Subtask subtask = new Subtask(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_1, epic.getId());
        manager.createSubtask(subtask);
        assertEquals(subtask, manager.getSubtask(2), TASK_NOT_CREATE);
    }

    @Test
    void createTask() {
        Task task = new Task(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_1);
        manager.createTask(task);
    }

    @Test
    void removeTask() {
        Task task = new Task(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_1);
        manager.createTask(task);
        assertEquals(task, manager.getTask(1), TASK_NOT_CREATE);
        manager.removeTask(1);
        assertThrows(NullPointerException.class, () -> manager.getTask(1), TASK_NOT_REMOVE);
    }

    @Test
    void removeEpic() {
        Epic epic = new Epic(generator.generate(), NAME, Status.NEW);
        manager.createEpic(epic);
        assertEquals(epic, manager.getEpic(1), TASK_NOT_CREATE);
        manager.removeEpic(1);
        assertEquals(0, manager.getEpics().size(), TASK_NOT_REMOVE);
    }

    @Test
    void removeSubtask() {
        Epic epic = new Epic(generator.generate(), NAME, Status.NEW);
        manager.createEpic(epic);
        Subtask subtask = new Subtask(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_1, epic.getId());
        manager.createSubtask(subtask);
        assertEquals(subtask, manager.getSubtask(2), TASK_NOT_CREATE);
        manager.removeSubtask(2);
        assertEquals(0, manager.getSubtasks().size(), TASK_NOT_REMOVE);
    }

    @Test
    void removeAll() {
        Epic epic = new Epic(generator.generate(), NAME, Status.NEW);
        manager.createEpic(epic);
        Subtask subtask = new Subtask(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_1, epic.getId());
        manager.createSubtask(subtask);
        assertEquals(subtask, manager.getSubtask(2), TASK_NOT_CREATE);
        Task task = new Task(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_2);
        manager.createTask(task);
        assertEquals(task, manager.getTask(3), TASK_NOT_CREATE);
        manager.removeAll();
        assertEquals(0, manager.getEpics().size(), TASK_NOT_REMOVE);
        assertEquals(0, manager.getSubtasks().size(), TASK_NOT_REMOVE);
        assertThrows(NullPointerException.class, () -> manager.getTask(3), TASK_NOT_REMOVE);
    }

    @Test
    void getEpics() {
        assertEquals(0, manager.getEpics().size(), LIST_WRONG);
        Epic epic = new Epic(generator.generate(), NAME, Status.NEW);
        manager.createEpic(epic);
        assertEquals(1, manager.getEpics().size(), LIST_WRONG);
        assertEquals(epic, manager.getEpic(1), TASK_NOT_CREATE);
    }

    @Test
    void getSubtasks() {
        assertEquals(0, manager.getSubtasks().size(), TASK_NOT_CREATE);
        Epic epic = new Epic(generator.generate(), NAME, Status.NEW);
        manager.createEpic(epic);
        Subtask subtask = new Subtask(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_1, epic.getId());
        manager.createSubtask(subtask);
        assertEquals(1, manager.getSubtasks().size(), TASK_NOT_CREATE);
        assertEquals(subtask, manager.getSubtask(2), TASK_NOT_CREATE);
    }

    @Test
    void getTasks() {
        assertEquals(0, manager.getTasks().size(), TASK_NOT_CREATE);
        Task task = new Task(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_1);
        manager.createTask(task);
        assertEquals(1, manager.getTasks().size(), TASK_NOT_CREATE);
        assertEquals(task, manager.getTask(1), TASK_NOT_CREATE);
    }

    @Test
    void getTask() {
        Task task = new Task(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_1);
        manager.createTask(task);
        assertEquals(task, manager.getTask(1), TASK_NOT_CREATE);
    }

    @Test
    void getEpic() {
        Epic epic = new Epic(generator.generate(), NAME, Status.NEW);
        manager.createEpic(epic);
        assertEquals(epic, manager.getEpic(1), TASK_NOT_CREATE);
    }

    @Test
    void getSubtask() {
        Epic epic = new Epic(generator.generate(), NAME, Status.NEW);
        manager.createEpic(epic);
        Subtask subtask = new Subtask(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_1, epic.getId());
        manager.createSubtask(subtask);
        assertEquals(subtask, manager.getSubtask(2), TASK_NOT_CREATE);
    }

    @Test
    void getSubtasksOfEpic() {
        Epic epic = new Epic(generator.generate(), NAME, Status.NEW);
        manager.createEpic(epic);
        assertEquals(0, manager.getSubtasksOfEpic(1).size(), LIST_WRONG);
        Subtask subtask2 = new Subtask(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_1, epic.getId());
        manager.createSubtask(subtask2);
        assertEquals(1, manager.getSubtasksOfEpic(1).size(), LIST_WRONG);
        Subtask subtask3 = new Subtask(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_2, epic.getId());
        manager.createSubtask(subtask3);
        assertEquals(2, manager.getSubtasksOfEpic(1).size(), LIST_WRONG);
    }

    @Test
    void getPrioritizedTasks() {
        Epic epic = new Epic(generator.generate(), NAME, Status.NEW);
        manager.createEpic(epic);
        assertEquals(0, manager.getPrioritizedTasks().size(), LIST_WRONG);
        Subtask subtask = new Subtask(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_1, epic.getId());
        manager.createSubtask(subtask);
        assertEquals(1, manager.getPrioritizedTasks().size(), LIST_WRONG);
        Task task = new Task(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_2);
        manager.createTask(task);
        assertEquals(2, manager.getPrioritizedTasks().size(), LIST_WRONG);
    }

    @Test
    void updateStatusTask() {
        Task task = new Task(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_1);
        manager.createTask(task);
        manager.updateStatusTask(1, Status.DONE);
        assertEquals(Status.DONE, manager.getTask(1).getStatus(), STATUS_WRONG);
    }

    @Test
    void updateStatusSubtask() {
        Epic epic = new Epic(generator.generate(), NAME, Status.NEW);
        manager.createEpic(epic);
        Subtask subtask = new Subtask(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_1, epic.getId());
        manager.createSubtask(subtask);
        manager.updateStatusSubtask(2, Status.IN_PROGRESS);
        assertEquals(Status.IN_PROGRESS, manager.getSubtask(2).getStatus(), STATUS_WRONG);
        assertEquals(Status.IN_PROGRESS, manager.getEpic(1).getStatus(), STATUS_WRONG);
    }

    @Test
    void getNewHistory() {
        assertEquals(0, manager.getNewHistory().size(), HISTORY_WRONG);
        Epic epic = new Epic(generator.generate(), NAME, Status.NEW);
        manager.createEpic(epic);
        manager.getEpic(1);
        assertEquals(1, manager.getNewHistory().size(), HISTORY_WRONG);
        Subtask subtask2 = new Subtask(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_1, epic.getId());
        manager.createSubtask(subtask2);
        assertEquals(subtask2, manager.getSubtask(2), TASK_NOT_CREATE);
        assertEquals(2, manager.getNewHistory().size(), HISTORY_WRONG);
        Subtask subtask3 = new Subtask(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_2, epic.getId());
        manager.createSubtask(subtask3);
        assertEquals(subtask3, manager.getSubtask(3), TASK_NOT_CREATE);
        assertEquals(3, manager.getNewHistory().size(), HISTORY_WRONG);
        manager.removeSubtask(2);
        manager.removeSubtask(3);
        assertEquals(1, manager.getNewHistory().size(), HISTORY_WRONG);
    }
}
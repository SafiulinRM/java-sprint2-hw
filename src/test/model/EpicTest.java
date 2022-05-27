package test.model;

import model.Epic;
import model.Status;
import model.Subtask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.FileBackedTasksManager;
import service.IdGenerator;

import java.io.File;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EpicTest {
    public static final String TYPE = "task";
    public static final String LIST_TASKS_WRONG = "Неправильное количество задач в списке";
    public static final String STATUS_OF_EPIC_WRONG = "Неправильный статус у эпика";
    public static final long DURATION = 100;
    public static final LocalDateTime START_TIME_ONE = LocalDateTime.of(2022, 6, 1, 0, 0);
    public static final LocalDateTime START_TIME_TWO = LocalDateTime.of(2022, 6, 2, 0, 0);
    private String fileName = "save.txt";
    private IdGenerator generator = new IdGenerator();
    private FileBackedTasksManager manager = new FileBackedTasksManager(fileName);

    @BeforeEach
    public void beforeEach() {
        generator.setCounterId(0);
        manager.removeAll();
    }

    @Test
    public void shouldCalculateStatusNewInEmptyListOfSubtasks() {
        Epic epic = new Epic(generator.generate(), TYPE, Status.NEW);
        manager.createEpic(epic);
        assertEquals(0, manager.getSubtasks().size(), LIST_TASKS_WRONG);
        assertEquals(Status.NEW, epic.getStatus(), STATUS_OF_EPIC_WRONG);
    }

    @Test
    public void shouldCalculateStatusNewAllSubtasksNew() {
        Epic epic = new Epic(generator.generate(), TYPE, Status.NEW);
        manager.createEpic(epic);
        manager.createSubtask(new Subtask(generator.generate(), TYPE, Status.NEW, DURATION, START_TIME_ONE, epic.getId()));
        manager.createSubtask(new Subtask(generator.generate(), TYPE, Status.NEW, DURATION, START_TIME_TWO, epic.getId()));
        assertEquals(2, manager.getSubtasks().size(), LIST_TASKS_WRONG);
        assertEquals(Status.NEW, epic.getStatus(), STATUS_OF_EPIC_WRONG);
    }

    @Test
    public void shouldCalculateStatusDoneAllSubtasksDone() {
        Epic epic = new Epic(generator.generate(), TYPE, Status.NEW);
        manager.createEpic(epic);
        manager.createSubtask(new Subtask(generator.generate(), TYPE, Status.DONE, DURATION, START_TIME_ONE, epic.getId()));
        manager.createSubtask(new Subtask(generator.generate(), TYPE, Status.DONE, DURATION, START_TIME_TWO, epic.getId()));
        assertEquals(2, manager.getSubtasks().size(), LIST_TASKS_WRONG);
        assertEquals(Status.DONE, epic.getStatus(), STATUS_OF_EPIC_WRONG);
    }

    @Test
    public void shouldCalculateStatusInProgressSubtasksNewAndDone() {
        Epic epic = new Epic(generator.generate(), TYPE, Status.NEW);
        manager.createEpic(epic);
        manager.createSubtask(new Subtask(generator.generate(), TYPE, Status.NEW, DURATION, START_TIME_ONE, epic.getId()));
        manager.createSubtask(new Subtask(generator.generate(), TYPE, Status.DONE, DURATION, START_TIME_TWO, epic.getId()));
        assertEquals(2, manager.getSubtasks().size(), LIST_TASKS_WRONG);
        assertEquals(Status.IN_PROGRESS, epic.getStatus(), STATUS_OF_EPIC_WRONG);
    }

    @Test
    public void shouldCalculateStatusInProgressSubtasksInProgress() {
        Epic epic = new Epic(generator.generate(), TYPE, Status.NEW);
        manager.createEpic(epic);
        manager.createSubtask(new Subtask(generator.generate(), TYPE, Status.IN_PROGRESS, DURATION, START_TIME_ONE, epic.getId()));
        manager.createSubtask(new Subtask(generator.generate(), TYPE, Status.IN_PROGRESS, DURATION, START_TIME_TWO, epic.getId()));
        assertEquals(2, manager.getSubtasks().size(), LIST_TASKS_WRONG);
        assertEquals(Status.IN_PROGRESS, epic.getStatus(), STATUS_OF_EPIC_WRONG);
    }
}
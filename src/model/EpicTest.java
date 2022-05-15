package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.FileBackedTasksManager;
import service.IdGenerator;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    IdGenerator generator = new IdGenerator();
    FileBackedTasksManager manager = new FileBackedTasksManager();
    Epic epic = new Epic(generator.generate(), "epic", Status.NEW);

    @BeforeEach
    public void beforeEach() {
        generator.setCounterId(0);
        manager.removeAll();
        epic = new Epic(generator.generate(), "epic", Status.NEW);
        manager.createEpic(epic);
    }

    @Test
    public void shouldCalculateStatusNewInEmptyListOfSubtasks() {
        assertEquals(0, manager.getSubtasks().size(), "Неверное количество задач.");
        assertEquals(Status.NEW, epic.getStatus(), "Неверное количество задач.");
    }

    @Test
    public void shouldCalculateStatusNewAllSubtasksNew() {
        manager.createSubtask(new Subtask(generator.generate(), "Накопить денег", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 0, 0), epic.getId()));
        manager.createSubtask(new Subtask(generator.generate(), "Пойти в автосалон", Status.NEW, 50,
                LocalDateTime.of(2022, 6, 2, 0, 0), epic.getId()));
        assertEquals(2, manager.getSubtasks().size());
        assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    public void shouldCalculateStatusDoneAllSubtasksDone() {
        manager.createSubtask(new Subtask(generator.generate(), "Накопить денег", Status.DONE, 100,
                LocalDateTime.of(2022, 6, 1, 0, 0), epic.getId()));
        manager.createSubtask(new Subtask(generator.generate(), "Пойти в автосалон", Status.DONE, 50,
                LocalDateTime.of(2022, 6, 2, 0, 0), epic.getId()));
        assertEquals(2, manager.getSubtasks().size());
        assertEquals(Status.DONE, epic.getStatus());
    }

    @Test
    public void shouldCalculateStatusInProgressSubtasksNewAndDone() {
        manager.createSubtask(new Subtask(generator.generate(), "Накопить денег", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 0, 0), epic.getId()));
        manager.createSubtask(new Subtask(generator.generate(), "Пойти в автосалон", Status.DONE, 50,
                LocalDateTime.of(2022, 6, 2, 0, 0), epic.getId()));
        assertEquals(2, manager.getSubtasks().size());
        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    public void shouldCalculateStatusInProgressSubtasksInProgress() {
        manager.createSubtask(new Subtask(generator.generate(), "Накопить денег", Status.IN_PROGRESS, 100,
                LocalDateTime.of(2022, 6, 1, 0, 0), epic.getId()));
        manager.createSubtask(new Subtask(generator.generate(), "Пойти в автосалон", Status.IN_PROGRESS, 50,
                LocalDateTime.of(2022, 6, 2, 0, 0), epic.getId()));
        assertEquals(2, manager.getSubtasks().size());
        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }
}
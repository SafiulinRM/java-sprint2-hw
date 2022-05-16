package test.service;

import model.Epic;
import model.Status;
import model.Subtask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.FileBackedTasksManager;
import service.IdGenerator;

import java.io.File;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {
    private File file = new File("save.txt");
    private IdGenerator generator = new IdGenerator();
    private FileBackedTasksManager manager = new FileBackedTasksManager(file);
    private Epic epic;

    @BeforeEach
    public void beforeEach() {
        generator.setCounterId(0);
        manager.removeAll();
        epic = new Epic(generator.generate(), "epic", Status.NEW);
        manager.createEpic(epic);
    }

    @Test
    void shouldGetEmptyHistoryIfNotGetTask() {
        manager.createSubtask(new Subtask(generator.generate(), "Накопить денег", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 0, 0), epic.getId()));
        manager.createSubtask(new Subtask(generator.generate(), "Пойти в автосалон", Status.NEW, 50,
                LocalDateTime.of(2022, 6, 2, 0, 0), epic.getId()));
        assertEquals(0, manager.getNewHistory().size(), "Неверное количество задач.");
    }

    @Test
    void shouldGetOneTaskInHistoryIfGetTwoSameTasks() {
        manager.createSubtask(new Subtask(generator.generate(), "Накопить денег", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 0, 0), epic.getId()));
        manager.createSubtask(new Subtask(generator.generate(), "Пойти в автосалон", Status.NEW, 50,
                LocalDateTime.of(2022, 6, 2, 0, 0), epic.getId()));
        manager.getSubtask(2);
        manager.getSubtask(2);
        assertEquals(1, manager.getNewHistory().size(), "Неверное количество задач.");
    }

    @Test
    void shouldFirstTaskIsSubtask3IfRemoveFirstTask() {

        Subtask subtask2 = new Subtask(generator.generate(), "Накопить денег", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 0, 0), epic.getId());
        Subtask subtask3 = new Subtask(generator.generate(), "Пойти в автосалон", Status.NEW, 50,
                LocalDateTime.of(2022, 6, 2, 0, 0), epic.getId());
        manager.createSubtask(subtask2);
        manager.createSubtask(subtask3);
        manager.getSubtask(2);
        manager.getSubtask(3);
        assertEquals(subtask2, manager.getNewHistory().get(0), "Задачи не совпадают.");
        manager.removeSubtask(2);
        assertEquals(subtask3, manager.getNewHistory().get(0), "Задачи не совпадают.");
    }

    @Test
    void shouldFifthTaskIsEpic6IfRemoveFifthTask() {
        Subtask subtask2 = new Subtask(generator.generate(), "Накопить денег", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 0, 0), epic.getId());
        Subtask subtask3 = new Subtask(generator.generate(), "Пойти в автосалон", Status.NEW, 50,
                LocalDateTime.of(2022, 6, 2, 0, 0), epic.getId());
        Epic epic4 = new Epic(generator.generate(), "epic", Status.NEW);
        Subtask subtask5 = new Subtask(generator.generate(), "Накопить денег", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 3, 0, 0), epic4.getId());
        Subtask subtask6 = new Subtask(generator.generate(), "Пойти в автосалон", Status.NEW, 50,
                LocalDateTime.of(2022, 6, 4, 0, 0), epic4.getId());
        manager.createSubtask(subtask2);
        manager.createSubtask(subtask3);
        manager.createEpic(epic4);
        manager.createSubtask(subtask5);
        manager.createSubtask(subtask6);
        manager.getEpic(1);
        manager.getSubtask(2);
        manager.getSubtask(3);
        manager.getEpic(4);
        manager.getSubtask(5);
        manager.getSubtask(6);
        assertEquals(subtask5, manager.getNewHistory().get(4), "Задачи не совпадают.");
        manager.removeSubtask(5);
        assertEquals(subtask6, manager.getNewHistory().get(4), "Задачи не совпадают.");
    }

    @Test
    void shouldEndTaskIsEpic10IfTheListOverflows() {
        Epic epic2 = new Epic(generator.generate(), "epic", Status.NEW);
        Epic epic3 = new Epic(generator.generate(), "epic", Status.NEW);
        Epic epic4 = new Epic(generator.generate(), "epic", Status.NEW);
        Epic epic5 = new Epic(generator.generate(), "epic", Status.NEW);
        Epic epic6 = new Epic(generator.generate(), "epic", Status.NEW);
        Epic epic7 = new Epic(generator.generate(), "epic", Status.NEW);
        Epic epic8 = new Epic(generator.generate(), "epic", Status.NEW);
        Epic epic9 = new Epic(generator.generate(), "epic", Status.NEW);
        Epic epic10 = new Epic(generator.generate(), "epic", Status.NEW);
        Epic epic11 = new Epic(generator.generate(), "epic", Status.NEW);
        manager.createEpic(epic2);
        manager.createEpic(epic3);
        manager.createEpic(epic4);
        manager.createEpic(epic5);
        manager.createEpic(epic6);
        manager.createEpic(epic7);
        manager.createEpic(epic8);
        manager.createEpic(epic9);
        manager.createEpic(epic10);
        manager.createEpic(epic11);
        manager.getEpic(1);
        assertEquals(epic, manager.getNewHistory().get(0), "Задачи не совпадают.");
        manager.getEpic(2);
        manager.getEpic(3);
        manager.getEpic(4);
        manager.getEpic(5);
        manager.getEpic(6);
        manager.getEpic(7);
        manager.getEpic(8);
        manager.getEpic(9);
        manager.getEpic(10);
        assertEquals(epic10, manager.getNewHistory().get(9), "Задачи не совпадают.");
        assertEquals(epic, manager.getNewHistory().get(0), "Задачи не совпадают.");
        manager.getEpic(11);
        assertEquals(epic11, manager.getNewHistory().get(9), "Задачи не совпадают.");
        assertEquals(epic2, manager.getNewHistory().get(0), "Задачи не совпадают.");
        manager.removeEpic(11);
        assertFalse(manager.getNewHistory().contains(11), "Задача не yдалилась.");
    }
}
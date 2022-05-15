package service;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    public File file = new File("C:\\Users\\User\\java-sprint2-hw\\src", "save.txt");

    public FileBackedTasksManagerTest() {
        super(new FileBackedTasksManager());
    }

    @Test
    void saveEmptyManager() {
        boolean isEmpty = true;
        try {
            manager.save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
        try {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            while (br.ready()) {
                String line = br.readLine();
                if (line.length() != 0) {
                    isEmpty = false;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(isEmpty, "Файл не пустой");
    }

    @Test
    void saveOnlyEpicsInManager() {
        epic1 = new Epic(generator.generate(), "epic1", Status.NEW);
        manager.createEpic(epic1);
        manager.getEpic(1);
        epic2 = new Epic(generator.generate(), "epic2", Status.NEW);
        manager.createEpic(epic2);
        manager.getEpic(1);
        FileBackedTasksManager test = FileBackedTasksManagerLoader.loadFromFile(file);
        assertEquals(epic1, test.getEpic(1), TASK_NOT_CREATE);
        assertEquals(epic2, test.getEpic(2), TASK_NOT_CREATE);
        assertEquals(2, test.getNewHistory().size());
    }

    @Test
    void saveEmptyHistory() {
        epic1 = new Epic(generator.generate(), "epic1", Status.NEW);
        manager.createEpic(epic1);
        subtask2 = new Subtask(generator.generate(), "subtask2", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 0, 0), epic1.getId());
        manager.createSubtask(subtask2);
        subtask3 = new Subtask(generator.generate(), "subtask3", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 1, 1), epic1.getId());
        manager.createSubtask(subtask3);
        task4 = new Task(generator.generate(), "task4", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 2, 2));
        manager.createTask(task4);
        FileBackedTasksManager test2 = FileBackedTasksManagerLoader.loadFromFile(file);
        assertEquals(0, test2.getNewHistory().size());
    }

    @Test
    void createHistoryString() {
        assertEquals("", manager.createHistoryString(manager.getNewHistory()), HISTORY_WRONG);
        epic1 = new Epic(generator.generate(), "epic1", Status.NEW);
        manager.createEpic(epic1);
        epic2 = new Epic(generator.generate(), "epic2", Status.NEW);
        manager.createEpic(epic2);
        assertEquals("", manager.createHistoryString(manager.getNewHistory()), HISTORY_WRONG);
        manager.getEpic(1);
        manager.getEpic(2);
        assertEquals("1,2", manager.createHistoryString(manager.getNewHistory()), HISTORY_WRONG);

    }
}
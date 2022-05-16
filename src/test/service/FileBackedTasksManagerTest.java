package test.service;

import model.*;
import org.junit.jupiter.api.Test;
import service.FileBackedTasksManager;
import service.FileBackedTasksManagerLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    private File file = new File("C:\\Users\\User\\java-sprint2-hw\\src", "save.txt");

    public FileBackedTasksManagerTest(File file) {
        super(new FileBackedTasksManager(file));
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
        Epic epic1 = new Epic(generator.generate(), "epic1", Status.NEW);
        manager.createEpic(epic1);
        manager.getEpic(1);
        FileBackedTasksManager testOnlyEpicInHistory = FileBackedTasksManagerLoader.loadFromFile(file);
        assertEquals(epic1, testOnlyEpicInHistory.getEpic(1), TASK_NOT_CREATE);
        assertEquals(1, testOnlyEpicInHistory.getNewHistory().size());
    }

    @Test
    void saveEmptyHistory() {
        Epic epic1 = new Epic(generator.generate(), "epic1", Status.NEW);
        manager.createEpic(epic1);
        Subtask subtask2 = new Subtask(generator.generate(), "subtask2", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 0, 0), epic1.getId());
        manager.createSubtask(subtask2);
        Task task4 = new Task(generator.generate(), "task4", Status.NEW, 100,
                LocalDateTime.of(2022, 6, 1, 2, 2));
        manager.createTask(task4);
        FileBackedTasksManager testEmptyHistory = FileBackedTasksManagerLoader.loadFromFile(file);
        assertEquals(0, testEmptyHistory.getNewHistory().size());
    }

    @Test
    void createHistoryString() {
        assertEquals("", manager.createHistoryString(manager.getNewHistory()), HISTORY_WRONG);
        Epic epic1 = new Epic(generator.generate(), "epic1", Status.NEW);
        manager.createEpic(epic1);
        Epic epic2 = new Epic(generator.generate(), "epic2", Status.NEW);
        manager.createEpic(epic2);
        assertEquals("", manager.createHistoryString(manager.getNewHistory()), HISTORY_WRONG);
        manager.getEpic(1);
        manager.getEpic(2);
        assertEquals("1,2", manager.createHistoryString(manager.getNewHistory()), HISTORY_WRONG);

    }
}
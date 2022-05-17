package test.service;

import model.*;
import org.junit.jupiter.api.Test;
import service.FileBackedTasksManager;
import service.FileBackedTasksManagerLoader;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

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
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.ready()) {
                String line = br.readLine();
                if (line.length() != 0) {
                    isEmpty = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(isEmpty, "Файл не пустой");
    }

    @Test
    void saveOnlyEpicsInManager() {
        Epic epic = new Epic(generator.generate(), NAME, Status.NEW);
        manager.createEpic(epic);
        manager.getEpic(1);
        FileBackedTasksManager testOnlyEpicInHistory = FileBackedTasksManagerLoader.loadFromFile(file);
        assertEquals(epic, testOnlyEpicInHistory.getEpic(1), TASK_NOT_CREATE);
        assertEquals(1, testOnlyEpicInHistory.getNewHistory().size());
    }

    @Test
    void saveEmptyHistory() {
        Epic epic = new Epic(generator.generate(), NAME, Status.NEW);
        manager.createEpic(epic);
        Subtask subtask = new Subtask(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_1, epic.getId());
        manager.createSubtask(subtask);
        Task task = new Task(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_2);
        manager.createTask(task);
        FileBackedTasksManager testEmptyHistory = FileBackedTasksManagerLoader.loadFromFile(file);
        assertEquals(0, testEmptyHistory.getNewHistory().size());
    }

    @Test
    void createHistoryString() {
        assertEquals("", manager.createHistoryString(manager.getNewHistory()), HISTORY_WRONG);
        Epic epic = new Epic(generator.generate(), NAME, Status.NEW);
        manager.createEpic(epic);
        Epic epic2 = new Epic(generator.generate(), NAME, Status.NEW);
        manager.createEpic(epic2);
        assertEquals("", manager.createHistoryString(manager.getNewHistory()), HISTORY_WRONG);
        manager.getEpic(1);
        manager.getEpic(2);
        assertEquals("1,2", manager.createHistoryString(manager.getNewHistory()), HISTORY_WRONG);

    }
}
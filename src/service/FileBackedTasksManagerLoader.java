package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManagerLoader {
    private static final int FIELDS_COUNT = 6;
    private static final int CHARACTERS_PER_LINE = 0;
    private static final String TYPE_SUBTASK = "SUBTASK";
    private static final String TYPE_TASK = "TASK";
    private static final String TYPE_EPIC = "EPIC";
    private static final String TASK_DELIMITER = ",";
    private static final int INDEX_ID = 0;
    private static final int INDEX_NAME = 2;
    private static final int INDEX_STATUS = 3;
    private static final int INDEX_EPIC_ID = 7;
    private static final int INDEX_DURATION = 5;
    private static final int INDEX_START_TIME = 6;

    public static FileBackedTasksManager loadFromFile(String fileName) {
        FileBackedTasksManager fileBackedTasksManagerNew = new FileBackedTasksManager(fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while (br.ready()) {
                String line = br.readLine();
                if (line.length() > FIELDS_COUNT) {
                    if (line.contains(TYPE_SUBTASK))
                        fileBackedTasksManagerNew.createSubtask(fromStringSubtask(line));
                    else if (line.contains(TYPE_EPIC))
                        fileBackedTasksManagerNew.epics.put(fromStringEpic(line).getId(), fromStringEpic(line));
                    else if (line.contains(TYPE_TASK))
                        fileBackedTasksManagerNew.createTask(fromStringTask(line));
                } else if (line.length() > CHARACTERS_PER_LINE) {
                    for (Integer id : (fromString(line))) {
                        if (fileBackedTasksManagerNew.tasks.containsKey(id))
                            fileBackedTasksManagerNew.getTask(id);
                        else if (fileBackedTasksManagerNew.epics.containsKey(id))
                            fileBackedTasksManagerNew.getEpic(id);
                        else if (fileBackedTasksManagerNew.subtasks.containsKey(id))
                            fileBackedTasksManagerNew.getSubtask(id);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileBackedTasksManagerNew;
    }

    static Task fromStringTask(String value) {
        String[] split = value.split(TASK_DELIMITER);
        return new Task(Integer.parseInt(split[INDEX_ID]), split[INDEX_NAME], Status.valueOf(split[INDEX_STATUS]),
                Integer.parseInt(split[INDEX_DURATION]), LocalDateTime.parse(split[INDEX_START_TIME]));
    }

    static Epic fromStringEpic(String value) {
        String[] split = value.split(TASK_DELIMITER);
        return new Epic(Integer.parseInt(split[INDEX_ID]), split[INDEX_NAME], Status.valueOf(split[INDEX_STATUS]));
    }

    static Subtask fromStringSubtask(String value) {
        String[] split = value.split(TASK_DELIMITER);
        return new Subtask(Integer.parseInt(split[INDEX_ID]), split[INDEX_NAME], Status.valueOf(split[INDEX_STATUS]),
                Integer.parseInt(split[INDEX_DURATION]), LocalDateTime.parse(split[INDEX_START_TIME]), Integer.parseInt(split[INDEX_EPIC_ID]));
    }

    static List<Integer> fromString(String value) {
        List<Integer> ids = new ArrayList<>();
        String[] split = value.split(TASK_DELIMITER);
        for (String id : split) {
            ids.add(Integer.parseInt(id));
        }
        return ids;
    }
}

package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManagerLoader {
    private static final int FIELDS_COUNT = 6;
    private static final int CHARACTERS_PER_LINE = 3;

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager test = new FileBackedTasksManager(file);
        try {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            while (br.ready()) {
                String line = br.readLine();
                if (line.length() > FIELDS_COUNT) {
                    if (line.contains("SUBTASK"))
                        test.subtasks.put(fromStringSubtask(line).getId(), fromStringSubtask(line));
                    else if (line.contains("EPIC"))
                        test.epics.put(fromStringEpic(line).getId(), fromStringEpic(line));
                    else if (line.contains("TASK"))
                        test.tasks.put(fromStringTask(line).getId(), fromStringTask(line));
                } else if (line.length() == CHARACTERS_PER_LINE) {
                    for (Integer id : (fromString(line))) {
                        if (test.tasks.containsKey(id))
                            test.getTask(id);
                        else if (test.epics.containsKey(id))
                            test.getEpic(id);
                        else if (test.subtasks.containsKey(id))
                            test.getSubtask(id);
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return test;
    }

    static Task fromStringTask(String value) {
        String[] split = value.split(",");
        return new Task(Integer.parseInt(split[0]), split[2], Status.valueOf(split[3]));
    }

    static Epic fromStringEpic(String value) {
        String[] split = value.split(",");
        return new Epic(Integer.parseInt(split[0]), split[2], Status.valueOf(split[3]));
    }

    static Subtask fromStringSubtask(String value) {
        String[] split = value.split(",");
        return new Subtask(Integer.parseInt(split[0]), split[2], Status.valueOf(split[3]),
                Integer.parseInt(split[5]));
    }

    static List<Integer> fromString(String value) {
        List<Integer> ids = new ArrayList<>();
        String[] split = value.split(",");
        for (String id : split) {
            ids.add(Integer.parseInt(id));
        }
        return ids;
    }
}

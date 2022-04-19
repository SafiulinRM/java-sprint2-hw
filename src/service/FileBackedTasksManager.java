package service;

import model.*;

import java.io.*;

import static service.InMemoryHistoryManager.fromString;

public class FileBackedTasksManager extends InMemoryTaskManager {
    File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public static void main(String[] args) {
        IdGenerator generator = new IdGenerator();
        File file = new File("C:\\Users\\User\\java-sprint2-hw\\src", "save.txt");
        FileBackedTasksManager manager = new FileBackedTasksManager(file);

        Epic buyCar = new Epic(generator.generate(), "Покупка Машины", Status.NEW);
        Subtask saveMoney = new Subtask(generator.generate(), "Накопить денег", Status.NEW, buyCar.getId());
        Subtask goToCarDealership = new Subtask(generator.generate(), "Пойти в автосалон", Status.NEW, buyCar.getId());
        Subtask buyWheels = new Subtask(generator.generate(), "Купить колеса", Status.NEW, buyCar.getId());
        manager.createEpic(buyCar);
        manager.createSubtask(saveMoney);
        manager.createSubtask(goToCarDealership);
        manager.createSubtask(buyWheels);
        Epic moving = new Epic(generator.generate(), "Переезд", Status.NEW);
        manager.createEpic(moving);
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());
        System.out.println(manager.getSubtask(2));
        System.out.println(manager.getSubtask(3));
        System.out.println(manager.getNewHistory());

        FileBackedTasksManager test = loadFromFile(file);
        System.out.println("новый функционал");
        System.out.println(test.getNewHistory());
        System.out.println(test.getEpics());
    }

    static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager test = new FileBackedTasksManager(file);
        try {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            while (br.ready()) {
                String line = br.readLine();
                if (line.length() > 6) {
                    if (line.contains("SUBTASK"))
                        test.subtasks.put(fromStringSubtask(line).getId(), fromStringSubtask(line));
                    else if (line.contains("EPIC"))
                        test.epics.put(fromStringEpic(line).getId(), fromStringEpic(line));
                    else if (line.contains("TASK"))
                        test.tasks.put(fromStringTask(line).getId(), fromStringTask(line));
                } else if (line.length() == 3) {
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

    void save() throws ManagerSaveException {
        try (Writer fileWriter = new FileWriter(file.getName())) {
            for (AbstractTask task : getTasks().values()) {
                fileWriter.write(task.toString() + "\n");
            }
            for (Epic epic : getEpics().values()) {
                fileWriter.write(epic.toString() + "\n");
            }
            for (AbstractTask task : getSubtasks().values()) {
                fileWriter.write(task.toString() + "\n");
            }
            fileWriter.write("\n");
            try {
                if (getNewHistory().size() > 0) {
                    fileWriter.write(InMemoryHistoryManager.toString(historyManager));
                }
            } catch (Exception e) {
            }
        } catch (IOException e) {
            throw new ManagerSaveException();
        }
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeTask(int taskId) {
        super.removeTask(taskId);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeEpic(int epicId) {
        super.removeEpic(epicId);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeSubtask(int subtaskId) {
        super.removeSubtask(subtaskId);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAll() {
        super.removeAll();
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Task getTask(int id) {
        historyManager.add(getTasks().get(id));
        super.getTask(id);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
        return getTasks().get(id);
    }

    @Override
    public Epic getEpic(int id) {
        historyManager.add(getEpics().get(id));
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
        return getEpics().get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        historyManager.add(getSubtasks().get(id));
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }

        return super.getSubtasks().get(id);
    }

    @Override
    public void updateStatusTask(int taskNumber, Status status) {
        super.updateStatusTask(taskNumber, status);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStatusSubtask(int subtaskId, Status status) {
        super.updateStatusSubtask(subtaskId, status);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }
}


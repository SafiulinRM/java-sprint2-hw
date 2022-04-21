package service;

import model.*;

import java.io.*;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File file;
    private static final String LINE_DELIMETER = "\n";
    private static final int TYPE_COLUMN_INDEX = 0;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    private void save() throws ManagerSaveException {
        try (Writer fileWriter = new FileWriter(file.getName())) {
            for (AbstractTask task : getTasks().values()) {
                fileWriter.write(task.toString() + LINE_DELIMETER);
            }
            for (Epic epic : getEpics().values()) {
                fileWriter.write(epic.toString() + LINE_DELIMETER);
            }
            for (AbstractTask task : getSubtasks().values()) {
                fileWriter.write(task.toString() + LINE_DELIMETER);
            }
            fileWriter.write(LINE_DELIMETER);
            try {
                if (getNewHistory().size() > TYPE_COLUMN_INDEX) {
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


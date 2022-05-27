package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;

import java.io.IOException;
import java.net.URI;

public class HTTPTaskManager extends FileBackedTasksManager {
    public static KVTaskClient client;
    URI uri;
    String url;
    String KEY_GREEN = "зеленый";

    public HTTPTaskManager(String url) {
        super(url);
        this.url = url;
        this.uri = URI.create(url);
        client = new KVTaskClient(this.uri);
    }

    public static HTTPTaskManager loadManager() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls()
                .registerTypeAdapter(AbstractTask.class, new AbstractElementAdapter());
        Gson gson = gsonBuilder.create();
        return gson.fromJson(client.load("зеленый"), HTTPTaskManager.class);
    }

    @Override
    public void save() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls()
                .registerTypeAdapter(AbstractTask.class, new AbstractElementAdapter());
        Gson gson = gsonBuilder.create();
        client.put(KEY_GREEN, gson.toJson(this));
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void removeTask(int taskId) {
        super.removeTask(taskId);
        save();
    }

    @Override
    public void removeEpic(int epicId) {
        super.removeEpic(epicId);
        save();
    }

    @Override
    public void removeSubtask(int subtaskId) {
        super.removeSubtask(subtaskId);
        save();
    }

    @Override
    public void removeAll() {
        super.removeAll();
        save();
    }

    @Override
    public Task getTask(int id) {
        super.getTask(id);
        save();
        return getTasks().get(id);
    }

    @Override
    public Epic getEpic(int id) {
        super.getEpic(id);
        save();
        return getEpics().get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        super.getSubtask(id);
        save();
        return getSubtasks().get(id);
    }

    @Override
    public void updateStatusTask(int taskNumber, Status status) {
        super.updateStatusTask(taskNumber, status);
        save();
    }

    @Override
    public void updateStatusSubtask(int subtaskId, Status status) {
        super.updateStatusSubtask(subtaskId, status);
        save();
    }
}

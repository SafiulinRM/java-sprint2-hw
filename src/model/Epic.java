package model;

import service.Status;

public class Epic extends AbstractTask {

    public Epic(int newId, String name, Status status) {
        super(newId, name, status);
    }
}

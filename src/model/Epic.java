package model;

import service.Status;

public class Epic extends AbstractTask {

    public Epic(int id, String name, Status status) {
        super(id, name, status);
    }
}

import model.Epic;
import model.Status;
import model.Subtask;
import service.FileBackedTasksManager;
import service.FileBackedTasksManagerLoader;
import service.IdGenerator;

import java.io.File;

public class Main {
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

        FileBackedTasksManager test = FileBackedTasksManagerLoader.loadFromFile(file);
        System.out.println("новый функционал");
        System.out.println(test.getNewHistory());
        System.out.println(test.getEpics());
    }
}

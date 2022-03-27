import model.Epic;
import model.Subtask;
import service.*;

public class Main {
    public static void main(String[] args) {
        IdGenerator generator = new IdGenerator();
        InMemoryTaskManager manager = new InMemoryTaskManager();
        Printer printer = new Printer();

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
        printer.printTasksAndEpicsAndSubtasks(manager.getTasks(), manager.getEpics(), manager.getSubtasks());
        manager.getSubtask(2);
        manager.getEpic(1);
        manager.getEpic(5);
        manager.getSubtask(2);
        manager.getSubtask(4);
        manager.getEpic(5);
        printer.printHistory(manager.getTasksHistory());
        System.out.println(manager.getNewHistory());
        manager.removeSubtask(2);
        System.out.println(manager.getNewHistory());
        manager.removeEpic(1);
        System.out.println(manager.getNewHistory());
    }
}
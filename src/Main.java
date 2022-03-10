import model.Epic;
import model.Subtask;
import service.IdGenerator;
import service.InMemoryTaskManager;
import service.Printer;
import service.Status;

public class Main {
    public static void main(String[] args) {
        IdGenerator generator = new IdGenerator();
        InMemoryTaskManager manager = new InMemoryTaskManager();
        Printer printer = new Printer();
        Epic buyCar = new Epic(generator.generate(), "Покупка Машины", Status.NEW);
        Subtask saveMoney = new Subtask(generator.generate(), "Накопить денег", Status.NEW, buyCar.getId());
        Subtask goToCarDealership = new Subtask(generator.generate(), "Пойти в автосалон", Status.NEW, buyCar.getId());
        manager.createEpic(buyCar);
        manager.createSubtask(saveMoney);
        manager.createSubtask(goToCarDealership);
        Epic moving = new Epic(generator.generate(), "Переезд", Status.NEW);
        Subtask packBackpack = new Subtask(generator.generate(), "Собрать рюкзак", Status.NEW, moving.getId());
        manager.createEpic(moving);
        manager.createSubtask(packBackpack);
        printer.printTasksAndEpicsAndSubtasks(manager.getTasks(), manager.getEpics(), manager.getSubtasks());
        System.out.println("Проверка нового функционала!");
        System.out.println(manager.getEpic(1).getName());
        System.out.println(manager.getEpic(4).getName());
        System.out.println(manager.getSubtask(2).getName());
        System.out.println(manager.getSubtask(3).getName());
        printer.printHistory(manager.getTasksHistory());
        System.out.println("Продолжение проверки старого функциронала!");
        manager.updateStatusSubtask(3, Status.DONE);
        manager.updateStatusSubtask(5, Status.DONE);
        printer.printTasksAndEpicsAndSubtasks(manager.getTasks(), manager.getEpics(), manager.getSubtasks());
        manager.removeSubtask(3);
        manager.removeEpic(4);
        printer.printTasksAndEpicsAndSubtasks(manager.getTasks(), manager.getEpics(), manager.getSubtasks());
    }
}
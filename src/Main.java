public class Main {
    public static void main(String[] args) {
        TaskManager manager = new InMemoryTaskManager();
        Epic buyCar = new Epic("Покупка Машины", Status.NEW);
        Subtask saveMoney = new Subtask("Накопить денег", Status.NEW, buyCar.getId());
        Subtask goToCarDealership = new Subtask("Пойти в автосалон", Status.NEW, buyCar.getId());
        manager.createEpic(buyCar);
        manager.createSubtask(saveMoney);
        manager.createSubtask(goToCarDealership);
        Epic moving = new Epic("Переезд", Status.NEW);
        Subtask packBackpack = new Subtask("Собрать рюкзак", Status.NEW, moving.getId());
        manager.createEpic(moving);
        manager.createSubtask(packBackpack);
        manager.printAllTasksAndEpicsAndSubtasks();
        System.out.println("Проверка нового функционала!");
        System.out.println(manager.getEpic(1).getName());
        System.out.println(manager.getEpic(4).getName());
        System.out.println(manager.getSubtask(2).getName());
        System.out.println(manager.getSubtask(3).getName());
        manager.getHistory();
        System.out.println("Продолжение проверки старого функциронала!");
        manager.updateStatusSubtask(3, Status.DONE);
        manager.updateStatusSubtask(5, Status.DONE);
        manager.printAllTasksAndEpicsAndSubtasks();
        manager.removeSubtask(3);
        manager.removeEpic(4);
        manager.printAllTasksAndEpicsAndSubtasks();
    }
}
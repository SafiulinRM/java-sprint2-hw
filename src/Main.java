public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
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
        manager.getAll();
        manager.updateStatusSubtask(3, Status.DONE);
        manager.updateStatusSubtask(5, Status.DONE);
        manager.getAll();
        manager.removeSubtask(3);
        manager.removeEpic(4);
        manager.getAll();
    }
}
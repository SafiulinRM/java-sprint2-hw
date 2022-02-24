public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        Epic buyCar = new Epic("Покупка машины");
        Subtask saveMoney = new Subtask("Накопить денег", buyCar.number);
        Subtask goToCarDealership = new Subtask("Пойти в автосасалон", buyCar.number);
        manager.createEpic(buyCar);
        manager.createSubtask(saveMoney, buyCar.number);
        manager.createSubtask(goToCarDealership, buyCar.number);
        Epic moving = new Epic("Переезд");
        Subtask packBackpack = new Subtask("Собрать рюкзак", moving.number);
        manager.createEpic(moving);
        manager.createSubtask(packBackpack, moving.number);
        manager.getAll();
        manager.updateStatusSubtask(3, "DONE");
        manager.updateStatusSubtask(5, "DONE");
        manager.getAll();
        manager.removeSubtask(3);
        manager.removeEpic(4);
        manager.getAll();
    }
}
public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        Epic buyCar = new Epic("Покупка машины");
        Subtask saveMoney = new Subtask("Накопить денег");
        Subtask goToCarDealership = new Subtask("Пойти в автосасалон");
        manager.createSubtaskAndEpics(buyCar, saveMoney, goToCarDealership);
        Epic moving = new Epic("Переезд");
        Subtask packBackpack = new Subtask("Собрать рюкзак");
        manager.createSubtaskAndEpics(moving, packBackpack, null);
        manager.getAll();
        manager.updateStatusSubtask(saveMoney, "DONE");
        manager.updateStatusSubtask(packBackpack, "DONE");
        manager.getAll();
        manager.removeEpic(4);
        manager.removeSubtask(2);
        manager.getAll();
        manager.removeAll();
    }
}
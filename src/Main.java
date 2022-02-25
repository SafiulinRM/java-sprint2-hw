public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        Epic buyCar = new Epic();
        buyCar.setName("Покупка Машины");
        Subtask saveMoney = new Subtask(buyCar.number);
        saveMoney.setName("Накопить денег");
        Subtask goToCarDealership = new Subtask(buyCar.number);
        goToCarDealership.setName("Пойти в автосалон");
        manager.createEpic(buyCar);
        manager.createSubtask(saveMoney, buyCar.number);
        manager.createSubtask(goToCarDealership, buyCar.number);
        Epic moving = new Epic();
        moving.setName("Переезд");
        Subtask packBackpack = new Subtask(moving.number);
        packBackpack.setName("Собрать рюкзак");
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
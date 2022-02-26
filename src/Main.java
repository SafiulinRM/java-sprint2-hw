public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        Epic buyCar = new Epic();
        buyCar.setName("Покупка Машины");
        Subtask saveMoney = new Subtask(buyCar.id);
        saveMoney.setName("Накопить денег");
        Subtask goToCarDealership = new Subtask(buyCar.id);
        goToCarDealership.setName("Пойти в автосалон");
        manager.createEpic(buyCar);
        manager.createSubtask(saveMoney, buyCar.id);
        manager.createSubtask(goToCarDealership, buyCar.id);
        Epic moving = new Epic();
        moving.setName("Переезд");
        Subtask packBackpack = new Subtask(moving.id);
        packBackpack.setName("Собрать рюкзак");
        manager.createEpic(moving);
        manager.createSubtask(packBackpack, moving.id);
        manager.getAll();
        manager.updateStatusSubtask(3, Task.Status.DONE);
        manager.updateStatusSubtask(5, Task.Status.DONE);
        manager.getAll();
        manager.removeSubtask(3);
        manager.removeEpic(4);
        manager.getAll();
    }
}
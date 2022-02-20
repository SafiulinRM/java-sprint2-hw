import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Epic buyCar = new Epic("Покупка машины");
        Subtask saveMoney = new Subtask("Накопить денег");
        Subtask goToCarDealership = new Subtask("Пойти в автосасалон");

        Manager manager = new Manager();
        manager.createEpic(buyCar.number, buyCar);
        manager.createSubtask(saveMoney);
        manager.createSubtask(goToCarDealership);
        Scanner scan = new Scanner(System.in);
        printMenu();
        int option = scan.nextInt();
        switch (option) {
            case 1:
                createTask();
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 0:
                break;
        }
    }

    static void printMenu() {
        System.out.println("Выберете:");
        System.out.println("1 - Создать задачу.");
        System.out.println("2 - Обновить задачу.");
        System.out.println("3 - Получить информацию о задаче.");
        System.out.println("4 - Удалить задачу.");
        System.out.println("0 - Выход.");
    }

    static void createTask() {
        System.out.println("Выберете вид задачи:");
        System.out.println("1 - Простая задача.");
        System.out.println("2 - Сложная задача.");
    }

}

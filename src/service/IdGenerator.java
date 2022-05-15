package service;

public class IdGenerator {
    private static int counterId = 0;

    public int generate() {
        return ++counterId;
    }

    public static void setCounterId(int counterId) {
        IdGenerator.counterId = counterId;
    }
} 


package service;

public class IdGenerator {
    private static int counterId = 1;

    public int generate() {
        return counterId++;
    }
} 


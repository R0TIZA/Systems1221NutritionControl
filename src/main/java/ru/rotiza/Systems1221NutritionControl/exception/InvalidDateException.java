package ru.rotiza.Systems1221NutritionControl.exception;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException() {
        super("Invalid date");
    }
    public InvalidDateException(String message) {
        super(message);
    }
}

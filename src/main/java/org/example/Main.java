package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var parser = new CsvParser("C:\\Users\\flyli\\IdeaProjects\\prNew\\src\\main\\java\\org\\example\\basicprogramming_2_1.csv");
        parser.parse();
        System.out.println("Введите имя студента для получения баллов: ");
        Scanner scanner = new Scanner(System.in);
        var name = scanner.next();
        for (Theme theme: parser.themes) {
            for (Task task: theme.getTasks()) {
                System.out.printf("Тема:%s | %s:%s | Результат - %s",
                        theme.getNameTheme(),
                        task.getTaskType().toString(),
                        task.getNameTask(),
                        task.getTaskResultForStudent(name).toString());
            }
        }
    }
}
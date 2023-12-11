import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import configUtils.ConfigFileReader;
import db.DBRepository;
import models.Task;
import models.Theme;
import parser.CsvParser;
import vkApi.VkRepository;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClientException, ApiException, InterruptedException {
//        var parser = new CsvParser("C:\\Users\\flyli\\IdeaProjects\\UlearnAnalyticsJava\\src\\main\\java\\basicprogramming_2_1.csv");
//        var vkApi = new VkRepository();
//        parser.parse();
//        vkApi.addCitiesToStudents(parser.getStudents());
        DBRepository.connect();
//        DBRepository.createTableStudents();
//        DBRepository.saveStudents(parser.getStudents());
        System.out.println(DBRepository.getStudents());
//        System.out.println("Введите имя студента для получения баллов: ");
//        Scanner scanner = new Scanner(System.in);
//        var name = scanner.next();
//        for (Theme theme: parser.getThemes()) {
//            for (Task task: theme.getTasks()) {
//                System.out.printf("Тема:%s | %s:%s | Результат - %s",
//                        theme.getNameTheme(),
//                        task.getTaskType().toString(),
//                        task.getNameTask(),
//                        task.getTaskResultForStudent(name).toString());
//            }
//        }
    }
}
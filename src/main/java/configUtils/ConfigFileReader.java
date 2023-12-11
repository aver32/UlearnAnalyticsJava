package configUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConfigFileReader {
    public static final String path = "C:\\Users\\flyli\\IdeaProjects\\UlearnAnalyticsJava\\src\\main\\java\\config.properties";
    public static List<String> getProperties() {
        try (FileInputStream inputStream = new FileInputStream(path)) {
            List<String> propertiesValue = new ArrayList<>();
            Properties properties = new Properties();
            properties.load(inputStream);
            propertiesValue.add(properties.getProperty("access_token"));
            propertiesValue.add(properties.getProperty("app_id"));
            return propertiesValue;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

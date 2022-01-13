package net.kicchi.todos.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import net.kicchi.todos.config.ApplicationConfig;
import net.kicchi.todos.enums.BrowserType;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * reads the properties file configuration.yml
 */
public class ConfigurationReaderUtil {

    public static ApplicationConfig getConfiguration(){
        try{
            File file = new File("src/test/resources/configuration.yml");
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            ApplicationConfig applicationConfig = objectMapper.readValue(file, ApplicationConfig.class);
            return applicationConfig;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
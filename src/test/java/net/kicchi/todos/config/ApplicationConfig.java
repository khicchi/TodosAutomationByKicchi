package net.kicchi.todos.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.kicchi.todos.enums.BrowserType;

import java.util.Arrays;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ApplicationConfig {
    @JsonProperty("main-page-url")
    private String mainPageUrl;

    @JsonProperty("url-to-navigate")
    private String urlToNavigate;

    @JsonProperty("browser-type-id")
    private int browserTypeId;

    @JsonProperty("remote-grid-url")
    private String remoteGridUrl;

    public BrowserType getBrowserType(){
        return BrowserType.getById(browserTypeId);
    }
}

package budkevych.squareapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("frontend")
@Data
public class ConfigProperties {
    private String address;
}

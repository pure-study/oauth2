package com.cheetah.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = IgnoreProperties.AUTH_IGNORE_PREFIX)
public class IgnoreProperties {

    public static final String AUTH_IGNORE_PREFIX = "security.ignore";

    public List<String> urls = new ArrayList<>();
}

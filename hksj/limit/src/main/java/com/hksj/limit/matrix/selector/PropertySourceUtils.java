package com.hksj.limit.matrix.selector;

import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class PropertySourceUtils {
    public static Map<String, Object> getSubProperties(PropertySources propertySources,
                                                       String keyPrefix) {
        return PropertySourceUtils.getSubProperties(propertySources, null, keyPrefix);
    }
    public static Map<String, Object> getSubProperties(PropertySources propertySources,
                                                       String rootPrefix, String keyPrefix) {
        RelaxedNames keyPrefixes = new RelaxedNames(keyPrefix);
        Map<String, Object> subProperties = new LinkedHashMap<String, Object>();
        for (PropertySource<?> source : propertySources) {
            if (source instanceof EnumerablePropertySource) {
                for (String name : ((EnumerablePropertySource<?>) source)
                        .getPropertyNames()) {
                    String key = PropertySourceUtils.getSubKey(name, rootPrefix,
                            keyPrefixes);
                    if (key != null && !subProperties.containsKey(key)) {
                        subProperties.put(key, source.getProperty(name));
                    }
                }
            }
        }
        return Collections.unmodifiableMap(subProperties);
    }

    private static String getSubKey(String name, String rootPrefixes,
                                    RelaxedNames keyPrefix) {
        rootPrefixes = (rootPrefixes != null ? rootPrefixes : "");
        for (String rootPrefix : new RelaxedNames(rootPrefixes)) {
            for (String candidateKeyPrefix : keyPrefix) {
                if (name.startsWith(rootPrefix + candidateKeyPrefix)) {
                    return name.substring((rootPrefix + candidateKeyPrefix).length());
                }
            }
        }
        return null;
    }
}

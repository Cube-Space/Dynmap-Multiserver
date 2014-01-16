package net.cubespace.dynmap.multiserver.Lib.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class ConfigFastLookupCache {
    static Logger logger = LoggerFactory.getLogger(ConfigFastLookupCache.class);

    private HashMap<String, Object> lookupTable = new HashMap<>();

    public void set(String fullPath, Object section) {
        lookupTable.put(fullPath, section);
    }

    public <T> T get(String fullpath) {
        return (T) lookupTable.get(fullpath);
    }

    public boolean has(String fullpath) {
        return lookupTable.containsKey(fullpath);
    }

    public void niceOutput() {
        for(Map.Entry<String, Object> lookupEntry : lookupTable.entrySet()) {
            logger.info(lookupEntry.getKey() + "->" + lookupEntry.getValue().getClass().getName());
        }
    }
}

package net.cubespace.dynmap.multiserver.Lib.Config;

import org.apache.commons.lang.util.Validate;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Config extends YamlConfigMapper implements IConfig {
    @Override
    public void save() {
        Validate.notNull(CONFIG_FILE, "Saving a config without given File");

        for (Field field : getClass().getDeclaredFields()) {
            String path = field.getName().replaceAll("_", ".");

            if (doSkip(field)) continue;

            try {
                root.set(path, field.get(this));
            } catch (IllegalAccessException e) {
                logger.error("Could not save field", e);
            }
        }

        saveToYaml();
    }

    @Override
    public void save(File file) {
        Validate.notNull(CONFIG_FILE, "New config File is null");

        CONFIG_FILE = file;
        save();
    }

    @Override
    public void init() {
        if(!CONFIG_FILE.exists()) {
            CONFIG_FILE.getParentFile().mkdirs();
            try {
                CONFIG_FILE.createNewFile();
                save();
            } catch (IOException e) {
                logger.error("Could not create new empty File", e);
            }
        } else {
            load();
        }
    }

    @Override
    public void init(File file) {
        Validate.notNull(CONFIG_FILE, "New config File is null");

        CONFIG_FILE = file;
        init();
    }

    @Override
    public void reload() {
        reloadFromYaml();
    }

    @Override
    public void load() {
        Validate.notNull(CONFIG_FILE, "Loading a config without given File");

        loadFromYaml();

        for (Field field : getClass().getDeclaredFields()) {
            String path = field.getName().replaceAll("_", ".");

            if (doSkip(field)) continue;

            if(root.has(path)) {
                try {
                    field.set(this, root.get(path));
                } catch (IllegalAccessException e) {
                    logger.error("Could not set field", e);
                }
            } else {
                try {
                    root.set(path, field.get(this));
                } catch (IllegalAccessException e) {
                    logger.error("Could not get field", e);
                }
            }
        }
    }

    @Override
    public void load(File file) {
        Validate.notNull(CONFIG_FILE, "New config File is null");

        CONFIG_FILE = file;
        load();
    }

    protected boolean doSkip(Field field) {
        return Modifier.isTransient(field.getModifiers()) || Modifier.isStatic(field.getModifiers())
                || Modifier.isFinal(field.getModifiers()) || !Modifier.isPublic(field.getModifiers());
    }
}

package net.cubespace.dynmap.multiserver.Lib.Config;

import java.io.File;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public interface IConfig {
    public void save();
    public void save(File file);

    public void init();
    public void init(File file);

    public void reload();

    public void load();
    public void load(File file);
}

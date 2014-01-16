package net.cubespace.dynmap.multiserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.cubespace.dynmap.multiserver.Config.Dynmap;
import net.cubespace.dynmap.multiserver.GSON.Component;
import net.cubespace.dynmap.multiserver.GSON.ComponentDeserializer;
import net.cubespace.dynmap.multiserver.GSON.DynmapConfig;
import net.cubespace.dynmap.multiserver.GSON.DynmapWorld;
import net.cubespace.dynmap.multiserver.GSON.DynmapWorldConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class DynmapServer {
    static Logger logger = LoggerFactory.getLogger(DynmapServer.class);

    private Gson gson = new Gson();

    private DynmapConfig dynmapConfig;

    //This dynmaps worlds
    private HashMap<String, DynmapWorldConfig> dynmapWorldConfigs = new HashMap<>();

    //Dynmap Files
    private File dynmapConfigFile;

    //Some Dynmap config things
    private File file;

    private class DynmapServerUpdater extends Thread {
        private int updateInterval;

        public DynmapServerUpdater(Integer updateInterval) {
            this.updateInterval = updateInterval;
        }

        public void run() {
            while(true) {
                try {
                    loadWorlds();
                } catch (DynmapInitException e) {
                    logger.warn("Error in getting new Worlds", e);
                }

                for(Map.Entry<String, DynmapWorldConfig> dynmapWorldConfig : new HashMap<>(dynmapWorldConfigs).entrySet()) {
                    File dynmapWorldConfigFile = new File(file, "standalone" + File.separator + "dynmap_" + dynmapWorldConfig.getKey() + ".json");
                    try(FileReader fileReader = new FileReader(dynmapWorldConfigFile)) {
                        dynmapWorldConfig.setValue(gson.fromJson(fileReader, DynmapWorldConfig.class));
                        dynmapWorldConfigs.put(dynmapWorldConfig.getKey(), dynmapWorldConfig.getValue());
                    } catch (FileNotFoundException e) {
                        logger.warn("Could not update Dynmap World", e);
                    } catch (IOException e) {
                        logger.warn("Could not update Dynmap World", e);
                    }
                }

                try {
                    Thread.sleep(updateInterval * 1000);
                } catch (InterruptedException e) {

                }
            }
        }
    }

    public DynmapServer(Dynmap config) throws DynmapInitException {
        this.file = new File(config.Folder);

        //Check if the installation is correct
        preCheck();

        //Read the Dynmap config
        readDynmapConfig();

        //Check if all Worlds in the Config are there
        loadWorlds();

        DynmapServerUpdater dynmapServerUpdater = new DynmapServerUpdater(config.UpdateInterval);
        dynmapServerUpdater.start();
    }

    private void loadWorlds() throws DynmapInitException {
        for(DynmapWorld world : dynmapConfig.getWorlds()) {
            logger.info("Checking World " + world.getName());

            File dynmapWorldConfig = new File(file, "standalone" + File.separator + "dynmap_" + world.getName() + ".json");

            if(!dynmapWorldConfig.exists()) {
                throw new DynmapInitException("World " + world.getName() + " has no config in Dynmap");
            }

            if(!dynmapWorldConfigs.containsKey(world.getName())) {
                try(FileReader fileReader = new FileReader(dynmapWorldConfig)) {
                    DynmapWorldConfig dynmapWorldConfig1 = gson.fromJson(fileReader, DynmapWorldConfig.class);
                    dynmapWorldConfigs.put(world.getName(), dynmapWorldConfig1);

                    logger.info("Loaded World " + world.getName());
                } catch (FileNotFoundException e) {
                    logger.error("Error in reading in the Worldfile", e);
                } catch (IOException e) {
                    logger.error("Error in closing in the Worldfile", e);
                }
            }
        }
    }

    private void readDynmapConfig() throws DynmapInitException {
        try(FileReader fileReader = new FileReader(dynmapConfigFile)) {
            Gson gsonDymapConfig = new GsonBuilder().registerTypeAdapter(Component.class, new ComponentDeserializer()).create();
            dynmapConfig = gsonDymapConfig.fromJson(fileReader, DynmapConfig.class);
        } catch (IOException e) {
            throw new DynmapInitException("Could not parse dynmap_config.json", e);
        }
    }

    private void preCheck() throws DynmapInitException {
        /**
         * The Dynmap must contain:
         * standalone/dynmap_config.json
         */
        dynmapConfigFile = new File(file, "standalone" + File.separator + "dynmap_config.json");

        if(!dynmapConfigFile.exists()) {
            throw  new DynmapInitException("dynmap_config.json in the standalone Folder is missing. Be sure you started the Dynmap");
        }
    }

    public DynmapWorld[] getWorlds() {
        return dynmapConfig.getWorlds();
    }

    public DynmapWorldConfig getWorldConfig(String name) {
        return dynmapWorldConfigs.get(name);
    }

    public File getFolder() {
        return file;
    }

    public Component[] getComponents() {
        return dynmapConfig.getComponents();
    }
}

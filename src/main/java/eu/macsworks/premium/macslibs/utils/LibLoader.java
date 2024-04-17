package eu.macsworks.premium.macslibs.utils;

import eu.macsworks.premium.macslibs.MacsLibs;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

public class LibLoader {

    private final HashMap<String, String> lang = new HashMap<>();

    public LibLoader(){
        load();
    }

    private void load(){
        File configFile = new File(MacsLibs.getInstance().getDataFolder() + "/config.yml");
        if(!configFile.exists()) MacsLibs.getInstance().saveResource("config.yml", false);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        config.getConfigurationSection("lang").getKeys(false).forEach(s -> lang.put(s, ColorTranslator.translate(config.getString("lang." + s))));
    }

    public String getLang(String key){
        if(!lang.containsKey(key)) return key;
        return lang.get(key);
    }

}

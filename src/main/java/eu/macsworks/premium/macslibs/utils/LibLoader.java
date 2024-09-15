package eu.macsworks.premium.macslibs.utils;

import eu.macsworks.premium.macslibs.MacsLibs;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

public class LibLoader {

    private final HashMap<String, String> lang = new HashMap<>();
    @Getter private boolean adsEnabled;

    public LibLoader(){
        load();
    }

    private void load(){
        File configFile = new File(MacsLibs.getInstance().getDataFolder() + "/config.yml");
        if(!configFile.exists()) MacsLibs.getInstance().saveResource("config.yml", false);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        if(!config.contains("advertising")){
            configFile.delete();
            load();
            return;
        }
        config.getConfigurationSection("lang").getKeys(false).forEach(s -> lang.put(s, ColorTranslator.translate(config.getString("lang." + s))));

        adsEnabled = config.getBoolean("advertising");
        if(adsEnabled) Bukkit.getScheduler().runTaskTimerAsynchronously(MacsLibs.getInstance(), MacsLibs.getInstance().getAdvertManager()::tick, 0L, 100L);
    }

    public String getLang(String key){
        if(!lang.containsKey(key)) return key;
        return lang.get(key);
    }

}

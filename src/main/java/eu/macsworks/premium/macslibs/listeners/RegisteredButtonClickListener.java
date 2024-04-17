package eu.macsworks.premium.macslibs.listeners;

import de.tr7zw.changeme.nbtapi.NBTItem;
import eu.macsworks.premium.macslibs.objects.InteractResult;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class RegisteredButtonClickListener implements Listener {

    public static HashMap<String, Consumer<InteractResult>> onClick = new HashMap<>();

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType() == Material.AIR) return;

        NBTItem it = new NBTItem(event.getCurrentItem());
        if (!it.hasTag("macsitem")) return;

        event.setCancelled(true);

        if (!onClick.containsKey(it.getString("macsitem"))) return;
        if(it.getString("macsitem").equalsIgnoreCase("nothing")) return;

        onClick.get(it.getString("macsitem")).accept(new InteractResult(it, (Player) event.getWhoClicked(), event));
    }

    public static void addAction(String id, Consumer<InteractResult> onClick){
        RegisteredButtonClickListener.onClick.put(id, onClick);
    }

}

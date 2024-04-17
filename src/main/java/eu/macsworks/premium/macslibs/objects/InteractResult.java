package eu.macsworks.premium.macslibs.objects;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

@Getter
public class InteractResult {

    private final NBTItem item;
    private final Player clicker;
    private final InventoryClickEvent event;

    public InteractResult(NBTItem item, Player clicker, InventoryClickEvent event){
        this.item = item;
        this.clicker = clicker;
        this.event = event;
    }

}

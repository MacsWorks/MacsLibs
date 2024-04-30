package eu.macsworks.premium.macslibs.objects;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

@Getter
public class PhisicalInteractResult {

	private final NBTItem item;
	private final Player clicker;
	private final PlayerInteractEvent event;

	public PhisicalInteractResult(NBTItem item, Player clicker, PlayerInteractEvent event){
		this.item = item;
		this.clicker = clicker;
		this.event = event;
	}

}
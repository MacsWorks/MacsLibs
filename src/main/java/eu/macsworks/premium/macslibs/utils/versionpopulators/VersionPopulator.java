package eu.macsworks.premium.macslibs.utils.versionpopulators;

import org.bukkit.inventory.meta.ItemMeta;

public interface VersionPopulator {

	void populate(ItemMeta meta, Object[] args);

}

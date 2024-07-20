package eu.macsworks.premium.macslibs.objects;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;
import java.util.function.*;
import java.util.stream.Collectors;

public interface TabCompletion {

	public List<String> availableCompletions(Player p);

	default List<String> getCompletions(String partial, Player p) {
		return availableCompletions(p).stream().filter(s -> s.toLowerCase().startsWith(partial.toLowerCase())).toList();
	}

}

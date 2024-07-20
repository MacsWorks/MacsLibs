package eu.macsworks.premium.macslibs.objects;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;
import java.util.function.*;
import java.util.stream.Collectors;

public interface TabCompletion {

	public List<String> availableCompletions(Player p, String[] args);

	default List<String> getCompletions(String partial, String[] args, Player p) {
		return availableCompletions(p, args).stream().filter(s -> s.toLowerCase().startsWith(partial.toLowerCase())).toList();
	}

}

package eu.macsworks.premium.macslibs.objects;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TabCompletion {

	private final int argIndex;
	private final List<String> completions;
	private final Predicate<String> completionFilter;

	public List<String> getCompletions() {
		return completions.stream().filter(completionFilter).toList();
	}

}

package eu.macsworks.premium.macslibs.objects;

import lombok.Data;

@Data
public class Pair<T, J> {

	private final T key;
	private final J value;
}

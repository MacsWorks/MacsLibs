package eu.macsworks.premium.macslibs.objects;

import lombok.Data;

import java.util.Objects;

@Data
public class Pair<T, J> {

	private final T key;
	private final J value;

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Pair)) return false;
		Pair<?, ?> pair = (Pair<?, ?>) o;
		return (Objects.equals(pair.key, key)) && Objects.equals(pair.value, value);
	}

	@Override
	public int hashCode(){
		return Objects.hash(key, value);
	}
}

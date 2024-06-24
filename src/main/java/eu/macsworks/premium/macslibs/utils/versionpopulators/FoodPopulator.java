package eu.macsworks.premium.macslibs.utils.versionpopulators;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodPopulator implements VersionPopulator {
	@Override
	public void populate(ItemMeta meta, Object[] args) {
		int nutrition = (Integer) args[0];
		float saturation = (Float) args[1];
		float eatSeconds = (Float) args[2];

		meta.setFood(new FoodComponent() {
			@Override
			public int getNutrition() {
				return nutrition;
			}

			@Override
			public void setNutrition(int i) {

			}


			@Override
			public float getSaturation() {
				return saturation;
			}

			@Override
			public void setSaturation(float v) {

			}

			@Override
			public boolean canAlwaysEat() {
				return false;
			}

			@Override
			public void setCanAlwaysEat(boolean b) {

			}

			@Override
			public float getEatSeconds() {
				return eatSeconds;
			}

			@Override
			public void setEatSeconds(float v) {

			}

			@Override
			public List<FoodEffect> getEffects() {
				return new ArrayList<>();
			}

			@Override
			public void setEffects(List<FoodEffect> list) {

			}

			@Override
			public FoodEffect addEffect(PotionEffect potionEffect, float v) {
				return null;
			}

			@Override
			public Map<String, Object> serialize() {
				return new HashMap<>();
			}
		});
	}
}

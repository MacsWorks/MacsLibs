package eu.macsworks.premium.macslibs.advertising;

import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZonedDateTime;

public class AdvertManager {

	private long lastMSG = 0;

	public void tick(){
		if(ZonedDateTime.now().toEpochSecond() - lastMSG <= 7200) return;
		if(Bukkit.getOnlinePlayers().stream().noneMatch(p -> p.hasPermission("macslibs.ads"))) return;

		String flavor;
		try{
			flavor = getHTML();
		}catch (Exception e){
			e.printStackTrace();
			return;
		}

		if(flavor.isEmpty() || flavor.equalsIgnoreCase("null")) return;

		lastMSG = ZonedDateTime.now().toEpochSecond();

		Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission("macslibs.ads")).forEach(p -> {
			p.sendMessage("§7Message from §aMacsWorks ");
			TextComponent comp = new TextComponent("§7(§8?§7)");
			comp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Disable these messages in plugins/MacsLibs/config.yml")));
			p.spigot().sendMessage(comp);
			p.sendMessage("§f");
			p.sendMessage("§e" + flavor + "\n§f");
		});
	}

	private String getHTML() throws Exception {
		StringBuilder result = new StringBuilder();
		String url1 = "http://testing.macsworks.eu:3252/flavormsgget";
		URL url = new URL(url1);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(conn.getInputStream()))) {
			for (String line; (line = reader.readLine()) != null; ) {
				result.append(line);
			}
		}

		return result.toString();
	}

}

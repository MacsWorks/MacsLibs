package eu.macsworks.premium.macslibs.licensing;

import com.google.gson.Gson;
import eu.macsworks.premium.macslibs.objects.LicenseRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class LicensingManager {

	public static CompletableFuture<Boolean> isLicenseBlocked(String pluginName){
		return CompletableFuture.supplyAsync(() -> {
			try {
				return Boolean.parseBoolean(getHTML(pluginName));
			} catch (Exception e) {
				return false;
			}
		});
	}

	private static String getHTML(String plugin) throws Exception {
		StringBuilder result = new StringBuilder();
		String url1 = "http://testing.macsworks.eu:3252/isblockedapp";
		URL url = new URL(url1);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setDoOutput(true);
		OutputStream os = conn.getOutputStream();
		os.write(new Gson().toJson(new LicenseRequest(plugin)).getBytes());
		os.flush();
		os.close();

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(conn.getInputStream()))) {
			for (String line; (line = reader.readLine()) != null; ) {
				result.append(line);
			}
		}

		return result.toString();
	}
}

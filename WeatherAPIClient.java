import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherAPIClient {
    public static void main(String[] args) {
        try {
            // Public API URL for weather forecast
            String apiUrl = "https://api.open-meteo.com/v1/forecast?latitude=11.0168&longitude=76.9558&current_weather=true";

            // Create URL and connection
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            // Check response
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.out.println("HTTP Error: " + responseCode);
                return;
            }

            // Read response
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder responseStr = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                responseStr.append(line);
            }
            br.close();
            conn.disconnect();

            // Parse JSON
            JSONObject json = new JSONObject(responseStr.toString());
            JSONObject weather = json.getJSONObject("current_weather");

            // Display weather data
            System.out.println("===== Weather Report for Coimbatore =====");
            System.out.println("Temperature : " + weather.getDouble("temperature") + " °C");
            System.out.println("Wind Speed  : " + weather.getDouble("windspeed") + " km/h");
            System.out.println("Time        : " + weather.getString("time"));
            System.out.println("=========================================");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

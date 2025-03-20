package ma.tr.docnearme;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpExample {
    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();

        // Create the request
        Request request = new Request.Builder()
                .url("https://www.dwa.ma/api/v1/search?word=a&type=medicaments")
                .header("Authorization", "Bearer YOUR_TOKEN")
                .build();

        try (Response response = client.newCall(request).execute()) {
            // Print the response body
            System.out.println("Response: " + response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

import java.io.IOException;
import java.net.*;
import java.util.*;
import com.google.gson.*;
import com.squareup.okhttp.*;
public class APITranslate {
        private static String subscriptionKey = "08df9d4e08574f5c89dcdadb31b3e841";

        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.cognitive.microsofttranslator.com")
                .addPathSegment("/translate")
                .addQueryParameter("api-version", "3.0")
                .addQueryParameter("from", "en")
                .addQueryParameter("to", "vi")
                .build();

        // Instantiates the OkHttpClient.
        OkHttpClient client = new OkHttpClient();

        // This function performs a POST request.
        public String Post(String word) throws IOException {
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType,
                    "[{\"Text\": \"" + word + "\"}]");
            Request request = new Request.Builder().url(url).post(body)
                    .addHeader("Ocp-Apim-Subscription-Key", subscriptionKey).addHeader("Content-type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }

        // This function prettifies the json response.
        public static String prettify(String json_text) {
            JsonParser parser = new JsonParser();
            JsonElement json = parser.parse(json_text);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(json);
        }
}

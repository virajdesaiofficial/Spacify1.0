package org.uci.spacifyEngine.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {
    public boolean sendWhatsAppMessage(String phoneNumber, String message) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://graph.facebook.com/v16.0/116410154764811/messages"))
                    .header("Authorization", "Bearer EAALGvX7haXwBAIG7mLf8ja0tewpS4zwu3SF5JCDYNwsoX8hiPXZCaZA5kjrnhMZA9MhV6GXYW7cZA2a4woZCsZBjUe3jgvMn10vCmg4KDvpzQO1KBqFGXSJaR8l1HlBFlZCwUeGoPQlZA0OYY07Wo9eNPNsiwwMxssRHOeerRRbOGSQvc6OBOOqcQbZCyq8lsIduTWvXy1CmS1wZDZD")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\n" +
                            "    \"messaging_product\": \"whatsapp\",\n" +
                            "    \"recipient_type\": \"individual\",\n" +
                            "    \"to\": \"" + phoneNumber + "\",\n" +
                            "    \"type\": \"interactive\",\n" +
                            "    \"interactive\": {\n" +
                            "        \"type\": \"button\",\n" +
                            "        \"body\": {\n" +
                            "            \"text\": \"" + message + "\"\n" +
                            "        },\n" +
                            "        \"action\": {\n" +
                            "            \"buttons\": [\n" +
                            "                {\n" +
                            "                    \"type\": \"reply\",\n" +
                            "                    \"reply\": {\n" +
                            "                        \"id\": \"1\",\n" +
                            "                        \"title\": \"Unsubscribe\"\n" +
                            "                    }\n" +
                            "                }\n" +
                            "            ]\n" +
                            "        }\n" +
                            "    }\n" +
                            "}"))
                    .build();
            HttpClient http = HttpClient.newHttpClient();
            HttpResponse<String> response = http.send(request, BodyHandlers.ofString());
            System.out.println(response.body());
            return response.statusCode() == 200;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}

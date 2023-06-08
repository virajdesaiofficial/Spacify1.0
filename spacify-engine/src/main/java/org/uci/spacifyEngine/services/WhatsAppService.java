package org.uci.spacifyEngine.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {

    @Value("${whatsapp.api.uri}")
    private String whatsappApiUri;

    @Value("${whatsapp.api.token}")
    private String whatsappApiToken;

    public boolean sendInteractiveWhatsAppMessage(String phoneNumber, String message, Long roomId) {
        String interactiveMessage = getInteractiveMessage(phoneNumber, message, Math.toIntExact(roomId));
        return sendWhatsAppMessage(interactiveMessage);
    }

    public boolean sendSimpleWhatsAppMessage(String phoneNumber, String message) {
        String simpleMessage = getSimpleMessage(phoneNumber, message);
        return sendWhatsAppMessage(simpleMessage);
    }

    private boolean sendWhatsAppMessage(String payload) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(whatsappApiUri))
                    .header("Authorization", "Bearer " + whatsappApiToken)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
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

    private String getSimpleMessage(String phoneNumber, String message) {
        return "{\n" +
                "    \"messaging_product\": \"whatsapp\",\n" +
                "    \"recipient_type\": \"individual\",\n" +
                "    \"to\": \"" + phoneNumber + "\",\n" +
                "    \"type\": \"text\",\n" +
                "    \"text\": {\n" +
                "        \"body\": \"" + message + "\"\n" +
                "    }\n" +
                "}";
    }

    private String getInteractiveMessage(String phoneNumber, String message, int roomId) {
        return "{\n" +
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
                "                        \"id\": \"" + roomId + "\",\n" +
                "                        \"title\": \"Unsubscribe\"\n" +
                "                    }\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    }\n" +
                "}";
    }
}
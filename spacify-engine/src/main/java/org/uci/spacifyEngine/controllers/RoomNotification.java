package org.uci.spacifyEngine.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uci.spacifyEngine.services.RoomOccupancyService;
import org.uci.spacifyEngine.services.UserSubscriberService;
import org.uci.spacifyEngine.services.WhatsAppService;
import org.uci.spacifyLib.entity.RoomEntity;
import org.uci.spacifyLib.entity.UserEntity;
import org.uci.spacifyLib.repository.UserRepository;
import org.uci.spacifyLib.services.SubscriberService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/notification")
public class RoomNotification {

    private final WhatsAppService whatsAppService;
    private final UserSubscriberService userSubscriberService;
    private final RoomOccupancyService roomOccupancyService;
    private final SubscriberService subscriberService;
    private final UserRepository userRepository;

    @Autowired
    public RoomNotification(WhatsAppService whatsAppService, UserSubscriberService usersubscriberService, RoomOccupancyService roomOccupancyService, SubscriberService subscriberService, UserRepository userRepository) {
        this.whatsAppService = whatsAppService;
        this.userSubscriberService = usersubscriberService;
        this.roomOccupancyService = roomOccupancyService;
        this.subscriberService = subscriberService;
        this.userRepository = userRepository;
    }

    // Verify the webhook for POST request
    @PostMapping("/verifyWebhook")
    @ResponseBody
    public String verifyWebhook(@RequestBody String payload) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode webhookData = mapper.readTree(payload);

            JsonNode messagesNode = webhookData.path("entry").get(0).path("changes").get(0).path("value").path("messages");
            for (JsonNode messageNode : messagesNode) {
                if (messageNode.path("type").asText().equals("interactive")) {
                    String phoneNumber = messageNode.path("from").asText();
                    int roomId = messageNode.path("interactive").path("button_reply").path("id").asInt();

                    boolean unsubscribed = userSubscriberService.updateSubscriberStatus(phoneNumber, roomId, false);

                    if (unsubscribed) {
                        String confirmationMessage = "You have unsubscribed successfully.";
                        boolean confirmationMessageSent = whatsAppService.sendSimpleWhatsAppMessage(phoneNumber, confirmationMessage);

                        if (confirmationMessageSent) {
                            return "Unsubscribe action handled successfully.";
                        } else {
                            return "Failed to send unsubscribed message.";
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Webhook verification completed.";
    }

    @GetMapping("/health")
    @ResponseBody
    public String healthCheck() {

        return "Boo Yeah !";

    }

    // Verify the webhook for GET request
    @GetMapping("/verifyWebhook")
    @ResponseBody
    public String verifyWhatsAppWebhook(
            @RequestParam("hub.mode") String hubMode,
            @RequestParam("hub.challenge") String hubChallenge,
            @RequestParam("hub.verify_token") String hubVerifyToken) {
        System.out.println(hubVerifyToken);
        return hubChallenge;
    }

    @GetMapping("/send-empty-room-notification")
    public ResponseEntity<String> sendEmptyRoomNotification() {
        // Get room IDs with zero occupancy
        List<RoomEntity> roomEntitiesWithZeroOccupancy = roomOccupancyService.getRoomsWithZeroOccupancy();

        if (roomEntitiesWithZeroOccupancy.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No rooms with zero occupancy.");
        }

        for (RoomEntity roomEntity : roomEntitiesWithZeroOccupancy) {
            //List<String> userIds = subscriberService.getUserIdsByRoomIds(List.of(roomId));
            List<String> userIds = subscriberService.getUserIdsByRoomIdsForWhatsapp(roomEntity.getRoomId());

            // Iterate over the userIds list
            for (String userId : userIds) {
                // Get the phone number for the user ID
                String phoneNumber = getUserPhoneNumber(userId);
                if (phoneNumber == null) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch user phone number.");
                }

                // Check if the user is already unsubscribed
                boolean isSubscribed = userSubscriberService.isSubscriberSubscribed(phoneNumber, roomEntity.getRoomId());
                if (!isSubscribed) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is already unsubscribed.");
                }

                // Create the message with dynamic room information
                String message = "The room " + roomEntity.getRoomName() + " you've subscribed to is available to occupy! " +
                        "Please select the Unsubscribe button below to unsubscribe. " +
                        "Thank you for choosing Spacify!";

                // Trigger the WhatsApp message
                boolean messageSent = whatsAppService.sendInteractiveWhatsAppMessage(phoneNumber, message, roomEntity.getRoomId());

                if (!messageSent) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to trigger WhatsApp message.");
                }
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body("Interactive WhatsApp messages triggered successfully.");
    }

    private String getUserPhoneNumber(String userId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            String phoneNumber = userEntity.getPhoneNumber();
            System.out.println("Retrieved phone number for userId: " + userId + " - Phone number: " + phoneNumber);
            return phoneNumber;
        } else {
            System.out.println("User not found for userId: " + userId);
            return null;
        }
    }


}
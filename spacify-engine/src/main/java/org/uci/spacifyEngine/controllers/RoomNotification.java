package org.uci.spacifyEngine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uci.spacifyEngine.services.WhatsAppService;

@RestController
@RequestMapping("/api/v1/notification")
public class RoomNotification {
    private final WhatsAppService whatsAppService;

    @Autowired
    public RoomNotification(WhatsAppService whatsAppService) {
        this.whatsAppService = whatsAppService;
    }

    @PostMapping("/send-empty-room-notification")
    public ResponseEntity<String> sendEmptyRoomNotification() {
        // Fetch the user_id, room_id, tippers_space_id, and phone_number
        String userId = "emilysu";
        int roomId = 2;
        int tippersSpaceId = 55;
        String phoneNumber = "19495656087";

        // Create the message with dynamic room information
        String message = "The room " + roomId + " you've subscribed to is available to occupy! " +
                "Please select the Unsubscribe button below to unsubscribe. " +
                "Thank you for choosing Spacify!";

        // Trigger the WhatsApp message
        boolean messageSent = whatsAppService.sendWhatsAppMessage(phoneNumber, message);

        if (messageSent) {
            return ResponseEntity.status(HttpStatus.OK).body("WhatsApp message triggered successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to trigger WhatsApp message.");
        }
    }
}

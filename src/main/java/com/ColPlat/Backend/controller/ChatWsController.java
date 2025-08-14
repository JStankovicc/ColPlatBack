package com.ColPlat.Backend.controller;

// package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.request.SendMessageRequest;
import com.ColPlat.Backend.model.dto.response.MessageResponse;
import com.ColPlat.Backend.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWsController {

    private final ChatService chatService;

    // Klijent šalje na /app/chat.send
    @MessageMapping("/chat.send")
    @SendToUser("/queue/ack") // opciono ACK nazad pošiljaocu
    public MessageResponse onSend(@Payload SendMessageRequest req,
                                  @Header("user-id") String userIdHeader) {
        Long senderUserId = Long.valueOf(userIdHeader); // ili iz Principal-a/ JWT-a tokom WS handshaka
        return chatService.sendMessage(req.getConversationId(), senderUserId, req.getContent());
    }
}

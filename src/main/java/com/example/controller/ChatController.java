package com.example.controller;

import com.example.config.chatserver.Message;
import com.example.config.chatserver.Status;
import com.example.dto.MessageData;
import com.example.service.customer.CustomerService;
import com.example.service.groupcar.GroupCarService;
import com.example.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MessageService messageService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private GroupCarService groupCarService;

//    @MessageMapping("/message")
//    private Message receivePublicMessage(@Payload Message message) {
//        simpMessagingTemplate.convertAndSend("/chatroom/" + message.getGroupId() + "/public", message);
//        return message;
//    }

    @GetMapping("/public/get-all-message/by-groupcar/{id}")
    public ResponseEntity<List<com.example.entity.Message>> getAllMessageByGroupCar(@PathVariable int id) {
        return ResponseEntity.ok(messageService.getAllMessageByGroupCarId(id));
    }

    @MessageMapping("/message")
    private Message receivePublicMessage(@Payload Message message) {
        com.example.entity.Message newMessage = new com.example.entity.Message(
                -1,
                message.getMessage(),
                new Date(),
                new Date(),
                customerService.getCustomer(message.getUserId()),
                groupCarService.getGroupCarById(message.getGroupCarId())
        );
        com.example.entity.Message message_db = messageService.save(newMessage);
        MessageData messageData = new MessageData();
        messageData.setMessage(message_db);
        messageData.setStatus(message.getStatus());
        simpMessagingTemplate.convertAndSend("/chatroom/"+ message.getGroupCarId() +"/public", message);
        return message;
    }

    @MessageMapping("/private-message")
    public  Message receivePrivateMessage(@Payload Message message) {

        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message); // /user/David/private
        return message;
    }

//    @MessageMapping("/leave")
//    public void handleUserLeave(@Payload Message message) {
//        message.setStatus(Status.LEAVE);
//        simpMessagingTemplate.convertAndSend("/chatroom/"+ message.getGroupId() +"/public", message);
//    }

    @MessageMapping("/leave")
    public void handleUserLeave(@Payload Message message) {
        message.setStatus(Status.LEAVE);
        simpMessagingTemplate.convertAndSend("/chatroom/public", message);
    }

//    @MessageMapping("/join")
//    public void handleUserJoin(@Payload Message message) {
//        message.setStatus(Status.JOIN);
//        simpMessagingTemplate.convertAndSend("/chatroom/"+ message.getGroupId() +"/public", message);
//    }

    @MessageMapping("/join")
    public void handleUserJoin(@Payload Message message) {
        message.setStatus(Status.JOIN);
        simpMessagingTemplate.convertAndSend("/chatroom/"+message.getGroupCarId()+"/public", message);
    }
}

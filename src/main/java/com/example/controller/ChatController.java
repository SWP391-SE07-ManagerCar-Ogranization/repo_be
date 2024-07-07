package com.example.controller;

import com.example.config.chatserver.Message;
import com.example.config.chatserver.Status;
import com.example.dto.MessageData;
import com.example.dto.MessageDto;
import com.example.service.DriverDetail.DriverDetailService;
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

    @Autowired
    private DriverDetailService driverDetailService;

//    @MessageMapping("/message")
//    private Message receivePublicMessage(@Payload Message message) {
//        simpMessagingTemplate.convertAndSend("/chatroom/" + message.getGroupId() + "/public", message);
//        return message;
//    }

    @GetMapping("/public/get-all-message/by-groupcar/{id}")
    public ResponseEntity<List<com.example.entity.Message>> getAllMessageByGroupCar(@PathVariable int id) {
        return ResponseEntity.ok(messageService.getAllMessageByGroupCarId(id));
    }

    @GetMapping("/public/get-all-private-message/by-driver-and-customer/{customerId}/{driverDetailId}")
    public ResponseEntity<List<MessageDto>> getAllMessageByGroupCar(@PathVariable int customerId, @PathVariable int driverDetailId) {
        return ResponseEntity.ok(messageService.findByCustomerIdAndDriverDetailId(customerId, driverDetailId));
    }

    @MessageMapping("/message")
    private Message receivePublicMessage(@Payload Message message) {
        com.example.entity.Message initMessage = new com.example.entity.Message();
        if(message.getRole().equals("DRIVER")){
            initMessage.setContent(message.getMessage());
            initMessage.setCreatedAt(new Date());
            initMessage.setUpdatedAt(new Date());
            initMessage.setDriverDetail(driverDetailService.getDriverDetail(message.getUserId()));
            initMessage.setGroupCar(groupCarService.getGroupCarById(message.getGroupCarId()));
        }else {
//            com.example.entity.Message newMessage = new com.example.entity.Message(
//                -1,
//                message.getMessage(),
//                new Date(),
//                new Date(),
//                customerService.getCustomer(message.getUserId()),
//                null,
//                groupCarService.getGroupCarById(message.getGroupCarId())
            initMessage.setContent(message.getMessage());
            initMessage.setCreatedAt(new Date());
            initMessage.setUpdatedAt(new Date());
            initMessage.setCustomer(customerService.getCustomer(message.getUserId()));
            initMessage.setGroupCar(groupCarService.getGroupCarById(message.getGroupCarId()));

        }


        com.example.entity.Message message_db = messageService.save(initMessage);
        MessageData messageData = new MessageData();
        messageData.setMessage(message_db);
        messageData.setStatus(message.getStatus());
        simpMessagingTemplate.convertAndSend("/chatroom/"+ message.getGroupCarId() +"/public", message);
        return message;
    }

    @MessageMapping("/private-message")
    public  Message receivePrivateMessage(@Payload Message message) {
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(message.getReceiverId()) , "/private", message); // /user/David/private
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

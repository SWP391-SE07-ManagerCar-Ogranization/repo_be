package com.example.service.message;

import com.example.dto.MessageDto;
import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImp implements MessageService{
    @Autowired
    MessageRepository messageRepository;

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public void delete(Message message) {
        messageRepository.delete(message);
    }

    @Override
    public List<Message> findAll() {
        return List.of();
    }

    @Override
    public Message findById(Integer id) {
        return null;
    }

    @Override
    public List<Message> getAllMessageByGroupCarId(Integer id) {
        return messageRepository.findByGroupCarGroupId(id);
    }

    @Override
    public List<MessageDto> findByCustomerIdAndDriverDetailId(Integer customerId, Integer driverId) {
        List<Message>  privateMessages = messageRepository.findByCustomerIdAndDriverDetailId(customerId, driverId);
        List<MessageDto> privateMessageDtos = new ArrayList<>();
        for (Message message : privateMessages) {
            MessageDto messageDto = new MessageDto();
            messageDto.setMessageId(message.getMessageId());
            messageDto.setCustomerId(message.getCustomer().getId());
            messageDto.setDriverDetailId(message.getDriverDetail().getId());
            messageDto.setContent(message.getContent());
            messageDto.setCreateAt(message.getCreatedAt());
            messageDto.setUpdatedAt(message.getUpdatedAt());
            messageDto.setSenderId(message.getSenderId());
            privateMessageDtos.add(messageDto);
        }
        return privateMessageDtos;
    }
}

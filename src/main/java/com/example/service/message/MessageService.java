package com.example.service.message;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MessageService {
    Message save(Message message);
    void delete(Message message);
    List<Message> findAll();
    Message findById(Integer id);
    List<Message> getAllMessageByGroupCarId(Integer id);
}

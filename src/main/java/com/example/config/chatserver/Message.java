package com.example.config.chatserver;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {

    private Integer userId;
    private Integer groupCarId;
    private String ReceiverName;
    private String message;
    private String createdAt;
    private Status status;
}

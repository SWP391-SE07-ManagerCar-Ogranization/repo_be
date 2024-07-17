package com.example.config.chatserver;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {

    private Integer userId;
    private String role;
    private Integer groupCarId;
    private Integer receiverId;
    private String message;
    private String createdAt;
    private Status status;
}

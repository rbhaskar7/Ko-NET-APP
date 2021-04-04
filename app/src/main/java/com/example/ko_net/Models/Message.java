package com.example.ko_net.Models;

public class Message {
    String uId, message, messageId;
    Long timeStamp;

    public Message(String uId, String message, Long timeStamp, String messageId) {
        this.uId = uId;
        this.message = message;
        this.timeStamp = timeStamp;
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Message(String uId, String message) {
        this.uId = uId;
        this.message = message;
    }

    public Message() {}

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}

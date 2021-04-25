package com.demo.smokingcontrolapp.models;

public class Chat {
    private String message;
    private String sender;
    private String receiver;
    private long createTime;

    public Chat(String message, String sender, String receiver, long createTime) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.createTime = createTime;
    }

    public Chat() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}

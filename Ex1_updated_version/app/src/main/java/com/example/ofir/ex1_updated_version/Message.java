package com.example.ofir.ex1_updated_version;

import java.util.Random;

class Message {
    /**
     * This class represents a Message object, each message contains a content, ID and it
     * creation timestamp
     */

    private String content;
    private String id;
    private String timestamp;
    private String device_name;

    Message(String content, String id, String timestamp, String device_name)
    {
        this.content = content;
        this.id = id;
        this.timestamp = timestamp;
        this.device_name = device_name;
    }

    /**
     * This function will generate 9 digit random Number.
     */
    static String getRandomNumberString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999999);

        return String.format("%09d", number);
    }


    // Getters

    String getContent(){
        return this.content;
    }

    String getId(){
        return this.id;
    }

    String getTimestamp(){
        return this.timestamp;
    }

    String getDevice_name(){
        return this.device_name;
    }

}

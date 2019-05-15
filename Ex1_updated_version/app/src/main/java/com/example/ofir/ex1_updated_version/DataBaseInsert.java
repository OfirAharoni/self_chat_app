package com.example.ofir.ex1_updated_version;

import android.os.AsyncTask;
import java.util.HashMap;
import java.util.Map;

/**
 * This class will insert the message to the FireStore DB
 */

public class DataBaseInsert extends AsyncTask<String, String, Void> {

    private final String CONTENT_TAG = "content";
    private final String DEVICE_TAG = "device";
    private final String ID_TAG = "id";
    private final String TIMESTAMP_TAG = "timestamp";
    private final String USER_TAG = "username";
    private final String COLLECTION_TAG = "ChatMessages";
    private final String DEFAULTS_TAG = "defaults";
    private final String USER_DOC_TAG = "user";
    final String MESSAGE_INSERTION_TAG = "Message_insertion";


    @Override
    protected Void doInBackground(String... strings) {
        Map<String, String> msg_map = new HashMap<>();

        if (strings[0].equals(MESSAGE_INSERTION_TAG))
        {
            msg_map.put(CONTENT_TAG, strings[1]);
            msg_map.put(ID_TAG, strings[2]);
            msg_map.put(TIMESTAMP_TAG, strings[3]);
            msg_map.put(DEVICE_TAG, strings[4]);
            MainActivity.firestore.collection(COLLECTION_TAG).add(msg_map);
        }

        if (strings[0].equals(USER_TAG))
        {
            msg_map.put(USER_TAG, strings[1]);
            MainActivity.firestore.collection(DEFAULTS_TAG).document(USER_DOC_TAG).set(msg_map);
        }

        return null;
    }

}
package com.example.ofir.ex1_updated_version;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.example.ofir.ex1_updated_version.MainActivity.recyclerViewAdapter;

/**
 * This class is responsible to load all the messages from FireBase DB
 */

public class DataBaseLoad extends AsyncTask<Integer, Void, Boolean> {

    private final String CONTENT_TAG = "content";
    private final String ID_TAG = "id";
    private final String TIMESTAMP_TAG = "timestamp";
    private final String USER_TAG = "username";
    private final String DEVICE_TAG = "device";
    private final String MAIN_ACTIVITY = "MainActivity";
    private final String CONFIGURE_ACTIVITY = "Configure_name";
    private final String CLEAR = "";
    public Staging staging;
    private String user_name_from_db = "";
    Context context;

    public DataBaseLoad(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    protected Boolean doInBackground(Integer... ints) {
        if (ints[0] == 0)
        {
            MainActivity.messagesRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if (queryDocumentSnapshots.getDocuments().size() > 0) {
                        ArrayList<Message> msg_from_db = new ArrayList<>();
                        for (int i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
                            String content =
                                    queryDocumentSnapshots.getDocuments().get(i).getString(CONTENT_TAG);
                            String id = queryDocumentSnapshots.getDocuments().get(i).getString(ID_TAG);
                            String timestamp =
                                    queryDocumentSnapshots.getDocuments().get(i).getString(TIMESTAMP_TAG);
                            String device_name =
                                    queryDocumentSnapshots.getDocuments().get(i).getString(DEVICE_TAG);
                            Message msg = new Message(content, id, timestamp, device_name);
                            msg_from_db.add(msg);
                        }

                        // sort messages by timestamp
                        Comparator<Message> compareByTs= new Comparator<Message>() {
                            @Override
                            public int compare(Message o1, Message o2) {
                                return o1.getTimestamp().compareTo(o2.getTimestamp());
                            }
                        };

                        Collections.sort(msg_from_db, compareByTs);

                        // update messages list and update recycler view adapter
                        MainActivity.messages = msg_from_db;
                        recyclerViewAdapter.notifyDataSetChanged();
                    }
                }
            });
        return false;
        }

        else
            {
            MainActivity.usersRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
            {

                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                   user_name_from_db = documentSnapshot.getString(USER_TAG);
                   if (user_name_from_db == null)
                   {
                       staging.switchActivity(CONFIGURE_ACTIVITY, CLEAR);
                   }
                   else
                   {
                       staging.switchActivity(MAIN_ACTIVITY, user_name_from_db);
                   }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    staging.switchActivity(CONFIGURE_ACTIVITY, CLEAR);
                }
            });
        }

        return false;
    }


}

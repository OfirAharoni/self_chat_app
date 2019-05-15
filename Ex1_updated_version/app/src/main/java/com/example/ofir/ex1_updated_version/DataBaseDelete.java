package com.example.ofir.ex1_updated_version;

import android.os.AsyncTask;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * This class is responsible to delete a given message from FireBase DB.
 */
public class DataBaseDelete extends AsyncTask<String, String, Void> {

    private final String CONTENT_TAG = "content";
    private final String ID_TAG = "id";
    private final String TIMESTAMP_TAG = "timestamp";
    private final String DEVICE_TAG = "device";
    private final String COLLECTION_TAG = "ChatMessages";

    @Override
    protected Void doInBackground(String... strings) {
        final String content_dl = strings[0];
        final String id_dl = strings[1];
        final String timestamp_dl = strings[2];
        final String device_dl = strings[3];
        MainActivity.messagesRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.getDocuments().size() > 0){
                    for (int i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++)
                    {
                        String content =
                                queryDocumentSnapshots.getDocuments().get(i).getString(CONTENT_TAG);
                        String id = queryDocumentSnapshots.getDocuments().get(i).getString(ID_TAG);
                        String timestamp =
                                queryDocumentSnapshots.getDocuments().get(i).getString(TIMESTAMP_TAG);
                        String device = queryDocumentSnapshots.getDocuments().get(i).getString(DEVICE_TAG);
                        if (content_dl.equals(content) && id_dl.equals(id)
                                && timestamp_dl.equals(timestamp) && device_dl.equals(device))
                        {
                            String id_to_del = queryDocumentSnapshots.getDocuments().get(i).getId();
                            MainActivity.firestore.collection(COLLECTION_TAG).document(id_to_del).delete();
                        }
                    }
                }
            }
        });

        return null;
        }
    }

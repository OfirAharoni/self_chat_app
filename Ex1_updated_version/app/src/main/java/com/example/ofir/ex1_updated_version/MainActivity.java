package com.example.ofir.ex1_updated_version;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.sql.Timestamp;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public static ArrayList<Message> messages = new ArrayList<Message>();
    public static RecyclerViewAdapter recyclerViewAdapter;
    static FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    static CollectionReference messagesRef = firestore.collection("ChatMessages");
    static DocumentReference usersRef = firestore.collection("defaults").document("user");
    final String CLEAR = "";
    final String ERROR = "you can't send an empty message, oh silly!";
    final String MESSAGE_INSERTION_TAG = "Message_insertion";
    final String USER_TAG = "username";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check for bundle
        Bundle b = getIntent().getExtras();
        String user_name;
        if(b != null)
        {
            user_name = b.getString(USER_TAG);
            TextView username_tv = findViewById(R.id.username_tv);
            username_tv.setText("hello " + user_name + "!");
        }

        Button send_btn = findViewById(R.id.send_btn);
        final EditText et_input = findViewById(R.id.et_input);
        RecyclerView recyclerView = findViewById(R.id.list_view);
        recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Load data from DB
        DataBaseLoad dataBaseLoad = new DataBaseLoad(getApplicationContext());
        dataBaseLoad.execute(0);

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = et_input.getText().toString();
                if (content.equals(CLEAR)){
                    Toast.makeText(view.getContext(), ERROR, Toast.LENGTH_LONG).show();
                }
                else {
                    // Create unique ID, Timestamp and get device name for the message
                    String id = Message.getRandomNumberString();
                    Timestamp ts = new Timestamp(System.currentTimeMillis());
                    Long timestamp = ts.getTime();
                    String device_name = Build.MANUFACTURER + ' ' + Build.MODEL;
                    // Create Message object
                    Message message = new Message(content, id, timestamp.toString(), device_name);
                    // add the message to the messages list and update recycle view
                    messages.add(message);
                    recyclerViewAdapter.notifyDataSetChanged();
                    // clear edit text
                    et_input.setText(CLEAR);

                    // Create AsyncTask insertion object and execute
                    DataBaseInsert dataBaseInsert = new DataBaseInsert();
                    dataBaseInsert.execute(MESSAGE_INSERTION_TAG, message.getContent(), message.getId(),
                            message.getTimestamp(), message.getDevice_name());
                }
            }
        });

    }

    public void onBackPressed() {
    }
}





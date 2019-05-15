package com.example.ofir.ex1_updated_version;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Configure_name extends AppCompatActivity {

    final String USER_TAG = "username";
    final String CLEAR = "";
    private EditText input_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_name);

        //TODO -  Get data from DB, if exits - move to the main activity

        input_et = findViewById(R.id.et_input);
        final Button configure_btn = findViewById(R.id.configure_btn);
        Button skip_btn = findViewById(R.id.skip_btn);


        // Check for input in the edit text and change button visibility accordingly

        input_et.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (input_et.getText().toString().length() > 0 &&
                    !input_et.getText().toString().equals(CLEAR))
            {
                configure_btn.setVisibility(View.VISIBLE);
            }
            else
            {
                configure_btn.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    });

        // if configure button is pressed - send data to DB
        configure_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send data to DB
                String user_name = input_et.getText().toString();
                DataBaseInsert dataBaseInsert = new DataBaseInsert();
                dataBaseInsert.execute(USER_TAG, user_name);

                // directs to the Main activity
                move_to_main_activity(1, input_et.getText().toString());

            }
        });

        // if skip button is pressed - directs to the main activity with no additional args
        skip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move_to_main_activity(0, CLEAR);
            }
        });

    }

    /**
     * This function will direct the user to the main activity after clicking the buttons.
     * if the user inserted user name, the mode parameter will be set to 1 and this function
     * will send the user input to the main activity.
     * else, the mode will be set to 0 and no arguments will be passed.
     */
    private void move_to_main_activity(int mode, String user_name)
    {
        if (mode == 0)
        {
            Intent i = new Intent(Configure_name.this, MainActivity.class);
            startActivity(i);
        }
        else {
            Intent i = new Intent(Configure_name.this, MainActivity.class);
            Bundle args = new Bundle();
            args.putString(USER_TAG, user_name);
            i.putExtras(args);
            startActivity(i);
        }
        // the users will not be navigated again to the "Configure name" screen.
        finish();
    }
}

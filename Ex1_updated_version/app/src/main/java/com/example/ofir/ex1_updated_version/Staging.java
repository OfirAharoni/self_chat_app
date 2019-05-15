package com.example.ofir.ex1_updated_version;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Staging extends AppCompatActivity {

    private final String USER_TAG = "username";
    private final String MAIN_ACTIVITY = "MainActivity";
    private final String CONFIGURE_ACTIVITY = "Configure_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staging);

        DataBaseLoad dataBaseLoad = new DataBaseLoad(getApplicationContext());
        dataBaseLoad.staging = this;
        dataBaseLoad.execute(1);
    }


    public void switchActivity(String activityName, String user_name)
    {
        if (activityName.equals(CONFIGURE_ACTIVITY))
        {
            Intent intent = new Intent(this, Configure_name.class);
            startActivity(intent);
        }
        else if (activityName.equals(MAIN_ACTIVITY))
        {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(USER_TAG, user_name);
            startActivity(intent);
        }
        finish();
    }
}

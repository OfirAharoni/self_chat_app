package com.example.ofir.ex1_updated_version;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Question_dialog_box extends DialogFragment
{

    private final String MSG_TAG = "msg";
    private final int NA_TAG = -99;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.delete_dialog_box, null);
        Button del_btn = view.findViewById(R.id.del_btn);
        TextView device_et = view.findViewById(R.id.device_et);
        TextView ts_et = view.findViewById(R.id.ts_et);

        Bundle args = getArguments();
        final int selected_msg = args.getInt(MSG_TAG, NA_TAG);
        Message msg = MainActivity.messages.get(selected_msg);
        // set text in the edit text
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        Date date = new Date(Long.parseLong(msg.getTimestamp()));
        device_et.setText(msg.getDevice_name());
        ts_et.setText(sf.format(date));


        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Removed the selected message from DB
                Message message_to_rm = MainActivity.messages.get(selected_msg);
                DataBaseDelete dataBaseDelete = new DataBaseDelete();
                dataBaseDelete.execute(message_to_rm.getContent(), message_to_rm.getId(),
                message_to_rm.getTimestamp(), message_to_rm.getDevice_name());
                MainActivity.messages.remove(message_to_rm);

                // update adapter
                MainActivity.recyclerViewAdapter.notifyDataSetChanged();
                dismiss();
            }
        });

        return view;
    }
}

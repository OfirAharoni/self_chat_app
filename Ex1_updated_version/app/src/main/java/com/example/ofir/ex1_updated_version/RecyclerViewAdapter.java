package com.example.ofir.ex1_updated_version;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Ofir on 25/03/2019.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private final String DELETE_WARNING = "Delete message warning";
    private final String MESSAGE_TAG = "msg";


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_layout,
                parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textView.setText(MainActivity.messages.get(position).getContent());
        holder.textView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                // Create fragment in order to present additional info
                Question_dialog_box question_dialog_box = new Question_dialog_box();
                Bundle args = new Bundle();
                args.putInt(MESSAGE_TAG,  holder.getAdapterPosition());
                question_dialog_box.setArguments(args);

                FragmentManager manager =
                        ((AppCompatActivity)v.getContext()).getFragmentManager();
                question_dialog_box.show(manager, DELETE_WARNING);

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return MainActivity.messages.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
            TextView textView;

        ViewHolder(final View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.custom_tv);

        }
    }
}

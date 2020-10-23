package com.vijayetar.mytasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> { // do this part first
    public ArrayList<Task> allMyTasks;
    public OnInteractWithTaskListener listener;

    public TaskAdapter(ArrayList<Task> allMyTasks, OnInteractWithTaskListener listener){
        this.allMyTasks = allMyTasks;
        this.listener = listener;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder { // do this second
        public Task task;
        public View taskView;

        public TaskViewHolder(@NonNull View taskView ) {
            super(taskView);
            this.taskView = taskView;
        }

    }
    public static interface OnInteractWithTaskListener {
        public void taskListener(Task task);
    }

    @NonNull
    @Override  // important when our viewholder comes into existence
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task, parent, false);
        final TaskViewHolder viewHolder = new TaskViewHolder(view);
        view.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                System.out.println("this si view holder title" + viewHolder.task.getTitle());
                // Goal: go to a detail page about that cheap thing
                // TODO: clicking 2: assume the Activity listener exists and try to call it
                listener.taskListener(viewHolder.task);
            }
        });
        return viewHolder;
    }

    @Override  // takes care of the fragment with a class attached
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) { // this part will be from the automatic extension
        holder.task = allMyTasks.get(position);
        TextView titleTextView = holder.taskView.findViewById(R.id.titleTV);
        TextView bodyTextView = holder.taskView.findViewById(R.id.bodyTV);
        TextView stateTextView = holder.taskView.findViewById(R.id.stateTV);
        titleTextView.setText(holder.task.getTitle());
        bodyTextView.setText(holder.task.getBody());
        stateTextView.setText(holder.task.getState());

    }

    @Override  // these are the number of fragments to show on the screen and allows it to be dynamic
    public int getItemCount() {
//        return 10;  // make it return the length of the list instead of 0!!!!!
         return allMyTasks.size();
    }


}

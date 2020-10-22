package com.vijayetar.mytasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> { // do this part first

    @NonNull
    @Override  // important when our viewholder comes into existence
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task, parent, false);
        return null;
    }

    @Override  // takes care of the fragment with a class attached
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) { // this part will be from the automatic extension

    }

    @Override  // these are the number of fragments to show on the screen and allows it to be dynamic
    public int getItemCount() {
        return 0;  // make it return the length of the list instead of 0
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder { // do this second

        public TaskViewHolder(@NonNull View itemView ) {
            super(itemView);
        }

    }
}

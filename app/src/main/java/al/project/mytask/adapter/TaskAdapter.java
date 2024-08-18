package al.project.mytask.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import al.project.mytask.R;
import al.project.mytask.activity_detail_task;
import al.project.mytask.data.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{
    private List<Task> taskList;
    public TaskAdapter(List<Task> taskList){
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_task, parent,false);
        return new TaskViewHolder(view);
    }

    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position){
        Task task = taskList.get(position);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), activity_detail_task.class);
            intent.putExtra("taskId", task.getTaskId());
            intent.putExtra("taskTitle", task.getTaskName());
            intent.putExtra("taskDate", task.getCalender());
            intent.putExtra("taskCategory", task.getCategory());
            intent.putExtra("taskDescription", task.getDescription());
            view.getContext().startActivity(intent);
        });
        holder.taskData.setText(task.getTaskName());
        holder.taskDate.setText(task.getCalender());
    }

    public int getItemCount(){
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder{
        TextView taskData, taskDate;

        public TaskViewHolder(View itemView){
            super(itemView);
            taskData = itemView.findViewById(R.id.tvTaskTitle);
            taskDate = itemView.findViewById(R.id.tvTaskDate);
        }
    }
}

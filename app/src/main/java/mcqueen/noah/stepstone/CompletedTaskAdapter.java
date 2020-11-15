package mcqueen.noah.stepstone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CompletedTaskAdapter extends RecyclerView.Adapter<CompletedTaskAdapter.TaskViewHolder> {
    final public List<Task> tasks;

    public CompletedTaskAdapter() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
        notifyDataSetChanged();
    }

    @Override public int getItemCount() { return tasks.size(); }
    @Override public int getItemViewType(final int position) { return R.layout.task_card; }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_task_cardbar, parent,false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedTaskAdapter.TaskViewHolder holder, int position) {
        holder.bindData(tasks.get(position));
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder{
        final private TextView taskText;

        public TaskViewHolder(View itemView) {
            super(itemView);

            CardView taskCard = itemView.findViewById(R.id.task_card_completeUnit);
            taskText = taskCard.findViewById(R.id.completedCard_description);
        }

        public void bindData(final Task task) {
            taskText.setText(task.getDescription());
        }
    }
}


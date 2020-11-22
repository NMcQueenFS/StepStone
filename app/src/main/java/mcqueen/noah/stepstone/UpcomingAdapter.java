package mcqueen.noah.stepstone;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mcqueen.noah.stepstone.primitives.Task;

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.TaskViewHolder> {
    final List<Task> tasks;

    public UpcomingAdapter() {
        tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.task_card;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_card, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bindData(tasks.get(position));
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        final private TextView taskText, taskPriority, taskDueDate;
        Date dueDate;

        public TaskViewHolder(View itemView) {
            super(itemView);

            CardView taskCard = itemView.findViewById(R.id.task_card_completeUnit);
            taskText = taskCard.findViewById(R.id.completedCard_description);
            taskPriority = taskCard.findViewById(R.id.task_priority_display);
            taskDueDate = taskCard.findViewById(R.id.taskCard_dueDate_display);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }

        public void bindData(final Task task) {
            taskText.setText(task.getDescription());
            dueDate = task.getDueDate();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            sdf.applyPattern("MM/dd/yyyy");
            String dueDateString = sdf.format(dueDate);

            taskDueDate.setText(dueDateString);

            /*
            String[] desc = Resources.getSystem().getStringArray(R.array.descriptions);
            String[] color = Resources.getSystem().getStringArray(R.array.colors);

            taskPriority.setText(desc[task.getPriority()]);
            taskPriority.setBackgroundColor(Color.parseColor(color[task.getPriority()]));

             */

            switch (task.getPriority()) {
                case 0:
                    taskPriority.setText("Very Low");
                    taskPriority.setBackgroundColor(Color.parseColor("#3FE0D0")); //Light blue
                    break;
                case 1:
                    taskPriority.setText("Low");
                    taskPriority.setBackgroundColor(Color.parseColor("#008000")); //Green
                    break;
                case 2:
                    taskPriority.setText("Medium");
                    taskPriority.setBackgroundColor(Color.parseColor("#FFFF00")); //Yellow
                    break;
                case 3:
                    taskPriority.setText("High");
                    taskPriority.setBackgroundColor(Color.parseColor("#FFA500")); //Orange
                    break;
                case 4:
                    taskPriority.setText("Very High");
                    taskPriority.setBackgroundColor(Color.parseColor("#FF0000")); //Red
                    break;
                case 5:
                    taskPriority.setText("Urgent");
                    taskPriority.setBackgroundColor(Color.parseColor("#FF0000")); //Red
                    break;
                default:
                    taskPriority.setText("Broken!");
                    taskPriority.setBackgroundColor(Color.parseColor("#FF6978")); //Pink
                    break;

            }
        }
    }
}


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
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mcqueen.noah.stepstone.primitives.Task;

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.TaskViewHolder> {
    List<Task> tasks;

    public UpcomingAdapter() {
        tasks = new ArrayList<>();
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    @Override public int getItemCount() { return tasks.size(); }
    @Override public int getItemViewType(final int position) { return R.layout.task_card; }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_card, parent,false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bindData(tasks.get(position));
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder{
        final private TextView taskText, taskPriority, taskDueDate, taskChildCount;
        Date dueDate;
        private int priority;

        public TaskViewHolder(View itemView) {
            super(itemView);

            CardView taskCard = itemView.findViewById(R.id.task_card_completeUnit);
            taskText = taskCard.findViewById(R.id.completedCard_description);
            taskPriority = taskCard.findViewById(R.id.task_priority_display);
            taskDueDate = taskCard.findViewById(R.id.taskCard_dueDate_display);
            taskChildCount = taskCard.findViewById(R.id.taskCard_childCount_Display);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { }
            });
        }

        public void bindData(final Task task) {
            taskText.setText(task.getDescription());
            priority = task.getPriority();
            dueDate = task.getDueDate();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            sdf.applyPattern("MM/dd/yyyy");
            String dueDateString =sdf.format(dueDate);

            taskDueDate.setText(dueDateString);
            taskChildCount.setText("0 SubTasks");

            switch (task.getPriority())
            {
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

    @SuppressWarnings("SpellCheckingInspection")
    static class CustomComparator implements Comparator<Task> {
        static final int PRIORITYDOWN = 0, PRIORITYUP = 1, DUEDOWN = 2, DUEUP = 3, DESCDOWN = 4, DESCUP = 5;
        final int type;

        public CustomComparator(int type) { this.type = type; }

        @Override
        public int compare(Task first, Task second) {
            if (type == PRIORITYUP) { return Integer.compare(first.getPriority(), second.getPriority()); }
            if (type == PRIORITYDOWN) { return Integer.compare(second.getPriority(), first.getPriority()); }
            if (type == DESCUP) { return first.getDescription().compareTo(second.getDescription()); }
            if (type == DESCDOWN) { return second.getDescription().compareTo(first.getDescription()); }
            if (type == DUEUP) { return compareDueDate(first.getDueDate(),second.getDueDate()); }
            if (type == DUEDOWN) { return compareDueDate(second.getDueDate(),first.getDueDate()); }
            else return 0;
        }

        private int compareDueDate(Date first, Date second) {
            if (first.before(second)) return 1;
            else if (second.before(first)) return -1;
            else return 0;
        }
    }

    public void TaskSort(int type) {
        tasks.sort(new CustomComparator(type));
    }
}


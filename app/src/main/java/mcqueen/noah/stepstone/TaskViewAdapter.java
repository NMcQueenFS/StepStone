package mcqueen.noah.stepstone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class TaskViewAdapter extends RecyclerView.Adapter {
    public List<Task> tasks = new ArrayList<>();

    public TaskViewAdapter(final List<Task> tasks) {
        if (tasks != null) { this.tasks.addAll(tasks); }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent,false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        ((TaskViewHolder) holder).bindData(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.task_card;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public List<Task> getTaskList()
    {
        return tasks;
    }

    public void TaskSort(int type) {
        tasks.sort(new CustomComparator(type));
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
            String pattern = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            if (first.before(second)) return 1;
            else if (second.before(first)) return -1;
            else return 0;
        }
    }
}


package mcqueen.noah.stepstone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskViewAdapter extends RecyclerView.Adapter {
    private List<Task> tasks = new ArrayList<>();

    public TaskViewAdapter(final List<Task> tasks) {
        if (tasks != null) { this.tasks.addAll(tasks); }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent,false);
        return new TaskViewHolder(view);
    }

    @NonNull
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
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

    public List<Task> addTask(Task task) {
        tasks.add(task);
        return tasks;
    }

    public void TaskSort(int type) {
        tasks.sort(new CustomComparator(type));
        }

    @SuppressWarnings("SpellCheckingInspection")
    static class CustomComparator implements Comparator<Task> {
        static final int PRIORITYDOWN = 0, PRIORITYUP = 1, DUEDOWN = 2, DUEUP = 3, DESCDOWN = 4, DESCUP = 5;
        int type;

        public CustomComparator(int type) { this.type = type; }

        @Override
        public int compare(Task first, Task second) {
            if (type == PRIORITYDOWN) { return Integer.compare(first.getPriority(), second.getPriority()); }
            if (type == PRIORITYUP) { return Integer.compare(second.getPriority(), first.getPriority()); }
            else return 0;
        }
    }
}


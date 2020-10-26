package mcqueen.noah.stepstone;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class TaskViewAdapter extends RecyclerView.Adapter<TaskViewAdapter.TaskViewHolder> {
    public List<Task> tasks;
    protected static TaskViewAdapter thisTaskAdapter;

    public TaskViewAdapter(List<Task> givenTasks) {
        this.tasks = new ArrayList<>();
        this.tasks.addAll(givenTasks);
        thisTaskAdapter = this;
    }

    public List<Task> getTaskList()
    {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }
    public void deleteItem(int position) {
        tasks.remove(position);
        notifyItemRemoved(position);
    }

    @Override public int getItemCount() { return tasks.size(); }
    @Override public int getItemViewType(final int position) { return R.layout.task_card; }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_card, parent,false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewAdapter.TaskViewHolder holder, int position) {
        holder.bindData(tasks.get(position));
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder{
        final private TextView taskText, taskPriority, taskDueDate;
        Date dueDate;
        private int priority;

        public TaskViewHolder(View itemView) {
            super(itemView);

            CardView taskCard = itemView.findViewById(R.id.task_card_completeUnit);
            taskText = taskCard.findViewById(R.id.task_description_textBox);
            taskPriority = taskCard.findViewById(R.id.task_priority_display);
            taskDueDate = taskCard.findViewById(R.id.taskCard_dueDate_display);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    Intent intent = new Intent(v.getContext(), TaskModifierScreen.class);
                    Bundle extras = new Bundle();
                    extras.putString("TASK_DESCRIPTION", taskText.getText().toString());
                    extras.putInt("TASK_PRIORITY", priority);
                    extras.putBoolean("TASK_EXISTS", true);
                    extras.putInt("TASK_POS_IN_LIST", position);
                    extras.putLong("TASK_DUE_DATE", dueDate.getTime());
                    intent.putExtras(extras);
                    ((Activity) v.getContext()).startActivityForResult(intent, 2);
                }
            });
        }

        public void bindData(final Task task) {
            taskText.setText(task.getDescription());
            priority = task.getPriority();
            dueDate = task.getDueDate();
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern("MM/dd/yyyy");
            String dueDateString =sdf.format(dueDate);

            taskDueDate.setText(dueDateString);

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
                case 6:
                    taskPriority.setText("Overdue");
                    taskPriority.setBackgroundColor(Color.parseColor("#FF0000")); //Red
                    break;
                default:
                    taskPriority.setText("Broken!");
                    taskPriority.setBackgroundColor(Color.parseColor("#FF6978")); //Pink
                    break;
            }
        }
    }

    public static class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
        private final ColorDrawable background;

        public SwipeToDeleteCallback(TaskViewAdapter adapter) {
            super(0, ItemTouchHelper.LEFT);
            thisTaskAdapter = adapter;
            background = new ColorDrawable(Color.RED);
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            View itemView = viewHolder.itemView;
            int backgroundCornerOffset = 20;

            if (dX > 0) { // Swiping to the right
                background.setBounds(itemView.getLeft(), itemView.getTop(),
                        itemView.getLeft() + ((int) dX) + backgroundCornerOffset,
                        itemView.getBottom());
            } else if (dX < 0) { // Swiping to the left
                background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                        itemView.getTop(), itemView.getRight(), itemView.getBottom());
            } else { // view is unSwiped
                background.setBounds(0, 0, 0, 0);
            }
            background.draw(c);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            thisTaskAdapter.deleteItem(position);
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


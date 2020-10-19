package mcqueen.noah.stepstone;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    final private TextView taskText, taskPriority, taskDueDate;
    Date dueDate;
    private int priority;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);

        CardView taskCard = itemView.findViewById(R.id.task_card_completeUnit);
        taskText = taskCard.findViewById(R.id.task_description_textBox);
        taskPriority = taskCard.findViewById(R.id.task_priority_display);
        taskDueDate = taskCard.findViewById(R.id.taskCard_dueDate_display);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getLayoutPosition();
                Intent intent = new Intent(v.getContext(),TaskModifierScreen.class);
                Bundle extras = new Bundle();
                extras.putString("TASK_DESCRIPTION",taskText.getText().toString());
                extras.putInt("TASK_PRIORITY",priority);
                extras.putBoolean("TASK_EXISTS",true);
                extras.putInt("TASK_POS_IN_LIST",position);
                extras.putLong("TASK_DUE_DATE", dueDate.getTime());
                intent.putExtras(extras);
                ((Activity)v.getContext()).startActivityForResult(intent, 2);
            }
        });
    }

    public void bindData(final Task task) {
        taskText.setText(task.getDescription());
        priority = task.getPriority();
        dueDate = task.getDueDate();
        taskDueDate.setText(task.getDueDate().toString());
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

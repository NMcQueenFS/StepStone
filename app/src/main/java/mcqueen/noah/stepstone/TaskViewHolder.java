package mcqueen.noah.stepstone;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    private CardView taskCard;
    private TextView taskText;
    private TextView taskPriority;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        taskCard = (CardView)itemView.findViewById(R.id.task_card_completeUnit);
        taskText = (TextView)taskCard.findViewById(R.id.task_description_textBox);
        taskPriority = (TextView)taskCard.findViewById(R.id.task_priority_display);

    }

    public void bindData(final Task task) {
        taskText.setText(task.getDescription());
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
                taskPriority.setText("Borked!!!");
                taskPriority.setBackgroundColor(Color.parseColor("#FF6978")); //Pink
                break;
        }
    }
}

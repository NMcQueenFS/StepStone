package mcqueen.noah.stepstone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.w3c.dom.Text;

public class TaskCardAdapter extends ArrayAdapter<taskCard> {
    public TaskCardAdapter(Context context) {
        super(context,R.layout.task_card);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.task_card, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        taskCard thisTask = getItem(position);

        holder.taskDescription.setText(thisTask.description);

        return convertView;
    }

    static class ViewHolder {
        TextView taskDescription;

        ViewHolder(View view){
            taskDescription = (TextView)view.findViewById(R.id.taskText);
        }
    }


}

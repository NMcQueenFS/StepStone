package mcqueen.noah.stepstone;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class taskCard_creator extends DialogFragment {

    EditText datePickText;
    public EditText taskDescriptionBox;
    final Calendar datePickCalendar = Calendar.getInstance();
    public String taskDescription;

    public interface CreatorDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
        public void setTaskDescription(String description);
        public void getTaskDescription(String description);

        ;
    }

    CreatorDialogListener dialogListener;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            dialogListener = (CreatorDialogListener)context;
        }catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement CreatorDialogListener.");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.task_createnew, null))
                .setPositiveButton("Create Task", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //TODO: Positive action for task creator dialog box
                        dialogListener.onDialogPositiveClick(taskCard_creator.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        taskCard_creator.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.task_createnew,container,false);

        datePickText = (EditText)dialogView.findViewById(R.id.dueDate_display);
        datePickText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerBuilder();
            }
        });

        taskDescriptionBox = (EditText)dialogView.findViewById(R.id.taskDescription_field);
        taskDescriptionBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                taskDescriptionBox.setText(s.toString());
                taskDescription = taskDescriptionBox.getText().toString();
                updateDesciption(taskDescription);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return dialogView;
    }

    private void DatePickerBuilder() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                datePickCalendar.set(Calendar.YEAR, year);
                datePickCalendar.set(Calendar.MONTH, month);
                datePickCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateText(datePickText);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date,
                datePickCalendar.get(Calendar.YEAR), datePickCalendar.get(Calendar.MONTH), datePickCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void updateDateText(EditText text) {
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        text.setText(sdf.format(datePickCalendar.getTime()));
    }

    public void updateDesciption(String taskDescription) {
        dialogListener.setTaskDescription(taskDescription);
    }
}


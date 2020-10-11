package mcqueen.noah.stepstone;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

public class taskCard_creator extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public interface CreatorDialogListener {
        void onDialogPositiveClick(String description);
    }
    private CreatorDialogListener dialogListener;

    EditText datePickText;
    public EditText taskDescriptionBox;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try { dialogListener = (CreatorDialogListener)context; }
        catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement CreatorDialogListener.");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        dialogListener = null;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        //taskDescriptionBox = (EditText)getActivity().findViewById(R.id.taskDescription_field);

        builder.setView(inflater.inflate(R.layout.task_createnew, null))
                .setPositiveButton("Create Task", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialogListener.onDialogPositiveClick(taskDescriptionBox.getText().toString());
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
        final View dialogView = inflater.inflate(R.layout.task_createnew,container,false);

        datePickText = (EditText) dialogView.findViewById(R.id.dueDate_display);
        datePickText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dateDialog = new DatePickerFragment();
                dateDialog.setTargetFragment(taskCard_creator.this,0);
                dateDialog.show(getActivity().getSupportFragmentManager(), "datePicker");

            }
        });
        taskDescriptionBox = (EditText)dialogView.findViewById(R.id.taskDescription_field);
        return dialogView;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) { // what should be done when a date is selected
        StringBuilder sb = new StringBuilder().append(dayOfMonth).append("/").append(monthOfYear + 1);
        String formattedDate = sb.toString();
        datePickText.setText(formattedDate);
    }
}


package com.example.agecalculator;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.DialogFragment;
import android.widget.DatePicker;
import android.app.Fragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month + 1, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        int mYear = year;
        int mMonth = monthOfYear + 1;
        int mDay = dayOfMonth;

        String selectedDate = mMonth + " - " + mDay + " - " + mYear;
        //Toast.makeText(getActivity(), "Selected " + selectedDate, Toast.LENGTH_LONG).show();
        Intent i = new Intent();
        i.putExtra("RETURNED_DATE",selectedDate);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
    }
}
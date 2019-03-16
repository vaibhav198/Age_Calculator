package com.example.agecalculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.Weeks;

import java.util.Calendar;



public class AgeCalculatorFragment extends Fragment {
    private TextView todayDate;
    private ImageView todayDateSelector;
    private DatePicker todayDateDialog;
    private TextView yourBirthdayDate;
    private ImageView yourBirthdayDateSelector;
    private int year;
    private int month;
    private int day;
    public static final int DATE_PICKER_FRAGMENT = 1;
    public static final int SECOND_DATE_PICKER_FRAGMENT = 2;
    private TextView currentBirthdayYear;
    private TextView currentBirthdayMonth;
    private TextView currentBirthdayDay;
    private TextView nextBirthdayYear;
    private TextView nextBirthdayMonth;
    private TextView nextBirthdayDay;
    private TextView totalYearResult;
    private TextView totalMonthResult;
    private TextView totalWeekResult;
    private TextView totalDayResult;
    private TextView totalHourResult;
    private TextView totalMinuteResult;
    private TextView totalSecondResult;
    public AgeCalculatorFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_age_calculator, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        currentBirthdayYear = (TextView)view.findViewById(R.id.years_in_age);
        currentBirthdayMonth = (TextView)view.findViewById(R.id.months_in_age);
        currentBirthdayDay = (TextView)view.findViewById(R.id.days_in_age);
        nextBirthdayYear = (TextView)view.findViewById(R.id.years_in_birthday);
        nextBirthdayMonth = (TextView)view.findViewById(R.id.months_in_birthday);
        nextBirthdayDay = (TextView)view.findViewById(R.id.days_in_birthday);
        totalYearResult = (TextView)view.findViewById(R.id.total_years_result);
        totalMonthResult = (TextView)view.findViewById(R.id.total_months_result);
        totalWeekResult = (TextView)view.findViewById(R.id.total_weeks_result);
        totalDayResult = (TextView)view.findViewById(R.id.total_days_result);
        totalHourResult = (TextView)view.findViewById(R.id.total_hours_result);
        totalMinuteResult = (TextView)view.findViewById(R.id.total_minutes_result);
        totalSecondResult = (TextView)view.findViewById(R.id.total_seconds_result);
        todayDate = (TextView)view.findViewById(R.id.display_today_date);
        todayDateSelector = (ImageView)view.findViewById(R.id.set_date_icon);
        yourBirthdayDate = (TextView)view.findViewById(R.id.display_birthday_date);
        yourBirthdayDateSelector = (ImageView)view.findViewById(R.id.set_birthday_icon);
        Button calculateButton = (Button)view.findViewById(R.id.button_calculate);
        Button clearButton = (Button)view.findViewById(R.id.button_clear);
        todayDate.setText(setCurrentDateOnView());
        yourBirthdayDate.setText(setCurrentDateOnView());
        todayDateSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerDialog = new DatePickerFragment();
                datePickerDialog.setTargetFragment(AgeCalculatorFragment.this, DATE_PICKER_FRAGMENT);
                datePickerDialog.show(getFragmentManager(), "DATE TODAY");
            }
        });
        yourBirthdayDateSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerDialog = new DatePickerFragment();
                datePickerDialog.setTargetFragment(AgeCalculatorFragment.this, SECOND_DATE_PICKER_FRAGMENT);
                datePickerDialog.show(getFragmentManager(), "BIRTHDAY TODAY");
            }
        });
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getTodayDate = todayDate.getText().toString();
                String getBirthdayDate = yourBirthdayDate.getText().toString();
                DateTime todayDateTime = convertToDateTime(getTodayDate);
                DateTime birthdayDateTime = convertToDateTime(getBirthdayDate);
                displayCurrentBirthday(todayDateTime, birthdayDateTime);
                displayNextBirthday(todayDateTime, birthdayDateTime);
                displayAgeAnalysis(todayDateTime, birthdayDateTime);
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBirthdayDay.setText(Html.fromHtml("<h6>Days</h6>"));
                currentBirthdayMonth.setText(Html.fromHtml("<h6>Months</h6>"));
                currentBirthdayYear.setText(Html.fromHtml("<h6>Years</h6>"));
                nextBirthdayDay.setText(Html.fromHtml("<h6>Days</h6>"));
                nextBirthdayMonth.setText(Html.fromHtml("<h6>Months</h6>"));
                nextBirthdayYear.setText(Html.fromHtml("<h6>Years</h6>"));
                totalDayResult.setText("");
                totalWeekResult.setText("");
                totalMonthResult.setText("");
                totalYearResult.setText("");
                totalHourResult.setText("");
                totalMinuteResult.setText("");
                totalSecondResult.setText("");
            }
        });
        return view;
    }
    private void displayAgeAnalysis(DateTime dateToday, DateTime birthdayDate){
        Period dateDifferencePeriod = displayBirthdayResult(dateToday, birthdayDate);
        int getDateInDays = dateDifferencePeriod.getDays();
        int getDateInWeeks = Weeks.weeksBetween(new DateTime(birthdayDate), new DateTime(dateToday)).getWeeks();;
        int getDateInMonths = dateDifferencePeriod.getMonths();
        int getDateInYears = dateDifferencePeriod.getYears();
        int mDay = getDateInWeeks * 7;
        int mMonth = getDateInMonths + (getDateInYears * 12);
        int hours = mDay * 24;
        int minutes = mDay * 24 * 60;
        int seconds = mDay * 24 * 60 * 60;
        totalDayResult.setText(Html.fromHtml(String.valueOf(mDay)));
        totalWeekResult.setText(Html.fromHtml(String.valueOf(getDateInWeeks)));
        totalMonthResult.setText(Html.fromHtml(String.valueOf(mMonth)));
        totalYearResult.setText(Html.fromHtml(String.valueOf(getDateInYears)));
        totalHourResult.setText(Html.fromHtml(String.valueOf(hours)));
        totalMinuteResult.setText(Html.fromHtml(String.valueOf(minutes)));
        totalSecondResult.setText(Html.fromHtml(String.valueOf(seconds)));
    }
    private void displayNextBirthday(DateTime dateToday, DateTime birthdayDate){
        Calendar mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        DateTime nextBirthday = birthdayDate.withYear(year);
        Toast.makeText(getActivity(), "Birthday " + nextBirthday.getYear(), Toast.LENGTH_LONG).show();
        Period dateDifferencePeriod = displayBirthdayResult(nextBirthday, dateToday);
        int getDateInDays = dateDifferencePeriod.getDays();
        int getDateInMonths = dateDifferencePeriod.getMonths();
        int getDateInYears = dateDifferencePeriod.getYears();
        nextBirthdayDay.setText(Html.fromHtml("<h6>Days</h6>" + String.valueOf(getDateInDays)));
        nextBirthdayMonth.setText(Html.fromHtml("<h6>Months</h6>" + String.valueOf(getDateInMonths)));
        nextBirthdayYear.setText(Html.fromHtml("<h6>Years</h6>" + String.valueOf(getDateInYears)));
    }
    private void displayCurrentBirthday(DateTime dateToday, DateTime birthdayDate){
        Period dateDifferencePeriod = displayBirthdayResult(dateToday, birthdayDate);
        int getDateInDays = dateDifferencePeriod.getDays();
        int getDateInMonths = dateDifferencePeriod.getMonths();
        int getDateInYears = dateDifferencePeriod.getYears();
        currentBirthdayDay.setText(Html.fromHtml("<h6>Days</h6>" + String.valueOf(getDateInDays)));
        currentBirthdayMonth.setText(Html.fromHtml("<h6>Months</h6>" + String.valueOf(getDateInMonths)));
        currentBirthdayYear.setText(Html.fromHtml("<h6>Years</h6>" + String.valueOf(getDateInYears)));
    }
    private Period displayBirthdayResult(DateTime dateToday, DateTime birthdayDate){
        Period dateDifferencePeriod = new Period(birthdayDate, dateToday, PeriodType.yearMonthDayTime());
        return dateDifferencePeriod;
    }
    private DateTime convertToDateTime(String stringToConvert){
//        String[] newStringArray = convertStringToArray(stringToConvert);
        String[] newStringArray = stringToConvert.split("-",3);
        int month = Integer.parseInt(newStringArray[0].trim());
        int day = Integer.parseInt(newStringArray[1].trim());
        int year = Integer.parseInt(newStringArray[2].trim());
        LocalDate mLocalDate = new LocalDate(year, month, day);
        DateTime firstDateTime = mLocalDate.toDateTime(LocalTime.fromDateFields(mLocalDate.toDate()));
        return firstDateTime;
    }
    private String[] convertStringToArray(String stringToConvert){
        String[] newStringArray = stringToConvert.split("-");
        return newStringArray;
    }
    public String setCurrentDateOnView() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        StringBuilder displayStringBuilder = new StringBuilder().append(month + 1).append("-").append(day).append("-").append(year).append(" ");
        return displayStringBuilder.toString();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case DATE_PICKER_FRAGMENT:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String resultDate = bundle.getString("RETURNED_DATE", "error");
                    todayDate.setText(resultDate);
                }
                break;
            case SECOND_DATE_PICKER_FRAGMENT:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String resultDate = bundle.getString("RETURNED_DATE", "error");
                    yourBirthdayDate.setText(resultDate);
                }
                break;
        }
    }
}

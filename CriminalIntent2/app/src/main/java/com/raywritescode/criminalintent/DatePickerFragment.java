package com.raywritescode.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by root on 08.08.16.
 */
public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE =
            "criminalintent.android.bignerdranch.com.criminalintent.date";

    private Date mDate;



    public static DatePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE, date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        mDate = (Date)getArguments().getSerializable(EXTRA_DATE);

        //создание обьекта Calendar для получения года. месяца и дня
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_date, null);

        DatePicker datePicker = (DatePicker)v.findViewById(R.id.dialog_date_datePicker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                //Преобразование года и месяца и дня в обьект date;

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(mDate);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                mDate = new GregorianCalendar(year, month, day, hour, minute).getTime();

                getArguments().putSerializable(EXTRA_DATE, mDate);
            }
        });


        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult(Activity.RESULT_OK);
                            }
                        })
                .create();
    }

    private void sendResult(int resultCode){
        if(getTargetFragment() == null)
            return;

        Intent i = new Intent();
        i.putExtra(EXTRA_DATE, mDate);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, i);
    }
}
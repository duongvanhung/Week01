package com.example.hung.newyorktimes.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends android.support.v4.app.DialogFragment implements DatePickerDialog.OnDateSetListener {

    Date date;

    public DatePickerFragment() {
        // empty constructor required for DialogFragment
    }



    public interface DatePickerFragmentListener {
        public void onDateSet(Date date);
    }

    private DatePickerFragmentListener mListener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the current time as the default values for the picker

        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public static DatePickerFragment newInstance(DatePickerFragmentListener listener) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setMdatePickerListener(listener);
        return fragment;
    }

    public DatePickerFragmentListener getMdatePickerListener() {
        return this.mListener;
    }

    public void setMdatePickerListener(DatePickerFragmentListener listener) {
        this.mListener = listener;
    }

    protected void notifyDatePickerListener(Date date) {
        if(this.mListener != null) {
            this.mListener.onDateSet(date);
        }
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        date  = c.getTime();
        notifyDatePickerListener(date);
    }

    public Date getDate() {
        return date;
    }

}

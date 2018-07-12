package com.example.hung.newyorktimes.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment  extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    Date date;

    public DatePickerFragment(){

    }

    public void show(FragmentManager supportFragmentManager, String date_picker) {
    }

    public interface DatePickerFragmentListener{
        public void onDateSet(Date date);

    }
    private DatePickerFragmentListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        return new DatePickerDialog(getActivity(),this,day,month,year);
    }
    public static DatePickerFragment newInstance(DatePickerFragmentListener listener){
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setMdatePickerListener(listener);
        return fragment;

    }
    public DatePickerFragmentListener getMdatePickerListener(){
        return this.mListener;
    }
    public void setMdatePickerListener(DatePickerFragmentListener listener){
        this.mListener = listener;
    }
    protected void notifyDatePickerListener(Date date){
        if(this.mListener != null){
            this.mListener.onDateSet(date);
        }
    }
    public void onDateSet(DatePicker view, int year, int month, int day){
        Calendar c = Calendar.getInstance();
        c.set(year,month,day);
        date = c.getTime();
        notifyDatePickerListener(date);

    }
    public Date getDate(){
        return date;
    }

}
package com.example.hung.newyorktimes.fragments;


import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.hung.newyorktimes.R;
import com.example.hung.newyorktimes.utils.ArticlesFilter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;


import static com.example.hung.newyorktimes.R.id.spSort;
import static com.example.hung.newyorktimes.utils.ArticlesConstants.BEGIN_DATE;
import static com.example.hung.newyorktimes.utils.ArticlesConstants.NEWS_DESK;
import static com.example.hung.newyorktimes.utils.ArticlesConstants.SORT;


public class FilterFragment extends DialogFragment implements TextView.OnEditorActionListener,DatePickerFragment.DatePickerFragmentListener{

    @BindView(R.id.begindates)
    EditText begindates;

    @BindView(spSort)
    Spinner spSortOrder;

    @BindView(R.id.tvArts)
    CheckBox cbArts;

    @BindView(R.id.tvFashionStyle)
    CheckBox cbFashionStyle;

    @BindView(R.id.tvSport)
    CheckBox cbSport;

    @BindView(R.id.btnClearFilter)
    Button btnClearFilter;

    @BindView(R.id.btnApplyFilter)
    Button btnApplyFilter;


    @BindString(R.string.Arts)
    String arts;


    @BindString(R.string.fashion_style)
    String fashionAndStyle;

    @BindString(R.string.Sports)
    String sports;

    ArticlesFilter mFilter;
    SimpleDateFormat dateFromPicker = new SimpleDateFormat("MM/dd/YYYY");
    SimpleDateFormat dateForQuery = new SimpleDateFormat("yyyyMMdd");


    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent){
        return false;
    }
    @Override
    public void onDateSet(Date date){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = dateFormatter.format(date);
        begindates.setText(strDate);
    }



    public interface SaveDialogListener{
        void onFinishEditDialog(String beginDate, String sortOrder, HashSet<String> newDeskValueSet);
    }
    public FilterFragment(){
        // Empty constructor is required for DialogFragment
    }
    public static FilterFragment newInstance(String date, String sortOrder, HashSet<String> newDeskValueSet){
        FilterFragment frag = new FilterFragment();

        Bundle args= new Bundle();
        args.putString(BEGIN_DATE, date);
        args.putString(SORT, sortOrder);
        args.putSerializable(NEWS_DESK, newDeskValueSet);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_filter, null);
        ButterKnife.bind(this, view);

        String beginDate = getArguments().getString(BEGIN_DATE);
        String sort = getArguments().getString(SORT);
        HashSet<String> newsDeskSelectedOptions = (HashSet<String>) getArguments().getSerializable(NEWS_DESK);

        if(beginDate != null){
            begindates.setText(convertDate(beginDate, dateForQuery,dateFromPicker));
        }
        if(sort != null){
            spSortOrder.setSelection(getIndex(spSortOrder,sort));
        }
        clearFilter();

        begindates.requestFocus();
        begindates.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showDatePickerDialog(v);
            }
        });

        cbArts.setChecked(false);
        cbFashionStyle.setChecked(false);
        cbSport.setChecked(false);

        if(newsDeskSelectedOptions != null){
            if(newsDeskSelectedOptions.contains(fashionAndStyle)){
                cbFashionStyle.setChecked(true);
            }
            if(newsDeskSelectedOptions.contains(arts)){
                cbArts.setChecked(true);
            }
            if (newsDeskSelectedOptions.contains(sports)){
                cbSport.setChecked(true);
            }
        }

        btnApplyFilter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String beginDate = null;
                String sort = null;

                String datePickerDate = begindates.getText().toString();
                if(datePickerDate != null && !datePickerDate.isEmpty()){
                    beginDate = convertDate(datePickerDate,dateFromPicker,dateForQuery);
                    mFilter.setBeginDate(convertDate(datePickerDate,dateFromPicker,dateForQuery));
                }

                if(spSortOrder.getSelectedItem() != null){
                    sort = spSortOrder.getSelectedItem().toString().toLowerCase();
                    mFilter.setSortOrder(sort);
                }
                HashSet<String> newsDeskValues = new HashSet<String>();

                if(cbArts.isChecked()){
                    newsDeskValues.add(arts);
                }
                if(cbFashionStyle.isChecked()){
                    newsDeskValues.add(fashionAndStyle);
                }
                if (cbSport.isChecked()){
                    newsDeskValues.add(sports);
                }
                SaveDialogListener listener = (SaveDialogListener) getActivity();
                listener.onFinishEditDialog(beginDate,sort, newsDeskValues);
                dismiss();
            }
        });

            btnClearFilter.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View view){
                    clearFilter();
            }
        });
        return view;

    }
    public void clearFilter(){
        mFilter = new ArticlesFilter();
        begindates.setText("");
        spSortOrder.setSelection(0);
        cbArts.setChecked(false);
        cbFashionStyle.setChecked(false);
        cbSport.setChecked(false);
    }
    public void showDatePickerDialog(View v) {
        DatePickerFragment fragment = DatePickerFragment.newInstance(this);
        fragment.show(getActivity().getSupportFragmentManager(), "date_picker");
    }

    public String convertDate(String date,SimpleDateFormat origFormat, SimpleDateFormat targetFormat){
        if(date != null && !date.isEmpty()){
            try{
                Date dateObtained = origFormat.parse(date);
                return targetFormat.format(dateObtained);
                } catch (ParseException e){
                Log.d("ERROR", e.getMessage(), e);
            }
        }
        return null;
    }
    private int getIndex(Spinner spinner, String item){
        int index = 0;
        for(int i = 0; i < spinner.getCount();i++){
            if(spinner.getItemAtPosition(i).toString().equalsIgnoreCase(item)){
                index = i;
                break;
            }
        }
        return index;
    }
}
package com.example.hung.newyorktimes.fragments;

import android.app.DialogFragment;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hung.newyorktimes.R;
import com.example.hung.newyorktimes.utils.ArticlesFilter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import butterknife.BindString;
import butterknife.BindView;

import static com.example.hung.newyorktimes.R.id.spSort;

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
    Button cbClearFilter;

    @BindView(R.id.btnApplyFilter)
    Button cbApplyFilter;


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
    

}
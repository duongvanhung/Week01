package com.example.usphuong.thenewyorktimes.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;


import com.example.usphuong.thenewyorktimes.R;
import com.example.usphuong.thenewyorktimes.databinding.FragmentFilterBinding;
import com.example.usphuong.thenewyorktimes.models.SearchFilters;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFilterFragment extends DialogFragment{

    private static final String ARG_FILTERS = "filters";
    private static final String TAG = "NY: " + SearchFilterFragment.class.getName();

    private SearchFilters filters;

    private OnFragmentInteractionListener mListener;

    FragmentFilterBinding binding;


    @BindView(R.id.etBeginDate)
    EditText etBeginDate;

    @BindView(R.id.cbNoBeginDate)
    CheckBox cbIgnoreBeginDate;

    @BindView(R.id.btSave)
    Button btSave;

    @BindView(R.id.cbArts)
    CheckBox cbArts;

    @BindView(R.id.cbFashion)
    CheckBox cbFashion;

    @BindView(R.id.cbStyle)
    CheckBox cbStyle;

    @BindView(R.id.cbSport)
    CheckBox cbSport;


    @BindView(R.id.spSort)
    Spinner spSort;

    private Calendar calendar = Calendar.getInstance();
    private String displayDatePattern = "MM/dd/yy";
    private SimpleDateFormat displayDateFormat = new SimpleDateFormat(displayDatePattern, Locale.getDefault());



        View.OnClickListener saveClickListener = v ->  {
            // update search filters
            if (mListener != null) {

                if (cbIgnoreBeginDate.isChecked()) {
                    filters.update(spSort.getSelectedItem().toString().toLowerCase().equals(SearchFilters.SORT_OLDEST.toLowerCase()),
                            cbArts.isChecked(), cbFashion.isChecked(), cbSport.isChecked(), cbStyle.isChecked());
                } else {
                    Date date = new Date();

                    try {
                        date = displayDateFormat.parse(etBeginDate.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    filters.update(date, spSort.getSelectedItem().toString().toLowerCase().equals(SearchFilters.SORT_OLDEST.toLowerCase()),
                            cbArts.isChecked(), cbFashion.isChecked(), cbSport.isChecked(), cbStyle.isChecked());
                }

                mListener.onFinishDialog(filters);
            }
            dismiss();
    };


    public SearchFilterFragment() {
        // Required empty public constructor
    }


    public interface OnFragmentInteractionListener {
        void onFinishDialog(SearchFilters filters);
    }

    public static SearchFilterFragment newInstance(SearchFilters filters) {
        SearchFilterFragment fragment = new SearchFilterFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_FILTERS, Parcels.wrap(filters));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           filters = Parcels.unwrap(getArguments().getParcelable(ARG_FILTERS));

        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter, container, false);
        binding.setFilters(filters);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        Objects.requireNonNull(getDialog().getWindow()).requestFeature(Window.FEATURE_NO_TITLE);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final String sortOrder[] = Objects.requireNonNull(getActivity()).getResources().getStringArray(R.array.sort_order);

        //Set begin date
        Date date = null;
        try {
            date = SearchFilters.getQueryDateFormat().parse(filters.getBeginDateString());
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        etBeginDate.setText(displayDateFormat.format(date));


        DatePickerDialog.OnDateSetListener listener = (datePicker, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            etBeginDate.setText(displayDateFormat.format(calendar.getTime()));
        };
        etBeginDate.setOnClickListener(view12 -> {
            //Hide Keyboard
            InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).hideSoftInputFromWindow(view12.getWindowToken(), 0);

            new DatePickerDialog(getContext(), listener, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        boolean ignoreBeginDate = filters.isIgnoreBeginDate();
        cbIgnoreBeginDate.setChecked(ignoreBeginDate);

        if (ignoreBeginDate)
            etBeginDate.setEnabled(false);

        cbIgnoreBeginDate.setOnClickListener(view13 -> etBeginDate.setEnabled(!cbIgnoreBeginDate.isChecked()));

        for (int i = 0; i < sortOrder.length; i++) {
            if (sortOrder[i].toLowerCase().equals(filters.getSortOrder())) {
                spSort.setSelection(i);
                break;
            }
        }

        btSave.setOnClickListener(view1 -> {
            if (mListener != null) {

                if (cbIgnoreBeginDate.isChecked()) {
                    filters.update(spSort.getSelectedItem().toString().toLowerCase().equals(SearchFilters.SORT_OLDEST.toLowerCase()),
                            cbArts.isChecked(), cbFashion.isChecked(), cbSport.isChecked(), cbStyle.isChecked());
                } else {
                    Date date1 = new Date();

                    try {
                        date1 = displayDateFormat.parse(etBeginDate.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    filters.update(date1, spSort.getSelectedItem().toString().toLowerCase().equals(SearchFilters.SORT_OLDEST.toLowerCase()),
                            cbArts.isChecked(), cbFashion.isChecked(), cbStyle.isChecked(), cbSport.isChecked());
                }

                mListener.onFinishDialog(filters);
            }
            dismiss();
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}

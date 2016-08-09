package com.raywritescode.criminalintent;
import java.util.Date;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public  class CrimeFragment extends Fragment {

		public static final String EXTRA_CRIME_ID =
				"com.bignerdranch.android.criminalintent.crime_id";
		private static final String DIALOG_DATE = "date";
		private static final String DIALOG_TIME = "time";
		private static final int REQUEST_DATE = 0;
		private static final int REQUEST_TIME = 0;

		private Crime mCrime;
		private EditText mTitleField;
		private Button mDateButton;
		private Button mTimeButton;
		private CheckBox mSolvedCheckBox;

		public void updateDate() {
			Date d = mCrime.getDate();
			CharSequence c = DateFormat.format("EEEE, MMM dd, yyyy", d);
			CharSequence t = DateFormat.format("h:mm a", d);
			mDateButton.setText(c);
			mTimeButton.setText(t);
		}


		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);

			mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup parent,
								 Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.fragment_crime, parent, false);

			mTitleField = (EditText)v.findViewById(R.id.crime_title);
			mTitleField.setText(mCrime.getTitle());
			mTitleField.addTextChangedListener(new TextWatcher() {
				public void onTextChanged(
						CharSequence c, int start, int before, int count) {
					mCrime.setTitle(c.toString());
				}

				public void beforeTextChanged(
						CharSequence c, int start, int count, int after) {
					// Здесь намеренно оставлено пустое место
				}

				public void afterTextChanged(Editable c) {
					// И здесь тоже
				}
			});
			mDateButton = (Button)v.findViewById(R.id.crime_date);
			mDateButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					FragmentManager fm = getActivity()
							.getSupportFragmentManager();
					DatePickerFragment dialog = DatePickerFragment
							.newInstance(mCrime.getDate());
					dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
					dialog.show(fm, DIALOG_DATE);
				}
			});

			mTimeButton = (Button)v.findViewById(R.id.crime_time);
			mTimeButton.setOnClickListener(new View.OnClickListener(){
				public void onClick(View v){
					FragmentManager fm =getActivity()
							.getSupportFragmentManager();
					TimePickerFragment dialog = TimePickerFragment
							.newInstance(mCrime.getDate());
					dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
					dialog.show(fm, DIALOG_TIME);
				}
			});

			updateDate();

			mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
			mSolvedCheckBox.setChecked(mCrime.isSolved());
			mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// Назначение флага раскрытия преступления
					mCrime.setSolved(isChecked);
				}
			});
			return v;
		}


		public static CrimeFragment newInstance(UUID crimeId){
			Bundle args = new Bundle();
			args.putSerializable(EXTRA_CRIME_ID, crimeId);
			CrimeFragment fragment = new CrimeFragment();
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (resultCode != Activity.RESULT_OK) return;
			if (requestCode == REQUEST_DATE) {
				Date date = (Date)data
						.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
				mCrime.setDate(date);
				updateDate();
			} else if (requestCode == REQUEST_TIME) {
				Date date = (Date)data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
				mCrime.setDate(date);
				updateDate();
			}
		}

	}

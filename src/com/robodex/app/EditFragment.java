package com.robodex.app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.robodex.R;
import com.robodex.Robodex;
import com.robodex.data.DatabaseContract;
import com.robodex.request.BaseRequest;
import com.robodex.request.EditLocation;
import com.robodex.request.EditPerson;
import com.robodex.request.ServerContract.RequestField;

public class EditFragment extends SherlockFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {
	private static final String LOG_TAG = EditFragment.class.getSimpleName();
	private static final String STRING_DELIMITER = "<>]&"; // hack array<--->string

	interface Callbacks {
		void onInvalidEditType(int type, int id);
		void onNoDetailsToEdit(int type, int id);
		void onInvalidDetailsToEdit(int type, int id);
		void onSave(int type, int id);
    }

	/** Used temporarily during lifecycle methods. */
	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override public void onInvalidEditType(int type, int id) {}
		@Override public void onNoDetailsToEdit(int type, int id) {}
		@Override public void onInvalidDetailsToEdit(int type, int id) {}
		@Override public void onSave(int type, int id) {}
    };

    static final String ARG_EDIT_TYPE 	= "type_of_edit";
    static final String ARG_ITEM_ID 	= "item_id";

    static final int EDIT_TYPE_PERSON   = 1;
    static final int EDIT_TYPE_LOCATION = 2;

    private int mEditType;
    private int mItemId;
    private BaseRequest mRequest;

    private Callbacks mCallbacks = sDummyCallbacks;

    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static class Person {
    	String firstName = "", lastName = "",
    			address = "", city = "", state = "", zip = "",
    			phone1 = "", phone2 = "", email = "", notes = "";
    	int stateId = 0;
    	List<String> specialties = new ArrayList<String>(),
    			organizations = new ArrayList<String>();

    	EditText firstNameView, lastNameView, addressView,
    			cityView, zipView, phone1View, phone2View, emailView, notesView;
    	Spinner stateView;
    	TextView addSpecialty, addOrganization, infoToggle, nameView;

    	ViewGroup specialtiesContainer, organizationsContainer, infoContainer;

    	boolean hasInitialized = false, hasUpdated = false;

	    void initializeViews(View v) {
	    	nameView = (TextView) v.findViewById(R.id.name);
			firstNameView = (EditText) v.findViewById(R.id.firstName);
			lastNameView = (EditText) v.findViewById(R.id.lastName);
			specialtiesContainer = (ViewGroup) v.findViewById(R.id.specialtiesContainer);
			organizationsContainer = (ViewGroup) v.findViewById(R.id.organizationsContainer);
			addressView = (EditText) v.findViewById(R.id.address);
			cityView = (EditText) v.findViewById(R.id.city);
			stateView = (Spinner) v.findViewById(R.id.state);
			zipView = (EditText) v.findViewById(R.id.zip);
			phone1View = (EditText) v.findViewById(R.id.phone1);
			phone2View = (EditText) v.findViewById(R.id.phone2);
			emailView = (EditText) v.findViewById(R.id.email);
			notesView = (EditText) v.findViewById(R.id.notes);
			addSpecialty = (TextView) v.findViewById(R.id.addSpecialty);
			addOrganization = (TextView) v.findViewById(R.id.addOrganization);
			infoToggle = (TextView) v.findViewById(R.id.personalInfoExpandCollapse);
			infoContainer = (ViewGroup) v.findViewById(R.id.personalInfoContainer);

			infoToggle.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (infoContainer.getVisibility() == View.VISIBLE) {
						infoContainer.setVisibility(View.GONE);
						infoToggle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.expand, 0, 0, 0);
					}
					else {
						infoContainer.setVisibility(View.VISIBLE);
						infoToggle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.collapse, 0, 0, 0);
					}
				}
			});

			addSpecialty.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					addSpecialty(v.getContext(), "");
				}
			});
			addOrganization.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					addOrganization(v.getContext(), "");
				}
			});
			hasInitialized = true;
			if (hasUpdated) updateViews(stateId);
	    }

	    void updateViews(int stateId) {
	    	this.stateId = stateId;
	    	hasUpdated = true;
	    	if (!hasInitialized) return;
	    	nameView.setText(firstName + " " + lastName);
	    	firstNameView.setText(firstName);
	    	lastNameView.setText(lastName);
	    	addressView.setText(address);
	    	cityView.setText(city);
	    	stateView.setSelection(stateId);
	    	zipView.setText(zip);
	    	phone1View.setText(phone1);
	    	phone2View.setText(phone2);
	    	emailView.setText(email);
	    	notesView.setText(notes);

	    	specialtiesContainer.removeAllViews();
	    	specialtiesContainer.addView(addSpecialty);
	    	for (String s : specialties) {
	    		addSpecialty(specialtiesContainer.getContext(), s);
	    	}
	    	organizationsContainer.removeAllViews();
	    	organizationsContainer.addView(addOrganization);
	    	for (String s : organizations) {
	    		addOrganization(organizationsContainer.getContext(), s);
	    	}
	    }

	    void updateStrings() {
	    	firstName = firstNameView.getText().toString();
	    	lastName = lastNameView.getText().toString();
	    	address = addressView.getText().toString();
	    	city = cityView.getText().toString();
	    	stateId = stateView.getSelectedItemPosition();
	    	zip = zipView.getText().toString();
	    	phone1 = phone1View.getText().toString();
	    	phone2 = phone2View.getText().toString();
	    	email = emailView.getText().toString();
	    	notes = notesView.getText().toString();

	    	specialties.clear();
	    	for (int i = 0; i < specialtiesContainer.getChildCount() - 1; ++i) {
	    		specialties.add(((AutoCompleteTextView) specialtiesContainer.getChildAt(i))
	    			.getText().toString());
	    	}

	    	organizations.clear();
	    	for (int i = 0; i < organizationsContainer.getChildCount() - 1; ++i) {
	    		organizations.add(((AutoCompleteTextView) organizationsContainer.getChildAt(i))
	    			.getText().toString());
	    	}
	    }

	    void addSpecialty(Context c, String name) {
	    	AutoCompleteTextView v = (AutoCompleteTextView) LayoutInflater.from(c)
	    			.inflate(R.layout.autocomplete_textview, null);
	    	v.setText(name);
	    	specialtiesContainer.addView(v,	specialtiesContainer.getChildCount() - 1);
	    }

	    void addOrganization(Context c, String name) {
	    	AutoCompleteTextView v = (AutoCompleteTextView) LayoutInflater.from(c)
	    			.inflate(R.layout.autocomplete_textview, null);
	    	v.setText(name);
	    	organizationsContainer.addView(v, organizationsContainer.getChildCount() - 1);
	    }
    }

    private static class Location {
    	String organization = "", address = "", city = "", state = "", zip = "",
    			phone1 = "",phone2 = "", email1 = "", email2 = "";
    	int stateId = 0;
    	boolean isPrimary = false;
    	EditText addressView, cityView, zipView,
    			phone1View, phone2View,	email1View, email2View;
    	TextView organizationView;
    	Spinner stateView;
    	CheckBox isPrimaryView;
    	boolean hasInitialized = false, hasUpdated = false;

    	void initializeViews(View v) {
    		organizationView = (TextView) v.findViewById(R.id.organization);
    		addressView = (EditText) v.findViewById(R.id.address);
			cityView = (EditText) v.findViewById(R.id.city);
			stateView = (Spinner) v.findViewById(R.id.state);
			zipView = (EditText) v.findViewById(R.id.zip);
    		phone1View = (EditText) v.findViewById(R.id.phone1);
    		phone2View = (EditText) v.findViewById(R.id.phone2);
    		email1View = (EditText) v.findViewById(R.id.email1);
    		email2View = (EditText) v.findViewById(R.id.email2);
    		isPrimaryView = (CheckBox) v.findViewById(R.id.primary);
    		hasInitialized = true;
			if (hasUpdated) updateViews(stateId);
    	}

    	void updateViews(int stateId) {
    		this.stateId = stateId;
    		hasUpdated = true;
	    	if (!hasInitialized) return;
    		organizationView.setText(organization);
    		addressView.setText(address);
	    	cityView.setText(city);
	    	stateView.setSelection(stateId);
	    	zipView.setText(zip);
	    	phone1View.setText(phone1);
	    	phone2View.setText(phone2);
	    	email1View.setText(email1);
	    	email2View.setText(email2);
	    	isPrimaryView.setChecked(isPrimary);
    	}

    	void updateStrings() {
	    	address = addressView.getText().toString();
	    	city = cityView.getText().toString();
	    	stateId = stateView.getSelectedItemPosition();
	    	zip = zipView.getText().toString();
	    	phone1 = phone1View.getText().toString();
	    	phone2 = phone2View.getText().toString();
	    	email1 = email1View.getText().toString();
	    	email2 = email2View.getText().toString();
	    	isPrimary = isPrimaryView.isChecked();
	    }
    }

    private Person mPerson;
    private Location mLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	mEditType = getArguments().getInt(ARG_EDIT_TYPE);
    	mItemId = getArguments().getInt(ARG_ITEM_ID);

    	mPerson = new Person();
    	mLocation = new Location();

    	setHasOptionsMenu(true);

    	switch (mEditType) {
        case EDIT_TYPE_LOCATION:
        	getActivity().setTitle(R.string.edit_location);
        	mRequest = new EditLocation(mItemId);
        	break;
        case EDIT_TYPE_PERSON:
        	getActivity().setTitle(R.string.edit_person);
        	mRequest = new EditPerson(mItemId);
        	break;
    	default:
    		mRequest = null;
    		mCallbacks.onInvalidEditType(mEditType, mItemId);
        }

    	getLoaderManager().initLoader(mEditType, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	if (container == null) return null;
    	View v = null;
        switch (mEditType) {
        case EDIT_TYPE_LOCATION:
        	getActivity().setTitle(R.string.edit_location);
        	v = inflater.inflate(R.layout.edit_location, container, false);
        	mLocation.initializeViews(v);
        	break;
        case EDIT_TYPE_PERSON:
        	getActivity().setTitle(R.string.edit_person);
        	v = inflater.inflate(R.layout.edit_person, container, false);
        	mPerson.initializeViews(v);
        	break;
        }
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	super.onCreateOptionsMenu(menu, inflater);
    	menu.findItem(R.id.save).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.save:
    		executeRequest();
    		mCallbacks.onSave(mEditType, mItemId);
    	}
    	return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = sDummyCallbacks;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    	CursorLoader cursorLoader = null;
    	Uri contentUri = null;
    	String[] projection = null;

    	switch (id) {
    	case EDIT_TYPE_PERSON:
    		contentUri = DatabaseContract.DetailPerson.CONTENT_URI;
    		projection = new String[] {
				DatabaseContract.DetailPerson.COL_ID,
				DatabaseContract.DetailPerson.COL_PERSON_ID,
				DatabaseContract.DetailPerson.COL_FIRST_NAME,
				DatabaseContract.DetailPerson.COL_LAST_NAME,
				DatabaseContract.DetailPerson.COL_SPECIALTIES,
				DatabaseContract.DetailPerson.COL_ORGANIZATIONS,
				DatabaseContract.DetailPerson.COL_LOCATION_TIME,
				DatabaseContract.DetailPerson.COL_LATITUDE,
				DatabaseContract.DetailPerson.COL_LONGITUDE,
				DatabaseContract.DetailPerson.COL_ADDRESS,
				DatabaseContract.DetailPerson.COL_CITY,
				DatabaseContract.DetailPerson.COL_STATE,
				DatabaseContract.DetailPerson.COL_ZIP,
				DatabaseContract.DetailPerson.COL_EMAIL,
				DatabaseContract.DetailPerson.COL_PHONE1,
				DatabaseContract.DetailPerson.COL_PHONE2,
				DatabaseContract.DetailPerson.COL_NOTES
			};
    		break;
    	case EDIT_TYPE_LOCATION:
    		contentUri = DatabaseContract.DetailLocation.CONTENT_URI;
    		projection = new String[] {
				DatabaseContract.DetailLocation.COL_ID,
				DatabaseContract.DetailLocation.COL_ORGANIZATION_ID,
				DatabaseContract.DetailLocation.COL_ORGANIZATION,
				DatabaseContract.DetailLocation.COL_PRIMARY,
				DatabaseContract.DetailLocation.COL_ADDRESS,
				DatabaseContract.DetailLocation.COL_CITY,
				DatabaseContract.DetailLocation.COL_STATE,
				DatabaseContract.DetailLocation.COL_ZIP,
				DatabaseContract.DetailLocation.COL_EMAIL1,
				DatabaseContract.DetailLocation.COL_EMAIL2,
				DatabaseContract.DetailLocation.COL_PHONE1,
				DatabaseContract.DetailLocation.COL_PHONE2
			};
    		break;
		default:
			Log.w(LOG_TAG, "Unknown loader id: " + id);
			return null;
    	}

    	cursorLoader = new CursorLoader(getActivity(), contentUri, projection, null, null, null);

    	return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
    	if (Robodex.DEBUG) Log.i(LOG_TAG, "onLoadFinished()");
    	if (c == null || c.getCount() < 1) {
    		mCallbacks.onNoDetailsToEdit(mEditType, mItemId);
    		return;
    	}
    	c.moveToFirst();
    	try {
	    	switch (mEditType) {
	    	case EDIT_TYPE_PERSON:
	    		mPerson.firstName = c.getString(c.getColumnIndex(DatabaseContract.DetailPerson.COL_FIRST_NAME));
	    		mPerson.lastName = c.getString(c.getColumnIndex(DatabaseContract.DetailPerson.COL_LAST_NAME));
	    		mPerson.address = c.getString(c.getColumnIndex(DatabaseContract.DetailPerson.COL_ADDRESS));
	    		mPerson.city = c.getString(c.getColumnIndex(DatabaseContract.DetailPerson.COL_CITY));
	    		mPerson.state = c.getString(c.getColumnIndex(DatabaseContract.DetailPerson.COL_STATE));
	    		mPerson.zip = c.getString(c.getColumnIndex(DatabaseContract.DetailPerson.COL_ZIP));
	    		mPerson.phone1 = c.getString(c.getColumnIndex(DatabaseContract.DetailPerson.COL_PHONE1));
				mPerson.phone2 = c.getString(c.getColumnIndex(DatabaseContract.DetailPerson.COL_PHONE2));
	    		mPerson.email =	c.getString(c.getColumnIndex(DatabaseContract.DetailPerson.COL_EMAIL));
	    		mPerson.notes =	c.getString(c.getColumnIndex(DatabaseContract.DetailPerson.COL_NOTES));

	    		String[] specs = c.getString(c.getColumnIndex(
	    				DatabaseContract.DetailPerson.COL_SPECIALTIES)).split(STRING_DELIMITER);
	    		mPerson.specialties.clear();
	    		for (String s : specs) mPerson.specialties.add(s);

	    		String[] orgs = c.getString(c.getColumnIndex(
	    				DatabaseContract.DetailPerson.COL_ORGANIZATIONS)).split(STRING_DELIMITER);
	    		mPerson.organizations.clear();
	    		for (String s : orgs) mPerson.organizations.add(s);

	    		mPerson.updateViews(getStateId(mPerson.state));
	    		break;
	    	case EDIT_TYPE_LOCATION:
	    		mLocation.isPrimary = (c.getInt(c.getColumnIndex(DatabaseContract.DetailLocation.COL_PRIMARY)) == 1);
	    		mLocation.organization = c.getString(c.getColumnIndex(DatabaseContract.DetailLocation.COL_ORGANIZATION));
	    		mLocation.address = c.getString(c.getColumnIndex(DatabaseContract.DetailLocation.COL_ADDRESS));
				mLocation.city = c.getString(c.getColumnIndex(DatabaseContract.DetailLocation.COL_CITY));
				mLocation.state = c.getString(c.getColumnIndex(DatabaseContract.DetailLocation.COL_STATE));
				mLocation.zip = c.getString(c.getColumnIndex(DatabaseContract.DetailLocation.COL_ZIP));
	    		mLocation.phone1 = c.getString(c.getColumnIndex(DatabaseContract.DetailLocation.COL_PHONE1));
				mLocation.phone2 = c.getString(c.getColumnIndex(DatabaseContract.DetailLocation.COL_PHONE2));
	    		mLocation.email1 = c.getString(c.getColumnIndex(DatabaseContract.DetailLocation.COL_EMAIL1));
				mLocation.email2 = c.getString(c.getColumnIndex(DatabaseContract.DetailLocation.COL_EMAIL2));
	    		mLocation.updateViews(getStateId(mLocation.state));
	    		break;
	    	}
    	}
	    catch (CursorIndexOutOfBoundsException e) {
	    	mCallbacks.onInvalidDetailsToEdit(mEditType, mItemId);
	    }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    	if (Robodex.DEBUG) Log.i(LOG_TAG, "onLoaderReset()");
    }

    private void executeRequest() {
    	Map<String, String> details = new HashMap<String, String>();

    	switch (mEditType) {
    	case EDIT_TYPE_PERSON:
    		mPerson.updateStrings();
    		mPerson.state = getState(mPerson.stateId);
    		details.put(RequestField.PERSON_ID, String.valueOf(mItemId));
    		details.put(RequestField.FIRST_NAME, mPerson.firstName);
    		details.put(RequestField.LAST_NAME, mPerson.lastName);
    		details.put(RequestField.SPECIALTIES, TextUtils.join(STRING_DELIMITER, mPerson.specialties));
    		details.put(RequestField.ORGANIZATIONS, TextUtils.join(STRING_DELIMITER, mPerson.organizations));
    		details.put(RequestField.ADDRESS, mPerson.address);
    		details.put(RequestField.CITY, mPerson.city);
    		details.put(RequestField.STATE, mPerson.state);
    		details.put(RequestField.ZIP, mPerson.zip);
    		details.put(RequestField.EMAIL, mPerson.email);
    		details.put(RequestField.PHONE1, mPerson.phone1);
    		details.put(RequestField.PHONE2, mPerson.phone2);
    		details.put(RequestField.NOTES, mPerson.notes);
    		((EditPerson) mRequest).setDetails(details);
    		break;
    	case EDIT_TYPE_LOCATION:
    		mLocation.updateStrings();
    		mLocation.state = getState(mLocation.stateId);
    		details.put(RequestField.LOCATION_ID, String.valueOf(mItemId));
    		details.put(RequestField.PRIMARY, mLocation.isPrimary ? "1" : "0");
    		details.put(RequestField.ADDRESS, mLocation.address);
    		details.put(RequestField.CITY, mLocation.city);
    		details.put(RequestField.STATE, mLocation.state);
    		details.put(RequestField.ZIP, mLocation.zip);
    		details.put(RequestField.EMAIL1, mLocation.email1);
    		details.put(RequestField.EMAIL2, mLocation.email2);
    		details.put(RequestField.PHONE1, mLocation.phone1);
    		details.put(RequestField.PHONE2, mLocation.phone2);
    		((EditLocation) mRequest).setDetails(details);
    		break;
    	}
    	mRequest.execute();
    }

    private int getStateId(String state) {
    	String[] states = getResources().getStringArray(R.array.states);
    	for (int i = 0; i < states.length; ++i) {
    		if (states[i].equals(state)) return i;
    	}

    	states = getResources().getStringArray(R.array.states_full);
    	for (int i = 0; i < states.length; ++i) {
    		if (states[i].equals(state)) return i;
    	}

    	return 0;
    }

    private String getState(int stateId) {
    	String[] states = getResources().getStringArray(R.array.states_full);
    	if (stateId >= 0 && stateId < states.length) return states[stateId];
    	return "";
    }

}

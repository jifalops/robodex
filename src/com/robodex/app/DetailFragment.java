package com.robodex.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.robodex.R;
import com.robodex.Robodex;
import com.robodex.data.DatabaseContract;
import com.robodex.request.BaseRequest;
import com.robodex.request.DetailLocation;
import com.robodex.request.DetailPerson;

public class DetailFragment extends SherlockFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {
	private static final String LOG_TAG = DetailFragment.class.getSimpleName();
	private static final String STRING_DELIMITER = "<>]&"; // hack array<--->string

	interface Callbacks {
		void onInvalidDetailType();
		void onNoDetails();
		void onInvalidDetails();
    }

	/** Used temporarily during lifecycle methods. */
	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override public void onInvalidDetailType() {}
		@Override public void onNoDetails() {}
		@Override public void onInvalidDetails() {}
    };

    static final String ARG_DETAIL_TYPE = "type_of_details";
    static final String ARG_ITEM_ID 	= "item_id";

    static final int DETAIL_TYPE_PERSON   = 1;
    static final int DETAIL_TYPE_LOCATION = 2;

    private int mDetailType;
    private int mItemId;
    private BaseRequest mRequest;

    private Callbacks mCallbacks = sDummyCallbacks;

    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static class Person {
    	String name = "", specialties = "", organizations = "",
    			address = "", phone = "", email = "", notes = "",
    			checkinTime = "", checkinLat = "", checkinLon = "";

    	TextView nameView, specialtiesView, organizationsView, addressView,
    			checkinView, phoneView, emailView, notesView,
    			specToggle, orgToggle;

    	boolean hasInitialized = false, hasUpdated = false;

	    void initializeViews(View v) {
			nameView = (TextView) v.findViewById(R.id.name);
			specialtiesView = (TextView) v.findViewById(R.id.specialties);
			organizationsView = (TextView) v.findViewById(R.id.organizations);
			addressView = (TextView) v.findViewById(R.id.address);
			checkinView = (TextView) v.findViewById(R.id.checkin);
			phoneView = (TextView) v.findViewById(R.id.phone);
			emailView = (TextView) v.findViewById(R.id.email);
			notesView = (TextView) v.findViewById(R.id.notes);
			specToggle = (TextView) v.findViewById(R.id.specialtiesExpandCollapse);
			orgToggle = (TextView) v.findViewById(R.id.organizationsExpandCollapse);
			specToggle.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (specialtiesView.getVisibility() == View.VISIBLE) {
						specialtiesView.setVisibility(View.GONE);
						specToggle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.expand, 0, 0, 0);
					}
					else {
						specialtiesView.setVisibility(View.VISIBLE);
						specToggle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.collapse, 0, 0, 0);
					}
				}
			});
			orgToggle.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (organizationsView.getVisibility() == View.VISIBLE) {
						organizationsView.setVisibility(View.GONE);
						orgToggle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.expand, 0, 0, 0);
					}
					else {
						organizationsView.setVisibility(View.VISIBLE);
						orgToggle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.collapse, 0, 0, 0);
					}
				}
			});
			hasInitialized = true;
			if (hasUpdated) updateViews();
	    }

	    void updateViews() {
	    	hasUpdated = true;
	    	if (!hasInitialized) return;
	    	removeNullStrings();
	    	showBlankFields(nameView.getContext().getString(R.string.blank));
	    	nameView.setText(name);
	    	specialtiesView.setText(specialties);
	    	organizationsView.setText(organizations);
	    	Hyperlink.setText(addressView, address);
	    	Hyperlink.setText(checkinView, checkinTime, "geo:" + checkinLat + "," + checkinLon);
	    	Hyperlink.setText(phoneView, phone);
	    	Hyperlink.setText(emailView, email);
	    	notesView.setText(notes);
	    }

	    void removeNullStrings() {
	    	name = name.replace("null", "");
	    	specialties = specialties.replace("null", "");
	    	organizations = organizations.replace("null", "");
	    	address = address.replace("null", "");
	    	phone = phone.replace("null", "");
	    	email = email.replace("null", "");
	    	notes = notes.replace("null", "");
	    	checkinTime = checkinTime.replace("null", "");
	    }

	    void showBlankFields(String replacement) {
	    	if (name.replaceAll("\\s+", "").length() == 0) name = replacement;
	    	if (specialties.replaceAll("\\s+", "").length() == 0) specialties = replacement;
	    	if (organizations.replaceAll("\\s+", "").length() == 0) organizations = replacement;
	    	if (address.replaceAll("\\s+", "").length() == 0) address = replacement;
	    	if (phone.replaceAll("\\s+", "").length() == 0) phone = replacement;
	    	if (email.replaceAll("\\s+", "").length() == 0) email = replacement;
	    	if (notes.replaceAll("\\s+", "").length() == 0) notes = replacement;
	    	if (checkinTime.replaceAll("\\s+", "").length() == 0) checkinTime = replacement;
	    }
    }

    private static class Location {
    	String organization = "", address = "", phone = "", email = "";
    	TextView organizationView, addressView, phoneView, emailView;
    	boolean hasInitialized = false, hasUpdated = false;

    	void initializeViews(View v) {
    		organizationView = (TextView) v.findViewById(R.id.organization);
    		addressView = (TextView) v.findViewById(R.id.address);
    		phoneView = (TextView) v.findViewById(R.id.phone);
    		emailView = (TextView) v.findViewById(R.id.email);
    		hasInitialized = true;
			if (hasUpdated) updateViews();
    	}

    	void updateViews() {
    		hasUpdated = true;
	    	if (!hasInitialized) return;
	    	removeNullStrings();
	    	showBlankFields(addressView.getContext().getString(R.string.blank));
    		organizationView.setText(organization);
    		Hyperlink.setText(addressView, address);
    		Hyperlink.setText(phoneView, phone);
    		Hyperlink.setText(emailView, email);
    	}

    	void removeNullStrings() {
	    	organization = organization.replace("null", "");
	    	address = address.replace("null", "");
	    	phone = phone.replace("null", "");
	    	email = email.replace("null", "");
	    }

    	void showBlankFields(String replacement) {
	    	if (organization.replaceAll("\\s+", "").length() == 0) organization = replacement;
	    	if (address.replaceAll("\\s+", "").length() == 0) address = replacement;
	    	if (phone.replaceAll("\\s+", "").length() == 0) phone = replacement;
	    	if (email.replaceAll("\\s+", "").length() == 0) email = replacement;
	    }
    }

    Person mPerson;
    Location mLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	mDetailType = getArguments().getInt(ARG_DETAIL_TYPE);
    	mItemId = getArguments().getInt(ARG_ITEM_ID);

    	mPerson = new Person();
    	mLocation = new Location();

    	switch (mDetailType) {
        case DETAIL_TYPE_LOCATION:
        	getActivity().setTitle(R.string.detail_location);
        	mRequest = new DetailLocation(mItemId);
        	break;
        case DETAIL_TYPE_PERSON:
        	getActivity().setTitle(R.string.detail_person);
        	mRequest = new DetailPerson(mItemId);
        	break;
    	default:
    		mRequest = null;
    		mCallbacks.onInvalidDetailType();
        }

    	if (mRequest != null) mRequest.execute();
    	getLoaderManager().initLoader(mDetailType, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	if (container == null) return null;
    	View v = null;
        switch (mDetailType) {
        case DETAIL_TYPE_LOCATION:
        	 v = inflater.inflate(R.layout.detail_location, container, false);
        	 mLocation.initializeViews(v);
        	 break;
        case DETAIL_TYPE_PERSON:
        	v = inflater.inflate(R.layout.detail_person, container, false);
        	mPerson.initializeViews(v);
        	break;
        }
        return v;
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
    	case DETAIL_TYPE_PERSON:
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
    	case DETAIL_TYPE_LOCATION:
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
    		mCallbacks.onNoDetails();
    		return;
    	}
    	c.moveToFirst();
    	try {
	    	switch (mDetailType) {
	    	case DETAIL_TYPE_PERSON:
	    		mPerson.name =
	    				c.getString(c.getColumnIndex(DatabaseContract.DetailPerson.COL_FIRST_NAME))
	    				+ " " +
	    				c.getString(c.getColumnIndex(DatabaseContract.DetailPerson.COL_LAST_NAME));
	    		mPerson.specialties =
	    				TextUtils.join("\n", c.getString(c.getColumnIndex(
						DatabaseContract.DetailPerson.COL_SPECIALTIES)).split(STRING_DELIMITER));
	    		mPerson.organizations =
	    				TextUtils.join("\n", c.getString(c.getColumnIndex(
						DatabaseContract.DetailPerson.COL_ORGANIZATIONS)).split(STRING_DELIMITER));
	    		mPerson.address =
	    				c.getString(c.getColumnIndex(DatabaseContract.DetailPerson.COL_ADDRESS))
	    				+ "\n" +
	    				c.getString(c.getColumnIndex(DatabaseContract.DetailPerson.COL_CITY))
	    				+ ", " +
	    				c.getString(c.getColumnIndex(DatabaseContract.DetailPerson.COL_STATE))
	    				+ " " +
	    				c.getString(c.getColumnIndex(DatabaseContract.DetailPerson.COL_ZIP));
	    		Date d = null;
				try { d = mFormat.parse(c.getString(c.getColumnIndex(DatabaseContract.DetailPerson.COL_LOCATION_TIME))); }
				catch (ParseException ignored) {}
	    		if (d != null) mPerson.checkinTime = d.toString();
	    		mPerson.checkinLat =
	    				c.getString(c.getColumnIndex(DatabaseContract.DetailPerson.COL_LATITUDE));
	    		mPerson.checkinLon =
	    				c.getString(c.getColumnIndex(DatabaseContract.DetailPerson.COL_LONGITUDE));
	    		mPerson.phone =
	    				c.getString(c.getColumnIndex(DatabaseContract.DetailPerson.COL_PHONE1))
	    				+ "\n" +
	    				c.getString(c.getColumnIndex(DatabaseContract.DetailPerson.COL_PHONE2));
	    		mPerson.email =
	    				c.getString(c.getColumnIndex(DatabaseContract.DetailPerson.COL_EMAIL));
	    		mPerson.notes =
	    				c.getString(c.getColumnIndex(DatabaseContract.DetailPerson.COL_NOTES));
	    		mPerson.updateViews();
	    		break;
	    	case DETAIL_TYPE_LOCATION:
	    		int primary = c.getInt(c.getColumnIndex(DatabaseContract.DetailLocation.COL_PRIMARY));
	    		mLocation.organization =
						c.getString(c.getColumnIndex(DatabaseContract.DetailLocation.COL_ORGANIZATION))
						+ (primary == 1 ? "\nPrimary Location" : "");
	    		mLocation.address =
						c.getString(c.getColumnIndex(DatabaseContract.DetailLocation.COL_ADDRESS))
						+ "\n" +
						c.getString(c.getColumnIndex(DatabaseContract.DetailLocation.COL_CITY))
						+ ", " +
						c.getString(c.getColumnIndex(DatabaseContract.DetailLocation.COL_STATE))
						+ " " +
						c.getString(c.getColumnIndex(DatabaseContract.DetailLocation.COL_ZIP));
	    		mLocation.phone =
						c.getString(c.getColumnIndex(DatabaseContract.DetailLocation.COL_PHONE1))
						+ "\n" +
						c.getString(c.getColumnIndex(DatabaseContract.DetailLocation.COL_PHONE2));
	    		mLocation.email =
						c.getString(c.getColumnIndex(DatabaseContract.DetailLocation.COL_EMAIL1))
						+ "\n" +
						c.getString(c.getColumnIndex(DatabaseContract.DetailLocation.COL_EMAIL2));
	    		mLocation.updateViews();
	    		break;
	    	}
    	}
	    catch (CursorIndexOutOfBoundsException e) {
	    	mCallbacks.onInvalidDetails();
	    }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    	if (Robodex.DEBUG) Log.i(LOG_TAG, "onLoaderReset()");
    }
}

package com.robodex.request;

import java.util.HashMap;
import java.util.Map;

import com.robodex.data.DatabaseContract;
import com.robodex.request.ServerContract.RequestField;

import android.content.ContentValues;

public final class EditLocation extends BaseRequest {
	private int mLocationId;
	private Map<String, String> mDetails;

	public EditLocation(int locationId) {
		mLocationId = locationId;
	}

	public void setDetails(Map<String, String> details) {
		mDetails = details;
	}

	@Override
	protected void prepareRequest() {
		Map<String, String> request = new HashMap<String, String>();
		request.put(DatabaseContract.DetailLocation.COL_LOCATION_ID, String.valueOf(mLocationId));
		request.put(DatabaseContract.DetailLocation.COL_PRIMARY, mDetails.get(RequestField.PRIMARY));
		request.put(DatabaseContract.DetailLocation.COL_ADDRESS, mDetails.get(RequestField.ADDRESS));
		request.put(DatabaseContract.DetailLocation.COL_CITY, mDetails.get(RequestField.CITY));
		request.put(DatabaseContract.DetailLocation.COL_STATE, mDetails.get(RequestField.STATE));
		request.put(DatabaseContract.DetailLocation.COL_ZIP, mDetails.get(RequestField.ZIP));
		request.put(DatabaseContract.DetailLocation.COL_EMAIL1, mDetails.get(RequestField.EMAIL1));
		request.put(DatabaseContract.DetailLocation.COL_EMAIL2, mDetails.get(RequestField.EMAIL2));
		request.put(DatabaseContract.DetailLocation.COL_PHONE1, mDetails.get(RequestField.PHONE1));
		request.put(DatabaseContract.DetailLocation.COL_PHONE2, mDetails.get(RequestField.PHONE2));
		executeRequest(request);
	}

	@Override
	protected ContentValues processRowForInsertion(Map<String, String> rowFromResponse) {
		ContentValues rowToInsert = new ContentValues();
		rowToInsert.put(DatabaseContract.EditPerson.COL_PERSON_ID, rowFromResponse.get(RequestField.PERSON_ID));
        return rowToInsert;
	}
}

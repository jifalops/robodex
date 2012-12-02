package com.robodex.request;

import java.util.HashMap;
import java.util.Map;

import com.robodex.data.DatabaseContract;
import com.robodex.request.ServerContract.RequestField;

import android.content.ContentValues;

public final class DetailPerson extends BaseRequest {
	private int mPersonId;

	public DetailPerson(int personId) {
		mPersonId = personId;
	}

	@Override
	protected void prepareRequest() {
		Map<String, String> request = new HashMap<String, String>();
		request.put(RequestField.PERSON_ID, String.valueOf(mPersonId));
		executeRequest(request);
	}

	@Override
	protected ContentValues processRowForInsertion(Map<String, String> rowFromResponse) {
		ContentValues rowToInsert = new ContentValues();
		rowToInsert.put(DatabaseContract.DetailPerson.COL_PERSON_ID, rowFromResponse.get(RequestField.PERSON_ID));
		rowToInsert.put(DatabaseContract.DetailPerson.COL_FIRST_NAME, rowFromResponse.get(RequestField.FIRST_NAME));
		rowToInsert.put(DatabaseContract.DetailPerson.COL_LAST_NAME, rowFromResponse.get(RequestField.LAST_NAME));
		rowToInsert.put(DatabaseContract.DetailPerson.COL_SPECIALTIES, rowFromResponse.get(RequestField.SPECIALTIES));
		rowToInsert.put(DatabaseContract.DetailPerson.COL_ORGANIZATIONS, rowFromResponse.get(RequestField.ORGANIZATIONS));
		rowToInsert.put(DatabaseContract.DetailPerson.COL_LOCATION_TIME, rowFromResponse.get(RequestField.LOCATION_TIME));
		rowToInsert.put(DatabaseContract.DetailPerson.COL_LATITUDE, rowFromResponse.get(RequestField.LATITUDE));
		rowToInsert.put(DatabaseContract.DetailPerson.COL_LONGITUDE, rowFromResponse.get(RequestField.LONGITUDE));
		rowToInsert.put(DatabaseContract.DetailPerson.COL_ADDRESS, rowFromResponse.get(RequestField.ADDRESS));
		rowToInsert.put(DatabaseContract.DetailPerson.COL_CITY, rowFromResponse.get(RequestField.CITY));
		rowToInsert.put(DatabaseContract.DetailPerson.COL_STATE, rowFromResponse.get(RequestField.STATE));
		rowToInsert.put(DatabaseContract.DetailPerson.COL_ZIP, rowFromResponse.get(RequestField.ZIP));
		rowToInsert.put(DatabaseContract.DetailPerson.COL_EMAIL, rowFromResponse.get(RequestField.EMAIL));
		rowToInsert.put(DatabaseContract.DetailPerson.COL_PHONE1, rowFromResponse.get(RequestField.PHONE1));
		rowToInsert.put(DatabaseContract.DetailPerson.COL_PHONE2, rowFromResponse.get(RequestField.PHONE2));
		rowToInsert.put(DatabaseContract.DetailPerson.COL_NOTES, rowFromResponse.get(RequestField.NOTES));
        return rowToInsert;
	}
}

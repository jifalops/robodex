package com.robodex.request;

import java.util.HashMap;
import java.util.Map;

import com.robodex.data.DatabaseContract;
import com.robodex.request.ServerContract.RequestField;

import android.content.ContentValues;

public final class ListPeopleBySpecialty extends BaseEndlessListRequest {
	private int mSpecialtyId;
	public ListPeopleBySpecialty(int specialtyId) {
		mSpecialtyId = specialtyId;
	}

	@Override
	protected void prepareRequest() {
		Map<String, String> request = new HashMap<String, String>();
		request.put(RequestField.START_POSITION, String.valueOf(getStartPosition()));
		request.put(RequestField.SPECIALTY_ID, String.valueOf(mSpecialtyId));
		executeRequest(request);
	}

	@Override
	protected ContentValues processRowForInsertion(Map<String, String> rowFromResponse) {
		ContentValues rowToInsert = new ContentValues();
		rowToInsert.put(DatabaseContract.ListPeopleBySpecialty.COL_PERSON_ID,
				rowFromResponse.get(RequestField.PERSON_ID));
		rowToInsert.put(DatabaseContract.ListPeopleBySpecialty.COL_FIRST_NAME,
				rowFromResponse.get(RequestField.FIRST_NAME));
		rowToInsert.put(DatabaseContract.ListPeopleBySpecialty.COL_LAST_NAME,
				rowFromResponse.get(RequestField.LAST_NAME));
		rowToInsert.put(DatabaseContract.ListPeopleBySpecialty.COL_CITY,
				rowFromResponse.get(RequestField.CITY));
		rowToInsert.put(DatabaseContract.ListPeopleBySpecialty.COL_STATE,
				rowFromResponse.get(RequestField.STATE));
		rowToInsert.put(DatabaseContract.ListPeopleBySpecialty.COL_ZIP,
				rowFromResponse.get(RequestField.ZIP));
        return rowToInsert;
	}

}

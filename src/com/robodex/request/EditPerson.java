package com.robodex.request;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;

import com.robodex.data.DatabaseContract;
import com.robodex.request.ServerContract.RequestField;

public final class EditPerson extends BaseRequest {
	private int mPersonId;
	private Map<String, String> mDetails;

	public EditPerson(int personId) {
		mPersonId = personId;
	}

	public void setDetails(Map<String, String> details) {
		mDetails = details;
	}

	@Override
	protected void prepareRequest() {
		Map<String, String> request = new HashMap<String, String>();
		request.put(DatabaseContract.EditPerson.COL_PERSON_ID, String.valueOf(mPersonId));
		request.putAll(mDetails);
//		request.put(DatabaseContract.EditPerson.COL_FIRST_NAME, mDetails.get(RequestField.FIRST_NAME));
//		request.put(DatabaseContract.EditPerson.COL_LAST_NAME, mDetails.get(RequestField.LAST_NAME));
//		request.put(DatabaseContract.EditPerson.COL_SPECIALTIES, mDetails.get(RequestField.SPECIALTIES));
//		request.put(DatabaseContract.EditPerson.COL_ORGANIZATIONS, mDetails.get(RequestField.ORGANIZATIONS));
//		request.put(DatabaseContract.EditPerson.COL_ADDRESS, mDetails.get(RequestField.ADDRESS));
//		request.put(DatabaseContract.EditPerson.COL_CITY, mDetails.get(RequestField.CITY));
//		request.put(DatabaseContract.EditPerson.COL_STATE, mDetails.get(RequestField.STATE));
//		request.put(DatabaseContract.EditPerson.COL_ZIP, mDetails.get(RequestField.ZIP));
//		request.put(DatabaseContract.EditPerson.COL_EMAIL, mDetails.get(RequestField.EMAIL));
//		request.put(DatabaseContract.EditPerson.COL_PHONE1, mDetails.get(RequestField.PHONE1));
//		request.put(DatabaseContract.EditPerson.COL_PHONE2, mDetails.get(RequestField.PHONE2));
//		request.put(DatabaseContract.EditPerson.COL_NOTES, mDetails.get(RequestField.NOTES));
		executeRequest(request);
	}

	@Override
	protected ContentValues processRowForInsertion(Map<String, String> rowFromResponse) {
		ContentValues rowToInsert = new ContentValues();
		rowToInsert.put(DatabaseContract.EditPerson.COL_PERSON_ID, rowFromResponse.get(RequestField.PERSON_ID));
        return rowToInsert;
	}
}

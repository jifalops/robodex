package com.robodex.request;

import java.util.HashMap;
import java.util.Map;

import com.robodex.data.DatabaseContract;
import com.robodex.request.ServerContract.RequestField;

import android.content.ContentValues;

public final class DetailLocation extends BaseRequest {
	private int mLocationId;

	public DetailLocation(int locationId) {
		mLocationId = locationId;
	}

	@Override
	protected void prepareRequest() {
		Map<String, String> request = new HashMap<String, String>();
		request.put(RequestField.LOCATION_ID, String.valueOf(mLocationId));
		executeRequest(request);
	}

	@Override
	protected ContentValues processRowForInsertion(Map<String, String> rowFromResponse) {
		ContentValues rowToInsert = new ContentValues();
		rowToInsert.put(DatabaseContract.DetailLocation.COL_LOCATION_ID, rowFromResponse.get(RequestField.LOCATION_ID));
		rowToInsert.put(DatabaseContract.DetailLocation.COL_ORGANIZATION_ID, rowFromResponse.get(RequestField.ORGANIZATION_ID));
		rowToInsert.put(DatabaseContract.DetailLocation.COL_ORGANIZATION, rowFromResponse.get(RequestField.ORGANIZATION));
		rowToInsert.put(DatabaseContract.DetailLocation.COL_PRIMARY, rowFromResponse.get(RequestField.PRIMARY));
		rowToInsert.put(DatabaseContract.DetailLocation.COL_ADDRESS, rowFromResponse.get(RequestField.ADDRESS));
		rowToInsert.put(DatabaseContract.DetailLocation.COL_CITY, rowFromResponse.get(RequestField.CITY));
		rowToInsert.put(DatabaseContract.DetailLocation.COL_STATE, rowFromResponse.get(RequestField.STATE));
		rowToInsert.put(DatabaseContract.DetailLocation.COL_ZIP, rowFromResponse.get(RequestField.ZIP));
		rowToInsert.put(DatabaseContract.DetailLocation.COL_EMAIL1, rowFromResponse.get(RequestField.EMAIL1));
		rowToInsert.put(DatabaseContract.DetailLocation.COL_EMAIL2, rowFromResponse.get(RequestField.EMAIL2));
		rowToInsert.put(DatabaseContract.DetailLocation.COL_PHONE1, rowFromResponse.get(RequestField.PHONE1));
		rowToInsert.put(DatabaseContract.DetailLocation.COL_PHONE2, rowFromResponse.get(RequestField.PHONE2));
        return rowToInsert;
	}
}

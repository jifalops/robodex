package com.robodex.request;

import java.util.HashMap;
import java.util.Map;

import com.robodex.data.DatabaseContract;
import com.robodex.request.ServerContract.RequestField;

import android.content.ContentValues;

public final class ListLocationsByOrganization extends BaseEndlessListRequest {
	private int mOrganizationId;
	public ListLocationsByOrganization(int organizationId) {
		mOrganizationId = organizationId;
	}

	@Override
	protected void prepareRequest() {
		Map<String, String> request = new HashMap<String, String>();
		request.put(RequestField.START_POSITION, String.valueOf(getStartPosition()));
		request.put(RequestField.ORGANIZATION_ID, String.valueOf(mOrganizationId));
		executeRequest(request);
	}

	@Override
	protected ContentValues processRowForInsertion(Map<String, String> rowFromResponse) {
		ContentValues rowToInsert = new ContentValues();
		rowToInsert.put(DatabaseContract.ListLocationsByOrganization.COL_LOCATION_ID,
				rowFromResponse.get(RequestField.LOCATION_ID));
		rowToInsert.put(DatabaseContract.ListLocationsByOrganization.COL_PRIMARY,
				rowFromResponse.get(RequestField.PRIMARY));
		rowToInsert.put(DatabaseContract.ListLocationsByOrganization.COL_ADDRESS,
				rowFromResponse.get(RequestField.ADDRESS));
		rowToInsert.put(DatabaseContract.ListLocationsByOrganization.COL_CITY,
				rowFromResponse.get(RequestField.CITY));
		rowToInsert.put(DatabaseContract.ListLocationsByOrganization.COL_STATE,
				rowFromResponse.get(RequestField.STATE));
		rowToInsert.put(DatabaseContract.ListLocationsByOrganization.COL_ZIP,
				rowFromResponse.get(RequestField.ZIP));
        return rowToInsert;
	}
}

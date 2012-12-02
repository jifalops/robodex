package com.robodex.request;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;

import com.robodex.data.DatabaseContract;
import com.robodex.request.ServerContract.RequestField;

public final class SearchAll extends BaseEndlessListRequest {
	private String mQuery;

	public SearchAll(String query) {
		mQuery = query;
	}

	@Override
	protected void prepareRequest() {
		Map<String, String> request = new HashMap<String, String>();
		request.put(RequestField.START_POSITION, String.valueOf(getStartPosition()));
		request.put(RequestField.SEARCH_TERMS, mQuery);
		executeRequest(request);
	}

	@Override
	protected ContentValues processRowForInsertion(Map<String, String> rowFromResponse) {
		ContentValues rowToInsert = new ContentValues();
		rowToInsert.put(DatabaseContract.SearchAll.COL_ADDRESS, rowFromResponse.get(RequestField.ADDRESS));
		rowToInsert.put(DatabaseContract.SearchAll.COL_CITY, rowFromResponse.get(RequestField.CITY));
		rowToInsert.put(DatabaseContract.SearchAll.COL_FIRST_NAME, rowFromResponse.get(RequestField.FIRST_NAME));
		rowToInsert.put(DatabaseContract.SearchAll.COL_LAST_NAME, rowFromResponse.get(RequestField.LAST_NAME));
		rowToInsert.put(DatabaseContract.SearchAll.COL_LINK, rowFromResponse.get(RequestField.LINK));
		rowToInsert.put(DatabaseContract.SearchAll.COL_LINK_TITLE, rowFromResponse.get(RequestField.LINK_TEXT));
		rowToInsert.put(DatabaseContract.SearchAll.COL_LOCATION_ID, rowFromResponse.get(RequestField.LOCATION_ID));
		rowToInsert.put(DatabaseContract.SearchAll.COL_PRIMARY, rowFromResponse.get(RequestField.PRIMARY));
		rowToInsert.put(DatabaseContract.SearchAll.COL_ORGANIZATION, rowFromResponse.get(RequestField.ORGANIZATION));
		rowToInsert.put(DatabaseContract.SearchAll.COL_ORGANIZATION_ID, rowFromResponse.get(RequestField.ORGANIZATION_ID));
		rowToInsert.put(DatabaseContract.SearchAll.COL_PERSON_ID, rowFromResponse.get(RequestField.PERSON_ID));
		rowToInsert.put(DatabaseContract.SearchAll.COL_SPECIALTY, rowFromResponse.get(RequestField.SPECIALTY));
		rowToInsert.put(DatabaseContract.SearchAll.COL_SPECIALTY_ID, rowFromResponse.get(RequestField.SPECIALTY_ID));
		rowToInsert.put(DatabaseContract.SearchAll.COL_STATE, rowFromResponse.get(RequestField.STATE));
		rowToInsert.put(DatabaseContract.SearchAll.COL_ZIP, rowFromResponse.get(RequestField.ZIP));
		rowToInsert.put(DatabaseContract.SearchAll.COL_SEARCH, rowFromResponse.get(RequestField.SEARCH_TERMS));
		rowToInsert.put(DatabaseContract.SearchAll.COL_LINK_ID, rowFromResponse.get(RequestField.LINK_ID));
        return rowToInsert;
	}
}

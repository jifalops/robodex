package com.robodex.request;

import java.util.HashMap;
import java.util.Map;

import com.robodex.Robodex;
import com.robodex.data.DatabaseContract;
import com.robodex.location.LocationUpdater;
import com.robodex.location.LocationUpdater.LocationUpdateListener;
import com.robodex.request.ServerContract.RequestField;

import android.content.ContentValues;
import android.location.Location;
import android.location.LocationManager;

public final class ListMap extends BaseRequest {
	private static final long GPS_TIMEOUT = 1000 * 10;
	private long mStartTime;
	private LocationUpdater mLocationUpdater;

	@Override
	protected void prepareRequest() {
		mLocationUpdater = new LocationUpdater(Robodex.sAppContext, new LocationUpdateListener() {
			@Override
			public void onLocationUpdated(Location location) {
				if (location.getProvider().equals(LocationManager.GPS_PROVIDER)
						|| System.currentTimeMillis() - mStartTime > GPS_TIMEOUT) {
					acceptLocation(location);
				}
			}
		});

		mStartTime = System.currentTimeMillis();
		mLocationUpdater.startListeningToGps();
		mLocationUpdater.startListeningToNetwork();
	}

	private void acceptLocation(Location location) {
		mLocationUpdater.stopListeningToAll();
		Map<String, String> request = new HashMap<String, String>();
		request.put(RequestField.LATITUDE, String.valueOf(location.getLatitude()));
		request.put(RequestField.LONGITUDE, String.valueOf(location.getLongitude()));
		request.put(RequestField.LOCATION_TIME, String.valueOf(location.getTime()));
		request.put(RequestField.LOCATION_ACCURACY, String.valueOf(location.getAccuracy()));
		executeRequest(request);
	}


	@Override
	protected ContentValues processRowForInsertion(Map<String, String> rowFromResponse) {
		ContentValues rowToInsert = new ContentValues();
		rowToInsert.put(DatabaseContract.ListMap.COL_LATITUDE, rowFromResponse.get(RequestField.LATITUDE));
		rowToInsert.put(DatabaseContract.ListMap.COL_LONGITUDE, rowFromResponse.get(RequestField.LONGITUDE));
		rowToInsert.put(DatabaseContract.ListMap.COL_ADDRESS, rowFromResponse.get(RequestField.ADDRESS));
		rowToInsert.put(DatabaseContract.ListMap.COL_CITY, rowFromResponse.get(RequestField.CITY));
		rowToInsert.put(DatabaseContract.ListMap.COL_STATE, rowFromResponse.get(RequestField.STATE));
		rowToInsert.put(DatabaseContract.ListMap.COL_ZIP, rowFromResponse.get(RequestField.ZIP));
		rowToInsert.put(DatabaseContract.ListMap.COL_PERSON_ID, rowFromResponse.get(RequestField.PERSON_ID));
		rowToInsert.put(DatabaseContract.ListMap.COL_FIRST_NAME, rowFromResponse.get(RequestField.FIRST_NAME));
		rowToInsert.put(DatabaseContract.ListMap.COL_LAST_NAME, rowFromResponse.get(RequestField.LAST_NAME));
		rowToInsert.put(DatabaseContract.ListMap.COL_MEMBER_ID, rowFromResponse.get(RequestField.MEMBER_ID));
		rowToInsert.put(DatabaseContract.ListMap.COL_LOCATION_TIME, rowFromResponse.get(RequestField.LOCATION_TIME));
		rowToInsert.put(DatabaseContract.ListMap.COL_LOCATION_ID, rowFromResponse.get(RequestField.LOCATION_ID));
		rowToInsert.put(DatabaseContract.ListMap.COL_ORGANIZATION, rowFromResponse.get(RequestField.ORGANIZATION));
		rowToInsert.put(DatabaseContract.ListMap.COL_PRIMARY, rowFromResponse.get(RequestField.PRIMARY));
        return rowToInsert;
	}

}

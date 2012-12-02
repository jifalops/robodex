package com.robodex.app;

import java.util.List;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.robodex.R;
import com.robodex.data.DatabaseContract;
import com.robodex.request.ListMap;

public class MyMapActivity extends MapActivity implements LoaderManager.LoaderCallbacks<Cursor> {
	private static final String LOG_TAG = MyMapActivity.class.getSimpleName();
	private static final int LOADER_MAP = 1;

    private static final int DEFAULT_ZOOM = 12;

    private MapView mMap;
    private List<Overlay> mOverlays;
    private MyItemizedOverlay mItemizedOverlay;
    private OverlayItem mSelfMarker;


    private ListMap mListMapRequest;
    private ListMap.Callbacks mCallbacks;

    private Cursor mCursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mCallbacks = new ListMap.Callbacks() {
			@Override
			public void onLocationAccepted(Location location) {
				updateLocation(location);
			}
			@Override
			public void onSetInitialLocation(Location location) {
				GeoPoint point = new GeoPoint(
		                (int) (location.getLatitude() * 1E6),
		                (int) (location.getLongitude() * 1E6));
		    	mSelfMarker = new OverlayItem(point, "You", null);
		    	mItemizedOverlay.addOverlay(mSelfMarker);
		    	mMap.getController().setCenter(point);
			}
		};

		mMap = (MapView) findViewById(R.id.mapview);
        mMap.setBuiltInZoomControls(true);
        mMap.getController().setZoom(DEFAULT_ZOOM);

        mOverlays = mMap.getOverlays();
    	mItemizedOverlay = new MyItemizedOverlay(getResources().getDrawable(R.drawable.marker_self), this);
    	if (mSelfMarker != null) mItemizedOverlay.addOverlay(mSelfMarker);


		mListMapRequest = new ListMap(mCallbacks);
        mListMapRequest.execute();

        getLoaderManager().initLoader(LOADER_MAP, null, this);


    }

    private void updateLocation(Location location) {
    	GeoPoint point = new GeoPoint(
                (int) (location.getLatitude() * 1E6),
                (int) (location.getLongitude() * 1E6));
    	mSelfMarker = new OverlayItem(point, "You", null);
    }

    @Override
    protected boolean isRouteDisplayed() {
        // TODO Auto-generated method stub
        return false;
    }

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader cursorLoader = null;
		Uri contentUri = null;
    	String[] projection = null;

		switch (id) {
		case LOADER_MAP:
			contentUri = DatabaseContract.ListMap.CONTENT_URI;
			projection = new String[] {
					DatabaseContract.ListMap.COL_LATITUDE,
					DatabaseContract.ListMap.COL_LONGITUDE,
					DatabaseContract.ListMap.COL_ADDRESS,
					DatabaseContract.ListMap.COL_CITY,
					DatabaseContract.ListMap.COL_STATE,
					DatabaseContract.ListMap.COL_ZIP,
					DatabaseContract.ListMap.COL_PERSON_ID,
					DatabaseContract.ListMap.COL_FIRST_NAME,
					DatabaseContract.ListMap.COL_LAST_NAME,
					DatabaseContract.ListMap.COL_MEMBER_ID,
					DatabaseContract.ListMap.COL_LOCATION_TIME,
					DatabaseContract.ListMap.COL_LOCATION_ID,
					DatabaseContract.ListMap.COL_ORGANIZATION,
					DatabaseContract.ListMap.COL_PRIMARY
			};
			break;
		}

		cursorLoader = new CursorLoader(this, contentUri, projection, null, null, null);

		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
		Log.i(LOG_TAG, "onLoadFinished");
		if (c == null || c.getCount() < 1) {
    		Log.w(LOG_TAG, "empty cursor");
    		return;
    	}

		mOverlays.clear();
		mItemizedOverlay = new MyItemizedOverlay(getResources().getDrawable(R.drawable.marker_self), this);
		mItemizedOverlay.addOverlay(mSelfMarker);

		GeoPoint point;
		OverlayItem item;
		String title, msg;
		int marker;

		while (c.moveToNext()) {
			point = new GeoPoint(
					(int) (c.getDouble(c.getColumnIndex(DatabaseContract.ListMap.COL_LATITUDE)) * 1E6),
					(int) (c.getDouble(c.getColumnIndex(DatabaseContract.ListMap.COL_LONGITUDE)) * 1E6));

			String locId = c.getString(c.getColumnIndex(DatabaseContract.ListMap.COL_LOCATION_ID));
			if (locId != null && locId.length() > 0) {
				marker = R.drawable.marker_building;
				int primary = c.getInt(c.getColumnIndex(DatabaseContract.ListMap.COL_PRIMARY));
				title =
					c.getString(c.getColumnIndex(DatabaseContract.ListMap.COL_ORGANIZATION))
					+ ((primary == 1) ? " (Primary)" : "");
				msg =
					c.getString(c.getColumnIndex(DatabaseContract.ListMap.COL_ADDRESS))
					+ " " +
					c.getString(c.getColumnIndex(DatabaseContract.ListMap.COL_CITY))
					+ ", " +
					c.getString(c.getColumnIndex(DatabaseContract.ListMap.COL_STATE))
					+ " " +
					c.getString(c.getColumnIndex(DatabaseContract.ListMap.COL_ZIP));
			}
			else {
				String locTime = c.getString(c.getColumnIndex(DatabaseContract.ListMap.COL_LOCATION_TIME));
				title =
					c.getString(c.getColumnIndex(DatabaseContract.ListMap.COL_FIRST_NAME))
					+ " " +
					c.getString(c.getColumnIndex(DatabaseContract.ListMap.COL_LAST_NAME))
					+ ((locTime != null && locTime.length() > 0) ? " (Check-In)" : "");
				if (locTime != null && locTime.length() > 0) {
					marker = R.drawable.marker_checkin;
					msg = locTime;
				}
				else {
					marker = R.drawable.marker_person;
					msg =
						c.getString(c.getColumnIndex(DatabaseContract.ListMap.COL_ADDRESS))
						+ " " +
						c.getString(c.getColumnIndex(DatabaseContract.ListMap.COL_CITY))
						+ ", " +
						c.getString(c.getColumnIndex(DatabaseContract.ListMap.COL_STATE))
						+ " " +
						c.getString(c.getColumnIndex(DatabaseContract.ListMap.COL_ZIP));
				}
			}

			item = new OverlayItem(point, title, msg);
			MyItemizedOverlay.setMarker(item, getResources().getDrawable(marker));
	    	mItemizedOverlay.addOverlay(item);
		}
		mOverlays.add(mItemizedOverlay);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub

	}

}

package com.robodex.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;


public class LocationUpdater {
	private static final String LOG_TAG 	= LocationUpdater.class.getSimpleName();
	private static final int 	TWO_MINUTES = 1000 * 60 * 2;

	public static interface LocationUpdateListener {
		void onLocationUpdated(Location location);
		void onTimeLimitExceeded(Location location);
	}

	private final 	LocationManager 	mLocationManager;
	private			Location			mBestLocation;

	private	final	ProviderListener	mGpsListener;
	private	final	ProviderListener	mNetworkListener;
	private	final	ProviderListener	mPassiveListener;

	private	final	LocationUpdateListener mLocationUpdateListener;

	private long mTimeLimit;

	public LocationUpdater(Context context, LocationUpdateListener listener) {
		mLocationManager 	= (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		mLocationUpdateListener = listener;
		mGpsListener 		= new ProviderListener(LocationManager.GPS_PROVIDER);
		mNetworkListener	= new ProviderListener(LocationManager.NETWORK_PROVIDER);
		mPassiveListener	= new ProviderListener(LocationManager.PASSIVE_PROVIDER);
		setInitialLocation();
	}

	public void setTimeLimit(long millis) {
		mTimeLimit = millis;
	}

	/** Can be used to get a location without having to listen for location changes. */
	public Location getBestLocation() {
		return new Location(mBestLocation);
	}

	public void startListeningPassively() {
		mPassiveListener.startListening();
	}

	public void startListeningToGps() {
		mGpsListener.startListening();
	}

	public void startListeningToNetwork() {
		mNetworkListener.startListening();
	}

	public void stopListeningPassively() {
		mPassiveListener.stopListening();
	}

	public void stopListeningToGps() {
		mGpsListener.stopListening();
	}

	public void stopListeningToNetwork() {
		mNetworkListener.stopListening();
	}

	public void startListeningToAll() {
		startListeningToGps();
		startListeningToNetwork();
		startListeningPassively();
	}

	public void stopListeningToAll() {
		stopListeningToGps();
		stopListeningToNetwork();
		stopListeningPassively();
	}

	private void setInitialLocation() {
		for (String provider : mLocationManager.getAllProviders()) {
			Location location = mLocationManager.getLastKnownLocation(provider);
			if (location == null) continue;
			if (isBetterLocation(location, mBestLocation)) {
				mBestLocation = location;
			}
		}
	}

	private class ProviderListener implements LocationListener {
		private final String mProvider;
		private long mStartTime;

		ProviderListener(String provider) {
			mProvider = provider;
		}

		void startListening() {
			mLocationManager.requestLocationUpdates(mProvider, 0, 0, this);
			mStartTime = System.currentTimeMillis();
		}

		void stopListening() {
			mLocationManager.removeUpdates(this);
		}

		@Override
		public void onLocationChanged(Location location) {
			if (location == null) {
				Log.e(LOG_TAG, mProvider + ": Received null location!");
				return;
			}
			if (isBetterLocation(location, mBestLocation)) {
				mBestLocation = location;
				if (mLocationUpdateListener != null) {
					mLocationUpdateListener.onLocationUpdated(location);
				}
			}

			if (System.currentTimeMillis() - mStartTime > mTimeLimit) {
				if (mLocationUpdateListener != null) {
					mLocationUpdateListener.onTimeLimitExceeded(mBestLocation);
				}
			}
		}
		@Override public void onStatusChanged(String provider, int status, Bundle extras) {}
		@Override public void onProviderEnabled(String provider) {}
		@Override public void onProviderDisabled(String provider) {}
	}


	/** Determines whether one Location reading is better than the current Location fix
	  * @param location  The new Location that you want to evaluate
	  * @param currentBestLocation  The current Location fix, to which you want to compare the new one
	  */
	private boolean isBetterLocation(Location location, Location currentBestLocation) {
	    if (currentBestLocation == null) {
	        // A new location is always better than no location
	        return true;
	    }

	    // Check whether the new location fix is newer or older
	    long timeDelta = location.getTime() - currentBestLocation.getTime();
	    boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
	    boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
	    boolean isNewer = timeDelta > 0;

	    // If it's been more than two minutes since the current location, use the new location
	    // because the user has likely moved
	    if (isSignificantlyNewer) {
	        return true;
	    // If the new location is more than two minutes older, it must be worse
	    } else if (isSignificantlyOlder) {
	        return false;
	    }

	    // Check whether the new location fix is more or less accurate
	    int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
	    boolean isLessAccurate = accuracyDelta > 0;
	    boolean isMoreAccurate = accuracyDelta < 0;
	    boolean isSignificantlyLessAccurate = accuracyDelta > 200;

	    // Check if the old and new location are from the same provider
	    boolean isFromSameProvider = isSameProvider(location.getProvider(),
	            currentBestLocation.getProvider());

	    // Determine location quality using a combination of timeliness and accuracy
	    if (isMoreAccurate) {
	        return true;
	    } else if (isNewer && !isLessAccurate) {
	        return true;
	    } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
	        return true;
	    }
	    return false;
	}

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
	    if (provider1 == null) {
	      return provider2 == null;
	    }
	    return provider1.equals(provider2);
	}
}

package com.robodex.app;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.robodex.R;
import com.robodex.Robodex;
import com.robodex.data.DatabaseContract;
import com.robodex.request.BaseRequest;
import com.robodex.request.ListLinks;
import com.robodex.request.ListLocationsByOrganization;
import com.robodex.request.ListOrganizations;
import com.robodex.request.ListPeopleBySpecialty;
import com.robodex.request.ListSpecialties;
public class ItemListFragment extends SherlockListFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {
	private static final String LOG_TAG = ItemListFragment.class.getSimpleName();

	interface Callbacks {
		void onInvalidListType();
		void onNoItems();
		void onInvalidItems();
        void onItemSelected(int position);
    }

	// Used temporarily during lifecycle methods
	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override public void onInvalidListType() {}
		@Override public void onNoItems() {}
		@Override public void onInvalidItems() {}
        @Override public void onItemSelected(int position) {}
    };

    static final String ARG_LIST_TYPE 		= "type_of_list";
    static final String ARG_ORGANIZATION_ID = "organization_id";	// to list locations
    static final String ARG_SPECIALTY_ID 	= "specialty_id";		// to list people
    static final String ARG_ORGANIZATION 	= "organization";    	// to set title
    static final String ARG_SPECIALTY 		= "specialty";			// to set title

    static final int LIST_TYPE_SPECIALTIES 		   = 1;
    static final int LIST_TYPE_ORGANIZATIONS 	   = 2;
    static final int LIST_TYPE_LOCATIONS 		   = 3;
    static final int LIST_TYPE_PEOPLE_BY_SPECIALTY = 4;
    static final int LIST_TYPE_MAP 				   = 5;
    static final int LIST_TYPE_LINKS 			   = 6;
    static final int LIST_TYPE_SEARCH_ALL 		   = 7;

    private static final String STATE_ACTIVATED_POSITION = "activated_item_position";

    private int			  mListType;
    private int 		  mActivatedPosition = ListView.INVALID_POSITION;
    private Callbacks 	  mCallbacks 		= sDummyCallbacks;
    private BaseRequest   mRequest;
    private CursorAdapter mListAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mListType = getArguments().getInt(ARG_LIST_TYPE);

        switch (mListType) {
    	case LIST_TYPE_LINKS:
    		getActivity().setTitle(R.string.links);
    		mRequest     = new ListLinks();
            mListAdapter = new LinkAdapter(getActivity());
    		break;
    	case LIST_TYPE_LOCATIONS:
    		getActivity().setTitle(getArguments().getString(ARG_ORGANIZATION) + " " + getString(R.string.locations));
    		mRequest     = new ListLocationsByOrganization(getArguments().getInt(ARG_ORGANIZATION_ID));
    		mListAdapter = new LocationAdapter(getActivity());
    		break;
    	case LIST_TYPE_MAP:
    		getActivity().setTitle(R.string.map_items);
    		mRequest     = null;
    		mListAdapter = new MapAdapter(getActivity());
    		break;
    	case LIST_TYPE_ORGANIZATIONS:
    		getActivity().setTitle(R.string.organizations);
    		mRequest     = new ListOrganizations();
            mListAdapter = new BasicAdapter(getActivity(), DatabaseContract.ListOrganizations.COL_ORGANIZATION);
    		break;
    	case LIST_TYPE_PEOPLE_BY_SPECIALTY:
    		getActivity().setTitle(getArguments().getString(ARG_SPECIALTY) + " " + getString(R.string.specialists));
    		mRequest     = new ListPeopleBySpecialty(getArguments().getInt(ARG_SPECIALTY_ID));
    		mListAdapter = new PersonAdapter(getActivity());
    		break;
    	case LIST_TYPE_SEARCH_ALL:
    		getActivity().setTitle(R.string.search_results);
    		mRequest   	 = null;
    		mListAdapter = new SearchAdapter(getActivity());
    		break;
    	case LIST_TYPE_SPECIALTIES:
    		getActivity().setTitle(R.string.specialties);
    		mRequest   	 = new ListSpecialties();
            mListAdapter = new BasicAdapter(getActivity(), DatabaseContract.ListSpecialties.COL_SPECIALTY);
    		break;
		default:
        	mCallbacks.onInvalidListType();
        	return;
    	}

        getLoaderManager().initLoader(mListType, null, this);
        if (mRequest != null) mRequest.execute();
        setListAdapter(mListAdapter);
    }




    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null && savedInstanceState
                .containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        mCallbacks.onItemSelected(position);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                        : ListView.CHOICE_MODE_NONE);
    }

    public void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }




    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    	CursorLoader cursorLoader = null;
    	Uri contentUri = null;
    	String[] projection = null;

    	switch (id) {
    	case LIST_TYPE_SPECIALTIES:
    		contentUri = DatabaseContract.ListSpecialties.CONTENT_URI;
    		projection = new String[] {
				DatabaseContract.ListSpecialties.COL_ID,
				DatabaseContract.ListSpecialties.COL_SPECIALTY_ID,
				DatabaseContract.ListSpecialties.COL_SPECIALTY
			};
    		break;
    	case LIST_TYPE_ORGANIZATIONS:
    		contentUri = DatabaseContract.ListOrganizations.CONTENT_URI;
    		projection = new String[] {
				DatabaseContract.ListOrganizations.COL_ID,
				DatabaseContract.ListOrganizations.COL_ORGANIZATION_ID,
				DatabaseContract.ListOrganizations.COL_ORGANIZATION
			};
    		break;
    	case LIST_TYPE_LINKS:
    		contentUri = DatabaseContract.ListLinks.CONTENT_URI;
    		projection = new String[] {
				DatabaseContract.ListLinks.COL_ID,
				DatabaseContract.ListLinks.COL_LINK_ID,
				DatabaseContract.ListLinks.COL_TITLE,
				DatabaseContract.ListLinks.COL_LINK
			};
    		break;
    	case LIST_TYPE_LOCATIONS:
    		contentUri = DatabaseContract.ListLocationsByOrganization.CONTENT_URI;
    		projection = new String[] {
				DatabaseContract.ListLocationsByOrganization.COL_ID,
				DatabaseContract.ListLocationsByOrganization.COL_LOCATION_ID,
				DatabaseContract.ListLocationsByOrganization.COL_PRIMARY,
				DatabaseContract.ListLocationsByOrganization.COL_ADDRESS,
				DatabaseContract.ListLocationsByOrganization.COL_CITY,
				DatabaseContract.ListLocationsByOrganization.COL_STATE,
				DatabaseContract.ListLocationsByOrganization.COL_ZIP
			};
    		break;
    	case LIST_TYPE_MAP:
    		contentUri = DatabaseContract.ListMap.CONTENT_URI;
    		projection = new String[] {
				DatabaseContract.ListMap.COL_ID,
				DatabaseContract.ListMap.COL_LOCATION_ID,
				DatabaseContract.ListMap.COL_PERSON_ID,
				DatabaseContract.ListMap.COL_PRIMARY,
				DatabaseContract.ListMap.COL_ORGANIZATION,
				DatabaseContract.ListMap.COL_ADDRESS,
				DatabaseContract.ListMap.COL_CITY,
				DatabaseContract.ListMap.COL_STATE,
				DatabaseContract.ListMap.COL_ZIP,
				DatabaseContract.ListMap.COL_LATITUDE,
				DatabaseContract.ListMap.COL_LONGITUDE,
				DatabaseContract.ListMap.COL_FIRST_NAME,
				DatabaseContract.ListMap.COL_LAST_NAME,
				DatabaseContract.ListMap.COL_LOCATION_TIME
			};
    		break;
    	case LIST_TYPE_PEOPLE_BY_SPECIALTY:
    		contentUri = DatabaseContract.ListPeopleBySpecialty.CONTENT_URI;
    		projection = new String[] {
				DatabaseContract.ListPeopleBySpecialty.COL_ID,
				DatabaseContract.ListPeopleBySpecialty.COL_PERSON_ID,
				DatabaseContract.ListPeopleBySpecialty.COL_CITY,
				DatabaseContract.ListPeopleBySpecialty.COL_STATE,
				DatabaseContract.ListPeopleBySpecialty.COL_ZIP,
				DatabaseContract.ListPeopleBySpecialty.COL_FIRST_NAME,
				DatabaseContract.ListPeopleBySpecialty.COL_LAST_NAME
			};
    		break;
    	case LIST_TYPE_SEARCH_ALL:
    		contentUri = DatabaseContract.SearchAll.CONTENT_URI;
    		projection = new String[] {
    				DatabaseContract.SearchAll.COL_ID,
    				DatabaseContract.SearchAll.COL_LOCATION_ID,
    				DatabaseContract.SearchAll.COL_ORGANIZATION_ID,
    				DatabaseContract.SearchAll.COL_SPECIALTY_ID,
    				DatabaseContract.SearchAll.COL_PERSON_ID,
    				DatabaseContract.SearchAll.COL_LINK_ID,
    				DatabaseContract.SearchAll.COL_PRIMARY,
    				DatabaseContract.SearchAll.COL_ORGANIZATION,
    				DatabaseContract.SearchAll.COL_SPECIALTY,
    				DatabaseContract.SearchAll.COL_LINK_TITLE,
    				DatabaseContract.SearchAll.COL_LINK,
    				DatabaseContract.SearchAll.COL_ADDRESS,
    				DatabaseContract.SearchAll.COL_CITY,
    				DatabaseContract.SearchAll.COL_STATE,
    				DatabaseContract.SearchAll.COL_ZIP,
    				DatabaseContract.SearchAll.COL_FIRST_NAME,
    				DatabaseContract.SearchAll.COL_LAST_NAME,
			};
    		break;
		default:
			Log.w(LOG_TAG, "Unknown loader id: " + id);
			return null;
    	}

    	cursorLoader = new CursorLoader(getActivity(), contentUri, projection, null, null, null);

    	return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
    	if (Robodex.DEBUG) Log.i(LOG_TAG, "onLoadFinished()");
    	if (cursor == null || cursor.getCount() < 1) {
    		mCallbacks.onNoItems();
    		return;
    	}
    	try {
    		mListAdapter.swapCursor(cursor);
    	}
    	catch (Exception e) {
    		mCallbacks.onInvalidItems();
    	}
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    	if (Robodex.DEBUG) Log.i(LOG_TAG, "onLoaderReset()");
    	mListAdapter.swapCursor(null);
    }






    private static class BasicAdapter extends CursorAdapter {
    	private final String mDbColumn;
		public BasicAdapter(Context context, String dbColumn) {
			super(context, null, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
			mDbColumn = dbColumn;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_activated_1, parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			((TextView) view.findViewById(android.R.id.text1)).setText(
				cursor.getString(cursor.getColumnIndex(mDbColumn))
			);
		}
    }

    private static class LinkAdapter extends CursorAdapter {
		public LinkAdapter(Context context) {
			super(context, null, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_activated_1, parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			Hyperlink.setText(((TextView) view.findViewById(android.R.id.text1)),
					cursor.getString(cursor.getColumnIndex(DatabaseContract.ListLinks.COL_TITLE)),
					cursor.getString(cursor.getColumnIndex(DatabaseContract.ListLinks.COL_LINK)));
		}
    }

    private static abstract class BasicTwoItemAdapter extends CursorAdapter {

		public BasicTwoItemAdapter(Context context) {
			super(context, null, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		}

		@Override
		public final View newView(Context context, Cursor cursor, ViewGroup parent) {
			return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_activated_2, parent, false);
		}

		@Override
		public final void bindView(View view, Context context, Cursor cursor) {
			bindText1((TextView) view.findViewById(android.R.id.text1), cursor);
			bindText2((TextView) view.findViewById(android.R.id.text2), cursor);
		}

		protected abstract void bindText1(TextView tv, Cursor cursor);
		protected abstract void bindText2(TextView tv, Cursor cursor);
    }

    private static class PersonAdapter extends BasicTwoItemAdapter {
		public PersonAdapter(Context context) {
			super(context);
		}

		@Override
		protected void bindText1(TextView tv, Cursor cursor) {
			tv.setText(
				cursor.getString(cursor.getColumnIndex(DatabaseContract.ListPeopleBySpecialty.COL_FIRST_NAME))
				+ " " +
				cursor.getString(cursor.getColumnIndex(DatabaseContract.ListPeopleBySpecialty.COL_LAST_NAME)));
		}

		@Override
		protected void bindText2(TextView tv, Cursor cursor) {
			tv.setText(
				cursor.getString(cursor.getColumnIndex(DatabaseContract.ListPeopleBySpecialty.COL_CITY))
				+ ", " +
				cursor.getString(cursor.getColumnIndex(DatabaseContract.ListPeopleBySpecialty.COL_STATE))
				+ " " +
				cursor.getString(cursor.getColumnIndex(DatabaseContract.ListLocationsByOrganization.COL_ZIP)));
		}
    }

    private static class LocationAdapter extends BasicTwoItemAdapter {
		public LocationAdapter(Context context) {
			super(context);
		}

		@Override
		protected void bindText1(TextView tv, Cursor cursor) {
			int primary = cursor.getInt(cursor.getColumnIndex(DatabaseContract.ListLocationsByOrganization.COL_PRIMARY));
			tv.setText(
				cursor.getString(cursor.getColumnIndex(DatabaseContract.ListLocationsByOrganization.COL_CITY))
				+ ", " +
				cursor.getString(cursor.getColumnIndex(DatabaseContract.ListLocationsByOrganization.COL_STATE))
				+ ((primary == 1) ? " (Primary)" : ""));
		}

		@Override
		protected void bindText2(TextView tv, Cursor cursor) {
			tv.setText(
				cursor.getString(cursor.getColumnIndex(DatabaseContract.ListLocationsByOrganization.COL_ADDRESS))
				+ ", " +
				cursor.getString(cursor.getColumnIndex(DatabaseContract.ListLocationsByOrganization.COL_ZIP)));
		}
    }

    private static class MapAdapter extends BasicTwoItemAdapter {
		public MapAdapter(Context context) {
			super(context);
		}

		@Override
		protected void bindText1(TextView tv, Cursor cursor) {
			String locId = cursor.getString(cursor.getColumnIndex(DatabaseContract.ListMap.COL_LOCATION_ID));
			if (locId != null && locId.length() > 0) {
				int primary = cursor.getInt(cursor.getColumnIndex(DatabaseContract.ListMap.COL_PRIMARY));
				tv.setText(
					cursor.getString(cursor.getColumnIndex(DatabaseContract.ListMap.COL_ORGANIZATION))
					+ ((primary == 1) ? " (Primary)" : ""));
			}
			else {
				String locTime = cursor.getString(cursor.getColumnIndex(DatabaseContract.ListMap.COL_LOCATION_TIME));
				tv.setText(
					cursor.getString(cursor.getColumnIndex(DatabaseContract.ListMap.COL_FIRST_NAME))
					+ " " +
					cursor.getString(cursor.getColumnIndex(DatabaseContract.ListMap.COL_LAST_NAME))
					+ ((locTime != null && locTime.length() > 0) ? " (Check-In)" : ""));
			}
		}

		@Override
		protected void bindText2(TextView tv, Cursor cursor) {
			String text = cursor.getString(cursor.getColumnIndex(DatabaseContract.ListMap.COL_ADDRESS))
					+ " " +
					cursor.getString(cursor.getColumnIndex(DatabaseContract.ListMap.COL_CITY))
					+ ", " +
					cursor.getString(cursor.getColumnIndex(DatabaseContract.ListMap.COL_STATE))
					+ " " +
					cursor.getString(cursor.getColumnIndex(DatabaseContract.ListMap.COL_ZIP));

			String locTime = cursor.getString(cursor.getColumnIndex(DatabaseContract.ListMap.COL_LOCATION_TIME));
			if (locTime != null && locTime.length() > 0) {
				text += " (" + locTime + ") ";
			}
			tv.setText(text);
		}
    }

    private static class SearchAdapter extends BasicTwoItemAdapter {
		public SearchAdapter(Context context) {
			super(context);
		}

		@Override
		protected void bindText1(TextView tv, Cursor cursor) {
			String locId = cursor.getString(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_LOCATION_ID));
			String orgId = cursor.getString(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_ORGANIZATION_ID));
			String specId = cursor.getString(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_SPECIALTY_ID));
			String personId = cursor.getString(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_PERSON_ID));
			String linkId = cursor.getString(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_LINK_ID));
			CharSequence text = "";
			if (locId != null && locId.length() > 0) {
				int primary = cursor.getInt(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_PRIMARY));
				text = cursor.getString(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_ORGANIZATION))
						+ ((primary == 1) ? " (Primary)" : "");
			}
			else if (orgId != null && orgId.length() > 0) {
				int primary = cursor.getInt(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_PRIMARY));
				text = cursor.getString(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_ORGANIZATION))
						+ ((primary == 1) ? " (Primary)" : "");
			}
			else if (specId != null && specId.length() > 0) {
				text = cursor.getString(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_SPECIALTY));
			}
			else if (personId != null && personId.length() > 0) {
				text = cursor.getString(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_FIRST_NAME))
						+ " " +
						cursor.getString(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_LAST_NAME));
			}


			if (linkId != null && linkId.length() > 0) {
				Hyperlink.setText(tv,
						cursor.getString(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_LINK_TITLE)),
						cursor.getString(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_LINK)));
			}
			else tv.setText(text);
		}

		@Override
		protected void bindText2(TextView tv, Cursor cursor) {
			String locId = cursor.getString(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_LOCATION_ID));
			String orgId = cursor.getString(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_ORGANIZATION_ID));
			String specId = cursor.getString(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_SPECIALTY_ID));
			String personId = cursor.getString(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_PERSON_ID));
			String linkId = cursor.getString(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_LINK_ID));
			CharSequence text = "";
			if (locId != null && locId.length() > 0) {
				text = cursor.getString(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_ADDRESS))
						+ " " +
						cursor.getString(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_CITY))
						+ ", " +
						cursor.getString(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_STATE))
						+ " " +
						cursor.getString(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_ZIP));
			}
			else if (orgId != null && orgId.length() > 0) {
				// blank
			}
			else if (specId != null && specId.length() > 0) {
				// blank
			}
			else if (personId != null && personId.length() > 0) {
				text = cursor.getString(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_ADDRESS))
						+ " " +
						cursor.getString(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_CITY))
						+ ", " +
						cursor.getString(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_STATE))
						+ " " +
						cursor.getString(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_ZIP));
			}
			else if (linkId != null && linkId.length() > 0) {
				text = cursor.getString(cursor.getColumnIndex(DatabaseContract.SearchAll.COL_LINK));
			}
			tv.setText(text);
		}
    }
}

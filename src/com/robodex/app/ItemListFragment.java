package com.robodex.app;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.robodex.R;
import com.robodex.Robodex;
import com.robodex.data.DatabaseContract;
import com.robodex.data.DummyData;
import com.robodex.data.DummyData.DummyLink;
import com.robodex.data.DummyData.DummyLocation;
import com.robodex.request.ListLinks;
import com.robodex.request.ListOrganizations;
import com.robodex.request.ListSpecialties;
public class ItemListFragment extends SherlockListFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {
	private static final String LOG_TAG = ItemListFragment.class.getSimpleName();

    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    public static final String ARG_MAIN_ITEM_ID = "main_item_id";
    public static final String ARG_CATEGORY_ITEM_ID = "category_item_id";
    private static final String DEFAULT_MAIN_ITEM_ID = "0";

    private static final int SPECIALTY_LIST_LOADER = 1;
    private static final int ORGANIZATION_LIST_LOADER = 2;
    private static final int LINK_LIST_LOADER = 3;
    private static final int SEARCH_RESULTS_LIST_LOADER = 4;

    private SimpleCursorAdapter mCursorAdapter;

    public interface Callbacks {
        public void onItemSelected(int position);
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(int position) {
        }
    };

    private Callbacks mCallbacks = sDummyCallbacks;

    private String mMainItem;

    private int mActivatedPosition = ListView.INVALID_POSITION;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_MAIN_ITEM_ID)) {
            mMainItem = getArguments().getString(ARG_MAIN_ITEM_ID);
        }
        if (mMainItem == null || mMainItem.length() == 0) mMainItem = DEFAULT_MAIN_ITEM_ID;

        int pos = Integer.valueOf(mMainItem);
        String[] mainItems = getResources().getStringArray(R.array.main_items);

        getActivity().setTitle(mainItems[pos]);

        String [] items = {};

        if (mainItems[pos].equals(getResources().getString(R.string.specialties))) {
//            items = DummyData.SPECIALTIES;


        	getLoaderManager().initLoader(SPECIALTY_LIST_LOADER, null, this);

        	String[] uiBindFrom = { DatabaseContract.ListSpecialties.COL_SPECIALTY};
            int[] uiBindTo = { android.R.id.text1 };

        	mCursorAdapter = new SimpleCursorAdapter(
                    getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,
                    null, uiBindFrom, uiBindTo,
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

            ListSpecialties request = new ListSpecialties();
            request.execute();

            setListAdapter(mCursorAdapter);
        }
        else if (mainItems[pos].equals(getResources().getString(R.string.organizations))) {
//          items = DummyData.AGENCIES;
	      	getLoaderManager().initLoader(ORGANIZATION_LIST_LOADER, null, this);

	      	String[] uiBindFrom = { DatabaseContract.ListOrganizations.COL_ORGANIZATION};
	          int[] uiBindTo = { android.R.id.text1 };

	      	mCursorAdapter = new SimpleCursorAdapter(
	                  getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,
	                  null, uiBindFrom, uiBindTo,
	                  CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

	      	ListOrganizations request = new ListOrganizations();
	      	request.execute();

	      	setListAdapter(mCursorAdapter);
        }
        else if (mainItems[pos].equals(getResources().getString(R.string.near_me))) {
            ArrayList<String> list = new ArrayList<String>();
            for (DummyLocation dl : DummyData.LOCATIONS) {
                list.add(dl.toString());
            }
            items = list.toArray(new String[list.size()]);
            setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items));
        }
        else if (mainItems[pos].equals(getResources().getString(R.string.links))) {
//            ArrayList<String> list = new ArrayList<String>();
//            for (DummyLink dl : DummyData.LINKS) {
//                list.add(dl.toString());
//            }
//            items = list.toArray(new String[list.size()]);
//
//            setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items));
        	getLoaderManager().initLoader(LINK_LIST_LOADER, null, this);

	      	String[] uiBindFrom = { DatabaseContract.ListLinks.COL_TITLE};
	          int[] uiBindTo = { android.R.id.text1 };

	      	mCursorAdapter = new SimpleCursorAdapter(
	                  getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,
	                  null, uiBindFrom, uiBindTo,
	                  CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

	      	ListLinks request = new ListLinks();
	      	request.execute();

	      	setListAdapter(mCursorAdapter);
        }


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

    	switch (id) {
    	case SPECIALTY_LIST_LOADER:
    		String[] projection = { DatabaseContract.ListSpecialties.COL_ID, DatabaseContract.ListSpecialties.COL_SPECIALTY };
            cursorLoader = new CursorLoader(getActivity(),
            		DatabaseContract.ListSpecialties.CONTENT_URI, projection, null, null, null);
    		break;
    	case ORGANIZATION_LIST_LOADER:
    		String[] projection1 = { DatabaseContract.ListOrganizations.COL_ID, DatabaseContract.ListOrganizations.COL_ORGANIZATION };
            cursorLoader = new CursorLoader(getActivity(),
            		DatabaseContract.ListOrganizations.CONTENT_URI, projection1, null, null, null);
    		break;
    	case LINK_LIST_LOADER:
    		String[] projection2 = { DatabaseContract.ListLinks.COL_ID, DatabaseContract.ListLinks.COL_TITLE };
            cursorLoader = new CursorLoader(getActivity(),
            		DatabaseContract.ListLinks.CONTENT_URI, projection2, null, null, null);
    		break;
		default:

			break;
    	}

    	if (cursorLoader == null) {
    		Log.d(LOG_TAG, "cursorLoader is null for id " + id);
    	}

    	return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

    	mCursorAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    	if (Robodex.DEBUG) Log.i(LOG_TAG, "onLoaderReset()");

    	mCursorAdapter.swapCursor(null);
    }
}

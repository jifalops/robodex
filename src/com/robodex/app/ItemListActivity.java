package com.robodex.app;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.widget.Toast;

import com.actionbarsherlock.view.MenuItem;
import com.robodex.R;
import com.robodex.data.DatabaseContract;

public class ItemListActivity extends BaseActivity implements ItemListFragment.Callbacks {
	private static final String LOG_TAG = ItemListActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            args.putInt(ItemListFragment.ARG_LIST_TYPE,
                    getIntent().getIntExtra(ItemListFragment.ARG_LIST_TYPE, 0));
            ItemListFragment fragment = new ItemListFragment();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
            	.add(R.id.fragment_container, fragment)
            	.commit();
        }
    }

    private void replaceFragment(Bundle args) {
        ItemListFragment fragment = new ItemListFragment();
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
        	.replace(R.id.fragment_container, fragment)
        	.addToBackStack(String.valueOf(args.getInt(ItemListFragment.ARG_LIST_TYPE)))
        	.commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            Intent parentActivityIntent = new Intent(this, MainActivity.class);
            parentActivityIntent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(parentActivityIntent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(int position) {
    	ItemListFragment f = (ItemListFragment) getSupportFragmentManager()
    			.findFragmentById(R.id.fragment_container);
    	Cursor c = ((CursorAdapter) f.getListAdapter()).getCursor();
    	c.moveToPosition(position);

    	int specIndex 	= c.getColumnIndex(DatabaseContract.SearchAll.COL_SPECIALTY_ID);
    	int specId 		= specIndex < 0 ? 0 : c.getInt(specIndex);
    	int locIndex 	= c.getColumnIndex(DatabaseContract.SearchAll.COL_LOCATION_ID);
    	int locId 		= locIndex < 0 ? 0 : c.getInt(locIndex);
    	int orgIndex 	= c.getColumnIndex(DatabaseContract.SearchAll.COL_ORGANIZATION_ID);
    	int orgId	 	= orgIndex < 0 ? 0 : c.getInt(orgIndex);
    	int personIndex = c.getColumnIndex(DatabaseContract.SearchAll.COL_PERSON_ID);
    	int personId 	= personIndex < 0 ? 0 : c.getInt(personIndex);

    	if (specIndex >= 0 && specId > 0) {
        	Bundle args = new Bundle();
        	args.putInt(ItemListFragment.ARG_LIST_TYPE, ItemListFragment.LIST_TYPE_PEOPLE_BY_SPECIALTY);
        	args.putInt(ItemListFragment.ARG_SPECIALTY_ID, specId);
        	args.putString(ItemListFragment.ARG_SPECIALTY,
        			c.getString(c.getColumnIndex(DatabaseContract.SearchAll.COL_SPECIALTY)));
        	replaceFragment(args);
    	}
    	else if (locIndex >= 0 && locId > 0) {
    		Intent details = new Intent(this, DetailActivity.class);
    		details.putExtra(DetailFragment.ARG_DETAIL_TYPE, DetailFragment.DETAIL_TYPE_LOCATION);
    		details.putExtra(DetailFragment.ARG_ITEM_ID, locId);
    		startActivity(details);
    	}
    	// Organization must be checked after location
    	else if (orgIndex >= 0 && orgId > 0) {
        	Bundle args = new Bundle();
        	args.putInt(ItemListFragment.ARG_LIST_TYPE, ItemListFragment.LIST_TYPE_LOCATIONS);
        	args.putInt(ItemListFragment.ARG_ORGANIZATION_ID, orgId);
        	args.putString(ItemListFragment.ARG_ORGANIZATION,
        			c.getString(c.getColumnIndex(DatabaseContract.SearchAll.COL_ORGANIZATION)));
        	replaceFragment(args);
    	}
    	else if (personIndex >= 0 && personId > 0) {
    		Intent details = new Intent(this, DetailActivity.class);
    		details.putExtra(DetailFragment.ARG_DETAIL_TYPE, DetailFragment.DETAIL_TYPE_PERSON);
    		details.putExtra(DetailFragment.ARG_ITEM_ID, personId);
    		startActivity(details);
    	}
    	else {
    		Log.w(LOG_TAG, "Unknown list item clicked");
    	}
    }


	@Override
	public void onInvalidListType() {
		Log.e(LOG_TAG, "InvalidListType");
		Toast.makeText(this, getString(R.string.error_invalid_list_type), Toast.LENGTH_LONG).show();
	}
}

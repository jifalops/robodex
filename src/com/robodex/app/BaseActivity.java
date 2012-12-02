package com.robodex.app;

import android.content.Intent;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;
import com.robodex.R;

public abstract class BaseActivity extends SherlockFragmentActivity {
    private SearchView mSearchView;
    private Menu mMenu;

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getSupportMenuInflater().inflate(R.menu.main, menu);
        mMenu = menu;
        //Create the search view
        mSearchView = new SearchView(getSupportActionBar().getThemedContext());
        mSearchView.setQueryHint(getString(R.string.search_hint_default));
        mSearchView.setOnQueryTextListener(new OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				Intent searchIntent = new Intent(BaseActivity.this, ItemListActivity.class);
				searchIntent.putExtra(ItemListFragment.ARG_LIST_TYPE, ItemListFragment.LIST_TYPE_SEARCH_ALL);
				searchIntent.putExtra(ItemListFragment.ARG_SEARCH_TERMS, query);
				startActivity(searchIntent);
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});

        (menu.findItem(R.id.menu_search)).setActionView(mSearchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (Robodex.DEBUG) {
//            Toast.makeText(this,"option: " + item.getTitle(), Toast.LENGTH_SHORT).show();
//        }

        return super.onOptionsItemSelected(item);
    }

    protected void focusSearchView() {
    	if (mSearchView == null) return;
    	mSearchView.setIconified(false);
    	mSearchView.requestFocusFromTouch();
    }

    protected void setSearchViewVisibility(boolean show) {
    	if (mMenu == null) return;
    	if (show) (mMenu.findItem(R.id.menu_search)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    	else (mMenu.findItem(R.id.menu_search)).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
    }
}

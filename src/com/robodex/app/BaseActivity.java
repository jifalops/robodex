package com.robodex.app;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;

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

        (menu.findItem(R.id.search)).setActionView(mSearchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if (item.getItemId() == R.id.about) {
    		new AlertDialog.Builder(this).setTitle(R.string.app_name)
	    		.setMessage("Verson: " + getAppVersionName(this)
	    				+ " (" + getAppVersionCode(this) + ") BETA\n")
				.setPositiveButton("OK", null)
				.setNegativeButton("Report Bug", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						startActivity(new Intent(Intent.ACTION_VIEW,
								Uri.parse("https://github.com/jphilli85/robodex/issues")));
					}
				})
	    		.show();
    	}

        return super.onOptionsItemSelected(item);
    }

    protected void focusSearchView() {
    	if (mSearchView == null) return;
    	mSearchView.setIconified(false);
    	mSearchView.requestFocusFromTouch();
    }

    protected void setSearchViewVisibility(boolean show) {
    	if (mMenu == null) return;
    	if (show) (mMenu.findItem(R.id.search)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    	else (mMenu.findItem(R.id.search)).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
    }

    private static String getAppVersionName(Context context) {
    	try {
    		ComponentName comp = new ComponentName(context, context.getClass());
    		PackageInfo info = context.getPackageManager().getPackageInfo(
    				comp.getPackageName(), 0);
    		return info.versionName;
    	}
    	catch (android.content.pm.PackageManager.NameNotFoundException e) {
    	    return null;
    	}
    }

    private static int getAppVersionCode(Context context) {
    	try {
    		ComponentName comp = new ComponentName(context, context.getClass());
    		PackageInfo info = context.getPackageManager().getPackageInfo(
    				comp.getPackageName(), 0);
    		return info.versionCode;
    	}
    	catch (android.content.pm.PackageManager.NameNotFoundException e) {
    	    return -1;
    	}
    }
}

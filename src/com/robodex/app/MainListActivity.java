package com.robodex.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.robodex.R;
import com.robodex.Robodex;

public class MainListActivity extends BaseActivity implements MainListFragment.Callbacks,
		ItemListFragment.Callbacks {
    private static final String LOG_TAG = MainListActivity.class.getSimpleName();

    private boolean mTwoPane;

    private int mMainItem;
    private int mListItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        if (findViewById(R.id.fragment_container) != null) {
            mTwoPane = true;
            ((MainListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.list))
                    .setActivateOnItemClick(true);
        }
    }

    @Override
    public void onMainItemSelected(int position) {
//        if (Robodex.DEBUG) {
//            Toast.makeText(this,"main item selected, position: " + position, Toast.LENGTH_SHORT).show();
//        }

        mMainItem = position;

        if (position == 1) {
            startActivity(new Intent(this, MyMapActivity.class));
        }
        else if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putInt(ItemListFragment.ARG_LIST_TYPE, mMainItem);
            ItemListFragment fragment = new ItemListFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit();
        }
        else {
            Intent category = new Intent(this, ItemListActivity.class);
            category.putExtra(ItemListFragment.ARG_LIST_TYPE, mMainItem);
            startActivity(category);
        }
    }


    @Override
    public void onItemSelected(int position) {
//        if (Robodex.DEBUG) {
//            Toast.makeText(this,"category item selected, position: " + position, Toast.LENGTH_SHORT).show();
//        }

        mListItem = position;

        if (mTwoPane) {
            Bundle arguments = new Bundle();
//            arguments.putInt(DetailFragment.ARG_MAIN_ITEM_ID, mMainItem);
//            arguments.putInt(DetailFragment.ARG_CATEGORY_ITEM_ID, mListItem);
            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit();
        }
        else {
            // This should never happen
            Log.wtf(LOG_TAG, "Going from main activity to detail activity");

            Intent details = new Intent(this, DetailActivity.class);
//            details.putExtra(DetailFragment.ARG_MAIN_ITEM_ID, mMainItem);
//            details.putExtra(DetailFragment.ARG_CATEGORY_ITEM_ID, mListItem);
            startActivity(details);
        }
    }

	@Override
	public void onInvalidListType() {
		Toast.makeText(this, getString(R.string.error_invalid_list_type), Toast.LENGTH_LONG).show();
	}
}

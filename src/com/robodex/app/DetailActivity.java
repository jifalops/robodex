package com.robodex.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.robodex.R;
import com.robodex.Robodex;

public class DetailActivity extends BaseActivity implements DetailFragment.Callbacks {
	private static final String LOG_TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
	            .add(R.id.detail_container, fragment)
	            .commit();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (Robodex.DEBUG) {
//            Toast.makeText(this,"detail option: " + item.getTitle(), Toast.LENGTH_SHORT).show();
//        }

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
	public void onInvalidDetailType() {
		Log.e(LOG_TAG, "InvalidDetailType");
		Toast.makeText(this, getString(R.string.error_invalid_details_type), Toast.LENGTH_LONG).show();
	}


	@Override
	public void onNoDetails() {
		Log.w(LOG_TAG, "NoDetails");
//		Toast.makeText(this, getString(R.string.error_invalid_details_type), Toast.LENGTH_LONG).show();
	}


	@Override
	public void onInvalidDetails() {
		Log.e(LOG_TAG, "InvalidDetails");
		Toast.makeText(this, getString(R.string.error_invalid_details_type), Toast.LENGTH_LONG).show();
	}
}

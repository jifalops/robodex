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

public class EditActivity extends BaseActivity implements EditFragment.Callbacks {
	private static final String LOG_TAG = EditActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            EditFragment fragment = new EditFragment();
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
	public void onInvalidEditType(int type, int id) {
		Log.e(LOG_TAG, "onInvalidEditType");
		Toast.makeText(this, getString(R.string.error_invalid_details_type), Toast.LENGTH_LONG).show();
	}


	@Override
	public void onNoDetailsToEdit(int type, int id) {
		Log.w(LOG_TAG, "onNoDetailsToEdit");
//		Toast.makeText(this, getString(R.string.error_invalid_details_type), Toast.LENGTH_LONG).show();
	}


	@Override
	public void onInvalidDetailsToEdit(int type, int id) {
		Log.e(LOG_TAG, "onInvalidDetailsToEdit");
		Toast.makeText(this, getString(R.string.error_invalid_details_type), Toast.LENGTH_LONG).show();
	}

	@Override
	public void onSave(int type, int id) {
		Toast.makeText(this, "Saving your changes...", Toast.LENGTH_LONG).show();
	}
}

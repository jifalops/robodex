package com.robodex.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.robodex.R;
import com.robodex.request.CheckIn;
import com.robodex.request.ListMap;


public class MainActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();

		setContentView(R.layout.activity_main);

		((ImageView) findViewById(R.id.search)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getSupportActionBar().show();
				focusSearchView();
			}
		});

		((ImageView) findViewById(R.id.specialties)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ItemListActivity.class);
				intent.putExtra(ItemListFragment.ARG_LIST_TYPE, ItemListFragment.LIST_TYPE_SPECIALTIES);
				startActivity(intent);
			}
		});

		((ImageView) findViewById(R.id.organizations)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ItemListActivity.class);
				intent.putExtra(ItemListFragment.ARG_LIST_TYPE, ItemListFragment.LIST_TYPE_ORGANIZATIONS);
				startActivity(intent);
			}
		});

		((ImageView) findViewById(R.id.links)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ItemListActivity.class);
				intent.putExtra(ItemListFragment.ARG_LIST_TYPE, ItemListFragment.LIST_TYPE_LINKS);
				startActivity(intent);
			}
		});

		((ImageView) findViewById(R.id.checkin)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(MainActivity.this)
				.setMessage(R.string.checkin)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO dyamicanizeit
						(new CheckIn(1)).execute();
						Toast.makeText(MainActivity.this, "Updating your location...", Toast.LENGTH_LONG).show();
					}
				})
				.setNegativeButton("Cancel", null)
	    		.show();

			}
		});

		((ImageView) findViewById(R.id.map)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Build.VERSION.SDK_INT < 11) {
					Toast.makeText(MainActivity.this, "Not supported on your device.", Toast.LENGTH_SHORT).show();
				}
				else {
					Intent intent = new Intent(MainActivity.this, MyMapActivity.class);
					startActivity(intent);
				}
			}
		});
	}
}

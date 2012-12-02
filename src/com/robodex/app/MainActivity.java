package com.robodex.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.robodex.R;
import com.robodex.request.CheckIn;


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
				// TODO dyamicanizeit
				(new CheckIn(1)).execute();
				Toast.makeText(MainActivity.this, "Updating your location...", Toast.LENGTH_LONG).show();
			}
		});

		((ImageView) findViewById(R.id.map)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, MyMapActivity.class);
				startActivity(intent);
			}
		});
	}
}

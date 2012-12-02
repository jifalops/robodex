package com.robodex.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.robodex.R;


public class MainActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		((ImageView) findViewById(R.id.search)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "SEARCH!!!", Toast.LENGTH_SHORT).show();
				// TODO dialog??
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

		((ImageView) findViewById(R.id.about)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "ABOUT!!!", Toast.LENGTH_SHORT).show();
				// TODO make activity
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

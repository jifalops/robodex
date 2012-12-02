package com.robodex.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.robodex.R;

public class DetailFragment extends SherlockFragment {
    static final String ARG_DETAIL_TYPE = "type_of_details";
    static final String ARG_ITEM_ID 	= "item_id";

    static final int DETAIL_TYPE_PERSON   = 1;
    static final int DETAIL_TYPE_LOCATION = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	if (container == null) return null;

    	int type = getArguments().getInt(ARG_DETAIL_TYPE);
    	int id = getArguments().getInt(ARG_ITEM_ID);

        switch (type) {
        case DETAIL_TYPE_LOCATION:
        	return inflater.inflate(R.layout.detail_location, container, false);
        case DETAIL_TYPE_PERSON:
        	return inflater.inflate(R.layout.detail_person, container, false);
    	default:
    		return null;
        }
    }
}

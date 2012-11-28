package com.robodex.request;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;

import com.robodex.data.DatabaseContract;
import com.robodex.request.ServerContract.RequestField;

public final class Login extends BaseRequest {

	private String mUsername;
	private String mPassword;

	public void setUsername(String username) {
		mUsername = username;
	}

	public void setPassword(String password) {
		mPassword = password;
	}

	@Override
	protected void prepareRequest() {
		Map<String, String> request = new HashMap<String, String>();
		request.put(RequestField.USERNAME, mUsername);
		request.put(RequestField.PASSWORD, mPassword);
		executeRequest(request);
	}

	@Override
	protected ContentValues processRowForInsertion(Map<String, String> rowFromResponse) {
		ContentValues rowToInsert = new ContentValues();
		rowToInsert.put(DatabaseContract.Login.COL_AUTH_KEY, rowFromResponse.get(RequestField.AUTH_KEY));
        return rowToInsert;
	}
}

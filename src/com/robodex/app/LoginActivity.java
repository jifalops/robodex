package com.robodex.app;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.robodex.R;
import com.robodex.data.DatabaseContract;
import com.robodex.request.Login;
import com.robodex.request.ServerContract.ResponseCode;



public class LoginActivity extends SherlockFragmentActivity implements
LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOGIN_LOADER = 1;

	Button   btnLogin;
	Button   btnLinkToRegister;
	EditText inputUser;
	EditText inputPassword;
	TextView loginErrorMsg;



	// JSON Response node names
	private static String KEY_SUCCESS    = "success";
	private static String KEY_ERROR      = "error";
	private static String KEY_ERROR_MSG  = "error_msg";
	private static String KEY_UID        = "uid";
	private static String KEY_NAME       = "name";
	private static String KEY_user      = "user";
	private static String KEY_CREATED_AT = "created_at";


	private Login userLogin;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		userLogin = new Login();

		inputUser    = (EditText) findViewById(R.id.loginUser);
		inputPassword = (EditText) findViewById(R.id.loginPassword);
		loginErrorMsg = (TextView) findViewById(R.id.login_error);
		btnLogin      = (Button)   findViewById(R.id.btnLogin);

		// Login button Click Event
		btnLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				String user    = inputUser.getText().toString();
				String password = inputPassword.getText().toString();

				getSupportLoaderManager().initLoader(LOGIN_LOADER, null,  LoginActivity.this);

				loginErrorMsg.setText("Loading...");

				;
				userLogin.setUsername(user);
				userLogin.setPassword(password);

				userLogin.execute();
				//UserLogin userLogin = new UserLogin();
				//JSONObject json = userLogin.loginUser(user, password);
				Log.d("Button", "Login");


				// check for login response
				/*try {
					if (json.getString(KEY_SUCCESS) != null) {
						loginErrorMsg.setText("");
						String res = json.getString(KEY_SUCCESS);
						if(Integer.parseInt(res) == 1){

							/*
							DatabaseHandler db   = new DatabaseHandler(getApplicationContext());
							JSONObject json_user = json.getJSONObject("user");

							userLogin.logoutUser(getApplicationContext());
							db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_user), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));


							Intent main = new Intent(getApplicationContext(), MainActivity.class);

							main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(main);

							// Close Login Screen
							finish();
						}else{
							loginErrorMsg.setText("Please check your username/password");

						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}*/
			}
		});

	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		CursorLoader cursorLoader = null;
		if (arg0 == LOGIN_LOADER){
			String[] projection = {DatabaseContract.Login.COL_AUTH_KEY};


			cursorLoader = new CursorLoader(this,
            DatabaseContract.Login.CONTENT_URI, projection, null, null, null);

		}
		return cursorLoader;
	}

	@Override
	 public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub

		loginErrorMsg.setText("Login....");
		if((userLogin.getResponseCode() != ResponseCode.OK)){


//			//del here
//			DatabaseHandler db   = new DatabaseHandler(getApplicationContext());
//
//			db.addUser("test", "test", "12345","fgf");
//			// to


			Intent main = new Intent(this, MainActivity.class);

			main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(main);

			finish();

		}else{
			loginErrorMsg.setText("Please check your username/password");
		}

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub

	}
}
package com.robodex.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.robodex.Private;
import com.robodex.Robodex;
import com.robodex.data.DatabaseContract;
import com.robodex.request.HttpHelper.HttpPostTask;
import com.robodex.request.HttpHelper.HttpPostTask.Callback;
import com.robodex.request.ServerContract.RequestField;
import com.robodex.request.ServerContract.RequestType;
import com.robodex.request.ServerContract.ResponseCode;
import com.robodex.request.ServerContract.ResponseField;

public abstract class BaseRequest {
    private static final String LOG_TAG = BaseRequest.class.getSimpleName();

    private static final String URL		= "http://businesshours.net/oatia/android_listener.php";
//    private static final String URL		= "http://businesshours.net/oatia/json/test.php";

    public static final int RESPONSE_CODE_NOT_GIVEN = -1;
    public static final int RESPONSE_CODE_UNKNOWN = -2;

    private final Callback mCallback;

    private Map<String, String> mRequest;
    private String mRequestType;
    private Uri mContentUri;

    private int mResponseCode;

    protected BaseRequest() {
    	mResponseCode = RESPONSE_CODE_UNKNOWN;
    	getChildInfo();

        mCallback = new HttpPostTask.Callback() {
        	@Override
			public HttpPost onCreateRequest() {
				return createRequest(mRequest);
			}

        	@Override
            public void onResponseReceived(HttpResponse response) {
        		processResponse(response);
            }
        };
    }


    public final void execute() {
        new PrepareRequestTask().execute();
    }

    private class PrepareRequestTask extends AsyncTask<Void, Void, Void> {
			@Override
			protected Void doInBackground(Void... params) {
    	        // Child classes populate the request object passed to them.
    	        prepareRequest();
    	        return null;
			}
    }

    /** Called by child classes when the request is done being prepared. */
    protected void executeRequest(Map<String, String> request) {
    	mRequest = request;
    	new HttpPostTask(mCallback).execute();
    }

    // Start creating the request here.

    private HttpPost createRequest(Map<String, String> requestMap) {
        String requestString = encodeRequest(requestMap);
    	return createHttpPost(requestString);
    }

    private String encodeRequest(Map<String, String> request) {
    	JSONObject json = new JSONObject();

    	try {
	    	json.put(RequestField.REQUEST_TYPE, mRequestType);
	        json.put(RequestField.AUTH_KEY, 	getAuthKey());

	        if (request != null) {
		        for (Entry<String, String> pair : request.entrySet()) {
		        	json.put(pair.getKey(), pair.getValue());
		        }
	        }
    	}
    	catch (JSONException e) {
    		Log.e(LOG_TAG, "Error encoding request.", e);
		}

        return json.toString();
    }

    private HttpPost createHttpPost(String request) {
        String hash = Private.calculateHash(request);

        List<NameValuePair> postArgs = new ArrayList<NameValuePair>();
        postArgs.add(new BasicNameValuePair(RequestField.REQUEST, request));
        postArgs.add(new BasicNameValuePair(RequestField.HASH, 	hash));

        HttpPost post = null;
        try { post = HttpHelper.getPost(URL, postArgs); }
        catch (UnsupportedEncodingException e) {
        	Log.e(LOG_TAG, "Error creating HTTP POST.", e);
        }

        return post;
    }

    // Start processing the response here.

    private void processResponse(HttpResponse response) {
    	List<String> responseLines = parseResponseLines(response);
    	JSONObject responseJson = decodeResponse(responseLines);
    	mResponseCode = handleResponseCodes(responseJson);
    	JSONArray results = getResponseResults(responseJson);
    	int numRows = processResponseResults(results);
    	if (Robodex.DEBUG) {
    		Log.i(LOG_TAG, "Recieved " + numRows + " rows from " + getContenUri().getLastPathSegment());
    	}
    }

    private List<String> parseResponseLines(HttpResponse response) {
    	List<String> responseLines = new ArrayList<String>();
    	if (response == null) return responseLines;

        HttpEntity entity = response.getEntity();
        if (entity == null) return responseLines;

        InputStream stream = null;
        BufferedReader reader = null;
        String line = null;

        // Not a repeatable entity, only one call to getContent() allowed
        try {
            stream = entity.getContent();
            reader = new BufferedReader(new InputStreamReader(stream,"iso-8859-1"), 8);
            while ((line = reader.readLine()) != null) {
            	responseLines.add(line);
            }
            reader.close();
            stream.close();
        }
        catch (IllegalStateException e) {
        	Log.e(LOG_TAG, "Error processing response lines.", e);
        }
        catch (IOException e) {
        	Log.e(LOG_TAG, "Error processing response lines.", e);
        }

        return responseLines;
    }


    private JSONObject decodeResponse(List<String> responseLines) {
    	String 		responseString	= null;
        String 		hash        	= null;
        int			size        	= responseLines.size();
        JSONObject	responseJson	= new JSONObject();

        if (size >= 2) {
            responseString 	= responseLines.get(0);
            hash            = responseLines.get(1);
        }
        else {
            Log.e(LOG_TAG, "Response and hash are not defined, received " + size + " line response.");
            return responseJson;
        }


        if (!hash.equals(Private.calculateHash(responseString))) {
            Log.e(LOG_TAG, "Failed hash check.");
            return responseJson;
        }


        try {
            responseString 	= URLDecoder.decode(responseString, "UTF-8");
            responseJson 	= new JSONObject(responseString);
        }
        catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, "Failed URL-decoding response.", e);
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Failed converting response to JSONObject.", e);
        }

        return responseJson;
    }


    protected int handleResponseCodes(JSONObject response) {
    	if (response == null) return RESPONSE_CODE_NOT_GIVEN;

    	int     code = ResponseCode.OK;
        String  msg  = null;

        try {
            code = response.getInt(		ResponseField.RESPONSE_CODE);
            msg  = response.getString(	ResponseField.RESPONSE_MESSAGE);
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Failed parsing response code or message.", e);
        }

        if (code != ResponseCode.OK) {
            Log.e(LOG_TAG, "Error " + code + ": " + msg);
        }
        else {
        	int rows = Robodex.sAppContext.getContentResolver().delete(getContenUri(), null, null);
			if (Robodex.DEBUG) {
				Log.i(LOG_TAG, "Truncated " + rows + " rows from " + getContenUri().getLastPathSegment());
			}
        }

        return code;
    }

    private JSONArray getResponseResults(JSONObject response) {
    	if (response == null) return null;

    	JSONArray results = null;
    	try {
    		results = response.getJSONArray(ResponseField.RESULTS);
    	}
    	catch (JSONException e) {
            Log.e(LOG_TAG, "Error getting results from response.");
        }

    	return results;
    }

    protected int processResponseResults(JSONArray results) {
    	if (results == null) return 0;

    	int numRows = 0;
    	JSONObject resultRow = null;
    	Map<String, String> rowToSave = null;

    	for (int i = 0; i < results.length(); ++i) {
    		try {
    			resultRow = results.getJSONObject(i);
    			rowToSave = convertResultRow(resultRow);
    			if (processConvertedResultRow(rowToSave)) ++numRows;
    		}
    		catch(JSONException e) {
    			Log.e(LOG_TAG, "Error processing result row " + i);
    		}
    	}

    	return numRows;
    }

    private Map<String, String> convertResultRow(JSONObject resultRow) {
    	Map<String, String> rowToSave = new HashMap<String, String>();

    	Iterator keys = resultRow.keys();
    	String key;
        while (keys.hasNext()) {
        	key = (String) keys.next();
        	try {
				rowToSave.put(key, resultRow.getString(key));
			}
        	catch (JSONException e) {
        		Log.e(LOG_TAG, "Cannot parse value of result field '" + key + "'", e);
			}
        }

        return rowToSave;
    }

	private boolean processConvertedResultRow(Map<String, String> rowToSave) {
		ContentValues dbRow = processRowForInsertion(rowToSave);

        try {
        	Robodex.sAppContext.getContentResolver().insert(
        		mContentUri, dbRow);
        }
        catch (Throwable t) {
        	Log.e(LOG_TAG, "Error saving row to database.", t);
        	return false;
        }
        return true;
    }




    private void getChildInfo() {
    	if (getClass() == CheckIn.class) {
    		mRequestType = RequestType.CHECK_IN;
    		mContentUri = DatabaseContract.CheckIn.CONTENT_URI;
    	}
    	else if (getClass() == CreateFlag.class) {
    		mRequestType = RequestType.CREATE_FLAG;
    		mContentUri = DatabaseContract.CreateFlag.CONTENT_URI;
    	}
    	else if (getClass() == CreateLink.class) {
    		mRequestType = RequestType.CREATE_LINK;
    		mContentUri = DatabaseContract.CreateLink.CONTENT_URI;
    	}
    	else if (getClass() == CreateLocation.class) {
    		mRequestType = RequestType.CREATE_LOCATION;
    		mContentUri = DatabaseContract.CreateLocation.CONTENT_URI;
    	}
    	else if (getClass() == CreateOrganization.class) {
    		mRequestType = RequestType.CREATE_ORGANIZATION;
    		mContentUri = DatabaseContract.CreateOrganization.CONTENT_URI;
    	}
    	else if (getClass() == CreatePerson.class) {
    		mRequestType = RequestType.CREATE_PERSON;
    		mContentUri = DatabaseContract.CreatePerson.CONTENT_URI;
    	}
    	else if (getClass() == CreateSpecialty.class) {
    		mRequestType = RequestType.CREATE_SPECIALTY;
    		mContentUri = DatabaseContract.CreateSpecialty.CONTENT_URI;
    	}
    	else if (getClass() == DetailLocation.class) {
    		mRequestType = RequestType.DETAIL_LOCATION;
    		mContentUri = DatabaseContract.DetailLocation.CONTENT_URI;
    	}
    	else if (getClass() == DetailPerson.class) {
    		mRequestType = RequestType.DETAIL_PERSON;
    		mContentUri = DatabaseContract.DetailPerson.CONTENT_URI;
    	}
    	else if (getClass() == EditFlag.class) {
    		mRequestType = RequestType.EDIT_FLAG;
    		mContentUri = DatabaseContract.EditFlag.CONTENT_URI;
    	}
    	else if (getClass() == EditLink.class) {
    		mRequestType = RequestType.EDIT_LINK;
    		mContentUri = DatabaseContract.EditLink.CONTENT_URI;
    	}
    	else if (getClass() == EditLocation.class) {
    		mRequestType = RequestType.EDIT_LOCATION;
    		mContentUri = DatabaseContract.EditLocation.CONTENT_URI;
    	}
    	else if (getClass() == EditOrganization.class) {
    		mRequestType = RequestType.EDIT_ORGANIZATION;
    		mContentUri = DatabaseContract.EditOrganization.CONTENT_URI;
    	}
    	else if (getClass() == EditPerson.class) {
    		mRequestType = RequestType.EDIT_PERSON;
    		mContentUri = DatabaseContract.EditPerson.CONTENT_URI;
    	}
    	else if (getClass() == EditRole.class) {
    		mRequestType = RequestType.EDIT_ROLE;
    		mContentUri = DatabaseContract.EditRole.CONTENT_URI;
    	}
    	else if (getClass() == EditSpecialty.class) {
    		mRequestType = RequestType.EDIT_SPECIALTY;
    		mContentUri = DatabaseContract.EditSpecialty.CONTENT_URI;
    	}
    	else if (getClass() == ListApprovedFlags.class) {
    		mRequestType = RequestType.LIST_APPROVED_FLAGS;
    		mContentUri = DatabaseContract.ListApprovedFlags.CONTENT_URI;
    	}
    	else if (getClass() == ListLastEditedByMember.class) {
    		mRequestType = RequestType.LIST_LAST_EDITED_BY_MEMBER;
    		mContentUri = DatabaseContract.ListLastEditedByMember.CONTENT_URI;
    	}
    	else if (getClass() == ListLinks.class) {
    		mRequestType = RequestType.LIST_LINKS;
    		mContentUri = DatabaseContract.ListLinks.CONTENT_URI;
    	}
    	else if (getClass() == ListLocationsByOrganization.class) {
    		mRequestType = RequestType.LIST_LOCATIONS_BY_ORGANIZATION;
    		mContentUri = DatabaseContract.ListLocationsByOrganization.CONTENT_URI;
    	}
    	else if (getClass() == ListMap.class) {
    		mRequestType = RequestType.LIST_MAP;
    		mContentUri = DatabaseContract.ListMap.CONTENT_URI;
    	}
    	else if (getClass() == ListOrganizations.class) {
    		mRequestType = RequestType.LIST_ORGANIZATIONS;
    		mContentUri = DatabaseContract.ListOrganizations.CONTENT_URI;
    	}
    	else if (getClass() == ListPendingFlags.class) {
    		mRequestType = RequestType.LIST_PENDING_FLAGS;
    		mContentUri = DatabaseContract.ListPendingFlags.CONTENT_URI;
    	}
    	else if (getClass() == ListPeopleBySpecialty.class) {
    		mRequestType = RequestType.LIST_PEOPLE_BY_SPECIALTY;
    		mContentUri = DatabaseContract.ListPeopleBySpecialty.CONTENT_URI;
    	}
    	else if (getClass() == ListRoles.class) {
    		mRequestType = RequestType.LIST_ROLES;
    		mContentUri = DatabaseContract.ListRoles.CONTENT_URI;
    	}
    	else if (getClass() == ListSpecialties.class) {
    		mRequestType = RequestType.LIST_SPECIALTIES;
    		mContentUri = DatabaseContract.ListSpecialties.CONTENT_URI;
    	}
    	else if (getClass() == Login.class) {
    		mRequestType = RequestType.LOGIN;
    		mContentUri = DatabaseContract.Login.CONTENT_URI;
    	}
    	else if (getClass() == SearchAll.class) {
    		mRequestType = RequestType.SEARCH_ALL;
    		mContentUri = DatabaseContract.SearchAll.CONTENT_URI;
    	}
    	else if (getClass() == SearchApprovedFlags.class) {
    		mRequestType = RequestType.SEARCH_APPROVED_FLAGS;
    		mContentUri = DatabaseContract.SearchApprovedFlags.CONTENT_URI;
    	}
    	else if (getClass() == SearchLinks.class) {
    		mRequestType = RequestType.SEARCH_LINKS;
    		mContentUri = DatabaseContract.SearchLinks.CONTENT_URI;
    	}
    	else if (getClass() == SearchLocationsByOrganization.class) {
    		mRequestType = RequestType.SEARCH_LOCATIONS_BY_ORGANIZATION;
    		mContentUri = DatabaseContract.SearchLocationsByOrganization.CONTENT_URI;
    	}
    	else if (getClass() == SearchMap.class) {
    		mRequestType = RequestType.SEARCH_MAP;
    		mContentUri = DatabaseContract.SearchMap.CONTENT_URI;
    	}
    	else if (getClass() == SearchOrganizations.class) {
    		mRequestType = RequestType.SEARCH_ORGANIZATIONS;
    		mContentUri = DatabaseContract.SearchOrganizations.CONTENT_URI;
    	}
    	else if (getClass() == SearchPendingFlags.class) {
    		mRequestType = RequestType.SEARCH_PENDING_FLAGS;
    		mContentUri = DatabaseContract.SearchPendingFlags.CONTENT_URI;
    	}
    	else if (getClass() == SearchPeopleBySpecialty.class) {
    		mRequestType = RequestType.SEARCH_PEOPLE_BY_SPECIALTY;
    		mContentUri = DatabaseContract.SearchPeopleBySpecialty.CONTENT_URI;
    	}
    	else if (getClass() == SearchSpecialties.class) {
    		mRequestType = RequestType.SEARCH_SPECIALTIES;
    		mContentUri = DatabaseContract.SearchSpecialties.CONTENT_URI;
    	}
    	else {
    		Log.wtf(LOG_TAG, "Unkown type of child class: " + getClass().getSimpleName());
    	}
    }


    private String getAuthKey() {
    	// TODO
    	return "";
    }

    public synchronized int getResponseCode() {
    	return mResponseCode;
    }

    protected String getRequestType() {
    	return mRequestType;
    }

    protected Uri getContenUri() {
    	return mContentUri;
    }

    /** Fields to send to server as defined by the ServerContract.RequestField class.*/
    protected abstract void				prepareRequest();

    /** Parse a row from the results so that it can be stored in the database */
    protected abstract ContentValues	processRowForInsertion(Map<String, String> rowFromResponse);

}

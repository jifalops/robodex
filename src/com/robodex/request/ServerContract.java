package com.robodex.request;

/**
 * Specifies the data that the server and app understand
 * so they can communicate it between one another.
 */
public final class ServerContract {

	/**
	 * The types of requests the app can make
	 */
	public static final class RequestType {

		public static final String LOGIN       				= "login";
	    public static final String CHECK_IN       				= "check_in";

	    public static final String DETAIL_PERSON   			= "detail_person";
	    public static final String DETAIL_LOCATION    			= "detail_location";

	    public static final String LIST_SPECIALTIES 			= "list_specialties";
	    public static final String LIST_MAP    				= "list_coordinates";
	    public static final String LIST_ORGANIZATIONS   		= "list_organizations";
	    public static final String LIST_LINKS    				= "list_links";
	    public static final String LIST_PENDING_FLAGS   	 	= "list_pending_flags";
	    public static final String LIST_ROLES    				= "list_roles";
	    public static final String LIST_PEOPLE_BY_SPECIALTY 	= "list_people_by_specialty";
	    public static final String LIST_LOCATIONS_BY_ORGANIZATION     			= "list_locations";
	    public static final String LIST_APPROVED_FLAGS  		= "list_approved_flags";
	    // TODO update documentation
	    public static final String LIST_LAST_EDITED_BY_MEMBER	= "list_last_edited_by_member";

	    public static final String SEARCH_ALL      			= "search_all";
	    public static final String SEARCH_SPECIALTIES 			= "search_specialties";
	    public static final String SEARCH_MAP      			= "search_map";
	    public static final String SEARCH_ORGANIZATIONS    	= "search_organizations";
	    public static final String SEARCH_LINKS               	= "search_links";
	    public static final String SEARCH_PEOPLE_BY_SPECIALTY	= "search_people_by_specialty";
	    public static final String SEARCH_LOCATIONS_BY_ORGANIZATION	= "search_locations";
	    public static final String SEARCH_PENDING_FLAGS    	= "search_pending_flags";
	    public static final String SEARCH_APPROVED_FLAGS 		= "search_approved_flags";

	    public static final String CREATE_PERSON    			= "create_person";
	    public static final String CREATE_SPECIALTY  			= "create_specialty";
	    public static final String CREATE_ORGANIZATION 		= "create_organization";
	    public static final String CREATE_LINK   				= "create_link";
	    public static final String CREATE_LOCATION   			= "create_location";
	    public static final String CREATE_FLAG   				= "create_flag";

	    public static final String EDIT_SPECIALTY  			= "edit_specialty";
	    public static final String EDIT_ORGANIZATION 			= "edit_organization";
	    public static final String EDIT_LINK   				= "edit_link";
	    public static final String EDIT_PERSON   				= "edit_person";
	    public static final String EDIT_LOCATION    			= "edit_location";
	    public static final String EDIT_FLAG     				= "edit_flag";
	    public static final String EDIT_ROLE      	 			= "edit_role";
	}


	/**
	 * Fields that can be in an HTTP request understood by the server.
	 */
	public static final class RequestField {

		/*
		 * HTTP POST fields
		 */

		// Except for the hash, everything else is embedded inside the request field.
	    public static final String REQUEST 					= "request";
	    public static final String HASH   			 			= "hash";


	    /*
	     * Fields shared between request types
	     */

	    // All requests share these
	    // The auth key may be empty for login requests
	    public static final String REQUEST_TYPE 				= "request_type";
	    public static final String AUTH_KEY     				= "auth_key";

	    // Endless list types of requests use this to get the next x results.
	    public static final String	START_POSITION 				= "start_position";

	    // Address/location fields
	    public static final String ADDRESS  					= "address";
	    public static final String CITY  						= "city";
	    public static final String STATE  						= "state";
	    public static final String ZIP  						= "zip";
	    public static final String LATITUDE  					= "latitude";
	    public static final String LONGITUDE  					= "longitude";

	    // Common contact fields.
	    public static final String EMAIL1  					= "email1";
	    public static final String EMAIL2  					= "email2";
	    public static final String PHONE1  					= "phone1";
	    public static final String PHONE2  					= "phone2";

	    // Other common fields
	    public static final String REMOVE  					= "remove";
	    public static final String REMOVE_APPROVED 			= "remove_approved";
	    public static final String MODIFIED_BY					= "modified_by";
	    public static final String MODIFIED_TIME				= "modified_time";


	    /*
         * Request-type-specific fields
         */

	    // Specialty fields
	    public static final String SPECIALTY_ID 				= "specialty_id";
	    public static final String SPECIALTY 					= "specialty";

	    // Organization
	    public static final String ORGANIZATION_ID 			= "organization_id";
        public static final String ORGANIZATION 				= "organization";

        // Organization Location
        public static final String LOCATION_ID					= "location_id";
        public static final String PRIMARY						= "primary_location";

        // User Flags
        public static final String FLAG_ID						= "flag_id";
        public static final String FLAG_BY						= "flag_by";
        public static final String FLAG_TIME					= "flag_time";
        public static final String FLAG_COMMENT				= "flag_comment";
        public static final String VERDICT						= "verdict";
        public static final String VERDICT_BY					= "verdict_by";
        public static final String VERDICT_TIME				= "verdict_time";
        public static final String VERDICT_COMMENT				= "verdict_comment";

        // Hyperlinks
        public static final String LINK_ID						= "link_id";
        public static final String LINK						= "link";
        public static final String LINK_TEXT					= "title";
        public static final String TOP_LEVEL					= "top_level";

        // Member
        public static final String MEMBER_ID					= "member_id";
        public static final String EMAIL						= "email";
        public static final String USERNAME					= "username";
        public static final String PASSWORD					= "password";
        public static final String LOCATION_TIME				= "location_time";
        public static final String LOCATION_ACCURACY			= "location_accuracy"; // TODO Document
        public static final String EDITS						= "edits";
        public static final String EDITS_TODAY					= "edits_today";
        public static final String LOGINS						= "logins";
        public static final String LOGIN_TIME					= "login_time";
        public static final String EXPIRE_DATE					= "expire_date";
        public static final String DISCLAIMER_ACCEPT			= "disclaimer_accept";

        // Person
        public static final String PERSON_ID					= "person_id";
        public static final String FIRST_NAME					= "first_name";
        public static final String LAST_NAME					= "last_name";
        public static final String SPECIALTIES					= "specialties";
        public static final String ORGANIZATIONS				= "organizations";
        public static final String NOTES						= "notes";

        // Role
        public static final String ROLE_ID						= "role_id";
        public static final String ROLE						= "role";
        public static final String DAILY_EDITS					= "daily_edits";
        public static final String EDIT_TOP_LINKS				= "edit_top_links";
        public static final String FLAG_FOR_REMOVAL			= "flag_for_removal";
        public static final String APPROVE_REMOVAL				= "approve_removal";
        public static final String UNREMOVE					= "unremove";
        public static final String CHANGE_MEMBER_ROLES			= "change_member_roles";
        public static final String MANAGE_ROLES				= "manage_roles";

        // Search
        public static final String SEARCH_TERMS				= "search_terms";
	}


	/**
	 * Fields that can be in a response understood by the app.
	 */
	public static final class ResponseField {
		 // All responses must have these
	    public static final String RESPONSE_CODE  				= "response_code";
	    public static final String RESPONSE_MESSAGE   			= "response_message";
	    // Send this array even if results are empty
	    public static final String RESULTS   					= "results";
	}


	/**
	 * Response codes given by the server
	 */
	public static final class ResponseCode {
		public static final int OK								= 0;

		// Business logic errors
		public static final int INVALID_USERNAME				= 10;
		public static final int INVALID_EMAIL					= 11;
		public static final int INVALID_PASSWORD				= 12;
		public static final int INVALID_REQUEST_TYPE			= 13;
		public static final int INVALID_COORD_BOX				= 14;
		public static final int PERMISSION_DENIED				= 15;
		public static final int INVALID_AUTH_KEY				= 16;
		public static final int EXPIRED_AUTH_KEY				= 17;
		public static final int INVALID_SEARCH_TYPE			= 18;
		public static final int EDIT_LIMIT_REACHED				= 19;
		public static final int ACCOUNT_SUSPENDED				= 20;

		// General database errors
		public static final int INVALID_ID						= 100;

		// Database read errors
		public static final int NO_RESULTS						= 110;
		public static final int START_PAST_END					= 111;
		public static final int PERSON_WITHOUT_MEMBER			= 112;

		// Database write errors
		public static final int UNKNOWN_FIELD					= 120;
		public static final int INSUFFICIENT_DATA				= 121;
		public static final int INVALID_DATA_TYPE				= 122;
		public static final int INVALID_DATA_COMBINATION		= 123;

		// Other errors
		public static final int UNKNOWN_ERROR					= 900;
		public static final int OUT_OF_MEMORY					= 901;
	}
}

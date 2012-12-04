package com.robodex.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class DatabaseContract {
    private DatabaseContract() { /* Not instantiable */ }

    public static final String	AUTHORITY               = "com.robodex";
    public static final String  DATABASE_NAME           = "robodex.db";
    public static final int     DATABASE_VERSION        = 1;


    /** List of organizations */
    public static final class ListOrganizations {
        private ListOrganizations() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "organization";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_ORGANIZATION_ID = "organization_id";
        public static final String 	COL_ORGANIZATION 	= "organization";
        public static final String 	COL_REMOVE 			= "remove";
        public static final String 	COL_REMOVE_APPROVED = "remove_approved";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_ORGANIZATION_ID 		+ " integer UNIQUE NOT NULL, "
                + COL_ORGANIZATION 			+ " text UNIQUE NOT NULL, "
                + COL_REMOVE 				+ " integer, "
                + COL_REMOVE_APPROVED 		+ " integer"
                + ");";
    }

    /** Details of a location */
    public static final class DetailLocation {
        private DetailLocation() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "organization_location";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_LOCATION_ID		= "location_id";
        public static final String 	COL_ORGANIZATION_ID	= "organization_id";
        public static final String 	COL_ORGANIZATION	= "organization";
        public static final String 	COL_PRIMARY			= "primary_location";
        public static final String 	COL_ADDRESS			= "address";
        public static final String 	COL_CITY			= "city";
        public static final String 	COL_STATE			= "state";
        public static final String 	COL_ZIP				= "zip";
        public static final String 	COL_LATITUDE		= "latitude";
        public static final String 	COL_LONGITUDE		= "longitude";
        public static final String 	COL_EMAIL1			= "email1";
        public static final String 	COL_EMAIL2			= "email2";
        public static final String 	COL_PHONE1			= "phone1";
        public static final String 	COL_PHONE2			= "phone2";
        public static final String 	COL_MODIFIED_BY		= "modified_by";
        public static final String 	COL_MODIFIED_TIME	= "modified_time";
        public static final String 	COL_REMOVE 			= "remove";
        public static final String 	COL_REMOVE_APPROVED = "remove_approved";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_LOCATION_ID 			+ " integer UNIQUE NOT NULL, "
                + COL_ORGANIZATION_ID		+ " integer NOT NULL, "
                + COL_ORGANIZATION 			+ " text NOT NULL, "
                + COL_PRIMARY 				+ " integer, "
                + COL_ADDRESS 				+ " text, "
                + COL_CITY 					+ " text, "
                + COL_STATE 				+ " text, "
                + COL_ZIP 					+ " text, "
                + COL_LATITUDE 				+ " real, "
                + COL_LONGITUDE				+ " real, "
                + COL_EMAIL1 				+ " text, "
                + COL_EMAIL2				+ " text, "
                + COL_PHONE1 				+ " text, "
                + COL_PHONE2 				+ " text, "
                + COL_MODIFIED_BY 			+ " integer, "
                + COL_MODIFIED_TIME			+ " text, "
                + COL_REMOVE 				+ " integer, "
                + COL_REMOVE_APPROVED 		+ " integer"
                + ");";
    }

//    public static final class Flag {
//        private Flag() { /* Not instantiable */ }
//
//        public static final String 	TABLE_NAME 			= "flag";
//        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
//
//        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
//        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;
//
//        public static final String 	COL_ID 				= BaseColumns._ID;
//        public static final String 	COL_FLAG_ID			= "flag_id";
//        public static final String 	COL_FLAG_BY			= "flag_by";
//        public static final String 	COL_FLAG_TIME		= "flag_time";
//        public static final String 	COL_FLAG_COMMENT	= "flag_comment";
//        public static final String 	COL_VERDICT			= "verdict";
//        public static final String 	COL_VERDICT_BY		= "verdict_by";
//        public static final String 	COL_VERDICT_TIME	= "verdict_time";
//        public static final String 	COL_VERDICT_COMMENT	= "verdict_comment";
//        public static final String 	COL_ORGANIZATION_ID	= "organization_id";
//        public static final String 	COL_LOCATION_ID		= "location_id";
//        public static final String 	COL_LINK_ID			= "link_id";
//        public static final String 	COL_MEMBER_ID		= "member_id";
//        public static final String 	COL_PERSON_ID		= "person_id";
//        public static final String 	COL_SPECIALTY_ID	= "specialty_id";
//
//        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
//                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
//                + COL_FLAG_ID 				+ " integer UNIQUE NOT NULL, "
//                + COL_FLAG_BY 				+ " integer NOT NULL, "
//                + COL_FLAG_TIME 			+ " text, "
//                + COL_FLAG_COMMENT 			+ " text, "
//                + COL_VERDICT 				+ " integer, "
//                + COL_VERDICT_BY 			+ " integer, "
//                + COL_VERDICT_TIME 			+ " text, "
//                + COL_VERDICT_COMMENT 		+ " text, "
//                + COL_ORGANIZATION_ID		+ " integer, "
//                + COL_LOCATION_ID 			+ " integer, "
//                + COL_LINK_ID				+ " integer, "
//                + COL_MEMBER_ID 			+ " integer, "
//                + COL_PERSON_ID 			+ " integer, "
//                + COL_SPECIALTY_ID 			+ " integer"
//                + ");";
//    }
//
    public static final class ListLinks {
        private ListLinks() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "links";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_LINK_ID			= "link_id";
        public static final String 	COL_ROLE_ID			= "role_id";
        public static final String 	COL_LINK			= "link";
        public static final String 	COL_TITLE			= "title";
        public static final String 	COL_TOP_LEVEL		= "top_level";
        public static final String 	COL_ORGANIZATION_ID	= "organization_id";
        public static final String 	COL_LOCATION_ID		= "location_id";
        public static final String 	COL_MEMBER_ID		= "member_id";
        public static final String 	COL_PERSON_ID		= "person_id";
        public static final String 	COL_SPECIALTY_ID	= "specialty_id";
        public static final String 	COL_REMOVE 			= "remove";
        public static final String 	COL_REMOVE_APPROVED = "remove_approved";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_LINK_ID 				+ " integer UNIQUE NOT NULL, "
                + COL_ROLE_ID 				+ " integer, "
                + COL_LINK 					+ " text, "
                + COL_TITLE 				+ " text, "
                + COL_TOP_LEVEL 			+ " integer, "
                + COL_ORGANIZATION_ID 		+ " integer, "
                + COL_LOCATION_ID 			+ " integer, "
                + COL_MEMBER_ID 			+ " integer, "
                + COL_PERSON_ID				+ " integer, "
                + COL_SPECIALTY_ID 			+ " integer, "
                + COL_REMOVE 				+ " integer, "
                + COL_REMOVE_APPROVED 		+ " integer"
                + ");";
    }
//
//    public static final class Member {
//        private Member() { /* Not instantiable */ }
//
//        public static final String 	TABLE_NAME 			= "member";
//        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
//
//        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
//        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;
//
//        public static final String 	COL_ID 				= BaseColumns._ID;
//        public static final String 	COL_MEMBER_ID		= "member_id";
//        public static final String 	COL_ROLE_ID			= "role_id";
//        public static final String 	COL_PERSON_ID		= "person_id";
//        public static final String 	COL_USERNAME		= "username";
//        public static final String 	COL_PASSWORD		= "password";
//        public static final String 	COL_EMAIL			= "email";
//        public static final String 	COL_LATITUDE		= "latitude";
//        public static final String 	COL_LONGITUDE		= "longitude";
//        public static final String 	COL_LOCATION_TIME	= "location_time";
//        public static final String 	COL_EDITS			= "edits";
//        public static final String 	COL_EDITS_TODAY		= "edits_today";
//        public static final String 	COL_LOGINS			= "logins";
//        public static final String 	COL_LOGIN_TIME		= "login_time";
//        public static final String 	COL_EXPIRE_DATE		= "expire_date";
//        public static final String 	COL_DISCLAIMER_ACCEPT	= "disclaimer_accept";
//        public static final String 	COL_REMOVE 			= "remove";
//        public static final String 	COL_REMOVE_APPROVED = "remove_approved";
//
//        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
//                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
//                + COL_MEMBER_ID 			+ " integer UNIQUE NOT NULL, "
//                + COL_ROLE_ID 				+ " integer NOT NULL, "
//                + COL_PERSON_ID 			+ " integer NOT NULL, "
//                + COL_USERNAME 				+ " text UNIQUE NOT NULL, "
//                + COL_PASSWORD 				+ " text, "
//                + COL_EMAIL 				+ " text, "
//                + COL_LATITUDE 				+ " real, "
//                + COL_LONGITUDE				+ " real, "
//                + COL_LOCATION_TIME 		+ " text, "
//                + COL_EDITS					+ " integer, "
//                + COL_EDITS_TODAY 			+ " integer, "
//                + COL_LOGINS 				+ " integer, "
//                + COL_LOGIN_TIME 			+ " text, "
//                + COL_EXPIRE_DATE			+ " text, "
//                + COL_DISCLAIMER_ACCEPT		+ " integer, "
//                + COL_REMOVE 				+ " integer, "
//                + COL_REMOVE_APPROVED 		+ " integer"
//                + ");";
//    }
//
//    public static final class Person {
//        private Person() { /* Not instantiable */ }
//
//        public static final String 	TABLE_NAME 			= "person";
//        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
//
//        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
//        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;
//
//        public static final String 	COL_ID 				= BaseColumns._ID;
//        public static final String 	COL_PERSON_ID		= "person_id";
//        public static final String 	COL_FIRST_NAME		= "first_name";
//        public static final String 	COL_LAST_NAME		= "last_name";
//        public static final String 	COL_ADDRESS			= "address";
//        public static final String 	COL_CITY			= "city";
//        public static final String 	COL_STATE			= "state";
//        public static final String 	COL_ZIP				= "zip";
//        public static final String 	COL_LATITUDE		= "latitude";
//        public static final String 	COL_LONGITUDE		= "longitude";
//        public static final String 	COL_EMAIL1			= "email1";
//        public static final String 	COL_PHONE1			= "phone1";
//        public static final String 	COL_PHONE2			= "phone2";
//        public static final String 	COL_NOTES			= "notes";
//        public static final String 	COL_MODIFIED_BY		= "modified_by";
//        public static final String 	COL_MODIFIED_TIME	= "modified_time";
//        public static final String 	COL_REMOVE 			= "remove";
//        public static final String 	COL_REMOVE_APPROVED = "remove_approved";
//
//        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
//                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
//                + COL_PERSON_ID 			+ " integer UNIQUE NOT NULL, "
//                + COL_FIRST_NAME 			+ " text, "
//                + COL_LAST_NAME 			+ " text, "
//                + COL_ADDRESS 				+ " text, "
//                + COL_CITY 					+ " text, "
//                + COL_STATE 				+ " text, "
//                + COL_ZIP 					+ " text, "
//                + COL_LATITUDE 				+ " real, "
//                + COL_LONGITUDE				+ " real, "
//                + COL_EMAIL1 				+ " text, "
//                + COL_PHONE1 				+ " text, "
//                + COL_PHONE2 				+ " text, "
//                + COL_NOTES 				+ " text, "
//                + COL_MODIFIED_BY 			+ " integer, "
//                + COL_MODIFIED_TIME			+ " text, "
//                + COL_REMOVE 				+ " integer, "
//                + COL_REMOVE_APPROVED 		+ " integer"
//                + ");";
//    }
//
//    public static final class PersonOrganization {
//        private PersonOrganization() { /* Not instantiable */ }
//
//        public static final String 	TABLE_NAME 			= "person_organization";
//        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
//
//        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
//        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;
//
//        public static final String 	COL_ID 				= BaseColumns._ID;
//        public static final String 	COL_PERSON_ID		= "person_id";
//        public static final String 	COL_ORGANIZATION_ID	= "organization_id";
//
//        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
//                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
//                + COL_PERSON_ID 			+ " integer, "
//                + COL_ORGANIZATION_ID 		+ " integer"
//                + ");";
//    }
//
//    public static final class PersonSpecialty {
//        private PersonSpecialty() { /* Not instantiable */ }
//
//        public static final String 	TABLE_NAME 			= "person_specialty";
//        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
//
//        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
//        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;
//
//        public static final String 	COL_ID 				= BaseColumns._ID;
//        public static final String 	COL_PERSON_ID		= "person_id";
//        public static final String 	COL_SPECIALTY_ID	= "specialty_id";
//
//        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
//                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
//                + COL_PERSON_ID 			+ " integer, "
//                + COL_SPECIALTY_ID 			+ " integer"
//                + ");";
//    }

    /** List roles */
    public static final class ListRoles {
        private ListRoles() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "role";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_ROLE_ID			= "role_id";
        public static final String 	COL_ROLE			= "role";
        public static final String 	COL_DAILY_EDITS		= "daily_edits";
        public static final String 	COL_EDIT_TOP_LINKS	= "edit_top_links";
        public static final String 	COL_FLAG_FOR_REMOVAL= "flag_for_removal";
        public static final String 	COL_APPROVE_REMOVAL	= "approve_removal";
        public static final String 	COL_UNREMOVE		= "unremove";
        public static final String 	COL_CHANGE_MEMBER_ROLES	= "change_member_roles";
        public static final String 	COL_MANAGE_ROLES	= "manage_roles";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_ROLE_ID 				+ " integer UNIQUE NOT NULL, "
                + COL_ROLE 					+ " text UNIQUE NOT NULL, "
                + COL_DAILY_EDITS 			+ " integer, "
                + COL_EDIT_TOP_LINKS 		+ " integer, "
                + COL_FLAG_FOR_REMOVAL 		+ " integer, "
                + COL_APPROVE_REMOVAL 		+ " integer, "
                + COL_UNREMOVE 				+ " integer, "
                + COL_CHANGE_MEMBER_ROLES 	+ " integer, "
                + COL_MANAGE_ROLES			+ " integer"
                + ");";
    }

    /** List specialties */
    public static final class ListSpecialties {
        private ListSpecialties() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "specialty";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_SPECIALTY_ID	= "specialty_id";
        public static final String 	COL_SPECIALTY		= "specialty";
        public static final String 	COL_REMOVE 			= "remove";
        public static final String 	COL_REMOVE_APPROVED = "remove_approved";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_SPECIALTY_ID 			+ " integer UNIQUE NOT NULL, "
                + COL_SPECIALTY 			+ " text UNIQUE NOT NULL, "
                + COL_REMOVE 				+ " integer, "
                + COL_REMOVE_APPROVED 		+ " integer"
                + ");";
    }


//    public static final class Edits {
//        private Edits() { /* Not instantiable */ }
//
//        public static final String 	TABLE_NAME 			= "edits";
//        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
//
//        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
//        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;
//
//        public static final String 	COL_ID 				= BaseColumns._ID;
//        public static final String 	COL_EDIT_ID 		= "edit_id";			// Can be the id of the first row in this set of edits
//        public static final String 	COL_EDIT_TIME 		= "edit_time";
//        public static final String 	COL_TABLE_NAME		= "table_name";
//        public static final String 	COL_ROW_ID			= "row_id";
//        public static final String 	COL_FIELD_NAME		= "field_name";
//        public static final String 	COL_OLD_VALUE 		= "old_value";
//        public static final String 	COL_NEW_VALUE 		= "new_value";
//        public static final String 	COL_RESULT 			= "result";
//        public static final String 	COL_RESULT_TIME 	= "result_time";
//
//        public static final int 	RESULT_UNKNOWN		= 0;
//        public static final int 	RESULT_PENDING		= 1;
//        public static final int 	RESULT_ACCEPTED		= 2;
//        public static final int 	RESULT_REJECTED		= 3;
//
//        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
//                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
//                + COL_EDIT_ID 				+ " integer NOT NULL, "
//                + COL_EDIT_TIME 			+ " text NOT NULL, "
//                + COL_TABLE_NAME 			+ " text NOT NULL, "
//                + COL_ROW_ID 				+ " integer NOT NULL, "
//                + COL_FIELD_NAME 			+ " text NOT NULL, "
//                + COL_OLD_VALUE 			+ " text NOT NULL, "
//                + COL_NEW_VALUE 			+ " text, "
//                + COL_RESULT 				+ " integer, "
//                + COL_RESULT_TIME 			+ " text"
//                + ");";
//    }
//
//    /** Results of all create and/or edit requests go here */
//    public static final class EditResult {
//        private EditResult() { /* Not instantiable */ }
//
//        public static final String 	TABLE_NAME 			= "edit_result";
//        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
//
//        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
//        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;
//
//        public static final String 	COL_ID 				= BaseColumns._ID;
//        public static final String 	COL_EDIT_ID 		= "edit_id";
//        public static final String 	COL_RESULT 			= "result";
//        public static final String 	COL_RESULT_TIME 	= "result_time";
//
//        public static final int 	RESULT_UNKNOWN		= 0;
//        public static final int 	RESULT_PENDING		= 1;
//        public static final int 	RESULT_ACCEPTED		= 2;
//        public static final int 	RESULT_REJECTED		= 3;
//
//        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
//                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
//                + COL_EDIT_ID 				+ " integer NOT NULL, "
//                + COL_RESULT 				+ " integer, "
//                + COL_RESULT_TIME 			+ " text"
//                + ");";
//    }

    /** List coordinates uses this */
    public static final class ListMap {
        private ListMap() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "map_cache";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        // Meta fields
        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_TIMESTAMP 		= "timestamp";

        // Common/shared fields
        public static final String 	COL_LATITUDE		= "latitude";
        public static final String 	COL_LONGITUDE		= "longitude";
        public static final String 	COL_ADDRESS			= "address";
        public static final String 	COL_CITY			= "city";
        public static final String 	COL_STATE			= "state";
        public static final String 	COL_ZIP				= "zip";
        public static final String 	COL_EMAIL1			= "email1";
        public static final String 	COL_PHONE1			= "phone1";
        public static final String 	COL_PHONE2			= "phone2";

        // Person/Member fields
        public static final String 	COL_FIRST_NAME		= "first_name";
        public static final String 	COL_LAST_NAME		= "last_name";

        // Person fields
        public static final String 	COL_PERSON_ID		= "person_id";

        // Member fields
        public static final String 	COL_MEMBER_ID		= "member_id";
        public static final String 	COL_LOCATION_TIME	= "location_time";

        // OrganizationLocation fields
        public static final String 	COL_LOCATION_ID		= "location_id";
        public static final String 	COL_ORGANIZATION	= "organization";
        public static final String 	COL_PRIMARY			= "primary_location";
        public static final String 	COL_EMAIL2			= "email2";



        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_TIMESTAMP 			+ " text, "

                + COL_LATITUDE 				+ " real, "
                + COL_LONGITUDE 			+ " real, "
                + COL_ADDRESS 				+ " text, "
                + COL_CITY 					+ " text, "
                + COL_STATE 				+ " text, "
                + COL_ZIP 					+ " text, "
                + COL_EMAIL1 				+ " text, "
                + COL_PHONE1 				+ " text, "
                + COL_PHONE2 				+ " text, "

                + COL_FIRST_NAME 			+ " text, "
                + COL_LAST_NAME 			+ " text, "

                + COL_PERSON_ID 			+ " integer, "

                + COL_MEMBER_ID 			+ " integer, "
                + COL_LOCATION_TIME 		+ " text, "

                + COL_LOCATION_ID 			+ " integer, "
                + COL_ORGANIZATION 			+ " text, "
                + COL_PRIMARY 				+ " integer, "
                + COL_EMAIL2 				+ " text"
                + ");";
    }




    /** Listing people with a specific specialty */
    public static final class ListPeopleBySpecialty {
        private ListPeopleBySpecialty() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "people_by_specialty";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_TIMESTAMP 		= "timestamp";

        public static final String 	COL_PERSON_ID		= "person_id";
        public static final String 	COL_FIRST_NAME		= "first_name";
        public static final String 	COL_LAST_NAME		= "last_name";
        public static final String 	COL_CITY			= "city";
        public static final String 	COL_STATE			= "state";
        public static final String 	COL_ZIP				= "zip";


        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_TIMESTAMP 			+ " text, "
                + COL_PERSON_ID 			+ " integer NOT NULL, "
                + COL_FIRST_NAME 			+ " text, "
                + COL_LAST_NAME 			+ " text, "
                + COL_CITY 					+ " text, "
                + COL_STATE 				+ " text, "
                + COL_ZIP	 				+ " text"
                + ");";
    }

    /** List locations of a specific organization */
    public static final class ListLocationsByOrganization {
        private ListLocationsByOrganization() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "location_by_organization";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_TIMESTAMP 		= "timestamp";
        public static final String 	COL_LOCATION_ID		= "location_id";
        public static final String 	COL_PRIMARY			= "primary_location";
        public static final String 	COL_ADDRESS			= "address";
        public static final String 	COL_CITY			= "city";
        public static final String 	COL_STATE			= "state";
        public static final String 	COL_ZIP				= "zip";


        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_TIMESTAMP 			+ " text, "
                + COL_LOCATION_ID 			+ " integer NOT NULL, "
                + COL_PRIMARY 				+ " integer, "
                + COL_ADDRESS 				+ " text, "
                + COL_CITY 					+ " text, "
                + COL_STATE 				+ " text, "
                + COL_ZIP 					+ " text"
                + ");";
    }



    /** Combined person/member details */
    public static final class DetailPerson {
        private DetailPerson() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "person_detail";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;

        // Person/Member fields
        public static final String 	COL_PERSON_ID		= "person_id";

        // Person fields
        public static final String 	COL_FIRST_NAME		= "first_name";
        public static final String 	COL_LAST_NAME		= "last_name";
        public static final String 	COL_SPECIALTIES		= "specialties";
        public static final String 	COL_ORGANIZATIONS	= "organizations";
        public static final String 	COL_ADDRESS			= "address";
        public static final String 	COL_CITY			= "city";
        public static final String 	COL_STATE			= "state";
        public static final String 	COL_ZIP				= "zip";
        public static final String 	COL_LATITUDE		= "latitude";
        public static final String 	COL_LONGITUDE		= "longitude";
        public static final String 	COL_EMAIL1			= "email1";
        public static final String 	COL_PHONE1			= "phone1";
        public static final String 	COL_PHONE2			= "phone2";
        public static final String 	COL_NOTES			= "notes";
        public static final String 	COL_MODIFIED_BY		= "modified_by";
        public static final String 	COL_MODIFIED_TIME	= "modified_time";
        // Removing a person will hide them from lists
        // If they are a member, it will effectively make them a restricted user (login, but no edits)
        public static final String 	COL_REMOVE 			= "remove";
        public static final String 	COL_REMOVE_APPROVED = "remove_approved";

        // Member fields
        public static final String 	COL_MEMBER_ID		= "member_id";
        public static final String 	COL_ROLE			= "role";
        public static final String 	COL_USERNAME		= "username";
        public static final String 	COL_EMAIL			= "email";
        public static final String 	COL_MEMBER_LATITUDE	= "member_latitude";
        public static final String 	COL_MEMBER_LONGITUDE	= "member_longitude";
        public static final String 	COL_LOCATION_TIME	= "location_time";
        public static final String 	COL_EDITS			= "edits";
        public static final String 	COL_EDITS_TODAY		= "edits_today";
        public static final String 	COL_LOGINS			= "logins";
        public static final String 	COL_LOGIN_TIME		= "login_time";
        public static final String 	COL_EXPIRE_DATE		= "expire_date";
        public static final String 	COL_DISCLAIMER_ACCEPT	= "disclaimer_accept";
        // Removing a member disable's their login
        public static final String 	COL_MEMBER_REMOVE 	= "member_remove";
        public static final String 	COL_MEMBER_REMOVE_APPROVED = "member_remove_approved";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_PERSON_ID 			+ " integer NOT NULL, "

                + COL_FIRST_NAME 			+ " text, "
                + COL_LAST_NAME 			+ " text, "
                + COL_SPECIALTIES 			+ " text, "
                + COL_ORGANIZATIONS			+ " text, "
                + COL_ADDRESS 				+ " text, "
                + COL_CITY 					+ " text, "
                + COL_STATE 				+ " text, "
                + COL_ZIP 					+ " text, "
                + COL_LATITUDE 				+ " real, "
                + COL_LONGITUDE				+ " real, "
                + COL_EMAIL1 				+ " text, "
                + COL_PHONE1 				+ " text, "
                + COL_PHONE2 				+ " text, "
                + COL_NOTES 				+ " text, "
                + COL_MODIFIED_BY 			+ " integer, "
                + COL_MODIFIED_TIME			+ " text, "
                + COL_REMOVE 				+ " integer, "
                + COL_REMOVE_APPROVED 		+ " integer, "

                + COL_MEMBER_ID 			+ " integer, "
                + COL_ROLE 					+ " text, "
                + COL_USERNAME 				+ " text, "
                + COL_EMAIL 				+ " text, "
                + COL_MEMBER_LATITUDE 		+ " real, "
                + COL_MEMBER_LONGITUDE		+ " real, "
                + COL_LOCATION_TIME 		+ " text, "
                + COL_EDITS					+ " integer, "
                + COL_EDITS_TODAY 			+ " integer, "
                + COL_LOGINS 				+ " integer, "
                + COL_LOGIN_TIME 			+ " text, "
                + COL_EXPIRE_DATE			+ " text, "
                + COL_DISCLAIMER_ACCEPT		+ " integer, "
                + COL_MEMBER_REMOVE 		+ " integer, "
                + COL_MEMBER_REMOVE_APPROVED	+ " integer"
                + ");";
    }

    /** List of items where the user was the last to edit/update them */
    public static final class ListLastEditedByMember {
        private ListLastEditedByMember() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "last_edited_by_member";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;

        public static final String 	COL_CITY			= "city";
        public static final String 	COL_STATE			= "state";

        public static final String 	COL_PERSON_ID		= "person_id";
        public static final String 	COL_FIRST_NAME		= "first_name";
        public static final String 	COL_LAST_NAME		= "last_name";

        public static final String 	COL_LOCATION_ID		= "location_id";
        public static final String 	COL_ORGANIZATION	= "organization";



        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "

                + COL_CITY 					+ " text, "
                + COL_STATE 				+ " text, "

                + COL_PERSON_ID 			+ " integer, "
                + COL_FIRST_NAME 			+ " text, "
                + COL_LAST_NAME 			+ " text, "

                + COL_LOCATION_ID 			+ " integer, "
                + COL_ORGANIZATION 			+ " text"
                + ");";
    }

    /** List pending flags */
    public static final class ListPendingFlags {
        private ListPendingFlags() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "pending_flags";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_FLAG_ID			= "flag_id";
        public static final String 	COL_FLAG_BY			= "flag_by";
        public static final String 	COL_FLAG_TIME		= "flag_time";
        public static final String 	COL_FLAG_COMMENT	= "flag_comment";
        public static final String 	COL_ORGANIZATION_ID	= "organization_id";
        public static final String 	COL_LOCATION_ID		= "location_id";
        public static final String 	COL_LINK_ID			= "link_id";
        public static final String 	COL_MEMBER_ID		= "member_id";
        public static final String 	COL_PERSON_ID		= "person_id";
        public static final String 	COL_SPECIALTY_ID	= "specialty_id";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_FLAG_ID 				+ " integer UNIQUE NOT NULL, "
                + COL_FLAG_BY 				+ " integer NOT NULL, "
                + COL_FLAG_TIME 			+ " text, "
                + COL_FLAG_COMMENT 			+ " text, "
                + COL_ORGANIZATION_ID		+ " integer, "
                + COL_LOCATION_ID 			+ " integer, "
                + COL_LINK_ID				+ " integer, "
                + COL_MEMBER_ID 			+ " integer, "
                + COL_PERSON_ID 			+ " integer, "
                + COL_SPECIALTY_ID 			+ " integer"
                + ");";
    }

    /** List flags that have been approved */
    public static final class ListApprovedFlags {
        private ListApprovedFlags() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "approved_flags";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_FLAG_ID			= "flag_id";
        public static final String 	COL_FLAG_BY			= "flag_by";
        public static final String 	COL_FLAG_TIME		= "flag_time";
        public static final String 	COL_FLAG_COMMENT	= "flag_comment";
        public static final String 	COL_VERDICT			= "verdict";
        public static final String 	COL_VERDICT_BY		= "verdict_by";
        public static final String 	COL_VERDICT_TIME	= "verdict_time";
        public static final String 	COL_VERDICT_COMMENT	= "verdict_comment";
        public static final String 	COL_ORGANIZATION_ID	= "organization_id";
        public static final String 	COL_LOCATION_ID		= "location_id";
        public static final String 	COL_LINK_ID			= "link_id";
        public static final String 	COL_MEMBER_ID		= "member_id";
        public static final String 	COL_PERSON_ID		= "person_id";
        public static final String 	COL_SPECIALTY_ID	= "specialty_id";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_FLAG_ID 				+ " integer UNIQUE NOT NULL, "
                + COL_FLAG_BY 				+ " integer NOT NULL, "
                + COL_FLAG_TIME 			+ " text, "
                + COL_FLAG_COMMENT 			+ " text, "
                + COL_VERDICT 				+ " integer, "
                + COL_VERDICT_BY 			+ " integer, "
                + COL_VERDICT_TIME 			+ " text, "
                + COL_VERDICT_COMMENT 		+ " text, "
                + COL_ORGANIZATION_ID		+ " integer, "
                + COL_LOCATION_ID 			+ " integer, "
                + COL_LINK_ID				+ " integer, "
                + COL_MEMBER_ID 			+ " integer, "
                + COL_PERSON_ID 			+ " integer, "
                + COL_SPECIALTY_ID 			+ " integer"
                + ");";
    }


    /** Results of general search */
    public static final class SearchAll {
        private SearchAll() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "search_all";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_SEARCH			= "search";

        public static final String 	COL_SPECIALTY_ID	= "specialty_id";
        public static final String 	COL_SPECIALTY		= "specialty";
        public static final String 	COL_ORGANIZATION_ID	= "organization_id";
        public static final String 	COL_ORGANIZATION	= "organization";
        public static final String 	COL_LOCATION_ID		= "location_id";
        public static final String 	COL_PRIMARY			= "primary_location";
        public static final String 	COL_MEMBER_ID		= "member_id";
        public static final String 	COL_PERSON_ID		= "person_id";

        public static final String 	COL_FIRST_NAME		= "first_name";
        public static final String 	COL_LAST_NAME		= "last_name";
        public static final String 	COL_ADDRESS			= "address";
        public static final String 	COL_CITY			= "city";
        public static final String 	COL_STATE			= "state";
        public static final String 	COL_ZIP				= "zip";

        public static final String 	COL_LINK_ID			= "link_id";
        public static final String 	COL_LINK			= "link";
        public static final String 	COL_LINK_TITLE		= "link_title";

        // Not searching flags from general search


        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_SEARCH				+ " text, "
                + COL_ORGANIZATION_ID		+ " integer, "
                + COL_ORGANIZATION			+ " text, "
                + COL_LOCATION_ID 			+ " integer, "
                + COL_PRIMARY 				+ " integer, "
                + COL_MEMBER_ID 			+ " integer, "
                + COL_PERSON_ID 			+ " integer, "

                + COL_FIRST_NAME 			+ " text, "
                + COL_LAST_NAME 			+ " text, "

                + COL_ADDRESS 				+ " text, "
                + COL_CITY 					+ " text, "
                + COL_STATE 				+ " text, "
                + COL_ZIP	 				+ " text, "

                + COL_SPECIALTY_ID 			+ " integer, "
                + COL_SPECIALTY 			+ " text, "
                + COL_LINK_ID				+ " integer, "
                + COL_LINK 					+ " text, "
                + COL_LINK_TITLE 			+ " text"
                + ");";
    }

    /** Results of specialties search */
    public static final class SearchSpecialties {
        private SearchSpecialties() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "search_specialties";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_SEARCH			= "search";
        public static final String 	COL_SPECIALTY_ID	= "specialty_id";
        public static final String 	COL_SPECIALTY		= "specialty";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_SEARCH				+ " text NOT NULL, "
                + COL_SPECIALTY_ID 			+ " integer, "
                + COL_SPECIALTY 			+ " text"
                + ");";
    }

    /** Results of organization search */
    public static final class SearchOrganizations {
        private SearchOrganizations() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "search_organizations";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_SEARCH			= "search";
        public static final String 	COL_ORGANIZATION_ID	= "organization_id";
        public static final String 	COL_ORGANIZATION	= "organization";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_SEARCH				+ " text NOT NULL, "
                + COL_ORGANIZATION_ID 		+ " integer, "
                + COL_ORGANIZATION 			+ " text"
                + ");";
    }

    /** Results of link search */
    public static final class SearchLinks {
        private SearchLinks() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "search_links";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_SEARCH			= "search";
        public static final String 	COL_LINK_ID			= "link_id";
        public static final String 	COL_LINK			= "link";
        public static final String 	COL_TITLE			= "title";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_SEARCH				+ " text NOT NULL, "
                + COL_LINK_ID 				+ " integer, "
                + COL_LINK 					+ " text, "
                + COL_TITLE					+ " text"
                + ");";
    }

    /** Results of searching for people with a given specialty */
    public static final class SearchPeopleBySpecialty {
        private SearchPeopleBySpecialty() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "search_people_by_specialty";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_SEARCH			= "search";

        public static final String 	COL_PERSON_ID		= "person_id";
        public static final String 	COL_FIRST_NAME		= "first_name";
        public static final String 	COL_LAST_NAME		= "last_name";
        public static final String 	COL_CITY			= "city";
        public static final String 	COL_STATE			= "state";


        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_SEARCH 				+ " text NOT NULL, "

                + COL_PERSON_ID 			+ " integer, "
                + COL_FIRST_NAME 			+ " text, "
                + COL_LAST_NAME 			+ " text, "
                + COL_CITY 					+ " text, "
                + COL_STATE 				+ " text"
                + ");";
    }

    /** Results of searching for a location of a given organization */
    public static final class SearchLocationsByOrganization {
        private SearchLocationsByOrganization() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "search_location_by_organization";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_SEARCH			= "search";
        public static final String 	COL_LOCATION_ID		= "location_id";
        public static final String 	COL_ADDRESS			= "address";
        public static final String 	COL_CITY			= "city";
        public static final String 	COL_STATE			= "state";
        public static final String 	COL_ZIP				= "zip";


        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_SEARCH 				+ " text NOT NULL, "
                + COL_LOCATION_ID 			+ " integer, "
                + COL_ADDRESS 				+ " text, "
                + COL_CITY 					+ " text, "
                + COL_STATE 				+ " text, "
                + COL_ZIP 					+ " text"
                + ");";
    }


    /** Results of pending flags search */
    public static final class SearchPendingFlags {
        private SearchPendingFlags() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "search_pending_flags";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_SEARCH			= "search";
        public static final String 	COL_FLAG_ID			= "flag_id";
        public static final String 	COL_FLAG_BY			= "flag_by";
        public static final String 	COL_FLAG_TIME		= "flag_time";
        public static final String 	COL_FLAG_COMMENT	= "flag_comment";
        public static final String 	COL_ORGANIZATION_ID	= "organization_id";
        public static final String 	COL_LOCATION_ID		= "location_id";
        public static final String 	COL_LINK_ID			= "link_id";
        public static final String 	COL_MEMBER_ID		= "member_id";
        public static final String 	COL_PERSON_ID		= "person_id";
        public static final String 	COL_SPECIALTY_ID	= "specialty_id";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_SEARCH 				+ " text NOT NULL, "
                + COL_FLAG_ID 				+ " integer, "
                + COL_FLAG_BY 				+ " integer, "
                + COL_FLAG_TIME 			+ " text, "
                + COL_FLAG_COMMENT 			+ " text, "
                + COL_ORGANIZATION_ID		+ " integer, "
                + COL_LOCATION_ID 			+ " integer, "
                + COL_LINK_ID				+ " integer, "
                + COL_MEMBER_ID 			+ " integer, "
                + COL_PERSON_ID 			+ " integer, "
                + COL_SPECIALTY_ID 			+ " integer"
                + ");";
    }


    /** Results of approved flags search */
    public static final class SearchApprovedFlags {
        private SearchApprovedFlags() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "search_approved_flags";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_SEARCH			= "search";
        public static final String 	COL_FLAG_ID			= "flag_id";
        public static final String 	COL_FLAG_BY			= "flag_by";
        public static final String 	COL_FLAG_TIME		= "flag_time";
        public static final String 	COL_FLAG_COMMENT	= "flag_comment";
        public static final String 	COL_VERDICT			= "verdict";
        public static final String 	COL_VERDICT_BY		= "verdict_by";
        public static final String 	COL_VERDICT_TIME	= "verdict_time";
        public static final String 	COL_VERDICT_COMMENT	= "verdict_comment";
        public static final String 	COL_ORGANIZATION_ID	= "organization_id";
        public static final String 	COL_LOCATION_ID		= "location_id";
        public static final String 	COL_LINK_ID			= "link_id";
        public static final String 	COL_MEMBER_ID		= "member_id";
        public static final String 	COL_PERSON_ID		= "person_id";
        public static final String 	COL_SPECIALTY_ID	= "specialty_id";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_SEARCH 				+ " text NOT NULL, "
                + COL_FLAG_ID 				+ " integer, "
                + COL_FLAG_BY 				+ " integer, "
                + COL_FLAG_TIME 			+ " text, "
                + COL_FLAG_COMMENT 			+ " text, "
                + COL_VERDICT 				+ " integer, "
                + COL_VERDICT_BY 			+ " integer, "
                + COL_VERDICT_TIME 			+ " text, "
                + COL_VERDICT_COMMENT 		+ " text, "
                + COL_ORGANIZATION_ID		+ " integer, "
                + COL_LOCATION_ID 			+ " integer, "
                + COL_LINK_ID				+ " integer, "
                + COL_MEMBER_ID 			+ " integer, "
                + COL_PERSON_ID 			+ " integer, "
                + COL_SPECIALTY_ID 			+ " integer"
                + ");";
    }

    /** Results of searching the map */
    public static final class SearchMap {
        private SearchMap() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "search_map";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        // Meta fields
        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_SEARCH			= "search";
        public static final String 	COL_TIMESTAMP 		= "timestamp";

        // Common/shared fields
        public static final String 	COL_LATITUDE		= "latitude";
        public static final String 	COL_LONGITUDE		= "longitude";
        public static final String 	COL_ADDRESS			= "address";
        public static final String 	COL_CITY			= "city";
        public static final String 	COL_STATE			= "state";
        public static final String 	COL_ZIP				= "zip";
        public static final String 	COL_EMAIL1			= "email1";
        public static final String 	COL_PHONE1			= "phone1";
        public static final String 	COL_PHONE2			= "phone2";

        // Person/Member fields
        public static final String 	COL_FIRST_NAME		= "first_name";
        public static final String 	COL_LAST_NAME		= "last_name";

        // Person fields
        public static final String 	COL_PERSON_ID		= "person_id";

        // Member fields
        public static final String 	COL_MEMBER_ID		= "member_id";
        public static final String 	COL_LOCATION_TIME	= "location_time";

        // OrganizationLocation fields
        public static final String 	COL_LOCATION_ID		= "location_id";
        public static final String 	COL_ORGANIZATION	= "organization";
        public static final String 	COL_PRIMARY			= "primary_location";
        public static final String 	COL_EMAIL2			= "email2";



        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_SEARCH 				+ " text, "
                + COL_TIMESTAMP 			+ " text, "

                + COL_LATITUDE 				+ " real, "
                + COL_LONGITUDE 			+ " real, "
                + COL_ADDRESS 				+ " text, "
                + COL_CITY 					+ " text, "
                + COL_STATE 				+ " text, "
                + COL_ZIP 					+ " text, "
                + COL_EMAIL1 				+ " text, "
                + COL_PHONE1 				+ " text, "
                + COL_PHONE2 				+ " text, "

                + COL_FIRST_NAME 			+ " text, "
                + COL_LAST_NAME 			+ " text, "

                + COL_PERSON_ID 			+ " integer, "

                + COL_MEMBER_ID 			+ " integer, "
                + COL_LOCATION_TIME 		+ " text, "

                + COL_LOCATION_ID 			+ " integer, "
                + COL_ORGANIZATION 			+ " text, "
                + COL_PRIMARY 				+ " integer, "
                + COL_EMAIL2 				+ " text"
                + ");";
    }

    /** The response of a checkin request */
    public static final class CheckIn {
        private CheckIn() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "check_in";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_TIMESTAMP 		= "timestamp";
        public static final String 	COL_LATITUDE		= "latitude";
        public static final String 	COL_LONGITUDE		= "longitude";
        public static final String 	COL_ACCURACY		= "accuracy";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_TIMESTAMP 			+ " text, "
                + COL_LATITUDE 				+ " real, "
                + COL_LONGITUDE 			+ " real, "
                + COL_ACCURACY 				+ " real"
                + ");";
    }

    /** The response of a login request */
    public static final class Login {
        private Login() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "login";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_TIMESTAMP 		= "timestamp";
        public static final String 	COL_AUTH_KEY 		= "auth_key";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_TIMESTAMP 			+ " text, "
                + COL_AUTH_KEY				+ " text"
                + ");";
    }



    /** The response of a create flag request */
    public static final class CreateFlag {
        private CreateFlag() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "create_flag";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_FLAG_ID			= "flag_id";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_FLAG_ID 				+ " integer UNIQUE"
                + ");";
    }


    /** The response of a create link request */
    public static final class CreateLink {
        private CreateLink() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "create_link";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_LINK_ID			= "link_id";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_LINK_ID 				+ " integer UNIQUE"
                + ");";
    }


    /** The response of a create location request */
    public static final class CreateLocation {
        private CreateLocation() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "create_location";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_LOCATION_ID			= "location_id";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_LOCATION_ID 				+ " integer UNIQUE"
                + ");";
    }


    /** The response of a create organization request */
    public static final class CreateOrganization {
        private CreateOrganization() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "create_organization";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_ORGANIZATION_ID			= "organization_id";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_ORGANIZATION_ID 				+ " integer UNIQUE"
                + ");";
    }


    /** The response of a create person request */
    public static final class CreatePerson {
        private CreatePerson() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "create_person";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_PERSON_ID		= "person_id";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_PERSON_ID 				+ " integer UNIQUE"
                + ");";
    }


    /** The response of a create specialty request */
    public static final class CreateSpecialty {
        private CreateSpecialty() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "create_specialty";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_SPECIALTY_ID		= "specialty_id";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_SPECIALTY_ID 				+ " integer UNIQUE"
                + ");";
    }


    /** The response of an edit flag request */
    public static final class EditFlag {
        private EditFlag() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "edit_flag";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_FLAG_ID		= "flag_id";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_FLAG_ID 				+ " integer UNIQUE"
                + ");";
    }


    /** The response of an edit link request */
    public static final class EditLink {
        private EditLink() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "edit_link";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_LINK_ID		= "link_id";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_LINK_ID 				+ " integer UNIQUE"
                + ");";
    }


    /** The response of an edit location request */
    public static final class EditLocation {
        private EditLocation() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "edit_location";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_LOCATION_ID		= "location_id";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_LOCATION_ID 				+ " integer UNIQUE"
                + ");";
    }


    /** The response of an edit organization request */
    public static final class EditOrganization {
        private EditOrganization() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "edit_organization";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_ORGANIZATION_ID		= "organization_id";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_ORGANIZATION_ID 				+ " integer UNIQUE"
                + ");";
    }


    /** The response of an edit person request */
    public static final class EditPerson {
        private EditPerson() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "edit_person";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_PERSON_ID		= "person_id";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_PERSON_ID 				+ " integer UNIQUE"
                + ");";
    }


    /** The response of an edit role request */
    public static final class EditRole {
        private EditRole() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "edit_role";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_ROLE_ID		= "role_id";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_ROLE_ID 				+ " integer UNIQUE"
                + ");";
    }


    /** The response of an edit specialty request */
    public static final class EditSpecialty {
        private EditSpecialty() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "edit_specialty";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_SPECIALTY_ID		= "specialty_id";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_SPECIALTY_ID 				+ " integer UNIQUE"
                + ");";
    }



    /*
     * Meta tables
     */

    public static final class SearchTerms {
        private SearchTerms() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "search_terms";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_TIMESTAMP		= "timestamp";
        public static final String 	COL_SEARCH			= "search";
        public static final String 	COL_SCOPE			= "scope";


        public static final int		SCOPE_UNKNOWN				= 0;
        public static final int		SCOPE_ALL					= 1;
        public static final int		SCOPE_SPECIALTIES			= 2;
        public static final int		SCOPE_MAP					= 3;
        public static final int		SCOPE_ORGANIZATIONS			= 4;
        public static final int		SCOPE_LINKS					= 5;
        public static final int		SCOPE_PEOPLE_BY_SPECIALTY	= 6;
        public static final int		SCOPE_LOCATIONS_BY_ORGANIZATION	= 7;
        public static final int		SCOPE_PENDING_FLAGS			= 8;
        public static final int		SCOPE_APPROVED_FLAGS		= 9;



        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 				+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_TIMESTAMP 		+ " text NOT NULL, "
                + COL_SEARCH 			+ " text NOT NULL, "
                + COL_SCOPE 			+ " integer NOT NULL"
                + ");";
    }

    /** Keep a list of requests on the client side */
    public static final class Requests {
        public static final String 	TABLE_NAME 				= "requests";
        public static final Uri 	CONTENT_URI 			= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 			= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE		= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 					= BaseColumns._ID;
        public static final String 	COL_REQUEST_TYPE		= "request_type";
        public static final String 	COL_REQUEST_TIME		= "request_time";
        public static final String 	COL_REQUEST_AUTH_KEY	= "request_session_id";
        public static final String 	COL_RESPONSE_TIME		= "response_time";
        public static final String 	COL_RESPONSE_TIME_SERVER	= "response_time_server";
        public static final String 	COL_RESPONSE_CODE		= "response_code";
        public static final String 	COL_RESPONSE_MESSAGE	= "response_message";

        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_REQUEST_TYPE 			+ " text NOT NULL, "
                + COL_REQUEST_TIME 			+ " text NOT NULL, "
                + COL_REQUEST_AUTH_KEY 		+ " text, "
                + COL_RESPONSE_TIME 		+ " text, "
                + COL_RESPONSE_TIME_SERVER 	+ " text, "
                + COL_RESPONSE_CODE 		+ " integer, "
                + COL_RESPONSE_MESSAGE 		+ " text"
                + ");";
    }

    /** Person or OrganizationLocation */
    public static final class Recent {
        private Recent() { /* Not instantiable */ }

        public static final String 	TABLE_NAME 			= "recent";
        public static final Uri 	CONTENT_URI 		= Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String 	CONTENT_TYPE 		= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
        public static final String 	CONTENT_ITEM_TYPE	= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_NAME;

        public static final String 	COL_ID 				= BaseColumns._ID;
        public static final String 	COL_TIMESTAMP 		= "timestamp";

        public static final String 	COL_CITY			= "city";
        public static final String 	COL_STATE			= "state";

        public static final String 	COL_PERSON_ID		= "person_id";
        public static final String 	COL_FIRST_NAME		= "first_name";
        public static final String 	COL_LAST_NAME		= "last_name";

        public static final String 	COL_LOCATION_ID		= "location_id";
        public static final String 	COL_ORGANIZATION	= "organization";



        static final String	CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID 					+ " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_TIMESTAMP 			+ " text NOT NULL, "

                + COL_CITY 					+ " text, "
                + COL_STATE 				+ " text, "

                + COL_PERSON_ID 			+ " integer, "
                + COL_FIRST_NAME 			+ " text, "
                + COL_LAST_NAME 			+ " text, "

                + COL_LOCATION_ID 			+ " integer, "
                + COL_ORGANIZATION 			+ " text"
                + ");";
    }
}

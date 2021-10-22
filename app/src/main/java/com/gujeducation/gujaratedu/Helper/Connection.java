package com.gujeducation.gujaratedu.Helper;


/**
 * Created by ASHISH on 7/13/2017.
 */

public class Connection {


    public static final String HOST = "http://gujarateducation.org/";
    public static final String REST_API = "android_api/";

    public static final String SERVER_ZONE = String.format("%s/", HOST) + REST_API;

    public static final String KEY_RESULTID = "ResultId";
    public static final String KEY_RESULTMESSAGE = "ResultMessage";
    /*

        public static String SERVER_GETURL = "192.168.1.32/members/sandip/hakoba/mobile";
        public static String SERVER_POSTURL = "http://192.168.1.32/members/sandip/hakoba/mobile/";
    */
    public static final String INTENT_EXTRA_SEARCH_STRING = "INTENT_EXTRA_SEARCH_STRING";
    public static String SERVER_GETURL = "gujarateducation.org/android_api";
    public static String SERVER_POSTURL = "http://gujarateducation.org/android_api/";
    public static String APP_NAME = "EklavyaMSB";
    public static String TAG_DATA = "Data";
    public static String TAG_SUCCESS = "success";
    public static String TAG_MESSAGE = "message";
    public static String Tag_data = "data";
    public static String TAG_ISLOGIN = "islogin";
    public static String TAG_USERID = "student_uuid";
    public static String TAG_SCHOOLNAME = "school_name";
    public static String TAG_DIVISION = "division_name";
    public static String TAG_STANDARD = "standard";
    public static String TAG_MEDIUM = "medium";
    public static String TAG_SCHOOLID = "student_schoolId";
    public static String TAG_FIRSTNAME = "student_name";
    public static String TAG_FATHERNAME = "student_fathername";
    public static String TAG_SURNAME = "student_surname";
    public static String TAG_STUDENTDB_ID = "studentId";
    public static String TAG_STUDENT_GRNO = "student_grnumber";
    public static String TAG_STUDENT_UUID = "student_uuid";
    public static String TAG_MEDIUM_ID = "medium_id";
    public static String MEDIUM_ID_GUEST = "medium_id_guest";


    public static String TAG_ISFLAGLOG = "isflaglog";
    public static String TAG_FLAGCODE = "flagcode";


    public static String UPDATE_MYACCOUNT = SERVER_ZONE + "updateMyAccount.php";
}

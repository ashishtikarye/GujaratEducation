package com.gujeducation.gujaratedu.Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.ColorUtils;

import com.gujeducation.R;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

/**
 * Created by Tikarye Ashish on 1/09/2019.
 */

public class Functions {
    public static String uuid;
    public static String TAG_ROLE = "role";
    public static String TAG_USERID = "userId";
    public static String TAG_USERIMAGE = "userImage";
    public static String TAG_USERNAME = "fullName";
    public static String TAG_ISLOGIN = "isloggedin";
    public static String TAG_ISSTUDENTID = "isStudId";
    public static String TAG_ISMEDIUM = "isMedium";
    public static String TAG_ISSECTION = "isSection";
    public static String TAG_ISSEMESTER = "isSemester";
    public static String TAG_ISSTANDARD = "isStandard";
    public static String TAG_ISSTANDARDIDENTIFY = "isStandardIdentify";
    public static String TAG_ISSTANDARDNAME = "isStandardName";
    public static String TAG_ISSUBJECT = "isSubject";
    public static String TAG_ISSUBJECTID = "isSubjectId";
    public static String TAG_ISSUBJECTNAME = "isSubjectName";
    public static String TAG_ISCHAPTER = "isChapter";
    public static String TAG_ISCHAPTERID = "isChapterId";
    public static String TAG_ISPAPERID = "isPaperId";
    public static String TAG_ISEXAMID = "isExamId";
    public static String TAG_ISCHAPTERNAME = "isChapterName";
    public static String TAG_ISLANGUAGE = "islanguage";
    public static String TAG_LANGUAGECODE = "languagecode";
    public static String TAG_ISCLASSTYPE = "isClassType";

    public static String TAG_QUE1 = "que1";
    public static String TAG_ANS1 = "ans1";
    public static String TAG_QUE2 = "que2";
    public static String TAG_ANS2 = "ans2";
    public static String TAG_QUE3 = "que3";
    public static String TAG_ANS3 = "ans3";
    public static String TAG_QUE4 = "que4";
    public static String TAG_ANS4 = "ans4";
    public static String TAG_QUE5 = "que5";
    public static String TAG_ANS5 = "ans5";
    public static String TAG_QUE6 = "que6";
    public static String TAG_ANS6 = "ans6";
    public static String TAG_QUE7 = "que7";
    public static String TAG_ANS7 = "ans7";
    public static String TAG_QUE8 = "que8";
    public static String TAG_ANS8 = "ans8";
    public static String TAG_QUE9 = "que9";
    public static String TAG_ANS9 = "ans9";
    public static String TAG_QUE10 = "que10";
    public static String TAG_ANS10 = "ans10";
    SharedPreferences _Preferences;
    SharedPreferences.Editor editor;
    Context mContext;

    //CONSTRUCTOR
    public Functions(Context context) {
        this.mContext = context;
        _Preferences = mContext.getSharedPreferences("GujaratEducation", 0);
    }

    public static boolean isEmptyEdittext(EditText edt) {
        return edt.getText().toString().trim().equals("");
    }

    //06-08-20


    public static boolean knowInternetOn(Activity activity) {
        if (activity != null)
            return isConnectedWifi(activity) || isConnectedMobile(activity);
        return false;
    }

    public static String ConvertImageURL(String urlImage) {
        URI uri = null;
        try {
            //Log.e("urlImage",""+urlImage);
            uri = new URI(urlImage.replaceAll("https", "http"));
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //Log.e("uriimage-","-------"+ uri);
        return String.valueOf(uri);
    }

//06-08-20

    public static boolean isConnectedWifi(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    public static boolean isConnectedMobile(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public static void showInternetAlert(final Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                alertDialog.setTitle("INTERNET ALERT");
                alertDialog.setMessage("Internet is not turn on. Go to settings menu and turn on WiFi or Mobile data.");
                alertDialog.setPositiveButton("Go Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        activity.startActivity(intent);
                    }
                });
                /*alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });*/
                alertDialog.show();
            }
        });
    }
//06-08-20
    /*public static void ToastUtility(final com.gujarateducation.Activity activity, final String message) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.toast_layout, null);
        AppCompatTextView tv = view.findViewById(R.id.toast_text);
        //Font.MontserratLight.apply(activity, tv);
        tv.setText(message);
        Toast toast = new Toast(activity);
        toast.setView(view);
        //  toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }*/

    public static int getRandomColor(int alpha) {
        Random rnd = new Random();
        int color = Color.argb(alpha, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        return ColorUtils.setAlphaComponent(color, alpha);
    }

    public static void ToastUtility(final Activity activity, final String message) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.toast_layout, null);
        AppCompatTextView tv = view.findViewById(R.id.toast_text);
        //Font.MontserratLight.apply(activity, tv);
        tv.setText(message);
        Toast toast = new Toast(activity);
        toast.setView(view);
        //  toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public void SetPrefLanguage(String languagecode) {
        editor = _Preferences.edit();
        editor.putBoolean(TAG_ISLANGUAGE, true);
        editor.putString(TAG_LANGUAGECODE, languagecode);
        editor.commit();
    }


    public String getMediumId() {
        String medium = _Preferences.getString(Connection.TAG_MEDIUM_ID, "");
        return medium;
    }

    public String getStandard() {
        String standard = _Preferences.getString(Connection.TAG_STANDARD, "");
        return standard;
    }

    public String getPrefLanguageCode() {
        String languageCodePref = _Preferences.getString(TAG_LANGUAGECODE, "");
        return languageCodePref;
    }

    public void SetPrefLogin(boolean value) {
        editor = _Preferences.edit();
        editor.putBoolean(TAG_ISLOGIN, value);
        editor.commit();
    }

    public void SetPrefMediumId(int value) {
        editor = _Preferences.edit();
        editor.putInt(TAG_ISMEDIUM, value);
        editor.commit();
    }

    public void SetPrefUserId(int value) {
        editor = _Preferences.edit();
        editor.putInt(TAG_USERID, value);
        editor.commit();
    }


    public void SetPrefUserImage(String value) {
        editor = _Preferences.edit();
        editor.putString(TAG_USERIMAGE, value);
        editor.commit();
    }

    public void SetPrefRole(String value) {
        editor = _Preferences.edit();
        editor.putString(TAG_ROLE, value);
        editor.commit();
    }

    public String getPrefRole() {
        String rolepref = _Preferences.getString(TAG_ROLE, "");
        return rolepref;
    }

    public int getPrefUserId() {
        int userIdpref = _Preferences.getInt(TAG_USERID, 0);
        return userIdpref;
    }

    public void SetPrefUserName(String username) {
        editor = _Preferences.edit();
        editor.putString(TAG_USERNAME, username);
        editor.commit();
    }


    public String getPrefUserName() {
        String userNamepref = _Preferences.getString(TAG_USERNAME, "");
        return userNamepref;
    }

    public String getPrefUserImage() {
        String userImagepref = _Preferences.getString(TAG_USERIMAGE, "");
        return userImagepref;
    }

    public void SetPrefChapterId(int value) {
        editor = _Preferences.edit();
        editor.putInt(TAG_ISCHAPTERID, value);
        editor.commit();
    }

    public int getPrefChapterId() {
        int chapeterIdpref = _Preferences.getInt(TAG_ISCHAPTERID, 0);
        return chapeterIdpref;
    }

    public void SetPrefSubjectId(int value) {
        editor = _Preferences.edit();
        editor.putInt(TAG_ISSUBJECTID, value);
        editor.commit();
    }

    public int getPrefSubjectId() {
        int subjectIdpref = _Preferences.getInt(TAG_ISSUBJECTID, 0);
        return subjectIdpref;
    }

    public void SetPrefClassType(String value) {
        editor = _Preferences.edit();
        editor.putString(TAG_ISCLASSTYPE, value);
        editor.commit();
    }

    public String getPrefClassType() {
        String classTypePref = _Preferences.getString(TAG_ISCLASSTYPE, "");
        return classTypePref;
    }

    public void SetPrefPapersId(int value) {
        editor = _Preferences.edit();
        editor.putInt(TAG_ISPAPERID, value);
        editor.commit();
    }

    public int getPrefPapersId() {
        int paperIdpref = _Preferences.getInt(TAG_ISPAPERID, 0);
        return paperIdpref;
    }

    public void SetPrefExamId(int value) {
        editor = _Preferences.edit();
        editor.putInt(TAG_ISEXAMID, value);
        editor.commit();
    }

    public int getPrefExamId() {
        int examIdpref = _Preferences.getInt(TAG_ISEXAMID, 0);
        return examIdpref;
    }


    public boolean getPrefLogin() {
        boolean loginpref = _Preferences.getBoolean(TAG_ISLOGIN, false);
        return loginpref;
    }

    public int getPrefMediumId() {
        int mediumIdpref = _Preferences.getInt(TAG_ISMEDIUM, 0);
        return mediumIdpref;
    }

    public void setSectionId(int value) {
        editor = _Preferences.edit();
        editor.putInt(TAG_ISSECTION, value);
        editor.commit();
    }

    public int getSection() {
        int sectionIdPref = _Preferences.getInt(TAG_ISSECTION, 0);
        return sectionIdPref;
    }

    public void setSemesterId(int value) {
        editor = _Preferences.edit();
        editor.putInt(TAG_ISSEMESTER, value);
        editor.commit();
    }

    public int getSemester() {
        int semesterIdPref = _Preferences.getInt(TAG_ISSEMESTER, 0);
        return semesterIdPref;
    }

    public int getStandardId() {
        int standardIdPref = _Preferences.getInt(TAG_ISSTANDARD, 0);
        return standardIdPref;
    }

    public void setStandardId(int value) {
        editor = _Preferences.edit();
        editor.putInt(TAG_ISSTANDARD, value);
        editor.commit();
    }

    public int getStandardIdentify() {
        int standardCategoryPref = _Preferences.getInt(TAG_ISSTANDARDIDENTIFY, 0);
        return standardCategoryPref;
    }

    public void setStandardIdentify(int value) {
        editor = _Preferences.edit();
        editor.putInt(TAG_ISSTANDARDIDENTIFY, value);
        editor.commit();
    }

    public String getStandardName() {
        String standardNamePref = _Preferences.getString(TAG_ISSTANDARDNAME, "");
        return standardNamePref;
    }

    public void setStandardName(String value) {
        editor = _Preferences.edit();
        editor.putString(TAG_ISSTANDARDNAME, value);
        editor.commit();
    }

    public int getSubjectId() {
        int subjectIdPref = _Preferences.getInt(TAG_ISSUBJECT, 0);
        return subjectIdPref;
    }

    public void setSubjectId(int value) {
        editor = _Preferences.edit();
        editor.putInt(TAG_ISSUBJECT, value);
        editor.commit();
    }

    public String getSubjectName() {
        String subjectNamePref = _Preferences.getString(TAG_ISSUBJECTNAME, "");
        return subjectNamePref;
    }

    public void setSubjectName(String value) {
        editor = _Preferences.edit();
        editor.putString(TAG_ISSUBJECTNAME, value);
        editor.commit();
    }

    public int getChapterId() {
        int chapterIdPref = _Preferences.getInt(TAG_ISCHAPTER, 0);
        return chapterIdPref;
    }

    public void setChapterId(int value) {
        editor = _Preferences.edit();
        editor.putInt(TAG_ISCHAPTER, value);
        editor.commit();
    }

    public String getChapterName() {
        String chapterNamePref = _Preferences.getString(TAG_ISCHAPTERNAME, "");
        return chapterNamePref;
    }

    public void setChapterName(String value) {
        editor = _Preferences.edit();
        editor.putString(TAG_ISCHAPTERNAME, value);
        editor.commit();
    }

   /* public int getStudentId() {
        int studentIdPref = _Preferences.getInt(TAG_ISSTUDENTID, 0);
        return studentIdPref;
    }


    public void setStudentId(int value) {
        editor = _Preferences.edit();
        editor.putInt(TAG_ISSTUDENTID, value);
        editor.commit();
    }
*/


    public int getFlagLogCode() {
        int FlagLogCodePref = _Preferences.getInt(Connection.TAG_FLAGCODE, 0);
        return FlagLogCodePref;
    }

    public void logout() {
        editor = _Preferences.edit();
        editor.putBoolean(Connection.TAG_ISLOGIN, false);
        editor.putString(Connection.TAG_USERID, "");
        editor.putString(Connection.TAG_STUDENTDB_ID, "0");
        editor.putString(Connection.TAG_STUDENT_GRNO, "");
        editor.putString(Connection.TAG_STUDENT_UUID, "");
        editor.putString(Connection.TAG_FIRSTNAME, "");
        editor.putString(Connection.TAG_FATHERNAME, "");
        editor.putString(Connection.TAG_SURNAME, "");
        editor.putString(Connection.TAG_SCHOOLID, "");
        editor.putString(Connection.TAG_STANDARD, "");
        editor.putString(Connection.TAG_DIVISION, "");
        editor.putString(Connection.TAG_MEDIUM_ID, "");
        editor.commit();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }


}

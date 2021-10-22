package com.gujeducation.gujaratedu.Helper;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import java.util.Locale;

/**
 * Created by Muhammad Fiaz Awan on 1/7/2016.
 */
public class Language {
    public static final String LANG_CODE_ENGLISH = "en";
    public static final String LANG_CODE_GUJARATI = "gu";
    public static final String LANG_CODE_HINDI = "hi";
    public static final String LANG_CODE_MARATHI = "mr";
    public static final String LANG_CODE_ODIYA = "od";
    public static final String LANG_CODE_TELUGU = "tl";
    public static final String LANG_CODE_URDU = "ur";
    private static final String PREFERRED_LOCALE = "AppPrefLocale";
    public static Configuration config;

    public static boolean isGujarati() {
        return Locale.getDefault().toString().equalsIgnoreCase(LANG_CODE_GUJARATI);
    }

    public static boolean isHindi() {
        return Locale.getDefault().toString().equalsIgnoreCase(LANG_CODE_HINDI);
    }

    public static boolean isMarathi() {
        return Locale.getDefault().toString().equalsIgnoreCase(LANG_CODE_MARATHI);
    }

    public static boolean isOdiya() {
        return Locale.getDefault().toString().equalsIgnoreCase(LANG_CODE_ODIYA);
    }

    public static boolean isTelugu() {
        return Locale.getDefault().toString().equalsIgnoreCase(LANG_CODE_TELUGU);
    }

    public static boolean isUrdu() {
        return Locale.getDefault().toString().equalsIgnoreCase(LANG_CODE_URDU);
    }

    public static void setArabic(Context context) {
        set(context, LANG_CODE_GUJARATI);
    }

    public static String getCode() {
        return isGujarati() ? LANG_CODE_GUJARATI : LANG_CODE_ENGLISH;
    }

    public static void toggle(Context context) {
        set(context, isGujarati() ? LANG_CODE_ENGLISH : LANG_CODE_GUJARATI);
    }

    public static void setEnglish(Context context) {
        set(context, LANG_CODE_ENGLISH);
    }

    public static void set(Context context, String localeCode) {
        //localeCode = localeCode.equalsIgnoreCase(LANG_CODE_GUJARATI) ? LANG_CODE_GUJARATI : LANG_CODE_ENGLISH;

        if (localeCode.equalsIgnoreCase(LANG_CODE_GUJARATI)) {
            localeCode = LANG_CODE_GUJARATI;
        } else if (localeCode.equalsIgnoreCase(LANG_CODE_HINDI)) {
            localeCode = LANG_CODE_HINDI;
        } else if (localeCode.equalsIgnoreCase(LANG_CODE_MARATHI)) {
            localeCode = LANG_CODE_MARATHI;
        } else if (localeCode.equalsIgnoreCase(LANG_CODE_ODIYA)) {
            localeCode = LANG_CODE_ODIYA;
        } else if (localeCode.equalsIgnoreCase(LANG_CODE_TELUGU)) {
            localeCode = LANG_CODE_TELUGU;
        } else if (localeCode.equalsIgnoreCase(LANG_CODE_URDU)) {
            localeCode = LANG_CODE_URDU;
        } else {
            localeCode = LANG_CODE_ENGLISH;
        }


        Locale locale = new Locale(localeCode);
        Locale.setDefault(locale);
        config = new Configuration();
        config.locale = locale;
        setPreferredLocale(context.getApplicationContext(), localeCode);
        Resources res = context.getResources();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }


    public static void sync(Context context) {
        String preferredLocale = getPreferredLocale(context);
        if (!preferredLocale.equalsIgnoreCase(getCode())) {

            Locale locale = new Locale(preferredLocale);
            Locale.setDefault(locale);

            Configuration config = new Configuration();
            config.locale = locale;

            Resources res = context.getResources();
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
    }

    public static String getPreferredLocale(Context context) {
        return PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext())
                .getString(PREFERRED_LOCALE, Locale.getDefault().toString());
    }

    private static void setPreferredLocale(Context context, String localeCode) {
        PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext())
                .edit()
                .putString(PREFERRED_LOCALE, localeCode)
                .commit();
    }
}

package ir.rezvandeveloper.bookonline;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Api {

    public static final String ROOT_URL = "https://rezvandeveloper.ir/noskheha/Api.php?apicall=";
    public static final String LINK_URL = "https://rezvandeveloper.ir/noskheha/archive/";
    public static final String OTHER_ESLAHE_ALEFBAYE_TOHID_URL = "https://rezvandeveloper.ir/noskheha/eslahe_alefbaye_tohid/";
    public static final String TAHRIME_SOKHAN_URL = "https://rezvandeveloper.ir/noskheha/tahrime_sokhan/";
    public static final String MOTEFAREGHE_URL = "https://rezvandeveloper.ir/noskheha/motefareghe/";
    public static final String LINK_1To408_URL = "https://rezvandeveloper.ir/noskheha/1to408/";
    public static final String MUSIC_LINK_URL = "https://rezvandeveloper.ir/noskheha/music/";
    public static final String LINK_MATNE_FARMAYESHAT_URL = "https://rezvandeveloper.ir/noskheha/matne_farmayeshat/";
    public static final String URL_QUERY = ROOT_URL + "query";
    public static final String URL_QUERY_TADRIS_VA_TAVASOL = ROOT_URL + "query_tadris_va_tavasol";
    public static final String URL_GET_ALL_LIST_BOOK = ROOT_URL + "get_all_list";
    public static final String URL_GET_OTHER_ESLAHE_ALEFBAYE_TOHID = ROOT_URL + "get_all_list_eslahe_alefbaye_tohid";
    public static final String URL_GET_ALL_LIST_TAHRIME_SOKHAN = ROOT_URL + "get_all_list_tahrime_sokhan";
    public static final String URL_GET_ALL_LIST_MOTEFAREGHE = ROOT_URL + "get_all_list_motefareghe";
    public static final String URL_GET_ALL_LIST_MUSIC = ROOT_URL + "get_all_list_music";
    public static final String URL_GET_SETTING = ROOT_URL + "get_setting";
    public static final String URL_INCREMENT = ROOT_URL + "increment";
    public static final String URL_CHECK_USER = ROOT_URL + "check_user";
    public static final String URL_LOGIN_USER = ROOT_URL + "login_user";
    public static final String URL_ONE_TIME_PASSWORD = ROOT_URL + "one_time_password";
    public static final String URL_FORGET_PASSWORD = ROOT_URL + "forget_password";
    public static final String URL_REGISTER = ROOT_URL + "register";
    public static final String URL_SEND_NEW_TALKING = ROOT_URL + "send_new_talking";
    public static final String URL_GET_ALL_TALK_QUESTION = ROOT_URL + "get_all_talk_question";
    public static final String URL_GET_ALL_RESPONSE = ROOT_URL + "get_all_response";
    public static final String URL_SEND_RESPONSE = ROOT_URL + "send_response";
    public static final String URL_WITHOUT_RESPONSE = ROOT_URL + "without_response";
    public static final String URL_GET_MY_REQUEST = ROOT_URL + "get_my_question";
    public static final String URL_GET_MY_FAVORITE = ROOT_URL + "get_my_favorite";

    public static SharedPreferences sharedPreferences;

    // چک قطع بودن یا نبودن اینترنت
    public static boolean HasInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }
        @SuppressLint("MissingPermission") NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }
        @SuppressLint("MissingPermission") NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }
        return false;
    }

    public static void ShowAlertDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create()
                .show();
    }

    public static void ShowAlertDialog(Context context, String title, String message, Intent intent) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(intent);
                        // context.finish(); بستن

                    }
                })
                .create()
                .show();
    }

    public static String ConvertNumberEnToFa(String number) {
        String[][] chars = new String[][]{
                {"0", "۰"},
                {"1", "۱"},
                {"2", "۲"},
                {"3", "۳"},
                {"4", "۴"},
                {"5", "۵"},
                {"6", "۶"},
                {"7", "۷"},
                {"8", "۸"},
                {"9", "۹"}
        };
        for (String[] num : chars) {
            number = number.replace(num[0], num[1]);
        }
        return number;
    }
}

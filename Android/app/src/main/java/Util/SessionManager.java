package Util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import View.UI.LoginWebApiActivity;

public class SessionManager {

    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "MySharedPreferences";

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_EMAIL = "KEY_EMAIL";

    public static final String KEY_PASSWORD = "KEY_SIFRE";

    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    
    public static final String TOKEN_TYPE = "TOKEN_TYPE";

    public static final String EXPIRES_IN = "EXPIRES_IN";

    public static final String USER_NAME = "USER_NAME";

    public static final String USER_ID = "USER_ID";

    public static final String ACCESS_TOKEN_RPC = "ACCESS_TOKEN_RPC";

    public static final String EXPIRES_IN_RPC = "EXPIRES_IN_RPC";

    public static final String REFRESH_TOKEN_RPC = "REFRESH_TOKEN_RPC";

    public static final String TEMP_OBJECT = "TEMP_OBJECT";

    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createAccessToken(String access_token, String token_type, String expires_in, String user_name, String user_id){

        editor.putString(ACCESS_TOKEN, access_token);
        editor.putString(TOKEN_TYPE, token_type);
        editor.putString(EXPIRES_IN, expires_in);
        editor.putString(USER_NAME, user_name);
        editor.putString(USER_ID, user_id);
        editor.commit();
    }

    public void createAccessTokenRPC(String access_token_rpc){

        editor.putString(ACCESS_TOKEN_RPC, access_token_rpc);
        //editor.putString(EXPIRES_IN, expires_in_rpc);
        //editor.putString(REFRESH_TOKEN_RPC, refresh_token_rpc);

        editor.commit();
    }

    public String getAccessToken()
    {
        return pref.getString(ACCESS_TOKEN, "");
    }

    public String getAccessTokenRPC()
    {
        return pref.getString(ACCESS_TOKEN_RPC, "");
    }

    public String getUserId()
    {
        return pref.getString(USER_ID, "");
    }

    public void createLoginSession(String email, String sifre){

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, sifre);

        editor.commit();
    }

    public void checkLogin(){

        if(!this.isLoggedIn()){

            Intent i = new Intent(_context, LoginWebApiActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }

    public HashMap getUserDetails(){

        HashMap user = new HashMap();

        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));

        return user;
    }

    public void logoutUser(){

        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, LoginWebApiActivity.class);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        _context.startActivity(i);
    }

    public boolean isLoggedIn(){

        return pref.getBoolean(IS_LOGIN, false);
    }

    public String getTemporaryObject() {
        return pref.getString(TEMP_OBJECT, "");
    }

    public void setTemporaryObject(String value) {
        editor.putString(TEMP_OBJECT, value);
        editor.commit();
    }
}
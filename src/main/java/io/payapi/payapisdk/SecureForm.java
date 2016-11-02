package io.payapi.payapisdk;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 *
 * Created by Payapi on 27/10/16.
 *
 */

public class SecureForm {

    private FragmentActivity activity;
    private String publicId;
    private String apiKey;
    private View.OnClickListener clickListener;
    private PayApiWebViewFragment mFragment = null;

    public SecureForm(FragmentActivity activity, String publicId, String apiKey){

        if (!SecureForm.validateKeyFormat(apiKey)) {
            Log.e("SecureForm","Key format is wrong");
        } else if (!SecureForm.validatePublicIdFormat(publicId)){
            Log.e("SecureForm","Incorrect public Id");
        } else {
            Log.v("SecureForm","valid config");
            this.apiKey = apiKey;
            this.publicId = publicId;
            this.activity = activity;
            initFragment();

            this.clickListener =  new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getTag() != null && v.getTag() instanceof Map){
                        Map map = (Map)v.getTag();
                        openSecureForm(map);
                    }
                }
            };
        }
    }

    private void initFragment() {
        if (activity.getSupportFragmentManager() != null && mFragment == null) {
            mFragment = new PayApiWebViewFragment();
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.replace(android.R.id.content, mFragment).commit();
        }
    }

    public boolean addSecureFormToButton(View button, String message){
        Map map = validJsonObject(message);
        if(map != null) {
            button.setTag(map);
            button.setOnClickListener(clickListener);
            return true;
        }
        return false;
    }

    private Map validJsonObject(String message) {
        try {
            return new ObjectMapper().readValue(message, HashMap.class);
        } catch (Exception e) {
            Log.e("Valid JSON", e.getMessage());
            return null;
        }
    }

    public void openSecureForm(Map map){
        String jwtSignedToken = Jwts.builder()
                .setClaims(map)
                .signWith(SignatureAlgorithm.HS512, Base64.encodeToString(apiKey.getBytes(), Base64.DEFAULT))
                .compact();

        mFragment.makePost("https://"+activity.getString(R.string.payapi_host)+"/v1/secureform/" + publicId, jwtSignedToken);
    }


    private static boolean validateKeyFormat(String key){
        return key != null && key.length() == 32;
    }

    private static boolean validatePublicIdFormat(String pId){

        return pId != null && pId.length() >= 6
                && pId.length() <= 50
                && pId.matches("^([a-z])[a-z0-9-_]{5,49}$");
    }
}


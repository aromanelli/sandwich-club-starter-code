package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    final static private String TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {

        Log.d(TAG, "parseSandwichJson: " + json);

        try {
            final JSONObject jsonObj = new JSONObject(json);

            final JSONObject jsonObjName = new JSONObject(jsonObj.getString("name"));

            // mainName ...
            final String mainName = jsonObjName.getString("mainName");

            // alsoKnownAs ...
            final List<String> alsoKnownAs = new ArrayList<>();
            final JSONArray jsonArrAKA = jsonObjName.getJSONArray("alsoKnownAs");
            for (int i = 0; i < jsonArrAKA.length(); i++) {
                alsoKnownAs.add(jsonArrAKA.getString(i));
            }

            // placeOfOrigin ...
            final String placeOfOrigin = jsonObj.getString("placeOfOrigin");

            // description ...
            final String description = jsonObj.getString("description");

            // image(URL) ...
            final String imageURL = jsonObj.getString("image");

            // ingredients ...
            final List<String> ingredients = new ArrayList<>();
            final JSONArray jsonArrIngredients = jsonObj.getJSONArray("ingredients");
            for (int i = 0; i < jsonArrIngredients.length(); i++) {
                ingredients.add(jsonArrIngredients.getString(i));
            }

            return new Sandwich(
                    mainName,
                    alsoKnownAs,
                    placeOfOrigin,
                    description,
                    imageURL,
                    ingredients
            );

        } catch (JSONException e) {
            Log.e(TAG, "parseSandwichJson: " + e.getLocalizedMessage(), e);
        }

        return null;
    }
}

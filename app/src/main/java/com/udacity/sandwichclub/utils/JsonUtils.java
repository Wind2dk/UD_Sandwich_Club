package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
            JSONObject sandwichJson = new JSONObject(json);
            JSONObject nameJson = sandwichJson.getJSONObject("name");
            sandwich.setMainName(nameJson.getString("mainName"));
            sandwich.setAlsoKnownAs(parseJSONArrayToList(nameJson.getJSONArray("alsoKnownAs")));
            sandwich.setPlaceOfOrigin(sandwichJson.getString("placeOfOrigin"));
            sandwich.setDescription(sandwichJson.getString("description"));
            sandwich.setImage(sandwichJson.getString("image"));
            sandwich.setIngredients(parseJSONArrayToList(sandwichJson.getJSONArray("ingredients")));
    } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }

    private static List parseJSONArrayToList(JSONArray jsonArray) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                list.add(jsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}

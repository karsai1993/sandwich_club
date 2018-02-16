package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject sandwichData = new JSONObject(json);

        JSONObject name = sandwichData.getJSONObject("name");
        String mainName = name.getString("mainName");
        JSONArray alsoKnownAsJSON = name.getJSONArray("alsoKnownAs");
        List<String> alsoKnownAs = new ArrayList<>();
        for (int i = 0; i < alsoKnownAsJSON.length(); i++) {
            alsoKnownAs.add(alsoKnownAsJSON.getString(i));
        }

        String placeOfOrigin = sandwichData.getString("placeOfOrigin");

        String description = sandwichData.getString("description");

        String image = sandwichData.getString("image");

        JSONArray ingredientsJSON = sandwichData.getJSONArray("ingredients");
        List<String> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsJSON.length(); i++) {
            ingredients.add(ingredientsJSON.getString(i));
        }

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}

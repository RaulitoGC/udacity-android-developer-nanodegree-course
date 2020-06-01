package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        Sandwich sandwich = new Sandwich();
        JSONObject sandwichJsonObject = new JSONObject(json);
        if (sandwichJsonObject.has("name")) {
            JSONObject nameJsonObject = sandwichJsonObject.getJSONObject("name");
            if (nameJsonObject.has("mainName")) {
                String mainName = nameJsonObject.getString("mainName");
                sandwich.setMainName(mainName);
            }

            if (nameJsonObject.has("alsoKnownAs")) {
                List<String> alsoKnowAs = new ArrayList<>();
                JSONArray alsoKnowAsJsonArray = nameJsonObject.getJSONArray("alsoKnownAs");
                for (int i = 0; i < alsoKnowAsJsonArray.length(); i++) {
                    alsoKnowAs.add(alsoKnowAsJsonArray.get(i).toString());
                }
                sandwich.setAlsoKnownAs(alsoKnowAs);
            }
        }

        if (sandwichJsonObject.has("placeOfOrigin")) {
            String placeOfOrigin = sandwichJsonObject.getString("placeOfOrigin");
            sandwich.setPlaceOfOrigin(placeOfOrigin);
        }

        if (sandwichJsonObject.has("description")) {
            String description = sandwichJsonObject.getString("description");
            sandwich.setDescription(description);
        }

        if (sandwichJsonObject.has("image")) {
            String image = sandwichJsonObject.getString("image");
            sandwich.setImage(image);
        }

        if (sandwichJsonObject.has("ingredients")) {
            List<String> ingredients = new ArrayList<>();

            JSONArray ingredientsJsonArray = sandwichJsonObject.getJSONArray("ingredients");
            for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                ingredients.add(ingredientsJsonArray.get(i).toString());
            }
            sandwich.setIngredients(ingredients);
        }

        return sandwich;
    }
}

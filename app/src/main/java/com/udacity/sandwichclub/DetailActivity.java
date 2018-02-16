package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String DATA_NOT_AVAILABLE = "DNA";

    private TextView mOriginTextView;
    private TextView mAlsoKnownAsTextView;
    private TextView mIngredientsTextView;
    private TextView mPlaceOfOriginTextView;
    private TextView mDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        mOriginTextView = findViewById(R.id.origin_tv);
        mOriginTextView.setText(sandwich.getMainName());

        mAlsoKnownAsTextView = findViewById(R.id.also_known_tv);
        List<String> alsoKnownAsNameList = sandwich.getAlsoKnownAs();
        int alsoKnownAsNameListSize = alsoKnownAsNameList.size();
        if (alsoKnownAsNameListSize > 0) {
            String alsoKnownAsNames = "";
            for (int i = 0; i < alsoKnownAsNameListSize - 1; i++) {
                alsoKnownAsNames += alsoKnownAsNameList.get(i) + "\n";
            }
            alsoKnownAsNames += alsoKnownAsNameList.get(alsoKnownAsNameListSize - 1);
            mAlsoKnownAsTextView.setText(alsoKnownAsNames);
        } else {
            mAlsoKnownAsTextView.setText(DATA_NOT_AVAILABLE);
        }

        mIngredientsTextView = findViewById(R.id.ingredients_tv);
        List<String> ingredientsList = sandwich.getIngredients();
        int ingredientsListSize = ingredientsList.size();
        String ingredientNames = "";
        for (int i = 0; i < ingredientsListSize - 1; i++) {
            ingredientNames += ingredientsList.get(i) + "\n";
        }
        ingredientNames += ingredientsList.get(ingredientsListSize - 1);
        mIngredientsTextView.setText(ingredientNames);

        mPlaceOfOriginTextView = findViewById(R.id.place_of_origin_tv);
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (placeOfOrigin.length() > 0) {
            mPlaceOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        } else {
            mPlaceOfOriginTextView.setText(DATA_NOT_AVAILABLE);
        }

        mDescriptionTextView = findViewById(R.id.description_tv);
        mDescriptionTextView.setText(sandwich.getDescription());
    }
}

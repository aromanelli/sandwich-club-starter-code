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

import java.util.List;
import java.util.ListIterator;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
            return; // Reviewer: I added this here, seemed like it was necessary to prevent NPE
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(final Sandwich sandwich) {

        setTitle(sandwich.getMainName());

        final ImageView ingredientsIv = findViewById(R.id.image_iv);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        final TextView alsoKnownAs = findViewById(R.id.also_known_tv);
        alsoKnownAs.setText(listToString(sandwich.getAlsoKnownAs()));

        final TextView placeOfOrigin = findViewById(R.id.origin_tv);
        placeOfOrigin.setText(sandwich.getPlaceOfOrigin());

        final TextView description = findViewById(R.id.description_tv);
        description.setText(sandwich.getDescription());

        final TextView ingredients = findViewById(R.id.ingredients_tv);
        ingredients.setText(listToString(sandwich.getIngredients()));

    }

    private String listToString(final List<String> list) {
        final StringBuilder builder = new StringBuilder();
        // Reviewer: Didn't know if I could change minSdkVersion to use Java 8 features
        //           or not, so stayed at 16, which means I cannot use .forEach(), etc.
        if (list != null) {
            ListIterator<String> listIterator = list.listIterator();
            while (listIterator.hasNext()) {
                builder.append(listIterator.next());
                if (listIterator.hasNext()) {
                    builder.append(", ");
                }
            }
        }
        return builder.toString();
    }
}

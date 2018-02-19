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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView txt_description, txt_alsoKnownAs, txt_ingredients, txt_origin;
    private Sandwich mSandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        txt_alsoKnownAs = findViewById(R.id.also_known_tv);
        txt_description = findViewById(R.id.description_tv);
        txt_ingredients = findViewById(R.id.ingredients_tv);
        txt_origin = findViewById(R.id.origin_tv);

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
        mSandwich = JsonUtils.parseSandwichJson(json);
        if (mSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(mSandwich.getImage())
                .into(ingredientsIv);

        setTitle(mSandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        txt_origin.setText(mSandwich.getPlaceOfOrigin());
        txt_description.setText(mSandwich.getDescription());
        populateTextViewWithList(txt_ingredients, mSandwich.getIngredients());
        populateTextViewWithList(txt_alsoKnownAs, mSandwich.getAlsoKnownAs());
    }

    private void populateTextViewWithList(TextView textView, List list) {
        for (int i = 0; i < list.size(); i++) {
            textView.append(list.get(i).toString());
            if (i != list.size() - 1) { //If not last element, make new line
                textView.append("\n");
            }
        }

    }
}

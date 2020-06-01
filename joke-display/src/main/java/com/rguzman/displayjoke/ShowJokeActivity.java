package com.rguzman.displayjoke;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class ShowJokeActivity extends AppCompatActivity {

    private static final String EXTRA_JOKE = "com.udacity.gradle.builtitbigger.extra.JOKE";

    private TextView txtJoke;

    public static Intent getCallingIntent(Context context, String joke) {
        Intent intent = new Intent(context, ShowJokeActivity.class);
        intent.putExtra(EXTRA_JOKE, joke);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        this.txtJoke = findViewById(R.id.txt_joke);

        if (getIntent() != null && getIntent().hasExtra(EXTRA_JOKE)) {
            String joke = getIntent().getStringExtra(EXTRA_JOKE);
            txtJoke.setText(joke);
        }
    }
}

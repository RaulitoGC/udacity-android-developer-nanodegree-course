package com.rguzman.baking.presentation.detail.step;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rguzman.baking.R;
import com.rguzman.baking.domain.model.Step;

import dagger.android.support.DaggerAppCompatActivity;

public class RecipeStepDetailActivity extends DaggerAppCompatActivity {

    private static final String EXTRA_STEP = "com.rguzman.baking.extra.STEP";

//    @BindView(R.id.toolbar)
//    Toolbar toolbar;


    public static Intent getCallingIntent(Context context, Step step) {
        Intent intent = new Intent(context, RecipeStepDetailActivity.class);
        intent.putExtra(EXTRA_STEP, step);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail_step);

        if (getIntent() != null && getIntent().hasExtra(EXTRA_STEP)) {
            Step step = getIntent().getParcelableExtra(EXTRA_STEP);
            //setTitle(step.getShortDescription());
//            toolbar.setTitle(step.getShortDescription());
//            toolbar.setNavigationIcon(R.drawable.ic_arrow);
//            toolbar.setNavigationOnClickListener(v -> finish());

            //ActivityUtils.replaceFragment(this, R.id.recipe_step_detail_fragment, RecipeStepDetailFragment.newInstance(step));

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_step_fragment, RecipeStepDetailFragment.newInstance(step).newInstance(step))
                        .commit();

        }
    }
}

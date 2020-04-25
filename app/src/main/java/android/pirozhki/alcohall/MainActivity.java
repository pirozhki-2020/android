package android.pirozhki.alcohall;

import androidx.appcompat.app.AppCompatActivity;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    FrameLayout mFragmentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentLayout = findViewById(R.id.layout_for_fragment);
        if (savedInstanceState == null) {
            mFragmentLayout.setVisibility(View.GONE);
        }
        final Button add_button = findViewById(R.id.add_ingrediets_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AddIngredientFragment f = new AddIngredientFragment();
                final FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.layout_for_fragment, f);
                transaction.addToBackStack(null);
                transaction.commit();
                mFragmentLayout.setVisibility(View.VISIBLE);
            }
        });

        final Button find_button = findViewById(R.id.find_recipes_button);
        find_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final NoResultFragment f = new NoResultFragment();
                final FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.layout_for_fragment, f);
                transaction.addToBackStack(null);
                transaction.commit();
                mFragmentLayout.setVisibility(View.VISIBLE);
            }
        });

    }
}

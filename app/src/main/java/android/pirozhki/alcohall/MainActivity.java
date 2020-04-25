package android.pirozhki.alcohall;

import androidx.appcompat.app.AppCompatActivity;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    FrameLayout fragmentLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button add_button  = (Button) findViewById(R.id.add_ingrediets_button);
        fragmentLayout = findViewById(R.id.layout_for_fragment);
        if (savedInstanceState == null){
            fragmentLayout.setVisibility(View.GONE);
        }
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentLayout.setVisibility(View.VISIBLE);
                final AddIngredientFragment f = new AddIngredientFragment();
                final FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.layout_for_fragment, f);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }
}

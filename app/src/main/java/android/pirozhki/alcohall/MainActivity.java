package android.pirozhki.alcohall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

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

        final Button find_button = findViewById(R.id.find_recipes_button);
        find_button.setOnClickListener(v -> {

            final NoResultFragment f = new NoResultFragment();
            final FragmentTransaction transaction = getFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.layout_for_fragment, f);
            transaction.addToBackStack(null);
            transaction.commit();
            mFragmentLayout.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_item_new_ingredient) {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(R.layout.add_ingr_fragment);
            View bottomSheetInternal = bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
            BottomSheetBehavior.from(bottomSheetInternal).setPeekHeight(400);
            bottomSheetDialog.show();
            Button back_from_add_button = bottomSheetInternal.findViewById(R.id.back_from_add_button);
            back_from_add_button.setOnClickListener(v -> bottomSheetDialog.dismiss());

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

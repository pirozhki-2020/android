package android.pirozhki.alcohall.activity;

import android.os.Bundle;
import android.pirozhki.alcohall.fragment.NoResultDialogFragment;
import android.pirozhki.alcohall.R;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mFragmentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentLayout = findViewById(R.id.layout_for_fragment);
        if (savedInstanceState == null) {
            mFragmentLayout.setVisibility(View.GONE);
        }

        final Button findButton = findViewById(R.id.find_recipes_button);
        findButton.setOnClickListener(v -> {
            NoResultDialogFragment dialog = new NoResultDialogFragment();
            dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
            /*getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.layout_for_fragment, new NoResultDialogFragment())
                    .addToBackStack(null)
                    .commit();
            mFragmentLayout.setVisibility(View.VISIBLE);*/
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
            showBottomSheetDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onTextViewClick(View view) {
        showBottomSheetDialog();
    }

    private void showBottomSheetDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.add_ingr_fragment);
        final View bottomSheetInternal = bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior.from(bottomSheetInternal).setPeekHeight(400);
        bottomSheetDialog.show();

        Button back_from_add_button = bottomSheetInternal.findViewById(R.id.back_from_add_button);
        back_from_add_button.setOnClickListener(v -> bottomSheetDialog.dismiss());
    }
}

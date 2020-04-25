package android.pirozhki.alcohall;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

public class AddIngredientFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_ingr_fragment, null);

        Button back_from_add_button = view.findViewById(R.id.back_from_add_button);
        back_from_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FrameLayout layout = getActivity().findViewById(R.id.layout_for_fragment);
                layout.setVisibility(View.GONE);
            }
        });

        return view;
    }
}

package com.pirozhki.alcohall.selections.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pirozhki.alcohall.R;
import com.pirozhki.alcohall.selections.model.Selection;
import com.pirozhki.alcohall.selections.viewmodel.SelectionViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelectionsFragment extends Fragment {
    private RecyclerView mSelectionsRecyclerView;
    private SelectionAdapter mAdapter = new SelectionAdapter();

    private SelectionViewModel mSelectionViewModel;

    private TextView mNoResultsTextView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.selections_fragment, null);

        mSelectionViewModel = new ViewModelProvider(this).get(SelectionViewModel.class);


        mNoResultsTextView = view.findViewById(R.id.no_selections_text_view);

        mSelectionsRecyclerView = view.findViewById(R.id.selections_recycler_view);
        mSelectionsRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        mAdapter = new SelectionsFragment.SelectionAdapter();
        mSelectionsRecyclerView.setAdapter(mAdapter);

        mSelectionViewModel.getApiResponse().observe(getViewLifecycleOwner(), apiResponse -> {
            if (apiResponse.getError() != null) {
                handleError(apiResponse.getError());
            } else {
                handleResponse(apiResponse.getSelections());
            }
        });


        return view;
    }


    public void onSelectionSelected(Selection Selection) {
    }

    private void handleError(Throwable error) {
        Log.e(SelectionsFragment.class.getName(), "error occurred while get api response: " + error.toString());
    }

    private void handleResponse(List<Selection> selections) {
        if (selections != null && selections.size() > 0) {
            mAdapter.setSelections(selections);
            mAdapter.notifyDataSetChanged();
            mNoResultsTextView.setVisibility(View.INVISIBLE);
        } else {
            mAdapter.clearSelections();
            mAdapter.notifyDataSetChanged();
            mNoResultsTextView.setVisibility(View.VISIBLE);
        }
    }

    private class SelectionHolder extends RecyclerView.ViewHolder {
        private Selection mSelection;

        private TextView mTitleTextView;
        private TextView mDescriptionTextView;

        public SelectionHolder(View itemView) {
            super(itemView);

            mTitleTextView = itemView.findViewById(R.id.selection_title_text_view);
            mDescriptionTextView = itemView.findViewById(R.id.selection_description_text_view);

            mTitleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelectionSelected(mSelection);
                }
            });
        }

        public void bindSelection(Selection Selection) {
            mSelection = Selection;
            mTitleTextView.setText(mSelection.getName());
            mDescriptionTextView.setText(mSelection.getDescription());
        }
    }

    private class SelectionAdapter extends RecyclerView.Adapter<SelectionsFragment.SelectionHolder> {
        private List<Selection> mSelections = new ArrayList<>();

        @Override
        @NonNull
        public SelectionsFragment.SelectionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(SelectionsFragment.this.getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_selection, parent, false);
            return new SelectionsFragment.SelectionHolder(view);
        }

        @Override
        public void onBindViewHolder(SelectionsFragment.SelectionHolder holder, int position) {
            Selection Selection = mSelections.get(position);
            holder.bindSelection(Selection);
        }

        @Override
        public int getItemCount() {
            return mSelections.size();
        }

        public void setSelections(List<Selection> Selections) {
            mSelections = Selections;
        }

        void clearSelections() {
            mSelections.clear();
        }
    }
}
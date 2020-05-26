package ru.techpark.agregator.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import ru.techpark.agregator.R;
import ru.techpark.agregator.viewmodels.BdSingleViewModel;

public class BdDetailedFragment extends DetailedFragment {

    public static BdDetailedFragment newInstance(int num) {
        BdDetailedFragment frag = new BdDetailedFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(NUM_CURR, num);
        frag.setArguments(bundle);
        return frag;
    }

    private void updateFeed() {
        navigator.openDBFeed();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailedViewModel = new ViewModelProvider(this).get(BdSingleViewModel.class);
        Bundle arguments = getArguments();
        if (arguments != null) {
            id = arguments.getInt(NUM_CURR);
            detailedViewModel.getDetailedEvent(id);
        }
        else handleErrorInObserver();
    }

    @Override
    void handleErrorInObserver() {
        super.handleErrorInObserver();
        errorText.setText(R.string.database_error);
        errorImage.setImageResource(R.drawable.ic_database_error);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        likeEvent.setVisibility(View.VISIBLE);
        likeEvent.setImageResource(R.drawable.ic_action_delete);

        likeEvent.setOnClickListener((v) -> {
            detailedViewModel.deleteEventBD(event);
            updateFeed();
        });

    }

}

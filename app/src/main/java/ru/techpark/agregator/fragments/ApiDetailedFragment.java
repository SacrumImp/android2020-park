package ru.techpark.agregator.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import ru.techpark.agregator.viewmodels.ApiSingleViewModel;

public class ApiDetailedFragment extends DetailedFragment {

    private static final String ERROR_IN_OBSERVER = "Нет соеинения с интернетом";

    public static ApiDetailedFragment newInstance(int num) {
        ApiDetailedFragment frag = new ApiDetailedFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(NUM_CURR, num);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailedViewModel = new ViewModelProvider(this).get(ApiSingleViewModel.class);
        Bundle arguments = getArguments();
        if (arguments != null) {
            id = arguments.getInt(NUM_CURR);
            detailedViewModel.getDetailedEvent(id);
        }
    }
    @Override
    void hideLoading() {
        super.hideLoading();
        likeEvent.setVisibility(View.VISIBLE);
    }
    @Override
    void handleErrorInObserver() {
        super.handleErrorInObserver();
        Toast.makeText(getContext(), ERROR_IN_OBSERVER, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //обработка нажатия лайка
        likeEvent.setOnClickListener((v) -> detailedViewModel.insertEventBD(event));
    }


}

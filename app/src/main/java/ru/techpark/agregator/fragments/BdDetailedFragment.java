package ru.techpark.agregator.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ru.techpark.agregator.R;
import ru.techpark.agregator.viewmodels.BdSingleViewModel;

public class BdDetailedFragment extends DetailedFragment {

    private static final String ERROR_IN_OBSERVER = "Ошибка в чтении БД";

    public static BdDetailedFragment newInstance(int num) {
        BdDetailedFragment frag = new BdDetailedFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(NUM_CURR, num);
        frag.setArguments(bundle);
        return frag;
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
        else Toast.makeText(getContext(), "@string/error_find", Toast.LENGTH_SHORT).show();
    }

    void hideLoading() {
        loading_progress.setVisibility(View.GONE);
    }

    void handleErrorInObserver() {
        loading_progress.setVisibility(View.GONE);
        Toast.makeText(getContext(), ERROR_IN_OBSERVER, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        FloatingActionButton floatingActionButton = view.findViewById(R.id.likeUnlike);
        floatingActionButton.setVisibility(View.GONE);

    }

}

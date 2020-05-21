package ru.techpark.agregator.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import ru.techpark.agregator.R;
import ru.techpark.agregator.event.Event;
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

    void updateFeed() {
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
        else Toast.makeText(getContext(), "@string/error_find", Toast.LENGTH_SHORT).show();
    }

    @Override
    void handleErrorInObserver() {
        super.handleErrorInObserver();
        Toast.makeText(getContext(), ERROR_IN_OBSERVER, Toast.LENGTH_SHORT).show();
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

    @Override
    void setTimeInformation(Event event) {
        notifyButton.setVisibility(View.VISIBLE);
        calendar_button.setVisibility(View.VISIBLE);
        super.setTimeInformation(event);
    }
}

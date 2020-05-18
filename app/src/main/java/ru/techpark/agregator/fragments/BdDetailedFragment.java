package ru.techpark.agregator.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
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
        button_go.setVisibility(View.GONE);
        likeEvent.setImageResource(R.drawable.ic_action_delete);
        Observer<Event> observer = event -> {
            if (event != null) {
                this.event = event;
                if (!(event.getDates().get(0).getStart_date().equals("null") || event.getDates().get(0).getStart_time().equals("00:00:00") ||
                        event.getDates().get(0).getStart_time().equals("null") )) {
                    time_label.setVisibility(View.VISIBLE);
                    time_start.setVisibility(View.VISIBLE);
                    date_start.setVisibility(View.VISIBLE);
                    button_go.setVisibility(View.VISIBLE);
                    date_start.setText(event.getDates().get(0).getStart_date());
                    time_start.setText(event.getDates().get(0).getStart_time());
                }
            } else {
                handleErrorInObserver();
            }
        };

        detailedViewModel
                .getEvent()
                .observe(getViewLifecycleOwner(), observer);

        likeEvent.setOnClickListener((v) -> {
            detailedViewModel.deleteEventBD(event);
            updateFeed();
        });

    }

}

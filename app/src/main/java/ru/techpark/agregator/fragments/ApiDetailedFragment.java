package ru.techpark.agregator.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import ru.techpark.agregator.R;
import ru.techpark.agregator.event.Event;
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

    void hideLoading() {
        loading_progress.setVisibility(View.GONE);
        likeEvent.setVisibility(View.VISIBLE);
    }

    void handleErrorInObserver() {
        loading_progress.setVisibility(View.GONE);
        Toast.makeText(getContext(), ERROR_IN_OBSERVER, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button_go.setVisibility(View.GONE);
        likeEvent.setVisibility(View.GONE);


        Observer<Event> observer = event -> {
            if (event != null) {
                this.event = event;
                if (!(event.getDates().get(0).getStart_date()==null || event.getDates().get(0).getStart_time() == null)) {
                    time_label.setVisibility(View.VISIBLE);
                    time_start.setVisibility(View.VISIBLE);
                    date_start.setVisibility(View.VISIBLE);
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

        //обработка нажатия лайка
        likeEvent.setOnClickListener((v) -> detailedViewModel.insertEventBD(event));
    }


}

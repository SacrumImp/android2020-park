package ru.techpark.agregator.fragments;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import ru.techpark.agregator.NotificationWorker;
import ru.techpark.agregator.R;
import ru.techpark.agregator.event.Event;
import ru.techpark.agregator.viewmodels.SingleViewModel;

public abstract class DetailedFragment extends Fragment {

    static final String NUM_CURR = "CURRENT";
    public final static String KEY_DES = "KEY_DES";
    protected int id;
    protected Event event;
    private static final String TAG = "fragment";
    private TextView title;
    private TextView description;
    private TextView body_text;
    private TextView price;
    private TextView date_start;
    private TextView time_start;
    private TextView location;
    private TextView location_label;
    private ImageView image;
    private TextView price_label;
    private TextView description_label;
    private TextView phone_label;
    private TextView time_label;
    private TextView place_title_label;
    private TextView place_title;
    private TextView place_address;
    private TextView place_address_label;
    ProgressBar loading_progress;
    ImageButton button_go;
    FloatingActionButton likeEvent;

    SingleViewModel detailedViewModel;

    public final static String KEY_ID = "KEY_ID";
    public final static String KEY_TITLE = "KEY_TITLE";
    private TextView phone;
    public final static String KEY_DATE = "KEY_DATE";
    public final static String KEY_TIME = "KEY_TIME";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detailed_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        time_label = view.findViewById(R.id.label_date);
        description_label = view.findViewById(R.id.label_description);
        body_text = view.findViewById(R.id.body_text);
        price = view.findViewById(R.id.price);
        image = view.findViewById(R.id.image);
        description = view.findViewById(R.id.description);
        date_start = view.findViewById(R.id.date_start);
        time_start = view.findViewById(R.id.time_start);
        price_label = view.findViewById(R.id.price_label);
        location = view.findViewById(R.id.location);
        location_label = view.findViewById(R.id.location_label);
        phone_label = view.findViewById(R.id.phone_label);
        place_address = view.findViewById(R.id.place_address);
        place_address_label = view.findViewById(R.id.place_address_label);
        place_title = view.findViewById(R.id.place_title);
        place_title_label = view.findViewById(R.id.place_title_label);
        phone = view.findViewById(R.id.phone);
        button_go = view.findViewById(R.id.go_btn);
        loading_progress = view.findViewById(R.id.loading_progress);
        title = view.findViewById(R.id.title);

        description_label.setVisibility(View.INVISIBLE);
        time_label.setVisibility(View.INVISIBLE);
        price_label.setVisibility(View.INVISIBLE);
        phone_label.setVisibility(View.GONE);
        place_address.setVisibility(View.GONE);
        place_address_label.setVisibility(View.GONE);
        place_title.setVisibility(View.GONE);
        place_title_label.setVisibility(View.GONE);
        phone.setVisibility(View.GONE);
        price_label.setVisibility(View.GONE);
        price.setVisibility(View.GONE);
        loading_progress.setVisibility(View.VISIBLE);

        Observer<Event> observer = event -> {
            if (event != null) {
                setEventData(event);
            } else {
                handleErrorInObserver();
            }
            Log.d(TAG, "observer worked");
        };

        detailedViewModel
                .getEvent()
                .observe(getViewLifecycleOwner(), observer);
    }

    private void setEventData(Event event) {
        this.event = event;
        hideLoading();
        title.setText(event.getTitle());
        description_label.setVisibility(View.VISIBLE);
        description.setText(Html.fromHtml(event.getDescription()));
        if (event.getImages().size() > 0)
            Glide.with(image.getContext())
                    .load(event.getImages().get(0).getImageUrl())
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .error(R.drawable.ic_image_placeholder)
                    .into(image);
        body_text.setText(Html.fromHtml(event.getBody_text()));
        if (event.getPrice().length() != 0) {
            price_label.setVisibility(View.VISIBLE);
            price.setVisibility(View.VISIBLE);
            price.setText(event.getPrice());
        }
        if (event.getLocation().getSlug().equals("online")) {
            location_label.setText(R.string.way_to_go);
        } else {
            location_label.setText(R.string.city);
        }
        location.setText(event.getLocation().getName());
        time_label.setVisibility(View.VISIBLE);
        date_start.setText(event.getDates().get(0).getStart_date());
        time_start.setText(event.getDates().get(0).getStart_time());
        if (event.getPlace() != null) {
            if (event.getPlace().getTitle().length() != 0) {
                place_title_label.setVisibility(View.VISIBLE);
                place_title.setVisibility(View.VISIBLE);
                place_title.setText(event.getPlace().getTitle());
            }
            if (event.getPlace().getAddress().length() != 0) {
                place_address.setVisibility(View.VISIBLE);
                place_address_label.setVisibility(View.VISIBLE);
                place_address.setText(event.getPlace().getAddress());
            }
            if (event.getPlace().getPhone().length() != 0) {
                phone_label.setVisibility(View.VISIBLE);
                phone.setVisibility(View.VISIBLE);
                phone.setText(event.getPlace().getPhone());
            }
        }
        button_go.setOnClickListener(v -> {
            String workTag = event.getId() + "";
            Data put = new Data.Builder().putInt(KEY_ID, event.getId())
                    .putString(KEY_DATE, event.getDates().get(0).getStart_date())
                    .putString(KEY_TIME, event.getDates().get(0).getStart_time())
                    .putString(KEY_TITLE, event.getTitle())
                    .putString(KEY_DES, event.getDescription()).build();
            long difference;
            Date curDate = new Date();
            Date eventDate = new Date(event.getDates().get(0).getStart() * 1000L);
            long extra_time = 18000000; // 5 часов.
            difference = eventDate.getTime() - curDate.getTime() - extra_time; // за 5 часов до события
            OneTimeWorkRequest notificationWork = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                    //          .setInputData(put).build();
                    .setInputData(put).setInitialDelay(difference, TimeUnit.MILLISECONDS).addTag(workTag).build();
            WorkManager.getInstance(getContext()).enqueue(notificationWork);
        });
    }

    abstract void hideLoading();

    abstract void handleErrorInObserver();
}

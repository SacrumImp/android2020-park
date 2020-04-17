package ru.techpark.agregator;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import ru.techpark.agregator.event.Event;


public class DetailedEventFragment extends Fragment {
    private static final String NUM_CURR = "CURRENT";
    private int id;
    private Event event;
    TextView title;
    TextView description;
    TextView body_text;
    TextView price;
    TextView date_start;
    TextView time_start;
    TextView location;
    TextView location_label;
    ImageView image;
    TextView price_label;
    TextView description_label;
    TextView phone_label;
    TextView time_label;
    TextView place_title_label;
    TextView place_title;
    TextView place_address;
    TextView place_address_label;
    TextView phone;
    int imageHeightPixels = 280;
    int imageWidthPixels = 600;

    static DetailedEventFragment newInstance(int num) {
        DetailedEventFragment frag = new DetailedEventFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(NUM_CURR, num);
        frag.setArguments(bundle);
        return frag;
    }

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
        title = view.findViewById(R.id.title);
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

        Bundle arguments = getArguments();
        if (arguments != null) {
            id = arguments.getInt(NUM_CURR);
        }
        Observer<Event> observer = new Observer<Event>() {
            //Todo тут, наверное не так надо, это костыль
            @Override
            public void onChanged(Event Event) {
                if (Event != null) {
                    event = Event;
                    title.setText(event.getTitle());
                    description_label.setVisibility(View.VISIBLE);
                    description.setText(Html.fromHtml(event.getDescription()));
                    if (event.getImages().size() > 0)
                        Glide.with(image.getContext())
                                .load(event.getImages().get(0).getImageUrl())
                                .skipMemoryCache(true)
                                .override(imageWidthPixels, imageHeightPixels)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .fitCenter()
                                .error(R.drawable.ic_image_placeholder)
                                .into(image);
                    body_text.setText(Html.fromHtml(event.getBody_text()));
                    if (event.getPrice().length() !=0) {
                        price_label.setVisibility(View.VISIBLE);
                        price.setVisibility(View.VISIBLE);
                        price.setText(event.getPrice());
                    }
                    if (event.getLocation().getSlug().equals("online")){
                        location_label.setText(R.string.way_to_go);
                    }
                    else{
                        location_label.setText(R.string.city);
                    }
                    location.setText(event.getLocation().getName());
                    time_label.setVisibility(View.VISIBLE);
                    date_start.setText(event.getDates().get(0).getStart_date());
                    time_start.setText(event.getDates().get(0).getStart_time());
                    if (event.getPlace() != null){
                        if(event.getPlace().getTitle().length()!=0){
                            place_title_label.setVisibility(View.VISIBLE);
                            place_title.setVisibility(View.VISIBLE);
                            place_title.setText(event.getPlace().getTitle());
                        }
                        if (event.getPlace().getAddress().length()!=0){
                            place_address.setVisibility(View.VISIBLE);
                            place_address_label.setVisibility(View.VISIBLE);
                            place_address.setText(event.getPlace().getAddress());
                        }
                        if(event.getPlace().getPhone().length()!=0){
                            phone_label.setVisibility(View.VISIBLE);
                            phone.setVisibility(View.VISIBLE);
                            phone.setText(event.getPlace().getPhone());
                        }
                    }

                }
                Log.d("fragment", "observer worked");
            }
        };

        FeedViewModel feedViewModel = new ViewModelProvider(this).get(FeedViewModel.class);
        feedViewModel
                .getEvent()
                .observe(getViewLifecycleOwner(), observer);
        feedViewModel.getDetailedEvent(id);

    }
}
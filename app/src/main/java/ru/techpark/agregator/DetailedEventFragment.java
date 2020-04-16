package ru.techpark.agregator;

import android.os.Bundle;
import android.text.Html;
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

import java.util.List;

import ru.techpark.agregator.event.Event;


public class DetailedEventFragment extends Fragment {
    private static final String NUM_CURR = "CURRENT";
    private int id;
    private  Event event;
    TextView title;
    TextView description;
    TextView body_text;
    TextView price;
    TextView date_start;
    TextView date_end;
    TextView location;
    ImageView image;
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
        View view = inflater.inflate(R.layout.fragment_detailed_event, container, false);
        Bundle arguments = getArguments();
        if (arguments != null) {
            id = arguments.getInt(NUM_CURR);
        } else
            return view;
        Observer<Event> observer = new Observer<Event>() {
            @Override
            public void onChanged(Event Event) {
                if (Event != null) {
                    event = Event;
                }
            }
        };
        //FeedViewModel feedViewModel = new FeedViewModel(getActivity().getApplication());
       // event = feedViewModel.getEvent(id).getValue();

        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        viewModelProvider.get(FeedViewModel.class).getEvent(id).observe(getViewLifecycleOwner(),observer);
        event = viewModelProvider.get(FeedViewModel.class).getEvent(id).getValue();

        title = view.findViewById(R.id.title);
        title.setText(event.getTitle());
        description = view.findViewById(R.id.description);
        description.setText(Html.fromHtml(event.getDescription()));
        image = view.findViewById(R.id.image);

        if (event.getImages().size() > 0)
            Glide.with(image.getContext())
                    .load(event.getImages().get(0).getImageUrl())
                    .skipMemoryCache(true)
                    .override(imageWidthPixels, imageHeightPixels)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .error(R.drawable.ic_image_placeholder)
                    .into(image);
        body_text = view.findViewById(R.id.body_text);
        body_text.setText(event.getBody_text());
        price = view.findViewById(R.id.price);
        price.setText(event.getPrice());



        return view;


    }
}

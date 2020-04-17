package ru.techpark.agregator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.Observer;


import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ru.techpark.agregator.event.Event;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FeedViewModel feedViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView feed = findViewById(R.id.list_of_events);
        final FeedAdapter adapter = new FeedAdapter();
        feed.setAdapter(adapter);
        feed.setLayoutManager(new LinearLayoutManager(this));

        Observer<List<Event>> observer = new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> Events) {
                if (Events != null) {
                    adapter.setEvents(Events);
                    Log.d(TAG, "observer");
                }
            }
        };
        feedViewModel = new ViewModelProvider(this)
                .get(FeedViewModel.class);
        feedViewModel
                .getEvents()
                .observe(this, observer);

    }
    private void loadNextPage(int page) {
        feedViewModel.addNextPage(page);
    }

    private class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {

        private List<Event> events = new ArrayList<>();
        private int pageCounter = 1;

        void setEvents(List<Event> events) {
            int EVENTS_ON_PAGE = 20;

            this.events = events;
            if (pageCounter == 1)
                notifyDataSetChanged();
            else {
                notifyItemRangeInserted(EVENTS_ON_PAGE * (pageCounter - 1), EVENTS_ON_PAGE);
            }
        }

        @NonNull
        @Override
        public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new FeedViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.feed_elem, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {

            Event event = events.get(position);
            holder.title.setText(event.getTitle());
            holder.description.setText(Html.fromHtml(event.getDescription()));
            if (event.getImages().size() > 0)
                Glide.with(holder.eventImage.getContext())
                        .load(event.getImages().get(0).getImageUrl())
                        .error(R.drawable.ic_image_placeholder)
                        .into(holder.eventImage);
            if (position == getItemCount() - 1) {
                loadNextPage(++pageCounter);
            }
        }

        @Override
        public int getItemCount() {
            return events.size();
        }
    }

    private static class FeedViewHolder extends RecyclerView.ViewHolder {
        ImageView eventImage;
        TextView title;
        TextView description;

        FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            eventImage = itemView.findViewById(R.id.image_in_feed);
            title = itemView.findViewById(R.id.title_in_feed);
            description = itemView.findViewById(R.id.description_in_feed);
        }
    }
}

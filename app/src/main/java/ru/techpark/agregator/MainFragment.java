package ru.techpark.agregator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.Observer;


import android.media.tv.TvContentRating;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import ru.techpark.agregator.event.Event;
import ru.techpark.agregator.event.EventRepo;
import ru.techpark.agregator.network.EventApi;

public class MainFragment extends Fragment {
    private FragmentNavigator navigator;
    private static final String TAG = "MainFragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        navigator = ((FragmentNavigator)getActivity());
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        RecyclerView feed = view.findViewById(R.id.list_of_events);
        final FeedAdapter adapter = new FeedAdapter();
        feed.setAdapter(adapter);
        feed.setLayoutManager(new LinearLayoutManager(view.getContext()));

        Observer<List<Event>> observer = new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> Events) {
                if (Events != null) {
                    adapter.setEvents(Events);
                }
            }
        };

        new ViewModelProvider(this)
                .get(FeedViewModel.class)
                .getEvents()
                .observe(getViewLifecycleOwner(), observer);
        return  view;
    }


    private static class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {

        private List<Event> events = new ArrayList<>();

        void setEvents(List<Event> events) {
            this.events = events;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new FeedViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.feed_elem, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
            int imageHeightPixels = 280;
            int imageWidthPixels = 600;

            Event event = events.get(position);
            holder.title.setText(event.getTitle());
            holder.description.setText(Html.fromHtml(event.getDescription()));
            if (event.getImages().size() > 0)
                Glide.with(holder.eventImage.getContext())
                        .load(event.getImages().get(0).getImageUrl())
                        .skipMemoryCache(true)
                        .override(imageWidthPixels, imageHeightPixels)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .fitCenter()
                        .error(R.drawable.ic_image_placeholder)
                        .into(holder.eventImage);
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

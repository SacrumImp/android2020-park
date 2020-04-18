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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import ru.techpark.agregator.event.Event;
import ru.techpark.agregator.event.EventRepo;
import ru.techpark.agregator.network.EventApi;

public class MainFragment extends Fragment {
    private static FragmentNavigator navigator;
    private static final String TAG = "MainFragment";
    private static  FeedAdapter adapter;
    private static FeedViewModel feedViewModel;
    private ProgressBar loadingProgress;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navigator = ((FragmentNavigator)getActivity());
        RecyclerView feed = view.findViewById(R.id.list_of_events);
        loadingProgress = view.findViewById(R.id.loading_progress);
        adapter = new FeedAdapter();
        feed.setAdapter(adapter);
        feed.setLayoutManager(new LinearLayoutManager(view.getContext()));
        Observer<List<Event>> observer = new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> Events) {
                if (Events != null) {
                    hideLoadingProgress();
                    adapter.setEvents(Events);
                }
            }
        };
        feedViewModel = new ViewModelProvider(this).get(FeedViewModel.class);
                feedViewModel.getEvents()
                .observe(getViewLifecycleOwner(), observer);

    }

    private void hideLoadingProgress() {
        loadingProgress.setVisibility(View.INVISIBLE);
    }

    private void loadNextPage(int page) {
        showLoadingProgress();
        Log.d(TAG, "progress" + loadingProgress.getVisibility());
        feedViewModel.addNextPage(page);
    }

    private void showLoadingProgress() {
        loadingProgress.setVisibility(View.VISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first, container, false);
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

        int getIdOfEvent(int position){
            return events.get(position).getId();
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

    static class FeedViewHolder extends RecyclerView.ViewHolder {
        ImageView eventImage;
        TextView title;
        TextView description;

        FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            eventImage = itemView.findViewById(R.id.image_in_feed);
            title = itemView.findViewById(R.id.title_in_feed);
            description = itemView.findViewById(R.id.description_in_feed);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = adapter.getIdOfEvent(getAbsoluteAdapterPosition());
                    navigator.navigateToAnotherFragment(id);
                    Log.d(TAG, "id " + id);
                }
            });
        }
    }
}
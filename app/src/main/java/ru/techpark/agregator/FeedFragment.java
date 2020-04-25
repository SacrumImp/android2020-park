package ru.techpark.agregator;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ru.techpark.agregator.event.Event;

public abstract class FeedFragment extends Fragment {
    private static final String SEARCH_STATE = "SEARCH_STATE";
    private static final String SEARCH_QUERY = "SEARCH_QUERY";
    private static final String PAGE = "PAGE";
    protected static final String TAG = "MainFragment";
    protected boolean isSearch = false;
    protected String searchQuery;
    protected int pageCounter = 1;
    protected boolean isAllEvents = false;

    protected static FragmentNavigator navigator;
    protected ProgressBar loadingProgress;
    protected static FeedViewModel feedViewModel;
    protected static FeedFragment.FeedAdapter adapter;

    protected EditText searchField;
    protected ImageButton startSearch;
    protected ImageButton exitSearch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            isSearch = savedInstanceState.getBoolean(SEARCH_STATE);
            searchQuery = savedInstanceState.getString(SEARCH_QUERY);
            pageCounter = savedInstanceState.getInt(PAGE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SEARCH_STATE, isSearch);
        outState.putString(SEARCH_QUERY, searchQuery);
        outState.putInt(PAGE, pageCounter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first, container, false);
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
            itemView.setOnClickListener(v -> {
                int id = adapter.getIdOfEvent(getAbsoluteAdapterPosition());
                navigator.navigateToAnotherFragment(id);
                Log.d(TAG, "id " + id);
            });
        }
    }

    protected void hideLoadingProgress() {
        Log.d(TAG, "progress hidden");
        loadingProgress.setVisibility(View.GONE);
    }

    protected void showLoadingProgress() {
        Log.d(TAG, "progress shown");
        loadingProgress.setVisibility(View.VISIBLE);
    }

    protected abstract void loadNextPage();

    protected class FeedAdapter extends RecyclerView.Adapter<FeedFragment.FeedViewHolder> {

        private List<Event> events = new ArrayList<>();

        void setEvents(List<Event> events) {
            int EVENTS_ON_PAGE = 20;

            //todo убрать этот костыль из-за конечности результатов поиска
            if (events.size() % EVENTS_ON_PAGE != 0)
                isAllEvents = true;
            this.events = events;
            if (pageCounter == 1)
                notifyDataSetChanged();
            else {
                notifyItemRangeInserted(EVENTS_ON_PAGE * (pageCounter - 1), EVENTS_ON_PAGE);
            }
        }

        int getIdOfEvent(int position) {
            return events.get(position).getId();
        }

        @NonNull
        @Override
        public FeedFragment.FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new FeedFragment.FeedViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.feed_elem, parent, false));
        }


        @Override
        public void onBindViewHolder(@NonNull FeedFragment.FeedViewHolder holder, int position) {

            Event event = events.get(position);
            holder.title.setText(event.getTitle());
            holder.description.setText(Html.fromHtml(event.getDescription()));
            if (event.getImages().size() > 0)
                Glide.with(holder.eventImage.getContext())
                        .load(event.getImages().get(0).getImageUrl())
                        .error(R.drawable.ic_image_placeholder)
                        .into(holder.eventImage);
            if (position == getItemCount() - 1 && !isAllEvents) {
                Log.d(TAG, "page " + pageCounter);
                ++pageCounter;
                loadNextPage();
            }
        }

        @Override
        public int getItemCount() {
            return events.size();
        }
    }
}
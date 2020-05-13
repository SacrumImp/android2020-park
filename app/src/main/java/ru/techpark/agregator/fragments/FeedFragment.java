package ru.techpark.agregator.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ru.techpark.agregator.FragmentNavigator;
import ru.techpark.agregator.R;
import ru.techpark.agregator.event.Event;
import ru.techpark.agregator.viewmodels.FeedViewModel;

public abstract class FeedFragment extends Fragment {
    private static final String TAG = "FeedFragment";
    private static final String SEARCH_STATE = "SEARCH_STATE";
    private static final String SEARCH_QUERY = "SEARCH_QUERY";
    private static final String PAGE = "PAGE";
    protected String searchQuery;
    FragmentNavigator navigator;
    FeedViewModel feedViewModel;
    boolean isSearch = false;
    int pageCounter = 1;
    private FeedFragment.FeedAdapter adapter;
    private boolean isAllEvents = false;
    private ProgressBar loadingProgress;
    private EditText searchField;
    private ImageButton startSearch;
    private ImageButton exitSearch;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        navigator = (FragmentNavigator) context;
    }

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
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView feed = view.findViewById(R.id.list_of_events);
        loadingProgress = view.findViewById(R.id.loading_progress);
        if (savedInstanceState == null)
            loadingProgress.setVisibility(View.VISIBLE);
        startSearch = view.findViewById(R.id.start_search);
        searchField = view.findViewById(R.id.search_query);
        exitSearch = view.findViewById(R.id.exit_search);
        if (isSearch)
            exitSearch.setVisibility(View.VISIBLE);

        exitSearch.setOnClickListener((l) -> {
            isSearch = false;
            isAllEvents = false;
            pageCounter = 1;
            loadNextPage();
            exitSearch.setVisibility(View.GONE);
            searchField.setText("");
        });

        startSearch.setOnClickListener((l) -> {
            searchQuery = searchField.getText().toString();
            if (!searchQuery.equals("")) {
                pageCounter = 1;
                isAllEvents = false;
                isSearch = true;
                exitSearch.setVisibility(View.VISIBLE);
                loadNextPage();
            }
        });

        searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchQuery = searchField.getText().toString();
                    if (!searchQuery.equals("")) {
                        pageCounter = 1;
                        isAllEvents = false;
                        isSearch = true;
                        exitSearch.setVisibility(View.VISIBLE);
                        loadNextPage();
                        handled = true;
                    }
                }
                return handled;
            }
        });
        adapter = new FeedFragment.FeedAdapter();
        feed.setAdapter(adapter);
        feed.setLayoutManager(new LinearLayoutManager(view.getContext()));
        Observer<List<Event>> observer = Events -> {
            if (Events != null) {
                hideLoadingProgress();
                adapter.setEvents(Events);
            } else {
                handleObserverError();
            }
        };
        feedViewModel.getEvents()
                .observe(getViewLifecycleOwner(), observer);
    }

    abstract void handleObserverError();

    abstract void getFromAdapter(int id);

    void hideLoadingProgress() {
        Log.d(TAG, "progress hidden");
        loadingProgress.setVisibility(View.GONE);
    }

    void showLoadingProgress() {
        Log.d(TAG, "progress shown");
        loadingProgress.setVisibility(View.VISIBLE);
    }

    abstract void loadNextPage();

    class FeedViewHolder extends RecyclerView.ViewHolder {
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
                getFromAdapter(id);
                Log.d(TAG, "id " + id);
            });
        }
    }

    protected class FeedAdapter extends RecyclerView.Adapter<FeedFragment.FeedViewHolder> {

        private List<Event> events = new ArrayList<>();

        void setEvents(List<Event> events) {
            int EVENTS_ON_PAGE = 20;

            if (events.size() % EVENTS_ON_PAGE != 0)
                isAllEvents = true;
           // this.events = events;
            if (pageCounter == 1)
                notifyDataSetChanged();
            else {
                notifyItemRangeInserted(EVENTS_ON_PAGE * (pageCounter - 1), EVENTS_ON_PAGE);
            }
            // todo, нужно поле "start" и "end" класса Date в БД добавить
            // отобразим только те инвенты, которые позже текущего времени
            for (int i = 0; i < events.size(); i++){
                if (!(events.get(i).getDates().get(0).getStart_date()==null || events.get(i).getDates().get(0).getStart_time()==null) &&
                        events.get(i).getDates().get(0).getStart()*1000 > System.currentTimeMillis()) {
                    this.events.add(events.get(i));
                }
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
package ru.techpark.agregator.fragments;

import android.os.Bundle;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ru.techpark.agregator.R;
import ru.techpark.agregator.viewmodels.SingleViewModel;
import ru.techpark.agregator.event.Event;

public abstract class DetailedFragment extends Fragment {

    static final String NUM_CURR = "CURRENT";
    protected int id;
    protected Event event;
    protected TextView title;
    protected TextView description;
    protected TextView body_text;
    protected TextView price;
    TextView date_start;
    TextView time_start;
    protected TextView location;
    TextView location_label;
    protected ImageView image;
    TextView price_label;
    TextView description_label;
    TextView phone_label;
    TextView time_label;
    TextView place_title_label;
    TextView place_title;
    TextView place_address;
    TextView place_address_label;
    TextView phone;
    ProgressBar loading_progress;
    ImageButton button_go;
    FloatingActionButton likeEvent;

    SingleViewModel detailedViewModel;

    public final static String KEY_ID = "KEY_ID";
    public final static String KEY_TITLE = "KEY_TITLE";
    final static String KEY_DES = "KEY_DES";
    public final static String KEY_DATE = "KEY_DATE";
    public final static String KEY_TIME = "KEY_TIME";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detailed_event, container, false);
    }

}

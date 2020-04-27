package ru.techpark.agregator;

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

import ru.techpark.agregator.event.Event;

public abstract class DetailedFragment extends Fragment {

    protected static final String NUM_CURR = "CURRENT";
    protected int id;
    protected Event event;
    protected TextView title;
    protected TextView description;
    protected TextView body_text;
    protected TextView price;
    protected TextView date_start;
    protected TextView time_start;
    protected TextView location;
    protected TextView location_label;
    protected ImageView image;
    protected TextView price_label;
    protected TextView description_label;
    protected TextView phone_label;
    protected TextView time_label;
    protected TextView place_title_label;
    protected TextView place_title;
    protected TextView place_address;
    protected TextView place_address_label;
    protected TextView phone;
    protected ProgressBar loading_progress;
    protected ImageButton button_go;

    SingleViewModel detailedViewModel;

    public final static String KEY_ID = "KEY_ID";
    public final static String KEY_TITLE = "KEY_TITLE";
    public final static String KEY_DES = "KEY_DES";
    public final static String KEY_DATE = "KEY_DATE";
    public final static String KEY_TIME = "KEY_TIME";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detailed_event, container, false);
    }

}

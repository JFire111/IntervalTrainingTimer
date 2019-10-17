package com.vinapp.intervaltrainingtimer;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FragmentSavedTimers extends Fragment implements MainActivity.DataProviderSavedTimers {

    private ArrayList<Training> trainings;
    private Training training;
    private final String SAVED_TIMERS_SETTINGS = "SAVED_TIMERS_SETTINGS";
    int numberOfSavedTimer = 0;
    private SavedTimersAdapter savedTimersAdapter;
    private static final String TAG = "FRAGMENT SAVED LOG";
    private ListView listView;
    private SharedPreferences savedTimerSettingsSharedPreferences;

    public FragmentSavedTimers() {
        // Required empty public constructor
    }

    private View createFooter() {
        View footer = getLayoutInflater().inflate(R.layout.saved_timers_list_footer, null);
        return footer;
    }

    private void saveInSharedPreferences() {
        SharedPreferences.Editor editor = savedTimerSettingsSharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(trainings);
        Log.i(TAG, json);
        editor.putString(SAVED_TIMERS_SETTINGS, json);
        editor.commit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_timers, container, false);

        savedTimersAdapter = new SavedTimersAdapter(getContext(), trainings);
        listView = (ListView) view.findViewById(R.id.savedTimersListView);
        listView.addFooterView(createFooter());
        listView.setAdapter(savedTimersAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                savedTimersAdapter.selectItem(position);
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        savedTimerSettingsSharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        trainings = new ArrayList<Training>();
        Gson gson = new Gson();
        if (savedTimerSettingsSharedPreferences.contains(SAVED_TIMERS_SETTINGS)){
            String json = savedTimerSettingsSharedPreferences.getString(SAVED_TIMERS_SETTINGS, "");
            Type listType = new TypeToken<ArrayList<Training>>(){}.getType();
            this.trainings = gson.fromJson(json, listType);
        }
    }

    @Override
    public void saveTimer(Training training) {
        if (training != null) {
            this.trainings.add(training);
        }
    }

    @Override
    public Training loadTimer() {
        training = savedTimersAdapter.getSelectedItem();
        return training;
    }

    @Override
    public void deleteTimer() {
        training = savedTimersAdapter.getSelectedItem();
        trainings.remove(training);
        saveInSharedPreferences();
    }

    @Override
    public void setTimerName(Training training, String name) {
        int timerPosition = trainings.indexOf(training);
        trainings.get(timerPosition).setTrainingName(name);
        saveInSharedPreferences();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}

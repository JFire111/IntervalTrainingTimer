package com.vinapp.intervaltrainingtimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout mainMenuLayout;

    private TabLayout tabLayout;
    private TabItem oneExerciseTabItem;
    private TabItem someExerciseTabItem;
    private TabItem savedTimersTabItem;
    private ViewPager viewPager;
    private SectionPageAdapter sectionPageAdapter;

    private Toolbar toolbar;

    private int position;
    private final int ONE_EXERCISE_FRAGMENT_ID = 0;
    private final int SOME_EXERCISES_FRAGMENT_ID = 1;
    private final int SAVED_TIMERS_FRAGMENT_ID = 2;

    private SharedPreferences savedTimerSettingsSharedPreferences;
    private int numberOfSavedTimers = 0;
    private final String SAVED_TIMERS_COUNTER = "SAVED_TIMERS_COUNTER";
    private String savedTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainMenuLayout = findViewById(R.id.mainLayout);

        tabLayout = findViewById(R.id.tabLayout);
        oneExerciseTabItem = findViewById(R.id.oneExerciseTabItem);
        someExerciseTabItem = findViewById(R.id.someExerciseTabItem);
        savedTimersTabItem = findViewById(R.id.savedTimersTabItem);
        viewPager = findViewById(R.id.viewPager);

        savedTimerSettingsSharedPreferences = getPreferences(MODE_PRIVATE);

        sectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(sectionPageAdapter);

        toolbar = findViewById(R.id.toolbarMain);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.settingsItem:
                    default:
                        break;
                }
                return true;
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    public void onStartButtonClick(View view) {

        int currentTabID = position;
        ZeroValueAlertDialogFragment zeroValueAlertDialogFragment = new ZeroValueAlertDialogFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (currentTabID) {
            case ONE_EXERCISE_FRAGMENT_ID: {
                DataProviderOneExercise dataProviderOneExercise = (DataProviderOneExercise) sectionPageAdapter.getItem(viewPager.getCurrentItem());
                int numberOfRounds = dataProviderOneExercise.getNumberOfRounds();
                int timeOfWork = dataProviderOneExercise.getTimeOfWork();
                int timeOfRestBetweenRounds = dataProviderOneExercise.getTimeOfRestBetweenRounds();
                int delay = dataProviderOneExercise.getDelay();
                boolean startFromRest = dataProviderOneExercise.getStartFromRest();

                if (numberOfRounds != 0 && timeOfWork != 0 && timeOfRestBetweenRounds != 0) {
                    Training training = new Training(numberOfRounds, timeOfWork, timeOfRestBetweenRounds, delay, startFromRest);
                    Intent intent = new Intent(MainActivity.this, ActionActivity.class);
                    intent.putExtra("training", training);
                    startActivity(intent);
                } else {
                    zeroValueAlertDialogFragment.show(fragmentManager, "dialog");
                }
            }
            break;
            case SOME_EXERCISES_FRAGMENT_ID: {
                DataProviderSomeExercises dataProviderSomeExercises = (DataProviderSomeExercises) sectionPageAdapter.getItem(viewPager.getCurrentItem());
                int numberOfRounds = dataProviderSomeExercises.getNumberOfRounds();
                int numberOfExercises = dataProviderSomeExercises.getNumberOfExercises();
                int timeOfWork = dataProviderSomeExercises.getTimeOfWork();
                int timeOfRestBetweenRounds = dataProviderSomeExercises.getTimeOfRestBetweenRounds();
                int timeOfRestBetweenExercises = dataProviderSomeExercises.getTimeOfRestBetweenExercises();
                int delay = dataProviderSomeExercises.getDelay();

                if (numberOfRounds != 0 && numberOfExercises !=0 && timeOfWork != 0 && timeOfRestBetweenExercises !=0 && timeOfRestBetweenRounds != 0) {
                    Training training = new Training(numberOfRounds, numberOfExercises, timeOfWork, timeOfRestBetweenExercises, timeOfRestBetweenRounds, delay);
                    Intent intent = new Intent(MainActivity.this, ActionActivity.class);
                    intent.putExtra("training", training);
                    startActivity(intent);
                } else {
                    zeroValueAlertDialogFragment.show(fragmentManager, "dialog");
                }
            }
            break;
            case SAVED_TIMERS_FRAGMENT_ID:
                break;
            default:
                break;
        }
    }

    public interface DataProviderOneExercise {
        int getNumberOfRounds();
        int getTimeOfWork();
        int getTimeOfRestBetweenRounds();
        int getDelay();
        boolean getStartFromRest();
    }

    public interface DataProviderSomeExercises {
        int getNumberOfRounds();
        int getNumberOfExercises();
        int getTimeOfWork();
        int getTimeOfRestBetweenRounds();
        int getTimeOfRestBetweenExercises();
        int getDelay();
    }

    public void onSaveTimerSettingsButtonClick(View view) {
        int currentTabID = position;
        ZeroValueAlertDialogFragment zeroValueAlertDialogFragment = new ZeroValueAlertDialogFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (currentTabID) {
            case ONE_EXERCISE_FRAGMENT_ID: {
                DataProviderOneExercise dataProviderOneExercise = (DataProviderOneExercise) sectionPageAdapter.getItem(viewPager.getCurrentItem());
                int numberOfRounds = dataProviderOneExercise.getNumberOfRounds();
                int timeOfWork = dataProviderOneExercise.getTimeOfWork();
                int timeOfRestBetweenRounds = dataProviderOneExercise.getTimeOfRestBetweenRounds();
                int delay = dataProviderOneExercise.getDelay();
                boolean startFromRest = dataProviderOneExercise.getStartFromRest();

                if (numberOfRounds != 0 && timeOfWork != 0 && timeOfRestBetweenRounds != 0) {
                    Training training = new Training(numberOfRounds, timeOfWork, timeOfRestBetweenRounds, delay, startFromRest);

                    numberOfSavedTimers++;
                    savedTimer = "SAVED_TIMER_" + numberOfSavedTimers;
                    SharedPreferences.Editor editor = savedTimerSettingsSharedPreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(training);
                    editor.putInt(SAVED_TIMERS_COUNTER, numberOfSavedTimers);
                    editor.putString(savedTimer, json);
                    editor.commit();
                } else {
                    zeroValueAlertDialogFragment.show(fragmentManager, "dialog");
                }
            }
            break;
            case SOME_EXERCISES_FRAGMENT_ID: {
                DataProviderSomeExercises dataProviderSomeExercises = (DataProviderSomeExercises) sectionPageAdapter.getItem(viewPager.getCurrentItem());
                int numberOfRounds = dataProviderSomeExercises.getNumberOfRounds();
                int numberOfExercises = dataProviderSomeExercises.getNumberOfExercises();
                int timeOfWork = dataProviderSomeExercises.getTimeOfWork();
                int timeOfRestBetweenRounds = dataProviderSomeExercises.getTimeOfRestBetweenRounds();
                int timeOfRestBetweenExercises = dataProviderSomeExercises.getTimeOfRestBetweenExercises();
                int delay = dataProviderSomeExercises.getDelay();

                if (numberOfRounds != 0 && numberOfExercises !=0 && timeOfWork != 0 && timeOfRestBetweenExercises !=0 && timeOfRestBetweenRounds != 0) {
                    Training training = new Training(numberOfRounds, numberOfExercises, timeOfWork, timeOfRestBetweenExercises, timeOfRestBetweenRounds, delay);

                    numberOfSavedTimers++;
                    savedTimer = "SAVED_TIMER_" + numberOfSavedTimers;
                    SharedPreferences.Editor editor = savedTimerSettingsSharedPreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(training);
                    editor.putInt(SAVED_TIMERS_COUNTER, numberOfSavedTimers);
                    editor.putString(savedTimer, json);
                    editor.commit();
                } else {
                    zeroValueAlertDialogFragment.show(fragmentManager, "dialog");
                }
            }
            break;
            case SAVED_TIMERS_FRAGMENT_ID:
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (savedTimerSettingsSharedPreferences.contains(SAVED_TIMERS_COUNTER)) {
            numberOfSavedTimers = savedTimerSettingsSharedPreferences.getInt(SAVED_TIMERS_COUNTER, 0);
        }
    }
}

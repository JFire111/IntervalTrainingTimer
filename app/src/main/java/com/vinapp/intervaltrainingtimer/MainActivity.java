package com.vinapp.intervaltrainingtimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout mainMenuLayout;

    private TabLayout tabLayout;
    private TabItem oneExerciseTabItem;
    private TabItem someExerciseTabItem;
    private TabItem savedTimersTabItem;
    private ViewPager viewPager;
    private SectionPageAdapter sectionPageAdapter;
    private AppCompatButton saveButton;
    private AppCompatButton loadButton;
    private AppCompatButton deleteButton;
    private FloatingActionButton startButton;

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

        saveButton = findViewById(R.id.saveTimerSettingsButton);
        loadButton = findViewById(R.id.loadTimerSettingsButton);
        deleteButton = findViewById(R.id.deleteTimerSettingsButton);
        startButton = findViewById(R.id.startButton);
        loadButton.setVisibility(View.GONE); //set invisibility on start page
        deleteButton.setVisibility(View.GONE); //set invisibility on start page
        final Animation hideButton = AnimationUtils.loadAnimation(this, R.anim.scale_hide);
        final Animation showButton = AnimationUtils.loadAnimation(this, R.anim.scale_show);

        savedTimerSettingsSharedPreferences = getPreferences(MODE_PRIVATE);

        sectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(sectionPageAdapter);
        viewPager.setOffscreenPageLimit(2);

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
                if (position == SAVED_TIMERS_FRAGMENT_ID) {
                    saveButton.setVisibility(View.GONE);
                    deleteButton.setVisibility(View.VISIBLE);
                    loadButton.setVisibility(View.VISIBLE);
                } else {
                    saveButton.setVisibility(View.VISIBLE);
                    deleteButton.setVisibility(View.GONE);
                    loadButton.setVisibility(View.GONE);
                }
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

    public void onSaveTimerSettingsButtonClick(View view) {
        int currentTabID = position;
        ZeroValueAlertDialogFragment zeroValueAlertDialogFragment = new ZeroValueAlertDialogFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        DataProviderSavedTimers dataProviderSavedTimers = (DataProviderSavedTimers) sectionPageAdapter.getItem(SAVED_TIMERS_FRAGMENT_ID);
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
                    /*numberOfSavedTimers++;
                    savedTimer = "SAVED_TIMER_" + numberOfSavedTimers;
                    SharedPreferences.Editor editor = savedTimerSettingsSharedPreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(training);
                    editor.putInt(SAVED_TIMERS_COUNTER, numberOfSavedTimers);
                    editor.putString(savedTimer, json);
                    editor.commit();
                    */
                    dataProviderSavedTimers.saveTimer(training);
                    updateFragment(SAVED_TIMERS_FRAGMENT_ID);
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
                    /*numberOfSavedTimers++;
                    savedTimer = "SAVED_TIMER_" + numberOfSavedTimers;
                    SharedPreferences.Editor editor = savedTimerSettingsSharedPreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(training);
                    editor.putInt(SAVED_TIMERS_COUNTER, numberOfSavedTimers);
                    editor.putString(savedTimer, json);
                    editor.commit();
                    */
                    dataProviderSavedTimers.saveTimer(training);
                    updateFragment(SAVED_TIMERS_FRAGMENT_ID);
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

    public void onDeleteTimerSettingsButtonClick(View view) {

    }

    public void onLoadTimerSettingsButtonClick(View view) {

    }

    private void updateFragment(int fragmentID){
        Fragment fragment = sectionPageAdapter.getItem(fragmentID);
        getSupportFragmentManager().beginTransaction()
                .detach(fragment)
                .attach(fragment)
                .commit();
    }



    /*
    @Override
    protected void onResume() {
        super.onResume();

        if (savedTimerSettingsSharedPreferences.contains(SAVED_TIMERS_COUNTER)) {
            numberOfSavedTimers = savedTimerSettingsSharedPreferences.getInt(SAVED_TIMERS_COUNTER, 0);
        }
    }

     */
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

    public interface DataProviderSavedTimers {
        void saveTimer(Training training);
        Training loadTimer();
        void deleteTimer();
    }
}

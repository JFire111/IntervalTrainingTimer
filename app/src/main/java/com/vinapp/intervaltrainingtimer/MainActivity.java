package com.vinapp.intervaltrainingtimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

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

        // TODO: Refactor this trash!...
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
                    // TODO: Change alert dialog, make custom dialog
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
                DataProviderSavedTimers dataProviderSavedTimers = (DataProviderSavedTimers) sectionPageAdapter.getItem(SAVED_TIMERS_FRAGMENT_ID);
                Training training = dataProviderSavedTimers.loadTimer();
                if (training != null) {
                    Intent intent = new Intent(MainActivity.this, ActionActivity.class);
                    intent.putExtra("training", training);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    // TODO: And refactor this...
    public void onSaveTimerSettingsButtonClick(View view) {
        int currentTabID = position;
        ZeroValueAlertDialogFragment zeroValueAlertDialogFragment = new ZeroValueAlertDialogFragment();
        SaveTimerDialogFragment saveTimerDialogFragment = new SaveTimerDialogFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        final DataProviderSavedTimers dataProviderSavedTimers = (DataProviderSavedTimers) sectionPageAdapter.getItem(SAVED_TIMERS_FRAGMENT_ID);
        switch (currentTabID) {
            case ONE_EXERCISE_FRAGMENT_ID: {
                DataProviderOneExercise dataProviderOneExercise = (DataProviderOneExercise) sectionPageAdapter.getItem(ONE_EXERCISE_FRAGMENT_ID);
                int numberOfRounds = dataProviderOneExercise.getNumberOfRounds();
                int timeOfWork = dataProviderOneExercise.getTimeOfWork();
                int timeOfRestBetweenRounds = dataProviderOneExercise.getTimeOfRestBetweenRounds();
                int delay = dataProviderOneExercise.getDelay();
                boolean startFromRest = dataProviderOneExercise.getStartFromRest();

                if (numberOfRounds != 0 && timeOfWork != 0 && timeOfRestBetweenRounds != 0) {
                    Training training = new Training(numberOfRounds, timeOfWork, timeOfRestBetweenRounds, delay, startFromRest);
                    dataProviderSavedTimers.saveTimer(training); //Save timer settings before set name of timer
                    saveTimerDialogFragment.show(fragmentManager, "saveTimerDialog");
                    saveTimerDialogFragment.setTraining(training); //Set training in dialog fragment for find this instance of Training in FragmentSavedTimers for set timer name
                    saveTimerDialogFragment.setDataProvider(dataProviderSavedTimers); //Set dataprovider in dialog fragment for send timer name to FragmentSavedTimers
                    updateFragment(SAVED_TIMERS_FRAGMENT_ID);
                } else {
                    zeroValueAlertDialogFragment.show(fragmentManager, "dialog");
                }
            }
            break;
            case SOME_EXERCISES_FRAGMENT_ID: {
                DataProviderSomeExercises dataProviderSomeExercises = (DataProviderSomeExercises) sectionPageAdapter.getItem(SOME_EXERCISES_FRAGMENT_ID);
                int numberOfRounds = dataProviderSomeExercises.getNumberOfRounds();
                int numberOfExercises = dataProviderSomeExercises.getNumberOfExercises();
                int timeOfWork = dataProviderSomeExercises.getTimeOfWork();
                int timeOfRestBetweenRounds = dataProviderSomeExercises.getTimeOfRestBetweenRounds();
                int timeOfRestBetweenExercises = dataProviderSomeExercises.getTimeOfRestBetweenExercises();
                int delay = dataProviderSomeExercises.getDelay();

                if (numberOfRounds != 0 && numberOfExercises !=0 && timeOfWork != 0 && timeOfRestBetweenExercises !=0 && timeOfRestBetweenRounds != 0) {
                    Training training = new Training(numberOfRounds, numberOfExercises, timeOfWork, timeOfRestBetweenExercises, timeOfRestBetweenRounds, delay);
                    dataProviderSavedTimers.saveTimer(training); //Save timer settings before set name of timer
                    saveTimerDialogFragment.show(fragmentManager, "saveTimerDialog");
                    saveTimerDialogFragment.setTraining(training); //Set training in dialog fragment for find this instance of Training in FragmentSavedTimers for set timer name
                    saveTimerDialogFragment.setDataProvider(dataProviderSavedTimers); //Set dataprovider in dialog fragment for send timer name to FragmentSavedTimers
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

    // TODO: And refactor this too...
    public void onLoadTimerSettingsButtonClick(View view) {
        DataProviderSavedTimers dataProviderSavedTimers = (DataProviderSavedTimers) sectionPageAdapter.getItem(SAVED_TIMERS_FRAGMENT_ID);
        Training training = dataProviderSavedTimers.loadTimer();
        final int ONE_EXERCISE_TRAINING = ONE_EXERCISE_FRAGMENT_ID;
        final int SOME_EXERCISES_TRAINING = SOME_EXERCISES_FRAGMENT_ID;
        if (training != null) {
            switch (training.getTrainingType()) {
                case ONE_EXERCISE_TRAINING:
                    DataProviderOneExercise dataProviderOneExercise = (DataProviderOneExercise) sectionPageAdapter.getItem(ONE_EXERCISE_FRAGMENT_ID);
                    dataProviderOneExercise.setNumberOfRounds(training.getNumberOfRounds());
                    dataProviderOneExercise.setTimeOfWork(training.getTimeOfWork());
                    dataProviderOneExercise.setTimeOfRestBetweenRounds(training.getTimeOfRestBetweenRounds());
                    dataProviderOneExercise.setStartFromRest(training.isStartFromRest());
                    dataProviderOneExercise.setDelay(training.getDelay());
                    viewPager.setCurrentItem(ONE_EXERCISE_FRAGMENT_ID);
                    break;
                case SOME_EXERCISES_TRAINING:
                    DataProviderSomeExercises dataProviderSomeExercises = (DataProviderSomeExercises) sectionPageAdapter.getItem(SOME_EXERCISES_FRAGMENT_ID);
                    dataProviderSomeExercises.setNumberOfRounds(training.getNumberOfRounds());
                    dataProviderSomeExercises.setNumberOfExercises(training.getNumberOfExercises());
                    dataProviderSomeExercises.setTimeOfWork(training.getTimeOfWork());
                    dataProviderSomeExercises.setTimeOfRestBetweenRounds(training.getTimeOfRestBetweenRounds());
                    dataProviderSomeExercises.setTimeOfRestBetweenExercises(training.getTimeOfRestBetweenExercises());
                    dataProviderSomeExercises.setDelay(training.getDelay());
                    viewPager.setCurrentItem(SOME_EXERCISES_FRAGMENT_ID);
                    break;
                default:
                    break;
            }
        }
    }

    public void onDeleteTimerSettingsButtonClick(View view) {
        DataProviderSavedTimers dataProviderSavedTimers = (DataProviderSavedTimers) sectionPageAdapter.getItem(SAVED_TIMERS_FRAGMENT_ID);
        // TODO: Add delete dialog
        dataProviderSavedTimers.deleteTimer();
        updateFragment(SAVED_TIMERS_FRAGMENT_ID);
    }

    private void updateFragment(int fragmentID){
        Fragment fragment = sectionPageAdapter.getItem(fragmentID);
        getSupportFragmentManager().beginTransaction()
                .detach(fragment)
                .attach(fragment)
                .commit();
    }

    //Interfaces for sending data between fragments and activity
    public interface DataProviderOneExercise {
        int getNumberOfRounds();
        int getTimeOfWork();
        int getTimeOfRestBetweenRounds();
        int getDelay();
        boolean getStartFromRest();
        void setNumberOfRounds(int numberOfRounds);
        void setTimeOfWork(int timeOfWork);
        void setTimeOfRestBetweenRounds(int timeOfRestBetweenRounds);
        void setDelay(int delay);
        void setStartFromRest(boolean startFromRest);
    }

    public interface DataProviderSomeExercises {
        int getNumberOfRounds();
        int getNumberOfExercises();
        int getTimeOfWork();
        int getTimeOfRestBetweenRounds();
        int getTimeOfRestBetweenExercises();
        int getDelay();
        void setNumberOfRounds(int numberOfRounds);
        void setNumberOfExercises(int numberOfExercises);
        void setTimeOfWork(int timeOfWork);
        void setTimeOfRestBetweenRounds(int timeOfRestBetweenRounds);
        void setTimeOfRestBetweenExercises(int timeOfRestBetweenExercises);
        void setDelay(int delay);
    }

    public interface DataProviderSavedTimers {
        void saveTimer(Training training);
        void setTimerName(Training training, String name);
        Training loadTimer();
        void deleteTimer();
    }
}

package com.vinapp.intervaltrainingtimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SectionPageAdapter sectionPageAdapter;
    private AppCompatButton saveButton;
    private AppCompatButton loadButton;
    private AppCompatButton deleteButton;
    private FloatingActionButton startButton;
    private Toolbar toolbar;

    private int position;
    public final int ONE_EXERCISE_FRAGMENT_ID = 0;
    public final int SOME_EXERCISES_FRAGMENT_ID = 1;
    public final int SAVED_TIMERS_FRAGMENT_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        saveButton = findViewById(R.id.saveTimerSettingsButton);
        loadButton = findViewById(R.id.loadTimerSettingsButton);
        deleteButton = findViewById(R.id.deleteTimerSettingsButton);
        startButton = findViewById(R.id.startButton);
        loadButton.setVisibility(View.GONE); //set invisibility on start page
        deleteButton.setVisibility(View.GONE); //set invisibility on start page

        //set default settings values
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);

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
                        showSettings();
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

                    updateFragment(SAVED_TIMERS_FRAGMENT_ID); //Update saved timers fragment to display new saved timers;
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


    private void showSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void onStartButtonClick(View view) {

        int currentTabID = position;
        DialogFragmentZeroValueAlert dialogFragmentZeroValueAlert = new DialogFragmentZeroValueAlert();
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
                    dialogFragmentZeroValueAlert.show(fragmentManager, "dialog");
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
                    dialogFragmentZeroValueAlert.show(fragmentManager, "dialog");
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
        DialogFragmentZeroValueAlert dialogFragmentZeroValueAlert = new DialogFragmentZeroValueAlert();
        DialogFragmentSaveTimer dialogFragmentSaveTimer = new DialogFragmentSaveTimer();
        DialogFragmentSavedTimerLimit dialogFragmentSavedTimerLimit = new DialogFragmentSavedTimerLimit();
        FragmentManager fragmentManager = getSupportFragmentManager();
        DataProviderSavedTimers dataProviderSavedTimers = (DataProviderSavedTimers) sectionPageAdapter.getItem(SAVED_TIMERS_FRAGMENT_ID);
        switch (currentTabID) {
            case ONE_EXERCISE_FRAGMENT_ID: {
                DataProviderOneExercise dataProviderOneExercise = (DataProviderOneExercise) sectionPageAdapter.getItem(ONE_EXERCISE_FRAGMENT_ID);
                int numberOfRounds = dataProviderOneExercise.getNumberOfRounds();
                int timeOfWork = dataProviderOneExercise.getTimeOfWork();
                int timeOfRestBetweenRounds = dataProviderOneExercise.getTimeOfRestBetweenRounds();
                int delay = dataProviderOneExercise.getDelay();
                boolean startFromRest = dataProviderOneExercise.getStartFromRest();

                if (numberOfRounds != 0 && timeOfWork != 0 && timeOfRestBetweenRounds != 0) {
                    if (dataProviderSavedTimers.getTrainingsSize() < 3) {
                        Training training = new Training(numberOfRounds, timeOfWork, timeOfRestBetweenRounds, delay, startFromRest);
                        dialogFragmentSaveTimer.setTraining(training); //Set training in dialog fragment for save this instance of Training in FragmentSavedTimers
                        dialogFragmentSaveTimer.show(fragmentManager, "saveTimerDialog");
                    } else {
                        dialogFragmentSavedTimerLimit.show(fragmentManager, "saveTimerLimitDialog");
                    }
                } else {
                    dialogFragmentZeroValueAlert.show(fragmentManager, "dialog");
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
                    if (dataProviderSavedTimers.getTrainingsSize() < 3) {
                        Training training = new Training(numberOfRounds, numberOfExercises, timeOfWork, timeOfRestBetweenExercises, timeOfRestBetweenRounds, delay);
                        dialogFragmentSaveTimer.setTraining(training); //Set training in dialog fragment for save this instance of Training in FragmentSavedTimers
                        dialogFragmentSaveTimer.show(fragmentManager, "saveTimerDialog");
                    } else {
                        dialogFragmentSavedTimerLimit.show(fragmentManager, "saveTimerLimitDialog");
                    }
                } else {
                    dialogFragmentZeroValueAlert.show(fragmentManager, "zeroValueAlertDialog");
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
        DialogFragmentDeleteTimer dialogFragmentDeleteTimer = new DialogFragmentDeleteTimer();
        if (dataProviderSavedTimers.isTimerSelect()) {
            dialogFragmentDeleteTimer.show(getSupportFragmentManager(), "deleteTimerDialog");
            dialogFragmentDeleteTimer.setDataProvider(dataProviderSavedTimers);
        }
    }

    public void saveTimer(Training training) {
        final DataProviderSavedTimers dataProviderSavedTimers = (DataProviderSavedTimers) sectionPageAdapter.getItem(SAVED_TIMERS_FRAGMENT_ID);
        dataProviderSavedTimers.saveTimer(training);
    }

    public void deleteTimer() {
        DataProviderSavedTimers dataProviderSavedTimers = (DataProviderSavedTimers) sectionPageAdapter.getItem(SAVED_TIMERS_FRAGMENT_ID);
        dataProviderSavedTimers.deleteTimer();
    }

    public void updateFragment(int fragmentID) {
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
        boolean isTimerSelect();
        Training loadTimer();
        void deleteTimer();
        int getTrainingsSize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startButton.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        startButton.hide();
    }
}

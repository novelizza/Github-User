package com.example.user.bfaa_submission2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class ReminderActivity extends AppCompatActivity {

    private Switch swReminder;
    private ReminderReceiver reminderReceiver;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        swReminder = findViewById(R.id.switch_reminder);

        reminderReceiver = new ReminderReceiver();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int notificationDaily = sharedPreferences.getInt("Reminder", 0);
        if (notificationDaily == 1){
            swReminder.setChecked(true);
        }else {
            swReminder.setChecked(false);
        }

        swReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    String repeatTime = "09:00";
                    String repeatMessage = "Ayo kembali cari user github favoritmu!";
                    reminderReceiver.setRepeatingAlarm(ReminderActivity.this, repeatTime, repeatMessage);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("Reminder",1);
                    editor.commit();
                    Toast.makeText(ReminderActivity.this, "Reminder Active", Toast.LENGTH_SHORT).show();
                }else {
                    reminderReceiver.cancelAlarm(ReminderActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("Reminder",0);
                    editor.commit();
                    Toast.makeText(ReminderActivity.this, "Reminder Not Active", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}
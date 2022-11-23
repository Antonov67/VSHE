package org.hse.timetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View button = findViewById(R.id.button);
        View button2 = findViewById(R.id.button2);
        View button3 = findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showStudent();
                toast("Расписание для студентов");
            };
        });

        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showTeacher();
                toast("Расписание для преподавателей");
            };
        });

        button3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showSettings();
                toast("Настройки");
            };
        });
    }

    private void toast(String text)
    {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void showStudent()
    {
        Intent intent  = new Intent(this, StudentActivity.class);
        startActivity(intent);
    }

    public void showTeacher()
    {
        Intent intent  = new Intent(this, TeacherActivity.class);
        startActivity(intent);
    }

    public void showSettings()
    {
        Intent intent  = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}
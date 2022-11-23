package org.hse.timetable;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class StudentActivity extends BaseActivity
{
    private TextView status, subject, cabinet, corp, teacher, time;

    private Spinner spinner;

    String[] course = {"БИ","ПИ","Э","УБ","И","Ю","МБ","РИС","ИЯ"};
    int[] year = {19,20,21,22};
    int[] groupNumber = {1,2,3};
    List<StudentActivity.Group> mock = new ArrayList<>();


    void enumeration (String[] course, int[] year, int[] groupNumber)
    {
        int count = 1;
        for(String s: course)
        {
            for(int i: year)
            {
                for(int j: groupNumber)
                {
                    mock.add(new StudentActivity.Group(count, assembly(s,i,j)));
                    count++;
                }
            }
        }
    }

    String assembly(String course, int year, int groupNumber)
    {
        return course + "-" + year + "-" + groupNumber;
    }




  static class Group
    {
        private Integer id;
        private String name;

        public Group(Integer id, String name)
        {
            this.id = id;
            this.name = name;
        }

        public Integer getId()
        {
            return id;
        }

        public void setId(Integer id)
        {
            this.id = id;
        }

        public String toString()
        {
            return name;
        }

        public String getName()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }
    }

//    @Override
//    protected void initTime ()
//    {
//        currentTime = new Date();
//        Locale locale = new Locale("ru");
//        SimpleDateFormat simpleDate = new SimpleDateFormat("HH:mm", locale);
//        time.setText(simpleDate.format(currentTime));
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        enumeration(course,year,groupNumber);
        time = findViewById(R.id.id_tv_time_Student);

       spinner = findViewById(R.id.spinnerGroup);


        List<Group> groups = new ArrayList<>();
        initGroupList(groups, mock);

        ArrayAdapter<?> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, groups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            //@Override
            public void onItemSelected(AdapterView<?> parent, View itemSelected,
                                       int selectedItemPosition, long selectedId)
            {
                Object item = adapter.getItem(selectedItemPosition);
                Log.d(TAG, "itemSelected" + item);
            }

            //@Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                //
            }
        });

        time = findViewById(R.id.id_tv_time_Student);
        initTime();

        status = findViewById(R.id.textView4);
        subject = findViewById(R.id.textView5);
        cabinet = findViewById(R.id.textView6);
        corp = findViewById(R.id.textView7);
        teacher = findViewById(R.id.textView8);

        initData();

        View button3 = findViewById(R.id.button3);
        View button4 = findViewById(R.id.button4);

        button3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //showStudent();
                toast("Расписание на день");
            };
        });

        button4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //showTeacher();
                toast("Расписание на неделю");
            };
        });

        View scheduleDay = findViewById(R.id.button3);
        scheduleDay.setOnClickListener(v -> {showSchedule(ScheduleType.DAY);});
        View scheduleWeek = findViewById(R.id.button4);
        scheduleWeek.setOnClickListener(v -> {showSchedule(ScheduleType.WEEK);});
    }



    private void initGroupList(List<StudentActivity.Group> groups, List<StudentActivity.Group> list)
    {
        for(StudentActivity.Group group: list)
        {
            groups.add(new StudentActivity.Group(group.getId(), group.getName()));
        }
    }

    @Override
    protected void initTime()
    {
        currentTime = new Date();
        currentDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm, EEEE", Locale.forLanguageTag("ru"));
        java.util.TimeZone tz = TimeZone.getTimeZone(("GMT+3")); //часовой пояс Москвы
        simpleDateFormat.setTimeZone(tz);
        String[] dateFormatSplit = simpleDateFormat.format(currentTime).split(" ");
        String timeText = "Сегодня: " + dateFormatSplit[0] + " " + dateFormatSplit[1].substring(0,1).toUpperCase() + dateFormatSplit[1].substring(1);
        time.setText(timeText);
    }

    private  void initData()
    {
        status.setText("Нет пар");

        subject.setText("Дисциплина");
        cabinet.setText("Кабинет");
        corp.setText("Корпус");
        teacher.setText("Преподаватель");
    }

    private void toast(String text)
    {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }


    private void showSchedule(ScheduleType type){
        Object selectedItem = spinner.getSelectedItem();
        if (!(selectedItem instanceof Group)) {
            return;
        }
        showScheduleImpl(ScheduleMode.STUDENT, type, (Group) selectedItem, currentDate);
    }

    protected void showScheduleImpl(ScheduleMode mode, ScheduleType type, Group group, Date date){
        Intent intent = new Intent(this, ScheduleActivity.class);
        intent.putExtra(ScheduleActivity.ARG_ID, group.getId());
        intent.putExtra(ScheduleActivity.ARG_TYPE, type);
        intent.putExtra(ScheduleActivity.ARG_MODE, mode);
        intent.putExtra(ScheduleActivity.ARG_DATE, date);
        startActivity(intent);
    }

}
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


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TeacherActivity extends BaseActivity{

    private TextView status, subject, cabinet, corp, teacher, time;

    protected MainViewModel mainViewModel;

    ArrayAdapter adapter;

    private Spinner spinner;
    Date currenttime;
    StudentActivity.Group [] teachers = {
            new StudentActivity.Group(1, "Алова Надежда Владимировна"),
            new StudentActivity.Group(2, "Андрианов Игорь Владимирович"),
            new StudentActivity.Group(3, "Бартов Олег Борисович"),
            new StudentActivity.Group(4, "Брейман Александр Давидович"),
            new StudentActivity.Group(5, "Викентьева Ольга Леонидовна"),
            new StudentActivity.Group(6, "Гордеев Юрий Матвеевич"),
            new StudentActivity.Group(7, "Грабарь Вадим Валерьевич"),
            new StudentActivity.Group(8, "Гуревич Дмитрий Алексеевич"),
            new StudentActivity.Group(9, "Гущин Валерий Рафаилович"),
            new StudentActivity.Group(10, "Еремин Евгений Александрович"),
            new StudentActivity.Group(11, "Замятина Елена Борисовна"),
            new StudentActivity.Group(12, "Ильин Иван Вадимович"),
            new StudentActivity.Group(13, "Каменских Алексей Александрович"),
            new StudentActivity.Group(14, "Кузнецов Денис Борисович"),
            new StudentActivity.Group(15, "Куприн Валентин Павлович"),
            new StudentActivity.Group(16, "Кушев Вадим Олегович"),
            new StudentActivity.Group(17, "Кычкин Алексей Владимирович"),
            new StudentActivity.Group(18, "Ланин Вячеслав Владимирович"),
            new StudentActivity.Group(19, "Лобанов Сергей Васильевич"),
            new StudentActivity.Group(20, "Логинова Валерия Валерьевна"),
            new StudentActivity.Group(21, "Лядова Людмила Николаевна"),
            new StudentActivity.Group(22, "Марквирер Владлена Дмитриевна"),
            new StudentActivity.Group(23, "Микрюков Максим Юрьевич"),
            new StudentActivity.Group(24, "Минина Ксения Александровна"),
            new StudentActivity.Group(25, "Мицюк Алексей Александрович"),
            new StudentActivity.Group(26, "Морозенко Владимир Викторович"),
            new StudentActivity.Group(27, "Морозова Ирина Сергеевна"),
            new StudentActivity.Group(28, "Мухин Олег Игоревич"),
            new StudentActivity.Group(29, "Назаров Алексей Николаевич"),
            new StudentActivity.Group(30, "Носкова Екатерина Николаевна"),
            new StudentActivity.Group(31, "Плаксин Михаил Александрович"),
            new StudentActivity.Group(32, "Плотникова Евгения Григорьевна"),
            new StudentActivity.Group(33, "Радионова Марина Владимировна"),
            new StudentActivity.Group(34, "Ремизова Вероника Германовна"),
            new StudentActivity.Group(35, "Рустамханова Гульшат Ильдаровна"),
            new StudentActivity.Group(36, "Ряпина Наталья Евгеньевна"),
            new StudentActivity.Group(37, "Сахипова Марина Станиславовна"),
            new StudentActivity.Group(38, "Селезнев Кирилл Андреевич"),
            new StudentActivity.Group(39, "Скорнякова Анна Юрьевна"),
            new StudentActivity.Group(40, "Смирнова Надежда Анатольевна"),
            new StudentActivity.Group(41, "Собянин Кирилл Валентинович"),
            new StudentActivity.Group(42, "Соколов Евгений Андреевич"),
            new StudentActivity.Group(43, "Суворов Александр Олегович"),
            new StudentActivity.Group(44, "Туляков Дмитрий Сергеевич"),
            new StudentActivity.Group(45, "Филиппов Александр Евгеньевич"),
            new StudentActivity.Group(46, "Фролова Наталья Владимировна"),
            new StudentActivity.Group(47, "Чащухин Александр Валерьевич"),
            new StudentActivity.Group(48, "Черемных Елена Леонидовна"),
            new StudentActivity.Group(49, "Чистогов Максим Дмитриевич"),
            new StudentActivity.Group(50, "Шевелева Марина Сергеевна"),
            new StudentActivity.Group(51, "Шестакова Лидия Валентиновна"),
            new StudentActivity.Group(52, "Шиловских Петр Александрович"),
            new StudentActivity.Group(53, "Шучалова Юлия Сергеевна"),
            new StudentActivity.Group(54, "Яборов Андрей Владимирович"),
    };
    //String[] _teacher = {"Преподаватель"};
    //int[] _teacher_number = {1,2,3,4,5,6,7,8,9,10};
    //int[] empty = {1,2};
    //List<StudentActivity.Group> mock = new ArrayList<>();


    //String assembly(String _teacher, int _teacher_number, int empty)
    //{
    //    return _teacher + "-" + _teacher_number + "-" + empty;
    //}


     @Override
    protected void initTime()
    {
        currentTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm, EEEE", Locale.forLanguageTag("ru"));
        java.util.TimeZone tz = TimeZone.getTimeZone(("GMT+3")); //часовой пояс Москвы
        simpleDateFormat.setTimeZone(tz);
        String[] dateFormatSplit = simpleDateFormat.format(currentTime).split(" ");
        String timeText = "Сегодня: " + dateFormatSplit[0] + " " + dateFormatSplit[1].substring(0,1).toUpperCase() + dateFormatSplit[1].substring(1);
        time.setText(timeText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        //enumeration(_teacher,_teacher_number,empty);

        mainViewModel = new ViewModelProvider( this).get(MainViewModel.class);

        spinner = findViewById(R.id.spinnerTeacher);

        List<StudentActivity.Group> groups = new ArrayList<>();
        //List<StudentActivity.Group> groups2 = initGroupList(groups, teachers);
        initGroupList(groups);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, groups);
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

        time = findViewById(R.id.id_tv_time_Teacher);
        initTime();

        status = findViewById(R.id.textView44);
        subject = findViewById(R.id.textView55);
        cabinet = findViewById(R.id.textView66);
        corp = findViewById(R.id.textView77);
        teacher = findViewById(R.id.textView88);

        initData();

        View button33 = findViewById(R.id.button33);
        View button44 = findViewById(R.id.button44);

        button33.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //showStudent();
                toast("Расписание на день");
            };
        });

        button44.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //showTeacher();
                toast("Расписание на неделю");
            };
        });
        View scheduleDay = findViewById(R.id.button33);
        scheduleDay.setOnClickListener(v -> {showSchedule(ScheduleType.DAY);});
        View scheduleWeek = findViewById(R.id.button44);
        scheduleWeek.setOnClickListener(v -> {showSchedule(ScheduleType.WEEK);});


    }



//    private List<StudentActivity.Group> initGroupList(List<StudentActivity.Group> groups, StudentActivity.Group[] list)
//    {
//        for(StudentActivity.Group group: list)
//        {
//            groups.add(new StudentActivity.Group(group.getId(), group.getName()));
//        }
//        return groups;
//    }



    private void initGroupList(List<StudentActivity.Group> groups){
         mainViewModel.getTEachers().observe(this, new Observer<List<TeacherEntity>>() {
             @Override
             public void onChanged(List<TeacherEntity> list) {
                 List<StudentActivity.Group> groupsResult = new ArrayList<>();
                 for (TeacherEntity teacher: list) {
                     groupsResult.add(new StudentActivity.Group(teacher.id, teacher.fio));
                 }
                 adapter.clear();
                 adapter.addAll(groupsResult);
             }
         });
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
        if (!(selectedItem instanceof StudentActivity.Group)) {
            return;
        }
        showScheduleImpl(ScheduleMode.TEACHER, type, (StudentActivity.Group) selectedItem, currentTime);
    }

    protected void showScheduleImpl(ScheduleMode mode, ScheduleType type, StudentActivity.Group group, Date date){
        Intent intent = new Intent(this, ScheduleActivity.class);
        intent.putExtra(ScheduleActivity.ARG_ID, group.getId());
        intent.putExtra(ScheduleActivity.ARG_TYPE, type);
        intent.putExtra(ScheduleActivity.ARG_MODE, mode);
        intent.putExtra(ScheduleActivity.ARG_DATE, date);
        startActivity(intent);
    }

}
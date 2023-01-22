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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class TeacherActivity extends BaseActivity{

    private TextView status, subject, cabinet, corp, teacher, time;

    protected MainViewModel mainViewModel;

    ArrayAdapter adapter;

    private Spinner spinner;

    private int teacherId;

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


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        mainViewModel = new ViewModelProvider( this).get(MainViewModel.class);

        spinner = findViewById(R.id.spinnerTeacher);

        List<StudentActivity.Group> groups = new ArrayList<>();

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

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                try {
                    //запрос по дате 01.01.2021 и ФИО преподавателя
                    showTime(simpleDateFormat.parse("2021-02-01 16:00"),String.valueOf(item));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            //@Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                //
            }
        });

        time = findViewById(R.id.id_tv_time_Teacher);


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

        //получение времени от сервера и вывод его в активити
        mainViewModel.getTime().observe(this, new Observer<Date>() {
            @Override
            public void onChanged(Date date) {
                currentTime = date;
                Log.d(TAG, "BaseActivity,DateFromServer: " + date);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                time.setText(simpleDateFormat.format(date));
            }
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

    //формирование списка преподавателей с помощью liveData
    private void initGroupList(List<StudentActivity.Group> groups){
         mainViewModel.getTeachers().observe(this, new Observer<List<TeacherEntity>>() {
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


    private void initData(){
        initDataFromTimeTable(null);
    }

    //метод записи данных о текущес занятии в форму
    private void initDataFromTimeTable(TimeTableWithTeacherEntity timeTableTeacherEntity){
        if (timeTableTeacherEntity == null){
            status.setText("Нет пар");
            subject.setText("Дисциплина");
            cabinet.setText("Кабинет");
            corp.setText("Корпус");
            teacher.setText("Преподаватель");
            return;
        }
        status.setText("Идет пара");
        TimeTableEntity timeTableEntity = timeTableTeacherEntity.timeTableEntity;

        subject.setText(timeTableEntity.subjName);
        cabinet.setText(timeTableEntity.cabinet);
        corp.setText(timeTableEntity.corp);
        teacher.setText(timeTableTeacherEntity.teacherEntity.fio);
    }

    @Override
    protected void showTime(Date dateTime, String... groupNameOrTeacher) {
        //  super.showTime(dateTime);


        // получим ФИО учителя по его ID
        mainViewModel.getTeacherByFIO(groupNameOrTeacher[0]).observe(TeacherActivity.this, new Observer<List<TeacherEntity>>() {
            @Override
            public void onChanged(List<TeacherEntity> list) {
                for (TeacherEntity teacher: list) {
                    teacherId = teacher.id;
                }
                //запрос в БД по id учителя и дате
                mainViewModel.getTimetableTeacherByDateAndTeacherID(dateTime,teacherId).observe(TeacherActivity.this, new Observer<List<TimeTableWithTeacherEntity>>() {
                    @Override
                    public void onChanged(List<TimeTableWithTeacherEntity> list) {
                        if (list.size() == 0){
                            initDataFromTimeTable(null);
                        }else{
                            for (TimeTableWithTeacherEntity teacherEntity: list) {
                                Log.d(TAG,teacherEntity.timeTableEntity.subjName + " " + teacherEntity.timeTableEntity.teacherId);

                                initDataFromTimeTable(teacherEntity);
                            }
                        }

                    }
                });


            }
        });
    }

    private void toast(String text)
    {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    private void showSchedule(ScheduleType type){
        Object selectedItem = spinner.getSelectedItem();
        showScheduleImpl(ScheduleMode.TEACHER, type, selectedItem + "", currentTime);
    }

    protected void showScheduleImpl(ScheduleMode mode, ScheduleType type, String teacher, Date date){
        Intent intent = new Intent(this, ScheduleActivity.class);
        intent.putExtra(ScheduleActivity.ARG_ID, teacher);
        intent.putExtra(ScheduleActivity.ARG_TYPE, type);
        intent.putExtra(ScheduleActivity.ARG_MODE, mode);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        try {
            intent.putExtra(ScheduleActivity.ARG_DATE, simpleDateFormat.parse("2021-02-01 16:00"));
        }catch (Exception e){

        }
        startActivity(intent);
    }


}
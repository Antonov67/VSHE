


package org.hse.timetable;


import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_NAME = "name";

    SharedPreferences mSettings;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;

    private static String fileName = "file.png";

    // объекты для работы с датчиками
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener sensorEventListener;

    TextView lightSensorValue;
    ListView sensorValueList; //список для отображения данных датчика

    EditText editText;
    Button photoButton, saveButton;

    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Start");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



        lightSensorValue = findViewById(R.id.light_value);
        sensorValueList = findViewById(R.id.list_sensor);
        imageView = findViewById(R.id.imageView);
        editText = findViewById(R.id.editText);
        photoButton = findViewById(R.id.button_photo);
        saveButton = findViewById(R.id.button_save);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        if(mSettings.contains(APP_PREFERENCES_NAME)) {
            editText.setText(mSettings.getString(APP_PREFERENCES_NAME, ""));
        }

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        if (sensorManager != null)
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);// если все хорошо, то инициализируем сенсор


        // создаем адаптер и привязываем наш listVew к нему
        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        List<String> list = new ArrayList<>();
        for (Sensor sensor: deviceSensors) {
            list.add(sensor.getName()  + " тип сенсора:" + sensor.getType());
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        sensorValueList.setAdapter(adapter);

        sensorEventListener = new SensorEventListener(){
            @Override
            public void onSensorChanged(SensorEvent event) {

                if(event.sensor.getType() == Sensor.TYPE_LIGHT){
                    lightSensorValue.setText("LIGHT: " + event.values[0]);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // TODO Auto-generated method stub
            }
        };




        if(lightSensor != null){
            lightSensorValue.setText("Sensor.TYPE_LIGHT Available");
            sensorManager.registerListener(
                    sensorEventListener,
                    lightSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString(APP_PREFERENCES_NAME, name);
                editor.apply();
            }
        });


        ReadFile();
        photoButton.setOnClickListener(v -> {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            }
            else
            {
                //      File photoFile = new File(getDir("", Context.MODE_PRIVATE), fileName);
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                //      Uri photoURI = FileProvider.getUriForFile(this,BuildConfig.APPLICATION_ID + ".provider",photoFile)   ;

                //        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });


    }

    public void ReadFile(){
        File file = new File(getDir("", Context.MODE_PRIVATE), fileName);
        if(file.exists()){
            Bitmap b = BitmapFactory.decodeFile(file.getPath());
            imageView.setImageBitmap(b);
        }
        else{
            Toast.makeText(getApplicationContext(), "not", Toast.LENGTH_LONG).show();
        }
    }

//
//    private void dispatchTakePictureIntent() {
//
//        Log.d(TAG, "start");
//
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//
//                photoFile = createImageFile();
//            } catch (Exception ex) {
//                // Error occurred while creating the File
//                Log.e(TAG, "IOException");
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                 Uri photoURI = FileProvider.getUriForFile(this,BuildConfig.APPLICATION_ID + ".provider",photoFile)   ;
//                 cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                try {
//                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
//                }catch (Exception e){
//                    Log.d("777","ошибка запуска камеры");
//                }
//
//
//            }
//        }
//
//    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            try {

                File file = new File(getDir("", Context.MODE_PRIVATE), fileName);
                file.createNewFile();
                OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                os.close();
                ReadFile();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }


}
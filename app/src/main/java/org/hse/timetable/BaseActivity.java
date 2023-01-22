package org.hse.timetable;




import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.widget.TextView;



import java.util.Date;



public abstract class BaseActivity extends FragmentActivity {

    enum ScheduleType{
        DAY,
        WEEK
    }

    enum ScheduleMode{
        STUDENT,
        TEACHER
    }

    protected MainViewModel mainViewModel;

    private final static String TAG = "BaseActivity";
  //  public static final String URL = "https://api.ipgeolocation.io/ipgeo?apiKey=b03018f75ed94023a005637878ec0977";


    protected TextView time;
    protected Date currentTime;


   // private OkHttpClient client = new OkHttpClient();

//    public void getTime(){
//        Request request = new Request.Builder().url(URL).build();
//        Call call = client.newCall(request);
//        call.enqueue(new Callback() {
//            public void onResponse (Call call, Response response) throws IOException {
//                parseResponse(response);
//            }
//
//            public void onFailure(Call call, IOException e){
//                Log.e(TAG, "getTime", e);
//            }
//        });
 //   }



    protected void showTime(Date dateTime, String... groupNameOrTeacher){

    }





    //    private void parseResponse(Response response){
//        Gson gson = new Gson();
//        ResponseBody body = response.body();
//        try{
//            if (body == null){
//                return;
//            }
//            String string = body.string();
//            Log.d(TAG, string);
//            TimeResponse timeResponse = gson.fromJson(string, TimeResponse.class);
//            String currentTimeVal = timeResponse.getTimeZone().getCurrentTime();
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
//
//            Date dateTime = simpleDateFormat.parse(currentTimeVal);
//
//            // run on UI thread
//            runOnUiThread(() -> showTime(dateTime));
//        } catch (Exception e) {
//            Log.e(TAG, "", e);
//        }
//    }
}
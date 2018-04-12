package ztml.dev.ngokhacbac.exampleweatherbounedservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MyService extends Service {
    private static String LOG_TAG = "WeatherService";

    public MyService() {
    }

    // Lưu trữ dữ liệu thời tiết.
    private static final Map<String, String> weatherData = new HashMap<String, String>();

    private final IBinder binder = new LocalWeatherBinder();

    public class LocalWeatherBinder extends Binder {

        public MyService getService() {
            return MyService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return this.binder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i(LOG_TAG, "onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(LOG_TAG, "onUnbind");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    // Trả về thông tin thời tiết ứng với địa điểm của ngày hiện tại.
    public String getWeatherToday(String location) {
        Date now = new Date();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        String dayString = df.format(now);
        String keyLocAndDay = location + "$" + dayString;

        String weather = weatherData.get(keyLocAndDay);
        //
        if (weather != null) {
            return weather;
        }

        //
        String[] weathers = new String[]{"Rainy", "Hot", "Cool", "Warm", "Snowy"};

        // Giá trị ngẫu nhiên từ 0 tới 4
        int i = new Random().nextInt(5);

        weather = weathers[i];
        weatherData.put(keyLocAndDay, weather);
        //
        return weather;
    }

}

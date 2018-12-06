package de.davidbielenberg.locationbasedservice;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_FINE_LOCATION =  1;

    //Widgets
    Button startButton;
    Button clearButton;
    Button stopButton;

    TextView textView;

    //Koordinaten
    double latitude;
    double longitude;

    //Map
    double x;
    double y;

    MapView mapView;

    Drawable drawable;


    LocationManager locationManager;

    LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {

            Log.i("Location Changed", "Location has changed");

            latitude = location.getLatitude();
            longitude = location.getLongitude();
            textView.setText(textView.getText().toString() + "long: " + longitude + ", lat: " + latitude + System.getProperty ("line.separator"));



            mapView.setPosition(longitude, latitude);
            mapView.invalidate();

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

            Log.i("Status Changed", "Provider: " + provider + ". Status: " + status );
            textView.setText("status changed");
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.view);
        startButton = findViewById(R.id.startBtn);
        clearButton = findViewById(R.id.clearBtn);
        stopButton = findViewById(R.id.stopBtn);

        drawable = getResources().getDrawable(R.drawable.fhfl_map);

        mapView = findViewById(R.id.mapView);

        textView.setMovementMethod(new ScrollingMovementMethod());

        try {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);

            }

        }catch(Exception exc){
            Log.i("Exception", "Permnission excception! " + exc.toString());
        }


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startButtonClicked();
            }
        });

        //LocationManager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);



    }

    private void startButtonClicked() {


        Log.i("Start", "Start Button Clicked");
        textView.setText("gestartet" + System.getProperty ("line.separator"));

        try {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {



            }

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        }catch(Exception exc){
            Log.i("Exception", "Permnission excception! " + exc.toString());
        }





    }


    //Button Methoden:


    public void stopTracking(View v) {


        textView.setText(textView.getText().toString() + "long: " + longitude + ", lat: " + latitude+ System.getProperty ("line.separator") +  "Stopped!" + System.getProperty ("line.separator") );

        locationManager.removeUpdates(locationListener);

    }

    public void clearTracking(View v) {

        textView.setText("Position cleared");

    }

}
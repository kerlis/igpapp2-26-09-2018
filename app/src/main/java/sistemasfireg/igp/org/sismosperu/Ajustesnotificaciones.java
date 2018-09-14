package sistemasfireg.igp.org.sismosperu;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.iid.InstanceID;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class Ajustesnotificaciones extends AppCompatActivity {
    static final int READ_BLOCK_SIZE = 100;
    String oldtoken;
    String json;
    File file;
    String nuevotoken;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajustescontainer);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Double latitud = Double.parseDouble("-12.0519782");
        Double longitud = Double.parseDouble("-76.9793685");

        Button button5 = (Button) findViewById(R.id.obtenerdireccion);
        button5.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //  Toast.makeText(Ajustesnotificaciones.this, "dsad", Toast.LENGTH_LONG).show();
                direccion();
            }
        });

        /*
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
           // List<Address> addresses = geocoder.getFromLocation(latitud, longitud, 1);
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            Toast.makeText(getBaseContext(),"token original" + addresses,Toast.LENGTH_SHORT).show();
            Log.d(TAG, "DIRECCION: " + addresses );


        } catch (IOException e) {
            e.printStackTrace();
        }
*/

        /*


        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton2);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                }
            }
        });

*/
        /////  generartokennuevo.setOnClickListener();
        //  Button generartokennuevo = (Button) findViewById(R.id.generarnuevaid);


/*






        Button button2 = (Button) findViewById(R.id.vercapturado2);
        button2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //  Toast.makeText(Ajustesnotificaciones.this, "dsad", Toast.LENGTH_LONG).show();
               vervalor();
            }
        });

        */


        Button button = (Button) findViewById(R.id.generarnuevaid);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //  Toast.makeText(Ajustesnotificaciones.this, "dsad", Toast.LENGTH_LONG).show();
                obtenertoken();
            }
        });


    }


    private void direccion() {



        Geocoder geocoder;
        String bestProvider;
        List<Address> user = null;
        double lat;
        double lng;

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        bestProvider = lm.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = lm.getLastKnownLocation(bestProvider);

      //  if (location == null){
      //      Toast.makeText(getBaseContext(),"Location Not found",Toast.LENGTH_LONG).show();
     //   }else{
            geocoder = new Geocoder(this);
            try {
                user = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                lat=(double)user.get(0).getLatitude();
                lng=(double)user.get(0).getLongitude();
                System.out.println(" DDD lat: " +lat+",  longitude: "+lng);
                Log.d(TAG, " DDD lat: " +lat+",  longitude: "+lng);

              //  List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                Toast.makeText(getBaseContext(),"token original" + user,Toast.LENGTH_SHORT).show();



            }catch (Exception e) {
                e.printStackTrace();
            }
       // }

/*
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            // List<Address> addresses = geocoder.getFromLocation(latitud, longitud, 1);
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            Toast.makeText(getBaseContext(),"token original" + addresses,Toast.LENGTH_SHORT).show();
            Log.d(TAG, "DIRECCION: " + addresses );


        } catch (IOException e) {
            e.printStackTrace();
        }
*/
    }



    private void obtenertoken() {


      //  try
       // {
            // Check for current token
            String originalToken = getTokenFromPrefs();
            Log.d(TAG, "Token before deletion: " + originalToken);


            //boolean deleted = file.delete();
            if (originalToken != ""){
                Toast.makeText(getBaseContext(),"token original" + originalToken,Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getBaseContext(),"no hay token original ",Toast.LENGTH_SHORT).show();
            }



        /*    // Resets Instance ID and revokes all tokens.
            FirebaseInstanceId.getInstance().deleteInstanceId();

            // Clear current saved token
            saveTokenToPrefs("");

            // Check for success of empty token
            String tokenCheck = getTokenFromPrefs();
            Log.d(TAG, "Token deleted. Proof: " + tokenCheck);

            // Now manually call onTokenRefresh()
            Log.d(TAG, "Getting new token");
            String nuevotoken =FirebaseInstanceId.getInstance().getToken();



        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
*/


    }


    private void saveTokenToPrefs(String _token)
    {
        // Access Shared Preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        // Save to SharedPreferences
        editor.putString("registration_id", _token);
        editor.apply();
    }

    private String getTokenFromPrefs()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString("registration_id", null);
    }




}


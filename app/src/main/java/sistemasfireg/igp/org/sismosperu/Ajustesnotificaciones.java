package sistemasfireg.igp.org.sismosperu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringTokenizer;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static android.content.ContentValues.TAG;

public class Ajustesnotificaciones extends AppCompatActivity {
    static final int READ_BLOCK_SIZE = 100;
    String oldtoken;
    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajustescontainer);


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


      /////  generartokennuevo.setOnClickListener();
      //  Button generartokennuevo = (Button) findViewById(R.id.generarnuevaid);


        Button button = (Button) findViewById(R.id.generarnuevaid);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
              //  Toast.makeText(Ajustesnotificaciones.this, "dsad", Toast.LENGTH_LONG).show();
                obtenertoken();
            }
        });




        Button button2 = (Button) findViewById(R.id.vercapturado2);
        button2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //  Toast.makeText(Ajustesnotificaciones.this, "dsad", Toast.LENGTH_LONG).show();
                vervalor();
            }
        });






    }






    private void obtenertoken() {
        try {
            InstanceID.getInstance(getApplicationContext()).deleteInstanceID();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String nuevotoken = FirebaseInstanceId.getInstance(FirebaseApp.initializeApp(getApplicationContext())).getToken();
        Toast.makeText(Ajustesnotificaciones.this, nuevotoken, Toast.LENGTH_LONG).show();
        String file2 = "eltoken";
        try {
            FileInputStream fileIn = openFileInput(file2);
            InputStreamReader InputRead= new InputStreamReader(fileIn);
            char[] inputBuffer= new char[READ_BLOCK_SIZE];
            int charRead;
            charRead=InputRead.read(inputBuffer);
             oldtoken = String.copyValueOf(inputBuffer,0,charRead);
           // k +=readstring;
            InputRead.close();
          Toast.makeText(getBaseContext(),"token antiguo : " + oldtoken + "\n " +  "nuevo token:" +  nuevotoken ,Toast.LENGTH_SHORT).show();
          enviar(oldtoken, nuevotoken);
          //  StringTokenizer st = new StringTokenizer(k.toString(), ",");
           // String ajustes = st.nextToken();
           // tipo = st.nextToken();
           // Log.d(TAG, "valor: json: " + k);
            //Toast.makeText(getBaseContext(),"data 2 : " + tipo,Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



    public void enviar(String valor1, String valor2){
        Toast.makeText(getBaseContext(), "velores: "+ valor1 + "\n" + valor2, Toast.LENGTH_SHORT).show();

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token",valor1)
                .add("Token",valor2)
                .build();
        Request request = new Request.Builder()
                .url("http://intranet.igp.gob.pe/UtAnMI0laYWBo4/index.php?NewToken="+valor2+"&"+"OldToken="+valor1)
                // .url("http://intranet.igp.gob.pe/test_erlis/test.php?Token="+nuevotoken)
                //.url("http://intranet.igp.gob.pe/MI0laYWBo4/")
                //http://intranet.igp.gob.pe/MI0laYWBo4/
                .post(body)
                .build();

          consulta("http://intranet.igp.gob.pe/UtAnMI0laYWBo4/index.php?NewToken="+valor2+"&"+"OldToken="+valor1);


        // consulta("http://arteypixel.com/envio_notificaciones/register.php?Token="+token);
        // consulta("http://intranet.igp.gob.pe/test_erlis/test.php?Token="+token);
        // consulta("http://intranet.igp.gob.pe/AnMI0laYWBo4/index.php?Token="+token);
        // guardartoken(token);

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void consulta(String urlString)  {
        Toast.makeText(getBaseContext(), "valor capturado : "+ urlString, Toast.LENGTH_SHORT).show();
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = null;
            BufferedReader bufferedReader = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            json = bufferedReader.readLine();
            //  Toast.makeText(getApplicationContext(), json, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "vslor json: " + json);

            ver2(json);
            urlConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //   }
        //  });
    }


    public void ver2(String valor) {



        String Message5 = valor;
        String file_namex = "capturado";
        try {
            FileOutputStream fileOutputStream = openFileOutput(file_namex, MODE_PRIVATE);
            fileOutputStream.write(Message5.getBytes());
            FirebaseMessaging.getInstance().subscribeToTopic(Message5);


            //  fileOutputStream.write(Message7.getBytes());
            fileOutputStream.close();
            //  Toast.makeText(getApplicationContext(), "Configurado", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


       // Toast.makeText(getBaseContext(), "valor capturado : "+ valor, Toast.LENGTH_SHORT).show();
    }




    public void vervalor() {
        Toast.makeText(getBaseContext(),"data : " + "el dato",Toast.LENGTH_SHORT).show();

        String file2 = "capturado";
        try {
            FileInputStream fileIn = openFileInput(file2);
            InputStreamReader InputRead = new InputStreamReader(fileIn);
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            int charRead;
            charRead = InputRead.read(inputBuffer);
            String readstring = String.copyValueOf(inputBuffer, 0, charRead);
            //k2 += readstring;
           // InputRead.close();
           Toast.makeText(getBaseContext(),"data : " + readstring,Toast.LENGTH_SHORT).show();
           // StringTokenizer st = new StringTokenizer(k.toString(), ",");
            //String ajustes = st.nextToken();
            //tipo = st.nextToken();

      //      Log.d(TAG, "valor: json: " + k);


            //Toast.makeText(getBaseContext(),"data 2 : " + tipo,Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}


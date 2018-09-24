package sistemasfireg.igp.org.sismosperu;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SplashScreen extends Activity {
    String elvalor = "a";
    String elvalor2 = "bb";
    String Message;
    String ko,ajustes,tipo;
    Integer r,s;
    private static DatabaseReference mFirebaseDatabase;
    private static FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(database == null) {
            database = FirebaseDatabase.getInstance();
            Firebase.getDefaultConfig().setPersistenceEnabled(true);
            database.setPersistenceEnabled(true);
        }
        mFirebaseDatabase = database.getReference("message");
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        mFirebaseDatabase.keepSynced(true);
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent7 = new Intent(SplashScreen.this,Ultimosismo3.class);
                    startActivity(intent7);
                }
            }
        };
        timerThread.start();
    }

        @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}
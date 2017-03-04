package apps.seelocationclicked;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    public static final String GETDATA = "http://54.169.86.227:8080/binbasket/getLocationDetails.jsp?USERNAME=demo_user";

    static TextView textView1,textView2,textView3;

    public static String latitude1,latitude2,latitude3,longitude1,longitude2,longitude3,address1,address2,address3,date1,time1,date2,time2,date3,time3;

    public static Context context;


    private static ProgressDialog pDialog;

    static MainActivity mInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        mInstance = this;
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
    }

    public void getData(View view){
        if(AppStatus.getInstance(getApplicationContext()).isOnline()){
            showProgressDialog("Please wait",this);
            FetchString fetchString = new FetchString();
            fetchString.execute(GETDATA);
        }else {
            Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_SHORT).show();
        }

    }

    public void spliteJsonString(String jSonString){
        Object obj = JSONValue.parse(jSonString);
        try {
            Log.d(TAG, "Spliting JSon String");
            JSONObject jsonObject = (JSONObject)obj;
            Long temp1= (Long)jsonObject.get("MAX");
            int max=((int)(long)temp1);
            for (int i=max-1;i>max-6;i--){

            }
            date1 = (String)jsonObject.get("DATE"+Integer.toString(max-1));
            date2 = (String)jsonObject.get("DATE"+Integer.toString(max-2));
            date3 = (String)jsonObject.get("DATE"+Integer.toString(max-3));
            time1 = (String)jsonObject.get("TIME"+Integer.toString(max-1));
            time2 = (String)jsonObject.get("TIME"+Integer.toString(max-2));
            time3 = (String)jsonObject.get("TIME"+Integer.toString(max-3));
            latitude1 = (String)jsonObject.get("LATITUDE"+Integer.toString(max-1));
            latitude2 = (String)jsonObject.get("LATITUDE"+Integer.toString(max-2));
            latitude3 = (String)jsonObject.get("LATITUDE"+Integer.toString(max-3));
            longitude1 = (String)jsonObject.get("LONGITUDE"+Integer.toString(max-1));
            longitude2 = (String)jsonObject.get("LONGITUDE"+Integer.toString(max-2));
            longitude3 = (String)jsonObject.get("LONGITUDE"+Integer.toString(max-3));
            Log.d(TAG, latitude1+"  "+latitude2+"  "+latitude3+",  "+longitude1+"  "+longitude2+"  "+longitude3);
            FetchLocationDetailes fetchLocationDetailes = new FetchLocationDetailes(latitude1,longitude1,1);
            fetchLocationDetailes.execute();
        }catch (Exception e){
            Log.d(TAG, "SpliteJsonString "+e.getMessage());
        }
    }

    public void showProgressDialog(String text, Context context){
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(text);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }
    public static void cancelProgressDialog(){
        pDialog.dismiss();
    }

    public static void setText(){
        textView1.setText(address1+"\n"+latitude1+"  "+longitude1+"\n"+date1+" "+time1+"\n");
        textView2.setText(address2+"\n"+latitude2+"  "+longitude2+"\n"+date2+" "+time2+"\n");
        textView3.setText(address3+"\n"+latitude3+"  "+longitude3+" "+date3+" "+time3);
    }
}

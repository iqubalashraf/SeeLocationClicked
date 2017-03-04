package apps.seelocationclicked;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by ashrafiqubal on 07/07/16.
 */
public class FetchString extends AsyncTask<String, Void, Boolean> {
    private static final String TAG = "FetchString";
    private static String receivedJsonString=null;
    @Override
    protected Boolean doInBackground(String... params) {
        // TODO Auto-generated method stub
        Boolean prepared;
        try {
            String str;
            HttpClient myClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(params[0]);
            HttpResponse myResponse = myClient.execute(get);
            BufferedReader br = new BufferedReader(new InputStreamReader(myResponse.getEntity().getContent()));
            while ((str = br.readLine()) != null) {
                receivedJsonString = str;
                Log.d(TAG, str);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.context,"Error: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        prepared = true;
        return prepared;
    }
    @Override
    protected void onPostExecute(Boolean result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        Log.d(TAG, "onPostExecution");
        MainActivity.mInstance.spliteJsonString(receivedJsonString);
    }
}
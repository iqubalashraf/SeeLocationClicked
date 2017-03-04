package apps.seelocationclicked;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;
import java.util.List;
import java.util.Locale;

/**
 * Created by ashrafiqubal on 06/01/17.
 */

public class FetchLocationDetailes extends AsyncTask<String, Void, Boolean> {
    private static final String TAG = "FetchLocationDetailes";
    String latitude ,longitude;
    int i;
    public FetchLocationDetailes(String latitudeTemp,String longitudeTemp,int iTemp){
        latitude = latitudeTemp;
        longitude = longitudeTemp;
        i=iTemp;
    }
    @Override
    protected Boolean doInBackground(String... params) {
        // TODO Auto-generated method stub
        Boolean prepared;
        try {
            Geocoder gcd = new Geocoder(MainActivity.context, Locale.getDefault());
            try{
                List<Address> addresses = gcd.getFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), 1);
                if(i==1){
                    MainActivity.mInstance.address1 = addresses.get(0).getAddressLine(0)+", "+addresses.get(0).getAddressLine(1)+"  "+addresses.get(0).getLocality()+"  "+addresses.get(0).getAdminArea();
                }
                if(i==2){
                    MainActivity.mInstance.address2 = addresses.get(0).getAddressLine(0)+", "+addresses.get(0).getAddressLine(1)+"  "+addresses.get(0).getLocality()+"  "+addresses.get(0).getAdminArea();
                }
                if(i==3){
                    MainActivity.mInstance.address3 = addresses.get(0).getAddressLine(0)+", "+addresses.get(0).getAddressLine(1)+"  "+addresses.get(0).getLocality()+"  "+addresses.get(0).getAdminArea();
                }
            }catch (Exception e){
                Log.d(TAG,"Error: "+e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Error: Do in background "+e.getMessage());
            //Toast.makeText(context,"Error: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        prepared = true;
        return prepared;
    }
    @Override
    protected void onPostExecute(Boolean result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        Log.d(TAG, "onPostExecution");
        i++;
        if(i==2){
            FetchLocationDetailes fetchLocationDetailes = new FetchLocationDetailes(MainActivity.latitude2,MainActivity.longitude2,i);
            fetchLocationDetailes.execute();
        }
        if(i==3){
            FetchLocationDetailes fetchLocationDetailes = new FetchLocationDetailes(MainActivity.latitude3,MainActivity.longitude3,i);
            fetchLocationDetailes.execute();
        }
        if(i>3){
            MainActivity.setText();
            MainActivity.cancelProgressDialog();
        }
    }
}

package com.nic.samathuvapuram.activity;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.VolleyError;
import com.nic.samathuvapuram.R;
import com.nic.samathuvapuram.adapter.HouseListAdapter;
import com.nic.samathuvapuram.adapter.SamathuvapuramDetailsAdapter;
import com.nic.samathuvapuram.api.Api;
import com.nic.samathuvapuram.api.ApiService;
import com.nic.samathuvapuram.api.ServerResponse;
import com.nic.samathuvapuram.constant.AppConstant;
import com.nic.samathuvapuram.dataBase.DBHelper;
import com.nic.samathuvapuram.dataBase.dbData;
import com.nic.samathuvapuram.databinding.HouseListScreenBinding;
import com.nic.samathuvapuram.model.ModelClass;
import com.nic.samathuvapuram.session.PrefManager;
import com.nic.samathuvapuram.support.ProgressHUD;
import com.nic.samathuvapuram.utils.UrlGenerator;
import com.nic.samathuvapuram.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HouseListActivity extends AppCompatActivity implements Api.ServerResponseListener, View.OnClickListener {
    private HouseListScreenBinding houseListScreenBinding;
    private PrefManager prefManager;
    public dbData dbData = new dbData(this);
    public static DBHelper dbHelper;
    public static SQLiteDatabase db;
    Handler myHandler = new Handler();
    private ArrayList<ModelClass> houselist = new ArrayList<>();
    private ProgressHUD progressHUD;
    HouseListAdapter houseListAdapter;
    String samathuvapuram_id;
    

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        houseListScreenBinding = DataBindingUtil.setContentView(this, R.layout.house_list_screen);
        houseListScreenBinding.setActivity(this);
        prefManager = new PrefManager(this);
        try {
            dbHelper = new DBHelper(this);
            db = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        samathuvapuram_id = getIntent().getStringExtra("samathuvapuram_id");
        houseListScreenBinding.recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        houseListScreenBinding.recycler.setItemAnimator(new DefaultItemAnimator());
        houseListScreenBinding.recycler.setHasFixedSize(true);
        houseListScreenBinding.recycler.setNestedScrollingEnabled(false);
        houseListScreenBinding.recycler.setFocusable(false);

//        house_list();

        //Sample
        JSONObject jsonObject = new JSONObject();
        String json = "{\"STATUS\":\"OK\",\"RESPONSE\":\"OK\",\"JSON_DATA\":[{\"id\":1,\"tax\":\"Property tax\"},{\"id\":2,\"tax\":\"Water Charges\"},{\"id\":3,\"tax\":\"Professional Tax\"},{\"id\":4,\"tax\":\"Non Tax\"},{\"id\":5,\"tax\":\"Trade License \"}]}";
        try {  jsonObject = new JSONObject(json); } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + json + "\""); }


        try {
            if (jsonObject.getString("STATUS").equalsIgnoreCase("OK") && jsonObject.getString("RESPONSE").equalsIgnoreCase("OK")) {
                new Insert_house_list().execute(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new fetch_House_list().execute();

    }


    public class fetch_House_list extends AsyncTask<Void, Void,
            ArrayList<ModelClass>> {
        @Override
        protected ArrayList<ModelClass> doInBackground(Void... params) {
            dbData.open();
            houselist = new ArrayList<>();
            houselist = dbData.getAllhouselist();
            Log.d("houselist_COUNT", String.valueOf(houselist.size()));
            return houselist;
        }

        @Override
        protected void onPostExecute(ArrayList<ModelClass> houselist) {
            super.onPostExecute(houselist);
            if(!Utils.isOnline()) {
                if (houselist.size() == 0) {
                    Utils.showAlert(HouseListActivity.this, "No Data Available in Local Database. Please, Turn On mobile data");
                }
            }
            if (houselist.size() > 0) {
                houseListScreenBinding.recycler.setVisibility(View.VISIBLE);
                houseListScreenBinding.notFoundTv.setVisibility(View.GONE);
                houseListAdapter = new HouseListAdapter(HouseListActivity.this, houselist,dbData);
                houseListScreenBinding.recycler.setAdapter(houseListAdapter);
            }else {
                houseListScreenBinding.recycler.setVisibility(View.GONE);
                houseListScreenBinding.notFoundTv.setVisibility(View.VISIBLE);
            }


        }
        }


    public void house_list() {
        try {
            new ApiService(this).makeJSONObjectRequest("house_list", Api.Method.POST, UrlGenerator.getMainService(), house_list_JSONParams(), "not cache", this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public JSONObject house_list_JSONParams() throws JSONException {
        String authKey = Utils.encrypt(prefManager.getUserPassKey(), getResources().getString(R.string.init_vector), house_list_JsonParams().toString());
        JSONObject dataSet = new JSONObject();
        dataSet.put(AppConstant.KEY_USER_NAME, prefManager.getUserName());
        dataSet.put(AppConstant.DATA_CONTENT, authKey);
        Log.d("house_list", "" + dataSet);
        return dataSet;
    }
    public  JSONObject house_list_JsonParams() throws JSONException {
        JSONObject dataSet = new JSONObject();
        dataSet.put(AppConstant.KEY_SERVICE_ID, "blk_bdo_samathuvapuram_list_of_houses");
        dataSet.put("samathuvapuram_id", samathuvapuram_id);
        Log.d("house_list", "" + dataSet);
        return dataSet;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
    

    @Override
    public void OnMyResponse(ServerResponse serverResponse) {

        try {
            String urlType = serverResponse.getApi();
            JSONObject responseObj = serverResponse.getJsonResponse();

            if ("house_list".equals(urlType) && responseObj != null) {
                String key = responseObj.getString(AppConstant.ENCODE_DATA);
                String responseDecryptedBlockKey = Utils.decrypt(prefManager.getUserPassKey(), key);
                JSONObject jsonObject = new JSONObject(responseDecryptedBlockKey);
                if (jsonObject.getString("STATUS").equalsIgnoreCase("OK") && jsonObject.getString("RESPONSE").equalsIgnoreCase("OK")) {
                    new Insert_house_list().execute(jsonObject);
                }else if(jsonObject.getString("STATUS").equalsIgnoreCase("OK") && jsonObject.getString("RESPONSE").equalsIgnoreCase("NO_RECORD") && jsonObject.getString("MESSAGE").equalsIgnoreCase("NO_RECORD")){
                    Utils.showAlert(this,"No Record Found!");
                }
                Log.d("Insert_house_list", "" + jsonObject);
            }
           
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void OnError(VolleyError volleyError) {

    }

    public class Insert_house_list extends AsyncTask<JSONObject, Void, Void> {
        @Override
        protected Void doInBackground(JSONObject... params) {
            if (params.length > 0) {
                dbData.open();
                dbData.deleteHOUSE_LISTable();
                JSONArray jsonArray = new JSONArray();
                try {
                    jsonArray = params[0].getJSONArray(AppConstant.JSON_DATA);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < jsonArray.length(); i++) {

                        ModelClass ListValue = new ModelClass();
                        try {
                            ListValue.setSamathuvapuram_id(jsonArray.getJSONObject(i).getInt("id"));
                            ListValue.setName(jsonArray.getJSONObject(i).getString("tax"));
                            dbData.Insert_house_list(ListValue);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                }
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }
    


}

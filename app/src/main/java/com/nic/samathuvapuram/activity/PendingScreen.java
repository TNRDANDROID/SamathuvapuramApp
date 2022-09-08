package com.nic.samathuvapuram.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.nic.samathuvapuram.R;
import com.nic.samathuvapuram.adapter.PendingScreenAdapter;
import com.nic.samathuvapuram.api.Api;
import com.nic.samathuvapuram.api.ApiService;
import com.nic.samathuvapuram.api.ServerResponse;
import com.nic.samathuvapuram.constant.AppConstant;
import com.nic.samathuvapuram.dataBase.DBHelper;
import com.nic.samathuvapuram.dataBase.dbData;
import com.nic.samathuvapuram.databinding.PendingScreenBinding;
import com.nic.samathuvapuram.model.ModelClass;
import com.nic.samathuvapuram.session.PrefManager;
import com.nic.samathuvapuram.support.ProgressHUD;
import com.nic.samathuvapuram.utils.UrlGenerator;
import com.nic.samathuvapuram.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class PendingScreen extends AppCompatActivity implements Api.ServerResponseListener {
    private PendingScreenBinding pendingScreenBinding;
    private ShimmerRecyclerView recyclerView;
    private PrefManager prefManager;
    private SQLiteDatabase db;
    public static DBHelper dbHelper;
    public com.nic.samathuvapuram.dataBase.dbData dbData = new dbData(this);
    ArrayList<ModelClass> pendingList = new ArrayList<>();
    private PendingScreenAdapter pendingScreenAdapter;
    private SearchView searchView;
    JSONObject dataset = new JSONObject();
    private ProgressHUD progressHUD;
    String type="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pendingScreenBinding = DataBindingUtil.setContentView(this, R.layout.pending_screen);
        pendingScreenBinding.setActivity(this);
        setSupportActionBar(pendingScreenBinding.toolbar);
        try {
            dbHelper = new DBHelper(this);
            db = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        prefManager = new PrefManager(this);

        pendingList = new ArrayList<>();
        pendingScreenAdapter = new PendingScreenAdapter(PendingScreen.this,pendingList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView = pendingScreenBinding.pendingListRecycler;
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        new fetchpendingtask().execute();

    }



    public class fetchpendingtask extends AsyncTask<JSONObject, Void,
            ArrayList<ModelClass>> {
        @Override
        protected ArrayList<ModelClass> doInBackground(JSONObject... params) {
            dbData.open();
            pendingList = new ArrayList<>();
            pendingList = dbData.getSavedHouseDetails();
            Log.d("PENDING_COUNT", String.valueOf(pendingList.size()));
            return pendingList;
        }

        @Override
        protected void onPostExecute(ArrayList<ModelClass> pendingList) {
            super.onPostExecute(pendingList);
            pendingScreenAdapter = new PendingScreenAdapter(PendingScreen.this,
                    pendingList);
            recyclerView.setAdapter(pendingScreenAdapter);
            recyclerView.showShimmerAdapter();
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadCards();
                }
            }, 1000);
        }
    }

    private void loadCards() {

        recyclerView.hideShimmerAdapter();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

// Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

// listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
// filter recycler view when query submitted
                pendingScreenAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
// filter recycler view when text is changed
                pendingScreenAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    public JSONObject savePMAYImagesJsonParams(JSONObject savePMAYDataSet,String typeValue) {
        type=typeValue;
        String authKey = Utils.encrypt(prefManager.getUserPassKey(), getResources().getString(R.string.init_vector), savePMAYDataSet.toString());
        JSONObject dataSet = new JSONObject();
        try {
            dataSet.put(AppConstant.KEY_USER_NAME, prefManager.getUserName());
            dataSet.put(AppConstant.DATA_CONTENT, authKey);

            new ApiService(this).makeJSONObjectRequest("saveImage", Api.Method.POST, UrlGenerator.getMainService(), dataSet, "not cache", this);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("saveImages", "" + dataSet);
        return dataSet;
    }


    @Override
    public void OnMyResponse(ServerResponse serverResponse) {
        try {
            String urlType = serverResponse.getApi();
            JSONObject responseObj = serverResponse.getJsonResponse();

            if ("saveImage".equals(urlType) && responseObj != null) {
                String key = responseObj.getString(AppConstant.ENCODE_DATA);
                String responseDecryptedBlockKey = Utils.decrypt(prefManager.getUserPassKey(), key);
                JSONObject jsonObject = new JSONObject(responseDecryptedBlockKey);
                if (jsonObject.getString("STATUS").equalsIgnoreCase("OK") && jsonObject.getString("RESPONSE").equalsIgnoreCase("OK")) {
                    Utils.showAlert(this, "Your Data is Synchronized to the server!");
                    dbData.open();
                    if(type.equals("INFRA")){
                        deleteFileInfraPath(String.valueOf(prefManager.getSamathuvapuramId()),
                                String.valueOf(prefManager.getscheme_group_id()),
                                String.valueOf(prefManager.getscheme_id()),
                                String.valueOf(prefManager.getwork_group_id()),
                                String.valueOf(prefManager.getwork_type_id()));
                        db.delete(DBHelper.HOUSE_IMAGES_DETAILS, "samathuvapuram_id = ? and scheme_group_id = ? " +
                                "and scheme_id = ? and work_group_id = ? and work_type_id = ?", new String[]{String.valueOf(prefManager.getSamathuvapuramId()), String.valueOf(prefManager.getscheme_group_id()), String.valueOf(prefManager.getscheme_id()), String.valueOf(prefManager.getwork_group_id()), String.valueOf(prefManager.getwork_type_id())});
                        db.delete(DBHelper.HOUSE_DETAILS, "samathuvapuram_id = ? and scheme_group_id = ? and scheme_id = ? and work_group_id = ? and work_type_id = ?", new String[]{String.valueOf(prefManager.getSamathuvapuramId()), String.valueOf(prefManager.getscheme_group_id()), String.valueOf(prefManager.getscheme_id()), String.valueOf(prefManager.getwork_group_id()), String.valueOf(prefManager.getwork_type_id())});

                    }else {
                        deleteFileHousePath(String.valueOf(prefManager.getSamathuvapuramId()),
                                String.valueOf(prefManager.getHouse_serial_number()));

                        db.delete(DBHelper.HOUSE_IMAGES_DETAILS, "samathuvapuram_id = ? and house_serial_number = ?", new String[]{String.valueOf(prefManager.getSamathuvapuramId()), String.valueOf(prefManager.getHouse_serial_number())});
                        db.delete(DBHelper.HOUSE_DETAILS, "samathuvapuram_id = ? and house_serial_number = ?", new String[]{String.valueOf(prefManager.getSamathuvapuramId()), String.valueOf(prefManager.getHouse_serial_number())});

                    }
                    pendingScreenAdapter.removeSavedItem(prefManager.getDeleteAdapterPosition());
                    pendingScreenAdapter.notifyDataSetChanged();
                }
                else if (jsonObject.getString("STATUS").equalsIgnoreCase("OK") && jsonObject.getString("RESPONSE").equalsIgnoreCase("FAIL")) {
                    Utils.showAlert(this, jsonObject.getString("MESSAGE"));
                }
                Log.d("savedImage", "" + responseDecryptedBlockKey);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void deleteFileInfraPath(String samathuvapuram_id, String scheme_group_id, String scheme_id, String work_group_id, String work_type_id) {
        ArrayList<ModelClass> activityImage = new ArrayList<>();
        activityImage = dbData.getParticularSavedInfraImage(samathuvapuram_id,scheme_group_id,scheme_id,work_group_id,work_type_id);
       for (int i=0; i < activityImage.size();i++){
           String file_path= activityImage.get(i).getImage_path();
           deleteFileDirectory(file_path);
       }

    }
    private void deleteFileHousePath(String samathuvapuram_id, String house_serial_number) {
        ArrayList<ModelClass> activityImage = new ArrayList<>();
        activityImage = dbData.getParticularSavedHouseImage(samathuvapuram_id,house_serial_number);
       for (int i=0; i < activityImage.size();i++){
           String file_path= activityImage.get(i).getImage_path();
           deleteFileDirectory(file_path);
       }

    }

    private void deleteFileDirectory(String file_path){
        File file = new File(file_path);
        // call deleteDirectory method to delete directory
        // recursively
        file.delete();

    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
    }
    @Override
    public void OnError(VolleyError volleyError) {

    }
}

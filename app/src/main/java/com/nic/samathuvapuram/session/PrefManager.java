package com.nic.samathuvapuram.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;



import org.json.JSONArray;

import com.nic.samathuvapuram.constant.AppConstant;


/**
 * Created by AchanthiSudan on 11/01/19.
 */
public class PrefManager {

    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    private static final String APP_KEY = "AppKey";
    private static final String KEY_USER_AUTH_KEY = "auth_key";
    private static final String KEY_USER_PASS_KEY = "pass_key";
    private static final String KEY_ENCRYPT_PASS = "pass";
    private static final String KEY_USER_NAME = "UserName";
    private static final String KEY_USER_PASSWORD = "UserPassword";
    private static final String KEY_DISTRICT_CODE = "District_Code";
    private static final String KEY_BLOCK_CODE = "Block_Code";
    private static final String KEY_PV_CODE = "Pv_Code";
    private static final String KEY_HAB_CODE = "Hab_Code";
    private static final String KEY_DISTRICT_NAME = "District_Name";
    private static final String KEY_DESIGNATION = "Designation";
    private static final String KEY_NAME = "Name";
    private static final String KEY_BLOCK_NAME = "Block_Name";
    private static final String KEY_SPINNER_SELECTED_PVCODE = "spinner_selected_pv_code";
    private static final String KEY_SPINNER_SELECTED_BLOCKCODE = "spinner_selected_block_code";

    private static final String KEY_BLOCK_CODE_JSON = "block_code_json";
    private static final String KEY_VILLAGE_CODE_JSON = "village_code_json";
    private static final String KEY_SCHEME_NAME = "Scheme_Name";
    private static final String KEY_FINANCIALYEAR_NAME = "FinancialYear_Name";
    private static final String KEY_VILLAGE_LIST_PV_NAME = "Village_List_Pv_Name";



    private static final String IMEI = "imei";
    private static final String MOTIVATOR_ID = "motivator_id";
    private static final String SCHEDULE_MASTER_ID = "schedule_master_id";
    private static final String KEY_DELETE_ID = "deleteId";
    private static final String KEY_DELETE_POSITION = "deletePosition";
    private static final String KEY_SAMATHUVAPURAM_ID = "samathuvapuram_id";
    private static final String KEY_HOUSE_SERIAL_NUMBER = "house_serial_number";
    private static final String DELETE_ADAPTER_POSITION = "delete_adapter_position";
    private static final String KEY_COMMUNITY_NAME = "Community_Name";
    private static final String KEY_COMMUNITY_CODE = "Community_Code";
    private static final String KEY_CURRRENT_USAGE_ID = "current_usage_id";
    private static final String KEY_CURRRENT_USAGE = "current_usage";
    private static final String KEY_PHOTO_TYPE = "photos_type";
    private static final String KEY_USER_TYPE = "user_type";
    private static final String KEY_scheme_group_id = "scheme_group_id";
    private static final String KEY_scheme_id = "scheme_id";
    private static final String KEY_work_group_id = "work_group_id";
    private static final String KEY_work_type_id = "work_type_id";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(AppConstant.PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public Object setPhototype(Object key) {
        editor.putString(KEY_PHOTO_TYPE, String.valueOf(key));
        editor.commit();
        return key;
    }

    public String getPhototype() {
        return pref.getString(KEY_PHOTO_TYPE, null);
    }

    public Object setUsertype(Object key) {
        editor.putString(KEY_USER_TYPE, String.valueOf(key));
        editor.commit();
        return key;
    }

    public String getUsertype() {
        return pref.getString(KEY_USER_TYPE, null);
    }

    public Object setCurrentUsageID(Object key) {
        editor.putString(KEY_CURRRENT_USAGE_ID, String.valueOf(key));
        editor.commit();
        return key;
    }

    public String getCurrentUsageID() {
        return pref.getString(KEY_CURRRENT_USAGE_ID, null);
    }
    public Object setCurrentUsage(Object key) {
        editor.putString(KEY_CURRRENT_USAGE, String.valueOf(key));
        editor.commit();
        return key;
    }

    public String getCurrentUsage() {
        return pref.getString(KEY_CURRRENT_USAGE, null);
    }
    public Object setCommunityCode(Object key) {
        editor.putString(KEY_COMMUNITY_CODE, String.valueOf(key));
        editor.commit();
        return key;
    }

    public String getCommunityCode() {
        return pref.getString(KEY_COMMUNITY_CODE, null);
    }
    public Object setCommunityName(Object key) {
        editor.putString(KEY_COMMUNITY_NAME, String.valueOf(key));
        editor.commit();
        return key;
    }

    public String getCommunityName() {
        return pref.getString(KEY_COMMUNITY_NAME, null);
    }

    public Integer getScheduleMasterId() {
        return pref.getInt(SCHEDULE_MASTER_ID,0);
    }

    public void setScheduleMasterId(Integer scheduleMasterId) {
        editor.putInt(SCHEDULE_MASTER_ID,scheduleMasterId);
        editor.commit();
    }

    public String getIMEI() {
        return pref.getString(IMEI,null);
    }

    public void setImei(String imei) {
        editor.putString(IMEI,imei);
        editor.commit();
    }

    public void setAppKey(String appKey) {
        editor.putString(APP_KEY, appKey);
        editor.commit();
    }

    public String getAppKey() {
        return pref.getString(APP_KEY, null);
    }


    public void clearSession() {
        editor.clear();
        editor.commit();
    }


    public void setUserAuthKey(String userAuthKey) {
        editor.putString(KEY_USER_AUTH_KEY, userAuthKey);
        editor.commit();
    }

    public String getKeyDeleteId() {
        return pref.getString(KEY_DELETE_ID,null);
    }

    public void setKeyDeleteId(String deleteId) {
        editor.putString(KEY_DELETE_ID,deleteId);
        editor.commit();
    }

    public Integer getKeyDeletePosition() {
        return pref.getInt(KEY_DELETE_POSITION,0);
    }

    public void setKeyDeletePosition(Integer deletePos) {
        editor.putInt(KEY_DELETE_POSITION,deletePos);
        editor.commit();
    }

    public Integer getSamathuvapuramId() {
        return pref.getInt(KEY_SAMATHUVAPURAM_ID,0);
    }

    public void setSamathuvapuramId(Integer deletePos) {
        editor.putInt(KEY_SAMATHUVAPURAM_ID,deletePos);
        editor.commit();
    }
  public Integer getHouse_serial_number() {
        return pref.getInt(KEY_HOUSE_SERIAL_NUMBER,0);
    }

    public void setHouse_serial_number(Integer deletePos) {
        editor.putInt(KEY_HOUSE_SERIAL_NUMBER,deletePos);
        editor.commit();
    }

   public Integer getscheme_group_id() {
        return pref.getInt(KEY_scheme_group_id,0);
    }

    public void setscheme_group_id(Integer scheme_group_id) {
        editor.putInt(KEY_scheme_group_id,scheme_group_id);
        editor.commit();
    }


    public Integer getscheme_id() {
        return pref.getInt(KEY_scheme_id,0);
    }

    public void setscheme_id(Integer scheme_id) {
        editor.putInt(KEY_scheme_id,scheme_id);
        editor.commit();
    }

    public Integer getwork_group_id() {
        return pref.getInt(KEY_work_group_id,0);
    }

    public void setwork_group_id(Integer work_group_id) {
        editor.putInt(KEY_work_group_id,work_group_id);
        editor.commit();
    }

    public Integer getwork_type_id() {
        return pref.getInt(KEY_work_type_id,0);
    }

    public void setwork_type_id(Integer work_type_id) {
        editor.putInt(KEY_work_type_id,work_type_id);
        editor.commit();
    }

    public void setDeleteAdapterPosition(Integer LocationId) {
        editor.putInt(DELETE_ADAPTER_POSITION,LocationId);
        editor.commit();
    }

    public Integer getDeleteAdapterPosition() {
        return pref.getInt(DELETE_ADAPTER_POSITION,0);
    }


    public String getUserAuthKey() {
        return pref.getString(KEY_USER_AUTH_KEY, null);
    }

    public void setUserPassKey(String userPassKey) {
        editor.putString(KEY_USER_PASS_KEY, userPassKey);
        editor.commit();
    }

    public String getMotivatorId() {
        return pref.getString(MOTIVATOR_ID, null);
    }

    public void setMotivatorId(String userPassKey) {
        editor.putString(MOTIVATOR_ID, userPassKey);
        editor.commit();
    }

    public String getUserPassKey() {
        return pref.getString(KEY_USER_PASS_KEY, null);
    }


    public void setUserName(String userName) {
        editor.putString(KEY_USER_NAME, userName);
        editor.commit();
    }

    public String getUserName() { return pref.getString(KEY_USER_NAME, null); }

    public void setUserPassword(String userPassword) {
        editor.putString(KEY_USER_PASSWORD, userPassword);
        editor.commit();
    }

    public String getUserPassword() { return pref.getString(KEY_USER_PASSWORD, null); }


    public void setEncryptPass(String pass) {
        editor.putString(KEY_ENCRYPT_PASS, pass);
        editor.commit();
    }

    public String getEncryptPass() {
        return pref.getString(KEY_ENCRYPT_PASS, null);
    }

    public Object setDistrictCode(Object key) {
        editor.putString(KEY_DISTRICT_CODE, String.valueOf(key));
        editor.commit();
        return key;
    }

    public String getDistrictCode() {
        return pref.getString(KEY_DISTRICT_CODE, null);
    }


    public Object setBlockCode(Object key) {
        editor.putString(KEY_BLOCK_CODE, String.valueOf(key));
        editor.commit();
        return key;
    }

    public String getBlockCode() {
        return pref.getString(KEY_BLOCK_CODE, null);
    }



    public Object setPvCode(Object key) {
        editor.putString(KEY_PV_CODE, String.valueOf(key));
        editor.commit();
        return key;
    }

    public String getPvCode() {
        return pref.getString(KEY_PV_CODE, null);
    }

    public Object setHabCode(Object key) {
        editor.putString(KEY_HAB_CODE, String.valueOf(key));
        editor.commit();
        return key;
    }

    public String getHabCode() {
        return pref.getString(KEY_HAB_CODE, null);
    }



    public Object setDistrictName(Object key) {
        editor.putString(KEY_DISTRICT_NAME, String.valueOf(key));
        editor.commit();
        return key;
    }

    public String getDistrictName() {
        return pref.getString(KEY_DISTRICT_NAME, null);
    }

    public Object setBlockName(Object key) {
        editor.putString(KEY_BLOCK_NAME, String.valueOf(key));
        editor.commit();
        return key;
    }

    public String getBlockName() {
        return pref.getString(KEY_BLOCK_NAME, null);

    }


    public Object setDesignation(Object key) {
        editor.putString(KEY_DESIGNATION, String.valueOf(key));
        editor.commit();
        return key;
    }

    public String getDesignation() {
        return pref.getString(KEY_DESIGNATION, null);
    }



    public void setName(String userName) {
        editor.putString(KEY_NAME, userName);
        editor.commit();
    }

    public String getName() {
        return pref.getString(KEY_NAME, null);
    }

    public  void setSchemeName(String key) {
        editor.putString(KEY_SCHEME_NAME,key);
        editor.commit();
    }

    public String getSchemeName() {return pref.getString(KEY_SCHEME_NAME,null);}

    public void setFinancialyearName(String key) {
        editor.putString(KEY_FINANCIALYEAR_NAME,key);
        editor.commit();
    }

    public String getFinancialyearName() {return pref.getString(KEY_FINANCIALYEAR_NAME,null);}


    public void setKeySpinnerSelectedPvcode(String userName) {
        editor.putString(KEY_SPINNER_SELECTED_PVCODE, userName);
        editor.commit();
    }

    public String getKeySpinnerSelectedPVcode() {
        return pref.getString(KEY_SPINNER_SELECTED_PVCODE, null);
    }


    public void clearSharedPreferences(Context context) {
        pref = _context.getSharedPreferences(AppConstant.PREF_NAME, PRIVATE_MODE);
        editor.clear();
        editor.apply();
    }


    public void setBlockCodeJson(JSONArray jsonarray) {
        editor.putString(KEY_BLOCK_CODE_JSON, jsonarray.toString());
        editor.commit();
    }

    private String getBlockCodeJsonList() {
        return pref.getString(KEY_BLOCK_CODE_JSON, null);
    }

    public JSONArray getBlockCodeJson() {
        JSONArray jsonData = null;
        String strJson = getBlockCodeJsonList();//second parameter is necessary ie.,Value to return if this preference does not exist.
        try {
            if (strJson != null) {
                jsonData = new JSONArray(strJson);
            }
        } catch (Exception e) {

        }
        Log.d("prefBlockJson",""+jsonData);
        return jsonData;
    }

    public void setVillagePvCodeJson(JSONArray jsonarray) {
        editor.putString(KEY_VILLAGE_CODE_JSON, jsonarray.toString());
        editor.commit();
    }

    private String getVillagePvCodeJsonList() {
        return pref.getString(KEY_VILLAGE_CODE_JSON, null);
    }

    public JSONArray getVillagePvCodeJson() {
        JSONArray jsonData = null;
        String strJson = getVillagePvCodeJsonList();//second parameter is necessary ie.,Value to return if this preference does not exist.
        try {
            if (strJson != null) {
                jsonData = new JSONArray(strJson);
            }
        } catch (Exception e) {

        }
        Log.d("prefVillageJson",""+jsonData);
        return jsonData;
    }

    public void setKeySpinnerSelectedBlockcode(String userName) {
        editor.putString(KEY_SPINNER_SELECTED_BLOCKCODE, userName);
        editor.commit();
    }

    public String   getKeySpinnerSelectedBlockcode() {
        return pref.getString(KEY_SPINNER_SELECTED_BLOCKCODE, null);
    }

    public void setVillageListPvName(String key) {
        editor.putString(KEY_VILLAGE_LIST_PV_NAME,  key);
        editor.commit();
    }

    public String getVillageListPvName() {
        return pref.getString(KEY_VILLAGE_LIST_PV_NAME, null);
    }

}

package com.nic.samathuvapuram.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.nic.samathuvapuram.constant.AppConstant;
import com.nic.samathuvapuram.model.ModelClass;

import java.util.ArrayList;


public class dbData {
    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;
    private Context context;

    public dbData(Context context){
        this.dbHelper = new DBHelper(context);
        this.context = context;
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        if(dbHelper != null) {
            dbHelper.close();
        }
    }

    /****** DISTRICT TABLE *****/


    /****** VILLAGE TABLE *****/
    public ModelClass insertVillage(ModelClass pmgsySurvey) {

        ContentValues values = new ContentValues();
        values.put(AppConstant.DISTRICT_CODE, pmgsySurvey.getDistictCode());
        values.put(AppConstant.BLOCK_CODE, pmgsySurvey.getBlockCode());
        values.put(AppConstant.PV_CODE, pmgsySurvey.getPvCode());
        values.put(AppConstant.PV_NAME, pmgsySurvey.getPvName());

        long id = db.insert(DBHelper.VILLAGE_TABLE_NAME,null,values);
        Log.d("Inserted_id_village", String.valueOf(id));

        return pmgsySurvey;
    }
    public ArrayList<ModelClass> getAll_Village(String dcode, String bcode) {

        ArrayList<ModelClass> cards = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("select * from "+DBHelper.VILLAGE_TABLE_NAME+" where dcode = "+dcode+" and bcode = "+bcode+" order by pvname asc",null);
            // cursor = db.query(CardsDBHelper.TABLE_CARDS,
            //       COLUMNS, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ModelClass card = new ModelClass();
                    card.setDistictCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.DISTRICT_CODE)));
                    card.setBlockCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.BLOCK_CODE)));
                    card.setPvCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.PV_CODE)));
                    card.setPvName(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.PV_NAME)));

                    cards.add(card);
                }
            }
        } catch (Exception e){
            //   Log.d(DEBUG_TAG, "Exception raised with a value of " + e);
        } finally{
            if (cursor != null) {
                cursor.close();
            }
        }
        return cards;
    }

    public ModelClass insertHabitation(ModelClass pmgsySurvey) {

        ContentValues values = new ContentValues();
        values.put(AppConstant.DISTRICT_CODE, pmgsySurvey.getDistictCode());
        values.put(AppConstant.BLOCK_CODE, pmgsySurvey.getBlockCode());
        values.put(AppConstant.PV_CODE, pmgsySurvey.getPvCode());
        values.put(AppConstant.HABB_CODE, pmgsySurvey.getHabCode());
        values.put(AppConstant.HABITATION_NAME, pmgsySurvey.getHabitationName());

        long id = db.insert(DBHelper.HABITATION_TABLE_NAME,null,values);
        Log.d("Inserted_id_habitation", String.valueOf(id));

        return pmgsySurvey;
    }
    public ArrayList<ModelClass> getAll_Habitation(String dcode, String bcode) {

        ArrayList<ModelClass> cards = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("select * from "+DBHelper.HABITATION_TABLE_NAME+" where dcode = "+dcode+" and bcode = "+bcode+" order by habitation_name asc",null);
            // cursor = db.query(CardsDBHelper.TABLE_CARDS,
            //       COLUMNS, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ModelClass card = new ModelClass();
                    card.setDistictCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.DISTRICT_CODE)));
                    card.setBlockCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.BLOCK_CODE)));
                    card.setPvCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.PV_CODE)));
                    card.setHabCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.HABB_CODE)));
                    card.setHabitationName(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.HABITATION_NAME)));

                    cards.add(card);
                }
            }
        } catch (Exception e){
            //   Log.d(DEBUG_TAG, "Exception raised with a value of " + e);
        } finally{
            if (cursor != null) {
                cursor.close();
            }
        }
        return cards;
    }

    public void Insert_menu_access_control(ModelClass modelClass) {

        ContentValues values = new ContentValues();
        values.put("menu_id", modelClass.getMenu_id());
        values.put("menu_name", modelClass.getMenu_name());
        values.put("menu_access_control", modelClass.getMenu_access_control());

        long id = db.insert(DBHelper.MENU_ACCESS_CONTROL,null,values);
        Log.d("Insert_id_menu", String.valueOf(id));

    }
    public void Insert_current_house_usage(ModelClass modelClass) {

        ContentValues values = new ContentValues();
        values.put("current_usage_id", modelClass.getCurrent_usage_id());
        values.put("current_usage", modelClass.getCurrent_usage());
        values.put("is_beneficiary_detail_required", modelClass.getIs_beneficiary_detail_required());

        long id = db.insert(DBHelper.CURRENT_HOUSE_USAGE,null,values);
        Log.d("Insert_id_house_usage", String.valueOf(id));

    }
    public void Insert_community(ModelClass modelClass) {

        ContentValues values = new ContentValues();
        values.put("community_category_id", modelClass.getCommunity_category_id());
        values.put("community_category", modelClass.getCommunity_category());

        long id = db.insert(DBHelper.COMMUNITY,null,values);
        Log.d("Insert_id_community", String.valueOf(id));

    }
    public void Insert_photos_type(ModelClass modelClass) {

        ContentValues values = new ContentValues();
        values.put("photo_type_id", modelClass.getPhoto_type_id());
        values.put("form_id", modelClass.getForm_id());
        values.put("photo_type_name", modelClass.getPhoto_type_name());
        values.put("min_no_of_photos", modelClass.getMin_no_of_photos());
        values.put("max_no_of_photos", modelClass.getMax_no_of_photos());

        long id = db.insert(DBHelper.PHOTOS_TYPE,null,values);
        Log.d("Insert_id_photos_type", String.valueOf(id));

    }
    public void Insert_condition_of_house(ModelClass modelClass) {

        ContentValues values = new ContentValues();
        values.put("condition_of_house_id", modelClass.getCondition_of_house_id());
        values.put("condition_of_house", modelClass.getCondition_of_house());

        long id = db.insert(DBHelper.CONDITION_OF_HOUSE,null,values);
        Log.d("Insert_id_con_of_house", String.valueOf(id));

    }
    public void Insert_condition_of_infra(ModelClass modelClass) {

        ContentValues values = new ContentValues();
        values.put("condition_of_infra_id", modelClass.getCondition_of_infra_id());
        values.put("condition_of_infra", modelClass.getCondition_of_infra());
        long id = db.insert(DBHelper.CONDITION_OF_INFRA,null,values);
        Log.d("Insert_id_con_of_infra", String.valueOf(id));

    }
    public void Insert_estimate_type(ModelClass modelClass) {

        ContentValues values = new ContentValues();
        values.put("estimate_type_id", modelClass.getEstimate_type_id());
        values.put("estimate_type_name", modelClass.getEstimate_type_name());

        long id = db.insert(DBHelper.ESTIMATE_TYPE,null,values);
        Log.d("Insert_id_estimate_type", String.valueOf(id));

    }
    public void Insert_work_scheme(ModelClass modelClass) {

        ContentValues values = new ContentValues();
        values.put("scheme_group_id", modelClass.getScheme_group_id());
        values.put("scheme_id", modelClass.getScheme_id());
        values.put("category", modelClass.getCategory());
        values.put("has_sub_type", modelClass.getHas_sub_type());
        values.put("existing_condition_required", modelClass.getExisting_condition_required());
        values.put("photos_required", modelClass.getPhotos_required());
        values.put("scheme_name", modelClass.getScheme_name());
        values.put("scheme_disp_order", modelClass.getScheme_disp_order());
        long id = db.insert(DBHelper.WORK_SCHEME,null,values);
        Log.d("Insert_id_work_scheme", String.valueOf(id));

    }
    public void Insert_work_type(ModelClass modelClass) {

        ContentValues values = new ContentValues();
        values.put("scheme_group_id", modelClass.getScheme_group_id());
        values.put("scheme_id", modelClass.getScheme_id());
        values.put("category", modelClass.getCategory());
        values.put("work_group_id", modelClass.getWork_group_id());
        values.put("work_type_id", modelClass.getWork_type_id());
        values.put("existing_condition_required", modelClass.getExisting_condition_required());
        values.put("photos_required", modelClass.getPhotos_required());
        values.put("work_name", modelClass.getWork_name());
        long id = db.insert(DBHelper.WORK_TYPE,null,values);
        Log.d("Insert_id_work_type", String.valueOf(id));

    }
    public void Insert_samathuvapuram_details(ModelClass modelClass) {

        ContentValues values = new ContentValues();
        values.put("samathuvapuram_id", modelClass.getSamathuvapuram_id());
        values.put("localbody_area_type", modelClass.getLocalbody_area_type());
        values.put("localbody_type_id", modelClass.getLocalbody_type_id());
        values.put("dcode", modelClass.getDcode());
        values.put("bcode", modelClass.getBcode());
        values.put("pvcode", modelClass.getPvCode());
        values.put("hab_code", modelClass.getHabCode());
        values.put("tpcode", modelClass.getTpcode());
        values.put("muncode", modelClass.getMuncode());
        values.put("corpcode", modelClass.getCorpcode());
        values.put("year_of_construction", modelClass.getYear_of_construction());
        values.put("no_of_houses_constructed", modelClass.getNo_of_houses_constructed());
        values.put("dname", modelClass.getDname());
        values.put("bname", modelClass.getBname());
        values.put("pvname", modelClass.getPvName());
        values.put("habitation_name", modelClass.getHabitationName());
        values.put("townpanchayat_name", modelClass.getTownpanchayat_name());
        values.put("municipality_name", modelClass.getMunicipality_name());
        values.put("corporation_name", modelClass.getCorporation_name());

        long id = db.insert(DBHelper.SAMATHUVAPURAM_DETAILS,null,values);
        Log.d("Ins_id_samathuvapuram", String.valueOf(id));

    }
    public void Insert_house_list(ModelClass modelClass) {

        ContentValues values = new ContentValues();
        values.put("samathuvapuram_id", modelClass.getSamathuvapuram_id());
        values.put("name", modelClass.getName());

        long id = db.insert(DBHelper.HOUSE_LIST,null,values);
        Log.d("Ins_id_samathuvapuram", String.valueOf(id));

    }



    public ArrayList<ModelClass> getAll_Menu_Access_Control() {

        ArrayList<ModelClass> cards = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("select * from "+DBHelper.MENU_ACCESS_CONTROL,null);
            // cursor = db.query(CardsDBHelper.TABLE_CARDS,
            //       COLUMNS, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ModelClass card = new ModelClass();
                    card.setMenu_id(cursor.getInt(cursor
                            .getColumnIndexOrThrow("menu_id")));
                    card.setMenu_name(cursor.getString(cursor
                            .getColumnIndexOrThrow("menu_name")));
                    card.setMenu_access_control(cursor.getString(cursor
                            .getColumnIndexOrThrow("menu_access_control")));


                    cards.add(card);
                }
            }
        } catch (Exception e){
            //   Log.d(DEBUG_TAG, "Exception raised with a value of " + e);
            e.printStackTrace();
        } finally{
            if (cursor != null) {
                cursor.close();
            }
        }
        return cards;
    }
    public ArrayList<ModelClass> getAllsamathuvapuramlist() {

        ArrayList<ModelClass> cards = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("select * from "+DBHelper.SAMATHUVAPURAM_DETAILS,null);
            // cursor = db.query(CardsDBHelper.TABLE_CARDS,
            //       COLUMNS, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ModelClass card = new ModelClass();
                    card.setSamathuvapuram_id(cursor.getInt(cursor.getColumnIndexOrThrow("samathuvapuram_id")));
                    card.setLocalbody_area_type(cursor.getInt(cursor.getColumnIndexOrThrow("localbody_area_type")));
                    card.setLocalbody_type_id(cursor.getInt(cursor.getColumnIndexOrThrow("localbody_type_id")));
                    card.setDcode(cursor.getInt(cursor.getColumnIndexOrThrow("dcode")));
                    card.setBcode(cursor.getInt(cursor.getColumnIndexOrThrow("bcode")));
                    card.setTpcode(cursor.getInt(cursor.getColumnIndexOrThrow("tpcode")));
                    card.setNo_of_houses_constructed(cursor.getInt(cursor.getColumnIndexOrThrow("no_of_houses_constructed")));
                    card.setPvCode(cursor.getString(cursor.getColumnIndexOrThrow("pvcode")));
                    card.setHabCode(cursor.getString(cursor.getColumnIndexOrThrow("hab_code")));
                    card.setMuncode(cursor.getString(cursor.getColumnIndexOrThrow("muncode")));
                    card.setCorpcode(cursor.getString(cursor.getColumnIndexOrThrow("corpcode")));
                    card.setYear_of_construction(cursor.getString(cursor.getColumnIndexOrThrow("year_of_construction")));
                    card.setDname(cursor.getString(cursor.getColumnIndexOrThrow("dname")));
                    card.setBname(cursor.getString(cursor.getColumnIndexOrThrow("bname")));
                    card.setPvName(cursor.getString(cursor.getColumnIndexOrThrow("pvname")));
                    card.setHabitationName(cursor.getString(cursor.getColumnIndexOrThrow("habitation_name")));
                    card.setTownpanchayat_name(cursor.getString(cursor.getColumnIndexOrThrow("townpanchayat_name")));
                    card.setMunicipality_name(cursor.getString(cursor.getColumnIndexOrThrow("municipality_name")));
                    card.setCorporation_name(cursor.getString(cursor.getColumnIndexOrThrow("corporation_name")));
                    cards.add(card);
                }
            }
        } catch (Exception e){
            //   Log.d(DEBUG_TAG, "Exception raised with a value of " + e);
            e.printStackTrace();
        } finally{
            if (cursor != null) {
                cursor.close();
            }
        }
        return cards;
    }
    public ArrayList<ModelClass> getAllhouselist() {

        ArrayList<ModelClass> cards = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("select * from "+DBHelper.HOUSE_LIST,null);
            // cursor = db.query(CardsDBHelper.TABLE_CARDS,
            //       COLUMNS, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ModelClass card = new ModelClass();
                    card.setSamathuvapuram_id(cursor.getInt(cursor
                            .getColumnIndexOrThrow("samathuvapuram_id")));
                    card.setName(cursor.getString(cursor
                            .getColumnIndexOrThrow("name")));


                    cards.add(card);
                }
            }
        } catch (Exception e){
            //   Log.d(DEBUG_TAG, "Exception raised with a value of " + e);
            e.printStackTrace();
        } finally{
            if (cursor != null) {
                cursor.close();
            }
        }
        return cards;
    }



    public Bitmap bytearrtoBitmap(byte[] photo){
        byte[] imgbytes = Base64.decode(photo, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgbytes, 0,
                imgbytes.length);
        return bitmap;
    }



    //////////////////////*****************/////////////


    public void deleteVillageTable() {
        db.execSQL("delete from " + DBHelper.VILLAGE_TABLE_NAME);
    }
    public void deleteHabitationTable() {
        db.execSQL("delete from " + DBHelper.HABITATION_TABLE_NAME);
    }
    public void deleteSAVE_IMAGESTable() {
        db.execSQL("delete from " + DBHelper.SAVE_IMAGES);
    }
    public void deleteMENU_ACCESS_CONTROLTable() {
        db.execSQL("delete from " + DBHelper.MENU_ACCESS_CONTROL);
    }
    public void deleteCURRENT_HOUSE_USAGETable() {
        db.execSQL("delete from " + DBHelper.CURRENT_HOUSE_USAGE);
    }
    public void deleteCOMMUNITYTable() {
        db.execSQL("delete from " + DBHelper.COMMUNITY);
    }
    public void deletePHOTOS_TYPETable() {
        db.execSQL("delete from " + DBHelper.PHOTOS_TYPE);
    }
    public void deleteCONDITION_OF_HOUSETable() {
        db.execSQL("delete from " + DBHelper.CONDITION_OF_HOUSE);
    }
    public void deleteCONDITION_OF_INFRATable() {
        db.execSQL("delete from " + DBHelper.CONDITION_OF_INFRA);
    }
    public void deleteESTIMATE_TYPETable() {
        db.execSQL("delete from " + DBHelper.ESTIMATE_TYPE);
    }
    public void deleteWORK_SCHEMETable() {
        db.execSQL("delete from " + DBHelper.WORK_SCHEME);
    }
    public void deleteWORK_TYPETable() {
        db.execSQL("delete from " + DBHelper.WORK_TYPE);
    }
    public void deleteSAMATHUVAPURAM_DETAILSTable() {
        db.execSQL("delete from " + DBHelper.SAMATHUVAPURAM_DETAILS);
    }
    public void deleteHOUSE_LISTable() {
        db.execSQL("delete from " + DBHelper.HOUSE_LIST);
    }




    public void deleteAll() {

        deleteVillageTable();
        deleteHabitationTable();
        deleteSAVE_IMAGESTable();
        deleteMENU_ACCESS_CONTROLTable();
        deleteCURRENT_HOUSE_USAGETable();
        deleteCOMMUNITYTable();
        deletePHOTOS_TYPETable();
        deleteCONDITION_OF_HOUSETable();
        deleteCONDITION_OF_INFRATable();
        deleteESTIMATE_TYPETable();
        deleteWORK_SCHEMETable();
        deleteWORK_TYPETable();
        deleteSAMATHUVAPURAM_DETAILSTable();
        deleteHOUSE_LISTable();
    }



}

package com.nic.samathuvapuram.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nic.samathuvapuram.R;
import com.nic.samathuvapuram.dataBase.dbData;
import com.nic.samathuvapuram.databinding.HouseListAdapterBinding;
import com.nic.samathuvapuram.databinding.PendingAdapterBinding;
import com.nic.samathuvapuram.model.ModelClass;
import com.nic.samathuvapuram.session.PrefManager;

import org.json.JSONObject;

import java.util.List;

public class HouseListAdapter extends RecyclerView.Adapter<HouseListAdapter.MyViewHolder> {

    private static Activity context;
    private PrefManager prefManager;
    private List<ModelClass> list;
    static JSONObject dataset = new JSONObject();
    private final com.nic.samathuvapuram.dataBase.dbData dbData;
    private LayoutInflater layoutInflater;

    public HouseListAdapter(Activity context, List<ModelClass> pendingListValues, dbData dbData) {

        this.context = context;
        prefManager = new PrefManager(context);
        this.dbData = dbData;
        this.list = pendingListValues;
    }

    @Override
    public HouseListAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        HouseListAdapterBinding houseListAdapterBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.house_list_adapter, viewGroup, false);
        return new HouseListAdapter.MyViewHolder(houseListAdapterBinding);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private HouseListAdapterBinding houseListAdapterBinding;

        public MyViewHolder(HouseListAdapterBinding Binding) {
            super(Binding.getRoot());
            houseListAdapterBinding = Binding;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.houseListAdapterBinding.name.setText(list.get(position).getCurrent_name_of_the_beneficiary());
        holder.houseListAdapterBinding.number.setText(String.valueOf(list.get(position).getHouse_serial_number()));

        holder.houseListAdapterBinding.number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                deletePending(position);
            }
        });



    }





    @Override
    public int getItemCount() {
        return list.size();
    }

}

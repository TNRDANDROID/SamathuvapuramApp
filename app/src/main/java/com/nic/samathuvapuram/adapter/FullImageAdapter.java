package com.nic.samathuvapuram.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nic.samathuvapuram.R;
import com.nic.samathuvapuram.dataBase.dbData;
import com.nic.samathuvapuram.databinding.GalleryThumbnailBinding;
import com.nic.samathuvapuram.model.ModelClass;
import com.nic.samathuvapuram.session.PrefManager;


import java.io.File;
import java.util.List;

public class FullImageAdapter extends RecyclerView.Adapter<FullImageAdapter.MyViewHolder> {

    private Context context;
    private PrefManager prefManager;
    private List<ModelClass> imagePreviewlistvalues;
    private final com.nic.samathuvapuram.dataBase.dbData dbData;
    private LayoutInflater layoutInflater;
    String onOffType;

    public FullImageAdapter(Context context, List<ModelClass> imagePreviewlistvalues, dbData dbData, String onOffType) {

        this.context = context;
        prefManager = new PrefManager(context);
        this.dbData = dbData;
        this.imagePreviewlistvalues = imagePreviewlistvalues;
        this.onOffType = onOffType;
    }

    @Override
    public FullImageAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        GalleryThumbnailBinding galleryThumbnailBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.gallery_thumbnail, viewGroup, false);
        return new FullImageAdapter.MyViewHolder(galleryThumbnailBinding);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private GalleryThumbnailBinding galleryThumbnailBinding;

        public MyViewHolder(GalleryThumbnailBinding Binding) {
            super(Binding.getRoot());
            galleryThumbnailBinding = Binding;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
//        holder.description.setText(imagePreviewlistvalues.get(position).getImageRemark());
//        holder.thumbnail.setImageBitmap(imagePreviewlistvalues.get(position).getImage());
//        holder.title.setText(imagePreviewlistvalues.get(position).getType()+" Activity");
       /* Glide.with(context).load(imagePreviewlistvalues.get(position).getImage())
                .thumbnail(0.5f)
                .into(holder.galleryThumbnailBinding.thumbnail);*/


       if(onOffType.equals("Online")){
           Glide.with(context).load(imagePreviewlistvalues.get(position).getImage())
                   .thumbnail(0.5f)
                   .into(holder.galleryThumbnailBinding.thumbnail);
       }else {
           File imgFile = new File(imagePreviewlistvalues.get(position).getImage_path());
           if(imgFile.exists()){
               Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
               holder.galleryThumbnailBinding.thumbnail.setImageBitmap(myBitmap);
           }
       }

    }

    @Override
    public int getItemCount() {
        return imagePreviewlistvalues.size();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private FullImageAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final FullImageAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}

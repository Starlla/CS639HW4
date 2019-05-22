package com.example.chuyutongcs639hw4;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import java.util.ArrayList;

public class CircleRecyclerViewAdapter extends RecyclerView.Adapter<CircleRecyclerViewAdapter.RecyclerViewHolder> {
    Context context;

    private int mSelectedPosition = RecyclerView.NO_POSITION;
    private OnItemClickListener mListener;
    private ArrayList<Circle> mCircleList;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener =listener;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        public CircleAnimationView mCircleView;

        public RecyclerViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            mCircleView = itemView.findViewById(R.id.listCircleView);

            itemView.setOnClickListener(view ->  {
                if (listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }

    public CircleRecyclerViewAdapter(ArrayList<Circle> circleList){
        mCircleList = circleList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(v,  mListener);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder viewHolder, int position) {
        Circle currentCircle = mCircleList.get(position);
        viewHolder.mCircleView.setCircleColor(currentCircle.mColor);
        viewHolder.mCircleView.setCircleRadius(currentCircle.mRadius);
        viewHolder.mCircleView.setCircleSpeed(currentCircle.mSpeed);
        viewHolder.mCircleView.invalidate();
    }

    public Circle getItem(int position) {
        return mCircleList.get(position);
    }

    @Override
    public int getItemCount() {
        return mCircleList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public int getSelectedPosition(){return mSelectedPosition;}

    public void setSelectedPosition(int position) {
        mSelectedPosition =  position ==  mSelectedPosition ? Adapter.NO_SELECTION : position;
        notifyDataSetChanged();
    }

    public boolean isPositionSelected() {
        return mSelectedPosition != Adapter.NO_SELECTION;
    }
}

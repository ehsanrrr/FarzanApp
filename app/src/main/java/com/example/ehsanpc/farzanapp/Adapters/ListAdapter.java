package com.example.ehsanpc.farzanapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ehsanpc.farzanapp.Models.FarzanModel;
import com.example.ehsanpc.farzanapp.R;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<FarzanModel> farzanModelList = new ArrayList<>();

    public ListAdapter(Context context, List<FarzanModel> farzanModelList) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.farzanModelList = farzanModelList;
    }

    @Override
    public ListAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.listitem_main, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        final FarzanModel currentObj = farzanModelList.get(position);
        holder.setData(currentObj, position);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    @Override
    public int getItemCount() {
        return farzanModelList.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtDistance;
        private TextView txtAddress;

        int position;
        public FarzanModel current;

        myViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtDistance = itemView.findViewById(R.id.txtDistance);
            txtAddress = itemView.findViewById(R.id.txtAddress);

        }

        private void setData(FarzanModel current, int position) {

            this.txtName.setText(current.name);
            this.txtDistance.setText(current.distance + "");
            this.txtAddress.setText(current.formatterdAddress);

            this.position = position;
            this.current = current;

        }
    }
}

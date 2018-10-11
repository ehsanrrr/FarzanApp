package com.example.ehsanpc.farzanapp;

import android.os.AsyncTask;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ehsanpc.farzanapp.Adapters.ListAdapter;
import com.example.ehsanpc.farzanapp.Classes.ClassConnection;
import com.example.ehsanpc.farzanapp.Models.FarzanModel;
import com.example.ehsanpc.farzanapp.Web.WebService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        WebServiceCallBackList webServiceCallBackList = new WebServiceCallBackList();
        webServiceCallBackList.execute();


    }

    private void setUpRecyclerView(List<FarzanModel> list) {

        RecyclerView recyclerView = findViewById(R.id.recycler);

        ListAdapter adapter = new ListAdapter(this, list);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(this);
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private class WebServiceCallBackList extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        private List<FarzanModel> farzanModelList;
        ClassConnection classConnection;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            farzanModelList = new ArrayList<>();
            webService = new WebService();
            classConnection = new ClassConnection(MainActivity.this);
        }

        @Override
        protected Void doInBackground(Object... params) {

            farzanModelList = webService.getMainList(classConnection.isInternetOn());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (farzanModelList != null){
                if (farzanModelList.size() > 0){
                    setUpRecyclerView(farzanModelList);
                }
            }

        }

    }

}

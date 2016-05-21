package com.indigo.tag;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements CoordinateDialog.CoordinateDialogListener{

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recycler_coordinates) RecyclerView recyclerCoordinates;
    @BindView(R.id.fab_add_coordinate) FloatingActionButton fabAddCoordinate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupCoordinateRecycler();
    }

    private void setupCoordinateRecycler() {
        recyclerCoordinates.setLayoutManager(new LinearLayoutManager(this));
        recyclerCoordinates.setAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_settings:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab_add_coordinate)
    public void openAddCoordinateDialog(View view) {
        final CoordinateDialog coordinateDialog = new CoordinateDialog();
        coordinateDialog.show(getSupportFragmentManager(), "coordinates");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    private static class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
        private List<Location> mLocations;

        public LocationAdapter(List<Location> mLocations) {
            this.mLocations = mLocations;
        }

        @Override
        public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(LocationViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public static class LocationViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.) TextView mTextViewName;
            private TextView getmTextViewCoordinate;

            public LocationViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}

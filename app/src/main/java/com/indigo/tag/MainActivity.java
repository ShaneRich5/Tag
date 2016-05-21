package com.indigo.tag;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.indigo.tag.dialogs.LocationDialog;
import com.indigo.tag.models.Location;
import com.indigo.tag.views.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements LocationDialog.LocationDialogListener{

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recycler_coordinates) RecyclerView recyclerCoordinates;
    @BindView(R.id.fab_add_coordinate) FloatingActionButton fabAddCoordinate;

    private LocationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupCoordinateRecycler();
    }

    private void setupCoordinateRecycler() {
        mAdapter = new LocationAdapter(new ArrayList<Location>());
        recyclerCoordinates.setLayoutManager(new LinearLayoutManager(this));
        recyclerCoordinates.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerCoordinates.setAdapter(mAdapter);
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
        final LocationDialog locationDialog = LocationDialog.newInstance();
        locationDialog.show(getSupportFragmentManager(), "coordinates");
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    @Override
    public void onDialogPositiveClick(Location location) {
        mAdapter.addLocation(location);
    }

    public static class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
        private List<Location> mLocations;
        private OnItemClickListener mItemClickListener;

        public LocationAdapter(List<Location> locations, OnItemClickListener listener) {
            this.mLocations = locations;
            mItemClickListener = listener;
        }

        public LocationAdapter(List<Location> locations) {
            this(locations, null);
        }

        public void setOnClickListener(OnItemClickListener listener) {
            mItemClickListener = listener;
        }

        public void addLocation(Location location) {
            mLocations.add(location);
            notifyDataSetChanged();
        }

        @Override
        public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_location, parent, false);
            return new LocationViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(LocationViewHolder holder, int position) {
            Location currentLocation = mLocations.get(position);
            holder.mTextViewName.setText(currentLocation.getName());
            holder.mTextViewCoordinate.setText(currentLocation.getCoordinate().toString());
        }

        @Override
        public int getItemCount() {
            return mLocations.size();
        }

        public class LocationViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener{
            @BindView(R.id.text_view_name) TextView mTextViewName;
            @BindView(R.id.text_view_coordinates) TextView mTextViewCoordinate;

            public LocationViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) mItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }

        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }
    }
}

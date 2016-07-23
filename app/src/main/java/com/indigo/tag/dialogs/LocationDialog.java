package com.indigo.tag.dialogs;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.indigo.tag.models.Coordinate;
import com.indigo.tag.models.Location;

/**
 * Created by Shane on 5/20/2016.
 */
public class LocationDialog extends DialogFragment {

    public static final String TAG = "location_dialog";

    EditText editTextName, editTextLatitude, editTextLongitude;

    private static final String ARG_LOCATION = "location";
    private LocationDialogListener mListener;
    private Location mLocation;

    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    public interface LocationDialogListener {
        void onDialogPositiveClick(Location location);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    public static LocationDialog newInstance(Location location) {
        LocationDialog dialog = new LocationDialog();
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARG_LOCATION, location);
        dialog.setArguments(arguments);
        return dialog;
    }

    public static LocationDialog newInstance() {
        return new LocationDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) mLocation = getArguments()
                .getParcelable(ARG_LOCATION);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        editTextName = new EditText(getContext());
        editTextLatitude = new EditText(getContext());
        editTextLongitude = new EditText(getContext());

        editTextName.setInputType(InputType.TYPE_CLASS_TEXT);
        editTextLongitude.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editTextLatitude.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        editTextName.setHint("Name");
        editTextLongitude.setHint("Longitude");
        editTextLatitude.setHint("Latitude");

        if (null != mLocation) {
            final Coordinate coordinate = mLocation.getCoordinate();
            editTextName.setText(mLocation.getName());
            editTextLatitude.setText(String.valueOf(coordinate.getLatitude()));
            editTextLongitude.setText(String.valueOf(coordinate.getLongitude()));
        }

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(editTextName);
        linearLayout.addView(editTextLatitude);
        linearLayout.addView(editTextLongitude);

        builder.setTitle((mLocation == null) ? "New Location" : "Edit Location")
                .setView(linearLayout)
                .setPositiveButton("Save", (dialog, which) -> {
                    final double latitude = Double.parseDouble(editTextLatitude.getText().toString());
                    final double longitude = Double.parseDouble(editTextLongitude.getText().toString());

                    final Coordinate coordinate = new Coordinate(latitude, longitude);

                    mLocation = new Location(editTextName.getText().toString(), coordinate);

                    mListener.onDialogPositiveClick(mLocation);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    mListener.onDialogNegativeClick(LocationDialog.this);
                }).setNeutralButton("Refresh", (dialog, which) -> {

                });

        setupLocationService();

        return builder.create();
    }

    private void setupLocationService() {
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location newLocation) {
                final double latitude = newLocation.getLatitude();
                final double longitude = newLocation.getLongitude();

                editTextLatitude.setText(String.valueOf(latitude));
                editTextLongitude.setText(String.valueOf(longitude));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1234);
            }
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1234: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
                } else {
                    mListener.onDialogNegativeClick(this);
                }
                return;
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (LocationDialogListener) activity;
        } catch(ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement CoordinateDialogListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}

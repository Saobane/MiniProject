package com.example.saobane.mini_project;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Saobane on 10/12/2016.
 */

public class Route {


    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;
}

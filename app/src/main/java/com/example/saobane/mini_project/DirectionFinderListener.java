package com.example.saobane.mini_project;

import java.util.List;

/**
 * Created by Saobane on 10/12/2016.
 */
//Interface de la classe précédente
public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}

package org.clas.modules;

import java.util.Map;
import java.util.HashMap;


public class ALERTCalConstants {

    //List of ALERT calibration constants goes here
    Map< String,Float> ALERTCals  = new HashMap< String,Float>();

    public ALERTCalConstants() {
        System.out.println("Finished loading ALERT constants");
    }
}

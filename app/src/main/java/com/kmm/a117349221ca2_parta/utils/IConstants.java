package com.kmm.a117349221ca2_parta.utils;

import android.telephony.emergency.EmergencyNumber;

import com.kmm.a117349221ca2_parta.R;
import com.kmm.a117349221ca2_parta.covid.Covid;
import com.kmm.a117349221ca2_parta.heroCRUD.GeneralInfo;
import com.kmm.a117349221ca2_parta.heroCRUD.Hero;

import java.util.ArrayList;

public class IConstants {
    public static boolean isInternetConnected ;
    public static volatile boolean isNotConnected;

    public static volatile ArrayList<Covid> COVID_LIST;
    public static final int GETHEROESLOADERID = 1;
    public static final int DELETEHEROLOADERID = 2;
    public static final int UPDATEHEROLOADERID = 3;
    public static final int CREATEHEROLOADERID =4;
    public static final int GETCOVIDLOADERID = 5;
    public static volatile GeneralInfo generalInfo;

    public static final int ID_ADD =1;
    public static final int ID_HOME =2;
    public static  final int ID_COVID =3;






}

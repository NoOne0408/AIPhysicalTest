package com.example.mediapipeposetracking.ropeSkippingProject;

import android.os.Message;
import com.example.mediapipeposetracking.Point;

import com.example.mediapipeposetracking.MainActivity;

import java.util.TimerTask;

public class PoseTest {
    public static String keyMessage="rope skipping";
    public static int threshold_height_sub;
    public static int width=1080;
    public static int hight=2340;

    public PoseTest(int threshold_height_sub){
        PoseTest.threshold_height_sub=threshold_height_sub;
    }

    public boolean test(Point LShoulder,Point RShoulder){
        return true;


    }

}

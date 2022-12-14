package com.example.mediapipeposetracking.ropeSkippingProject;

import android.os.Message;
import com.example.mediapipeposetracking.Point;

import com.example.mediapipeposetracking.MainActivity;

import java.util.ArrayList;
import java.util.TimerTask;

public class RopeSkipping {
    PoseTest poseTest=new PoseTest(10);
    public static int count=0;
    public static int time=0;
    public static boolean ready=false;
    public static float mean=0;
    public static ArrayList<Float> RShoulderList=new ArrayList<>();
    //起始计数时间戳
    private static long max_time = 999999999;
    private static long start_time = max_time;
    private static long count_time = max_time;
    public static int width=1080;
    public static int hight=2340;
    Point RShoulder,LShoulder,RHip,LHip,RKeen,LKeen,RAnkle,LAnkle,Nose;
    Point RElbow,LElbow,RWrist,LWrist;
    Point RHeel,LHeel,RIndex,LIndex;

    public float upperbound =PoseTest.hight, lowerbound =0;
    public boolean flag_up=false,flag_down=false;


    public void recover(){
        count=0;
        time=0;
        RShoulderList.clear();
        System.out.println("变量初始化");
    }

    public void updatePoints(Point RShoulder, Point LShoulder, Point RHip, Point LHip, Point Nose,
                             Point RElbow, Point LElbow, Point RWrist, Point LWrist){
        this.RShoulder=RShoulder;
        this.LShoulder=LShoulder;
        this.RHip=RHip;
        this.LHip=LHip;
//        this.RKeen=RKeen;
//        this.LKeen=LKeen;
//        this.RAnkle=RAnkle;
//        this.LAnkle=LAnkle;
        this.Nose=Nose;

        this.RElbow=RElbow;
        this.LElbow=LElbow;
        this.RWrist=RWrist;
        this.LWrist=LWrist;

//        this.RHeel=RHeel;
//        this.LHeel=LHeel;
//        this.RIndex=RIndex;
//        this.LIndex=LIndex;
        RShoulderList.add(RShoulder.Y);
    }

    private float updateUpperDownSub(){
        if(upperbound >RShoulder.Y){
            upperbound =RShoulder.Y;
        }

        if (lowerbound <RShoulder.Y){
            lowerbound =RShoulder.Y;
        }

        float sub=Math.abs(lowerbound-upperbound);
        System.out.println("up: down:"+ upperbound +"  "+ lowerbound);
        System.out.println("差距："+sub);
        return sub;
    }

    private boolean testUpperbound(float mean,float sub){
        boolean flag=RShoulder.Y<=mean&&RShoulder.Y>=mean-sub;
//        System.out.println("上界："+flag);
        return flag;
    }

    private boolean testLowerbound(float mean,float sub){
        boolean flag= RShoulder.Y>=mean&&RShoulder.Y<=mean+sub;
//        System.out.println("下界："+flag);
        return flag;
    }
    private void updateParam(){
        flag_up=false;
        flag_down=false;
        //起始计数时间戳
        max_time = 999999999;
        start_time = max_time;
        count_time = max_time;
    }

    public void startDetection(long start_time){
        System.out.println("当前时间戳"+System.currentTimeMillis());
        System.out.println("当前平均值"+mean);
        time= (int) (System.currentTimeMillis()-start_time)/1000;

        float sub=100;
        boolean isStartFlag=testUpperbound(mean,sub/2);
        if (isStartFlag) {
            flag_up=true;
            start_time=System.currentTimeMillis();
        }

        boolean isEndFlag=testLowerbound(mean,sub/2);
        if (isEndFlag){
            flag_down=true;
            count_time=System.currentTimeMillis();
        }


        if (flag_down&&flag_up&&start_time<count_time){
            count++;
            updateParam();
        }



        MainActivity.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //使用handler发送消息
                Message message = new Message();
                MainActivity.mHandler.sendMessage(message);
            }
        },1000,1000);//每 1s执行一次


    }

}

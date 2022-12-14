package com.example.mediapipeposetracking.obliquePullUpsProject;

import com.example.mediapipeposetracking.Point;
import com.google.mediapipe.formats.proto.LandmarkProto;

public class ArmModule {
    public static Point[] getArmKeyPoints(LandmarkProto.NormalizedLandmarkList landmarkList) {

        //左右手肘
        LandmarkProto.NormalizedLandmark LElbowPoint=landmarkList.getLandmarkList().get(13);
        LandmarkProto.NormalizedLandmark RElbowPoint=landmarkList.getLandmarkList().get(14);
        Point LElbow=new Point(LElbowPoint.getX()*PoseTest.width,LElbowPoint.getY()*PoseTest.hight,LElbowPoint.getVisibility());
        Point RElbow=new Point(RElbowPoint.getX()*PoseTest.width,RElbowPoint.getY()*PoseTest.hight,RElbowPoint.getVisibility());

        //左右手腕
        LandmarkProto.NormalizedLandmark LWristPoint=landmarkList.getLandmarkList().get(15);
        LandmarkProto.NormalizedLandmark RWristPoint=landmarkList.getLandmarkList().get(16);
        Point LWrist = new Point(LWristPoint.getX()*PoseTest.width,LWristPoint.getY()*PoseTest.hight,LWristPoint.getVisibility());
        Point RWrist = new Point(RWristPoint.getX()*PoseTest.width,RWristPoint.getY()*PoseTest.hight,RWristPoint.getVisibility());

        Point[] arm_points={RWrist,LWrist,RElbow,LElbow};
        return arm_points;
    }

    public static boolean isArmStright(Point Wrist,Point Elbow,Point Shoulder,float threshold_arm_angle){
        float angle = utils.calAngle(Wrist,Elbow,Elbow,Shoulder);
        System.out.println("胳膊夹角: "+angle);
        if(angle<threshold_arm_angle){
            return true;
        }
        else return false;
    }
}

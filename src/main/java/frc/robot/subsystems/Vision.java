package frc.robot.subsystems;
import org.photonvision.PhotonCamera;

import org.photonvision.targeting.*;
import org.photonvision.*;
import org.photonvision.common.dataflow.*;
import org.photonvision.common.dataflow.structures.*;
import org.photonvision.common.networktables.*;
import org.photonvision.targeting.*;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;




public class Vision extends SubsystemBase{
    PhotonCamera camera;
    // AprilTagFieldLayout aprilTagFieldLayout = new AprilTagFieldLayout(AprilTagFields.k2023ChargedUp.m_resourceFile);

    public Vision(String cameraName){
      camera = new PhotonCamera(cameraName);
    }
    
    public void start(){
      var result = camera.getLatestResult();
        PhotonTrackedTarget target = result.getBestTarget();
      if(result.hasTargets()){
    
        double yaw = target.getYaw();
        SmartDashboard.putNumber("Yaw", yaw);
        double pitch = target.getPitch();
        SmartDashboard.putNumber("Pitch", pitch);
        double area = target.getArea();
        SmartDashboard.putNumber("Area", area);
        double skew = target.getSkew();
        SmartDashboard.putNumber("Skew", skew);
    }
  }
  public boolean HasTarget(){
    var result = camera.getLatestResult();
    return result.hasTargets(); 
  }
   public double getPitch(){
    var result = camera.getLatestResult();
    PhotonTrackedTarget target = result.getBestTarget();
    if(result.hasTargets()){
      SmartDashboard.putNumber("Pitch",target.getPitch());
      // System.out.println(target.getPitch());
      return target.getPitch();
    }
    else{
      return 0;}
    }
    public double getskew(){
      var result = camera.getLatestResult();
      PhotonTrackedTarget target = result.getBestTarget();
      if(result.hasTargets() == true){
        return target.getSkew();
      }
      else{
        return 0;}
      }

    public double getYaw(){
      var result = camera.getLatestResult();
      PhotonTrackedTarget target = result.getBestTarget();
      if(result.hasTargets() == true){
        return target.getYaw();
      }
      else {
        return 0;
      }
      
    } 

    public PhotonPipelineResult getResult(){
      return camera.getLatestResult();
    }

    // public Transform3d get3D(){
    // var result = camera.get();
    //   try {
    //     PhotonTrackedTarget target = result.getBestTarget();
    //     return target.getBestCameraToTarget();
    //   } catch(NullPointerException e){
    //     System.out.println("Bruh");
    //   }
    // }

    //Transform2d pose = target.getCameraToTarget();
    //List<TargetCorner> corners = target.getCorners();

    @Override
    public void periodic(){
        // SmartDashboard.putNumber("X3D", get3D().getX());
        // SmartDashboard.putNumber("Y3D", get3D().getY());
        // SmartDashboard.putNumber("Z3D", get3D().getZ());

    }
}
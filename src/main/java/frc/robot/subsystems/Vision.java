package frc.robot.subsystems;
import org.photonvision.PhotonCamera;

import org.photonvision.targeting.*;
import org.photonvision.*;
//import org.photonvision.common.dataflow.*;
import org.photonvision.common.dataflow.structures.*;
import org.photonvision.common.networktables.*;
import org.photonvision.targeting.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;




public class Vision extends SubsystemBase{
    PhotonCamera camera;

    public Vision(String cameraName){
      camera = new PhotonCamera(cameraName);
    }
    
    public void start(){
      var result = camera.getLatestResult();
        PhotonTrackedTarget target = result.getBestTarget();
      if(result.hasTargets() == true){
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
   public double gettilt(){
    var result = camera.getLatestResult();
    PhotonTrackedTarget target = result.getBestTarget();
    if(result.hasTargets() == true){
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

    public double getpos(){
      var result = camera.getLatestResult();
      PhotonTrackedTarget target = result.getBestTarget();
      if(result.hasTargets() == true){
        return target.getYaw();
      }
      else{
        return 0;
      }
      
    } 
    

    
    
    //Transform2d pose = target.getCameraToTarget();
    //List<TargetCorner> corners = target.getCorners();

    @Override
    public void periodic(){
        
    }
}
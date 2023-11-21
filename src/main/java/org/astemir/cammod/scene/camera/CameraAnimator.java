package org.astemir.cammod.scene.camera;



public class CameraAnimator {

    private CameraPath cameraPath = new CameraPath();

    public CameraPath getCameraPath() {
        return cameraPath;
    }

    public void clear(){
       cameraPath = new CameraPath();
       cameraPath.getPoints().clear();
    }
}

package org.astemir.cammod.scene.camera;


import org.astemir.api.client.animation.AnimationUtils;
import org.astemir.api.client.animation.KeyFrame;
import org.astemir.api.math.collection.Couple;
import org.astemir.api.math.components.Vector2;
import org.astemir.api.math.components.Vector3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class CameraPath {

    private CopyOnWriteArrayList<CameraPathPoint> points = new CopyOnWriteArrayList<>();


    public void add(Vector3 pos, Vector2 rot, float time, float speed, CameraEasing easing){
        float a = getLength() > 0 ? lastPoint().getTime() : 0;
        points.add(new CameraPathPoint(pos,rot,easing,a+time,speed));
    }

    public Couple<Vector3,Vector2> currentPosition(float time){
        List<KeyFrame> positions = new ArrayList<>();
        List<KeyFrame> rotations = new ArrayList<>();
        for (CameraPathPoint point : points) {
            positions.add(new KeyFrame(point.getTime(),point.getPosition()));
            rotations.add(new KeyFrame(point.getTime(),new Vector3(point.getRotation().x,point.getRotation().y,0)));
        }
        Vector3 position = AnimationUtils.interpolatePoints(positions.toArray(new KeyFrame[positions.size()]),time);
        Vector3 rotation = AnimationUtils.interpolatePoints(rotations.toArray(new KeyFrame[rotations.size()]),time);
        return new Couple<>(position,new Vector2(rotation.x,rotation.y));
    }

    public CameraPathPoint lastPoint(){
        return points.get(points.size()-1);
    }

    public boolean isEmpty(){
        return points.isEmpty();
    }

    public float getLength(){
        float length = 0;
        if (points != null) {
            for (CameraPathPoint point : points) {
                length+=point.getTime()*point.getSpeed();
            }
            return length;
        }else{
            return 0f;
        }
    }

    public CopyOnWriteArrayList<CameraPathPoint> getPoints() {
        return points;
    }
}

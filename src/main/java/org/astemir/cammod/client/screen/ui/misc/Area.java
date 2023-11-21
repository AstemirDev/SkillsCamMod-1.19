package org.astemir.cammod.client.screen.ui.misc;


import org.astemir.api.math.components.Vector2;

public class Area {

    private Vector2 position;
    private Vector2 size;

    public Area(Vector2 position, Vector2 size) {
        this.position = position;
        this.size = size;
    }

    public Area(float x, float y, float width, float height){
        this(new Vector2(x,y),new Vector2(width,height));
    }


    public boolean intersects(Area area){
        return contains(area.getBottomCornerPosition());
    }

    public Area intersection(Area area){
        float leftX = Math.max(getX(), area.getX() );
        float rightX = Math.min(getX() + getWidth(), area.getX() + area.getWidth() );
        float topY = Math.max(getY(), area.getY() );
        float bottomY = Math.min(getY() + getHeight(), area.getY() + area.getHeight() );
        if ( leftX < rightX && topY < bottomY ) {
            return new Area(leftX, topY, rightX-leftX, bottomY-topY );
        } else {
            return null;
        }
    }

    public boolean contains(Vector2 point){
        return contains(point.getX(),point.getY());
    }

    public boolean contains(float x,float y){
        if (x >= getX() && y >= getY() && x <= getX()+getWidth() && y <= getY()+getHeight()){
            return true;
        }
        return false;
    }

    public Vector2 getBottomCornerPosition(){
        return position.add(size);
    }

    public float getWidth(){
        return size.x;
    }

    public float getHeight(){
        return size.y;
    }

    public float getX(){
        return position.x;
    }

    public float getY(){
        return position.y;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getSize() {
        return size;
    }
}

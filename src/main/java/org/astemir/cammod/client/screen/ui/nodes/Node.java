package org.astemir.cammod.client.screen.ui.nodes;

import com.mojang.blaze3d.vertex.*;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.math.components.Color;
import org.astemir.api.math.components.Vector2;
import org.astemir.cammod.client.screen.ui.misc.*;
import org.astemir.cammod.client.screen.ui.misc.themes.UITheme;
import org.astemir.cammod.client.screen.ui.screens.NodeScreen;
import org.astemir.cammod.client.screen.ui.utils.RenderUtils;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Node{

    private Vector2 globalPosition = new Vector2(0,0);
    private Vector2 position = new Vector2(0,0);
    private Vector2 size = new Vector2(32,32);
    private Vector2 scale = new Vector2(1,1);
    private Color modulate = Color.WHITE;
    private boolean hidden = false;
    private boolean disabled = false;
    private boolean scissorsEnabled = false;
    private int layer = 0;
    private int ticks = 0;
    private UITheme theme = Themes.DEFAULT;

    private CopyOnWriteArrayList<Node> children = new CopyOnWriteArrayList<>();
    private Node parent;
    private NodeScreen screen;

    public void init(){}

    public void onRender(PoseStack poseStack, int mouseX, int mouseY, float partialTick){};

    public void onKeyPressed(int keyCode,int scanCode,int modifiers){};

    public void onKeyReleased(int keyCode,int scanCode,int modifiers){};

    public void onCharTyped(char codePoint,int modifiers){};

    public void onMouseClicked(double x,double y,int button){};

    public void onMouseReleased(double x,double y,int button){};

    public void onMouseDragged(double x,double y,double dragX, double dragY,int button){};

    public void onMouseScrolled(double x,double y,double delta){};

    public void onBeginRenderChildren(PoseStack stack, int mouseX, int mouseY, float partialTick){}

    public void onEndRenderChildren(PoseStack stack, int mouseX, int mouseY, float partialTick){}

    public void onTick(){}

    public void updateChild(Node node,float partialTick){
    }

    public void render(PoseStack stack, int mouseX, int mouseY, float partialTick){
        updateGlobalPosition();
        try {
            if (!isHidden()) {
                onRender(stack, mouseX, mouseY, partialTick);
                children.sort(Comparator.comparingInt(Node::getLayer));
                for (Node child : children) {
                    updateChild(child,partialTick);
                }
                renderChildrenThenSelf(stack,mouseX,mouseY,partialTick);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void renderChildrenThenSelf(PoseStack stack, int mouseX, int mouseY, float partialTick) {
        IClipChildren clip = null;
        if (this instanceof IClipChildren clipChildren){
            clip = clipChildren;
        }
        children.sort(Comparator.comparingInt(Node::getLayer));
        onBeginRenderChildren(stack,mouseX,mouseY,partialTick);
        if (clip != null){
            beginClip((int)clip.getClipOffset().x,(int)clip.getClipOffset().y);
        }
        for (Node child : children) {
            child.render(stack,mouseX,mouseY,partialTick);
        }
        onEndRenderChildren(stack,mouseX,mouseY,partialTick);
        if (clip != null){
            endClip();
        }
    }

    public void renderBeginTexture(){
        RenderUtils.begin(getTexture(),getModulate().r,getModulate().g,getModulate().b,getModulate().a);
    }

    public void renderBegin(){
        RenderUtils.begin(getModulate().r,getModulate().g,getModulate().b,getModulate().a);
    }

    public void renderEnd(){
        RenderUtils.end();
    }

    public void tick(){
        ticks++;
        for (Node child : children) {
            child.tick();
        }
    }

    public void mouseClicked(double x,double y,int button){
        if (!isDisabled()) {
            for (Node child : children) {
                child.mouseClicked(x, y, button);
            }
            onMouseClicked(x,y,button);
        }
    }

    public void mouseReleased(double x,double y,int button){
        if (!isDisabled()) {
            for (Node child : children) {
                child.mouseReleased(x, y, button);
            }
            onMouseReleased(x,y,button);
        }
    }

    public void mouseDragged(double x,double y,double dragX, double dragY,int button){
        if (!isDisabled()) {
            for (Node child : children) {
                child.mouseDragged(x, y, dragX, dragY, button);
            }
            onMouseDragged(x,y,dragX,dragY,button);
        }
    }

    public void mouseScrolled(double x,double y,double delta){
        if (!isDisabled()) {
            for (Node child : children) {
                child.mouseScrolled(x, y, delta);
            }
            onMouseScrolled(x,y,delta);
        }
    }

    public void keyPressed(int keyCode,int scanCode,int modifiers){
        if (!isDisabled()) {
            for (Node child : children) {
                child.keyPressed(keyCode, scanCode, modifiers);
            }
            onKeyPressed(keyCode,scanCode,modifiers);
        }
    }

    public void keyReleased(int keyCode,int scanCode,int modifiers){
        if (!isDisabled()) {
            for (Node child : children) {
                child.keyReleased(keyCode, scanCode, modifiers);
            }
            onKeyReleased(keyCode,scanCode,modifiers);
        }
    }

    public void charTyped(char codePoint,int modifiers){
        if (!isDisabled()) {
            for (Node child : children) {
                child.charTyped(codePoint, modifiers);
            }
            onCharTyped(codePoint,modifiers);
        }
    }

    public void addChild(Node node){
        node.setParent(this);
        node.setScreen(screen);
        node.init();
        node.updateGlobalPosition();
        updateChildren();
        this.children.add(node);
    }

    public void removeChild(Node node){
        children.remove(node);
        updateChildren();
    }

    public Vector2 calculateGlobalPosition(){
        Node parent = getParent();
        if (parent != null){
            return parent.positionForChild(this,parent.getGlobalPosition(),getPosition());
        }else{
            return position;
        }
    }

    public Vector2 positionForChild(Node child,Vector2 globalPosition,Vector2 position){
        return globalPosition.add(position);
    }

    public void updateChildren(){};

    public void updateGlobalPosition(){
        globalPosition = calculateGlobalPosition();
    }

    public void setScreen(NodeScreen screen) {
        this.screen = screen;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node setPosition(Vector2 position) {
        this.position = position;
        return this;
    }

    public void setScale(Vector2 scale) {
        this.scale = scale;
    }

    public Node setSize(Vector2 size) {
        this.size = size;
        return this;
    }

    public void setTheme(UITheme theme) {
        this.theme = theme;
    }

    public void setModulate(Color modulate) {
        this.modulate = modulate;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getScale() {
        return scale;
    }

    public Vector2 getSize() {
        return size;
    }

    public Vector2 getRealSize(){
        return size.mul(scale);
    }

    public Color getModulate() {
        return modulate;
    }

    public Node getParent() {
        return parent;
    }

    public boolean isHidden() {
        return hidden;
    }

    public Vector2 getGlobalPosition() {
        return globalPosition;
    }

    public Area getArea() {
        return new Area(getGlobalPosition(),getSize().mul(getScale()));
    }

    public boolean isDisabled() {
        return disabled;
    }

    public NodeScreen getScreen() {
        if (screen != null) {
            return screen;
        }else{
            if (getParent() != null){
                return getParent().getScreen();
            }
        }
        return null;
    }

    public Vector2 getMaxCountLimitFor(float width,float height){
        int maxElementsHorizontal = (int) (getSize().x/width);
        int maxElementsVertical = (int) (getSize().y/height);
        return new Vector2(maxElementsHorizontal,maxElementsVertical);
    }

    public Vector2 getMaxCountLimitFor(AtlasTexture atlas,String textureName){
        Area texture = atlas.getTexture(textureName);
        return getMaxCountLimitFor(texture.getWidth(),texture.getHeight());
    }

    public void beginClip(int offsetX,int offsetY){
        Scissors.pushScissor(getScreen().getMinecraft().getWindow(),(int) getArea().getX()+offsetX,(int)getArea().getY()+offsetY,(int)getArea().getWidth()-(offsetX*2),(int)getArea().getHeight()-(offsetY*2));
    }

    public void beginClip(Area area,int offsetX,int offsetY){
        Scissors.pushScissor(getScreen().getMinecraft().getWindow(),(int) area.getX()+offsetX,(int)area.getY()+offsetY,(int)area.getWidth()-(offsetX*2),(int)area.getHeight()-(offsetY*2));
    }

    public void beginClip(int x,int y,int width,int height){
        Scissors.pushScissor(getScreen().getMinecraft().getWindow(),x,y,width,height);
    }

    public void endClip(){
        Scissors.popScissor(getScreen().getMinecraft().getWindow());
    }


    public UITheme getTheme() {
        return theme;
    }

    public ResourceLocation getTexture(){
        return getTheme().getTexture();
    }

    public AtlasTexture getAtlas(UIProperty property){
        return theme.getAtlases(getClass()).get(property);
    }

    public AtlasTexture getAtlas(){
        return theme.getAtlases(getClass()).get(UIProperty.DEFAULT);
    }

    public List<Node> getChildren() {
        return children;
    }

    public int getTicks() {
        return ticks;
    }
}

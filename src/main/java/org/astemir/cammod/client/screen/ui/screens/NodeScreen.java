package org.astemir.cammod.client.screen.ui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.astemir.api.math.components.Vector2;
import org.astemir.cammod.client.screen.ui.nodes.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class NodeScreen extends Screen {

    private Screen lastScreen;
    private Minecraft minecraft;
    private List<Node> nodes = new ArrayList<>();
    private Class<?> functionalClass;
    private Vector2 size;
    private long ticks = 0;



    public NodeScreen(Screen lastScreen,Component title,Vector2 size) {
        super(title);
        this.lastScreen = lastScreen;
        this.size = size;
        this.minecraft = Minecraft.getInstance();
    }


    @Override
    protected void init() {
        nodes.clear();
    }

    public void addNode(Node node){
        nodes.add(node);
        node.setScreen(this);
        node.init();
    }

    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public void tick() {
        for (Node node : nodes) {
            node.tick();
        }
    }

    @Override
    public void removed() {

    }

    @Override
    public void renderBackground(PoseStack pPoseStack) {
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        try {
            System.out.println(poseStack.last());
            super.render(poseStack,mouseX,mouseY,partialTick);
            ticks++;
            nodes.sort(Comparator.comparingInt(Node::getLayer));
            for (Node node : nodes){
                node.render(poseStack,mouseX,mouseY,partialTick);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        for (Node node : nodes) {
            node.mouseClicked(pMouseX,pMouseY,pButton);
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        for (Node node : nodes) {
            node.mouseReleased(pMouseX,pMouseY,pButton);
        }
        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        for (Node node : nodes) {
            node.mouseDragged(pMouseX,pMouseY,pDragX,pDragY,pButton);
        }
        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        for (Node node : nodes) {
            node.mouseScrolled(pMouseX,pMouseY,pDelta);
        }
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        for (Node node : nodes) {
            node.keyPressed(pKeyCode,pScanCode,pModifiers);
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public boolean keyReleased(int pKeyCode, int pScanCode, int pModifiers) {
        for (Node node : nodes) {
            node.keyReleased(pKeyCode,pScanCode,pModifiers);
        }
        return super.keyReleased(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public boolean charTyped(char pCodePoint, int pModifiers) {
        for (Node node : nodes) {
            node.charTyped(pCodePoint,pModifiers);
        }
        return super.charTyped(pCodePoint, pModifiers);
    }

    public void fitNode(Node node){
        node.setSize(getSize());
    }

    public void centerNode(Node node){
        node.setPosition(new Vector2((this.width/2)-getSize().x/2,(this.height/2)-getSize().y/2));
    }

    public List<Node> getAllNodes(){
        List<Node> list = new ArrayList<>();
        for (Node node : nodes) {
            for (Node child : node.getChildren()) {
                list.add(child);
            }
        }
        return list;
    }

    public Node getMostBottomNode(){
        Node res = null;
        for (Node node : getAllNodes()) {
            if (res == null){
                res = node;
            }else{
                if (res.getArea().getBottomCornerPosition().y < node.getArea().getBottomCornerPosition().y){
                    return res;
                }
            }
        }
        return res;
    }

    public Node getMostLeftNode(){
        Node res = null;
        for (Node node : getAllNodes()) {
            if (res == null){
                res = node;
            }else{
                if (res.getArea().getBottomCornerPosition().x < node.getArea().getBottomCornerPosition().x){
                    return res;
                }
            }
        }
        return res;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }

    public Vector2 getSize() {
        return size;
    }


    public Class<?> getFunctionalClass() {
        return functionalClass;
    }

    public void setFunctionalClass(Class<?> functionalClass) {
        this.functionalClass = functionalClass;
    }

    @Override
    public void onClose() {
        minecraft.setScreen(lastScreen);
    }

    public long getTicks() {
        return ticks;
    }
}

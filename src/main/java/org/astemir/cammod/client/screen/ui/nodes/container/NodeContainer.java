package org.astemir.cammod.client.screen.ui.nodes.container;

import org.astemir.api.math.components.Vector2;
import org.astemir.cammod.client.screen.ui.nodes.IClipChildren;
import org.astemir.cammod.client.screen.ui.nodes.Node;

public abstract class NodeContainer extends Node implements IClipChildren {

    private Vector2 childrenClipOffset = new Vector2(0,0);
    private boolean fitParent = false;

    @Override
    public void addChild(Node node) {
        super.addChild(node);
    }

    @Override
    public void setParent(Node parent) {
        super.setParent(parent);
        if (fitParent) {
            setSize(parent.getSize());
        }
    }

    @Override
    public Vector2 getClipOffset() {
        return childrenClipOffset;
    }

    public void setClipOffset(Vector2 childrenClipOffset) {
        this.childrenClipOffset = childrenClipOffset;
    }

    public boolean isFitParent() {
        return fitParent;
    }

    public void setFitParent(boolean fitParent) {
        this.fitParent = fitParent;
    }
}

package org.astemir.cammod.client.screen.ui.nodes.container;

import org.astemir.api.math.components.Vector2;
import org.astemir.cammod.client.screen.ui.nodes.Node;

public class VBoxNodeContainer extends NodeContainer{

    private Vector2 maxSize = new Vector2(0,0);
    private Vector2 cursorPosition = new Vector2(0,0);

    @Override
    public void addChild(Node node) {
        super.addChild(node);
        sortChildren();
    }

    public void sortChildren(){
        Vector2 pos = new Vector2(0,0);
        Vector2 size = getRealSize();
        for (Node child : getChildren()) {
            Vector2 newSize = new Vector2(size.x,child.getRealSize().y);
            child.setSize(newSize);
            child.setPosition(pos);
            pos = pos.add(new Vector2(0, newSize.y));
            maxSize = pos;
        }
    }

    public Vector2 getCursorPosition() {
        return cursorPosition;
    }

    public void setCursorPosition(Vector2 cursorPosition) {
        this.cursorPosition = cursorPosition;
    }

    @Override
    public Vector2 positionForChild(Node child, Vector2 globalPosition, Vector2 position) {
        return super.positionForChild(child, globalPosition, position).sub(cursorPosition);
    }

    public Vector2 getMaxSize() {
        return maxSize;
    }
}

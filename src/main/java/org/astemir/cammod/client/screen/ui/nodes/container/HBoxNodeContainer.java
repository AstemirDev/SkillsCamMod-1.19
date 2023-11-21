package org.astemir.cammod.client.screen.ui.nodes.container;

import org.astemir.api.math.components.Vector2;
import org.astemir.cammod.client.screen.ui.nodes.Node;

public class HBoxNodeContainer extends NodeContainer{

    private Vector2 maxSize = new Vector2(0,0);

    @Override
    public void addChild(Node node) {
        super.addChild(node);
        sortChildren();
    }


    public void sortChildren(){
        Vector2 pos = new Vector2(0,0);
        Vector2 size = getRealSize();
        for (Node child : getChildren()) {
            Vector2 newSize = new Vector2(child.getRealSize().x,size.y);
            child.setSize(newSize);
            child.setPosition(pos);
            pos = pos.add(new Vector2(newSize.x, 0));
            maxSize = pos;
        }
    }

    public Vector2 getMaxSize() {
        return maxSize;
    }
}

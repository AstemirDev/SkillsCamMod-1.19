package org.astemir.cammod.client.screen.ui.nodes.container;

import org.astemir.api.math.components.Vector2;
import org.astemir.cammod.client.screen.ui.nodes.Node;

public class VHBoxNodeContainer extends NodeContainer{

    @Override
    public void addChild(Node node) {
        super.addChild(node);
        sortChildren();
    }

    public void sortChildren(){
        Vector2 pos = new Vector2(0,0);
        for (Node child : getChildren()) {
            Vector2 newSize = new Vector2(child.getRealSize().x,child.getRealSize().y);
            child.setSize(newSize);
            child.setPosition(pos);
            pos.add(new Vector2(newSize.x, newSize.y));
        }
    }
}

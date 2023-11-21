package org.astemir.cammod.client.screen.ui.nodes.panel;

import org.astemir.api.math.components.Vector2;
import org.astemir.cammod.client.screen.ui.nodes.Node;
import org.astemir.cammod.client.screen.ui.nodes.SliderVertical;

public class ScrollableWindow extends Window {

    private SliderVertical slider = new SliderVertical();

    public ScrollableWindow() {
        setShowBorders(false);
    }

    @Override
    public void init() {
        super.init();
        slider.setPosition(new Vector2(getSize().x+4-getPartSize().x*2,getPartSize().y-4));
        slider.setSize(new Vector2(32,getSize().y+8-getPartSize().y*2));
        addChild(slider);
    }

    @Override
    public Vector2 positionForChild(Node child, Vector2 globalPosition, Vector2 position){
        float minY = 270;
        if (child != slider) {
            return globalPosition.add(position).add(new Vector2(0, (int) (-slider.getValue() * minY)));
        }
        return super.positionForChild(child, globalPosition, position);
    }

    @Override
    public Vector2 getRenderAreaOffset() {
        return super.getRenderAreaOffset();
    }

    @Override
    public boolean isShowBorders() {
        return true;
    }
}

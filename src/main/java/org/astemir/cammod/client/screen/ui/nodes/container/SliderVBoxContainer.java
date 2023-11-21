package org.astemir.cammod.client.screen.ui.nodes.container;

import org.astemir.api.math.components.Vector2;
import org.astemir.cammod.client.screen.ui.event.NodeEvent;
import org.astemir.cammod.client.screen.ui.nodes.SliderVertical;

public class SliderVBoxContainer extends NodeContainer{


    private VBoxNodeContainer childrenContainer = new VBoxNodeContainer();
    private SliderVertical sliderVertical = new SliderVertical();

    public SliderVBoxContainer() {
        childrenContainer.setFitParent(true);
        sliderVertical.onSliderDrag = new NodeEvent.Function<Float>() {
            @Override
            public void onHandle(Float value) {
                float percent = value;
                childrenContainer.setCursorPosition(new Vector2(0,childrenContainer.getMaxSize().y*percent));
            }
        };
        addChild(childrenContainer);
        addChild(sliderVertical);
    }


    public VBoxNodeContainer getChildrenContainer() {
        return childrenContainer;
    }
}

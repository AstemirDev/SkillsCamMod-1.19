package org.astemir.cammod.client.screen.ui.nodes.progress;

public enum ProgressBarStyle {

    FLAT("fill_flat"),
    SEGMENTED_5x("fill_segmented_5x"),
    SEGMENTED_2x("fill_segmented_2x"),
    SEGMENTED_1x("fill_segmented_1x");

    private String id;

    ProgressBarStyle(String id) {
        this.id = id;
    }


    public String getId() {
        return id;
    }
}

package org.astemir.cammod.scene.building;

import net.minecraft.world.level.Level;

import java.util.LinkedList;

public class BlockChangeStory {

    private LinkedList<BlockChange> blockChanges = new LinkedList<>();

    public void add(BlockChange change){
        this.blockChanges.add(change);
    }

    public void apply(Level level){
        for (BlockChange blockChange : blockChanges) {
            blockChange.invoke(level);
        }
    }

    public void revert(Level level){
        for (BlockChange blockChange : blockChanges) {
            blockChange.revertChanges(level);
        }
    }

    public boolean isEmpty(){
        return blockChanges.isEmpty();
    }

    public void clear(){
        blockChanges.clear();
    }
}

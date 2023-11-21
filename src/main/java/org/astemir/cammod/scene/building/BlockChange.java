package org.astemir.cammod.scene.building;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BlockChange {

    private BlockPos pos;

    private BlockState state;

    private BlockState previousState;

    private boolean isDestroy = true;

    private boolean hasDrop = false;

    public BlockChange(BlockPos pos) {
        this.pos = pos;
        isDestroy = true;
    }

    public BlockChange(BlockPos pos, BlockState state) {
        this.pos = pos;
        this.state = state;
        isDestroy = false;
    }


    public void invoke(Level level){
        if (isDestroy){
            level.destroyBlock(pos,hasDrop);
        }else{
            if (state != null) {
                level.setBlock(pos, state, 2);
            }
        }
    }

    public void revertChanges(Level level){
        if (isDestroy){
            level.setBlock(pos,previousState,2);
        }else{
            level.setBlock(pos, Blocks.AIR.defaultBlockState(),2);
        }
    }

    public BlockChange hasDrop(){
        this.hasDrop = true;
        return this;
    }

    public BlockChange previousState(BlockState previousState) {
        this.previousState = previousState;
        return this;
    }

    public BlockPos getPos() {
        return pos;
    }

    public BlockState getState() {
        return state;
    }

    public boolean isDestroy() {
        return isDestroy;
    }
}

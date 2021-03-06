package com.fredtargaryen.floocraft.block;

import com.fredtargaryen.floocraft.DataReference;
import com.fredtargaryen.floocraft.FloocraftBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

import static net.minecraft.state.properties.BlockStateProperties.AGE_0_15;

//import com.fredtargaryen.floocraft.tileentity.TileEntityMirageFire;

public class GreenFlamesIdle extends GreenFlamesBase {
    private static final VoxelShape SMALLBOX = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

	public GreenFlamesIdle() { super(12); }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) { return SMALLBOX; }

    @Override
    public void tick(BlockState state, ServerWorld w, BlockPos pos, Random rand) {
        if(w.getClosestPlayer((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, (double) DataReference.FLOO_FIRE_DETECTION_RANGE, false) != null)
        {
            w.setBlockState(pos, FloocraftBase.GREEN_FLAMES_BUSY.getDefaultState().with(AGE_0_15, state.get(AGE_0_15)));
        }
        super.tick(state, w, pos, rand);
    }

    ////////////////////////
    //MIRAGE COMPATIBILITY//
    ////////////////////////
//    @Override
//    public TileEntity createTileEntity(World world, BlockState state) {
//        TileEntityMirageFire temf = null;
//        if(FloocraftBase.isMirageInstalled())
//        {
//            temf = new TileEntityMirageFire();
//            temf.setRadius(6.0F);
//        }
//        return temf;
//        return null;
//    }
}

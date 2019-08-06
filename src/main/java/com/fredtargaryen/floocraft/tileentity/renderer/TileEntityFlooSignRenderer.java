package com.fredtargaryen.floocraft.tileentity.renderer;

import com.fredtargaryen.floocraft.block.FlooSignBlock;
import com.fredtargaryen.floocraft.tileentity.FireplaceTileEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class TileEntityFlooSignRenderer extends TileEntityRenderer<FireplaceTileEntity> {
    @Override
    public void render(FireplaceTileEntity sign, double x, double y, double z, float partialTicks, int destroyStage){
        GL11.glPushMatrix();
        float f1 = 0.6666667F;
        float f2;

        Direction i = sign.getBlockState().get(FlooSignBlock.FACING);
        f2 = 0.0F;

        if (i == Direction.NORTH) {
            f2 = 180.0F;
        }

        if (i == Direction.WEST) {
            f2 = 90.0F;
        }

        if (i == Direction.EAST) {
            f2 = -90.0F;
        }

        GL11.glTranslatef((float)x + 0.5F, (float)y + 0.75F * f1, (float)z + 0.5F);
        GL11.glRotatef(-f2, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(0.0F, -0.3125F, -0.4375F);

        FontRenderer fontrenderer = this.getFontRenderer();
        f2 = 0.016666668F * f1;
        GL11.glTranslatef(0.0F, 0.5F * f1, 0.07F * f1);
        GL11.glScalef(f2, -f2, f2);
        GL11.glNormal3f(0.0F, 0.0F, -1.0F * f2);
        GL11.glDepthMask(false);
        byte b0 = 0;

        for (int j = 0; j < sign.signText.length; ++j) {
            String s = sign.signText[j];

            if (j == sign.getLineBeingEdited()) {
                s = "> " + s + " <";
                fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, j * 10 - sign.signText.length * 5, b0);
            }
            else {
                fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, j * 10 - sign.signText.length * 5, b0);
            }
        }

        GL11.glDepthMask(true);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }
}
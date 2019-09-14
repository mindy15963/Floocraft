package com.fredtargaryen.floocraft.client.gui;

import com.fredtargaryen.floocraft.DataReference;
import com.fredtargaryen.floocraft.FloocraftBase;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;

@Mod.EventBusSubscriber(modid = DataReference.MODID, value = Dist.CLIENT)
public class Flash {
    private double ticks;
    private double timeScaledTicksAngle;
    private Minecraft minecraft;
    private static final ResourceLocation texloc = new ResourceLocation(DataReference.MODID, "textures/gui/flash.png");
    private TextureManager textureManager;
    private long startTime;
    private double xBend;
    private double yBend;
    private double zBend;
	
    public Flash(){
        this.ticks = -1;
    }

    public void start() {
        if(this.ticks == -1) {
            this.ticks = 0;
            this.minecraft = Minecraft.getInstance();
            this.textureManager = this.minecraft.getTextureManager();
            MinecraftForge.EVENT_BUS.register(this);
            Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(FloocraftBase.TP, 1.0F));
            this.startTime = System.currentTimeMillis();
            this.xBend = Math.random() - 0.5;
            this.yBend = Math.random() - 0.5;
            this.zBend = Math.random() - 0.5;
        }
    }

    @SubscribeEvent
    public void flash(TickEvent.RenderTickEvent event) {
        if(event.phase == TickEvent.Phase.END) {
            //this.ticks += 5;
            this.ticks = System.currentTimeMillis() - this.startTime;
            this.timeScaledTicksAngle = Math.toRadians(this.ticks * 90 / 1000.0);
            GlStateManager.disableAlphaTest();
            GlStateManager.disableDepthTest();
            GlStateManager.depthMask(false);
            GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, (float) Math.cos(this.timeScaledTicksAngle));
            this.textureManager.bindTexture(texloc);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            double width = this.minecraft.mainWindow.getScaledWidth();
            double height = this.minecraft.mainWindow.getScaledHeight();
            bufferbuilder.pos(width, height, -90.0).tex(1.0, 1.0).endVertex();
            bufferbuilder.pos(width, 0.0, -90.0).tex(1.0, 0.0).endVertex();
            bufferbuilder.pos(0.0, 0.0, -90.0).tex(0.0, 0.0).endVertex();
            bufferbuilder.pos(0.0, height, -90.0).tex(0.0, 1.0).endVertex();
            tessellator.draw();
            GlStateManager.depthMask(true);
            GlStateManager.enableDepthTest();
            GlStateManager.enableAlphaTest();
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        }
        if(this.ticks > 999) {
            this.ticks = -1;
            MinecraftForge.EVENT_BUS.unregister(this);
        }
    }

    public void handleCamera() {
        if(this.ticks != -1) {
            GlStateManager.translated(
                    this.xBend * Math.sin(this.timeScaledTicksAngle * 2),
                    this.yBend * Math.sin(this.timeScaledTicksAngle * 2),
                    this.zBend * Math.sin(this.timeScaledTicksAngle * 2));
            GlStateManager.rotated(this.zBend * Math.sin(this.timeScaledTicksAngle * 2) * 45, 0.0D, 0.0, 1.0);
        }
    }
}

package com.carlettos.roninmod.r0n1n;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class R0n1nRender extends BipedRenderer<R0n1nEntity, R0n1nModelo>{

	public R0n1nRender(EntityRendererManager rendererManager) {
		super(rendererManager, new R0n1nModelo(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(R0n1nEntity entity) {
		return new ResourceLocation("roninmod", "ronin.png");
	}
	
	@Override
	protected void applyRotations(R0n1nEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw,
			float partialTicks) {
		super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
	}
}

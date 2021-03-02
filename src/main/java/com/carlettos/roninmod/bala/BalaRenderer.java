package com.carlettos.roninmod.bala;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BalaRenderer extends EntityRenderer<BalaEntity> {
	private static final ResourceLocation BALA_TEXTURES = new ResourceLocation("roninmod", "bala.png");
	private final BalaModel model = new BalaModel();

	public BalaRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public void render(BalaEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		matrixStackIn.push();
		matrixStackIn.translate(0, -1.3, 0);
		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.model.getRenderType(BALA_TEXTURES));
		this.model.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		this.model.setRotationAngles(entityIn, 0, 0, 0, entityIn.getYaw(partialTicks), entityIn.getPitch(partialTicks));
		matrixStackIn.pop();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getEntityTexture(BalaEntity entity) {
		return BALA_TEXTURES;
	}
}

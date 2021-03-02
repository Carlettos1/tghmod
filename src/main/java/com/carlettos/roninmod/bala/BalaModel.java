package com.carlettos.roninmod.bala;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BalaModel extends SegmentedModel<BalaEntity> {
	private final ModelRenderer bb_main;

	public BalaModel() {
		textureWidth = 16;
		textureHeight = 16;

		bb_main = new ModelRenderer(this);		
		bb_main.setRotationPoint(0.0F, 22.5F, -0.875F);
		bb_main.setTextureOffset(0, 12).addBox(-1.0F, -0.5F, -1.125F, 2.0F, 1.0F, 3.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 0).addBox(-0.5F, 0.0F, -1.125F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 0).addBox(-0.5F, -1.0F, -1.125F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		bb_main.setTextureOffset(3, 10).addBox(-0.5F, -0.5F, -1.625F, 1.0F, 1.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(BalaEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
		this.bb_main.rotateAngleX = - headPitch * ((float) Math.PI / 180F) + (float)Math.PI;
		this.bb_main.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(this.bb_main);
	}
}

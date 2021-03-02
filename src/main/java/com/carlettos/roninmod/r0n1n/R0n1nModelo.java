package com.carlettos.roninmod.r0n1n;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.item.ShootableItem;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class R0n1nModelo extends BipedModel<R0n1nEntity> {
	private final ModelRenderer manoderecha1;
	private final ModelRenderer manoderecha2;
	private final ModelRenderer cola;
	private final ModelRenderer cabeza;
	private final ModelRenderer orejaderecha;
	private final ModelRenderer orejaizquierda;
	private final ModelRenderer marca3_r1;
	private final ModelRenderer marca2_r1;
	private final ModelRenderer marca1_r1;
	public static float max;

	public R0n1nModelo() {
		super(0);
		textureWidth = 32;
		textureHeight = 48;

		this.bipedRightArm = new ModelRenderer(this, 8, 36);
		this.bipedRightArm.setRotationPoint(-4, 8, 0);
		this.bipedRightArm.addBox(-2.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);

		this.manoderecha1 = new ModelRenderer(this, 12, 0);
		this.manoderecha1.setRotationPoint(-2, 7, 0);
		this.bipedRightArm.addChild(manoderecha1);
		this.manoderecha1.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);

		this.manoderecha2 = new ModelRenderer(this, 12, 4);
		this.manoderecha2.setRotationPoint(0, 7, 0);
		this.bipedRightArm.addChild(manoderecha2);
		this.manoderecha2.addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);

		this.bipedLeftArm = new ModelRenderer(this);
		this.bipedLeftArm.setRotationPoint(4.0F, 8.0F, 0.0F);
		this.bipedLeftArm.setTextureOffset(0, 36).addBox(0.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);
		this.bipedLeftArm.setTextureOffset(8, 13).addBox(-0.5F, 7.0F, -1.5F, 3.0F, 2.0F, 3.0F, 0.0F, false);

		this.bipedLeftLeg = new ModelRenderer(this);
		this.bipedLeftLeg.setRotationPoint(2.0F, 14.0F, 0.0F);
		this.bipedLeftLeg.setTextureOffset(16, 36).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);
		this.bipedLeftLeg.setTextureOffset(0, 2).addBox(-1.0F, 8.0F, -3.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);

		this.bipedRightLeg = new ModelRenderer(this);
		this.bipedRightLeg.setRotationPoint(-2.0F, 14.0F, 0.0F);
		this.bipedRightLeg.setTextureOffset(24, 36).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);
		this.bipedRightLeg.setTextureOffset(0, 2).addBox(-1.0F, 8.0F, -3.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);

		this.cola = new ModelRenderer(this);
		this.cola.setRotationPoint(0.0F, 12.5F, 2.0F);
		this.cola.setTextureOffset(10, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 9.0F, 0.0F, false);
		this.cola.setTextureOffset(28, 6).addBox(-0.5F, -2.0F, 0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.cola.setTextureOffset(28, 4).addBox(-0.5F, -2.0F, 2.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.cola.setTextureOffset(28, 2).addBox(-0.5F, -2.0F, 4.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.cola.setTextureOffset(28, 0).addBox(-0.5F, -2.0F, 6.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.cola.setTextureOffset(24, 6).addBox(-0.5F, -0.5F, 8.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		this.bipedBody = new ModelRenderer(this, 8, 24);
		this.bipedBody.setRotationPoint(0.0F, 10.0F, 0.0F);
		this.bipedBody.addBox(-4.0F, -4.0F, -2.0F, 8.0F, 8.0F, 4.0F, 0.0F, false);

		this.bipedHead = new ModelRenderer(this);
		this.bipedHead.setRotationPoint(0.0F, 6.0F, 0.0F);

		this.cabeza = new ModelRenderer(this, 8, 12);
		this.cabeza.setRotationPoint(0.0F, 18.0F, 0.0F);
		this.bipedHead.addChild(cabeza);
		this.cabeza.addBox(-3.0F, -24.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);

		this.orejaderecha = new ModelRenderer(this, 0, 24);
		this.orejaderecha.setRotationPoint(-2.0F, -6.0F, 0.5F);
		this.bipedHead.addChild(orejaderecha);
		setRotationAngle(orejaderecha, 0.0F, 0.0F, -0.1309F);
		this.orejaderecha.addBox(-1.0F, -7.5F, -0.5F, 2.0F, 8.0F, 1.0F, 0.0F, false);

		this.orejaizquierda = new ModelRenderer(this, 0, 15);
		this.orejaizquierda.setRotationPoint(2.0F, -6.0F, 0.5F);
		this.bipedHead.addChild(orejaizquierda);
		setRotationAngle(orejaizquierda, 0.0F, 0.0F, 0.1309F);
		this.orejaizquierda.addBox(-1.0F, -7.5F, -0.5F, 2.0F, 8.0F, 1.0F, 0.0F, false);

		marca3_r1 = new ModelRenderer(this);
		marca3_r1.setRotationPoint(1.3933F, -7.4896F, 0.0F);
		orejaizquierda.addChild(marca3_r1);
		setRotationAngle(marca3_r1, 0.0F, 0.0F, -1.3526F);
		marca3_r1.setTextureOffset(4, 11).addBox(-0.7243F, -0.5249F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		marca2_r1 = new ModelRenderer(this);
		marca2_r1.setRotationPoint(0.5F, -8.0F, 0.0F);
		orejaizquierda.addChild(marca2_r1);
		setRotationAngle(marca2_r1, 0.0F, 0.0F, -0.829F);
		marca2_r1.setTextureOffset(4, 11).addBox(-0.6682F, 0.0116F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		marca1_r1 = new ModelRenderer(this);
		marca1_r1.setRotationPoint(1.1291F, -6.8404F, 0.0F);
		orejaizquierda.addChild(marca1_r1);
		setRotationAngle(marca1_r1, 0.0F, 0.0F, -1.8326F);
		marca1_r1.setTextureOffset(0, 10).addBox(-0.7452F, -0.4568F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		this.bipedHeadwear = new ModelRenderer(this, 0, 33);
		this.bipedHeadwear.setRotationPoint(0.0F, 18.0F, 0.0F);
		this.bipedHead.addChild(bipedHeadwear);
		this.bipedHeadwear.addBox(-2.0F, -25.0F, -3.2F, 4.0F, 3.0F, 0.0F, 0.0F, false);
	}

	@Override
	public void setLivingAnimations(R0n1nEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
		this.rightArmPose = BipedModel.ArmPose.EMPTY;
		this.leftArmPose = BipedModel.ArmPose.EMPTY;
		if (entityIn.getHeldItem(Hand.MAIN_HAND).getItem() instanceof ShootableItem && entityIn.isAggressive()) {
			this.leftArmPose = BipedModel.ArmPose.BOW_AND_ARROW;
		}
		super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
	}

	@Override
	public void setRotationAngles(R0n1nEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
		// super.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks,
		// netHeadYaw, headPitch);
		// ModelHelper.func_239101_a_(this.bipedRightArm, bipedLeftArm, ageInTicks);
		this.bipedHead.rotateAngleX = headPitch * ((float) Math.PI / 180F);
		this.bipedHead.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);

		boolean isSwimming = entity.isInWater();
		this.cola.rotateAngleX = MathHelper.sin(ageInTicks) / 3 * (isSwimming ? 1 : 0);

		this.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount
				* 0.5F;
		this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
		this.bipedRightArm.rotateAngleY = 0.0F;
		this.bipedLeftArm.rotateAngleY = 0.0F;
		this.bipedRightArm.rotateAngleZ = 0.0F;
		this.bipedLeftArm.rotateAngleZ = 0.0F;
		this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.bipedRightLeg.rotateAngleY = 0.0F;
		this.bipedLeftLeg.rotateAngleY = 0.0F;
		this.bipedRightLeg.rotateAngleZ = 0.0F;
		this.bipedLeftLeg.rotateAngleZ = 0.0F;

		boolean flag1 = entity.getPrimaryHand().equals(HandSide.RIGHT);
		boolean flag2 = flag1 ? this.leftArmPose.func_241657_a_() : this.rightArmPose.func_241657_a_();

		if (flag1 != flag2) {
			rotarDerecho(entity);
			rotarIzquierdo(entity);
		} else {
			rotarIzquierdo(entity);
			rotarDerecho(entity);
		}

		this.manoderecha1.rotateAngleZ = MathHelper.cos(ageInTicks / 10) * 0.2618f
				* (this.bipedRightArm.rotateAngleX - 1);
		this.manoderecha2.rotateAngleZ = -MathHelper.cos(ageInTicks / 10) * 0.2618f
				* (this.bipedRightArm.rotateAngleX - 1);

		this.bipedBody.rotateAngleX = 0.0F;
	}

	private void rotarDerecho(R0n1nEntity ronin) {
		switch (this.rightArmPose) {
		case EMPTY:
			this.bipedRightArm.rotateAngleY = 0.0F;
			break;
		case BLOCK:
			this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - 0.9424779F;
			this.bipedRightArm.rotateAngleY = (-(float) Math.PI / 6F);
			break;
		case ITEM:
			this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F);
			this.bipedRightArm.rotateAngleY = 0.0F;
			break;
		case THROW_SPEAR:
			this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - (float) Math.PI;
			this.bipedRightArm.rotateAngleY = 0.0F;
			break;
		case BOW_AND_ARROW:
			this.bipedRightArm.rotateAngleY = -0.1F + this.bipedHead.rotateAngleY;
			//this.bipedLeftArm.rotateAngleY = 0.1F + this.bipedHead.rotateAngleY + 0.4F;
			this.bipedRightArm.rotateAngleX = (-(float) Math.PI / 2F) + this.bipedHead.rotateAngleX;
			//this.bipedLeftArm.rotateAngleX = (-(float) Math.PI / 2F) + this.bipedHead.rotateAngleX;
			break;
		case CROSSBOW_CHARGE:
			ModelHelper.func_239102_a_(this.bipedRightArm, this.bipedLeftArm, ronin, true);
			break;
		case CROSSBOW_HOLD:
			ModelHelper.func_239104_a_(this.bipedRightArm, this.bipedLeftArm, this.bipedHead, true);
		}
	}

	private void rotarIzquierdo(R0n1nEntity ronin) {
		switch (this.leftArmPose) {
		case EMPTY:
			this.bipedLeftArm.rotateAngleY = 0.0F;
			break;
		case BLOCK:
			this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - 0.9424779F;
			this.bipedLeftArm.rotateAngleY = ((float) Math.PI / 6F);
			break;
		case ITEM:
			this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F);
			this.bipedLeftArm.rotateAngleY = 0.0F;
			break;
		case THROW_SPEAR:
			this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - (float) Math.PI;
			this.bipedLeftArm.rotateAngleY = 0.0F;
			break;
		case BOW_AND_ARROW:
			//this.bipedRightArm.rotateAngleY = -0.1F + this.bipedHead.rotateAngleY - 0.4F;
			this.bipedLeftArm.rotateAngleY = 0.1F + this.bipedHead.rotateAngleY;
			//this.bipedRightArm.rotateAngleX = (-(float) Math.PI / 2F) + this.bipedHead.rotateAngleX;
			this.bipedLeftArm.rotateAngleX = (-(float) Math.PI / 2F) + this.bipedHead.rotateAngleX;
			break;
		case CROSSBOW_CHARGE:
			ModelHelper.func_239102_a_(this.bipedRightArm, this.bipedLeftArm, ronin, false);
			break;
		case CROSSBOW_HOLD:
			ModelHelper.func_239104_a_(this.bipedRightArm, this.bipedLeftArm, this.bipedHead, false);
		}
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		// TODO Auto-generated method stub

		bipedRightArm.render(matrixStack, buffer, packedLight, packedOverlay);
		bipedLeftArm.render(matrixStack, buffer, packedLight, packedOverlay);
		bipedRightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
		bipedLeftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
		bipedBody.render(matrixStack, buffer, packedLight, packedOverlay);
		bipedHead.render(matrixStack, buffer, packedLight, packedOverlay);
		cola.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	@Override
	public Iterable<ModelRenderer> getHeadParts() {
		return ImmutableList.of(this.bipedHead);
	}

	@Override
	public Iterable<ModelRenderer> getBodyParts() {
		return ImmutableList.of(this.bipedBody, this.bipedRightArm, this.bipedLeftArm, this.bipedRightLeg,
				this.bipedLeftLeg, this.cola);
	}
}

package com.carlettos.roninmod.bala;

import com.carlettos.roninmod.RoninMod;
import com.carlettos.roninmod.SpawnObjectHandler;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BalaEntity extends DamagingProjectileEntity {
	double damage = 2D;
	private int knockbackStrength;

	public BalaEntity(World world, double x, double y, double z) {
		super(RoninMod.balaEntity, x, y, z, 0, 0, 0, world);
	}

	public BalaEntity(EntityType<? extends DamagingProjectileEntity> entidad, World world) {
		super(RoninMod.balaEntity, world);
	}

	@OnlyIn(Dist.CLIENT)
	public BalaEntity(World world, LivingEntity shooter, double accX, double accY, double accZ) {
		super(RoninMod.balaEntity, shooter.getPosX(), shooter.getPosYEye() - 0.1F, shooter.getPosZ(), accX, accY,
				accZ, world);
		this.setShooter(shooter);
		this.setRotation(shooter.rotationYaw, shooter.rotationPitch);
	}

	public void setAceleraciones(double accX, double accY, double accZ) {
		this.accelerationX = 0;
		this.accelerationY = 0;
		this.accelerationZ = 0;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void tick() {
		if (!this.world.isRemote) {
			this.setFlag(6, this.isGlowing());
		}

		this.baseTick();
		Vector3d motion = this.getMotion();
		BlockPos blockpos = this.getPosition();
		BlockState blockstate = this.world.getBlockState(blockpos);
		if (!blockstate.isAir(this.world, blockpos) && !this.noClip) {
			VoxelShape voxelshape = blockstate.getCollisionShape(this.world, blockpos);
			if (!voxelshape.isEmpty()) {
				Vector3d vector3d1 = this.getPositionVec();

				for (AxisAlignedBB axisalignedbb : voxelshape.toBoundingBoxList()) {
					if (axisalignedbb.offset(blockpos).contains(vector3d1)) {
						this.remove();
						return;
					}
				}
			}
		}
		Vector3d position = this.getPositionVec();
		Vector3d positionFinal = position.add(motion);
		RayTraceResult raytraceresult = this.world.rayTraceBlocks(new RayTraceContext(position, positionFinal,
				RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
		if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
			positionFinal = raytraceresult.getHitVec();
		}

		EntityRayTraceResult entityraytraceresult = this.rayTraceEntities(position, positionFinal);
		if (entityraytraceresult != null) {
			raytraceresult = entityraytraceresult;
		}

		if (raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
			Entity entity = ((EntityRayTraceResult) raytraceresult).getEntity();
			Entity entity1 = this.func_234616_v_();
			if (entity instanceof PlayerEntity && entity1 instanceof PlayerEntity
					&& !((PlayerEntity) entity1).canAttackPlayer((PlayerEntity) entity)) {
				raytraceresult = null;
				entityraytraceresult = null;
			}
		}

		if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !this.noClip
				&& !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
			this.onImpact(raytraceresult);
			this.isAirBorne = true;
		}

		raytraceresult = null;

		motion = this.getMotion();
		double direccionX = motion.x;
		double direccionY = motion.y;
		double direccionZ = motion.z;

		double posFinalX = this.getPosX() + direccionX;
		double posFinalY = this.getPosY() + direccionY;
		double posFinalZ = this.getPosZ() + direccionZ;
		float magHorizontal = MathHelper.sqrt(horizontalMag(motion));
		if(this.noClip) {
			this.rotationYaw = (float)(MathHelper.atan2(-direccionX, -direccionZ) * 180F / Math.PI);
		} else {
			this.rotationYaw = (float)(MathHelper.atan2(direccionX, direccionZ) * 180F / Math.PI);
		}
		this.rotationPitch = (float)(MathHelper.atan2(direccionY, magHorizontal) * 180D / Math.PI);
		this.rotationYaw = func_234614_e_(this.prevRotationYaw, this.rotationYaw);
		this.rotationPitch = func_234614_e_(this.prevRotationPitch, this.rotationPitch);
		float roce = 0.999F;
		if (this.isInWater()) {
			for (int j = 0; j < 4; ++j) {
				this.world.addParticle(ParticleTypes.BUBBLE, posFinalX - direccionX * 0.25D, posFinalY - direccionY * 0.25D, posFinalZ - direccionZ * 0.25D, direccionX, direccionY,
						direccionZ);
			}
			roce = 0.9f;
		}

		this.setMotion(motion.scale((double) roce));
		if (!this.hasNoGravity() && !noClip) {
			Vector3d vector3d4 = this.getMotion();
			this.setMotion(vector3d4.x, vector3d4.y - 0.01, vector3d4.z);
		}

		this.setPosition(posFinalX, posFinalY, posFinalZ);
		this.doBlockCollisions();
	}

	@Override
	protected boolean isFireballFiery() {
		return false;
	}

	private EntityRayTraceResult rayTraceEntities(Vector3d startVec, Vector3d endVec) {
		return ProjectileHelper.rayTraceEntities(this.world, this, startVec, endVec,
				this.getBoundingBox().expand(this.getMotion()).grow(1.0D), this::func_230298_a_);
	}

	// onBlockHit
	@Override
	protected void func_230299_a_(BlockRayTraceResult p_230299_1_) {
		super.func_230299_a_(p_230299_1_);
		this.remove();
	}

	@Override
	protected void onEntityHit(EntityRayTraceResult rtr) {
		Entity entity = rtr.getEntity();
		int i = MathHelper.ceil(MathHelper.clamp(getMotion().length() * this.damage, 0, Integer.MAX_VALUE));
		Entity entity1 = func_234616_v_();
		DamageSource damagesource;
		if (entity1 == null) {
			damagesource = DamageSource.causeTridentDamage(this, this);
		} else {
			damagesource = DamageSource.causeThrownDamage(this, entity1);
			if (entity1 instanceof LivingEntity) {
				((LivingEntity) entity1).setLastAttackedEntity(entity);
			}
		}
		entity.setFire(1);
		if (entity.attackEntityFrom(damagesource, i)) {
			if (entity instanceof LivingEntity) {
				LivingEntity living = (LivingEntity) entity;
				if (!this.world.isRemote) {
					living.setArrowCountInEntity(living.getArrowCountInEntity() + 1);
				}
				if (this.knockbackStrength > 0) {
					Vector3d vector = this.getMotion().mul(1, 0, 1).normalize()
							.scale((double) this.knockbackStrength * 0.6);
					if (vector.lengthSquared() > 0d) {
						living.addVelocity(vector.x, 0.1d, vector.z);
					}
				}
				if (!this.world.isRemote && entity1 instanceof LivingEntity) {
					EnchantmentHelper.applyThornEnchantments(living, entity1);
					EnchantmentHelper.applyArthropodEnchantments((LivingEntity) entity1, living);
				}
			}

			this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1, 1.2f * 0.2f + 0.9f);
		}
		this.remove();
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean isInRangeToRenderDist(double distance) {
		return super.isInRangeToRenderDist(distance);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean isInRangeToRender3d(double x, double y, double z) {
		return super.isInRangeToRender3d(x, y, z);
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putDouble("damage", this.damage);
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if (compound.contains("damage")) {
			this.damage = compound.getDouble("damage");
		}
	}

	public void setDamage(double damageIn) {
		this.damage = damageIn;
	}

	public double getDamage() {
		return this.damage;
	}

	public void setKnockbackStrength(int knockbackStrengthIn) {
		this.knockbackStrength = knockbackStrengthIn;
	}

	@Override
	protected void registerData() {
	}

	@Override
	public Entity func_234616_v_() {
		return super.func_234616_v_();
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		Entity entity = this.func_234616_v_();
		int i = entity == null ? 0 : entity.getEntityId();
		return new SpawnObjectHandler(this.getEntityId(), this.getUniqueID(), this.getPosX(), this.getPosY(),
				this.getPosZ(), this.rotationPitch, this.rotationYaw, getType(), i, getMotion(), this.accelerationX,
				this.accelerationY, this.accelerationZ);
	}
}

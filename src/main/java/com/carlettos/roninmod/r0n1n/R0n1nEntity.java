package com.carlettos.roninmod.r0n1n;

import java.util.function.Predicate;

import com.carlettos.roninmod.RoninMod;
import com.carlettos.roninmod.bala.BalaEntity;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerBossInfo;
import net.minecraft.world.server.ServerWorld;

public class R0n1nEntity extends AgeableEntity implements IRangedAttackMob{
	private final ServerBossInfo bossInfo = new ServerBossInfo(this.getDisplayName(), BossInfo.Color.BLUE,
			BossInfo.Overlay.PROGRESS);
	
	private static final Predicate<LivingEntity> ENEMIES = (entidad) -> {
		return entidad instanceof MonsterEntity;
	};
	//private static final EntityPredicate ENEMY_CONDITION = new EntityPredicate().setDistance(20).setCustomPredicate(ENEMIES);

	public R0n1nEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
		super(RoninMod.RONIN, worldIn);
		this.experienceValue = 50;
	}
	
	@Override
	protected void registerGoals() {
		goalSelector.addGoal(0, new SwimGoal(this));
		goalSelector.addGoal(1, new R0n1nEntity.RangedRoninAttackGoal(this, 1.2, 20, 20));
		goalSelector.addGoal(2, new RandomWalkingGoal(this, 1.2));
		goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 8));
		goalSelector.addGoal(4, new LookRandomlyGoal(this));
		targetSelector.addGoal(1, new HurtByTargetGoal(this));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, MonsterEntity.class, 0, false, false, ENEMIES));
	}
	
	@Override
	public void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
		super.setEquipmentBasedOnDifficulty(difficulty);
		setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(RoninMod.arma));
	}
	
	@Override
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,
			ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
		spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		this.setLeftHanded(true);
		this.setEquipmentBasedOnDifficulty(difficultyIn);
		this.setCanPickUpLoot(true);
		return spawnDataIn;
	}
	
	@Override
	public boolean isLeftHanded() {
		return super.isLeftHanded();
	}
	
	@Override
	public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
		BalaEntity bala = RoninMod.bala.createBala(this.world, this);
		double x = target.getPosX() - this.getPosX();
		double y = target.getPosYHeight(1/3) - bala.getPosY();
		double z = target.getPosZ() - this.getPosZ();
		double horizontalMag = MathHelper.sqrt(x * x + z * z)/2d;
		bala.shoot(x, y + horizontalMag * 0.2, z, 2f, 0);
		this.world.playSound(x, y, z, SoundEvents.ENTITY_ARROW_SHOOT, getSoundCategory(), 1, 1, false);
		this.world.addEntity(bala);
	}
	
	@Override
	public CreatureAttribute getCreatureAttribute() {
		return CreatureAttribute.UNDEFINED;
	}
	
	@Override
	protected boolean canDropLoot() {
		return true;
	}
	
	@Override
	protected boolean canBeRidden(Entity entityIn) {
		return false;
	}
	
	@Override
	protected boolean func_230282_cS_() {
		return true;
	}
	
	@Override
	public void livingTick() {
		this.updateArmSwingProgress();
		super.livingTick();
	}
	
	@Override
	protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
		// TODO Auto-generated method stub
		super.dropSpecialItems(source, looting, recentlyHitIn);
		this.entityDropItem(RoninMod.webo);
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if(source.getImmediateSource() instanceof AbstractArrowEntity) {
			return false;
		}
		return super.attackEntityFrom(source, amount);
	}

	public static AttributeModifierMap.MutableAttribute attr() {
		return MobEntity.func_233666_p_()
				.createMutableAttribute(Attributes.MAX_HEALTH, 50)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 40)
				.createMutableAttribute(Attributes.ARMOR, 4)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 4);
	}
	
	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if(hasCustomName()) {
			this.bossInfo.setName(this.getDisplayName());
		}
	}
	
	@Override
	public void addTrackingPlayer(ServerPlayerEntity player) {
		super.addTrackingPlayer(player);
		//this.bossInfo.addPlayer(player);
	}
	
	@Override
	public void removeTrackingPlayer(ServerPlayerEntity player) {
		super.removeTrackingPlayer(player);
		this.bossInfo.removePlayer(player);
	}
	
	@Override
	public void setCustomName(ITextComponent name) {
		super.setCustomName(name);
		this.bossInfo.setName(this.getDisplayName());
	}
	
	@Override
	protected void updateAITasks() {
		super.updateAITasks();
		//List<LivingEntity> list = this.world.getTargettableEntitiesWithinAABB(LivingEntity.class, ENEMY_CONDITION, this, getBoundingBox().grow(20, 8, 20));
		
		this.bossInfo.setPercent(getHealth() / getMaxHealth());
	}

	@Override
	public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
		return RoninMod.RONIN.create(p_241840_1_);
	}
	
	private static class RangedRoninAttackGoal extends RangedAttackGoal{
		R0n1nEntity entity;
		public RangedRoninAttackGoal(IRangedAttackMob attacker, double movespeed, int maxAttackTime,
				float maxAttackDistanceIn) {
			super(attacker, movespeed, maxAttackTime, maxAttackDistanceIn);
			entity = (R0n1nEntity) attacker;
		}
		
		@Override
		public void startExecuting() {
			super.startExecuting();
			this.entity.setAggroed(true);
		}
		
		@Override
		public void resetTask() {
			super.resetTask();
			this.entity.setAggroed(false);
			this.entity.resetActiveHand();
		}
	}
}

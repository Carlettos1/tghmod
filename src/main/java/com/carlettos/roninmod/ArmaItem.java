package com.carlettos.roninmod;

import java.util.function.Predicate;

import com.carlettos.roninmod.bala.BalaEntity;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShootableItem;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ArmaItem extends ShootableItem{
	public static final Predicate<ItemStack> AMMO = (stack) -> {
		return stack.getItem().equals(Items.STONE);
	};
	
	public ArmaItem() {
		super(new Properties().group(ItemGroup.FOOD).maxDamage(369));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		playerIn.setActiveHand(handIn);
		playerIn.getCooldownTracker().setCooldown(this, 20);
		if(!worldIn.isRemote) {
			BalaEntity balaEntity = RoninMod.bala.createBala(worldIn, playerIn);
			balaEntity.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0, 2, 1);
			Vector3d vec = balaEntity.getMotion();
			balaEntity.setAceleraciones(vec.x, vec.y, vec.z);
			int power = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, playerIn.getHeldItem(handIn));
			int punch = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, playerIn.getHeldItem(handIn));
			int flame = EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, playerIn.getHeldItem(handIn));
			if(power > 0) {
				balaEntity.setDamage(balaEntity.getDamage() + power + 0.5);
			}
			balaEntity.setKnockbackStrength(punch + 1);
			if(flame > 0) {
				balaEntity.setFire(flame * 2);
			}
			playerIn.getHeldItem(handIn).damageItem(1, playerIn, (player) -> {
				player.sendBreakAnimation(playerIn.getActiveHand());
			});
			worldIn.addEntity(balaEntity);
			worldIn.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1);
		}               

		playerIn.addStat(Stats.ITEM_USED.get(this));
		return ActionResult.func_233538_a_(playerIn.getHeldItem(handIn), worldIn.isRemote());
	}

	@Override
	public Predicate<ItemStack> getInventoryAmmoPredicate() {
		return AMMO;
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}
	
	@Override
	public int getUseDuration(ItemStack stack) {
		return 0;
	}

	@Override
	public int func_230305_d_() {
		return 15;
	}
}

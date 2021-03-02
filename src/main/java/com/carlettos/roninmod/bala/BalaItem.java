package com.carlettos.roninmod.bala;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.World;

public class BalaItem extends Item{

	public BalaItem() {
		super(new Properties().group(ItemGroup.FOOD).isImmuneToFire());
	}
	
	public BalaEntity createBala(World worldIn, LivingEntity shooter) {
		return new BalaEntity(worldIn, shooter, 0, 0, 0);
	}
}

package com.carlettos.roninmod;

import java.io.IOException;
import java.util.UUID;

import com.carlettos.roninmod.bala.BalaEntity;

import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class SpawnObjectHandler implements IPacket<IClientPlayNetHandler> {
	private int entityId;
	private UUID uniqueId;
	private double x;
	private double y;
	private double z;
	private int speedX;
	private int speedY;
	private int speedZ;
	private int pitch;
	private int yaw;
	private EntityType<?> type;
	private int data;
	private World world;
	private double accX, accY, accZ;

	public SpawnObjectHandler(int entityId, UUID uuid, double xPos, double yPos, double zPos, float pitch, float yaw,
			EntityType<?> entityType, int entityData, Vector3d speedVector, double accX, double accY,
			double accZ) {
		this.entityId = entityId;
		this.uniqueId = uuid;
		this.x = xPos;
		this.y = yPos;
		this.z = zPos;
		this.accX = accX;
		this.accY = accY;
		this.accZ = accZ;
		this.pitch = MathHelper.floor(pitch * 256.0F / 360.0F);
		this.yaw = MathHelper.floor(yaw * 256.0F / 360.0F);
		this.type = entityType;
		this.data = entityData;
		this.speedX = (int) (MathHelper.clamp(speedVector.x, -3.9D, 3.9D) * 8000.0D);
		this.speedY = (int) (MathHelper.clamp(speedVector.y, -3.9D, 3.9D) * 8000.0D);
		this.speedZ = (int) (MathHelper.clamp(speedVector.z, -3.9D, 3.9D) * 8000.0D);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void readPacketData(PacketBuffer buf) throws IOException {
	      this.entityId = buf.readVarInt();
	      this.uniqueId = buf.readUniqueId();
	      this.type = Registry.ENTITY_TYPE.getByValue(buf.readVarInt());
	      this.x = buf.readDouble();
	      this.y = buf.readDouble();
	      this.z = buf.readDouble();
	      this.accX = buf.readDouble();
	      this.accY = buf.readDouble();
	      this.accZ = buf.readDouble();
	      this.pitch = buf.readByte();
	      this.yaw = buf.readByte();
	      this.data = buf.readInt();
	      this.speedX = buf.readShort();
	      this.speedY = buf.readShort();
	      this.speedZ = buf.readShort();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void writePacketData(PacketBuffer buf) throws IOException {
	      buf.writeVarInt(this.entityId);
	      buf.writeUniqueId(this.uniqueId);
	      buf.writeVarInt(Registry.ENTITY_TYPE.getId(this.type));
	      buf.writeDouble(this.x);
	      buf.writeDouble(this.y);
	      buf.writeDouble(this.z);
	      buf.writeDouble(this.accX);
	      buf.writeDouble(this.accY);
	      buf.writeDouble(this.accZ);
	      buf.writeByte(this.pitch);
	      buf.writeByte(this.yaw);
	      buf.writeInt(this.data);
	      buf.writeShort(this.speedX);
	      buf.writeShort(this.speedY);
	      buf.writeShort(this.speedZ);
	}

	@Override
	public void processPacket(IClientPlayNetHandler handler) {
		this.world = ((ClientPlayNetHandler)handler).getWorld();
		if(this.world == null) {
			return;
		}
		// PacketThreadUtil.checkThreadAndEnqueue(this, handler, cliente);
		Entity entity;
		if (type == RoninMod.balaEntity) {
			Entity entity2 = world.getEntityByID(data);
			entity = new BalaEntity(this.world, x, y, z);
			((BalaEntity) entity).setAceleraciones(accX, accY, accZ);
			if (entity2 != null) {
				((BalaEntity) entity).setShooter(entity2);
			}
		} else {
			entity = null;
		}
		if (entity != null) {
			entity.setPacketCoordinates(x, y, z);
			entity.moveForced(x, y, z);
			entity.rotationPitch = (float) (pitch * 360) / 256.0F;
			entity.rotationYaw = (float) (yaw * 360) / 256.0F;
			entity.setEntityId(entityId);
			entity.setUniqueId(uniqueId);
			if(this.world instanceof ClientWorld) {
				((ClientWorld) this.world).addEntity(entityId, entity);
			}
		}
	}
}

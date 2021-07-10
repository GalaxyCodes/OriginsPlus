package io.github.galaxy.originsplus.entity;

import java.util.Iterator;
import java.util.List;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.galaxy.originsplus.power.ModifyDragonFireballPower;
import io.github.galaxy.originsplus.registry.ModEntities;
import io.github.galaxy.originsplus.registry.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class SmallDragonFireballEntity extends ThrownItemEntity {
	public SmallDragonFireballEntity(EntityType<? extends SmallDragonFireballEntity> entityType, World world) {
        super(entityType, world);
    }

    public SmallDragonFireballEntity(World world, LivingEntity owner) {
        super(ModEntities.SMALL_DRAGON_FIREBALL, owner, world);
    }

    public SmallDragonFireballEntity(World world, double x, double y, double z) {
        super(ModEntities.SMALL_DRAGON_FIREBALL, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.DRAGON_FIREBALL;
    }

    @Override
    public void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        Entity entity = this.getOwner();
        if (hitResult.getType() != HitResult.Type.ENTITY) {
            if (!this.world.isClient) {
                List<LivingEntity> list = this.world.getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(2.5D, 1.0D, 2.5D), ModEntityPredicates.EXCEPT_PLAYER);
                FireballAreaEffectCloudEntity areaEffectCloudEntity = new FireballAreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
                if (entity instanceof LivingEntity) {
                    areaEffectCloudEntity.setOwner((LivingEntity)entity);
                }
                areaEffectCloudEntity.setParticleType(ParticleTypes.DRAGON_BREATH);
                areaEffectCloudEntity.setWaitTime(0);
                float minRadius = 1.125F;
                float maxRadius = 2.25F;
                int duration = 60;
                float damage = 6.0F;
                if(this.getOwner() != null && PowerHolderComponent.hasPower(this.getOwner(), ModifyDragonFireballPower.class)) {
                    minRadius = PowerHolderComponent.getPowers(this.getOwner(), ModifyDragonFireballPower.class).get(0).getMinRadius();
                    maxRadius = PowerHolderComponent.getPowers(this.getOwner(), ModifyDragonFireballPower.class).get(0).getMaxRadius();
                    duration = PowerHolderComponent.getPowers(this.getOwner(), ModifyDragonFireballPower.class).get(0).getDuration();
                    damage = PowerHolderComponent.modify(this.getOwner(), ModifyDragonFireballPower.class, 6.0F);
                }
                areaEffectCloudEntity.setRadius(minRadius);
                areaEffectCloudEntity.setDuration(duration);
                areaEffectCloudEntity.setDamage(damage);
                areaEffectCloudEntity.setRadiusGrowth((maxRadius - areaEffectCloudEntity.getRadius()) / (float)areaEffectCloudEntity.getDuration());
                if (!list.isEmpty()) {
                    Iterator var5 = list.iterator();
                    while (var5.hasNext()) {
                        LivingEntity livingEntity = (LivingEntity)var5.next();
                        double d = this.squaredDistanceTo(livingEntity);
                        if (d < 6.25D && list.size() == 1) {
                            areaEffectCloudEntity.updatePosition(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                            break;
                        }
                    }
                }

                this.world.syncWorldEvent(1520, this.getBlockPos(), this.isSilent() ? -1 : 1);
                this.world.spawnEntity(areaEffectCloudEntity);
                this.discard();
            }
        }
    }

    public void tick() {
        Entity entity = this.getOwner();
        if (entity instanceof PlayerEntity && !entity.isAlive()) {
            this.discard();
        } else {
            super.tick();
        }
    }
}

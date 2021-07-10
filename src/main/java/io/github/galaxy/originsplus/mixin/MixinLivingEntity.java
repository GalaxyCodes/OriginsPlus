package io.github.galaxy.originsplus.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.apace100.apoli.power.PreventSleepPower;
import io.github.galaxy.originsplus.registry.ModPowers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {

    @Shadow @Nullable public abstract EntityAttributeInstance getAttributeInstance(EntityAttribute attribute);

    @Shadow public abstract void setHealth(float health);

    @Shadow public abstract boolean clearStatusEffects();

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

    public MixinLivingEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tryUseTotem", at=@At("HEAD"), cancellable = true)
    private void tryUseTotem(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if (source.isOutOfWorld()) {
            cir.setReturnValue(false);
        }
        else {
            if (ModPowers.DOUBLE_LIFE.isActive(this)) {
                if(this.getScoreboardTags().toString().contains("HasDied")) {
                	this.removeScoreboardTag("HasDied");
                	cir.setReturnValue(false);
                } else {
                	this.addScoreboardTag("HasDied");
                	this.setHealth(1.0F);
                    this.clearStatusEffects();
                    
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 2));
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 40, 5));
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
                    cir.setReturnValue(true);
                }
                cir.cancel();
                return;
            }
        }
    }
}

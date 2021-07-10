package io.github.galaxy.originsplus.registry;

import io.github.apace100.calio.mixin.DamageSourceAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;

public class ModDamageSources {
	public static final DamageSource DRAGON_MAGIC = ((DamageSourceAccessor)((DamageSourceAccessor)DamageSourceAccessor.createDamageSource("dragonMagic")).callSetBypassesArmor()).callSetUnblockable().setUsesMagic();

    public static DamageSource dragonMagic(Entity magic, Entity attacker) {
        return ((DamageSourceAccessor)((DamageSourceAccessor)new ProjectileDamageSource("indirectDragonMagic", magic, attacker)).callSetBypassesArmor()).callSetUnblockable().setUsesMagic();
    }
}

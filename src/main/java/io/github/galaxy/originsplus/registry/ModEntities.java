package io.github.galaxy.originsplus.registry;

import io.github.galaxy.originsplus.OriginsPlus;
import io.github.galaxy.originsplus.entity.FireballAreaEffectCloudEntity;
import io.github.galaxy.originsplus.entity.SmallDragonFireballEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("deprecation")
public class ModEntities {
	public static final EntityType<SmallDragonFireballEntity> SMALL_DRAGON_FIREBALL;
    public static final EntityType<FireballAreaEffectCloudEntity> FIREBALL_AREA_EFFECT_CLOUD;

    static {
        SMALL_DRAGON_FIREBALL = FabricEntityTypeBuilder.<SmallDragonFireballEntity>create(SpawnGroup.MISC, SmallDragonFireballEntity::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackable(64, 10).build();
        FIREBALL_AREA_EFFECT_CLOUD = FabricEntityTypeBuilder.<FireballAreaEffectCloudEntity>create(SpawnGroup.MISC, FireballAreaEffectCloudEntity::new).fireImmune().dimensions(EntityDimensions.changing(6.0F, 0.5F)).trackable(10, 2147483647).build();
    }

    public static void register() {
        Registry.register(Registry.ENTITY_TYPE, new Identifier(OriginsPlus.MOD_ID, "small_dragon_fireball"), SMALL_DRAGON_FIREBALL);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(OriginsPlus.MOD_ID, "fireball_area_effect_cloud"), FIREBALL_AREA_EFFECT_CLOUD);
    }
}

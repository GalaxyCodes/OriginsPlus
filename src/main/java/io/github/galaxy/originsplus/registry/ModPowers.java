package io.github.galaxy.originsplus.registry;

import java.lang.annotation.Target;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.ActiveCooldownPower;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeReference;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.galaxy.originsplus.OriginsPlus;
import io.github.galaxy.originsplus.power.ModifyDragonFireballPower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModPowers {
	private static final Map<PowerFactory<?>, Identifier> POWER_FACTORIES = new LinkedHashMap<>();

    public static final PowerFactory<Power> MODIFY_DRAGON_FIREBALL = create(new PowerFactory<>(new Identifier(OriginsPlus.MOD_ID, "modify_dragon_fireball"),
            new SerializableData()
                    .add("damage_modifier", SerializableDataTypes.ATTRIBUTE_MODIFIER, null)
                    .add("damage_modifiers", SerializableDataTypes.ATTRIBUTE_MODIFIERS, null)
                    .add("min_radius", SerializableDataTypes.FLOAT, 1.125F)
                    .add("max_radius", SerializableDataTypes.FLOAT, 2.25F)
                    .add("duration", SerializableDataTypes.INT, 60),
            data ->
                    (type, entity) -> {
                        ModifyDragonFireballPower power = new ModifyDragonFireballPower(type, entity, data.getFloat("min_radius"), data.getFloat("max_radius"), data.getInt("duration"));
                        if (data.isPresent("damage_modifier")) {
                            power.addModifier(data.getModifier("damage_modifier"));
                        }
                        if (data.isPresent("damage_modifiers")) {
                            ((List<EntityAttributeModifier>)data.get("damage_modifiers")).forEach(power::addModifier);
                        }
                        return power;
                    })
            .allowCondition());
    
    public static final PowerFactory<Power> CHARGE = create(new PowerFactory<>(new Identifier(OriginsPlus.MOD_ID, "charge"),
    		new SerializableData()
	            .add("cooldown", SerializableDataTypes.INT)
	            .add("speed", SerializableDataTypes.FLOAT)
	            .add("sound", SerializableDataTypes.SOUND_EVENT, null)
	            .add("hud_render", ApoliDataTypes.HUD_RENDER)
	            .add("key", ApoliDataTypes.BACKWARDS_COMPATIBLE_KEY, new Active.Key()),
	        data -> {
	            SoundEvent soundEvent = (SoundEvent)data.get("sound");
	            return (type, player) -> {
	                ActiveCooldownPower power = new ActiveCooldownPower(type, player,
	                    data.getInt("cooldown"),
	                    (HudRender) data.get("hud_render"),
	                    e -> {
	                        if (!e.world.isClient && e instanceof PlayerEntity) {
	                            PlayerEntity p = (PlayerEntity) e;
	                            
	                            float speed = data.getFloat("speed");
	                            
	                            p.addVelocity(p.getRotationVector().getX() * speed, p.getRotationVector().getY() * 0.5, p.getRotationVector().getZ() * speed);
	                            p.velocityModified = true;
	                            if (soundEvent != null) {
	                                p.world.playSound((PlayerEntity) null, p.getX(), p.getY(), p.getZ(), soundEvent, SoundCategory.NEUTRAL, 0.5F, 0.4F / (p.getRandom().nextFloat() * 0.4F + 0.8F));
	                            }
	                            for (int i = 0; i < 4; ++i) {
	                                ((ServerWorld) p.world).spawnParticles(ParticleTypes.CLOUD, p.getX(), p.getRandomBodyY(), p.getZ(), 8, p.getRandom().nextGaussian(), 0.0D, p.getRandom().nextGaussian(), 0.5);
	                            }
	                        }
	                    });
	                power.setKey((Active.Key)data.get("key"));
	                return power;
	            };
        }).allowCondition());

    public static final PowerType<Power> DOUBLE_LIFE = new PowerTypeReference<Power>(new Identifier(OriginsPlus.MOD_ID, "paladin/double_life"));
    public static final PowerType<Power> IGNORANCE = new PowerTypeReference<Power>(new Identifier(OriginsPlus.MOD_ID, "dragonborn/ignorance"));
    
    private static <T extends Power> PowerFactory<T> create(PowerFactory<T> factory) {
        POWER_FACTORIES.put(factory, factory.getSerializerId());
        return factory;
    }

    public static void init() {
        POWER_FACTORIES.keySet().forEach(powerType -> Registry.register(ApoliRegistries.POWER_FACTORY, POWER_FACTORIES.get(powerType), powerType));
    }
}

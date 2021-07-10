package io.github.galaxy.originsplus.registry;

import io.github.galaxy.originsplus.OriginsPlus;
import io.github.galaxy.originsplus.effect.EndFireStatusEffect;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEffects {
    public static final InstantStatusEffect END_FIRE = new EndFireStatusEffect();

    public static void register() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(OriginsPlus.MOD_ID, "end_fire"), END_FIRE);
    }
}

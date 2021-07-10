package io.github.galaxy.originsplus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.galaxy.originsplus.registry.ModEffects;
import io.github.galaxy.originsplus.registry.ModEntities;
import io.github.galaxy.originsplus.registry.ModItems;
import io.github.galaxy.originsplus.registry.ModPowers;
import net.fabricmc.api.ModInitializer;

public class OriginsPlus implements ModInitializer {
    public static final String MOD_ID = "originsplus";
    public static final Logger LOGGER = LogManager.getLogger(OriginsPlus.class);

    @Override
    public void onInitialize() {
    	LOGGER.info("OriginsPlus has been initialised. Enjoy!");
		
		ModEntities.register();
		ModItems.register();
		ModEffects.register();
		ModPowers.init();
    }
}

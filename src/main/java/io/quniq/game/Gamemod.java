package io.quniq.game;

import io.quniq.game.config.DatabaseConfig;
import io.quniq.game.network.NetworkRegistry;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Gamemod implements ModInitializer {
	public static final String MOD_ID = "game-mod";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Initializing Game Mod...");
		
		// Инициализация базы данных
		try {
			DatabaseConfig.initialize();
			LOGGER.info("Database initialized successfully!");
		} catch (Exception e) {
            LOGGER.error("Failed to initialize database: {}", e.getMessage());
		}
		
		// Регистрация сетевых пакетов
		NetworkRegistry.registerServerPackets();
		LOGGER.info("Network packets registered successfully!");
		
		LOGGER.info("Game Mod initialized successfully!");
	}
}
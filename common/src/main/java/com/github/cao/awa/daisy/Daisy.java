package com.github.cao.awa.daisy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Daisy {
    private static final Logger LOGGER = LogManager.getLogger("Daisy");
    private static String platform = "fabric";
    public static final String MOD_ID = "daisy";

    public static void init() {
        LOGGER.info("Daisy initialized on platform {}", Daisy.platform);
    }

    public static void initFabric() {
        Daisy.platform = "fabric";
    }

    public static void initNeoForge() {
        Daisy.platform = "neoforge";
    }
}

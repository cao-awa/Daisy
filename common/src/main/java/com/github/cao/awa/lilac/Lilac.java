package com.github.cao.awa.lilac;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Lilac {
    private static final Logger LOGGER = LogManager.getLogger("Lilac");
    private static String platform = "fabric";
    public static final String MOD_ID = "lilac";

    public static void init() {
        LOGGER.info("Lilac initialized on platform {}", Lilac.platform);
    }

    public static void initFabric() {
        Lilac.platform = "fabric";
    }

    public static void initNeoForge() {
        Lilac.platform = "neoforge";
    }
}

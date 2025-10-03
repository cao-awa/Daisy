package com.github.cao.awa.lilac.fabric;

import com.github.cao.awa.lilac.Lilac;
import net.fabricmc.api.ModInitializer;

public final class LilacFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Lilac.init();
        Lilac.initFabric();
    }
}

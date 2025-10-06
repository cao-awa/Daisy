package com.github.cao.awa.daisy.fabric;

import com.github.cao.awa.daisy.Daisy;
import net.fabricmc.api.ModInitializer;

public final class DaisyFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Daisy.init();
        Daisy.initFabric();
    }
}

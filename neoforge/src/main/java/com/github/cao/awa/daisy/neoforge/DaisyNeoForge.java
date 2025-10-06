package com.github.cao.awa.daisy.neoforge;

import com.github.cao.awa.daisy.Daisy;
import net.neoforged.fml.common.Mod;

@Mod(Daisy.MOD_ID)
public final class DaisyNeoForge {
    public DaisyNeoForge() {
        Daisy.init();
        Daisy.initNeoForge();
    }
}

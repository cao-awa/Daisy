package com.github.cao.awa.lilac.neoforge;

import com.github.cao.awa.lilac.Lilac;
import net.neoforged.fml.common.Mod;

@Mod(Lilac.MOD_ID)
public final class LilacNeoForge {
    public LilacNeoForge() {
        Lilac.init();
        Lilac.initNeoForge();
    }
}

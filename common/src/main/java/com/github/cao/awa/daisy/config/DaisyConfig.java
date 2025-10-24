package com.github.cao.awa.daisy.config;

import com.github.cao.awa.catheter.Catheter;
import com.github.cao.awa.daisy.config.key.DaisyConfigKey;
import com.github.cao.awa.sinuatum.util.collection.CollectionFactor;
import com.github.cao.awa.sinuatum.util.io.IOUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.util.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class DaisyConfig {
    private static final Logger LOGGER = LogManager.getLogger("DaisyConfig");
    private final String modName;
    private final File configFile;
    private final Object2ObjectOpenHashMap<String, DaisyConfigKey<Object>> configKeys = new Object2ObjectOpenHashMap<>();
    private final Map<String, Object> configs = new Object2ObjectOpenHashMap<>();

    public DaisyConfig(String modName, Consumer<DaisyConfig> register) {
        this.modName = modName;
        this.configFile = new File("config/" + modName + ".json");
        register.accept(this);
    }

    public <X> void register(String key, X defaultValue) {
        this.configKeys.put(key, DaisyConfigKey.create(key, defaultValue));
    }

    public <X> void setConfig(DaisyConfigKey<X> configKey, X value) {
        this.configs.put(configKey.name(), value);
        configKey.triggerChangeAction();
    }

    public <X> void setConfigFromMap(DaisyConfigKey<X> configKey, Map<String, JsonElement> map) {
        map.forEach((key, value) -> {
            this.configs.put(configKey.name(), getConfig(configKey, map));
        });
    }

    @SuppressWarnings("unchecked")
    private <X> X getConfig(DaisyConfigKey<X> configKey, Map<String, JsonElement> map) {
        X result = (X) map.get(configKey.name());
        if (result == null) {
            return configKey.value();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public <X> X getConfig(@NotNull DaisyConfigKey<X> configKey) {
        X value = (X) this.configs.get(configKey.name());
        if (value == null) {
            return configKey.value();
        }
        return value;
    }

    public void load() {
        loadAsDefault();

        try {
            final Map<String, JsonElement> config = toMap(IOUtil.read(new FileReader(this.configFile, StandardCharsets.UTF_8)));

            for (DaisyConfigKey<Object> configKey : this.configKeys.values()) {
                setConfigFromMap(configKey, config);
            }
        } catch (FileNotFoundException e) {
            LOGGER.warn("Config not found, will use default config values", e);
        } catch (Exception e) {
            LOGGER.warn("Happening unexpected exception, config system cannot resolve it, will use default config values", e);
        }

        write();
    }

    @SuppressWarnings("unchecked")
    public <X> DaisyConfigKey<X> getConfigKey(String name) {
        return (DaisyConfigKey<X> )this.configKeys.get(name);
    }

    public Map<String, JsonElement> toMap(String data) {
        Map<String, JsonElement> jsonMap = JsonHelper.deserialize(data).asMap();

        Map<String, JsonElement> map = new Object2ObjectOpenHashMap<>();

        return Catheter.of(
                jsonMap.entrySet()
        ).varyMap(
                map,
                (identity, entry) -> {
                    identity.put(entry.getKey(), entry.getValue());
                }
        );
    }

    public void write() {
        try {
            if (!this.configFile.getParentFile().exists()) {
                this.configFile.getParentFile().mkdirs();
            }
            JsonObject json = new JsonObject();

            for (Map.Entry<String, Object> entry : this.configs.entrySet()) {
                Object value = entry.getValue();
                if (value instanceof String string) {
                    json.addProperty(entry.getKey(), string);
                } else if (value instanceof Number number) {
                    json.addProperty(entry.getKey(), number);
                } else if (value instanceof Character bool) {
                    json.addProperty(entry.getKey(), bool);
                }
            }

            IOUtil.write(
                    new FileWriter(this.configFile, StandardCharsets.UTF_8),
                    json.toString()
            );
        } catch (Exception e) {
            LOGGER.warn("Failed to save config", e);
        }
    }

    public void loadAsDefault() {
        this.configKeys.values().forEach((key) -> {
            setConfig(key, key.value());
        });
    }

    public void copyFrom(@NotNull DaisyConfig config) {
        this.configKeys.values().forEach((key) -> {
            setConfig(key, config.getConfig(key));
        });
    }

    public void print() {
        this.configs.forEach((name, value) -> {
            LOGGER.info("Config '{}' in mod '{}' is '{}'", name, this.modName, value);
        });
    }

    public Set<DaisyConfigKey<?>> collect() {
        Set<DaisyConfigKey<?>> configs = CollectionFactor.hashSet();

        configs.addAll(this.configKeys.values());

        return configs;
    }
}

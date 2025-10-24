package com.github.cao.awa.daisy.config.key;

import org.apache.commons.lang3.function.Consumers;

import java.util.function.Consumer;

public record DaisyConfigKey<T>(String name, T value, Consumer<T> onChangeAction) {
    public static <X> DaisyConfigKey<X> create(String name, X defaultValue) {
        return new DaisyConfigKey<>(name, defaultValue, Consumers.nop());
    }

    public static <X> DaisyConfigKey<X> create(String name, X defaultValue, Consumer<X> onChangeAction) {
        return new DaisyConfigKey<>(name, defaultValue, onChangeAction);
    }

    public void triggerChangeAction() {
        this.onChangeAction.accept(this.value);
    }
}

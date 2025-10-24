import com.github.cao.awa.daisy.config.DaisyConfig;
import com.github.cao.awa.daisy.config.key.DaisyConfigKey;

public class TestConfig {
    public static DaisyConfigKey<Boolean> BOOLEAN_KEY = DaisyConfigKey.create("test_boolean", true);
    public static DaisyConfigKey<Integer> INTEGER_KEY = DaisyConfigKey.create("test_integer", 123);
    public static DaisyConfigKey<String> STRING_KEY = DaisyConfigKey.create("test_string", "awa");

    private static DaisyConfig config = new DaisyConfig("daisy", config -> {
        config.register(BOOLEAN_KEY);
        config.register(INTEGER_KEY);
        config.register(STRING_KEY);
    });

    public static void main(String[] args) {
        System.out.println(config.collect());

        if (config.getConfig(BOOLEAN_KEY)) {
            System.out.println("QAQ");
        }
        config.setConfig(BOOLEAN_KEY, false);
        if (config.getConfig(BOOLEAN_KEY)) {
            System.out.println("AWA");
        }
    }
}

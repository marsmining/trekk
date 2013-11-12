package trekk.common;

/**
 * Views and it's sublasses define the configuration for
 * controlling which properties of classes will be part
 * of JSON serialization.
 */
public class Views {

    /**
     * For lightweight requests only needing basic data.
     */
    public static class LightPublic extends Views { }

    /**
     * The default for your run of the mill request.
     */
    public static class Public extends LightPublic { }

    /**
     * Extra info which is public, just more properties.
     */
    public static class ExtendedPublic extends Public { }

    /**
     * This would include private stuff.
     */
    public static class Internal extends ExtendedPublic { }

    /**
     * None.
     */
    public static class None extends Views { };
}

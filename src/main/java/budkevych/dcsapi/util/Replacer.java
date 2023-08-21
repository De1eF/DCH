package budkevych.dcsapi.util;

public class Replacer {
    public static <T> T replace(T toReplace, T toReplaceWith) {
        return toReplaceWith == null ? toReplace : toReplaceWith;
    }
}

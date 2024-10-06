package xyz.kbws.infrastructure;

import com.intellij.AbstractBundle;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.PropertyKey;

import java.util.ResourceBundle;

/**
 * @author kbws
 * @date 2024/10/6
 * @description:
 */
public class MsgBundle {

    @NonNls
    private static final String BUNDLE_NAME = "messages.MsgBundle";

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    public static String message(@PropertyKey(resourceBundle = BUNDLE_NAME) String key, Object ... params){
        return AbstractBundle.message(BUNDLE, key, params);
    }
}

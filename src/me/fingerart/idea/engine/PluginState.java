package me.fingerart.idea.engine;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import me.fingerart.idea.constants.CommonConst;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.java.generate.element.Element;

/**
 * Created by FingerArt on 16/10/7.
 */
@State(name = "Config", storages = {@Storage(id = "FingerArt", file = "$APP_CONFIG$/" + CommonConst.FILE_NAME_CONFIG)})
public class PluginState implements PersistentStateComponent<Element> {
    public static final String ELEMENT_ROOT_NAME = "file_upload";
    public static final String ELEMENT_NAME_URL = "url";
    public static final String ELEMENT_NAME_MODULE = "module";
    public static final String ELEMENT_NAME_PATH = "path";

    public static final String ELEMENT_ROOT_NAME_PARAMS = "params";
    public static final String ELEMENT_NAME_KEY = "key";
    public static final String ELEMENT_NAME_VAL = "value";

    @Nullable
    @Override
    public Element getState() {
        return null;
    }

    @Override
    public void loadState(Element element) {
        element.
    }
}

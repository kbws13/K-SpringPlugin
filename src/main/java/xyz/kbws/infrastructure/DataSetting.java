package xyz.kbws.infrastructure;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.kbws.domain.model.vo.ProjectConfigVO;

/**
 * @author kbws
 * @date 2024/10/5
 * @description:
 */
@Service
@State(name = "DataSetting", storages = @Storage("plugin.xml"))
public final class DataSetting implements PersistentStateComponent<DataState> {

    private DataState state = new DataState();

    public static DataSetting getInstance() {
        return ApplicationManager.getApplication().getService(DataSetting.class);
         //ServiceManager.getService(DataSetting.class);
    }

    @Override
    public @Nullable DataState getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull DataState state) {
        this.state = state;
    }

    public ProjectConfigVO getProjectConfig() {
        return state.getProjectConfigVO();
    }
}

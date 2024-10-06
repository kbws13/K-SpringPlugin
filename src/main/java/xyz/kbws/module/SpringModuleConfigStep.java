package xyz.kbws.module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.options.ConfigurationException;
import xyz.kbws.domain.model.vo.ProjectConfigVO;
import xyz.kbws.infrastructure.DataSetting;
import xyz.kbws.ui.ProjectConfigUI;

import javax.swing.*;

/**
 * @author kbws
 * @date 2024/10/5
 * @description:
 */
public class SpringModuleConfigStep extends ModuleWizardStep {

    private ProjectConfigUI projectConfigUI;

    public SpringModuleConfigStep(ProjectConfigUI projectConfigUI) {
        this.projectConfigUI = projectConfigUI;
    }

    @Override
    public JComponent getComponent() {
        return projectConfigUI.getComponent();
    }

    @Override
    public void updateDataModel() {

    }

    @Override
    public boolean validate() throws ConfigurationException {
        // 获取配置信息，写入到 DataSetting
        ProjectConfigVO projectConfig = DataSetting.getInstance().getProjectConfig();
        projectConfig.set_groupId(projectConfigUI.getGroupIdField().getText());
        projectConfig.set_artifactId(projectConfigUI.getArtifactIdField().getText());
        projectConfig.set_version(projectConfigUI.getVersionField().getText());
        projectConfig.set_package(projectConfigUI.getPackageField().getText());

        return super.validate();
    }
}

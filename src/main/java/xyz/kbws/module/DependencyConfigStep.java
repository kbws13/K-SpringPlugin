package xyz.kbws.module;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.options.ConfigurationException;
import xyz.kbws.domain.model.vo.DependencyConfigVO;
import xyz.kbws.domain.model.vo.ProjectConfigVO;
import xyz.kbws.infrastructure.DataSetting;
import xyz.kbws.ui.DependencyConfigUI;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author kbws
 * @date 2024/10/9
 * @description:
 */
public class DependencyConfigStep extends ModuleWizardStep {

    private DependencyConfigUI dependencyConfigUI;

    public DependencyConfigStep(DependencyConfigUI dependencyConfigUI) {
        this.dependencyConfigUI = dependencyConfigUI;
    }

    @Override
    public JComponent getComponent() {
        return dependencyConfigUI.getComponent();
    }

    @Override
    public void updateDataModel() {

    }

    @Override
    public boolean validate() throws ConfigurationException {
        // 获取配置信息，写入到 DataSetting
        ProjectConfigVO projectConfig = DataSetting.getInstance().getProjectConfig();
        String dependencyStr = HttpUtil.get("https://gitee.com/enteral/k-spring-plugin-config/raw/master/dependency.json");
        JSONObject dependencyObject = JSONUtil.parseObj(dependencyStr);
        Map<String, DependencyConfigVO> dependencies = dependencyObject.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> ((JSONObject) entry.getValue()).toBean(DependencyConfigVO.class)
                ));
        List<String> selectedNodeValues = dependencyConfigUI.getSelectedNodeValues();

        Map<String, Object> projectConfigMap = BeanUtil.beanToMap(projectConfig);
        Map<String, DependencyConfigVO> res = new HashMap<>();
        for (String selectedNodeValue : selectedNodeValues) {
            DependencyConfigVO dependencyConfigVO = dependencies.get(selectedNodeValue);
            res.put(selectedNodeValue, dependencyConfigVO);
            projectConfigMap.put(dependencyConfigVO.getParent(), true);
        }

        projectConfig = BeanUtil.toBean(projectConfigMap, ProjectConfigVO.class);
        projectConfig.setDependencies(res);

        DataSetting.getInstance().getState().setProjectConfigVO(projectConfig);
        return super.validate();
    }
}

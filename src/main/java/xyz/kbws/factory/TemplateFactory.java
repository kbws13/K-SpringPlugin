package xyz.kbws.factory;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.platform.ProjectTemplate;
import com.intellij.platform.ProjectTemplatesFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.kbws.infrastructure.ICONS;
import xyz.kbws.module.SpringModuleBuilder;

import javax.swing.*;

/**
 * @author kbws
 * @date 2024/10/6
 * @description:
 */
public class TemplateFactory extends ProjectTemplatesFactory {
    @Override
    public String @NotNull [] getGroups() {
        return new String[]{"Spring Template 脚手架"};
    }

    @Override
    public Icon getGroupIcon(String group) {
        return ICONS.DDD;
    }

    @Override
    public ProjectTemplate @NotNull [] createTemplates(@Nullable String s, @NotNull WizardContext wizardContext) {
        return new ProjectTemplate[]{
                new ProjectTemplate() {
                    @Override
                    public @NotNull String getName() {
                        return "Spring boot project";
                    }

                    @Override
                    public @NotNull String getDescription() {
                        return "Spring Boot project template with predefined structure.";
                    }

                    @Override
                    public Icon getIcon() {
                        return ICONS.SPRING_BOOT;
                    }

                    @NotNull
                    @Override
                    public ModuleBuilder createModuleBuilder() {
                        // 返回自定义的 SpringModuleBuilder 实例
                        return new SpringModuleBuilder();
                    }

                    @Override
                    public @Nullable ValidationInfo validateSettings() {
                        // 如果需要进行设置验证，可以在此实现；暂时不需要，可以保持为空。
                        return null;
                    }
                }
        };
    }
}

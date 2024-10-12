package xyz.kbws.module;

import com.intellij.ide.util.projectWizard.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.JavaModuleType;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ex.ProjectManagerEx;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.kbws.domain.service.IProjectGenerator;
import xyz.kbws.domain.service.impl.ProjectGeneratorImpl;
import xyz.kbws.infrastructure.DataSetting;
import xyz.kbws.infrastructure.ICONS;
import xyz.kbws.infrastructure.MsgBundle;
import xyz.kbws.ui.DependencyConfigUI;
import xyz.kbws.ui.ProjectConfigUI;

import javax.swing.*;
import java.io.File;
import java.util.Objects;

/**
 * @author kbws
 * @date 2024/10/5
 * @description:
 */
public class SpringModuleBuilder extends ModuleBuilder {

    private final IProjectGenerator projectGenerator = new ProjectGeneratorImpl();

    @Override
    public Icon getNodeIcon() {
        return ICONS.SPRING_BOOT;
    }

    /**
     * 重写 builderId 挂载自定义模板
     *
     * @return 返回 builderId
     */
    @Override
    @Nullable
    public String getBuilderId() {
        return getClass().getName();
    }

    @Override
    public ModuleType<?> getModuleType() {
        return JavaModuleType.getModuleType();
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title)
    String getPresentableName() {
        return "Spring Boot";
    }

    @Override
    public String getDescription() {
        return MsgBundle.message("wizard.spring.boot.description");
    }

    @Override
    public @Nullable ModuleWizardStep modifySettingsStep(@NotNull SettingsStep settingsStep) {
        ModuleNameLocationSettings moduleNameLocationSettings = settingsStep.getModuleNameLocationSettings();
        String artifactId = DataSetting.getInstance().getProjectConfig().get_artifactId();
        if (null != moduleNameLocationSettings && !StringUtil.isEmptyOrSpaces(artifactId)) {
            moduleNameLocationSettings.setModuleName(artifactId);
        }
        return super.modifySettingsStep(settingsStep);
    }

    @Override
    public void setupRootModel(@NotNull ModifiableRootModel rootModel) {

        // 设置 JDK
        if (null != this.myJdk) {
            rootModel.setSdk(this.myJdk);
        } else {
            rootModel.inheritSdk();
        }

        // 生成工程路径
        String path = FileUtil.toSystemIndependentName(Objects.requireNonNull(getContentEntryPath()));
        new File(path).mkdirs();
        VirtualFile virtualFile = LocalFileSystem.getInstance().refreshAndFindFileByPath(path);
        if (virtualFile != null) {
            rootModel.addContentEntry(virtualFile);
        }

        Project project = rootModel.getProject();

        // 创建工程结构
        ApplicationManager.getApplication().runWriteAction(() -> projectGenerator.doGenerator(project, getContentEntryPath(), DataSetting.getInstance().getProjectConfig()));
        //Runnable r = () -> WriteCommandAction.runWriteCommandAction(project, () -> {
        //    projectGenerator.doGenerator(project, getContentEntryPath(), DataSetting.getInstance().getProjectConfig());
        //});
        //
        //if (ApplicationManager.getApplication().isUnitTestMode()
        //        || ApplicationManager.getApplication().isHeadlessEnvironment()) {
        //    r.run();
        //    return;
        //}
        //
        //if (!project.isInitialized()) {
        //    StartupManager.getInstance(project).runAfterOpened(DisposeAwareRunnable.create(r, project));
        //    return;
        //}
        //
        //if (DumbService.isDumbAware(r)) {
        //    r.run();
        //} else {
        //    DumbService.getInstance(project).runWhenSmart(DisposeAwareRunnable.create(r, project));
        //}


        // Refresh the project to recognize the new files
        ApplicationManager.getApplication().invokeLater(() -> {
            try {
                VfsUtil.markDirtyAndRefresh(false, true, true, virtualFile);
                reloadProject(project);
            } catch (Exception e) {
                // Handle or log the exception
                e.printStackTrace();
            }
        });

    }

    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext wizardContext, @NotNull ModulesProvider modulesProvider) {

        // 添加工程配置步骤，可以自己定义需要的步骤，如果有多个可以依次添加
        SpringModuleConfigStep moduleConfigStep = new SpringModuleConfigStep(new ProjectConfigUI());
        // 添加项目依赖模块，根据选择的模块生成对应的基础代码
        DependencyConfigStep dependencyConfigStep = new DependencyConfigStep(new DependencyConfigUI());

        return new ModuleWizardStep[]{moduleConfigStep, dependencyConfigStep};
    }

    private void reloadProject(Project project) {
        ProjectManagerEx projectManager = ProjectManagerEx.getInstanceEx();
        projectManager.reloadProject(project);
    }

}

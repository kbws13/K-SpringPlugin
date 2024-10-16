package xyz.kbws.domain.service;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import xyz.kbws.domain.model.vo.ProjectConfigVO;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author kbws
 * @date 2024/10/6
 * @description:
 */
public abstract class AbstractProjectGenerator extends FreemarkerConfiguration implements IProjectGenerator {
    @Override
    public void doGenerator(Project project, String entryPath, ProjectConfigVO projectConfig) {
        // 1.创建工程主POM文件
        generateProjectPOM(project, entryPath, projectConfig);

        // 2.创建四层架构
        generateProject(project, entryPath, projectConfig);

        // 3.创建 Application
        generateApplication(project, entryPath, projectConfig);

        // 4. 创建 Yml
        generateYml(project, entryPath, projectConfig);

        // 5. 创建 Common
        //generateCommon(project, entryPath, projectConfig);
    }

    protected abstract void generateProjectPOM(Project project, String entryPath, ProjectConfigVO projectConfig);

    protected abstract void generateProject(Project project, String entryPath, ProjectConfigVO projectConfig);

    protected abstract void generateApplication(Project project, String entryPath, ProjectConfigVO projectConfig);

    protected abstract void generateYml(Project project, String entryPath, ProjectConfigVO projectConfig);

    protected abstract void generateCommon(Project project, String entryPath, ProjectConfigVO projectConfig);

    public void writeFile(Project project, String packageName, String entryPath, String name, String ftl, Object dataModel) {
        VirtualFile virtualFile;
        try {
            virtualFile = createPackageDir(packageName, entryPath).createChildData(project, name);
            StringWriter stringWriter = new StringWriter();
            Template template = super.getTemplate(ftl);
            template.process(dataModel, stringWriter);
            virtualFile.setBinaryContent(stringWriter.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private static VirtualFile createPackageDir(String packageName, String entryPath) {
        String path = FileUtil.toSystemIndependentName(entryPath + "/" + StringUtil.replace(packageName, ".", "/"));
        new File(path).mkdirs();
        return LocalFileSystem.getInstance().refreshAndFindFileByPath(path);
    }

    public void createIdeaFiles(Project project, String entryPath, ProjectConfigVO projectConfig) {
        // Create .idea directory
        new File(entryPath + "/.idea").mkdirs();

        // Create modules.xml
        writeFile(project, ".idea", entryPath, "modules.xml", "idea/modules.ftl", projectConfig);

        // Create .iml file
        writeFile(project, "", entryPath, projectConfig.get_artifactId() + ".iml", "idea/module.ftl", projectConfig);

        // Create misc.xml
        writeFile(project, ".idea", entryPath, "misc.xml", "idea/misc.ftl", projectConfig);

        // Create workspace.xml
        writeFile(project, ".idea", entryPath, "workspace.xml", "idea/workspace.ftl", projectConfig);
    }

}

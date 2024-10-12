package xyz.kbws.domain.service.impl;

import com.intellij.openapi.project.Project;
import xyz.kbws.domain.model.vo.DependencyConfigVO;
import xyz.kbws.domain.model.vo.ProjectConfigVO;
import xyz.kbws.domain.service.AbstractProjectGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author kbws
 * @date 2024/10/6
 * @description:
 */
public class ProjectGeneratorImpl extends AbstractProjectGenerator {
    @Override
    protected void generateProjectPOM(Project project, String entryPath, ProjectConfigVO projectConfig) {
        writeFile(project, "/", entryPath, "pom.xml", "pom.ftl", projectConfig);
    }

    @Override
    protected void generateProject(Project project, String entryPath, ProjectConfigVO projectConfig) {
        // create controller
        writeFile(project, "src/main/java/" + projectConfig.get_package() + ".controller", entryPath, "package-info.java", "controller/package-info.ftl", projectConfig);

        // create service
        writeFile(project, "src/main/java/" + projectConfig.get_package() + ".service", entryPath, "package-info.java", "service/package-info.ftl", projectConfig);
        writeFile(project, "src/main/java/" + projectConfig.get_package() + ".service.impl", entryPath, "package-info.java", "service/impl/package-info.ftl", projectConfig);

        // create mapper
        writeFile(project, "src/main/java/" + projectConfig.get_package() + ".mapper", entryPath, "package-info.java", "mapper/package-info.ftl", projectConfig);

        // create model
        writeFile(project, "src/main/java/" + projectConfig.get_package() + ".model.enums", entryPath, "UserRoleEnum.java", "model/enums/UserRoleEnum.ftl", projectConfig);

        // Add this line to create .idea files
        createIdeaFiles(project, entryPath, projectConfig);

        // create interfaces
        //writeFile(project, "src/main/java/" + projectConfig.get_package() + ".interfaces", entryPath, "package-info.java", "interfaces/package-info.ftl", projectConfig);

        Map<String, DependencyConfigVO> dependencies = projectConfig.getDependencies();
        for (Map.Entry<String, DependencyConfigVO> entry : dependencies.entrySet()) {
            DependencyConfigVO value = entry.getValue();
            List<String> files = value.getFiles();
            if (files.isEmpty()) {
                continue;
            }
            for (String item : files) {
                String[] split = item.split("/");
                writeFile(project, "src/main/java/" + projectConfig.get_package() + "." + split[0], entryPath, split[1].replace("ftl", "java"), item, projectConfig);
            }
        }

    }

    @Override
    protected void generateApplication(Project project, String entryPath, ProjectConfigVO projectConfig) {
        writeFile(project, "src/main/java/" + projectConfig.get_package(), entryPath, projectConfig.get_artifactId() + "Application.java", "application.ftl", projectConfig);
    }

    @Override
    protected void generateYml(Project project, String entryPath, ProjectConfigVO projectConfig) {
        writeFile(project, "src/main/resources/", entryPath, "application.yml", "yml.ftl", projectConfig);
    }

    @Override
    protected void generateCommon(Project project, String entryPath, ProjectConfigVO projectConfig) {
        writeFile(project, "src/main/java/" + projectConfig.get_package() + "/common", entryPath, "Constants.java", "common/Constants.ftl", projectConfig);
    }
}

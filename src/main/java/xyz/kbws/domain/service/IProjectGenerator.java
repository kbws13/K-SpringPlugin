package xyz.kbws.domain.service;

import com.intellij.openapi.project.Project;
import xyz.kbws.domain.model.vo.ProjectConfigVO;

/**
 * @author kbws
 * @date 2024/10/5
 * @description:
 */
public interface IProjectGenerator {
    void doGenerator(Project project, String entryPath, ProjectConfigVO projectConfig);
}

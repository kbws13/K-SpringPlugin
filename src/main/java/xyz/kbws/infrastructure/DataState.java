package xyz.kbws.infrastructure;

import xyz.kbws.domain.model.vo.ProjectConfigVO;

/**
 * @author kbws
 * @date 2024/10/5
 * @description:
 */
public class DataState {

    private ProjectConfigVO projectConfigVO = new ProjectConfigVO();

    public ProjectConfigVO getProjectConfigVO() {
        return projectConfigVO;
    }

    public void setProjectConfigVO(ProjectConfigVO projectConfigVO) {
        this.projectConfigVO = projectConfigVO;
    }
}

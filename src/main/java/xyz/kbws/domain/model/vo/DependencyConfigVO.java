package xyz.kbws.domain.model.vo;

import java.util.List;

/**
 * @author kbws
 * @date 2024/10/10
 * @description:
 */
public class DependencyConfigVO {
    private String parent;
    private List<String> files;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }
}

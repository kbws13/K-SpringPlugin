package xyz.kbws.ui;

import javax.swing.*;

/**
 * @author kbws
 * @date 2024/10/5
 * @description: 配置工程信息的窗口
 */
public class ProjectConfigUI {
    private JPanel mainPanel;

    private JTextField groupIdField;

    private JTextField artifactIdField;

    private JTextField versionField;

    private JTextField packageField;

    public JComponent getComponent(){
        return mainPanel;
    }

    public JTextField getGroupIdField() {
        return groupIdField;
    }

    public JTextField getArtifactIdField() {
        return artifactIdField;
    }

    public JTextField getVersionField() {
        return versionField;
    }

    public JTextField getPackageField() {
        return packageField;
    }

}

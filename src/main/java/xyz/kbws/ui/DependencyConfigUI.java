package xyz.kbws.ui;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.intellij.ui.treeStructure.Tree;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kbws
 * @date 2024/10/6
 * @description:
 */
public class DependencyConfigUI {
    private JPanel mainPanel;
    private JTree projectTree;
    private JTextArea selectedValuesArea; // 显示选中的节点值的区域

    public DependencyConfigUI() {
        mainPanel = new JPanel(new BorderLayout());
        initializeTree();
        initializeSelectedValuesArea(); // 初始化显示区域
        mainPanel.add(new JScrollPane(projectTree), BorderLayout.CENTER);
        mainPanel.add(new JScrollPane(selectedValuesArea), BorderLayout.EAST); // 添加显示区域
    }

    private void initializeTree() {
        // 创建根节点但不显示其内容
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("请选择要添加的依赖");

        String configStr = HttpUtil.get("https://gitee.com/enteral/k-spring-plugin-config/raw/master/config.json");
        JSONObject configObject = JSONUtil.parseObj(configStr);
        Map<String, List<String>> dependencyMap = configObject.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> ((JSONArray) entry.getValue()).toList(String.class)
                ));

        // 添加子节点
        for (Map.Entry<String, List<String>> entry : dependencyMap.entrySet()) {
            DefaultMutableTreeNode rootNode = createRootNode(entry.getKey(), entry.getValue());
            root.add(rootNode);
        }

        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        projectTree = new Tree(treeModel);
        projectTree.setCellRenderer(new RadioButtonTreeCellRenderer());
        projectTree.setRowHeight(30);  // 设置行高

        // 添加鼠标点击监听器
        projectTree.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                TreePath path = projectTree.getPathForLocation(e.getX(), e.getY());
                if (path != null) {
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
                    if (selectedNode.getUserObject() instanceof RadioButtonNode radioButtonNode) {
                        // 检查当前节点是否已经被选中
                        boolean isSelected = radioButtonNode.isSelected();
                        // 设置当前选中的单选框
                        if (!isSelected) {
                            radioButtonNode.setSelected(true);
                        } else {
                            radioButtonNode.setSelected(false); // 取消选中
                        }

                        projectTree.repaint();  // 刷新树以更新状态
                        updateSelectedValuesDisplay(); // 更新显示区域
                    }
                }
            }
        });
    }

    // 初始化显示区域
    private void initializeSelectedValuesArea() {
        selectedValuesArea = new JTextArea();
        selectedValuesArea.setEditable(false); // 设置为不可编辑
        selectedValuesArea.setLineWrap(true);
        selectedValuesArea.setWrapStyleWord(true);
        selectedValuesArea.setBorder(BorderFactory.createTitledBorder("添加的依赖")); // 设置边框标题
    }

    /**
     * 获取所有被选中节点的值
     *
     * @return 结果
     */
    public List<String> getSelectedNodeValues() {
        Set<String> selectedValues = new HashSet<>();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) projectTree.getModel().getRoot();

        // 遍历树的所有节点，收集被选中的节点
        if (root != null) {
            collectSelectedValues(root, selectedValues);
        }
        return new ArrayList<>(selectedValues);
    }


    // 递归收集选中节点的值
    private void collectSelectedValues(DefaultMutableTreeNode node, Set<String> selectedValues) {
        if (node.getUserObject() instanceof RadioButtonNode radioButtonNode) {
            if (radioButtonNode.isSelected()) {
                selectedValues.add(radioButtonNode.getName());
            }
        }

        // 遍历子节点
        for (int i = 0; i < node.getChildCount(); i++) {
            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) node.getChildAt(i);
            collectSelectedValues(childNode, selectedValues); // 递归处理子节点
        }
    }

    // 更新选中节点的显示
    private void updateSelectedValuesDisplay() {
        List<String> selectedValues = getSelectedNodeValues();
        selectedValuesArea.setText(String.join("\n", selectedValues)); // 将选中的值显示到区域中
    }

    private DefaultMutableTreeNode createRootNode(String name, List<String> children) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(name);
        for (String child : children) {
            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new RadioButtonNode(child));
            rootNode.add(childNode);
        }
        return rootNode;
    }

    public JComponent getComponent() {
        return mainPanel;
    }

    private static class RadioButtonNode {
        private String name;
        private boolean selected;

        public RadioButtonNode(String name) {
            this.name = name;
            this.selected = false;
        }

        public String getName() {
            return name;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }

    private static class RadioButtonTreeCellRenderer implements TreeCellRenderer {
        private final JRadioButton radioButton = new JRadioButton();

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            if (value instanceof DefaultMutableTreeNode node) {
                if (node.getUserObject() instanceof RadioButtonNode radioButtonNode) {
                    radioButton.setText(radioButtonNode.getName());
                    radioButton.setSelected(radioButtonNode.isSelected());
                    radioButton.setEnabled(tree.isEnabled());
                } else {
                    return new JLabel(node.toString()); // 直接返回标签以显示根节点的名称
                }
            }
            return radioButton;
        }
    }

    private static class Dependency {
        private String parent;
        private String node;

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public String getNode() {
            return node;
        }

        public void setNode(String node) {
            this.node = node;
        }
    }
}

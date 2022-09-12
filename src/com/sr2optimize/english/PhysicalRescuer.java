package com.sr2optimize.english;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class PhysicalRescuer {
    public static void main(String[] args) {

        JFrame jFrame = new JFrame("CraftRevise");
        jFrame.setSize(260,165);
        jFrame.setLocationRelativeTo(null);


        JPanel panelWindow = new JPanel();
        panelWindow.setLayout(new FlowLayout(FlowLayout.LEFT));

        //jFrame.setUndecorated(true);

        // 设置程序图标
        ImageIcon icon = new ImageIcon("E:\\optimize\\src\\com\\sr2optimize\\icon.png");
        jFrame.setIconImage(icon.getImage());


        JLabel head = new JLabel("FileName:");

        JTextField fileName = new JTextField(11);
        JCheckBox airResistance = new JCheckBox("Disable Drag");
        JCheckBox physicalCollision = new JCheckBox("Disable Part Collision");
        JButton button = new JButton("Modify");
        JButton settings = new JButton("Settings");

        head.setFont(new Font("微软雅黑",Font.PLAIN,16));
        fileName.setFont(new Font("微软雅黑",Font.PLAIN,14));
        airResistance.setFont(new Font("微软雅黑",Font.PLAIN,14));
        physicalCollision.setFont(new Font("微软雅黑",Font.PLAIN,14));
        // 鼠标悬浮文字
        fileName.setToolTipText("Type in the file name without the suffix \".xml\"");
        airResistance.setToolTipText("Check this option will disable Drag Properties in all the parts of your stuff." +
                "(Including in Drag=off & Drag Scale=0)");
        physicalCollision.setToolTipText("Check this option will change all the Collision Properties in all the parts of your stuff." +
                "(Part Collisiion=Never & Collision Response=None)");

        button.setFont(new Font("微软雅黑",Font.PLAIN,16));
        button.setPreferredSize(new Dimension(115,50)); // 设置按钮尺寸
        button.setFocusPainted(false);
        button.setBackground(new Color(215,215,215));
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        settings.setFont(new Font("微软雅黑",Font.PLAIN,12)); //设置字体样式
        settings.setPreferredSize(new Dimension(60,25)); // 按钮大小
        // 设置透明按钮
        settings.setBackground(new Color(215,215,215));
        //settings.setOpaque(false);

        settings.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // 修改边框颜色
        settings.setFocusPainted(false); // 去掉聚焦样式

        // 取消聚焦样式
        airResistance.setFocusPainted(false);
        physicalCollision.setFocusPainted(false);

        // 报错窗口
        JDialog errorWindow = new JDialog(jFrame,"Error");
        errorWindow.setSize(180,110);
        errorWindow.setLocationRelativeTo(null);

        JPanel jPanel = new JPanel(new FlowLayout());
        JLabel errorText = new JLabel("File does not exist:(");
        errorText.setFont(new Font("微软雅黑",Font.BOLD,14));
        errorText.setForeground(Color.RED);

        JButton nextButton = new JButton("OK");
        nextButton.setPreferredSize(new Dimension(90,45));
        nextButton.setFocusPainted(false);
        nextButton.setBackground(new Color(215,215,215));
        nextButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // 按钮事件
        nextButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                errorWindow.setVisible(false); //点击后关闭错误窗口

            }
        });

        jPanel.add(errorText);
        jPanel.add(nextButton);
        errorWindow.add(jPanel);

        errorWindow.setResizable(false);

        errorWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);


        // 逻辑层开始
        button.addActionListener(new AbstractAction() { // 按下确定按钮时
            @Override
            public void actionPerformed(ActionEvent e) {

                File ifFile = new File(fileName.getText() + ".xml");
                // 错误窗口
                if (!ifFile.exists()){
                    errorWindow.setVisible(true);
                }

                try {
                    // 调用修改xml方法
                    physicalRescuer(fileName.getText(),airResistance.isSelected(),physicalCollision.isSelected());
                } catch (DocumentException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });


        // 设置面板窗体
        JDialog settingsWindow = new JDialog(jFrame,"Settings");
        settingsWindow.setSize(150,150);
        settingsWindow.setLocationRelativeTo(null);
        settingsWindow.setIconImage(icon.getImage()); // 程序图标

        JPanel setPanel = new JPanel();
        setPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel languageLabel = new JLabel("Language:");
        JComboBox comboBox = new JComboBox(); // 更改语言的下拉列表选项
        comboBox.addItem("English");
        comboBox.addItem("简体中文");
        JButton reviseButton = new JButton("OK");
        JLabel developer = new JLabel("By JasonCaesar007");


        // 设置组件字体
        languageLabel.setFont(new Font("微软雅黑",Font.PLAIN,14));
        comboBox.setFont(new Font("微软雅黑",Font.PLAIN,12));
        reviseButton.setFont(new Font("微软雅黑",Font.PLAIN,14));
        developer.setFont(new Font("微软雅黑",Font.ITALIC,13));

        // 设置组件大小
        comboBox.setPreferredSize(new Dimension(120,25));
        languageLabel.setPreferredSize(new Dimension(120,20));
        developer.setPreferredSize(new Dimension(120,20));
        reviseButton.setPreferredSize(new Dimension(80,30));

        // 设置组件样式
        reviseButton.setFocusPainted(false);
        reviseButton.setBackground(new Color(215,215,215));
        reviseButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // 按钮事件
        reviseButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox.getSelectedItem() == "English"){
                    System.out.println("语言已设置为英语");

                    jFrame.setTitle("CraftRevise");
                    head.setText("FileName:");
                    fileName.setToolTipText("Type in the file name without the suffix \".xml\"");
                    airResistance.setText("Disable Drag");
                    physicalCollision.setText("Disable Part Collision");
                    airResistance.setFont(new Font("微软雅黑",Font.PLAIN,14));
                    physicalCollision.setFont(new Font("微软雅黑",Font.PLAIN,14));

                    button.setText("Modify");
                    button.setPreferredSize(new Dimension(115,50));
                    settings.setText("Settings");
                    settings.setPreferredSize(new Dimension(60,25)); // 缩小按钮

                    airResistance.setToolTipText("Check this option will disable Drag Properties in all the parts of your stuff." +
                            "(Including in Drag=off & Drag Scale=0)");
                    physicalCollision.setToolTipText("Check this option will change all the Collision Properties in all the parts of your stuff." +
                            "(Part Collisiion=Never & Collision Response=None)");
                    errorWindow.setTitle("Error");
                    errorText.setText("File does not exist:(");
                    nextButton.setText("OK");
                    settingsWindow.setTitle("Settings");
                    languageLabel.setText("Language:");
                    reviseButton.setText("OK");

                }else if (comboBox.getSelectedItem() == "简体中文"){
                    System.out.println("语言已设置为中文");

                    jFrame.setTitle("存档优化工具");
                    head.setText("文件名称：");
                    fileName.setToolTipText("请输入不带后缀的文件名，去掉.xml");
                    airResistance.setText("关闭空气阻力");
                    physicalCollision.setText("关闭物理碰撞");

                    button.setText("确定");
                    settings.setText("设置");
                    settings.setPreferredSize(new Dimension(100,25)); // 还原按钮大小

                    airResistance.setToolTipText("勾选此选项将关闭存档中的所有部件的空气阻力");
                    physicalCollision.setToolTipText("勾选此选项将修改存档中所有部件的物理碰撞机制");
                    errorWindow.setTitle("错误");
                    errorText.setText("文件不存在，请重新输入");
                    nextButton.setText("确定");
                    settingsWindow.setTitle("设置");
                    languageLabel.setText("界面语言：");
                    reviseButton.setText("确定");

                }

                settingsWindow.setVisible(false);
            }
        });

        // 载入窗体
        setPanel.add(languageLabel);
        setPanel.add(comboBox);
        setPanel.add(reviseButton);
        setPanel.add(developer);
        settingsWindow.add(setPanel);


        settingsWindow.setResizable(false);
        settingsWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);


        // 设置按钮事件
        settings.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsWindow.setVisible(true);
            }
        });


        // 载入窗体

        panelWindow.add(head);
        panelWindow.add(fileName);
        panelWindow.add(airResistance);
        panelWindow.add(button);
        panelWindow.add(physicalCollision);
        panelWindow.add(settings);
        jFrame.add(panelWindow);

        jFrame.setResizable(true); // 固定窗口尺寸不可调整
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    private static void physicalRescuer(String filePath, boolean but1, boolean but2) throws DocumentException, IOException {
        // 修改xml方法
        SAXReader saxReader = new SAXReader();

        // 载入xml文件
        Document read = saxReader.read(filePath + ".xml");

        Element rootElement = read.getRootElement();

        List<Element> parts = rootElement.element("Assembly").element("Parts").elements("Part");

        if(but1){ // 循环遍历修改Drag节点与config节点与空气阻力的属性
            parts.forEach(ret->{
                Element drag = ret.element("Drag");
                Element config = ret.element("Config");
                drag.addAttribute("drag","0,0,0,0,0,0");
                drag.addAttribute("area","0,0,0,0,0,0");
                config.addAttribute("includeInDrag","false");
                config.addAttribute("dragScale","0");
            });
        }

        if (but2){ // 循环遍历修改config节点与物理碰撞有关的属性
            parts.forEach(ret->{
                Element config = ret.element("Config");
                config.addAttribute("partCollisionHandling","Never");
                config.addAttribute("partCollisionResponse","None");
            });
        }

        System.out.println("处理完成");
        // 创建输出流导出文件
        OutputFormat outputFormat = OutputFormat.createPrettyPrint();
        outputFormat.setEncoding(StandardCharsets.UTF_8.name());
        FileOutputStream fos = new FileOutputStream(filePath + "-Edit.xml");
        XMLWriter xmlWriter = new XMLWriter(fos,outputFormat);
        xmlWriter.write(read);
        System.exit(0);

    }

}

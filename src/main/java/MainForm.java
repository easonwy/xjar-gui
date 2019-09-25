import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ewu
 * @date 2019-09-23 下午 5:00
 **/
public class MainForm {
    private JProgressBar progressBar1;
    private JTextField txtSrcJarPath;
    private JTextField txtPassword;
    private JTextField txtDestJarPath;
    private JCheckBox chkBoxGenerateKey;
    private JTextPane txtExcludeResources;
    private JButton btnEncrypt;
    private JButton btnBrowserSrc;
    private JButton btnBrowseDest;
    private JPanel rootPanel;
    private JPanel root;
    private JButton btnDecrypt;
    private JComboBox cbxJarType;

    private final int MAX = 100;

    public MainForm() {

        // http://c.biancheng.net/view/1252.html
        btnBrowserSrc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser srcBrowse = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("Jar File", "jar");
                srcBrowse.setDialogTitle("Choose Jar file");
                srcBrowse.setFileFilter(filter);
                int val=srcBrowse.showOpenDialog(null);    //文件打开对话框
                if (val == JFileChooser.APPROVE_OPTION) {
                    txtSrcJarPath.setText(srcBrowse.getSelectedFile().getAbsolutePath());
                    if(StrUtil.isBlank(txtDestJarPath.getText())) {
                        txtDestJarPath.setText(srcBrowse.getSelectedFile().getAbsolutePath());
                    }
                }
            }
        });

        btnBrowseDest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser destBrowse = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("Jar File", "jar");
                destBrowse.setDialogTitle("Choose Jar file");
                destBrowse.setFileFilter(filter);
                int val=destBrowse.showOpenDialog(null);    //文件打开对话框
                if (val == JFileChooser.APPROVE_OPTION) {
                    txtDestJarPath.setText(destBrowse.getSelectedFile().getAbsolutePath());
                }
            }
        });


        btnEncrypt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 校验输入
                String srcJarFile = txtSrcJarPath.getText();
                String srcDesFile = txtDestJarPath.getText();
                String password = txtPassword.getText();
                boolean needGenerateKeyFile = chkBoxGenerateKey.isSelected();
                String excludedFilters = txtExcludeResources.getText();

                List<String> filterList = new ArrayList<String>();

                if (StrUtil.isNotBlank(excludedFilters)) {
                    String[] lines = excludedFilters.split("\\n");
                    if (lines != null && lines.length > 0) {
                        for (String line: lines) {
                            filterList.add(line);
                        }
                    }
                }

                if (StrUtil.isBlank(srcJarFile)) {
                    JOptionPane.showMessageDialog(null, "Please provide source jar file");
                    return;
                }

                if (StrUtil.isBlank(srcDesFile)) {
                    JOptionPane.showMessageDialog(null, "Please provide destination jar file");
                    return;
                }

                if (StrUtil.isBlank(password)) {
                    JOptionPane.showMessageDialog(null, "Password is required");
                    return;
                }

                if (srcDesFile.equalsIgnoreCase(srcJarFile)) {
                    JOptionPane.showMessageDialog(null, "Destination jar file name should not be same with source jar file.");
                    return;
                }

                EncryptJarModel param = new EncryptJarModel();
                param.setSrcJarName(srcJarFile);
                param.setDestJarName(srcDesFile);
                param.setPassword(password);
                param.setGenerateKey(needGenerateKeyFile);
                param.setExcludedResourceList(filterList);
                param.setJarType(cbxJarType.getSelectedItem().toString());

                try {
                    Boolean encryptResult = XjarUtil.encryptJar(param);
                    if (encryptResult) {
                        JOptionPane.showMessageDialog(null, "encrypt done");
                        XjarUtil.openFolder(FileUtil.getParent(param.getDestJarName(), 1));
                    } else {
                        JOptionPane.showMessageDialog(null, "encrypt fail");
                    }

                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, String.format("something wrong, exception: ", e1.getMessage()));
                }
            }
        });


        btnDecrypt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String srcJarFile = txtSrcJarPath.getText();
                String srcDesFile = txtDestJarPath.getText();
                String password = txtPassword.getText();
                boolean needGenerateKeyFile = chkBoxGenerateKey.isSelected();
                String excludedFilters = txtExcludeResources.getText();
                List<String> filterList = new ArrayList<String>();

                if (StrUtil.isNotBlank(excludedFilters)) {
                    String[] lines = excludedFilters.split("\\\\n");
                    if (lines != null && lines.length > 0) {
                        for (String line: lines) {
                            filterList.add(line);
                        }
                    }
                }

                if (StrUtil.isBlank(srcJarFile)) {
                    JOptionPane.showMessageDialog(null, "Please provide source jar file");
                    return;
                }

                if (StrUtil.isBlank(srcDesFile)) {
                    JOptionPane.showMessageDialog(null, "Please provide destination jar file");
                    return;
                }

                if (StrUtil.isBlank(password)) {
                    JOptionPane.showMessageDialog(null, "Password is required");
                    return;
                }

                if (srcDesFile.equalsIgnoreCase(srcJarFile)) {
                    JOptionPane.showMessageDialog(null, "Destination jar file name should not be same with source jar file.");
                    return;
                }


                EncryptJarModel param = new EncryptJarModel();
                param.setSrcJarName(srcJarFile);
                param.setDestJarName(srcDesFile);
                param.setPassword(password);
                param.setGenerateKey(needGenerateKeyFile);
                param.setExcludedResourceList(filterList);
                param.setJarType(cbxJarType.getSelectedItem().toString());

                progressBar1.setValue(0);
                progressBar1.setStringPainted(true);

                try {
                    Boolean encryptResult = XjarUtil.decryptJar(param);
                    if (encryptResult) {
                        JOptionPane.showMessageDialog(null, "encrypt done");
                        XjarUtil.openFolder(FileUtil.getParent(param.getDestJarName(), 1));
                    } else {
                        JOptionPane.showMessageDialog(null, "encrypt fail");
                    }
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, String.format("something wrong, exception: ", e1.getMessage()));
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("XJar加解密GUI工具");
        JPanel rootPanel = new MainForm().root;
        frame.setContentPane(rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        URL iconUrl = ResourceUtil.getResource("main.png");
        ImageIcon img = new ImageIcon(iconUrl);

        frame.setSize(800, 500);
        frame.setLocationRelativeTo(rootPanel);
        frame.setResizable(false);

        frame.setIconImage(img.getImage());

        frame.setVisible(true);


    }
}

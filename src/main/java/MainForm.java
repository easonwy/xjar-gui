import javax.swing.*;

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

    public MainForm() {

    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("XJar加密工具");
        JPanel rootPanel = new MainForm().root;
        frame.setContentPane(rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setSize(800, 500);
        frame.setLocationRelativeTo(rootPanel);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}

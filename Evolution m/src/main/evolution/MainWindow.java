package evolution;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private static Color c_area = new Color(115, 157, 52);
    private static Color c_info = new Color(232, 240, 223);
    private static Color c_info_light = new Color(238, 246, 232);
    private static Color c_info_dark = new Color(198, 232, 183);
    private JLabel[] labels;
    private JButton submit;
    private JTextField[] inputs;
    private JSpinner windowsNum;
    private int unitWidth = 190;
    private int unitHeight = 48;

    public static void showMainWindow(){
        Parameters param = new Parameters();
        MainWindow mWnd = new MainWindow(param);
        JPanel panel = new JPanel();
        mWnd.add(panel);
        addComponents(mWnd, panel, param);
        mWnd.setVisible(true);
        addActionListeners(mWnd, param);
    }
    private MainWindow(Parameters param){
        super("Evolution Simulator");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        int width = 420;
        int height = 640;
        setSize(width, height);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((dimension.width - width)/2, (dimension.height - height)/2);

        Border whiteline = BorderFactory.createLineBorder(Color.white);
        this.labels = new JLabel[9];
        for(int i=0; i<9; i++){
            labels[i] = new JLabel();
            labels[i].setFont(new Font("Consolas", Font.PLAIN, 22));
            labels[i].setBorder(whiteline);
            labels[i].setOpaque(true);
            labels[i].setBackground(c_info);
            labels[i].setHorizontalAlignment(JLabel.CENTER);
            labels[i].setVerticalAlignment(JLabel.CENTER);
        } labels[0].setFont(new Font("Consolas", Font.PLAIN, 26));

        labels[0].setPreferredSize(new Dimension(2*this.unitWidth, 2*this.unitHeight));
        for(int i=1; i<9; i++)
            labels[i].setPreferredSize(new Dimension(1*this.unitWidth, 1*this.unitHeight));

        this.inputs = new JTextField[7];
        inputs[0] = new JTextField(String.valueOf(param.width));
        inputs[1] = new JTextField(String.valueOf(param.height));
        inputs[2] = new JTextField(String.valueOf(param.startEnergy));
        inputs[3] = new JTextField(String.valueOf(param.moveEnergy));
        inputs[4] = new JTextField(String.valueOf(param.plantEnergy));
        inputs[5] = new JTextField(String.valueOf(param.jungleRatio));
        inputs[6] = new JTextField(String.valueOf(param.animals));
        for(int i=0; i<7; i++){
            inputs[i].setFont(new Font("Consolas", Font.PLAIN, 22));
            inputs[i].setPreferredSize(new Dimension(1*this.unitWidth, 1*this.unitHeight));
            inputs[i].setHorizontalAlignment(JTextField.CENTER);
            inputs[i].setBorder(whiteline);
            inputs[i].setOpaque(true);
            inputs[i].setBackground(c_info_light);
        }

        windowsNum = new JSpinner(new SpinnerNumberModel(1,1,5,1));
        windowsNum.setEditor(new JSpinner.DefaultEditor(windowsNum));
        windowsNum.setFont(new Font("Consolas", Font.PLAIN, 22));
        windowsNum.setPreferredSize(new Dimension(1*this.unitWidth, 1*this.unitHeight));
        windowsNum.setOpaque(true);
        windowsNum.setBackground(c_info);
        windowsNum.setAlignmentX(JSpinner.CENTER_ALIGNMENT);

        submit = new JButton();
        submit.setFont(new Font("Consolas", Font.PLAIN, 22));
        submit.setBorder(whiteline);
        submit.setBackground(c_info_dark);
        submit.setPreferredSize(new Dimension(2*this.unitWidth, 2*this.unitHeight));
        submit.setHorizontalAlignment(JLabel.CENTER);
        submit.setVerticalAlignment(JLabel.CENTER);
    }
    private static void addComponents(MainWindow mWnd, JPanel panel, Parameters param){
        panel.setLayout(new GridBagLayout());
        panel.setBackground(c_area);
        GridBagConstraints layoutConstraints = new GridBagConstraints();
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 0;
        layoutConstraints.gridwidth = 2;
        layoutConstraints.gridheight = 1;
        panel.add(mWnd.labels[0], layoutConstraints);

        for(int i=0; i<7; i++){
            layoutConstraints.gridx = 0;
            layoutConstraints.gridy = i+1;
            layoutConstraints.gridwidth = 1;
            layoutConstraints.gridheight = 1;
            panel.add(mWnd.labels[i+1], layoutConstraints);

            layoutConstraints.gridx = 1;
            panel.add(mWnd.inputs[i], layoutConstraints);
        }

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 8;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.gridheight = 1;
        panel.add(mWnd.labels[8], layoutConstraints);

        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 8;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.gridheight = 1;
        panel.add(mWnd.windowsNum, layoutConstraints);

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 9;
        layoutConstraints.gridwidth = 2;
        layoutConstraints.gridheight = 1;
        panel.add(mWnd.submit, layoutConstraints);

        mWnd.labels[0].setText("<html><center>EVOLUTION SIM<br/>settings</center></html>");
        mWnd.labels[1].setText("<html>Width:</html>");
        mWnd.labels[2].setText("<html>Height:</html>");
        mWnd.labels[3].setText("<html>Start energy:</html>");
        mWnd.labels[4].setText("<html>Move energy:</html>");
        mWnd.labels[5].setText("<html>Plant energy:</html>");
        mWnd.labels[6].setText("<html>Jungle ratio:</html>");
        mWnd.labels[7].setText("<html>Animals:</html>");
        mWnd.labels[8].setText("<html>Windows:</html>");
        mWnd.submit.setText("<html><center><b>Run the simulations!</b><br/>" +
                            "Let Darwin be proud of you!</center></html>");
    }
    private static void addActionListeners(MainWindow mWnd, Parameters param) {
        mWnd.submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                param.width = Integer.parseInt(mWnd.inputs[0].getText());
                param.height = Integer.parseInt(mWnd.inputs[1].getText());
                param.startEnergy = Integer.parseInt(mWnd.inputs[2].getText());
                param.moveEnergy = Integer.parseInt(mWnd.inputs[3].getText());
                param.plantEnergy = Integer.parseInt(mWnd.inputs[4].getText());
                param.jungleRatio = Double.parseDouble(mWnd.inputs[5].getText());
                param.animals = Integer.parseInt(mWnd.inputs[6].getText());
                int sims = (Integer) mWnd.windowsNum.getValue();

                for(int i=0; i<sims; i++){
                    Window.runTheWindowSimulation(param);
                }
                mWnd.dispose();
            }
        });
    }
}

package evolution;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SettingsWindow extends JFrame {
    private static Color c_area = new Color(115, 157, 52);
    private static Color c_info = new Color(232, 240, 223);
    private static Color c_info_light = new Color(238, 246, 232);
    private static Color c_info_dark = new Color(198, 232, 183);
    private JLabel[] labels;
    private JButton[] buttons;
    private JTextField[] inputs;
    private int unitWidth = 45;
    private int unitHeight = 50;

    public static void showSettingsWindow(Window wnd, Simulation sim){
        Parameters param = new Parameters();
        SettingsWindow sWnd = new SettingsWindow(sim);
        JPanel panel = new JPanel();
        sWnd.add(panel);
        addComponents(sWnd, panel);
        sWnd.setVisible(true);
        addActionListeners(sWnd, wnd, sim);
    }
    private SettingsWindow(Simulation sim){
        super("Settings");
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);
        int width = 450;
        int height = 500;
        setSize(width, height);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((dimension.width - width)/2, (dimension.height - height)/2);

        Border whiteline = BorderFactory.createLineBorder(Color.white);
        this.labels = new JLabel[4];
        for(int i=0; i<4; i++){
            labels[i] = new JLabel();
            labels[i].setBorder(whiteline);
            labels[i].setOpaque(true);
            labels[i].setBackground(c_info);
            labels[i].setHorizontalAlignment(JLabel.CENTER);
            labels[i].setVerticalAlignment(JLabel.CENTER);
        }
        labels[0].setPreferredSize(new Dimension(9*this.unitWidth, 1*this.unitHeight));
        labels[0].setFont(new Font("Consolas", Font.PLAIN, 26));
        labels[1].setPreferredSize(new Dimension(9*this.unitWidth, 1*this.unitHeight));
        labels[1].setFont(new Font("Consolas", Font.PLAIN, 14));
        labels[2].setPreferredSize(new Dimension(6*this.unitWidth, 1*this.unitHeight));
        labels[2].setFont(new Font("Consolas", Font.PLAIN, 18));
        labels[3].setPreferredSize(new Dimension(6*this.unitWidth, 1*this.unitHeight));
        labels[3].setFont(new Font("Consolas", Font.PLAIN, 18));

        buttons = new JButton[6];
        for(int i=0; i<6; i++){
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Consolas", Font.PLAIN, 22));
            buttons[i].setBorder(whiteline);
            buttons[i].setBackground(c_info_dark);
            buttons[i].setPreferredSize(new Dimension(2*this.unitWidth, 2*this.unitHeight));
            buttons[i].setHorizontalAlignment(JLabel.CENTER);
            buttons[i].setVerticalAlignment(JLabel.CENTER);
        }
        buttons[0].setPreferredSize(new Dimension(9*this.unitWidth, 1*this.unitHeight));
        buttons[1].setPreferredSize(new Dimension(9*this.unitWidth, 1*this.unitHeight));
        buttons[2].setPreferredSize(new Dimension(1*this.unitWidth, 1*this.unitHeight));
        buttons[3].setPreferredSize(new Dimension(1*this.unitWidth, 1*this.unitHeight));
        buttons[4].setPreferredSize(new Dimension(9*this.unitWidth, 1*this.unitHeight));
        buttons[5].setPreferredSize(new Dimension(9*this.unitWidth, 1*this.unitHeight));

        inputs = new JTextField[2];
        inputs[0] = new JTextField(String.valueOf(sim.getPlantEnergy()));
        inputs[1] = new JTextField(String.valueOf(sim.getSimDelay()));
        for(int i=0; i<2; i++){
            inputs[i].setFont(new Font("Consolas", Font.PLAIN, 22));
            inputs[i].setPreferredSize(new Dimension(2*this.unitWidth, 1*this.unitHeight));
            inputs[i].setHorizontalAlignment(JTextField.CENTER);
            inputs[i].setBorder(whiteline);
            inputs[i].setOpaque(true);
            inputs[i].setBackground(c_info_light);
        }
    }
    private static void addComponents(SettingsWindow sWnd, JPanel panel){
        panel.setLayout(new GridBagLayout());
        panel.setBackground(c_area);
        GridBagConstraints layoutConstraints = new GridBagConstraints();
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 0;
        layoutConstraints.gridwidth = 3;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.labels[0], layoutConstraints);

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 1;
        layoutConstraints.gridwidth = 3;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.buttons[0], layoutConstraints);

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 2;
        layoutConstraints.gridwidth = 3;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.buttons[1], layoutConstraints);

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 3;
        layoutConstraints.gridwidth = 3;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.labels[1], layoutConstraints);

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 4;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.labels[2], layoutConstraints);

        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 4;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.inputs[0], layoutConstraints);

        layoutConstraints.gridx = 2;
        layoutConstraints.gridy = 4;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.buttons[2], layoutConstraints);

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 5;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.labels[3], layoutConstraints);

        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 5;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.inputs[1], layoutConstraints);

        layoutConstraints.gridx = 2;
        layoutConstraints.gridy = 5;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.buttons[3], layoutConstraints);

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 6;
        layoutConstraints.gridwidth = 3;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.buttons[4], layoutConstraints);

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 7;
        layoutConstraints.gridwidth = 3;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.buttons[5], layoutConstraints);

        sWnd.labels[0].setText("<html>SETTINGS</html>");
        sWnd.labels[1].setText("<html><center>TIP: To track animal<br/>" +
                               "choose the field in simulation first.<center></html>");
        sWnd.labels[2].setText("<html>Change plant energy:</html>");
        sWnd.labels[3].setText("<html>Change simulation delay:</html>");
        sWnd.buttons[0].setText("<html>Show dominants</html>");
        sWnd.buttons[1].setText("<html>Track chosen animal</html>");
        sWnd.buttons[2].setText("<html>OK</html>");
        sWnd.buttons[3].setText("<html>OK</html>");
        sWnd.buttons[4].setText("<html>QUIT SETTINGS</html>");
        sWnd.buttons[5].setText("<html>END SIMULATION</html>");
    }
    private static void addActionListeners(SettingsWindow sWnd, Window wnd, Simulation sim){
        sWnd.buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sim.markDominants();
            }
        });
        sWnd.buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Animal focused = sim.getStrongest(Window.getFocus(wnd));
                if(focused != null){
                    focused.setToBeTracked();
                    Window.markAsTracked(wnd, focused.position.x, focused.position.y);
                    sim.turnTrackingOn();
                }
            }
        });
        sWnd.buttons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int newPlantEnergy = Integer.parseInt(sWnd.inputs[0].getText());
                sim.changePlantEnergy(newPlantEnergy);
            }
        });
        sWnd.buttons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int newSimDelay = Integer.parseInt(sWnd.inputs[1].getText());
                sim.changeSimDelay(newSimDelay);
            }
        });
        sWnd.buttons[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window.closedSettingsWindow(wnd);
                sWnd.dispose();
            }
        });
        sWnd.buttons[5].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window.terminateSimulation(wnd);
                sWnd.dispose();
            }
        });
    }
}

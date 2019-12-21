package evolution;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class Window extends JFrame {
    private static Color c_info = new Color(232, 240, 223);
    private static Color c_info_clicked = new Color(196, 209, 182);
    private static Color c_area = new Color(115, 157, 52);
    private static ImageIcon BASE_i_animal = new ImageIcon("src\\img\\animal.png");
    private static ImageIcon BASE_i_animal_D = new ImageIcon("src\\img\\animal_D.png");
    private static ImageIcon BASE_i_animal_H = new ImageIcon("src\\img\\animal_H.png");
    private static ImageIcon BASE_i_animal_T = new ImageIcon("src\\img\\animal_T.png");
    private static ImageIcon BASE_i_animal_J = new ImageIcon("src\\img\\animal_J.png");
    private static ImageIcon BASE_i_plant = new ImageIcon("src\\img\\plant.png");
    private static ImageIcon BASE_i_ground = new ImageIcon("src\\img\\ground.png");
    private static ImageIcon BASE_i_settings = new ImageIcon("src\\img\\settings.png");
    private ImageIcon i_animal;
    private ImageIcon i_animal_D;
    private ImageIcon i_animal_H;
    private ImageIcon i_animal_T;
    private ImageIcon i_animal_J;
    private ImageIcon i_plant;
    private ImageIcon i_ground;
    private ImageIcon i_settings;
    private int width;
    private int height;
    private int cellDim;
    private int infoX_px;
    private int infoY_px;
    private boolean isSettingWindowOn = false;
    private Vector2d focus = null;
    private JButton[][] cells;
    private JButton[] infos;

    public static void runTheWindowSimulation(Parameters param){
        Window wnd = new Window(param.width, param.height);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        wnd.add(panel);
        addComponents(wnd, panel);
        wnd.setVisible(true);

        mainSimulation(wnd, panel, param);
    }
    private static void mainSimulation(Window wnd, JPanel panel, Parameters param){
        Simulation sim = new Simulation(param, wnd);
        addActionListeners(wnd, sim);
        addMapInsightClickListeners(wnd, sim);
    }
    private Window(int width, int height){
        super("The evolution simulator");
        this.width = width;
        this.height = height;
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.cellDim = (int) Math.floor(Toolkit.getDefaultToolkit().getScreenSize().height / Math.max(width, height));
        if(cellDim * height > Toolkit.getDefaultToolkit().getScreenSize().height - 28) this.cellDim--;

        this.i_animal = new ImageIcon(BASE_i_animal.getImage().getScaledInstance(cellDim, cellDim, Image.SCALE_SMOOTH));
        this.i_animal_D = new ImageIcon(BASE_i_animal_D.getImage().getScaledInstance(cellDim, cellDim, Image.SCALE_SMOOTH));
        this.i_animal_H = new ImageIcon(BASE_i_animal_H.getImage().getScaledInstance(cellDim, cellDim, Image.SCALE_SMOOTH));
        this.i_animal_T = new ImageIcon(BASE_i_animal_T.getImage().getScaledInstance(cellDim, cellDim, Image.SCALE_SMOOTH));
        this.i_animal_J = new ImageIcon(BASE_i_animal_J.getImage().getScaledInstance(cellDim, cellDim, Image.SCALE_SMOOTH));
        this.i_plant = new ImageIcon(BASE_i_plant.getImage().getScaledInstance(cellDim, cellDim, Image.SCALE_SMOOTH));
        this.i_ground = new ImageIcon(BASE_i_ground.getImage().getScaledInstance(cellDim, cellDim, Image.SCALE_SMOOTH));

        Border emptyBorder = BorderFactory.createEmptyBorder();
        Dimension dims = new Dimension(this.cellDim, this.cellDim);

        this.cells = new JButton[this.width][this.height];
        for(int i=0; i<this.width; i++){
            for(int j=0; j<this.height; j++){
                cells[i][j] = new JButton();
                cells[i][j].setPreferredSize(dims);
                cells[i][j].setBorder(emptyBorder);
                cells[i][j].setIcon(this.i_ground);
            }
        }

        this.infoX_px = (Toolkit.getDefaultToolkit().getScreenSize().width - this.width * this.cellDim - 200)/6;
        this.infoY_px = 55;

        Border whiteline = BorderFactory.createLineBorder(Color.white);
        this.infos = new JButton[23];

        for(int i=0; i<23; i++){
            infos[i] = new JButton();
            infos[i].setFont(new Font("Consolas", Font.PLAIN, 22));
            infos[i].setBorder(whiteline);
            infos[i].setBackground(c_info);
        } infos[4].setBackground(c_info_clicked);

        infos[0].setPreferredSize(new Dimension(5*this.infoX_px, 1*this.infoY_px));
        infos[1].setPreferredSize(new Dimension(1*this.infoX_px, 1*this.infoY_px));
        for(int i=2; i<=7; i++)
            infos[i].setPreferredSize(new Dimension(1*this.infoX_px, 1*this.infoY_px));
        for(int i=8; i<=19; i++)
            infos[i].setPreferredSize(new Dimension(3*this.infoX_px, 1*this.infoY_px));
        infos[20].setPreferredSize(new Dimension(3*this.infoX_px, 3*this.infoY_px));
        infos[21].setPreferredSize(new Dimension(3*this.infoX_px, 3*this.infoY_px));
        infos[22].setPreferredSize(new Dimension(6*this.infoX_px, 6*this.infoY_px));

        i_settings = new ImageIcon(BASE_i_settings.getImage().getScaledInstance(
                5*this.infoY_px/7, 5*this.infoY_px/7, Image.SCALE_SMOOTH));
        infos[1].setBorder(whiteline);
        infos[1].setIcon(i_settings);
    }
    private static void addComponents(Window wnd, JPanel panel){
        JPanel controls = new JPanel(new GridBagLayout());
        panel.add(controls);
        JPanel scene = new JPanel(new GridBagLayout());
        panel.add(scene);
        controls.setBackground(c_area);
        scene.setBackground(c_area);

        GridBagConstraints layoutConstraints = new GridBagConstraints();
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 0;
        layoutConstraints.gridwidth = 5;
        layoutConstraints.gridheight = 1;
        controls.add(wnd.infos[0], layoutConstraints);

        layoutConstraints.gridx = 5;
        layoutConstraints.gridy = 0;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.gridheight = 1;
        controls.add(wnd.infos[1], layoutConstraints);

        for(int i=2; i<=7; i++){
            layoutConstraints.gridx = i-2;
            layoutConstraints.gridy = 1;
            layoutConstraints.gridwidth = 1;
            layoutConstraints.gridheight = 1;
            controls.add(wnd.infos[i], layoutConstraints);
        }

        layoutConstraints.gridwidth = 3;
        layoutConstraints.gridheight = 1;
        for(int i=1; i<=6; i++){
            layoutConstraints.gridx = 0;
            layoutConstraints.gridy = i+1;
            controls.add(wnd.infos[2*i+6], layoutConstraints);

            layoutConstraints.gridx = 3;
            layoutConstraints.gridy = i+1;
            controls.add(wnd.infos[2*i+7], layoutConstraints);
        }

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 8;
        layoutConstraints.gridwidth = 3;
        layoutConstraints.gridheight = 1;
        controls.add(wnd.infos[20], layoutConstraints);

        layoutConstraints.gridx = 3;
        layoutConstraints.gridy = 8;
        layoutConstraints.gridwidth = 3;
        layoutConstraints.gridheight = 1;
        controls.add(wnd.infos[21], layoutConstraints);

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 9;
        layoutConstraints.gridwidth = 6;
        layoutConstraints.gridheight = 1;
        controls.add(wnd.infos[22], layoutConstraints);

        wnd.infos[0].setText("EVOLUTION SIM");
        wnd.infos[2].setText("PLAY");
        wnd.infos[3].setText("PROCEDE");
        wnd.infos[4].setText("1");
        wnd.infos[5].setText("7");
        wnd.infos[6].setText("30");
        wnd.infos[7].setText("100");
        wnd.infos[8].setText("Day no:");
        wnd.infos[9].setText("1");
        wnd.infos[10].setText("Number of animals:");
        wnd.infos[11].setText("");
        wnd.infos[12].setText("Number of plants:");
        wnd.infos[13].setText("");
        wnd.infos[14].setText("Avg stamina:");
        wnd.infos[15].setText("");
        wnd.infos[16].setText("Avg fertility rate:");
        wnd.infos[17].setText("");
        wnd.infos[18].setText("Most common gene:");
        wnd.infos[19].setText("");
        wnd.infos[20].setText("Most common genotype:");
        wnd.infos[21].setText("");
        wnd.infos[22].setText("Darwin was right!");

        defaultStatView(wnd);

        layoutConstraints = new GridBagConstraints();

        for(int i=0; i<wnd.width; i++){
            for(int j=0; j<wnd.height; j++){
                layoutConstraints.gridx = i;
                layoutConstraints.gridy = j;
                layoutConstraints.gridwidth = 1;
                layoutConstraints.gridheight = 1;
                scene.add(wnd.cells[i][j], layoutConstraints);
            }
        }
    }
    private static void addMapInsightClickListeners(Window wnd, Simulation sim){
        for(int i=0; i<wnd.width; i++){
            for(int j=0; j<wnd.height; j++){
                int finalI = i;
                int finalJ = j;
                wnd.cells[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        wnd.infos[22].setText(sim.getContentHere(finalI, finalJ));
                        wnd.focus = new Vector2d(finalI, finalJ);
                    }
                });
            }
        }
    }
    private static void addActionListeners(Window wnd, Simulation sim){
        wnd.infos[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sim.isRunning()){
                    wnd.infos[2].setText("START");
                    sim.startStopSimulation();
                }
                wnd.isSettingWindowOn = true;
                SettingsWindow.showSettingsWindow(wnd, sim);
            }
        });
        wnd.infos[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(wnd.isSettingWindowOn) return;
                if(sim.isRunning()) wnd.infos[2].setText("START");
                else wnd.infos[2].setText("STOP");
                sim.startStopSimulation();
            }
        });
        wnd.infos[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(wnd.isSettingWindowOn) return;
                sim.procedeNDays();
            }
        });
        wnd.infos[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wnd.infos[4].setBackground(c_info_clicked);
                wnd.infos[5].setBackground(c_info);
                wnd.infos[6].setBackground(c_info);
                wnd.infos[7].setBackground(c_info);
                sim.setAnimationRate(1);
            }
        });
        wnd.infos[5].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wnd.infos[4].setBackground(c_info);
                wnd.infos[5].setBackground(c_info_clicked);
                wnd.infos[6].setBackground(c_info);
                wnd.infos[7].setBackground(c_info);
                sim.setAnimationRate(7);
            }
        });
        wnd.infos[6].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wnd.infos[4].setBackground(c_info);
                wnd.infos[5].setBackground(c_info);
                wnd.infos[6].setBackground(c_info_clicked);
                wnd.infos[7].setBackground(c_info);
                sim.setAnimationRate(30);
            }
        });
        wnd.infos[7].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wnd.infos[4].setBackground(c_info);
                wnd.infos[5].setBackground(c_info);
                wnd.infos[6].setBackground(c_info);
                wnd.infos[7].setBackground(c_info_clicked);
                sim.setAnimationRate(100);
            }
        });
    }
    public static void animalAppearedOn(Window wnd, int x, int y){
        wnd.cells[x][y].setIcon(wnd.i_animal);
    }
    public static void plantAppearedOn(Window wnd, int x, int y){
        wnd.cells[x][y].setIcon(wnd.i_plant);
    }
    public static void somethingDisappeared(Window wnd, int x, int y){
        wnd.cells[x][y].setIcon(wnd.i_ground);
    }
    public static void markAsDominant(Window wnd, int x, int y){
        wnd.cells[x][y].setIcon(wnd.i_animal_D);
    }
    public static void markAsHungry(Window wnd, int x, int y){
        wnd.cells[x][y].setIcon(wnd.i_animal_H);
    }
    public static void markAsTracked(Window wnd, int x, int y){
        wnd.cells[x][y].setIcon(wnd.i_animal_T);
    }
    public static void markAsDescendant(Window wnd, int x, int y){
        wnd.cells[x][y].setIcon(wnd.i_animal_J);
    }
    public static void updateDayNo(Window wnd, int n){
        wnd.infos[9].setText(String.valueOf(n));
    }
    public static void updateNumberOfAnimals(Window wnd, int n){
        wnd.infos[11].setText(String.valueOf(n));
    }
    public static void updateNumberOfPlants(Window wnd, int n){
        wnd.infos[13].setText(String.valueOf(n));
    }
    public static void updateAvgStamina(Window wnd, float avgStm, int reproductiveMin){
        wnd.infos[15].setText(String.format(Locale.US,"%.2f / %d", avgStm, reproductiveMin));
    }
    public static void updateAverageFertility(Window wnd, float avgFert){
        wnd.infos[17].setText(String.format(Locale.US,"%.2f", avgFert));
    }
    public static void updateMostCommonGene(Window wnd, String str){
        wnd.infos[19].setText(str);
    }
    public static void updateMostCommonGenome(Window wnd, String str){
        wnd.infos[21].setText(str);
    }
    public static void defaultStatView(Window wnd) {
        wnd.infos[22].setText("Ya see? Darwin was right!");
        wnd.focus = null;
    }
    public static void closedSettingsWindow(Window wnd){
        wnd.isSettingWindowOn = false;
    }
    public static void terminateSimulation(Window wnd){
        wnd.dispose();
    }
    public static Vector2d getFocus(Window wnd){
        return wnd.focus;
    }
}
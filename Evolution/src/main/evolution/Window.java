package evolution;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame {
    private static Color c_animal = new Color(209, 106, 33);
    private static Color c_plant = new Color(112, 184, 0);
    private static Color c_ground = new Color(225, 204, 58);
    private static Color c_info = new Color(232, 240, 223);
    private static ImageIcon BASE_i_animal = new ImageIcon("src\\img\\animal.png");
    private static ImageIcon BASE_i_plant = new ImageIcon("src\\img\\plant.png");
    private static ImageIcon BASE_i_ground = new ImageIcon("src\\img\\ground.png");
    private ImageIcon i_animal;
    private ImageIcon i_plant;
    private ImageIcon i_ground;
    private int width;
    private int height;
    private int cellDim;
    private int infoX_px;
    private int infoY_cells;
    private JButton[][] cells;
    private JButton[] infos;

    public static void runTheWindowSimulation(Parameters param){
        Window wnd = new Window(param.width, param.height);
        JPanel panel = new JPanel();
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

        this.infoX_px = Toolkit.getDefaultToolkit().getScreenSize().width - this.width * this.cellDim - 200;
        this.infoY_cells = Math.floorDiv(50, this.cellDim) + 1;

        Border whiteline = BorderFactory.createLineBorder(Color.white);
        this.infos = new JButton[9];
        for(int i=0; i<9; i++){
            infos[i] = new JButton();
            infos[i].setFont(new Font("Consolas", Font.PLAIN, 22));
            infos[i].setPreferredSize(new Dimension(this.infoX_px,0));
            infos[i].setBorder(whiteline);
            infos[i].setBackground(c_info);
        }
    }
    private static void addComponents(Window wnd, JPanel panel){
        panel.setLayout(new GridBagLayout());
        GridBagConstraints layoutConstraints = new GridBagConstraints();

        layoutConstraints.gridx = 0;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.gridheight = 1;
        layoutConstraints.fill = GridBagConstraints.VERTICAL;
        for(int i=0; i<8; i++){
            layoutConstraints.gridy = i;
            panel.add(wnd.infos[i], layoutConstraints);
        }
        layoutConstraints.gridheight = 2 * wnd.infoY_cells;
        layoutConstraints.gridy = 8;
        panel.add(wnd.infos[8], layoutConstraints);

        JScrollPane scroller = new JScrollPane(wnd.infos[8]);
        scroller.setBorder(null);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(scroller, layoutConstraints);

        wnd.infos[0].setText("EVOLUTION SIM");
        wnd.infos[1].setText("Day no: 1");
        wnd.infos[2].setText("Number of animals:");
        wnd.infos[3].setText("Number of plants:");
        wnd.infos[4].setText("+1 DAY");
        wnd.infos[5].setText("+7 DAYS");
        wnd.infos[6].setText("+30 DAYS");
        wnd.infos[7].setText("+100 DAYS");
        defaultStatView(wnd);

        for(int i=0; i<wnd.width; i++){
            for(int j=0; j<wnd.height; j++){
                layoutConstraints.gridx = i+1;
                layoutConstraints.gridy = j;
                layoutConstraints.gridwidth = 1;
                layoutConstraints.gridheight = 1;
                panel.add(wnd.cells[i][j], layoutConstraints);
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
                        wnd.infos[8].setText(sim.getContentHere(finalI, finalJ));
                    }
                });
            }
        }
    }
    private static void addActionListeners(Window wnd, Simulation sim){
        wnd.infos[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sim.procedeNDays(1);
            }
        });
        wnd.infos[5].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sim.procedeNDays(7);
            }
        });
        wnd.infos[6].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sim.procedeNDays(30);
            }
        });
        wnd.infos[7].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sim.procedeNDays(100);
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
    public static void updateDayNo(Window wnd, int n){
        wnd.infos[1].setText("Day no: " + n);
    }
    public static void updateNumberOfAnimals(Window wnd, int n){
        wnd.infos[2].setText("Number of animals: " + n);
    }
    public static void updateNumberOfPlants(Window wnd, int n){
        wnd.infos[3].setText("Number of plants: " + n);
    }
    public static void defaultStatView(Window wnd) {
        wnd.infos[8].setText("Info bar");
    }
}
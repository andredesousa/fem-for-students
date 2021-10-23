/*
 * A classe UserInterface é a responsável por criar a janela do programa
 * Esta classe contém toda a informação relativa a todos os eventos do programa
 * É a classe principal do programa FEM for Students
 */

package app.frontend;

import app.backend.DrawingPanel;
import app.backend.Geometry;
import app.backend.LabelsNumeration;
import app.backend.LoadTable;
import app.backend.OpenSave;
import app.backend.ResultsPane;
import app.backend.ResultsTables;
import app.backend.TypesOfLoads;
import app.calculations.FiniteElement;
import app.calculations.Processor;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollBar;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author André de Sousa
 */
public class UserInterface extends javax.swing.JFrame {

    //Declaração do painel inferiol com as informações e coordenadas do rato
    private PanelBottom jP_Bottom;
    private Informations informations;

    //Declaração das variáveis para receber o estado do menu e do painel lateral
    private boolean activateLateralPanel;
    private boolean activateMenu;

    //Variável que define o tipo de problema em análise
    private String type;

    //Declaração dos panéis a adicionar ao painel lateral
    private final LateralPanelGeometry jLateralPanelGeometry;
    private final LateralPanelLoads jLateralPanelLoads;
    private final LateralPanelAnalysis jLateralPanelAnalysis;
    private final LateralPanelResults jLateralPanelResults;

    //Declaração dos paineis a adicionar ao jScrollPane1
    private final PanelWelcome jPWelcome;
    private final ReactionsSupportTable jTReactionsSupport;
    private final NodalForcesTable jTNodalForces;
    private final NodalStressesTable jTNodalStresses;

    //Declaração da numeração dos botões do painel jPanel5
    private final LabelsNumeration labelsNumeration;
    private int panelDraw_LabelSelected;
    private int panelView_LabelSelected;
    private int panelGeometry_LabelSelected;
    private int panelLoads_LabelSelected;
    private int panelAnalysis_LabelSelected;
    private int panelResults_LabelSelected;

    //Decalaração de todos os componentes associados ao jDrawingPanel
    private DrawingPanel jDrawingPanel;
    private ResultsPane resultsPane;
    private int factor = 100;
    private double scale = 1.0;
    private boolean handCursor;
    private boolean moveCursor;
    private final Point mouse = new Point();

    //Variável para armazenar os resultados cos cálculos
    private Processor results = new Processor();

    //Barras de rolagem do jScrollPane1
    private final JScrollBar scrollHorizontal;
    private final JScrollBar ScrollVertical;
    private boolean scrollMouseListener = true;

    //Variáveis associadas ao painel Geometry
    private final Geometry geometry = new Geometry();
    private ArrayList<Geometry.Section> sections = new ArrayList();
    private ArrayList<Geometry.Material> materials = new ArrayList();
    private ArrayList<Geometry.ElasticSupport> elasticSupports = new ArrayList();
    private ArrayList<Geometry.Settlement> settlements = new ArrayList();

    /**
     * Contador do número de cargas criadas de cada tipo
     *
     * Coluna 0: Concentrated Loads
     * Coluna 1: Bending Moments
     * Coluna 2: Uniformly Distributed Loads
     * Coluna 3: Distributed Axial Loads
     * Coluna 4: Uniform Planar Loads
     * Coluna 5: Thermal Loads
     * Coluna 6: Self-Weight
     */
    private int[] loadCounter = new int[7];

    //Variáveis associadas ao painel Loads
    private final TypesOfLoads loads = new TypesOfLoads();
    private ArrayList<TypesOfLoads.ConcentratedLoad> concentratedLoads = new ArrayList();
    private ArrayList<TypesOfLoads.BendingMoment> bendingMoments = new ArrayList();
    private ArrayList<TypesOfLoads.DistributedLoad> distributedLoads = new ArrayList();
    private ArrayList<TypesOfLoads.AxialLoad> axialLoads = new ArrayList();
    private ArrayList<TypesOfLoads.PlanarLoad> planarLoads = new ArrayList();
    private ArrayList<TypesOfLoads.ThermalLoad> thermalLoads = new ArrayList();
    private ArrayList<TypesOfLoads.SelfWeight> selfWeights = new ArrayList();

    /**
     * Contador do número de elementos criados no painel Geometry
     *
     * Coluna 0: Número da secção
     * Coluna 1: Número do material
     * Coluna 2: Número do apoio elástico
     * Coluna 3: Dúmero do assentamento
     */
    private int[] geometryCounter = new int[4];

    //Criação da lista de icons para as janelas
    private final ArrayList<Image> icons = new ArrayList();

    //Tabelas com propriedades dos elementos finitos e da malha
    private final IndividualProperties individualProperties = new IndividualProperties();
    private final MatrixTableResults matrixTableResults = new MatrixTableResults();
    private final VectorTableResults vectorTableResults = new VectorTableResults();

    /**
     * Creates new form UserInterface
     */
    public UserInterface() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screen.width / 2) - (900 / 2), (screen.height / 2) - (600 / 2), 0, 0);

        //Construção da lista de icons para as janelas
        Image icon16x16 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icon16x16.png"));
        Image icon32x32 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icon32x32.png"));
        Image icon64x64 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icon64x64.png"));
        icons.add(icon16x16);
        icons.add(icon32x32);
        icons.add(icon64x64);

        setIconImages(icons);
        initComponents();
        jFrameProperties.setIconImages(icons);
        jFrameLoadTable.setIconImages(icons);
        jFrameHelp.setIconImages(icons);

        activateMenuDraw();
        jDrawingPanel = new DrawingPanel();
        resultsPane = new ResultsPane();
        jPWelcome = new PanelWelcome();
        activateJPWelcome(); //<= Método para carregar painel inicial

        //Adição dos eventos MousePressed às labels do menu
        menuDrawMouseEvents();
        menuViewMouseEvents();
        menuGeometryMouseEvents();
        menuLoadsMouseEvents();
        menuAnalysisMouseEvents();
        menuResultsMouseEvents();

        labelsNumeration = new LabelsNumeration();
        activateLateralPanel = false;
        activateMenu = false;
        handCursor = false;
        moveCursor = false;

        //Declaração das tabelas a adicionar ao jScrollPane1
        jTReactionsSupport = new ReactionsSupportTable();
        jTNodalForces = new NodalForcesTable();
        jTNodalStresses = new NodalStressesTable();

        //Declaração do paineis laterais a adicionar ao jLateralPanel
        jLateralPanelGeometry = new LateralPanelGeometry();
        jLateralPanelLoads = new LateralPanelLoads();
        jLateralPanelAnalysis = new LateralPanelAnalysis();
        jLateralPanelResults = new LateralPanelResults();

        initializeGeometry();
        initializeLoads();
        initializeAnalysis();
        initializeResults();
        initializePopupMenus();

        scrollHorizontal = jScrollPane1.getHorizontalScrollBar();
        scrollHorizontal.addAdjustmentListener(this::adjustmentValueChanged);
        scrollHorizontal.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    scrollMousePressed(evt);
                }

                @Override
                public void mouseReleased(MouseEvent evt) {
                    scrollMouseReleased(evt);
                }
            }
        );

        ScrollVertical = jScrollPane1.getVerticalScrollBar();
        ScrollVertical.addAdjustmentListener(this::adjustmentValueChanged);
        ScrollVertical.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    scrollMousePressed(evt);
                }

                @Override
                public void mouseReleased(MouseEvent evt) {
                    scrollMouseReleased(evt);
                }
            }
        );

        if (!jRadioButtonGrid.isSelected()) {
            jRadioButtonGrid.setSelected(true);
            jRadioButtonGridActionPerformed(null);
        }
        if (!jRadioButtonLegends.isSelected()) {
            jRadioButtonLegends.setSelected(true);
            jRadioButtonLegendsActionPerformed(null);
        }

        addHelpJPBottom();
        jLFile.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLEdit.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLHelp.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 11));
        jPanel21.setBorder(
            javax.swing.BorderFactory.createTitledBorder(
                null,
                "Description",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Tahoma", 0, 11)
            )
        );
        jPanel22.setBorder(
            javax.swing.BorderFactory.createTitledBorder(
                null,
                "Description",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Tahoma", 0, 11)
            )
        );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jFrameProperties = new javax.swing.JFrame();
        jP_FrameProperties = new javax.swing.JPanel();
        jSeparatorProperties = new javax.swing.JSeparator();
        jButtonClose_Properties = new javax.swing.JButton();
        jSP_FrameProperties = new javax.swing.JScrollPane();
        jTable_FrameProperties = new javax.swing.JTable();
        jFrameLoadTable = new javax.swing.JFrame();
        jSPLoadTable = new javax.swing.JScrollPane();
        jLoadTable = new javax.swing.JTable();
        jSeparatorLoad = new javax.swing.JSeparator();
        jButtonClose_Load = new javax.swing.JButton();
        jFrameHelp = new javax.swing.JFrame();
        jPanel20 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jScrollPaneHelp = new javax.swing.JScrollPane();
        jDialogPoint = new javax.swing.JDialog();
        jPanel12 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jTInputX = new javax.swing.JTextField();
        jLabelDP_X = new javax.swing.JLabel();
        jLabelDP_Y = new javax.swing.JLabel();
        jTInputY = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jBInsertPointCancel = new javax.swing.JButton();
        jBInsertPointInsert = new javax.swing.JButton();
        jDialogAbout = new javax.swing.JDialog();
        jPanel11 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jButtonCloseAbout = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jDialogFiniteElement = new javax.swing.JDialog();
        jPanel25 = new javax.swing.JPanel();
        jButtonApply = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JSeparator();
        jPanel26 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabelNodesOfElement = new javax.swing.JLabel();
        jLabelSectionOfElement = new javax.swing.JLabel();
        jLabelMaterialOfElement = new javax.swing.JLabel();
        jComboBoxNodes = new javax.swing.JComboBox();
        jComboBoxSection = new javax.swing.JComboBox();
        jComboBoxMaterial = new javax.swing.JComboBox();
        jPanel21 = new javax.swing.JPanel();
        jLabelShape = new javax.swing.JLabel();
        jLabelLength = new javax.swing.JLabel();
        jLabelDimensions = new javax.swing.JLabel();
        jPanelDraw = new javax.swing.JPanel();
        jLabelPoint = new LabelMouseEvents();
        jLabelLine = new LabelMouseEvents();
        jLabelTriangle = new LabelMouseEvents();
        jLabelRectangle = new LabelMouseEvents();
        jLabelQuadrilateral = new LabelMouseEvents();
        jLabelUndo = new LabelMouseEvents();
        jLabelRedo = new LabelMouseEvents();
        jLabelMove = new LabelMouseEvents();
        jLabelSelect = new LabelMouseEvents();
        jLabelCut = new LabelMouseEvents();
        jLabelCopy = new LabelMouseEvents();
        jLabelPaste = new LabelMouseEvents();
        jPanelView = new javax.swing.JPanel();
        jLabelPan = new LabelMouseEvents();
        jLabelZoomIn = new LabelMouseEvents();
        jLabelZoomOut = new LabelMouseEvents();
        jRadioButtonGrid = new javax.swing.JRadioButton();
        jRadioButtonSnap = new javax.swing.JRadioButton();
        jRadioButtonLegends = new javax.swing.JRadioButton();
        jPanelGeometry = new javax.swing.JPanel();
        jLabelNodes = new LabelMouseEvents();
        jLabelNumberOfNodes = new LabelMouseEvents();
        jLabelSections = new LabelMouseEvents();
        jLabelMaterials = new LabelMouseEvents();
        jLabelMesh = new LabelMouseEvents();
        jLabelDimensionLine = new LabelMouseEvents();
        jLabelVSimpleSupport = new LabelMouseEvents();
        jLabelHSimpleSupport = new LabelMouseEvents();
        jLabelPinnedSupport = new LabelMouseEvents();
        jLabelHSliderSupport = new LabelMouseEvents();
        jLabelFixedSupport = new LabelMouseEvents();
        jLabelVSliderSupport = new LabelMouseEvents();
        jLabelElasticSupport = new LabelMouseEvents();
        jLabelSettlements = new LabelMouseEvents();
        jLabelCutSupports = new LabelMouseEvents();
        jPanelLoads = new javax.swing.JPanel();
        jLabelConcentratedLoad = new LabelMouseEvents();
        jLabelBendingMoment = new LabelMouseEvents();
        jLabelUnifDistLoad = new LabelMouseEvents();
        jLabelDistAxialLoad = new LabelMouseEvents();
        jLabelUnifPlanarLoad = new LabelMouseEvents();
        jLabelThermalLoad = new LabelMouseEvents();
        jLabelSelfWeight = new LabelMouseEvents();
        jLabelCutLoad = new LabelMouseEvents();
        jLabelLoadTable = new LabelMouseEvents();
        jPanelAnalysis = new javax.swing.JPanel();
        jLabelTheory = new LabelMouseEvents();
        jRadioButtonAnalytical = new javax.swing.JRadioButton();
        jRadioButtonNumerical = new javax.swing.JRadioButton();
        jLabelCalculate = new LabelMouseEvents();
        jPanelResults = new javax.swing.JPanel();
        jLabelVectorF = new LabelMouseEvents();
        jLabelMatrixK = new LabelMouseEvents();
        jLabelVectorDisplacements = new LabelMouseEvents();
        jLabelProperties = new LabelMouseEvents();
        jLabelSupportReactions = new LabelMouseEvents();
        jLabelNodalForces = new LabelMouseEvents();
        jLabelNodalStresses = new LabelMouseEvents();
        jLabelDisplacements = new LabelMouseEvents();
        jLabelIsovalues = new LabelMouseEvents();
        jLabelMaps = new LabelMouseEvents();
        jLabelPrincipalStresses = new LabelMouseEvents();
        jLabelDiagrams = new LabelMouseEvents();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new JPanelMenuTheme();
        menuDraw = new MenuMouseEvents();
        menuView = new MenuMouseEvents();
        menuLoads = new MenuMouseEvents();
        menuGeometry = new MenuMouseEvents();
        menuAnalysis = new MenuMouseEvents();
        menuResults = new MenuMouseEvents();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLHelp = new javax.swing.JLabel();
        jLEdit = new javax.swing.JLabel();
        jLFile = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLateralPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();

        jFrameProperties.setTitle("Stiffness Matrix [Finite Element 1]");
        jFrameProperties.setBounds(new java.awt.Rectangle(50, 50, 0, 0));
        jFrameProperties.setMinimumSize(new java.awt.Dimension(750, 500));

        jP_FrameProperties.setMinimumSize(new java.awt.Dimension(525, 375));
        jP_FrameProperties.setPreferredSize(new java.awt.Dimension(525, 350));

        jSeparatorProperties.setForeground(new java.awt.Color(204, 204, 204));
        jSeparatorProperties.setPreferredSize(new java.awt.Dimension(0, 1));

        jButtonClose_Properties.setText("Close");
        jButtonClose_Properties.setFocusable(false);
        jButtonClose_Properties.addActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButtonClose_PropertiesActionPerformed(evt);
                }
            }
        );

        jSP_FrameProperties.setBorder(null);

        jTable_FrameProperties.setModel(
            new javax.swing.table.DefaultTableModel(
                new Object[][] {
                    {
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                    },
                },
                new String[] {
                    "",
                    "1",
                    "2",
                    "3",
                    "4",
                    "5",
                    "6",
                    "7",
                    "8",
                    "9",
                    "10",
                    "11",
                    "12",
                    "13",
                    "14",
                    "15",
                    "16",
                    "17",
                    "18",
                    "19",
                    "20",
                }
            ) {
                Class[] types = new Class[] {
                    java.lang.String.class,
                    java.lang.String.class,
                    java.lang.String.class,
                    java.lang.String.class,
                    java.lang.String.class,
                    java.lang.String.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                };
                boolean[] canEdit = new boolean[] {
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                };

                public Class getColumnClass(int columnIndex) {
                    return types[columnIndex];
                }

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            }
        );
        jTable_FrameProperties.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable_FrameProperties.setGridColor(new java.awt.Color(222, 222, 222));
        jTable_FrameProperties.setRowHeight(20);
        jTable_FrameProperties.getTableHeader().setReorderingAllowed(false);
        jSP_FrameProperties.setViewportView(jTable_FrameProperties);
        if (jTable_FrameProperties.getColumnModel().getColumnCount() > 0) {
            jTable_FrameProperties.getColumnModel().getColumn(0).setMinWidth(100);
            jTable_FrameProperties.getColumnModel().getColumn(0).setMaxWidth(200);
            jTable_FrameProperties.getColumnModel().getColumn(1).setMinWidth(100);
            jTable_FrameProperties.getColumnModel().getColumn(1).setMaxWidth(200);
            jTable_FrameProperties.getColumnModel().getColumn(2).setMinWidth(100);
            jTable_FrameProperties.getColumnModel().getColumn(2).setMaxWidth(200);
            jTable_FrameProperties.getColumnModel().getColumn(3).setMinWidth(100);
            jTable_FrameProperties.getColumnModel().getColumn(3).setMaxWidth(200);
            jTable_FrameProperties.getColumnModel().getColumn(4).setMinWidth(100);
            jTable_FrameProperties.getColumnModel().getColumn(4).setMaxWidth(200);
            jTable_FrameProperties.getColumnModel().getColumn(5).setMinWidth(100);
            jTable_FrameProperties.getColumnModel().getColumn(5).setMaxWidth(200);
            jTable_FrameProperties.getColumnModel().getColumn(6).setMinWidth(100);
            jTable_FrameProperties.getColumnModel().getColumn(6).setMaxWidth(200);
            jTable_FrameProperties.getColumnModel().getColumn(7).setMinWidth(100);
            jTable_FrameProperties.getColumnModel().getColumn(7).setMaxWidth(200);
            jTable_FrameProperties.getColumnModel().getColumn(8).setMinWidth(100);
            jTable_FrameProperties.getColumnModel().getColumn(8).setMaxWidth(200);
            jTable_FrameProperties.getColumnModel().getColumn(9).setMinWidth(100);
            jTable_FrameProperties.getColumnModel().getColumn(9).setMaxWidth(200);
            jTable_FrameProperties.getColumnModel().getColumn(10).setMinWidth(100);
            jTable_FrameProperties.getColumnModel().getColumn(10).setMaxWidth(200);
            jTable_FrameProperties.getColumnModel().getColumn(11).setMinWidth(100);
            jTable_FrameProperties.getColumnModel().getColumn(11).setMaxWidth(200);
            jTable_FrameProperties.getColumnModel().getColumn(12).setMinWidth(100);
            jTable_FrameProperties.getColumnModel().getColumn(12).setMaxWidth(200);
            jTable_FrameProperties.getColumnModel().getColumn(13).setMinWidth(100);
            jTable_FrameProperties.getColumnModel().getColumn(13).setMaxWidth(200);
            jTable_FrameProperties.getColumnModel().getColumn(14).setMinWidth(100);
            jTable_FrameProperties.getColumnModel().getColumn(14).setMaxWidth(200);
            jTable_FrameProperties.getColumnModel().getColumn(15).setMinWidth(100);
            jTable_FrameProperties.getColumnModel().getColumn(15).setMaxWidth(200);
            jTable_FrameProperties.getColumnModel().getColumn(16).setMinWidth(100);
            jTable_FrameProperties.getColumnModel().getColumn(16).setMaxWidth(200);
            jTable_FrameProperties.getColumnModel().getColumn(17).setMinWidth(100);
            jTable_FrameProperties.getColumnModel().getColumn(17).setMaxWidth(200);
            jTable_FrameProperties.getColumnModel().getColumn(18).setMinWidth(100);
            jTable_FrameProperties.getColumnModel().getColumn(18).setMaxWidth(200);
            jTable_FrameProperties.getColumnModel().getColumn(19).setMinWidth(100);
            jTable_FrameProperties.getColumnModel().getColumn(19).setMaxWidth(200);
            jTable_FrameProperties.getColumnModel().getColumn(20).setMinWidth(100);
            jTable_FrameProperties.getColumnModel().getColumn(20).setMaxWidth(200);
        }

        javax.swing.GroupLayout jP_FramePropertiesLayout = new javax.swing.GroupLayout(jP_FrameProperties);
        jP_FrameProperties.setLayout(jP_FramePropertiesLayout);
        jP_FramePropertiesLayout.setHorizontalGroup(
            jP_FramePropertiesLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSP_FrameProperties, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
                .addComponent(
                    jSeparatorProperties,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE
                )
                .addGroup(
                    javax.swing.GroupLayout.Alignment.TRAILING,
                    jP_FramePropertiesLayout
                        .createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonClose_Properties)
                        .addContainerGap()
                )
        );
        jP_FramePropertiesLayout.setVerticalGroup(
            jP_FramePropertiesLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jP_FramePropertiesLayout
                        .createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addComponent(jSP_FrameProperties, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(
                            jSeparatorProperties,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            1,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonClose_Properties)
                        .addContainerGap()
                )
        );

        javax.swing.GroupLayout jFramePropertiesLayout = new javax.swing.GroupLayout(jFrameProperties.getContentPane());
        jFrameProperties.getContentPane().setLayout(jFramePropertiesLayout);
        jFramePropertiesLayout.setHorizontalGroup(
            jFramePropertiesLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(
                    jP_FrameProperties,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE
                )
        );
        jFramePropertiesLayout.setVerticalGroup(
            jFramePropertiesLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jP_FrameProperties, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
        );

        jFrameLoadTable.setTitle("Load Table");
        jFrameLoadTable.setBounds(new java.awt.Rectangle(50, 50, 0, 0));
        jFrameLoadTable.setMinimumSize(new java.awt.Dimension(750, 500));

        jSPLoadTable.setBorder(null);

        jLoadTable.setModel(
            new javax.swing.table.DefaultTableModel(
                new Object[][] { { null, null, null, null, null, null } },
                new String[] { "ID", "Name", "Element", "Value", "Unit", "Description" }
            ) {
                Class[] types = new Class[] {
                    java.lang.String.class,
                    java.lang.String.class,
                    java.lang.String.class,
                    java.lang.String.class,
                    java.lang.String.class,
                    java.lang.String.class,
                };
                boolean[] canEdit = new boolean[] { false, false, false, false, false, false };

                public Class getColumnClass(int columnIndex) {
                    return types[columnIndex];
                }

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            }
        );
        jLoadTable.setGridColor(new java.awt.Color(222, 222, 222));
        jLoadTable.setRowHeight(20);
        jLoadTable.getTableHeader().setReorderingAllowed(false);
        jSPLoadTable.setViewportView(jLoadTable);
        if (jLoadTable.getColumnModel().getColumnCount() > 0) {
            jLoadTable.getColumnModel().getColumn(0).setMinWidth(100);
            jLoadTable.getColumnModel().getColumn(0).setMaxWidth(200);
            jLoadTable.getColumnModel().getColumn(1).setMinWidth(100);
            jLoadTable.getColumnModel().getColumn(1).setMaxWidth(200);
            jLoadTable.getColumnModel().getColumn(2).setMinWidth(100);
            jLoadTable.getColumnModel().getColumn(2).setMaxWidth(200);
            jLoadTable.getColumnModel().getColumn(3).setMinWidth(100);
            jLoadTable.getColumnModel().getColumn(3).setMaxWidth(200);
            jLoadTable.getColumnModel().getColumn(4).setMinWidth(100);
            jLoadTable.getColumnModel().getColumn(4).setMaxWidth(200);
        }

        jSeparatorLoad.setForeground(new java.awt.Color(204, 204, 204));
        jSeparatorLoad.setPreferredSize(new java.awt.Dimension(0, 1));

        jButtonClose_Load.setText("Close");
        jButtonClose_Load.setFocusable(false);
        jButtonClose_Load.addActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButtonClose_LoadActionPerformed(evt);
                }
            }
        );

        javax.swing.GroupLayout jFrameLoadTableLayout = new javax.swing.GroupLayout(jFrameLoadTable.getContentPane());
        jFrameLoadTable.getContentPane().setLayout(jFrameLoadTableLayout);
        jFrameLoadTableLayout.setHorizontalGroup(
            jFrameLoadTableLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSPLoadTable, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
                .addComponent(jSeparatorLoad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(
                    javax.swing.GroupLayout.Alignment.TRAILING,
                    jFrameLoadTableLayout
                        .createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonClose_Load)
                        .addContainerGap()
                )
        );
        jFrameLoadTableLayout.setVerticalGroup(
            jFrameLoadTableLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jFrameLoadTableLayout
                        .createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addComponent(jSPLoadTable, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparatorLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonClose_Load)
                        .addContainerGap()
                )
        );

        jFrameHelp.setTitle("Help");

        jPanel16.setBackground(new java.awt.Color(52, 52, 52));
        jPanel16.setPreferredSize(new java.awt.Dimension(400, 65));

        jPanel17.setBackground(new java.awt.Color(52, 52, 52));
        jPanel17.setPreferredSize(new java.awt.Dimension(288, 40));

        jLabel34.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("FEM for Students");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel17Layout.createSequentialGroup().addGap(0, 0, 0).addComponent(jLabel34).addContainerGap(318, Short.MAX_VALUE)
                )
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel17Layout
                        .createSequentialGroup()
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)
                )
        );

        jPanel18.setBackground(new java.awt.Color(52, 52, 52));
        jPanel18.setPreferredSize(new java.awt.Dimension(60, 40));

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/iconFEM.png"))); // NOI18N

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel18Layout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap()
                )
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("HELP");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel16Layout
                        .createSequentialGroup()
                        .addComponent(
                            jPanel18,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                )
                .addComponent(
                    jLabel36,
                    javax.swing.GroupLayout.Alignment.TRAILING,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    110,
                    javax.swing.GroupLayout.PREFERRED_SIZE
                )
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel16Layout
                        .createSequentialGroup()
                        .addGroup(
                            jPanel16Layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(
                                    jPanel18,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jPanel17,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addGap(0, 0, 0)
                        .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap()
                )
        );

        jScrollPaneHelp.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPaneHelp.setBorder(null);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                .addComponent(jScrollPaneHelp)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel20Layout
                        .createSequentialGroup()
                        .addComponent(
                            jPanel16,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(jScrollPaneHelp, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                )
        );

        javax.swing.GroupLayout jFrameHelpLayout = new javax.swing.GroupLayout(jFrameHelp.getContentPane());
        jFrameHelp.getContentPane().setLayout(jFrameHelpLayout);
        jFrameHelpLayout.setHorizontalGroup(
            jFrameHelpLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jFrameHelpLayout.setVerticalGroup(
            jFrameHelpLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jDialogPoint.setTitle("Insert Point");
        jDialogPoint.setMinimumSize(new java.awt.Dimension(350, 140));
        jDialogPoint.setModal(true);
        jDialogPoint.setResizable(false);

        jPanel12.setPreferredSize(new java.awt.Dimension(350, 140));

        jLabel20.setText("Point Coordinates:");
        jLabel20.setPreferredSize(new java.awt.Dimension(34, 25));

        jTInputX.setPreferredSize(new java.awt.Dimension(70, 20));

        jLabelDP_X.setText("X =");
        jLabelDP_X.setPreferredSize(new java.awt.Dimension(25, 25));

        jLabelDP_Y.setText("Y =");
        jLabelDP_Y.setPreferredSize(new java.awt.Dimension(25, 25));

        jTInputY.setPreferredSize(new java.awt.Dimension(70, 20));

        jLabel23.setText("m");
        jLabel23.setPreferredSize(new java.awt.Dimension(40, 25));

        jLabel24.setText("m");
        jLabel24.setPreferredSize(new java.awt.Dimension(40, 25));

        jBInsertPointCancel.setText("Cancel");
        jBInsertPointCancel.setFocusable(false);
        jBInsertPointCancel.addActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jBInsertPointCancelActionPerformed(evt);
                }
            }
        );

        jBInsertPointInsert.setText("Insert ");
        jBInsertPointInsert.setFocusable(false);
        jBInsertPointInsert.addActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jBInsertPointInsertActionPerformed(evt);
                }
            }
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel12Layout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jPanel12Layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(
                                    jPanel12Layout
                                        .createSequentialGroup()
                                        .addGroup(
                                            jPanel12Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(
                                                    jLabelDP_X,
                                                    javax.swing.GroupLayout.Alignment.LEADING,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    Short.MAX_VALUE
                                                )
                                                .addComponent(
                                                    jLabelDP_Y,
                                                    javax.swing.GroupLayout.Alignment.LEADING,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    Short.MAX_VALUE
                                                )
                                        )
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(
                                            jPanel12Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(
                                                    jPanel12Layout
                                                        .createSequentialGroup()
                                                        .addComponent(
                                                            jTInputY,
                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                                        )
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(
                                                            jLabel23,
                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                                        )
                                                        .addPreferredGap(
                                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                            116,
                                                            Short.MAX_VALUE
                                                        )
                                                        .addComponent(jBInsertPointInsert)
                                                )
                                                .addGroup(
                                                    jPanel12Layout
                                                        .createSequentialGroup()
                                                        .addComponent(
                                                            jTInputX,
                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                                        )
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(
                                                            jLabel24,
                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                                        )
                                                        .addPreferredGap(
                                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                            Short.MAX_VALUE
                                                        )
                                                        .addComponent(jBInsertPointCancel)
                                                )
                                        )
                                )
                        )
                        .addContainerGap()
                )
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel12Layout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jPanel12Layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(
                                    jPanel12Layout
                                        .createSequentialGroup()
                                        .addGap(31, 31, 31)
                                        .addGroup(
                                            jPanel12Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(
                                                    jTInputX,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                                )
                                                .addComponent(
                                                    jLabel24,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                                )
                                                .addComponent(jBInsertPointCancel)
                                        )
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(
                                            jPanel12Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(
                                                    jLabelDP_Y,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                                )
                                                .addGroup(
                                                    jPanel12Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(
                                                            jTInputY,
                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                                        )
                                                        .addComponent(
                                                            jLabel23,
                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                                        )
                                                        .addComponent(jBInsertPointInsert)
                                                )
                                        )
                                )
                                .addGroup(
                                    jPanel12Layout
                                        .createSequentialGroup()
                                        .addComponent(
                                            jLabel20,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(
                                            jLabelDP_X,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                        )
                        .addContainerGap(42, Short.MAX_VALUE)
                )
        );

        javax.swing.GroupLayout jDialogPointLayout = new javax.swing.GroupLayout(jDialogPoint.getContentPane());
        jDialogPoint.getContentPane().setLayout(jDialogPointLayout);
        jDialogPointLayout.setHorizontalGroup(
            jDialogPointLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialogPointLayout.setVerticalGroup(
            jDialogPointLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jDialogAbout.setTitle("About");
        jDialogAbout.setIconImage(null);
        jDialogAbout.setMinimumSize(new java.awt.Dimension(405, 500));
        jDialogAbout.setModal(true);
        jDialogAbout.setResizable(false);

        jPanel11.setBackground(new java.awt.Color(52, 52, 52));
        jPanel11.setPreferredSize(new java.awt.Dimension(400, 65));

        jPanel13.setBackground(new java.awt.Color(52, 52, 52));
        jPanel13.setPreferredSize(new java.awt.Dimension(288, 40));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("FEM for Students");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel13Layout.createSequentialGroup().addGap(0, 0, 0).addComponent(jLabel3).addContainerGap(220, Short.MAX_VALUE)
                )
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel13Layout
                        .createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)
                )
        );

        jPanel14.setBackground(new java.awt.Color(52, 52, 52));
        jPanel14.setPreferredSize(new java.awt.Dimension(60, 40));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/iconFEM.png"))); // NOI18N

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel14Layout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap()
                )
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Version 1.3");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel11Layout
                        .createSequentialGroup()
                        .addComponent(
                            jPanel14,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGroup(
                    javax.swing.GroupLayout.Alignment.TRAILING,
                    jPanel11Layout
                        .createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap()
                )
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel11Layout
                        .createSequentialGroup()
                        .addGroup(
                            jPanel11Layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(
                                    jPanel14,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jPanel13,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addGap(0, 0, 0)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap()
                )
        );

        jPanel15.setBackground(new java.awt.Color(236, 236, 236));
        jPanel15.setPreferredSize(new java.awt.Dimension(400, 435));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText(
            "<html><p align='justify'>FEM for Students is a freeware program of modeling and linear elastic structural analysis by Finite Element Method. This program was developed by André de Sousa under Dissertation submitted for partial fulfillment of the requirements of the degree of Master of Civil Engineering in Structures Specialization.</p></html>"
        );
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("January 2015");

        jLabel26.setText(
            "<html><p align='justify'>The use of this program implies that user agree to the following terms of use:</p><p></p><p align='justify'>(i) The author and the Faculdade de Engenharia da Universidade do Porto are not responsible for the use or misuse of the program and its outcomes;</p><p align='justify'>(ii) Author and the Faculdade de Engenharia da Universidade do Porto have no legal duty or responsibility to any person or organization for direct or indirect damages resulting from the use of information provided by the program;</p><p align='justify'>(iii) The user is responsible for any and all conclusions made using the program;</p><p align='justify'>(iv) There is no commitment of proper working or any guarantee.</p></html>"
        );
        jLabel26.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jButtonCloseAbout.setText("Close");
        jButtonCloseAbout.setFocusable(false);
        jButtonCloseAbout.addActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButtonCloseAboutActionPerformed(evt);
                }
            }
        );

        jSeparator1.setPreferredSize(new java.awt.Dimension(50, 5));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel15Layout
                        .createSequentialGroup()
                        .addContainerGap(11, Short.MAX_VALUE)
                        .addGroup(
                            jPanel15Layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                                .addComponent(
                                    jLabel18,
                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE
                                )
                                .addComponent(
                                    jLabel26,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE
                                )
                                .addGroup(
                                    jPanel15Layout
                                        .createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jButtonCloseAbout)
                                        .addGap(0, 0, Short.MAX_VALUE)
                                )
                        )
                        .addContainerGap(11, Short.MAX_VALUE)
                )
                .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel15Layout
                        .createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel5)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 242, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonCloseAbout)
                        .addContainerGap()
                )
        );

        javax.swing.GroupLayout jDialogAboutLayout = new javax.swing.GroupLayout(jDialogAbout.getContentPane());
        jDialogAbout.getContentPane().setLayout(jDialogAboutLayout);
        jDialogAboutLayout.setHorizontalGroup(
            jDialogAboutLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
        );
        jDialogAboutLayout.setVerticalGroup(
            jDialogAboutLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jDialogAboutLayout
                        .createSequentialGroup()
                        .addComponent(
                            jPanel11,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
        );

        jDialogFiniteElement.setTitle("Finite Element Properties");
        jDialogFiniteElement.setIconImage(null);
        jDialogFiniteElement.setMinimumSize(new java.awt.Dimension(400, 360));
        jDialogFiniteElement.setModal(true);
        jDialogFiniteElement.setResizable(false);

        jButtonApply.setText("Apply");
        jButtonApply.setFocusable(false);
        jButtonApply.addActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButtonApplyActionPerformed(evt);
                }
            }
        );

        jButtonCancel.setText("Cancel");
        jButtonCancel.setFocusable(false);
        jButtonCancel.addActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButtonCancelActionPerformed(evt);
                }
            }
        );

        jSeparator5.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator5.setForeground(new java.awt.Color(222, 222, 222));

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSeparator5)
                .addGroup(
                    javax.swing.GroupLayout.Alignment.TRAILING,
                    jPanel25Layout
                        .createSequentialGroup()
                        .addGap(0, 260, Short.MAX_VALUE)
                        .addComponent(jButtonApply)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonCancel)
                        .addContainerGap()
                )
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel25Layout
                        .createSequentialGroup()
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(
                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            Short.MAX_VALUE
                        )
                        .addGroup(
                            jPanel25Layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButtonCancel, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jButtonApply, javax.swing.GroupLayout.Alignment.TRAILING)
                        )
                        .addContainerGap()
                )
        );

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder("Geometry"));

        jLabelNodesOfElement.setText("Nodes");

        jLabelSectionOfElement.setText("Section");

        jLabelMaterialOfElement.setText("Material");

        jComboBoxNodes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<Default>" }));
        jComboBoxNodes.setEnabled(false);
        jComboBoxNodes.setFocusable(false);
        jComboBoxNodes.setPreferredSize(new java.awt.Dimension(80, 20));

        jComboBoxSection.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<Default>" }));
        jComboBoxSection.setFocusable(false);
        jComboBoxSection.setPreferredSize(new java.awt.Dimension(80, 20));

        jComboBoxMaterial.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Concrete", "Steel", "Other" }));
        jComboBoxMaterial.setEnabled(false);
        jComboBoxMaterial.setFocusable(false);
        jComboBoxMaterial.setPreferredSize(new java.awt.Dimension(80, 20));

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel22Layout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jPanel22Layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(
                                    jLabelSectionOfElement,
                                    javax.swing.GroupLayout.Alignment.LEADING,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE
                                )
                                .addComponent(
                                    jLabelMaterialOfElement,
                                    javax.swing.GroupLayout.Alignment.LEADING,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    60,
                                    Short.MAX_VALUE
                                )
                                .addComponent(
                                    jLabelNodesOfElement,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jPanel22Layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(
                                    jComboBoxSection,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jComboBoxNodes,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jComboBoxMaterial,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel22Layout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jPanel22Layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelNodesOfElement)
                                .addComponent(
                                    jComboBoxNodes,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(
                            jPanel22Layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelSectionOfElement)
                                .addComponent(
                                    jComboBoxSection,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(
                            jPanel22Layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelMaterialOfElement)
                                .addComponent(
                                    jComboBoxMaterial,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
        );

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder("Description"));

        jLabelShape.setText("Shape:");

        jLabelLength.setText("Length:");

        jLabelDimensions.setText("Dimensions:");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel21Layout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jPanel21Layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabelShape)
                                .addComponent(jLabelLength)
                                .addComponent(jLabelDimensions)
                        )
                        .addContainerGap(301, Short.MAX_VALUE)
                )
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel21Layout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelShape)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelLength)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelDimensions)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
        );

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel26Layout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jPanel26Layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(
                                    jPanel21,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE
                                )
                                .addComponent(
                                    jPanel22,
                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE
                                )
                        )
                        .addContainerGap()
                )
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel26Layout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(
                            jPanel21,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(
                            jPanel22,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addContainerGap(32, Short.MAX_VALUE)
                )
        );

        javax.swing.GroupLayout jDialogFiniteElementLayout = new javax.swing.GroupLayout(jDialogFiniteElement.getContentPane());
        jDialogFiniteElement.getContentPane().setLayout(jDialogFiniteElementLayout);
        jDialogFiniteElementLayout.setHorizontalGroup(
            jDialogFiniteElementLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialogFiniteElementLayout.setVerticalGroup(
            jDialogFiniteElementLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jDialogFiniteElementLayout
                        .createSequentialGroup()
                        .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanel25,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                )
        );

        jPanelDraw.setBackground(new java.awt.Color(236, 236, 236));
        jPanelDraw.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanelDraw.setPreferredSize(new java.awt.Dimension(675, 35));

        jLabelPoint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/draw_point.png"))); // NOI18N
        jLabelPoint.setToolTipText("Point (P)");

        jLabelLine.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/draw_line.png"))); // NOI18N
        jLabelLine.setToolTipText("Line (L)");

        jLabelTriangle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/draw_triangle.png"))); // NOI18N
        jLabelTriangle.setToolTipText("Triangle (T)");

        jLabelRectangle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/draw_rectangle.png"))); // NOI18N
        jLabelRectangle.setToolTipText("Rectangle (R)");

        jLabelQuadrilateral.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/draw_quadrilateral.png"))); // NOI18N
        jLabelQuadrilateral.setToolTipText("Quadrilateral (Q)");

        jLabelUndo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/draw_undo.png"))); // NOI18N
        jLabelUndo.setToolTipText("Undo (Ctrl+Z)");
        jLabelUndo.setName(""); // NOI18N

        jLabelRedo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/draw_redo.png"))); // NOI18N
        jLabelRedo.setToolTipText("Redo (Ctrl+Y)");

        jLabelMove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/draw_move.png"))); // NOI18N
        jLabelMove.setToolTipText("Move (M)");

        jLabelSelect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/draw_select.png"))); // NOI18N
        jLabelSelect.setToolTipText("Select (S)");

        jLabelCut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/draw_cut.png"))); // NOI18N
        jLabelCut.setToolTipText("Cut (Ctrl+X)");

        jLabelCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/draw_copy.png"))); // NOI18N
        jLabelCopy.setToolTipText("Copy (Ctrl+C)");

        jLabelPaste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/draw_paste.png"))); // NOI18N
        jLabelPaste.setToolTipText("Paste (Ctrl+V)");

        javax.swing.GroupLayout jPanelDrawLayout = new javax.swing.GroupLayout(jPanelDraw);
        jPanelDraw.setLayout(jPanelDrawLayout);
        jPanelDrawLayout.setHorizontalGroup(
            jPanelDrawLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanelDrawLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelPoint)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelLine)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelTriangle)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelRectangle)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelQuadrilateral)
                        .addGap(30, 30, 30)
                        .addComponent(jLabelUndo)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelRedo)
                        .addGap(30, 30, 30)
                        .addComponent(jLabelMove)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelSelect)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelCut)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelCopy)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelPaste)
                        .addContainerGap(305, Short.MAX_VALUE)
                )
        );
        jPanelDrawLayout.setVerticalGroup(
            jPanelDrawLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanelDrawLayout
                        .createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(
                            jPanelDrawLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabelMove)
                                .addComponent(jLabelSelect)
                                .addGroup(
                                    jPanelDrawLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(
                                            javax.swing.GroupLayout.Alignment.TRAILING,
                                            jPanelDrawLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(
                                                    jPanelDrawLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabelTriangle)
                                                        .addComponent(jLabelRectangle)
                                                        .addComponent(jLabelQuadrilateral)
                                                )
                                                .addGroup(
                                                    jPanelDrawLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabelLine)
                                                        .addComponent(jLabelPoint)
                                                )
                                        )
                                        .addComponent(jLabelRedo)
                                        .addComponent(jLabelUndo)
                                )
                                .addGroup(
                                    jPanelDrawLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabelCut)
                                        .addComponent(jLabelCopy)
                                        .addComponent(jLabelPaste)
                                )
                        )
                        .addGap(6, 6, 6)
                )
        );

        jPanelView.setBackground(new java.awt.Color(236, 236, 236));
        jPanelView.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanelView.setPreferredSize(new java.awt.Dimension(675, 35));

        jLabelPan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view_pan.png"))); // NOI18N
        jLabelPan.setToolTipText("Pan");

        jLabelZoomIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view_zoom_in.png"))); // NOI18N
        jLabelZoomIn.setToolTipText("Zoom In");

        jLabelZoomOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view_zoom_out.png"))); // NOI18N
        jLabelZoomOut.setToolTipText("Zoom Out");

        jRadioButtonGrid.setText("Grid");
        jRadioButtonGrid.setToolTipText("Grid");
        jRadioButtonGrid.setFocusable(false);
        jRadioButtonGrid.setOpaque(false);

        jRadioButtonSnap.setText("Snap");
        jRadioButtonSnap.setToolTipText("Snap");
        jRadioButtonSnap.setEnabled(false);
        jRadioButtonSnap.setFocusable(false);
        jRadioButtonSnap.setOpaque(false);

        jRadioButtonLegends.setText("Legends");
        jRadioButtonLegends.setToolTipText("Legends");
        jRadioButtonLegends.setFocusable(false);
        jRadioButtonLegends.setOpaque(false);

        javax.swing.GroupLayout jPanelViewLayout = new javax.swing.GroupLayout(jPanelView);
        jPanelView.setLayout(jPanelViewLayout);
        jPanelViewLayout.setHorizontalGroup(
            jPanelViewLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanelViewLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelPan)
                        .addGap(30, 30, 30)
                        .addComponent(jLabelZoomIn)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelZoomOut)
                        .addGap(30, 30, 30)
                        .addComponent(jRadioButtonGrid)
                        .addGap(30, 30, 30)
                        .addComponent(jRadioButtonSnap)
                        .addGap(30, 30, 30)
                        .addComponent(jRadioButtonLegends)
                        .addContainerGap(311, Short.MAX_VALUE)
                )
        );
        jPanelViewLayout.setVerticalGroup(
            jPanelViewLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    javax.swing.GroupLayout.Alignment.TRAILING,
                    jPanelViewLayout
                        .createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(
                            jPanelViewLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(
                                    jPanelViewLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(
                                            jRadioButtonGrid,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            25,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                        .addComponent(
                                            jRadioButtonSnap,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            25,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                        .addComponent(
                                            jRadioButtonLegends,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            25,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                                .addGroup(
                                    jPanelViewLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabelPan)
                                        .addGroup(
                                            jPanelViewLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabelZoomOut)
                                                .addComponent(jLabelZoomIn)
                                        )
                                )
                        )
                        .addGap(6, 6, 6)
                )
        );

        jPanelGeometry.setBackground(new java.awt.Color(236, 236, 236));
        jPanelGeometry.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanelGeometry.setPreferredSize(new java.awt.Dimension(675, 35));

        jLabelNodes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/geometry_node.png"))); // NOI18N
        jLabelNodes.setToolTipText("Insert Nodes");

        jLabelNumberOfNodes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/geometry_element.png"))); // NOI18N
        jLabelNumberOfNodes.setToolTipText("Number Of Nodes");

        jLabelSections.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/geometry_sections.png"))); // NOI18N
        jLabelSections.setToolTipText("Sections");

        jLabelMaterials.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/geometry_materials.png"))); // NOI18N
        jLabelMaterials.setToolTipText("Materials");

        jLabelMesh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/geometry_mesh.png"))); // NOI18N
        jLabelMesh.setToolTipText("Mesh Refinement");

        jLabelDimensionLine.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/geometry_line.png"))); // NOI18N
        jLabelDimensionLine.setToolTipText("Dimension Line");

        jLabelVSimpleSupport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/geometry_support_1.png"))); // NOI18N
        jLabelVSimpleSupport.setToolTipText("Vertical Simple Support");

        jLabelHSimpleSupport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/geometry_support_2.png"))); // NOI18N
        jLabelHSimpleSupport.setToolTipText("Horizontal Simple Support");

        jLabelPinnedSupport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/geometry_support_3.png"))); // NOI18N
        jLabelPinnedSupport.setToolTipText("Pinned Support");

        jLabelHSliderSupport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/geometry_support_4.png"))); // NOI18N
        jLabelHSliderSupport.setToolTipText("Horizontal Slider Support");

        jLabelFixedSupport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/geometry_support_6.png"))); // NOI18N
        jLabelFixedSupport.setToolTipText("Fixed Support");

        jLabelVSliderSupport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/geometry_support_5.png"))); // NOI18N
        jLabelVSliderSupport.setToolTipText("Vertical Slider Support");

        jLabelElasticSupport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/geometry_support_7.png"))); // NOI18N
        jLabelElasticSupport.setToolTipText("Elastic Support");

        jLabelSettlements.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/geometry_support_8.png"))); // NOI18N
        jLabelSettlements.setToolTipText("Settlements");

        jLabelCutSupports.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/geometry_cut.png"))); // NOI18N
        jLabelCutSupports.setToolTipText("Delete Supports");

        javax.swing.GroupLayout jPanelGeometryLayout = new javax.swing.GroupLayout(jPanelGeometry);
        jPanelGeometry.setLayout(jPanelGeometryLayout);
        jPanelGeometryLayout.setHorizontalGroup(
            jPanelGeometryLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanelGeometryLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelNodes)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelNumberOfNodes)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelSections)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelMaterials)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelMesh)
                        .addGap(30, 30, 30)
                        .addComponent(jLabelDimensionLine)
                        .addGap(30, 30, 30)
                        .addComponent(jLabelVSimpleSupport)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelHSimpleSupport)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelPinnedSupport)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelHSliderSupport)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelVSliderSupport)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelFixedSupport)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelElasticSupport)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelSettlements)
                        .addGap(30, 30, 30)
                        .addComponent(jLabelCutSupports)
                        .addContainerGap(200, Short.MAX_VALUE)
                )
        );
        jPanelGeometryLayout.setVerticalGroup(
            jPanelGeometryLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanelGeometryLayout
                        .createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(
                            jPanelGeometryLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabelMesh)
                                .addGroup(
                                    jPanelGeometryLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(
                                            jPanelGeometryLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(
                                                    jPanelGeometryLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(
                                                            jPanelGeometryLayout
                                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(
                                                                    jPanelGeometryLayout
                                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(
                                                                            jPanelGeometryLayout
                                                                                .createParallelGroup(
                                                                                    javax.swing.GroupLayout.Alignment.LEADING
                                                                                )
                                                                                .addGroup(
                                                                                    jPanelGeometryLayout
                                                                                        .createParallelGroup(
                                                                                            javax.swing.GroupLayout.Alignment.LEADING
                                                                                        )
                                                                                        .addGroup(
                                                                                            jPanelGeometryLayout
                                                                                                .createParallelGroup(
                                                                                                    javax.swing.GroupLayout.Alignment.LEADING
                                                                                                )
                                                                                                .addComponent(
                                                                                                    jLabelNumberOfNodes,
                                                                                                    javax.swing.GroupLayout.Alignment.TRAILING
                                                                                                )
                                                                                                .addGroup(
                                                                                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                    jPanelGeometryLayout
                                                                                                        .createParallelGroup(
                                                                                                            javax.swing.GroupLayout.Alignment.BASELINE
                                                                                                        )
                                                                                                        .addComponent(jLabelVSimpleSupport)
                                                                                                        .addComponent(jLabelPinnedSupport)
                                                                                                        .addComponent(jLabelHSliderSupport)
                                                                                                )
                                                                                        )
                                                                                        .addGroup(
                                                                                            javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                            jPanelGeometryLayout
                                                                                                .createParallelGroup(
                                                                                                    javax.swing.GroupLayout.Alignment.LEADING
                                                                                                )
                                                                                                .addComponent(jLabelMaterials)
                                                                                                .addComponent(jLabelSections)
                                                                                        )
                                                                                )
                                                                                .addComponent(
                                                                                    jLabelNodes,
                                                                                    javax.swing.GroupLayout.Alignment.TRAILING
                                                                                )
                                                                        )
                                                                        .addComponent(
                                                                            jLabelHSimpleSupport,
                                                                            javax.swing.GroupLayout.Alignment.TRAILING
                                                                        )
                                                                )
                                                                .addComponent(jLabelCutSupports, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        )
                                                        .addComponent(jLabelFixedSupport, javax.swing.GroupLayout.Alignment.TRAILING)
                                                )
                                                .addComponent(jLabelVSliderSupport, javax.swing.GroupLayout.Alignment.TRAILING)
                                        )
                                        .addComponent(jLabelDimensionLine, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(
                                            javax.swing.GroupLayout.Alignment.TRAILING,
                                            jPanelGeometryLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabelElasticSupport)
                                                .addComponent(jLabelSettlements)
                                        )
                                )
                        )
                        .addGap(6, 6, 6)
                )
        );

        jPanelLoads.setBackground(new java.awt.Color(236, 236, 236));
        jPanelLoads.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanelLoads.setPreferredSize(new java.awt.Dimension(675, 35));

        jLabelConcentratedLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/loads_load_1.png"))); // NOI18N
        jLabelConcentratedLoad.setToolTipText("Concentrated Load");

        jLabelBendingMoment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/loads_load_2.png"))); // NOI18N
        jLabelBendingMoment.setToolTipText("Bending Moment");

        jLabelUnifDistLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/loads_load_3.png"))); // NOI18N
        jLabelUnifDistLoad.setToolTipText("Uniformly Distributed Load");

        jLabelDistAxialLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/loads_load_4.png"))); // NOI18N
        jLabelDistAxialLoad.setToolTipText("Distributed Axial Load");

        jLabelUnifPlanarLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/loads_load_5.png"))); // NOI18N
        jLabelUnifPlanarLoad.setToolTipText("Uniform Planar Load");

        jLabelThermalLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/loads_load_6.png"))); // NOI18N
        jLabelThermalLoad.setToolTipText("Thermal Load");

        jLabelSelfWeight.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/loads_load_7.png"))); // NOI18N
        jLabelSelfWeight.setToolTipText("Self-Weight");

        jLabelCutLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/loads_cut.png"))); // NOI18N
        jLabelCutLoad.setToolTipText("Delete Load");

        jLabelLoadTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/loads_load_table.png"))); // NOI18N
        jLabelLoadTable.setToolTipText("Load Table");

        javax.swing.GroupLayout jPanelLoadsLayout = new javax.swing.GroupLayout(jPanelLoads);
        jPanelLoads.setLayout(jPanelLoadsLayout);
        jPanelLoadsLayout.setHorizontalGroup(
            jPanelLoadsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanelLoadsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelConcentratedLoad)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelBendingMoment)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelUnifDistLoad)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelDistAxialLoad)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelUnifPlanarLoad)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelThermalLoad)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelSelfWeight)
                        .addGap(30, 30, 30)
                        .addComponent(jLabelCutLoad)
                        .addGap(30, 30, 30)
                        .addComponent(jLabelLoadTable)
                        .addContainerGap(380, Short.MAX_VALUE)
                )
        );
        jPanelLoadsLayout.setVerticalGroup(
            jPanelLoadsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanelLoadsLayout
                        .createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(
                            jPanelLoadsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabelThermalLoad)
                                .addComponent(jLabelSelfWeight)
                                .addComponent(jLabelUnifPlanarLoad)
                                .addComponent(jLabelCutLoad)
                                .addGroup(
                                    jPanelLoadsLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(
                                            jPanelLoadsLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(
                                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                                    jPanelLoadsLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabelConcentratedLoad)
                                                        .addComponent(jLabelUnifDistLoad)
                                                        .addComponent(jLabelDistAxialLoad)
                                                )
                                                .addComponent(jLabelLoadTable, javax.swing.GroupLayout.Alignment.TRAILING)
                                        )
                                        .addComponent(jLabelBendingMoment, javax.swing.GroupLayout.Alignment.TRAILING)
                                )
                        )
                        .addGap(6, 6, 6)
                )
        );

        jPanelAnalysis.setBackground(new java.awt.Color(236, 236, 236));
        jPanelAnalysis.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanelAnalysis.setPreferredSize(new java.awt.Dimension(675, 35));

        jLabelTheory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/analysis_theory.png"))); // NOI18N
        jLabelTheory.setToolTipText("Theory");

        jRadioButtonAnalytical.setText("Analytical");
        jRadioButtonAnalytical.setToolTipText("Analytical Analysis");
        jRadioButtonAnalytical.setFocusable(false);
        jRadioButtonAnalytical.setOpaque(false);

        jRadioButtonNumerical.setText("Numerical");
        jRadioButtonNumerical.setToolTipText("Numerical Analysis");
        jRadioButtonNumerical.setFocusable(false);
        jRadioButtonNumerical.setOpaque(false);

        jLabelCalculate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/analysis_calculate.png"))); // NOI18N
        jLabelCalculate.setToolTipText("Calculate");

        javax.swing.GroupLayout jPanelAnalysisLayout = new javax.swing.GroupLayout(jPanelAnalysis);
        jPanelAnalysis.setLayout(jPanelAnalysisLayout);
        jPanelAnalysisLayout.setHorizontalGroup(
            jPanelAnalysisLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    javax.swing.GroupLayout.Alignment.TRAILING,
                    jPanelAnalysisLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelTheory)
                        .addGap(30, 30, 30)
                        .addComponent(jRadioButtonAnalytical)
                        .addGap(30, 30, 30)
                        .addComponent(jRadioButtonNumerical)
                        .addGap(30, 30, 30)
                        .addComponent(jLabelCalculate)
                        .addContainerGap(383, Short.MAX_VALUE)
                )
        );
        jPanelAnalysisLayout.setVerticalGroup(
            jPanelAnalysisLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    javax.swing.GroupLayout.Alignment.TRAILING,
                    jPanelAnalysisLayout
                        .createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(
                            jPanelAnalysisLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelTheory)
                                .addComponent(
                                    jRadioButtonAnalytical,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jRadioButtonNumerical,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(jLabelCalculate)
                        )
                        .addGap(6, 6, 6)
                )
        );

        jPanelResults.setBackground(new java.awt.Color(236, 236, 236));
        jPanelResults.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanelResults.setPreferredSize(new java.awt.Dimension(675, 35));

        jLabelVectorF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/results_vectorF.png"))); // NOI18N
        jLabelVectorF.setToolTipText("Vector { F }");

        jLabelMatrixK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/results_matrixK.png"))); // NOI18N
        jLabelMatrixK.setToolTipText("Matrix [ K ]");

        jLabelVectorDisplacements.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/results_vectorDis.png"))); // NOI18N
        jLabelVectorDisplacements.setToolTipText("Vector { d }");

        jLabelProperties.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/results_element.png"))); // NOI18N
        jLabelProperties.setToolTipText("Individual Properties");

        jLabelSupportReactions.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/results_reactions.png"))); // NOI18N
        jLabelSupportReactions.setToolTipText("Support Reactions");

        jLabelNodalForces.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/results_forces.png"))); // NOI18N
        jLabelNodalForces.setToolTipText("Nodal Forces");

        jLabelNodalStresses.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/results_stresses.png"))); // NOI18N
        jLabelNodalStresses.setToolTipText("Nodal Stresses");

        jLabelDisplacements.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/results_displacements.png"))); // NOI18N
        jLabelDisplacements.setToolTipText("Displacements");

        jLabelIsovalues.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/results_lines_isovalue.png"))); // NOI18N
        jLabelIsovalues.setToolTipText("Lines of Isovalues");

        jLabelMaps.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/results_maps.png"))); // NOI18N
        jLabelMaps.setToolTipText("Maps of Stresses");

        jLabelPrincipalStresses.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/results_directions.png"))); // NOI18N
        jLabelPrincipalStresses.setToolTipText("Stresses and Principal Directions");

        jLabelDiagrams.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/results_diagrams.png"))); // NOI18N
        jLabelDiagrams.setToolTipText("Diagrams for Bars");

        javax.swing.GroupLayout jPanelResultsLayout = new javax.swing.GroupLayout(jPanelResults);
        jPanelResults.setLayout(jPanelResultsLayout);
        jPanelResultsLayout.setHorizontalGroup(
            jPanelResultsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    javax.swing.GroupLayout.Alignment.TRAILING,
                    jPanelResultsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelVectorF)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelMatrixK)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelVectorDisplacements)
                        .addGap(30, 30, 30)
                        .addComponent(jLabelProperties)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelSupportReactions)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelNodalForces)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelNodalStresses)
                        .addGap(30, 30, 30)
                        .addComponent(jLabelDisplacements)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelDiagrams)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelIsovalues)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelMaps)
                        .addGap(0, 0, 0)
                        .addComponent(jLabelPrincipalStresses)
                        .addGap(393, 393, 393)
                )
        );
        jPanelResultsLayout.setVerticalGroup(
            jPanelResultsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanelResultsLayout
                        .createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(
                            jPanelResultsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(
                                    jPanelResultsLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(
                                            jPanelResultsLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabelPrincipalStresses)
                                                .addGroup(
                                                    jPanelResultsLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabelMaps)
                                                        .addComponent(jLabelIsovalues)
                                                        .addComponent(jLabelDisplacements)
                                                        .addGroup(
                                                            javax.swing.GroupLayout.Alignment.TRAILING,
                                                            jPanelResultsLayout
                                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(jLabelSupportReactions)
                                                                .addComponent(jLabelNodalForces)
                                                                .addComponent(jLabelNodalStresses)
                                                                .addComponent(jLabelVectorF)
                                                        )
                                                )
                                                .addGroup(
                                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                                    jPanelResultsLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabelMatrixK)
                                                        .addComponent(jLabelVectorDisplacements)
                                                )
                                        )
                                        .addComponent(jLabelProperties, javax.swing.GroupLayout.Alignment.TRAILING)
                                )
                                .addComponent(jLabelDiagrams, javax.swing.GroupLayout.Alignment.TRAILING)
                        )
                        .addGap(6, 6, 6)
                )
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FEM for Students");
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(900, 600));
        setName("Interface"); // NOI18N
        addKeyListener(
            new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    formKeyPressed(evt);
                }

                public void keyReleased(java.awt.event.KeyEvent evt) {
                    formKeyReleased(evt);
                }

                public void keyTyped(java.awt.event.KeyEvent evt) {
                    formKeyTyped(evt);
                }
            }
        );

        jPanel2.setBackground(new java.awt.Color(222, 222, 222));
        jPanel2.setPreferredSize(new java.awt.Dimension(0, 25));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(41, 41, 41));
        jPanel3.setPreferredSize(new java.awt.Dimension(0, 1));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 1, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(57, 57, 57));
        jPanel4.setPreferredSize(new java.awt.Dimension(0, 26));

        menuDraw.setBackground(new java.awt.Color(236, 236, 236));
        menuDraw.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        menuDraw.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuDraw.setText("Draw");
        menuDraw.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        menuDraw.setOpaque(true);
        menuDraw.addMouseListener(
            new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    menuDrawMousePressed(evt);
                }
            }
        );

        menuView.setBackground(new java.awt.Color(236, 236, 236));
        menuView.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        menuView.setForeground(new java.awt.Color(255, 255, 255));
        menuView.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuView.setText("View");
        menuView.addMouseListener(
            new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    menuViewMousePressed(evt);
                }
            }
        );

        menuLoads.setBackground(new java.awt.Color(236, 236, 236));
        menuLoads.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        menuLoads.setForeground(new java.awt.Color(255, 255, 255));
        menuLoads.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuLoads.setText("Loads");
        menuLoads.addMouseListener(
            new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    menuLoadsMousePressed(evt);
                }
            }
        );

        menuGeometry.setBackground(new java.awt.Color(236, 236, 236));
        menuGeometry.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        menuGeometry.setForeground(new java.awt.Color(255, 255, 255));
        menuGeometry.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuGeometry.setText("Geometry");
        menuGeometry.addMouseListener(
            new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    menuGeometryMousePressed(evt);
                }
            }
        );

        menuAnalysis.setBackground(new java.awt.Color(236, 236, 236));
        menuAnalysis.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        menuAnalysis.setForeground(new java.awt.Color(255, 255, 255));
        menuAnalysis.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuAnalysis.setText("Analysis");
        menuAnalysis.addMouseListener(
            new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    menuAnalysisMousePressed(evt);
                }
            }
        );

        menuResults.setBackground(new java.awt.Color(236, 236, 236));
        menuResults.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        menuResults.setForeground(new java.awt.Color(255, 255, 255));
        menuResults.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuResults.setText("Results");
        menuResults.addMouseListener(
            new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    menuResultsMousePressed(evt);
                }
            }
        );

        jLabel11.setBackground(new java.awt.Color(41, 41, 41));
        jLabel11.setOpaque(true);

        jLabel12.setBackground(new java.awt.Color(41, 41, 41));
        jLabel12.setOpaque(true);

        jLabel13.setBackground(new java.awt.Color(41, 41, 41));
        jLabel13.setOpaque(true);

        jLabel14.setBackground(new java.awt.Color(41, 41, 41));
        jLabel14.setOpaque(true);

        jLabel16.setBackground(new java.awt.Color(41, 41, 41));
        jLabel16.setOpaque(true);

        jLabel15.setBackground(new java.awt.Color(41, 41, 41));
        jLabel15.setOpaque(true);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel4Layout
                        .createSequentialGroup()
                        .addComponent(menuDraw, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(menuView, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(menuGeometry, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(menuLoads, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(menuAnalysis, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(menuResults, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 169, Short.MAX_VALUE)
                )
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(menuView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(menuGeometry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(menuAnalysis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(menuResults, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(menuDraw, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(menuLoads, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(236, 236, 236));
        jPanel5.setPreferredSize(new java.awt.Dimension(0, 35));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 35, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.setPreferredSize(new java.awt.Dimension(0, 2));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(52, 52, 52));
        jPanel7.setPreferredSize(new java.awt.Dimension(288, 40));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("FEM for Students");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup().addGap(0, 0, 0).addComponent(jLabel2).addContainerGap(166, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel7Layout
                        .createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)
                )
        );

        jPanel8.setBackground(new java.awt.Color(52, 52, 52));
        jPanel8.setPreferredSize(new java.awt.Dimension(200, 40));

        jLHelp.setForeground(new java.awt.Color(255, 255, 255));
        jLHelp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLHelp.setText("Help");
        jLHelp.addMouseListener(
            new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    jLHelpMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    jLHelpMouseExited(evt);
                }

                public void mousePressed(java.awt.event.MouseEvent evt) {
                    jLHelpMousePressed(evt);
                }
            }
        );

        jLEdit.setForeground(new java.awt.Color(255, 255, 255));
        jLEdit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLEdit.setText("Edit");
        jLEdit.addMouseListener(
            new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    jLEditMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    jLEditMouseExited(evt);
                }

                public void mousePressed(java.awt.event.MouseEvent evt) {
                    jLEditMousePressed(evt);
                }
            }
        );

        jLFile.setForeground(new java.awt.Color(255, 255, 255));
        jLFile.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLFile.setText("File");
        jLFile.addMouseListener(
            new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    jLFileMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    jLFileMouseExited(evt);
                }

                public void mousePressed(java.awt.event.MouseEvent evt) {
                    jLFileMousePressed(evt);
                }
            }
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    javax.swing.GroupLayout.Alignment.TRAILING,
                    jPanel8Layout
                        .createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLFile, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLHelp, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                )
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel8Layout
                        .createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(
                            jPanel8Layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLHelp, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLFile, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        )
                        .addGap(10, 10, 10)
                )
        );

        jPanel9.setBackground(new java.awt.Color(52, 52, 52));
        jPanel9.setMinimumSize(new java.awt.Dimension(150, 40));
        jPanel9.setPreferredSize(new java.awt.Dimension(140, 40));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/UPwhite.png"))); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/FEUPwhite.png"))); // NOI18N

        jLabel8.setBackground(new java.awt.Color(41, 41, 41));
        jLabel8.setOpaque(true);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    javax.swing.GroupLayout.Alignment.TRAILING,
                    jPanel9Layout
                        .createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap()
                )
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(
                    jPanel9Layout
                        .createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                )
        );

        jPanel10.setBackground(new java.awt.Color(52, 52, 52));
        jPanel10.setPreferredSize(new java.awt.Dimension(60, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/iconFEM.png"))); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel10Layout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap()
                )
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLateralPanel.setBackground(new java.awt.Color(236, 236, 236));
        jLateralPanel.setAlignmentX(0.0F);
        jLateralPanel.setAlignmentY(0.0F);
        jLateralPanel.setPreferredSize(new java.awt.Dimension(0, 310));

        javax.swing.GroupLayout jLateralPanelLayout = new javax.swing.GroupLayout(jLateralPanel);
        jLateralPanel.setLayout(jLateralPanelLayout);
        jLateralPanelLayout.setHorizontalGroup(
            jLateralPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );
        jLateralPanelLayout.setVerticalGroup(
            jLateralPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 322, Short.MAX_VALUE)
        );

        jScrollPane1.setBorder(null);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel1Layout
                        .createSequentialGroup()
                        .addComponent(
                            jLateralPanel,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(jScrollPane1)
                )
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLateralPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                .addComponent(jScrollPane1)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)
                .addComponent(
                    jPanel3,
                    javax.swing.GroupLayout.Alignment.TRAILING,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    675,
                    Short.MAX_VALUE
                )
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)
                .addGroup(
                    layout
                        .createSequentialGroup()
                        .addComponent(
                            jPanel10,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanel7,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanel9,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                )
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    layout
                        .createSequentialGroup()
                        .addGroup(
                            layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(
                                    jPanel10,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jPanel8,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jPanel7,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jPanel9,
                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addComponent(
                            jPanel3,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanel4,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanel5,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanel6,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanel2,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                )
        );

        pack();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * Classe para pintar o fundo do painel que contém o menu
     */
    private class JPanelMenuTheme extends javax.swing.JPanel {

        private final Image image;

        public JPanelMenuTheme() {
            image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/background.png"));
        }

        @Override
        protected void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);

            int width = this.getWidth();

            int x = 0;
            while (x < width) {
                g.drawImage(image, x, 0, this);
                x = x + 100;
            }
        }
    }

    /**
     * Classe para criar os efeitos de transição nos rótulos do menu
     */
    private class LabelMouseEvents extends javax.swing.JLabel {

        private boolean selected;
        private boolean mousePressed;

        /**
         * Método que retorna o estado do rótulo
         *
         * @return
         */
        public boolean isSelected() {
            return selected;
        }

        /**
         * Método para mudar o estado do rótulo
         *
         * @param select parâmetro que contém o estado do objeto
         */
        public void setSelected(boolean selected) {
            if (this.selected && !selected && !mousePressed) {
                this.selected = selected;
                jLabelMouseExited(null);
            } else {
                this.selected = selected;
            }
        }

        /**
         * Método para ativar o estado de pressionar o rótulo
         *
         * @param selected parâmetro que contém o estado do objeto
         * @param pressed parâmetro que informa se o objeto está pressionado
         */
        public void setSelectedAndMousePressed(boolean selected, boolean pressed) {
            this.selected = selected;
            this.mousePressed = pressed;
        }

        public void jLabelMouseClicked(java.awt.event.MouseEvent evt) {
            if (isEnabled()) {
                mousePressed = false;
                setBackground(new java.awt.Color(209, 226, 242));
                setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(120, 174, 229)));
                setOpaque(true);
            }
        }

        public void jLabelMouseEntered(java.awt.event.MouseEvent evt) {
            if (isEnabled()) {
                mousePressed = false;
                setBackground(new java.awt.Color(209, 226, 242));
                setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(120, 174, 229)));
                setOpaque(true);
            }
        }

        public void jLabelMouseExited(java.awt.event.MouseEvent evt) {
            if (!selected) {
                setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(236, 236, 236)));
                setOpaque(false);
            }
            mousePressed = false;
        }

        public void jLabelMousePressed(java.awt.event.MouseEvent evt) {
            if (isEnabled()) {
                setBackground(new java.awt.Color(180, 212, 244));
                setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(96, 161, 226)));
                setOpaque(true);
                mousePressed = true;
            }
        }

        public void jLabelMouseDragged(java.awt.event.MouseEvent evt) {
            if (isEnabled()) {
                setBackground(new java.awt.Color(209, 226, 242));
                setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(120, 174, 229)));
                setOpaque(true);
                mousePressed = false;
            }
        }

        public void jLabelMouseMoved(java.awt.event.MouseEvent evt) {
            jLabelMouseDragged(evt);
        }

        public LabelMouseEvents() {
            this.selected = false;
            this.mousePressed = false;

            //Adição por defeito do contorno aos rótulos
            setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(236, 236, 236)));

            //Adição dos métodos para receber as ações do rato
            addMouseListener(
                new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        jLabelMouseClicked(evt);
                    }

                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        jLabelMouseEntered(evt);
                    }

                    @Override
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        jLabelMouseExited(evt);
                    }

                    @Override
                    public void mousePressed(java.awt.event.MouseEvent evt) {
                        jLabelMousePressed(evt);
                    }
                }
            );

            addMouseMotionListener(
                new MouseMotionAdapter() {
                    @Override
                    public void mouseDragged(java.awt.event.MouseEvent evt) {
                        jLabelMouseDragged(evt);
                    }

                    @Override
                    public void mouseMoved(java.awt.event.MouseEvent evt) {
                        jLabelMouseMoved(evt);
                    }
                }
            );
        }
    }

    /**
     * Classe para criar os efeitos de transição nos rótulos do menu
     */
    private class MenuMouseEvents extends javax.swing.JLabel {

        private void menuMouseEntered(java.awt.event.MouseEvent evt) {
            if (isOpaque() == false) {
                setForeground(new java.awt.Color(99, 180, 251));
            }
        }

        private void menuMouseExited(java.awt.event.MouseEvent evt) {
            if (isOpaque() == true) {
                setForeground(new java.awt.Color(0, 0, 0));
            } else {
                setForeground(new java.awt.Color(255, 255, 255));
            }
        }

        public MenuMouseEvents() {
            addMouseListener(
                new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        menuMouseEntered(evt);
                    }

                    @Override
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        menuMouseExited(evt);
                    }
                }
            );
        }
    }

    /**
     * Classe para fornecer as mensagens de texto relativas às dicas do programa
     */
    private class Informations extends javax.swing.JLabel {

        //Informações relativas às funções do painel Draw
        String jPDraw_Line = "Set two different points to draw a line.";
        String jPDraw_Triangle = "Set three different points to draw a triangle.";
        String jPDraw_Rectangle = "Set three different points to draw a rectangle.";
        String jPDraw_Quadrilateral = "Set four different points to draw a quadrilateral.";
        String jPDraw_Select = "Click or drag over objects to select. Press CTRL for multiple objects.";

        //Informações relativas às funções do painel Geometry
        String jPGeometry_Supports = "Select a vertex of the mesh to put the support.";
        String jPGeometry_ElasticSupport = "Select a vertex of the mesh to put the elastic support.";
        String jPGeometry_Settlements = "Select a vertex of the mesh to put the settlement.";
        String jPGeometry_CutSupports = "Select the support to remove the mesh.";

        //Informações relativas às funções do painel Loads
        String jPLoads_ConcentratedLoad = "Select a vertex of the mesh to assign the load.";
        String jPLoads_BendingMoment = "Select a vertex of the mesh to assign the load.";
        String jPLoads_UnifDistLoad = "Select two nodes of the finite element to assign the load.";
        String jPLoads_DistAxialLoad = "Select two nodes of the finite element to assign the load.";
        String jPLoads_UnifPlanarLoad = "Select a point of the finite element to assign the load.";
        String jPLoads_ThermalLoad = "Select two nodes of the finite element to assign the load.";
        String jPLoads_SelfWeight = "Set the density of the material of finite elements.";
        String jPLoads_CutLoads = "Select a point to remove the load.";

        //Informações relativas às funções do painel Analysis
        String jPAnalysis_CalculateBegin = "The compute...";
        String jPAnalysis_CalculateEnd = "Ready!";
    }

    /**
     * Método para abrir o painel do menu Draw
     */
    private void menuDrawMousePressed(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_menuDrawMousePressed
        if (activateMenu) {
            activateMenuDraw();
        }
    } //GEN-LAST:event_menuDrawMousePressed

    /**
     * Método para abrir o painel do menu View
     */
    private void menuViewMousePressed(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_menuViewMousePressed
        if (activateMenu) {
            activateMenuView();
        }
    } //GEN-LAST:event_menuViewMousePressed

    /**
     * Método para abrir o painel do menu Geometry
     */
    private void menuGeometryMousePressed(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_menuGeometryMousePressed
        if (activateMenu) {
            activateMenuGeometry();
        }
    } //GEN-LAST:event_menuGeometryMousePressed

    /**
     * Método para abrir o painel do menu Loads
     */
    private void menuLoadsMousePressed(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_menuLoadsMousePressed
        if (activateMenu) {
            activateMenuLoads();
        }
    } //GEN-LAST:event_menuLoadsMousePressed

    /**
     * Método para abrir o painel do menu Analysis
     */
    private void menuAnalysisMousePressed(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_menuAnalysisMousePressed
        if (activateMenu) {
            activateMenuAnalysis();
        }
    } //GEN-LAST:event_menuAnalysisMousePressed

    /**
     * Método para abrir o painel do menu Results
     */
    private void menuResultsMousePressed(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_menuResultsMousePressed
        if (activateMenu) {
            activateMenuResults();
        }
    } //GEN-LAST:event_menuResultsMousePressed

    /**
     * Método para fechar a janela jFrameLoadTable
     */
    private void jButtonClose_LoadActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButtonClose_LoadActionPerformed
        jFrameLoadTable.setVisible(false);
    } //GEN-LAST:event_jButtonClose_LoadActionPerformed

    /**
     * Método para fechar a janela jFrameProperties
     */
    private void jButtonClose_PropertiesActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButtonClose_PropertiesActionPerformed
        jFrameProperties.setVisible(false);
    } //GEN-LAST:event_jButtonClose_PropertiesActionPerformed

    /**
     * Método para abrir o popup jPopupButtonFile
     */
    private void jLFileMousePressed(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_jLFileMousePressed
        jLFile.setForeground(new java.awt.Color(255, 255, 255));
        jLFile.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jLEdit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(52, 52, 52)));
        jLHelp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(52, 52, 52)));
        jPopupButtonFile(evt);
    } //GEN-LAST:event_jLFileMousePressed

    /**
     * Método para abrir o popup jPopupButtonEdit
     */
    private void jLEditMousePressed(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_jLEditMousePressed
        jLFile.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(52, 52, 52)));
        jLEdit.setForeground(new java.awt.Color(255, 255, 255));
        jLEdit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jLHelp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(52, 52, 52)));
        jPopupButtonEdit(evt);
    } //GEN-LAST:event_jLEditMousePressed

    /**
     * Método para abrir o popup jPopupButtonHelp
     */
    private void jLHelpMousePressed(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_jLHelpMousePressed
        jLFile.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(52, 52, 52)));
        jLEdit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(52, 52, 52)));
        jLHelp.setForeground(new java.awt.Color(255, 255, 255));
        jLHelp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPopupButtonHelp(evt);
    } //GEN-LAST:event_jLHelpMousePressed

    /**
     * Método para fechar a dialog jDialogAbout
     */
    private void jButtonCloseAboutActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButtonCloseAboutActionPerformed
        jDialogAbout.setVisible(false);
    } //GEN-LAST:event_jButtonCloseAboutActionPerformed

    /**
     * Método para fechar a dialog jDialogPoint
     */
    private void jBInsertPointCancelActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jBInsertPointCancelActionPerformed
        jDialogPoint.setVisible(false);
        jTInputX.setText("");
        jTInputY.setText("");
    } //GEN-LAST:event_jBInsertPointCancelActionPerformed

    /**
     * Método para receber as coordenadas do ponto e mandar desenhá-lo
     */
    private void jBInsertPointInsertActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jBInsertPointInsertActionPerformed
        try {
            double x = Double.parseDouble(formatString(jTInputX.getText()));
            double y = Double.parseDouble(formatString(jTInputY.getText()));

            int xPoint = (int) (Math.round(x * factor));
            int yPoint = (int) (Math.round(y * factor));

            jDrawingPanel.drawPoint(xPoint, yPoint);
        } catch (NumberFormatException e) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Input values ​​are invalid!");
        }

        jDialogPoint.setVisible(false);
        jTInputX.setText("");
        jTInputY.setText("");
    } //GEN-LAST:event_jBInsertPointInsertActionPerformed

    /**
     * Método para quando o rato entra dentro da label jLFile
     */
    private void jLFileMouseEntered(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_jLFileMouseEntered
        if (!popupFile.isVisible()) {
            jLFile.setForeground(new java.awt.Color(99, 180, 251));
        }
        if (popupEdit.isVisible() || popupHelp.isVisible()) {
            jLFileMousePressed(evt);
        }
    } //GEN-LAST:event_jLFileMouseEntered

    /**
     * Método para quando o rato entra dentro da label jLEdit
     */
    private void jLEditMouseEntered(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_jLEditMouseEntered
        if (!popupEdit.isVisible()) {
            jLEdit.setForeground(new java.awt.Color(99, 180, 251));
        }
        if (popupFile.isVisible() || popupHelp.isVisible()) {
            jLEditMousePressed(evt);
        }
    } //GEN-LAST:event_jLEditMouseEntered

    /**
     * Método para quando o rato entra dentro da label jLHelp
     */
    private void jLHelpMouseEntered(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_jLHelpMouseEntered
        if (!popupHelp.isVisible()) {
            jLHelp.setForeground(new java.awt.Color(99, 180, 251));
        }
        if (popupFile.isVisible() || popupEdit.isVisible()) {
            jLHelpMousePressed(evt);
        }
    } //GEN-LAST:event_jLHelpMouseEntered

    /**
     * Método para quando uma tecla é pressionada
     */
    private void formKeyPressed(java.awt.event.KeyEvent evt) { //GEN-FIRST:event_formKeyPressed
        //TODO add your handling code here
    } //GEN-LAST:event_formKeyPressed

    /**
     * Método para quando uma tecla é libertada
     */
    private void formKeyReleased(java.awt.event.KeyEvent evt) { //GEN-FIRST:event_formKeyReleased
        //Eventos asociados aos métodos de desenho
        if (!jPWelcome.isShowing()) {
            LabelMouseEvents label;
            if (KeyEvent.VK_ESCAPE == evt.getKeyCode()) {
                resetLabelsNumeration();
                resetCursor();
                jDrawingPanel.escape();
                if (!jDrawingPanel.isShowing()) {
                    jScrollPane1.setViewportView(jDrawingPanel);
                }
            }
            if (KeyEvent.VK_DELETE == evt.getKeyCode()) {
                jDrawingPanel.cutObject();
            }
            if (KeyEvent.VK_P == evt.getKeyCode()) {
                label = (LabelMouseEvents) jLabelPoint;
                label.jLabelMouseClicked(null);
                panelDraw_jLabelPoint(null);
                label.jLabelMouseExited(null);
            }
            if (KeyEvent.VK_L == evt.getKeyCode()) {
                label = (LabelMouseEvents) jLabelLine;
                label.setSelectedAndMousePressed(false, true);
                label.jLabelMouseClicked(null);
                panelDraw_jLabelLine(null);
            }
            if (KeyEvent.VK_T == evt.getKeyCode()) {
                label = (LabelMouseEvents) jLabelTriangle;
                label.setSelectedAndMousePressed(false, true);
                label.jLabelMouseClicked(null);
                panelDraw_jLabelTriangle(null);
            }
            if (KeyEvent.VK_R == evt.getKeyCode()) {
                label = (LabelMouseEvents) jLabelRectangle;
                label.setSelectedAndMousePressed(false, true);
                label.jLabelMouseClicked(null);
                panelDraw_jLabelRectangle(null);
            }
            if (KeyEvent.VK_Q == evt.getKeyCode()) {
                label = (LabelMouseEvents) jLabelQuadrilateral;
                label.setSelectedAndMousePressed(false, true);
                label.jLabelMouseClicked(null);
                panelDraw_jLabelQuadrilateral(null);
            }
            if (KeyEvent.VK_M == evt.getKeyCode()) {
                label = (LabelMouseEvents) jLabelMove;
                label.jLabelMouseClicked(null);
                panelDraw_jLabelMove(null);
            }
            if (KeyEvent.VK_S == evt.getKeyCode()) {
                label = (LabelMouseEvents) jLabelSelect;
                label.setSelectedAndMousePressed(false, true);
                label.jLabelMouseClicked(null);
                panelDraw_jLabelSelect(null);
            }
        }

        //Eventos associados ao combinação de teclas
        if (!popupFile.isVisible() || !popupEdit.isVisible() || !popupHelp.isVisible()) {
            if (KeyEvent.VK_N == evt.getKeyCode()) {
                if (evt.isControlDown()) {
                    jMenuItemFileNew(null);
                }
            }
            if (KeyEvent.VK_O == evt.getKeyCode()) {
                if (evt.isControlDown()) {
                    jMenuItemFileOpen(null);
                }
            }
            if (KeyEvent.VK_W == evt.getKeyCode()) {
                if (evt.isControlDown()) {
                    jMenuItemFileClose(null);
                }
            }
            if (KeyEvent.VK_S == evt.getKeyCode()) {
                if (evt.isControlDown()) {
                    jMenuItemFileSave(null);
                }
            }

            if (!jPWelcome.isShowing()) {
                if (KeyEvent.VK_X == evt.getKeyCode()) {
                    if (evt.isControlDown()) {
                        panelDraw_jLabelCut(null);
                    }
                }
                if (KeyEvent.VK_C == evt.getKeyCode()) {
                    if (evt.isControlDown()) {
                        panelDraw_jLabelCopy(null);
                    }
                }
                if (KeyEvent.VK_V == evt.getKeyCode()) {
                    if (evt.isControlDown()) {
                        panelDraw_jLabelPaste(null);
                    }
                }
                if (KeyEvent.VK_Z == evt.getKeyCode()) {
                    if (evt.isControlDown()) {
                        panelDraw_jLabelUndo(null);
                    }
                }
                if (KeyEvent.VK_Y == evt.getKeyCode()) {
                    if (evt.isControlDown()) {
                        panelDraw_jLabelRedo(null);
                    }
                }
            }

            if (KeyEvent.VK_F1 == evt.getKeyCode()) {
                jMenuItemHelpHelp(null);
            }
        }
    } //GEN-LAST:event_formKeyReleased

    /**
     * Método para quando uma tecla está pressionada
     */
    private void formKeyTyped(java.awt.event.KeyEvent evt) { //GEN-FIRST:event_formKeyTyped
        //TODO add your handling code here
    } //GEN-LAST:event_formKeyTyped

    /**
     * Método para quando o rato sai de dentro da label jLFile
     */
    private void jLFileMouseExited(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_jLFileMouseExited
        jLFile.setForeground(new java.awt.Color(255, 255, 255));
    } //GEN-LAST:event_jLFileMouseExited

    /**
     * Método para quando o rato sai de dentro da label jLEdit
     */
    private void jLEditMouseExited(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_jLEditMouseExited
        jLEdit.setForeground(new java.awt.Color(255, 255, 255));
    } //GEN-LAST:event_jLEditMouseExited

    /**
     * Método para quando o rato sai de dentro da label jLHelp
     */
    private void jLHelpMouseExited(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_jLHelpMouseExited
        jLHelp.setForeground(new java.awt.Color(255, 255, 255));
    } //GEN-LAST:event_jLHelpMouseExited

    /**
     * Método para fechar a janela jDialogFinitElement
     */
    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButtonCancelActionPerformed
        jDialogFiniteElement.setVisible(false);
    } //GEN-LAST:event_jButtonCancelActionPerformed

    /**
     * Método para gravar as alterações ao fechar a janela jDialogFinitElement
     */
    private void jButtonApplyActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButtonApplyActionPerformed
        jDialogFiniteElement.setVisible(false);

        //Instruções para atualizar as figuras geométricas selecionadas
        int index = jComboBoxSection.getSelectedIndex();

        int size = sections.size();
        String[] model = new String[size + 1];
        for (int i = 0; i < size; i++) {
            model[i] = sections.get(i).name;
        }
        model[size] = "";

        String section = model[index];
        jDrawingPanel.updateSections(section);
    } //GEN-LAST:event_jButtonApplyActionPerformed

    /**
     * Método para controlar o posicionamento dos objetos dentro do jScrollPane1
     */
    private void adjustmentValueChanged(AdjustmentEvent evt) {
        if (scrollMouseListener && jRadioButtonLegends.isSelected()) {
            addLegendsForDrawingPanel();
        }
        if (jRadioButtonLegends.isSelected()) {
            addLegendsForResults(resultsPane.typeOfResults);
        }
    }

    /**
     * Método para quando o scroll é pressionado pelo rato
     */
    private void scrollMousePressed(MouseEvent evt) {
        scrollMouseListener = false;
    }

    /**
     * Método para quando o scroll deixa de estar pressionado pelo rato
     */
    private void scrollMouseReleased(MouseEvent evt) {
        scrollMouseListener = true;
        if (jDrawingPanel.isShowing() && jRadioButtonLegends.isSelected()) {
            addLegendsForDrawingPanel();
        }
        if (resultsPane.isShowing()) {
            addLegendsForResults(resultsPane.typeOfResults);
        }
    }

    /**
     * Método para definir o estado dos menus ativados e desativados
     *
     * @param select é o número do menu selecionado
     * @param activateMenu é o menu a ser ativado
     */
    private void activateMenu(int select, javax.swing.JPanel activatedMenu) {
        /*
         * Numbering assigned to menus
         *
         * 1 - Menu Draw
         * 2 - Menu View
         * 3 - Menu Geometry
         * 4 - Menu Loads
         * 5 - Menu Analysis
         * 6 - Menu Results
         */

        if (select != 1) {
            menuDraw.setForeground(new java.awt.Color(255, 255, 255));
            menuDraw.setOpaque(false);
        }

        if (select != 2) {
            menuView.setForeground(new java.awt.Color(255, 255, 255));
            menuView.setOpaque(false);
        }

        if (select != 3) {
            menuGeometry.setForeground(new java.awt.Color(255, 255, 255));
            menuGeometry.setOpaque(false);
        }

        if (select != 4) {
            menuLoads.setForeground(new java.awt.Color(255, 255, 255));
            menuLoads.setOpaque(false);
        }

        if (select != 5) {
            menuAnalysis.setForeground(new java.awt.Color(255, 255, 255));
            menuAnalysis.setOpaque(false);
        }

        if (select != 6) {
            menuResults.setForeground(new java.awt.Color(255, 255, 255));
            menuResults.setOpaque(false);
        }

        //Ativação do conteúdo associado ao menu selecionado
        jPanel5.removeAll();
        jPanel5.setLayout(new BorderLayout());
        jPanel5.add(activatedMenu);
        jPanel5.setVisible(true);
        jPanel5.validate();
        jPanel5.updateUI();

        //Chamada do método para requerer o focus para a janela
        requestFocus();
    }

    /**
     * Método para ativar o menu Draw e desativar os outros menus
     */
    private void activateMenuDraw() {
        menuDraw.setBackground(new java.awt.Color(236, 236, 236));
        menuDraw.setForeground(new java.awt.Color(0, 0, 0));
        menuDraw.setOpaque(true);

        activateMenu(1, jPanelDraw);
    }

    /**
     * Método para ativar o menu View e desativar os outros menus
     */
    private void activateMenuView() {
        menuView.setBackground(new java.awt.Color(236, 236, 236));
        menuView.setForeground(new java.awt.Color(0, 0, 0));
        menuView.setOpaque(true);

        activateMenu(2, jPanelView);
    }

    /**
     * Método para ativar o menu Geometry e desativar os outros menus
     */
    private void activateMenuGeometry() {
        menuGeometry.setBackground(new java.awt.Color(236, 236, 236));
        menuGeometry.setForeground(new java.awt.Color(0, 0, 0));
        menuGeometry.setOpaque(true);

        activateMenu(3, jPanelGeometry);
    }

    /**
     * Método para ativar o menu Loads e desativar os outros menus
     */
    private void activateMenuLoads() {
        menuLoads.setBackground(new java.awt.Color(236, 236, 236));
        menuLoads.setForeground(new java.awt.Color(0, 0, 0));
        menuLoads.setOpaque(true);

        activateMenu(4, jPanelLoads);
    }

    /**
     * Método para ativar o menu Analysis e desativar os outros menus
     */
    private void activateMenuAnalysis() {
        menuAnalysis.setBackground(new java.awt.Color(236, 236, 236));
        menuAnalysis.setForeground(new java.awt.Color(0, 0, 0));
        menuAnalysis.setOpaque(true);

        activateMenu(5, jPanelAnalysis);
    }

    /**
     * Método para ativar o menu Results e desativar os outros menus
     */
    private void activateMenuResults() {
        menuResults.setBackground(new java.awt.Color(236, 236, 236));
        menuResults.setForeground(new java.awt.Color(0, 0, 0));
        menuResults.setOpaque(true);

        activateMenu(6, jPanelResults);
    }

    //Declaração dos JPopupMenus a utilizar na Interface
    private JPopupMenu popupFile;
    private JPopupMenu popupEdit;
    private JPopupMenu popupHelp;

    private JMenu menuEditPreferences;
    private final JRadioButtonMenuItem menuGridPoints25px = new JRadioButtonMenuItem("Grid Points (25px * 25px)");
    private final JRadioButtonMenuItem menuGridPoints50px = new JRadioButtonMenuItem("Grid Points (50px * 50px)");
    private final JRadioButtonMenuItem menuScale100 = new JRadioButtonMenuItem("Scale (100px : 1m)");
    private final JRadioButtonMenuItem menuScale200 = new JRadioButtonMenuItem("Scale (200px : 1m)");

    private JMenuItem menuFileNew, menuFileOpen, menuFileClose, menuFileSave, menuFileExit;
    private JMenuItem menuEditUndo, menuEditRedo, menuEditCut, menuEditCopy, menuEditPaste, menuEditSelectAll, menuEditDeleteAll;
    private JMenuItem menuHelpHelp, menuHelpAbout;

    /**
     * Método para inicializar os JPopupMenus da Interface
     */
    private void initializePopupMenus() {
        //personalização dos efritos dos menus File, Edit e Help
        jLFile.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(52, 52, 52)));
        jLEdit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(52, 52, 52)));
        jLHelp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(52, 52, 52)));

        //Criação dos JMenuItem para adicionar aos JPopupMenus

        menuFileNew = new JMenuItem("New Project");
        menuFileOpen = new JMenuItem("Open Project");
        menuFileClose = new JMenuItem("Close Project");
        menuFileSave = new JMenuItem("Save Project");
        menuFileExit = new JMenuItem("Exit");

        menuEditUndo = new JMenuItem("Undo", new ImageIcon(getClass().getResource("/images/edit_undo.png")));
        menuEditRedo = new JMenuItem("Redo", new ImageIcon(getClass().getResource("/images/edit_redo.png")));
        menuEditCut = new JMenuItem("Cut", new ImageIcon(getClass().getResource("/images/edit_cut.png")));
        menuEditCopy = new JMenuItem("Copy", new ImageIcon(getClass().getResource("/images/edit_copy.png")));
        menuEditPaste = new JMenuItem("Paste", new ImageIcon(getClass().getResource("/images/edit_paste.png")));
        menuEditSelectAll = new JMenuItem("Select All");
        menuEditDeleteAll = new JMenuItem("Delete All");
        menuEditPreferences = new JMenu("Preferences");

        menuEditPreferences.add(menuGridPoints25px);
        menuEditPreferences.add(menuGridPoints50px);
        menuEditPreferences.add(menuScale100);
        menuEditPreferences.add(menuScale200);
        menuGridPoints25px.setSelected(true);
        menuScale100.setSelected(true);

        menuHelpHelp = new JMenuItem("Help");
        menuHelpAbout = new JMenuItem("About");

        //Gestão dos menus ativados e desativados
        menuFileNew.setEnabled(false);
        menuFileOpen.setEnabled(true);
        menuFileClose.setEnabled(false);
        menuFileSave.setEnabled(false);
        menuFileExit.setEnabled(true);
        menuEditUndo.setEnabled(false);
        menuEditRedo.setEnabled(false);
        menuEditCut.setEnabled(false);
        menuEditCopy.setEnabled(false);
        menuEditPaste.setEnabled(false);
        menuEditSelectAll.setEnabled(false);
        menuEditDeleteAll.setEnabled(false);
        menuEditPreferences.setEnabled(false);
        menuHelpHelp.setEnabled(true);
        menuHelpAbout.setEnabled(true);

        //Adição das teclas de atalho aos JMenuItens

        menuFileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        menuFileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        menuFileClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        menuFileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

        menuEditUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        menuEditRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        menuEditCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        menuEditCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        menuEditPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));

        menuHelpHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));

        //Declaração dos eventos a adicionar aos JMenuItens

        menuFileNew.addActionListener(this::jMenuItemFileNew);
        menuFileOpen.addActionListener(this::jMenuItemFileOpen);
        menuFileClose.addActionListener(this::jMenuItemFileClose);
        menuFileSave.addActionListener(this::jMenuItemFileSave);
        menuFileExit.addActionListener(this::jMenuItemFileExit);

        menuEditUndo.addActionListener(this::jMenuItemEditUndo);
        menuEditRedo.addActionListener(this::jMenuItemEditRedo);
        menuEditCut.addActionListener(this::jMenuItemEditCut);
        menuEditCopy.addActionListener(this::jMenuItemEditCopy);
        menuEditPaste.addActionListener(this::jMenuItemEditPaste);
        menuEditSelectAll.addActionListener(this::jMenuItemEditSelectAll);
        menuEditDeleteAll.addActionListener(this::jMenuItemEditDeleteAll);

        menuGridPoints25px.addActionListener(this::menuGridPoints25px);
        menuGridPoints50px.addActionListener(this::menuGridPoints50px);
        menuScale100.addActionListener(this::menuScale100);
        menuScale200.addActionListener(this::menuScale200);

        menuHelpHelp.addActionListener(this::jMenuItemHelpHelp);
        menuHelpAbout.addActionListener(this::jMenuItemHelpAbout);

        //Inicialização do JPopupMenu popupFile
        popupFile = new JPopupMenu();
        popupFile.addPopupMenuListener(
            new PopupMenuListener() {
                @Override
                public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                    //TODO add your handling code here
                }

                @Override
                public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                    jLFile.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(52, 52, 52)));
                }

                @Override
                public void popupMenuCanceled(PopupMenuEvent e) {
                    //TODO add your handling code here
                }
            }
        );
        popupFile.add(menuFileNew);
        popupFile.add(menuFileOpen);
        popupFile.add(menuFileClose);
        popupFile.add(menuFileSave);
        popupFile.addSeparator();
        popupFile.add(menuFileExit);

        //Inicialização do JPopupMenu popupEdit
        popupEdit = new JPopupMenu();
        popupEdit.addPopupMenuListener(
            new PopupMenuListener() {
                @Override
                public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                    //TODO add your handling code here
                }

                @Override
                public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                    jLEdit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(52, 52, 52)));
                }

                @Override
                public void popupMenuCanceled(PopupMenuEvent e) {
                    //TODO add your handling code here
                }
            }
        );
        popupEdit.add(menuEditUndo);
        popupEdit.add(menuEditRedo);
        popupEdit.addSeparator();
        popupEdit.add(menuEditCut);
        popupEdit.add(menuEditCopy);
        popupEdit.add(menuEditPaste);
        popupEdit.add(menuEditSelectAll);
        popupEdit.add(menuEditDeleteAll);
        popupEdit.addSeparator();
        popupEdit.add(menuEditPreferences);

        //Inicialização do JPopupMenu popupHelp
        popupHelp = new JPopupMenu();
        popupHelp.addPopupMenuListener(
            new PopupMenuListener() {
                @Override
                public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                    //TODO add your handling code here
                }

                @Override
                public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                    jLHelp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(52, 52, 52)));
                }

                @Override
                public void popupMenuCanceled(PopupMenuEvent e) {
                    //TODO add your handling code here
                }
            }
        );
        popupHelp.add(menuHelpHelp);
        popupHelp.add(menuHelpAbout);
    }

    /**
     * Método para gerir os menus ativados e desativados
     */
    private void menuItens() {
        menuFileNew.setEnabled(false);
        menuFileOpen.setEnabled(true);
        menuFileClose.setEnabled(false);
        menuFileSave.setEnabled(false);
        menuFileExit.setEnabled(true);
        menuEditUndo.setEnabled(false);
        menuEditRedo.setEnabled(false);
        menuEditCut.setEnabled(false);
        menuEditCopy.setEnabled(false);
        menuEditPaste.setEnabled(false);
        menuEditSelectAll.setEnabled(false);
        menuEditDeleteAll.setEnabled(false);
        menuEditPreferences.setEnabled(false);
        menuHelpHelp.setEnabled(true);
        menuHelpAbout.setEnabled(true);
    }

    /**
     * Método do evento de click do menu jMenuItemFileNew
     */
    private void jMenuItemFileNew(ActionEvent evt) {
        if (!jPWelcome.isShowing()) {
            Toolkit.getDefaultToolkit().beep();
            int events = JOptionPane.showConfirmDialog(
                null,
                "Want to save the current project?",
                "FEM for Students",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (events == JOptionPane.YES_OPTION) {
                jMenuItemFileSave(evt);
            }

            if (events != JOptionPane.CANCEL_OPTION && events != JOptionPane.CLOSED_OPTION) {
                setTitle("FEM for Students");
                jScrollPane1.setViewportView(jPWelcome);
                jButtonClose_jLateralPanel(null);
                activateMenuDraw();
                closeMenuContainer();
                menuItens();

                //Instruções para eliminar informação do problema anterior
                jDrawingPanel = new DrawingPanel();
                resultsPane = new ResultsPane();
                results = new Processor();

                //Instrunções para remover a informação do painel inferior
                addHelpJPBottom();
            }
        }
    }

    /**
     * Método do evento de click do menu jMenuItemFileOpen
     */
    private void jMenuItemFileOpen(ActionEvent evt) {
        boolean open = true;

        if (!jPWelcome.isShowing()) {
            Toolkit.getDefaultToolkit().beep();
            int events = JOptionPane.showConfirmDialog(
                null,
                "Want to save the current project?",
                "FEM for Students",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (events == JOptionPane.YES_OPTION) {
                jMenuItemFileSave(evt);
            }
            if (events != JOptionPane.YES_OPTION && events != JOptionPane.NO_OPTION) {
                open = false;
            }
        }

        if (open) {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileFilter(new FileNameExtensionFilter("FEM for Students", "fem"));

            int events = jFileChooser.showOpenDialog(this);
            if (events == JFileChooser.APPROVE_OPTION) {
                try {
                    //Instruções para recuperar a informação guardada
                    String absolutePath = jFileChooser.getSelectedFile().getAbsolutePath();
                    OpenSave openProject = new OpenSave(absolutePath);

                    if (openProject.allObjectsWereOpened()) {
                        //Instruções para eliminar informação do problema anterior
                        jDrawingPanel = new DrawingPanel();
                        resultsPane = new ResultsPane();
                        results = new Processor();

                        type = openProject.getTypeOfProject();
                        switch (this.type) {
                            case "1D":
                                panelWelcome_jLPlaneTrusses(null);
                                break;
                            case "2D":
                                panelWelcome_jLPlaneStressesStrains(null);
                                break;
                            case "Beams":
                                panelWelcome_jLBeams(null);
                                break;
                            case "Frames":
                                panelWelcome_jLFrames(null);
                                break;
                            case "Grids":
                                panelWelcome_jLGrids(null);
                                break;
                            case "Slabs":
                                panelWelcome_jLSlabs(null);
                                break;
                        }

                        jDrawingPanel.setAllObjects(
                            openProject.getArrayListNodes(),
                            openProject.getArrayListLines(),
                            openProject.getArrayListPolygons(),
                            openProject.getArrayListSupports(),
                            openProject.getArrayListElasticSupports(),
                            openProject.getArrayListSettlements(),
                            openProject.getArrayListLoads(),
                            openProject.getArrayListDimensionLines()
                        );

                        geometryCounter = openProject.getSectionCounter();
                        loadCounter = openProject.getLoadCounter();
                        sections = openProject.getSections();
                        materials = openProject.getMaterials();
                        elasticSupports = openProject.getElasticSupports();
                        settlements = openProject.getSettlements();
                        concentratedLoads = openProject.getConcentratedLoads();
                        bendingMoments = openProject.getBendingMoments();
                        distributedLoads = openProject.getDistributedLoads();
                        axialLoads = openProject.getAxialLoads();
                        planarLoads = openProject.getPlanarLoads();
                        thermalLoads = openProject.getThermalLoads();
                        selfWeights = openProject.getSelfWeight();

                        geometryUpdateAllModels();
                        loadsUpdateAllModels();
                    }
                } catch (Exception e) {
                    setTitle("FEM for Students");
                    jScrollPane1.setViewportView(jPWelcome);
                    jButtonClose_jLateralPanel(null);
                    activateMenuDraw();
                    closeMenuContainer();
                    menuItens();

                    //Instruções para eliminar informação do problema anterior
                    jDrawingPanel = new DrawingPanel();
                    resultsPane = new ResultsPane();
                    results = new Processor();

                    //Instrunções para remover a informação do painel inferior
                    addHelpJPBottom();
                }
            }
        }
    }

    /**
     * Método do evento de click do menu jMenuItemFileClose
     */
    private void jMenuItemFileClose(ActionEvent evt) {
        if (!jPWelcome.isShowing()) {
            Toolkit.getDefaultToolkit().beep();
            int events = JOptionPane.showConfirmDialog(
                null,
                "Want to save the current project?",
                "FEM for Students",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (events == JOptionPane.YES_OPTION) {
                jMenuItemFileSave(evt);
            }

            if (events != JOptionPane.CANCEL_OPTION && events != JOptionPane.CLOSED_OPTION) {
                setTitle("FEM for Students");
                jScrollPane1.setViewportView(jPWelcome);
                jButtonClose_jLateralPanel(null);
                activateMenuDraw();
                closeMenuContainer();
                menuItens();

                //Instruções para eliminar informação do problema anterior
                jDrawingPanel = new DrawingPanel();
                resultsPane = new ResultsPane();
                results = new Processor();

                //Instrunções para remover a informação do painel inferior
                addHelpJPBottom();
            }
        }
    }

    /**
     * Método do evento de click do menu jMenuItemFileSave
     */
    private void jMenuItemFileSave(ActionEvent evt) {
        if (!jPWelcome.isShowing()) {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileFilter(new FileNameExtensionFilter("FEM for Students", "fem"));

            int events = jFileChooser.showSaveDialog(this);
            if (events == JFileChooser.APPROVE_OPTION) {
                try {
                    String absolutePath = jFileChooser.getSelectedFile().getAbsolutePath();
                    absolutePath = absolutePath.replace(".fem", "");
                    absolutePath = absolutePath + ".fem";
                    OpenSave saveProject = new OpenSave(
                        type,
                        geometryCounter,
                        loadCounter,
                        sections,
                        materials,
                        elasticSupports,
                        settlements,
                        jDrawingPanel.arrayListNodes,
                        jDrawingPanel.arrayListLines,
                        jDrawingPanel.arrayListPolygons,
                        jDrawingPanel.arrayListSupports,
                        jDrawingPanel.arrayListElasticSupports,
                        jDrawingPanel.arrayListSettlements,
                        jDrawingPanel.arrayListLoads,
                        jDrawingPanel.arrayListDimensionLines,
                        concentratedLoads,
                        bendingMoments,
                        distributedLoads,
                        axialLoads,
                        planarLoads,
                        thermalLoads,
                        selfWeights,
                        absolutePath
                    );
                } catch (Exception e) {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "Error! Unable to save the project.");
                }
            }
        }
    }

    /**
     * Método do evento de click do menu jMenuItemFileExit
     */
    private void jMenuItemFileExit(ActionEvent evt) {
        System.exit(0);
    }

    /**
     * Método do evento de click do menu jMenuItemEditUndo
     */
    private void jMenuItemEditUndo(ActionEvent evt) {
        if (!jPWelcome.isShowing()) {
            panelDraw_jLabelUndo(null);
        }
    }

    /**
     * Método do evento de click do menu jMenuItemEditRedo
     */
    private void jMenuItemEditRedo(ActionEvent evt) {
        if (!jPWelcome.isShowing()) {
            panelDraw_jLabelRedo(null);
        }
    }

    /**
     * Método do evento de click do menu jMenuItemEditCut
     */
    private void jMenuItemEditSelect(ActionEvent evt) {
        if (!jPWelcome.isShowing()) {
            LabelMouseEvents label = (LabelMouseEvents) jLabelSelect;
            label.setSelectedAndMousePressed(false, true);
            label.jLabelMouseClicked(null);
            panelDraw_jLabelSelect(null);
        }
    }

    /**
     * Método do evento de click do menu jMenuItemEditCut
     */
    private void jMenuItemEditCut(ActionEvent evt) {
        if (!jPWelcome.isShowing()) {
            panelDraw_jLabelCut(null);
        }
    }

    /**
     * Método do evento de click do menu jMenuItemEditCopy
     */
    private void jMenuItemEditCopy(ActionEvent evt) {
        if (!jPWelcome.isShowing()) {
            panelDraw_jLabelCopy(null);
        }
    }

    /**
     * Método do evento de click do menu jMenuItemEditPaste
     */
    private void jMenuItemEditPaste(ActionEvent evt) {
        if (!jPWelcome.isShowing()) {
            panelDraw_jLabelPaste(null);
        }
    }

    /**
     * Método do evento de click do menu jMenuItemEditSelectAll
     */
    private void jMenuItemEditSelectAll(ActionEvent evt) {
        if (!jPWelcome.isShowing()) {
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            jDrawingPanel.selectAllObjects();
        }
    }

    /**
     * Método do evento de click do menu jMenuItemEditDeleteAll
     */
    private void jMenuItemEditDeleteAll(ActionEvent evt) {
        if (!jPWelcome.isShowing()) {
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            jDrawingPanel.deleteAllObjects();
        }
    }

    /**
     * Método do evento de click do menu jMenuPreferencesA
     */
    private void menuGridPoints25px(ActionEvent evt) {
        menuGridPoints25px.setSelected(true);
        menuGridPoints50px.setSelected(false);
        jDrawingPanel.gridPoints(25);
        resultsPane.gridPoints(25);
    }

    /**
     * Método do evento de click do menu jMenuPreferencesB
     */
    private void menuGridPoints50px(ActionEvent evt) {
        menuGridPoints25px.setSelected(false);
        menuGridPoints50px.setSelected(true);
        jDrawingPanel.gridPoints(50);
        resultsPane.gridPoints(50);
    }

    /**
     * Método do evento de click do menu jMenuPreferencesC
     */
    private void menuScale100(ActionEvent evt) {
        menuScale100.setSelected(true);
        menuScale200.setSelected(false);
        factor = 100;
        jDrawingPanel.changeFactor(100.0);
    }

    /**
     * Método do evento de click do menu jMenuPreferencesD
     */
    private void menuScale200(ActionEvent evt) {
        menuScale100.setSelected(false);
        menuScale200.setSelected(true);
        factor = 200;
        jDrawingPanel.changeFactor(200.0);
    }

    /**
     * Método do evento de click do menu jMenuItemHelpAbout
     */
    private void jMenuItemHelpHelp(ActionEvent evt) {
        PanelHelp help = new PanelHelp();
        jScrollPaneHelp.setViewportView(help);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        jFrameHelp.setBounds(screen.width - 550 - 50, 50, 0, 0);
        jFrameHelp.setSize(new Dimension(550, 650));
        jFrameHelp.setVisible(true);
    }

    /**
     * Método do evento de click do menu jMenuItemHelpHelp
     */
    private void jMenuItemHelpAbout(ActionEvent evt) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        jDialogAbout.setBounds((screen.width / 2) - (400 / 2), (screen.height / 2) - (500 / 2), 0, 0);
        jDialogAbout.setVisible(true);
    }

    /**
     * Método para criar o menu File na Interface
     */
    private void jPopupButtonFile(MouseEvent evt) {
        popupFile.show((Component) evt.getSource(), 0, 20);
    }

    /**
     * Método para criar o menu Edit na Interface
     */
    private void jPopupButtonEdit(MouseEvent evt) {
        popupEdit.show((Component) evt.getSource(), 0, 20);
    }

    /**
     * Método para criar o menu Help na Interface
     */
    private void jPopupButtonHelp(MouseEvent evt) {
        popupHelp.show((Component) evt.getSource(), 0, 20);
    }

    /**
     * Adicionar evento mousePressed às labels do panelDraw
     *
     * jLabelPoint
     * jLabelLine
     * jLabelTriangle
     * jLabelRectangle
     * jLabelQuadrilateral
     * jLabelUndo
     * jLabelRedo
     * jLabelMove
     * jLabelSelect
     * jLabelCut
     * jLabelCopy
     * jLabelPaste
     */
    private void menuDrawMouseEvents() {
        jLabelPoint.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelDraw_jLabelPoint(evt);
                }
            }
        );

        jLabelLine.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelDraw_jLabelLine(evt);
                }
            }
        );

        jLabelTriangle.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelDraw_jLabelTriangle(evt);
                }
            }
        );

        jLabelRectangle.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelDraw_jLabelRectangle(evt);
                }
            }
        );

        jLabelQuadrilateral.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelDraw_jLabelQuadrilateral(evt);
                }
            }
        );

        jLabelUndo.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelDraw_jLabelUndo(evt);
                }
            }
        );

        jLabelRedo.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelDraw_jLabelRedo(evt);
                }
            }
        );

        jLabelMove.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelDraw_jLabelMove(evt);
                }
            }
        );

        jLabelSelect.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelDraw_jLabelSelect(evt);
                }
            }
        );

        jLabelCut.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelDraw_jLabelCut(evt);
                }
            }
        );

        jLabelCopy.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelDraw_jLabelCopy(evt);
                }
            }
        );

        jLabelPaste.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelDraw_jLabelPaste(evt);
                }
            }
        );
    }

    /**
     * Adicionar evento mousePressed às labels e botões do panelView
     *
     * jLabelPan
     * jLabelZoomIn
     * jLabelZoomOut
     * jRadioButtonGrid
     * jRadioButtonSnap
     * jRadioButtonLegends
     */
    private void menuViewMouseEvents() {
        jLabelPan.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelView_jLabelPan(evt);
                }
            }
        );

        jLabelZoomIn.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelView_jLabelZoomIn(evt);
                }
            }
        );

        jLabelZoomOut.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelView_jLabelZoomOut(evt);
                }
            }
        );

        jRadioButtonGrid.addActionListener(this::jRadioButtonGridActionPerformed);

        jRadioButtonSnap.addActionListener(this::jRadioButtonSnapActionPerformed);

        jRadioButtonLegends.addActionListener(this::jRadioButtonLegendsActionPerformed);
    }

    /**
     * Adicionar evento mousePressed às labels do panelGeometry
     *
     * jLabelNodes
     * jLabelNumberOfNode
     * jLabelSections
     * jLabelMaterials
     * jLabelMesh
     * jLabelDimensionLine
     * jLabelVSimpleSupport
     * jLabelHSimpleSupport
     * jLabeljLabelPinnedSupport
     * jLabelHSliderSupport
     * jLabelVSliderSupport
     * jLabeljLabelFixedSupport
     * jLabelElasticSupport
     * jLabelSettlements
     * jLabelCutSupports
     */
    private void menuGeometryMouseEvents() {
        jLabelNodes.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelGeometry_jLabelNodes(evt);
                }
            }
        );

        jLabelNumberOfNodes.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelGeometry_jLabelNumberOfNodes(evt);
                }
            }
        );

        jLabelSections.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelGeometry_jLabelSections(evt);
                }
            }
        );

        jLabelMaterials.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelGeometry_jLabelMaterials(evt);
                }
            }
        );

        jLabelMesh.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelGeometry_jLabelMesh(evt);
                }
            }
        );

        jLabelDimensionLine.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelGeometry_jLabelDimensionLine(evt);
                }
            }
        );

        jLabelVSimpleSupport.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelGeometry_jLabelVSimpleSupport(evt);
                }
            }
        );

        jLabelHSimpleSupport.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelGeometry_jLabelHSimpleSupport(evt);
                }
            }
        );

        jLabelPinnedSupport.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelGeometry_jLabelPinnedSupport(evt);
                }
            }
        );

        jLabelHSliderSupport.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelGeometry_jLabelHSliderSupport(evt);
                }
            }
        );

        jLabelVSliderSupport.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelGeometry_jLabelVSliderSupport(evt);
                }
            }
        );

        jLabelFixedSupport.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelGeometry_jLabelFixedSupport(evt);
                }
            }
        );

        jLabelElasticSupport.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelGeometry_jLabelElasticSupport(evt);
                }
            }
        );

        jLabelSettlements.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelGeometry_jLabelSettlementSupport(evt);
                }
            }
        );

        jLabelCutSupports.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelGeometry_jLabelDeleteSupports(evt);
                }
            }
        );
    }

    /**
     * Adicionar evento mousePressed às labels do panelLoads
     *
     * jLabelConcentratedLoad
     * jLabelBendingMoment
     * jLabelUnifDistLoad
     * jLabelDistAxialLoad
     * jLabelUnifPlanarLoad
     * jLabelSelfWeight
     * jLabelDelete
     * jLabelLoadTable
     */
    private void menuLoadsMouseEvents() {
        jLabelConcentratedLoad.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelLoads_jLabelConcentratedLoad(evt);
                }
            }
        );

        jLabelBendingMoment.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelLoads_jLabelBendingMoment(evt);
                }
            }
        );

        jLabelUnifDistLoad.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelLoads_jLabelUnifDistLoad(evt);
                }
            }
        );

        jLabelDistAxialLoad.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelLoads_jLabelDistAxialLoad(evt);
                }
            }
        );

        jLabelUnifPlanarLoad.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelLoads_jLabelUnifPlanarLoad(evt);
                }
            }
        );

        jLabelThermalLoad.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelLoads_jLabelThermalLoad(evt);
                }
            }
        );

        jLabelSelfWeight.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelLoads_jLabelSelfWeight(evt);
                }
            }
        );

        jLabelCutLoad.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelLoads_jLabelDelete(evt);
                }
            }
        );

        jLabelLoadTable.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelLoads_jLabelLoadTable(evt);
                }
            }
        );
    }

    /**
     * Adicionar evento mousePressed às labels do panelAnalysis
     *
     * jLabelTheory
     * jRadioButtonAnalytical
     * jRadioButtonNumerical
     * jLabelCalculate
     */
    private void menuAnalysisMouseEvents() {
        jLabelTheory.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelAnalysis_jLabelTheory(evt);
                }
            }
        );

        jRadioButtonAnalytical.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelAnalysis_jRadioButtonAnalytical(evt);
                }
            }
        );

        jRadioButtonNumerical.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelAnalysis_jRadioButtonNumerical(evt);
                }
            }
        );

        jLabelCalculate.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelAnalysis_jLabelCalculate(evt);
                }
            }
        );

        //Ativação por padrão do jRadioButtonAnalytical
        jRadioButtonAnalytical.setSelected(true);
    }

    /**
     * Adicionar evento mousePressed às labels do panelResults
     *
     * jLabelVectorF
     * jLabelMatrixK
     * jLabelVectorDisplacements
     * jLabelProperties
     * jLabelSupportReactions
     * jLabelNodalForces
     * jLabelNodalStresses
     * jLabelDisplacements
     * jLabelDiagrams
     * jLabelIsovalues
     * jLabelMaps
     * jLabelPrincipalDirections
     */
    private void menuResultsMouseEvents() {
        jLabelVectorF.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelResults_jLabelVectorF(evt);
                }
            }
        );

        jLabelMatrixK.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelResults_jLabelMatrixK(evt);
                }
            }
        );

        jLabelVectorDisplacements.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelResults_jLabelVectorDisplacements(evt);
                }
            }
        );

        jLabelProperties.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelResults_jLabelProperties(evt);
                }
            }
        );

        jLabelSupportReactions.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelResults_jLabelSupportReactions(evt);
                }
            }
        );

        jLabelNodalForces.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelResults_jLabelNodalForces(evt);
                }
            }
        );

        jLabelNodalStresses.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelResults_jLabelNodalStresses(evt);
                }
            }
        );

        jLabelDisplacements.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelResults_jLabelDisplacements(evt);
                }
            }
        );

        jLabelDiagrams.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelResults_jLabelDiagrams(evt);
                }
            }
        );

        jLabelIsovalues.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelResults_jLabelIsovalues(evt);
                }
            }
        );

        jLabelMaps.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelResults_jLabelMaps(evt);
                }
            }
        );

        jLabelPrincipalStresses.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelResults_jLabelPrincipalStresses(evt);
                }
            }
        );
    }

    /*
     * Métodos associados aos eventos do rato no panelDraw
     */

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelDraw_jLabelPoint(MouseEvent evt) {
        if (jLabelPoint.isEnabled()) {
            resetLabelsNumeration();
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }

            if ("Beams".equals(type)) {
                jLabelDP_Y.setText("Z =");
            } else {
                jLabelDP_Y.setText("Y =");
            }

            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            jDialogPoint.setBounds((screen.width / 2) - (350 / 2), (screen.height / 2) - (140 / 2), 0, 0);
            jDialogPoint.setVisible(true);
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelDraw_jLabelLine(MouseEvent evt) {
        if (jLabelLine.isEnabled()) {
            resetLabelsNumeration((LabelMouseEvents) jLabelLine);
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            panelDraw_LabelSelected = labelsNumeration.jPDraw_Line;
            jP_Bottom.jLabelInformation.setText(informations.jPDraw_Line);
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelDraw_jLabelTriangle(MouseEvent evt) {
        if (jLabelTriangle.isEnabled()) {
            resetLabelsNumeration((LabelMouseEvents) jLabelTriangle);
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            panelDraw_LabelSelected = labelsNumeration.jPDraw_Triangle;
            jP_Bottom.jLabelInformation.setText(informations.jPDraw_Triangle);
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelDraw_jLabelRectangle(MouseEvent evt) {
        if (jLabelRectangle.isEnabled()) {
            resetLabelsNumeration((LabelMouseEvents) jLabelRectangle);
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            panelDraw_LabelSelected = labelsNumeration.jPDraw_Rectangle;
            jP_Bottom.jLabelInformation.setText(informations.jPDraw_Rectangle);
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelDraw_jLabelQuadrilateral(MouseEvent evt) {
        if (jLabelQuadrilateral.isEnabled()) {
            resetLabelsNumeration((LabelMouseEvents) jLabelQuadrilateral);
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            panelDraw_LabelSelected = labelsNumeration.jPDraw_Quadrilateral;
            jP_Bottom.jLabelInformation.setText(informations.jPDraw_Quadrilateral);
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelDraw_jLabelUndo(MouseEvent evt) {
        if (jLabelUndo.isEnabled()) {
            resetLabelsNumeration();
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            panelDraw_LabelSelected = labelsNumeration.jPDraw_Undo;
            jDrawingPanel.commandUndo();
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelDraw_jLabelRedo(MouseEvent evt) {
        if (jLabelRedo.isEnabled()) {
            resetLabelsNumeration();
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            panelDraw_LabelSelected = labelsNumeration.jPDraw_Redo;
            jDrawingPanel.commandRedo();
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelDraw_jLabelMove(MouseEvent evt) {
        if (jLabelMove.isEnabled()) {
            panelDraw_LabelSelected = labelsNumeration.jPDraw_Move;
            resetLabelsNumeration("move");
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }

            if (moveCursor == false) {
                setSelectedForLabels((LabelMouseEvents) jLabelMove);
                panelDraw_LabelSelected = labelsNumeration.jPDraw_Move;
                jDrawingPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
                moveCursor = true;
                handCursor = false;
            } else {
                jDrawingPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                moveCursor = false;
                handCursor = false;
            }
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelDraw_jLabelSelect(MouseEvent evt) {
        if (jLabelSelect.isEnabled()) {
            resetLabelsNumeration((LabelMouseEvents) jLabelSelect);
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            panelDraw_LabelSelected = labelsNumeration.jPDraw_Select;
            jP_Bottom.jLabelInformation.setText(informations.jPDraw_Select);
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelDraw_jLabelCut(MouseEvent evt) {
        if (jLabelCut.isEnabled()) {
            resetLabelsNumeration();
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            panelDraw_LabelSelected = labelsNumeration.jPDraw_Cut;
            jDrawingPanel.cutObject();
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelDraw_jLabelCopy(MouseEvent evt) {
        if (jLabelCopy.isEnabled()) {
            resetLabelsNumeration();
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            panelDraw_LabelSelected = labelsNumeration.jPDraw_Copy;
            jDrawingPanel.copyObject();
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelDraw_jLabelPaste(MouseEvent evt) {
        if (jLabelPaste.isEnabled()) {
            resetLabelsNumeration();
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            panelDraw_LabelSelected = labelsNumeration.jPDraw_Paste;
            jDrawingPanel.pasteObject();
        }
    }

    /*
     * Métodos associados aos eventos do rato no panelView
     */

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelView_jLabelPan(MouseEvent evt) {
        panelView_LabelSelected = labelsNumeration.jPanelView_Pan;
        resetLabelsNumeration("pan");
        if (!jDrawingPanel.isShowing() && !resultsPane.isShowing()) {
            jScrollPane1.setViewportView(jDrawingPanel);
        }

        if (handCursor == false) {
            setSelectedForLabels((LabelMouseEvents) jLabelPan);
            panelView_LabelSelected = labelsNumeration.jPanelView_Pan;
            jDrawingPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            resultsPane.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            moveCursor = false;
            handCursor = true;
        } else {
            jDrawingPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            resultsPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            moveCursor = false;
            handCursor = false;
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelView_jLabelZoomIn(MouseEvent evt) {
        resetLabelsNumeration();
        panelView_LabelSelected = labelsNumeration.jPanelView_ZoomIn;
        zoom("ZoomIn");
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelView_jLabelZoomOut(MouseEvent evt) {
        resetLabelsNumeration();
        panelView_LabelSelected = labelsNumeration.jPanelView_ZoomOut;
        zoom("ZoomOut");
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void jRadioButtonGridActionPerformed(ActionEvent evt) {
        if (jRadioButtonGrid.isSelected()) {
            jRadioButtonSnap.setEnabled(true);
            jDrawingPanel.drawGrid(true);
            resultsPane.drawGrid(true);
        } else {
            jRadioButtonSnap.setEnabled(false);
            jRadioButtonSnap.setSelected(false);
            jDrawingPanel.drawGrid(false);
            jDrawingPanel.snapSelected(false);
            resultsPane.drawGrid(false);
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void jRadioButtonSnapActionPerformed(ActionEvent evt) {
        if (jRadioButtonSnap.isSelected()) {
            jDrawingPanel.snapSelected(true);
        } else {
            jDrawingPanel.snapSelected(false);
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void jRadioButtonLegendsActionPerformed(ActionEvent evt) {
        if (jRadioButtonLegends.isSelected()) {
            jDrawingPanel.legendsSelected(true);

            addLegendsForDrawingPanel();
            addLegendsForResults(resultsPane.typeOfResults);
        } else {
            jDrawingPanel.legendsSelected(false);

            //Remoção das llegendas dos painéis ao painel
            jDrawingPanel.removeAll();
            jDrawingPanel.validate();
            jDrawingPanel.updateUI();
            resultsPane.removeAll();
            resultsPane.validate();
            resultsPane.updateUI();
        }
    }

    /*
     * Métodos associados aos eventos do rato no panelGeometry
     */

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelGeometry_jLabelNodes(MouseEvent evt) {
        if (jLabelNodes.isEnabled()) {
            resetLabelsNumeration();
            panelGeometry_LabelSelected = labelsNumeration.jPGeometry_Nodes;
            jDrawingPanel.drawNode();
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelGeometry_jLabelNumberOfNodes(MouseEvent evt) {
        if (jLabelNumberOfNodes.isEnabled()) {
            resetLabelsNumeration();
            panelGeometry_LabelSelected = labelsNumeration.jPGeometry_NumberOfNodes;
            openLateralPanel(jLateralPanelGeometry);
            jLateralPanelGeometry.jPanelNodesMousePressed();
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelGeometry_jLabelSections(MouseEvent evt) {
        resetLabelsNumeration();
        panelGeometry_LabelSelected = labelsNumeration.jPGeometry_Sections;
        openLateralPanel(jLateralPanelGeometry);
        jLateralPanelGeometry.jPanelSectionsMousePressed();
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelGeometry_jLabelMaterials(MouseEvent evt) {
        resetLabelsNumeration();
        panelGeometry_LabelSelected = labelsNumeration.jPGeometry_Materials;
        openLateralPanel(jLateralPanelGeometry);
        jLateralPanelGeometry.jPanelMaterialsMousePressed();
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelGeometry_jLabelMesh(MouseEvent evt) {
        if (jLabelMesh.isEnabled()) {
            resetLabelsNumeration();
            panelGeometry_LabelSelected = labelsNumeration.jPGeometry_Mesh;
            openLateralPanel(jLateralPanelGeometry);
            jLateralPanelGeometry.jPanelMeshMousePressed();
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelGeometry_jLabelDimensionLine(MouseEvent evt) {
        if (jLabelDimensionLine.isEnabled()) {
            resetLabelsNumeration((LabelMouseEvents) jLabelDimensionLine);
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            panelGeometry_LabelSelected = labelsNumeration.jPGeometry_DimensionLine;
            jP_Bottom.jLabelInformation.setText("");
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelGeometry_jLabelVSimpleSupport(MouseEvent evt) {
        if (jLabelVSimpleSupport.isEnabled()) {
            resetLabelsNumeration((LabelMouseEvents) jLabelVSimpleSupport);
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            panelGeometry_LabelSelected = labelsNumeration.jPGeometry_VSimpleSupport;
            jP_Bottom.jLabelInformation.setText(informations.jPGeometry_Supports);
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelGeometry_jLabelHSimpleSupport(MouseEvent evt) {
        if (jLabelHSimpleSupport.isEnabled()) {
            resetLabelsNumeration((LabelMouseEvents) jLabelHSimpleSupport);
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            panelGeometry_LabelSelected = labelsNumeration.jPGeometry_HSimpleSupport;
            jP_Bottom.jLabelInformation.setText(informations.jPGeometry_Supports);
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelGeometry_jLabelPinnedSupport(MouseEvent evt) {
        if (jLabelPinnedSupport.isEnabled()) {
            resetLabelsNumeration((LabelMouseEvents) jLabelPinnedSupport);
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            panelGeometry_LabelSelected = labelsNumeration.jPGeometry_PinnedSupport;
            jP_Bottom.jLabelInformation.setText(informations.jPGeometry_Supports);
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelGeometry_jLabelHSliderSupport(MouseEvent evt) {
        if (jLabelHSliderSupport.isEnabled()) {
            resetLabelsNumeration((LabelMouseEvents) jLabelHSliderSupport);
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            panelGeometry_LabelSelected = labelsNumeration.jPGeometry_HSliderSupport;
            jP_Bottom.jLabelInformation.setText(informations.jPGeometry_Supports);
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelGeometry_jLabelVSliderSupport(MouseEvent evt) {
        if (jLabelVSliderSupport.isEnabled()) {
            resetLabelsNumeration((LabelMouseEvents) jLabelVSliderSupport);
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            panelGeometry_LabelSelected = labelsNumeration.jPGeometry_VSliderSupport;
            jP_Bottom.jLabelInformation.setText(informations.jPGeometry_Supports);
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelGeometry_jLabelFixedSupport(MouseEvent evt) {
        if (jLabelFixedSupport.isEnabled()) {
            resetLabelsNumeration((LabelMouseEvents) jLabelFixedSupport);
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            panelGeometry_LabelSelected = labelsNumeration.jPGeometry_FixedSupport;
            jP_Bottom.jLabelInformation.setText(informations.jPGeometry_Supports);
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelGeometry_jLabelElasticSupport(MouseEvent evt) {
        if (jLabelElasticSupport.isEnabled()) {
            resetLabelsNumeration((LabelMouseEvents) jLabelElasticSupport);
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            panelGeometry_LabelSelected = labelsNumeration.jPGeometry_ElasticSupport;
            jP_Bottom.jLabelInformation.setText(informations.jPGeometry_ElasticSupport);
            openLateralPanel(jLateralPanelGeometry);
            jLateralPanelGeometry.jPanelElasticSupportsMousePressed();
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelGeometry_jLabelSettlementSupport(MouseEvent evt) {
        if (jLabelSettlements.isEnabled()) {
            resetLabelsNumeration((LabelMouseEvents) jLabelSettlements);
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            panelGeometry_LabelSelected = labelsNumeration.jPGeometry_Settlements;
            jP_Bottom.jLabelInformation.setText(informations.jPGeometry_Settlements);
            openLateralPanel(jLateralPanelGeometry);
            jLateralPanelGeometry.jPanelSettlementsMousePressed();
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelGeometry_jLabelDeleteSupports(MouseEvent evt) {
        resetLabelsNumeration((LabelMouseEvents) jLabelCutSupports);
        if (!jDrawingPanel.isShowing()) {
            jScrollPane1.setViewportView(jDrawingPanel);
        }
        panelGeometry_LabelSelected = labelsNumeration.jPGeometry_CutSupports;
        jP_Bottom.jLabelInformation.setText(informations.jPGeometry_CutSupports);
    }

    /*
     * Métodos associados aos eventos do rato no panelLoads
     */

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelLoads_jLabelConcentratedLoad(MouseEvent evt) {
        if (jLabelConcentratedLoad.isEnabled()) {
            resetLabelsNumeration((LabelMouseEvents) jLabelConcentratedLoad);
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            panelLoads_LabelSelected = labelsNumeration.jPLoads_ConcentratedLoad;
            openLateralPanel(jLateralPanelLoads);
            jLateralPanelLoads.jPanelConLoadsMousePressed();
            jP_Bottom.jLabelInformation.setText(informations.jPLoads_ConcentratedLoad);
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelLoads_jLabelBendingMoment(MouseEvent evt) {
        if (jLabelBendingMoment.isEnabled()) {
            resetLabelsNumeration((LabelMouseEvents) jLabelBendingMoment);
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            panelLoads_LabelSelected = labelsNumeration.jPLoads_BendingMoment;
            openLateralPanel(jLateralPanelLoads);
            jLateralPanelLoads.jPanelMomentsMousePressed();
            jP_Bottom.jLabelInformation.setText(informations.jPLoads_BendingMoment);
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelLoads_jLabelUnifDistLoad(MouseEvent evt) {
        if (jLabelUnifDistLoad.isEnabled()) {
            resetLabelsNumeration((LabelMouseEvents) jLabelUnifDistLoad);
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            panelLoads_LabelSelected = labelsNumeration.jPLoads_UnifDistLoad;
            openLateralPanel(jLateralPanelLoads);
            jLateralPanelLoads.jPanelDistrLoadsMousePressed();
            jP_Bottom.jLabelInformation.setText(informations.jPLoads_UnifDistLoad);
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelLoads_jLabelDistAxialLoad(MouseEvent evt) {
        if (jLabelDistAxialLoad.isEnabled()) {
            resetLabelsNumeration((LabelMouseEvents) jLabelDistAxialLoad);
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            panelLoads_LabelSelected = labelsNumeration.jPLoads_DistAxialLoad;
            openLateralPanel(jLateralPanelLoads);
            jLateralPanelLoads.jPanelAxialLoadsMousePressed();
            jP_Bottom.jLabelInformation.setText(informations.jPLoads_DistAxialLoad);
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelLoads_jLabelUnifPlanarLoad(MouseEvent evt) {
        if (jLabelUnifPlanarLoad.isEnabled()) {
            resetLabelsNumeration((LabelMouseEvents) jLabelUnifPlanarLoad);
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            panelLoads_LabelSelected = labelsNumeration.jPLoads_UnifPlanarLoad;
            openLateralPanel(jLateralPanelLoads);
            jLateralPanelLoads.jPanelPlanarLoadsMousePressed();
            jP_Bottom.jLabelInformation.setText(informations.jPLoads_UnifPlanarLoad);
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelLoads_jLabelThermalLoad(MouseEvent evt) {
        if (jLabelThermalLoad.isEnabled()) {
            resetLabelsNumeration((LabelMouseEvents) jLabelThermalLoad);
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            panelLoads_LabelSelected = labelsNumeration.jPLoads_ThermalLoad;
            openLateralPanel(jLateralPanelLoads);
            jLateralPanelLoads.jPanelThermalLoadsMousePressed();
            jP_Bottom.jLabelInformation.setText(informations.jPLoads_ThermalLoad);
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelLoads_jLabelSelfWeight(MouseEvent evt) {
        if (jLabelSelfWeight.isEnabled()) {
            resetLabelsNumeration((LabelMouseEvents) jLabelSelfWeight);
            if (!jDrawingPanel.isShowing()) {
                jScrollPane1.setViewportView(jDrawingPanel);
            }
            panelLoads_LabelSelected = labelsNumeration.jPLoads_SelfWeight;
            openLateralPanel(jLateralPanelLoads);
            jLateralPanelLoads.jPanelSelfWeightMousePressed();
            jP_Bottom.jLabelInformation.setText(informations.jPLoads_SelfWeight);
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelLoads_jLabelDelete(MouseEvent evt) {
        resetLabelsNumeration((LabelMouseEvents) jLabelCutLoad);
        if (!jDrawingPanel.isShowing()) {
            jScrollPane1.setViewportView(jDrawingPanel);
        }
        panelLoads_LabelSelected = labelsNumeration.jPLoads_Cut;
        jP_Bottom.jLabelInformation.setText(informations.jPLoads_CutLoads);
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelLoads_jLabelLoadTable(MouseEvent evt) {
        resetLabelsNumeration();
        jFrameLoadTable.setVisible(true);
        jSPLoadTable.setBorder(null);

        LoadTable loadTable = new LoadTable(jDrawingPanel.arrayListLines, jDrawingPanel.arrayListPolygons, jDrawingPanel.arrayListLoads);
        ArrayList<String[]> arrayListLoadTable = loadTable.getLoadTable(type);
        DefaultTableModel table = (DefaultTableModel) jLoadTable.getModel();

        int length = table.getRowCount();
        for (int i = 0; i < length; i++) {
            table.removeRow(0);
        }

        int line = 1;
        for (String[] strings : arrayListLoadTable) {
            table.addRow(
                new String[] {
                    " " + String.valueOf(line),
                    " " + strings[0],
                    " " + strings[1],
                    " " + strings[2],
                    " " + strings[3],
                    " " + strings[4],
                }
            );
            line++;
        }

        if (line <= 40) {
            for (int i = line; i <= 40; i++) {
                table.addRow(new String[] { " " + String.valueOf(line), "", "", "", "", "" });
                line++;
            }
        }

        jLoadTable.setModel(table);
    }

    /*
     * Métodos associados aos eventos do rato no panelAnalysis
     */

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelAnalysis_jLabelTheory(MouseEvent evt) {
        if (jLabelTheory.isEnabled()) {
            resetLabelsNumeration();
            panelAnalysis_LabelSelected = labelsNumeration.jPAnalysis_Theory;
            openLateralPanel(jLateralPanelAnalysis);
            jLateralPanelAnalysis.jPanelTheoryMousePressed();
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelAnalysis_jRadioButtonAnalytical(MouseEvent evt) {
        if (jRadioButtonAnalytical.isEnabled()) {
            if (jRadioButtonAnalytical.isSelected()) {
                jRadioButtonNumerical.setSelected(true);
                resetLabelsNumeration();
                panelLoads_LabelSelected = labelsNumeration.jPAnalysis_Numerical;
                openLateralPanel(jLateralPanelAnalysis);
                jLateralPanelAnalysis.jPanelNumericalMousePressed();
            } else {
                resetLabelsNumeration();
                panelLoads_LabelSelected = labelsNumeration.jPAnalysis_Analytical;
                jButtonClose_jLateralPanel(null);

                //Instrução para desmarcar o jRadioButtonNumerical
                jRadioButtonNumerical.setSelected(false);
            }

            jP_Bottom.jLabelInformation.setText("");
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelAnalysis_jRadioButtonNumerical(MouseEvent evt) {
        if (jRadioButtonNumerical.isEnabled()) {
            if (jRadioButtonNumerical.isSelected()) {
                jRadioButtonAnalytical.setSelected(true);
                resetLabelsNumeration();
                panelLoads_LabelSelected = labelsNumeration.jPAnalysis_Analytical;
                jButtonClose_jLateralPanel(null);
            } else {
                resetLabelsNumeration();
                panelLoads_LabelSelected = labelsNumeration.jPAnalysis_Numerical;
                openLateralPanel(jLateralPanelAnalysis);
                jLateralPanelAnalysis.jPanelNumericalMousePressed();

                //Instrução para desmarcar o jRadioButtonAnalytical
                jRadioButtonAnalytical.setSelected(false);
            }

            jP_Bottom.jLabelInformation.setText("");
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelAnalysis_jLabelCalculate(MouseEvent evt) {
        resetLabelsNumeration();

        String theory = "";
        if ("2D".equals(type)) {
            if (jLateralPanelAnalysis.jRB_AnalysisPlaneStress.isSelected()) {
                theory = "Plane Stress";
            }
            if (jLateralPanelAnalysis.jRB_AnalysisPlaneStrain.isSelected()) {
                theory = "Plane Strain";
            }
        }
        if ("Beams".equals(type)) {
            if (jLateralPanelAnalysis.jRB_AnalysisEulerBernoulli.isSelected()) {
                theory = "Euler-Bernoulli";
            }
            if (jLateralPanelAnalysis.jRB_AnalysisTimoshenko.isSelected()) {
                theory = "Timoshenko";
            }
        }
        if ("Slabs".equals(type)) {
            if (jLateralPanelAnalysis.jRB_AnalysisReissnerMindlin.isSelected()) {
                theory = "Reissner-Mindlin";
            }
            if (jLateralPanelAnalysis.jRB_AnalysisKirchhoff.isSelected()) {
                theory = "Kirchhoff";
            }
        }

        boolean calculate = true;
        if (jDrawingPanel.arrayListLines.isEmpty() && jDrawingPanel.arrayListPolygons.isEmpty()) {
            calculate = false;
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Error! It should draw a finite element mesh.");
        } else if (jDrawingPanel.arrayListSupports.isEmpty() && jDrawingPanel.arrayListElasticSupports.isEmpty()) {
            calculate = false;
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Error! Should give support to the finite element mesh.");
        } else if (jDrawingPanel.arrayListSettlements.isEmpty() && jDrawingPanel.arrayListLoads.isEmpty() && selfWeights.isEmpty()) {
            calculate = false;
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Error! Must assign loads to the finite element mesh.");
        }

        Geometry.Section section;
        if (calculate) {
            if (sections.isEmpty()) {
                section = null;
                calculate = false;
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Error! Must define the properties of sections of finite elements.");
            } else {
                int index = jLateralPanelGeometry.jCB_Sections.getSelectedIndex();
                section = sections.get(index);

                switch (this.type) {
                    case "1D":
                        for (Geometry.Section s : sections) {
                            if (s.area <= 0) {
                                calculate = false;
                                break;
                            }
                        }
                        break;
                    case "2D":
                        for (Geometry.Section s : sections) {
                            if (s.thickness <= 0) {
                                calculate = false;
                                break;
                            }
                        }
                        break;
                    case "Beams":
                        for (Geometry.Section s : sections) {
                            if (s.inertia <= 0 || s.area <= 0 || s.thickness <= 0) {
                                calculate = false;
                                break;
                            }
                        }
                        break;
                    case "Frames":
                        for (Geometry.Section s : sections) {
                            if (s.inertia <= 0 || s.area <= 0 || s.thickness <= 0) {
                                calculate = false;
                                break;
                            }
                        }
                        break;
                    case "Grids":
                        for (Geometry.Section s : sections) {
                            if (s.inertia <= 0 || s.torsion <= 0 || s.area <= 0 || s.thickness <= 0) {
                                calculate = false;
                                break;
                            }
                        }
                        break;
                    case "Slabs":
                        for (Geometry.Section s : sections) {
                            if (s.thickness <= 0) {
                                calculate = false;
                                break;
                            }
                        }
                        break;
                }

                if (calculate == false) {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "Error! The properties of the section are incorrect.");
                }
            }
        } else {
            section = null;
        }

        Geometry.Material material;
        int index = jLateralPanelGeometry.jCB_Materials.getSelectedIndex();
        material = materials.get(index);
        if (calculate) {
            if (material.elasticity <= 0 || material.poisson <= 0 || material.thermal <= 0) {
                calculate = false;
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Error! The material properties are incorrect.");
            }
        }

        if (calculate) {
            Processor processor = new Processor(type, theory, factor);

            String quadrature;
            int pointsBars;
            int pointsTriangles;
            int pointsQuadrilaterals;

            if (jRadioButtonNumerical.isSelected()) {
                quadrature = "";

                if (jLateralPanelAnalysis.jRB_LegendreAnalysis.isSelected()) {
                    quadrature = "Gauss-Legendre";
                }
                if (jLateralPanelAnalysis.jRB_LobattoAnalysis.isSelected()) {
                    quadrature = "Gauss-Lobatto";
                }

                pointsBars = clearText(String.valueOf(jLateralPanelAnalysis.jCB_AnalysisBars.getSelectedItem()));
                pointsTriangles = clearText(String.valueOf(jLateralPanelAnalysis.jCB_AnalysisTriangles.getSelectedItem()));
                pointsQuadrilaterals = clearText(String.valueOf(jLateralPanelAnalysis.jCB_AnalysisQuadrilaterals.getSelectedItem()));
            } else {
                quadrature = "";
                pointsBars = 0;
                pointsTriangles = 0;
                pointsQuadrilaterals = 0;
            }

            double density = 0;
            if (!selfWeights.isEmpty()) {
                density = selfWeights.get(jLateralPanelLoads.jCB_SelfWeight.getSelectedIndex()).selfWeight_S;
            }

            processor.setDrawnFigures(jDrawingPanel.arrayListLines, jDrawingPanel.arrayListPolygons);
            processor.setDrawnLoads(jDrawingPanel.arrayListLoads);
            processor.setDrawnSupports(
                jDrawingPanel.arrayListSupports,
                jDrawingPanel.arrayListElasticSupports,
                jDrawingPanel.arrayListSettlements
            );
            processor.setProperties(getNumberOfSelectedNodes(), section, material);
            processor.setSections(sections);
            processor.setDensity(density);
            processor.setQuadrature(quadrature, pointsBars, pointsTriangles, pointsQuadrilaterals);

            try {
                processor.calculate();
                results = processor;
                jP_Bottom.jLabelInformation.setText(informations.jPAnalysis_CalculateEnd);
            } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                jP_Bottom.jLabelInformation.setText("");
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Error! Check the condition of the structure.");
            } catch (OutOfMemoryError e) {
                jP_Bottom.jLabelInformation.setText("");
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Error! The memory is insufficient to calculate the structure.");
            } catch (Exception e) {
                jP_Bottom.jLabelInformation.setText("");
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Error! Check the condition of the structure.");
            }
        }
    }

    /*
     * Métodos associados aos eventos do rato no panelResults
     */

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelResults_jLabelVectorF(MouseEvent evt) {
        if (jLabelVectorF.isEnabled()) {
            jButtonClose_jLateralPanel(null);
            resetLabelsNumeration((LabelMouseEvents) jLabelVectorF);
            removeCoordinates();

            jScrollPane1.setViewportView(vectorTableResults);
            vectorTableResults.jScrollPane1.setBorder(null);

            DefaultTableModel table = (DefaultTableModel) vectorTableResults.jTableResults.getModel();

            int length = table.getRowCount();
            for (int i = 0; i < length; i++) {
                table.removeRow(0);
            }

            if (results.loadVector != null) {
                table = ResultsTables.printVector(table, results.loadVector);
            } else {
                int i = 1;
                for (int j = 0; j < 40; j++) {
                    table.addRow(new String[] { " " + String.valueOf(i), "", "" });
                    i++;
                }
            }

            vectorTableResults.jTableResults.setModel(table);
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelResults_jLabelMatrixK(MouseEvent evt) {
        if (jLabelMatrixK.isEnabled()) {
            jButtonClose_jLateralPanel(null);
            resetLabelsNumeration((LabelMouseEvents) jLabelMatrixK);
            removeCoordinates();

            jScrollPane1.setViewportView(matrixTableResults);
            matrixTableResults.jScrollPane1.setBorder(null);

            DefaultTableModel table = new DefaultTableModel();

            int length;
            if (results.stiffnessMatrix != null) {
                table = ResultsTables.printMatrix(results.stiffnessMatrix);
            } else {
                table.addColumn("");
                for (int i = 1; i <= 20; i++) {
                    table.addColumn(String.valueOf(i));
                }
                for (int i = 1; i <= 40; i++) {
                    table.addRow(new String[] { " " + String.valueOf(i) });
                }
            }

            matrixTableResults.jTableResults.setModel(table);

            if (matrixTableResults.jTableResults.getColumnModel().getColumnCount() > 0) {
                length = matrixTableResults.jTableResults.getColumnCount();
                for (int i = 0; i < length; i++) {
                    matrixTableResults.jTableResults.getColumnModel().getColumn(i).setMinWidth(100);
                    matrixTableResults.jTableResults.getColumnModel().getColumn(i).setMaxWidth(200);
                }
            }
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelResults_jLabelVectorDisplacements(MouseEvent evt) {
        if (jLabelVectorDisplacements.isEnabled()) {
            jButtonClose_jLateralPanel(null);
            resetLabelsNumeration((LabelMouseEvents) jLabelVectorDisplacements);
            removeCoordinates();

            jScrollPane1.setViewportView(vectorTableResults);
            vectorTableResults.jScrollPane1.setBorder(null);

            DefaultTableModel table = (DefaultTableModel) vectorTableResults.jTableResults.getModel();

            int length = table.getRowCount();
            for (int i = 0; i < length; i++) {
                table.removeRow(0);
            }

            if (results.displacementVector != null) {
                table = ResultsTables.printVector(table, results.displacementVector);
            } else {
                int i = 1;
                for (int j = 0; j < 40; j++) {
                    table.addRow(new String[] { " " + String.valueOf(i), "", "" });
                    i++;
                }
            }

            vectorTableResults.jTableResults.setModel(table);
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelResults_jLabelProperties(MouseEvent evt) {
        if (jLabelProperties.isEnabled()) {
            jButtonClose_jLateralPanel(null);
            resetLabelsNumeration((LabelMouseEvents) jLabelProperties);
            removeCoordinates();

            jScrollPane1.setViewportView(individualProperties);
            individualProperties.jScrollPane1.setBorder(null);

            ArrayList<FiniteElement> arrayListFiniteElements;
            arrayListFiniteElements = results.arrayListFiniteElements;

            DefaultTableModel table = (DefaultTableModel) individualProperties.jTable1.getModel();

            int length = table.getRowCount();
            for (int i = 0; i < length; i++) {
                table.removeRow(0);
            }

            int line = 1;
            for (FiniteElement finiteElement : arrayListFiniteElements) {
                String n = " " + String.valueOf(line);
                String shape = " " + finiteElement.getShape();
                String nodes = " " + String.valueOf(finiteElement.getNodes());
                String material = " " + finiteElement.getMaterialName();

                table.addRow(new String[] { n, shape, " " + type, nodes, material });
                line++;
            }

            if (line < 40) {
                for (int i = line; i <= 40; i++) {
                    table.addRow(new String[] { " " + String.valueOf(i) });
                }
            }

            individualProperties.jTable1.setModel(table);
            individualProperties.jButtonVectorF.setEnabled(false);
            individualProperties.jButtonMatrixK.setEnabled(false);
            individualProperties.jButtonVectorDisp.setEnabled(false);
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelResults_jLabelSupportReactions(MouseEvent evt) {
        if (jLabelSupportReactions.isEnabled()) {
            jButtonClose_jLateralPanel(null);
            resetLabelsNumeration((LabelMouseEvents) jLabelSupportReactions);
            removeCoordinates();

            DefaultTableModel table = (DefaultTableModel) jTReactionsSupport.jTable1.getModel();

            if (results.supportReactions != null) {
                String[][] supportReactions = results.getSupportReactions();

                int length = table.getRowCount();
                for (int i = 0; i < length; i++) {
                    table.removeRow(0);
                }

                int line = 1;
                for (String[] reactions : supportReactions) {
                    String node = " " + reactions[0];
                    String reaction = " " + reactions[1];
                    String unit = " " + reactions[2];
                    String description = " " + reactions[3];

                    table.addRow(new String[] { " " + String.valueOf(line), node, reaction, unit, description });
                    line++;
                }

                if (line <= 40) {
                    for (int i = line; i <= 40; i++) {
                        table.addRow(new String[] { " " + String.valueOf(line), "", "", "", "" });
                        line++;
                    }
                }

                jTReactionsSupport.jTable1.setModel(table);
            } else {
                int length = table.getRowCount();
                for (int i = 0; i < length; i++) {
                    table.removeRow(0);
                }

                int line = 1;
                for (int i = line; i <= 40; i++) {
                    table.addRow(new String[] { " " + String.valueOf(line), "", "", "", "" });
                    line++;
                }

                jTReactionsSupport.jTable1.setModel(table);
            }

            jScrollPane1.setViewportView(jTReactionsSupport);
            jTReactionsSupport.jScrollPane1.setBorder(null);
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelResults_jLabelNodalForces(MouseEvent evt) {
        if (jLabelNodalForces.isEnabled()) {
            jButtonClose_jLateralPanel(null);
            resetLabelsNumeration((LabelMouseEvents) jLabelNodalForces);
            removeCoordinates();

            jScrollPane1.setViewportView(jTNodalForces);
            jTNodalForces.jScrollPane1.setBorder(null);

            ArrayList<FiniteElement> arrayListFiniteElements;
            arrayListFiniteElements = results.arrayListFiniteElements;

            DefaultTableModel table = (DefaultTableModel) jTNodalForces.jTable1.getModel();

            switch (this.type) {
                case "1D":
                    table = ResultsTables.nodalForces_1D(table, arrayListFiniteElements);
                    break;
                case "2D":
                    table = ResultsTables.nodalForces_2D(table, arrayListFiniteElements);
                    break;
                case "3D":
                    table = ResultsTables.nodalForces_3D(table, arrayListFiniteElements);
                    break;
                case "Beams":
                    table = ResultsTables.nodalForces_Beams(table, arrayListFiniteElements);
                    break;
                case "Frames":
                    table = ResultsTables.nodalForces_Frames(table, arrayListFiniteElements);
                    break;
                case "Grids":
                    table = ResultsTables.nodalForces_Grids(table, arrayListFiniteElements);
                    break;
                case "Slabs":
                    table = ResultsTables.nodalForces_Slabs(table, arrayListFiniteElements);
                    break;
            }

            jTNodalForces.jTable1.setModel(table);
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelResults_jLabelNodalStresses(MouseEvent evt) {
        if (jLabelNodalStresses.isEnabled()) {
            jButtonClose_jLateralPanel(null);
            resetLabelsNumeration((LabelMouseEvents) jLabelNodalStresses);
            removeCoordinates();

            jScrollPane1.setViewportView(jTNodalStresses);

            ArrayList<FiniteElement> arrayListFiniteElements;
            arrayListFiniteElements = results.arrayListFiniteElements;

            DefaultTableModel table;

            switch (this.type) {
                case "1D":
                    table = (DefaultTableModel) jTNodalStresses.jTable_1D.getModel();
                    table = ResultsTables.nodalStresses_1D(table, arrayListFiniteElements);
                    jTNodalStresses.jTable_1D.setModel(table);
                    jTNodalStresses.selectTable(type);
                    break;
                case "2D":
                    table = (DefaultTableModel) jTNodalStresses.jTable_2D.getModel();
                    table = ResultsTables.nodalStresses_2D(table, arrayListFiniteElements);
                    jTNodalStresses.jTable_2D.setModel(table);
                    jTNodalStresses.selectTable(type);
                    break;
                case "3D":
                    table = (DefaultTableModel) jTNodalStresses.jTable_3D.getModel();
                    table = ResultsTables.nodalStresses_3D(table, arrayListFiniteElements);
                    jTNodalStresses.jTable_3D.setModel(table);
                    jTNodalStresses.selectTable(type);
                    break;
                case "Beams":
                    table = (DefaultTableModel) jTNodalStresses.jTable_Beams.getModel();
                    table = ResultsTables.nodalStresses_Beams(table, arrayListFiniteElements);
                    jTNodalStresses.jTable_Beams.setModel(table);
                    jTNodalStresses.selectTable(type);
                    break;
                case "Frames":
                    table = (DefaultTableModel) jTNodalStresses.jTable_Frames.getModel();
                    table = ResultsTables.nodalStresses_Frames(table, arrayListFiniteElements);
                    jTNodalStresses.jTable_Frames.setModel(table);
                    jTNodalStresses.selectTable(type);
                    break;
                case "Grids":
                    table = (DefaultTableModel) jTNodalStresses.jTable_Grids.getModel();
                    table = ResultsTables.nodalStresses_Grids(table, arrayListFiniteElements);
                    jTNodalStresses.jTable_Grids.setModel(table);
                    jTNodalStresses.selectTable(type);
                    break;
                case "Slabs":
                    table = (DefaultTableModel) jTNodalStresses.jTable_Slabs.getModel();
                    table = ResultsTables.nodalStresses_Slabs(table, arrayListFiniteElements);
                    jTNodalStresses.jTable_Slabs.setModel(table);
                    jTNodalStresses.selectTable(type);
                    break;
            }
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelResults_jLabelDisplacements(MouseEvent evt) {
        if (jLabelDisplacements.isEnabled()) {
            resetLabelsNumeration();
            openLateralPanel(jLateralPanelResults);
            jLateralPanelResults.jPanelDisplacementsMousePressed();
            int value = jLateralPanelResults.jSlider1.getValue();

            if ("1D".equals(type)) {
                jLateralPanelResults.jRBNodesMesh.setSelected(true);
                jLateralPanelResults.jRBNumberingNodes.setEnabled(true);
                jLateralPanelResults.jRBNodesDeformed.setSelected(true);
            }

            int gridPoints = 25;
            if (menuGridPoints25px.isSelected()) {
                gridPoints = 25;
            }
            if (menuGridPoints50px.isSelected()) {
                gridPoints = 50;
            }

            int scaleFactor = results.getFactor();
            resultsPane.resetPanel(scale, scaleFactor, gridPoints);
            jScrollPane1.setViewportView(resultsPane);

            if (results.arrayListFiniteElements != null) {
                int maximum = getDeformedFactor(maximumDisplacements(type, results.arrayListFiniteElements));
                resultsPane.changeDeformedFactor((int) (Math.round(maximum * (value / 50.0))));
                resultsPane.setGrid(jRadioButtonGrid.isSelected());
                resultsPane.drawNodesUndeformed(jLateralPanelResults.jRBNodesMesh.isSelected());
                resultsPane.drawNumberingOfNodes(jLateralPanelResults.jRBNumberingNodes.isSelected());
                resultsPane.drawNodesDeformed(jLateralPanelResults.jRBNodesDeformed.isSelected());
                resultsPane.drawDeformedStructure(results.arrayListFiniteElements);
                resultsPane.drawSupports(type, "Perspective", results.getRigidSupports(), results.getElasticSupports());

                resultsPane.typeOfResults = "Displacements";
                addLegendsForResults("Displacements");
            }
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelResults_jLabelDiagrams(MouseEvent evt) {
        if (jLabelDiagrams.isEnabled()) {
            resetLabelsNumeration();
            openLateralPanel(jLateralPanelResults);
            jLateralPanelResults.jPanelDiagramsMousePressed();

            if ("1D".equals(type)) {
                jLateralPanelResults.jRBAxialForce.setSelected(true);
                jLateralPanelResults.jRBShearForce.setSelected(false);
                jLateralPanelResults.jRBBendingMoment.setSelected(false);
                jLateralPanelResults.jRBTorsionalMoment.setSelected(false);
            }

            if ("Grids".equals(type) && jLateralPanelResults.jRBAxialForce.isSelected()) {
                jLateralPanelResults.jRBAxialForce.setSelected(false);
                jLateralPanelResults.jRBShearForce.setSelected(true);
                jLateralPanelResults.jRBBendingMoment.setSelected(false);
                jLateralPanelResults.jRBTorsionalMoment.setSelected(false);
            }

            String diagram = "";
            if (jLateralPanelResults.jRBAxialForce.isSelected()) {
                diagram = "Axial Force";
            }
            if (jLateralPanelResults.jRBShearForce.isSelected()) {
                diagram = "Shear Force";
            }
            if (jLateralPanelResults.jRBBendingMoment.isSelected()) {
                diagram = "Bending Moment";
            }
            if (jLateralPanelResults.jRBTorsionalMoment.isSelected()) {
                diagram = "Torsional Moment";
            }

            int gridPoints = 25;
            if (menuGridPoints25px.isSelected()) {
                gridPoints = 25;
            }
            if (menuGridPoints50px.isSelected()) {
                gridPoints = 50;
            }

            int scaleFactor = results.getFactor();
            resultsPane.resetPanel(scale, scaleFactor, gridPoints);
            resultsPane.removeAll();
            resultsPane.validate();
            resultsPane.updateUI();
            jScrollPane1.setViewportView(resultsPane);

            if (results.arrayListFiniteElements != null) {
                if ("1D".equals(type)) {
                    resultsPane.drawNodesUndeformed(true);
                }
                resultsPane.setGrid(jRadioButtonGrid.isSelected());
                resultsPane.drawDiagrams(type, diagram, results.arrayListFiniteElements);
                resultsPane.drawSupports(type, "Perspective", results.getRigidSupports(), results.getElasticSupports());
            }

            resultsPane.typeOfResults = "Diagrams";
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelResults_jLabelIsovalues(MouseEvent evt) {
        if (jLabelIsovalues.isEnabled()) {
            resetLabelsNumeration();

            if ("2D".equals(type) || "Slabs".equals(type)) {
                openLateralPanel(jLateralPanelResults);
                jLateralPanelResults.jPanelIsovaluesMousePressed();

                int gridPoints = 25;
                if (menuGridPoints25px.isSelected()) {
                    gridPoints = 25;
                }
                if (menuGridPoints50px.isSelected()) {
                    gridPoints = 50;
                }

                int scaleFactor = results.getFactor();
                resultsPane.resetPanel(scale, scaleFactor, gridPoints);
                jScrollPane1.setViewportView(resultsPane);

                if (results.arrayListFiniteElements != null) {
                    //Captura do botão selecionado
                    String stresses;
                    if (jLateralPanelResults.jRBIsolinesσy.isSelected()) {
                        stresses = "σy";
                    } else if (jLateralPanelResults.jRBIsolinesτxy.isSelected()) {
                        stresses = "τxy";
                    } else {
                        stresses = "σx";
                    }

                    String plane;
                    if (jLateralPanelResults.jRBIsolines_SlabsBottom.isSelected()) {
                        plane = "Bottom";
                    } else {
                        plane = "Top";
                    }

                    //Desenho do conteúdo no painel de resultados
                    ArrayList<double[]> listOfNodes = results.getArrayListNodes();
                    resultsPane.setGrid(jRadioButtonGrid.isSelected());
                    resultsPane.setDrawIsolinesAndMaps("Isolines");
                    resultsPane.drawIsolinesAndMaps(type, stresses, plane, results.arrayListFiniteElements, listOfNodes);
                    resultsPane.drawSupports(type, "", results.getRigidSupports(), results.getElasticSupports());

                    resultsPane.typeOfResults = "Isolines";
                    addLegendsForResults("Isolines");
                }
            }
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelResults_jLabelMaps(MouseEvent evt) {
        if (jLabelMaps.isEnabled()) {
            resetLabelsNumeration();

            if ("2D".equals(type) || "Slabs".equals(type)) {
                openLateralPanel(jLateralPanelResults);
                jLateralPanelResults.jPanelMapsMousePressed();

                int gridPoints = 25;
                if (menuGridPoints25px.isSelected()) {
                    gridPoints = 25;
                }
                if (menuGridPoints50px.isSelected()) {
                    gridPoints = 50;
                }

                int scaleFactor = results.getFactor();
                resultsPane.resetPanel(scale, scaleFactor, gridPoints);
                jScrollPane1.setViewportView(resultsPane);

                if (results.arrayListFiniteElements != null) {
                    //Captura do botão selecionado
                    String stresses;
                    if (jLateralPanelResults.jRBMapsσy.isSelected()) {
                        stresses = "σy";
                    } else if (jLateralPanelResults.jRBMapsτxy.isSelected()) {
                        stresses = "τxy";
                    } else {
                        stresses = "σx";
                    }

                    String plane;
                    if (jLateralPanelResults.jRBMaps_SlabsBottom.isSelected()) {
                        plane = "Bottom";
                    } else {
                        plane = "Top";
                    }

                    //Desenho do conteúdo no painel de resultados
                    ArrayList<double[]> listOfNodes = results.getArrayListNodes();
                    resultsPane.setGrid(jRadioButtonGrid.isSelected());
                    resultsPane.setDrawIsolinesAndMaps("Maps");
                    resultsPane.drawIsolinesAndMaps(type, stresses, plane, results.arrayListFiniteElements, listOfNodes);
                    resultsPane.drawSupports(type, "", results.getRigidSupports(), results.getElasticSupports());

                    resultsPane.typeOfResults = "Maps";
                    addLegendsForResults("Maps");
                }
            }
        }
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelResults_jLabelPrincipalStresses(MouseEvent evt) {
        if (jLabelPrincipalStresses.isEnabled()) {
            resetLabelsNumeration();

            int gridPoints = 25;
            if (menuGridPoints25px.isSelected()) {
                gridPoints = 25;
            }
            if (menuGridPoints50px.isSelected()) {
                gridPoints = 50;
            }

            int scaleFactor = results.getFactor();
            resultsPane.resetPanel(scale, scaleFactor, gridPoints);
            resultsPane.removeAll();
            resultsPane.validate();
            resultsPane.updateUI();
            jScrollPane1.setViewportView(resultsPane);

            if (results.arrayListFiniteElements != null) {
                resultsPane.setGrid(jRadioButtonGrid.isSelected());
                resultsPane.drawPrincipalStresses(results.arrayListFiniteElements);
                resultsPane.drawSupports(type, "", results.getRigidSupports(), results.getElasticSupports());
            }

            resultsPane.typeOfResults = "Principal Stresses";
        }
    }

    /**
     * Método para abrir o painel que contém os botões dos menus
     */
    private void openMenuContainer() {
        if (jPanel5.getHeight() == 0) {
            int widthScroll = jScrollPane1.getWidth();
            int heightScroll = jScrollPane1.getHeight();

            jPanel5.removeAll();
            jPanel5.setSize(jPanel5.getWidth(), 35);
            jPanel5.setPreferredSize(new java.awt.Dimension(jPanel5.getWidth(), 35));
            jPanel5.updateUI();

            jScrollPane1.setSize(widthScroll, heightScroll - 35);
            jScrollPane1.setPreferredSize(new java.awt.Dimension(widthScroll, heightScroll - 35));
            jScrollPane1.setLocation(0, 35);
            jScrollPane1.updateUI();
            jScrollPane1.setBorder(null);
        }

        activateMenu = true;
        activateMenuDraw();
    }

    /**
     * Método para fechar o painel que contém os botões dos menus
     */
    private void closeMenuContainer() {
        int widthScroll = jScrollPane1.getWidth();
        int heightScroll = jScrollPane1.getHeight();

        jPanel5.removeAll();
        jPanel5.setSize(jPanel5.getWidth(), 0);
        jPanel5.setPreferredSize(new java.awt.Dimension(jPanel5.getWidth(), 0));
        jPanel5.updateUI();

        jScrollPane1.setSize(widthScroll, heightScroll + 35);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(widthScroll, heightScroll + 35));
        jScrollPane1.setLocation(0, 0);
        jScrollPane1.updateUI();
        jScrollPane1.setBorder(null);

        menuDraw.setForeground(new java.awt.Color(255, 255, 255));
        menuDraw.setOpaque(false);
        activateMenu = false;
    }

    /**
     * Método para eliminar a contagem de clicks dentro do jDrawingPanel
     */
    private void resetClicks() {
        jDrawingPanel.nClicks = 0;
    }

    /**
     * Método para abrir o painel lateral na interface
     *
     * @param activatedPanel é o painel a ser ativado
     */
    private void openLateralPanel(javax.swing.JPanel activatedPanel) {
        if (activateLateralPanel == false) {
            activateLateralPanel = true;

            int height = jLateralPanel.getHeight();
            jLateralPanel.setSize(200, height);
            jLateralPanel.setPreferredSize(new java.awt.Dimension(200, height));
            jScrollPane1.updateUI();
            jScrollPane1.setBorder(null);
        }

        //Ativação do conteúdo associado ao painel selecionado
        jLateralPanel.removeAll();
        jLateralPanel.setLayout(new BorderLayout());
        jLateralPanel.add(activatedPanel);
        jLateralPanel.setVisible(true);
        jLateralPanel.validate();
    }

    /**
     * Método para fechar o painel lateral na interface
     */
    private void closeLateralPanel() {
        activateLateralPanel = false;

        int height = jLateralPanel.getHeight();
        jLateralPanel.setSize(0, height);
        jLateralPanel.setPreferredSize(new java.awt.Dimension(0, height));
        jScrollPane1.updateUI();
        jScrollPane1.setBorder(null);

        //Chamada do método para requerer o focus para a janela
        requestFocus();
    }

    /**
     * Método para fechar o painel lateral requerido pelo botão jButtonClose_jLateralPanel
     */
    private void jButtonClose_jLateralPanel(java.awt.event.ActionEvent evt) {
        jLateralPanel.removeAll();
        jLateralPanel.updateUI();
        closeLateralPanel();
    }

    /**
     * Método para criar o jDrawingPanel e ativar os seus eventos
     */
    private void eventsForDrawingPanel() {
        //Obtenção de elementos necessários à construção do jDrawingPanel
        int gridPoints = 25;
        if (menuGridPoints25px.isSelected()) {
            gridPoints = 25;
        }
        if (menuGridPoints50px.isSelected()) {
            gridPoints = 50;
        }

        //Atribuição dos restantes atributos ao painel jDrawingPanel
        jDrawingPanel.setBackground(new java.awt.Color(255, 255, 255));
        jDrawingPanel.setAlignmentX(0.0F);
        jDrawingPanel.setAlignmentY(0.0F);
        jDrawingPanel.setMaximumSize(new java.awt.Dimension(3000, 3000));
        jDrawingPanel.setMinimumSize(new java.awt.Dimension(500, 500));
        jDrawingPanel.setPreferredSize(new java.awt.Dimension(3000, 3000));
        jDrawingPanel.setFocusable(true);

        jDrawingPanel.setOtherProperties(
            type,
            jRadioButtonGrid.isSelected(),
            jRadioButtonSnap.isSelected(),
            jRadioButtonLegends.isSelected(),
            gridPoints,
            factor
        );

        /*
         * Adicionar eventos do rato ao jDrawingPanel
         */
        jDrawingPanel.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    jDrawingPanel_MouseClicked(evt);
                }

                @Override
                public void mouseEntered(MouseEvent evt) {
                    jDrawingPanel_MouseEntered(evt);
                }

                @Override
                public void mouseExited(MouseEvent evt) {
                    jDrawingPanel_MouseExited(evt);
                }

                @Override
                public void mousePressed(MouseEvent evt) {
                    jDrawingPanel_MousePressed(evt);
                }

                @Override
                public void mouseReleased(MouseEvent evt) {
                    jDrawingPanel_MouseReleased(evt);
                }
            }
        );

        jDrawingPanel.addMouseMotionListener(
            new java.awt.event.MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent evt) {
                    jDrawingPanel_MouseDragged(evt);
                }

                @Override
                public void mouseMoved(MouseEvent evt) {
                    jDrawingPanel_MouseMoved(evt);
                }
            }
        );

        jDrawingPanel.addMouseWheelListener(this::mouseWheelMoved);
    }

    /**
     * Método para gerir todos os objetos da interface gráfica
     */
    private void activateAllObjects() {
        jPWelcome.setForegroundForLabels();

        //Gestão dos menus ativados e desativados
        menuFileNew.setEnabled(true);
        menuFileOpen.setEnabled(true);
        menuFileClose.setEnabled(true);
        menuFileSave.setEnabled(true);
        menuFileExit.setEnabled(true);
        menuEditUndo.setEnabled(true);
        menuEditRedo.setEnabled(true);
        menuEditCut.setEnabled(true);
        menuEditCopy.setEnabled(true);
        menuEditPaste.setEnabled(true);
        menuEditSelectAll.setEnabled(true);
        menuEditDeleteAll.setEnabled(true);
        menuEditPreferences.setEnabled(true);
        menuHelpHelp.setEnabled(true);
        menuHelpAbout.setEnabled(true);

        resetAllActivatedFunctions();
        activateJPBottom();
        eventsForDrawingPanel();
        eventsForResultsPane();

        //Instruções gerir o conteúdo dos paineis laterais
        jLateralPanelGeometry.setType(type);
        jLateralPanelLoads.setType(type);
        jLateralPanelAnalysis.setType(type);
        jLateralPanelResults.setType(type);

        //Instruções para remover todos os elementos criados nos paineis laterais
        loadCounter = new int[7];
        geometryCounter = new int[4];
        sections.clear();
        materials.clear();
        elasticSupports.clear();
        settlements.clear();
        concentratedLoads.clear();
        bendingMoments.clear();
        distributedLoads.clear();
        axialLoads.clear();
        planarLoads.clear();
        thermalLoads.clear();
        selfWeights.clear();
        geometryUpdateAllModels();
        loadsUpdateAllModels();

        //Abrir o o painel que contém o conteúdo dos separadores
        openMenuContainer();
        if (jLateralPanel.getSize().width > 0) {
            jButtonClose_jLateralPanel(null);
        }
        if (jRadioButtonNumerical.isSelected()) {
            jRadioButtonAnalytical.setSelected(true);
            jRadioButtonNumerical.setSelected(false);
        }

        if ("1D".equals(type) || "Frames".equals(type) || "Grids".equals(type) || "Slabs".equals(type)) {
            jRadioButtonAnalytical.setEnabled(false);
            jRadioButtonNumerical.setEnabled(false);
        } else {
            jRadioButtonAnalytical.setEnabled(true);
            jRadioButtonNumerical.setEnabled(true);
        }

        //Instrução para mostrar o painel de desenho
        jScrollPane1.setViewportView(jDrawingPanel);
        jRadioButtonLegendsActionPerformed(null);
    }

    /**
     * Método para ativar o painel inicial JPWelcome
     */
    private void activateJPWelcome() {
        closeMenuContainer();
        jScrollPane1.setViewportView(jPWelcome);

        // Adicionar evento mousePressed às labels do jPWelcome

        jPWelcome.jLOpenProject.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelWelcome_jLOpenProject(evt);
                }
            }
        );

        jPWelcome.jLPlaneTrusses.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelWelcome_jLPlaneTrusses(evt);
                }
            }
        );

        jPWelcome.jLPlaneStressesStrains.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelWelcome_jLPlaneStressesStrains(evt);
                }
            }
        );

        jPWelcome.jLBeams.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelWelcome_jLBeams(evt);
                }
            }
        );

        jPWelcome.jLPlaneFrames.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelWelcome_jLFrames(evt);
                }
            }
        );

        jPWelcome.jLGrids.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelWelcome_jLGrids(evt);
                }
            }
        );

        jPWelcome.jLSlabs.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    panelWelcome_jLSlabs(evt);
                }
            }
        );
    }

    /*
     * Métodos associados aos eventos do rato no jPWelcome
     */

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelWelcome_jLOpenProject(MouseEvent evt) {
        jMenuItemFileOpen(null);
        jPWelcome.setForegroundForLabels();
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelWelcome_jLPlaneTrusses(MouseEvent evt) {
        type = "1D";
        setTitle("FEM for Students - Plane Trusses");
        activateAllObjects();
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelWelcome_jLPlaneStressesStrains(MouseEvent evt) {
        type = "2D";
        setTitle("FEM for Students - Plane Stresses/Strains");
        activateAllObjects();
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelWelcome_jLBeams(MouseEvent evt) {
        type = "Beams";
        setTitle("FEM for Students - Beams");
        activateAllObjects();
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelWelcome_jLFrames(MouseEvent evt) {
        type = "Frames";
        setTitle("FEM for Students - Plane Frames");
        activateAllObjects();
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelWelcome_jLGrids(MouseEvent evt) {
        type = "Grids";
        setTitle("FEM for Students - Grids");
        activateAllObjects();
    }

    /**
     * Método associado ao evento do rato mousePressed
     */
    private void panelWelcome_jLSlabs(MouseEvent evt) {
        type = "Slabs";
        setTitle("FEM for Students - Slabs");
        activateAllObjects();
    }

    /*
     * Métodos associados aos eventos do rato no jDrawingPanel
     */

    /**
     * Método MouseClicked associado ao jDrawingPanel
     *
     * @param evt é o evento MouseClicked do rato
     */
    private void jDrawingPanel_MouseClicked(MouseEvent evt) {
        //TODO add your handling code here
    }

    /**
     * Método MouseEntered associado ao jDrawingPanel
     *
     * @param evt é o evento MouseEntered do rato
     */
    private void jDrawingPanel_MouseEntered(MouseEvent evt) {
        //TODO add your handling code here
    }

    /**
     * Método MouseExited associado ao jDrawingPanel
     *
     * @param evt é o evento MouseExited do rato
     */
    private void jDrawingPanel_MouseExited(MouseEvent evt) {
        //TODO add your handling code here
    }

    /**
     * Método MousePressed associado ao jDrawingPanel
     *
     * @param evt é o evento MousePressed do rato
     */
    private void jDrawingPanel_MousePressed(MouseEvent evt) {
        //Esta condição é executada quando o botão esquerdo do rato é pressionado
        if (evt.getButton() == MouseEvent.BUTTON1) {
            Point point = changeCoordinates(evt.getX(), evt.getY());
            printMouseCoordinates(point.x, point.y);

            if (panelView_LabelSelected == labelsNumeration.jPanelView_Pan) {
                mouse.x = point.x;
                mouse.y = point.y;
            } else if (panelGeometry_LabelSelected == labelsNumeration.jPGeometry_ElasticSupport) {
                String name = getSupportName();
                double[] instructions = getSupportInstructions();

                if (instructions[0] > 0.0 || instructions[1] > 0.0 || instructions[2] > 0.0) {
                    jDrawingPanel.drawMousePressed_ElasticSupport(name, point.x, point.y, instructions);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "You must set a value for the stiffness of the spring!");
                }
            } else if (panelGeometry_LabelSelected == labelsNumeration.jPGeometry_Settlements) {
                String name = getSettlementName();
                double[] instructions = getSettlementInstructions();

                if (instructions[0] != 0.0 || instructions[1] != 0.0 || instructions[2] != 0.0) {
                    jDrawingPanel.drawMousePressed_Settlement(name, point.x, point.y, instructions);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "You must set a value for the settlement!");
                }
            } else if (panelLoads_LabelSelected > 0 && panelLoads_LabelSelected < 7) {
                String[] instructions = getLoadInstructions(panelLoads_LabelSelected);

                if (loadIsValid(panelLoads_LabelSelected, instructions)) {
                    jDrawingPanel.drawingPanel(
                        panelDraw_LabelSelected,
                        panelView_LabelSelected,
                        panelGeometry_LabelSelected,
                        panelLoads_LabelSelected,
                        panelAnalysis_LabelSelected,
                        panelResults_LabelSelected
                    );
                    jDrawingPanel.drawMousePressed_Loads(point.x, point.y, instructions);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "You must set a value for the load!");
                }
            } else {
                //Instruções para transmitir as coordenadas do rato para o jDrawingPanel
                jDrawingPanel.drawingPanel(
                    panelDraw_LabelSelected,
                    panelView_LabelSelected,
                    panelGeometry_LabelSelected,
                    panelLoads_LabelSelected,
                    panelAnalysis_LabelSelected,
                    panelResults_LabelSelected
                );

                jDrawingPanel.drawMousePressed(point.x, point.y, evt.isControlDown());
            }
        }
    }

    /**
     * Método MouseReleased associado ao jDrawingPanel
     *
     * @param evt é o evento MouseReleased do rato
     */
    private void jDrawingPanel_MouseReleased(MouseEvent evt) {
        //Esta condição é executada quando o botão esquerdo do rato é libertado
        if (evt.getButton() == MouseEvent.BUTTON1) {
            Point point = changeCoordinates(evt.getX(), evt.getY());
            printMouseCoordinates(point.x, point.y);

            if (panelView_LabelSelected != labelsNumeration.jPanelView_Pan) {
                if (panelDraw_LabelSelected == labelsNumeration.jPDraw_Select) {
                    jDrawingPanel.drawMouseReleased(point.x, point.y);
                }
            }
        }

        //Esta condição é executada quando o botão direito do rato é libertado
        if (evt.getButton() == MouseEvent.BUTTON3) {
            showPopMenu(evt);
        }
    }

    /**
     * Método MouseMoved associado ao jDrawingPanel
     *
     * @param evt é o evento MouseMoved do rato
     */
    private void jDrawingPanel_MouseMoved(MouseEvent evt) {
        Point point = changeCoordinates(evt.getX(), evt.getY());
        printMouseCoordinates(point.x, point.y);

        if (panelView_LabelSelected != labelsNumeration.jPanelView_Pan) {
            jDrawingPanel.drawingPanel(
                panelDraw_LabelSelected,
                panelView_LabelSelected,
                panelGeometry_LabelSelected,
                panelLoads_LabelSelected,
                panelAnalysis_LabelSelected,
                panelResults_LabelSelected
            );

            jDrawingPanel.drawMouseMoved(point.x, point.y);
        }
    }

    /**
     * Método MouseDragged associado ao jDrawingPanel
     *
     * @param evt é o evento MouseDragged do rato
     */
    private void jDrawingPanel_MouseDragged(MouseEvent evt) {
        if (panelView_LabelSelected == labelsNumeration.jPanelView_Pan) {
            Point point = changeCoordinates(evt.getX(), evt.getY());
            pan(point);
        } else {
            Point point = changeCoordinates(evt.getX(), evt.getY());
            jDrawingPanel.drawingPanel(
                panelDraw_LabelSelected,
                panelView_LabelSelected,
                panelGeometry_LabelSelected,
                panelLoads_LabelSelected,
                panelAnalysis_LabelSelected,
                panelResults_LabelSelected
            );

            jDrawingPanel.drawMouseMoved(point.x, point.y);
            jDrawingPanel.drawMouseDragged(point.x, point.y);
            printMouseCoordinates(point.x, point.y);
        }
    }

    /**
     * Método MouseWheelMoved associado ao jDrawingPanel e ao resultsPane
     *
     * @param evt é o evento MouseWheelMoved do rato
     */
    private void mouseWheelMoved(MouseWheelEvent evt) {
        int wheelRotation = evt.getWheelRotation();

        if (wheelRotation > 0) {
            //Diminuir a imagem dos painéis
            zoom("ZoomOut");
        }
        if (wheelRotation < 0) {
            //Ampliar a imagem dos painéis
            zoom("ZoomIn");
        }
    }

    /**
     * Método MousePressed associado ao resultsPane
     *
     * @param evt é o evento MousePressed do rato
     */
    private void resultsPane_MousePressed(MouseEvent evt) {
        if (panelView_LabelSelected == labelsNumeration.jPanelView_Pan) {
            Point point = changeCoordinates(evt.getX(), evt.getY());
            mouse.x = point.x;
            mouse.y = point.y;
        }
    }

    /**
     * Método MouseDragged associado ao resultsPane
     *
     * @param evt é o evento MouseDragged do rato
     */
    private void resultsPane_MouseDragged(MouseEvent evt) {
        if (panelView_LabelSelected == labelsNumeration.jPanelView_Pan) {
            Point point = changeCoordinates(evt.getX(), evt.getY());
            pan(point);
        }
    }

    /**
     * Método MouseMoved associado ao resultsPane
     *
     * @param evt é o evento MouseMoved do rato
     */
    private void resultsPane_MouseMoved(MouseEvent evt) {
        if (panelView_LabelSelected != labelsNumeration.jPanelView_Pan) {
            Point point = changeCoordinates(evt.getX(), evt.getY());
            printMouseCoordinates(point.x, point.y);
        }
    }

    /**
     * Método para obter o apoio elástico selecionado
     *
     * @return
     */
    private String getSupportName() {
        String name = "";

        int index = jLateralPanelGeometry.jCB_Supports.getSelectedIndex();
        if (index < jLateralPanelGeometry.jCB_Supports.getItemCount() - 1) {
            Geometry.ElasticSupport support = elasticSupports.get(index);
            name = support.name;
        }

        return name;
    }

    /**
     * Método para obter o assentamento selecionado
     *
     * @return
     */
    private String getSettlementName() {
        String name = "";

        int index = jLateralPanelGeometry.jCB_Settlements.getSelectedIndex();
        if (index < jLateralPanelGeometry.jCB_Settlements.getItemCount() - 1) {
            Geometry.Settlement settlement = settlements.get(index);
            name = settlement.name;
        }

        return name;
    }

    /**
     * Método para obter o apoio elástico selecionado
     *
     * @return
     */
    private double[] getSupportInstructions() {
        double[] instructions = new double[] { 0.0, 0.0, 0.0 };

        int index = jLateralPanelGeometry.jCB_Supports.getSelectedIndex();
        if (index < jLateralPanelGeometry.jCB_Supports.getItemCount() - 1) {
            Geometry.ElasticSupport support = elasticSupports.get(index);

            instructions[0] = support.stiffnessKx;
            instructions[1] = support.stiffnessKy;
            instructions[2] = support.stiffnessKz;
        }

        return instructions;
    }

    /**
     * Método para obter o assentamento selecionado
     *
     * @return
     */
    private double[] getSettlementInstructions() {
        double[] instructions = new double[] { 0.0, 0.0, 0.0 };

        int index = jLateralPanelGeometry.jCB_Settlements.getSelectedIndex();
        if (index < jLateralPanelGeometry.jCB_Settlements.getItemCount() - 1) {
            Geometry.Settlement settlement = settlements.get(index);

            instructions[0] = settlement.displacementDx;
            instructions[1] = settlement.displacementDy;
            instructions[2] = settlement.rotationRz;
        }

        return instructions;
    }

    /**
     * Método para obter a carga estrutural selecionada
     *
     * @param labelSelected contém o número da carga selecionada
     * @return
     */
    private String[] getLoadInstructions(int labelSelected) {
        String[] instructions = new String[] { "", "", "", "" };

        if (labelSelected == labelsNumeration.jPLoads_ConcentratedLoad) {
            int index = jLateralPanelLoads.jCB_ConLoadsLoad.getSelectedIndex();
            if (index < jLateralPanelLoads.jCB_ConLoadsLoad.getItemCount() - 1) {
                TypesOfLoads.ConcentratedLoad load = concentratedLoads.get(index);

                instructions[0] = "Concentrated Load";
                instructions[1] = load.name;
                instructions[2] = String.valueOf(load.concentratedLoad_Fx);
                instructions[3] = String.valueOf(load.concentratedLoad_Fy);
            }
        }

        if (labelSelected == labelsNumeration.jPLoads_BendingMoment) {
            int index = jLateralPanelLoads.jCB_MomentsLoad.getSelectedIndex();
            if (index < jLateralPanelLoads.jCB_MomentsLoad.getItemCount() - 1) {
                TypesOfLoads.BendingMoment load = bendingMoments.get(index);

                instructions[0] = "Bending Moment";
                instructions[1] = load.name;
                instructions[2] = String.valueOf(load.bendingMoment_M);
                instructions[3] = "";
            }
        }

        if (labelSelected == labelsNumeration.jPLoads_UnifDistLoad) {
            int index = jLateralPanelLoads.jCB_DistrLoadsLoad.getSelectedIndex();
            if (index < jLateralPanelLoads.jCB_DistrLoadsLoad.getItemCount() - 1) {
                TypesOfLoads.DistributedLoad load = distributedLoads.get(index);

                instructions[0] = "Distributed Load";
                instructions[1] = load.name;
                instructions[2] = String.valueOf(load.distributedLoad_Qx);
                instructions[3] = String.valueOf(load.distributedLoad_Qy);
            }
        }

        if (labelSelected == labelsNumeration.jPLoads_DistAxialLoad) {
            int index = jLateralPanelLoads.jCB_AxialLoadsLoad.getSelectedIndex();
            if (index < jLateralPanelLoads.jCB_AxialLoadsLoad.getItemCount() - 1) {
                TypesOfLoads.AxialLoad load = axialLoads.get(index);

                instructions[0] = "Axial Load";
                instructions[1] = load.name;
                instructions[2] = String.valueOf(load.axialLoad_N);
                instructions[3] = "";
            }
        }

        if (labelSelected == labelsNumeration.jPLoads_UnifPlanarLoad) {
            int index = jLateralPanelLoads.jCB_PlanarLoadsLoad.getSelectedIndex();
            if (index < jLateralPanelLoads.jCB_PlanarLoadsLoad.getItemCount() - 1) {
                TypesOfLoads.PlanarLoad load = planarLoads.get(index);

                instructions[0] = "Planar Load";
                instructions[1] = load.name;
                instructions[2] = String.valueOf(load.planarLoad_Qz);
                instructions[3] = "";
            }
        }

        if (labelSelected == labelsNumeration.jPLoads_ThermalLoad) {
            int index = jLateralPanelLoads.jCB_ThermalLoads.getSelectedIndex();
            if (index < jLateralPanelLoads.jCB_ThermalLoads.getItemCount() - 1) {
                TypesOfLoads.ThermalLoad load = thermalLoads.get(index);

                instructions = new String[] { "", "", "", "", "" };
                instructions[0] = "Thermal Load";
                instructions[1] = load.name;
                instructions[2] = String.valueOf(load.thermalLoad_Tzero);
                instructions[3] = String.valueOf(load.thermalLoad_Ttop);
                instructions[4] = String.valueOf(load.thermalLoad_Tbot);
            }
        }

        return instructions;
    }

    /**
     * Método para obter a carga estrutural selecionada
     *
     * @param labelSelected contém o número da carga selecionada
     * @param instructions contém as instruções da carga a desenhar
     * @return
     */
    private boolean loadIsValid(int labelSelected, String[] instructions) {
        boolean validateLoad = false;

        if (instructions != null && !"".equals(instructions[0])) {
            if (labelSelected == labelsNumeration.jPLoads_ConcentratedLoad) {
                double concentratedLoad_Fx = Double.valueOf(instructions[2]);
                double concentratedLoad_Fy = Double.valueOf(instructions[3]);

                if (concentratedLoad_Fx != 0 || concentratedLoad_Fy != 0) {
                    validateLoad = true;
                }
            }

            if (labelSelected == labelsNumeration.jPLoads_BendingMoment) {
                double bendingMoment_M = Double.valueOf(instructions[2]);

                if (bendingMoment_M != 0) {
                    validateLoad = true;
                }
            }

            if (labelSelected == labelsNumeration.jPLoads_UnifDistLoad) {
                double distributedLoad_Qx = Double.valueOf(instructions[2]);
                double distributedLoad_Qy = Double.valueOf(instructions[3]);

                if (distributedLoad_Qx != 0 || distributedLoad_Qy != 0) {
                    validateLoad = true;
                }
            }

            if (labelSelected == labelsNumeration.jPLoads_DistAxialLoad) {
                double axialLoad_N = Double.valueOf(instructions[2]);

                if (axialLoad_N != 0) {
                    validateLoad = true;
                }
            }

            if (labelSelected == labelsNumeration.jPLoads_UnifPlanarLoad) {
                double planarLoad_Qz = Double.valueOf(instructions[2]);

                if (planarLoad_Qz != 0) {
                    validateLoad = true;
                }
            }

            if (labelSelected == labelsNumeration.jPLoads_ThermalLoad) {
                double thermalLoad_Tzero = Double.valueOf(instructions[2]);
                double thermalLoad_Ttop = Double.valueOf(instructions[3]);
                double thermalLoad_Tbot = Double.valueOf(instructions[4]);

                if ("1D".equals(type) && thermalLoad_Tzero != 0) {
                    validateLoad = true;
                }
                if ("Frames".equals(type)) {
                    if (thermalLoad_Ttop != 0 || thermalLoad_Tbot != 0) {
                        validateLoad = true;
                    }
                }
            }
        }

        return validateLoad;
    }

    /**
     * Método para ativar o painel JPBottom
     */
    private void activateJPBottom() {
        jP_Bottom = new PanelBottom(type);
        jP_Bottom.jLabelInformation.setText("");
        informations = new Informations();
        scale = 1;

        jPanel2.removeAll();
        jPanel2.setLayout(new BorderLayout());
        jPanel2.add(jP_Bottom);
        jPanel2.setVisible(true);
        jPanel2.validate();
    }

    /**
     * Método para adicionar o texto "Press F1 for Help"
     */
    private void addHelpJPBottom() {
        javax.swing.JLabel jLabelHelp = new javax.swing.JLabel();
        jLabelHelp.setText("Press F1 for Help.");

        jPanel2.removeAll();
        javax.swing.GroupLayout jPanel2NewLayout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2NewLayout);
        jPanel2NewLayout.setHorizontalGroup(
            jPanel2NewLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel2NewLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelHelp, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(515, Short.MAX_VALUE)
                )
        );
        jPanel2NewLayout.setVerticalGroup(
            jPanel2NewLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabelHelp, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        jPanel2.setVisible(true);
        jPanel2.validate();
        jPanel2.updateUI();
    }

    /**
     * Método para imprimir as coordenadas do rato no JP_Bottom
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    private void printMouseCoordinates(int xPoint, int yPoint) {
        double drawingScale = factor * 1.0;
        if ("Beams".equals(type)) {
            jP_Bottom.jLabelX.setText("X = " + numberForPrint(xPoint / drawingScale) + " m");
            jP_Bottom.jLabelY.setText("Z = " + numberForPrint(yPoint / drawingScale) + " m");
        } else {
            jP_Bottom.jLabelX.setText("X = " + numberForPrint(xPoint / drawingScale) + " m");
            jP_Bottom.jLabelY.setText("Y = " + numberForPrint(yPoint / drawingScale) + " m");
        }
    }

    /**
     * Método para remover o valor das coordenadas no JP_Bottom
     */
    private void removeCoordinates() {
        if ("Beams".equals(type)) {
            jP_Bottom.jLabelX.setText("X = 0,00 m");
            jP_Bottom.jLabelY.setText("Z = 0,00 m");
        } else {
            jP_Bottom.jLabelX.setText("X = 0,00 m");
            jP_Bottom.jLabelY.setText("Y = 0,00 m");
        }
    }

    /**
     * Método para imprimir a escala do desenho
     *
     * @param scale é a escala do desenho
     */
    private void printScale(double scale) {
        jP_Bottom.jLabelZoom.setText("Zoom: " + (int) (Math.round(100 * scale)) + "%");
    }

    /**
     * Método para alterar as coordenadas do rato em função da escala
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     * @return
     */
    private Point changeCoordinates(int xPoint, int yPoint) {
        xPoint = (int) (Math.round(xPoint / scale));
        yPoint = (int) (Math.round(yPoint / scale));
        Point point = new Point(xPoint, yPoint);
        return point;
    }

    /**
     * Método para mover as JScrollBar a partir do eventos do rato
     *
     * @param point contém as coordenadas do rato
     */
    private void pan(Point point) {
        int distH = mouse.x - point.x;
        int distV = mouse.y - point.y;

        JScrollBar horizontal = jScrollPane1.getHorizontalScrollBar();
        JScrollBar vertical = jScrollPane1.getVerticalScrollBar();

        int horizontalPosition = horizontal.getValue();
        int verticalPosition = vertical.getValue();
        int maxHorizontal = horizontal.getMaximum();
        int maxVertical = vertical.getMaximum();

        if ((horizontalPosition + distH) > 0 && (horizontalPosition + distH) < maxHorizontal) {
            horizontal.setValue(horizontalPosition + distH);
        }
        if ((verticalPosition + distV) > 0 && (verticalPosition + distV) < maxVertical) {
            vertical.setValue(verticalPosition + distV);
        }
    }

    /**
     * Método para fazer zoom ao desenho
     *
     * @param zoom indica qual o zoom a aplicar
     */
    private void zoom(String zoom) {
        boolean change = false;

        if ("ZoomIn".equals(zoom) && scale <= 1.8) {
            scale = scale + 0.2;
            change = true;
        }
        if ("ZoomOut".equals(zoom) && scale >= 0.8) {
            scale = scale - 0.2;
            change = true;
        }
        if (change) {
            printScale(scale);

            int dimension = (int) (Math.round(3000 * scale));

            //Aplicação do zoom ao painel jDrawingPanel
            jDrawingPanel.scale(scale, scale);
            jDrawingPanel.setMaximumSize(new java.awt.Dimension(dimension, dimension));
            jDrawingPanel.setPreferredSize(new java.awt.Dimension(dimension, dimension));
            jDrawingPanel.updateUI();

            //Aplicação do zoom ao painel jDrawingPanel
            resultsPane.setScale(scale, scale);
            resultsPane.setMaximumSize(new java.awt.Dimension(dimension, dimension));
            resultsPane.setPreferredSize(new java.awt.Dimension(dimension, dimension));
            resultsPane.updateUI();
        }
    }

    /**
     * Método para resetar todas as funcionalidades ativas
     */
    private void resetAllActivatedFunctions() {
        //Métodos para controlar o número de clicks e o cursor
        resetClicks();
        resetCursor();

        //Reset aos valores das variáveis relativas à função selecionada
        panelDraw_LabelSelected = 0;
        panelView_LabelSelected = 0;
        panelGeometry_LabelSelected = 0;
        panelLoads_LabelSelected = 0;
        panelAnalysis_LabelSelected = 0;
        panelResults_LabelSelected = 0;

        //Alteração do estado do rótulo que estava anteriormente selecionado
        resetSelectedForLabels();
        manageActivatedLabels();
    }

    /**
     * Método para desativar as funcionalidades associadas às labels do menu
     */
    private void resetLabelsNumeration() {
        //Métodos para controlar o número de clicks e o cursor
        jP_Bottom.jLabelInformation.setText("");
        resetClicks();
        resetCursor();

        //Reset aos valores das variáveis relativas à função selecionada
        panelDraw_LabelSelected = 0;
        panelView_LabelSelected = 0;
        panelGeometry_LabelSelected = 0;
        panelLoads_LabelSelected = 0;
        panelAnalysis_LabelSelected = 0;
        panelResults_LabelSelected = 0;

        //Alteração do estado do rótulo que estava anteriormente selecionado
        resetSelectedForLabels();
    }

    /**
     * Método para desativar as funcionalidades associadas às labels do menu
     */
    private void resetLabelsNumeration(LabelMouseEvents labelSelected) {
        //Métodos para controlar o número de clicks e o cursor
        jP_Bottom.jLabelInformation.setText("");
        resetClicks();
        resetCursor();

        //Reset aos valores das variáveis relativas à função selecionada
        panelDraw_LabelSelected = 0;
        panelView_LabelSelected = 0;
        panelGeometry_LabelSelected = 0;
        panelLoads_LabelSelected = 0;
        panelAnalysis_LabelSelected = 0;
        panelResults_LabelSelected = 0;

        //Alteração do estado do rótulo que estava anteriormente selecionado
        setSelectedForLabels(labelSelected);
    }

    /**
     * Método para desativar as funcionalidades associadas às labels do menu
     *
     * @param tool é o nome da ferramenta selecionada
     */
    private void resetLabelsNumeration(String tool) {
        jP_Bottom.jLabelInformation.setText("");
        resetSelectedForLabels();
        resetClicks();

        panelDraw_LabelSelected = 0;
        panelView_LabelSelected = 0;
        panelGeometry_LabelSelected = 0;
        panelLoads_LabelSelected = 0;
        panelAnalysis_LabelSelected = 0;
        panelResults_LabelSelected = 0;
    }

    /**
     * Método para definir o cursor padrão a ser chamado após selecão
     */
    private void resetCursor() {
        jDrawingPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        resultsPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        moveCursor = false;
        handCursor = false;
    }

    /**
     * Método para alterar o estado do rótulo anteriomente selecionado
     *
     * @param labelSelected é a label que vai ser selecionada
     */
    private void setSelectedForLabels(LabelMouseEvents labelSelected) {
        resetSelectedForLabels();
        labelSelected.setSelected(true);
    }

    /**
     * Método para alterar o estado do rótulo anteriomente selecionado
     */
    private void resetSelectedForLabels() {
        LabelMouseEvents label;

        label = (LabelMouseEvents) jLabelPoint;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelLine;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelTriangle;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelRectangle;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelQuadrilateral;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelMove;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelSelect;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelPan;
        label.setSelected(false);

        label = (LabelMouseEvents) jLabelDimensionLine;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelVSimpleSupport;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelHSimpleSupport;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelPinnedSupport;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelHSliderSupport;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelVSliderSupport;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelFixedSupport;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelElasticSupport;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelSettlements;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelCutSupports;
        label.setSelected(false);

        label = (LabelMouseEvents) jLabelConcentratedLoad;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelBendingMoment;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelUnifDistLoad;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelDistAxialLoad;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelUnifPlanarLoad;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelThermalLoad;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelSelfWeight;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelCutLoad;
        label.setSelected(false);

        label = (LabelMouseEvents) jLabelVectorF;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelMatrixK;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelVectorDisplacements;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelProperties;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelSupportReactions;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelNodalForces;
        label.setSelected(false);
        label = (LabelMouseEvents) jLabelNodalStresses;
        label.setSelected(false);
    }

    /**
     * Método para gerir os rótulos ativados e desativados
     */
    private void manageActivatedLabels() {
        jLabelLine.setEnabled(true);
        jLabelTriangle.setEnabled(true);
        jLabelRectangle.setEnabled(true);
        jLabelQuadrilateral.setEnabled(true);

        jLabelNodes.setEnabled(true);
        jLabelNumberOfNodes.setEnabled(true);
        jLabelMesh.setEnabled(true);
        jLabelVSimpleSupport.setEnabled(true);
        jLabelHSimpleSupport.setEnabled(true);
        jLabelPinnedSupport.setEnabled(true);
        jLabelHSliderSupport.setEnabled(true);
        jLabelVSliderSupport.setEnabled(true);
        jLabelFixedSupport.setEnabled(true);
        jLabelElasticSupport.setEnabled(true);
        jLabelSettlements.setEnabled(true);

        jLabelConcentratedLoad.setEnabled(true);
        jLabelBendingMoment.setEnabled(true);
        jLabelUnifDistLoad.setEnabled(true);
        jLabelDistAxialLoad.setEnabled(true);
        jLabelUnifPlanarLoad.setEnabled(true);
        jLabelThermalLoad.setEnabled(true);
        jLabelSelfWeight.setEnabled(true);

        jLabelTheory.setEnabled(true);

        jLabelNodalForces.setEnabled(true);
        jLabelDiagrams.setEnabled(true);
        jLabelIsovalues.setEnabled(true);
        jLabelMaps.setEnabled(true);
        jLabelPrincipalStresses.setEnabled(true);

        jLateralPanelResults.jSlider1.setValue(50);
        jLateralPanelResults.jRBNodesMesh.setSelected(false);
        jLateralPanelResults.jRBNumberingNodes.setSelected(false);
        jLateralPanelResults.jRBNumberingNodes.setEnabled(false);
        jLateralPanelResults.jRBNodesDeformed.setSelected(false);

        if ("1D".equals(type)) {
            jLabelTriangle.setEnabled(false);
            jLabelRectangle.setEnabled(false);
            jLabelQuadrilateral.setEnabled(false);

            jLabelNodes.setEnabled(false);
            jLabelNumberOfNodes.setEnabled(false);
            jLabelMesh.setEnabled(false);
            jLabelHSliderSupport.setEnabled(false);
            jLabelVSliderSupport.setEnabled(false);
            jLabelFixedSupport.setEnabled(false);

            jLabelBendingMoment.setEnabled(false);
            jLabelUnifDistLoad.setEnabled(false);
            jLabelUnifPlanarLoad.setEnabled(false);
            jLabelSelfWeight.setEnabled(false);

            jLabelTheory.setEnabled(false);

            jLabelIsovalues.setEnabled(false);
            jLabelMaps.setEnabled(false);
            jLabelPrincipalStresses.setEnabled(false);
        }

        if ("2D".equals(type)) {
            jLabelLine.setEnabled(false);

            jLabelNodes.setEnabled(false);
            jLabelHSliderSupport.setEnabled(false);
            jLabelVSliderSupport.setEnabled(false);
            jLabelFixedSupport.setEnabled(false);

            jLabelBendingMoment.setEnabled(false);
            jLabelDistAxialLoad.setEnabled(false);
            jLabelUnifPlanarLoad.setEnabled(false);
            jLabelThermalLoad.setEnabled(false);
            jLabelSelfWeight.setEnabled(false);

            jLabelDiagrams.setEnabled(false);
        }

        if ("Beams".equals(type)) {
            jLabelTriangle.setEnabled(false);
            jLabelRectangle.setEnabled(false);
            jLabelQuadrilateral.setEnabled(false);

            jLabelNodes.setEnabled(false);
            jLabelHSimpleSupport.setEnabled(false);
            jLabelPinnedSupport.setEnabled(false);
            jLabelVSliderSupport.setEnabled(false);
            jLabelFixedSupport.setEnabled(false);

            jLabelDistAxialLoad.setEnabled(false);
            jLabelUnifPlanarLoad.setEnabled(false);
            jLabelThermalLoad.setEnabled(false);

            jLabelDiagrams.setEnabled(false);
            jLabelIsovalues.setEnabled(false);
            jLabelMaps.setEnabled(false);
            jLabelPrincipalStresses.setEnabled(false);
        }

        if ("Frames".equals(type)) {
            jLabelTriangle.setEnabled(false);
            jLabelRectangle.setEnabled(false);
            jLabelQuadrilateral.setEnabled(false);

            jLabelNodes.setEnabled(false);
            jLabelNumberOfNodes.setEnabled(false);

            jLabelDistAxialLoad.setEnabled(false);
            jLabelUnifPlanarLoad.setEnabled(false);

            jLabelTheory.setEnabled(false);

            jLabelIsovalues.setEnabled(false);
            jLabelMaps.setEnabled(false);
            jLabelPrincipalStresses.setEnabled(false);
        }

        if ("Grids".equals(type)) {
            jLabelTriangle.setEnabled(false);
            jLabelRectangle.setEnabled(false);
            jLabelQuadrilateral.setEnabled(false);

            jLabelNodes.setEnabled(false);
            jLabelNumberOfNodes.setEnabled(false);
            jLabelHSimpleSupport.setEnabled(false);
            jLabelPinnedSupport.setEnabled(false);
            jLabelVSliderSupport.setEnabled(false);
            jLabelFixedSupport.setEnabled(false);
            jLabelElasticSupport.setEnabled(false);
            jLabelSettlements.setEnabled(false);

            jLabelBendingMoment.setEnabled(false);
            jLabelDistAxialLoad.setEnabled(false);
            jLabelUnifPlanarLoad.setEnabled(false);
            jLabelThermalLoad.setEnabled(false);

            jLabelTheory.setEnabled(false);

            jLabelIsovalues.setEnabled(false);
            jLabelMaps.setEnabled(false);
            jLabelPrincipalStresses.setEnabled(false);
        }

        if ("Slabs".equals(type)) {
            jLabelLine.setEnabled(false);
            jLabelTriangle.setEnabled(false);
            jLabelQuadrilateral.setEnabled(false);

            jLabelNodes.setEnabled(false);
            jLabelHSimpleSupport.setEnabled(false);
            jLabelPinnedSupport.setEnabled(false);
            jLabelVSliderSupport.setEnabled(false);
            jLabelFixedSupport.setEnabled(false);
            jLabelElasticSupport.setEnabled(false);
            jLabelSettlements.setEnabled(false);

            jLabelBendingMoment.setEnabled(false);
            jLabelUnifDistLoad.setEnabled(false);
            jLabelDistAxialLoad.setEnabled(false);
            jLabelThermalLoad.setEnabled(false);

            jLabelDiagrams.setEnabled(false);
            jLabelPrincipalStresses.setEnabled(false);
        }
    }

    /**
     * Método para formatar uma string para coversão do tipo double
     *
     * @param string é a string a ser formatada
     * @return
     */
    private static String formatString(String string) {
        string = string.trim();
        string = string.replace(",", ".");

        return string;
    }

    /**
     * Método para formatar um número para impressão nos campos de texto
     *
     * @param string é a string a ser formatada
     * @return
     */
    private static String numberForPrint(double number) {
        String string = String.valueOf(number);
        string = string.replace(".", ",");

        return string;
    }

    /**
     * Método que retorna o número de nós selecionados
     *
     * @param string contém a informação relativa ao número de nós
     * @return
     */
    private static int clearText(String string) {
        string = string.replace("Nodes", "");
        string = string.replace("Points", "");
        string = string.replace("Point", "");
        string = string.trim();

        return Integer.valueOf(string);
    }

    /**
     * Método para iniciar o conteúdo do painel Geometry
     */
    private void initializeGeometry() {
        //Declaração dos eventos dos componentes do painel Geometry

        jLateralPanelGeometry.jCB_Sections.addActionListener(this::jCB_Sections);
        jLateralPanelGeometry.jB_SectionsEdit.addActionListener(this::jB_SectionsEdit);
        jLateralPanelGeometry.jB_SectionsSave.addActionListener(this::jB_SectionsSave);
        jLateralPanelGeometry.jB_SectionsDelete.addActionListener(this::jB_SectionsDelete);
        jLateralPanelGeometry.jCB_Materials.addActionListener(this::jCB_Materials);
        jLateralPanelGeometry.jB_MaterialsEdit.addActionListener(this::jB_MaterialsEdit);
        jLateralPanelGeometry.jB_MaterialsSave.addActionListener(this::jB_MaterialsSave);
        jLateralPanelGeometry.jB_MaterialsDelete.addActionListener(this::jB_MaterialsDelete);
        jLateralPanelGeometry.jCB_MeshLines.addActionListener(this::jCB_MeshLines);
        jLateralPanelGeometry.jCB_MeshTriangles.addActionListener(this::jCB_MeshTriangles);
        jLateralPanelGeometry.jCB_MeshRectQuadril.addActionListener(this::jCB_MeshRectQuadril);
        jLateralPanelGeometry.jCB_Supports.addActionListener(this::jCB_Supports);
        jLateralPanelGeometry.jB_SupportsEdit.addActionListener(this::jB_SupportsEdit);
        jLateralPanelGeometry.jB_SupportsSave.addActionListener(this::jB_SupportsSave);
        jLateralPanelGeometry.jB_SupportsDelete.addActionListener(this::jB_SupportsDelete);
        jLateralPanelGeometry.jCB_Settlements.addActionListener(this::jCB_Settlements);
        jLateralPanelGeometry.jB_SettlementsEdit.addActionListener(this::jB_SettlementsEdit);
        jLateralPanelGeometry.jB_SettlementsSave.addActionListener(this::jB_SettlementsSave);
        jLateralPanelGeometry.jB_SettlementsDelete.addActionListener(this::jB_SettlementsDelete);

        /*
         * Adicionar evento ActionPerformed ao botão para fechar o painel
         */
        jLateralPanelGeometry.jButtonClose.addActionListener(this::jButtonClose_jLateralPanel);
    }

    /**
     * Metodo para fornecer o número de nós selecionados para os elementos finitos
     *
     * @return
     */
    private Geometry.Nodes getNumberOfSelectedNodes() {
        int index;
        Geometry.Nodes numberNodes = geometry.new Nodes(2, 3, 4);

        //Atribuição do número de nós aos elementos finitos lineares
        index = jLateralPanelGeometry.jCB_NodesLines.getSelectedIndex();
        switch (index) {
            case 0:
                numberNodes.barsNumberNodes = 2;
                break;
            case 1:
                numberNodes.barsNumberNodes = 3;
                break;
            case 2:
                numberNodes.barsNumberNodes = 4;
                break;
            default:
                numberNodes.barsNumberNodes = 2;
        }

        //Atribuição do número de nós aos elementos finitos triangulares
        index = jLateralPanelGeometry.jCB_NodesTriangles.getSelectedIndex();
        switch (index) {
            case 0:
                numberNodes.trianglesNumberNodes = 3;
                break;
            case 1:
                numberNodes.trianglesNumberNodes = 6;
                break;
            default:
                numberNodes.trianglesNumberNodes = 3;
        }

        //Atribuição do número de nós aos elementos finitos quadrilaterais
        index = jLateralPanelGeometry.jCB_NodesQuadrilaterals.getSelectedIndex();
        switch (index) {
            case 0:
                numberNodes.quadrilateralsNumberNodes = 4;
                break;
            case 1:
                numberNodes.quadrilateralsNumberNodes = 8;
                break;
            case 2:
                numberNodes.quadrilateralsNumberNodes = 9;
                break;
            default:
                numberNodes.quadrilateralsNumberNodes = 4;
        }

        return numberNodes;
    }

    /**
     * Método para atualizar o conteúdo do painel Geometry
     */
    private void geometryUpdateAllModels() {
        //Atualização do conteúdo do painel lateral Sections
        if (!sections.isEmpty()) {
            jLateralPanelGeometry.jTF_SectionsInercia.setText(numberForPrint(sections.get(0).inertia));
            jLateralPanelGeometry.jTF_SectionsTorsion.setText(numberForPrint(sections.get(0).torsion));
            jLateralPanelGeometry.jTF_SectionsArea.setText(numberForPrint(sections.get(0).area));
            jLateralPanelGeometry.jTF_SectionsThickness.setText(numberForPrint(sections.get(0).thickness));

            //Instruções para atualizar a lista de secções da caixa de combinação
            int size = sections.size();
            String[] modelA = new String[size];

            for (int i = 0; i < size; i++) {
                modelA[i] = sections.get(i).name;
            }

            jLateralPanelGeometry.setModeljCB_Sections(modelA);
        } else {
            jLateralPanelGeometry.jTF_SectionsInercia.setText("");
            jLateralPanelGeometry.jTF_SectionsTorsion.setText("");
            jLateralPanelGeometry.jTF_SectionsArea.setText("");
            jLateralPanelGeometry.jTF_SectionsThickness.setText("");

            String[] modelA = new String[0];
            jLateralPanelGeometry.setModeljCB_Sections(modelA);
        }

        //Atualização do conteúdo do painel lateral Materials
        if (!materials.isEmpty()) {
            jLateralPanelGeometry.jTF_MaterialsElasticity.setText(numberForPrint(materials.get(0).elasticity));
            jLateralPanelGeometry.jTF_MaterialsPoisson.setText(numberForPrint(materials.get(0).poisson));
            jLateralPanelGeometry.jTF_MaterialsThermal.setText(numberForPrint(materials.get(0).thermal));

            //Instruções para atualizar a lista de materiais da caixa de combinação
            int size = materials.size();
            String[] modelA = new String[size];

            for (int i = 0; i < size; i++) {
                modelA[i] = materials.get(i).material;
            }

            jLateralPanelGeometry.setModeljCB_Materials(modelA);
        } else {
            geometryCounter[1] = 2;
            materials.add(geometry.new Material("Concrete", 30.0, 0.2, 0.000010));
            materials.add(geometry.new Material("Steel", 210.0, 0.3, 0.000012));

            String[] model = new String[2];
            for (int i = 0; i < materials.size(); i++) {
                model[i] = materials.get(i).material;
            }

            jLateralPanelGeometry.setModeljCB_Materials(model);
            jLateralPanelGeometry.jCB_Materials.setSelectedIndex(0);
        }

        //Atualização do conteúdo do painel lateral Elastic Supports
        if (!elasticSupports.isEmpty()) {
            jLateralPanelGeometry.jTF_SupportsKx.setText(numberForPrint(elasticSupports.get(0).stiffnessKx));
            jLateralPanelGeometry.jTF_SupportsKy.setText(numberForPrint(elasticSupports.get(0).stiffnessKy));
            jLateralPanelGeometry.jTF_SupportsKz.setText(numberForPrint(elasticSupports.get(0).stiffnessKz));

            //Instruções para atualizar a lista de apoios da caixa de combinação
            int size = elasticSupports.size();
            String[] modelA = new String[size];

            for (int i = 0; i < size; i++) {
                modelA[i] = elasticSupports.get(i).name;
            }

            jLateralPanelGeometry.setModeljCB_Supports(modelA);
        } else {
            jLateralPanelGeometry.jTF_SupportsKx.setText("");
            jLateralPanelGeometry.jTF_SupportsKy.setText("");
            jLateralPanelGeometry.jTF_SupportsKz.setText("");

            String[] modelA = new String[0];
            jLateralPanelGeometry.setModeljCB_Supports(modelA);
        }

        //Atualização do conteúdo do painel lateral Settlements
        if (!settlements.isEmpty()) {
            jLateralPanelGeometry.jTF_SettlementsDx.setText(numberForPrint(settlements.get(0).displacementDx));
            jLateralPanelGeometry.jTF_SettlementsDy.setText(numberForPrint(settlements.get(0).displacementDy));
            jLateralPanelGeometry.jTF_SettlementsRz.setText(numberForPrint(settlements.get(0).rotationRz));

            //Instruções para atualizar a lista de assentamentos da caixa de combinação
            int size = settlements.size();
            String[] modelA = new String[size];

            for (int i = 0; i < size; i++) {
                modelA[i] = settlements.get(i).name;
            }

            jLateralPanelGeometry.setModeljCB_Settlements(modelA);
        } else {
            jLateralPanelGeometry.jTF_SettlementsDx.setText("");
            jLateralPanelGeometry.jTF_SettlementsDy.setText("");
            jLateralPanelGeometry.jTF_SettlementsRz.setText("");

            String[] modelA = new String[0];
            jLateralPanelGeometry.setModeljCB_Settlements(modelA);
        }
    }

    /*
     * Eventos associados ao paineis laterais do painel Geometry
     */

    /**
     * Método para selecionar uma secção do elemento finito
     */
    private void jCB_Sections(ActionEvent evt) {
        int size = jLateralPanelGeometry.jCB_Sections.getItemCount();
        int index = jLateralPanelGeometry.jCB_Sections.getSelectedIndex();

        if (index < size - 1) {
            Geometry.Section section = sections.get(index);

            jLateralPanelGeometry.jTF_SectionsInercia.setText(numberForPrint(section.inertia));
            jLateralPanelGeometry.jTF_SectionsTorsion.setText(numberForPrint(section.torsion));
            jLateralPanelGeometry.jTF_SectionsArea.setText(numberForPrint(section.area));
            jLateralPanelGeometry.jTF_SectionsThickness.setText(numberForPrint(section.thickness));
        } else {
            geometryCounter[0] = geometryCounter[0] + 1;
            String sectionName = "Section " + String.valueOf(geometryCounter[0]);
            sections.add(geometry.new Section(sectionName, 0.0, 0.0, 0.0, 0.0));

            String[] model = new String[size];
            for (int i = 0; i < sections.size(); i++) {
                model[i] = sections.get(i).name;
            }

            jLateralPanelGeometry.setModeljCB_Sections(model);
            jLateralPanelGeometry.jCB_Sections.setSelectedIndex(size - 1);
            jB_SectionsEdit(null);
        }
    }

    /**
     * Método do botão para editar a secção do elemento finito
     */
    private void jB_SectionsEdit(ActionEvent evt) {
        int size = jLateralPanelGeometry.jCB_Sections.getItemCount();
        int index = jLateralPanelGeometry.jCB_Sections.getSelectedIndex();

        if (index < size - 1) {
            jLateralPanelGeometry.jCB_Sections.setEnabled(false);
            jLateralPanelGeometry.jB_SectionsEdit.setEnabled(false);
            jLateralPanelGeometry.jB_SectionsSave.setEnabled(true);
            jLateralPanelGeometry.jB_SectionsDelete.setEnabled(false);

            switch (this.type) {
                case "1D":
                    jLateralPanelGeometry.jTF_SectionsArea.setEnabled(true);
                    break;
                case "2D":
                    jLateralPanelGeometry.jTF_SectionsThickness.setEnabled(true);
                    break;
                case "Beams":
                    jLateralPanelGeometry.jTF_SectionsInercia.setEnabled(true);
                    jLateralPanelGeometry.jTF_SectionsArea.setEnabled(true);
                    jLateralPanelGeometry.jTF_SectionsThickness.setEnabled(true);
                    break;
                case "Frames":
                    jLateralPanelGeometry.jTF_SectionsInercia.setEnabled(true);
                    jLateralPanelGeometry.jTF_SectionsArea.setEnabled(true);
                    jLateralPanelGeometry.jTF_SectionsThickness.setEnabled(true);
                    break;
                case "Grids":
                    jLateralPanelGeometry.jTF_SectionsInercia.setEnabled(true);
                    jLateralPanelGeometry.jTF_SectionsTorsion.setEnabled(true);
                    jLateralPanelGeometry.jTF_SectionsArea.setEnabled(true);
                    jLateralPanelGeometry.jTF_SectionsThickness.setEnabled(true);
                    break;
                case "Slabs":
                    jLateralPanelGeometry.jTF_SectionsThickness.setEnabled(true);
                    break;
            }

            jCB_Sections(null);
        }
    }

    /**
     * Método do botão para salvar a secção do elemento finito
     */
    private void jB_SectionsSave(ActionEvent evt) {
        jLateralPanelGeometry.jCB_Sections.setEnabled(true);
        jLateralPanelGeometry.jB_SectionsEdit.setEnabled(true);
        jLateralPanelGeometry.jB_SectionsSave.setEnabled(false);
        jLateralPanelGeometry.jB_SectionsDelete.setEnabled(true);

        jLateralPanelGeometry.jTF_SectionsInercia.setEnabled(false);
        jLateralPanelGeometry.jTF_SectionsTorsion.setEnabled(false);
        jLateralPanelGeometry.jTF_SectionsArea.setEnabled(false);
        jLateralPanelGeometry.jTF_SectionsThickness.setEnabled(false);

        try {
            int index = jLateralPanelGeometry.jCB_Sections.getSelectedIndex();
            Geometry.Section section = sections.get(index);

            String sectionsInercia = formatString(jLateralPanelGeometry.jTF_SectionsInercia.getText());
            String sectionsTorsion = formatString(jLateralPanelGeometry.jTF_SectionsTorsion.getText());
            String sectionsArea = formatString(jLateralPanelGeometry.jTF_SectionsArea.getText());
            String sectionsThickness = formatString(jLateralPanelGeometry.jTF_SectionsThickness.getText());

            double inercia = Double.parseDouble(sectionsInercia);
            double torsion = Double.parseDouble(sectionsTorsion);
            double area = Double.parseDouble(sectionsArea);
            double thickness = Double.parseDouble(sectionsThickness);

            if (inercia >= 0 && torsion >= 0 && area >= 0 && thickness >= 0) {
                section.editSection(inercia, torsion, area, thickness);
            } else {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "The properties entered are invalid!");
            }
        } catch (NumberFormatException e) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "The properties entered are invalid!");
        }

        jCB_Sections(null);

        //Chamada do método para requerer o focus para a janela
        requestFocus();
    }

    /**
     * Método do botão para eliminar a secção do elemento finito
     */
    private void jB_SectionsDelete(ActionEvent evt) {
        int size = jLateralPanelGeometry.jCB_Sections.getItemCount();
        int index = jLateralPanelGeometry.jCB_Sections.getSelectedIndex();

        if (index < size - 1) {
            //Instruções para atualizar a lista de objetos
            jDrawingPanel.deleteSection(sections.get(index).name);

            //Instruções para remover a secção do painel lateral
            sections.remove(index);
            String[] model = new String[sections.size()];
            for (int i = 0; i < sections.size(); i++) {
                model[i] = sections.get(i).name;
            }

            jLateralPanelGeometry.setModeljCB_Sections(model);

            if (size > 2) {
                Geometry.Section section = sections.get(0);

                jLateralPanelGeometry.jTF_SectionsInercia.setText(numberForPrint(section.inertia));
                jLateralPanelGeometry.jTF_SectionsTorsion.setText(numberForPrint(section.torsion));
                jLateralPanelGeometry.jTF_SectionsArea.setText(numberForPrint(section.area));
                jLateralPanelGeometry.jTF_SectionsThickness.setText(numberForPrint(section.thickness));
            } else {
                jLateralPanelGeometry.jTF_SectionsInercia.setText("");
                jLateralPanelGeometry.jTF_SectionsTorsion.setText("");
                jLateralPanelGeometry.jTF_SectionsArea.setText("");
                jLateralPanelGeometry.jTF_SectionsThickness.setText("");
            }
        } else {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Do not exist anymore sections to eliminate.\nThe list is empty.");
        }
    }

    /**
     * Método para selecionar o material dos elementos finitos
     */
    private void jCB_Materials(ActionEvent evt) {
        int size = jLateralPanelGeometry.jCB_Materials.getItemCount();
        int index = jLateralPanelGeometry.jCB_Materials.getSelectedIndex();

        if (index < size - 1) {
            Geometry.Material material = materials.get(index);

            jLateralPanelGeometry.jTF_MaterialsElasticity.setText(numberForPrint(material.elasticity));
            jLateralPanelGeometry.jTF_MaterialsPoisson.setText(numberForPrint(material.poisson));
            jLateralPanelGeometry.jTF_MaterialsThermal.setText(numberForPrint(material.thermal));
        } else {
            geometryCounter[1] = geometryCounter[1] + 1;
            String materialName = "Other " + String.valueOf(geometryCounter[1]);
            materials.add(geometry.new Material(materialName, 0.0, 0.0, 0.0));

            String[] model = new String[size];
            for (int i = 0; i < materials.size(); i++) {
                model[i] = materials.get(i).material;
            }

            jLateralPanelGeometry.setModeljCB_Materials(model);
            jLateralPanelGeometry.jCB_Materials.setSelectedIndex(size - 1);
            jB_MaterialsEdit(null);
        }
    }

    /**
     * Método do botão para editar o material do elemento finito
     */
    private void jB_MaterialsEdit(ActionEvent evt) {
        int size = jLateralPanelGeometry.jCB_Materials.getItemCount();
        int index = jLateralPanelGeometry.jCB_Materials.getSelectedIndex();

        if (index < size - 1) {
            jLateralPanelGeometry.jCB_Materials.setEnabled(false);
            jLateralPanelGeometry.jB_MaterialsEdit.setEnabled(false);
            jLateralPanelGeometry.jB_MaterialsSave.setEnabled(true);
            jLateralPanelGeometry.jB_MaterialsDelete.setEnabled(false);

            jLateralPanelGeometry.jTF_MaterialsElasticity.setEnabled(true);
            jLateralPanelGeometry.jTF_MaterialsPoisson.setEnabled(true);
            jLateralPanelGeometry.jTF_MaterialsThermal.setEnabled(true);

            jCB_Materials(null);
        }
    }

    /**
     * Método do botão para salvar o material do elemento finito
     */
    private void jB_MaterialsSave(ActionEvent evt) {
        jLateralPanelGeometry.jCB_Materials.setEnabled(true);
        jLateralPanelGeometry.jB_MaterialsEdit.setEnabled(true);
        jLateralPanelGeometry.jB_MaterialsSave.setEnabled(false);
        jLateralPanelGeometry.jB_MaterialsDelete.setEnabled(true);

        jLateralPanelGeometry.jTF_MaterialsElasticity.setEnabled(false);
        jLateralPanelGeometry.jTF_MaterialsPoisson.setEnabled(false);
        jLateralPanelGeometry.jTF_MaterialsThermal.setEnabled(false);

        int index = jLateralPanelGeometry.jCB_Materials.getSelectedIndex();
        Geometry.Material material = materials.get(index);

        if (material != null) {
            String mElasticity = formatString(jLateralPanelGeometry.jTF_MaterialsElasticity.getText());
            String mPoisson = formatString(jLateralPanelGeometry.jTF_MaterialsPoisson.getText());
            String mThermal = formatString(jLateralPanelGeometry.jTF_MaterialsThermal.getText());

            try {
                double elasticity = Double.parseDouble(mElasticity);
                double poisson = Double.parseDouble(mPoisson);
                double thermal = Double.parseDouble(mThermal);

                if (elasticity > 0 && poisson > 0 && thermal > 0) {
                    material.editMaterial(elasticity, poisson, thermal);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "The properties entered are invalid!");
                }
            } catch (NumberFormatException e) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "The properties entered are invalid!");
            }
        }

        jCB_Materials(null);

        //Chamada do método para requerer o focus para a janela
        requestFocus();
    }

    /**
     * Método do botão para eliminar o material do elemento finito
     */
    private void jB_MaterialsDelete(ActionEvent evt) {
        int size = jLateralPanelGeometry.jCB_Materials.getItemCount();
        int index = jLateralPanelGeometry.jCB_Materials.getSelectedIndex();

        if (index < size - 1) {
            //Instruções para remover a secção do painel lateral
            materials.remove(index);
            String[] model = new String[materials.size()];
            for (int i = 0; i < materials.size(); i++) {
                model[i] = materials.get(i).material;
            }

            jLateralPanelGeometry.setModeljCB_Materials(model);

            if (size > 2) {
                Geometry.Material material = materials.get(0);

                jLateralPanelGeometry.jTF_MaterialsElasticity.setText(numberForPrint(material.elasticity));
                jLateralPanelGeometry.jTF_MaterialsPoisson.setText(numberForPrint(material.poisson));
                jLateralPanelGeometry.jTF_MaterialsThermal.setText(numberForPrint(material.thermal));
            } else {
                jLateralPanelGeometry.jTF_MaterialsElasticity.setText("");
                jLateralPanelGeometry.jTF_MaterialsPoisson.setText("");
                jLateralPanelGeometry.jTF_MaterialsThermal.setText("");
            }
        } else {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Do not exist anymore materials to eliminate.\nThe list is empty.");
        }
    }

    /**
     * Método do botão para definir o refinamento da malha de elementos finitos
     */
    private void jCB_MeshLines(ActionEvent evt) {
        if (!jPWelcome.isShowing()) {
            int index = jLateralPanelGeometry.jCB_MeshLines.getSelectedIndex();
            jDrawingPanel.meshRefinement(index, "Lines");
        }
    }

    /**
     * Método do botão para definir o refinamento da malha de elementos finitos
     */
    private void jCB_MeshTriangles(ActionEvent evt) {
        if (!jPWelcome.isShowing()) {
            int index = jLateralPanelGeometry.jCB_MeshTriangles.getSelectedIndex();
            jDrawingPanel.meshRefinement(index, "Triangles");
        }
    }

    /**
     * Método do botão para definir o refinamento da malha de elementos finitos
     */
    private void jCB_MeshRectQuadril(ActionEvent evt) {
        if (!jPWelcome.isShowing()) {
            int index = jLateralPanelGeometry.jCB_MeshRectQuadril.getSelectedIndex();
            jDrawingPanel.meshRefinement(index, "Quadrilaterals");
        }
    }

    /**
     * Método para selecionar um apoio elástico
     */
    private void jCB_Supports(ActionEvent evt) {
        int size = jLateralPanelGeometry.jCB_Supports.getItemCount();
        int index = jLateralPanelGeometry.jCB_Supports.getSelectedIndex();

        if (index < size - 1) {
            Geometry.ElasticSupport support = elasticSupports.get(index);

            jLateralPanelGeometry.jTF_SupportsKx.setText(numberForPrint(support.stiffnessKx));
            jLateralPanelGeometry.jTF_SupportsKy.setText(numberForPrint(support.stiffnessKy));
            jLateralPanelGeometry.jTF_SupportsKz.setText(numberForPrint(support.stiffnessKz));
        } else {
            geometryCounter[2] = geometryCounter[2] + 1;
            String supportName = "Spring " + String.valueOf(geometryCounter[2]);
            elasticSupports.add(geometry.new ElasticSupport(supportName, 0.0, 0.0, 0.0));

            String[] model = new String[size];
            for (int i = 0; i < elasticSupports.size(); i++) {
                model[i] = elasticSupports.get(i).name;
            }

            jLateralPanelGeometry.setModeljCB_Supports(model);
            jLateralPanelGeometry.jCB_Supports.setSelectedIndex(size - 1);
            jB_SupportsEdit(null);
        }
    }

    /**
     * Método do botão para editar o apoio elástico selecionado
     */
    private void jB_SupportsEdit(ActionEvent evt) {
        int size = jLateralPanelGeometry.jCB_Supports.getItemCount();
        int index = jLateralPanelGeometry.jCB_Supports.getSelectedIndex();

        if (index < size - 1) {
            jLateralPanelGeometry.jCB_Supports.setEnabled(false);
            jLateralPanelGeometry.jB_SupportsEdit.setEnabled(false);
            jLateralPanelGeometry.jB_SupportsSave.setEnabled(true);
            jLateralPanelGeometry.jB_SupportsDelete.setEnabled(false);

            if ("1D".equals(type) || "2D".equals(type)) {
                jLateralPanelGeometry.jTF_SupportsKx.setEnabled(true);
                jLateralPanelGeometry.jTF_SupportsKy.setEnabled(true);
            }
            if ("Beams".equals(type)) {
                jLateralPanelGeometry.jTF_SupportsKy.setEnabled(true);
                jLateralPanelGeometry.jTF_SupportsKz.setEnabled(true);
            }
            if ("Frames".equals(type)) {
                jLateralPanelGeometry.jTF_SupportsKx.setEnabled(true);
                jLateralPanelGeometry.jTF_SupportsKy.setEnabled(true);
                jLateralPanelGeometry.jTF_SupportsKz.setEnabled(true);
            }

            jCB_Supports(null);
        }
    }

    /**
     * Método do botão para salvar o apoio elástico selecionado
     */
    private void jB_SupportsSave(ActionEvent evt) {
        jLateralPanelGeometry.jCB_Supports.setEnabled(true);
        jLateralPanelGeometry.jB_SupportsEdit.setEnabled(true);
        jLateralPanelGeometry.jB_SupportsSave.setEnabled(false);
        jLateralPanelGeometry.jB_SupportsDelete.setEnabled(true);

        jLateralPanelGeometry.jTF_SupportsKx.setEnabled(false);
        jLateralPanelGeometry.jTF_SupportsKy.setEnabled(false);
        jLateralPanelGeometry.jTF_SupportsKz.setEnabled(false);

        try {
            int index = jLateralPanelGeometry.jCB_Supports.getSelectedIndex();
            Geometry.ElasticSupport support = elasticSupports.get(index);

            String supportKx = formatString(jLateralPanelGeometry.jTF_SupportsKx.getText());
            String supportKy = formatString(jLateralPanelGeometry.jTF_SupportsKy.getText());
            String supportKz = formatString(jLateralPanelGeometry.jTF_SupportsKz.getText());

            double Kx = Double.parseDouble(supportKx);
            double Ky = Double.parseDouble(supportKy);
            double Kz = Double.parseDouble(supportKz);

            if (Kx >= 0 && Ky >= 0 && Kz >= 0) {
                support.editSupport(Kx, Ky, Kz);
                jDrawingPanel.updateElasticSupport(support.name, new double[] { Kx, Ky, Kz });
            } else {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "The properties entered are invalid!");
            }
        } catch (NumberFormatException e) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "The properties entered are invalid!");
        }

        jCB_Supports(null);

        //Chamada do método para requerer o focus para a janela
        requestFocus();
    }

    /**
     * Método do botão para eliminar o apoio elástico selecionado
     */
    private void jB_SupportsDelete(ActionEvent evt) {
        int size = jLateralPanelGeometry.jCB_Supports.getItemCount();
        int index = jLateralPanelGeometry.jCB_Supports.getSelectedIndex();

        if (index < size - 1) {
            //Instruções para atualizar a lista de objetos
            jDrawingPanel.deleteElasticSupport(elasticSupports.get(index).name);

            //Instruções para remover o apoio elástico do painel lateral
            elasticSupports.remove(index);
            String[] model = new String[elasticSupports.size()];
            for (int i = 0; i < elasticSupports.size(); i++) {
                model[i] = elasticSupports.get(i).name;
            }

            jLateralPanelGeometry.setModeljCB_Supports(model);

            if (size > 2) {
                Geometry.ElasticSupport support = elasticSupports.get(0);

                jLateralPanelGeometry.jTF_SupportsKx.setText(numberForPrint(support.stiffnessKx));
                jLateralPanelGeometry.jTF_SupportsKy.setText(numberForPrint(support.stiffnessKy));
                jLateralPanelGeometry.jTF_SupportsKz.setText(numberForPrint(support.stiffnessKz));
            } else {
                jLateralPanelGeometry.jTF_SupportsKx.setText("");
                jLateralPanelGeometry.jTF_SupportsKy.setText("");
                jLateralPanelGeometry.jTF_SupportsKz.setText("");
            }
        } else {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Do not exist anymore supports to eliminate.\nThe list is empty.");
        }
    }

    /**
     * Método para selecionar um assentamento de apoio
     */
    private void jCB_Settlements(ActionEvent evt) {
        int size = jLateralPanelGeometry.jCB_Settlements.getItemCount();
        int index = jLateralPanelGeometry.jCB_Settlements.getSelectedIndex();

        if (index < size - 1) {
            Geometry.Settlement settlement = settlements.get(index);

            jLateralPanelGeometry.jTF_SettlementsDx.setText(numberForPrint(settlement.displacementDx));
            jLateralPanelGeometry.jTF_SettlementsDy.setText(numberForPrint(settlement.displacementDy));
            jLateralPanelGeometry.jTF_SettlementsRz.setText(numberForPrint(settlement.rotationRz));
        } else {
            geometryCounter[3] = geometryCounter[3] + 1;
            String settlementName = "Settl. " + String.valueOf(geometryCounter[3]);
            settlements.add(geometry.new Settlement(settlementName, 0.0, 0.0, 0.0));

            String[] model = new String[size];
            for (int i = 0; i < settlements.size(); i++) {
                model[i] = settlements.get(i).name;
            }

            jLateralPanelGeometry.setModeljCB_Settlements(model);
            jLateralPanelGeometry.jCB_Settlements.setSelectedIndex(size - 1);
            jB_SettlementsEdit(null);
        }
    }

    /**
     * Método do botão para editar o assentamento de apoio selecionado
     */
    private void jB_SettlementsEdit(ActionEvent evt) {
        int size = jLateralPanelGeometry.jCB_Settlements.getItemCount();
        int index = jLateralPanelGeometry.jCB_Settlements.getSelectedIndex();

        if (index < size - 1) {
            jLateralPanelGeometry.jCB_Settlements.setEnabled(false);
            jLateralPanelGeometry.jB_SettlementsEdit.setEnabled(false);
            jLateralPanelGeometry.jB_SettlementsSave.setEnabled(true);
            jLateralPanelGeometry.jB_SettlementsDelete.setEnabled(false);

            if ("1D".equals(type) || "2D".equals(type)) {
                jLateralPanelGeometry.jTF_SettlementsDx.setEnabled(true);
                jLateralPanelGeometry.jTF_SettlementsDy.setEnabled(true);
            }
            if ("Beams".equals(type)) {
                jLateralPanelGeometry.jTF_SettlementsDy.setEnabled(true);
                jLateralPanelGeometry.jTF_SettlementsRz.setEnabled(true);
            }
            if ("Frames".equals(type)) {
                jLateralPanelGeometry.jTF_SettlementsDx.setEnabled(true);
                jLateralPanelGeometry.jTF_SettlementsDy.setEnabled(true);
                jLateralPanelGeometry.jTF_SettlementsRz.setEnabled(true);
            }

            jCB_Settlements(null);
        }
    }

    /**
     * Método do botão para salvar o assentamento de apoio selecionado
     */
    private void jB_SettlementsSave(ActionEvent evt) {
        jLateralPanelGeometry.jCB_Settlements.setEnabled(true);
        jLateralPanelGeometry.jB_SettlementsEdit.setEnabled(true);
        jLateralPanelGeometry.jB_SettlementsSave.setEnabled(false);
        jLateralPanelGeometry.jB_SettlementsDelete.setEnabled(true);

        jLateralPanelGeometry.jTF_SettlementsDx.setEnabled(false);
        jLateralPanelGeometry.jTF_SettlementsDy.setEnabled(false);
        jLateralPanelGeometry.jTF_SettlementsRz.setEnabled(false);

        try {
            int index = jLateralPanelGeometry.jCB_Settlements.getSelectedIndex();
            Geometry.Settlement settlement = settlements.get(index);

            String settlementsDx = formatString(jLateralPanelGeometry.jTF_SettlementsDx.getText());
            String settlementsDy = formatString(jLateralPanelGeometry.jTF_SettlementsDy.getText());
            String settlementsRz = formatString(jLateralPanelGeometry.jTF_SettlementsRz.getText());

            double Dx = Double.parseDouble(settlementsDx);
            double Dy = Double.parseDouble(settlementsDy);
            double Rz = Double.parseDouble(settlementsRz);

            if (Dx != 0 || Dy != 0 || Rz != 0) {
                if (jDrawingPanel.isPossibleToUpdateSettlement(settlement.name, new double[] { Dx, Dy, Rz })) {
                    settlement.editSettlement(Dx, Dy, Rz);
                    jDrawingPanel.updateSettlements(settlement.name, new double[] { Dx, Dy, Rz });
                } else {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "The values entered are invalid!");
                }
            } else {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "The values entered are invalid!");
            }
        } catch (NumberFormatException e) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "The values entered are invalid!");
        }

        jCB_Settlements(null);

        //Chamada do método para requerer o focus para a janela
        requestFocus();
    }

    /**
     * Método do botão para eliminar o assentamento de apoio selecionado
     */
    private void jB_SettlementsDelete(ActionEvent evt) {
        int size = jLateralPanelGeometry.jCB_Settlements.getItemCount();
        int index = jLateralPanelGeometry.jCB_Settlements.getSelectedIndex();

        if (index < size - 1) {
            //Instruções para atualizar a lista de objetos
            jDrawingPanel.deleteSettlements(settlements.get(index).name);

            //Instruções para remover o assentamento do painel lateral
            settlements.remove(index);
            String[] model = new String[settlements.size()];
            for (int i = 0; i < settlements.size(); i++) {
                model[i] = settlements.get(i).name;
            }

            jLateralPanelGeometry.setModeljCB_Settlements(model);

            if (size > 2) {
                Geometry.Settlement settlement = settlements.get(0);

                jLateralPanelGeometry.jTF_SettlementsDx.setText(numberForPrint(settlement.displacementDx));
                jLateralPanelGeometry.jTF_SettlementsDy.setText(numberForPrint(settlement.displacementDy));
                jLateralPanelGeometry.jTF_SettlementsRz.setText(numberForPrint(settlement.rotationRz));
            } else {
                jLateralPanelGeometry.jTF_SettlementsDx.setText("");
                jLateralPanelGeometry.jTF_SettlementsDy.setText("");
                jLateralPanelGeometry.jTF_SettlementsRz.setText("");
            }
        } else {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Do not exist anymore settlements to eliminate.\nThe list is empty.");
        }
    }

    /**
     * Método para iniciar o conteúdo do painel Loads
     */
    private void initializeLoads() {
        //Declaração dos eventos dos componentes co painel Geometry

        jLateralPanelLoads.jCB_ConLoadsLoad.addActionListener(this::jCB_ConLoadsLoad);
        jLateralPanelLoads.jB_ConLoadsEdit.addActionListener(this::jB_ConLoadsEdit);
        jLateralPanelLoads.jB_ConLoadsSave.addActionListener(this::jB_ConLoadsSave);
        jLateralPanelLoads.jB_ConLoadsDelete.addActionListener(this::jB_ConLoadsDelete);

        jLateralPanelLoads.jCB_MomentsLoad.addActionListener(this::jCB_MomentsLoad);
        jLateralPanelLoads.jB_MomentsEdit.addActionListener(this::jB_MomentsEdit);
        jLateralPanelLoads.jB_MomentsSave.addActionListener(this::jB_MomentsSave);
        jLateralPanelLoads.jB_MomentsDelete.addActionListener(this::jB_MomentsDelete);

        jLateralPanelLoads.jCB_DistrLoadsLoad.addActionListener(this::jCB_DistrLoads);
        jLateralPanelLoads.jB_DistrLoadsEdit.addActionListener(this::jB_DistrLoadsEdit);
        jLateralPanelLoads.jB_DistrLoadsSave.addActionListener(this::jB_DistrLoadsSave);
        jLateralPanelLoads.jB_DistrLoadsDelete.addActionListener(this::jB_DistrLoadsDelete);

        jLateralPanelLoads.jCB_AxialLoadsLoad.addActionListener(this::jCB_AxialLoads);
        jLateralPanelLoads.jB_AxialLoadsEdit.addActionListener(this::jB_AxialLoadsEdit);
        jLateralPanelLoads.jB_AxialLoadsSave.addActionListener(this::jB_AxialLoadsSave);
        jLateralPanelLoads.jB_AxialLoadsDelete.addActionListener(this::jB_AxialLoadsDelete);

        jLateralPanelLoads.jCB_PlanarLoadsLoad.addActionListener(this::jCB_PlanarLoads);
        jLateralPanelLoads.jB_PlanarLoadsEdit.addActionListener(this::jB_PlanarLoadsEdit);
        jLateralPanelLoads.jB_PlanarLoadsSave.addActionListener(this::jB_PlanarLoadsSave);
        jLateralPanelLoads.jB_PlanarLoadsDelete.addActionListener(this::jB_PlanarLoadsDelete);

        jLateralPanelLoads.jCB_ThermalLoads.addActionListener(this::jCB_ThermalLoads);
        jLateralPanelLoads.jB_ThermalLoadsEdit.addActionListener(this::jB_ThermalLoadsEdit);
        jLateralPanelLoads.jB_ThermalLoadsSave.addActionListener(this::jB_ThermalLoadsSave);
        jLateralPanelLoads.jB_ThermalLoadsDelete.addActionListener(this::jB_ThermalLoadsDelete);

        jLateralPanelLoads.jCB_SelfWeight.addActionListener(this::jCB_SelfWeight);
        jLateralPanelLoads.jB_SelfWeightEdit.addActionListener(this::jB_SelfWeightEdit);
        jLateralPanelLoads.jB_SelfWeightSave.addActionListener(this::jB_SelfWeightSave);
        jLateralPanelLoads.jB_SelfWeightDelete.addActionListener(this::jB_SelfWeightDelete);

        /*
         * Adicionar evento ActionPerformed ao botão para fechar o painel
         */
        jLateralPanelLoads.jButtonClose.addActionListener(this::jButtonClose_jLateralPanel);
    }

    /**
     * Método para atualizar as cargas estruturais do painel lateral
     */
    private void loadsUpdateAllModels() {
        int size;

        //Instruções relativas a cargas estruturais concentradas
        size = concentratedLoads.size();
        String[] modelA = new String[size];
        for (int i = 0; i < size; i++) {
            modelA[i] = concentratedLoads.get(i).name;
        }

        if (size > 0) {
            TypesOfLoads.ConcentratedLoad load = concentratedLoads.get(0);
            jLateralPanelLoads.jTF_ConLoadsFx.setText(numberForPrint(load.concentratedLoad_Fx));
            jLateralPanelLoads.jTF_ConLoadsFy.setText(numberForPrint(load.concentratedLoad_Fy));
        } else {
            jLateralPanelLoads.jTF_ConLoadsFx.setText("");
            jLateralPanelLoads.jTF_ConLoadsFy.setText("");
        }

        //Instruções relativas a momentos fletores
        size = bendingMoments.size();
        String[] modelB = new String[size];
        for (int i = 0; i < size; i++) {
            modelB[i] = bendingMoments.get(i).name;
        }

        if (size > 0) {
            TypesOfLoads.BendingMoment load = bendingMoments.get(0);
            jLateralPanelLoads.jTF_MomentsM.setText(numberForPrint(load.bendingMoment_M));
        } else {
            jLateralPanelLoads.jTF_MomentsM.setText("");
        }

        //Instruções relativas a cargas estruturais distribuídas
        size = distributedLoads.size();
        String[] modelC = new String[size];
        for (int i = 0; i < size; i++) {
            modelC[i] = distributedLoads.get(i).name;
        }

        if (size > 0) {
            TypesOfLoads.DistributedLoad load = distributedLoads.get(0);
            jLateralPanelLoads.jTF_DistrLoadsQx.setText(numberForPrint(load.distributedLoad_Qx));
            jLateralPanelLoads.jTF_DistrLoadsQy.setText(numberForPrint(load.distributedLoad_Qy));
        } else {
            jLateralPanelLoads.jTF_DistrLoadsQx.setText("");
            jLateralPanelLoads.jTF_DistrLoadsQy.setText("");
        }

        //Instruções relativas a cargas estruturais axiais
        size = axialLoads.size();
        String[] modelD = new String[size];
        for (int i = 0; i < size; i++) {
            modelD[i] = axialLoads.get(i).name;
        }

        if (size > 0) {
            TypesOfLoads.AxialLoad load = axialLoads.get(0);
            jLateralPanelLoads.jTF_AxialLoadsN.setText(numberForPrint(load.axialLoad_N));
        } else {
            jLateralPanelLoads.jTF_AxialLoadsN.setText("");
        }

        //Instruções relativas a carregamentos de superfície
        size = planarLoads.size();
        String[] modelE = new String[size];
        for (int i = 0; i < size; i++) {
            modelE[i] = planarLoads.get(i).name;
        }

        if (size > 0) {
            TypesOfLoads.PlanarLoad load = planarLoads.get(0);
            jLateralPanelLoads.jTF_PlanarLoadsQz.setText(numberForPrint(load.planarLoad_Qz));
        } else {
            jLateralPanelLoads.jTF_PlanarLoadsQz.setText("");
        }

        //Instruções relativas a variações de temperatura
        size = thermalLoads.size();
        String[] modelF = new String[size];
        for (int i = 0; i < size; i++) {
            modelF[i] = thermalLoads.get(i).name;
        }

        if (size > 0) {
            TypesOfLoads.ThermalLoad load = thermalLoads.get(0);
            jLateralPanelLoads.jTF_ThermalLoadsTzero.setText(numberForPrint(load.thermalLoad_Tzero));
            jLateralPanelLoads.jTF_ThermalLoadsTtop.setText(numberForPrint(load.thermalLoad_Ttop));
            jLateralPanelLoads.jTF_ThermalLoadsTbot.setText(numberForPrint(load.thermalLoad_Tbot));
        } else {
            jLateralPanelLoads.jTF_ThermalLoadsTzero.setText("");
            jLateralPanelLoads.jTF_ThermalLoadsTtop.setText("");
            jLateralPanelLoads.jTF_ThermalLoadsTbot.setText("");
        }

        //Instruções relativas a carregamentos do tipo peso próprio
        size = selfWeights.size();
        String[] modelG = new String[size];
        for (int i = 0; i < size; i++) {
            modelG[i] = selfWeights.get(i).name;
        }

        if (size > 0) {
            TypesOfLoads.SelfWeight load = selfWeights.get(0);
            jLateralPanelLoads.jTF_SelfWeight.setText(numberForPrint(load.selfWeight_S));
        } else {
            jLateralPanelLoads.jTF_SelfWeight.setText("");
        }

        jLateralPanelLoads.updateAllModels(modelA, modelB, modelC, modelD, modelE, modelF, modelG);
    }

    /**
     * Eventos associados ao paineis laterais do painel Loads
     */

    /**
     * Método para selecionar uma carga concentrada
     */
    private void jCB_ConLoadsLoad(ActionEvent evt) {
        int size = jLateralPanelLoads.jCB_ConLoadsLoad.getItemCount();
        int index = jLateralPanelLoads.jCB_ConLoadsLoad.getSelectedIndex();

        if (index < size - 1) {
            TypesOfLoads.ConcentratedLoad load = concentratedLoads.get(index);

            jLateralPanelLoads.jTF_ConLoadsFx.setText(numberForPrint(load.concentratedLoad_Fx));
            jLateralPanelLoads.jTF_ConLoadsFy.setText(numberForPrint(load.concentratedLoad_Fy));
        } else {
            loadCounter[0] = loadCounter[0] + 1;
            String loadName = "Load C" + String.valueOf(loadCounter[0]);
            concentratedLoads.add(loads.new ConcentratedLoad(loadName, 0.0, 0.0));

            String[] model = new String[size];
            for (int i = 0; i < concentratedLoads.size(); i++) {
                model[i] = concentratedLoads.get(i).name;
            }

            jLateralPanelLoads.setModeljCB_ConLoads(model);
            jLateralPanelLoads.jCB_ConLoadsLoad.setSelectedIndex(size - 1);
            jB_ConLoadsEdit(null);
        }
    }

    /**
     * Método do botão para editar a carga concentrada selecionada
     */
    private void jB_ConLoadsEdit(ActionEvent evt) {
        int size = jLateralPanelLoads.jCB_ConLoadsLoad.getItemCount();
        int index = jLateralPanelLoads.jCB_ConLoadsLoad.getSelectedIndex();

        if (index < size - 1) {
            jLateralPanelLoads.jB_ConLoadsEdit.setEnabled(false);
            jLateralPanelLoads.jB_ConLoadsSave.setEnabled(true);
            jLateralPanelLoads.jB_ConLoadsDelete.setEnabled(false);
            jLateralPanelLoads.jCB_ConLoadsLoad.setEnabled(false);

            if (!"Beams".equals(type) && !"Grids".equals(type) && !"Slabs".equals(type)) {
                jLateralPanelLoads.jTF_ConLoadsFx.setEnabled(true);
            }
            jLateralPanelLoads.jTF_ConLoadsFy.setEnabled(true);
            jCB_ConLoadsLoad(null);
        }
    }

    /**
     * Método do botão para salvar a carga concentrada selecionada
     */
    private void jB_ConLoadsSave(ActionEvent evt) {
        jLateralPanelLoads.jB_ConLoadsEdit.setEnabled(true);
        jLateralPanelLoads.jB_ConLoadsSave.setEnabled(false);
        jLateralPanelLoads.jB_ConLoadsDelete.setEnabled(true);
        jLateralPanelLoads.jCB_ConLoadsLoad.setEnabled(true);

        jLateralPanelLoads.jTF_ConLoadsFx.setEnabled(false);
        jLateralPanelLoads.jTF_ConLoadsFy.setEnabled(false);

        try {
            int index = jLateralPanelLoads.jCB_ConLoadsLoad.getSelectedIndex();
            TypesOfLoads.ConcentratedLoad load = concentratedLoads.get(index);

            String loadFx = formatString(jLateralPanelLoads.jTF_ConLoadsFx.getText());
            String loadFy = formatString(jLateralPanelLoads.jTF_ConLoadsFy.getText());

            double Fx = Double.parseDouble(loadFx);
            double Fy = Double.parseDouble(loadFy);

            if (Fx != 0 || Fy != 0) {
                load.editLoad(Fx, Fy);

                //Instruções para atualizar o desenho da carga no jDrawingPanel
                String[] loadInstructions = new String[4];

                loadInstructions[0] = "Concentrated Load";
                loadInstructions[1] = load.name;
                loadInstructions[2] = String.valueOf(load.concentratedLoad_Fx);
                loadInstructions[3] = String.valueOf(load.concentratedLoad_Fy);

                jDrawingPanel.updateLoad(loadInstructions);
            } else {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Input values ​​are invalid!");
            }
        } catch (NumberFormatException e) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Input values ​​are invalid!");
        }

        jCB_ConLoadsLoad(null);

        //Chamada do método para requerer o focus para a janela
        requestFocus();
    }

    /**
     * Método para eliminar a carga selecionada
     */
    private void jB_ConLoadsDelete(ActionEvent evt) {
        int size = jLateralPanelLoads.jCB_ConLoadsLoad.getItemCount();
        int index = jLateralPanelLoads.jCB_ConLoadsLoad.getSelectedIndex();

        if (index < size - 1) {
            //Instruções para remover o carga do painel de desenho
            TypesOfLoads.ConcentratedLoad deleteLoad = concentratedLoads.get(index);
            String loadType = "Concentrated Load";
            String loadName = deleteLoad.name;
            jDrawingPanel.deleteLoad(loadType, loadName);

            //Instruções para remover a carga do painel lateral
            concentratedLoads.remove(index);
            String[] model = new String[concentratedLoads.size()];
            for (int i = 0; i < concentratedLoads.size(); i++) {
                model[i] = concentratedLoads.get(i).name;
            }

            jLateralPanelLoads.setModeljCB_ConLoads(model);

            if (size > 2) {
                TypesOfLoads.ConcentratedLoad load = concentratedLoads.get(0);
                jLateralPanelLoads.jTF_ConLoadsFx.setText(numberForPrint(load.concentratedLoad_Fx));
                jLateralPanelLoads.jTF_ConLoadsFy.setText(numberForPrint(load.concentratedLoad_Fy));
            } else {
                jLateralPanelLoads.jTF_ConLoadsFx.setText("");
                jLateralPanelLoads.jTF_ConLoadsFy.setText("");
            }
        } else {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Do not exist anymore structural loads to eliminate.\nThe list is empty.");
        }
    }

    /**
     * Método para selecionar um momento fletor
     */
    private void jCB_MomentsLoad(ActionEvent evt) {
        int size = jLateralPanelLoads.jCB_MomentsLoad.getItemCount();
        int index = jLateralPanelLoads.jCB_MomentsLoad.getSelectedIndex();

        if (index < size - 1) {
            TypesOfLoads.BendingMoment load = bendingMoments.get(index);

            jLateralPanelLoads.jTF_MomentsM.setText(numberForPrint(load.bendingMoment_M));
        } else {
            loadCounter[1] = loadCounter[1] + 1;
            String loadName = "Load M" + String.valueOf(loadCounter[1]);
            bendingMoments.add(loads.new BendingMoment(loadName, 0.0));

            String[] model = new String[size];
            for (int i = 0; i < bendingMoments.size(); i++) {
                model[i] = bendingMoments.get(i).name;
            }

            jLateralPanelLoads.setModeljCB_MomentsLoad(model);
            jLateralPanelLoads.jCB_MomentsLoad.setSelectedIndex(size - 1);
            jB_MomentsEdit(null);
        }
    }

    /**
     * Método do botão para editar o momento fletor selecionado
     */
    private void jB_MomentsEdit(ActionEvent evt) {
        int size = jLateralPanelLoads.jCB_MomentsLoad.getItemCount();
        int index = jLateralPanelLoads.jCB_MomentsLoad.getSelectedIndex();

        if (index < size - 1) {
            jLateralPanelLoads.jB_MomentsEdit.setEnabled(false);
            jLateralPanelLoads.jB_MomentsSave.setEnabled(true);
            jLateralPanelLoads.jB_MomentsDelete.setEnabled(false);
            jLateralPanelLoads.jCB_MomentsLoad.setEnabled(false);

            jLateralPanelLoads.jTF_MomentsM.setEnabled(true);
            jCB_MomentsLoad(null);
        }
    }

    /**
     * Método do botão para salvar o momento fletor selecionado
     */
    private void jB_MomentsSave(ActionEvent evt) {
        jLateralPanelLoads.jB_MomentsEdit.setEnabled(true);
        jLateralPanelLoads.jB_MomentsSave.setEnabled(false);
        jLateralPanelLoads.jB_MomentsDelete.setEnabled(true);
        jLateralPanelLoads.jCB_MomentsLoad.setEnabled(true);

        jLateralPanelLoads.jTF_MomentsM.setEnabled(false);

        try {
            int index = jLateralPanelLoads.jCB_MomentsLoad.getSelectedIndex();
            TypesOfLoads.BendingMoment load = bendingMoments.get(index);

            String loadM = formatString(jLateralPanelLoads.jTF_MomentsM.getText());

            double M = Double.parseDouble(loadM);

            if (M != 0) {
                load.editLoad(M);

                //Instruções para atualizar o desenho da carga no jDrawingPanel
                String[] loadInstructions = new String[4];

                loadInstructions[0] = "Bending Moment";
                loadInstructions[1] = load.name;
                loadInstructions[2] = String.valueOf(load.bendingMoment_M);
                loadInstructions[3] = "";

                jDrawingPanel.updateLoad(loadInstructions);
            } else {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Input values ​​are invalid!");
            }
        } catch (NumberFormatException e) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Input values ​​are invalid!");
        }

        jCB_MomentsLoad(null);

        //Chamada do método para requerer o focus para a janela
        requestFocus();
    }

    /**
     * Método para eliminar a carga selecionada
     */
    private void jB_MomentsDelete(ActionEvent evt) {
        int size = jLateralPanelLoads.jCB_MomentsLoad.getItemCount();
        int index = jLateralPanelLoads.jCB_MomentsLoad.getSelectedIndex();

        if (index < size - 1) {
            //Instruções para remover o carga do painel de desenho
            TypesOfLoads.BendingMoment deleteLoad = bendingMoments.get(index);
            String loadType = "Bending Moment";
            String loadName = deleteLoad.name;
            jDrawingPanel.deleteLoad(loadType, loadName);

            //Instruções para remover a carga do painel lateral
            bendingMoments.remove(index);
            String[] model = new String[bendingMoments.size()];
            for (int i = 0; i < bendingMoments.size(); i++) {
                model[i] = bendingMoments.get(i).name;
            }

            jLateralPanelLoads.setModeljCB_MomentsLoad(model);

            if (size > 2) {
                TypesOfLoads.BendingMoment load = bendingMoments.get(0);
                jLateralPanelLoads.jTF_MomentsM.setText(numberForPrint(load.bendingMoment_M));
            } else {
                jLateralPanelLoads.jTF_MomentsM.setText("");
            }
        } else {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Do not exist anymore structural loads to eliminate.\nThe list is empty.");
        }
    }

    /**
     * Método para selecionar uma carga distribuída
     */
    private void jCB_DistrLoads(ActionEvent evt) {
        int size = jLateralPanelLoads.jCB_DistrLoadsLoad.getItemCount();
        int index = jLateralPanelLoads.jCB_DistrLoadsLoad.getSelectedIndex();

        if (index < size - 1) {
            TypesOfLoads.DistributedLoad load = distributedLoads.get(index);

            jLateralPanelLoads.jTF_DistrLoadsQx.setText(numberForPrint(load.distributedLoad_Qx));
            jLateralPanelLoads.jTF_DistrLoadsQy.setText(numberForPrint(load.distributedLoad_Qy));
        } else {
            loadCounter[2] = loadCounter[2] + 1;
            String loadName = "Load D" + String.valueOf(loadCounter[2]);
            distributedLoads.add(loads.new DistributedLoad(loadName, 0.0, 0.0));

            String[] model = new String[size];
            for (int i = 0; i < distributedLoads.size(); i++) {
                model[i] = distributedLoads.get(i).name;
            }

            jLateralPanelLoads.setModeljCB_DistrLoads(model);
            jLateralPanelLoads.jCB_DistrLoadsLoad.setSelectedIndex(size - 1);
            jB_DistrLoadsEdit(null);
        }
    }

    /**
     * Método do botão para editar a carga distribuída selecionada
     */
    private void jB_DistrLoadsEdit(ActionEvent evt) {
        int size = jLateralPanelLoads.jCB_DistrLoadsLoad.getItemCount();
        int index = jLateralPanelLoads.jCB_DistrLoadsLoad.getSelectedIndex();

        if (index < size - 1) {
            jLateralPanelLoads.jB_DistrLoadsEdit.setEnabled(false);
            jLateralPanelLoads.jB_DistrLoadsSave.setEnabled(true);
            jLateralPanelLoads.jB_DistrLoadsDelete.setEnabled(false);
            jLateralPanelLoads.jCB_DistrLoadsLoad.setEnabled(false);

            if (!"Beams".equals(type) && !"Grids".equals(type)) {
                jLateralPanelLoads.jTF_DistrLoadsQx.setEnabled(true);
            }
            jLateralPanelLoads.jTF_DistrLoadsQy.setEnabled(true);
            jCB_DistrLoads(null);
        }
    }

    /**
     * Método do botão para salvar a carga distribuída selecionada
     */
    private void jB_DistrLoadsSave(ActionEvent evt) {
        jLateralPanelLoads.jB_DistrLoadsEdit.setEnabled(true);
        jLateralPanelLoads.jB_DistrLoadsSave.setEnabled(false);
        jLateralPanelLoads.jB_DistrLoadsDelete.setEnabled(true);
        jLateralPanelLoads.jCB_DistrLoadsLoad.setEnabled(true);

        jLateralPanelLoads.jTF_DistrLoadsQx.setEnabled(false);
        jLateralPanelLoads.jTF_DistrLoadsQy.setEnabled(false);

        try {
            int index = jLateralPanelLoads.jCB_DistrLoadsLoad.getSelectedIndex();
            TypesOfLoads.DistributedLoad load = distributedLoads.get(index);

            String loadQx = formatString(jLateralPanelLoads.jTF_DistrLoadsQx.getText());
            String loadQy = formatString(jLateralPanelLoads.jTF_DistrLoadsQy.getText());

            double Qx = Double.parseDouble(loadQx);
            double Qy = Double.parseDouble(loadQy);

            if (Qx != 0 || Qy != 0) {
                load.editLoad(Qx, Qy);

                //Instruções para atualizar o desenho da carga no jDrawingPanel
                String[] loadInstructions = new String[4];

                loadInstructions[0] = "Distributed Load";
                loadInstructions[1] = load.name;
                loadInstructions[2] = String.valueOf(load.distributedLoad_Qx);
                loadInstructions[3] = String.valueOf(load.distributedLoad_Qy);

                jDrawingPanel.updateLoad(loadInstructions);
            } else {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Input values ​​are invalid!");
            }
        } catch (NumberFormatException e) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Input values ​​are invalid!");
        }

        jCB_DistrLoads(null);

        //Chamada do método para requerer o focus para a janela
        requestFocus();
    }

    /**
     * Método para eliminar a carga selecionada
     */
    private void jB_DistrLoadsDelete(ActionEvent evt) {
        int size = jLateralPanelLoads.jCB_DistrLoadsLoad.getItemCount();
        int index = jLateralPanelLoads.jCB_DistrLoadsLoad.getSelectedIndex();

        if (index < size - 1) {
            //Instruções para remover o carga do painel de desenho
            TypesOfLoads.DistributedLoad deleteLoad = distributedLoads.get(index);
            String loadType = "Distributed Load";
            String loadName = deleteLoad.name;
            jDrawingPanel.deleteLoad(loadType, loadName);

            //Instruções para remover a carga do painel lateral
            distributedLoads.remove(index);
            String[] model = new String[distributedLoads.size()];
            for (int i = 0; i < distributedLoads.size(); i++) {
                model[i] = distributedLoads.get(i).name;
            }

            jLateralPanelLoads.setModeljCB_DistrLoads(model);

            if (size > 2) {
                TypesOfLoads.DistributedLoad load = distributedLoads.get(0);
                jLateralPanelLoads.jTF_DistrLoadsQx.setText(numberForPrint(load.distributedLoad_Qx));
                jLateralPanelLoads.jTF_DistrLoadsQy.setText(numberForPrint(load.distributedLoad_Qy));
            } else {
                jLateralPanelLoads.jTF_DistrLoadsQx.setText("");
                jLateralPanelLoads.jTF_DistrLoadsQy.setText("");
            }
        } else {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Do not exist anymore structural loads to eliminate.\nThe list is empty.");
        }
    }

    /**
     * Método para selecionar uma carga axial
     */
    private void jCB_AxialLoads(ActionEvent evt) {
        int size = jLateralPanelLoads.jCB_AxialLoadsLoad.getItemCount();
        int index = jLateralPanelLoads.jCB_AxialLoadsLoad.getSelectedIndex();

        if (index < size - 1) {
            TypesOfLoads.AxialLoad load = axialLoads.get(index);

            jLateralPanelLoads.jTF_AxialLoadsN.setText(numberForPrint(load.axialLoad_N));
        } else {
            loadCounter[3] = loadCounter[3] + 1;
            String loadName = "Load A" + String.valueOf(loadCounter[3]);
            axialLoads.add(loads.new AxialLoad(loadName, 0.0));

            String[] model = new String[size];
            for (int i = 0; i < axialLoads.size(); i++) {
                model[i] = axialLoads.get(i).name;
            }

            jLateralPanelLoads.setModeljCB_AxialLoads(model);
            jLateralPanelLoads.jCB_AxialLoadsLoad.setSelectedIndex(size - 1);
            jB_AxialLoadsEdit(null);
        }
    }

    /**
     * Método do botão para editar a carga axial selecionada
     */
    private void jB_AxialLoadsEdit(ActionEvent evt) {
        int size = jLateralPanelLoads.jCB_AxialLoadsLoad.getItemCount();
        int index = jLateralPanelLoads.jCB_AxialLoadsLoad.getSelectedIndex();

        if (index < size - 1) {
            jLateralPanelLoads.jB_AxialLoadsEdit.setEnabled(false);
            jLateralPanelLoads.jB_AxialLoadsSave.setEnabled(true);
            jLateralPanelLoads.jB_AxialLoadsDelete.setEnabled(false);
            jLateralPanelLoads.jCB_AxialLoadsLoad.setEnabled(false);

            jLateralPanelLoads.jTF_AxialLoadsN.setEnabled(true);
            jCB_AxialLoads(null);
        }
    }

    /**
     * Método do botão para salvar a carga axial selecionada
     */
    private void jB_AxialLoadsSave(ActionEvent evt) {
        jLateralPanelLoads.jB_AxialLoadsEdit.setEnabled(true);
        jLateralPanelLoads.jB_AxialLoadsSave.setEnabled(false);
        jLateralPanelLoads.jB_AxialLoadsDelete.setEnabled(true);
        jLateralPanelLoads.jCB_AxialLoadsLoad.setEnabled(true);

        jLateralPanelLoads.jTF_AxialLoadsN.setEnabled(false);

        try {
            int index = jLateralPanelLoads.jCB_AxialLoadsLoad.getSelectedIndex();
            TypesOfLoads.AxialLoad load = axialLoads.get(index);

            String loadN = formatString(jLateralPanelLoads.jTF_AxialLoadsN.getText());

            double N = Double.parseDouble(loadN);

            if (N != 0) {
                load.editLoad(N);

                //Instruções para atualizar o desenho da carga no jDrawingPanel
                String[] loadInstructions = new String[4];

                loadInstructions[0] = "Axial Load";
                loadInstructions[1] = load.name;
                loadInstructions[2] = String.valueOf(load.axialLoad_N);
                loadInstructions[3] = "";

                jDrawingPanel.updateLoad(loadInstructions);
            } else {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Input values ​​are invalid!");
            }
        } catch (NumberFormatException e) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Input values ​​are invalid!");
        }

        jCB_AxialLoads(null);

        //Chamada do método para requerer o focus para a janela
        requestFocus();
    }

    /**
     * Método para eliminar a carga selecionda
     */
    private void jB_AxialLoadsDelete(ActionEvent evt) {
        int size = jLateralPanelLoads.jCB_AxialLoadsLoad.getItemCount();
        int index = jLateralPanelLoads.jCB_AxialLoadsLoad.getSelectedIndex();

        if (index < size - 1) {
            //Instruções para remover o carga do painel de desenho
            TypesOfLoads.AxialLoad deleteLoad = axialLoads.get(index);
            String loadType = "Axial Load";
            String loadName = deleteLoad.name;
            jDrawingPanel.deleteLoad(loadType, loadName);

            //Instruções para remover a carga do painel lateral
            axialLoads.remove(index);
            String[] model = new String[axialLoads.size()];
            for (int i = 0; i < axialLoads.size(); i++) {
                model[i] = axialLoads.get(i).name;
            }

            jLateralPanelLoads.setModeljCB_AxialLoads(model);

            if (size > 2) {
                TypesOfLoads.AxialLoad load = axialLoads.get(0);
                jLateralPanelLoads.jTF_AxialLoadsN.setText(numberForPrint(load.axialLoad_N));
            } else {
                jLateralPanelLoads.jTF_AxialLoadsN.setText("");
            }
        } else {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Do not exist anymore structural loads to eliminate.\nThe list is empty.");
        }
    }

    /**
     * Método para selecionar uma carga de superfície
     */
    private void jCB_PlanarLoads(ActionEvent evt) {
        int size = jLateralPanelLoads.jCB_PlanarLoadsLoad.getItemCount();
        int index = jLateralPanelLoads.jCB_PlanarLoadsLoad.getSelectedIndex();

        if (index < size - 1) {
            TypesOfLoads.PlanarLoad load = planarLoads.get(index);

            jLateralPanelLoads.jTF_PlanarLoadsQz.setText(numberForPrint(load.planarLoad_Qz));
        } else {
            loadCounter[4] = loadCounter[4] + 1;
            String loadName = "Load P" + String.valueOf(loadCounter[4]);
            planarLoads.add(loads.new PlanarLoad(loadName, 0.0));

            String[] model = new String[size];
            for (int i = 0; i < planarLoads.size(); i++) {
                model[i] = planarLoads.get(i).name;
            }

            jLateralPanelLoads.setModeljCB_PlanarLoads(model);
            jLateralPanelLoads.jCB_PlanarLoadsLoad.setSelectedIndex(size - 1);
            jB_PlanarLoadsEdit(null);
        }
    }

    /**
     * Método do botão para editar a carga de superfície selecionada
     */
    private void jB_PlanarLoadsEdit(ActionEvent evt) {
        int size = jLateralPanelLoads.jCB_PlanarLoadsLoad.getItemCount();
        int index = jLateralPanelLoads.jCB_PlanarLoadsLoad.getSelectedIndex();

        if (index < size - 1) {
            jLateralPanelLoads.jB_PlanarLoadsEdit.setEnabled(false);
            jLateralPanelLoads.jB_PlanarLoadsSave.setEnabled(true);
            jLateralPanelLoads.jB_PlanarLoadsDelete.setEnabled(false);
            jLateralPanelLoads.jCB_PlanarLoadsLoad.setEnabled(false);

            jLateralPanelLoads.jTF_PlanarLoadsQz.setEnabled(true);
            jCB_PlanarLoads(null);
        }
    }

    /**
     * Método do botão para salvar a carga de superfície selecionada
     */
    private void jB_PlanarLoadsSave(ActionEvent evt) {
        jLateralPanelLoads.jB_PlanarLoadsEdit.setEnabled(true);
        jLateralPanelLoads.jB_PlanarLoadsSave.setEnabled(false);
        jLateralPanelLoads.jB_PlanarLoadsDelete.setEnabled(true);
        jLateralPanelLoads.jCB_PlanarLoadsLoad.setEnabled(true);

        jLateralPanelLoads.jTF_PlanarLoadsQz.setEnabled(false);

        try {
            int index = jLateralPanelLoads.jCB_PlanarLoadsLoad.getSelectedIndex();
            TypesOfLoads.PlanarLoad load = planarLoads.get(index);

            String loadQz = formatString(jLateralPanelLoads.jTF_PlanarLoadsQz.getText());

            double Qz = Double.parseDouble(loadQz);

            if (Qz != 0) {
                load.editLoad(Qz);

                //Instruções para atualizar o desenho da carga no jDrawingPanel
                String[] loadInstructions = new String[4];

                loadInstructions[0] = "Planar Load";
                loadInstructions[1] = load.name;
                loadInstructions[2] = String.valueOf(load.planarLoad_Qz);
                loadInstructions[3] = "";

                jDrawingPanel.updateLoad(loadInstructions);
            } else {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Input values ​​are invalid!");
            }
        } catch (NumberFormatException e) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Input values ​​are invalid!");
        }

        jCB_PlanarLoads(null);

        //Chamada do método para requerer o focus para a janela
        requestFocus();
    }

    /**
     * Método para eliminar a carga selecionada
     */
    private void jB_PlanarLoadsDelete(ActionEvent evt) {
        int size = jLateralPanelLoads.jCB_PlanarLoadsLoad.getItemCount();
        int index = jLateralPanelLoads.jCB_PlanarLoadsLoad.getSelectedIndex();

        if (index < size - 1) {
            //Instruções para remover o carga do painel de desenho
            TypesOfLoads.PlanarLoad deleteLoad = planarLoads.get(index);
            String loadType = "Planar Load";
            String loadName = deleteLoad.name;
            jDrawingPanel.deleteLoad(loadType, loadName);

            //Instruções para remover a carga do painel lateral
            planarLoads.remove(index);
            String[] model = new String[planarLoads.size()];
            for (int i = 0; i < planarLoads.size(); i++) {
                model[i] = planarLoads.get(i).name;
            }

            jLateralPanelLoads.setModeljCB_PlanarLoads(model);

            if (size > 2) {
                TypesOfLoads.PlanarLoad load = planarLoads.get(0);
                jLateralPanelLoads.jTF_PlanarLoadsQz.setText(numberForPrint(load.planarLoad_Qz));
            } else {
                jLateralPanelLoads.jTF_PlanarLoadsQz.setText("");
            }
        } else {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Do not exist anymore structural loads to eliminate.\nThe list is empty.");
        }
    }

    /**
     * Método para selecionar uma variação de temperatura
     */
    private void jCB_ThermalLoads(ActionEvent evt) {
        int size = jLateralPanelLoads.jCB_ThermalLoads.getItemCount();
        int index = jLateralPanelLoads.jCB_ThermalLoads.getSelectedIndex();

        if (index < size - 1) {
            TypesOfLoads.ThermalLoad load = thermalLoads.get(index);

            jLateralPanelLoads.jTF_ThermalLoadsTzero.setText(numberForPrint(load.thermalLoad_Tzero));
            jLateralPanelLoads.jTF_ThermalLoadsTtop.setText(numberForPrint(load.thermalLoad_Ttop));
            jLateralPanelLoads.jTF_ThermalLoadsTbot.setText(numberForPrint(load.thermalLoad_Tbot));
        } else {
            loadCounter[5] = loadCounter[5] + 1;
            String loadName = "Load T" + String.valueOf(loadCounter[5]);
            thermalLoads.add(loads.new ThermalLoad(loadName, 0.0, 0.0, 0.0));

            String[] model = new String[size];
            for (int i = 0; i < thermalLoads.size(); i++) {
                model[i] = thermalLoads.get(i).name;
            }

            jLateralPanelLoads.setModeljCB_ThermalLoads(model);
            jLateralPanelLoads.jCB_ThermalLoads.setSelectedIndex(size - 1);
            jB_ThermalLoadsEdit(null);
        }
    }

    /**
     * Método do botão para editar a variação de temperatura selecionada
     */
    private void jB_ThermalLoadsEdit(ActionEvent evt) {
        int size = jLateralPanelLoads.jCB_ThermalLoads.getItemCount();
        int index = jLateralPanelLoads.jCB_ThermalLoads.getSelectedIndex();

        if (index < size - 1) {
            jLateralPanelLoads.jB_ThermalLoadsEdit.setEnabled(false);
            jLateralPanelLoads.jB_ThermalLoadsSave.setEnabled(true);
            jLateralPanelLoads.jB_ThermalLoadsDelete.setEnabled(false);
            jLateralPanelLoads.jCB_ThermalLoads.setEnabled(false);

            if ("1D".equals(type)) {
                jLateralPanelLoads.jTF_ThermalLoadsTzero.setEnabled(true);
            }
            if ("Frames".equals(type)) {
                jLateralPanelLoads.jTF_ThermalLoadsTtop.setEnabled(true);
                jLateralPanelLoads.jTF_ThermalLoadsTbot.setEnabled(true);
            }

            jCB_ThermalLoads(null);
        }
    }

    /**
     * Método do botão para salvar a variação de temperatura selecionada
     */
    private void jB_ThermalLoadsSave(ActionEvent evt) {
        jLateralPanelLoads.jB_ThermalLoadsEdit.setEnabled(true);
        jLateralPanelLoads.jB_ThermalLoadsSave.setEnabled(false);
        jLateralPanelLoads.jB_ThermalLoadsDelete.setEnabled(true);
        jLateralPanelLoads.jCB_ThermalLoads.setEnabled(true);

        jLateralPanelLoads.jTF_ThermalLoadsTzero.setEnabled(false);
        jLateralPanelLoads.jTF_ThermalLoadsTtop.setEnabled(false);
        jLateralPanelLoads.jTF_ThermalLoadsTbot.setEnabled(false);

        try {
            int index = jLateralPanelLoads.jCB_ThermalLoads.getSelectedIndex();
            TypesOfLoads.ThermalLoad load = thermalLoads.get(index);

            double loadTzero = Double.parseDouble(formatString(jLateralPanelLoads.jTF_ThermalLoadsTzero.getText()));
            double loadTtop = Double.parseDouble(formatString(jLateralPanelLoads.jTF_ThermalLoadsTtop.getText()));
            double loadTbot = Double.parseDouble(formatString(jLateralPanelLoads.jTF_ThermalLoadsTbot.getText()));

            load.editLoad(loadTzero, loadTtop, loadTbot);

            //Instruções para atualizar o desenho da carga no jDrawingPanel
            String[] loadInstructions = new String[5];

            loadInstructions[0] = "Thermal Load";
            loadInstructions[1] = load.name;
            loadInstructions[2] = String.valueOf(load.thermalLoad_Tzero);
            loadInstructions[3] = String.valueOf(load.thermalLoad_Ttop);
            loadInstructions[4] = String.valueOf(load.thermalLoad_Tbot);

            jDrawingPanel.updateLoad(loadInstructions);
        } catch (NumberFormatException e) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Input values ​​are invalid!");
        }

        jCB_ThermalLoads(null);

        //Chamada do método para requerer o focus para a janela
        requestFocus();
    }

    /**
     * Método para eliminar a carga selecionada
     */
    private void jB_ThermalLoadsDelete(ActionEvent evt) {
        int size = jLateralPanelLoads.jCB_ThermalLoads.getItemCount();
        int index = jLateralPanelLoads.jCB_ThermalLoads.getSelectedIndex();

        if (index < size - 1) {
            //Instruções para remover o carga do painel de desenho
            TypesOfLoads.ThermalLoad deleteLoad = thermalLoads.get(index);
            jDrawingPanel.deleteLoad("Thermal Load", deleteLoad.name);

            //Instruções para remover a carga do painel lateral
            thermalLoads.remove(index);
            String[] model = new String[thermalLoads.size()];
            for (int i = 0; i < thermalLoads.size(); i++) {
                model[i] = thermalLoads.get(i).name;
            }

            jLateralPanelLoads.setModeljCB_ThermalLoads(model);

            if (size > 2) {
                TypesOfLoads.ThermalLoad load = thermalLoads.get(0);
                jLateralPanelLoads.jTF_ThermalLoadsTzero.setText(numberForPrint(load.thermalLoad_Tzero));
                jLateralPanelLoads.jTF_ThermalLoadsTtop.setText(numberForPrint(load.thermalLoad_Ttop));
                jLateralPanelLoads.jTF_ThermalLoadsTbot.setText(numberForPrint(load.thermalLoad_Tbot));
            } else {
                jLateralPanelLoads.jTF_ThermalLoadsTzero.setText("");
                jLateralPanelLoads.jTF_ThermalLoadsTtop.setText("");
                jLateralPanelLoads.jTF_ThermalLoadsTbot.setText("");
            }
        } else {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Do not exist anymore thermal loads to eliminate.\nThe list is empty.");
        }
    }

    /**
     * Método para selecionar uma carga do tipo peso próprio
     */
    private void jCB_SelfWeight(ActionEvent evt) {
        int size = jLateralPanelLoads.jCB_SelfWeight.getItemCount();
        int index = jLateralPanelLoads.jCB_SelfWeight.getSelectedIndex();

        if (index < size - 1) {
            TypesOfLoads.SelfWeight load = selfWeights.get(index);

            jLateralPanelLoads.jTF_SelfWeight.setText(numberForPrint(load.selfWeight_S));
        } else {
            loadCounter[6] = loadCounter[6] + 1;
            String loadName = "Load S" + String.valueOf(loadCounter[6]);
            selfWeights.add(loads.new SelfWeight(loadName, 0.0));

            String[] model = new String[size];
            for (int i = 0; i < selfWeights.size(); i++) {
                model[i] = selfWeights.get(i).name;
            }

            jLateralPanelLoads.setModeljCB_SelfWeight(model);
            jLateralPanelLoads.jCB_SelfWeight.setSelectedIndex(size - 1);
            jB_SelfWeightEdit(null);
        }
    }

    /**
     * Método do botão para editar a carga do tipo peso próprio
     */
    private void jB_SelfWeightEdit(ActionEvent evt) {
        int size = jLateralPanelLoads.jCB_SelfWeight.getItemCount();
        int index = jLateralPanelLoads.jCB_SelfWeight.getSelectedIndex();

        if (index < size - 1) {
            jLateralPanelLoads.jB_SelfWeightEdit.setEnabled(false);
            jLateralPanelLoads.jB_SelfWeightSave.setEnabled(true);
            jLateralPanelLoads.jB_SelfWeightDelete.setEnabled(false);
            jLateralPanelLoads.jCB_SelfWeight.setEnabled(false);

            jLateralPanelLoads.jTF_SelfWeight.setEnabled(true);
            jCB_SelfWeight(null);
        }
    }

    /**
     * Método do botão para salvar a carga do tipo peso próprio
     */
    private void jB_SelfWeightSave(ActionEvent evt) {
        jLateralPanelLoads.jB_SelfWeightEdit.setEnabled(true);
        jLateralPanelLoads.jB_SelfWeightSave.setEnabled(false);
        jLateralPanelLoads.jB_SelfWeightDelete.setEnabled(true);
        jLateralPanelLoads.jCB_SelfWeight.setEnabled(true);

        jLateralPanelLoads.jTF_SelfWeight.setEnabled(false);

        try {
            int index = jLateralPanelLoads.jCB_SelfWeight.getSelectedIndex();
            TypesOfLoads.SelfWeight load = selfWeights.get(index);

            double loadG = Double.parseDouble(formatString(jLateralPanelLoads.jTF_SelfWeight.getText()));

            if (loadG >= 0) {
                load.editLoad(loadG);
            } else {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Input values ​​are invalid!");
            }
        } catch (NumberFormatException e) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Input values ​​are invalid!");
        }

        jCB_SelfWeight(null);

        //Chamada do método para requerer o focus para a janela
        requestFocus();
    }

    /**
     * Método para eliminar a carga selecionada
     */
    private void jB_SelfWeightDelete(ActionEvent evt) {
        int size = jLateralPanelLoads.jCB_SelfWeight.getItemCount();
        int index = jLateralPanelLoads.jCB_SelfWeight.getSelectedIndex();

        if (index < size - 1) {
            //Instruções para remover a carga do painel lateral
            selfWeights.remove(index);
            String[] model = new String[selfWeights.size()];
            for (int i = 0; i < selfWeights.size(); i++) {
                model[i] = selfWeights.get(i).name;
            }

            jLateralPanelLoads.setModeljCB_SelfWeight(model);

            if (size > 2) {
                TypesOfLoads.SelfWeight load = selfWeights.get(0);
                jLateralPanelLoads.jTF_SelfWeight.setText(numberForPrint(load.selfWeight_S));
            } else {
                jLateralPanelLoads.jTF_SelfWeight.setText("");
            }
        } else {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Do not exist anymore structural loads to eliminate.\nThe list is empty.");
        }
    }

    /**
     * Método para iniciar o conteúdo do painel Analysis
     */
    private void initializeAnalysis() {
        //Declaração dos eventos dos componentes co painel Analysis

        jLateralPanelAnalysis.jRB_AnalysisPlaneStress.addActionListener(this::jRB_PlaneStress);
        jLateralPanelAnalysis.jRB_AnalysisPlaneStrain.addActionListener(this::jRB_PlaneStrain);
        jLateralPanelAnalysis.jRB_AnalysisTimoshenko.addActionListener(this::jRB_Timoshenko);
        jLateralPanelAnalysis.jRB_AnalysisEulerBernoulli.addActionListener(this::jRB_EulerBernoulli);
        jLateralPanelAnalysis.jRB_AnalysisReissnerMindlin.addActionListener(this::jRB_ReissnerMindlin);
        jLateralPanelAnalysis.jRB_AnalysisKirchhoff.addActionListener(this::jRB_Kirchhoff);
        jLateralPanelAnalysis.jRB_LegendreAnalysis.addActionListener(this::jRB_LegendreAnalysis);
        jLateralPanelAnalysis.jRB_LobattoAnalysis.addActionListener(this::jRB_LobattoAnalysis);
        jLateralPanelAnalysis.jCB_AnalysisBars.addActionListener(this::jCB_AnalysisBars);
        jLateralPanelAnalysis.jCB_AnalysisTriangles.addActionListener(this::jCB_AnalysisTriangles);
        jLateralPanelAnalysis.jCB_AnalysisQuadrilaterals.addActionListener(this::jCB_AnalysisRectQuadr);

        /*
         * Adicionar evento ActionPerformed ao botão para fechar o painel
         */
        jLateralPanelAnalysis.jButtonClose.addActionListener(this::jButtonClose_jLateralPanel);

        jLateralPanelAnalysis.jRB_AnalysisPlaneStress.setSelected(true);
        jLateralPanelAnalysis.jRB_AnalysisTimoshenko.setSelected(true);
        jLateralPanelAnalysis.jRB_AnalysisReissnerMindlin.setSelected(true);
        jLateralPanelAnalysis.jRB_LegendreAnalysis.setSelected(true);
    }

    /**
     * Método para selecionar a teoria de Timoshenko
     */
    private void jRB_PlaneStress(ActionEvent evt) {
        if (jLateralPanelAnalysis.jRB_AnalysisPlaneStress.isSelected()) {
            jLateralPanelAnalysis.jRB_AnalysisPlaneStrain.setSelected(false);
        } else {
            jLateralPanelAnalysis.jRB_AnalysisPlaneStrain.setSelected(true);
        }
    }

    /**
     * Método para selecionar a teoria de Timoshenko
     */
    private void jRB_PlaneStrain(ActionEvent evt) {
        if (jLateralPanelAnalysis.jRB_AnalysisPlaneStrain.isSelected()) {
            jLateralPanelAnalysis.jRB_AnalysisPlaneStress.setSelected(false);
        } else {
            jLateralPanelAnalysis.jRB_AnalysisPlaneStress.setSelected(true);
        }
    }

    /**
     * Método para selecionar a teoria de Timoshenko
     */
    private void jRB_Timoshenko(ActionEvent evt) {
        if (jLateralPanelAnalysis.jRB_AnalysisTimoshenko.isSelected()) {
            jLateralPanelAnalysis.jRB_AnalysisEulerBernoulli.setSelected(false);
        } else {
            jLateralPanelAnalysis.jRB_AnalysisEulerBernoulli.setSelected(true);
        }
    }

    /**
     * Método para selecionar a teoria de Euler-Bernoulli
     */
    private void jRB_EulerBernoulli(ActionEvent evt) {
        if (jLateralPanelAnalysis.jRB_AnalysisEulerBernoulli.isSelected()) {
            jLateralPanelAnalysis.jRB_AnalysisTimoshenko.setSelected(false);
        } else {
            jLateralPanelAnalysis.jRB_AnalysisTimoshenko.setSelected(true);
        }
    }

    /**
     * Método para selecionar a teoria de Reissner-Mindlin
     */
    private void jRB_ReissnerMindlin(ActionEvent evt) {
        if (jLateralPanelAnalysis.jRB_AnalysisReissnerMindlin.isSelected()) {
            jLateralPanelAnalysis.jRB_AnalysisKirchhoff.setSelected(false);
        } else {
            jLateralPanelAnalysis.jRB_AnalysisKirchhoff.setSelected(true);
        }
    }

    /**
     * Método para selecionar a teoria de Kirchhoff
     */
    private void jRB_Kirchhoff(ActionEvent evt) {
        if (jLateralPanelAnalysis.jRB_AnalysisKirchhoff.isSelected()) {
            jLateralPanelAnalysis.jRB_AnalysisReissnerMindlin.setSelected(false);
        } else {
            jLateralPanelAnalysis.jRB_AnalysisReissnerMindlin.setSelected(true);
        }
    }

    /**
     * Método para selecionar a quadratura de Gauss-Legendre
     */
    private void jRB_LegendreAnalysis(ActionEvent evt) {
        if (jLateralPanelAnalysis.jRB_LegendreAnalysis.isSelected()) {
            jLateralPanelAnalysis.jRB_LobattoAnalysis.setSelected(false);
        } else {
            jLateralPanelAnalysis.jRB_LobattoAnalysis.setSelected(true);
        }
    }

    /**
     * Método para selecionar a quadratura de Gauss-Lobatto
     */
    private void jRB_LobattoAnalysis(ActionEvent evt) {
        if (jLateralPanelAnalysis.jRB_LobattoAnalysis.isSelected()) {
            jLateralPanelAnalysis.jRB_LegendreAnalysis.setSelected(false);
        } else {
            jLateralPanelAnalysis.jRB_LegendreAnalysis.setSelected(true);
        }
    }

    /**
     * Método para definir o número de pontos de integração numa barra
     */
    private void jCB_AnalysisBars(ActionEvent evt) {
        //TODO add your handling code here
    }

    /**
     * Método para definir o número de pontos de integração num triângulo
     */
    private void jCB_AnalysisTriangles(ActionEvent evt) {
        //TODO add your handling code here
    }

    /**
     * Método para definir o número de pontos de integração num retângulo e num quadrilátero
     */
    private void jCB_AnalysisRectQuadr(ActionEvent evt) {
        //TODO add your handling code here
    }

    /**
     * Método para iniciar o conteúdo do painel Results
     */
    private void initializeResults() {
        //Declaração dos eventos dos componentes co painel Analysis

        jLateralPanelResults.jSlider1.addChangeListener(this::jSlider1StateChanged);
        jLateralPanelResults.jRBNodesMesh.addActionListener(this::jRBNodesMesh);
        jLateralPanelResults.jRBNumberingNodes.addActionListener(this::jRBNumberingNodes);
        jLateralPanelResults.jRBNodesDeformed.addActionListener(this::jRBNodes);
        jLateralPanelResults.jRBAxialForce.addActionListener(this::jRBAxialForce);
        jLateralPanelResults.jRBShearForce.addActionListener(this::jRBShearForce);
        jLateralPanelResults.jRBBendingMoment.addActionListener(this::jRBBendingMoment);
        jLateralPanelResults.jRBTorsionalMoment.addActionListener(this::jRBTorsionalMoment);
        jLateralPanelResults.jRBIsolinesσx.addActionListener(this::jRBIsolinesσx);
        jLateralPanelResults.jRBIsolinesσy.addActionListener(this::jRBIsolinesσy);
        jLateralPanelResults.jRBIsolinesτxy.addActionListener(this::jRBIsolinesτxy);
        jLateralPanelResults.jRBMapsσx.addActionListener(this::jRBMapsσx);
        jLateralPanelResults.jRBMapsσy.addActionListener(this::jRBMapsσy);
        jLateralPanelResults.jRBMapsτxy.addActionListener(this::jRBMapsτxy);
        jLateralPanelResults.jRBIsolines_SlabsTop.addActionListener(this::jRBIsolinesSlabsTop);
        jLateralPanelResults.jRBIsolines_SlabsBottom.addActionListener(this::jRBIsolinesSlabsBottom);
        jLateralPanelResults.jRBMaps_SlabsTop.addActionListener(this::jRBMapsSlabsTop);
        jLateralPanelResults.jRBMaps_SlabsBottom.addActionListener(this::jRBMapsSlabsBottom);

        /*
         * Adicionar evento ActionPerformed ao botão para fechar o painel
         */
        jLateralPanelResults.jButtonClose.addActionListener(this::jButtonClose_jLateralPanel);

        /**
         * Declaração do método para a tabela individualProperties
         */
        individualProperties.jTable1.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    jTablePropertiesMousePressed(evt);
                }
            }
        );

        individualProperties.jButtonVectorF.addActionListener(this::jButtonVectorF);
        individualProperties.jButtonMatrixK.addActionListener(this::jButtonMatrixK);
        individualProperties.jButtonVectorDisp.addActionListener(this::jButtonVectorDisplacements);
    }

    /**
     * Método para adicionar eventos do rato ao resultsPane
     */
    private void eventsForResultsPane() {
        resultsPane.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    resultsPane_MousePressed(evt);
                }
            }
        );

        resultsPane.addMouseMotionListener(
            new java.awt.event.MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent evt) {
                    resultsPane_MouseDragged(evt);
                }

                @Override
                public void mouseMoved(MouseEvent evt) {
                    resultsPane_MouseMoved(evt);
                }
            }
        );

        resultsPane.addMouseWheelListener(this::mouseWheelMoved);
    }

    /**
     * Método para alterar a dimensão da deformada da estrutura
     */
    private void jSlider1StateChanged(ChangeEvent evt) {
        resetLabelsNumeration();
        int value = jLateralPanelResults.jSlider1.getValue();

        int gridPoints = 25;
        if (menuGridPoints25px.isSelected()) {
            gridPoints = 25;
        }
        if (menuGridPoints50px.isSelected()) {
            gridPoints = 50;
        }

        int scaleFactor = results.getFactor();
        resultsPane.resetPanel(scale, scaleFactor, gridPoints);
        jScrollPane1.setViewportView(resultsPane);

        if (results.arrayListFiniteElements != null) {
            int maximum = getDeformedFactor(maximumDisplacements(type, results.arrayListFiniteElements));
            resultsPane.changeDeformedFactor((int) (Math.round(maximum * (value / 50.0))));
            resultsPane.setGrid(jRadioButtonGrid.isSelected());
            resultsPane.drawNodesUndeformed(jLateralPanelResults.jRBNodesMesh.isSelected());
            resultsPane.drawNumberingOfNodes(jLateralPanelResults.jRBNumberingNodes.isSelected());
            resultsPane.drawNodesDeformed(jLateralPanelResults.jRBNodesDeformed.isSelected());
            resultsPane.drawDeformedStructure(results.arrayListFiniteElements);
            resultsPane.drawSupports(type, "Perspective", results.getRigidSupports(), results.getElasticSupports());

            resultsPane.typeOfResults = "Displacements";
            addLegendsForResults("Displacements");
        }
    }

    /**
     * Método associado ao evento de click em botão
     */
    private void jRBNodesMesh(ActionEvent evt) {
        if (jLateralPanelResults.jRBNodesMesh.isSelected()) {
            jLateralPanelResults.jRBNumberingNodes.setEnabled(true);
            panelResults_jLabelDisplacements(null);
        } else {
            jLateralPanelResults.jRBNumberingNodes.setEnabled(false);
            jLateralPanelResults.jRBNumberingNodes.setSelected(false);
            panelResults_jLabelDisplacements(null);
        }
    }

    /**
     * Método associado ao evento de click em botão
     */
    private void jRBNumberingNodes(ActionEvent evt) {
        panelResults_jLabelDisplacements(null);
    }

    /**
     * Método associado ao evento de click em botão
     */
    private void jRBNodes(ActionEvent evt) {
        panelResults_jLabelDisplacements(null);
    }

    /**
     * Método associado ao evento de click em botão
     */
    private void jRBAxialForce(ActionEvent evt) {
        jLateralPanelResults.jRBAxialForce.setSelected(true);
        jLateralPanelResults.jRBShearForce.setSelected(false);
        jLateralPanelResults.jRBBendingMoment.setSelected(false);
        jLateralPanelResults.jRBTorsionalMoment.setSelected(false);
        panelResults_jLabelDiagrams(null);
    }

    /**
     * Método associado ao evento de click em botão
     */
    private void jRBShearForce(ActionEvent evt) {
        jLateralPanelResults.jRBAxialForce.setSelected(false);
        jLateralPanelResults.jRBShearForce.setSelected(true);
        jLateralPanelResults.jRBBendingMoment.setSelected(false);
        jLateralPanelResults.jRBTorsionalMoment.setSelected(false);
        panelResults_jLabelDiagrams(null);
    }

    /**
     * Método associado ao evento de click em botão
     */
    private void jRBBendingMoment(ActionEvent evt) {
        jLateralPanelResults.jRBAxialForce.setSelected(false);
        jLateralPanelResults.jRBShearForce.setSelected(false);
        jLateralPanelResults.jRBBendingMoment.setSelected(true);
        jLateralPanelResults.jRBTorsionalMoment.setSelected(false);
        panelResults_jLabelDiagrams(null);
    }

    /**
     * Método associado ao evento de click em botão
     */
    private void jRBTorsionalMoment(ActionEvent evt) {
        jLateralPanelResults.jRBAxialForce.setSelected(false);
        jLateralPanelResults.jRBShearForce.setSelected(false);
        jLateralPanelResults.jRBBendingMoment.setSelected(false);
        jLateralPanelResults.jRBTorsionalMoment.setSelected(true);
        panelResults_jLabelDiagrams(null);
    }

    /**
     * Método associado ao evento de click em botão
     */
    private void jRBIsolinesσx(ActionEvent evt) {
        if (jLateralPanelResults.jRBIsolinesσx.isSelected()) {
            jLateralPanelResults.jRBIsolinesσy.setSelected(false);
            jLateralPanelResults.jRBIsolinesτxy.setSelected(false);
        } else {
            jLateralPanelResults.jRBIsolinesσx.setSelected(true);
            jLateralPanelResults.jRBIsolinesσy.setSelected(false);
            jLateralPanelResults.jRBIsolinesτxy.setSelected(false);
        }
        panelResults_jLabelIsovalues(null);
    }

    /**
     * Método associado ao evento de click em botão
     */
    private void jRBIsolinesσy(ActionEvent evt) {
        if (jLateralPanelResults.jRBIsolinesσy.isSelected()) {
            jLateralPanelResults.jRBIsolinesσx.setSelected(false);
            jLateralPanelResults.jRBIsolinesτxy.setSelected(false);
        } else {
            jLateralPanelResults.jRBIsolinesσx.setSelected(false);
            jLateralPanelResults.jRBIsolinesσy.setSelected(true);
            jLateralPanelResults.jRBIsolinesτxy.setSelected(false);
        }
        panelResults_jLabelIsovalues(null);
    }

    /**
     * Método associado ao evento de click em botão
     */
    private void jRBIsolinesτxy(ActionEvent evt) {
        if (jLateralPanelResults.jRBIsolinesτxy.isSelected()) {
            jLateralPanelResults.jRBIsolinesσx.setSelected(false);
            jLateralPanelResults.jRBIsolinesσy.setSelected(false);
        } else {
            jLateralPanelResults.jRBIsolinesσx.setSelected(false);
            jLateralPanelResults.jRBIsolinesσy.setSelected(false);
            jLateralPanelResults.jRBIsolinesτxy.setSelected(true);
        }
        panelResults_jLabelIsovalues(null);
    }

    /**
     * Método associado ao evento de click em botão
     */
    private void jRBMapsσx(ActionEvent evt) {
        if (jLateralPanelResults.jRBMapsσx.isSelected()) {
            jLateralPanelResults.jRBMapsσy.setSelected(false);
            jLateralPanelResults.jRBMapsτxy.setSelected(false);
        } else {
            jLateralPanelResults.jRBMapsσx.setSelected(true);
            jLateralPanelResults.jRBMapsσy.setSelected(false);
            jLateralPanelResults.jRBMapsτxy.setSelected(false);
        }
        panelResults_jLabelMaps(null);
    }

    /**
     * Método associado ao evento de click em botão
     */
    private void jRBMapsσy(ActionEvent evt) {
        if (jLateralPanelResults.jRBMapsσy.isSelected()) {
            jLateralPanelResults.jRBMapsσx.setSelected(false);
            jLateralPanelResults.jRBMapsτxy.setSelected(false);
        } else {
            jLateralPanelResults.jRBMapsσx.setSelected(false);
            jLateralPanelResults.jRBMapsσy.setSelected(true);
            jLateralPanelResults.jRBMapsτxy.setSelected(false);
        }
        panelResults_jLabelMaps(null);
    }

    /**
     * Método associado ao evento de click em botão
     */
    private void jRBMapsτxy(ActionEvent evt) {
        if (jLateralPanelResults.jRBMapsτxy.isSelected()) {
            jLateralPanelResults.jRBMapsσx.setSelected(false);
            jLateralPanelResults.jRBMapsσy.setSelected(false);
        } else {
            jLateralPanelResults.jRBMapsσx.setSelected(false);
            jLateralPanelResults.jRBMapsσy.setSelected(false);
            jLateralPanelResults.jRBMapsτxy.setSelected(true);
        }
        panelResults_jLabelMaps(null);
    }

    /**
     * Método associado ao evento de click em botão
     */
    private void jRBIsolinesSlabsTop(ActionEvent evt) {
        if (jLateralPanelResults.jRBIsolines_SlabsTop.isSelected()) {
            jLateralPanelResults.jRBIsolines_SlabsBottom.setSelected(false);
        } else {
            jLateralPanelResults.jRBIsolines_SlabsTop.setSelected(true);
            jLateralPanelResults.jRBIsolines_SlabsBottom.setSelected(false);
        }
        panelResults_jLabelIsovalues(null);
    }

    /**
     * Método associado ao evento de click em botão
     */
    private void jRBIsolinesSlabsBottom(ActionEvent evt) {
        if (jLateralPanelResults.jRBIsolines_SlabsBottom.isSelected()) {
            jLateralPanelResults.jRBIsolines_SlabsTop.setSelected(false);
        } else {
            jLateralPanelResults.jRBIsolines_SlabsTop.setSelected(false);
            jLateralPanelResults.jRBIsolines_SlabsBottom.setSelected(true);
        }
        panelResults_jLabelIsovalues(null);
    }

    /**
     * Método associado ao evento de click em botão
     */
    private void jRBMapsSlabsTop(ActionEvent evt) {
        if (jLateralPanelResults.jRBMaps_SlabsTop.isSelected()) {
            jLateralPanelResults.jRBMaps_SlabsBottom.setSelected(false);
        } else {
            jLateralPanelResults.jRBMaps_SlabsTop.setSelected(true);
            jLateralPanelResults.jRBMaps_SlabsBottom.setSelected(false);
        }
        panelResults_jLabelMaps(null);
    }

    /**
     * Método associado ao evento de click em botão
     */
    private void jRBMapsSlabsBottom(ActionEvent evt) {
        if (jLateralPanelResults.jRBMaps_SlabsBottom.isSelected()) {
            jLateralPanelResults.jRBMaps_SlabsTop.setSelected(false);
        } else {
            jLateralPanelResults.jRBMaps_SlabsTop.setSelected(false);
            jLateralPanelResults.jRBMaps_SlabsBottom.setSelected(true);
        }
        panelResults_jLabelMaps(null);
    }

    /**
     * Método para ativar os botões do painel individualProperties
     */
    private void jTablePropertiesMousePressed(java.awt.event.MouseEvent evt) {
        individualProperties.jButtonVectorF.setEnabled(true);
        individualProperties.jButtonMatrixK.setEnabled(true);
        individualProperties.jButtonVectorDisp.setEnabled(true);
    }

    /**
     * Método do botão para mostrar o vetor de solicitação do elemento finito
     */
    private void jButtonVectorF(ActionEvent evt) {
        jFrameProperties.setVisible(true);
        jSP_FrameProperties.setBorder(null);

        ArrayList<FiniteElement> arrayListFiniteElements;
        arrayListFiniteElements = results.arrayListFiniteElements;

        DefaultTableModel table = (DefaultTableModel) jTable_FrameProperties.getModel();

        int length = table.getRowCount();
        for (int i = 0; i < length; i++) {
            table.removeRow(0);
        }

        try {
            int index = individualProperties.jTable1.getSelectedRow();
            jFrameProperties.setTitle("Load Vector [Finite Element " + (index + 1) + "]");
            FiniteElement finiteElement = arrayListFiniteElements.get(index);

            table = ResultsTables.printVector(table, finiteElement.getVectorF());
        } catch (Exception e) {
            int line = 1;
            for (int i = line; i <= 40; i++) {
                table.addRow(new String[] { " " + String.valueOf(i) });
            }
        }

        jTable_FrameProperties.setModel(table);
    }

    /**
     * Método do botão para mostrar a matriz de rigidez do elemento finito
     */
    private void jButtonMatrixK(ActionEvent evt) {
        jFrameProperties.setVisible(true);
        jSP_FrameProperties.setBorder(null);

        ArrayList<FiniteElement> arrayListFiniteElements;
        arrayListFiniteElements = results.arrayListFiniteElements;

        DefaultTableModel table = (DefaultTableModel) jTable_FrameProperties.getModel();

        int length = table.getRowCount();
        for (int i = 0; i < length; i++) {
            table.removeRow(0);
        }

        try {
            int index = individualProperties.jTable1.getSelectedRow();
            jFrameProperties.setTitle("Stiffness Matrix [Finite Element " + (index + 1) + "]");
            FiniteElement finiteElement = arrayListFiniteElements.get(index);

            table = ResultsTables.printMatrix(finiteElement.getLocalStiffnessMatrix());
        } catch (Exception e) {
            int line = 1;
            for (int i = line; i <= 40; i++) {
                table.addRow(new String[] { " " + String.valueOf(i) });
            }
        }

        jTable_FrameProperties.setModel(table);

        if (jTable_FrameProperties.getColumnModel().getColumnCount() > 0) {
            length = jTable_FrameProperties.getColumnCount();
            for (int i = 0; i < length; i++) {
                jTable_FrameProperties.getColumnModel().getColumn(i).setMinWidth(100);
                jTable_FrameProperties.getColumnModel().getColumn(i).setMaxWidth(200);
            }
        }
    }

    /**
     * Método do botão para mostrar o vetor de deslocamentos do elemento finito
     */
    private void jButtonVectorDisplacements(ActionEvent evt) {
        jFrameProperties.setVisible(true);
        jSP_FrameProperties.setBorder(null);

        ArrayList<FiniteElement> arrayListFiniteElements;
        arrayListFiniteElements = results.arrayListFiniteElements;

        DefaultTableModel table = (DefaultTableModel) jTable_FrameProperties.getModel();

        int length = table.getRowCount();
        for (int i = 0; i < length; i++) {
            table.removeRow(0);
        }

        try {
            int index = individualProperties.jTable1.getSelectedRow();
            jFrameProperties.setTitle("Displacement Vector [Finite Element " + (index + 1) + "]");
            FiniteElement finiteElement = arrayListFiniteElements.get(index);

            table = ResultsTables.printVector(table, finiteElement.getLocalDisplacements());
        } catch (Exception e) {
            int line = 1;
            for (int i = line; i <= 40; i++) {
                table.addRow(new String[] { " " + String.valueOf(i) });
            }
        }

        jTable_FrameProperties.setModel(table);
    }

    /**
     * Método para calcular o deslocamentos máximos da estrutura
     *
     * @param type é o tipo de modelo estrutural
     * @param finiteElements contém a lista de elementos finitos
     * @return
     */
    private static double[] maximumDisplacements(String type, ArrayList<FiniteElement> finiteElements) {
        double[] maximum = new double[4];

        for (FiniteElement finiteElement : finiteElements) {
            double[][] displacements = finiteElement.getDisplacementVector();
            int nodes = finiteElement.getNodes();

            switch (type) {
                case "1D":
                    for (int i = 0; i < nodes; i++) {
                        if (maximum[0] > displacements[i * 2][0]) {
                            maximum[0] = displacements[i * 2][0];
                        }
                        if (maximum[1] < displacements[i * 2][0]) {
                            maximum[1] = displacements[i * 2][0];
                        }
                        if (maximum[2] > displacements[i * 2 + 1][0]) {
                            maximum[2] = displacements[i * 2 + 1][0];
                        }
                        if (maximum[3] < displacements[i * 2 + 1][0]) {
                            maximum[3] = displacements[i * 2 + 1][0];
                        }
                    }
                    break;
                case "2D":
                    for (int i = 0; i < nodes; i++) {
                        if (maximum[0] > displacements[i * 2][0]) {
                            maximum[0] = displacements[i * 2][0];
                        }
                        if (maximum[1] < displacements[i * 2][0]) {
                            maximum[1] = displacements[i * 2][0];
                        }
                        if (maximum[2] > displacements[i * 2 + 1][0]) {
                            maximum[2] = displacements[i * 2 + 1][0];
                        }
                        if (maximum[3] < displacements[i * 2 + 1][0]) {
                            maximum[3] = displacements[i * 2 + 1][0];
                        }
                    }
                    break;
                case "3D":
                    //TODO add your handling code here
                    break;
                case "Beams":
                    for (int i = 0; i < nodes; i++) {
                        if (maximum[2] > displacements[i * 2][0]) {
                            maximum[2] = displacements[i * 2][0];
                        }
                        if (maximum[3] < displacements[i * 2][0]) {
                            maximum[3] = displacements[i * 2][0];
                        }
                    }
                    break;
                case "Frames":
                    for (int i = 0; i < nodes; i++) {
                        if (maximum[0] > displacements[i * 3][0]) {
                            maximum[0] = displacements[i * 3][0];
                        }
                        if (maximum[1] < displacements[i * 3][0]) {
                            maximum[1] = displacements[i * 3][0];
                        }
                        if (maximum[2] > displacements[i * 3 + 1][0]) {
                            maximum[2] = displacements[i * 3 + 1][0];
                        }
                        if (maximum[3] < displacements[i * 3 + 1][0]) {
                            maximum[3] = displacements[i * 3 + 1][0];
                        }
                    }
                    break;
                case "Grids":
                    for (int i = 0; i < nodes; i++) {
                        if (maximum[2] > displacements[i * 3][0]) {
                            maximum[2] = displacements[i * 3][0];
                        }
                        if (maximum[3] < displacements[i * 3][0]) {
                            maximum[3] = displacements[i * 3][0];
                        }
                    }
                    break;
                case "Slabs":
                    int dof = 3;
                    if ("Kirchhoff".equals(finiteElement.getTheory())) {
                        dof = 4;
                    }

                    for (int i = 0; i < nodes; i++) {
                        if (maximum[2] > displacements[i * dof][0]) {
                            maximum[2] = displacements[i * dof][0];
                        }
                        if (maximum[3] < displacements[i * dof][0]) {
                            maximum[3] = displacements[i * dof][0];
                        }
                    }
                    break;
            }
        }

        return maximum;
    }

    /**
     * Método para determinar o valor máximo de tensão no elemento finito
     *
     * @param type é o tipo de modelo estrutural
     * @param stressName é o nome da tensão
     * @param plane é o plano da laje
     * @param finiteElements contém a lista de elementos finitos
     * @return
     */
    private static double stressesMaxValue(String type, String stressName, String plane, ArrayList<FiniteElement> finiteElements) {
        double maxValue = 0.0;

        int index;
        switch (stressName) {
            case "σx":
                index = 0;
                if ("Slabs".equals(type) && "Bottom".equals(plane)) {
                    index = 3;
                }
                break;
            case "σy":
                index = 1;
                if ("Slabs".equals(type) && "Bottom".equals(plane)) {
                    index = 4;
                }
                break;
            default:
                index = 2;
                if ("Slabs".equals(type) && "Bottom".equals(plane)) {
                    index = 5;
                }
                break;
        }

        for (FiniteElement finiteElement : finiteElements) {
            ArrayList<double[][]> nodalStresses = finiteElement.getNodalStresses();

            for (double[][] nodalStress : nodalStresses) {
                double value = nodalStress[index][0];

                if (value >= 0 && value > maxValue) {
                    maxValue = value;
                }
                if (value < 0 && (value * -1) > maxValue) {
                    maxValue = value * -1;
                }
            }
        }

        return maxValue;
    }

    /**
     * Método para adicionar as legendas ao painel resultsPane
     *
     * @param typeOfResults é o tipo de resultado que está a ser visualizado
     */
    private void addLegendsForResults(String typeOfResults) {
        if (scrollMouseListener && resultsPane.isShowing() && jRadioButtonLegends.isSelected()) {
            LegendsForPanels subtitle = new LegendsForPanels();

            //Instruções para obter a posição das barras do jScrollPane1
            int horizontal = scrollHorizontal.getValue();
            int vertical = ScrollVertical.getValue();

            int width, height;
            Dimension dimensionScrollPane;
            Dimension dimensionSubtitle;
            Insets insets;

            double stressesMaxValue;
            String stresses, plane;

            switch (typeOfResults) {
                case "Displacements":
                    //Instruções para obter dimensões do jScrollPane1
                    double[] maximumDisplacements = maximumDisplacements(type, results.arrayListFiniteElements);
                    subtitle.legendsForDisplacements(maximumDisplacements);
                    dimensionScrollPane = jScrollPane1.getSize();
                    dimensionSubtitle = subtitle.getPreferredSize();

                    //Adicionar painel subtitle ao painel resultsPane
                    insets = resultsPane.getInsets();
                    resultsPane.removeAll();
                    resultsPane.setLayout(null);
                    resultsPane.add(subtitle);
                    resultsPane.validate();
                    resultsPane.updateUI();

                    height = dimensionScrollPane.height - 150;
                    subtitle.setBounds(
                        20 + insets.left + horizontal,
                        height + insets.top + vertical,
                        dimensionSubtitle.width,
                        dimensionSubtitle.height
                    );
                    break;
                case "Isolines":
                    //Captura do botão selecionado
                    if (jLateralPanelResults.jRBIsolinesσy.isSelected()) {
                        stresses = "σy";
                    } else if (jLateralPanelResults.jRBIsolinesτxy.isSelected()) {
                        stresses = "τxy";
                    } else {
                        stresses = "σx";
                    }

                    if (jLateralPanelResults.jRBIsolines_SlabsBottom.isSelected()) {
                        plane = "Bottom";
                    } else {
                        plane = "Top";
                    }

                    //Instruções para obter dimensões do jScrollPane1
                    stressesMaxValue = (stressesMaxValue(type, stresses, plane, results.arrayListFiniteElements)) * (6.0 / 7.0);
                    subtitle.legendsForIsolines(stressesMaxValue);
                    dimensionScrollPane = jScrollPane1.getSize();
                    dimensionSubtitle = subtitle.getPreferredSize();

                    //Adicionar painel subtitle ao painel resultsPane
                    insets = resultsPane.getInsets();
                    resultsPane.removeAll();
                    resultsPane.setLayout(null);
                    resultsPane.add(subtitle);
                    resultsPane.validate();
                    resultsPane.updateUI();

                    width = (dimensionScrollPane.width / 2) - (dimensionSubtitle.width / 2);
                    height = dimensionScrollPane.height - 80;
                    subtitle.setBounds(
                        width + insets.left + horizontal,
                        height + insets.top + vertical,
                        dimensionSubtitle.width,
                        dimensionSubtitle.height
                    );
                    break;
                case "Maps":
                    if (jLateralPanelResults.jRBMapsσy.isSelected()) {
                        stresses = "σy";
                    } else if (jLateralPanelResults.jRBMapsτxy.isSelected()) {
                        stresses = "τxy";
                    } else {
                        stresses = "σx";
                    }

                    if (jLateralPanelResults.jRBMaps_SlabsBottom.isSelected()) {
                        plane = "Bottom";
                    } else {
                        plane = "Top";
                    }

                    //Instruções para obter dimensões do jScrollPane1
                    stressesMaxValue = stressesMaxValue(type, stresses, plane, results.arrayListFiniteElements);
                    subtitle.legendsForMaps(stressesMaxValue);
                    dimensionScrollPane = jScrollPane1.getSize();
                    dimensionSubtitle = subtitle.getPreferredSize();

                    //Adicionar painel subtitle ao painel resultsPane
                    insets = resultsPane.getInsets();
                    resultsPane.removeAll();
                    resultsPane.setLayout(null);
                    resultsPane.add(subtitle);
                    resultsPane.validate();
                    resultsPane.updateUI();

                    width = (dimensionScrollPane.width / 2) - (dimensionSubtitle.width / 2);
                    height = dimensionScrollPane.height - 80;
                    subtitle.setBounds(
                        width + insets.left + horizontal,
                        height + insets.top + vertical,
                        dimensionSubtitle.width,
                        dimensionSubtitle.height
                    );
                    break;
            }
        }
    }

    /**
     * Método para adicionar as legendas ao painel resultsPane jDrawingPanel
     */
    private void addLegendsForDrawingPanel() {
        //Adição do sistema de eixos ao painel
        LegendsForPanels subtitle = new LegendsForPanels();
        subtitle.legendsForAxes(type);

        //Instruções para obter dimensões do jScrollPane1
        Dimension dimensionScrollPane = jScrollPane1.getSize();
        Dimension dimensionSubtitle = subtitle.getPreferredSize();
        int horizontal = scrollHorizontal.getValue();
        int vertical = ScrollVertical.getValue();

        //Adicionar painel subtitle ao painel jDrawingPanel
        jDrawingPanel.removeAll();
        jDrawingPanel.setLayout(null);
        jDrawingPanel.add(subtitle);
        jDrawingPanel.validate();
        jDrawingPanel.updateUI();

        int width = dimensionScrollPane.width - 100;
        int height = dimensionScrollPane.height - 100;
        subtitle.setBounds(width + horizontal, height + vertical, dimensionSubtitle.width, dimensionSubtitle.height);
    }

    /**
     * Método para obter o fator de escala da deformada da estrutura
     *
     * @param maximumDisplacements contém os valores máximos do deslocamento
     * @return
     */
    private int getDeformedFactor(double[] maximumDisplacements) {
        int value = 100;
        double maximumValue = 0.0;

        for (double displacements : maximumDisplacements) {
            if (Math.abs(displacements) > maximumValue) {
                maximumValue = Math.abs(displacements);
            }
        }

        if (maximumValue < 1.000) {
            value = 250;
        }
        if (maximumValue < 0.500) {
            value = 500;
        }
        if (maximumValue < 0.250) {
            value = 750;
        }
        if (maximumValue < 0.100) {
            value = 1000;
        }
        if (maximumValue < 0.090) {
            value = 2000;
        }
        if (maximumValue < 0.080) {
            value = 3000;
        }
        if (maximumValue < 0.070) {
            value = 4000;
        }
        if (maximumValue < 0.060) {
            value = 5000;
        }
        if (maximumValue < 0.050) {
            value = 6000;
        }
        if (maximumValue < 0.040) {
            value = 7000;
        }
        if (maximumValue < 0.030) {
            value = 8000;
        }
        if (maximumValue < 0.020) {
            value = 9000;
        }
        if (maximumValue < 0.010) {
            value = 10000;
        }
        if (maximumValue < 0.009) {
            value = 20000;
        }
        if (maximumValue < 0.008) {
            value = 30000;
        }
        if (maximumValue < 0.007) {
            value = 40000;
        }
        if (maximumValue < 0.006) {
            value = 50000;
        }
        if (maximumValue < 0.005) {
            value = 60000;
        }
        if (maximumValue < 0.004) {
            value = 70000;
        }
        if (maximumValue < 0.003) {
            value = 80000;
        }
        if (maximumValue < 0.002) {
            value = 90000;
        }
        if (maximumValue < 0.001) {
            value = 100000;
        }
        if (maximumValue < 0.0005) {
            value = 250000;
        }
        if (maximumValue < 0.0001) {
            value = 500000;
        }
        if (maximumValue < 0.00005) {
            value = 1000000;
        }
        if (maximumValue < 0.00001) {
            value = 10000000;
        }

        return value / 2;
    }

    /**
     * Método para criar e apresentar um PopupMenu no painel de desenho
     */
    private void showPopMenu(MouseEvent evt) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem menuLine = new JMenuItem("Draw Line");
        JMenuItem menuTriangle = new JMenuItem("Draw Triangle");
        JMenuItem menuRectangle = new JMenuItem("Draw Rectangle");
        JMenuItem menuQuadrilateral = new JMenuItem("Draw Quadrilateral");
        JMenuItem menuSelect = new JMenuItem("Select", new ImageIcon(getClass().getResource("/images/edit_select.png")));
        JMenuItem menuCut = new JMenuItem("Cut", new ImageIcon(getClass().getResource("/images/edit_cut.png")));
        JMenuItem menuCopy = new JMenuItem("Copy", new ImageIcon(getClass().getResource("/images/edit_copy.png")));
        JMenuItem menuPaste = new JMenuItem("Paste", new ImageIcon(getClass().getResource("/images/edit_paste.png")));
        JMenuItem menuFinitElement = new JMenuItem("Finite Element");

        menuLine.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, 0));
        menuTriangle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, 0));
        menuRectangle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0));
        menuQuadrilateral.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0));
        menuSelect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0));
        menuCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        menuCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        menuPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));

        menuLine.addActionListener(this::jMenuItemDrawLine);
        menuTriangle.addActionListener(this::jMenuItemDrawTriangle);
        menuRectangle.addActionListener(this::jMenuItemDrawRectangle);
        menuQuadrilateral.addActionListener(this::jMenuItemDrawQuadrilateral);
        menuSelect.addActionListener(this::jMenuItemEditSelect);
        menuCut.addActionListener(this::jMenuItemEditCut);
        menuCopy.addActionListener(this::jMenuItemEditCopy);
        menuPaste.addActionListener(this::jMenuItemEditPaste);
        menuFinitElement.addActionListener(this::jMenuItemFiniteElement);

        menuLine.setEnabled(false);
        menuTriangle.setEnabled(false);
        menuRectangle.setEnabled(false);
        menuQuadrilateral.setEnabled(false);
        menuFinitElement.setEnabled(menuItemIsVisible());

        if ("1D".equals(type) || "Beams".equals(type) || "Frames".equals(type) || "Grids".equals(type)) {
            menuLine.setEnabled(true);
        }
        if ("2D".equals(type)) {
            menuTriangle.setEnabled(true);
            menuRectangle.setEnabled(true);
            menuQuadrilateral.setEnabled(true);
        }
        if ("Slabs".equals(type)) {
            menuRectangle.setEnabled(true);
        }

        popupMenu.add(menuLine);
        popupMenu.add(menuTriangle);
        popupMenu.add(menuRectangle);
        popupMenu.add(menuQuadrilateral);
        popupMenu.addSeparator();
        popupMenu.add(menuSelect);
        popupMenu.add(menuCut);
        popupMenu.add(menuCopy);
        popupMenu.add(menuPaste);
        popupMenu.addSeparator();
        popupMenu.add(menuFinitElement);

        popupMenu.show((Component) evt.getSource(), evt.getX(), evt.getY());
    }

    /**
     * Método do evento de click do menu jMenuItemDrawLine
     */
    private void jMenuItemDrawLine(ActionEvent evt) {
        LabelMouseEvents label = (LabelMouseEvents) jLabelLine;
        label.setSelectedAndMousePressed(false, true);
        label.jLabelMouseClicked(null);
        panelDraw_jLabelLine(null);
    }

    /**
     * Método do evento de click do menu jMenuItemDrawTriangle
     */
    private void jMenuItemDrawTriangle(ActionEvent evt) {
        LabelMouseEvents label = (LabelMouseEvents) jLabelTriangle;
        label.setSelectedAndMousePressed(false, true);
        label.jLabelMouseClicked(null);
        panelDraw_jLabelTriangle(null);
    }

    /**
     * Método do evento de click do menu jMenuItemDrawRectangle
     */
    private void jMenuItemDrawRectangle(ActionEvent evt) {
        LabelMouseEvents label = (LabelMouseEvents) jLabelRectangle;
        label.setSelectedAndMousePressed(false, true);
        label.jLabelMouseClicked(null);
        panelDraw_jLabelRectangle(null);
    }

    /**
     * Método do evento de click do menu jMenuItemDrawQuadrilateral
     */
    private void jMenuItemDrawQuadrilateral(ActionEvent evt) {
        LabelMouseEvents label = (LabelMouseEvents) jLabelQuadrilateral;
        label.setSelectedAndMousePressed(false, true);
        label.jLabelMouseClicked(null);
        panelDraw_jLabelQuadrilateral(null);
    }

    /**
     * Método do evento de click do menu jMenuItemFinitElement
     */
    private void jMenuItemFiniteElement(ActionEvent evt) {
        //Instruções para construir o modelo da jComboBox
        int size = sections.size();
        String[] modelA = new String[size + 1];
        for (int i = 0; i < size; i++) {
            modelA[i] = sections.get(i).name;
        }
        modelA[size] = "<Default>";
        jComboBoxSection.setModel(new javax.swing.DefaultComboBoxModel(modelA));

        size = materials.size();
        String[] modelB = new String[size + 1];
        for (int i = 0; i < size; i++) {
            modelB[i] = materials.get(i).material;
        }
        modelB[size] = "<Default>";
        jComboBoxMaterial.setModel(new javax.swing.DefaultComboBoxModel(modelB));

        //Instruções para atualizar os elementos da janela
        jComboBoxNodes.setSelectedIndex(0);
        jComboBoxSection.setSelectedIndex(sections.size());
        jComboBoxMaterial.setSelectedIndex(jLateralPanelGeometry.jCB_Materials.getSelectedIndex());

        int number = jDrawingPanel.getNumberOfSlectedShapes();
        if (number == 1) {
            String[] description = jDrawingPanel.getDescription_selectedShape();

            jLabelShape.setText("Shape: " + description[0]);
            jLabelLength.setText("Length: " + description[1] + " m");
            jLabelDimensions.setText("Dimensions: " + description[2] + " m");

            if ("".equals(description[1])) {
                jLabelLength.setText("Length: <Null>");
            }
            if ("".equals(description[2])) {
                jLabelDimensions.setText("Dimensions: <Null>");
            }

            String section = description[3];
            int index = sections.size();
            for (int i = 0; i < sections.size(); i++) {
                if (modelA[i].equals(section)) {
                    index = i;
                    break;
                }
            }

            jComboBoxSection.setSelectedIndex(index);
        } else {
            jLabelShape.setText("Shape: <Null>");
            jLabelLength.setText("Length: <Null>");
            jLabelDimensions.setText("Dimensions: <Null>");

            String section = jDrawingPanel.getSection_selectedShapes();
            int index = sections.size();
            for (int i = 0; i < sections.size(); i++) {
                if (modelA[i].equals(section)) {
                    index = i;
                    break;
                }
            }

            jComboBoxSection.setSelectedIndex(index);
        }

        //Instrução para tornar visível a janela
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        jDialogFiniteElement.setBounds((screen.width / 2) - (350 / 2), (screen.height / 2) - (350 / 2), 0, 0);
        jDialogFiniteElement.setVisible(true);
    }

    /**
     * Método para informar se o menu fica habilitado
     *
     * @return
     */
    private boolean menuItemIsVisible() {
        return jDrawingPanel.shapesAreSelected();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBInsertPointCancel;
    private javax.swing.JButton jBInsertPointInsert;
    private javax.swing.JButton jButtonApply;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonCloseAbout;
    private javax.swing.JButton jButtonClose_Load;
    private javax.swing.JButton jButtonClose_Properties;
    private javax.swing.JComboBox jComboBoxMaterial;
    private javax.swing.JComboBox jComboBoxNodes;
    private javax.swing.JComboBox jComboBoxSection;
    private javax.swing.JDialog jDialogAbout;
    private javax.swing.JDialog jDialogFiniteElement;
    private javax.swing.JDialog jDialogPoint;
    private javax.swing.JFrame jFrameHelp;
    private javax.swing.JFrame jFrameLoadTable;
    private javax.swing.JFrame jFrameProperties;
    private javax.swing.JLabel jLEdit;
    private javax.swing.JLabel jLFile;
    private javax.swing.JLabel jLHelp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelBendingMoment;
    private javax.swing.JLabel jLabelCalculate;
    private javax.swing.JLabel jLabelConcentratedLoad;
    private javax.swing.JLabel jLabelCopy;
    private javax.swing.JLabel jLabelCut;
    private javax.swing.JLabel jLabelCutLoad;
    private javax.swing.JLabel jLabelCutSupports;
    private javax.swing.JLabel jLabelDP_X;
    private javax.swing.JLabel jLabelDP_Y;
    private javax.swing.JLabel jLabelDiagrams;
    private javax.swing.JLabel jLabelDimensionLine;
    private javax.swing.JLabel jLabelDimensions;
    private javax.swing.JLabel jLabelDisplacements;
    private javax.swing.JLabel jLabelDistAxialLoad;
    private javax.swing.JLabel jLabelElasticSupport;
    private javax.swing.JLabel jLabelFixedSupport;
    private javax.swing.JLabel jLabelHSimpleSupport;
    private javax.swing.JLabel jLabelHSliderSupport;
    private javax.swing.JLabel jLabelIsovalues;
    private javax.swing.JLabel jLabelLength;
    private javax.swing.JLabel jLabelLine;
    private javax.swing.JLabel jLabelLoadTable;
    private javax.swing.JLabel jLabelMaps;
    private javax.swing.JLabel jLabelMaterialOfElement;
    private javax.swing.JLabel jLabelMaterials;
    private javax.swing.JLabel jLabelMatrixK;
    private javax.swing.JLabel jLabelMesh;
    private javax.swing.JLabel jLabelMove;
    private javax.swing.JLabel jLabelNodalForces;
    private javax.swing.JLabel jLabelNodalStresses;
    private javax.swing.JLabel jLabelNodes;
    private javax.swing.JLabel jLabelNodesOfElement;
    private javax.swing.JLabel jLabelNumberOfNodes;
    private javax.swing.JLabel jLabelPan;
    private javax.swing.JLabel jLabelPaste;
    private javax.swing.JLabel jLabelPinnedSupport;
    private javax.swing.JLabel jLabelPoint;
    private javax.swing.JLabel jLabelPrincipalStresses;
    private javax.swing.JLabel jLabelProperties;
    private javax.swing.JLabel jLabelQuadrilateral;
    private javax.swing.JLabel jLabelRectangle;
    private javax.swing.JLabel jLabelRedo;
    private javax.swing.JLabel jLabelSectionOfElement;
    private javax.swing.JLabel jLabelSections;
    private javax.swing.JLabel jLabelSelect;
    private javax.swing.JLabel jLabelSelfWeight;
    private javax.swing.JLabel jLabelSettlements;
    private javax.swing.JLabel jLabelShape;
    private javax.swing.JLabel jLabelSupportReactions;
    private javax.swing.JLabel jLabelTheory;
    private javax.swing.JLabel jLabelThermalLoad;
    private javax.swing.JLabel jLabelTriangle;
    private javax.swing.JLabel jLabelUndo;
    private javax.swing.JLabel jLabelUnifDistLoad;
    private javax.swing.JLabel jLabelUnifPlanarLoad;
    private javax.swing.JLabel jLabelVSimpleSupport;
    private javax.swing.JLabel jLabelVSliderSupport;
    private javax.swing.JLabel jLabelVectorDisplacements;
    private javax.swing.JLabel jLabelVectorF;
    private javax.swing.JLabel jLabelZoomIn;
    private javax.swing.JLabel jLabelZoomOut;
    private javax.swing.JPanel jLateralPanel;
    private javax.swing.JTable jLoadTable;
    private javax.swing.JPanel jP_FrameProperties;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelAnalysis;
    private javax.swing.JPanel jPanelDraw;
    private javax.swing.JPanel jPanelGeometry;
    private javax.swing.JPanel jPanelLoads;
    private javax.swing.JPanel jPanelResults;
    private javax.swing.JPanel jPanelView;
    private javax.swing.JRadioButton jRadioButtonAnalytical;
    private javax.swing.JRadioButton jRadioButtonGrid;
    private javax.swing.JRadioButton jRadioButtonLegends;
    private javax.swing.JRadioButton jRadioButtonNumerical;
    private javax.swing.JRadioButton jRadioButtonSnap;
    private javax.swing.JScrollPane jSPLoadTable;
    private javax.swing.JScrollPane jSP_FrameProperties;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPaneHelp;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparatorLoad;
    private javax.swing.JSeparator jSeparatorProperties;
    private javax.swing.JTextField jTInputX;
    private javax.swing.JTextField jTInputY;
    private javax.swing.JTable jTable_FrameProperties;
    private javax.swing.JLabel menuAnalysis;
    private javax.swing.JLabel menuDraw;
    private javax.swing.JLabel menuGeometry;
    private javax.swing.JLabel menuLoads;
    private javax.swing.JLabel menuResults;
    private javax.swing.JLabel menuView;
    // End of variables declaration//GEN-END:variables
}

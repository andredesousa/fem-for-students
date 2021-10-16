/*
 * Esta classe cria um painel para desenhar figuras geométricas
 * As figuras são desenhadas a partir dos eventos transmitidos pelo rato
 * Esta classe requer sub-classes para gerar todas as figuras
 */

package app.backend;

import app.calculations.AnalyticGeometry;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author André de Sousa
 */
public class DrawingPanel extends javax.swing.JPanel {

    String type;
    LabelsNumeration labelsNumeration;
    Command command;

    /**
     * Construtor sem parâmetros da classe DrawingPanel
     */
    public DrawingPanel() {
        command = new Command();

        type = new String();
        labelsNumeration = new LabelsNumeration();
        drawGrid = false;
        snapSelected = false;
        legendsSelected = false;
        gridPoints = 25;
        factor = 100.0;

        //Escala de todos o objetos a desenhar
        xScale = 1.0;
        yScale = 1.0;
    }

    /**
     * Método para ativar a construção das outras propriedades do painel
     *
     * @param type é o tipo de modelo estrutural
     * @param drawGrid recebe a autorização para desenhar uma malha de ponts
     * @param snapSelected recebe a autorização para ativar o snap
     * @param legendsSelected recebe a autorização para informar detalhes
     * @param gridPoints é o afastamento da malha de pontos
     * @param factor é o fator de escala para o desenho
     */
    public void setOtherProperties(
        String type,
        boolean drawGrid,
        boolean snapSelected,
        boolean legendsSelected,
        int gridPoints,
        double factor
    ) {
        this.type = type;
        this.drawGrid = drawGrid;
        this.snapSelected = snapSelected;
        this.legendsSelected = legendsSelected;
        this.gridPoints = gridPoints;
        this.factor = factor;
    }

    private int jPDraw_LabelSelected;
    private int jPView_LabelSelected;
    private int jPGeometry_LabelSelected;
    private int jPLoads_LabelSelected;
    private int jPAnalysis_LabelSelected;
    private int jPResults_LabelSelected;

    /**
     * Método que indica as tarefas a executar internamente
     *
     * @param jPDraw_LabelSelected
     * @param jPView_LabelSelected
     * @param jPGeometry_LabelSelected
     * @param jPLoads_LabelSelected
     * @param jPAnalysis_LabelSelected
     * @param jPResults_LabelSelected
     */
    public void drawingPanel(
        int jPDraw_LabelSelected,
        int jPView_LabelSelected,
        int jPGeometry_LabelSelected,
        int jPLoads_LabelSelected,
        int jPAnalysis_LabelSelected,
        int jPResults_LabelSelected
    ) {
        this.jPDraw_LabelSelected = jPDraw_LabelSelected;
        this.jPView_LabelSelected = jPView_LabelSelected;
        this.jPGeometry_LabelSelected = jPGeometry_LabelSelected;
        this.jPLoads_LabelSelected = jPLoads_LabelSelected;
        this.jPAnalysis_LabelSelected = jPAnalysis_LabelSelected;
        this.jPResults_LabelSelected = jPResults_LabelSelected;
    }

    //Criação dos objetos que contêm as figuras geométricas
    public ArrayList<DrawEllipse> arrayListNodes = new ArrayList();
    public ArrayList<DrawLine> arrayListLines = new ArrayList();
    public ArrayList<DrawPolygon> arrayListPolygons = new ArrayList();
    public ArrayList<DrawSupports> arrayListSupports = new ArrayList();
    public ArrayList<DrawElasticSupports> arrayListElasticSupports = new ArrayList();
    public ArrayList<DrawSettlements> arrayListSettlements = new ArrayList();
    public ArrayList<DrawLoads> arrayListLoads = new ArrayList();
    public ArrayList<DrawDimensionLine> arrayListDimensionLines = new ArrayList();

    private final ArrayList<DrawLine> arrayListCopyLines = new ArrayList();
    private final ArrayList<DrawPolygon> arrayListCopyPolygons = new ArrayList();
    private final ArrayList<DrawSupports> arrayListCopySupports = new ArrayList();
    private final ArrayList<DrawElasticSupports> arrayListCopyElasticSupports = new ArrayList();
    private final ArrayList<DrawSettlements> arrayListCopySettlements = new ArrayList();
    private final ArrayList<DrawLoads> arrayListCopyLoads = new ArrayList();
    private final ArrayList<DrawDimensionLine> arrayListCopyDimensionLines = new ArrayList();
    private ArrayList<Point.Float> arrayListPoints = new ArrayList();

    //Variáveis internas para o funcionamento dos métodos
    public int nClicks;

    private int mouseX;
    private int mouseY;
    private int gridPoints;
    private double factor;
    private double xScale;
    private double yScale;
    private double maxDistance;

    private boolean drawSelect;
    private boolean drawNode;
    private boolean drawGrid;
    private boolean snapSelected;
    private boolean legendsSelected;

    private final Point pointSelect = new Point();
    private final int[] loadCoordinates = new int[2];
    private final int[] dimensionLineCoordinates = new int[2];
    private int[] move = new int[4];
    private int[] lines = new int[4];
    private int[] xTriangle = new int[3];
    private int[] yTriangle = new int[3];
    private int[] xRectangle = new int[4];
    private int[] yRectangle = new int[4];
    private int[] xQuadrilateral = new int[4];
    private int[] yQuadrilateral = new int[4];
    private int[] select = new int[4];

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D shape = (Graphics2D) g.create();

        shape.scale(xScale, yScale);

        if (drawGrid) {
            for (int i = 0; i < 3000 / gridPoints; i++) {
                for (int j = 0; j < (3000 / gridPoints); j++) {
                    shape.drawLine(i * gridPoints, j * gridPoints, i * gridPoints, j * gridPoints);
                }
            }
        }

        for (DrawPolygon polygon : arrayListPolygons) {
            polygon.draw(shape);
        }

        for (DrawLine line : arrayListLines) {
            if ("1D".equals(type)) {
                line.drawBar(shape);
            } else {
                line.draw(shape);
            }
        }

        for (DrawEllipse node : arrayListNodes) {
            node.drawEllipseAndPoint(shape);
        }

        for (DrawSupports supports : arrayListSupports) {
            if ("Grids".equals(type) || "Slabs".equals(type)) {
                supports.draw(type, shape);
            } else {
                supports.draw(shape);
            }
        }

        for (DrawElasticSupports supports : arrayListElasticSupports) {
            supports.draw(shape);
        }

        for (DrawSettlements settlements : arrayListSettlements) {
            settlements.draw(shape);
        }

        for (DrawLoads loads : arrayListLoads) {
            if ("Grids".equals(type) || "Slabs".equals(type)) {
                loads.draw(type, shape);
            } else {
                loads.draw(shape);
            }
        }

        for (DrawDimensionLine dimensionLine : arrayListDimensionLines) {
            dimensionLine.draw(shape, factor);
        }

        if (nClicks > 0) {
            //Desenha uma linha temporária
            if (jPDraw_LabelSelected == labelsNumeration.jPDraw_Line) {
                shape.setPaint(Color.BLACK);
                if ("1D".equals(type)) {
                    shape.drawLine(lines[0], lines[1], lines[2], lines[3]);
                    shape.draw(new DrawEllipse(lines[0], lines[1], 6, 6).drawEllipse);
                    shape.draw(new DrawEllipse(lines[2], lines[3], 6, 6).drawEllipse);
                } else {
                    shape.drawLine(lines[0], lines[1], lines[2], lines[3]);
                }
            }

            //Desenha um triângulo temporário
            if (jPDraw_LabelSelected == labelsNumeration.jPDraw_Triangle) {
                shape.setPaint(Color.BLACK);
                shape.drawPolygon(xTriangle, yTriangle, 3);
            }

            //Desenha um retângulo temporário
            if (jPDraw_LabelSelected == labelsNumeration.jPDraw_Rectangle) {
                shape.setPaint(Color.BLACK);
                shape.drawPolygon(xRectangle, yRectangle, 4);
            }

            //Desenha um quadrilátero temporário
            if (jPDraw_LabelSelected == labelsNumeration.jPDraw_Quadrilateral) {
                shape.setPaint(Color.BLACK);
                shape.drawPolygon(xQuadrilateral, yQuadrilateral, 4);
            }

            if (legendsSelected) {
                drawLegends(shape);
            }
        }

        if (drawNode == true) {
            //Desenha um nó no ponto mais proximo do rato
            shape.setPaint(Color.BLUE);
            shape.drawOval(mouseX - 6, mouseY - 6, 12, 12);
        }

        if (drawSelect == true) {
            AlphaComposite transparency = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.5);
            AlphaComposite opaque = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 1);
            float dashStyle[] = { 5.0f };
            BasicStroke basicStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 5.0f, dashStyle, 0.0f);

            shape.setPaint(new java.awt.Color(88, 252, 88));
            shape.setComposite(transparency);
            shape.fillRect(select[0], select[1], select[2], select[3]);

            shape.setComposite(opaque);
            shape.setStroke(basicStroke);
            shape.setPaint(new java.awt.Color(0, 0, 0));
            shape.drawRect(select[0], select[1], select[2], select[3]);
        }
    }

    /**
     * Método para eliminar toda a informação armazenada
     */
    public void resetPanel() {
        arrayListNodes.clear();
        arrayListLines.clear();
        arrayListPolygons.clear();
        arrayListSupports.clear();
        arrayListElasticSupports.clear();
        arrayListSettlements.clear();
        arrayListLoads.clear();
        arrayListDimensionLines.clear();
    }

    /**
     * Método para receber todos os objetos do projeto
     *
     * @param arrayListNodes
     * @param arrayListLines
     * @param arrayListPolygons
     * @param arrayListSupports
     * @param arrayListElasticSupports
     * @param arrayListLoads
     * @param arrayListSettlements
     * @param arrayListDimensionLines
     */
    public void setAllObjects(
        ArrayList<DrawEllipse> arrayListNodes,
        ArrayList<DrawLine> arrayListLines,
        ArrayList<DrawPolygon> arrayListPolygons,
        ArrayList<DrawSupports> arrayListSupports,
        ArrayList<DrawElasticSupports> arrayListElasticSupports,
        ArrayList<DrawSettlements> arrayListSettlements,
        ArrayList<DrawLoads> arrayListLoads,
        ArrayList<DrawDimensionLine> arrayListDimensionLines
    ) {
        this.arrayListNodes = arrayListNodes;
        this.arrayListLines = arrayListLines;
        this.arrayListPolygons = arrayListPolygons;
        this.arrayListSupports = arrayListSupports;
        this.arrayListElasticSupports = arrayListElasticSupports;
        this.arrayListSettlements = arrayListSettlements;
        this.arrayListLoads = arrayListLoads;
        this.arrayListDimensionLines = arrayListDimensionLines;

        command();
        repaint();
    }

    /**
     * Método para retroceder na lista de objetos desenhados
     *
     * @return
     */
    public boolean commandUndo() {
        if (command.getStateUndo()) {
            command.undo();
            arrayListNodes = command.getListOfNodes();
            arrayListLines = command.getListOfLines();
            arrayListPolygons = command.getListOfPolygons();
            arrayListSupports = command.getListOfSupports();
            arrayListElasticSupports = command.getListOfElasticSupports();
            arrayListSettlements = command.getListOfSettlements();
            arrayListLoads = command.getListOfLoads();
            arrayListDimensionLines = command.getListOfDimensionLines();
            repaint();

            return true;
        } else {
            return false;
        }
    }

    /**
     * Método para avançar na lista de objetos desenhados
     *
     * @return
     */
    public boolean commandRedo() {
        if (command.getStateRedo()) {
            command.redo();
            arrayListNodes = command.getListOfNodes();
            arrayListLines = command.getListOfLines();
            arrayListPolygons = command.getListOfPolygons();
            arrayListSupports = command.getListOfSupports();
            arrayListElasticSupports = command.getListOfElasticSupports();
            arrayListSettlements = command.getListOfSettlements();
            arrayListLoads = command.getListOfLoads();
            arrayListDimensionLines = command.getListOfDimensionLines();
            repaint();

            return true;
        } else {
            return false;
        }
    }

    /**
     * Método público para desenhar um ponto no painel
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    public void drawPoint(int xPoint, int yPoint) {
        drawMousePressedPoint(xPoint, yPoint);
    }

    /**
     * Método para eliminar os objetos selecionados
     *
     * @return
     */
    public boolean cutObject() {
        try {
            drawMousePressedCut();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método para copiar os objetos selecionados
     *
     * @return
     */
    public boolean copyObject() {
        try {
            drawMousePressedCopy();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método para colar os objetos copiados
     *
     * @return
     */
    public boolean pasteObject() {
        try {
            drawMousePressedPaste();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método para cancelar o desenho o mudar o estado dos objetos
     */
    public void escape() {
        waive();
        repaint();
    }

    /**
     * Método para selecionar todos os objectos desenhados
     *
     * @return
     */
    public boolean selectAllObjects() {
        try {
            for (DrawEllipse node : arrayListNodes) {
                node.select(true);
            }
            for (DrawLine line : arrayListLines) {
                line.select(true);
            }
            for (DrawPolygon polygon : arrayListPolygons) {
                polygon.select(true);
            }
            for (DrawSupports support : arrayListSupports) {
                support.select(true);
            }
            for (DrawElasticSupports support : arrayListElasticSupports) {
                support.select(true);
            }
            for (DrawSettlements settlement : arrayListSettlements) {
                settlement.select(true);
            }
            for (DrawLoads load : arrayListLoads) {
                load.select(true);
            }
            for (DrawDimensionLine dimensionLine : arrayListDimensionLines) {
                dimensionLine.select(true);
            }
            repaint();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método para apagar todos os objectos desenhados
     *
     * @return
     */
    public boolean deleteAllObjects() {
        try {
            arrayListNodes.clear();
            arrayListLines.clear();
            arrayListPolygons.clear();
            arrayListSupports.clear();
            arrayListElasticSupports.clear();
            arrayListSettlements.clear();
            arrayListLoads.clear();
            arrayListDimensionLines.clear();
            command();
            repaint();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método para mandar desenhar uma grelha de pontos
     *
     * @param drawGrid
     */
    public void drawGrid(boolean drawGrid) {
        this.drawGrid = drawGrid;
        repaint();
    }

    /**
     * Método para informar o estado da funcionalidade snap
     *
     * @param snapSelected
     */
    public void snapSelected(boolean snapSelected) {
        this.snapSelected = snapSelected;
    }

    /**
     * Método para informar o estado da funcionalidade legends
     *
     * @param legendsSelected
     */
    public void legendsSelected(boolean legendsSelected) {
        this.legendsSelected = legendsSelected;
    }

    /**
     * Método para definir o afastamento da grelha de pontos
     *
     * @param gridPoints
     */
    public void gridPoints(int gridPoints) {
        this.gridPoints = gridPoints;
        repaint();
    }

    /**
     * Método para definir a escala do desenho
     *
     * @param factor
     */
    public void changeFactor(double factor) {
        this.factor = factor;
    }

    /**
     * Método para definir a escala do desenho
     *
     * @param xScale
     * @param yScale
     */
    public void scale(double xScale, double yScale) {
        this.xScale = xScale;
        this.yScale = yScale;
        repaint();
    }

    /**
     * Método para criar um lista de pontos para os polígonos
     */
    public void drawNode() {
        if (arrayListNodes.isEmpty()) {
            arrayListPoints = createPointsForNodes();
        }
    }

    /**
     * Método para ordenar o refinamento da malha de elementos finitos
     *
     * @param level
     * @param shape
     */
    public void meshRefinement(int level, String shape) {
        if ("Lines".equals(shape)) {
            int i = 0;
            while (i < level) {
                drawMeshRefinement_Lines();
                i++;
            }
        }

        if ("Triangles".equals(shape)) {
            int i = 0;
            while (i < level) {
                drawMeshRefinement_Triangles();
                i++;
            }
        }

        if ("Quadrilaterals".equals(shape)) {
            int i = 0;
            while (i < level) {
                drawMeshRefinement_Quadrilaterals();
                i++;
            }
        }

        command();
        repaint();
    }

    /**
     * Método para atualizar a lista de cargas estruturais
     *
     * @param instructions
     */
    public void updateLoad(String[] instructions) {
        boolean update = updateAllLoads(instructions);

        if (update) {
            command = new Command();
            command();
            repaint();
        }
    }

    /**
     * Método para eleminar a carga estrutural recebida
     *
     * @param loadType
     * @param loadName
     */
    public void deleteLoad(String loadType, String loadName) {
        boolean repaint = false;
        int i, j;

        i = 0;
        j = arrayListLoads.size();
        while (i < j) {
            DrawLoads load = arrayListLoads.get(i);
            if (loadType.equals(load.loadType) && loadName.equals(load.loadName)) {
                arrayListLoads.remove(i);
                repaint = true;
                j--;
                i--;
            }
            i++;
        }

        if (arrayListCopyLoads.size() > 0) {
            i = 0;
            j = arrayListCopyLoads.size();
            while (i < j) {
                DrawLoads load = arrayListCopyLoads.get(i);
                if (loadType.equals(load.loadType) && loadName.equals(load.loadName)) {
                    arrayListCopyLoads.remove(i);
                    j--;
                    i--;
                }
                i++;
            }
        }

        if (repaint == true) {
            command = new Command();
            command();
            repaint();
        }
    }

    /**
     * Método para informar se as figuras geométricas estão selecionadas
     *
     * @return
     */
    public boolean shapesAreSelected() {
        boolean result = false;

        if (result == false) {
            for (DrawLine line : arrayListLines) {
                if (line.selected) {
                    result = true;
                    break;
                }
            }
        }
        if (result == false) {
            for (DrawPolygon polygon : arrayListPolygons) {
                if (polygon.selected) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Método para obter o número de figuras geométricas selecionadas
     *
     * @return
     */
    public int getNumberOfSlectedShapes() {
        int result = 0;

        for (DrawLine line : arrayListLines) {
            if (line.selected) {
                result++;
            }
        }
        for (DrawPolygon polygon : arrayListPolygons) {
            if (polygon.selected) {
                result++;
            }
        }

        return result;
    }

    /**
     * Método para obter a descrição do primeiro objeto selecionado
     *
     * Coluna 0: Forma da figura geométrica
     * Coluna 1: Comprimento da linha
     * Coluna 2: Dimensões do polígono
     * Coluna 3: Nome da secção do elemento
     *
     * @return
     */
    public String[] getDescription_selectedShape() {
        String[] description = new String[4];
        boolean search = true;

        if (search) {
            for (DrawLine line : arrayListLines) {
                if (line.selected) {
                    int[][] coordinates = line.getCoordinates();
                    double length = AnalyticGeometry.length(coordinates);

                    description[0] = line.shape;
                    description[1] = decimalFormat(length / factor);
                    description[2] = "";
                    description[3] = line.section;
                    search = false;
                    break;
                }
            }
        }
        if (search) {
            for (DrawPolygon polygon : arrayListPolygons) {
                if (polygon.selected) {
                    int[][] coordinates = polygon.getCoordinates();
                    int points = coordinates.length;
                    String dimension = "";

                    if (points == 3) {
                        double lengthA = AnalyticGeometry.length(
                            coordinates[0][0],
                            coordinates[0][1],
                            coordinates[1][0],
                            coordinates[1][1]
                        );
                        double lengthB = AnalyticGeometry.length(
                            coordinates[1][0],
                            coordinates[1][1],
                            coordinates[2][0],
                            coordinates[2][1]
                        );
                        double lengthC = AnalyticGeometry.length(
                            coordinates[0][0],
                            coordinates[0][1],
                            coordinates[2][0],
                            coordinates[2][1]
                        );

                        dimension =
                            decimalFormat(lengthA / factor) +
                            "; " +
                            decimalFormat(lengthB / factor) +
                            "; " +
                            decimalFormat(lengthC / factor);
                    }
                    if (points == 4) {
                        double lengthA = AnalyticGeometry.length(
                            coordinates[0][0],
                            coordinates[0][1],
                            coordinates[1][0],
                            coordinates[1][1]
                        );
                        double lengthB = AnalyticGeometry.length(
                            coordinates[1][0],
                            coordinates[1][1],
                            coordinates[2][0],
                            coordinates[2][1]
                        );
                        double lengthC = AnalyticGeometry.length(
                            coordinates[2][0],
                            coordinates[2][1],
                            coordinates[3][0],
                            coordinates[3][1]
                        );
                        double lengthD = AnalyticGeometry.length(
                            coordinates[0][0],
                            coordinates[0][1],
                            coordinates[3][0],
                            coordinates[3][1]
                        );

                        dimension =
                            decimalFormat(lengthA / factor) +
                            "; " +
                            decimalFormat(lengthB / factor) +
                            "; " +
                            decimalFormat(lengthC / factor) +
                            "; " +
                            decimalFormat(lengthD / factor);
                    }

                    description[0] = polygon.shape;
                    description[1] = "";
                    description[2] = dimension;
                    description[3] = polygon.section;
                    break;
                }
            }
        }

        return description;
    }

    /**
     * Método para obter a secção das figuras geométricas selecionadas
     *
     * @return
     */
    public String getSection_selectedShapes() {
        String[] description = getDescription_selectedShape();
        String section = description[3];

        for (DrawLine line : arrayListLines) {
            if (line.selected) {
                if (!line.section.equals(section)) {
                    section = "";
                    break;
                }
            }
        }
        for (DrawPolygon polygon : arrayListPolygons) {
            if (polygon.selected) {
                if (!polygon.section.equals(section)) {
                    section = "";
                    break;
                }
            }
        }

        return section;
    }

    /**
     * Método para atualizar as secções das figuras geométricas selecionadas
     *
     * @param section
     */
    public void updateSections(String section) {
        //Atulizar listas de objetos definitivos
        for (DrawLine line : arrayListLines) {
            if (line.selected) {
                line.section = section;
            }
        }
        for (DrawPolygon polygon : arrayListPolygons) {
            if (polygon.selected) {
                polygon.section = section;
            }
        }

        //Atualizar listas de objetos copiados
        for (DrawLine line : arrayListCopyLines) {
            if (line.selected) {
                line.section = section;
            }
        }
        for (DrawPolygon polygon : arrayListCopyPolygons) {
            if (polygon.selected) {
                polygon.section = section;
            }
        }

        command = new Command();
        command();
    }

    /**
     * Método para eleminar a secção atribuída às figuras geométricas
     *
     * @param section
     */
    public void deleteSection(String section) {
        boolean update = false;

        //Atulização das listas de objetos definitivos
        for (DrawLine line : arrayListLines) {
            if (line.shape.equals(section)) {
                line.section = "";
                update = true;
            }
        }
        for (DrawPolygon polygon : arrayListPolygons) {
            if (polygon.shape.equals(section)) {
                polygon.section = "";
                update = true;
            }
        }

        //Atualização das listas de objetos copiados
        for (DrawLine line : arrayListCopyLines) {
            if (line.shape.equals(section)) {
                line.section = "";
            }
        }
        for (DrawPolygon polygon : arrayListCopyPolygons) {
            if (polygon.shape.equals(section)) {
                polygon.section = "";
            }
        }

        if (update) {
            command = new Command();
            command();
        }
    }

    /**
     * Método para atualizar o apoio elástico adicionado ao modelo
     *
     * @param name contém o nome do apoio elástico
     * @param stiffness contém os valores de rigidez das molas
     */
    public void updateElasticSupport(String name, double[] stiffness) {
        boolean update = false;

        //Atulização das listas de objetos definitivos
        for (DrawElasticSupports elasticSupport : arrayListElasticSupports) {
            if (elasticSupport.name.equals(name)) {
                elasticSupport.stiffness = stiffness;
                update = true;
            }
        }

        //Atualização das listas de objetos copiados
        for (DrawElasticSupports elasticSupport : arrayListCopyElasticSupports) {
            if (elasticSupport.name.equals(name)) {
                elasticSupport.stiffness = stiffness;
            }
        }

        if (update) {
            command = new Command();
            command();
            repaint();
        }
    }

    /**
     * Método para verificar a possibilidade de atualizar o valor do assentamento
     *
     * @param name contém o nome do assentamento de apoio
     * @param displacements contém os valores do assentamento de apoio
     * @return
     */
    public boolean isPossibleToUpdateSettlement(String name, double[] displacements) {
        return isPossibleUpdateSettlements(name, displacements);
    }

    /**
     * Método para atualizar o assentamento adicionado ao modelo
     *
     * @param name contém o nome do assentamento de apoio
     * @param displacements contém os valores do assentamento de apoio
     */
    public void updateSettlements(String name, double[] displacements) {
        boolean update = false;

        //Atulização das listas de objetos definitivos
        for (DrawSettlements settlement : arrayListSettlements) {
            if (settlement.name.equals(name)) {
                settlement.displacements = displacements;
                update = true;
            }
        }

        //Atualização das listas de objetos copiados
        for (DrawSettlements settlement : arrayListCopySettlements) {
            if (settlement.name.equals(name)) {
                settlement.displacements = displacements;
            }
        }

        if (update) {
            command = new Command();
            command();
            repaint();
        }
    }

    /**
     * Método para eleminar o apoio elástico adicionado ao modelo
     *
     * @param support
     */
    public void deleteElasticSupport(String support) {
        boolean update = false;
        int i, j;

        i = 0;
        j = arrayListElasticSupports.size();
        while (i < j) {
            DrawElasticSupports elasticSupport = arrayListElasticSupports.get(i);
            if (support.equals(elasticSupport.name)) {
                arrayListElasticSupports.remove(i);
                update = true;
                j--;
                i--;
            }
            i++;
        }

        i = 0;
        j = arrayListCopyElasticSupports.size();
        while (i < j) {
            DrawElasticSupports elasticSupport = arrayListCopyElasticSupports.get(i);
            if (support.equals(elasticSupport.name)) {
                arrayListCopyElasticSupports.remove(i);
                j--;
                i--;
            }
            i++;
        }

        if (update) {
            command = new Command();
            command();
            repaint();
        }
    }

    /**
     * Método para eleminar o assentamento adicionado ao modelo
     *
     * @param settlements
     */
    public void deleteSettlements(String settlements) {
        boolean update = false;
        int i, j;

        i = 0;
        j = arrayListSettlements.size();
        while (i < j) {
            DrawSettlements settlement = arrayListSettlements.get(i);
            if (settlements.equals(settlement.name)) {
                arrayListSettlements.remove(i);
                update = true;
                j--;
                i--;
            }
            i++;
        }

        i = 0;
        j = arrayListCopySettlements.size();
        while (i < j) {
            DrawSettlements settlement = arrayListCopySettlements.get(i);
            if (settlements.equals(settlement.name)) {
                arrayListCopySettlements.remove(i);
                j--;
                i--;
            }
            i++;
        }

        if (update) {
            command = new Command();
            command();
            repaint();
        }
    }

    /**
     * Método para receber as instruções relativas ao apoio elástico
     *
     * @param name é o nome do apoio elástico
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     * @param stiffness contém os valores de rigidez das molas
     */
    public void drawMousePressed_ElasticSupport(String name, int xPoint, int yPoint, double[] stiffness) {
        pointSelect.x = xPoint;
        pointSelect.y = yPoint;

        mouseX = xPoint;
        mouseY = yPoint;
        mousePressed_SelectPoint(xPoint, yPoint);
        double distance = maxDistance;
        xPoint = mouseX;
        yPoint = mouseY;

        if (distance < maxDistance()) {
            drawElasticSupports(name, xPoint, yPoint, stiffness);
        }
    }

    /**
     * Método para receber as instruções relativas ao assentamento de apoio
     *
     * @param name é o nome do assentamento de apoio
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     * @param displacements contém os valores do assentamento de apoio
     */
    public void drawMousePressed_Settlement(String name, int xPoint, int yPoint, double[] displacements) {
        pointSelect.x = xPoint;
        pointSelect.y = yPoint;

        mouseX = xPoint;
        mouseY = yPoint;
        mousePressed_SelectPoint(xPoint, yPoint);
        double distance = maxDistance;
        xPoint = mouseX;
        yPoint = mouseY;

        if (distance < maxDistance()) {
            drawSettlements(name, xPoint, yPoint, displacements);
        }
    }

    /**
     * Método para receber as instruções relativas à carga estrutural
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     * @param instructions contém as instruções da carga estrutural
     */
    public void drawMousePressed_Loads(int xPoint, int yPoint, String[] instructions) {
        pointSelect.x = xPoint;
        pointSelect.y = yPoint;

        mouseX = xPoint;
        mouseY = yPoint;
        mousePressed_SelectPoint(xPoint, yPoint);
        double distance = maxDistance;
        xPoint = mouseX;
        yPoint = mouseY;

        if (jPLoads_LabelSelected == labelsNumeration.jPLoads_UnifPlanarLoad) {
            if (!"".equals(instructions[2])) {
                drawUnifPlanarLoad(xPoint, yPoint, instructions);
            }
        }

        if (distance < maxDistance()) {
            if (jPLoads_LabelSelected == labelsNumeration.jPLoads_ConcentratedLoad) {
                if (!"".equals(instructions[2]) || !"".equals(instructions[3])) {
                    drawConcentratedLoad(xPoint, yPoint, instructions);
                }
            }
            if (jPLoads_LabelSelected == labelsNumeration.jPLoads_BendingMoment) {
                if (!"".equals(instructions[2])) {
                    drawBendingMoment(xPoint, yPoint, instructions);
                }
            }
            if (jPLoads_LabelSelected == labelsNumeration.jPLoads_UnifDistLoad) {
                if (!"".equals(instructions[2]) || !"".equals(instructions[3])) {
                    drawUnifDistLoad(xPoint, yPoint, instructions);
                }
            }
            if (jPLoads_LabelSelected == labelsNumeration.jPLoads_DistAxialLoad) {
                if (!"".equals(instructions[2])) {
                    drawDistAxialLoad(xPoint, yPoint, instructions);
                }
            }
            if (jPLoads_LabelSelected == labelsNumeration.jPLoads_ThermalLoad) {
                if (!"".equals(instructions[2]) || !"".equals(instructions[3]) || !"".equals(instructions[4])) {
                    drawThermalLoad(xPoint, yPoint, instructions);
                }
            }
        }
    }

    /**
     * Método público para receber o evento "MousePressed" do rato
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     * @param ctrlDown informa se a tecla CTRL está pressionada
     */
    public void drawMousePressed(int xPoint, int yPoint, boolean ctrlDown) {
        pointSelect.x = xPoint;
        pointSelect.y = yPoint;

        mouseX = xPoint;
        mouseY = yPoint;
        mousePressed_SelectPoint(xPoint, yPoint);
        double distance = maxDistance;
        xPoint = mouseX;
        yPoint = mouseY;

        //Eventos associados ao painel JPanelDraw

        if (jPDraw_LabelSelected == labelsNumeration.jPDraw_Line) {
            drawMousePressedLine(xPoint, yPoint);
        }

        if (jPDraw_LabelSelected == labelsNumeration.jPDraw_Triangle) {
            drawMousePressedTriangle(xPoint, yPoint);
        }

        if (jPDraw_LabelSelected == labelsNumeration.jPDraw_Rectangle) {
            drawMousePressedRectangle(xPoint, yPoint);
        }

        if (jPDraw_LabelSelected == labelsNumeration.jPDraw_Quadrilateral) {
            drawMousePressedQuadrilateral(xPoint, yPoint);
        }

        if (jPDraw_LabelSelected == labelsNumeration.jPDraw_Move) {
            drawMousePressedMove(xPoint, yPoint);
        }

        if (jPDraw_LabelSelected == labelsNumeration.jPDraw_Select) {
            drawMousePressedSelect(xPoint, yPoint, ctrlDown);
        }

        if (jPDraw_LabelSelected == labelsNumeration.jPDraw_Cut) {
            drawMousePressedCut();
        }

        //Os apoios são desenhados nos nós do elemento finito
        //As cargas nodais são desenhadas nos nós do elemento finito

        if (distance < maxDistance()) {
            if (jPGeometry_LabelSelected == labelsNumeration.jPGeometry_Nodes) {
                drawMousePressedNode(xPoint, yPoint);
            }
            if (jPGeometry_LabelSelected == labelsNumeration.jPGeometry_DimensionLine) {
                drawDimensionLine(xPoint, yPoint);
            }
            if (jPGeometry_LabelSelected == labelsNumeration.jPGeometry_VSimpleSupport) {
                drawSupports(xPoint, yPoint, "Vertical Support");
            }
            if (jPGeometry_LabelSelected == labelsNumeration.jPGeometry_HSimpleSupport) {
                drawSupports(xPoint, yPoint, "Horizontal Support");
            }
            if (jPGeometry_LabelSelected == labelsNumeration.jPGeometry_PinnedSupport) {
                drawSupports(xPoint, yPoint, "Pinned Support");
            }
            if (jPGeometry_LabelSelected == labelsNumeration.jPGeometry_HSliderSupport) {
                drawSupports(xPoint, yPoint, "Horizontal Slider");
            }
            if (jPGeometry_LabelSelected == labelsNumeration.jPGeometry_VSliderSupport) {
                drawSupports(xPoint, yPoint, "Vertical Slider");
            }
            if (jPGeometry_LabelSelected == labelsNumeration.jPGeometry_FixedSupport) {
                drawSupports(xPoint, yPoint, "Fixed Support");
            }
            if (jPGeometry_LabelSelected == labelsNumeration.jPGeometry_CutSupports) {
                deleteAllSupports(xPoint, yPoint);
            }
            if (jPLoads_LabelSelected == labelsNumeration.jPLoads_Cut) {
                deleteAllLoads(xPoint, yPoint);
            }
        }

        if (distance == maxDistance()) {
            if (jPLoads_LabelSelected == labelsNumeration.jPLoads_Cut) {
                deletePlanarLoads(xPoint, yPoint);
            }
        }
    }

    /**
     * Método público para receber o evento "MouseReleased" do rato
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    public void drawMouseReleased(int xPoint, int yPoint) {
        //Instrução para quando o rectângulo de seleção estiver desenhado
        if (drawSelect == true) {
            if (select[2] > 0 || select[3] > 0) {
                DrawRectangle selectionRectangle;
                selectionRectangle = new DrawRectangle(select[0], select[1], select[2], select[3]);

                selectShape(selectionRectangle);
            }

            drawSelect = false;
            repaint();
        }
    }

    /**
     * Método público para receber o evento "MouseMoved" do rato
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    public void drawMouseMoved(int xPoint, int yPoint) {
        mouseX = xPoint;
        mouseY = yPoint;
        mouseMoved_SelectPoint(xPoint, yPoint);
        xPoint = mouseX;
        yPoint = mouseY;

        if (nClicks > 0) {
            if (jPDraw_LabelSelected == labelsNumeration.jPDraw_Line) {
                drawMouseMovedLine(xPoint, yPoint);
            }

            if (jPDraw_LabelSelected == labelsNumeration.jPDraw_Triangle) {
                drawMouseMovedTriangle(xPoint, yPoint);
            }

            if (jPDraw_LabelSelected == labelsNumeration.jPDraw_Rectangle) {
                drawMouseMovedRectangle(xPoint, yPoint);
            }

            if (jPDraw_LabelSelected == labelsNumeration.jPDraw_Quadrilateral) {
                drawMouseMovedQuadrilateral(xPoint, yPoint);
            }

            if (jPDraw_LabelSelected == labelsNumeration.jPDraw_Move) {
                drawMouseMovedMove(xPoint, yPoint);
            }
        }
    }

    /**
     * Método público para receber o evento "MouseDragged" do rato
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    public void drawMouseDragged(int xPoint, int yPoint) {
        if (jPDraw_LabelSelected == labelsNumeration.jPDraw_Select) {
            drawMouseMovedSelect(xPoint, yPoint);
        }
    }

    /**
     * Método para chamar o método setAllShapes do objeto command
     */
    private void command() {
        command.setAllShapes(
            arrayListNodes,
            arrayListLines,
            arrayListPolygons,
            arrayListSupports,
            arrayListElasticSupports,
            arrayListSettlements,
            arrayListLoads,
            arrayListDimensionLines
        );
    }

    /**
     * Método para mandar desenhar um nó definitivo
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    private void drawMousePressedPoint(int xPoint, int yPoint) {
        arrayListNodes.add(new DrawEllipse(xPoint, yPoint, 12, 12));
        command();
        repaint();
    }

    /**
     * Método para mandar desenhar uma linha definitiva
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    private void drawMousePressedLine(int xPoint, int yPoint) {
        nClicks++;
        switch (type) {
            case "Beams":
                lines = DrawingMethods.mP_HorizontalLine(xPoint, yPoint, nClicks, lines);
                break;
            default:
                lines = DrawingMethods.mP_LineCoordinates(xPoint, yPoint, nClicks, lines);
                break;
        }

        if (DrawingMethods.validateLine(lines, nClicks) == false) {
            nClicks--;
        }

        if (nClicks == 2) {
            lines = sortCoordinates(lines);
            arrayListLines.add(new DrawLine(lines[0], lines[1], lines[2], lines[3], "Line"));
            command();
            repaint();
        }

        nClicks = nClicks(nClicks, "Line");
    }

    /**
     * Método para mandar desenhar um triângulo definitivo
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    private void drawMousePressedTriangle(int xPoint, int yPoint) {
        nClicks++;

        Point point = new Point(xPoint, yPoint);
        xTriangle = DrawingMethods.mP_PolygonCoordinates(point, nClicks, xTriangle, "xTriangle");
        yTriangle = DrawingMethods.mP_PolygonCoordinates(point, nClicks, yTriangle, "yTriangle");

        if (DrawingMethods.validatePolygon(xTriangle, yTriangle, nClicks) == false) {
            nClicks--;
        }

        if (nClicks == 3) {
            int[][] coordinates = sortCoordinates(xTriangle, yTriangle, "Triangle");

            int[] xPoints = new int[3];
            int[] yPoints = new int[3];

            for (int i = 0; i < coordinates.length; i++) {
                xPoints[i] = coordinates[i][0];
                yPoints[i] = coordinates[i][1];
            }

            arrayListPolygons.add(new DrawPolygon(xPoints, yPoints, 3, "Triangle"));
            command();
            repaint();
        }

        nClicks = nClicks(nClicks, "Triangle");
    }

    /**
     * Método para mandar desenhar um retângulo definitivo
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    private void drawMousePressedRectangle(int xPoint, int yPoint) {
        nClicks++;

        Point point = new Point(xPoint, yPoint);
        xRectangle = DrawingMethods.mP_PolygonCoordinates(point, nClicks, xRectangle, "xRectangle");
        yRectangle = DrawingMethods.mP_PolygonCoordinates(point, nClicks, yRectangle, "yRectangle");

        if (DrawingMethods.validatePolygon(xRectangle, yRectangle, nClicks) == false) {
            nClicks--;
        }

        if (nClicks == 3) {
            int[][] coordinates = sortCoordinates(xRectangle, yRectangle, "Rectangle");

            int[] xPoints = new int[4];
            int[] yPoints = new int[4];

            for (int i = 0; i < coordinates.length; i++) {
                xPoints[i] = coordinates[i][0];
                yPoints[i] = coordinates[i][1];
            }

            arrayListPolygons.add(new DrawPolygon(xPoints, yPoints, 4, "Rectangle"));
            command();
            repaint();
        }

        nClicks = nClicks(nClicks, "Rectangle");
    }

    /**
     * Método para mandar desenhar um quadrilátero definitivo
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    private void drawMousePressedQuadrilateral(int xPoint, int yPoint) {
        nClicks++;

        Point point = new Point(xPoint, yPoint);
        xQuadrilateral = DrawingMethods.mP_PolygonCoordinates(point, nClicks, xQuadrilateral, "xQuadrilateral");
        yQuadrilateral = DrawingMethods.mP_PolygonCoordinates(point, nClicks, yQuadrilateral, "yQuadrilateral");

        if (DrawingMethods.validatePolygon(xQuadrilateral, yQuadrilateral, nClicks) == false) {
            nClicks--;
        }

        if (nClicks == 4) {
            if (DrawingMethods.validateQuadrilateral(xQuadrilateral, yQuadrilateral) == true) {
                int[][] coordinates = sortCoordinates(xQuadrilateral, yQuadrilateral, "Quadrilateral");

                int[] xPoints = new int[4];
                int[] yPoints = new int[4];

                for (int i = 0; i < coordinates.length; i++) {
                    xPoints[i] = coordinates[i][0];
                    yPoints[i] = coordinates[i][1];
                }

                arrayListPolygons.add(new DrawPolygon(xPoints, yPoints, 4, "Quadrilateral"));
                command();
                repaint();
            } else {
                nClicks--;
            }
        }

        nClicks = nClicks(nClicks, "Quadrilateral");
    }

    /**
     * Método para mandar desenhar uma linha de cotagem definitiva
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    private void drawDimensionLine(int xPoint, int yPoint) {
        nClicks++;
        if (nClicks == 1) {
            dimensionLineCoordinates[0] = xPoint;
            dimensionLineCoordinates[1] = yPoint;
        }

        if (nClicks == 2) {
            int x1 = dimensionLineCoordinates[0];
            int y1 = dimensionLineCoordinates[1];

            int[] dimensionLine = { x1, y1, xPoint, yPoint };
            dimensionLine = sortCoordinates(dimensionLine);

            if (x1 != xPoint || y1 != yPoint) {
                arrayListDimensionLines.add(new DrawDimensionLine(dimensionLine[0], dimensionLine[1], dimensionLine[2], dimensionLine[3]));
                command();
                repaint();
            } else {
                nClicks--;
            }
        }

        nClicks = nClicks(nClicks, "DimensionLine");
    }

    /**
     * Método para criar e mandar desenhar um nó
     *
     * @param xPoint
     * @param yPoint
     */
    private void drawMousePressedNode(int xPoint, int yPoint) {
        arrayListNodes.add(new DrawEllipse(xPoint, yPoint, 6, 6));
        repaint();
    }

    /**
     * Método para ordenar as coordenadas de uma linha
     *
     * @param points
     */
    private static int[] sortCoordinates(int[] points) {
        int[][] vertices = new int[2][2];

        vertices[0][0] = points[0];
        vertices[0][1] = points[1];
        vertices[1][0] = points[2];
        vertices[1][1] = points[3];

        vertices = VerticesCoordinates.line(vertices);
        int[] coordinates = { vertices[0][0], vertices[0][1], vertices[1][0], vertices[1][1] };

        return coordinates;
    }

    /**
     * Método para ordenar as coordenadas de um polígono
     *
     * @param xPoints
     * @param yPoints
     * @param object
     * @return
     */
    private static int[][] sortCoordinates(int[] xPoints, int[] yPoints, String object) {
        int[][] coordinates;

        if (xPoints.length == yPoints.length) {
            int length = xPoints.length;

            int[][] vertices = new int[length][2];
            for (int i = 0; i < length; i++) {
                vertices[i][0] = xPoints[i];
                vertices[i][1] = yPoints[i];
            }

            if ("Triangle".equals(object)) {
                vertices = VerticesCoordinates.triangle(vertices);
            }
            if ("Rectangle".equals(object)) {
                vertices = VerticesCoordinates.rectangle(vertices);
            }
            if ("Quadrilateral".equals(object)) {
                vertices = VerticesCoordinates.quadrilateral(vertices);
            }

            coordinates = vertices;
        } else {
            coordinates = null;
        }

        return coordinates;
    }

    /**
     * Método para definir as coordenadas para mover os objetos
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    private void drawMousePressedMove(int xPoint, int yPoint) {
        nClicks++;

        if (nClicks > 0 && nClicks <= 2) {
            Point point = new Point(xPoint, yPoint);
            move = DrawingMethods.mP_MoveCoordinates(point, nClicks, move);
            drawMousePressedMove(move[0], move[1], move[2], move[3]);
        }

        nClicks = nClicks(nClicks, "Move");
    }

    /**
     * Método para definir as coordenadas para mover os objetos
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    private void drawMouseMovedMove(int xPoint, int yPoint) {
        if (nClicks == 1) {
            Point point = new Point(xPoint, yPoint);
            move = DrawingMethods.mM_MoveCoordinates(point, nClicks, move);
            drawMousePressedMove(move[0], move[1], move[2], move[3]);
        }
    }

    /**
     * Método para mover os objetos selecionados
     *
     * @param x1 é a abscissa do primeiro ponto
     * @param y1 é a ordenada do primeiro ponto
     * @param x2 é a abscissa do último ponto
     * @param y2 é a ordenada do último ponto
     */
    private void drawMousePressedMove(int x1, int y1, int x2, int y2) {
        //Instruções para mover todos os objetos desenhados
        boolean repaint = false;
        int i, j, horizontal, vertical;

        horizontal = x2 - x1;
        vertical = y2 - y1;

        i = 0;
        j = arrayListLines.size();
        while (i < j) {
            DrawLine line = arrayListLines.get(i);
            if (line.selected) {
                line.moveLine(horizontal, vertical);
                repaint = true;
            }
            i++;
        }

        i = 0;
        j = arrayListPolygons.size();
        while (i < j) {
            DrawPolygon polygon = arrayListPolygons.get(i);
            if (polygon.selected) {
                polygon.movePolygon(horizontal, vertical);
                repaint = true;
            }
            i++;
        }

        i = 0;
        j = arrayListSupports.size();
        while (i < j) {
            DrawSupports support = arrayListSupports.get(i);
            if (support.selected) {
                support.moveSupport(horizontal, vertical);
                repaint = true;
            }
            i++;
        }

        i = 0;
        j = arrayListElasticSupports.size();
        while (i < j) {
            DrawElasticSupports support = arrayListElasticSupports.get(i);
            if (support.selected) {
                support.moveSupport(horizontal, vertical);
                repaint = true;
            }
            i++;
        }

        i = 0;
        j = arrayListSettlements.size();
        while (i < j) {
            DrawSettlements settlements = arrayListSettlements.get(i);
            if (settlements.selected) {
                settlements.moveSettlement(horizontal, vertical);
                repaint = true;
            }
            i++;
        }

        i = 0;
        j = arrayListLoads.size();
        while (i < j) {
            DrawLoads load = arrayListLoads.get(i);
            if (load.selected) {
                load.moveLoad(horizontal, vertical);
                repaint = true;
            }
            i++;
        }

        i = 0;
        j = arrayListDimensionLines.size();
        while (i < j) {
            DrawDimensionLine dimensionLine = arrayListDimensionLines.get(i);
            if (dimensionLine.selected) {
                dimensionLine.moveLine(horizontal, vertical);
                repaint = true;
            }
            i++;
        }

        if (repaint == true) {
            //Validação dos apoios e cargas estruturais aplicados na malha
            validateSupportsAndLoads();

            //Instruções para redesenhar o conteúdo do painel
            repaint();
        }
    }

    /**
     * Método para selecionar os objetos desenhados
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     * @param ctrlDown informa se a tecla CTRL está pressionada
     */
    private void drawMousePressedSelect(int xPoint, int yPoint, boolean ctrlDown) {
        Point point = new Point(xPoint, yPoint);

        for (DrawEllipse node : arrayListNodes) {
            if (node.contains(point)) {
                node.select(true);
            } else if (!ctrlDown) {
                node.select(false);
            }
        }
        for (DrawLine line : arrayListLines) {
            if (line.contains(point)) {
                line.select(true);
            } else if (!ctrlDown) {
                line.select(false);
            }
        }
        for (DrawPolygon polygon : arrayListPolygons) {
            if (polygon.contains(point)) {
                polygon.select(true);
            } else if (!ctrlDown) {
                polygon.select(false);
            }
        }
        for (DrawSupports support : arrayListSupports) {
            if (support.contains(point)) {
                support.select(true);
            } else if (!ctrlDown) {
                support.select(false);
            }
        }
        for (DrawElasticSupports support : arrayListElasticSupports) {
            if (support.contains(point)) {
                support.select(true);
            } else if (!ctrlDown) {
                support.select(false);
            }
        }
        for (DrawSettlements settlements : arrayListSettlements) {
            if (settlements.contains(point)) {
                settlements.select(true);
            } else if (!ctrlDown) {
                settlements.select(false);
            }
        }
        for (DrawLoads load : arrayListLoads) {
            if ("Planar Load".equals(load.loadType)) {
                if (load.polygonContains(point)) {
                    load.select(true);
                } else if (!ctrlDown) {
                    load.select(false);
                }
            } else {
                if (load.contains(point)) {
                    load.select(true);
                } else if (!ctrlDown) {
                    load.select(false);
                }
            }
        }
        for (DrawDimensionLine dimensionLine : arrayListDimensionLines) {
            if (dimensionLine.contains(point)) {
                dimensionLine.select(true);
            } else if (!ctrlDown) {
                dimensionLine.select(false);
            }
        }

        select = DrawingMethods.mM_SelectCoordinates(xPoint, yPoint, xPoint, yPoint);
        drawSelect = true;
        repaint();
    }

    /**
     * Método para eliminar os objetos selecionados
     */
    private void drawMousePressedCut() {
        //Instrução para chamar o método command sempre que existam objetos selecionados
        if (objectsAreSelected()) {
            command();
        }

        //Instruções para cortar todos os objetos desenhados
        boolean repaint = false;
        int i, j;

        i = 0;
        j = arrayListNodes.size();
        while (i < j) {
            DrawEllipse node = arrayListNodes.get(i);
            if (node.selected) {
                arrayListNodes.remove(i);
                repaint = true;
                j--;
                i--;
            }
            i++;
        }

        i = 0;
        j = arrayListLines.size();
        while (i < j) {
            DrawLine line = arrayListLines.get(i);
            if (line.selected) {
                arrayListLines.remove(i);
                repaint = true;
                j--;
                i--;
            }
            i++;
        }

        i = 0;
        j = arrayListPolygons.size();
        while (i < j) {
            DrawPolygon polygon = arrayListPolygons.get(i);
            if (polygon.selected) {
                arrayListPolygons.remove(i);
                repaint = true;
                j--;
                i--;
            }
            i++;
        }

        i = 0;
        j = arrayListSupports.size();
        while (i < j) {
            DrawSupports support = arrayListSupports.get(i);
            if (support.selected) {
                arrayListSupports.remove(i);
                repaint = true;
                j--;
                i--;
            }
            i++;
        }

        i = 0;
        j = arrayListElasticSupports.size();
        while (i < j) {
            DrawElasticSupports support = arrayListElasticSupports.get(i);
            if (support.selected) {
                arrayListElasticSupports.remove(i);
                repaint = true;
                j--;
                i--;
            }
            i++;
        }

        i = 0;
        j = arrayListSettlements.size();
        while (i < j) {
            DrawSettlements settlement = arrayListSettlements.get(i);
            if (settlement.selected) {
                arrayListSettlements.remove(i);
                repaint = true;
                j--;
                i--;
            }
            i++;
        }

        i = 0;
        j = arrayListLoads.size();
        while (i < j) {
            DrawLoads load = arrayListLoads.get(i);
            if (load.selected) {
                arrayListLoads.remove(i);
                repaint = true;
                j--;
                i--;
            }
            i++;
        }

        i = 0;
        j = arrayListDimensionLines.size();
        while (i < j) {
            DrawDimensionLine dimensionLine = arrayListDimensionLines.get(i);
            if (dimensionLine.selected) {
                arrayListDimensionLines.remove(i);
                repaint = true;
                j--;
                i--;
            }
            i++;
        }

        if (repaint == true) {
            //Validação dos apoios e cargas estruturais aplicados na malha
            validateSupportsAndLoads();

            //Instruções para redesenhar o conteúdo do painel
            repaint();
        }
    }

    /**
     * Método para copiar os objetos selecionados
     */
    private void drawMousePressedCopy() {
        arrayListCopyLines.clear();
        arrayListCopyPolygons.clear();
        arrayListCopySupports.clear();
        arrayListCopyElasticSupports.clear();
        arrayListCopySettlements.clear();
        arrayListCopyLoads.clear();
        arrayListCopyDimensionLines.clear();

        for (DrawLine line : arrayListLines) {
            if (line.selected) {
                arrayListCopyLines.add(DrawingMethods.pasteLine(line));
            }
        }

        for (DrawPolygon polygon : arrayListPolygons) {
            if (polygon.selected) {
                arrayListCopyPolygons.add(DrawingMethods.pastePolygon(polygon));
            }
        }

        for (DrawSupports support : arrayListSupports) {
            if (support.selected) {
                arrayListCopySupports.add(DrawingMethods.pasteSupport(support));
            }
        }

        for (DrawElasticSupports support : arrayListElasticSupports) {
            if (support.selected) {
                arrayListCopyElasticSupports.add(DrawingMethods.pasteElasticSupport(support));
            }
        }

        for (DrawSettlements settlements : arrayListSettlements) {
            if (settlements.selected) {
                arrayListCopySettlements.add(DrawingMethods.pasteSettlement(settlements));
            }
        }

        for (DrawLoads load : arrayListLoads) {
            if (load.selected) {
                arrayListCopyLoads.add(DrawingMethods.pasteLoad(load));
            }
        }

        for (DrawDimensionLine dimensionLine : arrayListDimensionLines) {
            if (dimensionLine.selected) {
                arrayListCopyDimensionLines.add(DrawingMethods.pasteDimensionLine(dimensionLine));
            }
        }
    }

    /**
     * Método para colar os objetos copiados
     */
    private void drawMousePressedPaste() {
        if (
            arrayListCopyLines.size() > 0 ||
            arrayListCopyPolygons.size() > 0 ||
            arrayListCopySupports.size() > 0 ||
            arrayListCopyLoads.size() > 0
        ) {
            //Mudar o estado dos objetos desenhados para não selecionado
            waive();

            arrayListLines.addAll(arrayListCopyLines);
            arrayListPolygons.addAll(arrayListCopyPolygons);
            arrayListSupports.addAll(arrayListCopySupports);
            arrayListElasticSupports.addAll(arrayListCopyElasticSupports);
            arrayListSettlements.addAll(arrayListCopySettlements);
            arrayListLoads.addAll(arrayListCopyLoads);
            arrayListDimensionLines.addAll(arrayListCopyDimensionLines);

            //Remoção de todos os objetos das listas de armazenamento das cópias
            arrayListCopyLines.clear();
            arrayListCopyPolygons.clear();
            arrayListCopySupports.clear();
            arrayListCopyElasticSupports.clear();
            arrayListCopySettlements.clear();
            arrayListCopyLoads.clear();
            arrayListCopyDimensionLines.clear();

            //Validação dos apoios e cargas estruturais aplicados na malha
            validateSupportsAndLoads();
            command();
            repaint();
        }
    }

    /**
     * Método para pôr os objetos em estado não selecionado
     */
    private void waive() {
        for (DrawEllipse node : arrayListNodes) {
            if (node.selected) {
                node.select(false);
            }
        }
        for (DrawLine line : arrayListLines) {
            if (line.selected) {
                line.select(false);
            }
        }
        for (DrawPolygon polygon : arrayListPolygons) {
            if (polygon.selected) {
                polygon.select(false);
            }
        }
        for (DrawSupports support : arrayListSupports) {
            if (support.selected) {
                support.select(false);
            }
        }
        for (DrawElasticSupports elasticSupport : arrayListElasticSupports) {
            if (elasticSupport.selected) {
                elasticSupport.select(false);
            }
        }
        for (DrawSettlements settlement : arrayListSettlements) {
            if (settlement.selected) {
                settlement.select(false);
            }
        }
        for (DrawLoads load : arrayListLoads) {
            if (load.selected) {
                load.select(false);
            }
        }
        for (DrawDimensionLine dimensionLine : arrayListDimensionLines) {
            if (dimensionLine.selected) {
                dimensionLine.select(false);
            }
        }
    }

    /**
     * Método para informar se existem objetos selecionados
     *
     * @return
     */
    private boolean objectsAreSelected() {
        boolean result = false;

        if (!result) {
            for (DrawLine line : arrayListLines) {
                if (line.selected) {
                    result = true;
                    break;
                }
            }
        }
        if (!result) {
            for (DrawPolygon polygon : arrayListPolygons) {
                if (polygon.selected) {
                    result = true;
                    break;
                }
            }
        }
        if (!result) {
            for (DrawSupports support : arrayListSupports) {
                if (support.selected) {
                    result = true;
                    break;
                }
            }
        }
        if (!result) {
            for (DrawElasticSupports elasticSupport : arrayListElasticSupports) {
                if (elasticSupport.selected) {
                    result = true;
                    break;
                }
            }
        }
        if (!result) {
            for (DrawSettlements settlement : arrayListSettlements) {
                if (settlement.selected) {
                    result = true;
                    break;
                }
            }
        }
        if (!result) {
            for (DrawLoads load : arrayListLoads) {
                if (load.selected) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Método para mandar desenhar uma linha temporária
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    private void drawMouseMovedLine(int xPoint, int yPoint) {
        if (nClicks == 1) {
            switch (type) {
                case "Beams":
                    lines = DrawingMethods.mM_HorizontalLine(xPoint, yPoint, nClicks, lines);
                    repaint();
                    break;
                default:
                    lines = DrawingMethods.mM_LineCoordinates(xPoint, yPoint, nClicks, lines);
                    repaint();
                    break;
            }
        }
    }

    /**
     * Método para mandar desenhar um triângulo temporário
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    private void drawMouseMovedTriangle(int xPoint, int yPoint) {
        if (nClicks > 0 && nClicks < 3) {
            Point point = new Point(xPoint, yPoint);
            xTriangle = DrawingMethods.mM_PolygonCoordinates(point, nClicks, xTriangle, "xTriangle");
            yTriangle = DrawingMethods.mM_PolygonCoordinates(point, nClicks, yTriangle, "yTriangle");
            repaint();
        }
    }

    /**
     * Método para mandar desenhar um retângulo temporário
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    private void drawMouseMovedRectangle(int xPoint, int yPoint) {
        if (nClicks > 0 && nClicks < 3) {
            Point point = new Point(xPoint, yPoint);
            xRectangle = DrawingMethods.mM_PolygonCoordinates(point, nClicks, xRectangle, "xRectangle");
            yRectangle = DrawingMethods.mM_PolygonCoordinates(point, nClicks, yRectangle, "yRectangle");
            repaint();
        }
    }

    /**
     * Método para mandar desenhar um quadrilátero temporário
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    private void drawMouseMovedQuadrilateral(int xPoint, int yPoint) {
        if (nClicks > 0 && nClicks < 4) {
            Point point = new Point(xPoint, yPoint);
            xQuadrilateral = DrawingMethods.mM_PolygonCoordinates(point, nClicks, xQuadrilateral, "xQuadrilateral");
            yQuadrilateral = DrawingMethods.mM_PolygonCoordinates(point, nClicks, yQuadrilateral, "yQuadrilateral");
            repaint();
        }
    }

    /**
     * Método privado para definir um retângulo de seleção
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    private void drawMouseMovedSelect(int xPoint, int yPoint) {
        select = DrawingMethods.mM_SelectCoordinates(pointSelect.x, pointSelect.y, xPoint, yPoint);
        repaint();
    }

    /**
     * Método privado que controla o número de clicks
     *
     * @param nClicks é o numero de clicks do rato
     * @param object é o tipo de objeto a desenhar
     * @return
     */
    private int nClicks(int nClicks, String object) {
        int clicks = DrawingMethods.clicks(nClicks, object);
        return clicks;
    }

    /**
     * Método que retorna a distância máxima para selecionar um ponto.
     * A distância máxima é dada em pixels.
     *
     * @return
     */
    private double maxDistance() {
        return 12.0;
    }

    /**
     * Método privado para alterar as coordenados do rato
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    private void mousePressed_SelectPoint(int xPoint, int yPoint) {
        maxDistance = maxDistance();

        snapNode(xPoint, yPoint);
        if (snapSelected) {
            snap(xPoint, yPoint);
        }

        for (DrawEllipse node : arrayListNodes) {
            node_selectNode(node, xPoint, yPoint);
        }
        for (DrawLine line : arrayListLines) {
            line_selectNode(line, xPoint, yPoint);
        }
        for (DrawPolygon polygon : arrayListPolygons) {
            polygon_selectNode(polygon, xPoint, yPoint);
        }
        for (DrawSupports support : arrayListSupports) {
            support_selectNode(support, xPoint, yPoint);
        }
        for (DrawLoads load : arrayListLoads) {
            load_selectNode(load, xPoint, yPoint);
        }
        for (DrawDimensionLine dimensionLine : arrayListDimensionLines) {
            dimensionLine_selectNode(dimensionLine, xPoint, yPoint);
        }
    }

    /**
     * Método privado para alterar as coordenados do rato
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    private void mouseMoved_SelectPoint(int xPoint, int yPoint) {
        maxDistance = maxDistance();
        drawNode = false;

        snapNode(xPoint, yPoint);
        if (snapSelected) {
            snap(xPoint, yPoint);
            drawNode = true;
        }

        boolean selectNode = false;
        if (jPDraw_LabelSelected == labelsNumeration.jPDraw_Move && nClicks == 1) {
            selectNode = true;
        }

        for (DrawEllipse node : arrayListNodes) {
            if (node.selected == false || selectNode == false) {
                node_selectNode(node, xPoint, yPoint);
            }
        }
        for (DrawLine line : arrayListLines) {
            if (line.selected == false || selectNode == false) {
                line_selectNode(line, xPoint, yPoint);
            }
        }
        for (DrawPolygon polygon : arrayListPolygons) {
            if (polygon.selected == false || selectNode == false) {
                polygon_selectNode(polygon, xPoint, yPoint);
            }
        }
        for (DrawSupports support : arrayListSupports) {
            if (support.selected == false || selectNode == false) {
                support_selectNode(support, xPoint, yPoint);
            }
        }
        for (DrawLoads load : arrayListLoads) {
            if (load.selected == false || selectNode == false) {
                load_selectNode(load, xPoint, yPoint);
            }
        }
        for (DrawDimensionLine dimensionLine : arrayListDimensionLines) {
            if (dimensionLine.selected == false || selectNode == false) {
                dimensionLine_selectNode(dimensionLine, xPoint, yPoint);
            }
        }

        if (maxDistance < maxDistance()) {
            drawNode = true;
        }
        repaint();
    }

    /**
     * Método para definir as coordenadas e a distância ao nó mais próximo
     *
     * @param node
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    private void node_selectNode(DrawEllipse node, int xPoint, int yPoint) {
        Point mouse = node.selectPoint(xPoint, yPoint, 12.0);

        if (mouse != null) {
            double distance = distance(xPoint, yPoint, mouse.x, mouse.y);

            if (distance < maxDistance) {
                maxDistance = distance;
                mouseX = mouse.x;
                mouseY = mouse.y;
            }
        }
    }

    /**
     * Método para definir as coordenadas e a distância ao nó mais próximo de uma linha
     *
     * @param line
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    private void line_selectNode(DrawLine line, int xPoint, int yPoint) {
        Point mouse = line.selectPoint(xPoint, yPoint, 12.0);

        if (mouse != null) {
            double distance = distance(xPoint, yPoint, mouse.x, mouse.y);

            if (distance < maxDistance) {
                maxDistance = distance;
                mouseX = mouse.x;
                mouseY = mouse.y;
            }
        }
    }

    /**
     * Método para definir as coordenadas e a distância ao nó mais próximo de um polígono
     *
     * @param polygon
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    private void polygon_selectNode(DrawPolygon polygon, int xPoint, int yPoint) {
        Point mouse = polygon.selectPoint(xPoint, yPoint, 12.0);

        if (mouse != null) {
            double distance = distance(xPoint, yPoint, mouse.x, mouse.y);

            if (distance < maxDistance) {
                maxDistance = distance;
                mouseX = mouse.x;
                mouseY = mouse.y;
            }
        }
    }

    /**
     * Método para definir as coordenadas e a distância ao nó mais próximo de um apoio estrutural
     *
     * @param support
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    private void support_selectNode(DrawSupports support, int xPoint, int yPoint) {
        Point mouse = support.selectPoint(xPoint, yPoint, 12.0);

        if (mouse != null) {
            double distance = distance(xPoint, yPoint, mouse.x, mouse.y);

            if (distance < maxDistance) {
                maxDistance = distance;
                mouseX = mouse.x;
                mouseY = mouse.y;
            }
        }
    }

    /**
     * Método para definir as coordenadas e a distância ao nó mais próximo de uma carga estrutural
     *
     * @param load
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    private void load_selectNode(DrawLoads load, int xPoint, int yPoint) {
        Point mouse = load.selectPoint(xPoint, yPoint, 12.0);

        if (mouse != null) {
            double distance = distance(xPoint, yPoint, mouse.x, mouse.y);

            if (distance < maxDistance) {
                maxDistance = distance;
                mouseX = mouse.x;
                mouseY = mouse.y;
            }
        }
    }

    /**
     * Método para definir as coordenadas e a distância ao nó mais próximo da linha de cotagem
     *
     * @param dimensionLine
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    private void dimensionLine_selectNode(DrawDimensionLine dimensionLine, int xPoint, int yPoint) {
        Point mouse = dimensionLine.selectPoint(xPoint, yPoint, 12.0);

        if (mouse != null) {
            double distance = distance(xPoint, yPoint, mouse.x, mouse.y);

            if (distance < maxDistance) {
                maxDistance = distance;
                mouseX = mouse.x;
                mouseY = mouse.y;
            }
        }
    }

    /**
     * Este método altera as coordenadas do rato para o múltiplo de 25 ou 50 mais próximo
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    private void snap(int xPoint, int yPoint) {
        if (gridPoints == 25) {
            mouseX = (int) (Math.round(xPoint / 25.0) * 25);
            mouseY = (int) (Math.round(yPoint / 25.0) * 25);
        }
        if (gridPoints == 50) {
            mouseX = (int) (Math.round(xPoint / 50.0) * 50);
            mouseY = (int) (Math.round(yPoint / 50.0) * 50);
        }
    }

    /**
     * Este método altera as coordenadas do rato para o ponto calculado
     *
     * @param xPoint
     * @param yPoint
     */
    private void snapNode(int xPoint, int yPoint) {
        for (Point2D.Float point : arrayListPoints) {
            double distance = distance(xPoint, yPoint, (int) point.x, (int) point.y);

            if (distance < maxDistance) {
                maxDistance = distance;
                mouseX = (int) point.x;
                mouseY = (int) point.y;
            }
        }
    }

    /**
     * Método para selecionar os objetos desenhados
     *
     * @param rectangle é o retângulo de seleção
     */
    private void selectShape(DrawRectangle selectionRectangle) {
        for (DrawEllipse node : arrayListNodes) {
            int[][] coordinates = node.getCoordinates();

            if (selectionRectangle.contains(coordinates[0][0], coordinates[0][1])) {
                node.select(true);
            }
        }

        for (DrawLine line : arrayListLines) {
            if ("Line".equals(line.shape)) {
                int[][] coordinates = line.getCoordinates();
                int counter = 0;

                if (selectionRectangle.contains(coordinates[0][0], coordinates[0][1])) {
                    counter++;
                }
                if (selectionRectangle.contains(coordinates[1][0], coordinates[1][1])) {
                    counter++;
                }

                if (counter == 2) {
                    line.select(true);
                } else {
                    line.select(false);
                }
            }
        }

        for (DrawPolygon triangle : arrayListPolygons) {
            if ("Triangle".equals(triangle.shape)) {
                int[][] coordinates = triangle.getCoordinates();
                int counter = 0;

                for (int[] coordinate : coordinates) {
                    if (selectionRectangle.contains(coordinate[0], coordinate[1])) {
                        counter++;
                    }
                }

                if (counter == 3) {
                    triangle.select(true);
                } else {
                    triangle.select(false);
                }
            }
        }

        for (DrawPolygon rectQuadrilateral : arrayListPolygons) {
            if ("Rectangle".equals(rectQuadrilateral.shape) || "Quadrilateral".equals(rectQuadrilateral.shape)) {
                int[][] coordinates = rectQuadrilateral.getCoordinates();
                int counter = 0;

                for (int[] coordinate : coordinates) {
                    if (selectionRectangle.contains(coordinate[0], coordinate[1])) {
                        counter++;
                    }
                }

                if (counter == 4) {
                    rectQuadrilateral.select(true);
                } else {
                    rectQuadrilateral.select(false);
                }
            }
        }

        for (DrawSupports support : arrayListSupports) {
            int[][] coordinates = support.getCoordinates();
            if (selectionRectangle.contains(coordinates[0][0], coordinates[0][1])) {
                support.select(true);
            } else {
                support.select(false);
            }
        }

        for (DrawLoads load : arrayListLoads) {
            if ("Concentrated Load".equals(load.loadType) || "Bending Moment".equals(load.loadType)) {
                int[][] coordinates = load.getCoordinates();

                if (selectionRectangle.contains(coordinates[0][0], coordinates[0][1])) {
                    load.select(true);
                } else {
                    load.select(false);
                }
            }
            if ("Distributed Load".equals(load.loadType) || "Axial Load".equals(load.loadType) || "Thermal Load".equals(load.loadType)) {
                int[][] coordinates = load.getCoordinates();
                int counter = 0;

                if (selectionRectangle.contains(coordinates[0][0], coordinates[0][1])) {
                    counter++;
                }
                if (selectionRectangle.contains(coordinates[1][0], coordinates[1][1])) {
                    counter++;
                }

                if (counter == 2) {
                    load.select(true);
                } else {
                    load.select(false);
                }
            }
            if ("Planar Load".equals(load.loadType)) {
                int[][] coordinates = load.getCoordinates();
                int counter = 0;

                for (int[] coordinate : coordinates) {
                    if (selectionRectangle.contains(coordinate[0], coordinate[1])) {
                        counter++;
                    }
                }

                if (counter == coordinates.length) {
                    load.select(true);
                } else {
                    load.select(false);
                }
            }
        }

        for (DrawElasticSupports support : arrayListElasticSupports) {
            int[][] coordinates = support.getCoordinates();
            if (selectionRectangle.contains(coordinates[0][0], coordinates[0][1])) {
                support.select(true);
            } else {
                support.select(false);
            }
        }

        for (DrawSettlements settlement : arrayListSettlements) {
            int[][] coordinates = settlement.getCoordinates();
            if (selectionRectangle.contains(coordinates[0][0], coordinates[0][1])) {
                settlement.select(true);
            } else {
                settlement.select(false);
            }
        }

        for (DrawDimensionLine dimensionLine : arrayListDimensionLines) {
            int[][] coordinates = dimensionLine.getCoordinates();
            int counter = 0;

            if (selectionRectangle.contains(coordinates[0][0], coordinates[0][1])) {
                counter++;
            }
            if (selectionRectangle.contains(coordinates[1][0], coordinates[1][1])) {
                counter++;
            }

            if (counter == 2) {
                dimensionLine.select(true);
            } else {
                dimensionLine.select(false);
            }
        }
    }

    /**
     * Método para validar os apoios e as cargas após eliminação de elementos da malha
     */
    private void validateSupportsAndLoads() {
        int i, j;

        //Verificação quanto aos apoios estruturais
        i = 0;
        j = arrayListSupports.size();
        while (i < j) {
            DrawSupports support = arrayListSupports.get(i);
            boolean contains = false;

            for (DrawLine line : arrayListLines) {
                if (line.vertexContains(new Point(support.point.x, support.point.y))) {
                    contains = true;
                    break;
                }
            }
            for (DrawPolygon polygon : arrayListPolygons) {
                if (polygon.vertexContains(new Point(support.point.x, support.point.y))) {
                    contains = true;
                    break;
                }
            }

            if (contains == false) {
                arrayListSupports.remove(i);
                j--;
                i--;
            }
            i++;
        }

        //Verificação quanto aos apoios elásticos
        i = 0;
        j = arrayListElasticSupports.size();
        while (i < j) {
            DrawElasticSupports support = arrayListElasticSupports.get(i);
            boolean contains = false;

            for (DrawLine line : arrayListLines) {
                if (line.vertexContains(new Point(support.point.x, support.point.y))) {
                    contains = true;
                    break;
                }
            }
            for (DrawPolygon polygon : arrayListPolygons) {
                if (polygon.vertexContains(new Point(support.point.x, support.point.y))) {
                    contains = true;
                    break;
                }
            }

            if (contains == false) {
                arrayListElasticSupports.remove(i);
                j--;
                i--;
            }
            i++;
        }

        //Verificação quanto aos assentamentos de apoio
        i = 0;
        j = arrayListSettlements.size();
        while (i < j) {
            DrawSettlements settlement = arrayListSettlements.get(i);
            boolean contains = false;

            for (DrawSupports support : arrayListSupports) {
                if (support.contains(new Point(settlement.point.x, settlement.point.y))) {
                    contains = true;
                    break;
                }
            }

            if (contains == false) {
                arrayListSettlements.remove(i);
                j--;
                i--;
            }
            i++;
        }

        //Verificação quanto às cargas estruturais
        i = 0;
        j = arrayListLoads.size();
        while (i < j) {
            DrawLoads load = arrayListLoads.get(i);
            int[][] coordinates = load.getCoordinates();
            int nPoints = coordinates.length;
            boolean contains = false;

            for (DrawLine line : arrayListLines) {
                int[][] lineCoordinates = line.getCoordinates();
                int points = 0;

                for (int k = 0; k < nPoints; k++) {
                    if (lineCoordinates[0][0] == coordinates[k][0] && lineCoordinates[0][1] == coordinates[k][1]) {
                        points++;
                    }
                    if (lineCoordinates[1][0] == coordinates[k][0] && lineCoordinates[1][1] == coordinates[k][1]) {
                        points++;
                    }
                }

                if (points == nPoints) {
                    contains = true;
                }
            }
            for (DrawPolygon polygon : arrayListPolygons) {
                int points = 0;

                for (int k = 0; k < nPoints; k++) {
                    if (polygon.vertexContains(new Point(coordinates[k][0], coordinates[k][1]))) {
                        points++;
                    }
                }

                if (points == nPoints) {
                    contains = true;
                }
            }

            if (contains == false) {
                arrayListLoads.remove(i);
                j--;
                i--;
            }
            i++;
        }
    }

    /**
     * Método que devolve uma lista de pontos pertencentes a um quadrilátero
     *
     * @return
     */
    private ArrayList<Point.Float> createPointsForNodes() {
        ArrayList<Point.Float> listOfPoints = new ArrayList();

        for (DrawPolygon polygon : arrayListPolygons) {
            String shape = polygon.shape;

            if ("Rectangle".equals(shape) || "Quadrilateral".equals(shape)) {
                listOfPoints.addAll(DrawingMethods.createPointsForNodes(polygon));
            }
        }

        return listOfPoints;
    }

    /**
     * Método para refinar a malha de linhas
     */
    private void drawMeshRefinement_Lines() {
        ArrayList<DrawLine> listOfLines = new ArrayList();
        int counter = 0;

        for (DrawLine line : arrayListLines) {
            if (line.selected) {
                listOfLines.addAll(DrawingMethods.meshRefinementLines(line));
                drawRefinement_LinearLoads(line);
                counter++;
            } else {
                listOfLines.add(line);
            }
        }

        if (counter == 0) {
            listOfLines = new ArrayList();

            for (DrawLine line : arrayListLines) {
                listOfLines.addAll(DrawingMethods.meshRefinementLines(line));
                drawRefinement_LinearLoads(line);
            }
        }

        arrayListLines = listOfLines;
    }

    /**
     * Método para refinar a malha de triângulos
     */
    private void drawMeshRefinement_Triangles() {
        ArrayList<DrawPolygon> listOfPolygons = new ArrayList();
        int counter = 0;

        for (DrawPolygon polygon : arrayListPolygons) {
            String shape = polygon.shape;

            if ("Triangle".equals(shape)) {
                if (polygon.selected) {
                    listOfPolygons.addAll(DrawingMethods.meshRefinementTriangles(polygon));
                    drawRefinement_LinearLoads(polygon);
                    trianglePlanarLoads(polygon);
                    counter++;
                } else {
                    listOfPolygons.add(polygon);
                }
            } else {
                listOfPolygons.add(polygon);
            }
        }

        if (counter == 0) {
            listOfPolygons = new ArrayList();

            for (DrawPolygon polygon : arrayListPolygons) {
                String shape = polygon.shape;

                if ("Triangle".equals(shape)) {
                    listOfPolygons.addAll(DrawingMethods.meshRefinementTriangles(polygon));
                    drawRefinement_LinearLoads(polygon);
                    trianglePlanarLoads(polygon);
                } else {
                    listOfPolygons.add(polygon);
                }
            }
        }

        arrayListPolygons = listOfPolygons;
    }

    /**
     * Método para refinar a malha de retângulos
     */
    private void drawMeshRefinement_Rectangles() {
        drawMeshRefinement_Quadrilaterals();
    }

    /**
     * Método para refinar a malha de quadriláteros
     */
    private void drawMeshRefinement_Quadrilaterals() {
        ArrayList<DrawPolygon> listOfPolygons = new ArrayList();
        int counter = 0;

        for (DrawPolygon polygon : arrayListPolygons) {
            String shape = polygon.shape;

            if ("Rectangle".equals(shape) || "Quadrilateral".equals(shape)) {
                if (polygon.selected) {
                    listOfPolygons.addAll(DrawingMethods.meshRefinementQuadrilaterals(polygon));
                    drawRefinement_LinearLoads(polygon);
                    quadrilateralPlanarLoads(polygon);
                    counter++;
                } else {
                    listOfPolygons.add(polygon);
                }
            } else {
                listOfPolygons.add(polygon);
            }
        }

        if (counter == 0) {
            listOfPolygons = new ArrayList();

            for (DrawPolygon polygon : arrayListPolygons) {
                String shape = polygon.shape;

                if ("Rectangle".equals(shape) || "Quadrilateral".equals(shape)) {
                    listOfPolygons.addAll(DrawingMethods.meshRefinementQuadrilaterals(polygon));
                    drawRefinement_LinearLoads(polygon);
                    quadrilateralPlanarLoads(polygon);
                } else {
                    listOfPolygons.add(polygon);
                }
            }
        }

        arrayListPolygons = listOfPolygons;
    }

    /**
     * Método para refinar cargas uniformemente distribuídas
     *
     * @param line
     * @param polygon
     */
    private void drawRefinement_LinearLoads(DrawLine line) {
        ArrayList<DrawLoads> listOfLoads = new ArrayList();

        for (DrawLoads load : arrayListLoads) {
            if ("Distributed Load".equals(load.loadType) || "Axial Load".equals(load.loadType) || "Thermal Load".equals(load.loadType)) {
                int[][] coordinatesOfLoad = load.getCoordinates();
                int[][] coordinatesOfShape = line.getCoordinates();

                if (sameCoordinates(coordinatesOfLoad, coordinatesOfShape)) {
                    listOfLoads.addAll(DrawingMethods.meshRefinementLinearLoads(load));
                } else {
                    listOfLoads.add(load);
                }
            } else {
                listOfLoads.add(load);
            }
        }

        arrayListLoads = listOfLoads;
    }

    /**
     * Método para refinar cargas uniformemente distribuídas
     *
     * @param line
     * @param polygon
     */
    private void drawRefinement_LinearLoads(DrawPolygon polygon) {
        ArrayList<DrawLoads> listOfLoads = new ArrayList();

        for (DrawLoads load : arrayListLoads) {
            if ("Distributed Load".equals(load.loadType) || "Axial Load".equals(load.loadType)) {
                int[][] coordinatesOfLoad = load.getCoordinates();
                int[][] coordinatesOfShape = polygon.getCoordinates();

                if (sameCoordinates(coordinatesOfLoad, coordinatesOfShape)) {
                    listOfLoads.addAll(DrawingMethods.meshRefinementLinearLoads(load));
                } else {
                    listOfLoads.add(load);
                }
            } else {
                listOfLoads.add(load);
            }
        }

        arrayListLoads = listOfLoads;
    }

    /**
     * Método para refinar cargas de superfície triangulares
     *
     * @param polygon
     */
    private void trianglePlanarLoads(DrawPolygon polygon) {
        ArrayList<DrawLoads> listOfLoads = new ArrayList();

        for (DrawLoads load : arrayListLoads) {
            int[][] coordinates = load.getCoordinates();

            if ("Planar Load".equals(load.loadType) && coordinates.length == 3) {
                int[][] coordinatesOfLoad = load.getCoordinates();
                int[][] coordinatesOfShape = polygon.getCoordinates();

                if (sameCoordinates("Triangle", coordinatesOfLoad, coordinatesOfShape)) {
                    listOfLoads.addAll(DrawingMethods.meshRefTriangleLoads(load));
                } else {
                    listOfLoads.add(load);
                }
            } else {
                listOfLoads.add(load);
            }
        }

        arrayListLoads = listOfLoads;
    }

    /**
     * Método para refinar cargas de superfície qudriláteras
     *
     * @param polygon
     */
    private void quadrilateralPlanarLoads(DrawPolygon polygon) {
        ArrayList<DrawLoads> listOfLoads = new ArrayList();

        for (DrawLoads load : arrayListLoads) {
            int[][] coordinates = load.getCoordinates();

            if ("Planar Load".equals(load.loadType) && coordinates.length == 4) {
                int[][] coordinatesOfLoad = load.getCoordinates();
                int[][] coordinatesOfShape = polygon.getCoordinates();

                if (sameCoordinates("Quadrilateral", coordinatesOfLoad, coordinatesOfShape)) {
                    listOfLoads.addAll(DrawingMethods.meshRefQuadrilateralLoads(load));
                } else {
                    listOfLoads.add(load);
                }
            } else {
                listOfLoads.add(load);
            }
        }

        arrayListLoads = listOfLoads;
    }

    /**
     * Método para comparar as coordenadas da carga com as da figura geométrica
     *
     * @param loadCoordinates
     * @param shapeCoordinates
     */
    private boolean sameCoordinates(int[][] coordinatesOfLoad, int[][] coordinatesOfShape) {
        boolean equals = false;
        int counter = 0;

        for (int i = 0; i < 2; i++) {
            for (int[] shapeCoordinate : coordinatesOfShape) {
                if (coordinatesOfLoad[i][0] == shapeCoordinate[0]) {
                    if (coordinatesOfLoad[i][1] == shapeCoordinate[1]) {
                        counter++;
                        break;
                    }
                }
            }
        }

        if (counter == 2) {
            equals = true;
        }

        return equals;
    }

    /**
     * Método para comparar as coordenadas da carga com as da figura geométrica
     *
     * @param shape
     * @param loadCoordinates
     * @param shapeCoordinates
     */
    private boolean sameCoordinates(String shape, int[][] coordinatesOfLoad, int[][] coordinatesOfShape) {
        boolean equals = false;
        int counter = 0;

        if ("Triangle".equals(shape)) {
            for (int i = 0; i < 3; i++) {
                for (int[] shapeCoordinate : coordinatesOfShape) {
                    if (coordinatesOfLoad[i][0] == shapeCoordinate[0]) {
                        if (coordinatesOfLoad[i][1] == shapeCoordinate[1]) {
                            counter++;
                            break;
                        }
                    }
                }
            }

            if (counter == 3) {
                equals = true;
            }
        }

        if ("Quadrilateral".equals(shape)) {
            for (int i = 0; i < 4; i++) {
                for (int[] shapeCoordinate : coordinatesOfShape) {
                    if (coordinatesOfLoad[i][0] == shapeCoordinate[0]) {
                        if (coordinatesOfLoad[i][1] == shapeCoordinate[1]) {
                            counter++;
                            break;
                        }
                    }
                }
            }

            if (counter == 4) {
                equals = true;
            }
        }

        return equals;
    }

    /**
     * Método para criar e mandar desenhar uma carga concentrada
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     * @param load é a carga concentrada a desenhar
     */
    private void drawConcentratedLoad(int xPoint, int yPoint, String[] load) {
        boolean createLoad = false;

        Point point = new Point(xPoint, yPoint);

        if (createLoad == false) {
            for (DrawLine line : arrayListLines) {
                if (line.vertexContains(point)) {
                    createLoad = true;
                    break;
                }
            }
        }
        if (createLoad == false) {
            for (DrawPolygon polygon : arrayListPolygons) {
                if (polygon.vertexContains(point)) {
                    createLoad = true;
                    break;
                }
            }
        }

        if (createLoad) {
            arrayListLoads.add(new DrawLoads(xPoint, yPoint, load));
            command();
            repaint();
        }
    }

    /**
     * Método para criar e mandar desenhar um momento fletor
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     * @param load é o momento fletor a desenhar
     */
    private void drawBendingMoment(int xPoint, int yPoint, String[] load) {
        boolean createLoad = false;

        Point point = new Point(xPoint, yPoint);

        if (createLoad == false) {
            for (DrawLine line : arrayListLines) {
                if (line.vertexContains(point)) {
                    createLoad = true;
                    break;
                }
            }
        }
        if (createLoad == false) {
            for (DrawPolygon polygon : arrayListPolygons) {
                if (polygon.vertexContains(point)) {
                    createLoad = true;
                    break;
                }
            }
        }

        if (createLoad) {
            arrayListLoads.add(new DrawLoads(xPoint, yPoint, load));
            command();
            repaint();
        }
    }

    /**
     * Método para criar e mandar desenhar uma carga uniformemente distribuída
     *
     * @param xPoint é a abscissa do último ponto da carga
     * @param yPoint é a ordenada do último ponto da carga
     * @param load é a carga uniformemente distribuída a desenhar
     */
    private void drawUnifDistLoad(int xPoint, int yPoint, String[] load) {
        nClicks++;
        if (nClicks == 1) {
            loadCoordinates[0] = xPoint;
            loadCoordinates[1] = yPoint;
        }

        if (nClicks == 2) {
            int x1 = loadCoordinates[0];
            int y1 = loadCoordinates[1];
            if (x1 != xPoint || y1 != yPoint) {
                /*
                 * Validação para verificar se a carga pertence ao mesmo
                 * elemento finito e se cumpre o posicionamento sequencial do nós
                 */
                int[][] load_Coordinates = { { x1, y1 }, { xPoint, yPoint } };
                boolean valid = validateLoads(load_Coordinates);

                if (valid) {
                    arrayListLoads.add(new DrawLoads(x1, y1, xPoint, yPoint, load));
                    command();
                    repaint();
                } else {
                    nClicks--;
                }
            } else {
                nClicks--;
            }
        }

        nClicks = nClicks(nClicks, "UnifDistLoad");
    }

    /**
     * Método para criar e mandar desenhar uma carga axial distribuída
     *
     * @param xPoint é a abscissa do último ponto da carga
     * @param yPoint é a ordenada do último ponto da carga
     * @param load é a carga axial distribuída a desenhar
     */
    private void drawDistAxialLoad(int xPoint, int yPoint, String[] load) {
        nClicks++;
        if (nClicks == 1) {
            loadCoordinates[0] = xPoint;
            loadCoordinates[1] = yPoint;
        }

        if (nClicks == 2) {
            int x1 = loadCoordinates[0];
            int y1 = loadCoordinates[1];
            if (x1 != xPoint || y1 != yPoint) {
                /*
                 * Validação para verificar se a carga pertence ao mesmo
                 * elemento finito e se cumpre o posicionamento sequencial do nós
                 */
                int[][] load_Coordinates = { { x1, y1 }, { xPoint, yPoint } };
                boolean valid = validateLoads(load_Coordinates);

                if (valid) {
                    arrayListLoads.add(new DrawLoads(x1, y1, xPoint, yPoint, load));
                    command();
                    repaint();
                } else {
                    nClicks--;
                }
            } else {
                nClicks--;
            }
        }

        nClicks = nClicks(nClicks, "DistAxialLoad");
    }

    /**
     * Método para criar e mandar desenhar uma carga de superfície
     *
     * @param xPoint é a abscissa do último ponto da carga
     * @param yPoint é a ordenada do último ponto da carga
     * @param load é a carga uniformemente distribuída a desenhar
     */
    private void drawUnifPlanarLoad(int xPoint, int yPoint, String[] load) {
        Point point = new Point(xPoint, yPoint);

        for (DrawPolygon polygon : arrayListPolygons) {
            if (polygon.contains(point)) {
                arrayListLoads.add(new DrawLoads(polygon, load));
                break;
            }
        }

        command();
        repaint();
    }

    /**
     * Método para criar e mandar desenhar uma variação de temperatura
     *
     * @param xPoint é a abscissa do último ponto da carga
     * @param yPoint é a ordenada do último ponto da carga
     * @param load é a carga axial distribuída a desenhar
     */
    private void drawThermalLoad(int xPoint, int yPoint, String[] load) {
        nClicks++;
        if (nClicks == 1) {
            loadCoordinates[0] = xPoint;
            loadCoordinates[1] = yPoint;
        }

        if (nClicks == 2) {
            int x1 = loadCoordinates[0];
            int y1 = loadCoordinates[1];
            if (x1 != xPoint || y1 != yPoint) {
                /*
                 * Validação para verificar se a carga pertence ao mesmo
                 * elemento finito e se cumpre o posicionamento sequencial do nós
                 */
                int[][] load_Coordinates = { { x1, y1 }, { xPoint, yPoint } };
                boolean valid = validateLoads(load_Coordinates);

                if (valid) {
                    arrayListLoads.add(new DrawLoads(x1, y1, xPoint, yPoint, load));
                    command();
                    repaint();
                } else {
                    nClicks--;
                }
            } else {
                nClicks--;
            }
        }

        nClicks = nClicks(nClicks, "ThermalLoad");
    }

    /**
     * Método para validar as posições da carga a criar
     *
     * @param loadCoordinates
     * @return
     */
    private boolean validateLoads(int[][] loadCoordinates) {
        boolean valid = false;

        if (valid == false) {
            for (DrawLine line : arrayListLines) {
                int[][] shape_Coordinates = line.getCoordinates();

                valid = DrawingMethods.validateLoad(loadCoordinates, shape_Coordinates);
                if (valid) {
                    break;
                }
            }
        }

        if (valid == false) {
            for (DrawPolygon polygon : arrayListPolygons) {
                int[][] shape_Coordinates = polygon.getCoordinates();

                valid = DrawingMethods.validateLoad(loadCoordinates, shape_Coordinates);
                if (valid) {
                    break;
                }
            }
        }

        return valid;
    }

    /**
     * Método para atualizar os atributos das cargas estruturais
     *
     * @param instructions
     */
    private boolean updateAllLoads(String[] instructions) {
        boolean result = false;

        for (DrawLoads load : arrayListLoads) {
            boolean update = load.updateLoad(instructions);
            if (update) {
                result = true;
            }
        }

        if (arrayListCopyLoads.size() > 0) {
            for (DrawLoads load : arrayListCopyLoads) {
                load.updateLoad(instructions);
            }
        }

        return result;
    }

    /**
     * Método para eliminar as cargas estruturais selecionadas
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    private void deleteAllLoads(int xPoint, int yPoint) {
        Point point = new Point(xPoint, yPoint);
        boolean repaint = false;
        int i, j;

        i = 0;
        j = arrayListLoads.size();
        while (i < j) {
            DrawLoads load = arrayListLoads.get(i);
            if (!"Planar Load".equals(load.loadType)) {
                if (load.contains(point)) {
                    arrayListLoads.remove(i);
                    repaint = true;
                    j--;
                    i--;
                }
            }
            i++;
        }

        if (repaint == true) {
            repaint();
        }
    }

    /**
     *  Método para eliminar as cargas estruturais selecionadas
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    private void deletePlanarLoads(int xPoint, int yPoint) {
        Point point = new Point(xPoint, yPoint);
        boolean repaint = false;
        int i, j;

        i = 0;
        j = arrayListLoads.size();
        while (i < j) {
            DrawLoads load = arrayListLoads.get(i);
            if ("Planar Load".equals(load.loadType)) {
                if (load.polygonContains(point)) {
                    arrayListLoads.remove(i);
                    repaint = true;
                    j--;
                    i--;
                }
            }
            i++;
        }

        if (repaint == true) {
            repaint();
        }
    }

    /**
     * Método para criar e mandar desenhar um apoio estrutural
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     * @param support é o tipo de apoio estrutural a desenhar
     */
    private void drawSupports(int xPoint, int yPoint, String support) {
        boolean drawSupport = false;

        Point point = new Point(xPoint, yPoint);

        if (drawSupport == false) {
            for (DrawLine line : arrayListLines) {
                if (line.vertexContains(point)) {
                    drawSupport = true;
                    break;
                }
            }
        }
        if (drawSupport == false) {
            for (DrawPolygon polygon : arrayListPolygons) {
                if (polygon.vertexContains(point)) {
                    drawSupport = true;
                    break;
                }
            }
        }

        if (drawSupport == true) {
            for (DrawSupports supports : arrayListSupports) {
                if (supports.contains(point)) {
                    drawSupport = false;
                }
            }
        }
        if (drawSupport == true) {
            for (DrawElasticSupports supports : arrayListElasticSupports) {
                if (supports.contains(point)) {
                    drawSupport = false;
                }
            }
        }

        if (drawSupport) {
            arrayListSupports.add(new DrawSupports(xPoint, yPoint, support));
            command();
            repaint();
        }
    }

    /**
     * Método para criar e mandar desenhar um apoio elástico
     *
     * @param name é o nome do apoio elástico
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     * @param stiffness contém os valores de rigidez das molas
     */
    private void drawElasticSupports(String name, int xPoint, int yPoint, double[] stiffness) {
        boolean drawSupport = false;
        Point point = new Point(xPoint, yPoint);

        if (drawSupport == false) {
            for (DrawLine line : arrayListLines) {
                if (line.vertexContains(point)) {
                    drawSupport = true;
                    break;
                }
            }
        }
        if (drawSupport == false) {
            for (DrawPolygon polygon : arrayListPolygons) {
                if (polygon.vertexContains(point)) {
                    drawSupport = true;
                    break;
                }
            }
        }

        if (drawSupport == true) {
            for (DrawSupports support : arrayListSupports) {
                if (support.contains(point)) {
                    drawSupport = false;
                }
            }
        }

        if (drawSupport) {
            arrayListElasticSupports.add(new DrawElasticSupports(name, xPoint, yPoint, stiffness));
            command();
            repaint();
        }
    }

    /**
     * Método para criar e mandar desenhar um apoio elástico
     *
     * @param name é o nome do assentamento de apoio
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     * @param displacements contém os valores do assentamento de apoio
     */
    private void drawSettlements(String name, int xPoint, int yPoint, double[] displacements) {
        Point point = new Point(xPoint, yPoint);
        boolean drawSettlement = false;

        //Instrução para verificar se o ponto está associado a um apoio estrutural
        for (DrawSupports support : arrayListSupports) {
            if (support.contains(point)) {
                drawSettlement = true;
                break;
            }
        }

        //Instrução para verificar se não existe um assentamento já aplicado
        if (drawSettlement) {
            for (DrawSettlements support : arrayListSettlements) {
                if (support.contains(point)) {
                    drawSettlement = false;
                }
            }
        }

        //Instrução para verificar se o assentamento é válido para as condições de apoio
        if (drawSettlement) {
            for (DrawSupports support : arrayListSupports) {
                if (xPoint == support.point.x && yPoint == support.point.y) {
                    drawSettlement = DrawingMethods.validateSettlements(type, support.support, displacements);
                    if (drawSettlement == false) {
                        break;
                    }
                }
            }
        }

        if (drawSettlement) {
            arrayListSettlements.add(new DrawSettlements(name, xPoint, yPoint, displacements));
            command();
            repaint();
        }
    }

    /**
     * Método para verificar a possibilidade de atualizar o valor do assentamento
     *
     * @param name contém o nome do assentamento de apoio
     * @param displacements contém os valores do assentamento de apoio
     * @return
     */
    private boolean isPossibleUpdateSettlements(String name, double[] displacements) {
        boolean result = true;
        int index;

        index = 0;
        while (result && index < arrayListSettlements.size()) {
            DrawSettlements settlement = arrayListSettlements.get(index);

            if (settlement.name.equals(name)) {
                for (DrawSupports support : arrayListSupports) {
                    if (settlement.point.x == support.point.x && settlement.point.y == support.point.y) {
                        result = DrawingMethods.validateSettlements(type, support.support, displacements);
                        if (result == false) {
                            break;
                        }
                    }
                }
            }

            index++;
        }

        index = 0;
        while (result && index < arrayListCopySettlements.size()) {
            DrawSettlements settlement = arrayListCopySettlements.get(index);

            if (settlement.name.equals(name)) {
                for (DrawSupports support : arrayListCopySupports) {
                    if (settlement.point.x == support.point.x && settlement.point.y == support.point.y) {
                        result = DrawingMethods.validateSettlements(type, support.support, displacements);
                        if (result == false) {
                            break;
                        }
                    }
                }
            }

            index++;
        }

        return result;
    }

    /**
     * Método para eliminar o apoio estrutural selecionado
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     */
    private void deleteAllSupports(int xPoint, int yPoint) {
        Point point = new Point(xPoint, yPoint);
        boolean repaint = false;
        int i, j;

        i = 0;
        j = arrayListSupports.size();
        while (i < j) {
            DrawSupports support = arrayListSupports.get(i);
            if (support.contains(point)) {
                arrayListSupports.remove(i);
                repaint = true;
                j--;
                i--;
            }
            i++;
        }

        i = 0;
        j = arrayListElasticSupports.size();
        while (i < j) {
            DrawElasticSupports support = arrayListElasticSupports.get(i);
            if (support.contains(point)) {
                arrayListElasticSupports.remove(i);
                repaint = true;
                j--;
                i--;
            }
            i++;
        }

        i = 0;
        j = arrayListSettlements.size();
        while (i < j) {
            DrawSettlements support = arrayListSettlements.get(i);
            if (support.contains(point)) {
                arrayListSettlements.remove(i);
                repaint = true;
                j--;
                i--;
            }
            i++;
        }

        if (repaint == true) {
            repaint();
        }
    }

    /**
     * Método para desenhar a informação relativa à geometria das formas
     *
     * @param shape
     */
    private void drawLegends(Graphics2D shape) {
        shape.setPaint(Color.RED);

        //Desenho da informação relativa a uma linha temporária
        if (jPDraw_LabelSelected == labelsNumeration.jPDraw_Line) {
            int[][] lineCoordinates = new int[2][2];
            lineCoordinates[0][0] = lines[0];
            lineCoordinates[0][1] = lines[1];
            lineCoordinates[1][0] = lines[2];
            lineCoordinates[1][1] = lines[3];

            double length = AnalyticGeometry.length(lineCoordinates);
            shape.drawString(decimalFormat(length / factor) + " m", lines[2] + 15, lines[3] + 10);
        }

        //Desenho da informação relativa a um triângulo temporário
        if (jPDraw_LabelSelected == labelsNumeration.jPDraw_Triangle) {
            if (nClicks == 1) {
                double length = AnalyticGeometry.length(xTriangle[0], yTriangle[0], xTriangle[1], yTriangle[1]);
                shape.drawString(decimalFormat(length / factor) + " m", xTriangle[1] + 15, yTriangle[1] + 10);
            }
            if (nClicks == 2) {
                double length;
                length = AnalyticGeometry.length(xTriangle[1], yTriangle[1], xTriangle[2], yTriangle[2]);
                shape.drawString(decimalFormat(length / factor) + " m", xTriangle[2] + 15, yTriangle[2] + 10);
                length = AnalyticGeometry.length(xTriangle[0], yTriangle[0], xTriangle[2], yTriangle[2]);
                shape.drawString(decimalFormat(length / factor) + " m", xTriangle[2] + 15, yTriangle[2] - 10);
            }
        }

        //Desenho da informação relativa a um retângulo temporário
        if (jPDraw_LabelSelected == labelsNumeration.jPDraw_Rectangle) {
            if (nClicks == 1) {
                double length = AnalyticGeometry.length(xRectangle[0], yRectangle[0], xRectangle[1], yRectangle[1]);
                shape.drawString(decimalFormat(length / factor) + " m", xRectangle[1] + 15, yRectangle[1] + 10);
            }
            if (nClicks == 2) {
                double length;
                length = AnalyticGeometry.length(xRectangle[1], yRectangle[1], xRectangle[2], yRectangle[2]);
                shape.drawString(decimalFormat(length / factor) + " m", xRectangle[1] + 15, yRectangle[1] + 10);
                length = AnalyticGeometry.length(xRectangle[0], yRectangle[0], xRectangle[1], yRectangle[1]);
                shape.drawString(decimalFormat(length / factor) + " m", xRectangle[1] + 15, yRectangle[1] - 10);
            }
        }

        //Desenho da informação relativa aum quadrilátero temporário
        if (jPDraw_LabelSelected == labelsNumeration.jPDraw_Quadrilateral) {
            if (nClicks == 1) {
                double length = AnalyticGeometry.length(xQuadrilateral[0], yQuadrilateral[0], xQuadrilateral[1], yQuadrilateral[1]);
                shape.drawString(decimalFormat(length / factor) + " m", xQuadrilateral[1] + 15, yQuadrilateral[1] + 10);
            }
            if (nClicks == 2) {
                double length = AnalyticGeometry.length(xQuadrilateral[1], yQuadrilateral[1], xQuadrilateral[2], yQuadrilateral[2]);
                shape.drawString(decimalFormat(length / factor) + " m", xQuadrilateral[2] + 15, yQuadrilateral[2] + 10);
            }
            if (nClicks == 3) {
                double length;
                length = AnalyticGeometry.length(xQuadrilateral[2], yQuadrilateral[2], xQuadrilateral[3], yQuadrilateral[3]);
                shape.drawString(decimalFormat(length / factor) + " m", xQuadrilateral[3] + 15, yQuadrilateral[3] + 10);
                length = AnalyticGeometry.length(xQuadrilateral[0], yQuadrilateral[0], xQuadrilateral[3], yQuadrilateral[3]);
                shape.drawString(decimalFormat(length / factor) + " m", xQuadrilateral[3] + 15, yQuadrilateral[3] - 10);
            }
        }
    }

    /**
     * Método para calcular a distância entre dois pontos
     *
     * @param x1 é a abscissa do primeiro ponto
     * @param y1 é a ordenada do primeiro ponto
     * @param x2 é a abscissa do último ponto
     * @param y2 é a ordenada do último ponto
     * @return
     */
    private static double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    /**
     * Método para formatar valores decimais na forma de texto
     *
     * @param number é o número a ser formatado
     * @return
     */
    private static String decimalFormat(double number) {
        BigDecimal decimal = new BigDecimal(number).setScale(3, RoundingMode.HALF_EVEN);
        DecimalFormat format = new DecimalFormat("#,###.###");

        return format.format(decimal.doubleValue());
    }
}

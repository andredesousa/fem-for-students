/*
 * Esta classe cria um painel para desenhar os elementos finitos
 * Os elementos finitos são desenhados a partir das coordenadas dos nós
 * Esta classe desenha os resultados para a malha de elementos finitos
 */

package app.backend;

import static java.lang.Math.round;

import app.calculations.AnalyticGeometry;
import app.calculations.FiniteElement;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author André de Sousa
 */
public class ResultsPane extends javax.swing.JPanel {

    public String typeOfResults;

    //Criação dos objetos que compõem a malha de elementos finitos
    private ArrayList<DrawNode> listNodesUndeformed = new ArrayList();
    private ArrayList<DrawNode> listNodesDeformed = new ArrayList();
    private ArrayList<DrawLine> arrayListLines = new ArrayList();
    private ArrayList<DrawPolygon> arrayListPolygons = new ArrayList();
    private ArrayList<DrawSupports> arrayListSupports = new ArrayList();
    private ArrayList<DrawElasticSupports> arrayListElasticSupports = new ArrayList();

    //Criação dos objetos para desenhar a deformada da malha de elementos finitos
    private ArrayList<DrawLine> linesDisplacements = new ArrayList();
    private ArrayList<DrawPolygon> polygonsDisplacements = new ArrayList();

    //Criação dos objectos para desenhar as isolinhas, os mapas e as tensões principais
    private DiagramsForBars diagrams;
    private IsolinesAndMaps isolinesAndMaps;
    private ArrayList<PrincipalStresses> principalStresses = new ArrayList();
    private double maximumStress;

    //Cores para desenhar os objetos nos seus diferentes estados
    private final Color defaultColor = new Color(255, 0, 0);
    private final Color selectedColor = new Color(51, 153, 255);

    //Variáveis para armazenar a escala do desenho
    private int deformedFactor;
    private int factor;
    private int gridPoints;
    private double angle;
    private double xScale;
    private double yScale;

    //Variável relativa ao desenho da malha de pontos
    private boolean drawGrid;
    private boolean drawNodesUndeformed;
    private boolean drawSupportsForSlabs;
    private boolean drawNumbering;
    private boolean drawNodesDeformed;

    //Variável para definir o desenho das isolinhas do dos mapas
    private String drawIsolinesAndMaps;

    /**
     * Construtor sem parâmetros da classe ResultsPane
     */
    public ResultsPane() {
        setAlignmentX(0.0F);
        setAlignmentY(0.0F);
        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(3000, 3000));
        setMinimumSize(new java.awt.Dimension(500, 500));
        setPreferredSize(new java.awt.Dimension(3000, 3000));

        xScale = 1.0;
        yScale = 1.0;
        factor = 100;
        gridPoints = 25;
        deformedFactor = 20000;
        angle = (Math.PI * 60) / 180;
        drawGrid = false;
        drawSupportsForSlabs = false;
        typeOfResults = "";
    }

    /**
     * Construtor com parâmetros da classe ResultsPane
     *
     * @param scale
     * @param factor
     * @param gridPoints
     */
    public ResultsPane(double scale, int factor, int gridPoints) {
        int dimension = (int) (Math.round(3000 * scale));

        setAlignmentX(0.0F);
        setAlignmentY(0.0F);
        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(dimension, dimension));
        setMinimumSize(new java.awt.Dimension(500, 500));
        setPreferredSize(new java.awt.Dimension(dimension, dimension));

        this.xScale = scale;
        this.yScale = scale;
        this.factor = factor;
        this.gridPoints = gridPoints;
        this.deformedFactor = 20000;
        this.angle = (Math.PI * 60) / 180;
        this.drawGrid = false;
        this.drawSupportsForSlabs = false;
        this.typeOfResults = "";
    }

    /**
     * Método para eliminar toda a informação armazenada
     *
     * @param scale
     * @param factor
     * @param gridPoints
     */
    public void resetPanel(double scale, int factor, int gridPoints) {
        //Criação dos objetos que compõem a malha de elementos finitos
        this.listNodesUndeformed = new ArrayList();
        this.listNodesDeformed = new ArrayList();
        this.arrayListLines = new ArrayList();
        this.arrayListPolygons = new ArrayList();
        this.arrayListSupports = new ArrayList();
        this.arrayListElasticSupports = new ArrayList();

        //Criação dos objetos para desenhar a deformada da malha de elementos finitos
        this.linesDisplacements = new ArrayList();
        this.polygonsDisplacements = new ArrayList();

        //Criação dos objectos para desenhar as isolinhas, os mapas e as tensões principais
        this.diagrams = null;
        this.isolinesAndMaps = null;
        this.principalStresses = new ArrayList();
        this.maximumStress = 0.0;

        //Variável relativa ao desenho da malha de pontos
        this.drawGrid = false;
        this.drawNodesUndeformed = false;
        this.drawSupportsForSlabs = false;
        this.drawNumbering = false;
        this.drawNodesDeformed = false;

        //Variável para definir o desenho das isolinhas do dos mapas
        this.drawIsolinesAndMaps = "";

        //Atribuição das propriedades do painel
        if (xScale != scale && yScale != scale) {
            int dimension = (int) (Math.round(3000 * scale));

            setAlignmentX(0.0F);
            setAlignmentY(0.0F);
            setBackground(new java.awt.Color(255, 255, 255));
            setMaximumSize(new java.awt.Dimension(dimension, dimension));
            setMinimumSize(new java.awt.Dimension(500, 500));
            setPreferredSize(new java.awt.Dimension(dimension, dimension));

            this.xScale = scale;
            this.yScale = scale;
        }

        this.factor = factor;
        this.gridPoints = gridPoints;
        this.deformedFactor = 20000;
        this.angle = (Math.PI * 60) / 180;
        this.drawGrid = false;
        this.drawSupportsForSlabs = false;
        this.typeOfResults = "";
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D shape = (Graphics2D) g.create();

        shape.scale(xScale, yScale);

        //Instruções para desenhar a malha de pontos
        if (drawGrid) {
            for (int i = 0; i < 3000 / gridPoints; i++) {
                for (int j = 0; j < (3000 / gridPoints); j++) {
                    shape.drawLine(i * gridPoints, j * gridPoints, i * gridPoints, j * gridPoints);
                }
            }
        }

        //Desenho das linhas de isovalores
        if ("Isolines".equals(drawIsolinesAndMaps)) {
            isolinesAndMaps.drawIsolines(shape);
        }

        //Desenho dos mapas de tensões
        if ("Maps".equals(drawIsolinesAndMaps)) {
            isolinesAndMaps.drawMaps(shape);
        }

        //Desenho dos elementos da estrutura indeformada
        for (DrawPolygon polygon : arrayListPolygons) {
            polygon.draw(shape);
        }
        for (DrawLine line : arrayListLines) {
            line.draw(shape);
        }
        for (DrawSupports support : arrayListSupports) {
            if (drawSupportsForSlabs) {
                support.draw("Slabs", shape);
            } else {
                support.draw(shape);
            }
        }
        for (DrawElasticSupports support : arrayListElasticSupports) {
            support.draw(shape);
        }

        //Instruções para desenhar os nós e a numeração dos elementos finitos
        if (drawNodesUndeformed) {
            for (DrawNode node : listNodesUndeformed) {
                node.draw(shape);
            }
        }
        if (drawNumbering) {
            drawNumberingOfNodes(shape, listNodesUndeformed);
        }
        if (drawNodesDeformed) {
            for (DrawNode node : listNodesDeformed) {
                node.draw(shape);
            }
        }

        //Desenho da estrutura deformada
        for (DrawPolygon polygon : polygonsDisplacements) {
            polygon.draw(shape);
        }
        for (DrawLine line : linesDisplacements) {
            line.draw(shape);
        }

        //Desenho dos diagramas de esforços
        if (diagrams != null) {
            diagrams.drawDiagrams(shape);
        }

        //Desenho das tensões e direcções principais
        for (PrincipalStresses stresses : principalStresses) {
            stresses.draw(shape, maximumStress);
        }
    }

    /**
     * Método para definir a escala do desenho
     *
     * @param xScale
     * @param yScale
     */
    public void setScale(double xScale, double yScale) {
        this.xScale = xScale;
        this.yScale = yScale;
    }

    /**
     * Método para desenhar uma grelha de pontos
     *
     * @param drawGrid
     */
    public void setGrid(boolean drawGrid) {
        this.drawGrid = drawGrid;
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
    public void changeFactor(int factor) {
        this.factor = factor;
    }

    /**
     * Método para mandar desenhar os nós dos elementos finitos
     *
     * @param drawNodes
     */
    public void drawNodesUndeformed(boolean drawNodes) {
        this.drawNodesUndeformed = drawNodes;
    }

    /**
     * Método para mandar desenhar a numeração dos nós dos elementos finitos
     *
     * @param drawNumbering
     */
    public void drawNumberingOfNodes(boolean drawNumbering) {
        this.drawNumbering = drawNumbering;
    }

    /**
     * Método para mandar desenhar os nós dos elementos finitos
     *
     * @param drawNodes
     */
    public void drawNodesDeformed(boolean drawNodes) {
        this.drawNodesDeformed = drawNodes;
    }

    /**
     * Método para alterar o factor de escala do desenho
     *
     * @param factor
     */
    public void changeDeformedFactor(int factor) {
        this.deformedFactor = factor;
    }

    /**
     *
     * @param drawIsolinesAndMaps
     */
    public void setDrawIsolinesAndMaps(String drawIsolinesAndMaps) {
        this.drawIsolinesAndMaps = drawIsolinesAndMaps;
    }

    /**
     * Método para obter os elementos finitos e desenhar a deformada
     *
     * @param arrayListFiniteElements
     */
    public void drawDeformedStructure(ArrayList<FiniteElement> arrayListFiniteElements) {
        for (FiniteElement finiteElement : arrayListFiniteElements) {
            double[][] coordinates, displacements, nodesDisplacements;
            int[][] nodalCoordinates, nodesCoordinates;
            String type, theory, shape;

            type = finiteElement.getType();
            theory = finiteElement.getTheory();
            shape = finiteElement.getShape();

            coordinates = finiteElement.getNodesCoordinates();
            displacements = finiteElement.getDisplacementVector();
            int nodes = finiteElement.getNodes();

            //Converter coordenadas dos nós do elemento finito
            nodalCoordinates = convertCoordinates(coordinates, factor);

            //Instrunções para reduzir o número de coordenadas
            nodesCoordinates = reduceNumberCoordinates(shape, nodalCoordinates, nodes);
            nodesDisplacements = reduceNumberDisplacements(type, theory, shape, displacements, nodes);
            nodes = reduceNumberNodes(shape, nodes);

            //Instruções para criar a estrutura inicial indeformada
            nodesUndeformedStructure(type, nodalCoordinates);
            createUndeformedStructure(type, "Perspective", shape, nodesCoordinates);

            //Instruções para criar a estrutura final deformada
            nodesDeformedStructure(type, theory, nodalCoordinates, displacements);
            createDeformedStructure(type, theory, shape, nodesCoordinates, nodes, nodesDisplacements);
        }

        repaint();
    }

    /**
     * Método para obter os apoios estruturais na malha
     *
     * @param type
     * @param other
     * @param arrayListSupports
     * @param arrayListElasticSupports
     */
    public void drawSupports(
        String type,
        String other,
        ArrayList<DrawSupports> arrayListSupports,
        ArrayList<DrawElasticSupports> arrayListElasticSupports
    ) {
        if ("Slabs".equals(type) || "Grids".equals(type)) {
            if ("Perspective".equals(other)) {
                for (DrawSupports support : arrayListSupports) {
                    int[][] coordinates = support.getCoordinates();

                    int xPoint = coordinates[0][0] + (int) Math.round(Math.cos(angle) * coordinates[0][1]);
                    int yPoint = (int) Math.round(Math.sin(angle) * coordinates[0][1]);

                    this.arrayListSupports.add(new DrawSupports(xPoint, yPoint, support.support));
                }
            } else {
                this.arrayListSupports = arrayListSupports;
            }
        } else {
            this.arrayListSupports = arrayListSupports;
            this.arrayListElasticSupports = arrayListElasticSupports;
        }

        repaint();
    }

    /**
     * Método para obter os elementos finitos e desenhar os diagramas de esfoços
     *
     * @param type
     * @param diagram
     * @param arrayListFiniteElements
     */
    public void drawDiagrams(String type, String diagram, ArrayList<FiniteElement> arrayListFiniteElements) {
        for (FiniteElement finiteElement : arrayListFiniteElements) {
            int nodes = finiteElement.getNodes();
            String shape = finiteElement.getShape();

            //Desenho da malha de elementos finitos da estrutura indeformada
            double[][] coordinatesOfNodes = finiteElement.getNodesCoordinates();
            int[][] nodalCoordinates = convertCoordinates(coordinatesOfNodes, factor);

            //Instrunções para reduzir o número de coordenadas
            int[][] nodesCoordinates = reduceNumberCoordinates("Quadrilateral", nodalCoordinates, nodes);

            //Instruções para criar a estrutura inicial indeformada
            if ("1D".equals(type)) {
                nodesUndeformedStructure(type, nodalCoordinates);
            }
            createUndeformedStructure(type, "", shape, nodesCoordinates);
        }

        this.diagrams = new DiagramsForBars(type, diagram, arrayListFiniteElements, factor);

        repaint();
    }

    /**
     * Método para obter os elementos finitos e desenhar as isolinhas ou os mapas
     *
     * @param type
     * @param stressName
     * @param planeOfSlab
     * @param arrayListFiniteElements
     * @param listOfNodes
     */
    public void drawIsolinesAndMaps(
        String type,
        String stressName,
        String planeOfSlab,
        ArrayList<FiniteElement> arrayListFiniteElements,
        ArrayList<double[]> listOfNodes
    ) {
        for (FiniteElement finiteElement : arrayListFiniteElements) {
            int nodes = finiteElement.getNodes();
            String shape = finiteElement.getShape();

            //Desenho da malha de elementos finitos da estrutura indeformada
            double[][] coordinatesOfNodes = finiteElement.getNodesCoordinates();
            int[][] nodalCoordinates = convertCoordinates(coordinatesOfNodes, factor);

            //Instrunções para reduzir o número de coordenadas
            int[][] nodesCoordinates = reduceNumberCoordinates("Quadrilateral", nodalCoordinates, nodes);

            //Instruções para criar a estrutura inicial indeformada
            createUndeformedStructure(type, "", shape, nodesCoordinates);
        }

        if ("Slabs".equals(type)) {
            drawSupportsForSlabs = true;
        }
        isolinesAndMaps = new IsolinesAndMaps(type, stressName, planeOfSlab, arrayListFiniteElements, listOfNodes, factor);

        repaint();
    }

    /**
     * Método para obter os elementos finitos e desenhar as tensões principais
     *
     * @param arrayListFiniteElements
     */
    public void drawPrincipalStresses(ArrayList<FiniteElement> arrayListFiniteElements) {
        ArrayList<double[][]> listOfStresses = new ArrayList();

        for (FiniteElement finiteElement : arrayListFiniteElements) {
            int nodes = finiteElement.getNodes();
            String type, theory, shape;

            type = finiteElement.getType();
            theory = finiteElement.getTheory();
            shape = finiteElement.getShape();

            if ("2D".equals(type)) {
                double[][] shapeCoordinates = finiteElement.getShapeCoordinates();
                double[][] stressesAndDirections = finiteElement.getStressesAndDirections();
                double[][] stressesCoordinates = finiteElement.getStressesCoordinates();

                //Cálculo das coordenadas dos pontos das tensões principais
                int[][] pointsCoordinates = coordinatesOfStresses(shape, shapeCoordinates, stressesCoordinates, nodes);

                //Construção da lista com as tensões e direcções principais
                principalStresses.add(new PrincipalStresses(pointsCoordinates, stressesAndDirections));
                listOfStresses.add(stressesAndDirections);

                //Desenho da malha de elementos finitos da estrutura indeformada
                double[][] coordinatesOfNodes = finiteElement.getNodesCoordinates();
                int[][] nodalCoordinates = convertCoordinates(coordinatesOfNodes, factor);

                //Instrunções para reduzir o número de coordenadas
                int[][] nodesCoordinates = reduceNumberCoordinates("Quadrilateral", nodalCoordinates, nodes);

                //Instruções para criar a estrutura inicial indeformada
                createUndeformedStructure(type, "Perspective", shape, nodesCoordinates);
            }
        }

        //Identificação do valor máximo da tensão para escalonamento
        maximumStress = maximumStress(listOfStresses);

        repaint();
    }

    /**
     * Método para desenhar os elementos finitos da estrutura indeformada
     *
     * @param type
     * @param nodesCoordinates
     */
    private void nodesUndeformedStructure(String type, int[][] nodesCoordinates) {
        for (int[] nodeCoordinates : nodesCoordinates) {
            if ("Grids".equals(type) || "Slabs".equals(type)) {
                int xPoint = nodeCoordinates[0] + (int) Math.round(Math.cos(angle) * nodeCoordinates[1]);
                int yPoint = (int) Math.round(Math.sin(angle) * nodeCoordinates[1]);

                listNodesUndeformed.add(new DrawNode(xPoint, yPoint));
            } else {
                listNodesUndeformed.add(new DrawNode(nodeCoordinates[0], nodeCoordinates[1]));
            }
        }
    }

    /**
     * Método para desenhar a numeração dos elementos finitos
     *
     * @param shape
     * @param listOfNodes
     */
    private void drawNumberingOfNodes(Graphics2D shape, ArrayList<DrawNode> listOfNodes) {
        ArrayList<int[][]> nodesCoordinates = new ArrayList();

        int node = 1;
        for (DrawNode numbering : listOfNodes) {
            String number = String.valueOf(node);
            boolean drawNumber = true;
            int x = numbering.point.x;
            int y = numbering.point.y;

            for (int[][] coordinates : nodesCoordinates) {
                if (x == coordinates[0][0] && y == coordinates[0][1]) {
                    drawNumber = false;
                    break;
                }
            }

            if (drawNumber) {
                FontMetrics fontSize = shape.getFontMetrics();
                Rectangle2D rectangle = fontSize.getStringBounds(number, shape);
                shape.drawString(number, (int) round(x - (rectangle.getWidth() / 2)), y - 4);
                nodesCoordinates.add(new int[][] { { x, y } });
                node++;
            }
        }
    }

    /**
     * Método para desenhar os elementos finitos da estrutura deformada
     *
     * @param type
     * @param theory
     * @param nodesCoordinates
     * @param displacements
     */
    private void nodesDeformedStructure(String type, String theory, int[][] nodesCoordinates, double[][] displacements) {
        int[][] coordinates = new int[nodesCoordinates.length][2];
        int nodes = nodesCoordinates.length;

        switch (type) {
            case "1D":
                for (int i = 0; i < nodes; i++) {
                    coordinates[i][0] = nodesCoordinates[i][0] + (int) Math.round(displacements[i * 2][0] * deformedFactor);
                    coordinates[i][1] = nodesCoordinates[i][1] + (int) Math.round(displacements[i * 2 + 1][0] * deformedFactor);
                }
                break;
            case "2D":
                for (int i = 0; i < nodes; i++) {
                    coordinates[i][0] = nodesCoordinates[i][0] + (int) Math.round(displacements[i * 2][0] * deformedFactor);
                    coordinates[i][1] = nodesCoordinates[i][1] + (int) Math.round(displacements[i * 2 + 1][0] * deformedFactor);
                }
                break;
            case "3D":
                //TODO add your handling code here
                break;
            case "Beams":
                for (int i = 0; i < nodes; i++) {
                    coordinates[i][0] = nodesCoordinates[i][0];
                    coordinates[i][1] = nodesCoordinates[i][1] + (int) Math.round(displacements[i * 2][0] * deformedFactor);
                }
                break;
            case "Frames":
                for (int i = 0; i < nodes; i++) {
                    coordinates[i][0] = nodesCoordinates[i][0] + (int) Math.round(displacements[i * 3][0] * deformedFactor);
                    coordinates[i][1] = nodesCoordinates[i][1] + (int) Math.round(displacements[i * 3 + 1][0] * deformedFactor);
                }
                break;
            case "Grids":
                int[][] perspectiveGrids = createPerspective(nodesCoordinates);
                int[][] gridsCoordinates = new int[nodes][2];

                for (int i = 0; i < nodes; i++) {
                    gridsCoordinates[i][0] = perspectiveGrids[i][0];
                    gridsCoordinates[i][1] = perspectiveGrids[i][1] + (int) Math.round(displacements[i * 3][0] * deformedFactor);
                }

                coordinates = gridsCoordinates;
                break;
            case "Slabs":
                int dof = 3;
                if ("Kirchhoff".equals(theory)) {
                    dof = 4;
                }
                int[][] perspectiveSlabs = createPerspective(nodesCoordinates);
                int[][] slabsCoordinates = new int[nodes][2];

                for (int i = 0; i < nodes; i++) {
                    slabsCoordinates[i][0] = perspectiveSlabs[i][0];
                    slabsCoordinates[i][1] = perspectiveSlabs[i][1] + (int) Math.round(displacements[i * dof][0] * deformedFactor);
                }

                coordinates = slabsCoordinates;
                break;
        }

        for (int[] nodeCoordinates : coordinates) {
            listNodesDeformed.add(new DrawNode(nodeCoordinates[0], nodeCoordinates[1], defaultColor));
        }
    }

    /**
     * Método para criar os objetos da estrutura indeformada
     *
     * @param type
     * @param theory
     * @param shape
     * @param shapeCoordinates
     * @param nodes
     */
    private void createUndeformedStructure(String type, String other, String shape, int[][] nodesCoordinates) {
        switch (shape) {
            case "Line":
                createLine(type, nodesCoordinates);
                break;
            case "Triangle":
                createPolygon(type, other, nodesCoordinates);
                break;
            case "Rectangle":
                createPolygon(type, other, nodesCoordinates);
                break;
            case "Quadrilateral":
                createPolygon(type, other, nodesCoordinates);
                break;
        }
    }

    /**
     * Método para criar os objetos da estrutura deformada
     *
     * @param type
     * @param theory
     * @param shape
     * @param shapeCoordinates
     * @param nodes
     * @param displacements
     */
    private void createDeformedStructure(
        String type,
        String theory,
        String shape,
        int[][] nodesCoordinates,
        int nodes,
        double[][] displacements
    ) {
        switch (shape) {
            case "Line":
                createDeformedLine(type, nodesCoordinates, displacements, nodes);
                break;
            case "Triangle":
                createDeformedPolygon(type, theory, nodesCoordinates, displacements, nodes);
                break;
            case "Rectangle":
                createDeformedPolygon(type, theory, nodesCoordinates, displacements, nodes);
                break;
            case "Quadrilateral":
                createDeformedPolygon(type, theory, nodesCoordinates, displacements, nodes);
                break;
        }
    }

    /**
     * Método para converter o tipo numérico das coordenadas
     *
     * @param coordinates
     * @return
     */
    private static int[][] convertCoordinates(double[][] coordinates, int factor) {
        int lines = coordinates.length;
        int columns = coordinates[0].length;

        int[][] nodesCoordinates = new int[lines][columns];

        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                nodesCoordinates[i][j] = (int) Math.round(coordinates[i][j] * factor);
            }
        }

        return nodesCoordinates;
    }

    /**
     * Método para eliminar as coordenadas em excesso do polígono
     *
     * @param shape
     * @param coordinates
     * @param nodes
     * @return
     */
    private static int[][] reduceNumberCoordinates(String shape, int[][] coordinates, int nodes) {
        int[][] shapecoordinates;

        if ("Rectangle".equals(shape) || "Quadrilateral".equals(shape)) {
            if (nodes == 9) {
                shapecoordinates = new int[8][2];
                for (int i = 0; i < 8; i++) {
                    shapecoordinates[i][0] = coordinates[i][0];
                    shapecoordinates[i][1] = coordinates[i][1];
                }
            } else {
                shapecoordinates = coordinates;
            }
        } else {
            shapecoordinates = coordinates;
        }

        return shapecoordinates;
    }

    /**
     * Método para eliminar as coordenadas em excesso do polígono
     *
     * @param type
     * @param theory
     * @param shape
     * @param displacements
     * @param nodes
     * @return
     */
    private static double[][] reduceNumberDisplacements(String type, String theory, String shape, double[][] displacements, int nodes) {
        double[][] nodalDisplacements;

        if ("Rectangle".equals(shape) || "Quadrilateral".equals(shape)) {
            if (nodes == 9) {
                switch (type) {
                    case "2D":
                        nodalDisplacements = new double[16][1];
                        for (int i = 0; i < 16; i++) {
                            nodalDisplacements[i][0] = displacements[i][0];
                        }
                        break;
                    case "Slabs":
                        if ("Kirchhoff".equals(theory)) {
                            nodalDisplacements = new double[32][1];
                            for (int i = 0; i < 32; i++) {
                                nodalDisplacements[i][0] = displacements[i][0];
                            }
                        } else {
                            nodalDisplacements = new double[24][1];
                            for (int i = 0; i < 24; i++) {
                                nodalDisplacements[i][0] = displacements[i][0];
                            }
                        }
                        break;
                    default:
                        nodalDisplacements = displacements;
                        break;
                }
            } else {
                nodalDisplacements = displacements;
            }
        } else {
            nodalDisplacements = displacements;
        }

        return nodalDisplacements;
    }

    /**
     * Método para reduzir o número de nós em excesso do polígono
     *
     * @param coordinates
     * @param nodes
     * @return
     */
    private static int reduceNumberNodes(String shape, int nodes) {
        int nNodes;

        if ("Rectangle".equals(shape) || "Quadrilateral".equals(shape)) {
            if (nodes == 9) {
                nNodes = 8;
            } else {
                nNodes = nodes;
            }
        } else {
            nNodes = nodes;
        }

        return nNodes;
    }

    /**
     * Método para adicionar uma linha ao arrayListLines
     *
     * @param type
     * @param coordinates
     */
    private void createLine(String type, int[][] coordinates) {
        switch (type) {
            case "1D":
                createLine(coordinates);
                break;
            case "Beams":
                createLine(coordinates);
                break;
            case "Frames":
                createLine(coordinates);
                break;
            case "Grids":
                createLinePerspective(coordinates);
                break;
        }
    }

    /**
     * Método para adicionar um polígono ao arrayListPolygons
     *
     * @param type
     * @param coordinates
     */
    private void createPolygon(String type, String other, int[][] coordinates) {
        switch (type) {
            case "2D":
                createPolygon(coordinates);
                break;
            case "Slabs":
                if ("Perspective".equals(other)) {
                    createPolygonPerspective(coordinates);
                } else {
                    createPolygon(coordinates);
                }
                break;
        }
    }

    /**
     * Método para adicionar uma linha ao arrayListLines
     *
     * @param type
     * @param coordinates
     * @param displacements
     * @param nodes
     */
    private void createDeformedLine(String type, int[][] coordinates, double[][] displacements, int nodes) {
        switch (type) {
            case "1D":
                createDeformedLine_1D(coordinates, displacements, nodes);
                break;
            case "Beams":
                createDeformedLine_Beams(coordinates, displacements, nodes);
                break;
            case "Frames":
                createDeformedLine_Frames(coordinates, displacements, nodes);
                break;
            case "Grids":
                createDeformedLine_Grids(coordinates, displacements, nodes);
                break;
        }
    }

    /**
     * Método para adicionar um polígono ao arrayListPolygons
     *
     * @param type
     * @param theory
     * @param coordinates
     * @param displacements
     * @param nodes
     */
    private void createDeformedPolygon(String type, String theory, int[][] coordinates, double[][] displacements, int nodes) {
        switch (type) {
            case "2D":
                createDeformedPolygon_2D(coordinates, displacements, nodes);
                break;
            case "Slabs":
                createDeformedPolygon_Slabs(theory, coordinates, displacements, nodes);
                break;
        }
    }

    /**
     * Método para adicionar uma linha ao arrayListLines
     *
     * @param coordinates
     */
    private void createLine(int[][] coordinates) {
        int length = coordinates.length;
        int x1, y1, x2, y2;

        for (int i = 0; i < length - 1; i++) {
            x1 = coordinates[i][0];
            y1 = coordinates[i][1];
            x2 = coordinates[i + 1][0];
            y2 = coordinates[i + 1][1];

            arrayListLines.add(new DrawLine(x1, y1, x2, y2));
        }
    }

    /**
     * Método para adicionar uma linha ao arrayListLines
     *
     * @param coordinates
     */
    private void createLinePerspective(int[][] coordinates) {
        int[][] perspectiveCoordinates = createPerspective(coordinates);
        int length = perspectiveCoordinates.length;
        int x1, y1, x2, y2;

        for (int i = 0; i < length - 1; i++) {
            x1 = perspectiveCoordinates[i][0];
            y1 = perspectiveCoordinates[i][1];
            x2 = perspectiveCoordinates[i + 1][0];
            y2 = perspectiveCoordinates[i + 1][1];

            arrayListLines.add(new DrawLine(x1, y1, x2, y2));
        }
    }

    /**
     * Método para adicionar um polígono ao arrayListPolygons
     *
     * @param coordinates
     */
    private void createPolygon(int[][] coordinates) {
        int length = coordinates.length;

        int[] xPoints = new int[length];
        int[] yPoints = new int[length];
        for (int i = 0; i < length; i++) {
            xPoints[i] = coordinates[i][0];
            yPoints[i] = coordinates[i][1];
        }

        arrayListPolygons.add(new DrawPolygon(xPoints, yPoints, length));
    }

    /**
     * Método para adicionar um polígono ao arrayListPolygons
     *
     * @param coordinates
     */
    private void createPolygonPerspective(int[][] coordinates) {
        int[][] perspectiveCoordinates = createPerspective(coordinates);
        int length = coordinates.length;

        int[] xPoints = new int[length];
        int[] yPoints = new int[length];
        for (int i = 0; i < length; i++) {
            xPoints[i] = perspectiveCoordinates[i][0];
            yPoints[i] = perspectiveCoordinates[i][1];
        }

        arrayListPolygons.add(new DrawPolygon(xPoints, yPoints, length));
    }

    /**
     * Método para adicionar uma linha ao linesDisplacements
     *
     * @param coordinates
     * @param displacements
     * @param nodes
     */
    private void createDeformedLine_1D(int[][] nodesCoordinates, double[][] displacements, int nodes) {
        int length = nodesCoordinates.length;
        int x1, y1, x2, y2;

        int[][] coordinates = new int[length][2];
        for (int i = 0; i < nodes; i++) {
            coordinates[i][0] = nodesCoordinates[i][0] + (int) Math.round(displacements[i * 2][0] * deformedFactor);
            coordinates[i][1] = nodesCoordinates[i][1] + (int) Math.round(displacements[i * 2 + 1][0] * deformedFactor);
        }

        for (int i = 0; i < length - 1; i++) {
            x1 = coordinates[i][0];
            y1 = coordinates[i][1];
            x2 = coordinates[i + 1][0];
            y2 = coordinates[i + 1][1];

            linesDisplacements.add(new DrawLine(x1, y1, x2, y2, defaultColor));
        }
    }

    /**
     * Método para adicionar uma linha ao linesDisplacements
     *
     * @param coordinates
     * @param displacements
     * @param nodes
     */
    private void createDeformedLine_Beams(int[][] nodesCoordinates, double[][] displacements, int nodes) {
        int length = nodesCoordinates.length;
        int x1, y1, x2, y2;

        int[][] coordinates = new int[length][2];
        for (int i = 0; i < nodes; i++) {
            coordinates[i][0] = nodesCoordinates[i][0];
            coordinates[i][1] = nodesCoordinates[i][1] + (int) Math.round(displacements[i * 2][0] * deformedFactor);
        }

        for (int i = 0; i < length - 1; i++) {
            x1 = coordinates[i][0];
            y1 = coordinates[i][1];
            x2 = coordinates[i + 1][0];
            y2 = coordinates[i + 1][1];

            linesDisplacements.add(new DrawLine(x1, y1, x2, y2, defaultColor));
        }
    }

    /**
     * Método para adicionar uma linha ao linesDisplacements
     *
     * @param coordinates
     * @param displacements
     * @param nodes
     */
    private void createDeformedLine_Frames(int[][] nodesCoordinates, double[][] displacements, int nodes) {
        int length = nodesCoordinates.length;
        int x1, y1, x2, y2;

        int[][] coordinates = new int[length][2];
        for (int i = 0; i < nodes; i++) {
            coordinates[i][0] = nodesCoordinates[i][0] + (int) Math.round(displacements[i * 3][0] * deformedFactor);
            coordinates[i][1] = nodesCoordinates[i][1] + (int) Math.round(displacements[i * 3 + 1][0] * deformedFactor);
        }

        for (int i = 0; i < length - 1; i++) {
            x1 = coordinates[i][0];
            y1 = coordinates[i][1];
            x2 = coordinates[i + 1][0];
            y2 = coordinates[i + 1][1];

            linesDisplacements.add(new DrawLine(x1, y1, x2, y2, defaultColor));
        }
    }

    /**
     * Método para adicionar uma linha ao linesDisplacements
     *
     * @param coordinates
     * @param displacements
     * @param nodes
     */
    private void createDeformedLine_Grids(int[][] nodesCoordinates, double[][] displacements, int nodes) {
        int[][] perspectiveCoordinates = createPerspective(nodesCoordinates);
        int length = perspectiveCoordinates.length;
        int x1, y1, x2, y2;

        int[][] coordinates = new int[length][2];
        for (int i = 0; i < nodes; i++) {
            coordinates[i][0] = perspectiveCoordinates[i][0];
            coordinates[i][1] = perspectiveCoordinates[i][1] + (int) Math.round(displacements[i * 3][0] * deformedFactor);
        }

        for (int i = 0; i < length - 1; i++) {
            x1 = coordinates[i][0];
            y1 = coordinates[i][1];
            x2 = coordinates[i + 1][0];
            y2 = coordinates[i + 1][1];

            linesDisplacements.add(new DrawLine(x1, y1, x2, y2, defaultColor));
        }
    }

    /**
     * Método para adicionar um polígono ao polygonsDisplacements
     *
     * @param coordinates
     * @param displacements
     * @param nodes
     */
    private void createDeformedPolygon_2D(int[][] coordinates, double[][] displacements, int nodes) {
        int length = coordinates.length;
        int[][] shapeCoordinates = new int[nodes][2];

        for (int i = 0; i < nodes; i++) {
            shapeCoordinates[i][0] = coordinates[i][0] + (int) Math.round(displacements[i * 2][0] * deformedFactor);
            shapeCoordinates[i][1] = coordinates[i][1] + (int) Math.round(displacements[i * 2 + 1][0] * deformedFactor);
        }

        int[] xPoints = new int[length];
        int[] yPoints = new int[length];
        for (int i = 0; i < length; i++) {
            xPoints[i] = shapeCoordinates[i][0];
            yPoints[i] = shapeCoordinates[i][1];
        }

        polygonsDisplacements.add(new DrawPolygon(xPoints, yPoints, length, defaultColor));
    }

    /**
     * Método para adicionar um polígono em perspectiva ao polygonsDisplacements
     *
     * @param theory
     * @param coordinates
     * @param displacements
     * @param nodes
     */
    private void createDeformedPolygon_Slabs(String theory, int[][] coordinates, double[][] displacements, int nodes) {
        int[][] perspectiveCoordinates = createPerspective(coordinates);
        int length = coordinates.length;
        int[][] shapeCoordinates = new int[nodes][2];

        int dof = 3;
        if ("Kirchhoff".equals(theory)) {
            dof = 4;
        }

        for (int i = 0; i < nodes; i++) {
            shapeCoordinates[i][0] = perspectiveCoordinates[i][0];
            shapeCoordinates[i][1] = perspectiveCoordinates[i][1] + (int) Math.round(displacements[i * dof][0] * deformedFactor);
        }

        int[] xPoints = new int[length];
        int[] yPoints = new int[length];
        for (int i = 0; i < length; i++) {
            xPoints[i] = shapeCoordinates[i][0];
            yPoints[i] = shapeCoordinates[i][1];
        }

        polygonsDisplacements.add(new DrawPolygon(xPoints, yPoints, length, defaultColor));
    }

    /**
     * Método para colocar as coordenadas das figuras em perspectiva
     *
     * @param coordinates
     */
    private int[][] createPerspective(int[][] coordinates) {
        int length = coordinates.length;
        int[][] perspectiveCoordinates = new int[length][2];

        for (int i = 0; i < length; i++) {
            perspectiveCoordinates[i][0] = coordinates[i][0] + (int) Math.round(Math.cos(angle) * coordinates[i][1]);
            perspectiveCoordinates[i][1] = (int) Math.round(Math.sin(angle) * coordinates[i][1]);
        }

        return perspectiveCoordinates;
    }

    /**
     * Método para calcular a posição dos pontos das tensões principais
     *
     * @param shape
     * @param shapeCoordinates
     * @param stressesCoordinates
     * @param nodes
     * @return
     */
    private int[][] coordinatesOfStresses(String shape, double[][] shapeCoordinates, double[][] stressesCoordinates, int nodes) {
        Point2D.Double centroid = AnalyticGeometry.centroid(shapeCoordinates, shape);

        double[][] coordinatesOfStresses;
        if (nodes == 3 || nodes == 6) {
            coordinatesOfStresses = new double[3][2];
        } else {
            coordinatesOfStresses = new double[4][2];
        }

        //Cálculo da posição dos pontos de cálculo das tensões
        for (int i = 0; i < coordinatesOfStresses.length; i++) {
            coordinatesOfStresses[i][0] = stressesCoordinates[i][0] + centroid.x;
            coordinatesOfStresses[i][1] = stressesCoordinates[i][1] + centroid.y;
        }

        //Conversão do valor das coordenadas para o tipo inteiro
        int[][] coordinates = convertCoordinates(coordinatesOfStresses, factor);

        return coordinates;
    }

    /**
     * Método para calcular o valor da tensão máxima instalada
     *
     * @param listOfStresses
     * @return
     */
    private double maximumStress(ArrayList<double[][]> listOfStresses) {
        double maximum = 0.0;

        for (double[][] stresses : listOfStresses) {
            for (double[] sigma : stresses) {
                if (sigma[0] >= 0.0) {
                    if (sigma[0] > maximum) {
                        maximum = sigma[0];
                    }
                }
                if (sigma[0] < 0.0) {
                    if ((sigma[0] * -1.0) > maximum) {
                        maximum = sigma[0] * -1.0;
                    }
                }
                if (sigma[1] >= 0.0) {
                    if (sigma[1] > maximum) {
                        maximum = sigma[1];
                    }
                }
                if (sigma[1] < 0.0) {
                    if ((sigma[1] * -1.0) > maximum) {
                        maximum = sigma[1] * -1.0;
                    }
                }
            }
        }

        //Instrução para corrigir eventuais erros de arredondamentos
        if (maximum < 0.001) {
            maximum = 0.0;
        }

        return maximum;
    }
}

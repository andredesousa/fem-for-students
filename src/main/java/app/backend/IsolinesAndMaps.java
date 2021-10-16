/*
 * Esta classe desenha as linhas de isovalores e os mapas de tensões
 * Esta classe para desenhar requer a lista de elementos finitos
 * A classe possui dois método públicos para escolher o tipo de tarefa
 */

package app.backend;

import app.calculations.AnalyticGeometry;
import app.calculations.FiniteElement;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 *
 * @author André de Sousa
 */
public class IsolinesAndMaps {

    private final int factor;
    private final String type;
    private final String stressName;
    private final String planeOfSlab;
    private ArrayList<NodalStresses> arrayListNodalStresses;

    /**
     * Método construtor da classe IsolinesAndMaps
     *
     * @param type
     * @param stressName
     * @param planeOfSlab
     * @param arrayListFiniteElements
     * @param listOfNodes
     * @param factor
     */
    public IsolinesAndMaps(
        String type,
        String stressName,
        String planeOfSlab,
        ArrayList<FiniteElement> arrayListFiniteElements,
        ArrayList<double[]> listOfNodes,
        int factor
    ) {
        this.type = type;
        this.factor = factor;
        this.stressName = stressName;
        this.planeOfSlab = planeOfSlab;
        this.arrayListNodalStresses = new ArrayList();
        createListOfTriangles(arrayListFiniteElements, listOfNodes);
    }

    /**
     * Método para desenhar as linhas de isovalores
     *
     * @param graphics
     */
    public void drawIsolines(Graphics2D graphics) {
        ArrayList<DrawLine> arrayListLines;

        //Identificação dos limites das tensões para atribuição das cores (0; máximo)
        double limitOfStresses = limitOfStresses(stressName, arrayListNodalStresses);

        //Construção da lista com as linhas de isovalores
        arrayListLines = isolines(arrayListNodalStresses, limitOfStresses);

        for (DrawLine line : arrayListLines) {
            line.draw(graphics);
        }
    }

    /**
     * Método para desenhar o mapa de tensões
     *
     * @param graphics
     */
    public void drawMaps(Graphics2D graphics) {
        ArrayList<DrawPolygon> arrayListPolygons;

        //Identificação dos limites das tensões para atribuição das cores (0; máximo)
        double limitOfStresses = limitOfStresses(stressName, arrayListNodalStresses);

        //Construção da lista com as linhas de isovalores
        arrayListPolygons = maps(arrayListNodalStresses, limitOfStresses);

        for (DrawPolygon polygon : arrayListPolygons) {
            polygon.fill(graphics);
        }
    }

    /**
     * Método para criar a lista de linhas representativas das linhas de isovalores
     *
     * @param arrayListFiniteElements
     * @param listOfNodes
     */
    private void createListOfTriangles(ArrayList<FiniteElement> arrayListFiniteElements, ArrayList<double[]> listOfNodes) {
        ArrayList<NodalStresses> listOfNodalStresses = new ArrayList();

        //Construção da lista de objetos para criar a malha de triângulos
        for (FiniteElement finiteElement : arrayListFiniteElements) {
            int nodes = finiteElement.getNodes();
            ArrayList<double[][]> stresses = finiteElement.getNodalStresses();

            String shape = finiteElement.getShape();
            double[][] nodalStresses = nodalStresses(stresses, nodes);
            double[][] shapeCoordinates = finiteElement.getShapeCoordinates();

            listOfNodalStresses.add(new NodalStresses(shape, shapeCoordinates, nodalStresses));
        }

        //Suavização do valor das tensões nodais
        listOfNodalStresses = smoothing(listOfNodalStresses, listOfNodes);

        //Construção dos objetos triangulares com as tensões nodais
        arrayListNodalStresses = createStressesTriangle(listOfNodalStresses);
    }

    /**
     * Método para definir a matriz das coordenadas das tensões principais
     *
     * @param type
     * @param nodalStresses
     * @param nodes
     * @return
     */
    private double[][] nodalStresses(ArrayList<double[][]> nodalStresses, int nodes) {
        double[][] stresses;

        if (nodes == 3 || nodes == 6) {
            stresses = new double[3][3];
        } else {
            stresses = new double[4][3];
        }

        int i = 0;
        if ("Slabs".equals(type) && "Bottom".equals(planeOfSlab)) {
            for (double[][] nodalStress : nodalStresses) {
                stresses[i][0] = nodalStress[3][0];
                stresses[i][1] = nodalStress[4][0];
                stresses[i][2] = nodalStress[5][0];
                i++;
            }
        } else {
            for (double[][] nodalStress : nodalStresses) {
                stresses[i][0] = nodalStress[0][0];
                stresses[i][1] = nodalStress[1][0];
                stresses[i][2] = nodalStress[2][0];
                i++;
            }
        }

        return stresses;
    }

    /**
     * Método para calcular o valor máximo absoluto das tensões
     *
     * @param type
     * @param stressName
     * @param planeOfSlab
     * @param listOfNodalStresses
     * @return
     */
    private static double limitOfStresses(String stressName, ArrayList<NodalStresses> listOfNodalStresses) {
        double maximum = 0;

        for (NodalStresses nodalStresses : listOfNodalStresses) {
            double[][] stresses = nodalStresses.nodalStresses;

            for (double[] stress : stresses) {
                int i;
                switch (stressName) {
                    case "σx":
                        i = 0;
                        break;
                    case "σy":
                        i = 1;
                        break;
                    default:
                        i = 2;
                        break;
                }

                if (stress[i] >= 0) {
                    if (stress[i] > maximum) {
                        maximum = stress[i];
                    }
                }
                if (stress[i] < 0) {
                    if ((stress[i] * -1) > maximum) {
                        maximum = stress[i] * -1;
                    }
                }
            }
        }

        //Instrução para corrigir eventuais erros de arredondamentos
        if (maximum == 0.0 || maximum < 0.001) {
            maximum = 0.001;
        } else {
            maximum = maximum + 0.001;
        }

        return maximum;
    }

    /**
     * Método para suavizar os valores nodais das tensões
     *
     * @param listOfNodalStresses
     * @param listOfNodes
     * @return
     */
    private ArrayList<NodalStresses> smoothing(ArrayList<NodalStresses> listOfNodalStresses, ArrayList<double[]> listOfNodes) {
        for (double[] nodesCoordinates : listOfNodes) {
            double x = nodesCoordinates[0];
            double y = nodesCoordinates[1];

            double nStresses = 0.0;
            double[][] stressesVector = new double[1][3];

            for (NodalStresses listOfStress : listOfNodalStresses) {
                double[][] shapeCoordinates = listOfStress.shapeCoordinates;
                double[][] nodalStresses = listOfStress.nodalStresses;

                //Adição dos valores à matriz stressesVector
                for (int i = 0; i < shapeCoordinates.length; i++) {
                    if (shapeCoordinates[i][0] == x && shapeCoordinates[i][1] == y) {
                        stressesVector[0][0] = stressesVector[0][0] + nodalStresses[i][0];
                        stressesVector[0][1] = stressesVector[0][1] + nodalStresses[i][1];
                        stressesVector[0][2] = stressesVector[0][2] + nodalStresses[i][2];
                        nStresses = nStresses + 1.0;
                    }
                }
            }

            //Cálculo das tensões médias
            double σx = stressesVector[0][0] / nStresses;
            double σy = stressesVector[0][1] / nStresses;
            double τxy = stressesVector[0][2] / nStresses;

            for (NodalStresses listOfStress : listOfNodalStresses) {
                double[][] shapeCoordinates = listOfStress.shapeCoordinates;

                for (int i = 0; i < shapeCoordinates.length; i++) {
                    if (shapeCoordinates[i][0] == x && shapeCoordinates[i][1] == y) {
                        listOfStress.nodalStresses[i][0] = σx;
                        listOfStress.nodalStresses[i][1] = σy;
                        listOfStress.nodalStresses[i][2] = τxy;
                    }
                }
            }
        }

        return listOfNodalStresses;
    }

    /**
     * Método para criar os objetos triangulares com as tensões nodais
     *
     * @param nodalStresses
     * @return
     */
    private ArrayList<NodalStresses> createStressesTriangle(ArrayList<NodalStresses> nodalStresses) {
        ArrayList<NodalStresses> triangleStresses = new ArrayList();

        for (NodalStresses nodalStress : nodalStresses) {
            String shape = nodalStress.shape;

            if ("Triangle".equals(shape)) {
                double[][] stresses = nodalStress.nodalStresses;
                double[][] coordinates = nodalStress.shapeCoordinates;

                //Criação dos triângulos de tensões nodais
                triangleStresses.add(new NodalStresses("Triangle", coordinates, stresses));
            } else {
                double[][] stresses = nodalStress.nodalStresses;
                double[][] coordinates = nodalStress.shapeCoordinates;

                //Cálculo da tensão do ponto central da figura geométrica
                double[][] centralStress = centralStress(stresses);

                //Calculo das coordenadas do ponto central da figura geométrica
                Point2D.Double centroid = AnalyticGeometry.centroid(coordinates, shape);

                //Construção das matrizes com as coordenadas e tensões nodais
                double[][] shapeCoordinatesA = {
                    { coordinates[0][0], coordinates[0][1] },
                    { coordinates[1][0], coordinates[1][1] },
                    { centroid.x, centroid.y },
                };
                double[][] shapeCoordinatesB = {
                    { coordinates[1][0], coordinates[1][1] },
                    { coordinates[2][0], coordinates[2][1] },
                    { centroid.x, centroid.y },
                };
                double[][] shapeCoordinatesC = {
                    { coordinates[2][0], coordinates[2][1] },
                    { coordinates[3][0], coordinates[3][1] },
                    { centroid.x, centroid.y },
                };
                double[][] shapeCoordinatesD = {
                    { coordinates[3][0], coordinates[3][1] },
                    { coordinates[0][0], coordinates[0][1] },
                    { centroid.x, centroid.y },
                };

                double[][] nodalStressesA = {
                    { stresses[0][0], stresses[0][1], stresses[0][2] },
                    { stresses[1][0], stresses[1][1], stresses[1][2] },
                    { centralStress[0][0], centralStress[0][1], centralStress[0][2] },
                };
                double[][] nodalStressesB = {
                    { stresses[1][0], stresses[1][1], stresses[1][2] },
                    { stresses[2][0], stresses[2][1], stresses[2][2] },
                    { centralStress[0][0], centralStress[0][1], centralStress[0][2] },
                };
                double[][] nodalStressesC = {
                    { stresses[2][0], stresses[2][1], stresses[2][2] },
                    { stresses[3][0], stresses[3][1], stresses[3][2] },
                    { centralStress[0][0], centralStress[0][1], centralStress[0][2] },
                };
                double[][] nodalStressesD = {
                    { stresses[3][0], stresses[3][1], stresses[3][2] },
                    { stresses[0][0], stresses[0][1], stresses[0][2] },
                    { centralStress[0][0], centralStress[0][1], centralStress[0][2] },
                };

                //Criação dos triângulos de tensões nodais
                triangleStresses.add(new NodalStresses("Triangle", shapeCoordinatesA, nodalStressesA));
                triangleStresses.add(new NodalStresses("Triangle", shapeCoordinatesB, nodalStressesB));
                triangleStresses.add(new NodalStresses("Triangle", shapeCoordinatesC, nodalStressesC));
                triangleStresses.add(new NodalStresses("Triangle", shapeCoordinatesD, nodalStressesD));
            }
        }

        return triangleStresses;
    }

    /**
     * Método para calcular as tensões no ponto central do quadrilátero
     *
     * @param stresses
     * @return
     */
    private static double[][] centralStress(double[][] stresses) {
        double[][] centralStress = new double[1][3];

        double σx = (stresses[0][0] + stresses[1][0] + stresses[2][0] + stresses[3][0]) / 4;
        double σy = (stresses[0][1] + stresses[1][1] + stresses[2][1] + stresses[3][1]) / 4;
        double τxy = (stresses[0][2] + stresses[1][2] + stresses[2][2] + stresses[3][2]) / 4;

        centralStress[0][0] = σx;
        centralStress[0][1] = σy;
        centralStress[0][2] = τxy;

        return centralStress;
    }

    /**
     * Método para criar uma lista com as linhas de isovalores
     *
     * @param listOfNodalStresses
     * @return
     */
    private ArrayList<DrawLine> isolines(ArrayList<NodalStresses> listOfNodalStresses, double limitOfStresses) {
        ArrayList<DrawLine> listOfLines = new ArrayList();
        int index = getIndex();

        //Criação da lista com as tensões a localizar e as cores a atribuir
        ArrayList<StressesAndColors> colorsOfStresses = colorsOfStresses_Isolines(limitOfStresses);

        //Construção da lista com as linhas de isovalores
        for (NodalStresses listOfStress : listOfNodalStresses) {
            double[][] shapeCoordinates = listOfStress.shapeCoordinates;
            double[][] nodalStresses = listOfStress.nodalStresses;

            //Validação para o caso de triângulo com tensões constantes
            if (validateNodalStresses(nodalStresses, index)) {
                //Ciclo para todas as cores e tensões da lista colorsOfStresses
                for (StressesAndColors stressesAndColors : colorsOfStresses) {
                    Color color = stressesAndColors.color;
                    double stress = stressesAndColors.stress;

                    Point2D.Double pointA = null;
                    Point2D.Double pointB = null;
                    Point2D.Double pointC = null;

                    if (
                        (stress >= nodalStresses[0][index] && stress <= nodalStresses[1][index]) ||
                        (stress >= nodalStresses[1][index] && stress <= nodalStresses[0][index])
                    ) {
                        double x1, y1, z1, x2, y2, z2;
                        x1 = shapeCoordinates[0][0];
                        y1 = shapeCoordinates[0][1];
                        z1 = nodalStresses[0][index];
                        x2 = shapeCoordinates[1][0];
                        y2 = shapeCoordinates[1][1];
                        z2 = nodalStresses[1][index];

                        pointA = intersectionStraightPlan(x1, y1, z1, x2, y2, z2, stress);
                    }

                    if (
                        (stress >= nodalStresses[1][index] && stress <= nodalStresses[2][index]) ||
                        (stress >= nodalStresses[2][index] && stress <= nodalStresses[1][index])
                    ) {
                        double x1, y1, z1, x2, y2, z2;
                        x1 = shapeCoordinates[1][0];
                        y1 = shapeCoordinates[1][1];
                        z1 = nodalStresses[1][index];
                        x2 = shapeCoordinates[2][0];
                        y2 = shapeCoordinates[2][1];
                        z2 = nodalStresses[2][index];

                        pointB = intersectionStraightPlan(x1, y1, z1, x2, y2, z2, stress);
                    }

                    if (
                        (stress >= nodalStresses[2][index] && stress <= nodalStresses[0][index]) ||
                        (stress >= nodalStresses[0][index] && stress <= nodalStresses[2][index])
                    ) {
                        double x1, y1, z1, x2, y2, z2;
                        x1 = shapeCoordinates[2][0];
                        y1 = shapeCoordinates[2][1];
                        z1 = nodalStresses[2][index];
                        x2 = shapeCoordinates[0][0];
                        y2 = shapeCoordinates[0][1];
                        z2 = nodalStresses[0][index];

                        pointC = intersectionStraightPlan(x1, y1, z1, x2, y2, z2, stress);
                    }

                    boolean hypothesis = true;
                    //Caso em que os pontos da linha 0-1 se unem aos pontos da linha 1-2
                    if (hypothesis && pointA != null && pointB != null) {
                        int xPointA, yPointA, xPointB, yPointB;

                        xPointA = (int) Math.round(pointA.x * factor);
                        yPointA = (int) Math.round(pointA.y * factor);
                        xPointB = (int) Math.round(pointB.x * factor);
                        yPointB = (int) Math.round(pointB.y * factor);

                        listOfLines.add(new DrawLine(xPointA, yPointA, xPointB, yPointB, color));
                        hypothesis = false;
                    }

                    //Caso em que os pontos da linha 1-2 se unem aos pontos da linha 2-0
                    if (hypothesis && pointB != null && pointC != null) {
                        int xPointB, yPointB, xPointC, yPointC;

                        xPointB = (int) Math.round(pointB.x * factor);
                        yPointB = (int) Math.round(pointB.y * factor);
                        xPointC = (int) Math.round(pointC.x * factor);
                        yPointC = (int) Math.round(pointC.y * factor);

                        listOfLines.add(new DrawLine(xPointB, yPointB, xPointC, yPointC, color));
                        hypothesis = false;
                    }

                    //Caso em que os pontos da linha 0-1 se unem aos pontos da linha 2-0
                    if (hypothesis && pointA != null && pointC != null) {
                        int xPointA, yPointA, xPointC, yPointC;

                        xPointA = (int) Math.round(pointA.x * factor);
                        yPointA = (int) Math.round(pointA.y * factor);
                        xPointC = (int) Math.round(pointC.x * factor);
                        yPointC = (int) Math.round(pointC.y * factor);

                        listOfLines.add(new DrawLine(xPointA, yPointA, xPointC, yPointC, color));
                    }
                }
            }
        }

        return listOfLines;
    }

    /**
     * Método para criar uma lista com o mapa de tensões
     *
     * @param listOfNodalStresses
     * @return
     */
    private ArrayList<DrawPolygon> maps(ArrayList<NodalStresses> listOfNodalStresses, double limitOfStresses) {
        ArrayList<DrawPolygon> listOfPolygons = new ArrayList();
        int index = getIndex();

        //Criação da lista com as tensões a localizar e as cores a atribuir
        ArrayList<StressesAndColors> colorsOfStresses = colorsOfStresses_Maps(limitOfStresses);

        //Construção da lista com os polígonos dos mapas de tensões
        for (NodalStresses listOfStress : listOfNodalStresses) {
            double[][] shapeCoordinates = listOfStress.shapeCoordinates;
            double[][] nodalStresses = listOfStress.nodalStresses;

            double x1, y1, z1, x2, y2, z2, x3, y3, z3;
            x1 = shapeCoordinates[0][0];
            y1 = shapeCoordinates[0][1];
            z1 = nodalStresses[0][index];
            x2 = shapeCoordinates[1][0];
            y2 = shapeCoordinates[1][1];
            z2 = nodalStresses[1][index];
            x3 = shapeCoordinates[2][0];
            y3 = shapeCoordinates[2][1];
            z3 = nodalStresses[2][index];

            //Ciclo para todas as cores e tensões da lista colorsOfStresses
            for (int j = 0; j < colorsOfStresses.size(); j++) {
                Color color = colorsOfStresses.get(j).color;
                double stressA, stressB;

                stressA = colorsOfStresses.get(j).stress;
                if (j < colorsOfStresses.size() - 1) {
                    stressB = colorsOfStresses.get(j + 1).stress;
                } else {
                    stressB = colorsOfStresses.get(j).stress;
                }

                if (validateTriangle(index, stressA, stressB, nodalStresses)) {
                    ArrayList<Point2D.Double> listOfPoints = new ArrayList();

                    //Caso para os pontos da linha 0-1
                    double inclinationA = z2 - z1;
                    if (inclinationA > 0) {
                        if (stressA >= nodalStresses[0][index] && stressA <= nodalStresses[1][index]) {
                            Point2D.Double point = intersectionStraightPlan(x1, y1, z1, x2, y2, z2, stressA);
                            listOfPoints.add(point);
                        } else {
                            if (validateLine(nodalStresses[0][index], nodalStresses[1][index], stressA, stressB)) {
                                listOfPoints.add(new Point2D.Double(x1, y1));
                            }
                        }

                        if (stressB >= nodalStresses[0][index] && stressB <= nodalStresses[1][index]) {
                            Point2D.Double point = intersectionStraightPlan(x1, y1, z1, x2, y2, z2, stressB);
                            listOfPoints.add(point);
                        } else {
                            if (validateLine(nodalStresses[0][index], nodalStresses[1][index], stressA, stressB)) {
                                listOfPoints.add(new Point2D.Double(x2, y2));
                            }
                        }
                    } else {
                        if (stressB <= nodalStresses[0][index] && stressB >= nodalStresses[1][index]) {
                            Point2D.Double point = intersectionStraightPlan(x1, y1, z1, x2, y2, z2, stressB);
                            listOfPoints.add(point);
                        } else {
                            if (validateLine(nodalStresses[0][index], nodalStresses[1][index], stressA, stressB)) {
                                listOfPoints.add(new Point2D.Double(x1, y1));
                            }
                        }

                        if (stressA <= nodalStresses[0][index] && stressA >= nodalStresses[1][index]) {
                            Point2D.Double point = intersectionStraightPlan(x1, y1, z1, x2, y2, z2, stressA);
                            listOfPoints.add(point);
                        } else {
                            if (validateLine(nodalStresses[0][index], nodalStresses[1][index], stressA, stressB)) {
                                listOfPoints.add(new Point2D.Double(x2, y2));
                            }
                        }
                    }

                    //Caso para os pontos da linha 1-2
                    double inclinationB = z3 - z2;
                    if (inclinationB > 0) {
                        if (stressA >= nodalStresses[1][index] && stressA <= nodalStresses[2][index]) {
                            Point2D.Double point = intersectionStraightPlan(x2, y2, z2, x3, y3, z3, stressA);
                            listOfPoints.add(point);
                        } else {
                            if (validateLine(nodalStresses[1][index], nodalStresses[2][index], stressA, stressB)) {
                                listOfPoints.add(new Point2D.Double(x2, y2));
                            }
                        }

                        if (stressB >= nodalStresses[1][index] && stressB <= nodalStresses[2][index]) {
                            Point2D.Double point = intersectionStraightPlan(x2, y2, z2, x3, y3, z3, stressB);
                            listOfPoints.add(point);
                        } else {
                            if (validateLine(nodalStresses[1][index], nodalStresses[2][index], stressA, stressB)) {
                                listOfPoints.add(new Point2D.Double(x3, y3));
                            }
                        }
                    } else {
                        if (stressB <= nodalStresses[1][index] && stressB >= nodalStresses[2][index]) {
                            Point2D.Double point = intersectionStraightPlan(x2, y2, z2, x3, y3, z3, stressB);
                            listOfPoints.add(point);
                        } else {
                            if (validateLine(nodalStresses[1][index], nodalStresses[2][index], stressA, stressB)) {
                                listOfPoints.add(new Point2D.Double(x2, y2));
                            }
                        }

                        if (stressA <= nodalStresses[1][index] && stressA >= nodalStresses[2][index]) {
                            Point2D.Double point = intersectionStraightPlan(x2, y2, z2, x3, y3, z3, stressA);
                            listOfPoints.add(point);
                        } else {
                            if (validateLine(nodalStresses[1][index], nodalStresses[2][index], stressA, stressB)) {
                                listOfPoints.add(new Point2D.Double(x3, y3));
                            }
                        }
                    }

                    //Caso para os pontos da linha 2-0
                    double inclinationC = z1 - z3;
                    if (inclinationC > 0) {
                        if (stressA >= nodalStresses[2][index] && stressA <= nodalStresses[0][index]) {
                            Point2D.Double point = intersectionStraightPlan(x3, y3, z3, x1, y1, z1, stressA);
                            listOfPoints.add(point);
                        } else {
                            if (validateLine(nodalStresses[2][index], nodalStresses[0][index], stressA, stressB)) {
                                listOfPoints.add(new Point2D.Double(x3, y3));
                            }
                        }

                        if (stressB >= nodalStresses[2][index] && stressB <= nodalStresses[0][index]) {
                            Point2D.Double point = intersectionStraightPlan(x3, y3, z3, x1, y1, z1, stressB);
                            listOfPoints.add(point);
                        } else {
                            if (validateLine(nodalStresses[2][index], nodalStresses[0][index], stressA, stressB)) {
                                listOfPoints.add(new Point2D.Double(x1, y1));
                            }
                        }
                    } else {
                        if (stressB <= nodalStresses[2][index] && stressB >= nodalStresses[0][index]) {
                            Point2D.Double point = intersectionStraightPlan(x3, y3, z3, x1, y1, z1, stressB);
                            listOfPoints.add(point);
                        } else {
                            if (validateLine(nodalStresses[2][index], nodalStresses[0][index], stressA, stressB)) {
                                listOfPoints.add(new Point2D.Double(x3, y3));
                            }
                        }

                        if (stressA <= nodalStresses[2][index] && stressA >= nodalStresses[0][index]) {
                            Point2D.Double point = intersectionStraightPlan(x3, y3, z3, x1, y1, z1, stressA);
                            listOfPoints.add(point);
                        } else {
                            if (validateLine(nodalStresses[2][index], nodalStresses[0][index], stressA, stressB)) {
                                listOfPoints.add(new Point2D.Double(x1, y1));
                            }
                        }
                    }

                    //Criação da lista de polígonos para representar os mapas de tensões
                    int nPoints = listOfPoints.size();
                    if (nPoints > 2) {
                        int[] xPoints = new int[nPoints];
                        int[] yPoints = new int[nPoints];

                        int i = 0;
                        for (Point2D.Double point : listOfPoints) {
                            xPoints[i] = (int) Math.round(point.x * factor);
                            yPoints[i] = (int) Math.round(point.y * factor);
                            i++;
                        }

                        listOfPolygons.add(new DrawPolygon(xPoints, yPoints, nPoints, color));
                    }
                }
            }
        }

        return listOfPolygons;
    }

    /**
     * Método que devolve a lista de tensões e cores a atribuidas
     *
     * @param limitOfStresses
     * @return
     */
    private ArrayList<StressesAndColors> colorsOfStresses_Isolines(double limitOfStresses) {
        ArrayList<StressesAndColors> colorsOfStresses = new ArrayList();
        double interval = limitOfStresses / 7;

        //Declaração das cores a atribuir às compressões
        Color compressionColorA = new Color(35, 30, 190);
        Color compressionColorB = new Color(23, 65, 235);
        Color compressionColorC = new Color(0, 105, 253);
        Color compressionColorD = new Color(0, 176, 219);
        Color compressionColorE = new Color(0, 208, 137);
        Color compressionColorF = new Color(84, 227, 84);
        Color compressionColorH = new Color(172, 247, 76);

        //Adicição à lista das tensões de compressão e respetivas cores
        colorsOfStresses.add(new StressesAndColors(compressionColorA, -6 * interval));
        colorsOfStresses.add(new StressesAndColors(compressionColorB, -5 * interval));
        colorsOfStresses.add(new StressesAndColors(compressionColorC, -4 * interval));
        colorsOfStresses.add(new StressesAndColors(compressionColorD, -3 * interval));
        colorsOfStresses.add(new StressesAndColors(compressionColorE, -2 * interval));
        colorsOfStresses.add(new StressesAndColors(compressionColorF, -1 * interval));
        colorsOfStresses.add(new StressesAndColors(compressionColorH, 0 * interval));

        //Declaração das cores a atribuir às trações
        Color tensionColorA = new Color(191, 30, 45);
        Color tensionColorB = new Color(225, 55, 55);
        Color tensionColorC = new Color(254, 95, 59);
        Color tensionColorD = new Color(255, 164, 58);
        Color tensionColorE = new Color(254, 225, 60);
        Color tensionColorF = new Color(237, 244, 70);

        //Adicição à lista das tensões de tração e respetivas cores
        colorsOfStresses.add(new StressesAndColors(tensionColorF, 1 * interval));
        colorsOfStresses.add(new StressesAndColors(tensionColorE, 2 * interval));
        colorsOfStresses.add(new StressesAndColors(tensionColorD, 3 * interval));
        colorsOfStresses.add(new StressesAndColors(tensionColorC, 4 * interval));
        colorsOfStresses.add(new StressesAndColors(tensionColorB, 5 * interval));
        colorsOfStresses.add(new StressesAndColors(tensionColorA, 6 * interval));

        return colorsOfStresses;
    }

    /**
     * Método que devolve a lista de tensões e cores a atribuidas
     *
     * @param limitOfStresses
     * @return
     */
    private ArrayList<StressesAndColors> colorsOfStresses_Maps(double limitOfStresses) {
        ArrayList<StressesAndColors> colorsOfStresses = new ArrayList();
        double interval = limitOfStresses / 7;

        //Declaração das cores a atribuir às compressões
        Color compressionColorA = new Color(35, 30, 190);
        Color compressionColorB = new Color(23, 65, 235);
        Color compressionColorC = new Color(0, 105, 253);
        Color compressionColorD = new Color(0, 176, 219);
        Color compressionColorE = new Color(0, 208, 137);
        Color compressionColorF = new Color(84, 227, 84);
        Color compressionColorG = new Color(145, 239, 74);

        //Adicição à lista das tensões de compressão e respetivas cores
        colorsOfStresses.add(new StressesAndColors(compressionColorA, -7 * interval));
        colorsOfStresses.add(new StressesAndColors(compressionColorB, -6 * interval));
        colorsOfStresses.add(new StressesAndColors(compressionColorC, -5 * interval));
        colorsOfStresses.add(new StressesAndColors(compressionColorD, -4 * interval));
        colorsOfStresses.add(new StressesAndColors(compressionColorE, -3 * interval));
        colorsOfStresses.add(new StressesAndColors(compressionColorF, -2 * interval));
        colorsOfStresses.add(new StressesAndColors(compressionColorG, -1 * interval));
        colorsOfStresses.add(new StressesAndColors(compressionColorG, 0 * interval));

        //Declaração das cores a atribuir às trações
        Color tensionColorA = new Color(191, 30, 45);
        Color tensionColorB = new Color(225, 55, 55);
        Color tensionColorC = new Color(254, 95, 59);
        Color tensionColorD = new Color(255, 164, 58);
        Color tensionColorE = new Color(254, 225, 60);
        Color tensionColorF = new Color(237, 244, 70);
        Color tensionColorG = new Color(198, 255, 77);

        //Adicição à lista das tensões de tração e respetivas cores
        colorsOfStresses.add(new StressesAndColors(tensionColorG, 0 * interval));
        colorsOfStresses.add(new StressesAndColors(tensionColorF, 1 * interval));
        colorsOfStresses.add(new StressesAndColors(tensionColorE, 2 * interval));
        colorsOfStresses.add(new StressesAndColors(tensionColorD, 3 * interval));
        colorsOfStresses.add(new StressesAndColors(tensionColorC, 4 * interval));
        colorsOfStresses.add(new StressesAndColors(tensionColorB, 5 * interval));
        colorsOfStresses.add(new StressesAndColors(tensionColorA, 6 * interval));
        colorsOfStresses.add(new StressesAndColors(tensionColorA, 7 * interval));

        return colorsOfStresses;
    }

    /**
     * Método para verificar se as tensões não são constantes
     *
     * @param nodalStresses
     * @return
     */
    private boolean validateNodalStresses(double[][] nodalStresses, int index) {
        return nodalStresses[0][index] != nodalStresses[1][index] || nodalStresses[0][index] != nodalStresses[2][index];
    }

    /**
     * Método para calcular a interseção entre uma reta e um plano
     *
     * @param x1
     * @param y1
     * @param z1
     * @param x2
     * @param y2
     * @param z2
     * @param stress
     * @return
     */
    private Point2D.Double intersectionStraightPlan(double x1, double y1, double z1, double x2, double y2, double z2, double stress) {
        if ((z2 - z1) != 0.0) {
            double t = (stress - z1) / (z2 - z1);
            return new Point2D.Double(x1 + t * (x2 - x1), y1 + t * (y2 - y1));
        } else {
            return new Point2D.Double(x1, y1);
        }
    }

    /**
     * Método para verificar se os pontos de uma linha pertencem a um intervalo
     *
     * @param stress
     * @param stressA
     * @param stressB
     * @return
     */
    private static boolean validateLine(double stress1, double stress2, double stressA, double stressB) {
        boolean result = false;

        if (stressA >= stress1 && stressB <= stress2) {
            result = true;
        }
        if (stressB >= stress1 && stressA <= stress2) {
            result = true;
        }

        if (stressB >= stress1 && stressB <= stress2) {
            result = true;
        }
        if (stressB <= stress1 && stressB >= stress2) {
            result = true;
        }

        return result;
    }

    /**
     * Método para verificar se deve ocorrer o desenho do mapa de tensões
     *
     * @param stressA
     * @param stressB
     * @param nodalStresses
     * @return
     */
    private static boolean validateTriangle(int index, double stressA, double stressB, double[][] nodalStresses) {
        boolean result = false;

        double average = (nodalStresses[0][index] + nodalStresses[1][index] + nodalStresses[2][index]) / 3.0;
        if (average >= stressA && average <= stressB) {
            result = true;
        }

        if (
            (stressA >= nodalStresses[0][index] && stressA <= nodalStresses[1][index]) ||
            (stressA >= nodalStresses[1][index] && stressA <= nodalStresses[0][index])
        ) {
            result = true;
        }
        if (
            (stressA >= nodalStresses[1][index] && stressA <= nodalStresses[2][index]) ||
            (stressA >= nodalStresses[2][index] && stressA <= nodalStresses[1][index])
        ) {
            result = true;
        }
        if (
            (stressA >= nodalStresses[2][index] && stressA <= nodalStresses[0][index]) ||
            (stressA >= nodalStresses[0][index] && stressA <= nodalStresses[2][index])
        ) {
            result = true;
        }

        if (
            (stressB >= nodalStresses[0][index] && stressB <= nodalStresses[1][index]) ||
            (stressB >= nodalStresses[1][index] && stressB <= nodalStresses[0][index])
        ) {
            result = true;
        }
        if (
            (stressB >= nodalStresses[1][index] && stressB <= nodalStresses[2][index]) ||
            (stressB >= nodalStresses[2][index] && stressB <= nodalStresses[1][index])
        ) {
            result = true;
        }
        if (
            (stressB >= nodalStresses[2][index] && stressB <= nodalStresses[0][index]) ||
            (stressB >= nodalStresses[0][index] && stressB <= nodalStresses[2][index])
        ) {
            result = true;
        }

        return result;
    }

    /**
     * Método para obter a posição da tensão a representar graficamente
     *
     * @return
     */
    private int getIndex() {
        int index;
        switch (stressName) {
            case "σx":
                index = 0;
                break;
            case "σy":
                index = 1;
                break;
            default:
                index = 2;
                break;
        }

        return index;
    }

    /**
     * Classe para criar a lista de tensões nodais
     */
    private class NodalStresses {

        String shape;
        double[][] shapeCoordinates;
        double[][] nodalStresses;

        /**
         * Método construtor da classe NodalStresses
         *
         * @param type
         * @param shape
         * @param coordinates
         * @param nodalStresses
         */
        public NodalStresses(String shape, double[][] coordinates, double[][] nodalStresses) {
            this.shape = shape;
            this.shapeCoordinates = coordinates;
            this.nodalStresses = nodalStresses;
        }
    }

    /**
     * Classe para criar a lista de cores
     */
    private class StressesAndColors {

        Color color;
        double stress;

        /**
         * Método construtor da classe StressesAndColors
         *
         * @param color
         * @param stress
         */
        public StressesAndColors(Color color, double stress) {
            this.color = color;
            this.stress = stress;
        }
    }
}

/*
 * Esta classe desenha os diagramas de esforços em barras
 * A classe requer as coordenadas dos nós, o valor da carga nodal e a distruição na barra
 * Os métodos desenham os diferentes diagramas com o respetivo valor
 */

package app.backend;

import static app.calculations.AnalyticGeometry.coordinateTransformations;
import static app.calculations.FormatResults.decimalFormat;
import static app.calculations.FormatResults.parseDouble;
import static app.matrices.Multiply.multiply;
import static app.matrices.Subtract.subtract;
import static java.lang.Math.PI;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.round;
import static java.lang.Math.sin;

import app.calculations.AnalyticGeometry;
import app.calculations.FiniteElement;
import app.finiteelement.MatrixT;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 *
 * @author André de Sousa
 */
public class DiagramsForBars {

    private final String type;
    private final String diagram;
    private final int factor;
    private final double perspective;
    private final ArrayList<FiniteElement> arrayListFiniteElements;

    private ArrayList<double[]> listOfNodalForces;
    private ArrayList<DiagramAxialForce> diagramsAxialForces;
    private ArrayList<DiagramShearForce> diagramsShearForces;
    private ArrayList<DiagramBendingMoment> diagramsBendingMoments;
    private ArrayList<DiagramTorsionalMoment> diagramsTorsionalMoments;

    /**
     * Método construtor da classe Diagrams for bars
     *
     * @param type
     * @param diagram
     * @param arrayListFiniteElements
     * @param factor
     */
    public DiagramsForBars(String type, String diagram, ArrayList<FiniteElement> arrayListFiniteElements, int factor) {
        this.type = type;
        this.diagram = diagram;
        this.arrayListFiniteElements = arrayListFiniteElements;
        this.factor = factor;
        this.perspective = (PI * 60) / 180;

        //Cálculo dos diagramas de esforços da estrutura
        switch (diagram) {
            case "Axial Force":
                diagramsAxialForces = new ArrayList();
                diagramsForAxialForces();
                break;
            case "Shear Force":
                diagramsShearForces = new ArrayList();
                diagramsForShearForces();
                break;
            case "Bending Moment":
                diagramsBendingMoments = new ArrayList();
                diagramsForMoments();
                break;
            case "Torsional Moment":
                diagramsTorsionalMoments = new ArrayList();
                diagramsForTorsion();
                break;
        }
    }

    /**
     * Método para desenhar os diagramas de esforços axiais
     *
     * @param graphics
     */
    public void drawDiagrams(Graphics2D graphics) {
        listOfNodalForces = new ArrayList();
        double maxValue;

        switch (diagram) {
            case "Axial Force":
                maxValue = maxValueAxialForce(diagramsAxialForces);
                for (DiagramAxialForce diagramAxialForce : diagramsAxialForces) {
                    int length = diagramAxialForce.length;
                    double angle = diagramAxialForce.angle;
                    double[] diagramForBars = diagramAxialForce.diagramForBars;
                    int[][] coordinates = diagramAxialForce.coordinates;

                    drawAllDiagrams(graphics, type, length, angle, maxValue, diagramForBars, coordinates);
                    drawValuesForDiagrams(graphics, type, length, angle, maxValue, diagramForBars, coordinates);
                }
                drawNodalValues(graphics, maxValue);
                break;
            case "Shear Force":
                maxValue = maxValueShearForce(diagramsShearForces);
                for (DiagramShearForce diagramShearForce : diagramsShearForces) {
                    int length = diagramShearForce.length;
                    double angle = diagramShearForce.angle;
                    double[] diagramForBars = diagramShearForce.diagramForBars;
                    int[][] coordinates = diagramShearForce.coordinates;

                    drawAllDiagrams(graphics, type, length, angle, maxValue, diagramForBars, coordinates);
                    drawValuesForDiagrams(graphics, type, length, angle, maxValue, diagramForBars, coordinates);
                }
                drawNodalValues(graphics, maxValue);
                break;
            case "Bending Moment":
                maxValue = maxValueBendingMoment(diagramsBendingMoments);
                for (DiagramBendingMoment diagramBendingMoment : diagramsBendingMoments) {
                    int length = diagramBendingMoment.length;
                    double angle = diagramBendingMoment.angle;
                    double[] diagramForBars = diagramBendingMoment.diagramForBars;
                    int[][] coordinates = diagramBendingMoment.coordinates;

                    drawAllDiagrams(graphics, type, length, angle, maxValue, diagramForBars, coordinates);
                    drawValuesForDiagrams(graphics, type, length, angle, maxValue, diagramForBars, coordinates);
                }
                drawNodalValues(graphics, maxValue);
                break;
            case "Torsional Moment":
                maxValue = maxValueTorsionalMoment(diagramsTorsionalMoments);
                for (DiagramTorsionalMoment diagramTorsionalMoment : diagramsTorsionalMoments) {
                    int length = diagramTorsionalMoment.length;
                    double angle = diagramTorsionalMoment.angle;
                    double[] diagramForBars = diagramTorsionalMoment.diagramForBars;
                    int[][] coordinates = diagramTorsionalMoment.coordinates;

                    drawAllDiagrams(graphics, type, length, angle, maxValue, diagramForBars, coordinates);
                    drawValuesForDiagrams(graphics, type, length, angle, maxValue, diagramForBars, coordinates);
                }
                drawNodalValues(graphics, maxValue);
                break;
        }
    }

    /**
     * Método para desenhar os diagramas de esforços axiais
     *
     * @param graphics
     */
    public void drawAxialForce(Graphics2D graphics) {
        drawDiagrams(graphics);
    }

    /**
     * Método para desenhar os diagramas de esforços transversos
     *
     * @param graphics
     */
    public void drawShearForce(Graphics2D graphics) {
        drawDiagrams(graphics);
    }

    /**
     * Método para desenhar os diagramas de momentos fletores
     *
     * @param graphics
     */
    public void drawBendingMoment(Graphics2D graphics) {
        drawDiagrams(graphics);
    }

    /**
     * Método para desenhar os diagramas de momentos torsores
     *
     * @param graphics
     */
    public void drawTorsionalMoment(Graphics2D graphics) {
        drawDiagrams(graphics);
    }

    /**
     * Método para construir a lista com os diagramas de esforços axiais
     */
    private void diagramsForAxialForces() {
        for (FiniteElement finiteElement : arrayListFiniteElements) {
            double[][] shapeCoordinates = finiteElement.getShapeCoordinates();
            double[][] localVectorF = finiteElement.getVectorF();
            double[][] globalLoadVector = finiteElement.getLoadVector();
            int[][] coordinates = convertCoordinates(shapeCoordinates, factor);

            diagramsAxialForces.add(new DiagramAxialForce(type, factor, localVectorF, globalLoadVector, coordinates));
        }
    }

    /**
     * Método para construir a lista com os diagramas de esforços transversos
     */
    private void diagramsForShearForces() {
        for (FiniteElement finiteElement : arrayListFiniteElements) {
            double[][] shapeCoordinates = finiteElement.getShapeCoordinates();
            double[][] localVectorF = finiteElement.getVectorF();
            double[][] globalLoadVector = finiteElement.getLoadVector();
            int[][] coordinates = convertCoordinates(shapeCoordinates, factor);

            diagramsShearForces.add(new DiagramShearForce(type, factor, localVectorF, globalLoadVector, coordinates));
        }
    }

    /**
     * Método para construir a lista com os diagramas de momentos fletores
     */
    private void diagramsForMoments() {
        for (FiniteElement finiteElement : arrayListFiniteElements) {
            double[][] shapeCoordinates = finiteElement.getShapeCoordinates();
            double[][] localVectorF = finiteElement.getVectorF();
            double[][] globalLoadVector = finiteElement.getLoadVector();
            int[][] coordinates = convertCoordinates(shapeCoordinates, factor);

            diagramsBendingMoments.add(new DiagramBendingMoment(type, factor, localVectorF, globalLoadVector, coordinates));
        }
    }

    /**
     * Método para construir a lista com os diagramas de momentos torsores
     */
    private void diagramsForTorsion() {
        for (FiniteElement finiteElement : arrayListFiniteElements) {
            double[][] shapeCoordinates = finiteElement.getShapeCoordinates();
            double[][] localVectorF = finiteElement.getVectorF();
            double[][] globalLoadVector = finiteElement.getLoadVector();
            int[][] coordinates = convertCoordinates(shapeCoordinates, factor);

            diagramsTorsionalMoments.add(new DiagramTorsionalMoment(type, factor, localVectorF, globalLoadVector, coordinates));
        }
    }

    /**
     * Método para calcular o valor máximo de uma lista de esforços
     *
     * @param diagramAxialForce
     * @return
     */
    private double maxValueAxialForce(ArrayList<DiagramsForBars.DiagramAxialForce> diagramAxialForce) {
        double maxValue = 0.0;

        for (DiagramAxialForce diagrams : diagramAxialForce) {
            double[] values = diagrams.diagramForBars;

            for (double value : values) {
                maxValue = maximumValue(maxValue, value);
            }
        }

        return maxValue;
    }

    /**
     * Método para calcular o valor máximo de uma lista de esforços
     *
     * @param diagramAxialForce
     * @return
     */
    private double maxValueShearForce(ArrayList<DiagramsForBars.DiagramShearForce> diagramShearForce) {
        double maxValue = 0.0;

        for (DiagramShearForce diagrams : diagramShearForce) {
            double[] values = diagrams.diagramForBars;

            for (double value : values) {
                maxValue = maximumValue(maxValue, value);
            }
        }

        return maxValue;
    }

    /**
     * Método para calcular o valor máximo de uma lista de esforços
     *
     * @param diagramAxialForce
     * @return
     */
    private double maxValueBendingMoment(ArrayList<DiagramsForBars.DiagramBendingMoment> diagramBendingMoment) {
        double maxValue = 0.0;

        for (DiagramBendingMoment diagrams : diagramBendingMoment) {
            double[] values = diagrams.diagramForBars;

            for (double value : values) {
                maxValue = maximumValue(maxValue, value);
            }
        }

        return maxValue;
    }

    /**
     * Método para calcular o valor máximo de uma lista de esforços
     *
     * @param diagramAxialForce
     * @return
     */
    private double maxValueTorsionalMoment(ArrayList<DiagramsForBars.DiagramTorsionalMoment> diagramTorsionalMoment) {
        double maxValue = 0.0;

        for (DiagramTorsionalMoment diagrams : diagramTorsionalMoment) {
            double[] values = diagrams.diagramForBars;

            for (double value : values) {
                maxValue = maximumValue(maxValue, value);
            }
        }

        return maxValue;
    }

    /**
     * Método que devolve o valor máximo absoluto de dois números
     *
     * @param maxValue
     * @param value
     * @return
     */
    private double maximumValue(double maxValue, double value) {
        if (value >= 0) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        if (value < 0) {
            if ((value * -1) > maxValue) {
                maxValue = value * -1;
            }
        }

        return maxValue;
    }

    /**
     * Método para converter coordenadas de metros para pixeis
     *
     * @param coordinates
     * @param factor
     * @return
     */
    private static int[][] convertCoordinates(double[][] coordinates, int factor) {
        int length = coordinates.length;

        int[][] shapeCoordinates = new int[length][2];
        for (int i = 0; i < length; i++) {
            shapeCoordinates[i][0] = (int) round(coordinates[i][0] * factor);
            shapeCoordinates[i][1] = (int) round(coordinates[i][1] * factor);
        }

        return shapeCoordinates;
    }

    /**
     * Método que devolve o ângulo de inclinação da reta
     *
     * @return
     */
    private double angle(int[][] shapeCoordinates) {
        double x1, y1, x2, y2;
        x1 = shapeCoordinates[0][0];
        y1 = shapeCoordinates[0][1];
        x2 = shapeCoordinates[1][0];
        y2 = shapeCoordinates[1][1];

        double inclination = (y2 - y1) / (x2 - x1);
        double angle = atan(inclination);

        return angle;
    }

    /**
     * Método para desenhar todos os diagramas de esforços da estrutura
     *
     * @param graphics
     * @param type
     * @param length
     * @param angle
     * @param diagramForBars
     * @param coordinates
     */
    private void drawAllDiagrams(
        Graphics2D graphics,
        String type,
        int length,
        double angle,
        double maxValue,
        double[] diagramForBars,
        int[][] coordinates
    ) {
        graphics.setPaint(Color.BLUE);

        int nPoints = length;
        if (length % 2 == 0) {
            nPoints = length - 1;
        }

        //Desenho da forma do diagrama de esforços
        for (int i = 0; i <= nPoints; i++) {
            double valueA = diagramForBars[i];

            double scaleValueA = 0.0;
            if (maxValue != 0) {
                //if(round(valueA) == 0.0) {valueA = 0.0;}
                if (parseDouble(valueA) == 0.0) {
                    valueA = 0.0;
                }
                scaleValueA = (valueA * 50) / maxValue;
            }

            //Coordenadas dos pontos do diagrama
            double x1, y1;
            if ("Grids".equals(type)) {
                x1 = coordinates[0][0] + i * cos(angle);
                y1 = coordinates[0][1] + i * sin(angle);
                double[][] matrix = createPerspective(new double[][] { { x1 }, { y1 } });
                x1 = matrix[0][0];
                y1 = matrix[1][0] - scaleValueA;
            } else {
                double[][] matrixA = { { 0 }, { 0 }, { scaleValueA }, { 0 } };
                double[][] pointsA = coordinateTransformations(matrixA, angle - (PI / 2));
                x1 = coordinates[0][0] + pointsA[2][0] + i * cos(angle);
                y1 = coordinates[0][1] + pointsA[3][0] + i * sin(angle);
            }

            graphics.draw(new Line2D.Double(x1, y1, x1, y1));
        }

        //Desenho das linhas de fecho do diagrama de esforços
        double valueA = diagramForBars[0];
        double valueB = diagramForBars[length];

        double scaleValueA = 0.0;
        double scaleValueB = 0.0;
        if (maxValue != 0) {
            if (parseDouble(valueA) == 0.0) {
                valueA = 0.0;
            }
            if (parseDouble(valueB) == 0.0) {
                valueB = 0.0;
            }
            scaleValueA = (valueA * 50) / maxValue;
            scaleValueB = (valueB * 50) / maxValue;
        }

        //Coordenadas dos pontos do diagrama
        double x1, x2, x3, x4, y1, y2, y3, y4;
        if ("Grids".equals(type)) {
            double[][] matrix;
            matrix = createPerspective(new double[][] { { coordinates[0][0] }, { coordinates[0][1] } });
            x1 = matrix[0][0];
            y1 = matrix[1][0];
            x2 = matrix[0][0];
            y2 = matrix[1][0] - scaleValueA;

            matrix = createPerspective(new double[][] { { coordinates[1][0] }, { coordinates[1][1] } });
            x3 = matrix[0][0];
            y3 = matrix[1][0];
            x4 = matrix[0][0];
            y4 = matrix[1][0] - scaleValueB;
        } else {
            double[][] matrixA = { { 0 }, { 0 }, { scaleValueA }, { 0 } };
            double[][] matrixB = { { 0 }, { 0 }, { scaleValueB }, { 0 } };
            double[][] pointsA = coordinateTransformations(matrixA, angle - (PI / 2));
            double[][] pointsB = coordinateTransformations(matrixB, angle - (PI / 2));

            x1 = coordinates[0][0];
            y1 = coordinates[0][1];
            x2 = coordinates[0][0] + pointsA[2][0];
            y2 = coordinates[0][1] + pointsA[3][0];

            x3 = coordinates[1][0];
            y3 = coordinates[1][1];
            x4 = coordinates[1][0] + pointsB[2][0];
            y4 = coordinates[1][1] + pointsB[3][0];
        }

        graphics.draw(new Line2D.Double(x1, y1, x2, y2));
        graphics.draw(new Line2D.Double(x3, y3, x4, y4));
    }

    /**
     * Método par desenhar os valores máximos dos diagramas de esforços
     *
     * @param graphics
     * @param type
     * @param length
     * @param angle
     * @param maxValue
     * @param diagramForBars
     * @param coordinates
     */
    private void drawValuesForDiagrams(
        Graphics2D graphics,
        String type,
        int length,
        double angle,
        double maxValue,
        double[] diagramForBars,
        int[][] coordinates
    ) {
        graphics.setPaint(Color.RED);

        double scaleValue;
        double[][] maxValues;

        if (parseDouble(diagramForBars[0]) == parseDouble(diagramForBars[length])) {
            maxValues = new double[][] { { -1.0, 0.0 }, { -1.0, 0.0 }, { -1.0, 0.0 } };
            maxValues[2][0] = length / 2;
            maxValues[2][1] = diagramForBars[0];
        } else {
            maxValues = new double[][] { { -1.0, 0.0 }, { -1.0, 0.0 } };

            //Instrução para adicionar valores nodais à lista de esforços nodais
            listOfNodalForces.add(new double[] { coordinates[0][0], coordinates[0][1], angle, diagramForBars[0], 1.0 });
            listOfNodalForces.add(new double[] { coordinates[1][0], coordinates[1][1], angle, diagramForBars[length], 1.0 });
        }

        //Instruções para desenhar valores máximos e/ou médios
        for (int i = 0; i <= length; i++) {
            if (diagramForBars[i] < maxValues[0][1]) {
                maxValues[0][0] = i;
                maxValues[0][1] = diagramForBars[i];
            }
            if (diagramForBars[i] > maxValues[1][1]) {
                maxValues[1][0] = i;
                maxValues[1][1] = diagramForBars[i];
            }
        }

        //Contador do número de valores desenhados
        int counter = 0;

        int i = 0;
        while (i < maxValues.length) {
            boolean drawValues = true;
            if (maxValues[i][0] == -1.0 || maxValues[i][0] == 0.0 || maxValues[i][0] == length) {
                drawValues = false;
            }

            //Condição para não desenhar o valor constante sempre que existem máximos
            if (drawValues && i == 2) {
                if (counter > 0) {
                    drawValues = false;
                }
            }

            if (drawValues) {
                counter++;

                scaleValue = (maxValues[i][1] * 50) / maxValue;
                if (parseDouble(maxValues[i][1]) == 0.0) {
                    scaleValue = 0.0;
                }

                if ("Grids".equals(type)) {
                    double[][] matrix = createPerspective(
                        new double[][] {
                            { coordinates[0][0] + maxValues[i][0] * cos(angle) },
                            { coordinates[0][1] + maxValues[i][0] * sin(angle) },
                        }
                    );
                    int x = (int) round(matrix[0][0]);
                    int y = (int) round(matrix[1][0] - scaleValue);

                    if ("Bending Moment".equals(diagram) || "Torsional Moment".equals(diagram)) {
                        maxValues[i][1] = maxValues[i][1] * -1;
                    }

                    graphics.drawString(decimalFormat(maxValues[i][1]), x, y);
                } else {
                    double[][] matrix = { { 0 }, { 0 }, { scaleValue }, { 0 } };
                    double[][] points = coordinateTransformations(matrix, angle - (PI / 2));

                    int x = (int) round(coordinates[0][0] + points[2][0] + maxValues[i][0] * cos(angle));
                    int y = (int) round(coordinates[0][1] + points[3][0] + maxValues[i][0] * sin(angle));

                    if ("Bending Moment".equals(diagram) || "Torsional Moment".equals(diagram)) {
                        maxValues[i][1] = maxValues[i][1] * -1;
                    }

                    graphics.drawString(decimalFormat(maxValues[i][1]), x, y);
                }
            }

            i++;
        }
    }

    /**
     * Método par desenhar os valores nodais dos diagramas de esforços
     *
     * @param graphics
     */
    private void drawNodalValues(Graphics2D graphics, double maxValue) {
        graphics.setPaint(Color.RED);

        /**
         * Descrição de cada entrada da lista de esforços nodais
         *
         * 0: A primeira coluna contém a coordenada x
         * 1: A segunda coluna contém a coordenada y
         * 2: A terceira coluna contém o ângulo de inclinação da barra
         * 3: A quarta coluna contém o valor do esforço no nó
         * 4: A quinta coluna contém o estado para impressão
         */

        for (double[] nodalForces : listOfNodalForces) {
            if (nodalForces[4] == 1.0) {
                double x, y, anglePositive, angleNegative;

                x = nodalForces[0];
                y = nodalForces[1];
                anglePositive = nodalForces[2];
                angleNegative = nodalForces[2];

                //Identificação dos valores máximos nodais
                boolean drawPositive = false;
                boolean drawNegative = false;

                double maxPositive = 0.0;
                double maxNegative = 0.0;
                for (double[] forces : listOfNodalForces) {
                    if (forces[0] == x && forces[1] == y && forces[4] == 1.0) {
                        if (forces[3] >= maxPositive) {
                            x = forces[0];
                            y = forces[1];
                            anglePositive = forces[2];
                            maxPositive = forces[3];
                            drawPositive = true;
                        }
                        if (forces[3] <= maxNegative) {
                            x = forces[0];
                            y = forces[1];
                            angleNegative = forces[2];
                            maxNegative = forces[3];
                            drawNegative = true;
                        }
                        forces[4] = 0.0;
                    }
                }

                //Desenho dos valores máximos nodais
                double[][] maxValues = new double[1][2];

                if (drawPositive && drawNegative) {
                    maxValues = new double[][] { { 0.0, maxNegative, angleNegative }, { 0.0, maxPositive, anglePositive } };
                } else {
                    if (drawPositive) {
                        maxValues = new double[][] { { 0.0, maxPositive, anglePositive } };
                    }
                    if (drawNegative) {
                        maxValues = new double[][] { { 0.0, maxNegative, angleNegative } };
                    }
                }

                int i = 0;
                while (i < maxValues.length) {
                    double scaleValue = (maxValues[i][1] * 50) / maxValue;
                    if (round(maxValues[i][1]) == 0.0) {
                        scaleValue = 0.0;
                    }

                    double angle = maxValues[i][2];
                    if ("Grids".equals(type)) {
                        double[][] matrix = createPerspective(
                            new double[][] { { x + maxValues[i][0] * cos(angle) }, { y + maxValues[i][0] * sin(angle) } }
                        );
                        int xx = (int) round(matrix[0][0]);
                        int yy = (int) round(matrix[1][0] - scaleValue);

                        if ("Bending Moment".equals(diagram) || "Torsional Moment".equals(diagram)) {
                            maxValues[i][1] = maxValues[i][1] * -1;
                        }

                        graphics.drawString(decimalFormat(maxValues[i][1]), xx, yy);
                    } else {
                        double[][] matrix = { { 0 }, { 0 }, { scaleValue }, { 0 } };
                        double[][] points = coordinateTransformations(matrix, angle - (PI / 2));

                        int xx = (int) round(x + points[2][0] + maxValues[i][0] * cos(angle));
                        int yy = (int) round(y + points[3][0] + maxValues[i][0] * sin(angle));

                        if ("Bending Moment".equals(diagram) || "Torsional Moment".equals(diagram)) {
                            maxValues[i][1] = maxValues[i][1] * -1;
                        }

                        graphics.drawString(decimalFormat(maxValues[i][1]), xx, yy);
                    }

                    i++;
                }
            }
        }
    }

    /**
     * Método para colocar as coordenadas das figuras em perspectiva
     *
     * @param coordinates
     */
    private double[][] createPerspective(double[][] coordinates) {
        double[][] perspectiveCoordinates = new double[4][1];

        perspectiveCoordinates[0][0] = coordinates[0][0] + cos(perspective) * coordinates[1][0];
        perspectiveCoordinates[1][0] = sin(perspective) * coordinates[1][0];

        return perspectiveCoordinates;
    }

    /**
     * Classe para desenhar os diagramas de esforços axiais
     */
    private class DiagramAxialForce {

        public final String type;
        public final int length;
        public final double factor;
        public final double angle;
        public final int[][] coordinates;
        public double[] diagramForBars;

        /**
         * Método construtor da classe DiagramAxialForce
         *
         * @param type
         * @param factor
         * @param localVectorF
         * @param globalLoadVector
         * @param coordinates
         */
        public DiagramAxialForce(String type, double factor, double[][] localVectorF, double[][] globalLoadVector, int[][] coordinates) {
            this.type = type;
            this.length = (int) round(AnalyticGeometry.length(coordinates));
            this.factor = factor;
            this.angle = angle(coordinates);
            this.coordinates = coordinates;

            double[][] localVector = null;
            double distributedLoad = 0.0;
            if ("1D".equals(type)) {
                double[][] matrixT = MatrixT.matrixT_1D(angle);
                double[][] localDistributedLoad = multiply(matrixT, globalLoadVector);
                localVector = subtract(localVectorF, localDistributedLoad);
                distributedLoad = (localDistributedLoad[0][0] + localDistributedLoad[2][0]) / (length / factor);
            }
            if ("Frames".equals(type)) {
                double[][] matrixT = MatrixT.matrixT_Frames(angle);
                double[][] localDistributedLoad = multiply(matrixT, globalLoadVector);
                localVector = subtract(localVectorF, localDistributedLoad);
                distributedLoad = (localDistributedLoad[0][0] + localDistributedLoad[3][0]) / (length / factor);
            }

            createDiagram(type, distributedLoad, localVector);
        }

        /**
         * Método para calcular os valores do diagrama de esforços
         *
         * @param type
         * @param distributedLoad
         * @param loadVector
         */
        private void createDiagram(String type, double distributedLoad, double[][] localVector) {
            diagramForBars = new double[length + 1];

            //Valor do momento fletor nas extremidades do elemento finito
            double axial = localVector[0][0];

            for (int i = 0; i <= length; i++) {
                double x = i / factor;
                diagramForBars[i] = -axial - distributedLoad * x;
            }
        }
    }

    /**
     * Classe para desenhar os diagramas de esforços transversos
     */
    private class DiagramShearForce {

        public final String type;
        public final int length;
        public final double factor;
        public final double angle;
        public final int[][] coordinates;
        public double[] diagramForBars;

        /**
         * Método construtor da classe DiagramShearForce
         *
         * @param type
         * @param factor
         * @param localVectorF
         * @param globalLoadVector
         * @param coordinates
         */
        public DiagramShearForce(String type, double factor, double[][] localVectorF, double[][] globalLoadVector, int[][] coordinates) {
            this.type = type;
            this.length = (int) round(AnalyticGeometry.length(coordinates));
            this.factor = factor;
            this.angle = angle(coordinates);
            this.coordinates = coordinates;

            double[][] localVector = null;
            double distributedLoad = 0.0;
            if ("Frames".equals(type)) {
                double[][] matrixT = MatrixT.matrixT_Frames(angle);
                double[][] localDistributedLoad = multiply(matrixT, globalLoadVector);
                localVector = subtract(localVectorF, localDistributedLoad);
                distributedLoad = (localDistributedLoad[1][0] + localDistributedLoad[4][0]) / (length / factor);
            }
            if ("Grids".equals(type)) {
                double[][] matrixT = MatrixT.matrixT_Grids(angle);
                double[][] localDistributedLoad = multiply(matrixT, globalLoadVector);
                localVector = subtract(localVectorF, localDistributedLoad);
                distributedLoad = (localDistributedLoad[0][0] + localDistributedLoad[3][0]) / (length / factor);
            }

            createDiagram(type, distributedLoad, localVector);
        }

        /**
         * Método para calcular os valores do diagrama de esforços
         *
         * @param type
         * @param distributedLoad
         * @param loadVector
         */
        private void createDiagram(String type, double distributedLoad, double[][] localVector) {
            diagramForBars = new double[length + 1];

            //Valor do momento fletor nas extremidades do elemento finito
            double shear = 0.0;
            if ("Frames".equals(type)) {
                shear = localVector[1][0];
            }
            if ("Grids".equals(type)) {
                shear = localVector[0][0];
            }

            for (int i = 0; i <= length; i++) {
                double x = i / factor;
                diagramForBars[i] = -shear - distributedLoad * x;
            }
        }
    }

    /**
     * Classe para desenhar os diagramas de momentos fletores
     */
    private class DiagramBendingMoment {

        public final String type;
        public final int length;
        public final double factor;
        public final double angle;
        public final int[][] coordinates;
        public double[] diagramForBars;

        /**
         * Método construtor da classe DiagramBendingMoment
         *
         * @param type
         * @param factor
         * @param localVectorF
         * @param globalLoadVector
         * @param coordinates
         */
        public DiagramBendingMoment(String type, double factor, double[][] localVectorF, double[][] globalLoadVector, int[][] coordinates) {
            this.type = type;
            this.length = (int) round(AnalyticGeometry.length(coordinates));
            this.factor = factor;
            this.angle = angle(coordinates);
            this.coordinates = coordinates;

            double[][] localVector = null;
            double distributedLoad = 0.0;
            if ("Frames".equals(type)) {
                double[][] matrixT = MatrixT.matrixT_Frames(angle);
                double[][] localDistributedLoad = multiply(matrixT, globalLoadVector);
                localVector = subtract(localVectorF, localDistributedLoad);
                distributedLoad = (localDistributedLoad[1][0] + localDistributedLoad[4][0]) / (length / factor);
            }
            if ("Grids".equals(type)) {
                double[][] matrixT = MatrixT.matrixT_Grids(angle);
                double[][] localDistributedLoad = multiply(matrixT, globalLoadVector);
                localVector = subtract(localVectorF, localDistributedLoad);
                distributedLoad = (localDistributedLoad[0][0] + localDistributedLoad[3][0]) / (length / factor);
            }

            createDiagram(type, distributedLoad, localVector);
        }

        /**
         * Método para calcular os valores do diagrama de esforços
         *
         * @param type
         * @param distributedLoad
         * @param loadVector
         */
        private void createDiagram(String type, double distributedLoad, double[][] localVector) {
            diagramForBars = new double[length + 1];

            //Valor do momento fletor nas extremidades do elemento finito
            double shear = 0.0;
            if ("Frames".equals(type)) {
                shear = localVector[1][0];
            }
            if ("Grids".equals(type)) {
                shear = localVector[0][0];
            }
            double moment = localVector[2][0];

            for (int i = 0; i <= length; i++) {
                double x = i / factor;
                diagramForBars[i] = -moment + shear * x + (distributedLoad / 2) * (x * x);
            }
        }
    }

    /**
     * Classe para desenhar os diagramas de momentos torsores
     */
    private class DiagramTorsionalMoment {

        public final String type;
        public final int length;
        public final double factor;
        public final double angle;
        public final int[][] coordinates;
        public double[] diagramForBars;

        /**
         * Método construtor da classe DiagramTorsionalMoment
         *
         * @param type
         * @param factor
         * @param localVectorF
         * @param globalLoadVector
         * @param coordinates
         */
        public DiagramTorsionalMoment(
            String type,
            double factor,
            double[][] localVectorF,
            double[][] globalLoadVector,
            int[][] coordinates
        ) {
            this.type = type;
            this.length = (int) round(AnalyticGeometry.length(coordinates));
            this.factor = factor;
            this.angle = angle(coordinates);
            this.coordinates = coordinates;

            double[][] matrixT = MatrixT.matrixT_Grids(angle);
            double[][] localDistributedLoad = multiply(matrixT, globalLoadVector);
            double[][] localVector = subtract(localVectorF, localDistributedLoad);
            double distributedLoad = (localDistributedLoad[1][0] + localDistributedLoad[4][0]) / (length / factor);

            createDiagram(type, distributedLoad, localVector);
        }

        /**
         * Método para calcular os valores do diagrama de esforços
         *
         * @param type
         * @param distributedLoad
         * @param loadVector
         */
        private void createDiagram(String type, double distributedLoad, double[][] localVector) {
            diagramForBars = new double[length + 1];

            //Valor do momento fletor nas extremidades do elemento finito
            double moment = localVector[1][0];

            for (int i = 0; i <= length; i++) {
                double x = i / factor;
                diagramForBars[i] = moment + distributedLoad * x;
            }
        }
    }
}

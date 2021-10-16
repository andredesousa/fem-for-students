/*
 * Esta classe permite cotar as dimensões dos objetos desenhados
 * A linha de cotagem é desenhada a partir das coordenadas de dois pontos
 * Os métodos fornecem todos os elementos relativos à sua geometria
 */

package app.backend;

import static app.calculations.AnalyticGeometry.coordinateTransformations;
import static app.calculations.AnalyticGeometry.lineInclination;
import static app.calculations.AnalyticGeometry.midPoint;
import static app.calculations.FormatResults.decimalFormat;
import static java.lang.Math.PI;
import static java.lang.Math.round;

import app.calculations.AnalyticGeometry;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 *
 * @author André de Sousa
 */
public class DrawDimensionLine implements Serializable {

    public double dimension;
    public boolean selected;

    //Pontos para armazenar as coordenadas da linha
    private final Point pointA;
    private final Point pointB;

    //Cores para desenhar a linha nos seus diferentes estados
    private Color defaultColor = new Color(0, 0, 0);
    private Color selectedColor = new Color(99, 180, 251);

    /**
     * Método construtor da classe DrawDimensionLine
     *
     * @param x1 é a abscissa do primeiro ponto da linha
     * @param y1 é a ordenada do primeiro ponto da linha
     * @param x2 é a abscissa do último ponto da linha
     * @param y2 é a ordenada do último ponto da linha
     */
    public DrawDimensionLine(int x1, int y1, int x2, int y2) {
        this.dimension = AnalyticGeometry.length(x1, y1, x2, y2);
        this.pointA = new Point(x1, y1);
        this.pointB = new Point(x2, y2);
        this.selected = false;
    }

    /**
     * Método construtor da classe DrawDimensionLine
     *
     * @param x1 é a abscissa do primeiro ponto da linha
     * @param y1 é a ordenada do primeiro ponto da linha
     * @param x2 é a abscissa do último ponto da linha
     * @param y2 é a ordenada do último ponto da linha
     * @param selected é o estado da linha a desenhar
     */
    public DrawDimensionLine(int x1, int y1, int x2, int y2, boolean selected) {
        this.dimension = AnalyticGeometry.length(x1, y1, x2, y2);
        this.pointA = new Point(x1, y1);
        this.pointB = new Point(x2, y2);
        this.selected = selected;
    }

    /**
     * Método para alterar as cores definidas por defeito
     *
     * @param defaultColor é a cor do objeto definida por defeito
     * @param selected é a cor do objeto no estado selecionado
     */
    public void editColors(Color defaultColor, Color selected) {
        this.defaultColor = defaultColor;
        this.selectedColor = selected;
    }

    /**
     * Método para alterar o estado da linha
     *
     * @param select informa se a linha está selecionada
     */
    public void select(boolean select) {
        selected = select;
    }

    /**
     * Método para desenhar a linha de cotagem
     *
     * @param line2D contém a informação da linha a desenhar
     */
    public void draw(Graphics2D line2D) {
        if (selected) {
            line2D.setPaint(selectedColor);
        } else {
            line2D.setPaint(defaultColor);
        }

        drawDimensionLine(line2D);
    }

    /**
     * Método para desenhar a linha de cotagem e a legenda
     *
     * @param line2D contém a informação da linha a desenhar
     * @param factor é o fator de escala para o desenho
     */
    public void draw(Graphics2D line2D, double factor) {
        if (selected) {
            line2D.setPaint(selectedColor);
        } else {
            line2D.setPaint(defaultColor);
        }

        drawDimensionLine(line2D);
        drawLegends(line2D, factor);
    }

    /**
     * Método para saber se o ponto está nas proximidades da linha
     *
     * @param point contém as coordenadas do rato
     * @return
     */
    public boolean contains(Point point) {
        double angle = lineInclination(pointA.x, pointA.y, pointB.x, pointB.y);

        //Instruções para criar a linha de cotagem
        double[][] matrixA = { { 0 }, { 0 }, { 30 }, { 0 } };
        double[][] matrixB = { { 0 }, { 0 }, { 25 }, { 0 } };

        double[][] pointsA = coordinateTransformations(matrixA, angle - (PI / 2));
        double[][] pointsB = coordinateTransformations(matrixB, angle - (PI / 2));

        Line2D.Double lineA = new Line2D.Double(pointA.x, pointA.y, pointA.x + pointsA[2][0], pointA.y + pointsA[3][0]);
        Line2D.Double lineB = new Line2D.Double(pointB.x, pointB.y, pointB.x + pointsA[2][0], pointB.y + pointsA[3][0]);
        Line2D.Double lineC = new Line2D.Double(
            pointA.x + pointsB[2][0],
            pointA.y + pointsB[3][0],
            pointB.x + pointsB[2][0],
            pointB.y + pointsB[3][0]
        );

        //Cálculo das distâncias aos elementos da linha
        if (lineA.ptSegDist(point) < 6) {
            return true;
        } else if (lineB.ptSegDist(point) < 6) {
            return true;
        } else {
            return (lineC.ptSegDist(point) < 6);
        }
    }

    /**
     * Método para mover a linha na relação fornecida
     *
     * @param deltaX é a distância a mover na horizontal
     * @param deltaY é a distância a mover na vertical
     */
    public void moveLine(int deltaX, int deltaY) {
        pointA.translate(deltaX, deltaY);
        pointB.translate(deltaX, deltaY);
    }

    /**
     * Método para obter uma matriz com as coordenadas da linha
     *
     * @return
     */
    public int[][] getCoordinates() {
        int[][] coordinates = new int[2][2];

        coordinates[0][0] = pointA.x;
        coordinates[0][1] = pointA.y;
        coordinates[1][0] = pointB.x;
        coordinates[1][1] = pointB.y;

        return coordinates;
    }

    /**
     * Método para obter o ponto mais proximo do ponto fornecido
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     * @param maxDistance é a distância máxima admissível
     * @return
     */
    public Point selectPoint(int xPoint, int yPoint, double maxDistance) {
        int[][] coordinates = getCoordinates();
        Point point2D = null;

        for (int[] coordinate : coordinates) {
            int x = coordinate[0];
            int y = coordinate[1];

            double distance = AnalyticGeometry.length(xPoint, yPoint, x, y);

            if (distance < maxDistance) {
                maxDistance = distance;
                point2D = new Point(x, y);
            }
        }

        return point2D;
    }

    /**
     * Método para desenhar a linha e a legenda
     *
     * @param line2D contém a informação da linha a desenhar
     */
    private void drawDimensionLine(Graphics2D line2D) {
        double angle = lineInclination(pointA.x, pointA.y, pointB.x, pointB.y);

        //Instruções para desenhar a linha de cotagem
        double[][] matrixA = { { 0 }, { 0 }, { 30 }, { 0 } };
        double[][] matrixB = { { 0 }, { 0 }, { 25 }, { 0 } };

        double[][] pointsA = coordinateTransformations(matrixA, angle - (PI / 2));
        double[][] pointsB = coordinateTransformations(matrixB, angle - (PI / 2));

        line2D.draw(new Line2D.Double(pointA.x, pointA.y, pointA.x + pointsA[2][0], pointA.y + pointsA[3][0]));
        line2D.draw(new Line2D.Double(pointB.x, pointB.y, pointB.x + pointsA[2][0], pointB.y + pointsA[3][0]));
        line2D.draw(
            new Line2D.Double(pointA.x + pointsB[2][0], pointA.y + pointsB[3][0], pointB.x + pointsB[2][0], pointB.y + pointsB[3][0])
        );

        //Instruções para desenhar as setas da linha de cotagem
        matrixA = new double[][] { { 0 }, { 0 }, { 7 }, { 0 } };
        matrixB = new double[][] { { 0 }, { 0 }, { 25 }, { 0 } };

        pointsA = coordinateTransformations(matrixA, angle - (PI / 4));
        pointsB = coordinateTransformations(matrixB, angle - (PI / 2));
        line2D.draw(
            new Line2D.Double(
                pointA.x + pointsB[2][0],
                pointA.y + pointsB[3][0],
                pointA.x + pointsB[2][0] + pointsA[2][0],
                pointA.y + pointsB[3][0] + pointsA[3][0]
            )
        );

        pointsA = coordinateTransformations(matrixA, angle + (PI / 4));
        pointsB = coordinateTransformations(matrixB, angle - (PI / 2));
        line2D.draw(
            new Line2D.Double(
                pointA.x + pointsB[2][0],
                pointA.y + pointsB[3][0],
                pointA.x + pointsB[2][0] + pointsA[2][0],
                pointA.y + pointsB[3][0] + pointsA[3][0]
            )
        );

        pointsA = coordinateTransformations(matrixA, angle - (3 * PI / 4));
        pointsB = coordinateTransformations(matrixB, angle - (PI / 2));
        line2D.draw(
            new Line2D.Double(
                pointB.x + pointsB[2][0],
                pointB.y + pointsB[3][0],
                pointB.x + pointsB[2][0] + pointsA[2][0],
                pointB.y + pointsB[3][0] + pointsA[3][0]
            )
        );

        pointsA = coordinateTransformations(matrixA, angle + (3 * PI / 4));
        pointsB = coordinateTransformations(matrixB, angle - (PI / 2));
        line2D.draw(
            new Line2D.Double(
                pointB.x + pointsB[2][0],
                pointB.y + pointsB[3][0],
                pointB.x + pointsB[2][0] + pointsA[2][0],
                pointB.y + pointsB[3][0] + pointsA[3][0]
            )
        );
    }

    /**
     * Método para desenhar a legenda da linha de cotagem
     *
     * @param line2D contém a informação da linha a desenhar
     */
    private void drawLegends(Graphics2D line2D, double factor) {
        double angle = lineInclination(pointA.x, pointA.y, pointB.x, pointB.y);

        double[][] matrix = { { 0 }, { 0 }, { 27 }, { 0 } };
        double[][] points = coordinateTransformations(matrix, angle - (PI / 2));

        Point2D midPoint = midPoint(pointA.x, pointA.y, pointB.x, pointB.y);
        String lineDimension = decimalFormat(dimension / factor) + " m";
        FontMetrics fontSize = line2D.getFontMetrics();
        Rectangle2D rectangle = fontSize.getStringBounds(lineDimension, line2D);

        double x = midPoint.getX() + points[2][0];
        double y = midPoint.getY() + points[3][0];

        line2D.rotate(angle, x, y);
        line2D.drawString(lineDimension, (int) (round(x - rectangle.getWidth() / 2)), (int) (round(y)));
        line2D.rotate(-angle, x, y);
    }
}

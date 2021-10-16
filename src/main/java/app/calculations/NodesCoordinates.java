/*
 * Estes métodos definem as coordenadas dos nós de cada tipo de elemento finito
 * As coordenadas são definidas a partir das coordenadas das figuras geométricas
 * A ordenação dos nós está definida de acordo com a documentação
 */

package app.calculations;

import static app.calculations.AnalyticGeometry.midPoint;

import java.awt.geom.Point2D;

/**
 *
 * @author André de Sousa
 */
public class NodesCoordinates {

    /**
     * Método para obter as coordenadas nos nós de um elemento finito linear
     *
     * @param shapeCoordinates
     * @param nodes
     * @return
     */
    public static double[][] line(double[][] shapeCoordinates, int nodes) {
        double[][] coordinates;

        if (nodes == 3) {
            coordinates = new double[3][2];

            double x1, y1, x2, y2;
            x1 = shapeCoordinates[0][0];
            y1 = shapeCoordinates[0][1];
            x2 = shapeCoordinates[1][0];
            y2 = shapeCoordinates[1][1];

            Point2D.Double point = midPoint(x1, y1, x2, y2);

            coordinates[0][0] = shapeCoordinates[0][0];
            coordinates[0][1] = shapeCoordinates[0][1];
            coordinates[1][0] = point.x;
            coordinates[1][1] = point.y;
            coordinates[2][0] = shapeCoordinates[1][0];
            coordinates[2][1] = shapeCoordinates[1][1];
        } else if (nodes == 4) {
            coordinates = new double[4][2];

            double x1, y1, x2, y2;
            x1 = shapeCoordinates[0][0];
            y1 = shapeCoordinates[0][1];
            x2 = shapeCoordinates[1][0];
            y2 = shapeCoordinates[1][1];

            double horizontal = x2 - x1;
            double vertical = y2 - y1;

            coordinates[0][0] = shapeCoordinates[0][0];
            coordinates[0][1] = shapeCoordinates[0][1];
            coordinates[1][0] = shapeCoordinates[0][0] + horizontal / 3.0;
            coordinates[1][1] = shapeCoordinates[0][1] + vertical / 3.0;
            coordinates[2][0] = shapeCoordinates[1][0] - horizontal / 3.0;
            coordinates[2][1] = shapeCoordinates[1][1] - vertical / 3.0;
            coordinates[3][0] = shapeCoordinates[1][0];
            coordinates[3][1] = shapeCoordinates[1][1];
        } else {
            coordinates = shapeCoordinates;
        }

        return coordinates;
    }

    /**
     * Método para obter as coordenadas nos nós de um elemento finito triangular
     *
     * @param shapeCoordinates
     * @param nodes
     * @return
     */
    public static double[][] triangle(double[][] shapeCoordinates, int nodes) {
        double[][] coordinates;

        if (nodes == 6) {
            coordinates = new double[6][2];

            double x1, y1, x2, y2, x3, y3;
            x1 = shapeCoordinates[0][0];
            y1 = shapeCoordinates[0][1];
            x2 = shapeCoordinates[1][0];
            y2 = shapeCoordinates[1][1];
            x3 = shapeCoordinates[2][0];
            y3 = shapeCoordinates[2][1];

            Point2D.Double pointA = midPoint(x1, y1, x2, y2);
            Point2D.Double pointB = midPoint(x2, y2, x3, y3);
            Point2D.Double pointC = midPoint(x1, y1, x3, y3);

            coordinates[0][0] = shapeCoordinates[0][0];
            coordinates[0][1] = shapeCoordinates[0][1];
            coordinates[1][0] = pointA.x;
            coordinates[1][1] = pointA.y;
            coordinates[2][0] = shapeCoordinates[1][0];
            coordinates[2][1] = shapeCoordinates[1][1];
            coordinates[3][0] = pointB.x;
            coordinates[3][1] = pointB.y;
            coordinates[4][0] = shapeCoordinates[2][0];
            coordinates[4][1] = shapeCoordinates[2][1];
            coordinates[5][0] = pointC.x;
            coordinates[5][1] = pointC.y;
        } else {
            coordinates = shapeCoordinates;
        }

        return coordinates;
    }

    /**
     * Método para obter as coordenadas nos nós de um elemento finito retangular
     *
     * @param shapeCoordinates
     * @param nodes
     * @return
     */
    public static double[][] rectangle(double[][] shapeCoordinates, int nodes) {
        return quadrilateral(shapeCoordinates, nodes);
    }

    /**
     * Método para obter as coordenadas nos nós de um elemento finito quadrilateral
     *
     * @param shapeCoordinates
     * @param nodes
     * @return
     */
    public static double[][] quadrilateral(double[][] shapeCoordinates, int nodes) {
        double[][] coordinates;

        if (nodes == 8) {
            coordinates = new double[8][2];

            double x1, y1, x2, y2, x3, y3, x4, y4;
            x1 = shapeCoordinates[0][0];
            y1 = shapeCoordinates[0][1];
            x2 = shapeCoordinates[1][0];
            y2 = shapeCoordinates[1][1];
            x3 = shapeCoordinates[2][0];
            y3 = shapeCoordinates[2][1];
            x4 = shapeCoordinates[3][0];
            y4 = shapeCoordinates[3][1];

            Point2D.Double pointA = midPoint(x1, y1, x2, y2);
            Point2D.Double pointB = midPoint(x2, y2, x3, y3);
            Point2D.Double pointC = midPoint(x3, y3, x4, y4);
            Point2D.Double pointD = midPoint(x1, y1, x4, y4);

            coordinates[0][0] = shapeCoordinates[0][0];
            coordinates[0][1] = shapeCoordinates[0][1];
            coordinates[1][0] = pointA.x;
            coordinates[1][1] = pointA.y;
            coordinates[2][0] = shapeCoordinates[1][0];
            coordinates[2][1] = shapeCoordinates[1][1];
            coordinates[3][0] = pointB.x;
            coordinates[3][1] = pointB.y;
            coordinates[4][0] = shapeCoordinates[2][0];
            coordinates[4][1] = shapeCoordinates[2][1];
            coordinates[5][0] = pointC.x;
            coordinates[5][1] = pointC.y;
            coordinates[6][0] = shapeCoordinates[3][0];
            coordinates[6][1] = shapeCoordinates[3][1];
            coordinates[7][0] = pointD.x;
            coordinates[7][1] = pointD.y;
        } else if (nodes == 9) {
            coordinates = new double[9][2];

            double x1, y1, x2, y2, x3, y3, x4, y4;
            x1 = shapeCoordinates[0][0];
            y1 = shapeCoordinates[0][1];
            x2 = shapeCoordinates[1][0];
            y2 = shapeCoordinates[1][1];
            x3 = shapeCoordinates[2][0];
            y3 = shapeCoordinates[2][1];
            x4 = shapeCoordinates[3][0];
            y4 = shapeCoordinates[3][1];

            Point2D.Double pointA = midPoint(x1, y1, x2, y2);
            Point2D.Double pointB = midPoint(x2, y2, x3, y3);
            Point2D.Double pointC = midPoint(x3, y3, x4, y4);
            Point2D.Double pointD = midPoint(x1, y1, x4, y4);
            Point2D.Double pointE = AnalyticGeometry.centroid(shapeCoordinates, "Quadrilateral");

            coordinates[0][0] = shapeCoordinates[0][0];
            coordinates[0][1] = shapeCoordinates[0][1];
            coordinates[1][0] = pointA.x;
            coordinates[1][1] = pointA.y;
            coordinates[2][0] = shapeCoordinates[1][0];
            coordinates[2][1] = shapeCoordinates[1][1];
            coordinates[3][0] = pointB.x;
            coordinates[3][1] = pointB.y;
            coordinates[4][0] = shapeCoordinates[2][0];
            coordinates[4][1] = shapeCoordinates[2][1];
            coordinates[5][0] = pointC.x;
            coordinates[5][1] = pointC.y;
            coordinates[6][0] = shapeCoordinates[3][0];
            coordinates[6][1] = shapeCoordinates[3][1];
            coordinates[7][0] = pointD.x;
            coordinates[7][1] = pointD.y;
            coordinates[8][0] = pointE.x;
            coordinates[8][1] = pointE.y;
        } else {
            coordinates = shapeCoordinates;
        }

        return coordinates;
    }
}

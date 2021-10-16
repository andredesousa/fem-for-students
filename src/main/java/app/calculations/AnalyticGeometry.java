/*
 * Esta classe fornece as dimensões e outras propriedades dos elementos finitos
 * Cada método devolve o resultado nas mesmas unidades recebidas
 * Os resultados fornecidos por cada método são sem arredondamentos
 */

package app.calculations;

import static app.matrices.Multiply.multiply;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 *
 * @author André de Sousa
 */
public class AnalyticGeometry {

    /**
     * Método para calcular a distância entre dois pontos
     *
     * @param x1 é a abscissa do primeiro ponto
     * @param y1 é a ordenada do primeiro ponto
     * @param x2 é a abscissa do último ponto
     * @param y2 é a ordenada do último ponto
     * @return
     */
    public static double length(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
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
    public static double length(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    /**
     * Método para calcular a distância entre dois pontos
     *
     * @param pointA contém as coordenadas do primeiro ponto
     * @param pointB contém as coordenadas do segundo ponto
     * @return
     */
    public static double length(Point2D.Double pointA, Point2D.Double pointB) {
        double x1 = pointA.x;
        double y1 = pointA.y;
        double x2 = pointB.x;
        double y2 = pointB.y;

        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    /**
     * Método para calcular a distância entre dois pontos
     *
     * @param coordinates contém as coordenadas de uma reta
     * @return
     */
    public static double length(int[][] coordinates) {
        double x1 = coordinates[0][0];
        double y1 = coordinates[0][1];
        double x2 = coordinates[1][0];
        double y2 = coordinates[1][1];

        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    /**
     * Método para calcular a distância entre dois pontos
     *
     * @param coordinates contém as coordenadas de uma reta
     * @return
     */
    public static double length(double[][] coordinates) {
        double x1 = coordinates[0][0];
        double y1 = coordinates[0][1];
        double x2 = coordinates[1][0];
        double y2 = coordinates[1][1];

        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    /**
     * Método para calcular a distância de um ponto a uma reta
     *
     * @param point contém as coordenadas do ponto
     * @param line contém as coordenadas da reta
     * @return
     */
    public static double distancePointLine(Point2D.Double point, Line2D.Double line) {
        return line.ptSegDist(point);
    }

    /**
     * Método para calcular a área de um polígono
     *
     * @param xPoints contém as abscissas dos pontos do polígono
     * @param yPoints contém as ordenadas dos pontos do polígono
     * @param shape é a forma do polígono a calcular
     * @return
     */
    public static double area(int[] xPoints, int[] yPoints, String shape) {
        double area, a, b, c, d, e, f, s, r;

        switch (shape) {
            case "Triangle":
                a = length(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
                b = length(xPoints[1], yPoints[1], xPoints[2], yPoints[2]);
                c = length(xPoints[0], yPoints[0], xPoints[2], yPoints[2]);
                s = (a + b + c) / 2;

                area = Math.sqrt(s * (s - a) * (s - b) * (s - c));
                break;
            case "Rectangle":
                a = length(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
                b = length(xPoints[1], yPoints[1], xPoints[2], yPoints[2]);

                area = a * b;
                break;
            case "Quadrilateral":
                a = length(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
                b = length(xPoints[1], yPoints[1], xPoints[2], yPoints[2]);
                c = length(xPoints[0], yPoints[0], xPoints[2], yPoints[2]);
                s = (a + b + c) / 2;

                d = length(xPoints[0], yPoints[0], xPoints[2], yPoints[2]);
                e = length(xPoints[2], yPoints[2], xPoints[3], yPoints[3]);
                f = length(xPoints[0], yPoints[0], xPoints[3], yPoints[3]);
                r = (d + e + f) / 2;

                area = Math.sqrt(s * (s - a) * (s - b) * (s - c)) + Math.sqrt(r * (r - d) * (r - e) * (r - f));
                break;
            default:
                area = 0;
                break;
        }

        return area;
    }

    /**
     * Método para calcular a área de um polígono
     *
     * @param coordinates contém as coordenadas do polígono
     * @param shape é a forma do polígono a calcular
     * @return
     */
    public static double area(double[][] coordinates, String shape) {
        double area, a, b, c, d, e, f, s, r;

        switch (shape) {
            case "Triangle":
                a = length(coordinates[0][0], coordinates[0][1], coordinates[1][0], coordinates[1][1]);
                b = length(coordinates[1][0], coordinates[1][1], coordinates[2][0], coordinates[2][1]);
                c = length(coordinates[0][0], coordinates[0][1], coordinates[2][0], coordinates[2][1]);
                s = (a + b + c) / 2;

                area = Math.sqrt(s * (s - a) * (s - b) * (s - c));
                break;
            case "Rectangle":
                a = length(coordinates[0][0], coordinates[0][1], coordinates[1][0], coordinates[1][1]);
                b = length(coordinates[1][0], coordinates[1][1], coordinates[2][0], coordinates[2][1]);

                area = a * b;
                break;
            case "Quadrilateral":
                a = length(coordinates[0][0], coordinates[0][1], coordinates[1][0], coordinates[1][1]);
                b = length(coordinates[1][0], coordinates[1][1], coordinates[2][0], coordinates[2][1]);
                c = length(coordinates[0][0], coordinates[0][1], coordinates[2][0], coordinates[2][1]);
                s = (a + b + c) / 2;

                d = length(coordinates[0][0], coordinates[0][1], coordinates[2][0], coordinates[2][1]);
                e = length(coordinates[2][0], coordinates[2][1], coordinates[3][0], coordinates[3][1]);
                f = length(coordinates[0][0], coordinates[0][1], coordinates[3][0], coordinates[3][1]);
                r = (d + e + f) / 2;

                area = Math.sqrt(s * (s - a) * (s - b) * (s - c)) + Math.sqrt(r * (r - d) * (r - e) * (r - f));
                break;
            default:
                area = 0;
                break;
        }

        return area;
    }

    /**
     * Método para calcular as dimensões de um triângulo
     *
     * A primeira coluna contém a largura do triângulo
     * A segunda coluna contém a altura do triângulo
     *
     * @param coordinates contém as coordenadas do triângulo
     * @return
     */
    public static double[] triangleDimensions(double[][] coordinates) {
        double a, b, x1, y1, x2, y2;

        x1 = coordinates[0][0];
        y1 = coordinates[0][1];
        x2 = coordinates[1][0];
        y2 = coordinates[1][1];

        Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);
        Point2D.Double point = new Point2D.Double(coordinates[2][0], coordinates[2][1]);

        a = length(x1, y1, x2, y2);
        b = distancePointLine(point, line);

        double[] dimensions = { a, b };

        return dimensions;
    }

    /**
     * Método para calcular as dimensões de um retângulo
     *
     * A primeira coluna contém o comprimento do retângulo
     * A segunda coluna contém a altura do retângulo
     *
     * @param xPoints contém as abscissas dos pontos do retângulo
     * @param yPoints contém as ordenadas dos pontos do retângulo
     * @return
     */
    public static double[] rectangleDimensions(int[] xPoints, int[] yPoints) {
        double a, b;

        a = length(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
        b = length(xPoints[1], yPoints[1], xPoints[2], yPoints[2]);

        double[] dimensions = { a, b };

        return dimensions;
    }

    /**
     * Método para calcular as dimensões de um retângulo
     *
     * A primeira coluna contém o comprimento do retângulo
     * A segunda coluna contém a altura do retângulo
     *
     * @param coordinates contém as coordenadas do retângulo
     * @return
     */
    public static double[] rectangleDimensions(double[][] coordinates) {
        double a, b;

        a = length(coordinates[0][0], coordinates[0][1], coordinates[1][0], coordinates[1][1]);
        b = length(coordinates[1][0], coordinates[1][1], coordinates[2][0], coordinates[2][1]);

        double[] dimensions = { a, b };

        return dimensions;
    }

    /**
     * Método para calcular o baricentro de um polígono
     *
     * @param xPoints contém as abscissas dos pontos do polígono
     * @param yPoints contém as ordenadas dos pontos do polígono
     * @param shape é a forma do polígono a calcular
     * @return
     */
    public static Point2D.Double centroid(int[] xPoints, int[] yPoints, String shape) {
        double x, y;

        switch (shape) {
            case "Triangle":
                x = (xPoints[0] + xPoints[1] + xPoints[2]) / 3.0;
                y = (yPoints[0] + yPoints[1] + yPoints[2]) / 3.0;
                break;
            case "Rectangle":
                x = (xPoints[0] + xPoints[1] + xPoints[2] + xPoints[3]) / 4.0;
                y = (yPoints[0] + yPoints[1] + yPoints[2] + yPoints[3]) / 4.0;
                break;
            case "Quadrilateral":
                x = (xPoints[0] + xPoints[1] + xPoints[2] + xPoints[3]) / 4.0;
                y = (yPoints[0] + yPoints[1] + yPoints[2] + yPoints[3]) / 4.0;
                break;
            default:
                x = 0;
                y = 0;
                break;
        }

        return new Point2D.Double(x, y);
    }

    /**
     * Método para calcular o baricentro de um polígono
     *
     * @param coordinates contém as coordenadas do polígono
     * @param shape é a forma do polígono a calcular
     * @return
     */
    public static Point2D.Double centroid(double[][] coordinates, String shape) {
        double x, y;

        switch (shape) {
            case "Triangle":
                x = (coordinates[0][0] + coordinates[1][0] + coordinates[2][0]) / 3.0;
                y = (coordinates[0][1] + coordinates[1][1] + coordinates[2][1]) / 3.0;
                break;
            case "Rectangle":
                x = (coordinates[0][0] + coordinates[1][0] + coordinates[2][0] + coordinates[3][0]) / 4.0;
                y = (coordinates[0][1] + coordinates[1][1] + coordinates[2][1] + coordinates[3][1]) / 4.0;
                break;
            case "Quadrilateral":
                x = (coordinates[0][0] + coordinates[1][0] + coordinates[2][0] + coordinates[3][0]) / 4.0;
                y = (coordinates[0][1] + coordinates[1][1] + coordinates[2][1] + coordinates[3][1]) / 4.0;
                break;
            default:
                x = 0;
                y = 0;
                break;
        }

        return new Point2D.Double(x, y);
    }

    /**
     * Método para calcular o ponto médio de uma reta
     *
     * @param x1 é a abscissa do primeiro ponto da reta
     * @param y1 é a ordenada do primeiro ponto da reta
     * @param x2 é a abscissa do último ponto da reta
     * @param y2 é a ordenada do último ponto da reta
     * @return
     */
    public static Point2D.Double midPoint(int x1, int y1, int x2, int y2) {
        double horizontal = (x2 - x1) / 2.0;
        double vertical = (y2 - y1) / 2.0;

        return new Point2D.Double(x1 + horizontal, y1 + vertical);
    }

    /**
     * Método para calcular o ponto médio de uma reta
     *
     * @param x1 é a abscissa do primeiro ponto da reta
     * @param y1 é a ordenada do primeiro ponto da reta
     * @param x2 é a abscissa do último ponto da reta
     * @param y2 é a ordenada do último ponto da reta
     * @return
     */
    public static Point2D.Double midPoint(double x1, double y1, double x2, double y2) {
        double horizontal = (x2 - x1) / 2.0;
        double vertical = (y2 - y1) / 2.0;

        return new Point2D.Double(x1 + horizontal, y1 + vertical);
    }

    /**
     * Método para calcular o ponto de interseção de duas retas
     *
     * @param lineA contém a informação relativa à reta A
     * @param lineB contém a informação relativa à reta B
     * @return
     */
    public static Point2D.Double intersectionPoint(Line2D.Double lineA, Line2D.Double lineB) {
        Point2D.Double point;

        double a1, a2, b1, b2, x, y;

        if (lineA.x2 - lineA.x1 != 0 && lineB.x2 - lineB.x1 != 0) {
            //Parâmetros da equação da reta A
            a1 = (lineA.y2 - lineA.y1) / (lineA.x2 - lineA.x1);
            b1 = lineA.y1 - a1 * lineA.x1;

            //Parâmetros da equação da reta B
            a2 = (lineB.y2 - lineB.y1) / (lineB.x2 - lineB.x1);
            b2 = lineB.y1 - a2 * lineB.x1;

            //Cálculo do ponto de interseção das duas retas
            if (a1 != a2) {
                x = (b2 - b1) / (a1 - a2);
                y = a1 * x + b1;

                point = new Point2D.Double(x, y);
            } else {
                point = null;
            }
        } else {
            if (lineA.x2 - lineA.x1 == 0 && lineB.x2 - lineB.x1 != 0) {
                //A reta A é vertical e a reta B é inclinada
                x = lineA.x1;
                y = lineB.y1 + (lineB.y2 - lineB.y1) / 2.0;

                point = new Point2D.Double(x, y);
            } else if (lineA.x2 - lineA.x1 != 0 && lineB.x2 - lineB.x1 == 0) {
                //A reta B é vertical e a reta A é inclinada
                x = lineB.x1;
                y = lineA.y1 + (lineA.y2 - lineA.y1) / 2.0;

                point = new Point2D.Double(x, y);
            } else {
                point = null;
            }
        }

        return point;
    }

    /**
     * Método para calcular o ângulo de inclinação de uma linha
     *
     * @param shapeCoordinates são as coordenadas da linha
     * @return
     */
    public static double lineInclination(int[][] shapeCoordinates) {
        double x1, y1, x2, y2;
        x1 = shapeCoordinates[0][0];
        y1 = shapeCoordinates[0][1];
        x2 = shapeCoordinates[1][0];
        y2 = shapeCoordinates[1][1];

        if ((x2 - x1) == 0) {
            if ((y2 - y1) > 0) {
                return Math.PI / 2;
            } else {
                return -Math.PI / 2;
            }
        } else {
            double inclination = (y2 - y1) / (x2 - x1);
            return Math.atan(inclination);
        }
    }

    /**
     * Método para calcular o ângulo de inclinação de uma linha
     *
     * @param x1 é a abscissa do primeiro ponto
     * @param y1 é a ordenada do primeiro ponto
     * @param x2 é a abscissa do último ponto
     * @param y2 é a ordenada do último ponto
     * @return
     */
    public static double lineInclination(int x1, int y1, int x2, int y2) {
        if ((x2 - x1) == 0) {
            if ((y2 - y1) > 0) {
                return Math.PI / 2;
            } else {
                return -Math.PI / 2;
            }
        } else {
            double a = (y2 - y1);
            double b = (x2 - x1);
            return Math.atan(a / b);
        }
    }

    /**
     * Método para calcular o ângulo de inclinação de uma linha
     *
     * @param x1 é a abscissa do primeiro ponto
     * @param y1 é a ordenada do primeiro ponto
     * @param x2 é a abscissa do último ponto
     * @param y2 é a ordenada do último ponto
     * @return
     */
    public static double lineInclination(double x1, double y1, double x2, double y2) {
        if ((x2 - x1) == 0) {
            if ((y2 - y1) > 0) {
                return Math.PI / 2;
            } else {
                return -Math.PI / 2;
            }
        } else {
            double inclination = (y2 - y1) / (x2 - x1);
            return Math.atan(inclination);
        }
    }

    /**
     * Método para realizar a transformação de coordenadas de uma rotação θ
     *
     * @param coordinates contém as oordenadas da linha a rodar
     * @param angle é o valor do ângulo da rotação
     * @return
     */
    public static double[][] coordinateTransformations(double[][] coordinates, double angle) {
        double[][] matrixT = {
            { cos(angle), -sin(angle), 0, 0 },
            { sin(angle), cos(angle), 0, 0 },
            { 0, 0, cos(angle), -sin(angle) },
            { 0, 0, sin(angle), cos(angle) },
        };

        return multiply(matrixT, coordinates);
    }
}

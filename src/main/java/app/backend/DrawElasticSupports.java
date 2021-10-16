/*
 * Esta classe cria e desenha apoios elásticos
 * A classe permite desenhar três tipos de molas que representam os apoios
 * As molas são desenhados a partir das coordenadas dos nós
 */

package app.backend;

import app.calculations.AnalyticGeometry;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 *
 * @author André de Sousa
 */
public class DrawElasticSupports implements Serializable {

    public String name;
    public Point point;
    public double[] stiffness;
    public boolean selected;

    /**
     * Método construtor da classe DrawElasticSupports
     *
     * @param name é o nome do apoio elástico
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     * @param stiffness contém os valores de rigidez das molas
     */
    public DrawElasticSupports(String name, int xPoint, int yPoint, double[] stiffness) {
        this.name = name;
        this.point = new Point(xPoint, yPoint);
        this.stiffness = stiffness;
        this.selected = false;
    }

    /**
     * Método construtor da classe DrawElasticSupports
     *
     * @param name é o nome do apoio elástico
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     * @param stiffness contém os valores de rigidez das molas
     * @param selected é o estado do apoio estrutural a desenhar
     */
    public DrawElasticSupports(String name, int xPoint, int yPoint, double[] stiffness, boolean selected) {
        this.name = name;
        this.point = new Point(xPoint, yPoint);
        this.stiffness = stiffness;
        this.selected = selected;
    }

    /**
     * Método para alterar o estado do apoio elástico
     *
     * @param select informa se o apoio estrutural está selecionado
     */
    public void select(boolean select) {
        selected = select;
    }

    /**
     * Método para desenhar o apoio elástico
     *
     * @param support contém a informação gráfica do apoio elástico
     */
    public void draw(Graphics2D support) {
        support.setPaint(new java.awt.Color(0, 0, 0));

        if (stiffness[0] > 0.0) {
            drawSpringKx(support);
        }
        if (stiffness[1] > 0.0) {
            drawSpringKy(support);
        }
        if (stiffness[2] > 0.0) {
            drawSpringKz(support);
        }
    }

    /**
     * Método para saber se o apoio elástico contém o ponto
     *
     * @param point contém as coordenadas do rato
     * @return
     */
    public boolean contains(Point point) {
        boolean contains;

        contains = false;
        if (point.x == this.point.x && point.y == this.point.y) {
            contains = true;
        }

        return contains;
    }

    /**
     * Método para obter uma matriz com as coordenadas do apoio elástico
     *
     * @return
     */
    public int[][] getCoordinates() {
        int[][] coordinates = new int[1][2];

        coordinates[0][0] = (int) point.x;
        coordinates[0][1] = (int) point.y;

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
     * Método para mover o apoio elástico na relação fornecida
     *
     * @param deltaX é a distância a mover na horizontal
     * @param deltaY é a distância a mover na vertical
     */
    public void moveSupport(int deltaX, int deltaY) {
        point.translate(deltaX, deltaY);
    }

    /**
     * Método para desenhar uma mola horizontal
     *
     * @param support contém a informação gráfica do apoio elástico
     */
    private void drawSpringKx(Graphics2D support) {
        int x = point.x;
        int y = point.y;

        //Instruções para desenhar a forma da mola
        support.draw(new Line2D.Float(x, y, x - 2, y - 5));
        support.draw(new Line2D.Float(x - 2, y - 5, x - 6, y + 5));
        support.draw(new Line2D.Float(x - 6, y + 5, x - 10, y - 5));
        support.draw(new Line2D.Float(x - 10, y - 5, x - 14, y + 5));
        support.draw(new Line2D.Float(x - 14, y + 5, x - 18, y - 5));
        support.draw(new Line2D.Float(x - 18, y - 5, x - 20, y));

        //Instruções para desenhar o apoio da mola
        support.draw(new Line2D.Float(x - 20, y - 10, x - 20, y + 10));
        support.draw(new Line2D.Float(x - 20, y - 10, x - 23, y - 13));
        support.draw(new Line2D.Float(x - 20, y - 5, x - 23, y - 8));
        support.draw(new Line2D.Float(x - 20, y, x - 23, y - 3));
        support.draw(new Line2D.Float(x - 20, y + 5, x - 23, y + 2));
        support.draw(new Line2D.Float(x - 20, y + 10, x - 23, y + 7));
    }

    /**
     * Método para desenhar uma mola vertical
     *
     * @param support contém a informação gráfica do apoio elástico
     */
    private void drawSpringKy(Graphics2D support) {
        int x = point.x;
        int y = point.y;

        //Instruções para desenhar a forma da mola
        support.draw(new Line2D.Float(x, y, x - 5, y + 2));
        support.draw(new Line2D.Float(x - 5, y + 2, x + 5, y + 6));
        support.draw(new Line2D.Float(x + 5, y + 6, x - 5, y + 10));
        support.draw(new Line2D.Float(x - 5, y + 10, x + 5, y + 14));
        support.draw(new Line2D.Float(x + 5, y + 14, x - 5, y + 18));
        support.draw(new Line2D.Float(x - 5, y + 18, x, y + 20));

        //Instruções para desenhar o apoio da mola
        support.draw(new Line2D.Float(x - 10, y + 20, x + 10, y + 20));
        support.draw(new Line2D.Float(x - 10, y + 20, x - 13, y + 23));
        support.draw(new Line2D.Float(x - 5, y + 20, x - 8, y + 23));
        support.draw(new Line2D.Float(x, y + 20, x - 3, y + 23));
        support.draw(new Line2D.Float(x + 5, y + 20, x + 2, y + 23));
        support.draw(new Line2D.Float(x + 10, y + 20, x + 7, y + 23));
    }

    /**
     * Método para desenhar uma mola para rotações
     *
     * @param support contém a informação gráfica do apoio elástico
     */
    private void drawSpringKz(Graphics2D support) {
        //Instruções para desenhar a forma da mola

        //Angle of paremetric equations for spiral
        double theta;

        //End points of latest line drawn
        Point2D.Double lastPoint;
        Point2D.Double newPoint;

        //Center of current spiral
        Point2D.Double center;

        //How many points to draw per circle
        double STEPS_PER_ROTATION = 25.0;

        //Amount to add to angle at each step
        double increment = 2.0 * Math.PI / STEPS_PER_ROTATION;

        //Use position of mouse press to determine center of spiral
        center = new Point2D.Double(point.x, point.y);
        lastPoint = center;
        theta = increment;

        while (theta < 4.2 * Math.PI) {
            newPoint = new Point2D.Double(center.getX() + theta * Math.cos(theta) * 1.5, center.getY() + theta * Math.sin(theta) * 1.5);
            support.draw(new Line2D.Float(lastPoint, newPoint));

            theta = theta + increment;
            lastPoint = newPoint;
        }

        //Instruções para desenhar o apoio da mola
        int x = point.x + 18;
        int y = point.y;

        support.draw(new Line2D.Float(x - 10, y + 10, x + 10, y + 10));
        support.draw(new Line2D.Float(x - 10, y + 10, x - 13, y + 13));
        support.draw(new Line2D.Float(x - 5, y + 10, x - 8, y + 13));
        support.draw(new Line2D.Float(x, y + 10, x - 3, y + 13));
        support.draw(new Line2D.Float(x + 5, y + 10, x + 2, y + 13));
        support.draw(new Line2D.Float(x + 10, y + 10, x + 7, y + 13));
    }
}

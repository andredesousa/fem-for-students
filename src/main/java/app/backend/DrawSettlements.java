/*
 * Esta classe cria e desenha assentamentos de apoio
 * A classe permite desenhar três tipos de assentamentos de apoio
 * As representações são desenhadas a partir das coordenadas dos nós
 */

package app.backend;

import app.calculations.AnalyticGeometry;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.Serializable;

/**
 *
 * @author André de Sousa
 */
public class DrawSettlements implements Serializable {

    public String name;
    public Point point;
    public double[] displacements;
    public boolean selected;

    /**
     * Método construtor da classe DrawSettlements
     *
     * @param name é o nome do assentamento de apoio
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     * @param displacements contém os valores do assentamento de apoio
     */
    public DrawSettlements(String name, int xPoint, int yPoint, double[] displacements) {
        this.name = name;
        this.point = new Point(xPoint, yPoint);
        this.displacements = displacements;
        this.selected = false;
    }

    /**
     * Método construtor da classe DrawSettlements
     *
     * @param name é o nome do assentamento de apoio
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     * @param displacements contém os valores do assentamento de apoio
     * @param selected é o estado do apoio estrutural a desenhar
     */
    public DrawSettlements(String name, int xPoint, int yPoint, double[] displacements, boolean selected) {
        this.name = name;
        this.point = new Point(xPoint, yPoint);
        this.displacements = displacements;
        this.selected = selected;
    }

    /**
     * Método para alterar o estado do assentamento de apoio
     *
     * @param select informa se o assentamento de apoio está selecionado
     */
    public void select(boolean select) {
        selected = select;
    }

    /**
     * Método para desenhar o assentamento de apoio
     *
     * @param support contém a informação gráfica do assentamento de apoio
     */
    public void draw(Graphics2D support) {
        support.setPaint(Color.BLUE);

        if (displacements[0] != 0.0) {
            drawDisplacementDx(support);
        }
        if (displacements[1] != 0.0) {
            drawDisplacementDy(support);
        }
        if (displacements[2] != 0.0) {
            drawRotationRz(support);
        }
    }

    /**
     * Método para saber se o assentamento de apoio contém o ponto
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
     * Método para obter uma matriz com as coordenadas do assentamento de apoio
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
     * Método para mover o assentamento de apoio na relação fornecida
     *
     * @param deltaX é a distância a mover na horizontal
     * @param deltaY é a distância a mover na vertical
     */
    public void moveSettlement(int deltaX, int deltaY) {
        point.translate(deltaX, deltaY);
    }

    /**
     * Método para desenhar um deslocamento horizontal
     *
     * @param support contém a informação gráfica do assentamento de apoio
     */
    private void drawDisplacementDx(Graphics2D support) {
        int x = point.x - 35;
        int y = point.y;

        //Deslocamento horizontal positivo
        if (displacements[0] > 0.0) {
            support.draw(new Ellipse2D.Float(x + 13, y - 2, 4, 4));
            support.draw(new Line2D.Double(x, y, x + 25, y));
            support.draw(new Line2D.Double(x + 25, y, x + 20, y - 5));
            support.draw(new Line2D.Double(x + 25, y, x + 20, y + 5));
        }

        //Deslocamento horizontal negativo
        if (displacements[0] < 0.0) {
            support.draw(new Ellipse2D.Float(x + 13, y - 2, 4, 4));
            support.draw(new Line2D.Double(x, y, x + 25, y));
            support.draw(new Line2D.Double(x, y, x + 5, y - 5));
            support.draw(new Line2D.Double(x, y, x + 5, y + 5));
        }
    }

    /**
     * Método para desenhar um deslocamento vertical
     *
     * @param support contém a informação gráfica do assentamento de apoio
     */
    private void drawDisplacementDy(Graphics2D support) {
        int x = point.x;
        int y = point.y + 35;

        //Deslocamento vertical positivo
        if (displacements[1] > 0.0) {
            support.draw(new Ellipse2D.Float(x - 2, y - 13, 4, 4));
            support.draw(new Line2D.Double(x, y, x, y - 25));
            support.draw(new Line2D.Double(x, y, x - 5, y - 5));
            support.draw(new Line2D.Double(x, y, x + 5, y - 5));
        }

        //Deslocamento vertical negativo
        if (displacements[1] < 0.0) {
            support.draw(new Ellipse2D.Float(x - 2, y - 13, 4, 4));
            support.draw(new Line2D.Double(x, y, x, y - 25));
            support.draw(new Line2D.Double(x, y - 25, x - 5, y - 20));
            support.draw(new Line2D.Double(x, y - 25, x + 5, y - 20));
        }
    }

    /**
     * Método para desenhar uma rotação
     *
     * @param support contém a informação gráfica do assentamento de apoio
     */
    private void drawRotationRz(Graphics2D support) {
        int x = point.x - 35;
        int y = point.y - 15;

        //Desenho de uma rotação positiva
        if (displacements[2] > 0.0) {
            support.draw(new Ellipse2D.Float(x + 5, y + 40, 4, 4));
            support.draw(new Line2D.Double(x, y + 25, x - 5, y + 30));
            support.draw(new Line2D.Double(x, y + 25, x + 5, y + 30));
            support.draw(new Arc2D.Double(x, y, 50, 50, 180, 90, Arc2D.OPEN));
        }

        //Desenho de uma rotação negativa
        if (displacements[2] < 0.0) {
            support.draw(new Ellipse2D.Float(x + 5, y + 40, 4, 4));
            support.draw(new Line2D.Double(x + 25, y + 50, x + 20, y + 45));
            support.draw(new Line2D.Double(x + 25, y + 50, x + 20, y + 55));
            support.draw(new Arc2D.Double(x, y, 50, 50, 180, 90, Arc2D.OPEN));
        }
    }
}

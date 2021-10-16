/*
 * Esta classe cria e desenha apoios estruturais
 * A classe permite desenhar quatro tipos de apoios estruturais
 * Os apoios são desenhados a partir das coordenadas dos nós
 */

package app.backend;

import app.calculations.AnalyticGeometry;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.io.Serializable;

/**
 *
 * @author André de Sousa
 */
public class DrawSupports implements Serializable {

    public Point point;
    public String support;
    public boolean selected;

    /**
     * Método construtor da classe DrawSupports
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     * @param support é o tipo de apoio estrutural a desenhar
     */
    public DrawSupports(int xPoint, int yPoint, String support) {
        this.point = new Point(xPoint, yPoint);
        this.support = support;
        this.selected = false;
    }

    /**
     * Método construtor da classe DrawSupports
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     * @param support é o tipo de apoio estrutural a desenhar
     * @param selected é o estado do apoio estrutural a desenhar
     */
    public DrawSupports(int xPoint, int yPoint, String support, boolean selected) {
        this.point = new Point(xPoint, yPoint);
        this.support = support;
        this.selected = selected;
    }

    /**
     * Método para alterar o estado do apoio estrutural
     *
     * @param select informa se o apoio estrutural está selecionado
     */
    public void select(boolean select) {
        selected = select;
    }

    /**
     * Método para desenhar o apoio estrutural
     *
     * @param support contém a informação gráfica do apoio estrutural
     */
    public void draw(Graphics2D support) {
        support.setPaint(new java.awt.Color(0, 0, 0));

        if ("Vertical Support".equals(this.support) || "Horizontal Support".equals(this.support)) {
            drawSimpleSupport(support);
        }
        if ("Pinned Support".equals(this.support)) {
            drawPinnedSupport(support);
        }
        if ("Horizontal Slider".equals(this.support) || "Vertical Slider".equals(this.support)) {
            drawSliderSupport(support);
        }
        if ("Fixed Support".equals(this.support)) {
            drawFixedSupport(support);
        }
    }

    /**
     * Método para desenhar o apoio estrutural
     *
     * @param type é o tipo de modelo estrutural
     * @param support contém a informação gráfica do apoio estrutural
     */
    public void draw(String type, Graphics2D support) {
        support.setPaint(new java.awt.Color(0, 0, 0));

        if ("Grids".equals(type) || "Slabs".equals(type)) {
            drawSupports_TopView(support);
        } else {
            draw(support);
        }
    }

    /**
     * Método para saber se o apoio estrutural contém o ponto
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
     * Método para obter uma matriz com as coordenadas do apoio estrutural
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
     * Método para mover o apoio estrutural na relação fornecida
     *
     * @param deltaX é a distância a mover na horizontal
     * @param deltaY é a distância a mover na vertical
     */
    public void moveSupport(int deltaX, int deltaY) {
        point.translate(deltaX, deltaY);
    }

    /**
     * Método para desenhar um apoio simples
     *
     * @param support contém a informação gráfica do apoio estrutural
     */
    private void drawSimpleSupport(Graphics2D support) {
        int x = point.x;
        int y = point.y;

        //Desenhar um apoio simples vertical
        if ("Vertical Support".equals(this.support)) {
            int[] xPoints = { x, x - 10, x + 10 };
            int[] yPoints = { y, y + 10, y + 10 };

            support.fill(new Polygon(xPoints, yPoints, 3));
            support.draw(new Polygon(xPoints, yPoints, 3));
            support.draw(new Line2D.Float(x - 10, y + 13, x + 10, y + 13));
        }

        //desenhar um apoio simples horizontal
        if ("Horizontal Support".equals(this.support)) {
            int[] xPoints = { x, x - 10, x - 10 };
            int[] yPoints = { y, y + 10, y - 10 };

            support.fill(new Polygon(xPoints, yPoints, 3));
            support.draw(new Polygon(xPoints, yPoints, 3));
            support.draw(new Line2D.Float(x - 13, y + 10, x - 13, y - 10));
        }
    }

    /**
     * Método para desenhar um apoio duplo
     *
     * @param support contém a informação gráfica do apoio estrutural
     */
    private void drawPinnedSupport(Graphics2D support) {
        int x = point.x;
        int y = point.y;

        //Desenhar um apoio duplo
        if ("Pinned Support".equals(this.support)) {
            int[] xPoints = { x, x - 10, x + 10 };
            int[] yPoints = { y, y + 10, y + 10 };

            support.fill(new Polygon(xPoints, yPoints, 3));
            support.draw(new Polygon(xPoints, yPoints, 3));
            support.draw(new Line2D.Float(x - 10, y + 10, x - 13, y + 13));
            support.draw(new Line2D.Float(x - 5, y + 10, x - 8, y + 13));
            support.draw(new Line2D.Float(x, y + 10, x - 3, y + 13));
            support.draw(new Line2D.Float(x + 5, y + 10, x + 2, y + 13));
            support.draw(new Line2D.Float(x + 10, y + 10, x + 7, y + 13));
        }
    }

    /**
     * Método para desenhar um apoio deslizante
     *
     * @param support contém a informação gráfica do apoio estrutural
     */
    private void drawSliderSupport(Graphics2D support) {
        int x = point.x;
        int y = point.y;

        //Desenhar um encastramento deslizante horizonatal
        if ("Horizontal Slider".equals(this.support)) {
            int[] xPoints = { x - 10, x - 10, x + 10, x + 10 };
            int[] yPoints = { y, y + 7, y + 7, y };

            support.fill(new Polygon(xPoints, yPoints, 4));
            support.draw(new Polygon(xPoints, yPoints, 4));
            support.draw(new Line2D.Float(x - 10, y + 10, x - 13, y + 13));
            support.draw(new Line2D.Float(x - 5, y + 10, x - 8, y + 13));
            support.draw(new Line2D.Float(x, y + 10, x - 3, y + 13));
            support.draw(new Line2D.Float(x + 5, y + 10, x + 2, y + 13));
            support.draw(new Line2D.Float(x + 10, y + 10, x + 7, y + 13));
            support.draw(new Line2D.Float(x - 10, y + 10, x + 10, y + 10));
        }

        //Desenhar um encastramento deslizante vertical
        if ("Vertical Slider".equals(this.support)) {
            int[] xPoints = { x, x - 7, x - 7, x };
            int[] yPoints = { y - 10, y - 10, y + 10, y + 10 };

            support.fill(new Polygon(xPoints, yPoints, 4));
            support.draw(new Polygon(xPoints, yPoints, 4));
            support.draw(new Line2D.Float(x - 10, y - 10, x - 13, y - 13));
            support.draw(new Line2D.Float(x - 10, y - 5, x - 13, y - 8));
            support.draw(new Line2D.Float(x - 10, y, x - 13, y - 3));
            support.draw(new Line2D.Float(x - 10, y + 5, x - 13, y + 2));
            support.draw(new Line2D.Float(x - 10, y + 10, x - 13, y + 7));
            support.draw(new Line2D.Float(x - 10, y - 10, x - 10, y + 10));
        }
    }

    /**
     * Método para desenhar um apoio fixo
     *
     * @param support contém a informação gráfica do apoio estrutural
     */
    private void drawFixedSupport(Graphics2D support) {
        int x = point.x;
        int y = point.y;

        //Desenhar um encastramento
        if ("Fixed Support".equals(this.support)) {
            int[] xPoints = { x - 10, x - 10, x + 10, x + 10 };
            int[] yPoints = { y, y + 10, y + 10, y };

            support.fill(new Polygon(xPoints, yPoints, 4));
            support.draw(new Polygon(xPoints, yPoints, 4));
            support.draw(new Line2D.Float(x - 10, y + 10, x - 13, y + 13));
            support.draw(new Line2D.Float(x - 5, y + 10, x - 8, y + 13));
            support.draw(new Line2D.Float(x, y + 10, x - 3, y + 13));
            support.draw(new Line2D.Float(x + 5, y + 10, x + 2, y + 13));
            support.draw(new Line2D.Float(x + 10, y + 10, x + 7, y + 13));
        }
    }

    /**
     * Método para desenhar os apoios vistos de cima
     *
     * @param support contém a informação gráfica do apoio estrutural
     */
    private void drawSupports_TopView(Graphics2D support) {
        int x = point.x;
        int y = point.y;

        //Desenhar um apoio simples vertical
        if ("Vertical Support".equals(this.support)) {
            int[] xPoints = { x - 7, x - 7, x + 7, x + 7 };
            int[] yPoints = { y - 7, y + 7, y + 7, y - 7 };

            support.fill(new Polygon(xPoints, yPoints, 4));
            support.draw(new Polygon(xPoints, yPoints, 4));
            support.draw(new Line2D.Float(x - 7, y + 10, x + 7, y + 10));
            support.draw(new Line2D.Float(x - 10, y + 7, x - 10, y - 7));
            support.draw(new Line2D.Float(x + 10, y + 7, x + 10, y - 7));
            support.draw(new Line2D.Float(x - 7, y - 10, x + 7, y - 10));
        }

        //Desenhar um encastramento deslizante horizonatal
        if ("Horizontal Slider".equals(this.support)) {
            int[] xPoints = { x - 7, x - 7, x + 7, x + 7 };
            int[] yPoints = { y - 7, y + 7, y + 7, y - 7 };

            support.fill(new Polygon(xPoints, yPoints, 4));
            support.draw(new Polygon(xPoints, yPoints, 4));

            support.draw(new Line2D.Float(x - 7, y + 10, x + 7, y + 10));
            support.draw(new Line2D.Float(x - 7, y - 10, x + 7, y - 10));
            support.draw(new Line2D.Float(x + 10, y + 7, x + 10, y - 7));
            support.draw(new Line2D.Float(x - 10, y + 7, x - 10, y - 7));

            support.draw(new Line2D.Float(x - 7, y + 10, x - 10, y + 13));
            support.draw(new Line2D.Float(x, y + 10, x - 3, y + 13));
            support.draw(new Line2D.Float(x + 7, y + 10, x + 4, y + 13));

            support.draw(new Line2D.Float(x - 7, y - 10, x - 4, y - 13));
            support.draw(new Line2D.Float(x, y - 10, x + 3, y - 13));
            support.draw(new Line2D.Float(x + 7, y - 10, x + 10, y - 13));

            support.draw(new Line2D.Float(x - 10, y - 7, x - 13, y - 10));
            support.draw(new Line2D.Float(x - 10, y, x - 13, y - 3));
            support.draw(new Line2D.Float(x - 10, y + 7, x - 13, y + 4));

            support.draw(new Line2D.Float(x + 10, y - 7, x + 13, y - 4));
            support.draw(new Line2D.Float(x + 10, y, x + 13, y + 3));
            support.draw(new Line2D.Float(x + 10, y + 7, x + 13, y + 10));
        }
    }
}

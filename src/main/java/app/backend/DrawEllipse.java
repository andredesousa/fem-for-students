/*
 * Este classe desenha um círculo ou uma elipse bidimensional
 * Se a largura for igual à altura é desenhado um círculo
 * Se a largura for diferente da altura é desenhada uma elipse
 */

package app.backend;

import app.calculations.AnalyticGeometry;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.Serializable;

/**
 *
 * @author André de Sousa
 */
public class DrawEllipse implements Serializable {

    public Ellipse2D drawEllipse;
    public boolean selected;
    public Point point;

    //Cores para desenhar a elipse nos seus diferentes estados
    private Color defaultColor = new Color(0, 0, 0);
    private Color selectedColor = new Color(99, 180, 251);

    /**
     * Método construtor da classe DrawEllipse
     *
     * @param x1 é a abscissa do primeiro ponto da figura geométrica
     * @param y1 é a ordenada do primeiro ponto da figura geométrica
     * @param width é a largura da figura geométrica
     * @param height é a altura da figura geométrica
     */
    public DrawEllipse(int x1, int y1, int width, int height) {
        drawEllipse = new Ellipse2D.Float(x1 - width / 2, y1 - height / 2, width, height);
        point = new Point(x1, y1);
        selected = false;
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
     * Método para alterar o estado da figura geométrica
     *
     * @param select informa se a figura geométrica está selecionada
     */
    public void select(boolean select) {
        selected = select;
    }

    /**
     * Método para desenhar a figura geométrica
     *
     * @param ellipse2D contém a informação da figura geométrica a desenhar
     */
    public void draw(Graphics2D ellipse2D) {
        AlphaComposite transparency = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.5);
        AlphaComposite opaque = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 1);

        if (selected) {
            ellipse2D.setPaint(selectedColor);
            ellipse2D.setComposite(transparency);
            ellipse2D.fill(drawEllipse);
            ellipse2D.setComposite(opaque);
            ellipse2D.setPaint(defaultColor);
        } else {
            ellipse2D.setPaint(defaultColor);
        }

        ellipse2D.draw(drawEllipse);
    }

    /**
     * Método para desenhar um círculo com um ponto ao centro
     *
     * @param ellipse2D contém a informação da figura geométrica a desenhar
     */
    public void drawEllipseAndPoint(Graphics2D ellipse2D) {
        AlphaComposite transparency = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.5);
        AlphaComposite opaque = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 1);

        if (selected) {
            ellipse2D.setPaint(selectedColor);
            ellipse2D.setComposite(transparency);
            ellipse2D.fill(drawEllipse);
            ellipse2D.setComposite(opaque);
            ellipse2D.setPaint(defaultColor);
        } else {
            ellipse2D.setPaint(defaultColor);
        }

        ellipse2D.draw(drawEllipse);
        ellipse2D.draw(new Line2D.Float(point.x, point.y, point.x, point.y));
    }

    /**
     * Método para verificar se a figura contém o ponto fornecido
     *
     * @param point contém as coordenadas do rato
     * @return
     */
    public boolean contains(Point point) {
        return (drawEllipse.contains(point.x, point.y));
    }

    /**
     * Método para obter uma matriz com as coordenadas do nó
     *
     * @return
     */
    public int[][] getCoordinates() {
        int[][] coordinates = new int[1][2];

        coordinates[0][0] = point.x;
        coordinates[0][1] = point.y;

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
}

/*
 * Esta classe cria e desenha uma linha bidimensional a partir das coordenadas fornecidas
 * A classe permite criar linhas de diferentes maneiras
 * Os métodos fornecem todos os elementos relativos à sua geometria
 */

package app.backend;

import app.calculations.AnalyticGeometry;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.io.Serializable;

/**
 *
 * @author André de Sousa
 */
public class DrawLine implements Serializable {

    public String shape;
    public String section;
    public Line2D drawLine;
    public boolean selected;

    //Cores para desenhar a linha nos seus diferentes estados
    private Color defaultColor = new Color(0, 0, 0);
    private Color selectedColor = new Color(99, 180, 251);

    /**
     * Método construtor da classe DrawLine
     *
     * @param x1 é a abscissa do primeiro ponto da linha
     * @param y1 é a ordenada do primeiro ponto da linha
     * @param x2 é a abscissa do último ponto da linha
     * @param y2 é a ordenada do último ponto da linha
     */
    public DrawLine(int x1, int y1, int x2, int y2) {
        this.drawLine = new Line2D.Float(x1, y1, x2, y2);
        this.selected = false;
        this.shape = "";
        this.section = "";
    }

    /**
     * Método construtor da classe DrawLine
     *
     * @param x1 é a abscissa do primeiro ponto da linha
     * @param y1 é a ordenada do primeiro ponto da linha
     * @param x2 é a abscissa do último ponto da linha
     * @param y2 é a ordenada do último ponto da linha
     * @param shape é o nome da linha a desenhar
     */
    public DrawLine(int x1, int y1, int x2, int y2, String shape) {
        this.drawLine = new Line2D.Float(x1, y1, x2, y2);
        this.selected = false;
        this.shape = shape;
        this.section = "";
    }

    /**
     * Método construtor da classe DrawLine
     *
     * @param x1 é a abscissa do primeiro ponto da linha
     * @param y1 é a ordenada do primeiro ponto da linha
     * @param x2 é a abscissa do último ponto da linha
     * @param y2 é a ordenada do último ponto da linha
     * @param defaultColor é a cor padrão da linha
     */
    public DrawLine(int x1, int y1, int x2, int y2, Color defaultColor) {
        this.drawLine = new Line2D.Float(x1, y1, x2, y2);
        this.selected = false;
        this.defaultColor = defaultColor;
        this.shape = "";
        this.section = "";
    }

    /**
     * Método construtor da classe DrawLine
     *
     * @param x1 é a abscissa do primeiro ponto da linha
     * @param y1 é a ordenada do primeiro ponto da linha
     * @param x2 é a abscissa do último ponto da linha
     * @param y2 é a ordenada do último ponto da linha
     * @param shape é o nome da linha a desenhar
     * @param selected é o estado da linha a desenhar
     */
    public DrawLine(int x1, int y1, int x2, int y2, String shape, boolean selected) {
        this.drawLine = new Line2D.Float(x1, y1, x2, y2);
        this.selected = selected;
        this.shape = shape;
        this.section = "";
    }

    /**
     * Método construtor da classe DrawLine
     *
     * @param x1 é a abscissa do primeiro ponto da linha
     * @param y1 é a ordenada do primeiro ponto da linha
     * @param x2 é a abscissa do último ponto da linha
     * @param y2 é a ordenada do último ponto da linha
     * @param shape é o nome da linha a desenhar
     * @param section é o nome da secção transversal
     * @param selected é o estado da linha a desenhar
     */
    public DrawLine(int x1, int y1, int x2, int y2, String shape, String section, boolean selected) {
        this.drawLine = new Line2D.Float(x1, y1, x2, y2);
        this.selected = selected;
        this.shape = shape;
        this.section = section;
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
     * Método para desenhar a linha
     *
     * @param line2D contém a informação da linha a desenhar
     */
    public void draw(Graphics2D line2D) {
        if (selected) {
            line2D.setPaint(selectedColor);
        } else {
            line2D.setPaint(defaultColor);
        }

        line2D.draw(drawLine);
    }

    /**
     * Método para desenhar uma barra
     *
     * @param line2D contém a informação da linha a desenhar
     */
    public void drawBar(Graphics2D line2D) {
        if (selected) {
            line2D.setPaint(selectedColor);
        } else {
            line2D.setPaint(defaultColor);
        }

        line2D.draw(drawLine);
        line2D.draw(new DrawEllipse((int) drawLine.getX1(), (int) drawLine.getY1(), 6, 6).drawEllipse);
        line2D.draw(new DrawEllipse((int) drawLine.getX2(), (int) drawLine.getY2(), 6, 6).drawEllipse);
    }

    /**
     * Método para saber se o ponto está nas proximidades da linha
     *
     * @param point contém as coordenadas do rato
     * @return
     */
    public boolean contains(Point point) {
        return (drawLine.ptSegDist(point) < 6);
    }

    /**
     * Método para saber se a linha contém o ponto
     *
     * @param point contém as coordenadas do ponto
     * @return
     */
    public boolean vertexContains(Point point) {
        boolean result = false;

        if (drawLine.getX1() == point.x && drawLine.getY1() == point.y) {
            result = true;
        }
        if (drawLine.getX2() == point.x && drawLine.getY2() == point.y) {
            result = true;
        }

        return result;
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
        Point point = null;

        for (int[] coordinate : coordinates) {
            int x = coordinate[0];
            int y = coordinate[1];

            double distance = AnalyticGeometry.length(xPoint, yPoint, x, y);

            if (distance < maxDistance) {
                maxDistance = distance;
                point = new Point(x, y);
            }
        }

        return point;
    }

    /**
     * Método para mover a linha na relação fornecida
     *
     * @param horizontal é a distância a mover na horizontal
     * @param vertical é a distância a mover na vertical
     */
    public void moveLine(int horizontal, int vertical) {
        Point pointA = new Point((int) drawLine.getX1() + horizontal, (int) drawLine.getY1() + vertical);
        Point pointB = new Point((int) drawLine.getX2() + horizontal, (int) drawLine.getY2() + vertical);

        drawLine.setLine(pointA, pointB);
    }

    /**
     * Método para obter uma matriz com as coordenadas da linha
     *
     * @return
     */
    public int[][] getCoordinates() {
        int[][] coordinates = new int[2][2];

        coordinates[0][0] = (int) drawLine.getX1();
        coordinates[0][1] = (int) drawLine.getY1();
        coordinates[1][0] = (int) drawLine.getX2();
        coordinates[1][1] = (int) drawLine.getY2();

        return coordinates;
    }
}

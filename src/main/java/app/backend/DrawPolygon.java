/*
 * Esta classe cria e desenha um polígono bidimensional a partir das coordenadas fornecidas
 * A classe permite criar polígonos de diferentes maneiras
 * Os métodos fornecem todos os elementos relativos à sua geometria
 */

package app.backend;

import app.calculations.AnalyticGeometry;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.io.Serializable;

/**
 *
 * @author André de Sousa
 */
public class DrawPolygon implements Serializable {

    public String shape;
    public String section;
    public Polygon drawPolygon;
    public boolean selected;

    //Cores para desenhar o polígono nos seus diferentes estados
    private Color defaultColor = new Color(0, 0, 0);
    private Color selectedColor = new Color(99, 180, 251);

    /**
     * Método construtor da classe DrawPolygon
     *
     * @param xPoints contém as abscissas dos pontos do polígono
     * @param yPoints contém as ordenadas dos pontos do polígono
     * @param nPoints é o número de pontos do polígono
     */
    public DrawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        this.drawPolygon = new Polygon(xPoints, yPoints, nPoints);
        this.selected = false;
        this.shape = "";
        this.section = "";
    }

    /**
     * Método construtor da classe DrawPolygon
     *
     * @param xPoints contém as abscissas dos pontos do polígono
     * @param yPoints contém as ordenadas dos pontos do polígono
     * @param nPoints é o número de pontos do polígono
     * @param shape é o nome do polígono a desenhar
     */
    public DrawPolygon(int[] xPoints, int[] yPoints, int nPoints, String shape) {
        this.drawPolygon = new Polygon(xPoints, yPoints, nPoints);
        this.selected = false;
        this.shape = shape;
        this.section = "";
    }

    /**
     * Método construtor da classe DrawPolygon
     *
     * @param xPoints contém as abscissas dos pontos do polígono
     * @param yPoints contém as ordenadas dos pontos do polígono
     * @param nPoints é o número de pontos do polígono
     * @param defaultColor é a cor padrão do polígono
     */
    public DrawPolygon(int[] xPoints, int[] yPoints, int nPoints, Color defaultColor) {
        this.drawPolygon = new Polygon(xPoints, yPoints, nPoints);
        this.selected = false;
        this.defaultColor = defaultColor;
        this.shape = "";
        this.section = "";
    }

    /**
     * Método construtor da classe DrawPolygon
     *
     * @param xPoints contém as abscissas dos pontos do polígono
     * @param yPoints contém as ordenadas dos pontos do polígono
     * @param nPoints é o número de pontos do polígono
     * @param shape é o nome do polígono a desenhar
     * @param selected é o estado do polígono a desenhar
     */
    public DrawPolygon(int[] xPoints, int[] yPoints, int nPoints, String shape, boolean selected) {
        this.drawPolygon = new Polygon(xPoints, yPoints, nPoints);
        this.selected = selected;
        this.shape = shape;
        this.section = "";
    }

    /**
     * Método construtor da classe DrawPolygon
     *
     * @param xPoints contém as abscissas dos pontos do polígono
     * @param yPoints contém as ordenadas dos pontos do polígono
     * @param nPoints é o número de pontos do polígono
     * @param shape é o nome do polígono a desenhar
     * @param section é o nome da secção transversal
     * @param selected é o estado do polígono a desenhar
     */
    public DrawPolygon(int[] xPoints, int[] yPoints, int nPoints, String shape, String section, boolean selected) {
        this.drawPolygon = new Polygon(xPoints, yPoints, nPoints);
        this.selected = selected;
        this.shape = shape;
        this.section = section;
    }

    /**
     * Método construtor da classe DrawPolygon
     *
     * @param coordinates contém as coordenadas do polígono
     * @param nPoints é o número de pontos do polígono
     * @param shape é o nome do polígono a desenhar
     */
    public DrawPolygon(int[][] coordinates, int nPoints, String shape) {
        int[] xPoints = new int[nPoints];
        int[] yPoints = new int[nPoints];

        for (int i = 0; i < nPoints; i++) {
            xPoints[i] = coordinates[i][0];
            yPoints[i] = coordinates[i][1];
        }

        this.drawPolygon = new Polygon(xPoints, yPoints, nPoints);
        this.selected = false;
        this.shape = shape;
        this.section = "";
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
     * Método para alterar o estado do polígono
     *
     * @param select informa se o polígono está selecionado
     */
    public void select(boolean select) {
        selected = select;
    }

    /**
     * Método para desenhar o polígono
     *
     * @param polygon contém a informação do polígono a desenhar
     */
    public void draw(Graphics2D polygon) {
        AlphaComposite transparency = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.5);
        AlphaComposite opaque = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 1);

        if (selected) {
            polygon.setPaint(selectedColor);
            polygon.setComposite(transparency);
            polygon.fill(drawPolygon);
            polygon.setComposite(opaque);
            polygon.setPaint(defaultColor);
        } else {
            polygon.setPaint(defaultColor);
        }

        polygon.draw(drawPolygon);
    }

    /**
     * Método para desenhar o polígono com preenchimento
     *
     * @param polygon contém a informação do polígono a desenhar
     */
    public void fill(Graphics2D polygon) {
        polygon.setPaint(defaultColor);
        polygon.fill(drawPolygon);
        polygon.draw(drawPolygon);
    }

    /**
     * Método para saber se o polígono contém o ponto
     *
     * @param point contém as coordenadas do rato
     * @return
     */
    public boolean contains(Point point) {
        return (drawPolygon.contains(point.x, point.y));
    }

    /**
     * Método para saber se o ponto é um vértice do polígono
     *
     * @param point contém as coordenadas do ponto
     * @return
     */
    public boolean vertexContains(Point point) {
        boolean result = false;

        int[] xPoints = drawPolygon.xpoints;
        int[] yPoints = drawPolygon.ypoints;
        int nPoints = drawPolygon.npoints;

        for (int i = 0; i < nPoints; i++) {
            if (xPoints[i] == point.x && yPoints[i] == point.y) {
                result = true;
                break;
            }
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
     * Método para mover o polígono na relação fornecida
     *
     * @param deltaX é a distância a mover na horizontal
     * @param deltaY é a distância a mover na vertical
     */
    public void movePolygon(int deltaX, int deltaY) {
        drawPolygon.translate(deltaX, deltaY);
    }

    /**
     * Método para obter uma matriz com as coordenadas do polígono
     *
     * @return
     */
    public int[][] getCoordinates() {
        int[] xPoints = drawPolygon.xpoints;
        int[] yPoints = drawPolygon.ypoints;
        int nPoints = drawPolygon.npoints;

        int[][] coordinates = new int[nPoints][2];

        for (int i = 0; i < nPoints; i++) {
            coordinates[i][0] = xPoints[i];
            coordinates[i][1] = yPoints[i];
        }

        return coordinates;
    }
}

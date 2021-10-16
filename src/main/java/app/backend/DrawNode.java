/*
 * Esta classe cria e desenha os nós dos elementos finitos
 * A classe desenha nós para qualquer tipo de elemento finito
 * Os nós são desenhados a partir das coordenadas fornecidas
 */

package app.backend;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

/**
 *
 * @author André de Sousa
 */
public class DrawNode implements Serializable {

    public Ellipse2D drawNode;
    public boolean selected;
    public Point point;

    //Cores para desenhar o polígono nos seus diferentes estados
    private Color defaultColor = new Color(0, 0, 0);
    private Color selectedColor = new Color(99, 180, 251);

    /**
     * Método construtor da classe DrawNode
     *
     * @param x1 é a abscissa do primeiro ponto da figura geométrica
     * @param y1 é a ordenada do primeiro ponto da figura geométrica
     */
    public DrawNode(int x1, int y1) {
        point = new Point(x1, y1);
        drawNode = new Ellipse2D.Float(x1 - 3, y1 - 3, 6, 6);
        selected = false;
    }

    /**
     * Método construtor da classe DrawNode
     *
     * @param x1 é a abscissa do primeiro ponto da figura geométrica
     * @param y1 é a ordenada do primeiro ponto da figura geométrica
     * @param defaultColor é a cor padrão da figura geométrica
     */
    public DrawNode(int x1, int y1, Color defaultColor) {
        this.point = new Point(x1, y1);
        this.drawNode = new Ellipse2D.Float(x1 - 3, y1 - 3, 6, 6);
        this.selected = false;
        this.defaultColor = defaultColor;
    }

    /**
     * Método construtor da classe DrawNode
     *
     * @param x1 é a abscissa do primeiro ponto da figura geométrica
     * @param y1 é a ordenada do primeiro ponto da figura geométrica
     * @param width é a largura da figura geométrica
     * @param height é a altura da figura geométrica
     */
    public DrawNode(int x1, int y1, int width, int height) {
        point = new Point(x1, y1);
        drawNode = new Ellipse2D.Float(x1, y1, width, height);
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
     * Método para alterar o estado do nó
     *
     * @param select informa se o nó está selecionado
     */
    public void select(boolean select) {
        selected = select;
    }

    /**
     * Método para desenhar o nó
     *
     * @param node contém a informação do nó a desenhar
     */
    public void draw(Graphics2D node) {
        AlphaComposite transparency = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.5);
        AlphaComposite opaque = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 1);

        if (selected) {
            node.setPaint(selectedColor);
            node.setComposite(transparency);
            node.fill(drawNode);
            node.setComposite(opaque);
            node.setPaint(defaultColor);
        } else {
            node.setPaint(defaultColor);
        }

        node.draw(drawNode);
    }

    /**
     * Método para saber se o ponto contém outro ponto
     *
     * @param point contém as coordenadas do rato
     * @return
     */
    public boolean contains(Point point) {
        return (drawNode.contains(point.x, point.y));
    }

    /**
     * Este método retorna uma matriz com as coordenadas do nó
     *
     * @return
     */
    public int[][] getCoordinates() {
        int[][] coordinates = new int[1][2];

        coordinates[0][0] = point.x;
        coordinates[0][1] = point.y;

        return coordinates;
    }
}

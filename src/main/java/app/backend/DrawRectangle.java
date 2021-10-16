/*
 * Esta classe cria e desenha um retângulo bidimensional a partir das coordenadas fornecidas
 * A classe permite criar retângulos de diferentes maneiras
 * Os métodos fornecem todos os elementos relativos à sua geometria
 */

package app.backend;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 *
 * @author André de Sousa
 */
public class DrawRectangle implements Serializable {

    public Rectangle2D drawRectangle;
    public boolean selected;

    //Cores para desenhar o retângulo nos seus diferentes estados
    private Color defaultColor = new Color(0, 0, 0);
    private Color selectedColor = new Color(99, 180, 251);

    /**
     * Método construtor da classe DrawRectangle
     *
     * @param x é a abscissa do primeiro ponto do retângulo
     * @param y é a ordenada do primeiro ponto do retâmgulo
     * @param width é a largura do retângulo
     * @param height é a altura do retângulo
     */
    public DrawRectangle(int x, int y, int width, int height) {
        drawRectangle = new Rectangle2D.Float(x, y, width, height);
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
     * Método para alterar o estado do retângulo
     *
     * @param select informa se o retângulo está selecionado
     */
    public void select(boolean select) {
        selected = select;
    }

    /**
     * Método para desenhar o retângulo
     *
     * @param rectangle2D contém a informação do retângulo a desenhar
     */
    public void draw(Graphics2D rectangle2D) {
        AlphaComposite transparency = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.5);
        AlphaComposite opaque = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 1);

        if (selected) {
            rectangle2D.setPaint(selectedColor);
            rectangle2D.setComposite(transparency);
            rectangle2D.fill(drawRectangle);
            rectangle2D.setComposite(opaque);
            rectangle2D.setPaint(defaultColor);
        } else {
            rectangle2D.setPaint(defaultColor);
        }

        rectangle2D.draw(drawRectangle);
    }

    /**
     * Método para saber se o retângulo contém o ponto
     *
     * @param point contém as coordenadas do rato
     * @return
     */
    public boolean contains(Point point) {
        return (drawRectangle.contains(point.x, point.y));
    }

    /**
     * Método para saber se o retângulo contém o ponto
     *
     * @param x é a abscissa das coordenadas do rato
     * @param y é a ordenada das coordenadas do rato
     * @return
     */
    public boolean contains(int x, int y) {
        return (drawRectangle.contains(x, y));
    }
}

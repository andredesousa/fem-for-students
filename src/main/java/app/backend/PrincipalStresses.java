/*
 * Esta classe permite desenhar as tensões e direcções principais num ponto
 * O método construtor requer a lista com as coordenadas e as respetivas tensões
 * A cor azul representa compressões e a cor vermelha trações
 */

package app.backend;

import static app.matrices.Multiply.multiply;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.round;
import static java.lang.Math.sin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

/**
 *
 * @author André de Sousa
 */
public class PrincipalStresses {

    private final int[][] pointsCoordinates;
    private final double[][] stressesAndDirections;

    //Cores para desenhar as diferentes tensões principais
    private final Color tension = new Color(255, 0, 0);
    private final Color compression = new Color(0, 0, 200);

    /**
     * Método constutor da classe PrincipalStresses
     *
     * @param pointsCoordinates
     * @param stressesAndDirections
     */
    public PrincipalStresses(int[][] pointsCoordinates, double[][] stressesAndDirections) {
        this.pointsCoordinates = pointsCoordinates;
        this.stressesAndDirections = stressesAndDirections;
    }

    /**
     * Método para desenhar as tensões principais nos pontos
     *
     * @param stresses
     * @param maximumStress
     */
    public void draw(Graphics2D stresses, double maximumStress) {
        stresses.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        double length_σ1, length_σ2;

        int lenght = pointsCoordinates.length;
        for (int i = 0; i < lenght; i++) {
            int[][] coordinates_σ1, coordinates_σ2;
            double σ1, σ2, β;

            //Escalonamento das tensões principais nos pontos
            σ1 = stressesAndDirections[i][0];
            σ2 = stressesAndDirections[i][1];
            β = stressesAndDirections[i][2];

            //Transformação de coordenadas com aplicação de uma rotação β
            length_σ1 = definingLineScale(σ1, maximumStress);
            length_σ2 = definingLineScale(σ2, maximumStress);

            coordinates_σ1 = coordinateTransformations(new double[][] { { 0 }, { 0 }, { length_σ1 }, { 0 } }, β);
            coordinates_σ2 = coordinateTransformations(new double[][] { { 0 }, { 0 }, { length_σ2 }, { 0 } }, β + (PI / 2));

            //Desenho das tensões principais nos pontos
            int x1 = pointsCoordinates[i][0];
            int y1 = pointsCoordinates[i][1];

            if (σ2 <= 0) {
                stresses.setPaint(compression);
            } else {
                stresses.setPaint(tension);
            }

            stresses.draw(
                new Line2D.Float(coordinates_σ2[0][0] + x1, coordinates_σ2[1][0] + y1, coordinates_σ2[2][0] + x1, coordinates_σ2[3][0] + y1)
            );
            stresses.draw(
                new Line2D.Float(
                    -coordinates_σ2[0][0] + x1,
                    -coordinates_σ2[1][0] + y1,
                    -coordinates_σ2[2][0] + x1,
                    -coordinates_σ2[3][0] + y1
                )
            );

            if (σ1 <= 0) {
                stresses.setPaint(compression);
            } else {
                stresses.setPaint(tension);
            }

            stresses.draw(
                new Line2D.Float(coordinates_σ1[0][0] + x1, coordinates_σ1[1][0] + y1, coordinates_σ1[2][0] + x1, coordinates_σ1[3][0] + y1)
            );
            stresses.draw(
                new Line2D.Float(
                    -coordinates_σ1[0][0] + x1,
                    -coordinates_σ1[1][0] + y1,
                    -coordinates_σ1[2][0] + x1,
                    -coordinates_σ1[3][0] + y1
                )
            );
        }
    }

    /**
     * Método para realizar a transformação de coordenadas de uma rotação θ
     *
     * @param coordinates
     * @param angle
     * @return
     */
    private static int[][] coordinateTransformations(double[][] coordinates, double angle) {
        int[][] nodesCoordinates = new int[4][1];

        double[][] matrixT = {
            { cos(angle), -sin(angle), 0, 0 },
            { sin(angle), cos(angle), 0, 0 },
            { 0, 0, cos(angle), -sin(angle) },
            { 0, 0, sin(angle), cos(angle) },
        };

        double[][] transformation = multiply(matrixT, coordinates);

        for (int i = 0; i < 4; i++) {
            nodesCoordinates[i][0] = (int) round(transformation[i][0]);
        }

        return nodesCoordinates;
    }

    /**
     * Método para definir o comprimento da linha da tensão principal
     *
     * @param stress
     * @param maximumStress
     * @return
     */
    private static double definingLineScale(double stress, double maximumStress) {
        double length = 0.0;

        if (maximumStress > 0.0) {
            if (stress >= 0) {
                length = (stress * 10) / maximumStress;
            }
            if (stress < 0) {
                length = ((-1 * stress) * 10) / maximumStress;
            }
        }

        return length;
    }
}

/*
 * Esta classe fornece a matriz B de um elemento finito
 * A classe requer os parâmetros relativos à geometria e aos pontos de cálculo
 * A matriz B é avaliada consoante o tipo de elemento finito
 */

package app.finiteelement;

import static app.finiteelement.MatrixB_1D.matrixB_1D;
import static app.finiteelement.MatrixB_2D.matrixB_2D;
import static app.finiteelement.MatrixB_3D.matrixB_3D;
import static app.finiteelement.MatrixB_Beams.matrixB_EulerBernoulli;
import static app.finiteelement.MatrixB_Beams.matrixBb_Timoshenko;
import static app.finiteelement.MatrixB_Beams.matrixBs_Timoshenko;
import static app.finiteelement.MatrixB_Frames.matrixB_Frames;
import static app.finiteelement.MatrixB_Grids.matrixB_Grids;
import static app.finiteelement.MatrixB_Slabs.matrixB_Kirchhoff;
import static app.finiteelement.MatrixB_Slabs.matrixB_ReissnerMindlin;
import static app.matrices.Sum.sum;

/**
 *
 * @author André de Sousa
 */
public class MatrixB {

    /**
     * Método construtor da classe MatrixB
     *
     * @param type é o tipo de elemento finito
     * @param L é o comprimento do elemento finito
     * @param a é o comprimento do elemento finito
     * @param b é a largura do elemento finito
     * @param c é a altura do elemento finito
     */
    public MatrixB(String type, double L, double a, double b, double c) {
        this.type = type;
        this.L = L;
        this.a = a;
        this.b = b;
        this.c = c;
    }

    //Variável para definir o tipo de elemento finito
    private final String type;

    //variáveis para definir a geometria do elemento finito
    private final double L;
    private final double a;
    private final double b;
    private final double c;

    /**
     * Método para obter a matriz B de um elemento finito
     *
     * @param x é a coordenada local segundo o eixo x
     * @param y é a coordenada local segundo o eixo y
     * @param z é a coordenada local segundo o eixo z
     * @param nodes é o número de nós do elemento finito
     * @param theory é a teoria de formulação do elemento finito
     * @return
     */
    public double[][] getMatrixB(double x, double y, double z, int nodes, String theory) {
        double[][] matrixB;

        switch (this.type) {
            case "1D":
                matrixB = matrixB_1D(x, L, nodes);
                break;
            case "2D":
                matrixB = matrixB_2D(x, y, a, b, nodes);
                break;
            case "3D":
                matrixB = matrixB_3D(x, y, z, a, b, c, nodes);
                break;
            case "Beams":
                switch (theory) {
                    case "Euler-Bernoulli":
                        matrixB = matrixB_EulerBernoulli(x, L, nodes);
                        break;
                    case "Timoshenko":
                        double[][] matrixBb = matrixBb_Timoshenko(x, L, nodes);
                        double[][] matrixBs = matrixBs_Timoshenko(x, L, nodes);
                        matrixB = sum(matrixBb, matrixBs);
                        break;
                    default:
                        matrixB = null;
                        break;
                }
                break;
            case "Frames":
                matrixB = matrixB_Frames(x, L, nodes);
                break;
            case "Grids":
                matrixB = matrixB_Grids(x, L, nodes);
                break;
            case "Slabs":
                switch (theory) {
                    case "Reissner-Mindlin":
                        matrixB = matrixB_ReissnerMindlin(x, y, a, b, nodes);
                        break;
                    case "Kirchhoff":
                        matrixB = matrixB_Kirchhoff(x, y, a, b, nodes);
                        break;
                    default:
                        matrixB = null;
                        break;
                }
                break;
            default:
                matrixB = null;
                break;
        }

        return matrixB;
    }

    /**
     * Método para obter a matriz Bb de um elemento finito de viga
     *
     * @param x é a coordenada local segundo o eixo x
     * @param nodes é o número de nós do elemento finito
     * @param theory é a teoria de formulação do elemento finito
     * @return
     */
    public double[][] getMatrixBb(double x, int nodes, String theory) {
        double[][] matrixB;

        switch (this.type) {
            case "Beams":
                switch (theory) {
                    case "Timoshenko":
                        matrixB = matrixBb_Timoshenko(x, L, nodes);
                        break;
                    default:
                        matrixB = null;
                        break;
                }
                break;
            default:
                matrixB = null;
                break;
        }

        return matrixB;
    }

    /**
     * Método para obter a matriz Bs de um elemento finito de viga
     *
     * @param x é a coordenada local segundo o eixo x
     * @param nodes é o número de nós do elemento finito
     * @param theory é a teoria de formulação do elemento finito
     * @return
     */
    public double[][] getMatrixBs(double x, int nodes, String theory) {
        double[][] matrixB;

        switch (this.type) {
            case "Beams":
                switch (theory) {
                    case "Timoshenko":
                        matrixB = matrixBs_Timoshenko(x, L, nodes);
                        break;
                    default:
                        matrixB = null;
                        break;
                }
                break;
            default:
                matrixB = null;
                break;
        }

        return matrixB;
    }
}

/*
 * Esta classe fornece a matriz B de um elemento finito
 * A classe requer as derivadas das funções de forma do elemento finito
 * A matriz B é avaliada consoante o tipo de elemento finito
 */

package app.variablesubstitution;

import static app.matrices.Sum.sum;
import static app.variablesubstitution.MatrixB_1D.matrixB_1D;
import static app.variablesubstitution.MatrixB_2D.matrixB_2D;
import static app.variablesubstitution.MatrixB_3D.matrixB_3D;
import static app.variablesubstitution.MatrixB_Beams.matrixB_EulerBernoulli;
import static app.variablesubstitution.MatrixB_Beams.matrixBb_Timoshenko;
import static app.variablesubstitution.MatrixB_Beams.matrixBs_Timoshenko;
import static app.variablesubstitution.MatrixB_Slabs.matrixB_Kirchhoff;
import static app.variablesubstitution.MatrixB_Slabs.matrixB_ReissnerMindlin;

/**
 *
 * @author André de Sousa
 */
public class MatrixB {

    /**
     * Método para obter a matriz B de um elemento finito com substituição de variável
     *
     * @param type
     * @param theory
     * @param derivedMatrixB
     * @param nodes
     * @return
     */
    public static double[][] matrixB(String type, String theory, double[][] derivedMatrixB, int nodes) {
        double[][] derivedMatrix;

        switch (type) {
            case "1D":
                derivedMatrix = matrixB_1D(derivedMatrixB, nodes);
                break;
            case "2D":
                derivedMatrix = matrixB_2D(derivedMatrixB, nodes);
                break;
            case "3D":
                derivedMatrix = matrixB_3D(derivedMatrixB, nodes);
                break;
            case "Beams":
                switch (theory) {
                    case "Euler-Bernoulli":
                        derivedMatrix = matrixB_EulerBernoulli(derivedMatrixB, nodes);
                        break;
                    case "Timoshenko":
                        double[][] derivedMatrixBb = matrixBb_Timoshenko(derivedMatrixB, nodes);
                        double[][] derivedMatrixBs = matrixBs_Timoshenko(derivedMatrixB, nodes);
                        derivedMatrix = sum(derivedMatrixBb, derivedMatrixBs);
                        break;
                    default:
                        derivedMatrix = null;
                        break;
                }
                break;
            case "Frames":
                derivedMatrix = null;
                break;
            case "Grids":
                derivedMatrix = null;
                break;
            case "Slabs":
                switch (theory) {
                    case "Reissner-Mindlin":
                        derivedMatrix = matrixB_ReissnerMindlin(derivedMatrixB, nodes);
                        break;
                    case "Kirchhoff":
                        derivedMatrix = matrixB_Kirchhoff(derivedMatrixB, nodes);
                        break;
                    default:
                        derivedMatrix = null;
                        break;
                }
                break;
            default:
                derivedMatrix = null;
                break;
        }

        return derivedMatrix;
    }
}

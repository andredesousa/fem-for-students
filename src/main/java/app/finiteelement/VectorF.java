/*
 * Esta classe é responsável pela criação do vetor de solicitação
 * A classe requer os parâmetros relativos à geometria dos elementos finitos
 * O vetor de solicitação é avaliado para o valor do carregamento
 */

package app.finiteelement;

import static app.finiteelement.VectorF_1D.vectorF_1D;
import static app.finiteelement.VectorF_2D.vectorF_2D;
import static app.finiteelement.VectorF_3D.vectorF_3D;
import static app.finiteelement.VectorF_Beams.vectorF_EulerBernoulli;
import static app.finiteelement.VectorF_Beams.vectorF_Timoshenko;
import static app.finiteelement.VectorF_Frames.vectorF_Frames;
import static app.finiteelement.VectorF_Grids.vectorF_Grids;
import static app.finiteelement.VectorF_Slabs.vectorF_Kirchhoff;
import static app.finiteelement.VectorF_Slabs.vectorF_ReissnerMindlin;
import static app.matrices.Multiply.multiply;

/**
 *
 * @author André de Sousa
 */
public class VectorF {

    /**
     * Método construtor da classe VectorF
     *
     * @param type
     * @param xLoad
     * @param yLoad
     * @param zLoad
     */
    public VectorF(String type, double xLoad, double yLoad, double zLoad) {
        this.type = type;
        this.xLoad = xLoad;
        this.yLoad = yLoad;
        this.zLoad = zLoad;
    }

    //variáveis internas para o funcionamento da classe
    String type;
    double xLoad;
    double yLoad;
    double zLoad;

    /**
     * Método para editar as cargas aplicadas no elemento finito
     *
     * @param xLoad
     * @param yLoad
     * @param zLoad
     */
    public void editLoads(double xLoad, double yLoad, double zLoad) {
        this.xLoad = xLoad;
        this.yLoad = yLoad;
        this.zLoad = zLoad;
    }

    /**
     * Método para criar o vetor de solicitação de um elemento finito
     *
     * @param L é o comprimento do elemento finito
     * @param a é o comprimento do elemento finito
     * @param b é a largura do elemento finito
     * @param c é a altura do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @param theory é a teoria de formulação do elemento finito
     * @return
     */
    public double[][] getLoadVector(double L, double a, double b, double c, int nodes, String theory) {
        double[][] vectorF;

        switch (this.type) {
            case "1D":
                vectorF = loadVector_1D(L, nodes);
                break;
            case "2D":
                vectorF = loadVector_2D(a, b, nodes);
                break;
            case "3D":
                vectorF = loadVector_3D(a, b, c, nodes);
                break;
            case "Beams":
                vectorF = loadVector_Beams(L, nodes, theory);
                break;
            case "Frames":
                vectorF = loadVector_Frames(L, nodes);
                break;
            case "Grids":
                vectorF = loadVector_Grids(L, nodes);
                break;
            case "Slabs":
                vectorF = loadVector_Slabs(a, b, nodes, theory);
                break;
            default:
                vectorF = null;
                break;
        }

        return vectorF;
    }

    /**
     * Método para criar o vetor de solicitação de um elemento finito
     *
     * @param length
     * @param thickness
     * @param nodes
     * @param theory
     * @return
     */
    public double[][] getLoadVector(double length, double thickness, int nodes, String theory) {
        double[][] vectorF;

        switch (this.type) {
            case "1D":
                vectorF = loadVector_1D(length, nodes);
                break;
            case "2D":
                vectorF = loadVector_2D(length, nodes);
                if ("Plane Stress".equals(theory)) {
                    vectorF = multiply(thickness, vectorF);
                }
                break;
            case "Beams":
                vectorF = loadVector_Beams(length, nodes, theory);
                break;
            case "Frames":
                vectorF = loadVector_Frames(length, nodes);
                break;
            case "Grids":
                vectorF = loadVector_Grids(length, nodes);
                break;
            default:
                vectorF = null;
                break;
        }

        return vectorF;
    }

    /**
     * Método para criar o vetor de solicitação de um elemento finito
     *
     * @param h é a altura da secção do elemento finito
     * @param EA é a rigidez axial do elemento finito
     * @param EI é a rigidez de flexão do elemento finito
     * @param alpha é o coeficiente de dilatação térmica linear
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public double[][] getThermalLoadVector(double h, double EA, double EI, double alpha, int nodes) {
        double[][] vectorF;

        switch (this.type) {
            case "1D":
                vectorF = vectorF_1D(xLoad, EA, alpha, nodes);
                break;
            case "Frames":
                double Tmean = (yLoad + zLoad) / 2.0;
                double deltaT = zLoad - yLoad;
                vectorF = vectorF_Frames(Tmean, deltaT, h, EA, EI, alpha, nodes);
                break;
            default:
                vectorF = null;
                break;
        }

        return vectorF;
    }

    /**
     * Este método cria o vetor de solicitação de um elemento finito unidimensional
     *
     * @param L
     * @param nodes
     * @return
     */
    private double[][] loadVector_1D(double L, int nodes) {
        double[][] vectorF;

        try {
            vectorF = vectorF_1D(L, nodes);
            vectorF = multiply(xLoad, vectorF);
        } catch (Exception e) {
            vectorF = null;
        }

        return vectorF;
    }

    /**
     * Este método cria o vetor de solicitação de um elemento finito bidimensional
     *
     * @param a
     * @param b
     * @param nodes
     * @return
     */
    private double[][] loadVector_2D(double a, double b, int nodes) {
        double[][] vectorF;

        try {
            double[][] loads = { { xLoad }, { yLoad } };
            vectorF = vectorF_2D(a, b, nodes);
            vectorF = multiply(vectorF, loads);
        } catch (Exception e) {
            vectorF = null;
        }

        return vectorF;
    }

    /**
     * Este método cria o vetor de solicitação de um elemento finito bidimensional
     *
     * @param length
     * @param nodes
     * @return
     */
    private double[][] loadVector_2D(double length, int nodes) {
        double[][] vectorF;

        try {
            double[][] loads = { { xLoad }, { yLoad } };
            vectorF = vectorF_2D(length, nodes);
            vectorF = multiply(vectorF, loads);
        } catch (Exception e) {
            vectorF = null;
        }

        return vectorF;
    }

    /**
     * Este método cria o vetor de solicitação de um elemento finito tridimensional
     *
     * @param a
     * @param b
     * @param c
     * @param nodes
     * @return
     */
    private double[][] loadVector_3D(double a, double b, double c, int nodes) {
        double[][] vectorF;

        try {
            double[][] loads = { { xLoad }, { yLoad }, { zLoad } };
            vectorF = vectorF_3D(a, b, c, nodes);
            vectorF = multiply(vectorF, loads);
        } catch (Exception e) {
            vectorF = null;
        }

        return vectorF;
    }

    /**
     * Este método cria o vetor de solicitação de um elemento finito de viga
     *
     * @param L
     * @param nodes
     * @param theory
     * @return
     */
    private double[][] loadVector_Beams(double L, int nodes, String theory) {
        double[][] vectorF;

        switch (theory) {
            case "Euler-Bernoulli":
                try {
                    vectorF = vectorF_EulerBernoulli(L, nodes);
                    vectorF = multiply(zLoad, vectorF);
                } catch (Exception e) {
                    vectorF = null;
                }
                break;
            case "Timoshenko":
                try {
                    vectorF = vectorF_Timoshenko(L, nodes);
                    vectorF = multiply(zLoad, vectorF);
                } catch (Exception e) {
                    vectorF = null;
                }
                break;
            default:
                vectorF = null;
                break;
        }

        return vectorF;
    }

    /**
     * Este método cria o vetor de solicitação de um elemento finito de barra
     *
     * @param L
     * @param nodes
     * @return
     */
    private double[][] loadVector_Frames(double L, int nodes) {
        double[][] vectorF;

        try {
            vectorF = vectorF_Frames(L, xLoad, yLoad, nodes);
        } catch (Exception e) {
            vectorF = null;
        }

        return vectorF;
    }

    /**
     * Este método cria o vetor de solicitação de um elemento finito de grelha
     *
     * @param L
     * @param nodes
     * @return
     */
    private double[][] loadVector_Grids(double L, int nodes) {
        double[][] vectorF;

        try {
            vectorF = vectorF_Grids(L, nodes);
            vectorF = multiply(zLoad, vectorF);
        } catch (Exception e) {
            vectorF = null;
        }

        return vectorF;
    }

    /**
     * Este método cria o vetor de solicitação de um elemento finito de laje
     *
     * @param a
     * @param b
     * @param nodes
     * @param theory
     * @return
     */
    private double[][] loadVector_Slabs(double a, double b, int nodes, String theory) {
        double[][] vectorF;

        switch (theory) {
            case "Reissner-Mindlin":
                try {
                    vectorF = vectorF_ReissnerMindlin(a, b, nodes);
                    vectorF = multiply(zLoad, vectorF);
                } catch (Exception e) {
                    vectorF = null;
                }
                break;
            case "Kirchhoff":
                try {
                    vectorF = vectorF_Kirchhoff(a, b, nodes);
                    vectorF = multiply(zLoad, vectorF);
                } catch (Exception e) {
                    vectorF = null;
                }
                break;
            default:
                vectorF = null;
                break;
        }

        return vectorF;
    }
}

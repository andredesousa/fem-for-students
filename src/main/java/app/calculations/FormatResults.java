/*
 * Esta classe fornece os resultados para impressão nas tabelas
 * Os resultados são forncecidos no formato de texto
 * A fração decimal é arredondada para 3 casas decimais
 */

package app.calculations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author André de Sousa
 */
public class FormatResults {

    /**
     * Método para formatar valores decimais na forma de texto
     *
     * @param number é o número a ser formatado
     * @return
     */
    public static String decimalFormat(double number) {
        BigDecimal decimal = new BigDecimal(number).setScale(3, RoundingMode.HALF_EVEN);
        DecimalFormat format = new DecimalFormat("#,###.###");

        String formatNumber = format.format(decimal.doubleValue());
        if ("-0".equals(formatNumber)) {
            formatNumber = "0";
        }

        return formatNumber;
    }

    /**
     * Método para arredondar valores decimais para 3 casas decimais
     *
     * @param number é o número a ser formatado
     * @return
     */
    public static double parseDouble(double number) {
        BigDecimal decimal = new BigDecimal(number).setScale(3, RoundingMode.HALF_EVEN);
        DecimalFormat format = new DecimalFormat("#.###");

        String formatNumber = format.format(decimal.doubleValue());
        if ("-0".equals(formatNumber)) {
            formatNumber = "0";
        }

        return Double.parseDouble(formatString(formatNumber));
    }

    /**
     * Método para formatar uma string para coversão do tipo double
     *
     * @param string é a string a ser formatada
     * @return
     */
    public static String formatString(String string) {
        string = string.trim();
        string = string.replace(",", ".");

        return string;
    }

    /**
     * Método que devolve uma matriz com os resultados das forças nodais
     *
     * @param type
     * @param ID
     * @param nodes
     * @param vectorF
     * @return
     */
    public static String[][] getNodalForces(String type, int ID, int nodes, double[][] vectorF) {
        String[][] nodalForces;

        switch (type) {
            case "1D":
                nodalForces = getNodalForces_1D(ID, nodes, vectorF);
                break;
            case "3D":
                nodalForces = getNodalForces_3D(ID, nodes, vectorF);
                break;
            case "Beams":
                nodalForces = getNodalForces_Beams(ID, nodes, vectorF);
                break;
            case "Frames":
                nodalForces = getNodalForces_Frames(ID, nodes, vectorF);
                break;
            case "Grids":
                nodalForces = getNodalForces_Grids(ID, nodes, vectorF);
                break;
            case "Slabs":
                nodalForces = getNodalForces_Slabs(ID, nodes, vectorF);
                break;
            default:
                nodalForces = null;
                break;
        }

        return nodalForces;
    }

    /**
     * Método que devolve uma matriz com os resultados das forças nodais
     *
     * @param type
     * @param ID
     * @param nodes
     * @param theory
     * @param vectorF
     * @return
     */
    public static String[][] getNodalForces(String type, int ID, int nodes, String theory, double[][] vectorF) {
        String[][] nodalForces;

        switch (type) {
            case "2D":
                nodalForces = getNodalForces_2D(ID, theory, vectorF);
                break;
            default:
                nodalForces = null;
                break;
        }

        return nodalForces;
    }

    /**
     * ´Método que devolve uma matriz com as reações de apoio
     *
     * @param type
     * @param theory
     * @param arrayListNodes
     * @param supportReactions
     * @return
     */
    public static String[][] getSupportReactions(
        String type,
        String theory,
        ArrayList<double[]> arrayListNodes,
        double[][] supportReactions
    ) {
        String[][] reactions;

        /* Descrição da variável arrayListNodes
         *
         * 3: A quarta coluna recebe o número identificativo grau de liberdade
         * 4: A quinta coluna recebe a pescrição do graus de liberdade
         * 5: A sexta coluna recebe a numeração dos nós da malha
         */

        int[][] listOfNodes = new int[supportReactions.length][2];

        int i = 0;
        for (double[] nodes : arrayListNodes) {
            if (nodes[4] == -1 || nodes[4] == 1) {
                listOfNodes[i][0] = (int) nodes[3];
                listOfNodes[i][1] = (int) nodes[5];
                i++;
            }
            if (nodes[4] == -2 || nodes[4] == 2) {
                listOfNodes[i][0] = (int) nodes[3];
                listOfNodes[i][1] = (int) nodes[5];
                i++;
            }
        }

        switch (type) {
            case "1D":
                reactions = supportReactions_1D(listOfNodes, supportReactions);
                break;
            case "2D":
                reactions = supportReactions_2D(listOfNodes, supportReactions, theory);
                break;
            case "3D":
                reactions = supportReactions_3D(listOfNodes, supportReactions);
                break;
            case "Beams":
                reactions = supportReactions_Beams(listOfNodes, supportReactions);
                break;
            case "Frames":
                reactions = supportReactions_Frames(listOfNodes, supportReactions);
                break;
            case "Grids":
                reactions = supportReactions_Grids(listOfNodes, supportReactions);
                break;
            case "Slabs":
                reactions = supportReactions_Slabs(listOfNodes, supportReactions);
                break;
            default:
                reactions = null;
                break;
        }

        return reactions;
    }

    /**
     * Método que devolve os resultados das forças nodais para um elemento unidimensional
     *
     * @param ID
     * @param nodes
     * @param vectorF
     * @return
     */
    private static String[][] getNodalForces_1D(int ID, int nodes, double[][] vectorF) {
        String[][] nodalForces;

        int length = vectorF.length;
        if (nodes == length) {
            nodalForces = new String[length][5];

            int n = 1;
            for (int i = 0; i < length; i++) {
                nodalForces[i][0] = String.valueOf(ID);
                nodalForces[i][1] = String.valueOf(n);
                nodalForces[i][2] = String.valueOf(decimalFormat(vectorF[i][0]));
                nodalForces[i][3] = "KN";

                //Instruções para descrever a direção e sentido da carga
                double value = parseDouble(vectorF[i][0]);

                if (value > 0) {
                    nodalForces[i][4] = "Tension";
                } else if (value < 0) {
                    nodalForces[i][4] = "Compression ";
                } else {
                    nodalForces[i][4] = "";
                }

                //Instruções para definir o número do nó
                n++;
            }
        } else {
            nodalForces = null;
        }

        return nodalForces;
    }

    /**
     * Método que devolve os resultados das forças nodais para um elemento bidimensional
     *
     * @param ID
     * @param nodes
     * @param vectorF
     * @return
     */
    private static String[][] getNodalForces_2D(int ID, String theory, double[][] vectorF) {
        String[][] nodalForces;

        int length = vectorF.length;
        if (length > 0) {
            nodalForces = new String[length][5];

            int n = 1;
            int j = 0;
            for (int i = 0; i < length; i++) {
                nodalForces[i][0] = String.valueOf(ID);
                nodalForces[i][1] = String.valueOf(n);
                nodalForces[i][2] = String.valueOf(decimalFormat(vectorF[i][0]));

                //Instruções para descrever a direção e sentido da carga
                double value = parseDouble(vectorF[i][0]);

                if ("Plane Stress".equals(theory)) {
                    nodalForces[i][3] = "KN/m";
                } else {
                    nodalForces[i][3] = "KN/m/m";
                }

                if (j == 0) {
                    if (value > 0) {
                        nodalForces[i][4] = "Horizontal Tension";
                    } else if (value < 0) {
                        nodalForces[i][4] = "Horizontal Compression";
                    } else {
                        nodalForces[i][4] = "Horizontal Force";
                    }
                }

                if (j == 1) {
                    if (value > 0) {
                        nodalForces[i][4] = "Vertical Tension";
                    } else if (value < 0) {
                        nodalForces[i][4] = "Vertical Compression";
                    } else {
                        nodalForces[i][4] = "Vertical Force";
                    }
                }

                if (j == 2) {
                    if (value > 0) {
                        nodalForces[i][4] = "Positive";
                    } else if (value < 0) {
                        nodalForces[i][4] = "Negative";
                    } else {
                        nodalForces[i][4] = "";
                    }
                }

                j++;

                //Instruções para definir o número do nó
                if (i != 0 && (i + 1) % 3 == 0) {
                    j = 0;
                    n++;
                }
            }
        } else {
            nodalForces = null;
        }

        return nodalForces;
    }

    /**
     * Método que devolve os resultados das forças nodais para um elemento tridimensional
     *
     * @param ID
     * @param nodes
     * @param vectorF
     * @return
     */
    private static String[][] getNodalForces_3D(int ID, int nodes, double[][] vectorF) {
        return null;
    }

    /**
     * Método que devolve os resultados das forças nodais para um elemento de viga
     *
     * @param ID
     * @param nodes
     * @param vectorF
     * @return
     */
    private static String[][] getNodalForces_Beams(int ID, int nodes, double[][] vectorF) {
        String[][] nodalForces;

        int length = vectorF.length;
        if (length > 0) {
            nodalForces = new String[length][5];

            int n = 1;
            for (int i = 0; i < length; i++) {
                nodalForces[i][0] = String.valueOf(ID);
                nodalForces[i][1] = String.valueOf(n);
                nodalForces[i][2] = String.valueOf(decimalFormat(vectorF[i][0]));

                //Instruções para descrever a direção e sentido da carga
                double value = parseDouble(vectorF[i][0]);

                if (i % 2 == 0) {
                    nodalForces[i][3] = "KNm";
                    if (value > 0) {
                        nodalForces[i][4] = "Positive Moment";
                    } else if (value < 0) {
                        nodalForces[i][4] = "Negative Moment";
                    } else {
                        nodalForces[i][4] = "Moment";
                    }
                }

                if (i % 2 != 0) {
                    nodalForces[i][3] = "KN";
                    if (value > 0) {
                        nodalForces[i][4] = "Positive Shear";
                    } else if (value < 0) {
                        nodalForces[i][4] = "Negative Shear";
                    } else {
                        nodalForces[i][4] = "Shear";
                    }
                }

                //Instruções para definir o número do nó
                if (i != 0 && (i + 1) % 2 == 0) {
                    n++;
                }
            }
        } else {
            nodalForces = null;
        }

        return nodalForces;
    }

    /**
     * Método que devolve os resultados das forças nodais para um elemento de barra
     *
     * @param ID
     * @param nodes
     * @param vectorF
     * @return
     */
    private static String[][] getNodalForces_Frames(int ID, int nodes, double[][] vectorF) {
        String[][] nodalForces;

        int length = vectorF.length;
        if (length > 0) {
            nodalForces = new String[length][5];

            int n = 1;
            int j = 0;
            for (int i = 0; i < length; i++) {
                nodalForces[i][0] = String.valueOf(ID);
                nodalForces[i][1] = String.valueOf(n);
                nodalForces[i][2] = String.valueOf(decimalFormat(vectorF[i][0]));

                //Instruções para descrever a direção e sentido da carga
                double value = parseDouble(vectorF[i][0]);

                if (j == 0) {
                    nodalForces[i][3] = "KN";
                    if (value > 0) {
                        nodalForces[i][4] = "Positive Axial Force";
                    } else if (value < 0) {
                        nodalForces[i][4] = "Negative Axial Force";
                    } else {
                        nodalForces[i][4] = "Axial Force";
                    }
                }

                if (j == 1) {
                    nodalForces[i][3] = "KNm";
                    if (value > 0) {
                        nodalForces[i][4] = "Positive Moment";
                    } else if (value < 0) {
                        nodalForces[i][4] = "Negative Moment";
                    } else {
                        nodalForces[i][4] = "Moment";
                    }
                }

                j++;

                //Instruções para definir o número do nó
                if (i != 0 && (i + 1) % 2 == 0) {
                    j = 0;
                    n++;
                }
            }
        } else {
            nodalForces = null;
        }

        return nodalForces;
    }

    /**
     * Método que devolve os resultados das forças nodais para um elemento de grelha
     *
     * @param ID
     * @param nodes
     * @param vectorF
     * @return
     */
    private static String[][] getNodalForces_Grids(int ID, int nodes, double[][] vectorF) {
        String[][] nodalForces;

        int length = vectorF.length;
        if (length > 0) {
            nodalForces = new String[length][5];

            int n = 1;
            int j = 0;
            for (int i = 0; i < length; i++) {
                nodalForces[i][0] = String.valueOf(ID);
                nodalForces[i][1] = String.valueOf(n);
                nodalForces[i][2] = String.valueOf(decimalFormat(vectorF[i][0]));

                //Instruções para descrever a direção e sentido da carga
                double value = parseDouble(vectorF[i][0]);

                if (j == 0) {
                    nodalForces[i][3] = "KNm";
                    if (value > 0) {
                        nodalForces[i][4] = "Positive Moment";
                    } else if (value < 0) {
                        nodalForces[i][4] = "Negative Moment";
                    } else {
                        nodalForces[i][4] = "Moment";
                    }
                }

                if (j == 1) {
                    nodalForces[i][3] = "KNm";
                    if (value > 0) {
                        nodalForces[i][4] = "Positive Moment";
                    } else if (value < 0) {
                        nodalForces[i][4] = "Negative Moment";
                    } else {
                        nodalForces[i][4] = "Moment";
                    }
                }

                j++;

                //Instruções para definir o número do nó
                if (i != 0 && (i + 1) % 2 == 0) {
                    j = 0;
                    n++;
                }
            }
        } else {
            nodalForces = null;
        }

        return nodalForces;
    }

    /**
     * Método que devolve os resultados das forças nodais para um elemento de laje
     *
     * @param ID
     * @param nodes
     * @param vectorF
     * @return
     */
    private static String[][] getNodalForces_Slabs(int ID, int nodes, double[][] vectorF) {
        String[][] nodalForces;

        int length = vectorF.length;
        if (length > 0) {
            nodalForces = new String[length][5];

            int n = 1;
            int j = 0;
            for (int i = 0; i < length; i++) {
                nodalForces[i][0] = String.valueOf(ID);
                nodalForces[i][1] = String.valueOf(n);
                nodalForces[i][2] = String.valueOf(decimalFormat(vectorF[i][0]));

                //Instruções para descrever a direção e sentido da carga
                double value = parseDouble(vectorF[i][0]);

                if (j == 0 || j == 1 || j == 2) {
                    nodalForces[i][3] = "KNm/m";
                    if (value > 0) {
                        nodalForces[i][4] = "Positive Moment";
                    } else if (value < 0) {
                        nodalForces[i][4] = "Negative Moment";
                    } else {
                        nodalForces[i][4] = "Moment";
                    }
                }

                if (j == 3 || j == 4) {
                    nodalForces[i][3] = "KN/m";
                    if (value > 0) {
                        nodalForces[i][4] = "Positive Shear";
                    } else if (value < 0) {
                        nodalForces[i][4] = "Negative Shear";
                    } else {
                        nodalForces[i][4] = "Shear";
                    }
                }

                j++;

                //Instruções para definir o número do nó
                if (i != 0 && (i + 1) % 5 == 0) {
                    j = 0;
                    n++;
                }
            }
        } else {
            nodalForces = null;
        }

        return nodalForces;
    }

    /**
     * Método que devolve a lista de reações de apoio para uma estrutura unidimensional
     *
     * @param listOfNodes
     * @param reactions
     * @return
     */
    private static String[][] supportReactions_1D(int[][] listOfNodes, double[][] reactions) {
        String[][] supportReactions;

        if (listOfNodes.length == reactions.length) {
            supportReactions = new String[listOfNodes.length][4];

            for (int i = 0; i < listOfNodes.length; i++) {
                supportReactions[i][0] = String.valueOf(listOfNodes[i][1]);
                supportReactions[i][1] = decimalFormat(reactions[i][0]);
                supportReactions[i][2] = "KN";

                //Instruções para descrever a direção e sentido da reação
                double value = parseDouble(reactions[i][0]);

                if (listOfNodes[i][0] == 1) {
                    if (value > 0) {
                        supportReactions[i][3] = "Horizontal from left to right";
                    } else if (value < 0) {
                        supportReactions[i][3] = "Horizontal from right to left";
                    } else {
                        supportReactions[i][3] = "Horizontal";
                    }
                }

                if (listOfNodes[i][0] == 2) {
                    if (value > 0) {
                        supportReactions[i][3] = "Vertical upwards";
                    } else if (value < 0) {
                        supportReactions[i][3] = "Vertical top-down";
                    } else {
                        supportReactions[i][3] = "Vertical";
                    }
                }
            }
        } else {
            supportReactions = null;
        }

        return supportReactions;
    }

    /**
     * Método que devolve a lista de reações de apoio para uma estrutura bidimensional
     *
     * @param listOfNodes
     * @param reactions
     * @param theory
     * @return
     */
    private static String[][] supportReactions_2D(int[][] listOfNodes, double[][] reactions, String theory) {
        String[][] supportReactions;

        if (listOfNodes.length == reactions.length) {
            supportReactions = new String[listOfNodes.length][4];

            for (int i = 0; i < listOfNodes.length; i++) {
                supportReactions[i][0] = String.valueOf(listOfNodes[i][1]);
                supportReactions[i][1] = decimalFormat(reactions[i][0]);
                if ("Plane Stress".equals(theory)) {
                    supportReactions[i][2] = "KN";
                } else {
                    supportReactions[i][2] = "KN/m";
                }

                //Instruções para descrever a direção e sentido da reação
                double value = parseDouble(reactions[i][0]);

                if (listOfNodes[i][0] == 1) {
                    if (value > 0) {
                        supportReactions[i][3] = "Horizontal from left to right";
                    } else if (value < 0) {
                        supportReactions[i][3] = "Horizontal from right to left";
                    } else {
                        supportReactions[i][3] = "Horizontal";
                    }
                }

                if (listOfNodes[i][0] == 2) {
                    if (value > 0) {
                        supportReactions[i][3] = "Vertical upwards";
                    } else if (value < 0) {
                        supportReactions[i][3] = "Vertical top-down";
                    } else {
                        supportReactions[i][3] = "Vertical";
                    }
                }
            }
        } else {
            supportReactions = null;
        }

        return supportReactions;
    }

    /**
     * Método que devolve a lista de reações de apoio para uma estrutura tridimensional
     *
     * @param listOfNodes
     * @param reactions
     * @return
     */
    private static String[][] supportReactions_3D(int[][] listOfNodes, double[][] reactions) {
        return null;
    }

    /**
     * Método que devolve a lista de reações de apoio para uma estrutura de viga
     *
     * @param listOfNodes
     * @param reactions
     * @return
     */
    private static String[][] supportReactions_Beams(int[][] listOfNodes, double[][] reactions) {
        String[][] supportReactions;

        if (listOfNodes.length == reactions.length) {
            supportReactions = new String[listOfNodes.length][4];

            for (int i = 0; i < listOfNodes.length; i++) {
                supportReactions[i][0] = String.valueOf(listOfNodes[i][1]);
                supportReactions[i][1] = decimalFormat(reactions[i][0]);

                //Instruções para descrever a direção e sentido da reação
                double value = parseDouble(reactions[i][0]);

                if (listOfNodes[i][0] == 1) {
                    supportReactions[i][2] = "KN";
                    if (value > 0) {
                        supportReactions[i][3] = "Vertical upwards";
                    } else if (value < 0) {
                        supportReactions[i][3] = "Vertical top-down";
                    } else {
                        supportReactions[i][3] = "Vertical";
                    }
                }

                if (listOfNodes[i][0] == 2) {
                    supportReactions[i][2] = "KNm";
                    if (value > 0) {
                        supportReactions[i][3] = "Positive Moment";
                    } else if (value < 0) {
                        supportReactions[i][3] = "Negative Moment";
                    } else {
                        supportReactions[i][3] = "Moment";
                    }
                }
            }
        } else {
            supportReactions = null;
        }

        return supportReactions;
    }

    /**
     * Método que devolve a lista de reações de apoio para uma estrutura de barra
     *
     * @param listOfNodes
     * @param reactions
     * @return
     */
    private static String[][] supportReactions_Frames(int[][] listOfNodes, double[][] reactions) {
        String[][] supportReactions;

        if (listOfNodes.length == reactions.length) {
            supportReactions = new String[listOfNodes.length][4];

            for (int i = 0; i < listOfNodes.length; i++) {
                supportReactions[i][0] = String.valueOf(listOfNodes[i][1]);
                supportReactions[i][1] = decimalFormat(reactions[i][0]);

                //Instruções para descrever a direção e sentido da reação
                double value = parseDouble(reactions[i][0]);

                if (listOfNodes[i][0] == 1) {
                    supportReactions[i][2] = "KN";
                    if (value > 0) {
                        supportReactions[i][3] = "Horizontal from left to right";
                    } else if (value < 0) {
                        supportReactions[i][3] = "Horizontal from right to left";
                    } else {
                        supportReactions[i][3] = "Horizontal";
                    }
                }

                if (listOfNodes[i][0] == 2) {
                    supportReactions[i][2] = "KN";
                    if (value > 0) {
                        supportReactions[i][3] = "Vertical upwards";
                    } else if (value < 0) {
                        supportReactions[i][3] = "Vertical top-down";
                    } else {
                        supportReactions[i][3] = "Vertical";
                    }
                }

                if (listOfNodes[i][0] == 3) {
                    supportReactions[i][2] = "KNm";
                    if (value > 0) {
                        supportReactions[i][3] = "Positive Moment";
                    } else if (value < 0) {
                        supportReactions[i][3] = "Negative Moment";
                    } else {
                        supportReactions[i][3] = "Moment";
                    }
                }
            }
        } else {
            supportReactions = null;
        }

        return supportReactions;
    }

    /**
     * Método que devolve a lista de reações de apoio para uma estrutura de grelha
     *
     * @param listOfNodes
     * @param reactions
     * @return
     */
    private static String[][] supportReactions_Grids(int[][] listOfNodes, double[][] reactions) {
        String[][] supportReactions;

        if (listOfNodes.length == reactions.length) {
            supportReactions = new String[listOfNodes.length][4];

            for (int i = 0; i < listOfNodes.length; i++) {
                supportReactions[i][0] = String.valueOf(listOfNodes[i][1]);
                supportReactions[i][1] = decimalFormat(reactions[i][0]);

                //Instruções para descrever a direção e sentido da reação
                double value = parseDouble(reactions[i][0]);

                if (listOfNodes[i][0] == 1) {
                    supportReactions[i][2] = "KN";
                    if (value > 0) {
                        supportReactions[i][3] = "Vertical upwards";
                    } else if (value < 0) {
                        supportReactions[i][3] = "Vertical top-down";
                    } else {
                        supportReactions[i][3] = "Vertical";
                    }
                }

                if (listOfNodes[i][0] == 2) {
                    supportReactions[i][2] = "KNm";
                    if (value > 0) {
                        supportReactions[i][3] = "Positive Moment";
                    } else if (value < 0) {
                        supportReactions[i][3] = "Negative Moment";
                    } else {
                        supportReactions[i][3] = "Moment";
                    }
                }

                if (listOfNodes[i][0] == 3) {
                    supportReactions[i][2] = "KNm";
                    if (value > 0) {
                        supportReactions[i][3] = "Positive Moment";
                    } else if (value < 0) {
                        supportReactions[i][3] = "Negative Moment";
                    } else {
                        supportReactions[i][3] = "Moment";
                    }
                }
            }
        } else {
            supportReactions = null;
        }

        return supportReactions;
    }

    /**
     * Método que devolve a lista de reações de apoio para uma estrutura de laje
     *
     * @param listOfNodes
     * @param reactions
     * @return
     */
    private static String[][] supportReactions_Slabs(int[][] listOfNodes, double[][] reactions) {
        String[][] supportReactions;

        if (listOfNodes.length == reactions.length) {
            supportReactions = new String[listOfNodes.length][4];

            for (int i = 0; i < listOfNodes.length; i++) {
                supportReactions[i][0] = String.valueOf(listOfNodes[i][1]);
                supportReactions[i][1] = decimalFormat(reactions[i][0]);

                //Instruções para descrever a direção e sentido da reação
                double value = parseDouble(reactions[i][0]);

                if (listOfNodes[i][0] == 1) {
                    supportReactions[i][2] = "KN";
                    if (value > 0) {
                        supportReactions[i][3] = "Vertical upwards";
                    } else if (value < 0) {
                        supportReactions[i][3] = "Vertical top-down";
                    } else {
                        supportReactions[i][3] = "Vertical";
                    }
                }

                if (listOfNodes[i][0] == 2) {
                    supportReactions[i][2] = "KNm";
                    if (value > 0) {
                        supportReactions[i][3] = "Positive Moment";
                    } else if (value < 0) {
                        supportReactions[i][3] = "Negative Moment";
                    } else {
                        supportReactions[i][3] = "Moment";
                    }
                }

                if (listOfNodes[i][0] == 3) {
                    supportReactions[i][2] = "KNm";
                    if (value > 0) {
                        supportReactions[i][3] = "Positive Moment";
                    } else if (value < 0) {
                        supportReactions[i][3] = "Negative Moment";
                    } else {
                        supportReactions[i][3] = "Moment";
                    }
                }
            }
        } else {
            supportReactions = null;
        }

        return supportReactions;
    }
}

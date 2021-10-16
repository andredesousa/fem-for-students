/*
 * Este método organiza as coordenadas dos vértices das figuras geométricas
 * A organização das coordenadas é feita no sentido oposto ao dos ponteiros do relógio
 * Com a organização da posição dos vértices pode-se posicionar os respetivos nós
 */

package app.backend;

import java.util.Arrays;

/**
 *
 * @author André de Sousa
 */
public class VerticesCoordinates {

    /**
     * Método para organizar a disposição dos vértices de uma linha
     *
     * @param vertices
     * @return
     */
    public static int[][] line(int[][] vertices) {
        int[][] numberedVertices;

        //Evaluation of the dimensions of the array

        int lines = vertices.length;
        int columns = vertices[0].length;

        //Validation and numbering of the vertices of geometry

        if (lines == 2 && columns == 2) {
            numberedVertices = new int[2][2];

            if (vertices[0][0] <= vertices[1][0]) {
                if (vertices[0][0] < vertices[1][0]) {
                    numberedVertices = vertices;
                } else {
                    if (vertices[0][1] >= vertices[1][1]) {
                        numberedVertices = vertices;
                    } else {
                        numberedVertices[0][0] = vertices[1][0];
                        numberedVertices[0][1] = vertices[1][1];
                        numberedVertices[1][0] = vertices[0][0];
                        numberedVertices[1][1] = vertices[0][1];
                    }
                }
            } else {
                numberedVertices[0][0] = vertices[1][0];
                numberedVertices[0][1] = vertices[1][1];
                numberedVertices[1][0] = vertices[0][0];
                numberedVertices[1][1] = vertices[0][1];
            }
        } else {
            numberedVertices = null;
        }

        return numberedVertices;
    }

    /**
     * Método para organizar a disposição dos vértices de um triângulo
     *
     * @param vertices
     * @return
     */
    public static int[][] triangle(int[][] vertices) {
        int[][] numberedVertices;

        //Evaluation of the dimensions of the array

        int lines = vertices.length;
        int columns = vertices[0].length;

        //Validation and numbering of the vertices of geometry

        if (lines == 3 && columns == 2) {
            numberedVertices = new int[3][2];

            double x1 = vertices[0][0];
            double y1 = vertices[0][1];
            int index1 = 0;
            int index2 = -1;
            int index3 = -1;

            //Instruções para definir o ponto da origem do referencial
            for (int i = 0; i < 3; i++) {
                if (vertices[i][0] < x1) {
                    x1 = vertices[i][0];
                    y1 = vertices[i][1];
                    index1 = i;
                }
                if (vertices[i][0] == x1 && vertices[i][1] < y1) {
                    x1 = vertices[i][0];
                    y1 = vertices[i][1];
                    index1 = i;
                }
            }

            //Instruções para calcular as inclinações das retas
            double inclination, x, y;
            int j = 1;

            double m1 = 0;
            double m2 = 0;

            for (int i = 0; i < 3; i++) {
                if (i != index1) {
                    x = vertices[i][0];
                    y = vertices[i][1];
                    if (j == 1) {
                        inclination = (y - y1) / (x - x1);
                        m1 = Math.atan(inclination);
                        index2 = i;
                    }
                    if (j == 2) {
                        inclination = (y - y1) / (x - x1);
                        m2 = Math.atan(inclination);
                        index3 = i;
                    }
                    j++;
                }
            }

            //Atribuição do índice correcto a cada variável
            if (m1 > m2) {
                int index = index2;
                index2 = index3;
                index3 = index;
            }

            numberedVertices[0][0] = vertices[index1][0];
            numberedVertices[0][1] = vertices[index1][1];
            numberedVertices[1][0] = vertices[index2][0];
            numberedVertices[1][1] = vertices[index2][1];
            numberedVertices[2][0] = vertices[index3][0];
            numberedVertices[2][1] = vertices[index3][1];
        } else {
            numberedVertices = null;
        }

        return numberedVertices;
    }

    /**
     * Método para organizar a disposição dos vértices de um retângulo
     *
     * @param vertices
     * @return
     */
    public static int[][] rectangle(int[][] vertices) {
        int[][] numberedVertices;

        //Evaluation of the dimensions of the array

        int lines = vertices.length;
        int columns = vertices[0].length;

        //Validation and numbering of the vertices of geometry

        if (lines == 4 && columns == 2) {
            int x1, y1, x2, y2, x3, y3, x4, y4;
            numberedVertices = new int[4][2];

            x1 = vertices[0][0];
            y1 = vertices[0][1];
            x2 = vertices[1][0];
            y2 = vertices[1][1];
            x3 = vertices[2][0];
            y3 = vertices[2][1];
            x4 = vertices[3][0];
            y4 = vertices[3][1];

            for (int i = 0; i < 4; i++) {
                if (vertices[i][0] <= x1 && vertices[i][1] <= y1) {
                    x1 = vertices[i][0];
                    y1 = vertices[i][1];
                }
            }
            for (int i = 0; i < 4; i++) {
                if (vertices[i][0] > x1 && vertices[i][1] == y1) {
                    x2 = vertices[i][0];
                    y2 = vertices[i][1];
                }
            }
            for (int i = 0; i < 4; i++) {
                if (vertices[i][0] == x2 && vertices[i][1] > y2) {
                    x3 = vertices[i][0];
                    y3 = vertices[i][1];
                }
            }
            for (int i = 0; i < 4; i++) {
                if (vertices[i][0] == x1 && vertices[i][1] > y1) {
                    x4 = vertices[i][0];
                    y4 = vertices[i][1];
                }
            }

            numberedVertices[0][0] = x1;
            numberedVertices[0][1] = y1;
            numberedVertices[1][0] = x2;
            numberedVertices[1][1] = y2;
            numberedVertices[2][0] = x3;
            numberedVertices[2][1] = y3;
            numberedVertices[3][0] = x4;
            numberedVertices[3][1] = y4;
        } else {
            numberedVertices = null;
        }

        return numberedVertices;
    }

    /**
     * Método para organizar a disposição dos vértices de um quadrilátero
     *
     * @param vertices
     * @return
     */
    public static int[][] quadrilateral(int[][] vertices) {
        int[][] numberedVertices;

        //Evaluation of the dimensions of the array
        int lines = vertices.length;
        int columns = vertices[0].length;

        //Validation and numbering of the vertices of geometry

        if (lines == 4 && columns == 2) {
            numberedVertices = new int[4][2];

            double x1 = vertices[0][0];
            double y1 = vertices[0][1];
            int index1 = 0;
            int index2 = -1;
            int index3 = -1;
            int index4 = -1;

            //Instruções para definir o ponto da origem do referencial
            for (int i = 0; i < 4; i++) {
                if (vertices[i][0] < x1) {
                    x1 = vertices[i][0];
                    y1 = vertices[i][1];
                    index1 = i;
                }
                if (vertices[i][0] == x1 && vertices[i][1] < y1) {
                    x1 = vertices[i][0];
                    y1 = vertices[i][1];
                    index1 = i;
                }
            }

            //Instruções para calcular as inclinações das retas
            double inclination, x, y;

            double m1 = 0;
            double m2 = 0;
            double m3 = 0;

            int j = 1;
            for (int i = 0; i < 4; i++) {
                if (i != index1) {
                    x = vertices[i][0];
                    y = vertices[i][1];
                    if (j == 1) {
                        inclination = (y - y1) / (x - x1);
                        m1 = Math.atan(inclination);
                        index2 = i;
                    }
                    if (j == 2) {
                        inclination = (y - y1) / (x - x1);
                        m2 = Math.atan(inclination);
                        index3 = i;
                    }
                    if (j == 3) {
                        inclination = (y - y1) / (x - x1);
                        m3 = Math.atan(inclination);
                        index4 = i;
                    }
                    j++;
                }
            }

            //Atribuição do índice correcto a cada variável
            double[] vector = { m1, m2, m3 };
            Arrays.sort(vector, 0, vector.length);

            numberedVertices[0][0] = vertices[index1][0];
            numberedVertices[0][1] = vertices[index1][1];

            for (int i = 1; i < 4; i++) {
                int index = -1;
                if (m1 == vector[i - 1]) {
                    index = index2;
                }
                if (m2 == vector[i - 1]) {
                    index = index3;
                }
                if (m3 == vector[i - 1]) {
                    index = index4;
                }

                numberedVertices[i][0] = vertices[index][0];
                numberedVertices[i][1] = vertices[index][1];
            }

            //Cálculo da inclinação da reta que une o vértice 1 ao 2
            double deltaX = (numberedVertices[1][0] - numberedVertices[0][0]);
            double deltaY = (numberedVertices[1][1] - numberedVertices[0][1]);
            double angle = Math.abs(Math.atan(deltaY / deltaX));

            //Validação das coordenadas dos nós do polígono
            if (angle > (Math.PI / 4)) {
                int[][] coordinates = new int[4][2];

                coordinates[0][0] = numberedVertices[1][0];
                coordinates[0][1] = numberedVertices[1][1];
                coordinates[1][0] = numberedVertices[2][0];
                coordinates[1][1] = numberedVertices[2][1];
                coordinates[2][0] = numberedVertices[3][0];
                coordinates[2][1] = numberedVertices[3][1];
                coordinates[3][0] = numberedVertices[0][0];
                coordinates[3][1] = numberedVertices[0][1];

                numberedVertices = coordinates;
            }
        } else {
            numberedVertices = null;
        }

        return numberedVertices;
    }
}

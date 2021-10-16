/*
 * Esta classe fornece o conteúdo para apresentar na Load Table
 * A classe requer a lista de objetos e de cargas estruturais desenhadas
 * O conteúdo é fornecido sob a forma de uma lista do tipo String
 */

package app.backend;

import java.util.ArrayList;

/**
 *
 * @author André de Sousa
 */
public class LoadTable {

    private String type = "";
    private ArrayList<DrawLine> arrayListLines = new ArrayList();
    private ArrayList<DrawPolygon> arrayListPolygons = new ArrayList();
    private ArrayList<DrawLoads> arrayListLoads = new ArrayList();
    private ArrayList<String[]> arrayListLoadTable = new ArrayList();

    /**
     * Método construtor da classe LoadTable
     *
     * @param arrayListLines
     * @param arrayListPolygons
     * @param arrayListLoads
     */
    public LoadTable(ArrayList<DrawLine> arrayListLines, ArrayList<DrawPolygon> arrayListPolygons, ArrayList<DrawLoads> arrayListLoads) {
        this.arrayListLines = arrayListLines;
        this.arrayListPolygons = arrayListPolygons;
        this.arrayListLoads = arrayListLoads;
    }

    /**
     * Método para obter a tabela de cargas estruturais aplicadas na malha
     *
     * @return
     */
    public ArrayList<String[]> getLoadTable() {
        buildTable();
        return arrayListLoadTable;
    }

    /**
     * Método para obter a tabela de cargas estruturais aplicadas na malha
     *
     * @param type
     * @return
     */
    public ArrayList<String[]> getLoadTable(String type) {
        this.type = type;
        buildTable();

        return arrayListLoadTable;
    }

    /**
     * Método para construir as linhas da tabela de cargas estruturais
     */
    private void buildTable() {
        boolean result;

        int[][] loadCoordinates, shapeCoordinates;
        String loadType, shape, unit, value, horizontalvalue, verticalvalue, description;

        for (DrawLoads load : arrayListLoads) {
            loadType = load.loadType;
            loadCoordinates = load.getCoordinates();
            result = false;

            switch (loadType) {
                case "Concentrated Load":
                    if ("2D".equals(type)) {
                        unit = "KN/m";
                    } else {
                        unit = "KN";
                    }
                    break;
                case "Bending Moment":
                    unit = "KNm";
                    break;
                case "Distributed Load":
                    if ("2D".equals(type)) {
                        unit = "KN/m/m";
                    } else {
                        unit = "KN/m";
                    }
                    break;
                case "Axial Load":
                    unit = "KN/m";
                    break;
                case "Planar Load":
                    unit = "KN/m^2";
                    break;
                default:
                    unit = "";
                    break;
            }

            //Verificação da existência de cargas desenhadas nas linhas

            if (result == false && !"Planar Load".equals(loadType)) {
                for (DrawLine line : arrayListLines) {
                    shape = line.shape;
                    shapeCoordinates = line.getCoordinates();

                    result = compareCoordinates(loadType, shape, loadCoordinates, shapeCoordinates);

                    //Adição de uma nova linha com a informação da carga
                    if (result == true) {
                        if ("Concentrated Load".equals(loadType) || "Distributed Load".equals(loadType)) {
                            horizontalvalue = String.valueOf(load.horizontalValue);
                            verticalvalue = String.valueOf(load.verticalValue);

                            if (load.horizontalValue != 0) {
                                description = load.loadType + ", " + load.horizontalSign;
                                arrayListLoadTable.add(new String[] { load.loadName, line.shape, horizontalvalue, unit, description });
                            }
                            if (load.verticalValue != 0) {
                                description = load.loadType + ", " + load.verticalSign;
                                arrayListLoadTable.add(new String[] { load.loadName, line.shape, verticalvalue, unit, description });
                            }
                        } else if ("Thermal Load".equals(loadType)) {
                            if ("1D".equals(type)) {
                                String Tzero = String.valueOf(load.Tzero);
                                arrayListLoadTable.add(new String[] { load.loadName, line.shape, Tzero, "ºC", "Thermal Load" });
                            }
                            if ("Frames".equals(type)) {
                                String Ttop = String.valueOf(load.Ttop);
                                String Tbot = String.valueOf(load.Tbot);
                                arrayListLoadTable.add(
                                    new String[] { load.loadName, line.shape, Ttop, "ºC", "Thermal Load, Higher Fiber" }
                                );
                                arrayListLoadTable.add(new String[] { load.loadName, line.shape, Tbot, "ºC", "Thermal Load, Lower Fiber" });
                            }
                        } else {
                            value = String.valueOf(load.value);
                            description = load.loadType + ", " + load.sign;

                            arrayListLoadTable.add(new String[] { load.loadName, line.shape, value, unit, description });
                        }
                        break;
                    }
                }
            }

            //Verificação da existência de cargas desenhadas nos polígonos

            if (result == false) {
                for (DrawPolygon polygon : arrayListPolygons) {
                    shape = polygon.shape;
                    shapeCoordinates = polygon.getCoordinates();

                    result = compareCoordinates(loadType, shape, loadCoordinates, shapeCoordinates);

                    //Adição de uma nova linha com a informação da carga
                    if (result == true) {
                        if ("Concentrated Load".equals(loadType) || "Distributed Load".equals(loadType)) {
                            horizontalvalue = String.valueOf(load.horizontalValue);
                            verticalvalue = String.valueOf(load.verticalValue);

                            if (load.horizontalValue != 0) {
                                description = load.loadType + ", " + load.horizontalSign;
                                arrayListLoadTable.add(new String[] { load.loadName, polygon.shape, horizontalvalue, unit, description });
                            }
                            if (load.verticalValue != 0) {
                                description = load.loadType + ", " + load.verticalSign;
                                arrayListLoadTable.add(new String[] { load.loadName, polygon.shape, verticalvalue, unit, description });
                            }
                        } else {
                            value = String.valueOf(load.value);
                            description = load.loadType + ", " + load.sign;

                            arrayListLoadTable.add(new String[] { load.loadName, polygon.shape, value, unit, description });
                        }
                        break;
                    }
                }
            }
        }
    }

    /**
     * Método para comparar as coordenadas da carga com as da figura geométrica
     *
     * @param loadType
     * @param shape
     * @param loadCoordinates
     * @param shapeCoordinates
     * @return
     */
    private boolean compareCoordinates(String loadType, String shape, int[][] loadCoordinates, int[][] shapeCoordinates) {
        boolean result = false;
        int counter;

        if ("Concentrated Load".equals(loadType) || "Bending Moment".equals(loadType)) {
            counter = 0;
            for (int[] shapeCoordinate : shapeCoordinates) {
                if (loadCoordinates[0][0] == shapeCoordinate[0]) {
                    if (loadCoordinates[0][1] == shapeCoordinate[1]) {
                        counter++;
                        break;
                    }
                }
            }
            if (counter == 1) {
                result = true;
            }
        }

        if ("Distributed Load".equals(loadType) || "Axial Load".equals(loadType) || "Thermal Load".equals(loadType)) {
            counter = 0;
            for (int[] loadCoordinate : loadCoordinates) {
                for (int[] shapeCoordinate : shapeCoordinates) {
                    if (loadCoordinate[0] == shapeCoordinate[0]) {
                        if (loadCoordinate[1] == shapeCoordinate[1]) {
                            counter++;
                            break;
                        }
                    }
                }
            }

            if (counter == 2) {
                result = true;
            }
        }

        if ("Planar Load".equals(loadType)) {
            if ("Triangle".equals(shape)) {
                counter = 0;
                for (int[] loadCoordinate : loadCoordinates) {
                    for (int[] shapeCoordinate : shapeCoordinates) {
                        if (loadCoordinate[0] == shapeCoordinate[0]) {
                            if (loadCoordinate[1] == shapeCoordinate[1]) {
                                counter++;
                                break;
                            }
                        }
                    }
                }

                if (counter == 3) {
                    result = true;
                }
            }
            if ("Rectangle".equals(shape) || "Quadrilateral".equals(shape)) {
                counter = 0;
                for (int[] loadCoordinate : loadCoordinates) {
                    for (int[] shapeCoordinate : shapeCoordinates) {
                        if (loadCoordinate[0] == shapeCoordinate[0]) {
                            if (loadCoordinate[1] == shapeCoordinate[1]) {
                                counter++;
                                break;
                            }
                        }
                    }
                }

                if (counter == 4) {
                    result = true;
                }
            }
        }

        return result;
    }
}

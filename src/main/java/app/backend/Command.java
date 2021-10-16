/*
 * Esta classe cria um padrão Command para gerir as figuras desenhadas
 * O método undo() permite retroceder até seis operações executadas
 * O método redo() permite avançar até seis operações executadas
 */

package app.backend;

import static app.backend.DrawingMethods.copyDimensionLine;
import static app.backend.DrawingMethods.copyElasticSupport;
import static app.backend.DrawingMethods.copyLine;
import static app.backend.DrawingMethods.copyLoad;
import static app.backend.DrawingMethods.copyNode;
import static app.backend.DrawingMethods.copyPolygon;
import static app.backend.DrawingMethods.copySettlement;
import static app.backend.DrawingMethods.copySupport;

import java.util.ArrayList;

/**
 *
 * @author André de Sousa
 */
public class Command {

    //Variáveis para constrolar o estado do padrão Command
    private int undo = 10;
    private int redo = 1;

    //Listas com os objetos para retroceder e avançar
    private ArrayList<AllShapes> arrayListUndo = new ArrayList();
    private ArrayList<AllShapes> arrayListRedo = new ArrayList();

    /**
     * Método para receber todas as listas de figuras geométricas
     *
     * @param arrayListNodes
     * @param arrayListLines
     * @param arrayListPolygons
     * @param arrayListSupports
     * @param arrayListElasticSupports
     * @param arrayListSettlements
     * @param arrayListLoads
     * @param arrayListDimensionLines
     */
    public void setAllShapes(
        ArrayList<DrawEllipse> arrayListNodes,
        ArrayList<DrawLine> arrayListLines,
        ArrayList<DrawPolygon> arrayListPolygons,
        ArrayList<DrawSupports> arrayListSupports,
        ArrayList<DrawElasticSupports> arrayListElasticSupports,
        ArrayList<DrawSettlements> arrayListSettlements,
        ArrayList<DrawLoads> arrayListLoads,
        ArrayList<DrawDimensionLine> arrayListDimensionLines
    ) {
        //Redimensionamento das listas arrayListUndo e arrayListRedo
        arrayListRedo = new ArrayList();
        setAllShapesInput(
            arrayListNodes,
            arrayListLines,
            arrayListPolygons,
            arrayListSupports,
            arrayListElasticSupports,
            arrayListSettlements,
            arrayListLoads,
            arrayListDimensionLines
        );

        //Gestão do valor das variáveis de controlo
        undo = 10;
        redo = 1;
    }

    /**
     * Método que informa sobre a possibilidade de retroceder
     *
     * @return
     */
    public boolean getStateUndo() {
        return arrayListUndo.size() != 1;
    }

    /**
     * Método que informa sobre a possibilidade de avançar
     *
     * @return
     */
    public boolean getStateRedo() {
        return undo != 10 || redo != 1;
    }

    /**
     * Método para retroceder na lista de objetos desenhados
     */
    public void undo() {
        undo--;
        redo++;
        if (undo <= 1) {
            undo = 1;
        }
        if (redo >= 10) {
            redo = 10;
        }

        int size = arrayListUndo.size();
        if (size > 0) {
            //Adição dos objetos à lista arrayListRedo
            arrayListRedo.add(createListOfShapes(arrayListUndo.get(size - 1)));

            //Adição dos objetos à lista arrayListUndo
            ArrayList<AllShapes> listOfObjects = new ArrayList();

            for (int i = 0; i < size - 1; i++) {
                listOfObjects.add(createListOfShapes(arrayListUndo.get(i)));
            }

            arrayListUndo.clear();
            arrayListUndo.addAll(listOfObjects);
        }
    }

    /**
     * Método para avançar na lista de objetos desenhados
     */
    public void redo() {
        undo++;
        redo--;
        if (undo >= 10) {
            undo = 10;
        }
        if (redo <= 1) {
            redo = 1;
        }

        int size = arrayListRedo.size();
        if (size > 0) {
            //Adição dos objetos à lista arrayListUndo
            arrayListUndo.add(createListOfShapes(arrayListRedo.get(size - 1)));

            //Remoção dos objetos da lista arrayListRedo
            arrayListRedo.remove(size - 1);
        }
    }

    /**
     * Método para obter a lista atual de nós desenhados
     *
     * @return
     */
    public ArrayList<DrawEllipse> getListOfNodes() {
        ArrayList<DrawEllipse> arrayListNodes = new ArrayList();

        int size = arrayListUndo.size();
        if (size > 0) {
            arrayListNodes.addAll(arrayListUndo.get(size - 1).arrayListNodes);
        }

        return arrayListNodes;
    }

    /**
     * Método para obter a lista atual de linhas desenhadas
     *
     * @return
     */
    public ArrayList<DrawLine> getListOfLines() {
        ArrayList<DrawLine> arrayListLines = new ArrayList();

        int size = arrayListUndo.size();
        if (size > 0) {
            arrayListLines.addAll(arrayListUndo.get(size - 1).arrayListLines);
        }

        return arrayListLines;
    }

    /**
     * Método para obter a lista atual de polígonos desenhados
     *
     * @return
     */
    public ArrayList<DrawPolygon> getListOfPolygons() {
        ArrayList<DrawPolygon> arrayListPolygons = new ArrayList();

        int size = arrayListUndo.size();
        if (size > 0) {
            arrayListPolygons.addAll(arrayListUndo.get(size - 1).arrayListPolygons);
        }

        return arrayListPolygons;
    }

    /**
     * Método para obter a lista atual de apoios desenhados
     *
     * @return
     */
    public ArrayList<DrawSupports> getListOfSupports() {
        ArrayList<DrawSupports> arrayListSupports = new ArrayList();

        int size = arrayListUndo.size();
        if (size > 0) {
            arrayListSupports.addAll(arrayListUndo.get(size - 1).arrayListSupports);
        }

        return arrayListSupports;
    }

    /**
     * Método para obter a lista atual de apoios elásticos desenhados
     *
     * @return
     */
    public ArrayList<DrawElasticSupports> getListOfElasticSupports() {
        ArrayList<DrawElasticSupports> arrayListSupports = new ArrayList();

        int size = arrayListUndo.size();
        if (size > 0) {
            arrayListSupports.addAll(arrayListUndo.get(size - 1).arrayListElasticSupports);
        }

        return arrayListSupports;
    }

    /**
     * Método para obter a lista atual de assentamentos de apoio desenhados
     *
     * @return
     */
    public ArrayList<DrawSettlements> getListOfSettlements() {
        ArrayList<DrawSettlements> arrayListSettlements = new ArrayList();

        int size = arrayListUndo.size();
        if (size > 0) {
            arrayListSettlements.addAll(arrayListUndo.get(size - 1).arrayListSettlements);
        }

        return arrayListSettlements;
    }

    /**
     * Método para obter a lista atual de cargas desenhadas
     *
     * @return
     */
    public ArrayList<DrawLoads> getListOfLoads() {
        ArrayList<DrawLoads> arrayListLoads = new ArrayList();

        int size = arrayListUndo.size();
        if (size > 0) {
            arrayListLoads.addAll(arrayListUndo.get(size - 1).arrayListLoads);
        }

        return arrayListLoads;
    }

    /**
     * Método para obter a lista atual das linhas de cotagem desenhadas
     *
     * @return
     */
    public ArrayList<DrawDimensionLine> getListOfDimensionLines() {
        ArrayList<DrawDimensionLine> arrayListDimensionLines = new ArrayList();

        int size = arrayListUndo.size();
        if (size > 0) {
            arrayListDimensionLines.addAll(arrayListUndo.get(size - 1).arrayListDimensionLines);
        }

        return arrayListDimensionLines;
    }

    /**
     * Método para construir as listas de objetos desenhados
     *
     * @param arrayListNodes
     * @param arrayListLines
     * @param arrayListPolygons
     * @param arrayListSupports
     * @param arrayListElasticSupports
     * @param arrayListSettlements
     * @param arrayListLoads
     * @param arrayListDimensionLines
     */
    private void setAllShapesInput(
        ArrayList<DrawEllipse> arrayListNodes,
        ArrayList<DrawLine> arrayListLines,
        ArrayList<DrawPolygon> arrayListPolygons,
        ArrayList<DrawSupports> arrayListSupports,
        ArrayList<DrawElasticSupports> arrayListElasticSupports,
        ArrayList<DrawSettlements> arrayListSettlements,
        ArrayList<DrawLoads> arrayListLoads,
        ArrayList<DrawDimensionLine> arrayListDimensionLines
    ) {
        int size = arrayListUndo.size();

        if (size == 10) {
            ArrayList<AllShapes> listOfObjects = new ArrayList();

            for (int i = 1; i < 10; i++) {
                listOfObjects.add(createListOfShapes(arrayListUndo.get(i)));
            }
            listOfObjects.add(
                new AllShapes(
                    arrayListNodes,
                    arrayListLines,
                    arrayListPolygons,
                    arrayListSupports,
                    arrayListElasticSupports,
                    arrayListSettlements,
                    arrayListLoads,
                    arrayListDimensionLines
                )
            );
            arrayListUndo = listOfObjects;
        } else {
            arrayListUndo.add(
                new AllShapes(
                    arrayListNodes,
                    arrayListLines,
                    arrayListPolygons,
                    arrayListSupports,
                    arrayListElasticSupports,
                    arrayListSettlements,
                    arrayListLoads,
                    arrayListDimensionLines
                )
            );
        }
    }

    /**
     * Método para criar uma nova lista com as formas desenhadas
     *
     * @param shapes
     * @return
     */
    private AllShapes createListOfShapes(AllShapes shapes) {
        ArrayList<DrawEllipse> arrayListNodes = new ArrayList();
        ArrayList<DrawLine> arrayListLines = new ArrayList();
        ArrayList<DrawPolygon> arrayListPolygons = new ArrayList();
        ArrayList<DrawSupports> arrayListSupports = new ArrayList();
        ArrayList<DrawElasticSupports> arrayListElasticSupports = new ArrayList();
        ArrayList<DrawSettlements> arrayListSettlements = new ArrayList();
        ArrayList<DrawLoads> arrayListLoads = new ArrayList();
        ArrayList<DrawDimensionLine> arrayListDimensionLines = new ArrayList();

        arrayListNodes.addAll(shapes.arrayListNodes);
        arrayListLines.addAll(shapes.arrayListLines);
        arrayListPolygons.addAll(shapes.arrayListPolygons);
        arrayListSupports.addAll(shapes.arrayListSupports);
        arrayListElasticSupports.addAll(shapes.arrayListElasticSupports);
        arrayListSettlements.addAll(shapes.arrayListSettlements);
        arrayListLoads.addAll(shapes.arrayListLoads);
        arrayListDimensionLines.addAll(shapes.arrayListDimensionLines);

        return new AllShapes(
            arrayListNodes,
            arrayListLines,
            arrayListPolygons,
            arrayListSupports,
            arrayListElasticSupports,
            arrayListSettlements,
            arrayListLoads,
            arrayListDimensionLines
        );
    }

    /**
     * Classe para criar um objeto que recebe todas as formas desenhadas
     */
    private class AllShapes {

        ArrayList<DrawEllipse> arrayListNodes = new ArrayList();
        ArrayList<DrawLine> arrayListLines = new ArrayList();
        ArrayList<DrawPolygon> arrayListPolygons = new ArrayList();
        ArrayList<DrawSupports> arrayListSupports = new ArrayList();
        ArrayList<DrawElasticSupports> arrayListElasticSupports = new ArrayList();
        ArrayList<DrawSettlements> arrayListSettlements = new ArrayList();
        ArrayList<DrawLoads> arrayListLoads = new ArrayList();
        ArrayList<DrawDimensionLine> arrayListDimensionLines = new ArrayList();

        /**
         * Método construtor da classe AllShapes
         */
        public AllShapes(
            ArrayList<DrawEllipse> arrayListNodes,
            ArrayList<DrawLine> arrayListLines,
            ArrayList<DrawPolygon> arrayListPolygons,
            ArrayList<DrawSupports> arrayListSupports,
            ArrayList<DrawElasticSupports> arrayListElasticSupports,
            ArrayList<DrawSettlements> arrayListSettlements,
            ArrayList<DrawLoads> arrayListLoads,
            ArrayList<DrawDimensionLine> arrayListDimensionLines
        ) {
            this.arrayListNodes.addAll(copyNode(arrayListNodes));
            this.arrayListLines.addAll(copyLine(arrayListLines));
            this.arrayListPolygons.addAll(copyPolygon(arrayListPolygons));
            this.arrayListSupports.addAll(copySupport(arrayListSupports));
            this.arrayListElasticSupports.addAll(copyElasticSupport(arrayListElasticSupports));
            this.arrayListSettlements.addAll(copySettlement(arrayListSettlements));
            this.arrayListLoads.addAll(copyLoad(arrayListLoads));
            this.arrayListDimensionLines.addAll(copyDimensionLine(arrayListDimensionLines));
        }
    }
}

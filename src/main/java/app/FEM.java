/*
 * Esta classe contém o método main() do programa FEM for Students
 * A primeira tarefa executada corresponde à importação do tema do Sistema Operativo
 * A segunda tarefa corresponde à criação de um objeto da classe UserInterface
 */

package app;

import app.frontend.UserInterface;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author André de Sousa
 */
public class FEM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
        }

        new UserInterface().setVisible(true);
    }
}

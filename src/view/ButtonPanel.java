package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import model.UpdateAction;
import controller.ButtonController;

/**
 * This class draws the button panel and reacts to updates from the model.
 *
 * @author Eric Beijer
 */
public class ButtonPanel extends JPanel implements Observer {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    JButton btnNew, btnSolve, btnCheck, btnExit;   // Used buttons.
    JButton btnSize3, btnSize4, btnSize5;
    JCheckBox cbHelp;               // Used check box.
    JToggleButton[] btnNumbers;     // Used toggle buttons.

    /**
     * Constructs the panel and arranges all components.
     */
    public ButtonPanel() {
        super(new BorderLayout());

        JPanel pnlAlign = new JPanel();
        pnlAlign.setLayout(new BoxLayout(pnlAlign, BoxLayout.PAGE_AXIS));
        add(pnlAlign, BorderLayout.NORTH);

        JPanel pnlOptions = new JPanel(new FlowLayout(FlowLayout.LEADING));
        pnlOptions.setBorder(BorderFactory.createTitledBorder(" Options "));
        pnlAlign.add(pnlOptions);

        btnNew = new JButton("New");
        btnNew.setFocusable(false);
        pnlOptions.add(btnNew);
        
        btnSolve = new JButton("Solve");
        btnSolve.setFocusable(false);
        pnlOptions.add(btnSolve);

        btnCheck = new JButton("Check");
        btnCheck.setFocusable(false);
        pnlOptions.add(btnCheck);

        btnExit = new JButton("Exit");
        btnExit.setFocusable(false);
        pnlOptions.add(btnExit);
        
        JPanel pnlSizes = new JPanel(new FlowLayout(FlowLayout.LEADING));
        pnlSizes.setBorder(BorderFactory.createTitledBorder(" Game size "));
        pnlAlign.add(pnlSizes);

        btnSize3 = new JButton("3x3");
        btnSize3.setFocusable(false);
        pnlSizes.add(btnSize3);
        
        btnSize4 = new JButton("4x4");
        btnSize3.setFocusable(false);
        pnlSizes.add(btnSize4);
        
        btnSize5 = new JButton("5x5");
        btnSize5.setFocusable(false);
        pnlSizes.add(btnSize5);
    }

    /**
     * Method called when model sends update notification.
     *
     * @param o     The model.
     * @param arg   The UpdateAction.
     */
    public void update(Observable o, Object arg) {
        switch ((UpdateAction)arg) {
            case NEW_GAME:
            case CHECK:
                break;
	default:
	    break;
        }
    }
    
    

    /**
     * Adds controller to all components.
     *
     * @param buttonController  Controller which controls all user actions.
     */
    public void setController(ButtonController buttonController) {
        btnNew.addActionListener(buttonController);
        btnSolve.addActionListener(buttonController);
        btnCheck.addActionListener(buttonController);
        btnExit.addActionListener(buttonController);
        btnSize3.addActionListener(buttonController);
        btnSize4.addActionListener(buttonController);
        btnSize5.addActionListener(buttonController);
    }
}
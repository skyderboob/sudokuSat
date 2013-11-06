package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import controller.ButtonController;

/**
 * This class draws the button panel and reacts to updates from the model.
 *
 * @author Eric Beijer
 */
public class ButtonPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    JButton btnNew, btnSolve, btnCheck, btnExit, btnImport;
    JRadioButton btnSize3, btnSize4, btnSize5;
    ButtonGroup btnGroup;
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
        
        JPanel pnlFile = new JPanel(new FlowLayout(FlowLayout.LEADING));
        pnlFile.setBorder(BorderFactory.createTitledBorder(" File "));
        pnlAlign.add(pnlFile);
        
        btnImport = new JButton("Import");
        btnImport.setFocusable(false);
        pnlFile.add(btnImport);
        
        btnSize3 = new JRadioButton("3x3");
        btnSize3.setFocusable(false);
        pnlSizes.add(btnSize3);
        
        btnSize4 = new JRadioButton("4x4");
        btnSize3.setFocusable(false);
        pnlSizes.add(btnSize4);
        
        btnSize5 = new JRadioButton("5x5");
        btnSize5.setFocusable(false);
        pnlSizes.add(btnSize5);
        
        btnGroup = new ButtonGroup();
        btnGroup.add(btnSize3);
        btnGroup.add(btnSize4);
        btnGroup.add(btnSize5);
        btnSize3.setSelected(true);
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
        btnImport.addActionListener(buttonController);
        for (Enumeration<AbstractButton> buttons = btnGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            button.addActionListener(buttonController);
        }
    }
}
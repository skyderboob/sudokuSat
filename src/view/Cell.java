package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * This class represents a field on the SudokuPanel.
 * 
 * @author Eric Beijer
 */
public class Cell extends JTextField {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int row;
    private int col;
    public boolean hasChanged = false;

    /***
     * Contructor khởi tạo ô
     * @param row
     * @param col
     * @param size kích thước của sudoku
     */
    public Cell(int row, int col, int size) {
	super("", CENTER);
	this.row = row;
	this.col = col;

	((AbstractDocument) getDocument()).setDocumentFilter(new NumberOnlyFilter(size));
	setHorizontalAlignment(JTextField.CENTER);
	setPreferredSize(new Dimension(30, 30));
	setBorder(BorderFactory.createLineBorder(Color.GRAY));
	setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
	setOpaque(true);
    }

    /**
     * Sets number and foreground color according to userInput.
     * 
     * @param number
     *            Number to be set.
     * @param userInput
     *            Boolean indicating number is user input or not.
     */
    public void setNumber(int number, boolean userInput) {
	setForeground(userInput ? Color.BLUE : Color.BLACK);
	setText(number > 0 ? number + "" : "");
	setEditable(userInput ? true : false);
    }
    
    public int getRow() {
	return row;
    }

    public int getColumn() {
	return col;
    }

    public class NumberOnlyFilter extends DocumentFilter {
	public int maxNumber;

	public NumberOnlyFilter(int size) {
	    maxNumber = size;
	}

	public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr)
		throws BadLocationException {
	    StringBuilder sb = new StringBuilder();
	    sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
	    sb.insert(offset, text);
	    if (!containsOnlyNumbers(sb.toString()))
		return;
	    fb.insertString(offset, text, attr);
	    hasChanged = true;
	}

	public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attr)
		throws BadLocationException {
	    StringBuilder sb = new StringBuilder();
	    sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
	    sb.replace(offset, offset + length, text);
	    if (!containsOnlyNumbers(sb.toString()))
		return;
	    fb.replace(offset, length, text, attr);
	    hasChanged = true;
	}

	/**
	 * Tùy vào kích thước của sudoku mà ô sẽ chỉ giới hạn cho nhập số phù hợp
	 */
	public boolean containsOnlyNumbers(String text) {
	    if (text.equals("")) return true;
	    boolean isNumber = text.matches("[0-9]([0-9])?");
	    if (isNumber) {
		int value = Integer.parseInt(text);
		return ((value > 0) && (value <= maxNumber)) ? true : false;
	    } else
		return false;
	}
    }
}
package swingUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This class defines the text panel
 * @author SImon Mor
 *
 */
public class TextPanel extends JPanel{
	private static JTextArea textArea;
	
	public TextPanel() {
		textArea = new JTextArea();
		Dimension dim = getPreferredSize();
		dim.width = 400;
		setPreferredSize(dim);
		setLayout(new BorderLayout());
		add(new JScrollPane(textArea), BorderLayout.CENTER);
	}
	
	
	
	public static void appendText(String text) {
		textArea.append(text);
	}
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class TestFrame extends JPanel
		implements ComponentListener, ItemListener {
	static JFrame frame;
	JTextArea display;
	JLabel label;
	String newline = "\n";

	public TestFrame() {
		super(new BorderLayout());

		display = new JTextArea();
		display.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(display);
		scrollPane.setPreferredSize(new Dimension(350, 200));

		JPanel panel = new JPanel(new BorderLayout());
		label = new JLabel("This is a label", JLabel.CENTER);
		label.addComponentListener(this);
		panel.add(label, BorderLayout.CENTER);

		JCheckBox checkbox = new JCheckBox("Label visible", true);
		checkbox.addItemListener(this);
		checkbox.addComponentListener(this);
		panel.add(checkbox, BorderLayout.PAGE_END);
		panel.addComponentListener(this);

		add(scrollPane, BorderLayout.CENTER);
		add(panel, BorderLayout.PAGE_END);
		frame.addComponentListener(this);
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		frame = new JFrame("ComponentEventDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		JComponent newContentPane = new TestFrame();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			label.setVisible(true);

			// Need to revalidate and repaint, or else the label
			// will probably be drawn in the wrong place.
			label.revalidate();
			label.repaint();
		} else {
			label.setVisible(false);
		}
	}

	protected void displayMessage(String message) {
		// If the text area is not yet realized, and
		// we tell it to draw text, it could cause
		// a text/AWT tree deadlock. Our solution is
		// to ensure that the text area is realized
		// before attempting to draw text.
		// if (display.isShowing()) {
		display.append(message + newline);
		display.setCaretPosition(display.getDocument().getLength());
		// }
	}

	public void componentHidden(ComponentEvent e) {
		displayMessage(e.getComponent().getClass().getName() + " --- Hidden");
	}

	public void componentMoved(ComponentEvent e) {
		displayMessage(e.getComponent().getClass().getName() + " --- Moved");
	}

	public void componentResized(ComponentEvent e) {
		displayMessage(e.getComponent().getClass().getName() + " --- Resized ");
	}

	public void componentShown(ComponentEvent e) {
		displayMessage(e.getComponent().getClass().getName() + " --- Shown");

	}
}

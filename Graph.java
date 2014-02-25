import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;

import java.awt.*;
import java.awt.event.*;

class Root extends JFrame {
    public Root() {
	super("JGraph ver 0.1");

	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	build(getContentPane());

	// display the windows
	// pack();
	setVisible(true);

	getRootPane().
	    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
	    put(KeyStroke.getKeyStroke("ESCAPE"), "quit");

	getRootPane().getActionMap().put("quit", new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
		    System.out.println("Bye Bye");
		    System.exit(0);
		}
	    });
    }

    MyTable table;
    public void build(Container panel) {
	panel.setLayout(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();
	GridBagConstraints _gbc = new GridBagConstraints();

	// gbc_def
	// _gbc.gridx=0; _gbc.gridy=0;
	// _gbc.insets= new Insets(0, 0, 0, 0);
	_gbc.gridwidth = 1;
	_gbc.gridheight = 1;
	_gbc.weightx = 0;
	_gbc.weighty = 0;
	_gbc.fill = GridBagConstraints.NONE;
	_gbc.anchor = GridBagConstraints.FIRST_LINE_START;

	gbc = _gbc;

	JCheckBox join_pts = new JCheckBox("Join Points", true);
	panel.add(join_pts, gbc);

	JCheckBox pts_show = new JCheckBox("Show Point", true);
	gbc.gridx = 1;
	panel.add(pts_show, gbc);

	JCheckBox pts_label = new JCheckBox("Point Label");
	gbc.gridx = 2;
	panel.add(pts_label, gbc);

	JButton clear = new JButton("Clear");
	gbc.gridx = 3;
	gbc.anchor = GridBagConstraints.LAST_LINE_END;
	gbc.insets = new Insets(2, 10, 0, 0);
	panel.add(clear, gbc);

	JButton del = new JButton("Delete Row");
	gbc.gridx = 4;
	gbc.anchor = GridBagConstraints.LAST_LINE_END;
	gbc.insets = new Insets(2, 10, 0, 0);
	panel.add(del, gbc);

	table = new MyTable();
	gbc.gridx = 0;
	gbc.gridy = 1;
	gbc.gridwidth = 4;
	gbc.gridheight = 1;
	gbc.weightx = 1;
	gbc.weighty = 1;
	gbc.insets = new Insets(0, 0, 0, 0);

	gbc.fill = GridBagConstraints.BOTH;
	gbc.anchor = GridBagConstraints.CENTER;
	panel.add(table, gbc);

	clear.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
		    table.canvas.data.clear();
		    table.dtm.setRowCount(0);
		    repaint();
		}});

	del.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent arg0) {
		    if (table.table.getSelectedRow() < 0) return;
		    int row_no = table.table.getSelectedRow();
		    table.dtm.removeRow(table.table.getSelectedRow());
		    table.canvas.data.remove(row_no);
		    repaint();
		}});

	join_pts.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    table.canvas.join = !table.canvas.join;
		    repaint();
		}
	    });

	pts_show.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    table.canvas.point = !table.canvas.point;
		    repaint();
		}
	    });

	pts_label.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    table.canvas.label = !table.canvas.label;
		    repaint();
		}
	    });
    }
}

public class Graph {
    public static void main(String[] args) {
	Root root = new Root();
	root.setSize(600, 462);
	root.setLocation(350, 150);
    }
}

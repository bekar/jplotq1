import java.awt.*;
import java.awt.event.*;

import java.util.Random;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class MyTable extends JPanel {
    JTextField xentry, yentry;
    JTable tbl;
    MyCanvas canvas;
    boolean rand_fill = true;

    Random seed = new Random();
    public String[] colNames = { "x-axis", "y-axis", };
    public Object[][] data;

    DefaultTableModel dtm = new DefaultTableModel(data, colNames);

    public MyTable() {
	build();
    }

    public void build() {
	setLayout(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();

	canvas = new MyCanvas();
	gbc.gridwidth = 4; gbc.gridheight = 3;
	gbc.weightx = 1; gbc.weighty = 1;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.anchor = GridBagConstraints.CENTER;
	add(canvas, gbc);

	JButton clear_btn = new JButton("Clear");
	gbc.gridx = 4; gbc.gridy = 0;
	gbc.weightx = 0; gbc.weighty = 0;
	gbc.gridwidth = 1; gbc.gridheight = 1;
	gbc.fill = GridBagConstraints.HORIZONTAL;
	add(clear_btn, gbc);

	JButton pop_btn = new JButton(" Pop ");
	gbc.gridx = 5; gbc.gridy = 0;
	gbc.insets = new Insets(0, 0, 0, 1);
	add(pop_btn, gbc);

	tbl = new JTable(dtm);
	tbl.setPreferredScrollableViewportSize(new Dimension(100, 80));
	JScrollPane scrollPane = new JScrollPane(tbl);
	tbl.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

	gbc.gridx = 4; 	gbc.gridy = 2;
	gbc.weightx = 0; gbc.weighty = 0;
	gbc.gridwidth = 2; gbc.gridheight = 1;
	gbc.insets = new Insets(0, 0, 0, 0);
	gbc.fill = GridBagConstraints.BOTH;
	add(scrollPane, gbc);

	JLabel label = new JLabel("x: ");
	gbc.gridwidth = 1; gbc.gridheight = 1;
	gbc.gridx = 2;	gbc.gridy = 3;
	gbc.weightx = 1; gbc.weighty = 0;
	gbc.fill = GridBagConstraints.NONE;
	gbc.anchor = GridBagConstraints.LINE_END;
	add(label, gbc);

	label = new JLabel("y: ");
	gbc.gridy = 4;
	add(label, gbc);

	xentry = new JTextField();
	xentry.setText("0.0");
	gbc.gridx = 3; gbc.gridy = 3;
	gbc.weightx = 1; gbc.weighty = 0;
	gbc.fill = GridBagConstraints.HORIZONTAL;
	add(xentry, gbc);

	yentry = new JTextField();
	yentry.setText("0.0");
	gbc.gridy = 4;
	add(yentry, gbc);

	JButton add_btn = new JButton("Add");
	gbc.gridx = 4; gbc.gridy = 3;
	gbc.weightx = 0; gbc.weighty = 0;
	gbc.gridwidth = 2; gbc.gridheight = 2;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.anchor = GridBagConstraints.CENTER;
	add(add_btn, gbc);

	JCheckBox rand_gen = new JCheckBox("Random Generate", true);
	gbc.gridx = 0; gbc.gridy = 3;
	gbc.gridwidth = 1; gbc.gridheight = 1;
	add(rand_gen, gbc);

	add_btn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    add_data_point();
		}
	    });

	pop_btn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    int row_no = tbl.getRowCount()-1;
		    if(row_no < 0) return;
		    dtm.removeRow(row_no);
		    canvas.data.remove(row_no);
		    repaint();
		    xentry.setText(String.valueOf(tbl.getRowCount()));
		}
	    });

	rand_gen.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    rand_fill = !rand_fill;
		}
	    });

	clear_btn.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
		    canvas.data.clear();
		    dtm.setRowCount(0);
		    repaint();
		    xentry.setText(String.valueOf(tbl.getRowCount()));
		}});


	// int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
	// InputMap inputMap = tbl.getInputMap(condition);
	// ActionMap actionMap = tbl.getActionMap();

	// // DELETE is a String constant that for me was defined as "Delete"
	// inputMap.put(KeyStroke.getKeyStroke("DELETE"), "delete");
	// actionMap.put("delete", new AbstractAction() {
	// 	public void actionPerformed(ActionEvent e) {
	// 	    // TODO: do deletion action here
	// 	    System.out.println("Delete the row");
	// 	}});

	// tbl.addActionListener(new ActionListener() {
	// 	public void actionPerformed(ActionEvent e) {
	// 	    if (tbl.getSelectedRow() < 0) return;
	// 	    int row_no = tbl.getSelectedRow();
	// 	    dtm.removeRow(tbl.getSelectedRow());
	// 	    canvas.data.remove(row_no);
	// 	    repaint();
	// 	}});

    }

    public void add_data_point() {
	String sx = xentry.getText();
	String sy = yentry.getText();

	int _x = 0;
	int _y = 0;

	if (sx != "")
	    _x = (int) Double.parseDouble(sx);
	if (sy != "")
	    _y = (int) Double.parseDouble(sy);
	Object[] newdata = { _x, _y };

	dtm.addRow(newdata);

	if (rand_fill) {
	    xentry.setText(String.valueOf(tbl.getRowCount()));
	    yentry.setText(String.valueOf(50 * seed.nextDouble()));
	}

	canvas.data.add(_y);
	repaint();
    }
}

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

class MyTable extends JPanel {
    JTextField xentry, yentry;
    JTable table;
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
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.gridwidth = 4;
	gbc.gridheight = 1;
	gbc.weightx = 1;
	gbc.weighty = 1;
	// gbc.insets= new Insets(0, 0, 0, 0);
	gbc.fill = GridBagConstraints.BOTH;
	gbc.anchor = GridBagConstraints.CENTER;
	add(canvas, gbc);

	table = new JTable(dtm);
	table.setPreferredScrollableViewportSize(new Dimension(100, 80));
	// Create the scroll pane and add the table to it.
	JScrollPane scrollPane = new JScrollPane(table);
	table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

	gbc.gridx = 4;
	gbc.gridy = 0;
	gbc.gridwidth = 4;
	gbc.gridheight = 1;
	gbc.weightx = 0;
	gbc.weighty = 0;
	gbc.anchor = GridBagConstraints.CENTER;
	gbc.fill = GridBagConstraints.VERTICAL;
	add(scrollPane, gbc);

	JLabel label = new JLabel("x: ");
	gbc.gridx = 2;
	gbc.gridy = 1;
	gbc.gridwidth = 1;
	gbc.gridheight = 1;
	gbc.weightx = 1;
	gbc.weighty = 0;
	gbc.fill = GridBagConstraints.NONE;
	gbc.anchor = GridBagConstraints.LINE_END;
	add(label, gbc);

	label = new JLabel("y: ");
	gbc.gridx = 2;
	gbc.gridy = 2;
	add(label, gbc);

	xentry = new JTextField();
	xentry.setText("0.0");
	gbc.gridx = 3;
	gbc.gridy = 1;
	gbc.gridwidth = 1;
	gbc.gridheight = 1;
	gbc.fill = GridBagConstraints.HORIZONTAL;
	gbc.weightx = 1;
	gbc.weighty = 0;
	add(xentry, gbc);

	yentry = new JTextField();
	yentry.setText("0.0");
	gbc.gridx = 3;
	gbc.gridy = 2;
	add(yentry, gbc);

	JButton add_btn = new JButton("Add");
	gbc.gridx = 4;
	gbc.gridy = 1;
	gbc.gridwidth = 4;
	gbc.gridheight = 2;
	gbc.weightx = 0;
	gbc.weighty = 0;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.anchor = GridBagConstraints.CENTER;
	add(add_btn, gbc);

	JCheckBox rand_gen = new JCheckBox("Random Generate", true);
	gbc.gridx = 0;
	gbc.gridy = 1;
	gbc.gridwidth = 1;
	gbc.gridheight = 1;
	add(rand_gen, gbc);

	add_btn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    add_data_point();
		}
	    });

	rand_gen.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    rand_fill = !rand_fill;
		}
	    });
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
	    xentry.setText(String.valueOf(100 * seed.nextDouble()));
	    yentry.setText(String.valueOf(50 * seed.nextDouble()));
	}

	canvas.data.add(_x);
	repaint();
    }
}

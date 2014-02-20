import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.geom.*;

class MyData extends JPanel {
    JTextField xentry, yentry;
    JTable table;
    String[] columnNames = {"x-axis", "y-axis",};
    Object[][] data = {
	{5, 10},
	{15, 20},
	{5, 120},
	{65, 10},
	{25, 40}
    };

    public MyData() {
	//setBorder(BorderFactory.createEtchedBorder());
	build();
    }

    public void build() {
	setLayout(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();

        table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(100, 70));
        //table.setFillsViewportHeight(true);
        //table.setFillsViewportWidth(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

	gbc.anchor=GridBagConstraints.FIRST_LINE_START;
	gbc.gridx=0; gbc.gridy=0;
	gbc.gridwidth = 1; gbc.gridheight = 4;
	gbc.fill=GridBagConstraints.BOTH;
        add(scrollPane, gbc);

	JLabel label = new JLabel("x: ");
	gbc.gridx=1; gbc.gridy=0;
	gbc.gridwidth = 1; gbc.gridheight = 1;
	gbc.fill=GridBagConstraints.HORIZONTAL;
	gbc.insets = new Insets(2,10,2,10);
	add(label, gbc);

	label = new JLabel("y: ");
	gbc.gridx=1; gbc.gridy=1;
	add(label, gbc);

	xentry = new JTextField();
	gbc.gridx=2; gbc.gridy=0;
	gbc.weightx = 1; gbc.weighty = 0;
	gbc.fill=GridBagConstraints.HORIZONTAL;
	add(xentry, gbc);

	yentry = new JTextField();
	gbc.gridx=2; gbc.gridy=1;
	add(yentry, gbc);

        JButton add = new JButton("Add");
	gbc.gridx=3; gbc.gridy=0;
	gbc.insets = new Insets(0,0,0,0);
	gbc.gridwidth = 1; gbc.gridheight = 2;
	gbc.fill=GridBagConstraints.VERTICAL;
	gbc.anchor=GridBagConstraints.FIRST_LINE_START;
	add(add, gbc);

        add.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    add_data_point();
		}
	    });
    }

    public void add_data_point(){
	String sx = xentry.getText();
	String sy = yentry.getText();

        // if (s.length() <= 0) {
        //     message("Nothing to search");
        //     return;
        // }
	double _x=0;
	double _y=0;

	if (sx != "") _x = Double.parseDouble(sx);
	if (sy != "") _y = Double.parseDouble(sy);

	DefaultTableModel model = (DefaultTableModel) table.getModel();
	model.addRow(new Object[]{_x, _y});
    }
}

class MyCanvas extends JPanel {
    int[] data = {
        21, 14, 18, 03, 86, 88, 74, 87, 54, 77,
        61, 55, 48, 60, 49, 36, 38, 27, 20, 18
    };
    final int PAD = 20;

    public MyCanvas() {
	setBorder(BorderFactory.createEtchedBorder());
    }

    public void build() {
    }

    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();

        //y-axis
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h-PAD));

        //x-axis
        g2.draw(new Line2D.Double(PAD, h-PAD, w-PAD, h-PAD));
        double xInc = (double)(w - 2*PAD)/(data.length-1);
        double scale = (double)(h - 2*PAD)/getMax();

	//label-maker
	Font font = g2.getFont();
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics lm = font.getLineMetrics("0", frc);
        float sh = lm.getAscent() + lm.getDescent();

	//y-label
	String ylabel= "y-axis";
	float sy = PAD + ((h - 2*PAD) - ylabel.length()*sh)/2 + lm.getAscent();
        for(int i = 0; i < ylabel.length(); i++) {
            String letter = String.valueOf(ylabel.charAt(i));
            float sw = (float)font.getStringBounds(letter, frc).getWidth();
            float sx = (PAD - sw)/2;
            g2.drawString(letter, sx, sy);
            sy += sh;
        }

	//x-label
        String xlabel = "x-axis";
        sy = h - PAD + (PAD - sh)/2 + lm.getAscent();
        float sw = (float)font.getStringBounds(xlabel, frc).getWidth();
        float sx = (w - sw)/2;
        g2.drawString(xlabel, sx, sy);

        // Mark data points.
        g2.setPaint(Color.red);
        for(int i = 0; i < data.length; i++) {
            double x = PAD + i*xInc;
            double y = h - PAD - scale*data[i];
            g2.fill(new Ellipse2D.Double(x-2, y-2, 4, 4));
        }
    }

    private int getMax() {
        int max = -Integer.MAX_VALUE;
        for(int i = 0; i < data.length; i++) {
            if(data[i] > max)
                max = data[i];
        }
        return max;
    }

    public void clear_canvas() {
	System.out.println("clear canvas");
	repaint();
    }
}

class Root extends JFrame{
    public Root() {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	build(getContentPane());

	//display the windows
	//pack();
	setVisible(true);

	getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "quit");
	getRootPane().getActionMap().put("quit", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
		    System.out.println("Bye Bye");
		    System.exit(0);
		}
	    });
    }

    MyCanvas canvas;
    public void build(Container panel) {
	panel.setLayout(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();
	GridBagConstraints _gbc = new GridBagConstraints();

	//gbc_def
	//_gbc.gridx=0; _gbc.gridy=0;
	//_gbc.insets= new Insets(0, 0, 0, 0);
	_gbc.gridwidth = 1; _gbc.gridheight = 1;
	_gbc.weightx = 0; _gbc.weighty = 0;
	_gbc.fill=GridBagConstraints.NONE;
	_gbc.anchor=GridBagConstraints.FIRST_LINE_START;

	JLabel label = new JLabel("Graph Demo");
	gbc=_gbc;
	panel.add(label, gbc);

	JCheckBox join_pts = new JCheckBox("Join Points");
	gbc.gridy=1;
	panel.add(join_pts, gbc);

	JCheckBox pts_label = new JCheckBox("Show Point Value");
	gbc.gridx=1;
	panel.add(pts_label, gbc);

        JButton clear = new JButton("Clear");
	gbc.gridx=2;
	gbc.anchor=GridBagConstraints.LAST_LINE_END;
	panel.add(clear, gbc);

	canvas = new MyCanvas();
	gbc.gridx=0; gbc.gridy=2;
	gbc.gridwidth = 3; gbc.gridheight = 1;
	gbc.weightx = 1; gbc.weighty = 1;
	gbc.insets= new Insets(10, 25, 10, 25);
	gbc.fill=GridBagConstraints.BOTH;
	gbc.anchor=GridBagConstraints.CENTER;
	panel.add(canvas, gbc);

	MyData mydata = new MyData();
	gbc=_gbc;
	gbc.gridy=3;
	gbc.weightx = 0; gbc.weighty = 0;
	gbc.gridwidth = 3; gbc.gridheight = 1;
	gbc.fill=GridBagConstraints.BOTH;
	//gbc.anchor=GridBagConstraints.FIRST_LINE_START;
	panel.add(mydata, gbc);

	clear.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e){
		    canvas.clear_canvas();

		}
	    }
	    );

        join_pts.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    join_points();
		}
	    });
    }

    public void join_points() {
	JOptionPane.showMessageDialog(this, "Join Point button pressed");
    }
}

public class graph{
    public static void main(String[] args) {
        Root root = new Root();
	root.setTitle("Plot the Graph");
	root.setSize(600, 462);
	root.setLocation(350,150);
    }
}

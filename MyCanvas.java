import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


class MyCanvas extends JPanel implements Observer{
    public ArrayList<Integer> data = new ArrayList<Integer>();

    boolean join = true;
    boolean point = true;
    boolean label = false;

    final int PAD = 40;
    private Graphics2D g2;

    public void nullData() { // is required for cleaning
	this.data = null;
    }

    public MyCanvas() {
	setBorder(BorderFactory.createEtchedBorder());
	setBackground(Color.white);
    }

    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	g2 = (Graphics2D) g;
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
	int w = getWidth();
	int h = getHeight();

	// y-axis
	g2.draw(new Line2D.Double(PAD, PAD, PAD, h - PAD));

	// x-axis
	g2.draw(new Line2D.Double(PAD, h - PAD, w - PAD, h - PAD));

	double xInc = PAD, scale = 0;

	// label-maker
	Font font = g2.getFont();
	FontRenderContext frc = g2.getFontRenderContext();
	LineMetrics lm = font.getLineMetrics("0", frc);
	float sh = lm.getAscent() + lm.getDescent();

	// y-label
	String ylabel = "y-axis";
	float sy = PAD + ((h - 2 * PAD) - ylabel.length() * sh) / 2
	    + lm.getAscent();
	for (int i = 0; i < ylabel.length(); i++) {
	    String letter = String.valueOf(ylabel.charAt(i));
	    float sw = (float) font.getStringBounds(letter, frc).getWidth();
	    float sx = (PAD - sw) / 2;
	    g2.drawString(letter, sx, sy);
	    sy += sh;
	}

	// x-label
	String xlabel = "x-axis";
	sy = h - PAD + (PAD - sh) / 2 + lm.getAscent();
	float sw = (float) font.getStringBounds(xlabel, frc).getWidth();
	float sx = (w - sw) / 2;
	g2.drawString(xlabel, sx, sy);


	double x, y, x_ = 0, y_ = 0;

	if (data.size() > 1) // if data set contains only one data
	    xInc = (double) (w - 2 * PAD) / (data.size() - 1);

	if (getMax() > 0) // if the data set contains only zero
	    scale = (double) (h - 2 * PAD) / getMax();

	// Mark data points.
	for (int i = 0; i < data.size(); i++) {
	    x = PAD + i * xInc;
	    y = h - PAD - scale * data.get(i);
	    if (point) {
		g2.setPaint(Color.red);
		g2.fill(new Ellipse2D.Double(x - 2, y - 2, 4, 4));
	    }

	    if (join) {
		if (i < data.size() - 1) {
		    x_ = PAD + (i + 1) * xInc;
		    y_ = h - PAD - scale * data.get(i + 1);
		}
		g2.setPaint(Color.green.darker());
		if (data.size() > 1) // draw line only if more than one data
		    g2.draw(new Line2D.Double(x, y, x_, y_));
	    }

	    if (label) {
		String l = "(" + String.valueOf(i) + ",";
		l += String.valueOf(data.get(i)) + ")";
		g2.setPaint(Color.black);
		g2.drawString(l, (float) x + 3, (float) y - 3);
	    }
	}
    }

    private int getMax() {
	int max = -Integer.MAX_VALUE;
	for (int i = 0; i < data.size(); i++) {
	    if (data.get(i) > max)
		max = data.get(i);
	}
	return max;
    }

    public void dataUpdate(Data d) {
	repaint();
    }
}

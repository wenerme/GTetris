package game.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;

/**
 * 带尖角的边框
 * @see http://stackoverflow.com/questions/15025092/border-with-rounded-corners-transparency
 */
class BubbleBorder extends AbstractBorder
{
	private static final long serialVersionUID = 1L;
	private Color color;
	private int thickness = 4;
	private int radii = 8;
	private int pointerSize = 7;
	private Insets insets = null;
	private BasicStroke stroke = null;
	private int strokePad;
	private double pointerPadPercent = 0.5;
	int pointerSide = SwingConstants.BOTTOM;
	RenderingHints hints;

	BubbleBorder(Color color)
	{
		this(color, 4, 8, 7, SwingConstants.BOTTOM);
	}

	BubbleBorder(Color color, int thickness, int radii, int pointerSize, int pointerSide)
	{
		this.thickness = thickness;
		this.radii = radii;
		this.pointerSize = pointerSize;
		this.color = color;
		this.pointerSide = pointerSide;

		stroke = new BasicStroke(thickness);
		strokePad = thickness / 2;

		hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// 这里需要 radii/2 不然会有多出来的边距
		int pad = radii/2 + strokePad;
		
		int pointerSidePad = pad + pointerSize + strokePad;
		// 根据不同的方向设置不同的Padding
		int left, right, bottom, top;
		left = right = bottom = top = pad;
		switch (pointerSide)
		{
			case SwingConstants.TOP:
				top = pointerSidePad;
				break;
			case SwingConstants.LEFT:
				left = pointerSidePad;
				break;
			case SwingConstants.RIGHT:
				right = pointerSidePad;
				break;
			default:
			case SwingConstants.BOTTOM:
				bottom = pointerSidePad;
			break;
		}
		insets = new Insets(top, left, bottom, right);
		
	}

	@Override
	public Insets getBorderInsets(Component c)
	{
		return insets;
	}

	@Override
	public Insets getBorderInsets(Component c, Insets insets)
	{
		return getBorderInsets(c);
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
	{
		Graphics2D g2 = (Graphics2D) g;
		// 修正背景色的问题
		g2.setBackground(c.getBackground());
		
		int bottomLineY = height - thickness - pointerSize;

		RoundRectangle2D.Double bubble;
		Polygon pointer = new Polygon();
		
		// 初始范围
		{
			// 设置圆角矩形
			int rx, ry, rw, rh;
			rx = ry = strokePad;
			rw = width - thickness;
			rh = height - thickness;
			
			
			switch (pointerSide)
			{
				case SwingConstants.LEFT:
					rx += pointerSize;
				case SwingConstants.RIGHT:
					rw -= pointerSize;
					break;
					
				case SwingConstants.TOP:
					ry += pointerSize;
				case SwingConstants.BOTTOM:
				default:
					rh -= pointerSize;
				break;
			}
			
			bubble = new RoundRectangle2D.Double(rx,ry,rw,rh,radii, radii);
			
			// 计算偏移
			int pointerPad;
			
			if (pointerSide == SwingConstants.LEFT || pointerSide == SwingConstants.RIGHT)
			{
				pointerPad = (int) (pointerPadPercent * (height-radii*2-pointerSize));
			}else {
				pointerPad = (int) (pointerPadPercent * (width-radii*2-pointerSize));
			}

			// 设置三角
			int basePad = strokePad + radii + pointerPad;
			
			switch (pointerSide)
			{

				case SwingConstants.LEFT:
					pointer.addPoint(rx, basePad);// top
					pointer.addPoint(rx, basePad+pointerSize);// bottom
					pointer.addPoint(strokePad, basePad+pointerSize/2);
					break;
				case SwingConstants.RIGHT:
					pointer.addPoint(rw, basePad);// top
					pointer.addPoint(rw, basePad+pointerSize);// bottom
					pointer.addPoint(width-strokePad, basePad+pointerSize/2);
					break;
					
				case SwingConstants.TOP:
					pointer.addPoint(basePad, ry);// left
					pointer.addPoint(basePad + pointerSize, ry);// right
					pointer.addPoint(basePad + (pointerSize / 2), strokePad);
					break;
				default:
				case SwingConstants.BOTTOM:
					pointer.addPoint(basePad, rh);// left
					pointer.addPoint(basePad + pointerSize, rh);// right
					pointer.addPoint(basePad + (pointerSize / 2), height - strokePad);
				break;
			}			
		}
		
		Area area = new Area(bubble);
		area.add(new Area(pointer));

		g2.setRenderingHints(hints);

		Area spareSpace = new Area(new Rectangle(0, 0, width, height));
		spareSpace.subtract(area);
		g2.setClip(spareSpace);
		g2.clearRect(0, 0, width, height);
		
		g2.setClip(null);

		g2.setColor(color);
		g2.setStroke(stroke);
		g2.draw(area);
	}
}

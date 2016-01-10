package me.wener.game.gtetris.ui.components;

import me.wener.game.gtetris.ui.UISetting;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class GButton extends JButton {
    private static final long serialVersionUID = 1L;

    public GButton() {
        UISetting.ApplySetting(this);
        setFont(UISetting.ButtonFont);
        setFocusPainted(false);
        setBorderPainted(true);
        setBorder(new LineBorder(Color.GRAY, 2, false));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(Color.BLACK);
                setBackground(Color.WHITE);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.BLACK);
                setForeground(Color.WHITE);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0,0,getWidth(),getHeight());
        super.paint(g);
    }
}

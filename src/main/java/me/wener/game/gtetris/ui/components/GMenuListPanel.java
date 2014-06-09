package me.wener.game.gtetris.ui.components;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.wener.game.gtetris.ui.UISetting;

@Accessors(chain=true)
public class GMenuListPanel extends GPanel
	implements ActionListener
{
	private static final long serialVersionUID = 1L;
	Font menuFont;
	@Getter GLabel titleLabel;
	@Getter GPanel buttonPanel;
	@Getter Map<Object, String> menuMap = null;
	@Getter @Setter ActionListener actionListener;
	public GMenuListPanel()
	{
		super();
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(getForeground(), 1),
				BorderFactory.createEmptyBorder(4, 4, 4, 4)));
		
		// titleLabel
		{
			titleLabel = new GLabel();
			//titleLabel.setText("Option");
			titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
			titleLabel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
			
			titleLabel.setSize(titleLabel.getPreferredSize());
			add(titleLabel,BorderLayout.NORTH);
		}
		// buttonPanel
		{
			buttonPanel = new GPanel();
			add(buttonPanel,BorderLayout.CENTER);
		}
	}
	void initButton()
	{
		if (menuMap == null)
			return;
		
		GButton button;
		Iterator<Entry<Object, String>> it = menuMap.entrySet().iterator();
	    while (it.hasNext())
	    {
	        Entry<Object, String> pairs = it.next();
	        
	        button = new GButton();
	        button.setName(pairs.getKey().toString());
			button.setText(pairs.getValue().toString());
			button.setFont(UISetting.ChineseFont);
			button.addActionListener(this);
//			button.addActionListener(new ActionListener()
//			{
//				
//				@Override
//				public void actionPerformed(ActionEvent e)
//				{
//					String cmd;
//					if(e.getSource() instanceof JComponent)
//					{
//						cmd = ((JComponent) e.getSource()).getName();
//					}else
//						return;
//					
//					doAction(SceneAction.valueOf(cmd));
//				}
//			});
			buttonPanel.add(button);
	        
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		
		buttonPanel.setLayout(new GridLayout(menuMap.size(), 1, 2, 4));
		// 需要从新调整大小
		buttonPanel.setSize(buttonPanel.getPreferredSize());
		setSize(getPreferredSize());
	}
	/**
	 * 该映射为按键Namp和显示值的映射
	 * Object的值作为按键的 Name, String 作为按键的 Text
	 * @param menuMap
	 * @return
	 */
	public GMenuListPanel setMenuMap(Map<Object, String> menuMap)
	{
		this.menuMap = menuMap;
		initButton();
		return this;
	}
	
	//{{ setter/getter
	
	public GMenuListPanel setTitle(String title)
	{
		titleLabel.setText(title);
		return this;
	}
	public String getTitle()
	{
		return titleLabel.getText();
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (actionListener != null)
			actionListener.actionPerformed(e);
		
	}
	
	//}}

}

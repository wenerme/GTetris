package me.wener.game.gtetris.ui.scene;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.wener.game.gtetris.framework.Game;
import me.wener.game.gtetris.framework.GameSetting;
import me.wener.game.gtetris.ui.MessageBox;
import me.wener.game.gtetris.ui.SceneManager;
import me.wener.game.gtetris.ui.UISetting;
import me.wener.game.gtetris.ui.components.GButton;
import me.wener.game.gtetris.ui.components.GDialogPanel.DialogButton;
import me.wener.game.gtetris.ui.components.GLabel;
import me.wener.game.gtetris.ui.components.GScene;
import me.wener.game.gtetris.ui.res.TextRes;
import me.wener.game.gtetris.utils.U5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;

public class MainMenuScene extends GScene
        implements KeyListener, ActionListener {
    private static final long serialVersionUID = 1L;
	List<String> tips;

    public MainMenuScene() {
        setName("MainMenuScene");
		addKeyListener(this);

		// 添加菜单
		{
			int x = getWidth() - 220;
			int y = getHeight() - 180;
			GButton button;
			EnumMap<SceneAction, String> menuMap = Maps.newEnumMap(SceneAction.class);

			menuMap.put(SceneAction.StartGame, "Start Game");
			menuMap.put(SceneAction.ContinueGame, "Continue");
			menuMap.put(SceneAction.Quit, "Quit");
			menuMap.put(SceneAction.About, "About");

			Iterator<Entry<SceneAction, String>> it = menuMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<SceneAction, String> pairs = it.next();

                button = new GButton();
                button.setName(pairs.getKey().toString());
                button.setText(pairs.getValue());
                button.setBounds(x, y += 30, 200, 24);
                button.addActionListener(this);
				add(button);

                it.remove(); // avoids a ConcurrentModificationException
            }
        }

		// Label
		{
			GLabel label;
			String text;

			// 版本号
			label = new GLabel();
			text = "%s v %s";
            text = String.format(text, GameSetting.title, GameSetting.Version);
            label.setText(text);

			label.setFont(UISetting.ChineseFont.deriveFont(Font.PLAIN, 12));
			label.setVerticalAlignment(JLabel.TOP);
			label.setHorizontalAlignment(JLabel.LEFT);
			label.setSize(label.getPreferredSize());
			label.setLocation(getWidth() - 100, getHeight() - label.getHeight());
			add(label);

			// logo
			label = new GLabel();
			label.setText(GameSetting.logo);
			label.setFont(UISetting.LogoFont.deriveFont(Font.PLAIN, 60));
			label.setSize(label.getPreferredSize());
            label.setLocation((getWidth() - label.getWidth()) / 2, 120);
            add(label);
		}

		// tips
        tips = Lists.newArrayList(TextRes.Tips.getText().split("\n"));

    }

    public void enterStage(SceneManager sceneManager) {
        super.enterStage(sceneManager);
        MessageBox.ShowNotify(tips.get(U5.randomInt(tips.size())), 0);
    }

	@Override
    public void keyPressed(KeyEvent e) {
    }

	@Override
    public void keyReleased(KeyEvent e) {
    }

	@Override
    public void keyTyped(KeyEvent e) {

	}

	@Override
    public void actionPerformed(ActionEvent e) {

		String cmd;
        if (e.getSource() instanceof JComponent) {
            cmd = ((JComponent) e.getSource()).getName();
        } else {
            return;
		}

        doAction(SceneAction.getEnum(cmd));
	}

    private void doAction(SceneAction cmd) {
        if (cmd == null)
			return;

        switch (cmd) {
            case StartGame:
				sceneManager.setSceneInfo("StartGame").showScene("PlayScene");
				break;

            case ContinueGame:
				sceneManager.setSceneInfo("Continue").showScene("PlayScene");
				break;

            case About:
                MessageBox.Build("About", TextRes.About.getText(), null).Show();
                break;

            case Quit:
				MessageBox.Build("Sure to quit ?", "亲,你确定要退出么?",
						new DialogButton[]{DialogButton.Yes, DialogButton.No},
                        (dialogPanel, clicked) -> {
                            if (clicked == DialogButton.Yes) {
                                Game.Quit();
                            }
                        }
                ).Show();
                break;

            default:
                break;
        }
    }

    public enum SceneAction {
        StartGame("StartName"),
        ContinueGame("Continue"),
        About("About"),
        Quit("Quit");

        private static Map<String, SceneAction> actionMap;

        static {
            actionMap = new HashMap<String, SceneAction>();
            SceneAction[] array = SceneAction.values();
            for (SceneAction anArray : array) {
                actionMap.put(anArray.toString(), anArray);
            }
        }

        private final String text;

        private SceneAction(final String text) {
            this.text = text;
        }

        public static SceneAction getEnum(String name) {
            return actionMap.get(name);
        }

        @Override
        public String toString() {
            return text;
        }
	}

}

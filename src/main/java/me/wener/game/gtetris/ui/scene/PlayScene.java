package me.wener.game.gtetris.ui.scene;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JComponent;
import lombok.Getter;
import me.wener.game.gtetris.Game;
import me.wener.game.gtetris.GameSetting;
import me.wener.game.gtetris.utils.U5;
import me.wener.game.gtetris.logic.CubeGraphics;
import me.wener.game.gtetris.logic.CubeShape;
import me.wener.game.gtetris.logic.GameArea;
import me.wener.game.gtetris.logic.GameAreaEventListener;
import me.wener.game.gtetris.logic.GameData;
import me.wener.game.gtetris.logic.GamePlayer;
import me.wener.game.gtetris.logic.GamePlayerAction;
import me.wener.game.gtetris.ui.components.DialogResultListener;
import me.wener.game.gtetris.ui.components.GDialogPanel;
import me.wener.game.gtetris.ui.components.GLabel;
import me.wener.game.gtetris.ui.components.GLayeredPane;
import me.wener.game.gtetris.ui.components.GMenuListPanel;
import me.wener.game.gtetris.ui.components.GScene;
import me.wener.game.gtetris.ui.MessageBox;
import me.wener.game.gtetris.ui.SceneManager;
import me.wener.game.gtetris.ui.UISetting;
import me.wener.game.gtetris.ui.components.GDialogPanel.DialogButton;

public class PlayScene extends GScene
	implements KeyListener, GameAreaEventListener
{
	private static final long serialVersionUID = 1L;
	
	public enum SceneAction
	{
		Pause, Resume, 
		Save, Load, 
		ToggleMusic, 
		Quit, ExitToMainMenu, 
		AddPlayer,
		Restart;
	}
	
	GameArea gameArea;
	GLabel timeLabel;
	GLabel pointLabel;
	CubeGraphics cubeGraphics;
	Timer gameTimer;
	@Getter int point;
	@Getter int time;
	Map<Integer, SceneAction> sceneActionMap;
	List<Map<Integer, GamePlayerAction>> playersActionMap;
	boolean paused;
	GMenuListPanel optionPanel;
	GMenuListPanel gameOverPanel;
	// {{ constructor
	public PlayScene()
	{
		setName("PlayScene");
		addKeyListener(this);
		gameTimer = new Timer();
		
		gameArea = new GameArea(GameSetting.cubePreLine, GameSetting.cubePreColumn);
		gameArea.setEventListener(this);
		// cubeGraphics
		{
			cubeGraphics = new CubeGraphics();
			cubeGraphics.setLocation(0, 0);
			cubeGraphics.setPreferredSize(getSize());
			cubeGraphics.setSize(getSize());
			cubeGraphics.setCubeMatrix(gameArea.getCubeMatrix());
			// 该层为最底层
			setLayer(cubeGraphics, -1);
			add(cubeGraphics);
			
		}
		// optionPanel
		{
			optionPanel = new GMenuListPanel();
			optionPanel.setTitle("Option");
			
			Map<Object, String> menuMap = new LinkedHashMap<Object, String>();
			menuMap.put(SceneAction.Resume, "继续游戏");
			menuMap.put(SceneAction.Restart, "从新开始");
			menuMap.put(SceneAction.AddPlayer, "添加玩家");
			menuMap.put(SceneAction.Load, "加载游戏");
			menuMap.put(SceneAction.Save, "保存游戏");
			
			menuMap.put(SceneAction.ExitToMainMenu, "回到主菜单");
			menuMap.put(SceneAction.Quit, "退出游戏");
			
			optionPanel.setMenuMap(menuMap);
			
			optionPanel.setActionListener(new ActionListener()
			{
				
				@Override
				public void actionPerformed(ActionEvent e)
				{
					String cmd;
					if(e.getSource() instanceof JComponent)
					{
						cmd = ((JComponent) e.getSource()).getName();
					}else
						return;
					
					doAction(SceneAction.valueOf(cmd));
				}
			});
			
			optionPanel.setSize(optionPanel.getPreferredSize());
			optionPanel.setLocation((getWidth()-optionPanel.getWidth())/2,
					(getHeight()-optionPanel.getHeight())/2);
			setLayer(optionPanel, GLayeredPane.POPUP_LAYER + 100);
			optionPanel.setVisible(false);
			add(optionPanel);
		}
		
		// gameOverPanel
		{
			gameOverPanel = new GMenuListPanel();
			gameOverPanel.setTitle("Game Over");
			
			Map<Object, String> menuMap = new LinkedHashMap<Object, String>();
			menuMap.put(SceneAction.Restart, "从新开始");
			
			menuMap.put(SceneAction.Load, "加载游戏");
			
			menuMap.put(SceneAction.ExitToMainMenu, "返回到主菜单");
			menuMap.put(SceneAction.Quit, "退出游戏");
			
			gameOverPanel.setMenuMap(menuMap);
			
			gameOverPanel.setActionListener(new ActionListener()
			{
				
				@Override
				public void actionPerformed(ActionEvent e)
				{
					String cmd;
					if(e.getSource() instanceof JComponent)
					{
						cmd = ((JComponent) e.getSource()).getName();
					}else
						return;
					
					doAction(SceneAction.valueOf(cmd));
				}
			});
			
			gameOverPanel.setSize(gameOverPanel.getPreferredSize());
			gameOverPanel.setLocation((getWidth()-gameOverPanel.getWidth())/2,
					(getHeight()-gameOverPanel.getHeight())/2);
			setLayer(gameOverPanel, GLayeredPane.POPUP_LAYER + 101);
			gameOverPanel.setVisible(false);
			add(gameOverPanel);
		}
		
		// 初始化按键绑定
		{
			sceneActionMap = new HashMap<Integer, PlayScene.SceneAction>();
//			sceneActionMap.put(KeyEvent.VK_SPACE, SceneAction.Pause);
			sceneActionMap.put(KeyEvent.VK_1, SceneAction.Save);
			sceneActionMap.put(KeyEvent.VK_2, SceneAction.Load);
			sceneActionMap.put(KeyEvent.VK_3, SceneAction.ToggleMusic);
			sceneActionMap.put(KeyEvent.VK_4, SceneAction.AddPlayer);
			sceneActionMap.put(KeyEvent.VK_6, SceneAction.Restart);
			sceneActionMap.put(KeyEvent.VK_ESCAPE, SceneAction.Pause);
		}
		// 场景中的元素
		{
			Font font = UISetting.MiniFont.deriveFont(20f);
			
			timeLabel = new GLabel();
			timeLabel.setText("TIME: 00000");
			timeLabel.setFont(font);
			timeLabel.setSize(timeLabel.getPreferredSize());
			timeLabel.setLocation(getWidth()-timeLabel.getWidth(), 0);
			add(timeLabel);
			
			pointLabel = new GLabel();
			pointLabel.setText("POINT: 0000");
			pointLabel.setFont(font);
			pointLabel.setSize(pointLabel.getPreferredSize());
			pointLabel.setLocation(getWidth() - pointLabel.getWidth(), pointLabel.getHeight());
			add(pointLabel);
		}
		// 设置玩家按键映射
		{
			playersActionMap = new ArrayList<Map<Integer,GamePlayerAction>>();
			// 按键根据从左到右
			Map<Integer, GamePlayerAction> actionMap;
			
			actionMap = new HashMap<>();
			actionMap.put(KeyEvent.VK_W, GamePlayerAction.RightRotation);
			actionMap.put(KeyEvent.VK_S, GamePlayerAction.FallDown);
			actionMap.put(KeyEvent.VK_A, GamePlayerAction.LeftMove);
			actionMap.put(KeyEvent.VK_D, GamePlayerAction.RightMove);
			playersActionMap.add(actionMap);
			
			actionMap = new HashMap<>();
			actionMap.put(KeyEvent.VK_I, GamePlayerAction.RightRotation);
			actionMap.put(KeyEvent.VK_K, GamePlayerAction.FallDown);
			actionMap.put(KeyEvent.VK_J, GamePlayerAction.LeftMove);
			actionMap.put(KeyEvent.VK_L, GamePlayerAction.RightMove);
			playersActionMap.add(actionMap);
			
			actionMap = new HashMap<>();
			actionMap.put(KeyEvent.VK_UP, GamePlayerAction.RightRotation);
			actionMap.put(KeyEvent.VK_DOWN, GamePlayerAction.FallDown);
			actionMap.put(KeyEvent.VK_LEFT, GamePlayerAction.LeftMove);
			actionMap.put(KeyEvent.VK_RIGHT, GamePlayerAction.RightMove);
			playersActionMap.add(actionMap);
			
			
		}
	}
	
	// }}
	
	boolean addPlayer()
	{
		GamePlayer player;
		player = new GamePlayer();
		Map<Integer, GamePlayerAction> actionMap;
		// 根据当前的玩家数量来获取动作映射
		actionMap = playersActionMap.get(gameArea.getPlayers().size()% playersActionMap.size());
		
		player.setActionMap(actionMap);
		
		return gameArea.addPlayer(player);
	}
	
	
	boolean doAction(KeyEvent e)
	{
		return doAction(e.getKeyCode());
	}
	boolean doAction(int keyCode)
	{
		SceneAction action;
		action = getActionByKeyCode(keyCode);
		return doAction(action);
	}
	boolean doAction(SceneAction action)
	{
//		System.out.println("Play Scene Action "+ action);
		if (action == null)
			return false;
		// 当游戏结束后,有些动作不能做
		if (gameArea.isGameOver())
		switch (action)
		{
			case Pause:
			case Save:
			case AddPlayer:
				
			return true;
			default:
		}
		//
		switch (action)
		{
			case Restart:
				setPoint(0);
				setTime(0);
				optionPanel.setVisible(false);
				paused = false;
				gameOverPanel.setVisible(false);
				gameArea.reset();
				addPlayer();
				break;
			case Resume:
				optionPanel.setVisible(false);
				paused = false;
				break;
			case Pause:
				paused = !paused;
				optionPanel.setVisible(paused);
				break;
			case Quit:
				MessageBox.Build("Sure to quit ?", "亲,你确定要退出么?",
						new DialogButton[]{DialogButton.Yes, DialogButton.No},
						new DialogResultListener()
						{
							
							@Override
							public void OnDialogButtonClick(GDialogPanel dialogPanel,
									DialogButton clicked)
							{
								if (clicked == DialogButton.Yes)
								{
									Game.QuitGame();
								}
							}
						}).Show();
				break;
			case ExitToMainMenu:
				sceneManager.showScene("MainMenuScene");
				break;
			case AddPlayer:
				if (addPlayer())
				{
					MessageBox.ShowNotify("玩家添加成功,当前玩家数量 "+gameArea.getPlayers().size());
				}else
					MessageBox.ShowNotify("已经达到最大玩家数量了哦,当前玩家数量 "+gameArea.getPlayers().size());
				break;
			case Save:
				
				byte[] bytes = U5.SerializeObject(save());
				if (bytes != null && U5.FilePutContents("save.dat", bytes))
				{
					MessageBox.ShowNotify("保存存档 成功");
				}else {
					MessageBox.ShowNotify("保存加载 失败");
				}
				MessageBox.ShowNotify("Saved");
				System.out.println("Saved");
				break;
			case Load:
				GameData data = null;
			    data = U5.DeserializeObject(new File("save.dat"));
			   
				if (data != null && load(data))
				{
					MessageBox.ShowNotify("存档加载 成功");
				}else {
					MessageBox.ShowNotify("存档加载 失败");
				}
				break;
			default:
				return false;
		}
		
		return true;
	}
	
	public GameData save()
	{
		GameData data = new GameData();
		data.setPoint(point)
			.setTime(time)
			.setGameArea(gameArea);
		
		return data;
	}
	public boolean load(GameData data)
	{
		point = data.getPoint();
		time = data.getTime();
		gameArea = data.getGameArea();
		gameArea.setEventListener(this);
		cubeGraphics.setCubeMatrix(gameArea.getCubeMatrix());
		repaint();
		doAction(SceneAction.Resume);
		return true;
	}
	SceneAction getActionByKeyCode(int keyCode)
	{
		return sceneActionMap.get(keyCode);
	}
	@Override
    public void enterStage(SceneManager sceneManager)
	{
		super.enterStage(sceneManager);
		MessageBox.clearAllNotify();
		MessageBox.ShowNotify("游戏愉快 :-)");
		
		switch (sceneManager.getSceneInfo())
		{
			case "Easy":
			case "Normal":
			case "Hard":
				
			default:
				System.out.println("Unknow info " + sceneManager.getSceneInfo());
			case "StartGame":
				doAction(SceneAction.Restart);
			break;
			case "Continue":
				doAction(SceneAction.Restart);
				doAction(SceneAction.Load);
				break;
		}
		
		// 初始 Timer
		{
			// 游戏运行
			gameTimer = new Timer();
			gameTimer.schedule(new TimerTask()
			{
				
				@Override
				public void run()
				{
					if (paused || gameArea.isGameOver())
						return;
					gameArea.stepRun();
				}
			}, 0, 500);
			// 计时
			gameTimer.schedule(new TimerTask()
			{
				
				@Override
				public void run()
				{
					if (paused || gameArea.isGameOver())
						return;
					increaseTime(1);
				}
			}, 0, 1000);
		}
	}

	@Override
    public void leaveStage(SceneManager sceneManager)
	{
		super.leaveStage(sceneManager);
		gameTimer.cancel();
	}
	// {{ setter/getter
	PlayScene setTime(int t)
	{
		time = t;
		timeLabel.setText("TIME: "+time);
		return this;
	}
	PlayScene increaseTime(int n)
	{
		setTime(time + n);
		return this;
	}
	PlayScene setPoint(int n)
	{
		point = n;
		pointLabel.setText("Point: "+n);
		return this;
	}
	PlayScene increasePoint(int n)
	{
		setPoint(point + n);
		return this;
	}
	// }}
	
	// {{ event
	@Override
	public void keyPressed(KeyEvent e)
	{
		if (doAction(e) == false)
		{
			gameArea.doAction(e);
		}
		;
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnCubeChanged(GameArea area)
	{
		cubeGraphics.repaint();
	}

	@Override
	public void OnGameOver(GameArea area)
	{
		gameOverPanel.setVisible(true);
	}

	@Override
	public void OnGotPoint(GameArea area, GamePlayer player, int point)
	{
		player.increasePoint(point);
		increasePoint(point);
	}

	@Override
	public void OnNextShapeChanged(GameArea area, CubeShape nextShape)
	{
		cubeGraphics.setNextCubeMartix(nextShape.getCurrentInfoeArray());
	}

	// }}

}

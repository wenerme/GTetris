package me.wener.game.gtetris.logic;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Accessors(chain = true)
public class GameArea
		implements Serializable {

	private static final long serialVersionUID = 1L;
	// field
	@Getter
	int height;
	@Getter
	int width;
	@Getter
	CubeShape nextShape;
	/**
	 * 合成后的
	 */
	@Getter
	CubeInfo[][] cubeMatrix;
	/**
	 * 基本的,不包括正在运动的
	 */
	CubeInfo[][] baseCubeMatrix;
	/**
	 * 游戏玩家
	 */
	@Getter
	List<GamePlayer> players;
	@Getter
	@Setter
	transient GameAreaEventListener eventListener;
	@Getter
	boolean gameOver = false;

	public GameArea(int width, int height) {
		this.height = height;
		this.width = width;
		cubeMatrix = new CubeInfo[height][width];
		baseCubeMatrix = new CubeInfo[height][width];
		players = new ArrayList<GamePlayer>();
		generateNextShape();
	}

	// {{ 功能函数
	public static void CloneShape(int x1, int y1, CubeInfo[][] cubeInfoA,
								  int x2, int y2, CubeInfo[][] cubeInfoB) {
		int w1, h1, w2, h2;
		h1 = cubeInfoA.length;
		w1 = cubeInfoA[0].length;
		h2 = cubeInfoB.length;
		w2 = cubeInfoB[0].length;

		CloneShape(x1, y1, w1, h1, cubeInfoA, x2, y2, w2, h2, cubeInfoB);
	}

	/**
	 * 将 B 复制到 A
	 */
	public static void CloneShape(int x1, int y1, int w1, int h1, CubeInfo[][] cubeInfoA,
								  int x2, int y2, int w2, int h2, CubeInfo[][] cubeInfoB) {
		// 相交部分
		int x, y, w, h;

		x = Math.max(x1, x2);
		y = Math.max(y1, y2);
		w = Math.min(x1 + w1, x2 + w2) - x;
		h = Math.min(y1 + h1, y2 + h2) - y;

		// 无相交部分
		if (w <= 0 || h <= 0)
			return;

		// 复制相交部分
		CubeInfo cubeA, cubeB;
		int dy, dx;
		for (dy = y; dy < h + y; dy++)
			for (dx = x; dx < w + x; dx++) {
				cubeA = cubeInfoA[dy - y1][dx - x1];
				cubeB = cubeInfoB[dy - y2][dx - x2];

				if (cubeB == null)
					cubeB = new CubeInfo().setVisible(false);

				if (cubeA == null)
					cubeA = new CubeInfo(cubeB);
				else
					cubeA.setTo(cubeB);

				cubeInfoA[dy - y1][dx - x1] = cubeA;
				cubeInfoB[dy - y2][dx - x2] = cubeB;
			}
	}

	// {{ Player operation

	public static void MergeShape(int x1, int y1, CubeInfo[][] cubeInfoA,
								  int x2, int y2, CubeInfo[][] cubeInfoB) {
		int w1, h1, w2, h2;
		h1 = cubeInfoA.length;
		w1 = cubeInfoA[0].length;
		h2 = cubeInfoB.length;
		w2 = cubeInfoB[0].length;

		MergeShape(x1, y1, w1, h1, cubeInfoA, x2, y2, w2, h2, cubeInfoB);
	}

	/**
	 * 将 B 合并到 A
	 */
	public static void MergeShape(int x1, int y1, int w1, int h1, CubeInfo[][] cubeInfoA,
								  int x2, int y2, int w2, int h2, CubeInfo[][] cubeInfoB) {
		// 相交部分
		int x, y, w, h;

		x = Math.max(x1, x2);
		y = Math.max(y1, y2);
		w = Math.min(x1 + w1, x2 + w2) - x;
		h = Math.min(y1 + h1, y2 + h2) - y;

//		System.out.println(String.format("inst Rectangle: %s,%s,%s,%s", x,y,w,h));
		// 无相交部分
		if (w <= 0 || h <= 0)
			return;

		// 合并相交部分
		CubeInfo cubeA, cubeB;
		int dy, dx;
		for (dy = y; dy < h + y; dy++)
			for (dx = x; dx < w + x; dx++) {
				cubeA = cubeInfoA[dy - y1][dx - x1];
				cubeB = cubeInfoB[dy - y2][dx - x2];

				if (cubeB == null || cubeB.isVisible() == false)
					continue;

				if (cubeA == null)
					cubeA = new CubeInfo(cubeB);
				else
					cubeA.setTo(cubeB);

				cubeInfoA[dy - y1][dx - x1] = cubeA;
			}
	}

	/**
	 * 做碰撞检测
	 *
	 * @return true 有碰撞
	 */
	public static boolean CollsionDetection(int x1, int y1, CubeInfo[][] cubeInfoA,
											int x2, int y2, CubeInfo[][] cubeInfoB) {
		int w1, h1, w2, h2;
		h1 = cubeInfoA.length;
		w1 = cubeInfoA[0].length;
		h2 = cubeInfoB.length;
		w2 = cubeInfoB[0].length;

		// 相交部分
		int x, y, w, h;

		x = Math.max(x1, x2);
		y = Math.max(y1, y2);
		w = Math.min(x1 + w1, x2 + w2) - x;
		h = Math.min(y1 + h1, y2 + h2) - y;

		// 无相交部分
		if (w <= 0 || h <= 0)
			return false;

		// 碰撞检测 需要考虑 CubeInfo 的可见性
		CubeInfo cubeA, cubeB;
		int dy, dx;
		for (dy = y; dy < h + y; dy++)
			for (dx = x; dx < w + x; dx++) {
				cubeA = cubeInfoA[dy - y1][dx - x1];
				cubeB = cubeInfoB[dy - y2][dx - x2];
				if (cubeA == null || cubeB == null || // 两个均不为null
						cubeA.isVisible() == false || cubeB.isVisible() == false)//
					continue;
				else
					return true;
			}

		return false;
	}
	// }}

	/**
	 * 判断 cubeB 是否超出了 cubeA 的边界
	 *
	 * @return true 超出边界
	 */
	public static boolean OutOfRange(int x1, int y1, CubeInfo[][] cubeInfoA,
									 int x2, int y2, CubeInfo[][] cubeInfoB) {
		int w1, h1, w2, h2;
		h1 = cubeInfoA.length;
		w1 = cubeInfoA[0].length;
		h2 = cubeInfoB.length;
		w2 = cubeInfoB[0].length;

		// B 完全在 A 里
		if (false == (x2 < x1 || y2 < y1 || // left top
				(x2 + w2) > (x1 + w1) || (y2 + h2) > (y1 + h1))) // right bottom
			return false;

		// 相交部分
		int x, y, w, h;

		x = Math.max(x1, x2);
		y = Math.max(y1, y2);
		w = Math.min(x1 + w1, x2 + w2) - x;
		h = Math.min(y1 + h1, y2 + h2) - y;

		// 无相交部分 肯定超出边界 假设 B 肯定有显示的内容
		if (w <= 0 || h <= 0)
			return true;

		// 判断未相交部分 cubeInfoB 的可见性
		// 需要判断8个方位的未相交部分

		if (isOpaque(x2, y2, x - x2, y - y2, x2, y2, cubeInfoB) || // left top
				isOpaque(x, y2, w, y - y2, x2, y2, cubeInfoB) || // top
				isOpaque(x + w, y2, (x2 + w2) - (x + w), y - y2, x2, y2, cubeInfoB) || // right top
				isOpaque(x + w, y, (x2 + w2) - (x + w), h, x2, y2, cubeInfoB) || // right
				isOpaque(x + w, y + h, (x2 + w2) - (x + w), (y2 + h2) - (y + h), x2, y2, cubeInfoB) || // right bottom
				isOpaque(x, y + h, w, (y2 + h2) - (y + h), x2, y2, cubeInfoB) || // bottom
				isOpaque(x2, y + h, x - x2, (y2 + h2) - (y + h), x2, y2, cubeInfoB) || // left bottom
				isOpaque(x2, y, x - x2, h, x2, y2, cubeInfoB)    // left
				) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检测部分区域是否不透明,也就是有需要显示的
	 *
	 * @param cx Cube的位置
	 * @param sx 目标区域坐标
	 * @param w  目标区域尺寸
	 */
	static boolean isOpaque(int sx, int sy, int w, int h, int cx, int cy, CubeInfo[][] cubeInfo) {
		int dx, dy;
		dx = sx - cx + w;
		dy = sy - cy + h;

		// 目标区域不在范围内 无显示 返回false
		if (sx - cx < 0 || sy - cy < 0 || dy < 0 || dx < 0)
			return false;

		// 确定边界
		dy = Math.min(dy, cubeInfo.length);
		dx = Math.min(dx, cubeInfo[0].length);

		for (int y = sy - cy; y < dy; y++)
			for (int x = sx - cx; x < dx; x++)
				if (cubeInfo[y][x] != null && cubeInfo[y][x].isVisible())
					return true;

		return false;
	}

	/**
	 * 重置所有方块
	 */
	public GameArea reset() {
		gameOver = false;
		// 隐藏所有
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				if (baseCubeMatrix[y][x] != null)
					baseCubeMatrix[y][x].setVisible(false);

		// 重置玩家
		removeAllPlayer();

		OnCubeChanged();
		return this;
	}

	/**
	 * 添加玩家
	 *
	 * @return true 添加成功
	 */
	public boolean addPlayer(GamePlayer player) {
		// 有最大玩家数量限制
		if (width < (CubeShape.WIDTH * (players.size() + 1)))
			return false;

		players.add(player);
		player.setGameArea(this);
		generatePlayerShape(player);
		return true;
	}

	public GameArea removePlayer(GamePlayer player) {
		players.remove(player);
		return this;
	}

	public GameArea removeAllPlayer() {
		players.clear();
		return this;
	}

	GameArea generateNextShape() {
		nextShape = CubeShapeManager.getRadomCubeShape();
		OnNextShapeChanged();
		return this;
	}

	boolean generatePlayerShape(GamePlayer player) {
		player.setCubeShape(nextShape);


		// 设置坐标
		int i = players.indexOf(player);
		CubeShape shape = player.getCubeShape();
		int playerCount = players.size();
		int gap = (width - playerCount * shape.getWidth()) / (playerCount + 1);
		shape.setX(gap + (gap + shape.getWidth()) * i);

		// 如果生成出来的图形和其他玩家相碰撞, 则取消该生成结果
		if (playerCollisionDetect(player)) {
			player.setCubeShape(null);
		} else {
			// 生成成功,从新生成下一个
			generateNextShape();
		}

		return true;
	}

	/**
	 * 将所有 player 合并到背景上 结果为 cubeMatrix
	 */
	GameArea render() {
		CloneShape(0, 0, cubeMatrix, 0, 0, baseCubeMatrix);

		for (int i = 0; i < players.size(); i++) {
			GamePlayer player = players.get(i);
			CubeShape shape = player.getCubeShape();
			if (shape != null)
				MergeShape(0, 0, cubeMatrix, shape.getX(), shape.getY(), shape.getCurrentInfoeArray());
		}
		OnCubeChanged();
		return this;
	}

	/**
	 * 尝试消除
	 *
	 * @return 返回消除的行数, 如果返回0 则表示没有消除
	 */
	public int tryReslove() {
		System.out.println("try Resolve");
		int removedLineCount = 0;
		for (int y = 0; y < baseCubeMatrix.length; y++) {
			boolean canRemove = true;
			// 检测一行是否可以移除
			for (int x = 0; x < baseCubeMatrix[y].length && canRemove; x++)
				if (baseCubeMatrix[y][x] == null || false == baseCubeMatrix[y][x].isVisible())
					canRemove = false;
			//
			if (canRemove) {
				removedLineCount++;
				System.out.println("Resolve " + y);
				removeLine(y);
			}

		}

		return removedLineCount;
	}

	public GameArea removeLine(int ypos) {
		for (int x = 0; x < width; x++) {
			// 设置 0 行为不可见
			baseCubeMatrix[0][x].setVisible(false);
			// 将该行之上的位置 Y 位置的设置到 Y+1
			for (int y = ypos; y > 0; y--)
				baseCubeMatrix[y][x].setTo(baseCubeMatrix[y - 1][x]);
		}

		return this;
	}
	// }}

	// {{ Event
	public void doAction(KeyEvent e) {
		doAction(e.getKeyCode());
	}

	public void doAction(int keyCode) {
		if (gameOver)
			return;

		// 将事件分发给玩家
		GamePlayerAction action;
		GamePlayer player;
		for (int i = 0; i < players.size(); i++) {
			player = players.get(i);
			action = player.getActionByKeyCode(keyCode);
			if (action != null) {
				player.doAction(action);
				render();
				break;
			}
		}
	}

	void OnCubeChanged() {
		if (eventListener != null) {
			eventListener.OnCubeChanged(this);
		}
	}

	void OnGameOver() {
		if (eventListener != null) {
			eventListener.OnGameOver(this);
		}
	}

	void OnGotPoint(GamePlayer player, int point) {
		if (eventListener != null) {
			eventListener.OnGotPoint(this, player, point);
		}
	}

	void OnNextShapeChanged() {
		if (eventListener != null) {
			eventListener.OnNextShapeChanged(this, nextShape);
		}
	}

	public void stepRun() {
		if (gameOver)
			return;
		// 玩家自由下落
		{
			GamePlayer player;
			for (int i = 0; i < players.size(); i++) {
				player = players.get(i);

				// 从新生成图形, 如果图形是该轮生成的,则不下落
				if (player.getCubeShape() == null) {
					generatePlayerShape(player);
					player.setDoneAction(true);
				}

				// 如果玩家有任何动作 则该轮不自动下落
				if (player.isDoneAction())
					player.setDoneAction(false);
				else if (player.autoFallDown().withScene())// 下降过程中与场景碰撞,则停止
				{
					// 一个图形停止运动, 合并到basic
					CubeShape shape = player.getCubeShape();
					MergeShape(0, 0, baseCubeMatrix, shape.getX(), shape.getY(), shape.getCurrentInfoeArray());

					// 尝试消除
					{
						int point = tryReslove();
						if (point != 0)
							OnGotPoint(player, point);
					}
					// 生成新的
					generatePlayerShape(player);

					// 如果刚刚生成出来就检测碰撞失败,则说明game over了
					if (player.getCubeShape() != null && CollisionType.Scene == testCubeShape(player)) {
						setGameOver(true);
					}

				}

			}
		}
		// 绘制
		render();
	}

	public GameArea setGameOver(boolean state) {
		if (state == true)
			OnGameOver();

		gameOver = state;
		return this;
	}

	/**
	 * 测试 该player在场景中是否可行
	 * 用于尝试改变shape状态时的检测
	 *
	 * @return true 可行
	 */
	CollisionType testCubeShape(GamePlayer player) {
		CubeShape shape = player.getCubeShape();
		if (shape == null) {
			System.out.println("Wrong here");
		}
		// shape 没有超出场景边界
		if (OutOfRange(0, 0, baseCubeMatrix, shape.getX(), shape.getY(), shape.getCurrentInfoeArray()))
			return CollisionType.OutOfRange;

		// shape 没有与当前场景碰撞
		if (CollsionDetection(0, 0, baseCubeMatrix, shape.getX(), shape.getY(), shape.getCurrentInfoeArray()))
			return CollisionType.Scene;

		// shape 没有与其他的player碰撞
		if (playerCollisionDetect(player))
			return CollisionType.Player;


		// 上面的一切都没有发生, 然后就可以了
		// 我很可爱~是不是
		return CollisionType.None;
	}

	/**
	 * 检测该玩家是否和其它玩家碰撞
	 *
	 * @return true 有碰撞
	 */
	boolean playerCollisionDetect(GamePlayer player) {
		CubeShape shape = player.getCubeShape();
		for (int i = 0; i < players.size(); i++) {
			// 避免和自己检测
			if (player == players.get(i))
				continue;
			CubeShape s = players.get(i).getCubeShape();
			// 有可能其他玩家的图形还没生成 所以不做碰撞检测
			if (s == null)
				continue;
			// 如果有碰撞则失败
			if (CollsionDetection(s.getX(), s.getY(), s.getCurrentInfoeArray(), shape.getX(), shape.getY(), shape.getCurrentInfoeArray()))
				return true;
		}
		return false;
	}

	public enum CollisionType {
		/**
		 * 超出边界碰撞
		 */
		OutOfRange,
		/**
		 * 与场景碰撞
		 */
		Scene,
		/**
		 * 与玩家碰撞
		 */
		Player, None;

		public boolean isNone() {
			return this == None;
		}

		/**
		 * 是否是与场景碰撞
		 *
		 * @return true Scene 或 OutOfRange
		 */
		public boolean withScene() {
			return this == Scene || this == OutOfRange;
		}

		public boolean withPlayer() {
			return this == Player;
		}
	}
	// }}
}







package me.wener.game.gtetris.logic;

import me.wener.game.gtetris.utils.U5;

import java.awt.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;


public class CubeShapeManager {
	static final List<Color> cubeColorList;
	// field
	static EnumMap<ShapeType, int[][][]> shapeArray;

	static {
		shapeArray = new EnumMap<ShapeType, int[][][]>(ShapeType.class);
		initializeShape();

		// 方块颜色列表
		cubeColorList = new ArrayList<Color>();
		cubeColorList.add(new Color(0xE6DB74));
		cubeColorList.add(new Color(0xA6E22E));
		cubeColorList.add(new Color(0xF92672));
		cubeColorList.add(new Color(0xAE81FF));
		cubeColorList.add(new Color(0x79ABFF));
		cubeColorList.add(new Color(0xFF0000));
		cubeColorList.add(new Color(0xFFFFFF));
	}

	/**
	 * 初始化图形
	 */
	private static void initializeShape() {
		// I
		shapeArray.put(ShapeType.I, new int[][][]
				{
						{
								{1, 0, 0, 0},
								{1, 0, 0, 0},
								{1, 0, 0, 0},
								{1, 0, 0, 0}
						},
						{
								{1, 1, 1, 1},
								{0, 0, 0, 0},
								{0, 0, 0, 0},
								{0, 0, 0, 0}
						}
				});
		// J
		shapeArray.put(ShapeType.J, new int[][][]
				{

						{
								{0, 1, 0, 0},
								{0, 1, 0, 0},
								{1, 1, 0, 0},
								{0, 0, 0, 0}
						},
						{
								{1, 0, 0, 0},
								{1, 1, 1, 0},
								{0, 0, 0, 0},
								{0, 0, 0, 0}
						},
						{
								{1, 1, 0, 0},
								{1, 0, 0, 0},
								{1, 0, 0, 0},
								{0, 0, 0, 0}
						}, {
						{1, 1, 1, 0},
						{0, 0, 1, 0},
						{0, 0, 0, 0},
						{0, 0, 0, 0}
				}
				});
		// L
		shapeArray.put(ShapeType.L, new int[][][]
				{

						{
								{1, 0, 0, 0},
								{1, 0, 0, 0},
								{1, 1, 0, 0},
								{0, 0, 0, 0}
						},
						{
								{1, 1, 1, 0},
								{1, 0, 0, 0},
								{0, 0, 0, 0},
								{0, 0, 0, 0}
						},
						{
								{1, 1, 0, 0},
								{0, 1, 0, 0},
								{0, 1, 0, 0},
								{0, 0, 0, 0}
						}, {
						{0, 0, 1, 0},
						{1, 1, 1, 0},
						{0, 0, 0, 0},
						{0, 0, 0, 0}
				}
				});
		// O
		shapeArray.put(ShapeType.O, new int[][][]
				{
						{
								{1, 1, 0, 0},
								{1, 1, 0, 0},
								{0, 0, 0, 0},
								{0, 0, 0, 0}
						}
				});
		// S
		shapeArray.put(ShapeType.S, new int[][][]
				{

						{
								{0, 1, 1, 0},
								{1, 1, 0, 0},
								{0, 0, 0, 0},
								{0, 0, 0, 0}
						},
						{
								{1, 0, 0, 0},
								{1, 1, 0, 0},
								{0, 1, 0, 0},
								{0, 0, 0, 0}
						}
				});

		// Z
		shapeArray.put(ShapeType.Z, new int[][][]
				{

						{
								{1, 1, 0, 0},
								{0, 1, 1, 0},
								{0, 0, 0, 0},
								{0, 0, 0, 0}
						},
						{
								{0, 1, 0, 0},
								{1, 1, 0, 0},
								{1, 0, 0, 0},
								{0, 0, 0, 0}
						}
				});
		// T
		shapeArray.put(ShapeType.T, new int[][][]
				{

						{
								{1, 1, 1, 0},
								{0, 1, 0, 0},
								{0, 0, 0, 0},
								{0, 0, 0, 0}
						},
						{
								{0, 1, 0, 0},
								{1, 1, 0, 0},
								{0, 1, 0, 0},
								{0, 0, 0, 0}
						},
						{
								{0, 1, 0, 0},
								{1, 1, 1, 0},
								{0, 0, 0, 0},
								{0, 0, 0, 0}
						}, {
						{1, 0, 0, 0},
						{1, 1, 0, 0},
						{1, 0, 0, 0},
						{0, 0, 0, 0}
				}
				});
	}

	/**
	 * 获取图形
	 */
	public static CubeShape getCubeShape(ShapeType type) {
		CubeShape shape;
		shape = new CubeShape(shapeArray.get(type));
		return shape;
	}

	public static Color getRandomCubeColor() {
		return cubeColorList.get(U5.randomInt(cubeColorList.size()));
	}

	/**
	 * 获取随机图形
	 */
	public static CubeShape getRadomCubeShape() {
		ShapeType[] shapeTypeList = new ShapeType[]
				{
						ShapeType.I, ShapeType.L, ShapeType.J,
						ShapeType.T, ShapeType.O, ShapeType.Z, ShapeType.S
				};

		int i = U5.randomInt(shapeTypeList.length);

		CubeShape shape = getCubeShape(shapeTypeList[i]);

		shape.randomRotate().setColor(getRandomCubeColor());
		return shape;
	}

	/**
	 * 预定义图形类型
	 */
	public enum ShapeType {
		I, L, J, T, O, Z, S
	}
}

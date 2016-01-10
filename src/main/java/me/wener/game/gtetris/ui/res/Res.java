package me.wener.game.gtetris.ui.res;

import lombok.Value;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@Value
public class Res {
	private static List<Res> resList = new ArrayList<Res>();
	private static String rootPath = "me.wener.game.gtetris.ui.res/";
	private String path;
	private ResType type;
	private String description;

	public Res(ResType type, String path) {
		this(type, path, "");
	}

	public Res(ResType type, String path, String description) {
		this.type = type;
		this.path = rootPath + path;
		this.description = description;

		resList.add(this);
	}

	/**
	 * 获取资源列表
	 */
	public static List<Res> getResList() {
		return resList;
	}

	//
	public static Res Font(String path) {
		return Font(path, "");
	}

	public static Res Font(String path, String description) {
		return new Res(ResType.Font, path, description);
	}
	//

	//
	public static Res Text(String path) {
		return Font(path, "");
	}

	public static Res Text(String path, String description) {
		return new Res(ResType.Text, path, description);
	}

	public URL getResource() {
		return Res.class.getResource(path);
	}

	public InputStream getResourceAsStream() {
		return this.getClass().getClassLoader().getResourceAsStream(getPath());
	}

	public String getResourceFile() {
		System.out.printf("Res: %s\nFile:%s\n", getResource(), getResource().getFile());
		return getResource().toString();
	}

}

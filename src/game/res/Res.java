package game.res;

import game.Game;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import lombok.Value;


@Value
public class Res
{
	private String path;
	private ResType type;
	private String description;
	private static List<Res> resList = new ArrayList<Res>();
	
	public Res(ResType type,String path)
	{
		this(type, path, "");
	}
	public Res(ResType type,String path, String description)
	{
		this.type = type;
		this.path = path;
		this.description = description;
		
		resList.add(this);
	}
	
	public URL getResource()
	{
		return Res.class.getResource(path);
	}
	public InputStream getResourceAsStream()
	{
		return Res.class.getResourceAsStream(path);
	}
	public String getResourceFile()
	{
		return getResource().getFile();
	}
	//
	/**
	 * 获取资源列表
	 */
	public static List<Res> getResList()
	{
		return resList;
	}
	//
	public static Res Font(String path)
	{
		return Font(path, "");
	}
	public static Res Font(String path, String description)
	{
		return new Res(ResType.Font, path, description);
	}
	//
	public static Res Text(String path)
	{
		return Font(path, "");
	}
	public static Res Text(String path, String description)
	{
		return new Res(ResType.Text, path, description);
	}
	
}
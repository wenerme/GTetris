package game;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.Set;

import javax.management.openmbean.InvalidOpenTypeException;

import org.apache.commons.io.IOUtils;

import lombok.SneakyThrows;

public class U5
{
	static Random random;
	static String charset;
	static
	{
		random = new Random();
	}
	
	public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value)
	{
		Set<T> keys = new HashSet<T>();
		for (Entry<T, E> entry : map.entrySet())
		{
			if (value.equals(entry.getValue()))
			{
				keys.add(entry.getKey());
			}
		}
		return keys;
	}

	public static <T, E> T getKeyByValue(Map<T, E> map, E value)
	{
		for (Entry<T, E> entry : map.entrySet())
		{
			if (value.equals(entry.getValue()))
			{
				return entry.getKey();
			}
		}
		return null;
	}
	
	// {{ file
	public void setFileCharset(String charset)
	{
		U5.charset = charset;
	}
	
	@SneakyThrows
	public static String FileGetContents(InputStream stream)
	{
		String result = null;
		try
		{
			result = IOUtils.toString(stream,charset);
		} catch (Exception e)
		{
			result = null;
			throw e;
		}
		return result;
	}

	@SneakyThrows
	public static String FileGetContents(String filePath)
	{
		String result = null;
		try
		{
			result = IOUtils.toString(new FileInputStream(filePath),charset);
		} catch (Exception e)
		{
			result = null;
			throw e;
		}
		return result;
	}
	
	@SneakyThrows
	public static boolean FilePutContents(String filePath, byte[] contents)
	{
		boolean result = true;
		try
		{
			IOUtils.write(contents, new FileOutputStream(filePath));
		} catch (Exception e)
		{
			result = false;
			throw e;
		}
		return result;
	}
	@SneakyThrows
	public static boolean FilePutContents(String filePath, String contents)
	{
		boolean result = true;
		try
		{
			IOUtils.write(contents, new FileOutputStream(filePath));
		} catch (Exception e)
		{
			result = false;
			throw e;
		}
		return result;
	}
	// }}
	
	// {{ Serialize
	/**
	 * 序列化对象,失败的时候返回null
	 */
	@SneakyThrows
	public static byte[] SerializeObject(Object object)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		byte[] result = null;
		try {
		  out = new ObjectOutputStream(bos);   
		  out.writeObject(object);
		  result = bos.toByteArray();
		} catch(Exception e){
			result = null;
			throw e;
		}finally {
		  out.close();
		  bos.close();
		}
		return result;
	}
	/**
	 * 反序列化对象,失败的时候返回null
	 */
	@SneakyThrows
	public static <T> T DeserializeObject(byte[] bytes)
	{
		T result = null;
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInput in = null;
		try {
		  in = new ObjectInputStream(bis);
		  result = (T)in.readObject(); 
		} catch(Exception e){
			result = null;
			e.printStackTrace();
			//throw e;
		}finally {
		  bis.close();
		  in.close();
		}
		return result;
	}
	@SneakyThrows
	public static <T> T DeserializeObject(File file)
	{
		T result = null;
		try
		{
			result = DeserializeObject(IOUtils.toByteArray(new FileInputStream(file)));
		} catch (Exception e)
		{
			result = null;
			//throw e;
			e.printStackTrace();
		}
		return result;
	}
	
	//}}
	
	// {{ Random
	public static void setRandomSeed(long seed)
	{
		random.setSeed(seed);
	}
	public static double randomDouble()
	{
		return random.nextDouble();
	}
	public static int randomInt()
	{
		return random.nextInt();
	}
	public static int randomInt(int n)
	{
		return random.nextInt(n);
	}
	public static float randomFloat()
	{
		return random.nextFloat();
	}
	public static long randomLong()
	{
		return random.nextLong();
	}
	// }}
}

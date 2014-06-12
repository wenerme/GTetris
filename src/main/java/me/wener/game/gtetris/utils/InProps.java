package me.wener.game.gtetris.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import jodd.props.Props;
import jodd.props.PropsEntry;
import jodd.typeconverter.TypeConverterManager;

/**
 * 对于所有转换操作
 * 如果没有结果返回 null
 * 对于集合类型, 如果没有结果返回 空集合
 * 如果转换失败,抛出异常
 */
public class InProps
{
    private Props props;

    public static InProps in(Props props)
    {
        InProps inProps = new InProps();
        inProps.props = props;
        return inProps;
    }

    public static void register()
    {
    }

    public Integer asInteger(String key)
    {
        return as(key, Integer.class);
    }

    public Double asDouble(String key)
    {
        return as(key, Double.class);
    }

    public Float asFloat(String key)
    {
        return as(key, Float.class);
    }

    public String asString(String key)
    {
        return props.getValue(key);
    }

    public Byte asByte(String key)
    {
        return as(key, Byte.class);
    }

    public Character asChar(String key)
    {
        return as(key, Character.class);
    }

    public Boolean asBoolean(String key)
    {
        return as(key, Boolean.class);
    }

    public Map<String, String> asStringMap(String key)
    {
        return asStringMap(key, false);
    }

    public Map<String, String> asStringMap(String key, boolean fullKey)
    {
        return asStringMap(key, fullKey, false);
    }

    public Map<String, String> asStringMap(String key, boolean fullKey, boolean withSubValue)
    {
        final String sectionKey = key.substring(0, key.lastIndexOf("."));
        final List<String> list = new ArrayList<>();
        final Map<String, String> map = new HashMap<>();
        final int keyLength = sectionKey.length();
        final int dotChar = 46;// char code of .
        String k = getMapKey(sectionKey, key, false);

        Iterator<PropsEntry> itor = props.entries()
                                         .section(sectionKey)
                                         .iterator();
        while (itor.hasNext())
        {
            PropsEntry e = itor.next();
            String eKey = e.getKey();
            String mapKey = getMapKey(key, eKey, fullKey);

            if (mapKey == null)
                continue;

            if (withSubValue || mapKey.indexOf('.') < 0 || fullKey)
                map.put(mapKey, e.getValue());
        }

        return map;
    }

    public List<String> asStringList(String key)
    {
        return asStringList(key, false);
    }

    public List<String> asStringList(String key, boolean withSubValue)
    {
        final String sectionKey = key.substring(0, key.lastIndexOf("."));
        final List<String> list = new ArrayList<>();
        final int keyLength = sectionKey.length();
        final int dotChar = 46;// char code of .
        Iterator<PropsEntry> itor = props.entries()
                                         .section(sectionKey)
                                         .iterator();
        while (itor.hasNext())
        {
            PropsEntry e = itor.next();
            String eKey = e.getKey();

            if (withSubValue)
                list.add(e.getValue());
            else if (eKey.length() == key.length() && eKey.equals(key))
                list.add(e.getValue());
        }
        return list;
    }

    public boolean hasKey(String key)
    {
        return props.getValue(key) != null;
    }

    public <T> T as(String key, Class<T> type)
    {
        checkKeyType(key, type);
        return tryConvert(key, type);
    }

    public <T> List<T> asList(String key, Class<T> type)
    {
        return asList(key, type, false);
    }
    @SuppressWarnings("unchecked")
    public <T> List<T> asList(String key, Class<T> type, boolean withSubValue)
    {
        checkKeyType(key, type);
        List<String> list = asStringList(key, withSubValue);
        if (type == String.class)
            return (List<T>) list;// this is safe

        List<T> targetList = new ArrayList<>();
        for (String s : list)
        {
            T v = tryConvertValue(s, type);
            targetList.add(v);
        }
        return targetList;
    }

    public <T> Map<String, T> asMap(String key, Class<T> type)
    {
        return asMap(key, type, false, false);
    }
    @SuppressWarnings("unchecked")
    public <T> Map<String, T> asMap(String key, Class<T> type, boolean fullKey, boolean withSubValue)
    {
        checkKeyType(key, type);
        Map<String, String> map = asStringMap(key, fullKey, withSubValue);
        if (type == String.class)
            return (Map<String, T>) map;// this is safe
        Map<String, T> targetMap = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet())
        {
            T v = tryConvertValue(entry.getValue(), type);
            targetMap.put(entry.getKey(), v);
        }
        return targetMap;
    }

    private void checkKeyType(String key, Class<?> type)
    {
        if (key == null)
            throw new IllegalArgumentException("key must not null.");
        if (type == null)
            throw new IllegalArgumentException("type must not null.");
    }

    private <T> T tryConvert(String key, Class<T> type)
    {
        return tryConvertValue(asString(key), type);
    }

    private <T> T tryConvertValue(String string, Class<T> type)
    {
        return TypeConverterManager
                .convertType(string, type);
    }

    /**
     * 获取作为 map 时的 key 值
     * 如果 entryKey 没有包含 key 则返回 null
     */
    private String getMapKey(String key, String entryKey, boolean fullKey)
    {
        if (entryKey.length() <= key.length() || !entryKey.startsWith(key))
            return null;
        if (fullKey)
            return entryKey;
        return entryKey.substring(key.length() + 1);
    }
}

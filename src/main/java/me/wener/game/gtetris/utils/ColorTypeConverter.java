package me.wener.game.gtetris.utils;

import java.awt.Color;
import jodd.typeconverter.TypeConversionException;
import jodd.typeconverter.TypeConverter;

public class ColorTypeConverter implements TypeConverter<Color>
{
    @Override
    public Color convert(Object o)
    {
        if (o instanceof String)
            return Colors.fromHtml((String) o);
        else
            throw new TypeConversionException("所需参数必须为 String 类型 : "+o);
    }
}

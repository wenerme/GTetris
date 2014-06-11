import static java.lang.Integer.parseInt;

import com.google.common.collect.Iterators;
import java.awt.Color;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jodd.props.Props;
import jodd.props.PropsEntry;
import jodd.typeconverter.TypeConverter;
import jodd.typeconverter.TypeConverterManager;
import me.wener.game.gtetris.utils.InProps;
import org.junit.Before;
import org.junit.Test;

public class PropsTest
{

    private Props props;
    private InProps inProps;

    @Before
    public void before() throws IOException
    {
        props = new Props();
        props.load(this.getClass().getResourceAsStream("gtetris.props"));
        props.load(this.getClass().getResourceAsStream("app.props"));
        inProps = InProps.in(props);
    }

    @Test
    public void test() throws IOException
    {
        props.entries()
             .section("game.setting.cube")
             .iterator()
             .forEachRemaining(System.out::println);
        System.out.println("total size: " + Iterators.size(props.iterator()));
        System.out.println("game colors size: " + Iterators
                .size(props.entries().section("game.setting.cube").iterator()));

    }

    @Test
    public void testInProps() throws IOException
    {

        System.out.println(inProps.asString("app.info.author.name"));
        Iterator<PropsEntry> iterator = props.entries()
                                             .section("game.setting.cube")
                                             .iterator();
        // 这样可行
        iterator.forEachRemaining(e ->
        {
            if (e.getKey().equals("game.setting.cube.colors"))
                System.out.println(e);
        });
    }

    @Test
    public void testList() throws IOException
    {
        List<String> list = inProps.asStringList("game.setting.cube.colors");
        list.forEach(System.out::println);
    }

    @Test
    public void testMap() throws IOException
    {
        Map<String, String> map = inProps.asStringMap("app.info.author");
        map.forEach((k, v) -> {System.out.println(k + ":" + v);});
    }

    @Test
    public void testTypeConvert()
    {
        TypeConverterManager.register(Color.class, new TypeConverter<Color>()
        {
            @Override
            public Color convert(Object o)
            {
                // 转换 #RRGGBB 或 #AARRGGBB 为 Color 类型
                if (o instanceof String)
                {
                    String str = (String) o;
                    if (str.charAt(0) != '#')
                        return null;
                    Pattern regColor = Pattern.compile("^#(?<a>\\w{2})?(?<r>\\w{2})(?<g>\\w{2})(?<b>\\w{2})$");
                    Matcher matcher = regColor.matcher(str);

                    if (!matcher.find())
                        return null;

                    String alpha = matcher.group("a");
                    alpha = alpha == null || alpha.isEmpty() ? "FF" : alpha;
                    String red = matcher.group("r");
                    String green = matcher.group("g");
                    String blue = matcher.group("b");
                    return new Color(
                            Integer.parseInt(alpha, 16),
                            Integer.parseInt(red, 16),
                            Integer.parseInt(blue, 16),
                            Integer.parseInt(green, 16));
                }
                return null;
            }
        });

        List<Color> list = inProps.asList("game.setting.cube.colors", Color.class);
        list.forEach(System.out::println);
    }

    @Test
    public void testInject()
    {
        
    }

    @Test
    public void testConvert()
    {
        assert TypeConverterManager.convertType("123", Integer.class) == 123;
        assert TypeConverterManager.convertType("123", Byte.class) == 123;
        assert TypeConverterManager.convertType("a", Character.class) == 'a';

    }
}

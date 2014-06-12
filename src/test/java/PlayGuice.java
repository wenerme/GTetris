import com.google.inject.Guice;
import com.google.inject.Injector;
import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Named;
import jodd.props.Props;
import jodd.typeconverter.TypeConverter;
import jodd.typeconverter.TypeConverterManager;
import me.wener.game.gtetris.utils.InProps;
import me.wener.game.gtetris.utils.Prop;
import me.wener.game.gtetris.utils.PropsModule;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class PlayGuice
{

    @Prop("app.info.author.name")
    private String authorName = "this is not wener";
    @Prop("app.info.author")
    Map<String, String> authorInfo;
    @Prop("game.setting.cube.colors")
    List<Color> colors;
    @Prop(value = "game.setting", fullKey = true)
    Map<String, String> cubeInfo;

    private String name;

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

    //    @Inject
    public void setName(@Named("name") String name)
    {
        this.name = name;
    }

    @Test
    public void testInject()
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

        Injector injector = Guice.createInjector(PropsModule.of(props));

        injector.injectMembers(this);

        System.out.println("My name " + name);
        System.out.println("author name " + authorName);
        System.out.println("colors");
        colors.forEach(System.out::println);
        System.out.println("authorInfo");
        authorInfo.forEach((k, v) -> {System.out.println(k + ":" + v);});

        System.out.println("cubeInfo");
        cubeInfo.forEach((k, v) -> {System.out.println(k + ":" + v);});
    }
}

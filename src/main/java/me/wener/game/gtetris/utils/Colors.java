package me.wener.game.gtetris.utils;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Preconditions;
import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Colors
{
    private static Colors self = new Colors();
    private Colors()
    {}
    private static final Pattern regHexColor =
            Pattern.compile("^#(?<a>\\w{2})?(?<r>\\w{2})(?<g>\\w{2})(?<b>\\w{2})$");

    public static Color fromHtml(String str) throws IllegalArgumentException
    {
        checkArgument(str.charAt(0) == '#',"需要形如 #AARRGGBB 或 #RRGGBB 的参数");
        Matcher matcher = regHexColor.matcher(str);
        checkArgument(matcher.find(),"参数不匹配所需格式 : "+str);

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
}

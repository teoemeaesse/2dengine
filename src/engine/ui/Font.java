package engine.ui;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by tomas on 4/23/2017.
 */
public class Font {
    public static java.awt.Font loadFontFromFile(InputStream path, int type) {
        java.awt.Font font = null;

        try {
            font = java.awt.Font.createFont(type, path);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        return font;
    }
}

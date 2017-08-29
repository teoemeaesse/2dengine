package engine.ui;

import java.awt.*;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;

/**
 * Created by tomas on 6/7/2017.
 */
public class TextBox extends UIElement {
    private Color textColor;
    private Font font;
    private String text;
    private int offset = OFFSET_MIDDLE,
            horizontalPadding = 0, verticalPadding = 0,
            x, y, width, height;

    public TextBox(String text, String id){
        super(null, id);
        this.textColor = Color.BLACK;
        this.font = new Font("Helvetica", Font.PLAIN, 12);
        setText(text);
    }
    public TextBox(String text, String id, int horizontalPadding, int verticalPadding){
        super(null, id);
        this.textColor = Color.BLACK;
        this.font = new Font("Helvetica", Font.PLAIN, 12);
        this.horizontalPadding = horizontalPadding;
        this.verticalPadding = verticalPadding;
        setText(text);
    }
    public TextBox(String text){
        super(null);
        this.textColor = Color.BLACK;
        this.font = new Font("Helvetica", Font.PLAIN, 12);
        setText(text);
    }
    public TextBox(String text, int horizontalPadding, int verticalPadding){
        super(null);
        this.textColor = Color.BLACK;
        this.font = new Font("Helvetica", Font.PLAIN, 12);
        this.horizontalPadding = horizontalPadding;
        this.verticalPadding = verticalPadding;
        setText(text);
    }

    @Override public void init(){
    }

    @Override
    public void additionalRender(Graphics g) {
        g.setColor(textColor);
        g.setFont(font);
        g.drawString(text, x, y);
    }


    private Dimension getStringDimensions(String text, Font font){
        Dimension dimension = new Dimension();
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setFont(font);
        FontRenderContext frc = g2d.getFontRenderContext();
        GlyphVector gv = g2d.getFont().createGlyphVector(frc, text);
        Rectangle bounds = gv.getPixelBounds(null, 0, 0);
        dimension.setSize(bounds.width, bounds.height);

        return dimension;
    }

    public final void setTextColor(Color textColor) {
        this.textColor = textColor;
    }
    public final void setFont(Font font) {
        this.font = font;
    }
    public final void setText(String text) {
        this.text = text;
        Dimension dimensions = getStringDimensions(text, font);
        width = dimensions.width;
        height = dimensions.height;
    }
    public final void setX(int x) {
        this.x = x;
    }
    public final void setY(int y) {
        this.y = y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public final void setOffset(int offset) {
        this.offset = offset;
    }
    public final int getOffset() {
        return offset;
    }
    public final int getHorizontalPadding() {
        return horizontalPadding;
    }
    public final int getVerticalPadding() {
        return verticalPadding;
    }
}

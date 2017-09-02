package engine.ui;

import engine.gfx.Sprite;

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
            horizontalPadding = 0, verticalPadding = 0, backgroundPadding = 0;

    public TextBox(String text, String id, boolean instance, int backgroundPadding){
        super(null, id, instance);
        this.textColor = Color.BLACK;
        this.font = new Font("Helvetica", Font.PLAIN, 12);
        this.backgroundPadding = backgroundPadding;
        setText(text);
    }
    public TextBox(String text, boolean instance, int backgroundPadding){
        super(null, instance);
        this.textColor = Color.BLACK;
        this.font = new Font("Helvetica", Font.PLAIN, 12);
        this.backgroundPadding = backgroundPadding;
        setText(text);
    }

    @Override public void init(){
        setSprite(new Sprite(new Rectangle(0, 0, 0, 0), Color.BLACK));
        getSprite().setVisible(false);//background is by default off
    }

    @Override
    public void render(Graphics g){
        if(getSprite() != null){
            Graphics2D g2d = (Graphics2D) g;
            getSprite().draw(g2d, -backgroundPadding + 1, -backgroundPadding - getSprite().getHeight(), backgroundPadding * 2, backgroundPadding * 2);
        }
        additionalRender(g);
    }

    @Override
    public void additionalRender(Graphics g) {
        g.setColor(textColor);
        g.setFont(font);
        g.drawString(text, getSprite().getX(), getSprite().getY());
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
        getSprite().getCollisionBox().setLocation(
                (int) getSprite().getCollisionBox().getX(),
                (int) getSprite().getCollisionBox().getY()
        );
        getSprite().getCollisionBox().setSize(
                (int) dimensions.getWidth(),
                (int) dimensions.getHeight()
        );
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
    public void setHorizontalPadding(int horizontalPadding) {
        this.horizontalPadding = horizontalPadding;
    }
    public void setVerticalPadding(int verticalPadding) {
        this.verticalPadding = verticalPadding;
    }
    public final void setBackgroundVisible(boolean backgroundVisible){
        getSprite().setVisible(backgroundVisible);
    }
}

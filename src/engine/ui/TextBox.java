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
    private Dimension textDimensions;
    private Sprite parentSprite;
    private int offset = OFFSET_MIDDLE,
            horizontalPadding = 0, verticalPadding = 0;

    public TextBox(Color textColor, Font font, String text, String id){
        super(null, id);
        this.textColor = textColor;
        this.font = font;
        setText(text);
    }
    public TextBox(Color textColor, Font font, String text, String id, int horizontalPadding, int verticalPadding){
        super(null, id);
        this.textColor = textColor;
        this.font = font;
        this.horizontalPadding = horizontalPadding;
        this.verticalPadding = verticalPadding;
        setText(text);
    }
    public TextBox(Color textColor, Font font, String text){
        super(null);
        this.textColor = textColor;
        this.font = font;
        setText(text);
    }
    public TextBox(Color textColor, Font font, String text, int horizontalPadding, int verticalPadding){
        super(null);
        this.textColor = textColor;
        this.font = font;
        this.horizontalPadding = horizontalPadding;
        this.verticalPadding = verticalPadding;
        setText(text);
    }

    @Override public void init(){ }

    @Override
    public void additionalRender(Graphics g) {
        g.setColor(textColor);
        g.setFont(font);
        if(parentSprite != null){
            if(offset == OFFSET_TOP_LEFT)
                g.drawString(text, parentSprite.getX() + horizontalPadding, parentSprite.getY() + textDimensions.height + 1 + verticalPadding);
            else if(offset == OFFSET_TOP_RIGHT)
                g.drawString(text, parentSprite.getX() + parentSprite.getWidth() - textDimensions.width - 2 - horizontalPadding, parentSprite.getY() + textDimensions.height + 1 + verticalPadding);
            else if(offset == OFFSET_BOTTOM_LEFT)
                g.drawString(text, parentSprite.getX() + horizontalPadding, parentSprite.getY() + parentSprite.getHeight() - 1 - verticalPadding);
            else if(offset == OFFSET_BOTTOM_RIGHT)
                g.drawString(text, parentSprite.getX() + parentSprite.getWidth() - textDimensions.width - 2 - horizontalPadding, parentSprite.getY() + parentSprite.getHeight() - 1 - verticalPadding);
            else if(offset == OFFSET_MIDDLE)
                g.drawString(text, parentSprite.getX() + parentSprite.getWidth() / 2 - textDimensions.width / 2, parentSprite.getY() + textDimensions.height + parentSprite.getHeight() / 2 - textDimensions.height / 2);
            else if(offset == OFFSET_TOP_MIDDLE)
                g.drawString(text, parentSprite.getX() + parentSprite.getWidth() / 2 - textDimensions.width / 2, parentSprite.getY() + textDimensions.height + verticalPadding);
            else if(offset == OFFSET_LEFT_MIDDLE)
                g.drawString(text, parentSprite.getX() + horizontalPadding, parentSprite.getY() + textDimensions.height + parentSprite.getHeight() / 2 - textDimensions.height / 2);
            else if(offset == OFFSET_BOTTOM_MIDDLE)
                g.drawString(text, parentSprite.getX() + parentSprite.getWidth() / 2 - textDimensions.width / 2, parentSprite.getY() + parentSprite.getHeight() - verticalPadding);
            else if(offset == OFFSET_RIGHT_MIDDLE)
                g.drawString(text, parentSprite.getX() + parentSprite.getWidth() - textDimensions.width - horizontalPadding, parentSprite.getY() + textDimensions.height + parentSprite.getHeight() / 2 - textDimensions.height / 2);
        }
    }

    public void updateParentSprite(Sprite sprite){
        this.parentSprite = sprite;
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

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }
    public void setFont(Font font) {
        this.font = font;
    }
    public void setText(String text) {
        this.text = text;
        textDimensions = getStringDimensions(text, font);
    }
    public void setOffset(int offset) {
        this.offset = offset;
    }
}

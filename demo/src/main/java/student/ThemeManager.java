package student;
import java.awt.Color;
import java.util.HashMap;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.Raster;

public class ThemeManager {
    private Theme light;
    private Theme dark;
    private static Theme currentTheme;
    private HashMap < String, Theme > themes = new HashMap < > ();

    public ThemeManager(String currentTheme) {
        /*
         * ColorBox
         * ColorBoxLight = for use in the calendar, slightly lighter color for inputting new tasks
         * ColorText
         * ColorBorder = for use in calendar, slightly darker color for dividing up the table
         * ColorBG
         */

        dark = new Theme(
            new Color(48, 47, 78),
            new Color(58, 57, 88),
            new Color(225, 225, 225),
            new Color(150, 150, 150),
            new Color(37, 36, 62)
        );

        light = new Theme(
            new Color(221, 221, 243),
            new Color(234, 234, 250),
            new Color(58, 58, 85),
            new Color(88, 88, 115),
            new Color(231, 231, 249)
        );

        themes = new HashMap < > ();
        themes.put("dark", dark);
        themes.put("light", light);

        this.currentTheme = themes.get(currentTheme);
    }
    public void switchTheme(String currentTheme) {
        this.currentTheme = themes.get(currentTheme);
    }
    public static Color getColorBox() {
        return currentTheme.getColorBox();
    }
    public static Color getColorBoxLight() {
        return currentTheme.getColorBoxLight();
    }
    public static Color getColorText() {
        return currentTheme.getColorText();
    }
    public static Color getColorBorder() {
        return currentTheme.getColorBorder();
    }
    public static Color getColorBG() {
        return currentTheme.getColorBG();
    }
    
    public static BufferedImage tint(String fileLocation, Color color) {
        return (tintHelper(fileLocation, color, 1.0f));
    }
    public static BufferedImage tint(String fileLocation, Color color, float opacity) {
        return (tintHelper(fileLocation, color, opacity));
    }
    
    private static BufferedImage tintHelper(String fileLocation, Color color, float opacity) {
        BufferedImage image = null;
        try{
            File folderInput = new File(fileLocation);
            image = ImageIO.read(folderInput);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        
            int w = image.getWidth();
            int h = image.getHeight();
        
            final float tintOpacity = opacity;
            BufferedImage tint = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
            Graphics2D g = tint.createGraphics(); 

            g.drawImage(image, null, 0, 0);
            g.setComposite(AlphaComposite.SrcAtop);
            g.setColor(new Color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, tintOpacity));
            g.fillRect(0,0,w,h);
            
            g.dispose();
            
            return tint;
    }
    
}
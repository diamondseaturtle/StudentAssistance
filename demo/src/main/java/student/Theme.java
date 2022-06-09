package student;
import java.awt.Color;
public class Theme{
    private Color colorBox;
    private Color colorBoxLight;
    private Color colorText;
    private Color colorBorder;
    private Color colorBG;
    private Color colorImage;

    /*
     * ColorBox
     * ColorBoxLight = for use in the calendar, slightly lighter color for inputting new tasks
     * ColorText
     * ColorBorder = for use in calendar, slightly darker color for dividing up the table
     * ColorBG
     * ColorImage = tints the images
     */
    public Theme(Color colorbox, Color colorBoxLight, Color colorText, Color colorBorder, Color colorBG){
        this.colorBox = colorbox;
        this.colorBoxLight = colorBoxLight;
        this.colorText = colorText;
        this.colorBorder = colorBorder;
        this.colorBG = colorBG;
        // this.colorImage = colorImage;
    }
    public Color getColorBox(){
        return colorBox;
    }
    public Color getColorBoxLight(){
        return colorBoxLight;
    }
    public Color getColorText(){
        return colorText;
    }
    public Color getColorBorder(){
        return colorBorder;
    }
    public Color getColorBG(){
        return colorBG;
    }
    
    /*
    public Color getColorImage(){
        return colorImage;
    }
    */
}

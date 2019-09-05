import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Samir Neupane
 */
public class ImgCreator {

    public static void main(String args[]){
        createImage(20,50,200, "Yolo");
    }

    public static String createImage(int r, int g, int b, String name){
        int wd = 400;
        int ht = 400;
        Color cr = new Color(r,g,b);
        int rgb = cr.getRGB();
        BufferedImage output = new BufferedImage(wd, ht, BufferedImage.TYPE_INT_RGB);
        for (int x=0;x<wd;x++){
            for (int y=0;y<ht;y++){
                output.setRGB(x,y,rgb);
            }
        }
        try {
            File out = new File(name+".png");
            System.out.println(ImageIO.write(output, "png", out));
        } catch (IOException e) {
            return("Error! File could not be created");
        }
        return "Success!";
    }
}

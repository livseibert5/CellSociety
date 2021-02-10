package cellsociety.visuals;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Graphics {

    private static final String FONT = "Verdana";
    private static final Paint HIGHLIGHT = Color.OLIVEDRAB;
    public static final int SCREENWIDTH = 800;
    public static final int SCREENHEIGHT = 800;
    public static final Paint BACKGROUND = Color.AZURE;

//    public Group getCurrentDisplay(Grid grid){
//
//    }

    public Scene creatingLandingScreen(){
        Group root = new Group();
        Text welcome = constructText(100, 36, "Simulation Menu", FontWeight.BOLD, FONT);
        Text instructions = constructText(125, 25, "click on any simulation to start", FontWeight.NORMAL, FONT);

        root.getChildren().add(welcome);
        root.getChildren().add(instructions);

        Scene scene = new Scene(root, SCREENWIDTH, SCREENHEIGHT, BACKGROUND);

        return scene;
    }


    private Text constructText(double baseY, int size, String message, FontWeight fontWeight, String font) {
        Text welcome = new Text(75, 100, message);
        Font welcomeFont = Font.font(font, fontWeight, size);
        welcome.setFont(welcomeFont);

        Bounds textBounds = welcome.getBoundsInParent();
        double ascent = -textBounds.getMinY();
        double descent = textBounds.getMinY();
        double width = textBounds.getWidth();

        double leftX = (SCREENWIDTH-width)/2;
        double topY = baseY - ascent;
        welcome.relocate(leftX, topY);

        return welcome;
    }
}

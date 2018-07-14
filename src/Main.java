import information.MainList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import marta.*;
import sim.Simulation;

import java.io.*;

import javax.swing.table.*;

public class Main extends Application {

    static String path = "C:\\Users\\hespe\\Documents\\TeamDelta Marta\\src\\data.ser";
    private static Simulation sim = MainList.sim;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("welcome/welcomeScreen.fxml"));
        primaryStage.setTitle("Team Delta Marta Simulation");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    @Override
    public void stop() throws IOException {
//        FileOutputStream fOut = new FileOutputStream(path);
//        ObjectOutputStream oOut = new ObjectOutputStream(fOut);
//        oOut.writeObject(sim);
//        oOut.close();
//        fOut.close();

    }

    public static void main(String[] args) throws Exception {
//        if(new File(path).exists()) {
//            FileInputStream fIn = new FileInputStream(path);
//            ObjectInputStream oIn = new ObjectInputStream(fIn);
//            sim = (Simulation) oIn.readObject();
//            oIn.close();
//            fIn.close();
//        }
        launch(args);
    }
}

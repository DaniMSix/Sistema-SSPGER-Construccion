package sistema.spger;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SistemaSPGER extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("vistas/FXMLConsultarActividadesDeAnteproyectos.fxml"));
        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image("sistema/spger/utils/imagenes/Libreta.png"));
        primaryStage.setScene(scene);
        primaryStage.setTitle("SSPGER");
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
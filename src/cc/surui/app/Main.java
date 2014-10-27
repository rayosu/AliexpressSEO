package cc.surui.app;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import cc.surui.app.controllers.MainController;
import cc.surui.db.DatebaseInit;
import cc.surui.db.SQLiteJDBC;


public class Main extends Application {
	private MainController mainController;

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("app.fxml"));
	        VBox root = fxmlLoader.load();
			Scene scene = new Scene(root,1200,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("速卖通排名检索");
			primaryStage.show();
			
			mainController = (MainController)fxmlLoader.getController();
			mainController.setApp(primaryStage);
			// 初始化DB
			DatebaseInit.initDB(SQLiteJDBC.instance());

			mainController.initStoreInfo();
			mainController.initProductInfo();		
			mainController.pageProductInfo(1);
			mainController.initKeywordInfo();
//			mainController.initRankingInfo(0L);
			// 初始化监听事件
			mainController.initEvent();
//			mainController.setRankingStoreId();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

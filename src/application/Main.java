package application;
	
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;


public class Main extends Application {
	
	boolean isStop = false;
	Task copyWorker;
	
	public Parent createPage(Stage primaryStage) {
		
		
		
		Pane root = new Pane();
		
		ProgressBar progressBar = new ProgressBar();
		progressBar.setPrefSize(300, 20);
		progressBar.setLayoutX(5);
		progressBar.setLayoutY(5);
		
		
		Button Reset = new Button("Reset");
		Reset.setLayoutX(310);
		Reset.setLayoutY(2);
		
		
		Button stop = new Button("Close");
		stop.setLayoutY(2);
		stop.setLayoutX(370);
		
		root.getChildren().addAll(progressBar,Reset,stop);
		
		Reset.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				copyWorker.cancel(true);
				progressBar.progressProperty().unbind();
				progressBar.setProgress(0);
				copyWorker = createWorker();
				progressBar.progressProperty().unbind();
				progressBar.progressProperty().bind(copyWorker.progressProperty());
				
				new Thread(copyWorker).start();
				
			}
		});
		
		progressBar.setProgress(0);
		stop.setDisable(false);
		copyWorker = createWorker();
		progressBar.progressProperty().unbind();
		progressBar.progressProperty().bind(copyWorker.progressProperty());
		
		new Thread(copyWorker).start();
		
		stop.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				copyWorker.cancel(true);
				progressBar.progressProperty().unbind();
				progressBar.setProgress(0);
				
				primaryStage.close();
				
			}
			
		});
		
		
		
		
		return root;
		
	}
	
	public Task createWorker() {
		return new Task() {

			@Override
			protected Object call() throws Exception {
				// TODO Auto-generated method stub
				
				for(int i=1; i<61; i++) {
					Thread.sleep(1000);
//					if(i==60) i=1;
					updateProgress(i, 60);
					if(i==60) i=1;
					
				}
						
				return true;
			}
			
		};
	}
	
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
//			BorderPane root = new BorderPane();
			Pane root = (Pane) createPage(primaryStage);
			Scene scene = new Scene(root,435,30);
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
			});
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
//			primaryStage.initStyle(StageStyle.UNDECORATED);
//			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setAlwaysOnTop(true);
			primaryStage.setTitle("陈科羽的秒表");
			primaryStage.show();
	
		} catch(Exception e) {
			e.printStackTrace();
		}
			
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

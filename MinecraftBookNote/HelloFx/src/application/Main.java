package application;
	
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class Main extends Application {
	int current_page;
	int total_pages;
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	

	@Override
	public void start(Stage stage) throws Exception {
		//File save locations
		File save_location = new File("/test.txt");
		//Book page map
		Map<Integer,String> Book_map = new HashMap<>();
		
		//Book title
		VBox popupWindow = new VBox();
//		popupWindow.getChildren().add(new label)
		
		//Initialization
		
		Group root = new Group();
		Scene scene = new Scene(root,400,500,Color.BLACK);
		String book_css = this.getClass().getResource("application.css").toExternalForm();
		scene.getStylesheets().add(book_css);
		Font minecraftFont = Font.loadFont(getClass().getResourceAsStream("/fonts/MinecraftSeven_2.ttf"), 22);
		Font minecraftFont_user = Font.loadFont(getClass().getResourceAsStream("/fonts/MinecraftSeven_2.ttf"), 16);
		
		//Image loading
		Image icon = new Image("MinecraftBook.png");
		Image book_img = new Image("book.png");
		
		Image SaveBookIcon_selected_Img = new Image("SaveBookLight.png");
		Image SaveBookIcon_unselected_Img = new Image("SaveBookDark.png");
		
		
		Image forward_page_img = new Image("page_forward.png");
		Image forward_page_img_higlighted = new Image("page_forward_highlighted.png");
		Image backward_page_img = new Image("page_backward.png");
		Image backward_page_img_higlighted = new Image("page_backward_highlighted.png");
		
		stage.getIcons().add(icon);
		stage.setTitle("Book");
		

		stage.setResizable(false);
		stage.setY(100);
		stage.setX(1000);
		//Sound
//		URL resource = getClass().getResource("music.mp3");
		
		
		
		
		//Book setup
		ImageView book_view = new ImageView(book_img);
		book_view.setFitWidth(700);
		book_view.setPreserveRatio(true);
		
		book_view.setLayoutX(-53);
		book_view.setLayoutY(0);
		root.getChildren().add(book_view);
		
		//Book buttons
		//Forward Button set up
		ImageView forward_page = new ImageView(forward_page_img);
		ImageView forward_page_higlighted = new ImageView(forward_page_img_higlighted);
		Button forward_page_btn = new Button();
		
		forward_page_btn.setLayoutX(250);
		forward_page_btn.setLayoutY(420);
		forward_page_btn.setStyle("-fx-background-color: transparent;");
		forward_page_btn.setGraphic(forward_page);
		
		forward_page.setFitWidth(70);
		forward_page.setPreserveRatio(true);
		
		forward_page_higlighted.setFitWidth(70);
		forward_page_higlighted.setPreserveRatio(true);
		
		
		root.getChildren().add(forward_page_btn);
		//Backward Button set up
		ImageView backward_page = new ImageView(backward_page_img);
		ImageView backward_page_highlighted = new ImageView(backward_page_img_higlighted);
		Button backward_page_btn = new Button();
		
		backward_page_btn.setLayoutX(60);
		backward_page_btn.setLayoutY(420);
		backward_page_btn.setStyle("-fx-background-color: transparent;");
		backward_page_btn.setGraphic(backward_page);
		
		backward_page.setFitWidth(70);
		backward_page.setPreserveRatio(true);
		
		backward_page_highlighted.setFitWidth(70);
		backward_page_highlighted.setPreserveRatio(true);
		
		
		backward_page_btn.setDisable(true);
		backward_page_btn.setVisible(false);
		backward_page_btn.setManaged(false);
		root.getChildren().add(backward_page_btn);
		
		//Save Button set up
		ImageView SaveBookIcon_selected = new ImageView(SaveBookIcon_selected_Img);
		ImageView SaveBookIcon_unselected = new ImageView(SaveBookIcon_unselected_Img);
		Button SaveBookIcon_btn = new Button();
		
		SaveBookIcon_btn.setLayoutX(155);
		SaveBookIcon_btn.setLayoutY(400);
		SaveBookIcon_btn.setStyle("-fx-background-color: transparent;");
		SaveBookIcon_btn.setGraphic(SaveBookIcon_unselected);
		
		SaveBookIcon_selected.setFitWidth(70);
		SaveBookIcon_unselected.setFitWidth(70);
		SaveBookIcon_selected.setPreserveRatio(true);
		SaveBookIcon_unselected.setPreserveRatio(true);
		
		
		
		root.getChildren().add(SaveBookIcon_btn);
		
		
		//page title
		current_page = 1;
		total_pages = 1;
		
		String page_number = String.format("Page %d of %d",current_page,total_pages);
		
		Text page_title = new Text(page_number);
		page_title.setStyle("-fx-font-smoothing-type: gray;");
		page_title.setX(200);
		page_title.setY(60);
		page_title.setFont(minecraftFont);
		
		root.getChildren().add(page_title);
		
		//text field
		TextArea user_text = new TextArea();
		user_text.getStyleClass().add("textField");
		user_text.setPromptText("what time is it?");
		user_text.setWrapText(true);
		user_text.setLayoutY(70);
		user_text.setLayoutX(55);
		user_text.setMaxWidth(320);
//		user_text.setMaxHeight(500);
		user_text.setMinHeight(350);
		
		user_text.setFont(minecraftFont_user);
	
		root.getChildren().add(user_text);
		
		//Book logic
		//Saving Page
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Book");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		
		//Button logic
		//Save Book
		SaveBookIcon_btn.setOnMouseEntered(e ->{
			SaveBookIcon_btn.setGraphic(SaveBookIcon_selected);
			SaveBookIcon_btn.setOnMouseClicked(event ->{
				Book_map.put(current_page, user_text.getText());
				fileChooser.getExtensionFilters().addAll(
						new FileChooser.ExtensionFilter("Text Files", "*.txt"),
						new FileChooser.ExtensionFilter("All Files", "*.*")
					);
				
				File selectedFile = fileChooser.showSaveDialog(stage);
				try(FileWriter fileWriter = new FileWriter(selectedFile)){
					String Whole_book = "";
					for (int page: Book_map.keySet()) {
						
						Whole_book = (Whole_book + String.format("\n\nPage %d\n========\n\n", page) + Book_map.get(page) + "\n\n========");
					}
					fileWriter.write(Whole_book);
				}catch(IOException ex) {
					System.out.print("Error saving file: " + ex.getMessage());
				}
				
			});
		});
		SaveBookIcon_btn.setOnMouseExited(e ->{
			SaveBookIcon_btn.setGraphic(SaveBookIcon_unselected);
		});
		
		//Forward
		
		forward_page_btn.setOnMouseEntered(e ->{
			forward_page_btn.setGraphic(forward_page_higlighted);
			forward_page_btn.setOnMouseClicked(event ->{
				
				backward_page_btn.setDisable(false);
				backward_page_btn.setVisible(true);
				backward_page_btn.setManaged(true);
				
				int previous_page = current_page;
				
				
				System.out.println(previous_page);
				current_page++;
				if (current_page > total_pages) {
					total_pages++;
				}
				
				page_title.setText(String.format("Page %d of %d",current_page,total_pages));
				
				Book_map.put(previous_page, user_text.getText());
				if (Book_map.containsKey(current_page)) {
					user_text.setText(Book_map.get(current_page));
				}else {
					user_text.clear();
				}
				
				System.out.println(Book_map.get(previous_page));
				
				
				
			});
		});
		forward_page_btn.setOnMouseExited(e ->{
			forward_page_btn.setGraphic(forward_page);
		});
		
		//Backward
		backward_page_btn.setOnMouseEntered(e ->{
			backward_page_btn.setGraphic(backward_page_highlighted);
			backward_page_btn.setOnMouseClicked(event ->{
				if (current_page > 1) {
					current_page--;
				}
				
				if (current_page == 1){
					backward_page_btn.setDisable(true);
					backward_page_btn.setVisible(false);
					backward_page_btn.setManaged(false);
				}else{
					backward_page_btn.setDisable(false);
					backward_page_btn.setVisible(true);
					backward_page_btn.setManaged(true);
				}
				
				int previous_page = (current_page + 1);
				Book_map.put(previous_page, user_text.getText());
				if (Book_map.containsKey(current_page)) {
					user_text.setText(Book_map.get(current_page));
				}
				
				page_title.setText(String.format("Page %d of %d",current_page,total_pages));
			});
		});
		backward_page_btn.setOnMouseExited(e ->{
			backward_page_btn.setGraphic(backward_page);
		});
		
		
		
		

		
		stage.setScene(scene);
		stage.show();
		
	}
}

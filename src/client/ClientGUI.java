package client;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ClientGUI extends Application{
	private Connections connection = createClient();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	HBox h,h1,h2;
	VBox v;
	TextArea area;
	Button send, clear;
	Scene scene;
	Text msgTime,userName,memo;
	TextFlow Flow;
	BorderPane borderpane;
	ComboBox<String> Style,Size,FColor;
	String userNamecolor = "";
	String memocolor="";
	static class Cell extends ListCell<String>{
	      @Override
	      public void updateItem(String i, boolean empty){
	          super.updateItem(i, empty);
	          Rectangle abc = new Rectangle(43,13);
	          if(i != null){
	              abc.setFill(Color.web(i));
	              System.out.println(i);
	              setGraphic(abc);
	      }
	  }
	}
	public static void main(String[] args){
		launch(args);
	}
	public void init() throws Exception{
		connection.startConnection();
	}
	@Override
	public void start(Stage PrimaryWindow) throws Exception {
		PrimaryWindow.setTitle("Messenger");
		PrimaryWindow.setMaxHeight(600);
		PrimaryWindow.setMaxWidth(470);
		PrimaryWindow.setMinHeight(600);
		PrimaryWindow.setMinWidth(470);
	    borderpane = new BorderPane();
		area = new TextArea();
		area.setWrapText(true);
		fsize();
		final ImageView setImage = new ImageView();
		final Image img = new Image("file:src/Images/user2.png");
		setImage.setFitHeight(150);
		setImage.setFitWidth(130);
		setImage.setImage(img);
		back();

		fontcolor();
		h1 = new HBox(Style, Size, FColor);
		send = new Button("Send");
		clear = new Button("Clear");
		Flow = new TextFlow();
	  		ScrollPane scroll = new ScrollPane();
	  		scroll.setFitToWidth(true);
	  		scroll.setPrefHeight(280);
	  		scroll.setPrefWidth(270);
	  		scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
	  		scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
	  		scroll.vvalueProperty().bind(Flow.heightProperty());
	  		scroll.setContent(Flow);
	  		send.setOnAction(e -> {	
		    	memo = new Text(area.getText()+"\n");
		    	if(memo.getText().trim().length()>0){
		    	LocalTime l = LocalTime.now();
				String dateformatter = formatter.format(l);
			    msgTime = new Text("\n"+dateformatter);
				userName = new Text("\nPatri: ");
				msgTime.setStyle("-fx-font-weight:bold;");
			    memo = new Text(area.getText()+"\n");
			    Flow.getChildren().addAll(msgTime,userName,memo);
				area.clear();
				try{
					connection.send(memo.getText().toString());
	  				connection.SendNameColor(userName.getFill().toString());
	  				connection.SendMessageColor(memo.getFill().toString());
				}catch(Exception e1){
					System.out.println("Exception raised");
				}
		    	}
			});
	  		 area.setOnKeyReleased(event -> {
			  if (event.getCode() == KeyCode.ENTER){
				  memo = new Text(area.getText()+"\n");
				  if(memo.getText().trim().length()>0){
				  send.fire();
				  System.out.println("Message Color"+memocolor);
				  System.out.println("userName Color"+userNamecolor);
				  }
				  else{
					  area.clear();
				  }
			  }
			});
	  		 clear.setOnAction(e -> {
	  			 area.clear();
	  		 });
	  		 h2 = new HBox(10, send, clear);
	  		 v = new VBox(scroll, h1, area, h2);
	  		 v.getChildren().add(Flow);
	  		 v.setStyle("-fx-padding: 10;"+"-fx-background-color: #DCD7D7;");
	  		 h = new HBox(10,setImage,v);
	  		 HBox.setMargin(setImage, new Insets(20,0,0,0));
	  		 borderpane.setCenter(h);
	  		 BorderPane.setMargin(h, new Insets(10,15,10,10));
	  		 scene = new Scene(borderpane, 470, 600);
	  		 scene.getStylesheets().add("style.css");
	  		 PrimaryWindow.setScene(scene);
	  		 PrimaryWindow.show();
		}
		private void fontcolor() {

			FColor = new ComboBox<String>();
			ObservableList<String> data = FXCollections.observableArrayList(
			        "chocolate", "salmon", "gold", "coral", "darkorchid",
			        "darkgoldenrod", "lightsalmon", "black", "rosybrown", "blue",
			        "blueviolet", "brown");
			FColor.setItems(data);
			
			Callback<ListView<String>, ListCell<String>> factory = new Callback<ListView<String>, ListCell<String>>() {
		        @Override
		        public ListCell<String> call(ListView<String> list) {
		            return new Cell();
		        }
		    };
		    FColor.getSelectionModel().select("black");
		    FColor.getSelectionModel().selectedItemProperty()
		    .addListener(new ChangeListener<String>() {
		        public void changed(ObservableValue<? extends String> observable,
		                            String oldValue, String font) {		
		        		switch(font){
		        		case "chocolate":
				        	  area.setStyle("-fx-text-inner-color: #d2691e;");
				      		  memo.setFill(Color.CHOCOLATE);
				      		memocolor = memo.getFill().toString();
				      		System.out.println(memocolor+" - Here");
				      		break;
				          case "salmon":
				        	  area.setStyle("-fx-text-inner-color: #fa8072;");
					      	  memo.setFill(Color.SALMON);
					      	memocolor = memo.getFill().toString();
					      	System.out.println(memocolor+" - Here");
					      	break;
				          case "gold":
				        	  area.setStyle("-fx-text-inner-color: #D4AF37;");
				        	  memo.setFill(Color.GOLD);
				        	  memocolor = memo.getFill().toString();
				        	  System.out.println(memocolor+" - Here");
				        	  break;
				          case "coral":
				        	  area.setStyle("-fx-text-inner-color: #ff7f50;");
					      	  memo.setFill(Color.CORAL); 
					      	memocolor = memo.getFill().toString(); 
					      	System.out.println(memocolor+" - Here");
					      	break;
				          case "darkorchid":
				        	  area.setStyle("-fx-text-inner-color: #9932cc;");
				        	  memo.setFill(Color.DARKORCHID);
				        	  memocolor = memo.getFill().toString();
				        	  System.out.println(memocolor+" - Here");
				        	  break;
				          case "darkgoldenrod":
				        	  area.setStyle("-fx-text-inner-color: #b8860b;");
				        	  memo.setFill(Color.DARKGOLDENROD);
				        	  memocolor = memo.getFill().toString();
				        	  System.out.println(memocolor+" - Here");
				        	  break;
				          case "lightsalmon":
				        	  area.setStyle("-fx-text-inner-color: #ffa07a;");
				        	  memo.setFill(Color.LIGHTSALMON);
				        	  memocolor = memo.getFill().toString();
				        	  System.out.println(memocolor+" - Here");
				        	  break;
				          case "black":
				        	  area.setStyle("-fx-text-inner-color: #000000;");
				        	  memo.setFill(Color.BLACK);
				        	  memocolor = memo.getFill().toString();
				        	  System.out.println(memocolor+" - Here");
				        	  break;
				          case "rosybrown":
				        	  area.setStyle("-fx-text-inner-color: #bc8f8f;");
				        	  memo.setFill(Color.ROSYBROWN);
				        	  memocolor = memo.getFill().toString();
				        	  System.out.println(memocolor+" - Here");
				        	  break;
				          case "blue":
				        	  area.setStyle("-fx-text-inner-color: #0000FF;");
				        	  memo.setFill(Color.BLUE);
				        	  memocolor = memo.getFill().toString();
				        	  System.out.println(memocolor+" - Here");
				        	  break;
				          case "blueviolet":
				        	  area.setStyle("-fx-text-inner-color: #8A2BE2;");
				        	  memo.setFill(Color.BLUEVIOLET);
				        	  memocolor = memo.getFill().toString();
				        	  System.out.println(memocolor+" - Here");
				        	  break;
				          case "brown":
				        	  area.setStyle("-fx-text-inner-color: #A52A2A;");
				        	  memo.setFill(Color.BROWN);
				        	  memocolor = memo.getFill().toString();
				        	  System.out.println(memocolor+" - Here");
				        	  break;
				          default :
				        	  area.setStyle("-fx-text-inner-color: #000000;");
				        	  memo.setFill(Color.BLACK);
				        	  memocolor = memo.getFill().toString();
				        	  break;
				          }
		        }
		});
				FColor.setCellFactory(factory);
		    	FColor.setButtonCell(factory.call(null));
		
	}
		private void fsize() {
			Size = new ComboBox<String>();
			ObservableList<String> asdg = FXCollections.observableArrayList(
					"1","2","3","4","5","6");
			Size.setItems(asdg);
			Size.getSelectionModel().selectedItemProperty()
		    .addListener(new ChangeListener<String>() {
		        public void changed(ObservableValue<? extends String> observable,
		                            String oldValue, String font) {
		        	memo = new Text(area.getText());
		        		if (font.contains("1")) {
		        			area.setFont(Font.font(13));
		        		  } else if (font.contains("2")) {
		        			  area.setFont(Font.font(17));
		                  } else if (font.contains("3")) {
		                	  area.setFont(Font.font(18)); 
			              } else if (font.contains("4")) {
			            	  area.setFont(Font.font(20));
				          } else if (font.contains("5")) {
				        	  area.setFont(Font.font(22)); 
				          } else if (font.contains("6")) {
				        	  area.setFont(Font.font(24));  
				          } else {
				        	  area.setFont(Font.font(12)); 
		                  }
		        }
		    });	
		
	}
		private void back() {Style = new ComboBox<String>();
		ObservableList<String> qwert = FXCollections.observableArrayList(
				"black","silver","gray","pink","white","gold","blue");
		Style.setItems(qwert);
		Style.valueProperty().addListener(new ChangeListener<String>() {
		      @Override public void changed( ObservableValue<? extends String> over, String task, String swi) {
		          switch(swi){
		          case "silver":
		        	  borderpane.setStyle("-fx-background-color: #C0C0C0;");
		        	  send.setOnAction(e -> {
		        		  memo = new Text(area.getText()+"\n");
		        		  if(memo.getText().trim().length()>0){ 
		        			  setData();
			            userName.setFill(Color.SILVER);
			            userNamecolor = userName.getFill().toString();
			            Fontchange((String) FColor.getSelectionModel().getSelectedItem());
			            Flow.getChildren().addAll(msgTime,userName,memo);
		      		  try{
		  				connection.send(memo.getText().toString());
		  				connection.SendNameColor(userName.getFill().toString());
		  				connection.SendMessageColor(memo.getFill().toString());
		  			}catch(Exception e1){
		  				System.out.println("Exception raised");
		  			}
		      		  area.clear();
		        		  }
		      		});
		        	  break;
		          case "black":
		        	  borderpane.setStyle("-fx-background-color: #000000;");
		        	  send.setOnAction(e -> {
		        		  memo = new Text(area.getText()+"\n");
		        		  if(memo.getText().trim().length()>0){
		        			  setData();
		        			  userName.setFill(Color.BLACK);
		        			  userNamecolor = userName.getFill().toString();
		        			  Fontchange((String) FColor.getSelectionModel().getSelectedItem());
		        			  Flow.getChildren().addAll(msgTime,userName,memo);
		        			  try{
		        				  connection.send(memo.getText().toString());
		        				  connection.SendNameColor(userName.getFill().toString());
		        				  connection.SendMessageColor(memo.getFill().toString());
		        			  }catch(Exception e1){
		        				  System.out.println("Exception raised");
		        			  }
		        			  area.clear();
		        		  }
		      		});
		        	  break;
		          case "white":
		        	  borderpane.setStyle("-fx-background-color: #ffffff;");
		        	  send.setOnAction(e -> {	
		        		  memo = new Text(area.getText()+"\n");
		        		  if(memo.getText().trim().length()>0){ 
		        			  setData();
		      		  userName.setFill(Color.WHITE);
		      		userNamecolor = userName.getFill().toString();
		      		Fontchange((String) FColor.getSelectionModel().getSelectedItem());
		      		Flow.getChildren().addAll(msgTime,userName,memo);
		      		    try{
			  				connection.send(memo.getText().toString());
			  				connection.SendNameColor(userName.getFill().toString());
			  				connection.SendMessageColor(memo.getFill().toString());
			  			}catch(Exception e1){
			  				System.out.println("Exception raised");
			  			}
		      		  area.clear();
		        		  }
		      		});
		        	  break;
		          case "gray":
		        	  borderpane.setStyle("-fx-background-color: #808080;");
		        	  send.setOnAction(e -> {	
		        		  memo = new Text(area.getText()+"\n");
		        		  if(memo.getText().trim().length()>0){
		        			  setData();
		      		  userName.setFill(Color.GRAY);
		      		userNamecolor = userName.getFill().toString();
		      		Fontchange((String) FColor.getSelectionModel().getSelectedItem());
		      		Flow.getChildren().addAll(msgTime,userName,memo);
		      		  try{
		  				connection.send(memo.getText().toString());
		  				connection.SendNameColor(userName.getFill().toString());
		  				connection.SendMessageColor(memo.getFill().toString());
		  			}catch(Exception e1){
		  				System.out.println("Exception raised");
		  			}
		      		  area.clear();
		        		  }
		      		});
		        	  break;
		          case "blue":
		        	  borderpane.setStyle("-fx-background-color: #0000ff;");
		        	  send.setOnAction(e -> {	
		        		  memo = new Text(area.getText()+"\n");
		        		  if(memo.getText().trim().length()>0){
		        			  setData();
		      		  userName.setFill(Color.BLUE);
		      		Fontchange((String) FColor.getSelectionModel().getSelectedItem());
		      		Flow.getChildren().addAll(msgTime,userName,memo);
		      		  try{
			  				connection.send(memo.getText().toString());
			  				connection.SendNameColor(userName.getFill().toString());
			  				connection.SendMessageColor(memo.getFill().toString());
			  			}catch(Exception e1){
			  				System.out.println("Exception raised");
			  			}
		      		  area.clear();
		        		}
		      		});
		        	  break;
		          case "pink":
		        	  borderpane.setStyle("-fx-background-color: #FFC0CB;");
		        	  send.setOnAction(e -> {	
		        		  memo = new Text(area.getText()+"\n");
		        		  if(memo.getText().trim().length()>0){
		        			  setData();
		      		  userName.setFill(Color.PINK);
		      		userNamecolor = userName.getFill().toString();
		      		Fontchange((String) FColor.getSelectionModel().getSelectedItem());
		      		Flow.getChildren().addAll(msgTime,userName,memo);
		      		  try{
			  				connection.send(memo.getText().toString());
			  				connection.SendNameColor(userName.getFill().toString());
			  				connection.SendMessageColor(memo.getFill().toString());
			  			}catch(Exception e1){
			  				System.out.println("Exception raised");
			  			}
		      		  area.clear();
		        		  }
		      		});
		        	  break;
		          
		          case "gold":
		        	  borderpane.setStyle("-fx-background-color: #FFDF00;");
		        	  send.setOnAction(e -> {	
		        		  memo = new Text(area.getText()+"\n");
		        		  if(memo.getText().trim().length()>0){
		        			  setData();
		      		  userName.setFill(Color.GOLD);
		      		userNamecolor = userName.getFill().toString();
		      		Fontchange((String) FColor.getSelectionModel().getSelectedItem());
		      		Flow.getChildren().addAll(msgTime,userName,memo);
		      		  try{
			  				connection.send(memo.getText().toString());
			  				connection.SendNameColor(userName.getFill().toString());
			  				connection.SendMessageColor(memo.getFill().toString());
			  			}catch(Exception e1){
			  				System.out.println("Exception raised");
			  			}
		      		  area.clear();
		        		  }
		      		});
		        	  break;
		    
		          default :
		        	  borderpane.setStyle("-fx-background-color: #ffffff;");
		        	  userName.setFill(Color.BLACK);
		        	  break;
		          }
		      }
			private void Fontchange(String selectedItem) {
				selectedItem = (String) FColor.getSelectionModel().getSelectedItem();
				switch(selectedItem){
		          case "chocolate":
		        	  memo.setFill(Color.CHOCOLATE);
		        	  memocolor = memo.getFill().toString();
		        	  break;
		          case "salmon":
		        	  memo.setFill(Color.SALMON);
		        	  memocolor = memo.getFill().toString();
		        	  break;
		          case "gold":
		        	  memo.setFill(Color.GOLD);
		        	  memocolor = memo.getFill().toString();
		        	  break;
		          case "coral":
		        	  memo.setFill(Color.CORAL);
		        	  memocolor = memo.getFill().toString();
		        	  break;
		          case "darkorchid":
		        	  memo.setFill(Color.DARKORCHID);
		        	  memocolor = memo.getFill().toString();
		        	  break;
		          case "darkgoldenrod":
		        	  memo.setFill(Color.DARKGOLDENROD);
		        	  memocolor = memo.getFill().toString();
		        	  break;
		          case "lightsalmon":
			      	  memo.setFill(Color.LIGHTSALMON);
			      	memocolor = memo.getFill().toString();
			      	break;
		          case "black":
			      	  memo.setFill(Color.BLACK);
			      	memocolor = memo.getFill().toString(); 
			      	break;
		          case "rosybrown":
			      	  memo.setFill(Color.ROSYBROWN);
			      	memocolor = memo.getFill().toString();
			      	break;
		          case "blue":
			      	  memo.setFill(Color.BLUE);
			      	memocolor = memo.getFill().toString();  
			      	break;
		          case "blueviolet":
			      	  memo.setFill(Color.BLUEVIOLET);
			      	memocolor = memo.getFill().toString(); 
			      	break;
		          case "brown":
			      	  memo.setFill(Color.BROWN);
			      	memocolor = memo.getFill().toString(); 
			      	break;
		          default :
			      	  memo.setFill(Color.BLACK);
			      	memocolor = memo.getFill().toString(); 
			      	break;
		          }
				return;
			}  
		  });  	
		
	}
	
		protected void setData() {
			LocalTime timp = LocalTime.now();
			String dateformatter = formatter.format(timp);
		    msgTime = new Text("\n"+dateformatter);
			msgTime.setStyle("-fx-font-weight:bold;");
			userName = new Text("\nPatri: ");
		    memo = new Text(area.getText()+"\n");	
	}
	public void stop() throws Exception{
		connection.closeConnection();
	}
	public String map(String s){
		switch(s){
		case "0x000000ff":
			return "black";
		case "0xc0c0c0ff":
			return "silver";
		case "0x808080ff":
			return "gray";
		case "0xffc0cbff":
			return "pink";
		case "0xffffffff":
			return "white";
		case "0xffd700ff":
			return "gold";
		case "0x0000ffff":
			return "blue";
		case "0xd2691eff":
			return "chocolate";
		case "0xfa8072ff":
			return "salmon";
		case "0xff7f50ff":
			return "coral";
		case "0x9932ccff":
			return "darkorchid";
		case "0xb8860bff":
			return "darkgoldenrod";
		case "0xffa07aff":
			return "lightsalmon";
		case "0xbc8f8fff":
			return "rosybrown";
		case "0x8a2be2ff":
			return "blueviolet";
		case "0xa52a2aff":
			return "brown";
		default:
			return "black";
		}
	}
	private client createClient(){
		return new client("127.0.0.1",12345, data ->{
			Platform.runLater(()->{
				memo = new Text(data+"\n");
		    	if(memo.getText().trim().length()>0){
		    	LocalTime l = LocalTime.now();
				String dateformatter = formatter.format(l);
			    msgTime = new Text("\n"+dateformatter);
				userName = new Text("\nSudha: ");
				msgTime.setStyle("-fx-font-weight:bold;");
			    memo = new Text(data.toString()+"\n");
				area.clear();
		    	}
			});
		}, message->{
			Platform.runLater(()->{
				String a = map(message.toString());
			 userName.setFill(Color.web(a));
			});
		}, name->{
			Platform.runLater(()->{
				String b = map(name.toString());
			memo.setFill(Color.web(b));
			Flow.getChildren().addAll(msgTime,userName,memo);
			});
		});
	}
}


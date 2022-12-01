package com.example.supplychain;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class SupplyChain extends Application {

    public static final int height = 600, width = 700, upperLine=50, lowerLine=50;
    Pane bodyPane = new Pane();

    boolean loggedIn = false;
    public Login loginCheck = new Login();
    ProductDetails details = new ProductDetails();
    Label loginLabel;
    Button loginButton;
    Button orderButton;
    private GridPane headerBar(){
        GridPane head = new GridPane();
        head.setPrefSize(width,upperLine-5);
        head.setAlignment(Pos.CENTER);
        head.setHgap(5);

        TextField searchText = new TextField();
        searchText.setMinWidth(250);
        searchText.setPromptText("Search");

         loginLabel = new Label("");

        loginButton = new Button("SignIn");
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(loggedIn == false){
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(loginPage());
                    //loginButton.setText("Logout");
                }
                else{
                    loginLabel.setText("");
                    loginButton = new Button("SignIn");
                    loggedIn = false;
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(details.getAllProducts());
                }
            }
        });

        Button searchButton = new Button("Search");
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                //bodyPane.getChildren().add(details.getAllProducts());
                String search = searchText.getText();
                bodyPane.getChildren().add(details.getProductsByName(search));
            }
        });
        head.add(searchText,0,0);
        head.add(searchButton,1,0);
        head.add(loginLabel,3,0);
        head.add(loginButton,6,0);
        return head;
    }
    private GridPane footerBar(){
        GridPane footer = new GridPane();
        orderButton = new Button("Buy Now");

        orderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(loggedIn==false){
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(loginPage());
                }
                else{
                    Product product = details.getSelectedProduct();
                    if(product != null){
                       String email = loginCheck.CustomerEmailId;
                        System.out.println(email);
                        if(Order.placeOrder(product,email))
                            System.out.println("Order Placed");
                    }
                    else{
                        System.out.println("Select a product");
                    }
                }
            }
        });

        footer.add(orderButton,0,0);
        footer.setMinWidth(width);
        footer.setTranslateY(height+65);
        footer.setTranslateX(20);
        return footer;
    }
    private GridPane loginPage(){
        Label email = new Label("Email");
        Label password = new Label("Password");
        Label messagelabel = new Label("");

        TextField UserMailId = new TextField();
        UserMailId.setPromptText("Please enter the emailId");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Please enter the password");

        Button login = new Button("Login");
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String email = UserMailId.getText();
                String password = passwordField.getText();
                if(loginCheck.customerLogin(email,password)){
                    loginLabel.setText("Hi " + loginCheck.CustomerName);
                    loginButton = new Button("SignOut");
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(details.getAllProducts());
                    loggedIn = true;
                    //messagelabel.setText("Login Successful");
                }
                else
                    messagelabel.setText("Invalid User");
                //messagelabel.setText("email :"+email+" "+"password :"+password);
            }
        });

        GridPane gridpane = new GridPane();
        gridpane.setMinSize(bodyPane.getMinWidth(), bodyPane.getMinHeight());
        gridpane.setAlignment(Pos.CENTER);
        gridpane.setVgap(10);//vertical gap
        gridpane.setHgap(10);//horizontal gap
        gridpane.add(email,0,0);
        gridpane.add(UserMailId,1,0);
        gridpane.add(password,0,1);
        gridpane.add(passwordField,1,1);
        gridpane.add(login,0,3);
        gridpane.add(messagelabel, 1, 3);


        return gridpane;
    }
    Pane content(){
        //Pane is a datatype
        Pane root = new Pane();
        root.setPrefSize(width,upperLine+height+lowerLine);
        bodyPane.setTranslateY(upperLine);
        bodyPane.setMinSize(width,height);
//        bodyPane.setBorder(new Border(Color.BLACK);
//        bodyPane.setStyle("-fx-background-color: #C0C9C0;");
        bodyPane.getChildren().addAll(details.getAllProducts());
        root.getChildren().addAll(headerBar(),bodyPane,footerBar());
        return root;
    }
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(SupplyChain.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(content());
        stage.setTitle("SupplyChain");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
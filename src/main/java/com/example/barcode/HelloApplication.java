package com.example.barcode;

import java.io.*;
import java.sql.Connection;
import java.text.ParseException;
import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.BaseDirection;
import com.itextpdf.text.pdf.languages.ArabicLigaturizer;
import com.itextpdf.text.pdf.languages.LanguageProcessor;
import javafx.application.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

//import pdf.pdftest.BillPrintable;
import javafx.scene.Scene;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
//import javafx.scene.text.Font;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.ImageIcon;

import javax.swing.JRootPane;

//import com.itextpdf.kernel.color.Color;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.property.TextAlignment;

public class HelloApplication extends Application {

    ObservableList<Prodect> ProdectList = FXCollections.observableArrayList();
    ObservableList<MonthSales> MonthSalesList = FXCollections.observableArrayList();
    ObservableList<YearSales> YearSalesList = FXCollections.observableArrayList();
    ObservableList<Prodect> DaySalesList = FXCollections.observableArrayList();
    ObservableList<Customer> CustomerList = FXCollections.observableArrayList();
    ObservableList<Prodect> ItemsData = FXCollections.observableArrayList();
    ObservableList<Prodect> ItemsSales = FXCollections.observableArrayList();

    ArrayList<ArrayList<Prodect>> temp = new ArrayList<ArrayList<Prodect>>();
    int v = 0;
    int b = 0;
    int finalflag = 0;
    Double bHeight = 0.0;
    DataBaseConnection db = new DataBaseConnection();

    File f = null;
    String path = null;

    double cash = 0;
    double balance = 0;



    private void readDataBaseCustomer() throws SQLException, ClassNotFoundException {

        try {
            Connection con = db.getConnection().connectDB();
            String sql = "SELECT * FROM Customer";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                CustomerList.add(
                        new Customer(rs.getString(1),rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getString(5), rs.getString(6)));
            }
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void readDataBaseProdect() throws SQLException, ClassNotFoundException {

        try {
            Connection con = db.getConnection().connectDB();
            String sql = "SELECT * FROM Prodect";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                ProdectList.add(new Prodect(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getInt(4),
                        rs.getDouble(5)));

            }

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void readDataBaseDaySales() throws SQLException, ClassNotFoundException {

        try {
            Connection con = db.getConnection().connectDB();
            String sql = "SELECT * FROM daySales";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                DaySalesList.add(new Prodect(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4),
                        rs.getInt(5), rs.getDouble(6), rs.getDouble(7), rs.getDouble(8) , rs.getInt(9)));

            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    private void readDataBaseItemsSales() throws SQLException, ClassNotFoundException {

        try {
            Connection con = db.getConnection().connectDB();
            String sql = "SELECT * FROM ItemsSales";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ItemsSales.add(new Prodect(rs.getString(1), rs.getDouble(2), rs.getDouble(3),
                        rs.getInt(4), rs.getDouble(5), rs.getDouble(6), rs.getDouble(7) , rs.getInt(8)));

            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }



    private void readDataBaseMonthSales() throws SQLException, ClassNotFoundException {
        try {
            Connection con = db.getConnection().connectDB();
            String sql = "SELECT * FROM MonthSales";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                double total1 = rs.getDouble("total");
                String monthDate2 = rs.getString("Monthdate");
                double profit = rs.getDouble("proft");
                int id = rs.getInt("id");

                MonthSalesList.add(new MonthSales( id,total1, monthDate2, profit));
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private void readDataBaseYearSales() throws SQLException, ClassNotFoundException {
        Connection con = db.getConnection().connectDB();
        try {
            String sql = "SELECT * FROM YearSales";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                YearSalesList.add(new YearSales(rs.getInt(1),rs.getDouble(2), rs.getString(3), rs.getDouble(4)));

            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    private void readDateFromFile() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Use ClassLoader to load the resource from the project directory
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("Expire.txt");

            if (inputStream != null) {
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader br = new BufferedReader(reader);

                String dateText = br.readLine();
                if (dateText != null) {
                    // Parse the date from the text file
                    Date fileDate = dateFormat.parse(dateText);

                    // Get the current date
                    Date currentDate = new Date();

                    // Set the time components of both dates to midnight
                    Calendar fileCalendar = Calendar.getInstance();
                    fileCalendar.setTime(fileDate);
                    fileCalendar.set(Calendar.HOUR_OF_DAY, 0);
                    fileCalendar.set(Calendar.MINUTE, 0);
                    fileCalendar.set(Calendar.SECOND, 0);
                    fileCalendar.set(Calendar.MILLISECOND, 0);
                    fileDate = fileCalendar.getTime();

                    Calendar currentCalendar = Calendar.getInstance();
                    currentCalendar.setTime(currentDate);
                    currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
                    currentCalendar.set(Calendar.MINUTE, 0);
                    currentCalendar.set(Calendar.SECOND, 0);
                    currentCalendar.set(Calendar.MILLISECOND, 0);
                    currentDate = currentCalendar.getTime();

                    System.out.println("File date: " + dateFormat.format(fileDate));
                    System.out.println("Current date: " + dateFormat.format(currentDate));

                    // Compare the dates
                    if (fileCalendar.compareTo(currentCalendar) <= 0) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Expired");
                        alert.setContentText("The program has expired");
                        alert.showAndWait();

                        System.exit(0);
                    } else {
                        System.out.println("File date is after the current date.");
                        // Perform other actions or continue with your application logic
                    }
                } else {
                    System.out.println("The file is empty.");
                    // Handle the case where the file is empty
                }

                // Close the BufferedReader and InputStreamReader
                br.close();
                reader.close();
            } else {
                System.out.println("Expire.txt not found in the project directory.");
                // Handle the case where the file is not found
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }





    public void read() {
        temp.add(new ArrayList<Prodect>());
    }
    protected static double cm_to_pp(double cm) {
        return toPPI(cm * 0.393600787);
    }

    protected static double toPPI(double inch) {
        return inch * 72d;
    }

    public PageFormat getPageFormat(PrinterJob pj) {

        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();

        double bodyHeight = bHeight;
        double headerHeight = 5.0;
        double footerHeight = 5.0;
        double width = cm_to_pp(8);
        double height = cm_to_pp(headerHeight + bodyHeight + footerHeight);
        paper.setSize(width, height);
        paper.setImageableArea(0, 10, width, height - cm_to_pp(1));

        pf.setOrientation(PageFormat.PORTRAIT);
        pf.setPaper(paper);

        return pf;
    }

    public class BillPrintable implements Printable {
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

            int r = ItemsData.size();
            ImageIcon icon = new ImageIcon(
                    getClass().getResource("/logon.png"));
            int result = NO_SUCH_PAGE;
            if (pageIndex == 0) {

                Graphics2D g2d = (Graphics2D) graphics;
                double width = pageFormat.getImageableWidth();
                g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());

                try {


                    int y = 20;
                    int yShift = 10;
                    int headerRectHeight = 15;
                    int itemNameColumn = 10;
                    int quantityColumn = 85;
                    int priceColumn = 125;
                    int TotalpriceColumn = 175;

                    int tableX = itemNameColumn - 5; // Adjust the X-coordinate as needed
                    int tableY = y - yShift; // The Y-coordinate for the top of the table
                    int tableWidth = TotalpriceColumn + 50; // Adjust the width as needed
                    int tableHeight = y + yShift - tableY + 5; // Adjust the height as needed

                    JRootPane rootPane = new JRootPane();
                    g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
                    g2d.drawImage(icon.getImage(), 75, 10, 90, 40, rootPane);
                    y += yShift + 30;
                    g2d.drawLine(tableX, y-5, tableX + tableWidth, y-5);
                    y += yShift;
                    g2d.drawString("           AbuThaher.com        ", 12, y);
                    y += yShift;
                    g2d.drawString("      No 00000 Address Line One ", 12, y);
                    y += yShift;
                    g2d.drawString("      Address  Ramallah-Palestion ", 12, y);
                    y += yShift;
                    g2d.drawString("   www.facebook.com/mohammad.mashhour.399 ", 12, y);
                    y += yShift;
                    g2d.drawString("        +972569482508      ", 12, y);
                    y += yShift;


                    g2d.drawLine(tableX, y-5, tableX + tableWidth, y-5);
                    y += headerRectHeight;

                    g2d.drawString("Name      Quantity   Price    Total   ", itemNameColumn, y);
                    y += yShift;
                    g2d.drawLine(tableX, y-5, tableX + tableWidth, y-5);
                    y += yShift;



                    for (int i = 0; i < ItemsData.size(); i++) {
                        double b = ItemsData.get(i).getPrice();
                        int v = ItemsData.get(i).getQuantity();
                        double res = b * v;

                        // Item Name
                        g2d.drawString(ItemsData.get(i).getName(), itemNameColumn, y);

                        // Quantity
                        g2d.drawString(Integer.toString(ItemsData.get(i).getQuantity()), quantityColumn, y);

                        // Price
                        g2d.drawString(Double.toString(ItemsData.get(i).getPrice()), priceColumn, y);

                        // Total
                        g2d.drawString(Double.toString(ItemsData.get(i).getTotal()),TotalpriceColumn,y);

                        y += yShift;
                    }
                    g2d.drawLine(tableX, y, tableX + tableWidth, y);
                    y += yShift;
                    double sumAmout1 = 0;
                    for (Prodect o : ItemsData) {

                        sumAmout1 = o.getTotal() + sumAmout1;
                    }

                    balance = cash - sumAmout1;
                    g2d.setFont(new Font("Monospaced", Font.BOLD
                    , 12));
                    g2d.drawString(" Total amount:       " + sumAmout1 + "   ", 10, y);
                    y += yShift;
                    g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
                    g2d.drawLine(tableX, y-5, tableX + tableWidth, y-5);
                    y += yShift;
                    g2d.drawString("*************************************", 10, y);
                    y += yShift;
                    g2d.drawString("       THANK YOU COME AGAIN            ", 10, y);
                    y += yShift;
                    g2d.drawString("*************************************", 10, y);
                    y += yShift;
                    g2d.drawString("       SOFTWARE BY:AbuThaher          ", 10, y);
                    y += yShift;
                    g2d.drawString("   CONTACT: mohammadmashhour24@gmail.com       ", 10, y);
                    y += yShift;

                }

                catch (Exception e) {
                    e.printStackTrace();
                }

                result = PAGE_EXISTS;
            }
            return result;
        }

    }

    static Cell getHeaderTextCell(String textValue) {

        return new Cell().add(textValue).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);
    }

    static Cell getHeaderTextCellValue(String textValue) {

        return new Cell().add(textValue).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }

    static Cell getBillingandShippingCell(String textValue) {

        return new Cell().add(textValue).setFontSize(12f).setBold().setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.LEFT);
    }

    static Cell getCell10fLeft(String textValue, Boolean isBold) {
        Cell myCell = new Cell().add(textValue).setFontSize(10f).setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.LEFT);
        return isBold ? myCell.setBold() : myCell;

    }

    String usernameAlert = "";
    String passwordAlert = "";

    Scene LoginPAGE,SiginUpPAGE ,ItemesPage,SaleItemsPage,CustomerPage,
            DaySalesPage,MonthSalesPage ,YearSalesPage;


    @Override
    public void start(Stage stage) throws IOException {

        try {
            readDateFromFile();
            readDataBaseCustomer();
            readDataBaseProdect();
            readDataBaseDaySales();
            readDataBaseMonthSales();
            readDataBaseYearSales();
          //  readDataBaseItemsSales();
            read();

        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
//====================================  LogIn  ==========================================================================
        Pane LoginPane = new Pane();
        Image mh8 = new Image("loginBG.jpg");
        ImageView mah8 = new ImageView(mh8);
        mah8.setFitHeight(563);
        mah8.setFitWidth(900);

        TextField Text1 = new TextField();
        Text1.setPrefHeight(28);
        Text1.setPrefWidth(175);
        Text1.setLayoutX(573);
        Text1.setLayoutY(223);
        Text1.setPromptText("Enter a UserName");

        PasswordField pass = new PasswordField();
        pass.setPrefHeight(28);
        pass.setPrefWidth(175);
        pass.setLayoutX(573);
        pass.setLayoutY(281);
        pass.setPromptText("Enter a Password");

        Hyperlink signupLabel = new Hyperlink("Not Here? Sign Up");
        signupLabel.setLayoutX(600);
        signupLabel.setLayoutY(400);
        signupLabel.setFont(javafx.scene.text.Font.font("Barlow Condensed", 12));
        signupLabel.setTextFill(Color.WHITE);
        signupLabel.setOnAction(e -> {
            Text1.clear();
            pass.clear();
            stage.setScene(SiginUpPAGE);
            stage.setTitle("تسجيل حساب جديد");

        });

        Button But = new Button("دخول", new ImageView("key.png"));
        But.setPrefHeight(34);
        But.setPrefWidth(162);
        But.setLayoutX(564);
        But.setLayoutY(357);
        But.setOnAction(e -> {
            if(Text1.getText().isEmpty() || pass.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("خطأ");
                alert.setHeaderText("اسم المستخدم أو كلمة المرور خاطئة");
                alert.setContentText("الرجاء إدخال اسم مستخدم وكلمة مرور صحيحة");
                alert.showAndWait();
                return;
            }
            String user="";
            String passd="";
            int fg = 0;
            try {
                Connection con = db.getConnection().connectDB();
                String sql = "SELECT * FROM login WHERE username = '" + Text1.getText() + "' AND Password = '" + pass.getText() + "'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    fg = 1;
                    user = rs.getString("username");
                    passd = rs.getString("Password");
                }


            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if(fg==1 && user.equals(Text1.getText()) && passd.equals(pass.getText())){
                stage.setScene(SaleItemsPage);
                stage.setTitle("الصفحة الرئيسية");
                usernameAlert = Text1.getText();
                passwordAlert = pass.getText();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("خطأ");
                alert.setHeaderText("اسم المستخدم أو كلمة المرور خاطئة");
                alert.setContentText("الرجاء إدخال اسم مستخدم وكلمة مرور صحيحة");
                alert.showAndWait();
                return;
            }


            Text1.clear();
            pass.clear();

        });


        LoginPane.getChildren().addAll(mah8, Text1, pass, But, signupLabel);

        LoginPAGE = new Scene(LoginPane, 900, 563);
        LoginPAGE.getStylesheets().add(getClass().getResource("login.css").toExternalForm());
//=========================================  SignUp ====================================================================================
        Image Regpageimage = new Image("signup.jpg");
        ImageView RegBackImage = new ImageView(Regpageimage);
        RegBackImage.setFitHeight(563);
        RegBackImage.setFitWidth(900);


        TextField userName = new TextField();
        userName.setPrefSize(175, 28);
        userName.setLayoutX(563);
        userName.setLayoutY(198);
        userName.setPromptText("Enter a UserName");


        PasswordField password = new PasswordField();
        password.setPrefSize(175, 28);
        password.setLayoutX(563);
        password.setLayoutY(255);
        password.setPromptText("Enter a Password");

        PasswordField choiceBox = new PasswordField();
        choiceBox.setLayoutX(563);
        choiceBox.setLayoutY(310);
        choiceBox.setPrefSize(175, 28);
        choiceBox.setPromptText("Enter The Secret Code");
        choiceBox.setStyle("-fx-background-color: transparent");


        Hyperlink backLoginLabel = new Hyperlink("Back to Login !");
        backLoginLabel.setLayoutX(594);
        backLoginLabel.setLayoutY(418);
        backLoginLabel.setFont(javafx.scene.text.Font.font("Barlow Condensed", 12));
        backLoginLabel.setTextFill(Color.WHITE);
        backLoginLabel.setOnAction(e -> {
            userName.clear();
            password.clear();

            stage.setScene(LoginPAGE);
            stage.setTitle("تسجيل الدخول");
        });

        Button Register = new Button("Sign Up", new ImageView("key.png"));
        Register.setPrefSize(162, 34);
        Register.setLayoutX(556);
        Register.setLayoutY(371);
        Register.setContentDisplay(ContentDisplay.LEFT);
        Register.setOnAction(e->{
            int flag= 0;
            if(userName.getText().isEmpty() || password.getText().isEmpty() || choiceBox.getText().isEmpty()){
                //Alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("خطأ");
                alert.setHeaderText("خطأ");
                alert.setContentText("الرجاء إدخال كافة المعلومات");
                alert.showAndWait();
                return;
            }

            try {

                Connection con = db.getConnection().connectDB();
                String sql = "Select * from login where username = '" + userName.getText() + "'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                if (!rs.next()) {
                    flag = 1;
                    System.out.println("Username is available");
                }
                con.close();

            } catch (Exception e2) {
                e2.getMessage();
            }
            if(flag == 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("خطأ");
                alert.setHeaderText("خطأ");
                alert.setContentText("اسم المستخدم مسجل بالفعل");
                alert.showAndWait();
                return;
            }else {
                if (choiceBox.getText().equals("nimer")) {
                    try {
                        Connection con = db.getConnection().connectDB();
                        String sql = "INSERT INTO login (username, password) VALUES ('" + userName.getText() + "', '" + password.getText() + "')";
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate(sql);
                        con.close();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("نجاح");
                        alert.setHeaderText("نجاح");
                        alert.setContentText("لقد قمت بالتسجيل بنجاح");
                        alert.showAndWait();
                        userName.clear();
                        password.clear();
                        choiceBox.clear();
                    } catch (Exception e1) {
                        e1.getMessage();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("خطأ");
                    alert.setHeaderText("خطأ");
                    alert.setContentText("رمز سري خاطئ");
                    alert.showAndWait();
                    return;
                }
            }

        });


        Pane RegEPane = new Pane();
        RegEPane.getChildren().addAll(RegBackImage, userName, password, choiceBox, Register, backLoginLabel);
        SiginUpPAGE = new Scene(RegEPane, 900, 563);
        SiginUpPAGE.getStylesheets().add(getClass().getResource("login.css").toExternalForm());
//===========================================   ItemesPage   ==============================================================================================

        Image mh1 = new Image("mainPageEx.jpg");
        ImageView mah1 = new ImageView(mh1);


        Pane Prodect1 = new Pane();
        TableView<Prodect> Pro = new TableView<Prodect>();
        Pro.setEditable(true);
        Button BProdect = new Button("رجوع");
        BProdect.setPrefHeight(55);
        BProdect.setPrefWidth(90);
        BProdect.setContentDisplay(ContentDisplay.TOP);
        BProdect.setId("button");
        BProdect.setLayoutX(188);
        BProdect.setLayoutY(432);
        BProdect.setOnAction(e -> {

            stage.setScene(SaleItemsPage);
            stage.setTitle("الصفحة الرئيسية");
        });

        Button DProdect = new Button("حذف");
        DProdect.setPrefHeight(55);
        DProdect.setPrefWidth(90);
        DProdect.setContentDisplay(ContentDisplay.TOP);
        DProdect.setId("button");
        DProdect.setLayoutX(644);
        DProdect.setLayoutY(432);
        DProdect.setOnAction(e -> {
            if (Pro.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.NONE, "يحب ان تختار حقل ", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }
                return;
            }
            Prodect selectedItem = Pro.getSelectionModel().getSelectedItem();
            String IdSelected = selectedItem.getID();


            try {

                Connection con = db.getConnection().connectDB();
                String sql = "Delete from prodect WHERE ID='" + IdSelected + "'";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(sql);
                con.close();


                Alert alert = new Alert(Alert.AlertType.NONE, "تم الحذف بنجاح ", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }


            } catch (Exception e2) {
                e2.printStackTrace();
            }

                ProdectList.remove(selectedItem);
                Pro.refresh();


        });




        TextField FliterProdectex = new TextField();
        FliterProdectex.setLayoutX(376);
        FliterProdectex.setLayoutY(50);


        Label lebpp = new Label();
        lebpp.setText("Search Box :");
        lebpp.setPrefHeight(30);
        lebpp.setPrefWidth(130);
        lebpp.setFont(javafx.scene.text.Font.font("Oranienbaum", 15));
        lebpp.setTextFill(Color.WHITE);
        lebpp.setLayoutX(217);
        lebpp.setLayoutY(50);

        Button BCProdect = new Button("اضافة");
        BCProdect.setPrefHeight(55);
        BCProdect.setPrefWidth(90);
        BCProdect.setContentDisplay(ContentDisplay.TOP);
        BCProdect.setId("button");
        BCProdect.setLayoutX(427);
        BCProdect.setLayoutY(432);
        BCProdect.setOnAction(e -> {

            //=========================================== Add Items page==============================================================================================



            Image mh6 = new Image("mainPageEx.jpg");
            ImageView mah6 = new ImageView(mh6);


            Pane AddProdect = new Pane();

            Label pro = new Label();
            pro.setText("رقم الباركود :");
           pro.setLayoutX(342);
            pro.setLayoutY(53);
            pro.setTextFill(Color.WHITE);


            Label pro1 = new Label();
            pro1.setText(" اسم المنتج :");
            pro1.setLayoutX(342);
            pro1.setLayoutY(110);
            pro1.setTextFill(Color.WHITE);

            Label pro2 = new Label();
            pro2.setText("سعر الشراء :");
            pro2.setLayoutX(342);
            pro2.setLayoutY(167);
            pro2.setTextFill(Color.WHITE);

            Label pro3 = new Label();
            pro3.setText("نسخ المنتج :");
            pro3.setLayoutX(342);
            pro3.setLayoutY(224);
            pro3.setTextFill(Color.WHITE);

            Label pro4 = new Label();
            pro4.setText("سعر البيع :");
            pro4.setLayoutX(342);
            pro4.setLayoutY(281);
            pro4.setTextFill(Color.WHITE);



            TextField tex5 = new TextField();
            tex5.setLayoutX(84);
            tex5.setLayoutY(49);

            TextField tex6e = new TextField();
            tex6e.setLayoutX(84);
            tex6e.setLayoutY(106);


            TextField tex7e = new TextField();
            tex7e.setLayoutX(84);
            tex7e.setLayoutY(163);


            TextField tex8e = new TextField();
            tex8e.setLayoutX(84);
            tex8e.setLayoutY(220);


            TextField AT = new TextField();
            AT.setLayoutX(84);
            AT.setLayoutY(277);


            Button addProButton = new Button("اضافة");
            addProButton.setPrefHeight(60);
            addProButton.setPrefWidth(90);
            addProButton.setLayoutX(233);
            addProButton.setLayoutY(351);
            addProButton.setTextFill(Color.BLACK);
            addProButton.setContentDisplay(ContentDisplay.TOP);
            addProButton.setId("button");

            addProButton.setOnAction(ed -> {
                if (tex5.getText().isEmpty() || tex6e.getText().isEmpty() || tex7e.getText().isEmpty()
                        || tex8e.getText().isEmpty() || AT.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.NONE, "يجب عليك ملء حقول النص", ButtonType.OK);
                    if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                    }
                    return;

                } else

                    try {

                        Connection con = db.getConnection().connectDB();

                        String sql = "insert into Prodect (ID,Name,Purchase,NumCopy,price) values('"
                                + tex5.getText() + "','" + tex6e.getText() + "','" + tex7e.getText() + "','"
                                + tex8e.getText() + "','" + AT.getText() + "')";

                        ProdectList.add(new Prodect(tex5.getText(), tex6e.getText(), Double.parseDouble(tex7e.getText()),
                                Integer.parseInt(tex8e.getText()), Double.parseDouble(AT.getText())));
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate(sql);
                        Alert alert = new Alert(Alert.AlertType.NONE, "تم اضافة المنتج بنجاح", ButtonType.OK);
                        if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                        }
                        tex5.setText("");
                        tex6e.setText("");
                        tex7e.setText("");
                        tex8e.setText("");
                        AT.setText("");
                        path = null;
                        con.close();
                    } catch (Exception m) {
                        System.out.println(m);
                        Alert alert = new Alert(Alert.AlertType.NONE, "حاول مرة اخرى", ButtonType.OK);
                        if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                        }

                    }

            });




            AddProdect.getChildren().addAll(mah6, pro, pro1, pro2, pro3, pro4, tex5, tex6e, tex7e, tex8e, AT,
                    addProButton);
           Scene AddItemesPage= new Scene(AddProdect, 500, 416);
            AddItemesPage.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            Stage AddItemsstage = new Stage();
            AddItemsstage.setTitle("اضافة منتج");
            AddItemsstage.setResizable(false);
            AddItemsstage.setScene(AddItemesPage);
            AddItemsstage.show();

        });

        Pro.setLayoutX(179);
        Pro.setLayoutY(101);
        TableColumn<Prodect, String> name = new TableColumn<>("الاسم");
        name.setPrefWidth(90);
        name.setResizable(false);
        name.setCellValueFactory(new PropertyValueFactory<Prodect, String>("name"));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Prodect, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Prodect, String> arg0) {

                arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).setName(arg0.getNewValue());
                String id = arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getID();
                String name = arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getName();

                try {

                    Connection con = db.getConnection().connectDB();
                    String sql = "UPDATE prodect set Name ='" + name + "'  WHERE ID='" + id + "'";
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(sql);
                    con.close();

                } catch (Exception e2) {
                    e2.getMessage();
                }
            }

        });
        name.setComparator(String::compareToIgnoreCase);

        TableColumn<Prodect, Double> Price = new TableColumn<>("السعر");
        Price.setPrefWidth(90);
        Price.setResizable(false);
        Price.setCellValueFactory(new PropertyValueFactory<Prodect, Double>("price"));
        Price.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        Price.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Prodect, Double>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Prodect, Double> arg0) {

                arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).setPrice(arg0.getNewValue());
                String id = arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getID();
                double Weight = arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getPrice();

                try {

                    Connection con = db.getConnection().connectDB();
                    String sql = "UPDATE prodect set price ='" + Weight + "'  WHERE ID='" + id + "'";
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(sql);
                    con.close();
                    Pro.refresh();

                } catch (Exception e2) {
                    e2.getMessage();
                }

            }

        });
        Price.setComparator(Double::compareTo);

        TableColumn<Prodect, Double> Purchase = new TableColumn<>("الشراء");
        Purchase.setPrefWidth(90);
        Purchase.setResizable(false);
        Purchase.setCellValueFactory(new PropertyValueFactory<Prodect, Double>("Purchase"));
        Purchase.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        Purchase.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Prodect, Double>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Prodect, Double> arg0) {

                arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).setPurchase(arg0.getNewValue());
                String id = arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getID();
                double Weight = arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getPurchase();

                try {

                    Connection con = db.getConnection().connectDB();
                    String sql = "UPDATE prodect set Purchase ='" + Weight + "'  WHERE ID='" + id + "'";
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(sql);
                    con.close();
                    Pro.refresh();

                } catch (Exception e2) {
                    e2.getMessage();
                }

            }

        });
        Purchase.setComparator(Double::compareTo);


        TableColumn<Prodect, String> id = new TableColumn<>("الكود");
        id.setPrefWidth(90);
        id.setResizable(false);
        id.setCellValueFactory(new PropertyValueFactory<Prodect, String>("ID"));
        id.setCellFactory(TextFieldTableCell.forTableColumn());
        id.setEditable(false);
        id.setComparator(String::compareToIgnoreCase);


        TableColumn<Prodect, Integer> num = new TableColumn<>("النسخ");
        num.setPrefWidth(90);
        num.setResizable(false);
        num.setCellValueFactory(new PropertyValueFactory<Prodect, Integer>("numCopy"));
        num.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        num.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Prodect, Integer>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Prodect, Integer> arg0) {

                arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).setNumCopy(arg0.getNewValue());
                String id = arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getID();
                int Weight = arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getNumCopy();

                try {

                    Connection con = db.getConnection().connectDB();
                    String sql = "UPDATE prodect set NumCopy ='" + Weight + "'  WHERE ID='" + id + "'";
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(sql);
                    con.close();
                    Pro.refresh();

                } catch (Exception e2) {
                    e2.getMessage();
                }

            }

        });
        num.setComparator(Integer::compareTo);



        TableColumn<Prodect, Double> Proft = new TableColumn<>("المربح");
        Proft.setPrefWidth(90);
        Proft.setResizable(false);
        Proft.setCellValueFactory(new PropertyValueFactory<Prodect, Double>("proft"));
        Proft.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        Proft.setComparator(Double::compareTo);

        Pro.getColumns().addAll( id, name, num, Price, Purchase, Proft);


        Pro.setPrefSize(542, 300);

        FilteredList<Prodect> FliterProdect = new FilteredList<>(ProdectList, b -> true);
        FliterProdectex.textProperty().addListener((observable, oldValue, newValue) -> {
            FliterProdect.setPredicate(Prodect -> {

                if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (Prodect.getID().toLowerCase().indexOf(lowerCaseFilter) > -1) {
                    return true;
                } else if (Prodect.getName().toLowerCase().indexOf(lowerCaseFilter) > -1) {
                    return true;
                } else if (String.valueOf(Prodect.getPrice()).indexOf(lowerCaseFilter) > -1) {
                    return true;
                } else if (String.valueOf(Prodect.getPurchase()).indexOf(lowerCaseFilter) > -1) {
                    return true;
                } else
                    return false;

            });

        });

        SortedList<Prodect> sortdata = new SortedList<>(FliterProdect);
        sortdata.comparatorProperty().bind(Pro.comparatorProperty());
        Pro.setItems(sortdata);


        Prodect1.getChildren().addAll(mah1, BProdect, BCProdect, Pro, FliterProdectex, lebpp, DProdect);

        ItemesPage = new Scene(Prodect1, 900, 563);
        ItemesPage.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//==============================================  Customer Page ==========================================================================================================

        Image mh2 = new Image("mainPageEx.jpg");
        ImageView mah2 = new ImageView(mh2);


       Pane customer = new Pane();

        Button BCustomer = new Button("رجوع");
        BCustomer.setPrefHeight(60);
        BCustomer.setPrefWidth(90);
        BCustomer.setContentDisplay(ContentDisplay.TOP);
        BCustomer.setId("button");
        BCustomer.setLayoutX(179);
        BCustomer.setLayoutY(422);
        BCustomer.setOnAction(e -> {
            stage.setScene(SaleItemsPage);
            stage.setTitle("الصفحة الرئيسية");

        });

        TextField FliterProdectex1 = new TextField();
        FliterProdectex1.setLayoutX(376);
        FliterProdectex1.setLayoutY(50);


        Label lebpp1 = new Label();
        lebpp1.setText("Search Box :");
        lebpp1.setPrefHeight(30);
        lebpp1.setPrefWidth(130);
        lebpp1.setFont(javafx.scene.text.Font.font("Oranienbaum", 15));
        lebpp1.setTextFill(Color.WHITE);
        lebpp1.setLayoutX(217);
        lebpp1.setLayoutY(50);




        Button AddCustomer = new Button("اضافة");
        AddCustomer.setPrefHeight(60);
        AddCustomer.setPrefWidth(90);
        AddCustomer.setContentDisplay(ContentDisplay.TOP);
        AddCustomer.setId("button");
        AddCustomer.setLayoutX(414);
        AddCustomer.setLayoutY(422);
        AddCustomer.setOnAction(e -> {


            Image mh5 = new Image("mainPageEx.jpg");
            ImageView mah5 = new ImageView(mh5);


           Pane AddCustomerPa = new Pane();

            Label cancleb = new Label();
            cancleb.setText("الاسم :");
            cancleb.setLayoutX(342);
            cancleb.setLayoutY(53);
            cancleb.setTextFill(Color.WHITE);

            Label cancleb2 = new Label();
            cancleb2.setText("الدين :");
            cancleb2.setLayoutX(342);
            cancleb2.setLayoutY(110);
            cancleb2.setTextFill(Color.WHITE);

            Label cancleb3 = new Label();
            cancleb3.setText("رقم الهاتف :");
            cancleb3.setLayoutX(342);
            cancleb3.setLayoutY(167);
            cancleb3.setTextFill(Color.WHITE);

            Label cancleb4 = new Label();
            cancleb4.setText("التاريخ :");
            cancleb4.setLayoutX(342);
            cancleb4.setLayoutY(224);
            cancleb4.setTextFill(Color.WHITE);

            Label cancleb5 = new Label();
            cancleb5.setText("نوع العميل :");
            cancleb5.setLayoutX(342);
            cancleb5.setLayoutY(281);
            cancleb5.setTextFill(Color.WHITE);

            ToggleGroup TGg = new ToggleGroup();
            RadioButton R3 = new RadioButton("Normal");
            R3.setLayoutX(84);
            R3.setLayoutY(281);

            R3.setTextFill(Color.GREEN);

            RadioButton R4 = new RadioButton("Sellers");
            R4.setLayoutX(163);
            R4.setLayoutY(281);
            R4.setTextFill(Color.RED);

            R3.setToggleGroup(TGg);
            R4.setToggleGroup(TGg);

            DatePicker date1 = new DatePicker();
            date1.setPrefHeight(25);
            date1.setPrefWidth(150);
            date1.setLayoutX(84);
            date1.setLayoutY(229);
            date1.setEditable(false);

            TextField labs = new TextField();
            labs.setPrefHeight(113);
            labs.setPrefWidth(227);

            LocalDate now1 = LocalDate.now();

            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {

                    LocalDate i = date1.getValue();

                    labs.setText("" + i);
                }
            };

            date1.setShowWeekNumbers(true);
            date1.setOnAction(event);

            TextField tex4 = new TextField();
            tex4.setLayoutX(84);
            tex4.setLayoutY(49);

            TextField tex4e = new TextField();
            tex4e.setLayoutX(84);
            tex4e.setLayoutY(106);

            TextField tex5e = new TextField();
            tex5e.setLayoutX(84);
            tex5e.setLayoutY(163);



            Button AddCustomerBut = new Button("اضافة");
            AddCustomerBut.setPrefHeight(60);
            AddCustomerBut.setPrefWidth(90);
            AddCustomerBut.setLayoutX(218);
            AddCustomerBut.setLayoutY(331);
            AddCustomerBut.setTextFill(Color.BLACK);
            AddCustomerBut.setContentDisplay(ContentDisplay.TOP);
            AddCustomerBut.setId("button");
            AddCustomerBut.setOnAction(m -> {
                if (tex4.getText().isEmpty() || tex4e.getText().isEmpty() || tex5e.getText().isEmpty()
                        || labs.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.NONE, "يجب عليك ملء حقول النص", ButtonType.OK);
                    if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                    }

                    return;

                } else

                    try {
                        RadioButton sel = (RadioButton) TGg.getSelectedToggle();
                        String val = sel.getText();
                        Connection con = db.getConnection().connectDB();

                        String sql = "insert into Customer (name,debt,PhoneNumber,Customertype,DateDebt) values('"
                                + tex4.getText() + "','" + tex4e.getText() + "','" + tex5e.getText() + "','" + val + "','"
                                + labs.getText() + "')";

                        CustomerList.add(new Customer(tex4.getText(), Double.parseDouble(tex4e.getText()), tex5e.getText(), val,
                                labs.getText()));
                        Statement stmt = con.createStatement();

                        tex4.clear();
                        tex4e.clear();
                        tex5e.clear();
                        labs.clear();
                        date1.setValue(null);
                        R3.setSelected(false);
                        R4.setSelected(false);
                        stmt.executeUpdate(sql);

                        Alert alert = new Alert(Alert.AlertType.NONE, "تمت إضافة العميل", ButtonType.OK);
                        if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                        }
                        con.close();
                    } catch (Exception r) {
                        System.out.println(r);
                        Alert alert = new Alert(Alert.AlertType.NONE, "حاول مرة اخرى", ButtonType.OK);
                        if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                        }

                    }

            });





            AddCustomerPa.getChildren().addAll(mah5, cancleb, cancleb2, cancleb3, cancleb4, cancleb5, tex4, tex4e, tex5e,
                    date1, R3, R4, AddCustomerBut);
          Scene  AddCustomerPage = new Scene(AddCustomerPa, 500, 416);
            AddCustomerPage.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            Stage AddCustomerStage = new Stage();
            AddCustomerStage.setTitle("اضافة عميل");
            AddCustomerStage.setResizable(false);
            AddCustomerStage.setScene(AddCustomerPage);
            AddCustomerStage.show();



        });




        TableView<Customer> Cus = new TableView<>();
        Cus.setLayoutX(179);
        Cus.setLayoutY(101);
        Cus.setEditable(true);


        TableColumn<Customer, String> Name = new TableColumn<>("الاسم");
        Name.setPrefWidth(144);
        Name.setResizable(false);
        Name.setCellFactory(TextFieldTableCell.forTableColumn());
        Name.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        Name.setComparator(String::compareToIgnoreCase);
        Name.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Customer, String> arg0) {

                arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).setName(arg0.getNewValue());
                String id = arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getId();
                String name = arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getName();
                try {
                    Connection con = db.getConnection().connectDB();
                    String sql = "UPDATE customer set Name ='" + name + "'  WHERE id='" + id + "'";
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(sql);
                    con.close();
                } catch (Exception e2) {
                    e2.getMessage();
                }
            }

        });





        TableColumn<Customer, Double> debt = new TableColumn<>("الديون");
        debt.setPrefWidth(90);
        debt.setResizable(false);
        debt.setCellValueFactory(new PropertyValueFactory<Customer, Double>("debt"));
        debt.setComparator(Double::compareTo);


        TableColumn<Customer, String> phoneNumber = new TableColumn<>("رقم الهاتف");
        phoneNumber.setPrefWidth(90);
        phoneNumber.setResizable(false);
        phoneNumber.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumber.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
        phoneNumber.setComparator(String::compareToIgnoreCase);
        phoneNumber.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Customer, String> arg0) {

                arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).setPhoneNumber(arg0.getNewValue());
                String id = arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getId();
                String name = arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getPhoneNumber();
                try {
                    Connection con = db.getConnection().connectDB();
                    String sql = "UPDATE customer set PhoneNumber ='" + name + "'  WHERE id='" + id + "'";
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(sql);
                    con.close();
                } catch (Exception e2) {
                    e2.getMessage();
                }
            }

        });

        TableColumn<Customer, String> CustomerTybe = new TableColumn<>("نوع العميل");
        CustomerTybe.setPrefWidth(90);
        CustomerTybe.setResizable(false);
        ObservableList<String> options2 = FXCollections.observableArrayList("Normal", "Sellers");
        CustomerTybe.setCellFactory(ChoiceBoxTableCell.forTableColumn(options2));
        CustomerTybe.setCellValueFactory(new PropertyValueFactory<Customer, String>("CustomerType"));
        CustomerTybe.setComparator(String::compareToIgnoreCase);
        CustomerTybe.setOnEditCommit(event -> {
            Customer customer1 = event.getRowValue();
            customer1.setCustomerType(event.getNewValue());
            String ids = event.getTableView().getItems().get(event.getTablePosition().getRow()).getId();
            String names= event.getTableView().getItems().get(event.getTablePosition().getRow()).getCustomerType();

            try {
                Connection con = db.getConnection().connectDB();
                String sql = "UPDATE customer set Customertype ='" + names + "'  WHERE id='" + ids + "'";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(sql);
                con.close();
            } catch (Exception e2) {
                e2.getMessage();
            }

//            @Override
//            public void handle(TableColumn.CellEditEvent<Customer, String> arg0) {
//
//                arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).setCustomerType(arg0.getNewValue());
//                String id = arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getId();
//                String name = arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getCustomerType();
//                try {
//                    Connection con = db.getConnection().connectDB();
//                    String sql = "UPDATE customer set Customertype ='" + name + "'  WHERE id='" + id + "'";
//                    Statement stmt = con.createStatement();
//                    stmt.executeUpdate(sql);
//                    con.close();
//                } catch (Exception e2) {
//                    e2.getMessage();
//                }
//            }

        });




        Cus.setRowFactory(row -> new TableRow<Customer>() {
            @Override
            public void updateItem(Customer item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else {
                    if (item.getCustomerType().equals("Normal")) {
                        for (int i = 0; i < getChildren().size(); i++) {
                            ((Labeled) getChildren().get(i)).setTextFill(Color.GREEN);

                        }
                    } else {
                        for (int i = 0; i < getChildren().size(); i++) {
                            ((Labeled) getChildren().get(i)).setTextFill(Color.RED);
                        }
                    }
                }

            }

        });

        TableColumn<Customer, String> Date = new TableColumn<>("التاريخ");
        Date.setPrefWidth(144);
        Date.setCellFactory(TextFieldTableCell.forTableColumn());
        Date.setResizable(false);
        Date.setCellValueFactory(new PropertyValueFactory<Customer, String>("Date"));
        Date.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Customer, String> arg0) {

                arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).setDate(arg0.getNewValue());
                String id = arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getId();
                String name = arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getDate();
                try {
                    Connection con = db.getConnection().connectDB();
                    String sql = "UPDATE customer set DateDebt ='" + name + "'  WHERE id='" + id + "'";
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(sql);
                    con.close();
                } catch (Exception e2) {
                    e2.getMessage();
                }
            }

        });


        Cus.getColumns().addAll(Date,CustomerTybe,debt,phoneNumber,Name);
        Cus.setPrefSize(560, 300);

        Button BRCustomer = new Button("حذف");
        BRCustomer.setPrefHeight(60);
        BRCustomer.setPrefWidth(90);
        BRCustomer.setContentDisplay(ContentDisplay.TOP);
        BRCustomer.setId("button");
        BRCustomer.setLayoutX(649);
        BRCustomer.setLayoutY(422);
        BRCustomer.setOnAction(e -> {
            if (Cus.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.NONE, "يحب ان تختار حقل ", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }
                return;
            }



            Customer selectedItem = Cus.getSelectionModel().getSelectedItem();
            String selectedName = selectedItem.getId();
            try {

                Connection con = db.getConnection().connectDB();
                String sql = "DELETE FROM customer WHERE id='" + selectedName + "'";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(sql);
                con.close();
                Alert alert = new Alert(Alert.AlertType.NONE, "تم الحذف بنجاح ", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }



            } catch (Exception e2) {
                e2.getMessage();
            }
            CustomerList.remove(selectedItem);
            Cus.refresh();



        });

        Button AddDebt = new Button("دفع دين");
        AddDebt.setPrefHeight(60);
        AddDebt.setPrefWidth(90);
        AddDebt.setContentDisplay(ContentDisplay.TOP);
        AddDebt.setId("button");
        AddDebt.setLayoutX(294);
        AddDebt.setLayoutY(422);
        AddDebt.setOnAction(e->{

            if (Cus.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.NONE, "يحب ان تختار حقل ", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }
                return;
            }
            double dept = 0;
            double currentDept = 0;
            TextInputDialog textInput = new TextInputDialog();
            textInput.setTitle("دفع دين");
            textInput.setHeaderText("ادخل المبلغ :");
            textInput.setContentText("الدين :");
            Optional<String> result = textInput.showAndWait();
            TextField input = textInput.getEditor();
            if(input.getText() != null){
                dept = Double.parseDouble(input.getText());
            }

            Customer selectedItem = Cus.getSelectionModel().getSelectedItem();
            String selectedName = selectedItem.getId();
            try {

                Connection con = db.getConnection().connectDB();
                String sql = "select * FROM customer WHERE id='" + selectedName + "'";

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){
                    currentDept += rs.getDouble("debt");
                }
                stmt.executeUpdate(sql);
                con.close();

            } catch (Exception e2) {
                e2.getMessage();
            }

            if(currentDept<dept){
                Alert alert = new Alert(Alert.AlertType.NONE, "الدين الحالي اقل من الدين المضاف ", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }
                return;
            }else{
                double newDept = currentDept - dept;
                try {
                    Connection con = db.getConnection().connectDB();
                    String sql = "UPDATE customer set debt='"+newDept+"' WHERE id='" + selectedName + "'";
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(sql);
                    con.close();

                } catch (Exception e2) {
                    e2.getMessage();
                }

            }

            CustomerList.clear();
            try {
                readDataBaseCustomer();
            } catch (SQLException e1) {
                throw new RuntimeException(e1);
            } catch (ClassNotFoundException e1) {
                throw new RuntimeException(e1);
            }
            Cus.setItems(CustomerList);
            Cus.refresh();




        });



        Button payDebt = new Button("اضافة دين");
        payDebt.setPrefHeight(60);
        payDebt.setPrefWidth(90);
        payDebt.setContentDisplay(ContentDisplay.TOP);
        payDebt.setId("button");
        payDebt.setLayoutX(534);
        payDebt.setLayoutY(422);
        payDebt.setOnAction(e->{

            if (Cus.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.NONE, "يحب ان تختار حقل ", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }
                return;
            }
            double dept = 0;
            double currentDept = 0;
            TextInputDialog textInput = new TextInputDialog();
            textInput.setTitle("اضافة دين");
            textInput.setHeaderText("ادخل المبلغ :");
            textInput.setContentText("الدين :");
            Optional<String> result = textInput.showAndWait();
            TextField input = textInput.getEditor();
            if(input.getText() != null){
                dept = Double.parseDouble(input.getText());
            }

            Customer selectedItem = Cus.getSelectionModel().getSelectedItem();
            String selectedName = selectedItem.getId();
            try {

                Connection con = db.getConnection().connectDB();
                String sql = "select * FROM customer WHERE id='" + selectedName + "'";

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){
                    currentDept += rs.getDouble("debt");
                }
                stmt.executeUpdate(sql);
                con.close();

            } catch (Exception e2) {
                e2.getMessage();
            }


                double newDept = currentDept + dept;
                try {
                    Connection con = db.getConnection().connectDB();
                    String sql = "UPDATE customer set debt='"+newDept+"' WHERE id='" + selectedName + "'";
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(sql);
                    con.close();

                } catch (Exception e2) {
                    e2.getMessage();
                }



            CustomerList.clear();
            try {
                readDataBaseCustomer();
            } catch (SQLException e1) {
                throw new RuntimeException(e1);
            } catch (ClassNotFoundException e1) {
                throw new RuntimeException(e1);
            }
            Cus.setItems(CustomerList);
            Cus.refresh();






        });

        FilteredList<Customer> FliterCus = new FilteredList<>(CustomerList , b -> true);
        FliterProdectex1.textProperty().addListener((observable, oldValue, newValue) -> {
            FliterCus.setPredicate(m -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (m.getDate().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (m.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (m.getPhoneNumber().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(m.getDebt()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (m.getCustomerType().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else
                    return false;

            });

        });

        SortedList<Customer> sortdata1 = new SortedList<>(FliterCus);
        Cus.setItems(sortdata1);
        sortdata1.comparatorProperty().bind(Cus.comparatorProperty());



        customer.getChildren().addAll(mah2, Cus,BCustomer, BRCustomer, AddCustomer, AddDebt, payDebt,FliterProdectex1, lebpp1);

        CustomerPage = new Scene(customer, 900, 563);
        CustomerPage.getStylesheets().add(getClass().getResource("application.css").toExternalForm());



//========================================================================================================================================================
        Image Imeg = new Image("mainPageEx.jpg");
        ImageView pic = new ImageView(Imeg);
        pic.setFitHeight(563);
        pic.setFitWidth(900);

        Pane root = new Pane();
        root.getChildren().add(pic);


        Label left = new Label();
        left.setGraphic(new ImageView("Larrow.png"));
        left.setLayoutX(284);
        left.setLayoutY(80);

        Label right = new Label();

        right.setGraphic(new ImageView("Rarrow.png"));
        right.setLayoutX(352);
        right.setLayoutY(80);
        root.getChildren().addAll(left, right);


        TableView<Prodect> table = new TableView<Prodect>();
        table.setPrefWidth(442);
        table.setPrefHeight(286);
        table.setEditable(true);
        table.setLayoutX(243);
        table.setLayoutY(139);
        root.getChildren().add(table);

        Button logoutButton = new Button("LOGOUT", new ImageView("logout.png"));
        logoutButton.setPrefSize(150, 33);
        logoutButton.setLayoutX(25);
        logoutButton.setLayoutY(31);
        logoutButton.setContentDisplay(ContentDisplay.LEFT);
        logoutButton.setId("button");
        logoutButton.setOnAction(e -> {
            stage.setScene(LoginPAGE);
            stage.setTitle("Login Page");
            usernameAlert = "";
            passwordAlert = "";
        });


        TableColumn Clumone = new TableColumn<Prodect, String>("الاسم");
        Clumone.setMinWidth(10);
        Clumone.setMaxWidth(5000);
        Clumone.setPrefWidth(110);
        Clumone.setCellValueFactory(new PropertyValueFactory<Prodect, String>("name"));

        TableColumn Clumtow = new TableColumn<Prodect, Double>("السعر");
        Clumtow.setMinWidth(10);
        Clumtow.setMaxWidth(5000);
        Clumtow.setPrefWidth(110);
        Clumtow.setCellValueFactory(new PropertyValueFactory<Prodect, Double>("price"));

        TableColumn Clumthree = new TableColumn("الكمية");
        Clumthree.setMinWidth(10);
        Clumthree.setMaxWidth(5000);
        Clumthree.setPrefWidth(110);
        Clumthree.setCellValueFactory(new PropertyValueFactory<Prodect, Integer>("quantity"));
        Clumthree.setEditable(true);

        TableColumn ClumFure = new TableColumn("المجموع");
        ClumFure.setMinWidth(10);
        ClumFure.setMaxWidth(5000);
        ClumFure.setPrefWidth(110);
        ClumFure.setCellValueFactory(new PropertyValueFactory<Prodect, Double>("total"));
        ClumFure.setEditable(true);

        Calendar calendar1 = Calendar.getInstance();
        Date dateae1 = calendar1.getTime();
        String day1 = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(dateae1.getTime());

        DatePicker date1 = new DatePicker();
        date1.setPrefHeight(30);
        date1.setPrefWidth(150);
        date1.setEditable(false);

        TextField labs = new TextField();
        labs.setPrefHeight(113);
        labs.setPrefWidth(227);



        LocalDate now1 = LocalDate.now();

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                LocalDate i = date1.getValue();

                labs.setText("" + i);
            }
        };




        Label date = new Label();
        date.setPrefHeight(25);
        date.setPrefWidth(300);
        date.setText("" + now1 + "\t" + day1);
        date.setTextFill(Color.BLACK);

      date.setLayoutX(399);
      date.setLayoutY(14);
      root.getChildren().add(date);


        TextField vv = new TextField();
        vv.setPrefHeight(25);
        vv.setPrefWidth(195);

        vv.setLayoutX(464);
        vv.setLayoutY(76);
        root.getChildren().add(vv);


        TextField texr = new TextField();
        texr.setLayoutX(41);
        texr.setLayoutY(514);
        texr.setEditable(false);
        root.getChildren().add(texr);

        Clumtow.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        Clumtow.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Prodect, Double>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Prodect, Double> t) {
                ((Prodect) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPrice(t.getNewValue());

                double sumAmout = 0;
                for (Prodect o : ItemsData) {

                    sumAmout = o.getTotal() + sumAmout;
                }

                texr.setText(NumberFormat.getCurrencyInstance().format(sumAmout));
                table.refresh();


            }

        });

        Clumthree.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        Clumthree.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Prodect, Integer>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Prodect, Integer> t) {
                ((Prodect) t.getTableView().getItems().get(t.getTablePosition().getRow())).setQuantity(t.getNewValue());

                double sumAmout = 0;
                for (Prodect o : ItemsData) {

                    sumAmout = o.getTotal() + sumAmout;
                }
                try {
                    Connection con = db.getConnection().connectDB();
                    String sql = "select * from prodect where ID = '" + t.getTableView().getItems().get(t.getTablePosition().getRow()).getID() + "'";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        int numberofcopy = rs.getInt("NumCopy");
                        numberofcopy++;
                        numberofcopy -= t.getNewValue();
                        String sql2 = "update prodect set NumCopy = " + numberofcopy + " where ID = '" + t.getTableView().getItems().get(t.getTablePosition().getRow()).getID() + "'";
                        stmt.executeUpdate(sql2);
                    }

                    con.close();

                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }



                texr.setText(NumberFormat.getCurrencyInstance().format(sumAmout));
                table.refresh();
            }

        });

        left.setOnMouseClicked(e -> {
            double sumAmout = 0;
            try {

                ItemsData.clear();
                table.refresh();
                b--;

                for (int i = 0; i < temp.get(b).size(); i++) {
                    ItemsData.add(new Prodect(temp.get(b).get(i).getID(), temp.get(b).get(i).getName(),
                            temp.get(b).get(i).getPrice(), temp.get(b).get(i).getTotal(),
                            temp.get(b).get(i).getQuantity(), temp.get(b).get(i).getProft(),
                            temp.get(b).get(i).getPurchase(), temp.get(b).get(i).getTotalproft()));

                    finalflag = 1;
                }
            } catch (IndexOutOfBoundsException e2) {
                e2.getMessage();
            }

            for (Prodect o : ItemsData) {

                sumAmout = o.getTotal() + sumAmout;
            }

            texr.setText(NumberFormat.getCurrencyInstance().format(sumAmout));

        });

        right.setOnMouseClicked(e -> {
            double sumAmout = 0;
            try {

                ItemsData.clear();
                table.refresh();
                b++;
                for (int i = 0; i < temp.get(b).size(); i++) {
                    ItemsData.add(new Prodect(temp.get(b).get(i).getID(), temp.get(b).get(i).getName(),
                            temp.get(b).get(i).getPrice(), temp.get(b).get(i).getTotal(),
                            temp.get(b).get(i).getQuantity(), temp.get(b).get(i).getProft(),
                            temp.get(b).get(i).getPurchase(), temp.get(b).get(i).getTotalproft()));

                    finalflag = 1;
                }

                table.refresh();

                for (Prodect o : ItemsData) {

                    sumAmout = o.getTotal() + sumAmout;
                }
                texr.setText(NumberFormat.getCurrencyInstance().format(sumAmout));

            } catch (IndexOutOfBoundsException e2) {
                e2.getMessage();
            }

        });

        vv.setOnAction(e -> {


            try {

                Connection con = db.getConnection().connectDB();
                String sql = "SELECT ID ,Name , price , total , quantity , proft , Purchase , NumCopy , totalProft FROM Prodect where ID='"
                        + vv.getText() + "'";

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                double price = 0;
                String bame = null;
                int Quntit = 0;
                double proft = 0;
                double pra = 0;
                String ib = null;
                double sumAmout = 0;
                int flag = 0;
                int numcopy = 0;
                double total = 0;
                int flag4 = 0;
                double totalproft = 0;

                while (rs.next()) {
                    if (flag == 0) {
                        price = rs.getDouble("price");
                        bame = rs.getString("name");
                        total = rs.getDouble("total");
                        Quntit = rs.getInt("quantity");
                        proft = rs.getDouble("proft");
                        pra = rs.getDouble("Purchase");
                        ib = rs.getString("ID");
                        numcopy = rs.getInt("NumCopy");
                        totalproft = rs.getDouble("totalProft");

                        if (numcopy == 0) {
                            Alert alert = new Alert(Alert.AlertType.NONE, "Out Of Stock", ButtonType.OK);
                            if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                            }

                            return;
                        }

                        for (int j = 0; j < ItemsData.size(); j++) {
                            if (ItemsData.get(j).getID().equals(vv.getText())) {

                                int n = 0;
                                n = ItemsData.get(j).getQuantity();
                                n++;
                                ItemsData.get(j).setQuantity(n);

                                table.refresh();
                                flag = 1;
                                flag4 = 1;
                                break;
                            }
                        }
                        if (flag4 == 0) {
                            ItemsData.add(
                                    new Prodect(ib, bame, price, ++Quntit, total, proft, pra, numcopy, totalproft));

                            con.close();
                            break;
                        }

                    }
                }
                for (Prodect o : ItemsData) {

                    sumAmout = o.getTotal() + sumAmout;

                }
                texr.setText(NumberFormat.getCurrencyInstance().format(sumAmout));

                // table.refresh();

                if (ib == null) {
                    Connection con1 = db.getConnection().connectDB();
                    String sql1 = "SELECT ID ,Name , price , total , quantity , proft , Purchase , NumCopy ,totalProft  FROM Prodect where Name='"
                            + vv.getText() + "'";

                    Statement stmt1 = con1.createStatement();
                    ResultSet rs1 = stmt1.executeQuery(sql1);

                    double price1 = 0;
                    String bame1 = null;
                    int Quntit1 = 0;
                    double proft1 = 0;
                    double pra1 = 0;
                    String ib1 = null;
                    double sumAmout1 = 0;
                    int flag1 = 0;
                    int numcopy1 = 0;
                    int flag5 = 0;
                    double totalpro = 0;

                    while (rs1.next()) {
                        if (flag1 == 0) {
                            price1 = rs1.getDouble("price");
                            bame1 = rs1.getString("name");
                            double total1 = rs1.getDouble("total");
                            Quntit1 = rs1.getInt("quantity");
                            proft1 = rs1.getDouble("proft");
                            pra1 = rs1.getDouble("Purchase");
                            ib1 = rs1.getString("ID");
                            numcopy1 = rs1.getInt("NumCopy");
                            totalpro = rs1.getDouble("totalProft");

                            if (numcopy1 == 0) {
                                Alert alert = new Alert(Alert.AlertType.NONE, "Out Of Stock", ButtonType.OK);
                                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                                }

                                return;
                            }

                            for (int j = 0; j < ItemsData.size(); j++) {

                                if (ItemsData.get(j).getName().equalsIgnoreCase(vv.getText())) {
                                    int n = 0;
                                    n = ItemsData.get(j).getQuantity();
                                    n++;
                                    ItemsData.get(j).setQuantity(n);

                                    table.refresh();
                                    flag1 = 1;
                                    flag5 = 1;
                                    break;

                                }
                            }

                            if (flag5 == 0) {
                                ItemsData.add(new Prodect(ib1, bame1, price1, ++Quntit1, total1, proft1, pra1, numcopy1,
                                        totalpro));
                                con.close();
                                break;
                            }
                        }
                    }

                    for (Prodect o : ItemsData) {

                        sumAmout1 = o.getTotal() + sumAmout1;
                    }

                    texr.setText(NumberFormat.getCurrencyInstance().format(sumAmout1));

                }

            } catch (Exception m) {
                System.out.println(e);
            }

            int rus = 0;

            try {
                for (int i = 0; i < ItemsData.size(); i++) {
                    int r1 = ItemsData.get(i).getNumCopy();
                    int r2 = ItemsData.get(i).getQuantity();
                    rus = r1 - r2;
                    Connection con = db.getConnection().connectDB();
                    String sql = "Update Prodect set NumCopy='" + rus + "' where ID='" + ItemsData.get(i).getID()
                            + "' ";

                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(sql);

                    con.close();

                }

            } catch (Exception m) {
                System.out.println(e);
            }
            vv.setText("");
        });

        table.getColumns().addAll(ClumFure, Clumthree, Clumtow, Clumone);
        table.setItems(ItemsData);


        VBox V1 = new VBox();
        V1.setPrefWidth(149);
        V1.setPrefHeight(379);
        V1.setLayoutX(731);
        V1.setLayoutY(62);


        Button b1 = new Button("المنتجات");
        b1.setPrefWidth(133);
        b1.setPrefHeight(25);
        b1.setTextFill(Color.BLACK);
        b1.setOnAction(E -> {
            stage.setScene(ItemesPage);
            stage.setTitle("المنتجات");

            ProdectList.clear();
            try {
                readDataBaseProdect();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            Pro.setItems(ProdectList);
            Pro.refresh();

        });
        Button b2 = new Button("العملاء");
        b2.setPrefWidth(133);
        b2.setPrefHeight(25);
        b2.setTextFill(Color.BLACK);
        b2.setOnAction(e -> {
            stage.setScene(CustomerPage);
            stage.setTitle("العملاء");
            CustomerList.clear();
            try {
              readDataBaseCustomer();
            } catch (SQLException e1) {
                throw new RuntimeException(e1);
            } catch (ClassNotFoundException e1) {
                throw new RuntimeException(e1);
            }
            Cus.setItems(CustomerList);
            Cus.refresh();

        });

        Button b3 = new Button("+");
        b3.setPrefWidth(133);
        b3.setPrefHeight(25);
        b3.setTextFill(Color.BLACK);
        b3.setOnAction(e -> {
            if (finalflag == 1) {
                Alert alert = new Alert(Alert.AlertType.NONE, "لا يمكنك التعديل" + "\n" + "اضغط على Del", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }

                return;
            } else{



            Prodect p = table.getSelectionModel().getSelectedItem();

             int count = 0;
            // get the number of copy from database
            try {
                Connection con = db.getConnection().connectDB();
                String sql = "SELECT NumCopy FROM Prodect where ID='" + p.getID() + "'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    count = rs.getInt("NumCopy");
                }
                con.close();

            }catch (Exception m) {
                System.out.println(e);
            }
            if (count == 0) {
                Alert alert = new Alert(Alert.AlertType.NONE, "إنتهى من المخزن", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }

                return;
            }

            int n = p.getQuantity();
            n++;
            p.setQuantity(n);
            table.getItems().add(table.getSelectionModel().getSelectedIndex(), p);
            table.getItems().remove(table.getSelectionModel().getSelectedIndex() - 1);
            table.refresh();
            double sumAmout1 = 0;
            for (Prodect o : ItemsData) {

                sumAmout1 = o.getTotal() + sumAmout1;
            }

            texr.setText(NumberFormat.getCurrencyInstance().format(sumAmout1));

            try {
                int rus = 0;

                int r1 = p.getNumCopy();
                int r2 = p.getQuantity();
                rus = r1 - r2;

                Connection con = db.getConnection().connectDB();
                String sql = "Update Prodect set NumCopy='" + rus + "' where ID='" + p.getID() + "' ";

                Statement stmt = con.createStatement();
                stmt.executeUpdate(sql);
                con.close();

            } catch (Exception m) {
                System.out.println(m);
            }
            }
        });


        Button b4 = new Button("-");
        b4.setPrefWidth(133);
        b4.setPrefHeight(25);
        b4.setTextFill(Color.BLACK);
        b4.setOnAction(e -> {
            if (finalflag == 1) {
                Alert alert = new Alert(Alert.AlertType.NONE, "لا يمكنك التعديل" + "\n" + "اضغط على Del", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }

                return;
            } else {


                Prodect p1 = table.getSelectionModel().getSelectedItem();
                int n1 = p1.getQuantity();
                n1--;
                p1.setQuantity(n1);
                table.getItems().add(table.getSelectionModel().getSelectedIndex(), p1);
                table.getItems().remove(table.getSelectionModel().getSelectedIndex() - 1);
                table.refresh();
                double sumAmout2 = 0;
                for (Prodect o : ItemsData) {

                    sumAmout2 = o.getTotal() + sumAmout2;
                }

                texr.setText(NumberFormat.getCurrencyInstance().format(sumAmout2));
                int numcopy = 0;

                try {

                    Connection con = db.getConnection().connectDB();
                    String sql = "Select NumCopy from Prodect where ID='" + p1.getID() + "'";

                    Statement stmt = con.createStatement();
                    ResultSet rs1 = stmt.executeQuery(sql);
                    while (rs1.next()) {

                        numcopy = rs1.getInt("NumCopy");
                    }

                    stmt.executeUpdate(sql);

                    // readDataBaseProdect();
                    con.close();

                } catch (Exception m) {
                    System.out.println(m);
                }

                try {
                    int rus = 0;

                    rus = numcopy + 1;
                    Connection con = db.getConnection().connectDB();
                    String sql = "Update Prodect set NumCopy='" + rus + "' where ID='" + p1.getID() + "' ";

                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(sql);

                    // readDataBaseProdect();
                    con.close();

                } catch (Exception m) {
                    System.out.println(m);
                }
            }
        });



        Button b7 = new Button("مسح الكل");
        b7.setPrefWidth(133);
        b7.setPrefHeight(25);
        b7.setTextFill(Color.BLACK);
        b7.setOnAction(e -> {

            if (finalflag == 1) {
                Alert alert = new Alert(Alert.AlertType.NONE, "لا يمكنك مسح الكل " + "\n" + "اضغط على Del", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }

                return;
            } else




            try {

                for (int i = 0; i < ItemsData.size(); i++) {

                    int numcopies = 0;
                    Connection con = db.getConnection().connectDB();
                    String sql = "Select NumCopy from Prodect where ID='" + ItemsData.get(i).getID() + "'";

                    Statement stmt = con.createStatement();
                    ResultSet rs1 = stmt.executeQuery(sql);

                    while (rs1.next()) {

                        numcopies = rs1.getInt("NumCopy");
                    }
                    System.out.println("Coppes : " + numcopies);
                    stmt.executeQuery(sql);
                    con.close();

                    int r = ItemsData.get(i).getQuantity();
                    int res = r + numcopies;

                    ItemsData.get(i).setNumCopy(res);
                    Connection con1 = db.getConnection().connectDB();
                    String sql1 = "Update Prodect set NumCopy='" + res + "' where ID='"
                            + ItemsData.get(i).getID() + "' ";

                    Statement stmt1 = con1.createStatement();
                    stmt1.executeUpdate(sql1);
                    con1.close();
                }

            } catch (Exception m) {
                System.out.println(m);
            }
            ItemsData.clear();
            table.refresh();

        });

        Button b8 = new Button("حذف منتج");
        b8.setPrefWidth(133);
        b8.setPrefHeight(25);
        b8.setTextFill(Color.BLACK);
        b8.setOnAction(e -> {

            if (finalflag == 1) {
                Alert alert = new Alert(Alert.AlertType.NONE, "لا يمكنك حذف المنتح " + "\n" + "اضغط على Del", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }

                return;
            } else{



            Prodect p2 = table.getSelectionModel().getSelectedItem();

            int n2 = p2.getQuantity();
            table.refresh();
            double sumAmout3 = 0;
            for (Prodect o : ItemsData) {

                sumAmout3 = o.getTotal() + sumAmout3;
            }
            texr.setText(NumberFormat.getCurrencyInstance().format(sumAmout3));
            int numcopy12 = 0;

            try {

                Connection con = db.getConnection().connectDB();
                String sql = "Select NumCopy from Prodect where ID='" + p2.getID() + "'";

                Statement stmt = con.createStatement();
                ResultSet rs1 = stmt.executeQuery(sql);
                while (rs1.next()) {

                    numcopy12 = rs1.getInt("NumCopy");
                }

                stmt.executeQuery(sql);
                con.close();
                // readDataBaseProdect();

            } catch (Exception m) {
                System.out.println(m);
            }

            try {
                int rus = 0;

                rus = numcopy12 + n2;
                Connection con = db.getConnection().connectDB();
                String sql = "Update Prodect set NumCopy='" + rus + "' where ID='" + p2.getID() + "' ";

                Statement stmt = con.createStatement();
                stmt.executeUpdate(sql);

                // readDataBaseProdect();
                con.close();
            } catch (Exception m) {
                System.out.println(m);
            }
            table.getItems().remove(table.getSelectionModel().getSelectedIndex());

            }
        });


        Button b9 = new Button("اضافة حقل فارغ");
        b9.setPrefWidth(133);
        b9.setPrefHeight(25);
        b9.setTextFill(Color.BLACK);
        b9.setOnAction(e -> {
            if (finalflag == 1) {
                Alert alert = new Alert(Alert.AlertType.NONE, "لا يمكنك اضافة حقل " + "\n" + "اضغط على Del", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }

                return;
            }
            else

            ItemsData.add(new Prodect( "1","متفرقات", 0, 0, 0,1));
        });


        Button b10 = new Button("معلومات المنتج");
        b10.setPrefWidth(133);
        b10.setPrefHeight(25);
        b10.setTextFill(Color.BLACK);
        b10.setOnAction(e -> {
            Prodect p3 = table.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(
                    Alert.AlertType.NONE, "The Purchase : " + p3.getPurchase() + "\n" + "Name : " + p3.getName()
                    + "\n" + "Barcode : " + p3.getID() + "\n" + "The Pricce : " + p3.getPrice(),
                    ButtonType.OK);
            if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            }

        });

        Button b11 = new Button("طباعة فاتورة");
        b11.setPrefWidth(133);
        b11.setPrefHeight(25);
        b11.setTextFill(Color.BLACK);
        b11.setOnAction(e -> {
            bHeight = Double.valueOf(ItemsData.size());


            PrinterJob pj = PrinterJob.getPrinterJob();
            pj.setPrintable(new BillPrintable(), getPageFormat(pj));

             boolean dor = pj.printDialog();
              if (dor) {
            try {
                pj.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        }
        });

        Button b12 = new Button("المبيعات اليومية");
        b12.setPrefWidth(133);
        b12.setPrefHeight(25);
        b12.setTextFill(Color.BLACK);



        Button b13 = new Button("المبيعات الشهرية");
        b13.setPrefWidth(133);
        b13.setPrefHeight(25);
        b13.setTextFill(Color.BLACK);



        Button b14 = new Button("المبيعات السنوية");
        b14.setPrefWidth(133);
        b14.setPrefHeight(25);
        b14.setTextFill(Color.BLACK);


        V1.getChildren().addAll(b1, b2, b3, b4 , b7 , b8 , b9 , b10 , b11 , b12 , b13 , b14);
        V1.setMargin(b1, new Insets(0, 0, 6.5, 0));
        V1.setMargin(b2, new Insets(0, 0, 6.5, 0));
        V1.setMargin(b3, new Insets(0, 0, 6.5, 0));
        V1.setMargin(b4, new Insets(0, 0, 6.5, 0));
        V1.setMargin(b7, new Insets(0, 0, 6.5, 0));
        V1.setMargin(b8, new Insets(0, 0, 6.5, 0));
        V1.setMargin(b9, new Insets(0, 0, 6.5, 0));
        V1.setMargin(b10, new Insets(0, 0, 6.5, 0));
        V1.setMargin(b11, new Insets(0, 0, 6.5, 0));
        V1.setMargin(b12, new Insets(0, 0, 6.5, 0));
        V1.setMargin(b13, new Insets(0, 0, 6.5, 0));
        root.getChildren().add(V1);




        Button B1 = new Button("بيع");
        B1.setPrefWidth(98);
        B1.setPrefHeight(56);
        B1.setLayoutX(531);
        B1.setLayoutY(441);
        B1.setTextFill(Color.BLACK);
        B1.setOnAction(e -> {

            if (ItemsData.isEmpty() == true) {
                Alert alert = new Alert(Alert.AlertType.NONE, "الجدول فارغ", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }

                return;
            }
            if (finalflag == 1) {
                Alert alert = new Alert(Alert.AlertType.NONE, "لا يمكنك بيعه"+"\n"+"اضغط على Del", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }

                return;
            } else
                try {

                    finalflag = 0;

                    ArrayList<Prodect> tem = null;
                    tem = new ArrayList<>();
                    for (int i = 0; i < ItemsData.size(); i++) {

                        tem.add(new Prodect(ItemsData.get(i).getID(), ItemsData.get(i).getName(),
                                ItemsData.get(i).getPrice(), ItemsData.get(i).getTotal(),
                                ItemsData.get(i).getQuantity(), ItemsData.get(i).getProft(),
                                ItemsData.get(i).getPurchase(), ItemsData.get(i).getTotalProft()));

                        DaySalesList.add(new Prodect(ItemsData.get(i).getID(), ItemsData.get(i).getName(),
                                ItemsData.get(i).getPrice(), ItemsData.get(i).getTotal(),
                                ItemsData.get(i).getQuantity(), ItemsData.get(i).getProft(),
                                ItemsData.get(i).getPurchase(), ItemsData.get(i).getTotalProft()));

                        Connection con = db.getConnection().connectDB();
                        String sql = "insert into DaySales(ID,name,price,total,quantity,proft,Purchase,totalProft) values('"
                                + ItemsData.get(i).getID() + "','" + ItemsData.get(i).getName() + "','"
                                + ItemsData.get(i).getPrice() + "','" + ItemsData.get(i).getTotal() + "','"
                                + ItemsData.get(i).getQuantity() + "','" + ItemsData.get(i).getProft() + "' ,'"
                                + ItemsData.get(i).getPurchase() + "' , '" + ItemsData.get(i).getTotalProft() + "')";

                        Statement stmt = con.createStatement();
                        stmt.executeUpdate(sql);
                        con.close();
                    }

                    System.out.println("before " + v);
                    temp.add(v, tem);
                    v++;

                    System.out.println("After " + v);

                    ItemsData.clear();
                    texr.setText("");

                } catch (Exception m) {
                    System.out.println(m);
                }

        });


        Button B2 = new Button("ارجاع منتج");
        B2.setPrefWidth(98);
        B2.setPrefHeight(56);
        B2.setLayoutX(292);
        B2.setLayoutY(441);
        B2.setTextFill(Color.BLACK);
        B2.setOnAction(e -> {
            if (finalflag == 1) {
                Alert alert = new Alert(Alert.AlertType.NONE, "لا يمكنك ارجاعه" + "\n" + "اضغط على Del", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }

                return;
            } else

            try {

                for (int i = 0; i < ItemsData.size(); i++) {

                    int numcopies = 0;
                    Connection con = db.getConnection().connectDB();
                    String sql = "Select NumCopy from Prodect where ID='" + ItemsData.get(i).getID() + "'";

                    Statement stmt = con.createStatement();
                    ResultSet rs1 = stmt.executeQuery(sql);

                    while (rs1.next()) {

                        numcopies = rs1.getInt("NumCopy");
                    }
                    System.out.println("Coppes : " + numcopies);
                    stmt.executeQuery(sql);
                    con.close();
                    int r = ItemsData.get(i).getQuantity();
                    int res = r + numcopies;

                    ItemsData.get(i).setNumCopy(res);
                    Connection con1 = db.getConnection().connectDB();
                    String sql1 = "Update Prodect set NumCopy='" + res + "' where ID='" + ItemsData.get(i).getID()
                            + "' ";

                    Statement stmt1 = con1.createStatement();
                    stmt1.executeUpdate(sql1);
                    con1.close();
                }

            } catch (Exception m) {
                System.out.println(m);
            }

            try {
                for (int i = 0; i < ItemsData.size(); i++) {
                    DaySalesList.add(new Prodect(ItemsData.get(i).getID(), "Returning " + ItemsData.get(i).getName(),
                            ItemsData.get(i).getPrice(), ItemsData.get(i).getTotal(), ItemsData.get(i).getQuantity(),
                            ItemsData.get(i).getProft(), ItemsData.get(i).getPurchase(),
                            ItemsData.get(i).getTotalProft()));
                    double m = ItemsData.get(i).getPrice();
                    m = -m;
                    double b = ItemsData.get(i).getPurchase();
                    b = -b;

                    double r = ItemsData.get(i).getTotal();
                    r = -r;

                    double t = ItemsData.get(i).getPrice();
                    t = -t;

                    double y = ItemsData.get(i).getTotalProft();
                    y = -y;

                    Connection con = db.getConnection().connectDB();
                    String sql = "insert into DaySales(ID,name,price,total,quantity,proft,Purchase,totalProft) values('"
                            + ItemsData.get(i).getID() + "','" + ("Returning " + ItemsData.get(i).getName()) + "','" + t
                            + "','" + r + "','" + ItemsData.get(i).getQuantity() + "','" + (m) + "' ,'" + (b) + "' , '"
                            + (y) + "')";

                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(sql);
                    con.close();
                }

            } catch (Exception m) {
                System.out.println(e);
            }

            ItemsData.clear();

        });




        Label opp = new Label("المجموع الكلي :");
        opp.setPrefWidth(88);
        opp.setPrefHeight(17);
        opp.setLayoutX(219);
        opp.setLayoutY(518);
        root.getChildren().addAll(opp,B1,B2,logoutButton);





        SaleItemsPage = new Scene(root, 900, 563);
        SaleItemsPage.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        SaleItemsPage.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {
                switch (arg0.getCode()) {

                    // focus on the Text
                    case ALT:
                        vv.requestFocus();
                        break;
                    // increment the quantity
                    case F11:

                        if (finalflag == 1) {
                            Alert alert = new Alert(Alert.AlertType.NONE, "لا يمكنك التعديل" + "\n" + "اضغط على Del", ButtonType.OK);
                            if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                            }

                            return;
                        } else{



                            Prodect p = table.getSelectionModel().getSelectedItem();

                            int count = 0;
                            // get the number of copy from database
                            try {
                                Connection con = db.getConnection().connectDB();
                                String sql = "SELECT NumCopy FROM Prodect where ID='" + p.getID() + "'";
                                Statement stmt = con.createStatement();
                                ResultSet rs = stmt.executeQuery(sql);
                                while (rs.next()) {
                                    count = rs.getInt("NumCopy");
                                }
                                con.close();

                            }catch (Exception m) {
                                System.out.println(m);
                            }
                            if (count == 0) {
                                Alert alert = new Alert(Alert.AlertType.NONE, "إنتهى من المخزن", ButtonType.OK);
                                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                                }

                                return;
                            }

                            int n = p.getQuantity();
                            n++;
                            p.setQuantity(n);
                            table.getItems().add(table.getSelectionModel().getSelectedIndex(), p);
                            table.getItems().remove(table.getSelectionModel().getSelectedIndex() - 1);
                            table.refresh();
                            double sumAmout1 = 0;
                            for (Prodect o : ItemsData) {

                                sumAmout1 = o.getTotal() + sumAmout1;
                            }

                            texr.setText(NumberFormat.getCurrencyInstance().format(sumAmout1));

                            try {
                                int rus = 0;

                                int r1 = p.getNumCopy();
                                int r2 = p.getQuantity();
                                rus = r1 - r2;

                                Connection con = db.getConnection().connectDB();
                                String sql = "Update Prodect set NumCopy='" + rus + "' where ID='" + p.getID() + "' ";

                                Statement stmt = con.createStatement();
                                stmt.executeUpdate(sql);
                                con.close();

                            } catch (Exception m) {
                                System.out.println(m);
                            }
                        }
                        break;

                    // decrement the quantity
                    case F10:

                        if (finalflag == 1) {
                            Alert alert = new Alert(Alert.AlertType.NONE, "لا يمكنك التعديل" + "\n" + "اضغط على Del", ButtonType.OK);
                            if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                            }

                            return;
                        } else {


                            Prodect p1 = table.getSelectionModel().getSelectedItem();
                            int n1 = p1.getQuantity();
                            n1--;
                            p1.setQuantity(n1);
                            table.getItems().add(table.getSelectionModel().getSelectedIndex(), p1);
                            table.getItems().remove(table.getSelectionModel().getSelectedIndex() - 1);
                            table.refresh();
                            double sumAmout2 = 0;
                            for (Prodect o : ItemsData) {

                                sumAmout2 = o.getTotal() + sumAmout2;
                            }

                            texr.setText(NumberFormat.getCurrencyInstance().format(sumAmout2));
                            int numcopy = 0;

                            try {

                                Connection con = db.getConnection().connectDB();
                                String sql = "Select NumCopy from Prodect where ID='" + p1.getID() + "'";

                                Statement stmt = con.createStatement();
                                ResultSet rs1 = stmt.executeQuery(sql);
                                while (rs1.next()) {

                                    numcopy = rs1.getInt("NumCopy");
                                }

                                stmt.executeUpdate(sql);

                                // readDataBaseProdect();
                                con.close();

                            } catch (Exception m) {
                                System.out.println(m);
                            }

                            try {
                                int rus = 0;

                                rus = numcopy + 1;
                                Connection con = db.getConnection().connectDB();
                                String sql = "Update Prodect set NumCopy='" + rus + "' where ID='" + p1.getID() + "' ";

                                Statement stmt = con.createStatement();
                                stmt.executeUpdate(sql);

                                // readDataBaseProdect();
                                con.close();

                            } catch (Exception m) {
                                System.out.println(m);
                            }
                        }

                        break;
                    // Clear The Item Table
                    case F7:

                        if (finalflag == 1) {
                            Alert alert = new Alert(Alert.AlertType.NONE, "لا يمكنك مسح الكل " + "\n" + "اضغط على Del", ButtonType.OK);
                            if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                            }

                            return;
                        } else




                            try {

                                for (int i = 0; i < ItemsData.size(); i++) {

                                    int numcopies = 0;
                                    Connection con = db.getConnection().connectDB();
                                    String sql = "Select NumCopy from Prodect where ID='" + ItemsData.get(i).getID() + "'";

                                    Statement stmt = con.createStatement();
                                    ResultSet rs1 = stmt.executeQuery(sql);

                                    while (rs1.next()) {

                                        numcopies = rs1.getInt("NumCopy");
                                    }
                                    System.out.println("Coppes : " + numcopies);
                                    stmt.executeQuery(sql);
                                    con.close();

                                    int r = ItemsData.get(i).getQuantity();
                                    int res = r + numcopies;

                                    ItemsData.get(i).setNumCopy(res);
                                    Connection con1 = db.getConnection().connectDB();
                                    String sql1 = "Update Prodect set NumCopy='" + res + "' where ID='"
                                            + ItemsData.get(i).getID() + "' ";

                                    Statement stmt1 = con1.createStatement();
                                    stmt1.executeUpdate(sql1);
                                    con1.close();
                                }

                            } catch (Exception m) {
                                System.out.println(m);
                            }
                        ItemsData.clear();
                        table.refresh();

                        break;
                    // Sale Item

                    case F3:
                        if (ItemsData.isEmpty() == true) {
                            Alert alert = new Alert(Alert.AlertType.NONE, "الجدول فارغ", ButtonType.OK);
                            if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                            }

                            return;
                        }
                        if (finalflag == 1) {
                            Alert alert = new Alert(Alert.AlertType.NONE, "لا يمكنك بيعه"+"\n"+"اضغط على Del", ButtonType.OK);
                            if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                            }

                            return;
                        } else
                            try {

                                finalflag = 0;

                                ArrayList<Prodect> tem = null;
                                tem = new ArrayList<>();
                                for (int i = 0; i < ItemsData.size(); i++) {

                                    tem.add(new Prodect(ItemsData.get(i).getID(), ItemsData.get(i).getName(),
                                            ItemsData.get(i).getPrice(), ItemsData.get(i).getTotal(),
                                            ItemsData.get(i).getQuantity(), ItemsData.get(i).getProft(),
                                            ItemsData.get(i).getPurchase(), ItemsData.get(i).getTotalProft()));

                                    DaySalesList.add(new Prodect(ItemsData.get(i).getID(), ItemsData.get(i).getName(),
                                            ItemsData.get(i).getPrice(), ItemsData.get(i).getTotal(),
                                            ItemsData.get(i).getQuantity(), ItemsData.get(i).getProft(),
                                            ItemsData.get(i).getPurchase(), ItemsData.get(i).getTotalProft()));

                                    Connection con = db.getConnection().connectDB();
                                    String sql = "insert into DaySales(ID,name,price,total,quantity,proft,Purchase,totalProft) values('"
                                            + ItemsData.get(i).getID() + "','" + ItemsData.get(i).getName() + "','"
                                            + ItemsData.get(i).getPrice() + "','" + ItemsData.get(i).getTotal() + "','"
                                            + ItemsData.get(i).getQuantity() + "','" + ItemsData.get(i).getProft() + "' ,'"
                                            + ItemsData.get(i).getPurchase() + "' , '" + ItemsData.get(i).getTotalProft() + "')";

                                    Statement stmt = con.createStatement();
                                    stmt.executeUpdate(sql);
                                    con.close();
                                }

                                System.out.println("before " + v);
                                temp.add(v, tem);
                                v++;

                                System.out.println("After " + v);

                                ItemsData.clear();
                                texr.setText("");

                            } catch (Exception m) {
                                System.out.println(m);
                            }

                        bHeight = Double.valueOf(ItemsData.size());

                        // System.out.println(ItemsData.size());
                        PrinterJob pj = PrinterJob.getPrinterJob();
                        pj.setPrintable(new BillPrintable(), getPageFormat(pj));


                        try {
                            pj.print();

                        } catch (PrinterException ex) {
                            ex.printStackTrace();
                        }
                        break;



                    // Return The Item After The Sale
                    case F2:

                        if (finalflag == 1) {
                            Alert alert = new Alert(Alert.AlertType.NONE, "لا يمكنك ارجاعه" + "\n" + "اضغط على Del", ButtonType.OK);
                            if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                            }

                            return;
                        } else

                            try {

                                for (int i = 0; i < ItemsData.size(); i++) {

                                    int numcopies = 0;
                                    Connection con = db.getConnection().connectDB();
                                    String sql = "Select NumCopy from Prodect where ID='" + ItemsData.get(i).getID() + "'";

                                    Statement stmt = con.createStatement();
                                    ResultSet rs1 = stmt.executeQuery(sql);

                                    while (rs1.next()) {

                                        numcopies = rs1.getInt("NumCopy");
                                    }
                                    System.out.println("Coppes : " + numcopies);
                                    stmt.executeQuery(sql);
                                    con.close();
                                    int r = ItemsData.get(i).getQuantity();
                                    int res = r + numcopies;

                                    ItemsData.get(i).setNumCopy(res);
                                    Connection con1 = db.getConnection().connectDB();
                                    String sql1 = "Update Prodect set NumCopy='" + res + "' where ID='" + ItemsData.get(i).getID()
                                            + "' ";

                                    Statement stmt1 = con1.createStatement();
                                    stmt1.executeUpdate(sql1);
                                    con1.close();
                                }

                            } catch (Exception m) {
                                System.out.println(m);
                            }

                        try {
                            for (int i = 0; i < ItemsData.size(); i++) {
                                DaySalesList.add(new Prodect(ItemsData.get(i).getID(), "Returning " + ItemsData.get(i).getName(),
                                        ItemsData.get(i).getPrice(), ItemsData.get(i).getTotal(), ItemsData.get(i).getQuantity(),
                                        ItemsData.get(i).getProft(), ItemsData.get(i).getPurchase(),
                                        ItemsData.get(i).getTotalProft()));
                                double m = ItemsData.get(i).getPrice();
                                m = -m;
                                double b = ItemsData.get(i).getPurchase();
                                b = -b;

                                double r = ItemsData.get(i).getTotal();
                                r = -r;

                                double t = ItemsData.get(i).getPrice();
                                t = -t;

                                double y = ItemsData.get(i).getTotalProft();
                                y = -y;

                                Connection con = db.getConnection().connectDB();
                                String sql = "insert into DaySales(ID,name,price,total,quantity,proft,Purchase,totalProft) values('"
                                        + ItemsData.get(i).getID() + "','" + ("Returning " + ItemsData.get(i).getName()) + "','" + t
                                        + "','" + r + "','" + ItemsData.get(i).getQuantity() + "','" + (m) + "' ,'" + (b) + "' , '"
                                        + (y) + "')";

                                Statement stmt = con.createStatement();
                                stmt.executeUpdate(sql);
                                con.close();
                            }

                        } catch (Exception m) {
                            System.out.println(m);
                        }

                        ItemsData.clear();
                        break;
                    // delete one Item from ItemData table
                    case F5:
                        if (finalflag == 1) {
                            Alert alert = new Alert(Alert.AlertType.NONE, "لا يمكنك حذف المنتح " + "\n" + "اضغط على Del", ButtonType.OK);
                            if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                            }

                            return;
                        } else{



                            Prodect p2 = table.getSelectionModel().getSelectedItem();

                            int n2 = p2.getQuantity();
                            table.refresh();
                            double sumAmout3 = 0;
                            for (Prodect o : ItemsData) {

                                sumAmout3 = o.getTotal() + sumAmout3;
                            }
                            texr.setText(NumberFormat.getCurrencyInstance().format(sumAmout3));
                            int numcopy12 = 0;

                            try {

                                Connection con = db.getConnection().connectDB();
                                String sql = "Select NumCopy from Prodect where ID='" + p2.getID() + "'";

                                Statement stmt = con.createStatement();
                                ResultSet rs1 = stmt.executeQuery(sql);
                                while (rs1.next()) {

                                    numcopy12 = rs1.getInt("NumCopy");
                                }

                                stmt.executeQuery(sql);
                                con.close();
                                // readDataBaseProdect();

                            } catch (Exception m) {
                                System.out.println(m);
                            }

                            try {
                                int rus = 0;

                                rus = numcopy12 + n2;
                                Connection con = db.getConnection().connectDB();
                                String sql = "Update Prodect set NumCopy='" + rus + "' where ID='" + p2.getID() + "' ";

                                Statement stmt = con.createStatement();
                                stmt.executeUpdate(sql);

                                // readDataBaseProdect();
                                con.close();
                            } catch (Exception m) {
                                System.out.println(m);
                            }
                            table.getItems().remove(table.getSelectionModel().getSelectedIndex());

                        }
                        break;
                    // add empty row
                    case F1 :
                        if (finalflag == 1) {
                            Alert alert = new Alert(Alert.AlertType.NONE, "لا يمكنك اضافة حقل " + "\n" + "اضغط على Del", ButtonType.OK);
                            if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                            }

                            return;
                        }
                        else

                            ItemsData.add(new Prodect( "1","متفرقات", 0, 0, 0,1));

                        break;
                    // get information about the items
                    case F6:

                        Prodect p3 = table.getSelectionModel().getSelectedItem();
                        Alert alert = new Alert(
                                Alert.AlertType.NONE, "The Purchase : " + p3.getPurchase() + "\n" + "Name : " + p3.getName()
                                + "\n" + "Barcode : " + p3.getID() + "\n" + "The Pricce : " + p3.getPrice(),
                                ButtonType.OK);
                        if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                        }
                        break;
                    // print a bill
                    case F8:

                        bHeight = Double.valueOf(ItemsData.size());

                        // System.out.println(ItemsData.size());
                        PrinterJob pjd = PrinterJob.getPrinterJob();
                        pjd.setPrintable(new BillPrintable(), getPageFormat(pjd));
                            try {
                                pjd.print();

                            } catch (PrinterException ex) {
                                ex.printStackTrace();
                            }
                        break;
                    case DELETE:
                        ItemsData.clear();
                        finalflag = 0;
                        b = 0;
                        break;

                }
            }

            private void AlertBox() {
                TextInputDialog textInput = new TextInputDialog();
                textInput.setTitle("Input The Cash");
                textInput.getDialogPane().setContentText("The Cash :");
                Optional<String> result = textInput.showAndWait();
                TextField input = textInput.getEditor();
                if (input.getText() != null) {
                    cash = Double.parseDouble(input.getText());
                }
            }

        });


//============================================  Days Sales   ============================================================================================================

        Image mh13 = new Image("mainPageEx.jpg");
        ImageView mah13 = new ImageView(mh13);


        Pane daySales = new Pane();

        TextField texday = new TextField();
        texday.setLayoutX(505);
        texday.setLayoutY(21);
        texday.setEditable(false);

        TextField texday1 = new TextField();
        texday1.setLayoutX(505);
        texday1.setLayoutY(72);
        texday1.setEditable(false);


        Label lebday = new Label();
        lebday.setText("مجموع البيع :");
        lebday.setTextFill(Color.WHITE);
        lebday.setLayoutX(694);
        lebday.setLayoutY(25);


        Label lebday1 = new Label();
        lebday1.setText("مجموع الربح :");
        lebday1.setTextFill(Color.WHITE);
        lebday1.setLayoutX(694);
        lebday1.setLayoutY(76);


        Button dayProdect = new Button("رجوع");
        dayProdect.setPrefHeight(60);
        dayProdect.setPrefWidth(90);
        dayProdect.setLayoutX(170);
        dayProdect.setLayoutY(440);
        dayProdect.setTextFill(Color.BLACK);
        dayProdect.setFont(javafx.scene.text.Font.font("Oranienbaum", 18));
        dayProdect.setContentDisplay(ContentDisplay.TOP);
        dayProdect.setId("button");
        dayProdect.setOnAction(e -> {
            stage.setScene(SaleItemsPage);
            stage.setTitle("الصفحة الرئيسية");

        });

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        TextField test1 = new TextField();
        test1.setText(dtf.format(now));



        Button dayProdect1 = new Button("الى الشهرية");
        dayProdect1.setPrefHeight(60);
        dayProdect1.setPrefWidth(121);
        dayProdect1.setLayoutX(390);
        dayProdect1.setLayoutY(440);
        dayProdect1.setTextFill(Color.BLACK);
        dayProdect1.setFont(javafx.scene.text.Font.font("Oranienbaum", 18));
        dayProdect1.setContentDisplay(ContentDisplay.TOP);
        dayProdect1.setId("button");
        dayProdect1.setOnAction(e -> {
            if (DaySalesList.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.NONE, "الجدول فارغ", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }
                return;
            } else

                AlertBox.display("Are You Sure !!", usernameAlert,passwordAlert);

            if (AlertBox.flage == 1) {
                return;
            }

            double sumProft = 0;
            double sumPurchase = 0;

            for (Prodect o : DaySalesList) {
                sumPurchase = o.getTotal() + sumPurchase;

                sumProft = o.getTotalProft() + sumProft;
            }

            texday.setText(NumberFormat.getCurrencyInstance().format(sumPurchase));
            texday1.setText(NumberFormat.getCurrencyInstance().format(sumProft));



            MonthSalesList.add(new MonthSales(sumPurchase, test1.getText(), sumProft));

            try {
                Connection con = db.getConnection().connectDB();
                String sql = "insert into MonthSales(total,Monthdate,proft) values ('" + sumPurchase + "','"
                        + test1.getText() + "','" + sumProft + "' )";
                Statement stmt = con.createStatement();

                stmt.executeUpdate(sql);
                con.close();
            } catch (Exception r) {
                System.out.println(r);
            }


            int PTID = 0;
            try {
                Connection con = db.getConnection().connectDB();
                String sql = "SELECT max(id) AS MAX  from MonthSales ";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    PTID = rs.getInt("MAX");
                }
                con.close();
            } catch (Exception r) {
                System.out.println(r);
            }



            try {
                Connection con = db.getConnection().connectDB();
                for(int i =0 ;i <DaySalesList.size();i++) {
                    String sql = "insert into ItemsSales(name,price,total,quantity,proft,Purchase,totalProft,monthsales_id) values ('"
                            + DaySalesList.get(i).getName() + "','" + DaySalesList.get(i).getPrice() + "','" + DaySalesList.get(i).getTotal() + "','"
                            + DaySalesList.get(i).getQuantity() + "','" + DaySalesList.get(i).getProft() + "' ,'"
                            + DaySalesList.get(i).getPurchase() + "' , '" + DaySalesList.get(i).getTotalProft() + "','" + PTID + "')";

                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(sql);
                }

                con.close();
            } catch (Exception r) {
                System.out.println(r);
            }

            try {

                Calendar calendar = Calendar.getInstance();
                Date dateae = calendar.getTime();
                String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(dateae.getTime());
                LocalDate currentdate = LocalDate.now();
                String NameBills = currentdate + "-" + PTID;
                String path = "C:\\bills\\" + NameBills + ".pdf";
                PdfWriter pdfWriter = new PdfWriter(path);
                PdfDocument pdfDocument = new PdfDocument(pdfWriter);
                pdfDocument.setDefaultPageSize(PageSize.A4);
                float x = pdfDocument.getDefaultPageSize().getWidth() / 2;
                float y = pdfDocument.getDefaultPageSize().getHeight() / 2;

                Document document = new Document(pdfDocument);
                float threecol = 190f;
                float twocol = 285f;
                float twocol150 = 435f;
                float twoColumnWidth[] = { twocol150, twocol };
                float threeColumnWidth[] = { threecol, threecol, threecol, threecol };
                float fullWidth[] = { threecol * 3 };

                Paragraph onesp = new Paragraph("\n");
                Paragraph twosp = new Paragraph("\n\n");
                Table nestedtable = new Table(new float[] { twocol / 2, twocol / 2 });
                Table headerTable = new Table(twoColumnWidth);
                Border nb = new SolidBorder(com.itextpdf.kernel.color.Color.WHITE, 1, 0);

                headerTable.addCell(new Cell().add("Bills").setBold().setFontSize(20f).setBorder(nb)
                        .setTextAlignment(TextAlignment.LEFT).setMarginLeft(5));

                nestedtable.addCell(getHeaderTextCell("Day.:"));
                nestedtable.addCell(getHeaderTextCellValue(day));
                nestedtable.addCell(getHeaderTextCell("Bill Date:"));
                nestedtable.addCell(getHeaderTextCellValue("" + currentdate));

                headerTable.addCell(nestedtable.setBorder(nb)).setBorder(nb);
                document.add(headerTable);
                document.add(new Paragraph("\n"));
                Border gb = new SolidBorder(com.itextpdf.kernel.color.Color.GRAY, 2);
                Table tableDivider = new Table(fullWidth);
                document.add(tableDivider.setBorder(gb));
                document.add(onesp);
                Table twoColTable = new Table(twoColumnWidth);
                twoColTable.addCell(getBillingandShippingCell("Billing Information"));
                document.add(twoColTable.setMarginBottom(12f));

                Table twoColTable2 = new Table(twoColumnWidth);
                twoColTable2.addCell(getCell10fLeft("Company", true));
                twoColTable2.addCell(getCell10fLeft("", true));
                twoColTable2.addCell(getCell10fLeft("AbuThaher", false));

                document.add(twoColTable2);

                Table twoColTable3 = new Table(twoColumnWidth);
                twoColTable3.addCell(getCell10fLeft("Name", true));
                twoColTable3.addCell(getCell10fLeft("", true));
                twoColTable3.addCell(getCell10fLeft("" + usernameAlert+"dd", false));
                document.add(twoColTable3);
                float oneColoumnwidth[] = { twocol150 };
                Table oneColTable1 = new Table(oneColoumnwidth);
                oneColTable1.addCell(getCell10fLeft("Address", true));
                oneColTable1.addCell(getCell10fLeft("AbuSkadem-Birzeit\nRamallah, Main Street", false));
                document.add(oneColTable1.setMarginBottom(10f));

                Table tableDivider2 = new Table(fullWidth);
                Border dgb = new DashedBorder(com.itextpdf.kernel.color.Color.GRAY, 0.5f);
                document.add(tableDivider2.setBorder(dgb));

                Paragraph producPara = new Paragraph("Products");

                document.add(producPara.setBold());
                Table threeColTable1 = new Table(threeColumnWidth);
                threeColTable1.setBackgroundColor(com.itextpdf.kernel.color.Color.BLACK, 0.7f);

                threeColTable1.addCell(new Cell().add("Description").setBold()
                        .setFontColor(com.itextpdf.kernel.color.Color.WHITE).setBorder(nb));
                threeColTable1.addCell(
                        new Cell().add("Quantity").setBold().setFontColor(com.itextpdf.kernel.color.Color.WHITE)
                                .setTextAlignment(TextAlignment.CENTER).setBorder(nb));
                threeColTable1
                        .addCell(new Cell().add("Price").setBold().setFontColor(com.itextpdf.kernel.color.Color.WHITE)
                                .setTextAlignment(TextAlignment.RIGHT).setBorder(nb))
                        .setMarginRight(15f);
                threeColTable1
                        .addCell(new Cell().add("Proft").setBold().setFontColor(com.itextpdf.kernel.color.Color.WHITE)
                                .setTextAlignment(TextAlignment.RIGHT).setBorder(nb))
                        .setMarginRight(20f);
                document.add(threeColTable1);

                Table threeColTable2 = new Table(threeColumnWidth);
                String FONT = "C:\\ARIALUNI.TTF";

                PdfFont F = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H);


                LanguageProcessor al = new ArabicLigaturizer();

                float totalSum = 0;
                float totalProf = 0;
                for (int i = 0; i < DaySalesList.size(); i++) {

                    double total = DaySalesList.get(i).getQuantity() * DaySalesList.get(i).getPrice();
                    double totalpr = DaySalesList.get(i).getQuantity() * DaySalesList.get(i).getProft();
                    totalSum += total;
                    totalProf += totalpr;

                    Paragraph text = new Paragraph(al.process(DaySalesList.get(i).getName())).setFont(F)
                            .setBaseDirection(BaseDirection.RIGHT_TO_LEFT).setTextAlignment(TextAlignment.LEFT);

                    threeColTable2.addCell(new Cell().add(text).setBorder(nb)).setMarginLeft(10f);
                    threeColTable2.addCell(new Cell().add(String.valueOf(DaySalesList.get(i).getQuantity()))
                            .setTextAlignment(TextAlignment.CENTER).setBorder(nb));
                    threeColTable2.addCell(
                                    new Cell().add(String.valueOf(total)).setTextAlignment(TextAlignment.RIGHT).setBorder(nb))
                            .setMarginRight(15f);
                    threeColTable2.addCell(new Cell().add(String.valueOf(DaySalesList.get(i).getProft()))
                            .setTextAlignment(TextAlignment.RIGHT).setBorder(nb)).setMarginRight(20f);
                }
                document.add(threeColTable2.setMarginBottom(20f));
                float onetwo[] = { threecol + 125f, threecol * 2 };
                Table threeColTable4 = new Table(onetwo);
                threeColTable4.addCell(new Cell().add("").setBorder(nb));
                threeColTable4.addCell(tableDivider2).setBorder(nb);
                document.add(threeColTable4);
                float threeColumnWidth23[] = { threecol, threecol, threecol, threecol, threecol, threecol };
                Table threeColTable3 = new Table(threeColumnWidth23);
                threeColTable3.addCell(new Cell().add("").setBorder(nb)).setMarginLeft(10f);
                threeColTable3.addCell(new Cell().add("Total").setTextAlignment(TextAlignment.CENTER).setBorder(nb));
                threeColTable3.addCell(
                                new Cell().add(String.valueOf(totalSum)).setTextAlignment(TextAlignment.RIGHT).setBorder(nb))
                        .setMarginRight(15f);
                threeColTable3.addCell(new Cell().add("").setBorder(nb)).setMarginLeft(10f);
                threeColTable3
                        .addCell(new Cell().add("Total Proft").setTextAlignment(TextAlignment.CENTER).setBorder(nb));
                threeColTable3.addCell(
                                new Cell().add(String.valueOf(totalProf)).setTextAlignment(TextAlignment.RIGHT).setBorder(nb))
                        .setMarginRight(12f);

                document.add(threeColTable3);
                document.add(tableDivider2);
                document.add(new Paragraph("\n"));
                document.add(tableDivider.setBorder(new SolidBorder(1)).setMarginBottom(15f));
                document.close();

            } catch (Exception e2) {
                e2.getMessage();
            }

            try {
                Connection con = db.getConnection().connectDB();
                String sql = "TRUNCATE TABLE daySales";
                Statement stmt = con.createStatement();
                // ResultSet rs = stmt.executeQuery( sql );
                stmt.executeUpdate(sql);
                con.close();
            } catch (Exception r) {
                System.out.println(r);
            }

            DaySalesList.clear();

        });

        TableView<Prodect> Pro1 = new TableView<>();

        Button dayCProdect = new Button("حذف");
        dayCProdect.setPrefHeight(60);
        dayCProdect.setPrefWidth(90);
        dayCProdect.setLayoutX(637);
        dayCProdect.setLayoutY(440);
        dayCProdect.setTextFill(Color.BLACK);
        dayCProdect.setFont(javafx.scene.text.Font.font("Oranienbaum", 18));
        dayCProdect.setContentDisplay(ContentDisplay.TOP);
        dayCProdect.setId("button");
        dayCProdect.setOnAction(e -> {

            if (Pro1.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.NONE, "يحب ان تختار حقل ", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }
                return;
            }
            Prodect selectedItem = Pro1.getSelectionModel().getSelectedItem();
            int IdSelected = selectedItem.getIdd();

            try {

                Connection con = db.getConnection().connectDB();
                String sql = "Delete from daysales WHERE idd='" + IdSelected + "'";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(sql);
                con.close();


                Alert alert = new Alert(Alert.AlertType.NONE, "تم الحذف بنجاح ", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }


            } catch (Exception e2) {
                e2.printStackTrace();
            }

            DaySalesList.remove(selectedItem);
            Pro1.refresh();
            double sumProft = 0;
            double sumPurchase = 0;
            int one = 0;
            double two = 0;
            double res = 0;

            for (Prodect o : DaySalesList) {
                sumPurchase = o.getTotal() + sumPurchase;
                one = o.getQuantity();
                two = o.getProft();
                res = two * one;
                sumProft = res + sumProft;
            }

            texday.setText(NumberFormat.getCurrencyInstance().format(sumPurchase));
            texday1.setText(NumberFormat.getCurrencyInstance().format(sumProft));
        });

        Pro1.setPrefHeight(300);
        Pro1.setPrefWidth(600);
        Pro1.setLayoutX(150);
        Pro1.setLayoutY(113);
        b12.setOnAction(e -> {
            stage.setScene(DaySalesPage);
            stage.setTitle("المبيعات اليومية");

            try {
                DaySalesList.clear();
                readDataBaseDaySales();
            } catch (ClassNotFoundException | SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            double sumProft = 0;
            double sumPurchase = 0;
            int one = 0;
            double two = 0;
            double res = 0;

            for (Prodect o : DaySalesList) {
                sumPurchase = o.getTotal() + sumPurchase;
                one = o.getQuantity();
                two = o.getProft();
                res = two * one;
                sumProft = res + sumProft;
            }

            texday.setText(NumberFormat.getCurrencyInstance().format(sumPurchase));
            texday1.setText(NumberFormat.getCurrencyInstance().format(sumProft));




        });

        TableColumn<Prodect, String> name1 = new TableColumn<>("الاسم");
        name1.setPrefWidth(120);
        name1.setResizable(false);
        name1.setCellValueFactory(new PropertyValueFactory<Prodect, String>("name"));

        TableColumn<Prodect, Double> Price1 = new TableColumn<>("السعر");
        Price1.setPrefWidth(120);
        Price1.setResizable(false);
        Price1.setCellValueFactory(new PropertyValueFactory<Prodect, Double>("price"));

        TableColumn<Prodect, Integer> num1 = new TableColumn<>("الكمية");
        num1.setPrefWidth(120);
        num1.setResizable(false);
        num1.setCellValueFactory(new PropertyValueFactory<Prodect, Integer>("quantity"));

        TableColumn<Prodect, Double> Proft1 = new TableColumn<>("الربح");
        Proft1.setPrefWidth(120);
        Proft1.setResizable(false);
        Proft1.setCellValueFactory(new PropertyValueFactory<Prodect, Double>("proft"));

        TableColumn<Prodect, Double> Proft2 = new TableColumn<>("مجموع الربح");
        Proft2.setPrefWidth(120);
        Proft2.setResizable(false);
        Proft2.setCellValueFactory(new PropertyValueFactory<Prodect, Double>("totalproft"));


        Pro1.getColumns().addAll(Proft2, Proft1, Price1, num1, name1);

        Pro1.setPrefSize(600, 300);

        Pro1.setItems(DaySalesList);

        daySales.getChildren().addAll(mah13, dayProdect, dayProdect1, dayCProdect,Pro1, texday, texday1, lebday, lebday1);

        DaySalesPage = new Scene(daySales, 900, 563);
        DaySalesPage.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//============================================  Month Sales   ============================================================================================================

        Image mh14 = new Image("mainPageEx.jpg");
        ImageView mah14 = new ImageView(mh14);


        Pane MonthSales = new Pane();

        TextField texmonth = new TextField();
        texmonth.setPromptText("مجموع البيع");
        texmonth.setLayoutX(505);
        texmonth.setLayoutY(21);
        texmonth.setEditable(false);


        TextField texmonth1 = new TextField();
        texmonth1.setPromptText("مجموع الربح");
        texmonth1.setLayoutX(505);
        texmonth1.setLayoutY(72);
        texmonth1.setEditable(false);

        Label lebmonth = new Label();
        lebmonth.setText("مجموع البيع :");
        lebmonth.setTextFill(Color.WHITE);
        lebmonth.setLayoutX(694);
        lebmonth.setLayoutY(21);



        Label lebmonth1 = new Label();
        lebmonth1.setText("مجموع الربح :");
        lebmonth1.setTextFill(Color.WHITE);
        lebmonth1.setLayoutX(694);
        lebmonth1.setLayoutY(72);

        TableView<MonthSales> Pro2 = new TableView<>();
        Pro2.setLayoutX(244);
        Pro2.setLayoutY(117);
        Pro2.setPrefSize(450, 300);


        Button MonthProdect = new Button("رجوع");
        MonthProdect.setPrefHeight(60);
        MonthProdect.setPrefWidth(90);
        MonthProdect.setLayoutX(244);
        MonthProdect.setLayoutY(440);
        MonthProdect.setTextFill(Color.BLACK);
        MonthProdect.setContentDisplay(ContentDisplay.TOP);
        MonthProdect.setId("button");
        MonthProdect.setOnAction(e -> {
            stage.setScene(SaleItemsPage);
            stage.setTitle("الصفحة الرئيسية");

        });

        Button monthProdect1 = new Button("الى"+"\n"+"السنوية");
        monthProdect1.setPrefHeight(60);
        monthProdect1.setPrefWidth(90);
        monthProdect1.setTextFill(Color.BLACK);
        monthProdect1.setLayoutX(367);
        monthProdect1.setLayoutY(440);
        monthProdect1.setContentDisplay(ContentDisplay.TOP);
        monthProdect1.setId("button");
        monthProdect1.setOnAction(e -> {

            if (MonthSalesList.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.NONE, "الجدول فارغ", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }
                return;
            } else

                AlertBox.display("Are You Sure !!",usernameAlert,passwordAlert);

            if (AlertBox.flage == 1) {
                return;
            }

            double sumProft = 0;
            double sumPurchase = 0;

            for (MonthSales o : MonthSalesList) {
                sumPurchase = o.getTotalPurchase() + sumPurchase;
                sumProft = o.getTotalProft() + sumProft;
            }

            texmonth.setText(NumberFormat.getCurrencyInstance().format(sumPurchase));
            texmonth1.setText(NumberFormat.getCurrencyInstance().format(sumProft));

            YearSalesList.add(new YearSales(sumProft, test1.getText(), sumPurchase));

            try {
                Connection con = db.getConnection().connectDB();
                String sql = "insert into YearSales(total,Monthdate,proft) values ('" + sumProft + "','"
                        + test1.getText() + "','" + sumPurchase + "' )";
                Statement stmt = con.createStatement();

                stmt.executeUpdate(sql);
                con.close();
            } catch (Exception m) {
                System.out.println(m);
            }
        });

        Button monthCProdect = new Button("حذف");
        monthCProdect.setPrefHeight(60);
        monthCProdect.setPrefWidth(90);
        monthCProdect.setLayoutX(609);
        monthCProdect.setLayoutY(440);
        monthCProdect.setTextFill(Color.BLACK);
        monthCProdect.setFont(javafx.scene.text.Font.font("Oranienbaum", 18));
        monthCProdect.setContentDisplay(ContentDisplay.TOP);
        monthCProdect.setId("button");
        monthCProdect.setOnAction(e -> {

            if (Pro2.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.NONE, "يحب ان تختار حقل ", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }
                return;
            }
            MonthSales selectedItem = Pro2.getSelectionModel().getSelectedItem();
            int IdSelected = selectedItem.getId();
            try {

                Connection con = db.getConnection().connectDB();
                String sql = "Delete from monthsales WHERE id='" + IdSelected + "'";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(sql);
                con.close();


                Alert alert = new Alert(Alert.AlertType.NONE, "تم الحذف بنجاح ", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }


            } catch (Exception e2) {
                e2.printStackTrace();
            }
            MonthSalesList.remove(selectedItem);
            Pro2.refresh();

            double sumProft = 0;
            double sumPurchase = 0;

            for (MonthSales o : MonthSalesList) {
                sumPurchase = o.getTotalPurchase() + sumPurchase;

                sumProft = o.getTotalProft() + sumProft;
            }

            texmonth.setText(NumberFormat.getCurrencyInstance().format(sumPurchase));
            texmonth1.setText(NumberFormat.getCurrencyInstance().format(sumProft));


        });

        Button BillInfo = new Button("عرض"+"\n"+"المبيعات");
        BillInfo.setPrefHeight(60);
        BillInfo.setPrefWidth(90);
        BillInfo.setLayoutX(489);
        BillInfo.setLayoutY(440);
        BillInfo.setTextFill(Color.BLACK);
        BillInfo.setFont(javafx.scene.text.Font.font("Oranienbaum", 18));
        BillInfo.setContentDisplay(ContentDisplay.TOP);
        BillInfo.setId("button");
        BillInfo.setOnAction(e->{
            ItemsSales.clear();
            if (Pro2.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.NONE, "يحب ان تختار حقل ", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }
                return;
            }
            MonthSales selectedItem = Pro2.getSelectionModel().getSelectedItem();
            int IdSelected = selectedItem.getId();


                try {
                Connection con = db.getConnection().connectDB();
                String sql = "SELECT * FROM ItemsSales WHERE monthsales_id='" + IdSelected + "'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    String nameS = rs.getString("name");
                    double price = rs.getDouble("price");
                    double total = rs.getDouble("total");
                    int quantity = rs.getInt("quantity");
                    double proft = rs.getDouble("proft");
                    double purchase = rs.getDouble("Purchase");
                    double totalProft = rs.getDouble("totalProft");
                    int ids = rs.getInt("id");
                    ItemsSales.add(new Prodect(nameS, price, total, quantity, proft, purchase, totalProft, ids));
                }
                con.close();
            } catch (Exception e2) {
                System.out.println(e2);
            }

                Image mh15 = new Image("mainPageEx.jpg");
                ImageView mah15 = new ImageView(mh15);


                Pane ViewItems = new Pane();
                TableView<Prodect> Pro4 = new TableView<>();
                Pro4.setLayoutX(82);
                Pro4.setLayoutY(109);
                Pro4.setPrefSize(600, 300);



                TableColumn<Prodect, String> name2 = new TableColumn<>("الاسم");
                name2.setPrefWidth(120);
                name2.setResizable(false);
                name2.setCellValueFactory(new PropertyValueFactory<Prodect, String>("name"));

                TableColumn<Prodect, Double> Price2 = new TableColumn<>("السعر");
                Price2.setPrefWidth(120);
                Price2.setResizable(false);
                Price2.setCellValueFactory(new PropertyValueFactory<Prodect, Double>("price"));

                TableColumn<Prodect, Integer> num2 = new TableColumn<>("الكمية");
                num2.setPrefWidth(120);
                num2.setResizable(false);
                num2.setCellValueFactory(new PropertyValueFactory<Prodect, Integer>("quantity"));

                TableColumn<Prodect, Double> Proft3 = new TableColumn<>("الربح");
                Proft3.setPrefWidth(120);
                Proft3.setResizable(false);
                Proft3.setCellValueFactory(new PropertyValueFactory<Prodect, Double>("proft"));

                TableColumn<Prodect, Double> Proft4 = new TableColumn<>("مجموع الربح");
                Proft4.setPrefWidth(120);
                Proft4.setResizable(false);
                Proft4.setCellValueFactory(new PropertyValueFactory<Prodect, Double>("totalproft"));

                Pro4.getColumns().addAll(Proft4, Proft3, Price2, num2, name2);
                Pro4.setItems(ItemsSales);
                Label lebmonth2 = new Label();
                lebmonth2.setText("مجموع البيع :");
                lebmonth2.setTextFill(Color.WHITE);
                lebmonth2.setLayoutX(651);
                lebmonth2.setLayoutY(25);

                Label lebmonth3 = new Label();
                lebmonth3.setText("مجموع الربح :");
                lebmonth3.setTextFill(Color.WHITE);
                lebmonth3.setLayoutX(651);
                lebmonth3.setLayoutY(76);

                TextField texmonth2 = new TextField();
                texmonth2.setPromptText("مجموع البيع");
                texmonth2.setLayoutX(480);
                texmonth2.setLayoutY(21);
                texmonth2.setEditable(false);

                TextField texmonth3 = new TextField();
                texmonth3.setPromptText("مجموع الربح");
                texmonth3.setLayoutX(480);
                texmonth3.setLayoutY(72);
                texmonth3.setEditable(false);

            double sumProft = 0;
            double sumPurchase = 0;
            int one = 0;
            double two = 0;
            double res = 0;

            for (Prodect o : ItemsSales) {
                sumPurchase = o.getTotal() + sumPurchase;
                one = o.getQuantity();
                two = o.getProft();
                res = two * one;
                sumProft = res + sumProft;
            }

            texmonth2.setText(NumberFormat.getCurrencyInstance().format(sumPurchase));
            texmonth3.setText(NumberFormat.getCurrencyInstance().format(sumProft));




                ViewItems.getChildren().addAll(mah15,Pro4 , lebmonth2 , lebmonth3 , texmonth2 , texmonth3);
                Scene ViewItemsPage = new Scene(ViewItems, 763, 434);
                ViewItemsPage.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                Stage stage1 = new Stage();
                stage1.setScene(ViewItemsPage);
                stage1.show();







        });





        TableColumn<MonthSales, Double> totalproft = new TableColumn<>("مجموع الربح");
        totalproft.setPrefWidth(150);
        totalproft.setResizable(false);
        totalproft.setCellValueFactory(new PropertyValueFactory<MonthSales, Double>("totalProft"));

        TableColumn<MonthSales, Double> totalPur = new TableColumn<>("مجموع البيع");
        totalPur.setPrefWidth(150);
        totalPur.setResizable(false);
        totalPur.setCellValueFactory(new PropertyValueFactory<MonthSales, Double>("totalPurchase"));

        TableColumn<MonthSales, String> dateSales = new TableColumn<>("التاريخ");
        dateSales.setPrefWidth(150);
        dateSales.setResizable(false);
        dateSales.setCellValueFactory(new PropertyValueFactory<MonthSales, String>("date"));
        b13.setOnAction(e -> {
            stage.setScene(MonthSalesPage);
            stage.setTitle("المبيعات الشهرية");
            try {
                MonthSalesList.clear();
                readDataBaseMonthSales();
            } catch (ClassNotFoundException | SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            double sumProft = 0;
            double sumPurchase = 0;

            for (MonthSales o : MonthSalesList) {
                sumPurchase = o.getTotalPurchase() + sumPurchase;

                sumProft = o.getTotalProft() + sumProft;
            }

            texmonth.setText(NumberFormat.getCurrencyInstance().format(sumPurchase));
            texmonth1.setText(NumberFormat.getCurrencyInstance().format(sumProft));

        });




        Pro2.getColumns().addAll(dateSales, totalproft,totalPur );
        Pro2.setItems(MonthSalesList);
        MonthSales.getChildren().addAll(mah14, MonthProdect, monthProdect1, monthCProdect, BillInfo, Pro2, texmonth, texmonth1,
                lebmonth, lebmonth1);

        MonthSalesPage = new Scene(MonthSales, 900, 563);
        MonthSalesPage.getStylesheets().add(getClass().getResource("application.css").toExternalForm());



//============================================  YearSalesPage   ============================================================================================================

        Image mh15 = new Image("mainPageEx.jpg");
        ImageView mah15 = new ImageView(mh15);

       Pane YearMonthSales = new Pane();

        TextField texyear = new TextField();
        texyear.setLayoutX(505);
        texyear.setLayoutY(21);
        texyear.setPromptText("مجموع البيع");


        TextField texyear1 = new TextField();
        texyear1.setLayoutX(505);
        texyear1.setLayoutY(72);
        texyear1.setPromptText("مجموع الربح");


        Label lebyear = new Label();
        lebyear.setText("مجموع البيع :");
        lebyear.setLayoutX(694);
        lebyear.setLayoutY(25);
        lebyear.setTextFill(Color.WHITE);


        Label lebyear1 = new Label();
        lebyear1.setText("مجموع الربح :");
        lebyear1.setLayoutX(694);
        lebyear1.setLayoutY(76);
        lebyear1.setTextFill(Color.WHITE);


        Button yearProdect = new Button("رجوع");
        yearProdect.setPrefHeight(60);
        yearProdect.setPrefWidth(90);
        yearProdect.setLayoutX(244);
        yearProdect.setLayoutY(433);
        yearProdect.setTextFill(Color.BLACK);
        yearProdect.setContentDisplay(ContentDisplay.TOP);
        yearProdect.setId("button");

        yearProdect.setOnAction(e -> {
            stage.setScene(SaleItemsPage);
            stage.setTitle("الصفحة الرئيسية");

        });

        Button yearCProdect = new Button("حذف");
        yearCProdect.setPrefHeight(60);
        yearCProdect.setPrefWidth(90);
        yearCProdect.setLayoutX(604);
        yearCProdect.setLayoutY(440);
        yearCProdect.setTextFill(Color.BLACK);
        yearCProdect.setContentDisplay(ContentDisplay.TOP);
        yearCProdect.setId("button");

        TableView<YearSales> Pro3 = new TableView<>();
        Pro3.setLayoutX(244);
        Pro3.setLayoutY(117);
        Pro3.setPrefSize(450, 300);
        yearCProdect.setOnAction(e -> {
            if (Pro3.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.NONE, "يحب ان تختار حقل ", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }
                return;
            }
            YearSales selectedItem = Pro3.getSelectionModel().getSelectedItem();
            int IdSelected = selectedItem.getId();



            try {

                Connection con = db.getConnection().connectDB();
                String sql = "Delete from yearsales WHERE id='" + IdSelected + "'";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(sql);
                con.close();


                Alert alert = new Alert(Alert.AlertType.NONE, "تم الحذف بنجاح ", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }


            } catch (Exception e2) {
                e2.printStackTrace();
            }

            YearSalesList.remove(selectedItem);
            Pro3.refresh();

            double sumProft = 0;
            double sumPurchase = 0;

            for (YearSales o : YearSalesList) {
                sumPurchase = o.getTotalPurchase() + sumPurchase;

                sumProft = o.getTotalProft() + sumProft;
            }

            texyear.setText(NumberFormat.getCurrencyInstance().format(sumPurchase));
            texyear1.setText(NumberFormat.getCurrencyInstance().format(sumProft));



        });



        TableColumn<YearSales, Double> totalproft1 = new TableColumn<>("مجموع الربح");
        totalproft1.setPrefWidth(150);
        totalproft1.setResizable(false);
        totalproft1.setCellValueFactory(new PropertyValueFactory<YearSales, Double>("totalProft"));

        TableColumn<YearSales, Double> totalPur1 = new TableColumn<>("مجموع البيع");
        totalPur1.setPrefWidth(150);
        totalPur1.setResizable(false);
        totalPur1.setCellValueFactory(new PropertyValueFactory<YearSales, Double>("totalPurchase"));

        TableColumn<YearSales, String> dateSales1 = new TableColumn<>("التاريخ");
        dateSales1.setPrefWidth(150);
        dateSales1.setResizable(false);
        dateSales1.setCellValueFactory(new PropertyValueFactory<YearSales, String>("date"));

        Pro3.getColumns().addAll(dateSales1, totalproft1,totalPur1);

        Pro3.setItems(YearSalesList);
        YearMonthSales.getChildren().addAll(mah15, yearProdect, yearCProdect, Pro3, texyear, texyear1, lebyear,
                lebyear1);
        b14.setOnAction(e -> {
            stage.setScene(YearSalesPage);
            stage.setTitle("المبيعات السنوية");

            try {
                YearSalesList.clear();
                readDataBaseYearSales();
            } catch (ClassNotFoundException | SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            double sumProft = 0;
            double sumPurchase = 0;

            for (YearSales o : YearSalesList) {
                sumPurchase = o.getTotalPurchase() + sumPurchase;

                sumProft = o.getTotalProft() + sumProft;
            }

            texyear.setText(NumberFormat.getCurrencyInstance().format(sumPurchase));
            texyear1.setText(NumberFormat.getCurrencyInstance().format(sumProft));

        });

        YearSalesPage = new Scene(YearMonthSales, 900, 563);
        YearSalesPage.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        stage.setScene(LoginPAGE);
        stage.setTitle("تسجيل الدخول");
        stage.getIcons().add(new Image("sales-manager.png"));
      //  stage.setResizable(false);
        stage.show();

    }

    private void AlertBox() {
        TextInputDialog textInput = new TextInputDialog();
        textInput.setTitle("Input The Cash");
        textInput.getDialogPane().setContentText("The Cash :");
        Optional<String> result = textInput.showAndWait();
        TextField input = textInput.getEditor();
        if (input.getText() != null) {
            cash = Double.parseDouble(input.getText());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}


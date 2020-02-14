/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg0.notepad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author migo2
 */
public class NotePad extends Application {

    //global variables
    String text;

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();

        MenuBar bar = new MenuBar();
        Menu file = new Menu("File");
        Menu edit = new Menu("Edit");
        Menu help = new Menu("Help");
        Menu compile = new Menu("Compile");

        //separators
        SeparatorMenuItem menuItem0 = new SeparatorMenuItem();
        SeparatorMenuItem menuItem00 = new SeparatorMenuItem();
        SeparatorMenuItem menuItem000 = new SeparatorMenuItem();

        //file menu
        MenuItem New = new MenuItem("New ");
        New.setAccelerator(KeyCombination.keyCombination("ctrl+q"));
        MenuItem Open = new MenuItem("Open ");
        Open.setAccelerator(KeyCombination.keyCombination("ctrl+w"));
        MenuItem Save = new MenuItem("Save ");
        Save.setAccelerator(KeyCombination.keyCombination("ctrl+e"));
        MenuItem Exit = new MenuItem("Exit ");
        Exit.setAccelerator(KeyCombination.keyCombination("ctrl+r"));

        //edit menu
        MenuItem Undo = new MenuItem("Undo ");
        Undo.setAccelerator(KeyCombination.keyCombination("ctrl+t"));

        MenuItem Cut = new MenuItem("Cut ");
        Cut.setAccelerator(KeyCombination.keyCombination("ctrl+u"));
        MenuItem Copy = new MenuItem("Copy ");
        Copy.setAccelerator(KeyCombination.keyCombination("ctrl+i"));
        MenuItem Paste = new MenuItem("Paste ");
        Paste.setAccelerator(KeyCombination.keyCombination("ctrl+p"));
        MenuItem Delete = new MenuItem("Delete ");
        Delete.setAccelerator(KeyCombination.keyCombination("ctrl+d"));

        MenuItem SelectA = new MenuItem("select All ");
        SelectA.setAccelerator(KeyCombination.keyCombination("ctrl+f"));

        //help menu
        MenuItem About = new MenuItem("About ");
        About.setAccelerator(KeyCombination.keyCombination("ctrl+h"));

        //compile menu
        MenuItem Compile = new MenuItem("Compile ");
        Compile.setAccelerator(KeyCombination.keyCombination("ctrl+k"));

        //adding MenuItems to their respective Menus
        file.getItems().addAll(New, Open, Save, menuItem0, Exit);
        edit.getItems().addAll(Undo, menuItem00, Cut, Copy, Paste, Delete, menuItem000, SelectA);
        help.getItems().addAll(About);
        compile.getItems().addAll(Compile);

        //adding Menus to MenuBar
        bar.getMenus().addAll(file, edit, help, compile);

        //adding TextArea to Pane
        TextArea textArea = new TextArea();

        BorderPane pane = new BorderPane();
        pane.setTop(bar);
        pane.setCenter(textArea);

        Scene scene = new Scene(pane, 300, 250);

        primaryStage.setTitle("notePad!");
        primaryStage.getIcons().add(new Image(NotePad.class.getResourceAsStream("icon.png")));
        primaryStage.setScene(scene);
        primaryStage.show();

        //Adding Action Listeners to fileMenu Items.
        New.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (!textArea.getText().isEmpty()) {
                    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                    Alert sure = new Alert(AlertType.WARNING,
                            "Do you want to save this file?", yes, no);
                    sure.setTitle("NotePad");
                    Optional<ButtonType> result = sure.showAndWait();
                    if (result.get() == no) {
                        textArea.clear();
                    } else if (result.get() == yes) {
                        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                        fileChooser.getExtensionFilters().add(extFilter);
                        File file = fileChooser.showSaveDialog(primaryStage);
                        try {
                            saveTextToFile(textArea.getText(), file);
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(NotePad.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        textArea.clear();
                    }
                }
            }
        });
        Open.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    if (!textArea.getText().isEmpty()) {
                        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                        Alert sure = new Alert(AlertType.WARNING,
                                "Do you want to save this file?", yes, no);
                        sure.setTitle("NotePad");
                        Optional<ButtonType> result = sure.showAndWait();
                        if (result.get() == no) {
                            textArea.clear();
                        } else if (result.get() == yes) {
                            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                            fileChooser.getExtensionFilters().add(extFilter);
                            File file = fileChooser.showSaveDialog(primaryStage);
                            try {
                                saveTextToFile(textArea.getText(), file);
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(NotePad.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }

                    }
                    File openedFileLow = fileChooser.showOpenDialog(primaryStage);
                    if (openedFileLow != null) {
                        FileInputStream openedFile = new FileInputStream(openedFileLow);
                        int size = openedFile.available();
                        byte[] b = new byte[size];
                        openedFile.read(b);
                        String conv = new String(b);
                        textArea.setText(conv);
                        textArea.requestFocus();
                        textArea.end();
                        primaryStage.setTitle(openedFileLow.getName());
                    }

                } catch (Exception ex) {
                    Logger.getLogger(NotePad.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        Save.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //Show save file dialog
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);

                File file = fileChooser.showSaveDialog(primaryStage);

                try {
                    if (file != null) {
                        saveTextToFile(textArea.getText(), file);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(NotePad.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        Exit.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });

        //Adding Action Listeners to EditMenu Items.
        Undo.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                textArea.undo();
            }
        });
        Cut.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                textArea.cut();
            }
        });
        Copy.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                textArea.copy();
            }
        });
        Paste.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                textArea.paste();
            }
        });
        Delete.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                textArea.deleteNextChar();
            }
        });
        SelectA.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                textArea.selectAll();
            }
        });

        About.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Developer Information");
//                alert.setHeaderText("Look, an Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("This Program was developed by Eng. Mohammed Naguib");
                alert.show();
            }
        });

    }

    private void saveTextToFile(String str, File file) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(file);
        writer.println(str);
        writer.close();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

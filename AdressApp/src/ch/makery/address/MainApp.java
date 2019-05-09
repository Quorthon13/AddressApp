package ch.makery.address;

import java.io.File;
import java.io.IOException;

import java.util.prefs.Preferences;

import javafx.application.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import ch.makery.address.model.Person;
import ch.makery.address.model.PersonListWrapper;
import ch.makery.address.util.Alertas;
import ch.makery.address.view.BirthdayStatisticsController;
import ch.makery.address.view.PersonEditDialogController;
import ch.makery.address.view.PersonOverviewController;
import ch.makery.address.view.RootLayoutController;
import javax.xml.bind.JAXBException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private final String layoutResource = "view/RootLayout.fxml";
    private final String personResource = "view/PersonOverview.fxml";
    private final String personEditResource = "view/PersonEditDialog.fxml";
    private final String birthdayResource = "view/BirthdayStatistics.fxml";
    
    
    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
        personData.add(new Person("Jair", "Bolsonaro"));
        personData.add(new Person("Luis Inácio", "Lula da Silva"));
        personData.add(new Person("Heinz", "Kurz"));
        personData.add(new Person("Cornelia", "Meier"));
        personData.add(new Person("Werner", "Meyer"));
        personData.add(new Person("Lydia", "Kunz"));
        personData.add(new Person("Anna", "Best"));
        personData.add(new Person("Stefan", "Meier"));
        personData.add(new Person("Martin", "Mueller"));
    }
  
    /**
     * Returns the data as an observable list of Persons. 
     * @return
     */
    
    public ObservableList<Person> getPersonData() {
        return personData;
    }
    
    

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");
        
        // Set the application icon.
        this.primaryStage.getIcons().add(new Image("file:resources/images/address_book.png"));

        initRootLayout();

        showPersonOverview();
    }
    
    
    
    /**
     * Método auxiliar que cria um FMXL loader em determinada resource
     * 
     */
    public FXMLLoader loadFXML(String resource){
        FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource(resource));
            return loader;
    }
     
    /**
     * Initializes the root layout and tries to load the last opened
     * person file.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = loadFXML(layoutResource);
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            
            // Give the controller access to the main app.
            rootLayoutAccessController layoutController;
            layoutController = new rootLayoutAccessController();
            layoutController.AccessController(loader, this);
            
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened person file.
        loadLastFile();
    }
    public void loadLastFile(){
        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() {
    try {
        // Load person overview.
        FXMLLoader loader = loadFXML(personResource);
        AnchorPane personOverview = (AnchorPane) loader.load();

        // Set person overview into the center of root layout.
        rootLayout.setCenter(personOverview);

        // Give the controller access to the main app.
        personOverviewAccessController personController;
        personController = new personOverviewAccessController();
        personController.AccessController(loader, this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Cria uma caixa modal
     */
    public Stage createDialog(AnchorPane page,String title){
        
        //Cria um novo palco com titulo e icone
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.getIcons().add(new Image("file:resources/images/address_book.png"));
        
        //Coloca a modal no palco principal
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        return dialogStage;  
    }
    
    
    
    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     * 
     * @param person the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
                         
    public boolean showPersonEditDialog(Person person) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = loadFXML(personEditResource);
            AnchorPane page = (AnchorPane) loader.load();
            
            // Create the dialog Stage.
            Stage dialogStage = createDialog(page,"Edit Person");
            
            // Set the person into the controller.
            PersonEditDialogController controller = null;
            personEditDialogAccessController controllerAccess = 
                            new personEditDialogAccessController();
            controller = controllerAccess.AccessController(person, dialogStage, loader);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Returns the person file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     * 
     * @return
     */
   public File getPersonFilePath() {
       Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
       String filePath = prefs.get("filePath", null);
       if (filePath != null) {
           return new File(filePath);
       } else {
           return null;
       }
   }

   /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     * 
     * @param file the file or null to remove the path
     */
   public void setPersonFilePath(File file) {
       Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
       if (file != null) {
           prefs.put("filePath", file.getPath());

           // Update the stage title.
           primaryStage.setTitle("AddressApp - " + file.getName());
       } else {
           prefs.remove("filePath");

           // Update the stage title.
           primaryStage.setTitle("AddressApp");
       }
   }
   
   /**
     * Loads person data from the specified file. The current person data will
     * be replaced.
     * 
     * @param file
     */
   public void loadPersonDataFromFile(File file) {
       try {
           JAXBContext context = JAXBContext
                   .newInstance(PersonListWrapper.class);
           Unmarshaller um = context.createUnmarshaller();

           // Reading XML from the file and unmarshalling.
           PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);

           personData.clear();
           personData.addAll(wrapper.getPersons());

           // Save the file path to the registry.
           setPersonFilePath(file);

       } catch (JAXBException e) { // catches ANY exception
           Alertas.unknownData(file);
       }
   }

   /**
     * Saves the current person data to the specified file.
     * 
     * @param file
     */
   public void savePersonDataToFile(File file) {
       try {
           JAXBContext context = JAXBContext
                   .newInstance(PersonListWrapper.class);
           Marshaller m = context.createMarshaller();
           m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

           // Wrapping our person data.
           PersonListWrapper wrapper = new PersonListWrapper();
           wrapper.setPersons(personData);

           // Marshalling and saving XML to the file.
           m.marshal(wrapper, file);

           // Save the file path to the registry.
           setPersonFilePath(file);
           
       } catch (Exception e) { // catches ANY exception
           Alertas.unknownData(file);
       }
   }
   
   /**
     * Opens a dialog to show birthday statistics.
     */
   public void showBirthdayStatistics() {
       try {
           // Load the fxml file and create a new stage for the popup.
           FXMLLoader loader = loadFXML(birthdayResource);
           AnchorPane page = (AnchorPane) loader.load();
           
           Stage dialogStage = createDialog(page,"Birthday Statistics");

           // Set the persons into the controller.
           birthdayAccessController birthdayController = new birthdayAccessController();
           birthdayController.AccessController(loader,personData);
           
           dialogStage.show();

       } catch (IOException e) {
           e.printStackTrace();
       }
   }
   
   /*
   * Classes de acesso aos controllers com diferentes contratos (interfaces)
   */
   
   public class birthdayAccessController implements AccessDefault{
        @Override
        public void AccessController(FXMLLoader loader, ObservableList<Person> personData){
            BirthdayStatisticsController controller = loader.getController();
            controller.setPersonData(personData);
        }
    }
    public class rootLayoutAccessController implements AccessMainApp{
        @Override
        public void AccessController(FXMLLoader loader, MainApp mainApp) {
            RootLayoutController controller = loader.getController();
            controller.setMainApp(mainApp);
        }
    }
    public class personOverviewAccessController implements AccessMainApp{
        @Override
        public void AccessController(FXMLLoader loader, MainApp mainApp) {
            PersonOverviewController controller = loader.getController();
           controller.setMainApp(mainApp);
        }
    }
    public class personEditDialogAccessController implements AccessSpecial{
        @Override
        public PersonEditDialogController AccessController(Person person, Stage dialogStage, FXMLLoader loader){
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);
            return controller;
        }
        
    }
   
}

/* Contratos para as classes de acesso aos controllers */
interface AccessDefault{
    public abstract void AccessController(FXMLLoader loader,ObservableList<Person> personData);
}
interface AccessSpecial{
    public abstract PersonEditDialogController AccessController(Person person, Stage dialogStage, FXMLLoader loader);
}
interface AccessMainApp{
    public abstract void AccessController(FXMLLoader loader, MainApp mainApp);
}

package ch.makery.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;
import ch.makery.address.util.Alertas;
import ch.makery.address.util.Validator;


public class PersonEditDialogController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField birthdayField;


    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

    
    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setPerson(Person person) {
        this.person = person;
        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        streetField.setText(person.getStreet());
        postalCodeField.setText(Integer.toString(person.getPostalCode()));
        cityField.setText(person.getCity());
        birthdayField.setText(DateUtil.format(person.getBirthday()));
        birthdayField.setPromptText("dd.mm.yyyy");
    }

    public boolean isOkClicked() {
        return okClicked;
    }
    
    @FXML
    private void handleOk() {
        if (Validator.ValidateFields(this)) {
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setStreet(streetField.getText());
            person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            person.setCity(cityField.getText());
            person.setBirthday(DateUtil.parse(birthdayField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public TextField getFirstNameField() {
        return firstNameField;
    }

    public void setFirstNameField(TextField firstNameField) {
        this.firstNameField = firstNameField;
    }

    public TextField getLastNameField() {
        return lastNameField;
    }

    public void setLastNameField(TextField lastNameField) {
        this.lastNameField = lastNameField;
    }

    public TextField getStreetField() {
        return streetField;
    }

    public void setStreetField(TextField streetField) {
        this.streetField = streetField;
    }

    public TextField getPostalCodeField() {
        return postalCodeField;
    }

    public void setPostalCodeField(TextField postalCodeField) {
        this.postalCodeField = postalCodeField;
    }

    public TextField getCityField() {
        return cityField;
    }

    public void setCityField(TextField cityField) {
        this.cityField = cityField;
    }

    public TextField getBirthdayField() {
        return birthdayField;
    }

    public void setBirthdayField(TextField birthdayField) {
        this.birthdayField = birthdayField;
    }
    
}

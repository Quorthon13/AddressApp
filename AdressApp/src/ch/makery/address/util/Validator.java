
package ch.makery.address.util;

import ch.makery.address.view.PersonEditDialogController;

public class Validator {
    Validator(PersonEditDialogController person){
        
    }
    public static boolean ValidateFields(PersonEditDialogController person){
        String errorMessage = "";

        if (person.getFirstNameField().getText() == null || person.getFirstNameField().getText().length() == 0) {
            errorMessage += "Nome inválido!\n"; 
        }
        if (person.getLastNameField().getText() == null || person.getLastNameField().getText().length() == 0) {
            errorMessage += "Sobrenome inválido!\n"; 
        }
        if (person.getStreetField().getText() == null || person.getStreetField().getText().length() == 0) {
            errorMessage += "Rua inválida!\n"; 
        }

        if (person.getPostalCodeField().getText() == null || person.getPostalCodeField().getText().length() == 0) {
            errorMessage += "Código Postal inválido!\n"; 
        } else {
            // tenta converter o código postal em um int.
            try {
                Integer.parseInt(person.getPostalCodeField().getText());
            } catch (NumberFormatException e) {
                errorMessage += "Código Postal inválido (deve ser um inteiro)!\n"; 
            }
        }

        if (person.getCityField().getText() == null || person.getCityField().getText().length() == 0) {
            errorMessage += "Cidade inválida!\n"; 
        }

        if (person.getBirthdayField().getText() == null || person.getBirthdayField().getText().length() == 0) {
            errorMessage += "Aniversário inválido!\n";
        } else {
            if (!DateUtil.validDate(person.getBirthdayField().getText())) {
                errorMessage += "Aniversário inválido. Use o formato dd.mm.yyyy!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alertas.campoInvalido(errorMessage);
            return false;
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.makery.address.model;

import ch.makery.address.util.DateUtil;
import ch.makery.address.view.PersonEditDialogController;

/**
 *
 * @author USER
 */
public class PersonWrapper {
    public static Person PersonEditWrapper(PersonEditDialogController uPerson){
        Person person = new Person();
        
        person.setFirstName(uPerson.getFirstNameField().getText());
        person.setLastName(uPerson.getLastNameField().getText());
        person.setStreet(uPerson.getStreetField().getText());
        person.setPostalCode(Integer.parseInt(uPerson.getPostalCodeField().getText()));
        person.setCity(uPerson.getCityField().getText());
        person.setBirthday(DateUtil.parse(uPerson.getBirthdayField().getText()));
        
        return person;
    }
}

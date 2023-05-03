package com.ubb.postuniv.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ClientCardValidator {
    public void validate(ClientCard clientCard) throws ClientCardValidatorException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        StringBuilder sb = new StringBuilder();

        if(clientCard.getFirstName().equals("")){
            sb.append("The first name field cannot be empty!\n");
        }


        if(clientCard.getLastName().equals("")){
            sb.append("The last name field cannot be empty!\n");
        }

        if(clientCard.getBirthDate().before(dateFormat.parse("01.01.1920")) &&
                clientCard.getBirthDate().after(dateFormat.parse("20.03.2023"))){
            sb.append("Enter valid birth date!.\n");
        }

        if(clientCard.getRegistrationDate().before(dateFormat.parse("20.01.2001")) &&
                clientCard.getRegistrationDate().after(dateFormat.parse("20.03.2023"))){
            sb.append("Enter valid registration date!.\n");
        }
        if (sb.length() > 0) {
            throw new ClientCardValidatorException(sb.toString());
        }
    }
}

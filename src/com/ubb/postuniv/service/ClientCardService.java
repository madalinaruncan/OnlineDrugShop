package com.ubb.postuniv.service;

import com.ubb.postuniv.domain.ClientCard;
import com.ubb.postuniv.domain.ClientCardValidator;
import com.ubb.postuniv.repository.InterfaceRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientCardService {
    private InterfaceRepository<ClientCard> clientCardRepository;
    private ClientCardValidator clientCardValidator;

    public ClientCardService(InterfaceRepository<ClientCard> clientCardRepository, ClientCardValidator clientCardValidator) {
        this.clientCardRepository = clientCardRepository;
        this.clientCardValidator = clientCardValidator;
    }

    /**
     * Adds a client card to repository.
     *
     * @param id               of the client card.
     * @param firstName        of the client.
     * @param lastName         of the client.
     * @param pin              of the client.
     * @param birthDate        of the client.
     * @param registrationDate of the client card.
     * @throws Exception if the ID already exist.
     */
    public void addClientCard(int id, String firstName, String lastName, String pin, Date birthDate, Date registrationDate) throws Exception {
        ClientCard clientCard = new ClientCard(id, firstName, lastName, pin, birthDate, registrationDate);
        List<ClientCard> clientCards = this.showAllClientCards();
        for (int i = 0; i < clientCards.size(); i++) {
            ClientCard card = clientCards.get(i);
            if (card.getPin().equals(pin)) {
                throw new Exception("It look's like your PIN is associated with another account.");
            }
        }
        this.clientCardValidator.validate(clientCard);
        this.clientCardRepository.create(clientCard);
    }

    /**
     * Gets a list of all ClientCard objects.
     *
     * @return a list of all ClientCard objects.
     */
    public List<ClientCard> showAllClientCards() {
        return this.clientCardRepository.readAll();
    }

    /**
     * Gets one ClientCard object.
     *
     * @param id of the client card.
     */
    public void showOneClientCard(int id) {
        this.clientCardRepository.read(id);
    }

    /**
     * Updates a ClientCard object information.
     *
     * @param clientCard to update.
     * @throws Exception if the ID doesn't exist.
     */
    public void updateClientCard(ClientCard clientCard) throws Exception {
        this.clientCardValidator.validate(clientCard);
        this.clientCardRepository.update(clientCard);
    }

    /**
     * Delete a ClientCard object from repository.
     *
     * @param id of the ClientCard object to delete.
     * @throws Exception if the ID doesn't exist.
     */
    public void deleteClientCard(int id) throws Exception {
        this.clientCardRepository.delete(id);
    }

    /**
     * Search ClientCard object that match with user's input.
     *
     * @param text to match with ClientCard objects data.
     * @return a list with objects that have matching content.
     */
    public List<ClientCard> searchClientCard(String text) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        text = text.toLowerCase();
        List<ClientCard> clients = this.showAllClientCards();
        List<ClientCard> searched = new ArrayList<>();
        for (ClientCard client : clients) {
            String id = String.valueOf(client.getId());
            String firstName = client.getFirstName().toLowerCase();
            String lastName = client.getLastName().toLowerCase();
            String fullName = firstName + " " + lastName;
            String fullNameReversed = lastName + " " + firstName;
            String pin = client.getPin();
            String birthDate = dateFormat.format(client.getBirthDate());
            String registrationDate = dateFormat.format(client.getRegistrationDate());
            if (id.contains(text) || fullName.contains(text) || fullNameReversed.contains(text) || pin.contains(text) || birthDate.contains(text) || registrationDate.contains(text)) {
                searched.add(client);
            }
        }
        return searched;
    }
}

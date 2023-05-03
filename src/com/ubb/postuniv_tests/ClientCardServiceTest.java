package com.ubb.postuniv_tests;

import com.ubb.postuniv.domain.ClientCard;
import com.ubb.postuniv.domain.ClientCardValidator;
import com.ubb.postuniv.repository.InMemoryRepository;
import com.ubb.postuniv.repository.InterfaceRepository;
import com.ubb.postuniv.service.ClientCardService;
import org.junit.jupiter.api.Assertions;

import java.text.SimpleDateFormat;
import java.util.List;


class ClientCardServiceTest {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public void addFromService(ClientCardService clientCardService) throws Exception {

        clientCardService.addClientCard(1, "firstname1", "lastname1",
                "2970114372193", dateFormat.parse("24.01.1997"), dateFormat.parse("28.03.2023"));
        clientCardService.addClientCard(2, "firstname2", "lastname2",
                "2970114398793", dateFormat.parse("24.09.1980"), dateFormat.parse("12.10.2020"));
        clientCardService.addClientCard(3, "firstname3", "lastname3",
                "2987114330193", dateFormat.parse("16.08.2003"), dateFormat.parse("01.12.1998"));
        clientCardService.addClientCard(4, "firstname4", "lastname4",
                "2657114330193", dateFormat.parse("05.01.2000"), dateFormat.parse("31.05.2018"));
        clientCardService.addClientCard(5, "firstname1", "lastname1",
                "2970114330193", dateFormat.parse("24.01.1997"), dateFormat.parse("28.03.2023"));

    }
    @org.junit.jupiter.api.Test
    void addValidClientCard_should_sendToRepository() throws Exception {
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);

        this.addFromService(clientCardService);
        List<ClientCard> allClientCard = clientCardService.showAllClientCards();
        Assertions.assertEquals(5, allClientCard.size());
    }

    @org.junit.jupiter.api.Test
    void addClientCardWithAnAlreadyExistingId_should_raiseException() throws Exception {
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);

        this.addFromService(clientCardService);

        Assertions.assertThrows(Exception.class, () -> clientCardService.addClientCard(1, "firstname1", "lastname1",
                "2970114372193", dateFormat.parse("24.01.1997"), dateFormat.parse("28.03.2023")));
    }

    @org.junit.jupiter.api.Test
    void updateClientCard_should_updateClientCardInfo() throws Exception {
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);
        this.addFromService(clientCardService);

        ClientCard c1u = new ClientCard(1, "firstname1Updated", "lastname1",
                "2970114372193", dateFormat.parse("24.01.1997"), dateFormat.parse("28.03.2023"));
        ClientCard c2u = new ClientCard(2, "firstname2", "lastname2Updated",
                "2970114398793", dateFormat.parse("24.09.1980"), dateFormat.parse("12.10.2020"));
        ClientCard c3u = new ClientCard(3, "firstname3", "lastname3",
                "2987114000000", dateFormat.parse("16.08.2003"), dateFormat.parse("01.12.1998"));

        clientCardService.updateClientCard(c1u);
        clientCardService.updateClientCard(c2u);
        clientCardService.updateClientCard(c3u);

        List<ClientCard> allClientCard = clientCardService.showAllClientCards();
        Assertions.assertEquals(5, allClientCard.size());
    }

    @org.junit.jupiter.api.Test
    void updateClientCardWithNoMatchingId_should_raiseException() throws Exception {
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);
        this.addFromService(clientCardService);

        ClientCard c1u = new ClientCard(130, "firstname1Updated", "lastname1",
                "2970114372193", dateFormat.parse("24.01.1997"), dateFormat.parse("28.03.2023"));

        Assertions.assertThrows(Exception.class, () -> clientCardService.updateClientCard(c1u));
    }

    @org.junit.jupiter.api.Test
    void deleteValidClientCard_should_deleteClientCard() throws Exception {
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);
        this.addFromService(clientCardService);

        clientCardService.deleteClientCard(1);
        clientCardService.deleteClientCard(2);
        clientCardService.deleteClientCard(3);
        clientCardService.deleteClientCard(4);
        clientCardService.deleteClientCard(5);

        List<ClientCard> allClientCard = clientCardService.showAllClientCards();
        Assertions.assertEquals(0, allClientCard.size());
    }

    @org.junit.jupiter.api.Test
    void deleteClientCardWithNoMatchingId_should_raiseException() throws Exception {
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);
        this.addFromService(clientCardService);

        Assertions.assertThrows(Exception.class, () -> clientCardService.deleteClientCard(500));
    }

    @org.junit.jupiter.api.Test
    void searchvalidClientCard_should_returnAListWithMatchingClientCards() throws Exception {
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);
        this.addFromService(clientCardService);

        List<ClientCard> searched = clientCardService.searchClientCard("24.09.1980");

        Assertions.assertEquals(1, searched.size());
    }
}
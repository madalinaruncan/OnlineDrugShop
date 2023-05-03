package com.ubb.postuniv_tests;

import com.ubb.postuniv.domain.ClientCard;
import com.ubb.postuniv.repository.InMemoryRepository;
import com.ubb.postuniv.repository.InterfaceRepository;
import org.junit.jupiter.api.Assertions;

import java.text.SimpleDateFormat;
import java.util.List;

class InMemoryRepositoryTest {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public void populateRepository(InterfaceRepository<ClientCard> clientCardRepository) throws Exception {

        ClientCard c1 = new ClientCard(1, "firstname1", "lastname1",
                "2970114372193", dateFormat.parse("24.01.1997"), dateFormat.parse("28.03.2023"));
        ClientCard c2 = new ClientCard(2, "firstname2", "lastname2",
                "2970114398793", dateFormat.parse("24.09.1980"), dateFormat.parse("12.10.2020"));
        ClientCard c3 = new ClientCard(3, "firstname3", "lastname3",
                "2987114330193", dateFormat.parse("16.08.2003"), dateFormat.parse("01.12.1998"));
        ClientCard c4 = new ClientCard(4, "firstname4", "lastname4",
                "2657114330193", dateFormat.parse("05.01.2000"), dateFormat.parse("31.05.2018"));
        ClientCard c5 = new ClientCard(5, "firstname1", "lastname1",
                "2970114330193", dateFormat.parse("24.01.1997"), dateFormat.parse("28.03.2023"));

        clientCardRepository.create(c1);
        clientCardRepository.create(c2);
        clientCardRepository.create(c3);
        clientCardRepository.create(c4);
        clientCardRepository.create(c5);
    }

    @org.junit.jupiter.api.Test
    void validAddClientCard_should_saveToRepository() throws Exception {
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        this.populateRepository(clientCardRepository);
        List<ClientCard> allClientCard = clientCardRepository.readAll();
        Assertions.assertEquals(5, allClientCard.size());
    }

    @org.junit.jupiter.api.Test
    void addsWithDuplicateId_should_raiseExceptionInRepository() throws Exception {
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        this.populateRepository(clientCardRepository);

        ClientCard c3 = new ClientCard(1, "firstname3", "lastname3",
                "2987114330193", dateFormat.parse("16.08.2003"), dateFormat.parse("01.12.1998"));

        Assertions.assertThrows(Exception.class, () -> clientCardRepository.create(c3));
    }

    @org.junit.jupiter.api.Test
    void updateClientCard_should_updateClientCardInRepository() throws Exception {
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        this.populateRepository(clientCardRepository);

        ClientCard c1u = new ClientCard(1, "firstname1Updated", "lastname1",
                "2970114372193", dateFormat.parse("24.01.1997"), dateFormat.parse("28.03.2023"));
        ClientCard c2u = new ClientCard(2, "firstname2", "lastname2Updated",
                "2970114398793", dateFormat.parse("24.09.1980"), dateFormat.parse("12.10.2020"));
        ClientCard c3u = new ClientCard(3, "firstname3", "lastname3",
                "2987114000000", dateFormat.parse("16.08.2003"), dateFormat.parse("01.12.1998"));
        clientCardRepository.update(c1u);
        clientCardRepository.update(c2u);
        clientCardRepository.update(c3u);

        Assertions.assertNotNull(clientCardRepository.read(1));
        Assertions.assertNotNull(clientCardRepository.read(2));
        Assertions.assertNotNull(clientCardRepository.read(3));
    }

    @org.junit.jupiter.api.Test
    void updatesAnClientCardWithNoMatchingId_should_raiseExceptionInRepository() throws Exception {
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        this.populateRepository(clientCardRepository);

        ClientCard c80u = new ClientCard(80, "firstname1Updated", "lastname1",
                "2970114372193", dateFormat.parse("24.01.1997"), dateFormat.parse("28.03.2023"));

        Assertions.assertThrows(Exception.class, () -> clientCardRepository.update(c80u));
    }

    @org.junit.jupiter.api.Test
    void deleteValidClientCard_should_deleteEntryFromRepository() throws Exception {
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        this.populateRepository(clientCardRepository);

        clientCardRepository.delete(1);
        clientCardRepository.delete(2);
        clientCardRepository.delete(3);
        clientCardRepository.delete(4);
        clientCardRepository.delete(5);

        List<ClientCard> allClientCard = clientCardRepository.readAll();
        Assertions.assertEquals(0, allClientCard.size());
    }

    @org.junit.jupiter.api.Test
    void deleteClientCardWithNoMatchingId_should_raiseExceptionInRepository() throws Exception {
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        this.populateRepository(clientCardRepository);

        Assertions.assertThrows(Exception.class, () -> clientCardRepository.delete(100));
    }
}
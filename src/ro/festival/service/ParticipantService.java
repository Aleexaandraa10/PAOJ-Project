package ro.festival.service;

import ro.festival.dao.ParticipantDAO;
import ro.festival.model.Participant;

import java.util.List;

public class ParticipantService {
    private static ParticipantService instance;

    private ParticipantService() {}

    public static ParticipantService getInstance() {
        if (instance == null) {
            instance = new ParticipantService();
        }
        return instance;
    }

    public void addParticipant(Participant p) {
        ParticipantDAO.getInstance().create(p);
    }

    public Participant getParticipant(int id) {
        return ParticipantDAO.getInstance().read(id).orElse(null);
    }

    public List<Participant> getAllParticipants() {
        return ParticipantDAO.getInstance().readAll();
    }

    public void updateParticipant(Participant p) {
        ParticipantDAO.getInstance().update(p);
    }

    public void deleteParticipant(int id) {
        ParticipantDAO.getInstance().delete(id);
    }
}

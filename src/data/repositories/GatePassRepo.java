package data.repositories;

import data.models.GatePass;
import java.util.List;

public interface GatePassRepo {
    List<GatePass> findAll();
    GatePass findById(String id);
    GatePass save(GatePass pass);
    void delete(GatePass pass);
    void deleteById(int id);
    void deleteByObject(GatePass pass);
    void deleteAll();
}
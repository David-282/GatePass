package data.repositories;

import data.models.GatePass;
import java.util.List;

public interface GatePassRepository {
    List<GatePass> findAll();
    GatePass findById(String id);
    GatePass save(GatePass pass);
    void delete(GatePass pass);
    void deleteById(String id);
    void deleteAll();

    GatePass findByCode(String code);
}
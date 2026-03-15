package data.repositories;
import data.models.Visitor;
import java.util.List;

public interface VisitorRepository {
    List<Visitor> findAll();
    Visitor findById(String Id);
    Visitor save(Visitor visitor);
    void delete(Visitor visitor);
    void deleteById(String Id);
    void deleteAll();
}
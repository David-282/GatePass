package data.repositories;
import data.models.Visitor;
import utils.RandomCodeGenerator;

import java.util.ArrayList;
import java.util.List;

public class Visitors implements VisitorRepository {
    private List<Visitor> visitors = new ArrayList<>();

    @Override
    public List<Visitor> findAll() {
        return new ArrayList<>(visitors);
    }



    @Override
    public Visitor findById(String id) {
        for (Visitor visitor : visitors) {
            if (visitor.getId().equals(id)) {
                return visitor;
            }
        }
        return null;
    }

    @Override
    public Visitor save(Visitor visitor) {
        Visitor newVisitor = findById (visitor.getId());

        if (newVisitor == null){
            visitor.setId(RandomCodeGenerator.codeGenerator());
            visitors.add(visitor);
        }
        return visitor;
    }


    @Override
    public void delete(Visitor visitor) {
        visitors.remove(visitor);
    }



    @Override
    public void deleteById(String id) {
        Visitor visitor = findById(id);
        if (visitor != null) {
            visitors.remove(visitor);
        }
    }


    @Override
    public void deleteAll() {
        visitors.clear();
    }

    public int count() {
        return visitors.size();
    }
}
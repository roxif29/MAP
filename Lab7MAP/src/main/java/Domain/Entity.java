package Domain;

public class Entity<ID> {

    protected ID id;

    public Entity (ID id){
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
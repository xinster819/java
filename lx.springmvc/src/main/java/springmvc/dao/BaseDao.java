package springmvc.dao;

public interface BaseDao<E> {

    public boolean insert(E one);

    public boolean update(E one);

    public boolean delete(E one);

    public E byId(long id);
}

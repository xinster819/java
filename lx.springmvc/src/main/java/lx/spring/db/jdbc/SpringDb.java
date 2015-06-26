package lx.spring.db.jdbc;

import java.util.List;

import lx.springmvc.vo.LabsStat;

public interface SpringDb {
    public void insert();

    public List<LabsStat> select(int offset, int limit);
}

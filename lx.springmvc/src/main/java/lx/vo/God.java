package lx.vo;

public class God {

    private String name = "xinster";

    private int id;

    public God(int id) {
        this.id = id;
    }
    
    public void print() {
        System.out.println(God.this.id);
    }
    
    public void whoIsGod() {
        System.out.println(id + "-" + name);
    }
}

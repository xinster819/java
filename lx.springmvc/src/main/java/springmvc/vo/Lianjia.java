package springmvc.vo;

@Table(value = "lianjia")
public class Lianjia {
    @ColumnProperty(value = "id", isIncrement = true, isPk = true, needUpdate = false)
    private String id;
    @ColumnProperty(value = "price")
    private int price;
    @ColumnProperty(value = "plot_name")
    private String plotName;
    @ColumnProperty(value = "structure")
    private String structure;
    @ColumnProperty(value = "area")
    private double area;
    @ColumnProperty(value = "direction")
    private String direction;
    @ColumnProperty(value = "floor")
    private String floor;
    @ColumnProperty(value = "construct_year")
    private int constructYear;
    @ColumnProperty(value = "url", needUpdate = false)
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPlotName() {
        return plotName;
    }

    public void setPlotName(String plotName) {
        this.plotName = plotName;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public int getConstructYear() {
        return constructYear;
    }

    public void setConstructYear(int constructYear) {
        this.constructYear = constructYear;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}

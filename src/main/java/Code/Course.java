package Code;

public class Course {
    private int id;
    private String department;
    private int catalog;
    public Course(int id, String department, int catalog){
        this.id = id;
        this.department = department;
        this.catalog = catalog;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getCatalog() {
        return catalog;
    }

    public void setCatalog(int catalog) {
        this.catalog = catalog;
    }
}

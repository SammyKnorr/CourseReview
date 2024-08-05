package Code;

public class Review {
    private int id;
    private Student student;
    private Course course;
    private String message;
    private int rating;
    public Review(int id, Student student, Course course, String message, int rating) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.message = message;
        this.rating = rating;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

package vuv;

import java.util.List;

public class StartData {

  public final List<Student> students;

  public final List<Course> courses;

  public StartData(List<Student> students, List<Course> courses) {
    this.students = students;
    this.courses = courses;
  }

  @Override
  public String toString() {
    return "StartData[" + students + ", " + courses + "]";
  }

}
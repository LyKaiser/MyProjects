package vuv;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student {

  public final String name;

  public final List<Course> courses;

  public Student(String name, List<Course> courses) {
    this.name = name;
    this.courses = new ArrayList<>(courses);
  }

  public String getName() {
    return name;
  }

  public List<Course> getCourses() {
    return courses;
  }

  @Override
  public String toString() {
    return "Student[" + name + ", " + courses + "]";
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Student)) {
      return false;
    }

    return Objects.equals(name, ((Student) other).name) && Objects.equals(courses, ((Student) other).courses);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, courses);
  }

}
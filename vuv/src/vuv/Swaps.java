package vuv;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Swaps {

  public final ArrayList<Student> students;

  public final ArrayList<Course> courses;

  public Swaps(List<Student> students, List<Course> courses) {
    if (students.size() != courses.size()) {
      throw new IllegalArgumentException("The number of students was different from the number of courses");
    }

    this.students = new ArrayList<>(students);
    this.courses = new ArrayList<>(courses);
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Swaps)) {
      return false;
    }
    Swaps s = (Swaps) other;
    return Objects.equals(students, s.students) && Objects.equals(courses, s.courses);
  }

  @Override
  public int hashCode() {
    return Objects.hash(students, courses);
  }

  @Override
  public String toString() {
    return "Swap[" + students + ", " + courses + "]";
  }

}
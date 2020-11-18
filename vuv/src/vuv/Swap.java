package vuv;

import java.util.Objects;

public class Swap {

  public final Student studentA;

  public final Student studentB;

  public final Course courseA;

  public final Course courseB;

  public Swap(Student sa, Student sb, Course ca, Course cb) {
    this.studentA = sa;
    this.studentB = sb;
    this.courseA = ca;
    this.courseB = cb;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Swap)) {
      return false;
    }
    Swap s = (Swap) other;
    return Objects.equals(studentA, s.studentA) &&
            Objects.equals(studentB, s.studentB) &&
            Objects.equals(courseA, s.courseA) &&
            Objects.equals(courseB, s.courseB);
  }

  @Override
  public int hashCode() {
    return Objects.hash(studentA, studentB, courseA, courseB);
  }

  @Override
  public String toString() {
    return "Swap[" + studentA + ", " + studentB + ", " + courseA + ", " + courseB + "]";
  }

}
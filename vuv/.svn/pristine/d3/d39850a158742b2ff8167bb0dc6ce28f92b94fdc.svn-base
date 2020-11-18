package vuv;

import java.util.Objects;

public class Course {

  public final String name;

  public final int capacity;

  public Course(String name, int capacity) {
    this.name = name;
    this.capacity = capacity;
  }

  public String getName() {
    return name;
  }

  public int getCapacity() {
    return capacity;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Course)) {
      return false;
    }

    return Objects.equals(name, ((Course) other).name) && Objects.equals(capacity, ((Course) other).capacity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, capacity);
  }

  @Override
  public String toString() {
    return "Course[" + name + ", " + capacity + "]";
  }

}
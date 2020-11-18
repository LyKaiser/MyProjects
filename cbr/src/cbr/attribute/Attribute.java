package cbr.attribute;

public interface Attribute {

  <T> T accept(AttributeVisitor<T> visitor);

}

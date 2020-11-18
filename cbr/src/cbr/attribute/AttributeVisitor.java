package cbr.attribute;

import java.util.function.Function;

public interface AttributeVisitor<T> {

  T visit(NumAttribute attribute);

  T visit(ChoiceAttribute attribute);

  static <T> AttributeVisitor<T> create(Function<ChoiceAttribute, T> sdf, Function<NumAttribute, T> idf) {
    return new AttributeVisitor<>() {

      @Override
      public T visit(ChoiceAttribute attribute) {
        return sdf.apply(attribute);
      }

      @Override
      public T visit(NumAttribute attribute) {
        return idf.apply(attribute);
      }
    };
  }

  static <T> T visit(Attribute attribute, Function<ChoiceAttribute, T> sdf, Function<NumAttribute, T> idf) {
    return attribute.accept(create(sdf, idf));
  }

}

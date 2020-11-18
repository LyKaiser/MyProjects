package naive_bayes.value;

import java.util.function.Function;

public interface ValueVisitor<T> {
  
  T visit(ChoiceValue value);

  T visit(NumValue value);

  static <T> ValueVisitor<T> create(
          Function<ChoiceValue, T> sdf,
          Function<NumValue, T> idf) {
    return new ValueVisitor<T>() {

      @Override
      public T visit(ChoiceValue value) {
        return sdf.apply(value);
      }

      @Override
      public T visit(NumValue value) {
        return idf.apply(value);
      }
    };
  }

  static <T> T visit(Value value, Function<ChoiceValue, T> sdf, Function<NumValue, T> idf) {
    return value.accept(create(sdf, idf));
  }
}

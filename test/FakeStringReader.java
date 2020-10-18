import java.io.StringReader;


/**
 * A class to represent a fake string reader that has null field.
 */
public class FakeStringReader extends StringReader {

  /**
   * Creates a new string reader.
   *
   * @param s String providing the character stream.
   */
  public FakeStringReader(String s) {
    super(null);
  }
}

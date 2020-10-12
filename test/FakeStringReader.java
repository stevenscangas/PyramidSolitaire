import java.io.StringReader;

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

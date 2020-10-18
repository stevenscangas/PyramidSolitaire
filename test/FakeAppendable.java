import java.io.IOException;

/**
 * Appendable derived class that always throws exceptions. To be used with testing.
 */
public class FakeAppendable implements Appendable {


  /**
   * Appends the specified character sequence to this {@code Appendable}.
   */
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("Fake appendable.");
  }

  /**
   * Appends a subsequence of the specified character sequence to this {@code Appendable}.
   */
  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException("Fake appendable.");
  }

  /**
   * Appends the specified character to this {@code Appendable}.
   *
   * @param c The character to append
   * @return A reference to this {@code Appendable}
   * @throws IOException If an I/O error occurs
   */
  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException("Fake appendable.");
  }
}

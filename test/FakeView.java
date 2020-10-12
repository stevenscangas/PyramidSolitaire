import cs3500.pyramidsolitaire.view.PyramidSolitaireView;
import java.io.IOException;

public class FakeView implements PyramidSolitaireView {

  Appendable out;

  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   *
   * @throws IOException if the rendering fails for some reason
   */
  @Override
  public void render()  {
    try {
      out.append("");
    } catch (IOException e) {
      throw new IllegalStateException("Cannot append to this appendable");
    }
  }
}

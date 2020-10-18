package cs3500.pyramidsolitaire.view;

import java.io.IOException;


/**
 * View Interface that represents the view of a pyramid solitaire.
 */
public interface PyramidSolitaireView {

  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   *
   * @throws IOException if the rendering fails for some reason
   */
  void render() throws IOException;
}
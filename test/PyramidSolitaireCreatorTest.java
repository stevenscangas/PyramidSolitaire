import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.GameStatus;
import cs3500.pyramidsolitaire.model.hw04.MultiPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import org.junit.Test;


/**
 * This class is used for testing purposes.
 */
public class PyramidSolitaireCreatorTest {

  PyramidSolitaireCreator creator = new PyramidSolitaireCreator();


  // create basic model
  @Test
  public void testCreateBasic() {
    assertTrue(new BasicPyramidSolitaire().isEqual( creator.create(GameType.BASIC)));
  }

  // create relaxed model
  @Test
  public void testCreateRelaxed() {
    assertTrue(new RelaxedPyramidSolitaire().isEqual( creator.create(GameType.RELAXED)));
  }

  // create multipyramid model
  @Test
  public void testCreateMulti() {
    assertTrue(new MultiPyramidSolitaire().isEqual( creator.create(GameType.MULTIPYRAMID)));
  }

  // is there a way to test for bad enums?

}
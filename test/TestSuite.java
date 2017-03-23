
/**
 * Runs all JUnit tests
 * 
 * @author Chris Bentley
 */
import games.letitride.GameTest;
import games.letitride.PayoutTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import cards.CardTest;
import cards.DeckTest;
import cards.HandTest;
import cards.HandValueTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	CardTest.class,
	DeckTest.class,
	HandTest.class,
	HandValueTest.class,
	GameTest.class,
	PayoutTest.class
})

public class TestSuite {
  // the class remains empty,
  // used only as a holder for the above annotations
}

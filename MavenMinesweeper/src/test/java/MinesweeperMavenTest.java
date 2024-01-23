import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.*;

import java.util.Vector;

import static org.example.MinesweeperMaven.*;

public class MinesweeperMavenTest {

    @Test
    public void testPlacesNumber() {
        Assertions.assertEquals(places((byte) 60, 16, 30).size(), 60, "Wrong number of mines");
    }

    @Test
    public void testGetIndex() {
        String[] testArray = {"A", "B", "C"};
        Assertions.assertEquals(getIndex(testArray, "A"), 0, "Wrong index for A");
        Assertions.assertEquals(getIndex(testArray, "B"), 1, "Wrong index for B");
        Assertions.assertEquals(getIndex(testArray, "C"), 2, "Wrong index for C");
    }

    @Test
    public void testSetBoardDims() {
        Assertions.assertEquals(setBoardDims('S')[0], 12, "Small board wrong height");
        Assertions.assertEquals(setBoardDims('S')[1], 20, "Small board wrong width");
        Assertions.assertEquals(setBoardDims('M')[0], 16, "Medium board wrong height");
        Assertions.assertEquals(setBoardDims('M')[1], 30, "Medium board wrong width");
        Assertions.assertEquals(setBoardDims('L')[0], 20, "Large board wrong height");
        Assertions.assertEquals(setBoardDims('L')[1], 40, "Large board wrong width");
    }

    @Test
    public void testPLaceMines() {
        Vector<Integer> testVector = new Vector<>();
        testVector.addElement(1);
        Assertions.assertEquals(placeMinesInGrid(testVector, 1, 1, 2).getValue(0, 0), 1, "No mine when should be");
        Assertions.assertEquals(placeMinesInGrid(testVector, 1, 1, 2).getValue(0, 1), 0, "Mine when shouldn't be");
    }

}

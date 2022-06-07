package external.to_implement;
/*
import org.junit.Assert;
import org.junit.Test;
import patchwork.core.model.timeboard.TimeBoard;
import patchwork.core.model.tokens.TimeToken;

public class TimeBoardTest {
    @Test
    public void testAdvanceTimeToken() {
        TimeBoard timeBoard = new TimeBoard(3);
        TimeToken t1 = new TimeToken();
        TimeToken t2 = new TimeToken();

        // Initial positions of both tokens should be 0.
        Assert.assertEquals(0, t1.getPosition());
        Assert.assertEquals(0, t2.getPosition());

        // Advancing T1 will place it directly in front of T2
        timeBoard.advanceTimeToken(t1, t2);
        Assert.assertEquals(1, t1.getPosition());
        Assert.assertEquals(0, t2.getPosition());

        // T1 is in front of T2, advancing it will not change its position.
        timeBoard.advanceTimeToken(t1, t2);
        Assert.assertEquals(1, t1.getPosition());
        Assert.assertEquals(0, t2.getPosition());

        // T2 is behind T1, advancing it should place it in front of T1.
        timeBoard.advanceTimeToken(t2, t1);
        Assert.assertEquals(1, t1.getPosition());
        Assert.assertEquals(2, t2.getPosition());

        // T2 reached the end of the board, advancing T1 will place it at the end of the board.
        timeBoard.advanceTimeToken(t1, t2);
        Assert.assertEquals(2, t1.getPosition());
        Assert.assertEquals(2, t2.getPosition());
    }
}
*/
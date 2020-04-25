package spliter;

import entity.Brick;
import wrapper.Cell;

import java.util.List;
import java.util.Map;

public interface Splitter {
    Map<Brick, List<List<Cell>>> divideOnPart();
}

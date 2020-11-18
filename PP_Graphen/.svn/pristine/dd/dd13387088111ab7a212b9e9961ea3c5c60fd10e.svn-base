package de.jpp.algorithm;

import com.sun.javafx.property.adapter.PropertyDescriptor;
import de.jpp.algorithm.interfaces.NodeStatus;
import de.jpp.algorithm.interfaces.SearchStopStrategy;

import java.util.List;
import java.util.Map;


public class StartToDestStrategy<N> implements SearchStopStrategy<N> {

    private N dest;

    public StartToDestStrategy(N dest) {
        this.dest = dest;
    }

    @Override
    public boolean stopSearch(N lastClosedNode) {
        if (dest==lastClosedNode){
            return true;
        }else return false;
    }

    /**
     * Returns the destination node of this search
     *
     * @Returns the destination node of this search
     */
    public N getDest() {
        return dest;
    }

}

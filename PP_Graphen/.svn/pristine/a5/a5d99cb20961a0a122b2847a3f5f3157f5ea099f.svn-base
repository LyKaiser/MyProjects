package de.jpp.algorithm;

import de.jpp.algorithm.interfaces.*;
import de.jpp.factory.SearchStopFactory;
import de.jpp.model.interfaces.Edge;
import de.jpp.model.interfaces.WeightedGraph;

import java.util.*;


public class AStarSearch<N, A, G extends WeightedGraph<N, A>> implements SearchAlgorithm<N, A, G> {

    private G graph;
    private N start;
    private N dest;
    private EstimationFunction<N> estimationFunction;
    private boolean stopped = false;
    private SearchResultImpl<N, A> result = new SearchResultImpl<>();
    Map<N, Double> open = new HashMap<>();
    Set<N> close = new HashSet<>();

    public AStarSearch(G graph, N start, N dest, EstimationFunction<N> estimationFunction) {
        this.graph = graph;
        this.start = start;
        this.dest = dest;
        this.estimationFunction = estimationFunction;
    }

    @Override
    public SearchResult<N, A> findPaths(SearchStopStrategy<N> type) {
        Collection<N> nodes = graph.getNodes();
        for (N node : nodes) {
            result.statusMap.put(node, NodeStatus.UNKOWN);
        }
        open.put(start, 0.0);
        result.setOpen(start);
        NodeInformation<N, A> info = new NodeInformation<>(null, 0.0);
        result.infoMap.put(start, info);

        while (!open.isEmpty()) {

            N lowest = null;
            double low = Double.MAX_VALUE;
            for (N no : open.keySet()) {
                if (open.get(no) < low) {
                    low = open.get(no);
                    lowest = no;
                }
            }

            result.setClosed(lowest);
            if (lowest == dest) {
                break;
            }
            open.remove(lowest);
            close.add(lowest);
            if (lowest.equals(dest)) {
                stop();
            }
            if (stopped){
                break;
            }
            expand(lowest);
        }

        return result;
    }

    public void expand(N node) {
        //System.out.println(node);

        for (Edge<N, A> neig : graph.getNeighbours(node)) {
            if (close.contains(neig.getDestination())) {
                continue;
            }
            if (neig.getAnnotation().isPresent()) {
                double dist = graph.getDistance(neig);
                dist += result.infoMap.get(node).getDistance();
                NodeInformation<N, A> info = new NodeInformation<>(neig, dist);
                result.infoMap.put(neig.getDestination(), info);
                if (open.containsKey(neig.getDestination()) && dist >= result.infoMap.get(node).getDistance()) {
                    continue;
                }
                double fValue = dist + getEstimatedDistance(neig.getDestination(), dest);
                result.setOpen(neig.getDestination());
                if (open.containsKey(neig.getDestination())){
                    open.replace(neig.getDestination(), fValue);
                }else open.put(neig.getDestination(), fValue);
            } else stop();
            break;
        }
    }

    @Override
    public SearchResult<N, A> findAllPaths() {
        return findPaths(new SearchStopFactory().expandAllNodes());
    }

    @Override
    public ObservableSearchResult<N, A> getSearchResult() {
        return result;
    }

    @Override
    public N getStart() {
        return start;
    }

    @Override
    public G getGraph() {
        return graph;
    }

    @Override
    public void stop() {
        stopped = true;
    }

    public double getEstimatedDistance(N node, N destination) {
        return estimationFunction.getEstimatedDistance(node, destination);
    }
}

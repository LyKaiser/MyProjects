package de.jpp.algorithm;


import de.jpp.algorithm.interfaces.*;
import de.jpp.factory.SearchStopFactory;
import de.jpp.model.interfaces.Edge;
import de.jpp.model.interfaces.Graph;

import java.util.Collection;
import java.util.LinkedList;


public class BreadthFirstSearch<N, A, G extends Graph<N, A>> implements SearchAlgorithm<N, A, G> {
    private boolean stopped = false;
    private G graph;
    private N start;
    private SearchResultImpl<N, A> result = new SearchResultImpl<>();
    private LinkedList<N> que = new LinkedList<>();
    private double dist = 0;
    double vergl = Double.MAX_VALUE;

    public BreadthFirstSearch(G graph, N start) {
        this.graph = graph;
        this.start = start;
    }

    @Override
    public SearchResult<N, A> findPaths(SearchStopStrategy<N> type) {
        Collection<N> nodes = graph.getNodes();
        for (N node : nodes) {
            result.statusMap.put(node, NodeStatus.UNKOWN);
        }
        result.setOpen(start);
        que.add(start);
        while (!que.isEmpty()) {

                N n = que.getFirst();
                que.remove(n);
                for (Edge<N, A> edge : graph.getNeighbours(n)) {
                    if (result.getNodeStatus(edge.getDestination()).equals(NodeStatus.UNKOWN)) {
                        result.setOpen(edge.getDestination());
                        double anno;
                        if (edge.getAnnotation().isPresent()){
                            anno=(double)edge.getAnnotation().get();
                        }else anno=1;
                        NodeInformation<N, A> info = new NodeInformation<>(edge, vergl + 1);
                        vergl += info.getDistance();
                        result.infoMap.put(edge.getDestination(), info);
                        que.addLast(edge.getDestination());
                        if (type.stopSearch(edge.getDestination())){
                            stop();
                        }
                    }
                }
                result.setClosed(n);
                if (type.stopSearch(n)){
                    stop();
                }
                if (stopped){
                    break;
                }

        }
        return result;
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
        stopped=true;

    }
}

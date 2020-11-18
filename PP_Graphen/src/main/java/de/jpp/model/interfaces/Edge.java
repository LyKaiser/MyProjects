package de.jpp.model.interfaces;

import java.util.Objects;
import java.util.Optional;

public class Edge<N, A> {

    N start;
    N dest;
    Optional<A>annotation;
    /**
     * Creates a new edge with the specified start node, destination node and annotation
     *
     * @param start      the start node
     * @param dest       the destination node
     * @param annotation the annotation
     */
    public Edge(N start, N dest, Optional<A> annotation) {
        this.start=start;
        this.dest=dest;
        this.annotation=annotation;
    }

    /**
     * Returns the start node of this edge
     *
     * @return the start node of this edge
     */
    public N getStart() {

        return start;
    }

    /**
     * Returns the destination node of this edge
     *
     * @return the destination node of this edge
     */
    public N getDestination() {

        return dest;
    }

    /**
     * Returns the annotation of this edge
     *
     * @return the annotation of this edge
     */
    public Optional<A> getAnnotation() {
        if (annotation.isPresent()){
            return annotation;
        }else return Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge<?, ?> edge = (Edge<?, ?>) o;
        return Objects.equals(start, edge.start) &&
                Objects.equals(dest, edge.dest) &&
                Objects.equals(annotation, edge.annotation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, dest, annotation);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "start=" + start +
                ", dest=" + dest +
                ", annotation=" + annotation +
                '}';
    }
}

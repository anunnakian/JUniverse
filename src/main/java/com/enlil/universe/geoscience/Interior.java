package com.enlil.universe.geoscience;

import java.util.List;

/**
 * The inside of a planet: concentric layers of uniform density, from the center out.
 */
public class Interior {

    private final List<InternalLayer> layers;

    /**
     * @param layers from the center out, with strictly increasing outer radii
     */
    public Interior(List<InternalLayer> layers) {
        if (layers.isEmpty()) {
            throw new IllegalArgumentException("an interior needs at least one layer");
        }
        for (int i = 1; i < layers.size(); i++) {
            if (layers.get(i).getOuterRadiusMeters() <= layers.get(i - 1).getOuterRadiusMeters()) {
                throw new IllegalArgumentException("layers must go outwards, with increasing radii");
            }
        }
        this.layers = List.copyOf(layers);
    }

    /** Layers from the center out. */
    public List<InternalLayer> getLayers() {
        return layers;
    }

    /** Radius of the surface, the outer radius of the last layer, in meters. */
    public double radiusMeters() {
        return layers.get(layers.size() - 1).getOuterRadiusMeters();
    }

    /**
     * The layer found at a given depth below the surface; a boundary belongs to the layer below it.
     *
     * @param depthMeters depth below the surface, in meters, between 0 and the radius
     */
    public InternalLayer layerAt(double depthMeters) {
        if (depthMeters < 0 || depthMeters > radiusMeters()) {
            throw new IllegalArgumentException("depth must be between the surface and the center");
        }
        double radius = radiusMeters() - depthMeters;
        return layers.stream()
                .filter(layer -> radius <= layer.getOuterRadiusMeters())
                .findFirst()
                .orElseThrow();
    }

    /** Distance from the center to the bottom of a layer, in meters; 0 for the innermost one. */
    public double innerRadiusMeters(InternalLayer layer) {
        int index = indexOf(layer);
        return index == 0 ? 0 : layers.get(index - 1).getOuterRadiusMeters();
    }

    /** Mass of a layer, its density times the volume of its shell, in kg. */
    public double layerMassKg(InternalLayer layer) {
        double outer = layer.getOuterRadiusMeters();
        double inner = innerRadiusMeters(layer);
        return layer.getDensityKgPerCubicMeter() * 4.0 / 3 * Math.PI * (outer * outer * outer - inner * inner * inner);
    }

    /** Total mass of the layers, in kg. */
    public double massKg() {
        return layers.stream().mapToDouble(this::layerMassKg).sum();
    }

    private int indexOf(InternalLayer layer) {
        int index = layers.indexOf(layer);
        if (index < 0) {
            throw new IllegalArgumentException(layer.getName() + " is not a layer of this interior");
        }
        return index;
    }
}

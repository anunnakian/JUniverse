package com.enlil.universe.civilization.polity;

/** How a polity shares power between its centre and its regions. */
public enum TerritorialOrganization {
    /** One central government holds sovereignty; regions only hold the powers it delegates (France, Japan). */
    UNITARY,
    /** Sovereignty is constitutionally split between a central government and states (United States, Germany). */
    FEDERAL,
    /** Sovereign member states delegate a few powers to a common body they can leave. */
    CONFEDERAL
}

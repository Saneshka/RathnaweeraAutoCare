package com.heavenscode.rac.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class VehiclebrandmodelAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVehiclebrandmodelAllPropertiesEquals(Vehiclebrandmodel expected, Vehiclebrandmodel actual) {
        assertVehiclebrandmodelAutoGeneratedPropertiesEquals(expected, actual);
        assertVehiclebrandmodelAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVehiclebrandmodelAllUpdatablePropertiesEquals(Vehiclebrandmodel expected, Vehiclebrandmodel actual) {
        assertVehiclebrandmodelUpdatableFieldsEquals(expected, actual);
        assertVehiclebrandmodelUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVehiclebrandmodelAutoGeneratedPropertiesEquals(Vehiclebrandmodel expected, Vehiclebrandmodel actual) {
        assertThat(expected)
            .as("Verify Vehiclebrandmodel auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVehiclebrandmodelUpdatableFieldsEquals(Vehiclebrandmodel expected, Vehiclebrandmodel actual) {
        assertThat(expected)
            .as("Verify Vehiclebrandmodel relevant properties")
            .satisfies(e -> assertThat(e.getBrandid()).as("check brandid").isEqualTo(actual.getBrandid()))
            .satisfies(e -> assertThat(e.getBrandname()).as("check brandname").isEqualTo(actual.getBrandname()))
            .satisfies(e -> assertThat(e.getModel()).as("check model").isEqualTo(actual.getModel()))
            .satisfies(e -> assertThat(e.getLmu()).as("check lmu").isEqualTo(actual.getLmu()))
            .satisfies(e -> assertThat(e.getLmd()).as("check lmd").isEqualTo(actual.getLmd()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVehiclebrandmodelUpdatableRelationshipsEquals(Vehiclebrandmodel expected, Vehiclebrandmodel actual) {}
}
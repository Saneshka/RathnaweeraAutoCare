package com.heavenscode.rac.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AutocarecancelitemoptAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAutocarecancelitemoptAllPropertiesEquals(Autocarecancelitemopt expected, Autocarecancelitemopt actual) {
        assertAutocarecancelitemoptAutoGeneratedPropertiesEquals(expected, actual);
        assertAutocarecancelitemoptAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAutocarecancelitemoptAllUpdatablePropertiesEquals(
        Autocarecancelitemopt expected,
        Autocarecancelitemopt actual
    ) {
        assertAutocarecancelitemoptUpdatableFieldsEquals(expected, actual);
        assertAutocarecancelitemoptUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAutocarecancelitemoptAutoGeneratedPropertiesEquals(
        Autocarecancelitemopt expected,
        Autocarecancelitemopt actual
    ) {
        assertThat(expected)
            .as("Verify Autocarecancelitemopt auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAutocarecancelitemoptUpdatableFieldsEquals(Autocarecancelitemopt expected, Autocarecancelitemopt actual) {
        assertThat(expected)
            .as("Verify Autocarecancelitemopt relevant properties")
            .satisfies(e -> assertThat(e.getCanceloption()).as("check canceloption").isEqualTo(actual.getCanceloption()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAutocarecancelitemoptUpdatableRelationshipsEquals(
        Autocarecancelitemopt expected,
        Autocarecancelitemopt actual
    ) {}
}

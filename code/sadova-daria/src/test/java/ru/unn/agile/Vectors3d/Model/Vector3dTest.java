package ru.unn.agile.vectors.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class Vector3dTest {
    private final double delta = 1e-6;

    @Test
    public void canCreateVector3d() {
        Vector3d vector = new Vector3d(1, 1, 1);
        assertNotNull(vector);
    }

    @Test
    public void canCreateNegativeVector3d() {
        Vector3d vector = new Vector3d(-1, 2, -1);
        assertNotNull(vector);
    }
    @Test
    public void canCreateVector3dWithZeroValues() {
        Vector3d vector = new Vector3d(0, 0, 0);
        assertNotNull(vector);
    }

    @Test
    public void canLengthBeFromZeroVector() {
        Vector3d vector = new Vector3d(0, 0, 0);
        double len = vector.vectorLength();
        assertEquals(0, len, delta);
    }
    @Test
    public void canFindLength() {
        Vector3d vector = new Vector3d(0, 3, 4);
        double len = vector.vectorLength();
        assertEquals(5, len, delta);
    }

    @Test
    public void canNormalizeZeroVector() {
        Vector3d vector = new Vector3d(0, 0, 0);
        Vector3d normVector = vector.normalize();
        assertTrue(normVector.equalComplitely(vector));
    }

    @Test
    public void canFindDotProduct() {
        Vector3d first = new Vector3d(1, 2, 3);
        Vector3d second = new Vector3d(4, 5, 6);
        double dotProd = first.dotProduct(second);
        assertEquals(32, dotProd, delta);
    }
    @Test
    public void canFindFalseDotProduct() {
        Vector3d first = new Vector3d(1, 2, 3);
        Vector3d second = new Vector3d(4, 5, 6);
        double dotProd = first.dotProduct(second);
        assertFalse(33 == dotProd);
    }

    @Test
    public void canFindDotProductWith90DegreesBetweenThem() {
        Vector3d first = new Vector3d(1, 2, 3);
        Vector3d second = new Vector3d(1, 4, -3);
        double dotProd = first.dotProduct(second);
        assertTrue(0 == dotProd);
    }

    @Test
    public void canFindDotProductWithMultiplingByZero() {
        Vector3d first = new Vector3d(0, 0, 0);
        Vector3d second = new Vector3d(1, 4, -3);
        double dotProd = first.dotProduct(second);
        assertTrue(0 == dotProd);
    }

    @Test
    public void canFindCrossProduct() {
        Vector3d first = new Vector3d(1, 2, 3);
        Vector3d second = new Vector3d(4, 5, 6);
        Vector3d result = new Vector3d(-3, 6, -3);
        Vector3d crossProd = first.crossProduct(second);
        assertTrue(result.equalComplitely(crossProd));
    }

    @Test
    public void canFindCrossProductEvenIfResultIsNormalised() {
        Vector3d first = new Vector3d(1, 2, 3);
        Vector3d second = new Vector3d(4, 5, 6);
        Vector3d result = new Vector3d(1, -2, 1);
        Vector3d crossProd = first.crossProduct(second);
        assertFalse(result.equalNormalized(crossProd));
    }

    @Test
    public void canFindFalseCrossProduct() {
        Vector3d first = new Vector3d(1, 2, 3);
        Vector3d second = new Vector3d(4, 5, 6);
        Vector3d result = new Vector3d(3, 6, -3);
        Vector3d crossProd = first.crossProduct(second);
        assertFalse(result.equalComplitely(crossProd));
    }

    @Test
    public void canFindCrossProductWithMultiplingByZero() {
        Vector3d first = new Vector3d(0, 0, 0);
        Vector3d second = new Vector3d(1, 2, 3);
        Vector3d result = new Vector3d(0, 0, 0);
        Vector3d crossProd = first.crossProduct(second);
        assertTrue(result.equalComplitely(crossProd));
    }
}


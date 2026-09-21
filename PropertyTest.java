package edu.vt.hokiehousing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests the Property class.
 */
public class PropertyTest {
    private Property property;
    private TravelInfo travelInfo;
    private LandlordReport landlordReport;

    /**
     * Creates the objects used before each test.
     */
    public void setUp() {
        travelInfo = new TravelInfo(15, 5, 10, 8, 6, "Main Street");
        landlordReport = new LandlordReport(
            "Hokie Rentals",
            "Private",
            4.0,
            4.5,
            3.5,
            4.0,
            "Verified",
            "County Records");

        property = new Property(
            "Campus View",
            "100 Main Street",
            900.0,
            1.2,
            travelInfo,
            landlordReport,
            "Property Website",
            "09/21/2026");
    }

    /**
     * Tests all getters and the automatically created review manager.
     */
    public void testGetters() {
        assertEquals("Campus View", property.getName());
        assertEquals("100 Main Street", property.getAddress());
        assertEquals(900.0, property.getMonthlyRent(), 0.001);
        assertEquals(1.2, property.getDistanceFromCampus(), 0.001);
        assertSame(travelInfo, property.getTravelInfo());
        assertSame(landlordReport, property.getLandlordReport());
        assertNotNull(property.getReviewManager());
        assertEquals("Property Website", property.getDataSource());
        assertEquals("09/21/2026", property.getVerifiedDate());
    }

    /**
     * Tests that text is trimmed and missing optional text becomes Unknown.
     */
    public void testTextCleanup() {
        Property cleaned = new Property(
            "  Foxridge  ",
            " ",
            Property.UNKNOWN_NUMBER,
            Property.UNKNOWN_NUMBER,
            travelInfo,
            landlordReport,
            null,
            "  09/20/2026  ");

        assertEquals("Foxridge", cleaned.getName());
        assertEquals("Unknown", cleaned.getAddress());
        assertEquals("Unknown", cleaned.getDataSource());
        assertEquals("09/20/2026", cleaned.getVerifiedDate());
    }

    /**
     * Tests the methods that format rent and distance for display.
     */
    public void testDisplayMethods() {
        assertEquals("$900.00", Property.displayMoney(900.0));
        assertEquals("Unknown",
            Property.displayMoney(Property.UNKNOWN_NUMBER));
        assertEquals("1.2 miles", Property.displayDistance(1.2));
        assertEquals("Unknown",
            Property.displayDistance(Property.UNKNOWN_NUMBER));
    }

    /**
     * Tests that a null name is rejected.
     */
    public void testNullName() {
        Exception exception = null;
        try {
            createProperty(null, 900.0, 1.2, travelInfo, landlordReport);
        }
        catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    /**
     * Tests that a blank name is rejected.
     */
    public void testBlankName() {
        Exception exception = null;
        try {
            createProperty("   ", 900.0, 1.2, travelInfo, landlordReport);
        }
        catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    /**
     * Tests that rent below -1 is rejected.
     */
    public void testInvalidRent() {
        Exception exception = null;
        try {
            createProperty("Name", -2.0, 1.2, travelInfo, landlordReport);
        }
        catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    /**
     * Tests that distance below -1 is rejected.
     */
    public void testInvalidDistance() {
        Exception exception = null;
        try {
            createProperty("Name", 900.0, -2.0, travelInfo, landlordReport);
        }
        catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    /**
     * Tests that null travel information is rejected.
     */
    public void testNullTravelInfo() {
        Exception exception = null;
        try {
            createProperty("Name", 900.0, 1.2, null, landlordReport);
        }
        catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    /**
     * Tests that a null landlord report is rejected.
     */
    public void testNullLandlordReport() {
        Exception exception = null;
        try {
            createProperty("Name", 900.0, 1.2, travelInfo, null);
        }
        catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    /**
     * Creates a property for constructor exception tests.
     */
    private Property createProperty(
        String name,
        double rent,
        double distance,
        TravelInfo travel,
        LandlordReport landlord) {

        return new Property(
            name,
            "Address",
            rent,
            distance,
            travel,
            landlord,
            "Source",
            "09/21/2026");
    }
}
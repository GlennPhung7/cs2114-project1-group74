package edu.vt.hokiehousing;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests the LandlordReport class.
 */
public class LandlordReportTest {
    private LandlordReport report;

    /**
     * Creates a landlord report before each test.
     */
    public void setUp() {
        report = new LandlordReport(
            "Hokie Rentals",
            "Private",
            4.0,
            4.5,
            3.5,
            4.0,
            "Verified",
            "County Records");
    }

    /**
     * Tests all getter methods.
     */
    public void testGetters() {
        assertEquals("Hokie Rentals", report.getCompanyName());
        assertEquals("Private", report.getOwnershipType());
        assertEquals(4.0, report.getMaintenanceRating(), 0.001);
        assertEquals(4.5, report.getCommunicationRating(), 0.001);
        assertEquals(3.5, report.getFeeRating(), 0.001);
        assertEquals(4.0, report.getOverallRating(), 0.001);
        assertEquals("Verified", report.getOwnershipStatus());
        assertEquals("County Records", report.getOwnershipSource());
    }

    /**
     * Tests that text is trimmed.
     */
    public void testTextIsTrimmed() {
        LandlordReport trimmed = new LandlordReport(
            "  Hokie Rentals  ",
            "  Private  ",
            4.0,
            4.0,
            4.0,
            4.0,
            "  Verified  ",
            "  County Records  ");

        assertEquals("Hokie Rentals", trimmed.getCompanyName());
        assertEquals("Private", trimmed.getOwnershipType());
        assertEquals("Verified", trimmed.getOwnershipStatus());
        assertEquals("County Records", trimmed.getOwnershipSource());
    }

    /**
     * Tests that null and blank text values become Unknown.
     */
    public void testMissingTextBecomesUnknown() {
        LandlordReport missing = new LandlordReport(
            null,
            " ",
            LandlordReport.UNKNOWN_RATING,
            LandlordReport.UNKNOWN_RATING,
            LandlordReport.UNKNOWN_RATING,
            LandlordReport.UNKNOWN_RATING,
            null,
            "");

        assertEquals("Unknown", missing.getCompanyName());
        assertEquals("Unknown", missing.getOwnershipType());
        assertEquals("Unknown", missing.getOwnershipStatus());
        assertEquals("Unknown", missing.getOwnershipSource());
        assertEquals(
            LandlordReport.UNKNOWN_RATING,
            missing.getMaintenanceRating(),
            0.001);
    }

    /**
     * Tests the displayRating method.
     */
    public void testDisplayRating() {
        assertEquals("4.5/5", LandlordReport.displayRating(4.5));
        assertEquals(
            "Unknown",
            LandlordReport.displayRating(LandlordReport.UNKNOWN_RATING));
    }

    /**
     * Tests that the boundary ratings of 1 and 5 are accepted.
     */
    public void testRatingBoundaries() {
        LandlordReport boundaries = new LandlordReport(
            "Company",
            "Private",
            1.0,
            5.0,
            1.0,
            5.0,
            "Verified",
            "Source");

        assertEquals(1.0, boundaries.getMaintenanceRating(), 0.001);
        assertEquals(5.0, boundaries.getCommunicationRating(), 0.001);
        assertEquals(1.0, boundaries.getFeeRating(), 0.001);
        assertEquals(5.0, boundaries.getOverallRating(), 0.001);
    }

    /**
     * Tests that a rating below 1, other than -1, is rejected.
     */
    public void testRatingBelowRange() {
        Exception exception = null;
        try {
            new LandlordReport(
                "Company", "Private", 0.0, 4.0, 4.0, 4.0,
                "Verified", "Source");
        }
        catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    /**
     * Tests that a rating above 5 is rejected.
     */
    public void testRatingAboveRange() {
        Exception exception = null;
        try {
            new LandlordReport(
                "Company", "Private", 4.0, 6.0, 4.0, 4.0,
                "Verified", "Source");
        }
        catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull(exception);
    }
}

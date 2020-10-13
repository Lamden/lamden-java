package io.lamden.api;

import io.lamden.exception.MasternodesNotAvailableException;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

class MasterNodeTest {

    @Test
    public void isHealthy_untilRetries_returnsUnhealthy() throws Exception {
        MasterNode testee = new MasterNode(new URI("https://masternode-01.lamden.io/"), 5, 10);
        assertTrue(testee.isHealthy());

        testee.reportUnhealthy();
        assertTrue(testee.isHealthy());

        testee.reportUnhealthy();
        assertTrue(testee.isHealthy());

        testee.reportUnhealthy();
        assertTrue(testee.isHealthy());

        testee.reportUnhealthy();
        assertTrue(testee.isHealthy());

        testee.reportUnhealthy();
        assertTrue(testee.isHealthy());

        testee.reportUnhealthy();
        assertFalse(testee.isHealthy());

    }

    @Test
    public void isHealthy_untilRetriesAndTimeSpan_returnsUnhealthy() throws Exception {
        MasterNode testee = new MasterNode(new URI("https://masternode-01.lamden.io/"), 5, 2);
        assertTrue(testee.isHealthy());

        testee.reportUnhealthy();
        assertTrue(testee.isHealthy());

        testee.reportUnhealthy();
        assertTrue(testee.isHealthy());

        testee.reportUnhealthy();
        assertTrue(testee.isHealthy());

        testee.reportUnhealthy();
        assertTrue(testee.isHealthy());

        Thread.sleep(2000); //This should reset the unhealthy retries

        testee.reportUnhealthy();
        assertTrue(testee.isHealthy());

        testee.reportUnhealthy();
        assertTrue(testee.isHealthy());

        testee.reportUnhealthy();
        assertTrue(testee.isHealthy());

        testee.reportUnhealthy();
        assertTrue(testee.isHealthy());

        testee.reportUnhealthy();
        assertTrue(testee.isHealthy());

        testee.reportUnhealthy();
        assertFalse(testee.isHealthy());

    }


}
package com.fof.fof

import org.junit.Assert.assertFalse
import org.junit.Test

/**
 * Unit tests for FofAccessibilityService companion object state.
 *
 * These verify the "service not running" paths that MainActivity depends on —
 * specifically that isRunning() and triggerPowerDialog() behave correctly
 * when no service instance has been connected.
 *
 * The "service running" path requires a live Android environment and is
 * covered by the manual pre-release checklist.
 */
class FofAccessibilityServiceTest {

    @Test
    fun `isRunning returns false when no instance is connected`() {
        assertFalse(FofAccessibilityService.isRunning())
    }

    @Test
    fun `triggerPowerDialog returns false when service is not running`() {
        assertFalse(FofAccessibilityService.triggerPowerDialog())
    }
}

package com.citybond.mobile.ui

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AssistantAuthErrorTest {
    @Test
    fun `normal login error does not require captcha`() {
        assertFalse(captchaIsRequired("""{"detail":{"captchaRequired":false}}"""))
    }

    @Test
    fun `threshold login error requires captcha`() {
        assertTrue(captchaIsRequired("""{"detail":{"captchaRequired":true}}"""))
    }

    @Test
    fun `malformed error response does not require captcha`() {
        assertFalse(captchaIsRequired("not-json"))
    }
}

package com.reactnativedidomi

enum class EventTypes(val event: String) {
    // Consent
    CONSENT_CHANGED("on_consent_changed"),
    // SDK lifecycle events
    ERROR("on_error"),
    ERROR_CALLBACK("on_error_callback"),
    READY("on_ready"),
    READY_CALLBACK("on_ready_callback"),
    // Notice
    HIDE_NOTICE("on_hide_notice"),
    SHOW_NOTICE("on_show_notice"),
    NOTICE_CLICK_AGREE("on_notice_click_agree"),
    NOTICE_CLICK_DISAGREE("on_notice_click_disagree"),
    NOTICE_CLICK_VIEW_VENDORS("on_notice_click_view_vendors"),
    NOTICE_CLICK_VIEW_SPI_PURPOSES("on_notice_click_view_spi_purposes"),
    NOTICE_CLICK_MORE_INFO("on_notice_click_more_info"),
    NOTICE_CLICK_PRIVACY_POLICY("on_notice_click_privacy_policy"),
    // Preferences
    HIDE_PREFERENCES("on_hide_preferences"),
    SHOW_PREFERENCES("on_show_preferences"),
    // Preferences - Views
    PREFERENCES_CLICK_VIEW_PURPOSES("on_preferences_click_view_purposes"),
    PREFERENCES_CLICK_VIEW_VENDORS("on_preferences_click_view_vendors"),
    PREFERENCES_CLICK_VIEW_SPI_PURPOSES("on_preferences_click_view_spi_purposes"),
    // Preferences - Purpose
    PREFERENCES_CLICK_AGREE_TO_ALL("on_preferences_click_agree_to_all"),
    PREFERENCES_CLICK_DISAGREE_TO_ALL("on_preferences_click_disagree_to_all"),
    PREFERENCES_CLICK_AGREE_TO_ALL_PURPOSES("on_preferences_click_agree_to_all_purposes"),
    PREFERENCES_CLICK_DISAGREE_TO_ALL_PURPOSES("on_preferences_click_disagree_to_all_purposes"),
    PREFERENCES_CLICK_RESET_ALL_PURPOSES("on_preferences_click_reset_all_purposes"),
    PREFERENCES_CLICK_PURPOSE_AGREE("on_preferences_click_purpose_agree"),
    PREFERENCES_CLICK_PURPOSE_DISAGREE("on_preferences_click_purpose_disagree"),
    PREFERENCES_CLICK_CATEGORY_AGREE("on_preferences_click_category_agree"),
    PREFERENCES_CLICK_CATEGORY_DISAGREE("on_preferences_click_category_disagree"),
    PREFERENCES_CLICK_SAVE_CHOICES("on_preferences_click_save_choices"),
    // Preferences - Vendor
    PREFERENCES_CLICK_AGREE_TO_ALL_VENDORS("on_preferences_click_agree_to_all_vendors"),
    PREFERENCES_CLICK_DISAGREE_TO_ALL_VENDORS("on_preferences_click_disagree_to_all_vendors"),
    PREFERENCES_CLICK_VENDOR_AGREE("on_preferences_click_vendor_agree"),
    PREFERENCES_CLICK_VENDOR_DISAGREE("on_preferences_click_vendor_disagree"),
    PREFERENCES_CLICK_VENDOR_SAVE_CHOICES("on_preferences_click_vendor_save_choices"),
    // Preferences - Sensitive Personal Information
    PREFERENCES_CLICK_SPI_PURPOSE_AGREE("on_preferences_click_spi_purpose_agree"),
    PREFERENCES_CLICK_SPI_PURPOSE_DISAGREE("on_preferences_click_spi_purpose_disagree"),
    PREFERENCES_CLICK_SPI_CATEGORY_AGREE("on_preferences_click_spi_category_agree"),
    PREFERENCES_CLICK_SPI_CATEGORY_DISAGREE("on_preferences_click_spi_category_disagree"),
    PREFERENCES_CLICK_SPI_PURPOSE_SAVE_CHOICES("on_preferences_click_spi_purpose_save_choices"),
    // Sync
    SYNC_DONE("on_sync_done"),
    SYNC_ERROR("on_sync_error"),
    // Language
    LANGUAGE_UPDATED("on_language_updated"),
    LANGUAGE_UPDATE_FAILED("on_language_update_failed"),
    // Vendor Status
    VENDOR_STATUS_CHANGE_PREFIX("on_vendor_status_change_");
}

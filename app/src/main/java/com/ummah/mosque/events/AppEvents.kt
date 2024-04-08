package com.ummah.mosque.events

sealed interface AppEvents {
    data object RemoteConfigActivated : AppEvents
    data object RemoteConfigActivationFailed : AppEvents
    data object LocalisationUpdated : AppEvents
    data object CountriesFetched: AppEvents
}

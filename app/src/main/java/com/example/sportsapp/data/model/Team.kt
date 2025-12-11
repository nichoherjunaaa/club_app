package com.example.sportsapp.data.model

import com.google.gson.annotations.SerializedName

data class TeamResponse(
    @SerializedName("teams")
    val teams: List<Team>? = emptyList()
)

data class Team(
    @SerializedName("idTeam")
    val idTeam: String? = "",

    @SerializedName("strTeam")
    val strTeam: String? = "",

    @SerializedName("strTeamShort")
    val strTeamShort: String? = "",

    @SerializedName("strTeamAlternate")
    val strTeamAlternate: String? = "",

    @SerializedName("intFormedYear")
    val intFormedYear: String? = "",

    @SerializedName("strSport")
    val strSport: String? = "",

    @SerializedName("strLeague")
    val strLeague: String? = "",

    @SerializedName("strStadium")
    val strStadium: String? = "",

    @SerializedName("strLocation")
    val strLocation: String? = "",

    @SerializedName("intStadiumCapacity")
    val intStadiumCapacity: String? = "",

    @SerializedName("strWebsite")
    val strWebsite: String? = "",

    @SerializedName("strDescriptionEN")
    val strDescriptionEN: String? = "",

    @SerializedName("strCountry")
    val strCountry: String? = "",

    @SerializedName("strBadge")
    val strBadge: String? = "",

    @SerializedName("strLogo")
    val strLogo: String? = "",

    @SerializedName("strBanner")
    val strBanner: String? = "",

    @SerializedName("strYoutube")
    val strYoutube: String? = "",

    @SerializedName("strFacebook")
    val strFacebook: String? = "",

    @SerializedName("strTwitter")
    val strTwitter: String? = "",

    @SerializedName("strInstagram")
    val strInstagram: String? = ""
)
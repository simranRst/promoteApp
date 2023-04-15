package com.rootsquare.promote.model

import com.google.gson.annotations.SerializedName


data class Demo (

    @SerializedName("name" ) var name : String? = null,
    @SerializedName("job"  ) var job  : String? = null

)
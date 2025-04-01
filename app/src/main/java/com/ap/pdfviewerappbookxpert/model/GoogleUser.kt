package com.ap.pdfviewerappbookxpert.model

data class GoogleUser(val email: String = "", val fullName:String="",val type: TYPE = TYPE.PATIENT) {

    enum class TYPE {
        PATIENT,
        MEDIC
    }
}



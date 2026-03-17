package com.allan88.journeymanager.network

object TokenManager {

    private var token: String? = null

    fun saveToken(newToken: String) {
        token = newToken
    }

    fun getToken(): String? {
        return token
    }
<<<<<<< HEAD

    fun clearToken() {
        token = null
    }

}
=======
}


>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)

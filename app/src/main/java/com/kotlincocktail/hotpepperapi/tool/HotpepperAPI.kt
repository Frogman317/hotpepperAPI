package com.kotlincocktail.hotpepperapi.tool

import java.net.URL
import java.security.cert.Certificate
import javax.net.ssl.HttpsURLConnection

class HotpepperAPI(url: URL?) : HttpsURLConnection(url) {
    override fun connect() {
        TODO("Not yet implemented")
    }

    override fun disconnect() {
        TODO("Not yet implemented")
    }

    override fun usingProxy(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getCipherSuite(): String {
        TODO("Not yet implemented")
    }

    override fun getLocalCertificates(): Array<Certificate> {
        TODO("Not yet implemented")
    }

    override fun getServerCertificates(): Array<Certificate> {
        TODO("Not yet implemented")
    }
}
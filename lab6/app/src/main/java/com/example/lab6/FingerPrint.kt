package com.example.lab6

import android.Manifest
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

@Suppress("DEPRECATION")
class FingerPrint : AppCompatActivity() {

    lateinit var fingerprintManager: FingerprintManager
    lateinit var keyguardManager: KeyguardManager
    lateinit var keyStore: KeyStore
    lateinit var secretKey: SecretKey
    lateinit var keyGenerator: KeyGenerator
    var KEY_NAME = "my_key"
    lateinit var cipher: Cipher
    lateinit var cryptoObject: FingerprintManager.CryptoObject

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finger_print)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            prepareFingerprint()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun prepareFingerprint() {
        keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fingerprintManager = getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager
        }

        if (!keyguardManager.isKeyguardSecure) {
            Toast.makeText(this, "Lock Screen Seciurity not enabled", Toast.LENGTH_LONG).show()
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!fingerprintManager.hasEnrolledFingerprints()) {
                Toast.makeText(this, "Register fingerprint", Toast.LENGTH_LONG).show()
                return
            }
        }
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.USE_FINGERPRINT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.USE_FINGERPRINT),
                    111
                )
            }
        } else {
            validateFingerprint()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun validateFingerprint() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyGenerator =
                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            keyStore.load(null)
            keyGenerator.init(
                KeyGenParameterSpec.Builder(
                    KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build()
            )
            secretKey = keyGenerator.generateKey()
        } catch (e: Exception) {
        }

        if (initCipher()) {
            cryptoObject = FingerprintManager.CryptoObject(cipher)
        }

        val helper = FingerPrintHelper(this)

        if (fingerprintManager != null && cryptoObject!=null){
            helper.startAuth(fingerprintManager, cryptoObject)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun initCipher(): Boolean {
        try {
            cipher =
                Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7)
        } catch (e: Exception) {
        }

        return try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            true
        } catch (e: Exception){
            false
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            validateFingerprint()
        }
    }
}
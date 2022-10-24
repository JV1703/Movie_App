package com.example.core.utils

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.io.InputStream
import java.io.OutputStream
import java.security.KeyStore
import java.security.KeyStore.SecretKeyEntry
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class CryptoManager {

    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    private fun encryptCipher(alias: String) = Cipher.getInstance(TRANSFORMATION).apply {
        init(Cipher.ENCRYPT_MODE, getKey(alias))
    }

    private fun getDecryptCipherForIv(alias: String, iv: ByteArray): Cipher = Cipher.getInstance(
        TRANSFORMATION
    ).apply {
        init(Cipher.DECRYPT_MODE, getKey(alias), IvParameterSpec(iv))
    }

    fun encrypt(alias: String, bytes: ByteArray, outputStream: OutputStream): ByteArray {
        val encryptedBytes = encryptCipher(alias).doFinal(bytes)
        outputStream.use {
            it.write(encryptCipher(alias).iv.size)
            it.write(encryptCipher(alias).iv)
            it.write(encryptedBytes.size)
            it.write(encryptedBytes)
        }
        return encryptedBytes
    }

    fun decrypt(alias: String, inputStream: InputStream): ByteArray {
        return inputStream.use {
            val ivSize = it.read()
            val iv = ByteArray(ivSize)
            it.read(iv)

            val encryptedBytesSize = it.read()
            val encryptedBytes = ByteArray(encryptedBytesSize)
            it.read(encryptedBytes)

            getDecryptCipherForIv(alias, iv).doFinal(encryptedBytes)
        }
    }

    private fun getKey(alias: String): SecretKey {
        val existingKey = keyStore.getEntry(alias, null) as? SecretKeyEntry
        return existingKey?.secretKey ?: createKey(alias)
    }

    private fun createKey(alias: String): SecretKey {
        return KeyGenerator.getInstance(ALGORITHM).apply {
            init(
                KeyGenParameterSpec.Builder(
                    alias,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(BLOCK_MODE)
                    .setEncryptionPaddings(PADDING)
                    .setUserAuthenticationRequired(false)
                    .setRandomizedEncryptionRequired(true)
                    .build()
            )
        }.generateKey()
    }

    companion object {
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }

}
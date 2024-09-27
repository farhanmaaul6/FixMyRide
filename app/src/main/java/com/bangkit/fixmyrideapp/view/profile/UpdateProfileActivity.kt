package com.bangkit.fixmyrideapp.view.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bangkit.fixmyrideapp.R
import com.bangkit.fixmyrideapp.data.api.ApiConfig
import com.bangkit.fixmyrideapp.data.response.UpdateProfileResponse
import com.bangkit.fixmyrideapp.data.utils.SessionManager
import com.bangkit.fixmyrideapp.data.utils.reduceFileImage
import com.bangkit.fixmyrideapp.data.utils.rotateFile
import com.bangkit.fixmyrideapp.data.utils.uriToFile
import com.bangkit.fixmyrideapp.databinding.ActivityUpdateProfileBinding
import com.bangkit.fixmyrideapp.view.camera.CameraActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UpdateProfileActivity : AppCompatActivity() {
    private var getFile: File? = null
    private lateinit var binding: ActivityUpdateProfileBinding
    private var token: String? = null
    private lateinit var sharedPref: SessionManager


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SessionManager(this)
        token = sharedPref.getToken

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.btnOpenCamera.setOnClickListener { openCamera() }
        binding.btnOpenGaleri.setOnClickListener { openGallery() }
        binding.btnSubmit.setOnClickListener { submitData() }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@UpdateProfileActivity)
                getFile = myFile
                binding.ivPhoto.setImageURI(uri)
            }
        }
    }

    private fun openCamera() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.data?.getSerializableExtra("picture")
            } as? File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            myFile?.let { file ->
                rotateFile(file, isBackCamera)
                getFile = file
                binding.ivPhoto.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    private fun submitData() {
        val email = binding.etEmail.text.toString()

        if (getFile != null){
            val file = reduceFileImage(getFile as File)
            val typeEmail = email.toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
            val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image",
                file.name,
                requestImageFile
            )

            val apiService = ApiConfig.getApiService()
            val updateProfileData = apiService.updateDetailProfile("$token", imageMultiPart, typeEmail)
            updateProfileData.enqueue( object : Callback<UpdateProfileResponse>{
                override fun onResponse(
                    call: Call<UpdateProfileResponse>,
                    response: Response<UpdateProfileResponse>,
                ) {
                    if (response.isSuccessful){
                        val responseBody = response.body()
                        if (responseBody != null && !responseBody.error) {
                            Log.e("error", responseBody.error.toString())
                            Toast.makeText(this@UpdateProfileActivity, responseBody.message, Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@UpdateProfileActivity, ProfileActivity::class.java)
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(this@UpdateProfileActivity, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                    Toast.makeText(this@UpdateProfileActivity, t.message, Toast.LENGTH_SHORT).show()
                }

            })
        } else{
            Toast.makeText(this@UpdateProfileActivity, "Pastikan Semua Field Terisi.", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}
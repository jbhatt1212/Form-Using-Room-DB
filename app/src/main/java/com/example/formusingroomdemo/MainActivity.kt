package com.example.formusingroomdemo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.formusingroomdemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel: UsersViewModel by viewModels()
    private var selectedImageBitmap: Bitmap? = null
    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            val inputStream = contentResolver.openInputStream(uri)
            selectedImageBitmap = BitmapFactory.decodeStream(inputStream)

            val imageName = getFileName(uri)

            binding.tvImgName.text = imageName
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSelectImg.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }
        binding.btnSubmit.setOnClickListener {
            try {
                val userName = binding.etUserName.text.toString()
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                val contact = binding.etContact.text.toString()
                val selectedGenderId = binding.rgGender.checkedRadioButtonId
                val selectedGender: String = when (selectedGenderId) {
                    R.id.rbFemale-> "Female"
                    R.id.rbMale -> "Male"
                    else -> ""
                }
                val hobbies = mutableListOf<String>()
                if (binding.cbRead.isChecked) hobbies.add("Reading")
                if (binding.cbWrite.isChecked) hobbies.add("Writing")
                if (binding.cbListen.isChecked) hobbies.add("Listening")

                Log.e("Debug", "Form values captured: Name=$userName, Email=$email,password=$password,contact=$contact,selectedGender=$selectedGenderId,selectedImageBitmap=$selectedImageBitmap, hobbies=$hobbies")

                if (userName.isNotBlank() && email.isNotBlank() && password.isNotBlank() && contact.isNotBlank() && selectedGender.isNotBlank()) {
                    viewModel.addUser(
                        userName,
                        email,
                        password,
                        contact,
                        selectedGender,
                        selectedImageBitmap,
                        hobbies
                    )
                    binding.etUserName.text?.clear()
                    binding.etEmail.text?.clear()
                    binding.etPassword.text?.clear()
                    binding.etContact.text?.clear()
                    binding.rgGender.clearCheck()
                    selectedImageBitmap = null
                    binding.tvImgName.text = null
                    binding.cbRead.isChecked = false
                    binding.cbWrite.isChecked = false
                    binding.cbListen.isChecked = false



                    Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this, "Please fill all fields correctly", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
            }
            catch (e: Exception) {
                Log.e("FormSubmitError", "Error in submission", e)
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.users.observe(this, Observer {
          //  Log.d("LiveData", "Users observed: $users")
        })


    }

    private fun getFileName(uri: Uri): String {
        var fileName = ""
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex != -1 && it.moveToFirst()) {
                fileName = it.getString(nameIndex)
            }
        }
        return fileName
    }


}

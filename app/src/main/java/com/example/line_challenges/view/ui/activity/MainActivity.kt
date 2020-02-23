package com.example.line_challenges

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.example.line_challenges.databinding.ActivityMainBinding
import com.example.line_challenges.view.ui.fragment.MainFragment
import com.example.line_challenges.view.ui.fragment.MemoFragment
import com.example.line_challenges.viewmodel.BackPressViewModel
import com.example.line_challenges.viewmodel.MainViewModel
import java.io.FileInputStream
import android.net.Uri
import android.os.Environment
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity()
{
    val TAG = "MainActivity.kt"
    lateinit var viewModel : MainViewModel
    lateinit var backPressViewModel: BackPressViewModel
    lateinit var mainFragment : MainFragment
    lateinit var memoFragment : MemoFragment
    lateinit var fragmentTransaction: FragmentTransaction
    lateinit var fragmentManager: FragmentManager
    val REQUEST_GALLERY = 1
    var photoFile : File? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        initSetting()
        setFragNumBind()
    }

    fun initSetting()
    {
        val binding = DataBindingUtil
            .setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this // LiveData를 사용하기 위해서 없으면 Observe할때마다 refresh안딤

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        backPressViewModel = ViewModelProvider(this).get(BackPressViewModel::class.java)
        mainFragment = MainFragment()
        memoFragment = MemoFragment()
        fragmentManager = supportFragmentManager
        fragmentTransaction = fragmentManager.beginTransaction()
        viewModel.getAll()
        setFrag(0)
        viewModel.nowPath.observe(this, Observer {
            memoFragment.checkImage()
        })
    }

    fun setFragNumBind()
    {
        viewModel.fragNum.observe(this, Observer {
            setFrag(it)
        })
    }

    fun setFrag(n : Int)
    {
        fragmentTransaction = fragmentManager.beginTransaction()

        when(n)
        {
            0 ->
            {
                fragmentTransaction.replace(R.id.frag_main,mainFragment)
                fragmentTransaction.commit()
            }
            1->
            {
                fragmentTransaction.replace(R.id.frag_main,memoFragment)
                fragmentTransaction.commit()
            }
        }
    }

    fun getGallery()
    {
        var permissionResult = ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)

        if(permissionResult == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
        }
        else
        {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            startActivityForResult(intent, REQUEST_GALLERY)
        }
    }

    fun createFile() : File
    {

        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }

    fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                photoFile = try {
                    createFile()
                } catch (ex: IOException) {
                    null
                }

                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.line_challenges",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, 2)
                }

            }
        }
    }


    override fun onBackPressed()
    {
        when(viewModel.fragNum.value)
        {
            0->
            {
                backPressViewModel.onBackPressed(this)
            }
            1->
            {
                viewModel.fragNum.value=0
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==REQUEST_GALLERY)
        {
            if(data!=null)
            {
                val selectedImage = data.data
                var filePathColumn: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
                var cursor = contentResolver.query(
                    selectedImage!!,
                    filePathColumn, null, null, null
                )
                cursor!!.moveToFirst()

                val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                val picturePath = cursor.getString(column_index)

                cursor.close()


                viewModel.putImagePath(picturePath)
            }
        }
        else
        {
            if(resultCode== Activity.RESULT_OK)
                viewModel.putImagePath(photoFile.toString())
        }
    }

}

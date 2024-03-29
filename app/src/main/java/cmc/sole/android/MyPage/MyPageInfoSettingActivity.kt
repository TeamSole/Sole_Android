package cmc.sole.android.MyPage

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import cmc.sole.android.*
import cmc.sole.android.Home.MyPageInfoResult
import cmc.sole.android.Home.Retrofit.HomeService
import cmc.sole.android.Home.Retrofit.MyPageInfoUpdateView
import cmc.sole.android.Signup.SignupNicknameActivity

import cmc.sole.android.Utils.ImageTranslator
import cmc.sole.android.databinding.ActivityMyPageInfoSettingBinding
import cmc.sole.android.databinding.ActivityMyPageNoticeBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File

class MyPageInfoSettingActivity: AppCompatActivity(),
    MyPageInfoUpdateView {

    lateinit var binding: ActivityMyPageInfoSettingBinding

    private lateinit var homeService: HomeService

    private var imageUri: Uri? = null
    companion object{
        // 갤러리 권한 요청
        const val REQ_GALLERY = 1
    }
    private val imageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            imageUri = result.data?.data
            imageUri?.let{
                Glide.with(this)
                    .load(imageUri).placeholder(R.drawable.ic_profile).fallback(R.drawable.ic_profile).fitCenter()
                    .apply(RequestOptions().circleCrop().override(130,130))
                    .into(binding.signupNicknameProfileIv)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageInfoSettingBinding.inflate(layoutInflater)

        Glide.with(this).load(intent.getStringExtra("profileImgUrl")).placeholder(R.drawable.ic_profile)
            .circleCrop().centerCrop().into(binding.signupNicknameProfileIv)
        binding.myPageInfoNicknameTv.setText(intent.getStringExtra("nickname"))
        binding.myPageInfoIntroEt.setText(intent.getStringExtra("description"))

        initService()
        initTextWatcher()
        initClickListener()

        setContentView(binding.root)
    }

    private fun initService() {
        homeService = HomeService()
        homeService.setMyPageInfoUpdateView(this)
    }

    private fun initTextWatcher() {
        binding.myPageInfoNicknameTv.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (binding.myPageInfoNicknameTv.length() < 50 && binding.myPageInfoNicknameTv.text.isNotEmpty() && binding.myPageInfoNicknameTv.text.length < 11) {
                    binding.myPageInfoSaveBtn.setBackgroundResource(R.drawable.default_button_o)
                    binding.myPageInfoSaveBtn.isEnabled = true
                    binding.myPageInfoIntroResult.setTextColor(Color.parseColor("#D3D4D5"))
                    binding.myPageInfoIntroCv.strokeColor = Color.parseColor("#D3D4D5")
                } else {
                    binding.myPageInfoSaveBtn.setBackgroundResource(R.drawable.default_button_x)
                    binding.myPageInfoSaveBtn.isEnabled = false
                    binding.myPageInfoIntroCv.strokeColor = ContextCompat.getColor(this@MyPageInfoSettingActivity, R.color.red)
                    binding.myPageInfoIntroResult.setTextColor(ContextCompat.getColor(this@MyPageInfoSettingActivity, R.color.red))
                }
            }
        })

        binding.myPageInfoIntroEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                binding.myPageInfoIntroResult.text="${binding.myPageInfoIntroEt.length()}/50"

                if (binding.myPageInfoIntroEt.length() < 50 && binding.myPageInfoNicknameTv.text.isNotEmpty() && binding.myPageInfoNicknameTv.text.length < 11) {
                    binding.myPageInfoSaveBtn.setBackgroundResource(R.drawable.default_button_o)
                    binding.myPageInfoSaveBtn.isEnabled = true
                    binding.myPageInfoIntroResult.setTextColor(Color.parseColor("#D3D4D5"))
                    binding.myPageInfoIntroCv.strokeColor = Color.parseColor("#D3D4D5")
                } else {
                    binding.myPageInfoSaveBtn.setBackgroundResource(R.drawable.default_button_x)
                    binding.myPageInfoSaveBtn.isEnabled = false
                    binding.myPageInfoIntroCv.strokeColor = ContextCompat.getColor(this@MyPageInfoSettingActivity, R.color.red)
                    binding.myPageInfoIntroResult.setTextColor(ContextCompat.getColor(this@MyPageInfoSettingActivity, R.color.red))
                }
            }
        })
    }

    private fun initClickListener() {
        binding.myPageInfoBackIv.setOnClickListener {
            finish()
        }

        binding.signupNicknameProfilePlusCv.setOnClickListener {
            val writePermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            val readPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )

            //권한 확인
            if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED) {
                // 권한 요청
                ActivityCompat.requestPermissions(this, arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), SignupNicknameActivity.REQ_GALLERY
                )
            } else {
                // 권한이 있는 경우 갤러리 실행
                val intent = Intent(Intent.ACTION_PICK)
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                imageResult.launch(intent)
            }
        }

        binding.myPageInfoSaveBtn.setOnClickListener {
            // UPDATE: API 연동 필요
            var file: File
            var requestFile: RequestBody
            var multipartFile: MultipartBody.Part?

            if (imageUri == null) {
                multipartFile = null
            } else {
                file = File(ImageTranslator.optimizeBitmap(this, imageUri!!))
                requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                multipartFile = MultipartBody.Part.createFormData("multipartFile", file.name, requestFile)
            }

            var jsonBody = JSONObject()
            jsonBody.put("description", binding.myPageInfoIntroEt.text)
            jsonBody.put("nickname", binding.myPageInfoNicknameTv.text)

            val requestBody: RequestBody = jsonBody.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            var mypageRequestDto = MultipartBody.Part.createFormData("mypageRequestDto", "mypageRequestDto", requestBody)

            homeService.myPageInfoUpdate(mypageRequestDto, multipartFile)
        }

        binding.myPageInfoWithdrawal.setOnClickListener {
            val dialogWithdrawal = DialogMyPageWithdrawal()
            dialogWithdrawal.show(supportFragmentManager, "WithdrawalDialog")
        }
    }

    override fun myPageInfoUpdateSuccessView(myPageInfoResult: MyPageInfoResult) {
        Glide.with(this).load(myPageInfoResult.profileImgUrl)
            .placeholder(R.drawable.ic_profile).centerCrop().circleCrop().into(binding.signupNicknameProfileIv)
        binding.myPageInfoNicknameTv.setText(myPageInfoResult.nickname)
        binding.myPageInfoIntroEt.setText(myPageInfoResult.description)

        finish()
    }

    override fun myPageInfoUpdateFailureView() {

    }
}
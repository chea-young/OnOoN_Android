package com.example.xproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        var retrofit = Retrofit.Builder()
            .baseUrl("http://10.10.0.162:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var signupservice: SignupService = retrofit.create(SignupService::class.java)




        register_addface.setOnClickListener {
            var textId = register_id.text.toString()
            var textPw = register_pass.text.toString()
            var textName = register_name.text.toString()
            var check = 0
            val intent = Intent(this, AddFaceActivity::class.java)
            signupservice.requestSignup(textId, textPw, textName).enqueue(object : Callback<Signup> {
                override fun onFailure(call: Call<Signup>, t: Throwable) {
                    Log.e("DEBUG1", t.message)
                    var dialog = AlertDialog.Builder(this@SignupActivity)
                    Toast.makeText(this@SignupActivity, "통신에 실패했습니다..", Toast.LENGTH_SHORT).show()
                    check --
                }
                override fun onResponse(call: Call<Signup>, response: Response<Signup>) {
                    if (response?.isSuccessful) {
                        var login = response.body()
                        Log.d("LOGIN", "msg : " + login?.msg)
                        Log.d("LOGIN", "code : " + login?.code)
                        var dialog = AlertDialog.Builder(this@SignupActivity)
                        Toast.makeText(this@SignupActivity, "사진을 찍으세요.", Toast.LENGTH_SHORT).show()
                        intent.putExtra("userid", textId)
                        startActivity(intent)
                        finish()

                    }else {
                        Toast.makeText(getApplicationContext(), "중복되었습니다.", Toast.LENGTH_LONG).show();

                    }
                }
            })


        }
    }
}
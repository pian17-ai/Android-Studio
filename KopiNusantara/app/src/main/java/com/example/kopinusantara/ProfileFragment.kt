package com.example.kopinusantara

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.kopinusantara.api.ApiClient
import com.example.kopinusantara.session.SessionManager
import com.example.kopinusantara.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var txtName: TextView
    private lateinit var txtEmail: TextView
    private lateinit var txtCreated: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        txtName = view.findViewById(R.id.txtName)
        txtEmail = view.findViewById(R.id.txtEmail)
        txtCreated = view.findViewById(R.id.txtCreated)

        getUser()

        btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun getUser() {
        val session = SessionManager(requireContext())
        val token = session.getToken()
        val bearer = "Bearer $token"

        ApiClient.instance.getUser(bearer)
            .enqueue(object : Callback<UserResponse> {

                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        val user = response.body()?.data

                        txtName.text = user?.full_name
                        txtEmail.text = user?.email
                        txtCreated.text = user?.created_at
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }

    private fun logout() {
        val session = SessionManager(requireContext())
        val token = session.getToken()
        val bearer = "Bearer $token"

        ApiClient.instance.logout(bearer)
            .enqueue(object : Callback<Any> {

                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    // walaupun gagal, tetap logout di app
                    session.clearSession()

                    Toast.makeText(requireContext(), "Logout berhasil", Toast.LENGTH_SHORT).show()

                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    // tetap logout juga
                    session.clearSession()

                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            })
    }
}
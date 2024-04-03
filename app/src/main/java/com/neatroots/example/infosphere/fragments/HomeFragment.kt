package com.neatroots.example.infosphere.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.neatroots.example.infosphere.Models.ImageModel
import com.neatroots.example.infosphere.R
import com.neatroots.example.infosphere.Service.ApiClient
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    //var view: View? = null
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        //
        val call = ApiClient.apiService.getImageInfo()
        val ll: LinearLayout? = view.findViewById<LinearLayout>(R.id.item_layout)

        Log.e("activity","started");
        call.enqueue(object : Callback<List<ImageModel>> {
            override fun onResponse(call: Call<List<ImageModel>>, response: retrofit2.Response<List<ImageModel>>) {
                Toast.makeText(requireActivity(), "Success "+response.code(), Toast.LENGTH_SHORT).show()
                val imageModel = response.body()

                val inflater: LayoutInflater = LayoutInflater.from(requireActivity())

                imageModel?.forEach {
                    println(it.id)
                    println(it.description)
                    println(it.url)
                    println(it.type)

                    val inflatedLayout = inflater.inflate(R.layout.item_layout, ll, false) as LinearLayout

                    val imageView: ImageView = inflatedLayout.findViewById<ImageView>(R.id.imageView) // Replace R.id.imageView with your ImageView ID

                    Picasso.get().load(it.url).into(imageView)
                    inflatedLayout.findViewById<TextView>(R.id.description).text = it.description
                    if (ll != null) {
                        ll.addView(inflatedLayout)
                    }
                }

                println(imageModel)
            }

            override fun onFailure(call: Call<List<ImageModel>>, t: Throwable) {
                println(t.message)
            }
        })
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
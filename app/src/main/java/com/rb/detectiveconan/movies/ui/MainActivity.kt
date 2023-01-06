package com.rb.detectiveconan.movies.ui

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.rb.detectiveconan.movies.R
import com.rb.detectiveconan.movies.databinding.ActivityMainBinding
import com.rb.detectiveconan.movies.presentation.MoviesViewModel
import com.rb.detectiveconan.movies.ui.fragments.MoviesUiEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

   private val viewModel :MoviesViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }

    private fun goToMarket(){
        val appPackageName = packageName
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (ex: android.content.ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        try {
            var search_Item= menu?.findItem(R.id.search)
            if (search_Item!=null){
                var search_view=search_Item.actionView as SearchView
                var edit_text=search_view.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)

                edit_text.hint="Enter Number Or Name"
                edit_text.background=AppCompatResources.getDrawable(this,R.drawable.search_input_dark_style)
                edit_text.setTextColor(Color.WHITE)
                edit_text.setHintTextColor(Color.DKGRAY)
                edit_text.height=25
                edit_text.textSize=15f
                edit_text.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(this,R.drawable.ic_search_black_24dp), null, null, null)
                edit_text.compoundDrawablePadding = 8
                edit_text.setPadding(10,3,3,3)
                search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return try {
                            return true
                        }catch (r:Exception){
                            r.printStackTrace()
                            return true
                        }

                    }
                    override fun onQueryTextChange(newText: String?): Boolean {
                        return try {
                            newText?.let { MoviesUiEvent.OnToast<String>(it) }
                                ?.let { viewModel.onUiTriggered(it) }
                            return true
                        }catch (r:Exception){
                            Toast.makeText(applicationContext,r.toString(), Toast.LENGTH_LONG).show()
                            return true
                        }
                    }
                })

                search_view.setOnCloseListener {
                    viewModel.onUiTriggered(MoviesUiEvent.OnToast<String>(""))
                    false
                }

            }
        }catch (ex2:Exception){
            Log.d("MAIN_ACTIVITY",ex2.toString())
        }

        return true
    }
    private fun showDialog(){
        val alertBuilder= AlertDialog.Builder(this)
        val layoutInflation=
            LayoutInflater.from(this).inflate(R.layout.about_layout,null,false)
        alertBuilder.apply {
            setView(layoutInflation)
        }

        val alertCreate=alertBuilder.create()
        val btn=layoutInflation.findViewById<Button>(R.id.rateus_button)
        btn.setOnClickListener {

            alertCreate.dismiss()
            goToMarket()
        }
        alertCreate.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.exit->{finish()}
            R.id.rate_us->{
                goToMarket()
            }
            R.id.search->{}
            R.id.about->{
                showDialog()
            }
        }
        return true
    }
}
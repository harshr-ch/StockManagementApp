package com.harshit.stockmanagementapp.activity

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.harshit.stockmanagementapp.R
import com.harshit.stockmanagementapp.model.UserModel
import com.harshit.stockmanagementapp.database.UsersDBHelper
import java.io.ByteArrayOutputStream


class addItem : AppCompatActivity() {
    lateinit var name_of_entry: EditText
    lateinit var quantity_of_entry: EditText
    lateinit var price_of_entry: EditText
    lateinit var btnsave: Button
    lateinit var btnAddImage: ImageView
    lateinit var id: EditText
    lateinit var usersDBHelper: UsersDBHelper
    lateinit var btnAddImage1: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        usersDBHelper =
            UsersDBHelper(this)
        name_of_entry = findViewById(R.id.entryName)
        quantity_of_entry = findViewById(R.id.quantity)
        price_of_entry = findViewById(R.id.price)
        id = findViewById(R.id.id)
        btnsave = findViewById(R.id.btnsave)
        btnAddImage = findViewById(R.id.btnAddImage)
        btnAddImage1 = findViewById(R.id.btnAddImage1)

        title = "Add An Item"

        btnAddImage.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, 101)
            //btnAddImage?.setImageBitmap(intent?.extras!!.get("data") as Bitmap?)
        }
        btnsave.setOnClickListener {
            val bitmap = (btnAddImage.getDrawable() as? BitmapDrawable)?.getBitmap()
            val stream = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.PNG, 90, stream)
            val image = stream.toByteArray()


            if (id.text.toString().isEmpty() || name_of_entry.text.toString()
                    .isEmpty() || quantity_of_entry.text.toString()
                    .isEmpty()
            )
                Toast.makeText(this@addItem, "You can't leave a entry blank", Toast.LENGTH_SHORT)
                    .show()
            else if (image.isEmpty()
            )
                Toast.makeText(this@addItem, "Add image to your item", Toast.LENGTH_SHORT)
                    .show()
            else {
                var result = usersDBHelper.insertUser(
                    UserModel(
                        userid = id.text.toString(),
                        name = name_of_entry.text.toString(),
                        quantity = quantity_of_entry.text.toString(),
                        price = price_of_entry.text.toString(),
                        image = image
                    )
                )
                Toast.makeText(this@addItem, "Item Added To Inventory", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@addItem, Inventory2::class.java))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            btnAddImage1.visibility = (View.INVISIBLE)
            btnAddImage.setImageBitmap(imageBitmap)
        }
    }

    override fun onBackPressed() {
        if (id.text.toString().isNotEmpty() || name_of_entry.text.toString()
                .isNotEmpty() || quantity_of_entry.text.toString().isNotEmpty() || price_of_entry.text.toString().isNotEmpty()
        ) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Warning!!!")
            builder.setMessage("Your data will not be saved. You still want to go back?")
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                super.onBackPressed()
            }
            builder.setNegativeButton("No") { dialogInterface, which ->

            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        } else {
            super.onBackPressed()
        }
    }
}
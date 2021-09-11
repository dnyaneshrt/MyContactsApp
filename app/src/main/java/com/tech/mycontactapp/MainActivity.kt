package com.tech.mycontactapp


import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get the run time permission.

      var status=  ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS)
      if(status==PackageManager.PERMISSION_GRANTED)
      {
          getContacts()
      }else
      {
          ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.READ_CONTACTS),58)
      }

    }


    private fun getContacts() {

      //step 1: get the object of  contentResolver
        var contentResolver = contentResolver

        //step 2:call query method(URI for contacts: ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
        var cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        var fromArray = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        var intArray = intArrayOf(R.id.text_name, R.id.text_number)

        var cursor_adapter =
            SimpleCursorAdapter(this, R.layout.contact_view, cursor, fromArray, intArray)
        list_view.adapter=cursor_adapter
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            getContacts()
        }else
        {
            Toast.makeText(this,"user is not allwed here",Toast.LENGTH_SHORT).show()
        }
    }
}
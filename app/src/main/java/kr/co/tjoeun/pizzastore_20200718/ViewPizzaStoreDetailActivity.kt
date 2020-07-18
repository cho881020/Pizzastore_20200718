package kr.co.tjoeun.pizzastore_20200718

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_view_pizza_store_detail.*
import kr.co.tjoeun.pizzastore_20200718.datas.PizzaStore

class ViewPizzaStoreDetailActivity : BaseActivity() {

//    들고 온 피자가게 데이터를 저장할 멤버 변수
    lateinit var mPizzaStore : PizzaStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pizza_store_detail)
        setupEvents()
        setValues()
    }
    override fun setupEvents() {

        callBtn.setOnClickListener {
//            실제로 권한 확인하고 전화걸도록

            val pl = object : PermissionListener {
                override fun onPermissionGranted() {
                    val myUri = Uri.parse("tel:${mPizzaStore.phoneNum}")
                    val myIntent = Intent(Intent.ACTION_CALL, myUri)
                    startActivity(myIntent)
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(mContext, "권한 거절로 연결이 불가능합니다.", Toast.LENGTH_SHORT).show()
                }

            }

            TedPermission.with(mContext)
                .setPermissions(Manifest.permission.CALL_PHONE)
                .setDeniedMessage("설정에서 허가해줘야 연결됩니다.")
                .setPermissionListener(pl)
                .check()


        }

    }

    override fun setValues() {

//        가져온 피자가게를 멤버변수에 저장. (캐스팅 활용)
        mPizzaStore = intent.getSerializableExtra("store") as PizzaStore

        Glide.with(mContext).load(mPizzaStore.logoUrl).into(logoImg)

        storeNameTxt.text = mPizzaStore.name
        storePhoneNumTxt.text = mPizzaStore.phoneNum

    }


}
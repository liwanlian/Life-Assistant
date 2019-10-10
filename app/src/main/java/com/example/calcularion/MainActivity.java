package com.example.calcularion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calcularion.Configure.PhoneEditText;
import com.example.calcularion.Configure.QR_code;
import com.example.calcularion.Forgetpassword.Forgetone;
import com.example.calcularion.MYsqldata.DBManager;
import com.example.calcularion.Userdata.HomeActivity;

public class MainActivity extends exitsystem implements View.OnClickListener{

    //登录界面的控件
    PhoneEditText et_accnum;
    EditText et_password;
    EditText et_code;
    ImageView iv_showcode;
    TextView tv_forgetpassword;
    TextView tv_regester;
    Button bt_login;

    private long exitTime=0;
    private String getcode;//获取验证码框的验证码
    private DBManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=new DBManager(this);
        init();

    }
    //初始化控件
    private void init(){

        et_accnum=(PhoneEditText)findViewById(R.id.et_phonenum);
        et_password=(EditText)findViewById(R.id.et_paswword);
        et_code=(EditText)findViewById(R.id.et_code);
        iv_showcode=(ImageView)findViewById(R.id.iv_showCode);
        tv_forgetpassword=(TextView)findViewById(R.id.tv_forgetpassword);
        tv_regester=(TextView)findViewById(R.id.tv_regester);
        bt_login=(Button)findViewById(R.id.bt_login);

        iv_showcode.setImageBitmap(QR_code.getInstance().createBitmap());
        iv_showcode.setOnClickListener(this);
        getcode=QR_code.getInstance().getCode().toLowerCase();

        tv_forgetpassword.setOnClickListener(this);
        tv_regester.setOnClickListener(this);
        bt_login.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //刷新获取新的验证码
            case R.id.iv_showCode:
                iv_showcode.setImageBitmap(QR_code.getInstance().createBitmap());
                getcode=QR_code.getInstance().getCode().toLowerCase();
                break;
            case R.id.tv_forgetpassword:
                Intent intent=new Intent(MainActivity.this, Forgetone.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.tv_regester:
                Intent intent1=new Intent(MainActivity.this, Regester.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);

                break;
            case R.id.bt_login:
                String code=et_code.getText().toString();
                if (code.equals(getcode))
                {
                    String password=et_password.getText().toString();
                    String acc=et_accnum.getText().toString().replaceAll(" " ,"");
                    long accnum=Long.valueOf(acc);
                    if (db.searchaccandpassword(accnum,password)){
                        Intent intent2=new Intent(MainActivity.this, HomeActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putLong("acc_num",accnum);
                        intent2.putExtra("bundle",bundle);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);
                    }
                    else{
                        Toast.makeText(MainActivity.this,"账号或者密码有误，请重新输入",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,"验证码有误，请重新输入",Toast.LENGTH_LONG).show();
                    iv_showcode.setImageBitmap(QR_code.getInstance().createBitmap());
                    getcode=QR_code.getInstance().getCode().toLowerCase();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) != 0) {
            finish();
            System.exit(0);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode== KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }
        return super.onKeyDown(keyCode,event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(),
                    "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        }
        else{
            Intent intent = new Intent();
            intent.setAction(exitsystem.SYSTEM_EXIT);
            sendBroadcast(intent);
        }
    }
}

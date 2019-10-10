package com.example.calcularion.Userdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;


import com.example.calcularion.MainActivity;
import com.example.calcularion.R;
import com.example.calcularion.UserControl.Edit_accnum;
import com.example.calcularion.UserControl.Edit_password;
import com.example.calcularion.UserControl.Edit_username;
import com.example.calcularion.exitsystem;


public class UserActivity extends exitsystem implements View.OnClickListener {

    //界面控件

    Button user_exit;
    TextView tv_accedit,tv_passwordedit,tv_usernameedit,user_counttv;
    ImageView iv_accedit,iv_passwordedit,iv_usernameedit,user_countiv;
    TextView tv_back;
    private long accnum;
    private long exitTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Bundle bundle=getIntent().getBundleExtra("bundle");
        accnum=bundle.getLong("acc_num");
        initView();
    }
    //界面初始化
    private void initView(){
       // lv_settting=(ListView)findViewById(R.id.lv_settting);
        user_exit=(Button)findViewById(R.id.user_exit);
        tv_accedit=(TextView)findViewById(R.id.tv_accedit);
        tv_passwordedit=(TextView)findViewById(R.id.tv_passwordedit);
        tv_usernameedit=(TextView)findViewById(R.id.tv_usernameedit);
        user_counttv=(TextView)findViewById(R.id.user_counttv);
        iv_accedit=(ImageView)findViewById(R.id.iv_accedit);
        iv_passwordedit=(ImageView)findViewById(R.id.iv_passwordedit);
        iv_usernameedit=(ImageView)findViewById(R.id.iv_usernameedit);
        user_countiv=(ImageView)findViewById(R.id.user_countiv);
        tv_back=(TextView)findViewById(R.id.user_back);

        user_exit.setOnClickListener(this);
        tv_accedit.setOnClickListener(this);
        tv_passwordedit.setOnClickListener(this);
        tv_usernameedit.setOnClickListener(this);
        user_counttv.setOnClickListener(this);
        iv_accedit.setOnClickListener(this);
        iv_passwordedit.setOnClickListener(this);
        iv_usernameedit.setOnClickListener(this);
        user_countiv.setOnClickListener(this);
        tv_back.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.user_back:
                Bundle bundle5=getIntent().getBundleExtra("bundle");
                Intent intent5=new Intent(UserActivity.this,HomeActivity.class);
                intent5.putExtra("bundle",bundle5);
                intent5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent5);
                break;
            case R.id.user_exit:
                Intent intent =new Intent(UserActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.tv_accedit:
            case R.id.iv_accedit:
                Bundle bundle=new Bundle();
                bundle.putLong("acc_num",accnum);
                Intent intent2=new Intent(UserActivity.this, Edit_accnum.class);
                intent2.putExtra("bundle",bundle);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                break;
            case R.id.tv_passwordedit:
            case R.id.iv_passwordedit:
                Bundle bundle1=new Bundle();
                bundle1.putLong("acc_num",accnum);
                Intent intent3=new Intent(UserActivity.this, Edit_password.class);
                intent3.putExtra("bundle",bundle1);
                intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent3);
                break;
            case R.id.tv_usernameedit:
            case R.id.iv_usernameedit:
                Bundle bundle2=new Bundle();
                bundle2.putLong("acc_num",accnum);
                Intent intent4=new Intent(UserActivity.this, Edit_username.class);
                intent4.putExtra("bundle",bundle2);
                intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent4);
                break;
            case R.id.user_counttv:
            case R.id.user_countiv:
                Bundle bundle3=getIntent().getBundleExtra("bundle");
                Intent intent6=new Intent(UserActivity.this,CheckActivity.class);
                intent6.putExtra("bundle",bundle3);
                intent6.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent6);
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

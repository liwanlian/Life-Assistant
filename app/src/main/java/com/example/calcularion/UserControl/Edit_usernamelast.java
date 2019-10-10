package com.example.calcularion.UserControl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calcularion.MYsqldata.DBManager;
import com.example.calcularion.R;
import com.example.calcularion.Userdata.UserActivity;
import com.example.calcularion.exitsystem;

public class Edit_usernamelast extends exitsystem implements View.OnClickListener {
//界面控件
    TextView tv_back;
    EditText et_name,et_zname;
    Button bt_sure;
    private DBManager db;
    private  int id;
    private long accnum;
    private long exitTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_usernamelast);
       initView();
     db=new DBManager(this);
     Bundle bundle=getIntent().getBundleExtra("bundle");
     id=bundle.getInt("id");
     accnum=bundle.getLong("acc_num");
    }
    private void initView(){
        tv_back=(TextView)findViewById(R.id.editusernamelast_back);
        et_name=(EditText)findViewById(R.id.editusernamelast_name);
        et_zname=(EditText)findViewById(R.id.editusernalast_zname);
        bt_sure=(Button)findViewById(R.id.editusernamelast_sure);

        tv_back.setOnClickListener(this);
        bt_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.editpasswordlast_back:
                Bundle bundle=getIntent().getBundleExtra("bundle");
                Intent intent=new Intent(Edit_usernamelast.this,Edit_username.class);
                intent.putExtra("bundle",bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.editusernamelast_sure:
                String namw=et_name.getText().toString();
                String zname=et_zname.getText().toString();
                if (namw.equals(zname))
                {
                    boolean result=db.updateusername(id,namw);
                    if (result)
                    {
                        Toast.makeText(Edit_usernamelast.this,"更改成功",Toast.LENGTH_LONG).show();
                        Bundle bundle1=new Bundle();
                        bundle1.putLong("acc_num",accnum);
                        bundle1.putString("name",namw);
                        Intent intent1=new Intent(Edit_usernamelast.this,UserActivity.class);
                        intent1.putExtra("bundle",bundle1);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);
                    }
                    else {
                        Toast.makeText(Edit_usernamelast.this, "更改失败", Toast.LENGTH_LONG).show();
                        Bundle bundle2=getIntent().getBundleExtra("bundle");
                        Intent intent1=new Intent(Edit_usernamelast.this, UserActivity.class);
                        intent1.putExtra("bundle",bundle2);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);
                    }

                }
                else
                    Toast.makeText(Edit_usernamelast.this,"前后输入的用户名不一致",Toast.LENGTH_LONG).show();
                break;
            default:break;
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

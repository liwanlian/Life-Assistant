package com.example.calcularion.Forgetpassword;

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
import com.example.calcularion.MainActivity;
import com.example.calcularion.R;
import com.example.calcularion.exitsystem;

public class Forgettwo extends exitsystem {

    //界面控件
    EditText forgettwo_newpassword;
    EditText forgettwo_znewpassword;
    Button bt_forgettwo;
    DBManager db;
    TextView tv_backtwo;
    int id;
    Bundle b;
    private long exitTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgettwo);
        init();
        db=new DBManager(this);
         b=getIntent().getBundleExtra("bundle");
         id=b.getInt("id");
        bt_forgettwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String password;
                String zpassword;
                password=forgettwo_newpassword.getText().toString();
                zpassword=forgettwo_znewpassword.getText().toString();
                long acc=b.getLong("acc_num");
                if (password.equals(zpassword)){
                   if (db.updatapassword(id,password)){
                       Toast.makeText(Forgettwo.this,"修改密码成功",Toast.LENGTH_LONG).show();
                       Intent intent=new Intent(Forgettwo.this, MainActivity.class);
                       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                       startActivity(intent);
                   }
                   else{
                       Toast.makeText(Forgettwo.this,"修改密码失败",Toast.LENGTH_LONG).show();
                       Intent intent=new Intent(Forgettwo.this, MainActivity.class);
                       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                       startActivity(intent);
                   }
                }
                else{
                    Toast.makeText(Forgettwo.this,"前后输入的密码不一致",Toast.LENGTH_LONG).show();
                    forgettwo_newpassword.setText("");
                    forgettwo_znewpassword.setText("");
                }
            }
        });
        tv_backtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Forgettwo.this, Forgetone.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    //控件初始化
    private void init(){
        forgettwo_newpassword=(EditText)findViewById(R.id.forgettwo_newpassword);
        forgettwo_znewpassword=(EditText)findViewById(R.id.forgettwo_znewpassword);
        bt_forgettwo=(Button)findViewById(R.id.bt_forgettwo);
        tv_backtwo=(TextView)findViewById(R.id.tv_backtwo);
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

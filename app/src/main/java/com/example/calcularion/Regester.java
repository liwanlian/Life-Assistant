package com.example.calcularion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.calcularion.Configure.PhoneEditText;
import com.example.calcularion.Configure.QR_code;
import com.example.calcularion.MYsqldata.DBManager;

public class Regester extends AppCompatActivity implements View.OnClickListener  {

    //注册界面的控件
    EditText et_username;
    PhoneEditText regester_acc;
    EditText regester_password;
    EditText regester_zpassword;
    EditText regester_code;
    ImageView regester_showcode;
    Button bt_regester;
    Button  bt_exit;

    private String getcode;

   private DBManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester);
        db=new DBManager(this);
        init();

    }
    //初始化控件
    private void init(){
        et_username=(EditText)findViewById(R.id.et_username);
        regester_acc=(PhoneEditText)findViewById(R.id.regester_acc);
        regester_code=(EditText)findViewById(R.id.regester_code);
        regester_password=(EditText)findViewById(R.id.regester_password);
        regester_zpassword=(EditText)findViewById(R.id.regester_zpassword);
        regester_showcode=(ImageView)findViewById(R.id.regester_showCode);
        bt_regester=(Button)findViewById(R.id.bt_regester);
        bt_exit=(Button)findViewById(R.id.bt_exit);


        regester_acc.setInputType(InputType.TYPE_CLASS_PHONE);

        regester_showcode.setImageBitmap(QR_code.getInstance().createBitmap());
        regester_showcode.setOnClickListener(this);
        getcode=QR_code.getInstance().getCode().toLowerCase();

        bt_regester.setOnClickListener(this);
        bt_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.regester_showCode:
                regester_showcode.setImageBitmap(QR_code.getInstance().createBitmap());
                getcode=QR_code.getInstance().getCode().toLowerCase();
                break;
            case R.id.bt_regester:
                String code=regester_code.getText().toString();
                if (!code.equals(getcode)){
                    Toast.makeText(Regester.this,"验证码有误，请重新输入",Toast.LENGTH_LONG).show();
                    regester_showcode.setImageBitmap(QR_code.getInstance().createBitmap());
                    getcode=QR_code.getInstance().getCode().toLowerCase();
                    et_username.setText("");
                    regester_code.setText("");
                    regester_password.setText("");
                    regester_zpassword.setText("");
                    regester_acc.setText("");
                }
                else{
                    String password=regester_password.getText().toString();
                    String zpassword=regester_zpassword.getText().toString();
                    String acc_num=regester_acc.getText().toString().replaceAll(" " ,"") ;
                    if (!password.equals(zpassword)){
                        Toast.makeText(Regester.this,"前后输入的密码不一致，请重新输入",Toast.LENGTH_LONG).show();
                        regester_showcode.setImageBitmap(QR_code.getInstance().createBitmap());
                        getcode=QR_code.getInstance().getCode().toLowerCase();
                        et_username.setText("");
                        regester_code.setText("");
                        regester_password.setText("");
                        regester_zpassword.setText("");
                        regester_acc.setText("");
                    }
                    else{
                        long accnum=Long.valueOf(acc_num);
                        boolean result=db.searchacc_num(accnum);
                        if (result){
                            Toast.makeText(Regester.this,"该手机号已经被注册了，请输入另外的手机号",Toast.LENGTH_LONG).show();
                            regester_showcode.setImageBitmap(QR_code.getInstance().createBitmap());
                            getcode=QR_code.getInstance().getCode().toLowerCase();
                            et_username.setText("");
                            regester_code.setText("");
                            regester_password.setText("");
                            regester_zpassword.setText("");
                            regester_acc.setText("");
                        }
                        else{
                            int id=db.countid();
                            String name=et_username.getText().toString();
                            db.addnewdata(id,name,accnum,password);
                            Intent intent=new Intent(Regester.this,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }
                }
                break;
            case R.id.bt_exit:
                Intent intent=new Intent(Regester.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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
}
